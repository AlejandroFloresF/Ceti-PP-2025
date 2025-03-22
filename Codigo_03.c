// Codigo 3 - SIMD - Single Instruction Multiple Data

#include <stdio.h>
#include <omp.h>

int main() {
    int i, n = 100;// Se inicializa la variable i y n en 100.
    float array[100];// Se declara un arreglo de 100 elementos de tipo flotante.

    for(int i = 0; i < 100; i++) {// Se llena el arreglo con valores del 1 al 100.
        array[i] = i + 1;// Se asigna el valor i + 1 al arreglo en la posición i.
    }
    // Se aplica el modelo SIMD para realizar la operación de dividir cada elemento del arreglo entre 100. De esta forma se realiza la operación en paralelo
    #pragma omp simd// Se inicia la sección paralela.
    for(int i = 0; i < 100; i++) {// Se recorre el arreglo.
        array[i] = array[i] / 100;// Se divide el valor en la posición i del arreglo entre 100.
    }

    for(int i = 0; i < 100; i++) {// Se recorre el arreglo.
        printf("array[%d] = %.2f\n", i, array[i]);// Se imprime el valor en la posición i del arreglo.
    }

    return 0;
}