import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Código de Alejandro Velazquez Luna 
 * Patron Map
 * Map es un patrón que replica una función sobre todos los elementos de un conjunto de entrada. 
 * La función que está siendo replicada se llama función elemental, dada que la misma se aplica 
 * a una coleccion real de datos.
 * 
 * El objetivo de este codigo es transformar una lista de strings a mayúsculas utilizando el patrón Map.
 * 
 * Para resolverlo utilizamos lo siguiente:
 * 
 * stream() - Crea una secuencia de procesamientos sobre la colección.
 * map(String::toUpperCase) - Aplica la funcion elemental que convierte cada string a mayusculas.
 * collect((Collectors.toList()) - Recolecta los resultados en una nueva lista.
 */


public class UpperCaseMap {
  public static void main(String[] args) {
    // Lista original de nombres
    List<String> names = Arrays.asList("alejandro", "ceti", "luna", "computacion");

    // Aplicamos el patrón Map: convertimos cada nombre a mayúsculas
    List<String> uppercased = names.stream() // Creamos el stream
        .map(String::toUpperCase) // Función elemental: String -> String en mayúsculas
        .collect(Collectors.toList()); // Recolectamos el resultado

    // Imprimimos resultados
    System.out.println("Original: " + names);
    System.out.println("Mayúsculas: " + uppercased);
  }
}
