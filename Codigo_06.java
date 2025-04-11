import java.util.*;
import java.util.concurrent.*;

// Clase principal
public class Codigo_06 {

    // Mapa concurrente para almacenar las transacciones 
    private final Map<Integer, Integer> mapaTransacciones = new ConcurrentHashMap<>();

    // Método que busca dos transacciones donde la suma sea igual al límite sospechoso
    public int[] transaccionSospechosa(int[] transacciones, int limite) {
        for (int i = 0; i < transacciones.length; i++) {
            int complemento = limite - transacciones[i];// Se calcula cuánto falta para llegar al límite con la transacción actual
            if (mapaTransacciones.containsKey(complemento)) { // Si el complemento ya fue registrado en el mapa, se retorna el par de índices
                return new int[]{mapaTransacciones.get(complemento), i};
            }
            mapaTransacciones.put(transacciones[i], i);// Si no se encontró el complemento, se guarda la transacción actual en el mapa
        }
        return null; // Si no se encontró ningún par que cumpla con la condición, se retorna null
    }

    public static void main(String[] args) throws InterruptedException {
        Codigo_06 solver = new Codigo_06(); // Se crea una instancia del detector de transacciones

        int[] transacciones = {2500, 4000, 6000, 7500, 5000, 8500};// Arreglo de montos de transacciones simuladas
        int limite1 = 10000;  // Límite sospechoso a verificar

        // Se define una tarea runnable que ejecuta la verificación de transacciones
        Runnable task = () -> {
            int[] resultado = solver.transaccionSospechosa(transacciones, limite1);
            resultados(resultado, limite1, transacciones);
        };

        // Se ejecuta la tarea en dos hilos separados
        new Thread(task).start();
        new Thread(task).start();
    }

    // Método para mostrar los resultados 
    public static void resultados(int[] resultado, int limite1, int[] transacciones) {
        if (resultado != null) {            // Si se encontró la suma, se imprimen los índices y montos
            System.out.println("|Límite $" + limite1 + "| Sospecha entre índices: " +
                    Arrays.toString(resultado) +
                    " - Montos: $" + transacciones[resultado[0]] + " + $" + transacciones[resultado[1]]);
        } else {
            System.out.println("|Límite $" + limite1 + "| No se encontraron transacciones sospechosas.");// Si no se encontró ninguna coincidencia
        }
    }
}
