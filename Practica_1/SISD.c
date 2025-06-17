/*
    Práctica 1 - SISD
    Fernando Zazir Gómez Corona
    22110227
*/

#include <stdio.h>
#include <omp.h>

#define N 5

int main() {
    int A[N] = {4, 3, 2, 5, 0};      // Primer vector
    int B[N] = {5, 4, 5, 3, 5};      // Segundo vector
    int C[N];                        // Vector resultado

    // Aquí hacemos uso de un solo 1 hilo, por lo que aunque estemos aplicando técnicas de paralelismo
    // sigue siendo como secuencial (SISD), al final es un solo proceso
    #pragma omp parallel for num_threads(1)
    for (int i = 0; i < N; i++) {
        C[i] = A[i] + B[i];  // MISMA instrucción sobre un dato por iteración
    }

    // Imprimir resultados
    printf("Resultado de la suma de calificaciones:\n");
    for (int i = 0; i < N; i++) {
        printf("%d ", C[i]);
    }
    printf("\n");

    return 0;
}