// Pregunta 9 - OpenMP multiplicación de matrices

#include <stdio.h>
#include <omp.h>

int main() {
    int tam = 3;                       // Tamaño de la matriz (3x3)
    int A[3][3] = { {11,88,3}, {4,1,6}, {7,8,10} };
    int B[3][3] = { {11,89,4}, {6,91,4}, {32,22,15} };
    int C[3][3] = {0};                 // Matriz resultado inicializada en cero

    // Paralelización con secciones: cada sección es una tarea independiente
    // Aquí dividimos la multiplicación por filas para que cada hilo procese una fila distinta
    #pragma omp parallel sections
    {
        #pragma omp section
        for (int i = 0; i < 1; i++) {  // Primera fila (hilo 1)
            for (int j = 0; j < tam; j++) {
                for (int k = 0; k < tam; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        #pragma omp section
        for (int i = 1; i < 2; i++) {  // Segunda fila (hilo 2)
            for (int j = 0; j < tam; j++) {
                for (int k = 0; k < tam; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        #pragma omp section
        for (int i = 2; i < 3; i++) {  // Tercera fila (hilo 3)
            for (int j = 0; j < tam; j++) {
                for (int k = 0; k < tam; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }

    printf("Matriz resultado C:\n"); // Imprimir la matriz resultado
    for (int i = 0; i < tam; i++) {
        for (int j = 0; j < tam; j++) {
            printf("%4d ", C[i][j]);
        }
        printf("\n");
    }

    return 0;
}