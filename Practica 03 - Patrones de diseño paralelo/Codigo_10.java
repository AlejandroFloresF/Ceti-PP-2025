// Codigo 10 - Reduce
// Marco Antonio Galindo Torres - 22110221

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Codigo_10 extends RecursiveTask<Integer> {

    // Mínimo de elementos
    private static final int THRESHOLD = 20;
    // Tamaño del arreglo
    private static final int ARRAY_LENGTH = 100;

    // Atributos
    private int[] numbers;
    private int start;
    private int end;

    // Constructor
    public Codigo_10(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int length = end - start;
        // Si el tamaño es menor al mínimo, empezamos a sumar
        if (length <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        }
        // Caso contrario, hemos de dividir la tarea aún más
        else {
            // Inicializamos las tareas
            int mid = start + length / 2;
            Codigo_10 leftTask = new Codigo_10(numbers, start, mid);
            Codigo_10 rightTask = new Codigo_10(numbers, mid, end);

            // Dividimos y ejecutamos en paralelo
            leftTask.fork(); // Envamos la subtarea izquierda al pool de hilos para que se ejecute en
                             // paralelo
            int rightResult = rightTask.compute(); // Ejecutamos la derecha en este mismo hilo
            int leftResult = leftTask.join(); // Esperamos a que la izquierda termine y regrese

            // Imprimimos el resultado de la suma parcial
            System.out.println(
                    "Suma parcial de " + leftResult + " + " + rightResult + " = " + (leftResult + rightResult));

            // Retornamos el resultado de la suma
            return leftResult + rightResult;
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[ARRAY_LENGTH];
        // Incializamos el arreglo con números del 1 al 100
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            numbers[i] = i + 1;
        }

        // Creamos la piscina de hilos
        ForkJoinPool pool = new ForkJoinPool();
        // Creamos la tarea que dividirá y conquistará la suma de elementos del arreglo
        Codigo_10 task = new Codigo_10(numbers, 0, ARRAY_LENGTH);
        // El resultado se obtiene al invocar la tarea con pool.invoke
        int result = pool.invoke(task);

        // Imprimimos el resultado
        // Se espera que sea 5050 al sumar los numeros del 1 al 100
        System.out.println("Suma total: " + result);
    }

}