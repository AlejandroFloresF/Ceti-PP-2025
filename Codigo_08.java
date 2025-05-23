/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.codigo_08;

import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jcfv3
 */
public class Codigo_08 {
    
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
    
    //Regresa la reduccion de un arreglo de enteros, es decir, la suma de todos sus elementos
    private static int reducir(int[] arreglo){
        int reduccion = 0; //Contendra el resultado a retornar
        for(int f = 0; f < arreglo.length; f++){ //Se recorre todo el arreglo
            reduccion +=  arreglo[f]; //acumulando la suma de todos sus elementos
        }
        return reduccion; //Se regresa la variable que tiene dicho resultao
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

    public static void main(String[] args) throws InterruptedException{
        Random random = new Random(); //Para generar valores aleatorios
        int tam = random.nextInt(5, 11); //Se tendra entre 5 y 10 arreglos
        int[][] arregloDeArreglos = new int[tam][];//Se crea el arreglo de arreglos
        int[] reducciones = new int[tam]; //Se crear el arreglo que tendra las
        //reducciones de cada arreglo de enteros
     
        
        System.out.println("Arreglos antes de ordenar:\n");
        
        //Se generan los arreglos del arreglo de arreglos
        for(int f = 0; f < tam; f++){  
            //Se crea un arreglo con numeros aleatorios para cada arreglo miembro
            arregloDeArreglos[f] = generarArreglo();
            //Se imprime el arreglo generado
            System.out.print("Arreglo" + (f+1) + ": ");
            imprimirArreglo(arregloDeArreglos[f]);
        }
        
        //Se crea el administrador de hilos y estos, que haran el trabajo de
        //ordenar cada arreglo mediante mergesort
        ExecutorService executor = Executors.newFixedThreadPool(tam);
        
        for(int f = 0; f < tam; f++){
            //Las variables finales no cambian de valor y son las unicas que puede
            //ser pasadas dentro de una funcion lambda
            final int pos = f; 
            //Establecemos la tarea que hara cada hilo
            executor.submit(() -> {
                //mandando a que cada hilo ordene el respectivo arreglo que se le pasa
                //por mergesort paralelo
                MergeSort mergeSort = new MergeSort(arregloDeArreglos[pos]);
                //creando un ForkJoinPool para cada hilo, para que este administre
                //los hilos que se van creando al ordenar paralelamente con el patron
                //Fork-Join Pool
                ForkJoinPool pool = new ForkJoinPool();
                pool.invoke(mergeSort);
            });
        }
        
        //Mandamos a terminar todos los hilos y esperamos a que cada uno complete su tarea
        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);
      
        //Para despues, poder imprimir los arreglos ya ordenados
        System.out.println("\nArreglos despues de ordenar:\n");
        
        //Se recorre el arreglo de arreglos para imprimir cada uno de ellos ya ordenado
        for(int f = 0; f < tam; f++){
            System.out.print("Arreglo" + (f+1) + ":");
            imprimirArreglo(arregloDeArreglos[f]);
        }
        
        //Esta es la segunda parte del progra, en la que se demostrara el patron Map
        //apegandose estreictamente a la definicion.
        
        //Se crea un nuevo administrador de hilos, ya que apagamos el otro
        ExecutorService executor2 = Executors.newFixedThreadPool(tam);
        
        //Se le asignara un trabajo a cada hilo
         for(int f = 0; f < tam; f++){
             //Las variables finales no cambian de valor y son las unicas que puede
            //ser pasadas dentro de una funcion lambda
            final int pos = f;
            //Se define el trabajo que cada hilo hara
            executor2.submit(() -> {
                //aplicando una reduccion (la suma de todos sus elementos), que es la funcion elemental,
                //a cada arreglo, y se guardando el resultado en un arreglo de salida; cumpliendo asi el patron Map
                reducciones[pos] = reducir(arregloDeArreglos[pos]);
            });
        }
         //Mandamos a terminar todos los hilos y esperamos a que cada uno complete su tarea
        executor2.shutdown();
        executor2.awaitTermination(2, TimeUnit.SECONDS);
        
        //Para despues imprimir el arreglo de reducciones resultante
        System.out.println("\nReduccion de cada arreglo(la suma de todos sus elementos):");
        imprimirArreglo(reducciones);
       
    }
}
