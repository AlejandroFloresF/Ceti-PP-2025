public class StrongConsistencyExample {
    // 'volatile' asegura que cualquier escritura en 'ready' sea inmediatamente
    // visible para otros hilos. Es una forma de consistencia fuerte (happens-before).
    private static volatile boolean ready = false;
    private static int number = 0;

    private static class WriterThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hilo Escritor: Iniciando...");
            number = 42; // Escribe el dato
            ready = true; // Establece la bandera a 'true'
            System.out.println("Hilo Escritor: Data escrita y bandera establecida.");
        }
    }

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hilo Lector: Esperando la bandera...");
            // Este bucle espera hasta que 'ready' sea true.
            // Gracias a 'volatile', una vez que 'ready' es true, se garantiza
            // que la lectura de 'number' verá el valor actualizado (42).
            while (!ready) {
                // Espera activa (busy-waiting) - en aplicaciones reales se usaría wait/notify
                Thread.yield(); // Cede el control para permitir que otros hilos se ejecuten
            }
            System.out.println("Hilo Lector: Bandera lista. Leyendo numero = " + number);
        }
    }

    public static void main(String[] args) {
        // Crea y arranca los hilos
        ReaderThread reader = new ReaderThread();
        WriterThread writer = new WriterThread();

        reader.start(); // El lector se inicia primero para esperar al escritor
        writer.start();

        try {
            reader.join(); // Espera a que el hilo lector termine
            writer.join(); // Espera a que el hilo escritor termine
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Programa principal terminado.");
    }
}