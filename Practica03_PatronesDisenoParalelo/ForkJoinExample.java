import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample {

    // Clase para la tarea que suma un rango de un array
    private static class SumArrayTask extends RecursiveTask<Long> {
        private final long[] array;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 1000; // Umbral para la división de la tarea

        public SumArrayTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            // Si el tamaño del segmento es menor o igual al umbral, se procesa secuencialmente
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Si es más grande, se divide en dos subtareas
                int mid = start + (end - start) / 2;
                SumArrayTask leftTask = new SumArrayTask(array, start, mid);
                SumArrayTask rightTask = new SumArrayTask(array, mid, end);

                // Bifurca la tarea izquierda para que se ejecute en paralelo
                leftTask.fork();

                // Calcula la tarea derecha en el hilo actual (o lo que esté disponible)
                Long rightResult = rightTask.compute();

                // Espera a que la tarea izquierda termine y obtiene su resultado
                Long leftResult = leftTask.join();

                // Combina los resultados
                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Patrón Fork-Join: Suma de un Array Grande ---");

        long[] largeArray = new long[1000000]; // Array de 1 millón de elementos
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i + 1; // Llenar con valores para sumar
        }

        // Crear un ForkJoinPool, que es el ejecutor para tareas Fork/Join
        ForkJoinPool pool = new ForkJoinPool();

        // Crear la tarea principal que abarcará todo el array
        SumArrayTask mainTask = new SumArrayTask(largeArray, 0, largeArray.length);

        // Ejecutar la tarea en el pool y obtener el resultado
        long totalSum = pool.invoke(mainTask);

        // Calcular la suma esperada para verificación
        long expectedSum = (long)largeArray.length * (largeArray.length + 1) / 2;

        System.out.println("Suma calculada por Fork-Join: " + totalSum);
        System.out.println("Suma esperada: " + expectedSum);
        System.out.println("Resultados coinciden: " + (totalSum == expectedSum));

        // Apagar el pool
        pool.shutdown();
    }
}