import java.util.Arrays;
import java.util.List;

/*
 * Código de Alejandro Velazquez Luna 
 * Patrón Reducción
 * 
 * Reducción (Reduce) es un patrón que toma una colección de elementos y la transforma 
 * en un único resultado combinando sus valores mediante una función acumulativa. 
 * 
 * El objetivo de este código es encontrar el prefijo común más largo (Longest Common Prefix)
 * entre una lista de palabras utilizando el patrón de reducción.
 * 
 * Para resolverlo utilizamos lo siguiente:
 * 
 * - stream() - Inicia una secuencia de procesamiento sobre la colección de palabras.
 * - reduce(LongestCommonPrefixReduction::commonPrefix) - Aplica una función acumuladora 
 *   que va combinando dos palabras a la vez, conservando el prefijo común entre ellas.
 * - orElse("") - Devuelve una cadena vacía si la lista de entrada está vacía.
 * 
 * Además, la función combinatoria `commonPrefix` compara dos palabras carácter por carácter
 * hasta encontrar el punto donde dejan de coincidir, devolviendo el prefijo común entre ambas.
 * 
 * Este patrón es útil para consolidar información o calcular un valor global a partir
 * de una colección, como sumas, productos, máximos, mínimos o, como en este caso, prefijos comunes.
 */

public class LongestCommonPrefixReduction {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("flower", "flow", "floght");

        // Aplicamos reducción con función de prefijo común
        String lcp = words.stream()
                          .reduce(LongestCommonPrefixReduction::commonPrefix)
                          .orElse(""); // Si la lista está vacía

        System.out.println("Longest Common Prefix: " + lcp);
    }

    // Función combinatoria que retorna el prefijo común entre dos palabras
    public static String commonPrefix(String a, String b) {
        // Encuentramos el minLength para evitar IndexOutOfBoundsException
        int minLength = Math.min(a.length(), b.length());

        // Comparamos caracter por caracter 
        for (int i = 0; i < minLength; i++) {
            // Si los caracteres no coinciden, retornamos el prefijo comun encontrado
            if (a.charAt(i) != b.charAt(i)) {
                return a.substring(0, i);
            }
        }
        // si todos los caracteres coinciden hasta el minLength, retornamos el prefijo comun.
        return a.substring(0, minLength);
    }
}
