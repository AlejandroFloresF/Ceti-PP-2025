/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.codigo_07;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author jcfv3
 */
public class Codigo_07 {
    
    static class MergeSort extends RecursiveAction{
        private final int[] mergeArray; //Arreglo a ordenar
        private int[] izq; //Separacion izquierda
        private int[] der; //Separacion derecha 
        
        //Constructor de la clase que recibe el arreglo a ordenar
        public MergeSort(int[] mergeArray){ 
            this.mergeArray = mergeArray;
        }
        
        //Copia los valores de la mitad izquierda del arreglo padre en otro
        //arreglo
        private void copiaIzq(){
            izq = new int[mergeArray.length/2];
            System.arraycopy(mergeArray, 0, izq, 0, izq.length);

        }
        
        //Copia los valores de la mitad derecha del arreglo padre en otro
        //arreglo
        private void copiaDer(){
            int med; //tamaño del subarreglo derecho
            if(mergeArray.length % 2 == 0){ //Si el arreglo padre es divisible entre dos
                med = mergeArray.length/2; //El tamaño del subarreglo derecho es la mitad del padre
               
            }
            else{ //Si tamaño del arreglo padre es impar
                med = mergeArray.length/2 + 1; //El tamaño del subarreglo es la mitad del padre mas uno
            }
            
            der = new int[med]; //creamos el arreglo
            //Copiamos los valores de la mitad derecha del padre
            System.arraycopy(mergeArray, mergeArray.length/2, der, 0, der.length);
        }
         
        //Ejecuta el algoritmo mergesort 
        private void ordenar(){
            int i = 0; //indice de posiciones del hijo izquierdo
            int j = 0; //indice de posiciones del hijo derecho
            int f = 0; //indice de posiciones del padre

            //Mientras no se haya recorrido por completo uno de los arreglos hijos.
            while( i < izq.length && j < der.length){
                if(izq[i] < der[j]){ //Se comparan los elementos de los arreglos hijos
                    mergeArray[f] = izq[i]; //Si el de la izquierda es menor, se copia a la posicion del padre
                    i++; //y se avanza a comparar el siguiente elemento del hijo izquierdo
                }
                else{ //Si el de la dereecha es menor
                    mergeArray[f] = der[j]; //Se copia a la posicion del padre
                    j++; //Se avanza a comparar el siguiente elemento del hijo derecho
                }
                f++; //Algun elemento de alguno de los hijos se habra copiado a la actual
                //posicion del padre, por lo que avanzamos un lugar en este arreglo para
                //repetir el procesos anterior
            }
            
            //al finalizar el bucle anterior, uno de los dos arreglos hijos habra
            //terminado de recorrerse primero. Debido a la recursividad del metodo,
            //los dos arreglos hijos ya estaban ordenados desde una iteracion anterior, o
            //solo tienen un elemento, por lo que de antemano ya se sabe que estos arreglos
            //estan ordenados, permitiendo copiar en secuencia los valores del arreglo hijo que
            //haga falta por recorrer

            //Entonces se comprueba si quedan elementos del hijo izquierdo
            //por recorrer
            while(i < izq.length){
                mergeArray[f] = izq[i]; //y los copiamos al arreglo padrea en secuencia
                i++; //y avanzamos sus respectivas posiciones
                f++;
            }
            
            //Luego pasamos a comprobar si quedan elementos del hijo izquierdo
            //por recorrer
            while(j < der.length){
                mergeArray[f] = der[j]; //y los copiamos al arreglo padrea en secuencia
                j++; //y avanzamos sus respectivas posiciones
                f++;
            }
        }

        //En este metodo se va diviendo la tarea del merge sort entre diferentes hilos
        @Override
        protected void compute() {
            if(mergeArray.length > 1){
                copiaIzq(); //se copia la mitad izquierda al subarreglo izquierdo
                copiaDer(); //se copia la mitad derecha al subarreglo derecha
                //Se crea un objeto de esta clase cuyo arreglo padre sera el hijo izquierdo
                //del objeto padre de la misma clase que lo esta creando
                MergeSort izqTask = new MergeSort(izq); 
                //Se crea un objeto de esta clase cuyo arreglo padre sera el hijo derecho
                //del objeto padre de la misma clase que lo esta creando
                MergeSort derTask = new MergeSort(der);
                izqTask.fork(); //El objeto hijo izquierdo llama a su propia funcion compute en otro hilo
                derTask.compute(); //El objeto hijo derecho llama su funcion compute en este mismo hilo
                izqTask.join(); //Se espera a que el objeto hijo izquierdo haya terminado su trabajo
                //que como recursivamente también creo a otros objetos hijos, tendra que esperar a que
                //esperar a su vez que todos ellos terminen su trabajo.
                
                ordenar(); //Cuando el tanto el objeto hijo derecho como el izquierdo
                //hayan terminado su trabajo, se orderan los valores del arreglo hijo
                //izquierdo y derecho, que son los arreglos padres en los respectivos
                //objetos hijos y que fueron ordenados en la recursion anterior, en el arreglo padre.
                
                
                //Al final gracia a que cada hilo fue ordenando su respectivo par
                //de arreglos hijos en el arreglo padre recursivamente, se obtiene
                //todo el arreglo original ordenado. Habiendo de esta forma aplicando
                //El metodo mergesort paralelamente mediante el patron fork-join pool.
                       
            }
        }
        
    }
    
    //Genera un arreglo de tamaño y valores aleatorios
    private static int[] generarArreglo(){
        Random random = new Random(); //Se crea un objeto de esta claae
        int tam = random.nextInt(5, 16); //El tamaño del arreglo a ordenar sera de entre 5 y 15
        int[] arreglo = new int[tam]; //se crea el arreglo
        for(int f = 0; f < tam; f++){ //se llenan sus valores
            arreglo[f] = random.nextInt(30) + 1; //con numeros aleatorios del 1 al 30
        }
        return arreglo; //se retorna el arreglo creado
    }
    
    //Se imprimen los elementos del arreglo pasado como parametro
    private static void imprimirArreglo(int[] arreglo){
        for(int f = 0; f < arreglo.length; f++){ //Se recorre todo el arreglo
            if(f < arreglo.length - 1){ //Si todavia no es el elemento final
                System.out.print(arreglo[f] + "-"); //Se imprimen seguidos de un guion
            }
            else{ //El elemento final no se imprime seguido de un guion
                System.out.print(arreglo[f]);
            }
        }
        System.out.println(); //Se imprime un salto de linea
    }
 
  
    public static void main(String[] args) {
        int[] arreglo = generarArreglo(); //Se genera el arreglo de tamaño y valores aleatorios
        System.out.println("Arreglo antes de ordenar"); //se imprimen los valores del arreglo
        imprimirArreglo(arreglo); //antes de ordenar
        MergeSort mergeSort = new MergeSort(arreglo); //se crea el primer objeto que ira subdividiendo el arreglo original
        //Se crea este objeto que es el que administra los hilos que se van creando en las subdivisiones de tareas
        ForkJoinPool pool = new ForkJoinPool(); 
        pool.invoke(mergeSort); //se llama al metodo del primer objeto creado, comenzando asi todo el proceso fork-join
        System.out.println("Arreglo despues de ordenar"); //Se imprimen los valores
        imprimirArreglo(arreglo); //del arreglo ya ordenado
    }
}
