/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.codigo_11;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author jcfv3
 */
public class Codigo_11 {
    //Esta clase interna implementa los metodos para hacer la primer parte del
    //algoritmo de Belloch, la barrida hacia arriba
    static class UpSweep extends RecursiveAction{
        
        int[] arreglo; //Arreglo a recorrer
        int start; //Posicion inicial del arreglo
        int end; //Tamanno del arreglo o subarreglo que esta dividiendo
        //que actuara como posición final
        
        //Constructor de la clase que recibe los atributos anteriores
        UpSweep(int[] arreglo, int start, int end){
            this.arreglo = arreglo;
            this.start = start;
            this.end = end;
        }
        
        //Función que cada hilo creado por el mecanismo Fork-Join ejecutara
        //junto con el principal
        @Override
        protected void compute() {
            int size = end - start; //Se calcula el tamanno del arreglo o subarreglo
            if(size <= 1){ //Si el tamaño es menor o igual a uno, ya no se puede dividir más
                return; //Y se retorna de la función
            }
            
            int mid = start + size / 2; //Se calcula la posisición media del arreglo
            invokeAll(
                    //Aplicamos el procedimiento anterior al subarreglo izquierdo
                    new UpSweep(arreglo, start, mid),
                    //Aplicamos el procemiento anterior al subarreglo derecho
                    new UpSweep(arreglo, mid, end)
            );
            //Cuando ya no se pueda subdividir más los arreglos o se este regresando
            //de una función que fue ejecutada por un hilo hijo.
            //Se le suma al último elemento del subarreglo acutal, el el elemento
            //de la posicion media.
            //De esta forma, gracias a la recursividad, estaremos recorriendo las subdivisiones
            //hacia arriba, sumando el elemento medio al final, recursivamente por cada subarreglo
            //en la pila de llamadas, cumpliendo así con la barrida hacía arriba del algoritmo
            // de Belloch
            arreglo[end - 1] += arreglo[mid - 1];
        }
        
    }
    
     //Esta clase interna implementa el metodo para hacer la segunda parte del
    //algoritmo de Belloch, la barrida hacia abajo
    static class DownSweep extends RecursiveAction{
        int[] arreglo;//Arreglo a recorrer
        int start; //Posicion inicial del arreglo
        int end; //Tamanno del arreglo o subarreglo que esta dividiendo
        //que actuara como posición final
        
        //Constructor de la clase que recibe los ataributos antes descritos
        DownSweep(int[] arreglo, int start, int end){
            this.arreglo = arreglo;
            this.start = start;
            this.end = end;
        }
        //Función que cada hilo creado por el mecanismo Fork-Join ejecutara
        //junto con el principal
        @Override
        protected void compute() {
            int size = end - start; //Se calcula el tamanno del arreglo o subarreglo
            if(size <= 1){ //Si el tamanno es menor o igual a uno, ya no se sigue subdiviendo
                return; //Se retorna de la funcion
            }
            int mid = start + size / 2; //Se calcual la posición media del arreglo
            int t = arreglo[mid - 1]; //Se guarda en una variable auxiliar, el valor de la posicion media
            arreglo[mid - 1] = arreglo[end - 1]; //El valor de la posicion final es copiado a la posicion media
            arreglo[end - 1] +=  t; //Se le suma el valor anterior del elemento medio al ultimo
            invokeAll( //Se divide el arreglo en izquierdo y derecho a partir de elemento medio
                    new DownSweep(arreglo, start, mid), //Creando otro hilo que repita lo mismo por el lado izquierdo
                    new DownSweep(arreglo, mid, end)
                    );            
        }
        
        //Aqui no hacemos nada despues de que un hilo hijo haya terminado su trabajo
        //todas las operaciones necesarias ya fueron hechas mientras se iba subdividiendo el arraglo,
        //siendo esto la barrida hacia abajo del algoritmo de Belloch
        
    }
    
    //Funcion que devuelve un arreglo con valores aleatorios
    public static int[] generarVentasAleatorias(){
        int[] arreglo = new int[8]; //Los arreglos que trabajan con el algoritmo de Belloc tienen que ser una potencia de dos
        Random random = new Random(); //Variable para generar valores aleatorios
        for(int f = 0; f < 8; f++){ //Rellanamos el arreglo con valores aleatorios
            arreglo[f] = random.nextInt(1000);
        }
        return arreglo; //Retornamos el arreglo.
    }
    
    //Funcion para imprimir un arreglo
    public static void imprimirArreglo(int[] arreglo){
        for(int f = 0; f < arreglo.length; f++){ //Se recorren todos los elementos del arreglo
            System.out.print("$" + arreglo[f]); //Se imprime cada elemento
            if(f < arreglo.length - 1){ //Mientras no sea el ultimo elemento
                System.out.print("-");  //Se imprime este caracter
            }
        }
    }
    
    //Funcion principal
    public static void main(String[] args) {
        //Obtenemos un arreglo con ventas aleatorias de 8 dias
        int[] ventasSemana = generarVentasAleatorias();
        //Este arreglo tendra las ventas acumuladas al iniciar cada dia de los 8
        int[] ventasAcumuladas = Arrays.copyOf(ventasSemana, ventasSemana.length);
        
        //Para obtener dichos valores de las ventaas acumuladas, hacemos una suma prefijada aplicando el patron 
        //paralelo scann con el algoritmo de Belloch y el mecanismo de Fork-Join Pool, haciendo aqui la primera parte que es la de la barrida hacia arriba
        ForkJoinPool.commonPool().invoke(new UpSweep(ventasAcumuladas, 0, ventasAcumuladas.length));
        //Ponemos el ultimo elemento del arreglo como cero para hacer un scann exclusivo y asi tener las ventas acumuladas al iniciar el dia.
        //De lo contrario, tendriamos un scann inclusivo y se obtendria las ventas acumuladas al final de cada dia
        ventasAcumuladas[ventasAcumuladas.length - 1] = 0;
        //Hacemos la segunda parte del algoritmo de Belloch, la barrida hacia abajo, para asi tener finalmente los resultados de las ventas acumuladas
        //al iniciar cada dia; habiendo realizado una suma prefijada.
        ForkJoinPool.commonPool().invoke(new DownSweep(ventasAcumuladas, 0, ventasAcumuladas.length));
        
        //Imprimimos el arreglo inicial de las ventas de cada dia
        System.out.println("Venta de cada día:");
        imprimirArreglo(ventasSemana);
        
        //Imprimimos el arreglo de las ventas acumuladas de los dias previos al iniciar cada dia.
        System.out.println("\nVentas acumuladas al iniciar cada día");
        imprimirArreglo(ventasAcumuladas);
        
        
    }
}
