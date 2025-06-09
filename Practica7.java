/*22110223*/
/*Fork join*/

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

/**
 * Clase para definir las tareas a paralelizar
 */
class PrimosTask extends RecursiveTask<List<Integer>> {

    private final int inicio, fin;
    private static final int maximo = 10;

    /**
     * constructor de la clase
     * @param inicio
     * @param fin
     */
    public PrimosTask(int inicio, int fin) {

        this.inicio = inicio;
        this.fin = fin;
    }

    /**
     * Verifica si el numero es primo
     * @param num numero
     * @return resultado booleano
     */
    private boolean esPrimo(int num) {

        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {

            if (num % i == 0) return false;
        }
        return true;
    }

    /**
     * Se define la forma de manejar la tarea en paralelo
     */
    @Override
    protected List<Integer> compute() {

        List<Integer> primos = new ArrayList<>();
        //comprueba sobre el rango
        if (fin - inicio <= maximo) {

            //comprueba todos los numeros para asignar a la lista
            for (int i = inicio; i <= fin; i++) {

                if (esPrimo(i)) primos.add(i);
            }
        } else {

            int mid = (inicio + fin) / 2; //mitad de los numeros
            //crea subtareas las que se divide
            PrimosTask izquierda = new PrimosTask(inicio, mid); 
            PrimosTask derecha = new PrimosTask(mid + 1, fin);
            izquierda.fork(); //ejecuta la izquierda
            primos.addAll(derecha.compute()); //ejecuta la derecha
            primos.addAll(izquierda.join()); 
        }
        return primos;
    }
}

/**
 * Clase principal
 */
public class Practica7 {

    /**
     * Funcion main
     * @param args
     */
    public static void main(String[] args) {

        //crea el administrador de hilos para fork join
        try (ForkJoinPool pool = new ForkJoinPool()){ 

            int inicio = 1, fin= 100; //busca los primos entre 1 y 100
            PrimosTask task = new PrimosTask(inicio, fin); //crea la tarea
            List<Integer> primos = pool.invoke(task); //ejecuta tarea y obtiene la lista 
            System.out.println("Números primos: " + primos); //muestra la lista
        }
    }
}