//Codigo 2 - MISD - Multiple Instruction Single Data

#include <stdio.h>
#include <omp.h>
#include <limits.h>

int main() {
    int array[1000];
    int suma = 0;
    int max = 0;
    int min = 0;


    // Inicializamos el arreglo con valores del 1 al 1000.
    for (int i = 0; i < 1000; i++) {
        array[i] = i + 1;
    }

    // Se aplica el modelo MISD para realizar las instrucciones de suma, encontrar el máximo y el mínimo en paralelo sobre un mismo arreglo 
    #pragma omp parallel sections
    {
        #pragma omp section
        {
            // Suma total del arreglo.
            for (int i = 0; i < 1000; i++) {
                suma += array[i];
            }
        }
        #pragma omp section
        {
            // Se encuentra el valor máximo del arreglo.
            for (int i = 0; i < 1000; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
        }
        #pragma omp section
        {
            // se encuentra el valor mínimo del arreglo.
            for (int i = 0; i < 1000; i++) {
                if (array[i] < min) {
                    min = array[i];
                }
            }
        }
    }

    // se imprimen los resultados.
    printf("Suma total");

    printf("Suma total: %d\n", suma);
    printf("Valor máximo: %d\n", max);
    printf("Valor mínimo: %d\n", min);

    return 0;
}