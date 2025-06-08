import java.util.Arrays;


public class Codigo_11 {

    // Scan
    public static void main(String[] args) { 
        int[] datos = {1, 2, 3, 4, 5}; // Colección de entrada
        int[] resultado = new int[datos.length]; // Colección de salida

        int acumulado = 1;
        for (int i = 0; i < datos.length; i++) { // Iterar sobre la colección de entrada
            acumulado *= datos[i];               // Multiplicamos el acumulado por el valor actual
            resultado[i] = acumulado;            // Guardamos el valor acumulado en el resultado
        }

        System.out.println("Entrada:  " + Arrays.toString(datos));         // Mostramos la entrada original
        System.out.println("Scan producto: " + Arrays.toString(resultado));// Mostramos el resultado del scan (producto acumulado)
    }
}