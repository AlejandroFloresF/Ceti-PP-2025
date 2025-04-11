import java.util.*;
import java.util.concurrent.*;

// Clase principal
public class Codigo_05 {
    
    // Mapa concurrente que almacena las transacciones 
    private final Map<Integer, Integer> mapaTransacciones = new ConcurrentHashMap<>();

    // Método sincronizado que busca dos transacciones que sumen el valor del límite
    public synchronized int[] transaccionSospechosa(int[] transacciones, int limite) {
        for (int i = 0; i < transacciones.length; i++) {
            int complemento = limite - transacciones[i]; // Se calcula el valor que se necesita para llegar al límite
            if (mapaTransacciones.containsKey(complemento)) { // Si ya se ha visto el complemento antes, se retorna el par de índices
                return new int[]{mapaTransacciones.get(complemento), i};
            }
            mapaTransacciones.put(transacciones[i], i); // Se guarda la transacción actual en el mapa con su índice
        }
        return null; // Si no se encuentra ninguna pareja que cumpla con el límite, se retorna null
    }

    public static void main(String[] args) throws InterruptedException {
        Codigo_05 solver = new Codigo_05();// Se crea una instancia del detector

        int[] transacciones = {2500, 4000, 6000, 7500, 5000, 8500};   // Arreglo de transacciones 
        int limite1 = 10000;   // Primer límite sospechoso
        int limite2 = 13500;   // Segundo límite sospechoso

        ExecutorService ejecutor = Executors.newFixedThreadPool(2);        // Se crea un pool de hilos con 2 hilos

        // Primer hilo: busca transacciones que sumen 10,000
        ejecutor.submit(() -> {
            int[] resultado = solver.transaccionSospechosa(transacciones, limite1);
            resultados(resultado, limite1, transacciones);
        });

        // Segundo hilo: busca transacciones que sumen 13,500
        ejecutor.submit(() -> {
            int[] resultado = solver.transaccionSospechosa(transacciones, limite2);
            resultados(resultado, limite2, transacciones);
        });
        ejecutor.shutdown();// Se apaga el ejecutor 
    }

    // Método auxiliar para imprimir los resultados de la búsqueda
    public static void resultados(int[] resultado, int limite1, int[] transacciones) {
        if (resultado != null) {
            System.out.println("|Límite $" + limite1 + "| Sospecha entre índices: " +
                Arrays.toString(resultado) +
                " - Montos: $" + transacciones[resultado[0]] + " + $" + transacciones[resultado[1]]);
        } else {
            System.out.println("|Límite $" + limite1 + "| No se encontraron transacciones sospechosas.");
        }
    }
}