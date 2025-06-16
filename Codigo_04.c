#include <stdio.h>
#include <omp.h> // Necesario para OpenMP
#include <unistd.h> // Para sleep (para simular trabajo)

// Función para el Hilo 0: procesa el primer conjunto de datos
void tarea_hilo_0(int* data, int size) {
    printf("Hilo %d: Procesando Datos 1 (sumando 10 a cada elemento)...\n", omp_get_thread_num());
    for (int i = 0; i < size; i++) {
        data[i] += 10; // Diferente instruccion 1
        usleep(100); // Pequeña pausa para simular trabajo
    }
    printf("Hilo %d: Datos 1 procesados.\n", omp_get_thread_num());
}

// Función para el Hilo 1: procesa el segundo conjunto de datos
void tarea_hilo_1(int* data, int size) {
    printf("Hilo %d: Procesando Datos 2 (multiplicando por 2 cada elemento)...\n", omp_get_thread_num());
    for (int i = 0; i < size; i++) {
        data[i] *= 2; // Diferente instruccion 2
        usleep(100); // Pequeña pausa para simular trabajo
    }
    printf("Hilo %d: Datos 2 procesados.\n", omp_get_thread_num());
}

int main() {
    // Definimos diferentes conjuntos de datos (Multiple Data)
    int data_set_1[5] = {1, 2, 3, 4, 5};
    int data_set_2[5] = {10, 20, 30, 40, 50};

    printf("--- MIMD (Multiple Instruction, Multiple Data) ---\n");

    // Bloque paralelo de OpenMP
    #pragma omp parallel num_threads(2) // Creamos 2 hilos
    {
        int tid = omp_get_thread_num(); // Obtener el ID del hilo

        if (tid == 0) {
            // Hilo 0 ejecuta una instruccion diferente en data_set_1
            tarea_hilo_0(data_set_1, 5);
        } else if (tid == 1) {
            // Hilo 1 ejecuta otra instruccion diferente en data_set_2
            tarea_hilo_1(data_set_2, 5);
        }
    } // Fin del bloque parallel

    printf("\nResultados finales:\n");
    printf("Data Set 1: ");
    for (int i = 0; i < 5; i++) {
        printf("%d ", data_set_1[i]);
    }
    printf("\n");

    printf("Data Set 2: ");
    for (int i = 0; i < 5; i++) {
        printf("%d ", data_set_2[i]);
    }
    printf("\n");
    printf("--------------------------------------------\n");

    return 0;
}