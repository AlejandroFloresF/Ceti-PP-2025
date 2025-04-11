/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.codigo_05;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jcfv3
 */
public class Codigo_05 {
    

    public static void main(String[] args) {
        //Este tipo de HashMap es Thread-safe, lo que significa que sus operaciones
        //de escritura estan sincronizadas, evitando que otros hilos lean o modifiquen
        //los datos que otro hilo ya esta modificando.
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap();
        //Se definen 10 hilos que ejecutaran las tareas definidas adelante
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        //Ciclo for que va iniciando las tareas de los hilos escritores
        for(int i = 1; i <=5; i++){
            final int id = i; //clave
            final int valor = i*2; // y valor a poner en el mapa
            //La tarea del hilo escritor consiste en poner un par clave-valor
            //(segun el indice prporcionado por el for) en el mapa e imprimir lo que hizo.
            executor.submit(() -> {
                String nombreHilo = Thread.currentThread().getName();
                map.put(id, valor);
                 System.out.println("El hilo " + nombreHilo + "escribio el par clave-valor:"
                        + "{" + id + "," + valor + "}");
            });
        }
         //Ciclo for que va iniciando las tareas de los hilos lectores
        for(int i = 1; i <=5; i++){
            final int id = i;
            //La tarea del hilo lector consiste en leer los pares clave-valor
            //(según el inidice proporcionado por eel for), imprimiendo su nombre
            //y el par clave-valor que leyo
            executor.submit(() -> {
                String nombreHilo = Thread.currentThread().getName();
                Integer valor = map.get(id);
                System.out.println("El hilo " + nombreHilo + "leyo el par clave-valor:"
                        + "{" + id + "," + valor + "}");
            });
            
        }
        executor.shutdown();
        
    }
}


