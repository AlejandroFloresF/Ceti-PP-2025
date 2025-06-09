//Reduction 

import java.util.List;      //Se importa List para manejar listas de elementos
import java.util.Arrays;    //Se importa Arrays para crear listas a partir de arrays

public class Codigo09 {
    public static void main(String[] args) { //Método principal para ejecutar el programa
        List<Integer> listaCalificaciones = Arrays.asList(90,85,70,100,95,71,80,88,99); //Se crea una lista con las calificaciones
        
        System.out.println("Lista de calificaciones:");
        listaCalificaciones.forEach(System.out::println); //Se imprime cada calificación en una línea separada
        
        // Se calcula la suma total de calificaciones usando streams paralelos
        int sumaTotal = listaCalificaciones.parallelStream() //Se crea un stream paralelo de la lista de calificaciones
                .mapToInt(Integer::intValue) //Se convierte cada elemento a un valor primitivo int
                .sum(); //Se aplica la operación de reducción sum() para obtener la suma total
        
        // Se encuentra la calificación más alta usando streams paralelos
        int calificacionMaxima = listaCalificaciones.parallelStream() //Se crea un stream paralelo de la lista de calificaciones
                .mapToInt(Integer::intValue) //Se convierte cada elemento a un valor primitivo int
                .max() //Se obtiene el valor máximo del stream
                .orElse(0); //Si no hay elementos, se devuelve 0 
        
        // Se encuentra la calificación más baja usando streams paralelos
        int calificacionMinima = listaCalificaciones.parallelStream() //Se crea un stream paralelo de la lista de calificaciones
                .mapToInt(Integer::intValue) //Se convierte cada elemento a un valor primitivo int
                .min() //Se obtiene el valor mínimo del stream
                .orElse(0); //Si no hay elementos, se devuelve 0 

        // Se calcula el promedio de calificaciones usando streams paralelos
        double promedioCalificaciones = listaCalificaciones.parallelStream() //Se crea un stream paralelo de la lista de calificaciones
                .mapToInt(Integer::intValue) //Se convierte cada elemento a un valor primitivo int
                .average() //Se calcula el promedio de los valores
                .orElse(0.0); //Si no hay elementos, se devuelve 0.0 
        
        // Se muestran los resultados obtenidos
        System.out.println("\n\nResultados:"); //Se imprime un encabezado para los resultados
        System.out.println("Suma de calificaciones: " + sumaTotal); //Se imprime la suma total de calificaciones
        System.out.println("Calificación alta: " + calificacionMaxima); //Se imprime la calificación más alta
        System.out.println("Calificación baja: " + calificacionMinima); //Se imprime la calificación más baja
        System.out.println("Promedio de calificaciones: " + promedioCalificaciones); //Se imprime el promedio de calificaciones
    }
}