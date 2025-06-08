import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//Map
public class Codigo_08 {
    // Función elemental: convierte una palabra a mayúsculas
    public static String funcionElemental(String palabra) {
        return palabra.toUpperCase(); // Se convierte la palabra a mayúsculas
    }

    public static void main(String[] args) {
        // Lista de palabras
        List<String> datos = Arrays.asList("hola", "mundo", "java", "stream", "paralelo"); // Lista de palabras a procesar

        // Aplicar el patrón map en paralelo
        List<String> resultados = datos.parallelStream() // Se usa parallelStream para procesar en paralelo
                                      .map(Codigo_08::funcionElemental) // Se aplica la función elemental a cada palabra
                                      .collect(Collectors.toList()); // Se recolectan los resultados en una lista

        System.out.println("Resultados: " + resultados); // Se imprime la lista de resultados procesados
    }
}