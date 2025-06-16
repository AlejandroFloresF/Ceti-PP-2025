#include <stdio.h> // Necesario para funciones de entrada/salida como printf

int main() {
    // Declaración e inicialización de variables (Single Data)
    int numero1 = 10;
    int numero2 = 5;
    int resultado;

    // Operación (Single Instruction)
    resultado = numero1 + numero2;

    // Impresión del resultado
    printf("SISD (Single Instruction, Single Data) \n");
    printf("Numero 1: %d\n", numero1);
    printf("Numero 2: %d\n", numero2);
    printf("Resultado de la suma: %d\n", resultado);
    printf("\n");

    return 0; // Indica que el programa terminó correctamente
}