/*22110223*/
/*Single instruction, multiple data*/

#include <stdio.h>
#include <omp.h>

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

/// @brief Funcion para calcular factorial (paralelizada)
/// @param num 
/// @return Resultado de factorial en unsigned long long 
unsigned long long factorial(int num){

    unsigned long long res = 1;

    //paralelizar con reduccion en multiplicacion
    #pragma omp parallel for simd reduction(*:res)
    for (int i =1; i <= num; i++){
        
        res *= i; //multiplicacion paralelizada

        //resultados parciales de reduccion
        printf("En hilo %d: %llu en iteracion %d\n", omp_get_thread_num(), res, i);
    }

    return res;
}