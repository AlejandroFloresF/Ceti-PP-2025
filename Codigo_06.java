/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.codigo_06;

import java.util.HashMap;

/**
 *
 * @author jcfv3
 */
public class Codigo_06 {

    public static void main(String[] args) throws InterruptedException{
        final String clave = "clave1"; //clave
        final int valor = 23; //y valor a ser escritos
        HashMap<String, Integer> map = new HashMap(); //en un HashMap, que no es Thread-safe
        //La tarea de este hilo escritor consisten en poner el par clave-valor en el mapa
        //e imprimir lo que hizo
        Thread escritor = new Thread(() ->{ 
            String nombreHilo = Thread.currentThread().getName();
            System.out.println("El hilo " + nombreHilo + "escribio el par clave-valor:"
                        + "{" + clave + "," + valor + "}");
            map.put(clave, valor);
        });
        
        //La tarea del hilo eliminador consiste en borrar el para clave-valor
        //e imprimir lo que hizo.
        Thread eliminador = new Thread(() -> {
            String nombreHilo = Thread.currentThread().getName();
            final int val = map.remove(clave);
            System.out.println("El hilo " + nombreHilo + "elimino el par clave-valor:"
                        + "{" + clave + "," + val + "}");
        });
        
        //El orden en el fue escrito su metodo star no garantiza ese mismo orden de ejecución,
        //por lo que el hilo eliminador se puede ejecutar primero y borrar un dato que no existe.
        escritor.start();
        eliminador.start();
        
    }
}
