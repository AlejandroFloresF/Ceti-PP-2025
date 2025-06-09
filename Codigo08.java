//Map

import java.util.stream.Collectors; // Sirve para guardar los resultados en una lista o conjunto.
import java.util.List;      //Se importa List para manejar listas de elementos
import java.util.Arrays;    //Se importa Arrays para crear listas a partir de arrays

public class Codigo08 {
    public static void main(String[] args) { //Método principal para ejecutar el programa
        List<String> listaRFCs = Arrays.asList( //Se crea una lista de RFCs para procesar
            "AAA010101AAA", "", "INVÁLIDO", "XAXX010101000", "BCD920202BBB", "123ABC", null);

        // Expresión regular para validar formato de RFC
        String patronRFC = "^[A-Z&Ñ]{3,4}[0-9]{6}[A-Z0-9]{3}$"; //Patrón que debe cumplir un RFC válido
        
        System.out.println("Lista original de RFC:");
        listaRFCs.forEach(System.out::println); //Se imprime cada RFC en una línea separada

        // Se procesa en paralelo con map para validar y corregir RFCs
        List<String> rfcsCorregidos = listaRFCs.parallelStream() //Se crea un stream paralelo de la lista de RFCs
            .map(rfcActual -> { //Se aplica la función map a cada elemento del stream
                if (rfcActual == null || !rfcActual.matches(patronRFC)) { //Si el RFC es nulo o no cumple con el patrón
                    return "XAXX010101000"; //Se devuelve el RFC genérico para personas físicas
                }
                return rfcActual; //Si el RFC es válido, se mantiene sin cambios
            })
            .collect(Collectors.toList()); //Se recolectan los resultados en una nueva lista

        // Se muestra el resultado final
        System.out.println("\n\nRFCs corregidos:"); //Se imprime un mensaje indicando que se mostrarán los RFCs corregidos
        rfcsCorregidos.forEach(System.out::println); //Se imprime cada RFC corregido en una línea separada
    }
}