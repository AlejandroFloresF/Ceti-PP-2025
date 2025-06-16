public class WeakConsistencyExample {
    // En este ejemplo, 'ready' y 'number' NO son 'volatile'.
    // Esto significa que los cambios hechos por un hilo NO están garantizados
    // de ser visibles inmediatamente para otros hilos en el orden esperado.
    private static boolean ready = false; // Sin volatile
    private static int number = 0;       // Sin volatile

    private static class WriterThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hilo Escritor: Iniciando...");
            number = 42; // Escribe el dato
            // Añadimos un pequeño retraso para aumentar la probabilidad de inconsistencia
            try {
                Thread.sleep(10); // 10 milisegundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            ready = true; // Establece la bandera
            System.out.println("Hilo Escritor: Data escrita y bandera establecida.");
        }
    }

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hilo Lector: Esperando la bandera...");
            // Este bucle espera hasta que 'ready' sea true.
            // Sin 'volatile', NO hay garantía de que, cuando 'ready' se haga true,
            // 'number' ya haya sido visto como 42. Podría seguir siendo 0 en el caché del lector.
            while (!ready) {
                // Espera activa. En un sistema débilmente consistente, 'ready' puede hacerse
                // true sin que 'number' se sincronice aún.
                Thread.yield();
            }
            System.out.println("Hilo Lector: Bandera lista. Leyendo numero = " + number);
        }
    }

    public static void main(String[] args) {
        ReaderThread reader = new ReaderThread();
        WriterThread writer = new WriterThread();

        reader.start(); // El lector se inicia primero
        writer.start();

        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Programa principal terminado.");
        System.out.println("Nota: Debido a la Consistencia Débil, el Hilo Lector PUEDE haber leído '0' para 'number' si no hay sincronización.");
        System.out.println("Ejecute varias veces para observar la inconsistencia (puede variar según el sistema).");
    }
}
