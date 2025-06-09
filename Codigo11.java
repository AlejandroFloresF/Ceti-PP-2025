import java.util.Arrays; // Se importa la clase Arrays para imprimir matrices
import java.util.stream.IntStream; // Se importa para usar streams paralelos

public class Codigo11{
    public static void main(String[] args) {
        // Matriz de temperaturas (5x5)
        int[][] matrizTemperaturas = {
            {10, 10, 10, 10, 10},   // zona fría
            {10, 80, 90, 80, 10},   // borde caliente
            {10, 90,100, 90, 10},   // centro muy caliente
            {10, 80, 90, 80, 10},   // borde caliente
            {10, 10, 10, 10, 10}    // zona fría
        };

        // Se imprime la matriz de temperaturas original
        System.out.println("Matriz de temperaturas:");
        for (int i = 0; i < 5; i++) {
            System.out.println(Arrays.toString(matrizTemperaturas[i]));
        }

        // Matriz para almacenar las temperaturas suavizadas
        int[][] matrizResultado = new int[5][5];

        // Se aplica el patrón stencil de forma paralela usando streams
        IntStream.range(1, 4) // Se recorren las filas internas (1 a 3)
                 .parallel() // Se ejecuta en paralelo para aprovechar varios núcleos
                 .forEach(i -> {
                     for (int j = 1; j < 4; j++) { // Se recorren las columnas internas (1 a 3)
                         // Se calcula el promedio de la celda actual y sus vecinos (arriba, abajo, izquierda, derecha)
                         matrizResultado[i][j] = (
                             matrizTemperaturas[i][j] +       // Celda actual
                             matrizTemperaturas[i - 1][j] +   // Vecino superior
                             matrizTemperaturas[i + 1][j] +   // Vecino inferior
                             matrizTemperaturas[i][j - 1] +   // Vecino izquierdo
                             matrizTemperaturas[i][j + 1]     // Vecino derecho
                         ) / 5; // Se divide entre 5 para obtener el promedio
                     }
                 });

        // Se imprime la matriz resultante con las temperaturas suavizadas
        System.out.println("\nMatriz de temperaturas suavizadas:");
        for (int i = 0; i < 5; i++) {
            System.out.println(Arrays.toString(matrizResultado[i]));
        }
    }
}