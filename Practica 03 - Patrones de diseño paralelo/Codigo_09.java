// Codigo 09 - Stencil
// Marco Antonio Galindo Torres - 22110221

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Random;

public class Codigo_09 extends RecursiveAction {

    // Mínimo de elementos
    private static final int THRESHOLD = 2;
    // Tamaño del arreglo
    private static final int ARRAY_LENGTH = 6;

    // Atributos
    private int[][] input;
    private int[][] output;
    private int startRow;
    private int endRow;

    // Constructor
    public Codigo_09(int[][] input, int[][] output, int startRow, int endRow) {
        this.input = input;
        this.output = output;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    protected void compute() {
        int rows = endRow - startRow;

        // Si el tamaño es menor al mínimo, aplicamos la máscara directamente
        if (rows <= THRESHOLD) {
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < input[0].length; j++) {
                    output[i][j] = applyMask(i, j);
                }
            }
        }

        // Caso contrario, hemos de dividir la tarea aún más
        else {
            // Inicializamos las tareas
            int mid = startRow + rows / 2;
            Codigo_09 topTask = new Codigo_09(input, output, startRow, mid);
            Codigo_09 bottomTask = new Codigo_09(input, output, mid, endRow);

            // Las ejecutamos y esperamos a que terminen
            invokeAll(topTask, bottomTask);
        }
    }

    private int applyMask(int i, int j) {
        // Aquí es donde se aplicaría una máscara, y esta podría ser cualquiera
        // Para el propósito del ejemplo, devuelve el promedio de los ~9 elementos
        // del cubo 3x3 con centro en (i, j)

        int sum = 0; // Total de la suma
        int count = 0; // Total de elementos contados [ya que en las esquinas no es 9]

        // Procede revisar los elementos dentro del cuadro de 3x3
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                // Calculamos las coordenadas del elemento actual
                int ii = a + i;
                int jj = b + j;

                // Revisamos que el elemento actual esté dentro de la matriz
                if (ii >= 0 && ii < ARRAY_LENGTH && jj >= 0 && jj < ARRAY_LENGTH) {
                    // De ser así, lo agregamos a la suma y aumentamos el contador en 1
                    sum += input[ii][jj];
                    count++;
                }
            }
        }

        // Retornamos el promedio de esos elementos
        return sum / count;
    }

    public static void main(String[] args) {
        int[][] input = new int[ARRAY_LENGTH][ARRAY_LENGTH];
        int[][] output = new int[ARRAY_LENGTH][ARRAY_LENGTH];

        // Llenamos el arreglo de entrada con valores aleatorios
        Random random = new Random();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            for (int j = 0; j < ARRAY_LENGTH; j++) {

                input[i][j] = random.nextInt(100);
            }
        }

        // Creamos la piscina de hilos
        ForkJoinPool pool = new ForkJoinPool();
        // Creamos la tarea que dividirá y conquistará la suma de elementos del arreglo
        Codigo_09 task = new Codigo_09(input, output, 0, ARRAY_LENGTH);
        // Invocamos la tarea
        pool.invoke(task);

        // Mostramos el resultado
        System.out.println("Input: ");
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            System.out.println(java.util.Arrays.toString(input[i]));
        }
        System.out.println("Output: ");
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            System.out.println(java.util.Arrays.toString(output[i]));
        }
    }

}