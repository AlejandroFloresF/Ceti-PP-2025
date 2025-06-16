public class StencilExample {

    public static void main(String[] args) {
        System.out.println("--- Patrón Stencil (Aplicación de Filtro Simple) ---");

        // Matriz de entrada (imagen simplificada o cuadrícula de datos)
        int[][] inputMatrix = {
            {1, 1, 1, 1},
            {1, 2, 2, 1},
            {1, 2, 2, 1},
            {1, 1, 1, 1}
        };

        int rows = inputMatrix.length;
        int cols = inputMatrix[0].length;
        int[][] outputMatrix = new int[rows][cols];

        System.out.println("Matriz de entrada:");
        printMatrix(inputMatrix);

        // Aplicar un 'stencil' simple: el nuevo valor es la suma de los vecinos directos (arriba, abajo, izquierda, derecha)
        // Esto es una simplificación; un stencil real incluiría el elemento central y aplicaría pesos.
        // Los elementos del borde se manejarían de forma diferente o se asumiría un borde de ceros.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int sumNeighbors = 0;
                // Suma el vecino de arriba
                if (i > 0) sumNeighbors += inputMatrix[i - 1][j];
                // Suma el vecino de abajo
                if (i < rows - 1) sumNeighbors += inputMatrix[i + 1][j];
                // Suma el vecino de la izquierda
                if (j > 0) sumNeighbors += inputMatrix[i][j - 1];
                // Suma el vecino de la derecha
                if (j < cols - 1) sumNeighbors += inputMatrix[i][j + 1];

                outputMatrix[i][j] = sumNeighbors;
            }
        }

        System.out.println("\nMatriz de salida (después de aplicar Stencil):");
        printMatrix(outputMatrix);
    }

    // Método auxiliar para imprimir matrices
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}