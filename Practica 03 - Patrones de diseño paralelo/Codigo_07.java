// Codigo 07 - Fork-join -> Fibonacci
// Marco Antonio Galindo Torres - 22110221

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import javax.swing.filechooser.FileSystemView;

public class Codigo_07 extends RecursiveTask<File> {
    // Atributos
    private File directory;
    private String fileName;

    // Constructor
    public Codigo_07(File directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    @Override
    protected File compute() {
        // Obtenemos todos los archivos del directorio actual
        File[] files = directory.listFiles();
        // Si ya no hay, retornamos nulo
        if (files == null) {
            return null;
        }

        // Generamos un arreglo en el que insertamos todas las subtareas
        // para luego hacerles join
        List<Codigo_07> subTasks = new ArrayList<Codigo_07>();

        // Hacemos un foreach file en files
        for (File file : files) {
            // Si el file es un subdirectorio: generamos una nueva tarea
            if (file.isDirectory()) {
                Codigo_07 task = new Codigo_07(file, fileName);
                // Mandamos la tarea al pool de hilos para que se ejecute en paralelo
                task.fork();
                // La agregamos a la lista de tareas para hacer join después
                subTasks.add(task);
            } else {
                // Caso contrario, es un archivo: debemos ver si es el archivo que buscamos
                if (file.getName().equalsIgnoreCase(fileName)) {
                    // Si efectivamente coincide, retornamos el archivo
                    return file;
                }
            }
        }

        // Ya que terminamos de computar para este directorio actual,
        // debemos esperar a que terminen todas las subtareas de los subdirectorios
        for (Codigo_07 task : subTasks) {
            File result = task.join();
            // En el momento en el que encuentra una coincidencia, ya no
            // necesita esperar más, y procede a retornar
            if (result != null) {
                return result;
            }
        }

        // En caso de que ya haya esperado por todas las subtareas y no lo haya
        // encontrado
        // retornamos null
        return null;
    }

    public static void main(String[] args) {
        // Carpeta raíz de donde empezar a buscar: C:\Users\marqu\Documents\CETI -
        // Ingenieria
        File root = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()
                + File.separator + "CETI - Ingenieria");
        System.out.println("Inicio: " + root.getAbsolutePath());
        // Nombre del archivo a buscar
        String targetFile = "Pregunta4.c";

        // Creamos la piscina de hilos
        ForkJoinPool pool = new ForkJoinPool();
        // Creamos la tarea inicial
        Codigo_07 task = new Codigo_07(root, targetFile);
        // El resultado se obtiene al invocar la tarea con pool.invoke
        File result = pool.invoke(task);

        // Imprimimos los resultados
        if (result != null) {
            System.out.println("Archivo encontrado en: " + result.getAbsolutePath());
        } else {
            System.out.println("Archivo no encontrado.");
        }

    }
}
