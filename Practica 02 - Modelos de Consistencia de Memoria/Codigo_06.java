// Codigo 6 - Weak consistency
// Marco Antonio Galindo Torres - 22110221

public class Codigo_06 {
    // Declaramos un valor que será accedido por ambos hilos
    // No volátil, ni sincronizado
    private static int sharedValue = 0;
    // Tambien declaramos una bandera que nos indicará si ya está listo
    // No volátil, ni sincronizado
    private static boolean ready = false;

    // Versión volátil de ready
    //private static volatile boolean ready = false;

    public static void main(String[] args) {
        Thread writer = new Thread(() -> {
            // Lo hacemos esperar a propósito, para que el lector se adelante
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                // TODO: handle exception
            }

            sharedValue = 10; // Operación 1 - poner el valor en sharedValue
            ready = true; // Operación 2 - decir que ya está listo

            // Para mostrar que el escritor ya acabó
            System.out.println("Escritor: Escritor ha terminado!\n");
        });

        Thread reader = new Thread(() -> {
            System.out.println("Lector: Valor preeliminar: " + sharedValue + "\n");
            System.out.println("Lector: Listo para leer!\n");
            while (!ready) {
                // Espera activa
            }

            System.out.println("Lector: Valor leido: " + sharedValue + "\n");

        });

        // Iniciamos los hilos
        // Start: Causes this thread to begin execution;
        // the Java Virtual Machine calls the run method of this thread.
        writer.start();
        reader.start();

        // Esperamos a que los hilos mueran
        try {
            writer.join();
            reader.join();
        } catch (Exception e) {
            // TODO: handle exception.. nahh
        }
    }

}