import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapExample {

    public static void main(String[] args) {
        System.out.println("--- Patrón Map (Transformación Paralela) ---");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("Lista original: " + numbers);

        // Aplicación del patrón Map usando Streams paralelos de Java
        // La operación de "map" aplica una función a cada elemento de forma independiente.
        // .parallelStream() permite que esta operación se realice en paralelo.
        List<Integer> squaredNumbers = numbers.parallelStream()
                                            .map(n -> n * n) // La función lambda (n -> n * n) es la operación 'map'
                                            .collect(Collectors.toList());

        System.out.println("Lista de números al cuadrado (Map): " + squaredNumbers);

        // Otro ejemplo: convertir Strings a su longitud en paralelo
        List<String> words = Arrays.asList("hola", "mundo", "java", "paralelo", "ejemplo");
        System.out.println("\nLista de palabras: " + words);

        List<Integer> wordLengths = words.parallelStream()
                                        .map(s -> s.length()) // La operación 'map' obtiene la longitud de cada string
                                        .collect(Collectors.toList());

        System.out.println("Longitudes de las palabras (Map): " + wordLengths);
    }
}