// Programa de Alejandro Velazquez Luna
// Registro 22110228
// Multiple Instruction, Single Data (MISD)
#include <stdio.h>
#include <stdlib.h> 
#include <omp.h>
//Funcion que se mandara a llamar para ordenar de manera ascendente
int asc(const void *a, const void *b)
{
    return (*(int *)a - *(int *)b); 
}
//Funcion que se mandara a llamar para ordenar de manera descendente
int desc(const void *a, const void *b)
{
    return (*(int *)b - *(int *)a); 
}
// Función que ordena ascendentemente
void func1(int *data, int n)
{
    //Para el uso de qsort se necesitan pasarle 4 parametros que son los siguientes
    // 1. El arreglo a ordenar
    // 2. El tamaño del arreglo
    // 3. El Tamaño de cada elemento del arreglo
    // 4. La funcion de comparacion que se va a utilizar
    qsort(data, n, sizeof(int), asc); // Orden ascendente

}
// Función que ordena descendentemente
void func2(int *data, int n)
{
    //Arriba se explica el uso de qsort 
    qsort(data, n, sizeof(int), desc); // Orden descendente
}
int main()
{
    int i, n = 15;
    int data[15];

    // Llenamos el array con valores del 1 al 15 
    for (i = 0; i < 15; i++)
    {
        data[i] = i + 1;
    }

    //Como entendi en clase esta parte nos ayuda a que seccionar funciones de un programa
    // en este caso quise ver como se comportaba con dos funciones que ordenan un arreglo
    // de manera ascendente y descendente
#pragma omp parallel sections
    {
#pragma omp section
        // Sección 1 se ordena de forma ascendente 
        func1(data, n); 
        
        // Sección 2 se ordena de forma descendente  
#pragma omp section
        func2(data, n); 
    }
    //Imprimimos en consola el ordenamiento resultante
    printf("Orden Final: ");
    for (int i = 0; i < n; i++)
    {
        printf("%d ", data[i]);
    }
    printf("\n");
    return 0;
}
