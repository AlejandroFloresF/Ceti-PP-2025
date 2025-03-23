// Programa de Alejandro Velazquez Luna 
// Registro 22110228 

// Single Instruction, Multiple Data (SIMD)

#include <stdio.h>
#include <omp.h>

int main()
{
    int i, n = 8; //Inicializamos n en 8 junto con i 
    float arr[n]; //Declaramos un arreglo de floats de tamaño n
    float result[n]; // Declaramos un arreglo de floats para los resultados;

    //Ciclo para el llenado del arreglo con valores del 1 a n
    for (i = 0; i < n; i++)
    {
        arr[i] = i + 1;
    }

    //Seccion paralela con pragma omp parallel for simd
    /*
        pragma omp parallel for
        Con esto lo que logramos, es dividir las iteracciones del ciclo entre multiples hilos,
        cada hilo ejecuta una parte del bucle en paralelo,

        simd
        Con esto indicamos que dentro de cada hilo, las iteraciones del ciclo deben ejecutarse usando instrucciones 
        SIMD.
    */
#pragma omp parallel for simd
    for (int i = 0; i < n; i++)
    {
        //Guardamos en result el cuadrado de cada elemento del arreglo
        result[i] = arr[i] * arr[i];
    }
    //Imprimimos los resultados obtenidos con un ciclo
    printf("Raices cuadradas obtenidas: ");
    for (int i = 0; i < n; i++)
    {
        printf("%.1f ", result[i]);
    }

    return 0;
}