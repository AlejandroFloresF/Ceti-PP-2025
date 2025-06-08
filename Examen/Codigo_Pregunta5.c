// Pregunta 5 - OpenMP Numeros Primos

#include <stdio.h>
#include <omp.h>
#include <math.h>

int esPrimo(int n) {            // Función que verifica si un número es primo
    if (n <= 1) return 0;       // Números <= 1 no son primos
    if (n == 2) return 1;       // 2 es primo

    for (int i = 2; i <= n / 2; i++) {// Revisa si n tiene algún divisor desde 2 hasta n/2
        if (n % i == 0) {
            return 0;           // No es primo si tiene divisor
        }
    }
    return 1;                   // Es primo si no encontró divisores
}

int main() {
    printf("Numeros primos entre 1 y 1000:\n");
    #pragma omp parallel for schedule(dynamic)    // Paraleliza el ciclo for usando OpenMP
    for (int i = 1; i <= 1000; i++) {
        if (esPrimo(i)) {
            #pragma omp critical                  // Solo un hilo imprime a la vez para evitar mezcla de texto
            printf("El numero %d es primo\n", i);
        } 
    }
    return 0;                                   // Fin del programa
}