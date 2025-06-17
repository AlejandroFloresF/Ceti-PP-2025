/*
    Práctica 1 - SIMD
    Fernando Zazir Gómez Corona
    22110227
*/

#include <stdio.h>
#include <omp.h>

#define N 5

int main() {
    int A[N] = {1, 6, 12, 8, 5};        // Vector A
    int B[N] = {6, 4, 3, 9, 10};        // Vector B
    int C[N];                           // Resultado

    // OpenMP divide automáticamente el bucle entre hilos
    // MISMA instrucción sobre DIFERENTES datos (SIMD)
    #pragma omp parallel for
    for (int i = 0; i < N; i++) {       // Dentro del proceso que vamos a paralelizar
        C[i] = A[i] + B[i];             // notamos que impacta los mismos datos
    }

    // Mostrar resultado
    printf("Resultado de la suma paralelizada:\n");
    for (int i = 0; i < N; i++) {
        printf("%d ", C[i]);
    }
    printf("\n");

    return 0;
}