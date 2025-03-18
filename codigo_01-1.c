#include <stdio.h>

int main() {
    int sum = 0;
    int n; // Variable para almacenar el numero hasta el cual se sumará.

    printf("Ingrese un numero hasta el cual sumar: ");
    scanf("%d", &n); // Leer el numero ingresado por el usuario.

    int array[n]; // Declarar un arreglo del tamaño especificado por el usuario.

    for (int i = 0; i < n; i++) {
        array[i] = i + 1; // Llenar el arreglo con numeros del 1 al n.
    }

    for (int i = 0; i < n; i++) {
        sum += array[i]; // Sumar los elementos del arreglo.
    }

    printf("Suma total hasta %d: %d\n", n, sum); // Imprimir la suma total.

    return 0;
}