// Codigo 11 - Scan
// Marco Antonio Galindo Torres - 22110221

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Codigo_11 extends RecursiveAction {

    // Mínimo de elementos
    private static final int THRESHOLD = 5;
    // Tamaño del arreglo
    private static final int ARRAY_LENGTH = 20;

    // Atributos
    private int[] input;
    private int[] output;
    private int start;
    private int end;
    private int[] total; // Almacena la suma total del rango actual
    // Se usa un int[] para total ya que los objetos (array) se pasan como
    // referencia en java, a diferencia de los tipos primitivos

    // Constructor
    public Codigo_11(int[] input, int[] output, int start, int end, int[] total) {
        this.input = input;
        this.output = output;
        this.start = start;
        this.end = end;
        this.total = total;
    }

    @Override
    protected void compute() {
        int length = end - start;

        // Si el tamaño es menor al mínimo, empezamos a computar
        if (length <= THRESHOLD) {
            int sum = 0;
            // hacemos un scan secuencial
            for (int i = start; i < end; i++) {
                // Vamos sumando los valores del subgrupo actual
                sum += input[i];
                // Y los vamos anotando tambien en la salida
                output[i] = sum;
            }

            // Si hay un arreglo total, guardamos la suma total de la partición
            // Esto solo será necesario en las subtareas
            if (total != null && total.length > 0) {
                total[0] = sum;
            }
        }
        // Caso contrario, hemos de dividir la tarea aún más
        else {
            // Calculamos un punto medio
            int mid = start + length / 2;

            // Declaramos variables para capturar la suma parcial de los lados izq y der
            int[] leftTotal = new int[1];
            int[] rightTotal = new int[1];

            // Inicializamos las tareas
            Codigo_11 leftTask = new Codigo_11(input, output, start, mid, leftTotal);
            Codigo_11 rightTask = new Codigo_11(input, output, mid, end, rightTotal);

            // Dividimos y ejecutamos en paralelo
            leftTask.fork(); // Envamos la subtarea izquierda al pool de hilos para que se ejecute en
                             // paralelo
            rightTask.compute(); // Ejecutamos la derecha en este mismo hilo
            leftTask.join(); // Esperamos a que la izquierda termine y regrese

            // Una vez que tenemos la suma del lado izquierdo, se debe propagar ese valor a
            // cada elemento del lado derecho
            for (int i = mid; i < end; i++) {
                output[i] += leftTotal[0];
                ;
            }

            // El total de la sección actual es la suma de los totales izquierdos y derechos
            // Esta suma se propaga hacia arriba, para que el ancestro ajuste su lado
            // derecho. Claro que solo es necesario cuando hay dicho ancestro: cuando 
            // total != null
            if (total != null && total.length > 0) {
                total[0] = leftTotal[0] + rightTotal[0];
            }
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[ARRAY_LENGTH];
        // Incializamos el arreglo de entrada con números del 1 al ARRAY_LENGTH
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            numbers[i] = i + 1;
        }

        // Creamos el arreglo de salida
        int[] output = new int[ARRAY_LENGTH];

        // Creamos la piscina de hilos
        ForkJoinPool pool = new ForkJoinPool();
        // Creamos la tarea que dividirá y conquistará la suma de elementos del arreglo
        Codigo_11 task = new Codigo_11(numbers, output, 0, ARRAY_LENGTH, null);
        // El resultado se obtiene al invocar la tarea con pool.invoke
        pool.invoke(task);

        // Imprimimos la salida
        System.out.println("Input: " + Arrays.toString(numbers));
        System.out.println("Scan: " + Arrays.toString(output));
    }

}
