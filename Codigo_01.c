
// Single Instruction, Single Data (SISD)
#include <stdio.h>

int main()
{
    int data = 100;//Nuestra data 
    int n = 10; // Numero de iteraciones para el for
    int result = 0; // Resultado final


    //Ciclo for que se ejecuta mientras i sea menor a n
    for(int i = 0; i < n; i++)
    {
        //En cada iteracion se suma la data al resultado
        result += data;
    }
    //Imprimimos el resultado en consola
    printf("Resultado obtenido: %d", result);
    
    return 0;
}