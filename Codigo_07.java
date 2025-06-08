import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

// Clase que representa una tarea recursiva que retorna un resultado (long)
// Se usa RecursiveTask para tareas que devuelven valores (RecursiveAction es para tareas sin valor de retorno)
public class Codigo_07 extends RecursiveTask<Long> {
    // Se define cuándo dividir la tarea o resolverla directamente
    private static final int THRESHOLD = 3;
    private int[] numeros;
    private int inicio;
    private int fin;

    // Constructor que recibe el arreglo y el rango que debe procesar esta instancia de la tarea
    public Codigo_07(int[] otrosNumeros, int inicio, int fin) {
        this.numeros = otrosNumeros;
        this.inicio = inicio;
        this.fin = fin;
    }

    // Método que se ejecuta cuando se invoca la tarea
    @Override
    protected Long compute() {
        // Si el rango de números es pequeño (menor o igual al umbral), se procesa secuencialmente
        if (fin - inicio <= THRESHOLD) {
            long sum = 0;
            for (int i = inicio; i < fin; i++) {
                sum += numeros[i];
            }
            return sum;
        } else {
            // Si el rango es grande, se divide en dos tareas: izquierda y derecha (Fork)
            int mid = (inicio + fin) / 2;
            Codigo_07 izquierda = new Codigo_07(numeros, inicio, mid);
            Codigo_07 derecha = new Codigo_07(numeros, mid, fin);

            izquierda.fork(); // Lanza la sub-tarea izquierda en otro hilo de manera asíncrona
            long izqResultado = derecha.compute(); // La sub-tarea derecha se ejecuta en el hilo actual
            long derResultado = izquierda.join(); // Join: espera a que termine la sub-tarea izquierda y obtiene su resultado

            // Se combinan los resultados de ambas mitades (Join)
            return derResultado + izqResultado;
        }
    }

    public static void main(String[] args) {
        // Arreglo de entrada a procesar
        int[] nums = {1, 2, 3, 4, 5, 6, 7};

        // Se crea un pool de hilos para ejecutar tareas Fork-Join
        ForkJoinPool pool = new ForkJoinPool();

        // Se crea una tarea para sumar todos los elementos del arreglo
        Codigo_07 task = new Codigo_07(nums, 0, nums.length);

        // Se ejecuta la tarea principal (invoca compute internamente)
        long result = pool.invoke(task);

        // Se imprime el resultado final
        System.out.println("Suma total: " + result);

        // Se cierra el pool para liberar recursos
        pool.shutdown();
    }
}
