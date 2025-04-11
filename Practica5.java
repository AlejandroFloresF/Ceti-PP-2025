/*22110223*/
/*Strong consistency*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/***
 * clase principal
 */
public class Practica5 {

    private int contador = 0;
    /***
     * Metodo sincronizado para incrementar el contador
     * @param incrementar true: suma o false:resta
     * @param contador valor actual
     * @return
     */
    public synchronized void contadorMod(boolean incrementar) {

        contador = incrementar? contador+1 : contador-1;
    }

    /***
     * Metodo para ejecutar hilos y probar forma de funcionamiento 
     */
    public void ejecutarHilos() {
        
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //hilo de suma
        executor.submit(() -> {
            for (int i = 0; i < 10; i++){
                
                contadorMod(true);
                System.out.println("suma");
                System.out.println("resultado: " + contador + "\n");
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "HiloSuma");

        //hilo de resta
        executor.submit(() -> {
            for (int i = 0; i < 10; i++){
                
                contadorMod(false);
                System.out.println("resta");
                System.out.println("resultado: " + contador + "\n");
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "HiloResta");

        //finaliza el ExecutorService
        executor.shutdown();
        try {
            
            if(!executor.awaitTermination(1, TimeUnit.SECONDS)){

                System.out.println("No terminaron los hilos");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * funcion main
     * @param args
     */
    public static void main(String[] args) {

        Practica5 contadorSinc = new Practica5();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        char opcion;

        do {
            
            System.out.println("valor actual del contador: " + contadorSinc.contador + "\n");
            System.out.println("Ingresar '+' para incrementar el contador");
            System.out.println("Ingresar '-' para disminuir el contador");
            System.out.println("Ingresar '*' para ejecutar hilosx");
            System.out.println("Ingresar 'x' para salir");
            opcion = scanner.next().charAt(0); //obtiene primer caracter de linea

            if (opcion == '+') { //suma

                contadorSinc.contadorMod(true);
            }

            else if (opcion == '-') { //resta

                contadorSinc.contadorMod(false);
            }

            else if (opcion == '*')  { //ejecuta hilos para probar consistencia

                contadorSinc.ejecutarHilos();
            }

        } while (opcion != 'x'); 

        scanner.close();
    }
}