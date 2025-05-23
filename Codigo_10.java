/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.codigo_10;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jcfv3
 */
public class Codigo_10 {
    
    //Genera un número aleatorio de calificaciones aleatorias del uno al 10
    private static int[] generarCalificaciones(){
        Random random = new Random(); //objeto para generar valores aleatorios
        int tam = random.nextInt(200, 501); //Habra un número aleatorio de entre 200 y 500 calificaciones
        int[] califs = new int[tam]; //Se define el arreglo de calificaciones con el tamanno aleatorio generado
        for(int f = 0; f < tam; f++){ //se recorre todo el arreglo de enteros
            califs[f] = random.nextInt(1, 11); //inicializando cada uno de sus componentes con un numero del 1 al 10
        }
        return califs; //Se regresa dicho arreglo
    }

    public static void main(String[] args) throws InterruptedException {
        int[] califs = generarCalificaciones(); //Se genera un numero aleatorio de calificaciones aleatorias del 1 al 10
        ConcurrentHashMap<String, Integer> frecuencias = new ConcurrentHashMap();
        int num_hilos = 10; //Se define el numero de hilos que se repartiran el trabajo
        ExecutorService executor = Executors.newFixedThreadPool(num_hilos); //Se define el objeto que administra la ejecucion de esos hilos
        int chunkSize =  califs.length/num_hilos; //Se determina el tamanno de los bloques entre los que sera dividido el arreglo segun el numero de hilos
        
        //Se le asigna el numero de elementos a cada hilo segun el trabajo del bloque
        for(int f = 0; f < num_hilos; f++){ 
            final int start = f * chunkSize; //Segun el hilo que se va asignando, se define desde que elemento del arreglo comenzara a recorrer
            //Si se llego al ultimo hilo, este recorrera hasta la ultima posicion del arreglo. Sino, se recorrera hasta la ultima posicion que abarce el tamanno del bloque
            final int end = (f == num_hilos - 1)? califs.length: start + chunkSize;
            executor.submit(() ->{ //Se define la tarea que hara cada hilo
                //Cada hilo ira poniendo como llave en un mapa cada calificacion del uno al 10, poniendo como valor 1,
                //e ira sumando uno por cada ocurrencia de llave que haya, es decir, por cada calificacion repetida que encuentre;
                //esto recorriendo el bloque se les fue asigando
                for(int j = start; j < end; j++){
                    //Esto de ir sumando al valor de una clave por cada repiticion de llave que se haga,
                    //es gracias al funcion merge de los objetos Map; con el ConcurrentHashMap, se evita coflicto de acceso
                    //entre hilos e incoherencia de datos en los valores de las claves del ConcurrentHashMap
                    frecuencias.merge(Integer.toString(califs[j]), 1, Integer::sum);
                }
            });
        }
        //Se le dice a todos los hilos que ya terminen
        executor.shutdown();
        //Y se espera a que cada uno termine la tarea que estaba ejecutando
        executor.awaitTermination(1, TimeUnit.SECONDS);
        
        //Se imprime en pantalla las frecuecias de las calificaciones
        System.out.println("Calificación\tFrecuencia");
        for(var calif: frecuencias.entrySet()){ //rrecoriendo cada clave y valor
            //del mapa para imprimirlos en pantalla
            System.out.println(calif.getKey() + "\t\t" + calif.getValue());
        }
        
        
    }
}
