// Codigo 4 - MIMD
// Marco Antonio Galindo Torres - 22110221

#include <omp.h>
#include <stdio.h>

// Num de elementos del arreglo
#define N 4

int main() {

    int a[N] = {1, 2, 3, 4};
    int b[N] = {5, 10, 15, 20};
    int res[N];

    // Usamos #pragma omp parallel for para paralelizar el bucle, permitiendo así que
    // diferentes hilos ejecuten diferentes instrucciones (suma o multiplicación) sobre 
    // diferentes datos (a[i] y b[i]) 
    // https://www.ibm.com/docs/en/xl-c-aix/13.1.2?topic=pdpp-pragma-omp-parallel
#pragma omp parallel for
    for (int i = 0; i < N; i++) {
        if (i % 2 == 0) {
            res[i] = a[i] + b[i]; // Instrucción 1 - Suma
        } else {
            res[i] = a[i] * b[i]; // Instrucción 2 - Multiplicación
        }
    }

    printf("Resultado MIMD: ");
    for (int i = 0; i < N; i++) {
        printf("%d ", res[i]);
    }
    printf("\n");

    return 0;
}
