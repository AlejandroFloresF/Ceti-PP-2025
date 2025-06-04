import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

/*
 * Código de Alejandro Velazquez Luna 
 * Fork Join
 * En este patrón de diseño se generan dos ejecuciones concurrentes, que empieza inmediatamente después de que el fork es 
 * llamado en código, después, se usa join para combinar estas dos ejecuciones concurrentes en una. Cada join puede unirse 
 * entonces a su fork correspondiente y lo hace antes de las otras terminen.
 * 
 * El objetivo de este codigo es calcular la suma de los elementos de un arreglo de enteros
 * utilizando el patron Fork Join.
 * 
 * Para resolverlo utilizamos lo siguiente:
 * 
 * -> RecursiveTask<T> - Clase abstracta para tareas que retornan un valor.
 * -> compute() - Método que se ejecuta cuando la tarea es invocada.
 * -> fork() - lanza la tarea en un hilo separado.
 * -> join(): espera a que termine la tarea paralela y obtiene su resultado.
 * -> ForkJoinPool: ejecuta las tareas en paralelo, reutilizando hilos.
 */

public class ArraySumForkJoin {

    static class SumTask extends RecursiveTask<Integer> {
        //Arreglo a procesar
        private final int[] arr;
        //Index inicial del segmento
        private final int start;
        //Index final del segmento
        private final int end;

        //Caso base => Umbral para no dividir más
        private static final int THRESHOLD = 10;

        // Constructor de la tarea
        public SumTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        //Sobreescribimos el metood original
        @Override
        //Metodo que se ejecuta cuando la tarea es invocada
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                // Resolver directamente si el segmento es pequeño
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += arr[i];
                }
                return sum;
            } else {
                // Fork: dividir el problema en dos tareas
                int mid = (start + end) / 2;
                //Cremos la tarea para la mitad izquierda
                SumTask leftTask = new SumTask(arr, start, mid);
                //Creamos la tarea para la mitad derecha
                SumTask rightTask = new SumTask(arr, mid, end);
                // Ejecutar en paralelo
                leftTask.fork(); 
                // Ejecutar en este hilo
                int rightResult = rightTask.compute(); 
                // Esperar resultado del fork
                int leftResult = leftTask.join(); 

                // Combinamos los resultados obetenidos de ambas partes
                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        //Creamos un array de enteros con 100 posiciones.
        int[] array = new int[100];
        //Llenamos el array con valores del 1 al 100
        for (int i = 0; i < array.length; i++) array[i] = i + 1; 
        // Crea un ForkJoinPool para ejecutar tareas en paralelo
        ForkJoinPool pool = new ForkJoinPool();
        //Creamos la task que viene siendo una instancia de la clase SumTask
        SumTask task = new SumTask(array, 0, array.length);
        // Ejecutamos la tarea y obtenemos el resultado
        int result = pool.invoke(task);
        //Mostramos el resultado en consola
        System.out.println("Suma total: " + result);
        //Cerramos el pool para evitar posibles leaks
        pool.close();
    }
}
