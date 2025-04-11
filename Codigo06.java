//Weak consistency
public class Codigo06 {
    public static class CuentaBancaria {
        int saldo; //El saldo es público y no privado lo que permite acceso concurrente sin bloqueo

        public CuentaBancaria(int saldoInicial) {  // Constructor de la clase CuentaBancaria
            this.saldo = saldoInicial; //Se inicializa el saldo con el valor proporcionado
        }

        //Método para depositar dinero - NO usa synchronized, lo que permite acceso concurrente sin bloqueo
        public void depositar(int monto){
            saldo += monto; //Se agrega el monto al saldo sin protección de concurrencia
        }

        //Método para retirar dinero - NO usa synchronized, lo que permite acceso concurrente sin bloqueo
        public void retirar(int monto){
            if(saldo >= monto){ //Se verifica si hay saldo suficiente
                saldo -= monto; //Se resta el monto al saldo sin protección de concurrencia
            }else{ //Si no hay saldo suficiente
                System.out.println("Fondos insuficientes"); //Se muestra mensaje de error
            }
        }
    }

    static class transacciones{ //Clase para realizar transferencias entre cuentas
        //Método para transferir dinero - NO usa synchronized, permitiendo posibles condiciones de error
        public static void transferir(CuentaBancaria origen, CuentaBancaria destino, int monto){
            origen.retirar(monto); //Se retira el monto de la cuenta origen
            destino.depositar(monto); //Se deposita el monto en la cuenta destino
        }
    }
    

    public static void main(String[] args) throws InterruptedException{ //Método principal
       CuentaBancaria cuentaA = new CuentaBancaria(1000); //Se crea cuenta A con saldo inicial de 1000
       CuentaBancaria cuentaB = new CuentaBancaria(2000); //Se crea cuenta B con saldo inicial de 2000

       Thread hiloA = new Thread(() -> { //Se crea un hilo para transferir de cuenta A a cuenta B
          for (int i = 0; i < 10; i++) { //Se realizan 10 transferencias
            transacciones.transferir(cuentaA, cuentaB, 10); //Se llama al metodo transferir de la clase transacciones y se le pasa la cuenta origen, la cuenta destino y el monto que se quiere transferir
          } 
       });

       Thread hiloB = new Thread(() -> { //Se crea un hilo para transferir de cuenta B a cuenta A
        for (int i = 0; i < 100; i++) { //Se realizan 100 transferencias
            transacciones.transferir(cuentaB, cuentaA, 5); //Se llama al metodo transferir de la clase transacciones y se le pasa la cuenta origen, la cuenta destino y el monto que se quiere transferir
             } 
       });

       hiloA.start(); //Se inicia el hilo A
       hiloB.start(); //Se inicia el hilo B
       //Se espera a que ambos hilos terminen
        try {
        hiloA.join(); //Se espera a que el hilo A termine
        System.out.println("hiloA termino\n"); //Se notifica que el hilo A terminó
        hiloB.join(); //Se espera a que el hilo B termine
        System.out.println("hiloB termino\n"); //Se notifica que el hilo B terminó
        } catch (InterruptedException e) { //Se captura excepción si un hilo es interrumpido
            e.printStackTrace(); //Se imprime la traza de la excepción
        }

        if(cuentaA.saldo < 0 || cuentaB.saldo < 0){ //Se verifica si alguna cuenta tiene saldo negativo
            System.out.println(" ❌ Saldo negativo detectado, esto no deberia pasar"); //Error por inconsistencia
        }else{
            System.out.println("Saldo de cuenta A: " + cuentaA.saldo); //Se muestra el saldo final de cuenta A que puede o no tener los datos bien
            System.out.println("Saldo de cuenta B: " + cuentaB.saldo); //Se muestra el saldo final de cuenta B que puede o no tener los datos bien
        }
    }
}