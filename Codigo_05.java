import java.util.*;
import java.util.concurrent.*;

/*
 * Strong Consistency con synchronized
 * Codigo de Alejandro Velazquez Luna
 * Registro: 22110228
 * Materia: Computacion Paralela
 * Practica 02 Modelos de Consistencia de Memoria 
 */
public class Codigo_05 {
    /**
     * Dado un array de enteros y un número, , realiza rotaciones a la izquierda en
     * el array. Devuelve el array actualizado para que se imprima como una sola
     * línea de enteros separados por espacios.
     * 
     * @param nums el array a rotar
     * @param d    el número de posiciones a rotar el array
     * 
     * @return el array rotado
     */
    public synchronized int[] rotLeft(int[] nums, int d) {
        // Length del array
        int n = nums.length;
        // Creacion de arreglo para almacenar el resultado
        int[] rotated = new int[n];

        // For para recorrer el array realizar las rotaciones
        for (int i = 0; i < n; i++) {
            // Calculamos la nueva posicion de cada elemento
            rotated[i] = nums[(i + d) % n];
        }
        // Retornamos el array rotado
        return rotated;
    }

    // Main methos para probar el codigo
    public static void main(String[] args) throws InterruptedException {
        // Instanciamos la clase Codigo_05
        Codigo_05 solver = new Codigo_05();
        // Array de enteros a rotar
        int[] nums = { 1, 2, 3, 4, 5 };
        // Creamos un ExecutorService para ejecutar tareas en paralelo, en este caso
        // creamos solamente dos hilos
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // Enviamos las tareas al ExecutorService para que se ejecuten en paralelo
        executor.submit(() -> System.out.println(Arrays.toString(solver.rotLeft(nums, 2))));
        executor.submit(() -> System.out.println(Arrays.toString(solver.rotLeft(nums, 4))));
        // Esperamos a que todas las tareas se completen
        executor.shutdown();
    }
}