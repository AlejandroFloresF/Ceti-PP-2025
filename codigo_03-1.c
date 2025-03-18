#include <stdio.h> // Incluir la biblioteca estándar de entrada/salida para usar printf.
#include <omp.h>   // Incluir la biblioteca OpenMP para la programacion paralela.

// funcion 1: Modifica los datos y los imprime.
void func1(int *data, int n) { // Definicion de la funcion func1 que toma un puntero a un arreglo de enteros y su tamaño.
    for (int i = 0; i < n; i++) { // Bucle para iterar sobre cada elemento del arreglo.
        data[i] += i; // Modificar el elemento actual sumando su indice.
        printf("Soy func1: %d\n", data[i]); // Imprimir el valor modificado del elemento.
    }
}

// funcion 2: Modifica los datos y los imprime.
void func2(int *data, int n) { // Definicion de la funcion func2, similar a func1.
    for (int i = 0; i < n; i++) { // Bucle para iterar sobre cada elemento del arreglo.
        data[i] += i; // Modificar el elemento actual sumando su índice.
        printf("Soy func2: %d\n", data[i]); // Imprimir el valor modificado del elemento.
    }
}

int main() {
    int n; // Variable para almacenar el numero de elementos.
    printf("Ingrese el numero de elementos: ");
    scanf("%d", &n); // Leer el numero ingresado por el usuario.

    int data[n]; // Declarar un arreglo del tamaño especificado por el usuario.

    // Inicializa el arreglo con valores del 1 al n.
    for (int i = 0; i < n; i++) {
        data[i] = i + 1; // Llenar el arreglo con numeros del 1 al n.
    }

    // Ejecucion paralela con OpenMP utilizando secciones.
    #pragma omp parallel sections
    {
        #pragma omp section // Comienzo de la primera seccion paralela.
        func1(data, n); // Llamar a la funcion func1 en un hilo separado.

        #pragma omp section // Comienzo de la segunda seccion paralela.
        func2(data, n); // Llamar a la funcion func2 en otro hilo separado.
    }

    return 0; // Indicar que el programa ha terminado correctamente.
}