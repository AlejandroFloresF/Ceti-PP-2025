/*22110223*/
/*Weak consistency*/

/***
 * clase principal
 */
public class Practica6 {

    private int contador = 0;
    /***
     * Metodo no sincronizado para incrementar el contador
     * @param incrementar true: suma o false:resta
     * @param contador valor actual
     * @return
     */
    public void contadorMod(boolean incrementar) {

        contador = incrementar? contador+1 : contador-1;
    }

    /***
     * Metodo para ejecutar hilos y probar forma de funcionamiento 
     */
    public void ejecutarHilos() {

        //hilo de suma
        Thread hiloSuma = new Thread(() -> {
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
        Thread hiloResta = new Thread(() -> {
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

        //inicia los hilos
        hiloSuma.start();
        hiloResta.start();

        //espera la ejecucion completa
        try{
            hiloSuma.join();
            hiloResta.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * funcion main
     * @param args
     */
    public static void main(String[] args) {

        Practica6 contadorThr = new Practica6();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        char opcion;

        do {
            
            System.out.println("valor actual del contador: " + contadorThr.contador + "\n");
            System.out.println("Ingresar '+' para incrementar el contador");
            System.out.println("Ingresar '-' para disminuir el contador");
            System.out.println("Ingresar '*' para ejecutar hilos");
            System.out.println("Ingresar 'x' para salir");
            opcion = scanner.next().charAt(0); //obtiene primer caracter de linea

            if (opcion == '+') { //suma

                contadorThr.contadorMod(true);
            }

            else if (opcion == '-') { //resta

                contadorThr.contadorMod(false);
            }

            else if (opcion == '*')  { //ejecuta hilos para probar consistencia

                contadorThr.ejecutarHilos();
            }

        } while (opcion != 'x'); 

        scanner.close();
    }
}
