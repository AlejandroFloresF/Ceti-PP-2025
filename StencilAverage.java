
/*
 * Código de Alejandro Velazquez Luna 
 * Patrón Stencil
 * 
 * Stencil es un generalización del patron de Map, en el cual una función elemental
 *  tiene acceso no solo a un elemento del conjunto de entrada sino también a un 
 * conjunto de "vecinos"
 * 
 * El objetivo de este código es transformar una matriz de enteros aplicando el promedio
 * de cada celda con sus vecinos inmediatos (arriba, abajo, izquierda, derecha).
 * 
 * Para resolverlo utilizamos lo siguiente:
 * 
 * - Doble bucle anidado: Recorre cada celda de la matriz original.
 * - Suma condicional: Se suman los valores de los vecinos válidos (dentro de los límites).
 * - Conteo de vecinos: Para calcular correctamente el promedio, se cuenta cuántos valores fueron sumados.
 * - Matriz resultado: Se guarda en una nueva matriz el promedio de cada celda con sus vecinos.
 * 
 */

public class StencilAverage {
  public static void main(String[] args) {
    // Inicializamos una matriz de enteros
    int[][] matrix = {
        { 1, 2, 3 },
        { 4, 5, 6 },
        { 7, 8, 9 }
    };
    // Calculamos la matriz promedio usando Stencil
    int[][] result = applyStencilAverage(matrix);

    // Mostramos resultado
    printMatrix(result);
  }
  // Aplica el promedio con vecinos (Stencil)
  public static int[][] applyStencilAverage(int[][] matrix) {
    //Obtenemos las dimensiones de la matriz
    int rows = matrix.length;
    int cols = matrix[0].length;
    // Creamos una matriz para almacenar el resultado
    int[][] result = new int[rows][cols];
    // Recorrer cada celda
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // Inicializamos la suma y el contador de vecinos
        int sum = matrix[i][j];
        // Empezamos con el valor de la celda actual
        int count = 1;
        // Verificamos vecinos y los sumamos si están dentro de los límites
        //Arriba
        if (i > 0) {
          sum += matrix[i - 1][j];
          count++;
        }
        // Abajo
        if (i < rows - 1) {
          sum += matrix[i + 1][j];
          count++;
        }
        // Izquierda
        if (j > 0) { 
          sum += matrix[i][j - 1];
          count++;
        }
        // Derecha
        if (j < cols - 1) { 
          sum += matrix[i][j + 1];
          count++;
        }
        // Calculamos el promedio y lo guardamos en la matriz resultado
        result[i][j] = sum / count;
      }
    }
    // Retornamos la matriz con los promedios
    return result;
  }

  // Metodo para imprimir la matriz
  public static void printMatrix(int[][] matrix) {
    for (int[] row : matrix) {
      for (int val : row) {
        System.out.print(val + "\t");
      }
      System.out.println();
    }
  }
}
