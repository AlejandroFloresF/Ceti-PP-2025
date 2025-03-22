//Codigo 1 - SISD - Single Instruction Single Data

#include <stdio.h>

int main() {
    int calMate = 100;
    int calCompuParalela = 90;
    int calFisica = 80;
    
    // Se realiza una operación aritmética sobre datos individuales 
    float promedio = (calMate + calCompuParalela + calFisica) / 3; // Se realizan las peraciones una por una
    printf("El promedio es: %.2f\n", promedio);  // Imprime como float con 2 decimales
    return 0;
}