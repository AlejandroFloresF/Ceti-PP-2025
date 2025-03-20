/*22110223*/
/*Single instruction, single data*/

#include <stdio.h>

unsigned long long factorial(int num);

/// @brief Funcion main
/// @return 0
int main(){

    int num = 0;

    do{

        printf("Ingrese un numero para calcular factorial (positivo): ");
        scanf("%d", &num); //recibe numero

    } while (num < 0);

    printf("El resultado del factorial es: %llu\n", factorial(num));

    return 0;
}

/// @brief Funcion para calcular factorial
/// @param num 
/// @return Resultado de factorial en unsigned long long 
unsigned long long factorial(int num){

    unsigned long long res = 1;

    for (int i =1; i <= num; i++){
        
        res *= i; //multiplicacion secuencial
    }

    return res;
}