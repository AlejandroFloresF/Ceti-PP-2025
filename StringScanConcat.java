import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/*
 * Código de Alejandro Velazquez Luna 
 * Patrón Scan
 * 
 * Scan (también conocido como prefix sum o acumulación) es un patrón que produce 
 * una colección de resultados parciales aplicando una función acumulativa a lo largo 
 * de una secuencia de entrada.
 * 
 * El objetivo de este código es generar una lista con la concatenación acumulativa 
 * de una lista de strings, es decir, ir acumulando y guardando cada resultado parcial.
 * 
 * Para resolverlo utilizamos lo siguiente:
 * 
 * - Bucle for-each: Recorre cada string de la lista original.
 * - Acumulador `runningConcat`: Va concatenando cada nuevo elemento con el valor acumulado.
 * - Lista `result`: Almacena cada resultado parcial generado por la acumulación.
 */

public class StringScanConcat {

  public static void main(String[] args) {
    // Lista de strings a procesar
    List<String> words = Arrays.asList("a", "b", "c", "d");
    // Lista para almacenar los resultados de la concatencacion acumulativa
    List<String> result = new ArrayList<>();
    // Variable para acumular la concatenacion 
    String runningConcat = "";

    // Recorremos cada palabra en la lista
    for (String word : words) {
      runningConcat += word; // se concatena con el acumulado
      result.add(runningConcat); // se guarda el acumulado (scan)
    }
    // Imprimimos el resultado final
    System.out.println("Concatenación acumulativa (Scan): " + result);
  }
}
