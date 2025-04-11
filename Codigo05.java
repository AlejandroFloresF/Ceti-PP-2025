//Strong consistency
public class Codigo05 {

    public static class CuentaBancaria {
        private int saldo; //Se pone como privado para que no se pueda acceder desde fuera de la clase

        public CuentaBancaria(int saldoInicial) {  // Constructor de la clase CuentaBancaria
            this.saldo = saldoInicial; //Se pone como this para que se acceda al atributo de la clase y no al parametro
        }

        //Para depositar se usa synchronized para que no se pueda acceder al metodo mientras se esta ejecutando otro hilo 
        public synchronized void depositar(int monto){
            saldo += monto; //Se agrega el monto al saldo en el hilo que se ejecuta
        }

        //Para retirar se usa synchronized para que no se pueda acceder al metodo mientras se esta ejecutando otro hilo
        public synchronized void retirar(int monto){
            if(saldo >= monto){ //Se verifica si el saldo es mayor o igual al monto que se quiere retirar
                saldo -= monto; //Se resta el monto al saldo en el hilo que se ejecuta
            }else{ //Si el saldo es menor al monto que se quiere retirar
                System.out.println("Fondos insuficientes");  //Se imprime un mensaje de fondos insuficientes
            }
        }
    }

    static class transacciones{ //Clase transacciones para realizar las transferencias entre cuentas 
        public static synchronized void transferir(CuentaBancaria origen, CuentaBancaria destino, int monto){ //Se recibe la cuenta origen, la cuenta destino y el monto que se quiere transferir
            origen.retirar(monto);  //Se llama al metodo retirar de la cuenta origen y se le resta el monto al saldo en el hilo que se ejecuta
            destino.depositar(monto); //Se llama al metodo depositar de la cuenta destino y se le agrega el monto al saldo en el hilo que se ejecuta
        }
    }
    

    public static void main(String[] args) throws InterruptedException { //Metodo main para ejecutar el programa
       CuentaBancaria cuentaA = new CuentaBancaria(1000); //Se crea una cuenta bancaria con saldo inicial de 1000
       CuentaBancaria cuentaB = new CuentaBancaria(2000); //Se crea una cuenta bancaria con saldo inicial de 2000

       Thread hiloA = new Thread(() -> { //Se crea un hilo para realizar las transferencias de la cuenta A a la cuenta B
          for (int i = 0; i < 10; i++) { //Se realiza 10 transferencias
            transacciones.transferir(cuentaA, cuentaB, 10);  //Se llama al metodo transferir de la clase transacciones y se le pasa la cuenta origen, la cuenta destino y el monto que se quiere transferir
          } 
       });

       Thread hiloB = new Thread(() -> { //Se crea un hilo para realizar las transferencias de la cuenta B a la cuenta A
        for (int i = 0; i < 100; i++) { //Se realiza 100 transferencias
            transacciones.transferir(cuentaB, cuentaA, 5); //Se llama al metodo transferir de la clase transacciones y se le pasa la cuenta origen, la cuenta destino y el monto que se quiere transferir
             } 
       });

       hiloA.start(); //Se inicia el hilo A
       hiloB.start(); //Se inicia el hilo B
        try {
        hiloA.join(); //Se espera a que el hilo A termine
        System.out.println("hiloA termino\n"); //Se imprime un mensaje cuando el hilo A termina
        hiloB.join(); //Se espera a que el hilo B termine
        System.out.println("hiloB termino\n"); //Se imprime un mensaje cuando el hilo B termina
        } catch (InterruptedException e) { //Se captura la excepcion si el hilo se interrumpe
            e.printStackTrace(); //Se imprime la excepcion
        }

       System.out.println("Saldo de cuenta A: " + cuentaA.saldo); //Se imprime el saldo de la cuenta A
       System.out.println("Saldo de cuenta B: " + cuentaB.saldo); //Se imprime el saldo de la cuenta B
        
    }
}