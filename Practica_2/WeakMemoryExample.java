/*  
    Fernando Zazir Gómez Corona
    22110227
    Práctica 2
*/

public class WeakMemoryExample {
    // Variables compartidas entre hilos
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;

        // Repetimos hasta que se observe la condición "imposible"
        while (true) {
            x = y = a = b = 0; // Reiniciamos todas las variables

            Thread t1 = new Thread(() -> {
                a = 1;              // Escribe a = 1
                x = b;              // Lee el valor de b en x
            });

            Thread t2 = new Thread(() -> {
                b = 1;              // Escribe b = 1
                y = a;              // Lee el valor de a en y
            });

            // Esto lo hacemos para catchar en qué momento ocurre un estado "imposible"
            // es decir, un error de consistencia de memoria
            t1.start(); // Disparamos los hilos
            t2.start();
            t1.join();  // Esperamos la ejecución de los hilos
            t2.join();

            i++;
            // Imprimimos si ambos hilos leen 0, lo cual NO debería ocurrir secuencialmente
            if (x == 0 && y == 0) {
                System.out.printf("Iteracion %d: x = %d, y = %d\n", i, x, y);
                break;  // condición de memoria débil detectada
            }
        }
    }
}