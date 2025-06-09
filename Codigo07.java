//Fork-Join con Fibonacci

import java.util.concurrent.ForkJoinPool; //Se importa la clase ForkJoinPool para crear un pool de hilos
import java.util.concurrent.ForkJoinTask; //Se importa la clase ForkJoinTask para crear tareas que se ejecutarán en el pool
import java.util.concurrent.RecursiveTask; //Se importa la clase RecursiveTask para crear tareas recursivas que devuelven un resultado

public class Codigo07{
    
    public static class TareaFibonacci extends RecursiveTask<Integer> { //Se crea una clase que extiende de RecursiveTask para calcular Fibonacci de forma paralela
        int numeroFib; //Número para el cual se calculará el valor de Fibonacci

        TareaFibonacci(int numeroFib) { //Constructor de la clase TareaFibonacci
            this.numeroFib = numeroFib; //Se asigna el valor recibido como parámetro al atributo de la clase
        }

        @Override
        protected Integer compute() { //Método que implementa la lógica de cálculo de Fibonacci
            if (numeroFib <= 1) return numeroFib; //Caso base: si numeroFib es 0 o 1, el resultado es el mismo valor
            
            TareaFibonacci tarea1 = new TareaFibonacci(numeroFib - 1); //Se crea una tarea para calcular Fibonacci de numeroFib-1
            tarea1.fork(); //Se envía la tarea anterior a otro hilo para que se ejecute en paralelo
            TareaFibonacci tarea2 = new TareaFibonacci(numeroFib - 2); //Se crea una tarea para calcular Fibonacci de numeroFib-2

            int resultado = tarea2.compute() + tarea1.join(); //Se calcula tarea2 en el hilo actual y se espera el resultado de tarea1 con join()
            return resultado; //Se devuelve la suma de ambos resultados, que es el valor de Fibonacci para numeroFib
        }
    }
    
    public static void main(String[] args) { //Método principal para ejecutar el programa
        ForkJoinPool poolHilos = new ForkJoinPool(); //Se crea un pool de hilos para ejecutar las tareas Fork-Join
        ForkJoinTask<Integer> tareaFib = new TareaFibonacci(20); //Se crea una tarea para calcular Fibonacci de 20
        int resultado = poolHilos.invoke(tareaFib); //Se invoca la tarea en el pool y se espera su resultado
        System.out.println("Resultados de Fibonacci con Fork-Join:");
        System.out.println("Fibonacci(20) = " + resultado); //Se imprime el resultado del cálculo
        poolHilos.close(); //Se cierra el pool de hilos cuando ya no se necesita
    }
}