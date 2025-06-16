#include <stdio.h>
#include <omp.h> // Necesario para OpenMP

// Definimos una variable global que sera nuestro "Single Data"
int shared_data = 100;

int main() {
    printf("--- MISD (Multiple Instruction, Single Data) ---\n");
    printf("Dato inicial: %d\n", shared_data);

    // Bloque paralelo de OpenMP con secciones
    // Multiples hilos ejecutaran diferentes bloques de codigo (Multiples Instrucciones)
    // sobre el mismo dato (shared_data).
    #pragma omp parallel sections
    {
        #pragma omp section // Primera seccion: Operacion de resta
        {
            // Declara tid dentro de esta seccion, sera privado para el hilo que la ejecute
            int tid = omp_get_thread_num();
            // Nota: Aquí los hilos compiten por shared_data, el orden no es garantizado.
            shared_data = shared_data - 10; // Instruccion 1: resta
            printf("Hilo %d: Despues de restar 10, shared_data = %d\n", tid, shared_data);
        }

        #pragma omp section // Segunda seccion: Operacion de multiplicacion
        {
            // Declara tid dentro de esta seccion
            int tid = omp_get_thread_num();
            shared_data = shared_data * 2; // Instruccion 2: multiplicacion
            printf("Hilo %d: Despues de multiplicar por 2, shared_data = %d\n", tid, shared_data);
        }

        #pragma omp section // Tercera seccion: Operacion de division
        {
            // Declara tid dentro de esta seccion
            int tid = omp_get_thread_num();
            shared_data = shared_data / 5; // Instruccion 3: division
            printf("Hilo %d: Despues de dividir por 5, shared_data = %d\n", tid, shared_data);
        }
    } // Fin del bloque parallel sections

    printf("Dato final despues de multiples instrucciones: %d\n", shared_data);
    printf("--------------------------------------------\n");

    return 0;
}