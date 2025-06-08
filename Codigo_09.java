import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Stencil
public class Codigo_09 {

    // Función elemental tipo stencil:
    // Opera sobre una palabra y sus vecinos (anterior y siguiente)
    public static String funcionStencil(String anterior, String actual, String siguiente) {
        // Concatena los vecinos con la palabra actual, si existen
        return (anterior == null ? "" : anterior + "-") 
             + actual 
             + (siguiente == null ? "" : "-" + siguiente);
    }

    public static void main(String[] args) {
        List<String> datos = Arrays.asList("hola", "mundo", "java", "stream", "paralelo"); // Lista de palabras a procesar

        // Creamos una lista de resultados aplicando el patrón stencil
        List<String> resultados = IntStream.range(0, datos.size())
            .parallel() // procesamiento paralelo
            .mapToObj(i -> {
                String anterior = (i > 0) ? datos.get(i - 1) : null;                    // Obtener palabra anterior si existe (sino, null)
                String actual = datos.get(i);                                           // Palabra actual
                String siguiente = (i < datos.size() - 1) ? datos.get(i + 1) : null;    // Obtener palabra siguiente si existe (sino, null)

                return funcionStencil(anterior, actual, siguiente);
            })
            .collect(Collectors.toList());

        System.out.println("Resultados Stencil: " + resultados);
    }
}