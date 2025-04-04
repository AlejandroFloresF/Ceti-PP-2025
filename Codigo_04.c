//Multiple Instruction, Multiple Data

#include <stdio.h>
#include <omp.h>

//conversiones de monedas a pesos mexicanos
#define TASA_DOLAR 20.09
#define TASA_EURO 22.25  

// Función para convertir los dolares a pesos mexicanos
void convertir_dolares(int *PC_EUA, int n) {
    for (int i = 0; i < n; i++) {
        PC_EUA[i] = PC_EUA[i] * TASA_DOLAR;
    }
}

// Función para convertir los euros a pesos mexicanos
void convertir_euros(int *PC_ESP, int n) {
    for (int i = 0; i < n; i++) {
        PC_ESP[i] = PC_ESP[i] * TASA_EURO;
    }
}

int main() {
    int n = 5;  //computadiras totales 
    int PC_EUA[5] = {800, 1200, 700, 950, 1100}; // Precios en dólares
    int PC_ESP[5] = {700, 1100, 650, 900, 1000}; // Precios en euros

    
    #pragma omp parallel sections
    {
        #pragma omp section
        convertir_dolares(PC_EUA, n);//llamamos a la funcion que convertira dolares a pesos

        #pragma omp section
        convertir_euros(PC_ESP, n);//llamamos a la funcion que convertira euros a pesos
    }

    // Mostrar los precios convertidos en pesos mexicanos
    printf("Precios en pesos mexicanos (convertidos de dolares):\n");
    for (int i = 0; i < n; i++) {
        printf("Computadora %d: %.2f pesos\n", i + 1, (float)PC_EUA[i]);
    }

    printf("\nPrecios en pesos mexicanos (convertidos de euros):\n");
    for (int i = 0; i < n; i++) {
        printf("Computadora %d: %.2f pesos\n", i + 1, (float)PC_ESP[i]);
    }

    return 0;
}
