// Pregunta 3 - OpenMP 

#include <stdio.h>
#include <omp.h>

int main() { 
    int sum = 0;        // Variable para almacenar la suma total
    int array[50];     // Arreglo de 100 enteros

    // Inicializa el arreglo con los números del 1 al 50
    for(int i = 0; i < 50; i++) {
        array[i] = i + 1;
    }

    // Bucle paralelo con OpenMP, usando reducción para sumar los elementos del arreglo
    // 'reduction(+:sum)' asegura que cada hilo tenga su propia copia de 'sum' y luego se combinen al final
    #pragma omp parallel for reduction(+:sum)
    for(int i = 0; i < 50; i++) {
        sum += array[i];   // Suma los elementos del arreglo
    }

    // Imprime la suma total en consola
    printf("Suma total: %d\n", sum);

    return 0;   // Fin del programa
}