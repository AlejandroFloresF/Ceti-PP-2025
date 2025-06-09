/*22110223*/
/*Stencil*/

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.concurrent.RecursiveTask;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

/**
 * Clase para definir las tareas a paralelizar
 */
class DifuminarTask extends RecursiveTask<BufferedImage> {

    private BufferedImage imagen;
    private int inicioX, inicioY, ancho, alto;
    private static final int minimo = 100; //minimo para dividir
    
    /**
     * constructor de la clase
     * @param imagen
     * @param inicioX
     * @param inicioY
     * @param ancho
     * @param alto
     */
    public DifuminarTask(BufferedImage imagen, int inicioX, int inicioY, int ancho, int alto) {

        this.imagen = imagen;
        this.inicioX = inicioX;
        this.inicioY = inicioY;
        this.ancho = ancho;
        this.alto = alto;
    }

    /**
     * Se define la forma de manejar la tarea en paralelo
     */
    @Override
    protected BufferedImage compute() {

        //se verifica el minimo
        if (alto * ancho <= minimo) {

            return Difuminar();
        } else {

            int midY = alto / 2; //mitad del alto
            int midX = ancho / 2; //mitad del ancho
            int altoRestante = alto - midY; //restante de alto
            int anchoRestante = ancho - midX; //restante de alto
            //creacion de subtareas
            DifuminarTask superiorIzq = new DifuminarTask(imagen, inicioX, inicioY, midX, midY);
            DifuminarTask superiorDer = new DifuminarTask(imagen, inicioX + midX, inicioY, anchoRestante, midY);
            DifuminarTask inferiorIzq = new DifuminarTask(imagen, inicioX, inicioY + midY, midX, altoRestante);
            DifuminarTask inferiorDer = new DifuminarTask(imagen, inicioX + midX, inicioY + midY, anchoRestante, altoRestante);
            //ejecutar las subtareas
            invokeAll(superiorIzq, superiorDer, inferiorIzq, inferiorDer);
            //combinar resultados
            return combinar(superiorIzq.join(), superiorDer.join(), inferiorIzq.join(), inferiorDer.join());
        }
    }

    /*
     * Difumina la parte de la imagen
     */
    private BufferedImage Difuminar() {

        BufferedImage resultado = new BufferedImage(ancho, alto, imagen.getType());

        //for anidados para recorrer todos los pixeles
        for (int x = inicioX; x < inicioX + ancho; x++) {
            for (int y = inicioY; y < inicioY + alto; y++) {
                int sumA = 0, sumR = 0, sumG = 0, sumB = 0, count = 0;

                //itera sobre los vecinos alrededor del pixel principal
                for (int dx = -1; dx <= 5; dx++) {
                    for (int dy = -1; dy <= 5; dy++) {
                        int nx = x + dx, ny = y + dy;
                        //asegura que el vecino este dentro de la imagen 
                        if (nx >= 0 && ny >= 0 && nx < imagen.getWidth() && ny < imagen.getHeight()) {

                            int rgb = imagen.getRGB(nx, ny);
                            sumA += (rgb >> 24) & 0xFF; //transparencia
                            sumR += (rgb >> 16) & 0xFF;
                            sumG += (rgb >> 8) & 0xFF;
                            sumB += rgb & 0xFF;
                            count++;
                        }
                    }
                }

                //calcula el promedio de los colores
                int A = sumA / count;
                int R = sumR / count;
                int G = sumG / count;
                int B = sumB / count;
                int RGBf = (A << 24) | (R << 16) | (G << 8) | B;

                //asigna el nuevo color al pixel de la imagen resultado
                resultado.setRGB(x - inicioX, y - inicioY, RGBf);
            }
        }
        return resultado;
    }

    /**
     * junta los resultados de la division de tareas
     * @param imagenes
     * @return
     */
    private BufferedImage combinar(BufferedImage superiorIzq, BufferedImage superiorDer, BufferedImage inferiorIzq, BufferedImage inferiorDer) {

        int midX = ancho / 2;
        int midY = alto / 2;

        //objeto de la nueva imagen creada
        BufferedImage resultado = new BufferedImage(ancho, alto, imagen.getType());
        
        //agrega a la imagen final las partes del resultado
        Graphics2D g2d = resultado.createGraphics();
        try {
            g2d.drawImage(superiorIzq, 0, 0, null);
            g2d.drawImage(superiorDer, midX, 0, null);
            g2d.drawImage(inferiorIzq, 0, midY, null);
            g2d.drawImage(inferiorDer, midX, midY, null);
        } finally {
            g2d.dispose();
        }
        return resultado;
    }
}

/**
 * Clase principal
 */
public class Practica9 {

    /**
     * Funcion main
     * @param args
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //obtiene la imagen
        System.out.println("Mueve una imagen a la carpeta de este programa");
        System.out.print("Ingresa el nombre del archivo de la imagen: ");
        String nombre = scanner.nextLine();

        //buscar la posicion del ultimo punto
        int indicePunto = nombre.lastIndexOf('.');

        //verificar que existe un punto y que no este al inicio ni final del string
        if (indicePunto > 0 && indicePunto < nombre.length() - 1) {

            //separa nombre y extension
            String nombreArchivo = nombre.substring(0, indicePunto);
            String extension = nombre.substring(indicePunto);

            File archivo = new File(nombre);
            try{

                BufferedImage imagen = ImageIO.read(archivo);

                //crea el administrador de hilos para fork join
                try (ForkJoinPool pool = new ForkJoinPool()){ 

                    //crea la tarea
                    DifuminarTask task = new DifuminarTask(imagen, 0, 0, imagen.getWidth(), imagen.getHeight());
                    BufferedImage imagenDifuminada = pool.invoke(task); //ejecuta tarea y obtiene la nueva imagen
                    //guarda la imagen difuminada
                    File salida = new File(nombreArchivo + "_difuminada" + extension);
                    boolean resultado = ImageIO.write(imagenDifuminada, extension.substring(1), salida);
                    if (!resultado) {
                        System.out.println("No se encontró un escritor para el formato:" + extension);
                    } else {
                        System.out.println("Imagen difuminada guardada en " + salida.getAbsolutePath());
                    }
                } 
                catch (Exception e) {

                System.out.println("Imagen no generada ");
                }
            }
            catch (Exception e) {

                System.out.println("Imagen no encontrada ");
            }
        } else {

            //En caso de que no se encuentre un punto o el formato no sea el esperado
            System.out.println("El archivo no tiene extension o el formato es incorrecto.");
        }
        scanner.close();
    }
}
