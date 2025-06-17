/*
    Práctica 3 - Map
    Fernando Zazir Gómez Corona
    22110227
*/

import java.util.Arrays;
import java.util.List;

public class MapPat {
    public static void main(String[] args) {
        // Lista de valores objetivo
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

        // Aplica la misma función a cada elemento en paralelo
        List<Integer> cuadrados = numeros.parallelStream()
                                         .map(n -> n * n) // Es importante notar que se hace un mapeo de los items
                                         .toList();

        // Imprimimos el resultado
        System.out.println("Resultado: " + cuadrados);
    }
}