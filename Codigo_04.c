// Multiple Instruction, Multiple Data

#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

// Funcion que se mandara a llamar para ordenar de manera ascendente
int asc(const void *a, const void *b)
{
    return (*(int *)a - *(int *)b);
}

// Funcion que se mandara a llamar para ordenar de manera descendente
int desc(const void *a, const void *b)
{
    return (*(int *)b - *(int *)a);
}

// Función que ordena ascendentemente
void func1(int *data, int n)
{
    // Para el uso de qsort se necesitan pasarle 4 parametros que son los siguientes
    //  1. El arreglo a ordenar
    //  2. El tamaño del arreglo
    //  3. El Tamaño de cada elemento del arreglo
    //  4. La funcion de comparacion que se va a utilizar
    qsort(data, n, sizeof(int), asc); // Orden ascendente
}

// Función que ordena descendentemente
void func2(int *data, int n)
{
    // Arriba se explica el uso de qsort
    qsort(data, n, sizeof(int), desc); // Orden descendente
}

int main()
{
    //Variables para los for y el tamaño de los arrays 
    int n = 15;
    //Inicializamos dos arrays
    int data1[15], data2[15];

    //Llenamos los arrays con valores del 1 al 15
    for (int i = 0; i < n; i++)
    {
        data1[i] = i + 1;
        data2[i] = i + 1;
    }

    //Hacemos uso de la directiva parallel sections para que se ejecute en paralelo
#pragma omp parallel sections
    {
        //Seccion 1 
        //Se manda a llamar la funcion func1 con los parametros data1 y n
        //Se ordena de manera ascendente 
        #pragma omp section
        func1(data1, n);
        
        //Seccion 2
        //Se manda a llamar la funcion func2 con los parametros data2 y n
        //Se ordena de manera descendente 
#pragma omp section
        func2(data2, n);
    }

    //Imprimimos los resultados de los arrays resultantes
    printf("Resultados de data1:\n");
    for (int i = 0; i < n; i++)
    {
        printf("%d ", data1[i]);
    }
    printf("\n");

    printf("Resultados de data2:\n");
    for (int i = 0; i < n; i++)
    {
        printf("%d ", data2[i]);
    }
    printf("\n");

    return 0;
}