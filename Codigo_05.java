// Codigo 5 - Strong consistency
// Marco Antonio Galindo Torres - 22110221

public class Codigo_05 {
    // Declaramos un valor que será accedido por ambos hilos
    private static int sharedValue = 0;
    // Monitor común para ambos hilos
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread writer = new Thread(() -> {
            synchronized (lock) {
                // Lo hacemos esperar a propósito, para que el lector se adelante
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                // Se usa "lock" como un objeto de bloqueo para sincronizar el acceso a la
                // variable
                // sharedValue, asegurando que solo un hilo a la vez pueda modificarlo o leerlo.

                sharedValue = 10; // Escritura protegida

                System.out.println("Escritor: Escritor ha terminado!\n");
            }
        });

        Thread reader = new Thread(() -> {
            System.out.println("Lector: Listo para leer!\n");
            synchronized (lock) {
                System.out.println("Lector: Valor leido: " + sharedValue + "\n"); // Lectura protegida
            }
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