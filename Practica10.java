/*22110223*/
/*Reduccion*/

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
class FiltroTask extends RecursiveTask<BufferedImage> {

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
    public FiltroTask(BufferedImage imagen, int inicioX, int inicioY, int ancho, int alto) {

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

            return Filtro();
        } else {

            int midY = alto / 2; //mitad del alto
            int midX = ancho / 2; //mitad del ancho
            int altoRestante = alto - midY; //restante de alto
            int anchoRestante = ancho - midX; //restante de alto
            //creacion de subtareas
            FiltroTask superiorIzq = new FiltroTask(imagen, inicioX, inicioY, midX, midY);
            FiltroTask superiorDer = new FiltroTask(imagen, inicioX + midX, inicioY, anchoRestante, midY);
            FiltroTask inferiorIzq = new FiltroTask(imagen, inicioX, inicioY + midY, midX, altoRestante);
            FiltroTask inferiorDer = new FiltroTask(imagen, inicioX + midX, inicioY + midY, anchoRestante, altoRestante);
            //ejecutar las subtareas
            invokeAll(superiorIzq, superiorDer, inferiorIzq, inferiorDer);
            //combinar resultados
            return combinar(superiorIzq.join(), superiorDer.join(), inferiorIzq.join(), inferiorDer.join());
        }
    }

    /*
     * Aplica el filtro a una parte de la imagen
     */
    private BufferedImage Filtro() {

        BufferedImage resultado = new BufferedImage(ancho, alto, imagen.getType());

        //for anidados para recorrer todos los pixeles
        for (int x = inicioX; x < inicioX + ancho; x++) {
            for (int y = inicioY; y < inicioY + alto; y++) {
                int rgb = imagen.getRGB(x, y); //obtiene color del pixel

                //obtiene el valor de cada color
                int A = (rgb >> 24) & 0xFF; //transparencia
                int R = (rgb >> 16) & 0xFF;
                int G = (rgb >> 8) & 0xFF;
                int B = rgb & 0xFF;

                //si son colores monocromaticos o cercanos no aplica filtro
                if (Math.abs(R - G) < 50 && Math.abs(G - B) < 50 && Math.abs(R - B) < 50) {
                    resultado.setRGB(x - inicioX, y - inicioY, rgb);
                }
                else if((R>100) || (B>100)){
                    //aplica filtro a los colores aumenta verde y intercambia rojo por azul
                    int auxR = R;
                    R = B;
                    G = Math.min((int) (G * 1.05), 255);
                    B = auxR;

                    //color final del pixel
                    int RGBf = (A << 24) | (R << 16) | (G << 8) | B;

                    //asigna el nuevo color al pixel de la imagen resultado
                    resultado.setRGB(x - inicioX, y - inicioY, RGBf);
                }
                else{
                    resultado.setRGB(x - inicioX, y - inicioY, rgb);
                }
                
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
public class Practica10 {

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
                    FiltroTask task = new FiltroTask(imagen, 0, 0, imagen.getWidth(), imagen.getHeight());
                    BufferedImage imagenDifuminada = pool.invoke(task); //ejecuta tarea y obtiene la nueva imagen
                    //guarda la imagen difuminada
                    File salida = new File(nombreArchivo + "_modificada" + extension);
                    boolean resultado = ImageIO.write(imagenDifuminada, extension.substring(1), salida);
                    if (!resultado) {
                        System.out.println("No se encontró un escritor para el formato:" + extension);
                    } else {
                        System.out.println("Imagen modificada guardada en " + salida.getAbsolutePath());
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
