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
    int n = 15;
    int data1[15], data2[15];

    for (int i = 0; i < n; i++)
    {
        data1[i] = i + 1;
        data2[i] = i + 1;
    }

#pragma omp parallel sections
    {
#pragma omp section
        func1(data1, n);

#pragma omp section
        func2(data2, n);
    }

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