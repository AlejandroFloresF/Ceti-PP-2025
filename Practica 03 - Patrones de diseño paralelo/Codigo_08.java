// Codigo 08 - Map
// Marco Antonio Galindo Torres - 22110221

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Codigo_08 extends RecursiveAction {

    // Mínimo de elementos
    private static final int THRESHOLD = 5;
    // Tamaño del arreglo
    private static final int ARRAY_LENGTH = 20;

    // Atributos
    private int[] input;
    private int[] output;
    private int start;
    private int end;

    // Constructor
    public Codigo_08(int[] input, int[] output, int start, int end) {
        this.input = input;
        this.output = output;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int length = end - start;
        // Si el tamaño es menor al mínimo, aplicamos la máscara directamente
        if (length <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                output[i] = applyMask(input[i]);
            }
        }
        // Caso contrario, hemos de dividir la tarea aún más
        else {
            // Inicializamos las tareas
            int mid = start + length / 2;
            Codigo_08 leftTask = new Codigo_08(input, output, start, mid);
            Codigo_08 rightTask = new Codigo_08(input, output, mid, end);

            // Las ejecutamos y esperamos a que terminen
            invokeAll(leftTask, rightTask);
        }
    }

    private int applyMask(int i) {
        // Aquí es donde se aplicaría una máscara, y esta podría ser cualquiera
        // Para el propósito del ejemplo, vamos a devolver el doble
        return i * i;
    }

    public static void main(String[] args) {
        int[] input = new int[ARRAY_LENGTH];
        int[] output = new int[ARRAY_LENGTH];

        // Llenamos el arreglo de entrada con valores del 1 al ARRAY_LENGTH + 1
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            input[i] = i + 1;
        }

        // Creamos la piscina de hilos
        ForkJoinPool pool = new ForkJoinPool();
        // Creamos la tarea que dividirá y conquistará la suma de elementos del arreglo
        Codigo_08 task = new Codigo_08(input, output, 0, ARRAY_LENGTH);
        // Invocamos la tarea
        pool.invoke(task);

        // Mostramos el resultado
        System.out.println("Input: " + java.util.Arrays.toString(input));
        System.out.println("Output: " + java.util.Arrays.toString(output));
    }

}