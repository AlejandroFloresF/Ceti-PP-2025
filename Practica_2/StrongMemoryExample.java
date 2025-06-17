/*  
    Fernando Zazir Gómez Corona
    22110227
    Práctica 2
*/

public class StrongMemoryExample {
    // Variables compartidas entre hilos
    static volatile int x = 0, y = 0; // volatile garantiza visibilidad
    static volatile int a = 0, b = 0; // volatile garantiza visibilidad

    public static void main(String[] args) throws InterruptedException {
        int i = 0; // Iniciamos el contador

        while (true) {
            x = y = a = b = 0; // Reiniciar variables

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
            t1.start();
            t2.start();
            t1.join();
            t2.join();

            i++;
            if (x == 0 && y == 0) {
                // Esta condición NO debería suceder si hay visibilidad fuerte
                System.out.printf("Iteración %d: x = %d, y = %d\n", i, x, y);
                break; // debería nunca imprimirse
            }

            // Después de muchas iteraciones, si no pasa, confirmamos consistencia fuerte
            if (i % 1_000_000 == 0)
                System.out.println("Iteraciones: " + i);
        }
    }
}