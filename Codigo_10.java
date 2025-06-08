import java.util.Arrays;
import java.util.List;

public class Codigo_10 {

    // Reduccion
    public static void main(String[] args) {
        // Lista de palabras (colección de entrada)
        List<String> palabras = Arrays.asList("hola", "mundo", "java", "stream", "paralelo");

        // Se aplica reduccion sumando las longitudes de las palabras
        int totalCaracteres = palabras
            .parallelStream() // se puede distribuir en paralelo
            .map(String::length) // transformar cada palabra en su longitud
            .reduce(0, Integer::sum); // función combinatoria de suma al juntar los resultados

        System.out.println("Total de caracteres: " + totalCaracteres);
    }
}