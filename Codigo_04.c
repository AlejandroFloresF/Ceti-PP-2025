 // Codigo 4 - MIMD - Multiple Instruction Multiple Data
#include <stdio.h>  
#include <omp.h>

#include <stdio.h>
#include <omp.h>
#include <limits.h>

int main() {
    int data[1000];// Se declara un arreglo de 1000 elementos de tipo entero.
    int dataDos[1000];// Se declara un arreglo de 1000 elementos de tipo entero.
    int dataTres[1000];// Se declara un arreglo de 1000 elementos de tipo entero.

    int suma = 0;// Se inicializa la variable suma en 0.
    int max = 0;// Se inicializa la variable max en 0.
    int min = 0;// Se inicializa la variable min en 0.


    // Inicializamos el arreglo con valores del 1 al 1000.
    for (int i = 0; i < 1000; i++) {
        data[i] = i + 1;//
        dataDos[i] = i * 2;
        dataTres[i] = i * -1;
    }
   

    // se aplica el modelo MIMD para realizar las instrucciones de suma, encontrar el máximo y el mínimo en paralelo sobre arreglos diferentes.
    #pragma omp parallel sections
    {
        #pragma omp section
        {
            // Suma total del arreglo.
            for (int i = 0; i < 1000; i++) {// Se recorre el arreglo.
                suma += data[i];// Se suma el valor en la posición i del arreglo a la variable suma.
            }
        }
        #pragma omp section
        {
            // Se encuentra el valor máximo del arreglo.
            for (int i = 0; i < 1000; i++) {// Se recorre el arreglo.
                if (dataDos[i] > max) {// Se compara el valor en la posición i del arreglo con el valor máximo.
                    max = dataDos[i];
                }
            }
        }
        #pragma omp section
        {
            // se encuentra el valor mínimo del arreglo.
            for (int i = 0; i < 1000; i++) {//  Se recorre el arreglo.
                if (dataTres[i] < min) {// Se compara el valor en la posición i del arreglo con el valor mínimo.
                    min = dataTres[i];
                }
            }
        }
    }

    // se imprimen los resultados.
    printf("Suma total: %d\n", suma);
    printf("Valor máximo: %d\n", max);
    printf("Valor mínimo: %d\n", min);

    return 0;
}
