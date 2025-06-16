import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ReductionExample {

    // Implementación de una tarea de reducción usando Fork/Join
    private static class SumTask extends RecursiveTask<Long> {
        private final long[] array;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 1000; // Umbral para la división

        public SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            // Si la tarea es lo suficientemente pequeña, la resolvemos directamente (secuencialmente)
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Si es grande, la dividimos en dos subtareas
                int mid = start + (end - start) / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);

                // Bifurcamos la tarea izquierda para que se ejecute en paralelo
                leftTask.fork();

                // Calculamos la tarea derecha en el hilo actual
                Long rightResult = rightTask.compute();

                // Esperamos a que la tarea izquierda termine y combinamos los resultados
                Long leftResult = leftTask.join();

                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Patrón de Reducción (usando Fork/Join para la suma) ---");

        // Crear un array grande para demostrar la paralelización
        long[] numbers = new long[1000000]; // Un millón de elementos
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1; // Valores de 1 a 1,000,000
        }

        // Crear un pool de Fork/Join
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Crear la tarea principal de suma
        SumTask mainTask = new SumTask(numbers, 0, numbers.length);

        // Ejecutar la tarea y obtener el resultado
        long totalSum = forkJoinPool.invoke(mainTask);

        // Calcular la suma esperada (fórmula para la suma de una serie aritmética)
        long expectedSum = (long) numbers.length * (numbers.length + 1) / 2;

        System.out.println("Suma calculada: " + totalSum);
        System.out.println("Suma esperada: " + expectedSum);
        System.out.println("Resultados coinciden: " + (totalSum == expectedSum));

        forkJoinPool.shutdown(); // Apagar el pool
    }
}