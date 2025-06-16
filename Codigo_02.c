#include <stdio.h>
#include <stdlib.h> // Para malloc y free
#include <omp.h>    // Necesario para OpenMP

#define SIZE 1000 // Tamaño del arreglo

int main() { // <--- ASEGURATE QUE LA FUNCION MAIN ABRA AQUI
    // Declaración y asignación de memoria para los arreglos (Multiple Data)
    int *array_a = (int *)malloc(sizeof(int) * SIZE);
    int *array_b = (int *)malloc(sizeof(int) * SIZE);
    int *result_array = (int *)malloc(sizeof(int) * SIZE);

    // Inicialización de los arreglos
    for (int i = 0; i < SIZE; i++) {
        array_a[i] = i;
        array_b[i] = i * 2;
    }

    printf("--- SIMD (Single Instruction, Multiple Data) ---\n");
    printf("Realizando la suma de elementos de dos arreglos de tamano %d...\n", SIZE);

    // Bloque paralelo de OpenMP
    // La misma instruccion (suma) se aplica a multiples datos (elementos del arreglo)
    #pragma omp parallel for // Directiva OpenMP: divide el bucle entre los hilos
    for (int i = 0; i < SIZE; i++) {
        result_array[i] = array_a[i] + array_b[i]; // Misma instruccion: suma
    }

    // Imprimir algunos resultados para verificar (opcional, solo para depuración)
    printf("Primeros 5 resultados:\n");
    for (int i = 0; i < 5; i++) {
        printf("result_array[%d] = %d\n", i, result_array[i]);
    }
    printf("...\n");
    printf("Ultimos 5 resultados:\n");
    for (int i = SIZE - 5; i < SIZE; i++) {
        printf("result_array[%d] = %d\n", i, result_array[i]);
    }

    printf("--------------------------------------------\n"); 

    // Liberar memoria asignada
    free(array_a);
    free(array_b);
    free(result_array);

    return 0; 
} 