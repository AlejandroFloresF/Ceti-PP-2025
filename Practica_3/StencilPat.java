/*
    Práctica 3 - Stencil
    Fernando Zazir Gómez Corona
    22110227
*/

import java.util.Arrays;

public class StencilPat {
    public static void main(String[] args) {
        int[] entrada = {7, 8, 150, 9, 5}; // Para este caso suavizaremos el valor central en promedio
        int[] salida = new int[entrada.length];

        // Los extremos no se procesan para omitir excepciones
        Arrays.parallelSetAll(salida, i -> {
            if (i == 0 || i == entrada.length - 1)
                return entrada[i];  // extremos se copian igual
            else
                return (entrada[i - 1] + entrada[i] + entrada[i + 1]) / 3; // promedio
        });

        System.out.println("Resultado Stencil: " + Arrays.toString(salida));
    }
}