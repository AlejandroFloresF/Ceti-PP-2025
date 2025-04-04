/*Single Instruction, Single Data*/

#include <stdio.h>

int main () {
    int Dividendo = 37, Divisor=5;
    int Cociente, Residuo;

    Cociente = Dividendo / Divisor;
    Residuo= Dividendo % Divisor;

    printf("Cociente: %d\n", Cociente);
    printf("Residuo: %d\n", Residuo);
    return 0; 
}