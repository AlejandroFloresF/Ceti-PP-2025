import java.util.Arrays;

public class ScanPat {
    public static void main(String[] args) {
        int[] entrada = {1, 2, 3, 4, 5};
        int[] salida = new int[entrada.length];

        // Acumulador secuencial
        salida[0] = entrada[0]; // Cada resultado de estados representará la entrada para el siguiente
        for (int i = 1; i < entrada.length; i++) {
            salida[i] = salida[i - 1] + entrada[i]; // suma acumulada
        }

        System.out.println("Resultado Scan: " + Arrays.toString(salida));
    }
}