// Codigo 3 - MISD
// Marco Antonio Galindo Torres - 22110221

#include <omp.h>
#include <stdio.h>

int main() {

    int a = 5;
    int resultado1, resultado2;

    // Usamos #pragma omp parallel sections para crear dos secciones paralelas
    // de esta forma, cada sección ejecuta una instrucción diferente sobre el mismo dato "a"
#pragma omp parallel sections
    {
#pragma omp section
        {
            resultado1 = a + 1; // Primer instrucción
        }

#pragma omp section
        {
            resultado2 = a * 2; // Segunda instrucción
        }
    }

    printf("Resultado MISD -> a + 1 = %d\n", resultado1);
    printf("Resultado MISD -> a x 2 = %d\n", resultado2);

    return 0;
}
