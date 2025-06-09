/*22110223*/
/*Map*/

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

/**
 * Clase para definir las tareas a paralelizar
 */
class MayusculasTask extends RecursiveTask<List<String>> {

    private final List<String> lista;
    private final int inicio, fin;
    private static final int minimo = 1; //minimo para dividir

    /**
     * constructor de la clase
     * @param lista
     * @param inicio
     * @param fin
     */
    public MayusculasTask(List<String> lista, int inicio, int fin) {
        this.lista = lista;
        this.inicio = inicio;
        this.fin = fin;
    }

    /**
     * Se define la forma de manejar la tarea en paralelo
     */
    @Override
    protected List<String> compute() {

        //se verifica el minimo
        if (fin - inicio <= minimo) {

            //se crea una lista para resultados
            List<String> resultado = new ArrayList<>();
            //convierte a mayusculas los elementos
            for (int i = inicio; i < fin; i++) {

                resultado.add(lista.get(i).toUpperCase()); 
            }
            return resultado;
        } else {

            int mid = (inicio + fin) / 2; //mitad del arreglo
            //creacion de subtareas
            MayusculasTask izquierda = new MayusculasTask(lista, inicio, mid); 
            MayusculasTask derecha = new MayusculasTask(lista, mid, fin);
            izquierda.fork(); //ejecuta la izquierda
            List<String> resultadoDer = derecha.compute(); //ejecuta la derecha
            List<String> resultadoIzq = izquierda.join(); //espera que termine la izquierda
            resultadoIzq.addAll(resultadoDer); //combina la lista
            return resultadoIzq; //regresa el resultado
        }
    }
}

/**
 * Clase principal
 */
public class Practica8 {

    /**
     * Funcion main
     * @param args
     */
    public static void main(String[] args) {

        //lista de palabras de tipo string
        List<String> palabras = List.of("otro", "saludos", "cordiales", "texto",  "prueba", "map", "elemento", "adicional");

        //crea el administrador de hilos para fork join
        try (ForkJoinPool pool = new ForkJoinPool()){ 

            //crea la tarea
            MayusculasTask task = new MayusculasTask(palabras, 0, palabras.size());
            List<String> resultado = pool.invoke(task); //ejecuta tarea y obtiene la nueva lista
            //original y resultado
            System.out.println("Original: " + palabras);
            System.out.println("Mayusculas: " + resultado);
        }
    }
}
