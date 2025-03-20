// Codigo 2 - SIMD
// Marco Antonio Galindo Torres - 22110221

#include <omp.h>
#include <stdio.h>

// Num de elementos del arreglo
#define N 4 

int main() {

    int a[N] = {1, 2, 3, 4};
    int b[N] = {9, 8, 7, 6};
    int res[N];

    // Con pragma omp simd hacemos que el bucle se ejecute en paralelo, aplicando la misma operación
    // a multiples datos de forma simultánea
    // https://www.ibm.com/docs/zh/xl-c-and-cpp-linux/16.1.0?topic=pdop-pragma-omp-simd
#pragma omp simd
    for (int i = 0; i < N; i++) {
        res[i] = a[i] + b[i]; // Una sola instrucción que opera sobre múltiples datos
    }

    printf("Resultado para SIMD: \n");
    for (int i = 0; i < N; i++) {
        printf("%d ", res[i]);
    }

    return 0;
}
