import java.util.*;

//Weak Consistency sin sincronización
/*
 * Strong Consistency con synchronized
 * Codigo de Alejandro Velazquez Luna
 * Registro: 22110228
 * Materia: Computacion Paralela
 * Practica 02 Modelos de Consistencia de Memoria 
 */
public class Codigo_06 {

    // Conjunto sincronizado para evitar condiciones de carrera
    Set<Integer> visto = Collections.synchronizedSet(new HashSet<>());

    /**
     * Metoodo que verifica si hay duplicados en un array de enteros. Utiliza un
     * conjunto para almacenar los elementos vistos hasta ahora. Si se encuentra un 
     * elemento que ya esta en el conjunto, se devuelve true. Si no, se agrega el conjunto 
     * y se continua. Al final, si no se encuentran duplicados, se devuele false.
     * 
     * @param nums array de enteros a verificar 
     * @return true si hay duplicados, false si no
     */
    public boolean containsDuplicate(int[] nums) {
        // Sincronizamos el bloque de código que usa el conjunto compartido
        synchronized (visto) {
            //Iteramos sobre el array de enteros 
            for (int num : nums) {
                // Verificamos si el conjunto contiene el numero actual
                if (visto.contains(num)) {
                    return true;
                }
                // Si no lo contiene, lo agregamos al conjunto
                visto.add(num);
            }
            return false;
        }
    }

    public static void main(String[] args) {
        // Instanciamos la clase Codigo_06
        Codigo_06 solver = new Codigo_06();

        // Tarea que se ejecutará en múltiples hilos
        Runnable task = () -> {
            //Llamamos al metodo containsDuplicate y mostramos el resultado
            boolean result = solver.containsDuplicate(new int[] { 2, 7, 11, 15 });
            // Mostramos el nombre de cada hilo y el resultado obtenido
            System.out.println(Thread.currentThread().getName() + " → Resultado: " + result);
        };

        // Crear y ejecutar dos hilos concurrentes
        new Thread(task, "Hilo-1").start();
        new Thread(task, "Hilo-2").start();
    }
}
