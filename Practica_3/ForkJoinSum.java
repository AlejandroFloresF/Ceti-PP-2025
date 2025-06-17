/*
    Práctica 3 - ForkJoin
    Fernando Zazir Gómez Corona
    22110227
*/

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinSum {
    static class SumaTask extends RecursiveTask<Integer> { // Definimos la clase recursiva
        int[] arr;
        int start, end;

        public SumaTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        protected Integer compute() {
            if (end - start <= 2) { // caso base: pocos elementos
                int suma = 0;
                for (int i = start; i < end; i++)
                    suma += arr[i]; // suma secuencial
                return suma;
            } else {
                int mid = (start + end) / 2;
                SumaTask left = new SumaTask(arr, start, mid);
                SumaTask right = new SumaTask(arr, mid, end);
                left.fork(); // ejecutar left en paralelo
                int rightResult = right.compute(); // Resolvemos el lado derecho
                int leftResult = left.join();      // Esperamos el resultado del lado izquierdo
                return leftResult + rightResult;   // Hacemos una combinación de los resultados resultados
            }
        }
    }

    public static void main(String[] args) {
        int[] arreglo = {1, 2, 3, 4, 5, 6};
        ForkJoinPool pool = new ForkJoinPool();
        int resultado = pool.invoke(new SumaTask(arreglo, 0, arreglo.length));
        System.out.println("Fork-Join\nSuma total: " + resultado);
    }
}