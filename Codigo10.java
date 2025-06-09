//Scan

import java.util.List;      //Se importa List para manejar listas de elementos
import java.util.Arrays;    //Se importa Arrays para crear listas a partir de arrays
import java.util.ArrayList; //Se importa ArrayList para crear listas dinámicas
import java.util.stream.IntStream; //Se importa IntStream para generar secuencias de índices

public class Codigo10 {
    public static void main(String[] args) { //Método principal para ejecutar el programa
        List<Integer> listaGastos = Arrays.asList(100,200,500,250,1000,3000,541,2000,100); //Se crea una lista con los gastos 
        List<Integer> listaAcumulada = new ArrayList<>(); //Se crea una lista vacía para almacenar los valores acumulados

        int totalAcumulado = 0; //Variable para llevar el registro del total acumulado

        // Se realiza un escaneo (scan) para calcular los valores acumulados
        for(int gastoActual: listaGastos){ //Para cada gasto en la lista de gastos
            totalAcumulado += gastoActual; //Se suma el gasto actual al total acumulado
            listaAcumulada.add(totalAcumulado); //Se añade el nuevo total a la lista acumulada
        }

        // Se imprime el encabezado de la tabla con formato
        System.out.println("+-------+------------+------------+"); //Línea superior de la tabla
        System.out.println("| Índice|    Gasto   |  Acumulado |"); //Encabezados de columnas
        System.out.println("+-------+------------+------------+"); //Línea separadora
        
        // Se imprime cada fila de la tabla usando un stream de índices
        IntStream.range(0, listaGastos.size()) //Se crea un stream de índices desde 0 hasta el tamaño de la lista - 1
            .forEach(indice -> { //Para cada índice en el stream
                System.out.printf("| %-5d | %10d | %10d |%n", //Se formatea la salida como una fila de tabla
                    indice+1, //Se muestra el índice + 1 (para que empiece en 1 en lugar de 0)
                    listaGastos.get(indice), //Obtenemos el gasto correspondiente al índice
                    listaAcumulada.get(indice)); //Obtenemos el valor acumulado correspondiente al índice
            });
        
        // Se imprime el pie de la tabla y el total final
        System.out.println("+-------+------------+------------+"); //Línea inferior de la tabla
        System.out.println("Total de gastos: " + listaAcumulada.get(listaAcumulada.size() - 1)); //Mostramos el total final (último elemento de la lista acumulada)
    }
}