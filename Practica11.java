/*22110223*/
/*Scan*/

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;
import java.util.concurrent.ForkJoinPool;

/**
 * Clase para definir las tareas a paralelizar
 */
class ScanTask extends RecursiveTask<int[]> {

    private final int inicio, fin;
    private final int[] ventas;

    /**
     * constructor de la clase
     * @param inicio
     * @param fin
     */
    public ScanTask(int[] ventas, int inicio, int fin) {

        this.ventas = ventas;
        this.inicio = inicio;
        this.fin = fin;
    }

    /**
     * Se define la forma de manejar la tarea en paralelo
     */
    @Override
    protected int[] compute() {

        //solo un elemento
        if (fin - inicio == 1) {
                return new int[] {0, ventas[inicio]};
        } else {

            //encuentra la mitad
            int mid = (inicio + fin) / 2;

            //se crean las subtareas
            ScanTask izquierda = new ScanTask(ventas, inicio, mid);
            ScanTask derecha = new ScanTask(ventas, mid, fin);
            
            //ejecuta y obtiene resultados
            izquierda.fork();
            int[] resultadoDer = derecha.compute();
            int[] resultadoIzq = izquierda.join();

            //acumular resultados en izquierda
            int total = resultadoIzq[resultadoIzq.length -1];

            //combina los resultados
            int[] resFinal = new int[resultadoIzq.length + resultadoDer.length - 1];
            System.arraycopy(resultadoIzq, 0, resFinal, 0, resultadoIzq.length);
            System.arraycopy(resultadoDer, 1, resFinal, resultadoIzq.length, resultadoDer.length - 1);
            for (int i = 1; i < resultadoDer.length; i++) {
                resFinal[resultadoIzq.length - 1 + i] = resultadoDer[i] + total;
            }
        
            return resFinal;
        }
    }
}

/**
 * Clase principal
 */
public class Practica11 {

    /**
     * Funcion main
     * @param args
     */
    public static void main(String[] args) {

        //valores de ventas semanales aleatorias
        Random random = new Random();
        int[] ventas = IntStream.range(0, 12)
            .map(i -> random.nextInt(5000 - 500 + 1) + 500)
            .toArray();

        //valores parciales mensuales
        int[] parcial = new int [ventas.length / 4];

        //crea el administrador de hilos para fork join
        try (ForkJoinPool pool = new ForkJoinPool()){ 

            System.out.println("Todas las ventas:" + Arrays.toString(ventas)); //todas las ventas
            ScanTask task = new ScanTask(ventas, 0, ventas.length); //crea la tarea
            int[] resultado = pool.invoke(task); //ejecuta tarea y obtiene la lista 

            //calculo de parciales por mes
            for (int j = 0; j < parcial.length; j++) {
                int bloqueInicio = j * 4;
                int bloqueFin = Math.min(ventas.length, bloqueInicio + 4);
                parcial[j] = resultado[bloqueFin] - resultado[bloqueInicio];
            }

            System.out.println("Parciales semanales: " + Arrays.toString(resultado)); //muestra la lista
            System.out.println("Resultados mensuales: " + Arrays.toString(parcial)); //muestra la lista de parcial mensual

            int sumaTotal = Arrays.stream(ventas).sum(); //suma las ventas
            System.out.println("Total: " + sumaTotal); //muestra resultado
        }
    }
}
