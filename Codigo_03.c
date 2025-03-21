/*Multiple instruction, Single data*/

#include <stdio.h>
#include <omp.h>
#include <math.h>

/*void func1(int *data, int n){   
    for (int i = 0; i < n; i++){
        data[i] += 1;
        printf("Soy Func1: %d\n", data[i]);
        printf("\n");
    }
}

void func2(int *data, int n){
    for (int i = 0; i < n; i++){
        data[i] *= 2;
        printf("Soy Func2: %d\n", data[i]);
        printf("\n");
    }
}

int main(){

    int n = 100;
    int data[100];
    
    for (int i = 0; i < n; i++){
        data[i] = i + 1;
    }

    #pragma omp parallel sections
    {
        #pragma omp section
        func1(data, n);

        #pragma omp section
        func2(data, n);
    }

    for (int i = 0; i < n; i++){
        printf("Soy el hilo %d y calculo %d", i, data[i]);
        printf("\n");
    }

    return 0;
} */

int main(){ // Funcion principal
    int dato = 25; // Dato unico sobre el que se realizaran multiples operaciones
    double resultados[4]; // Array para almacenar los resultados de las operaciones
    
    #pragma omp parallel sections //Se divide el trabajo entre los hilos
    {
        #pragma omp section // Se ejecuta en un hilo
        resultados[0] = sqrt(dato); // Calculo de la raiz cuadrada

        #pragma omp section // Se ejecuta en otro hilo
        resultados[1] = log(dato); // Calculo del logaritmo

        #pragma omp section // Se ejecuta en otro hilo
        resultados[2] = pow(dato, 2); // Calculo de la potencia

        #pragma omp section // Se ejecuta en otro hilo
        resultados[3] = exp(dato); // Calculo del exponencial  
    }
    
    printf("Resultado de raiz: %.2f\n", resultados[0]); // Imprime el resultado de la raiz cuadrada
    printf("Resultado de logaritmo: %.2f\n", resultados[1]); // Imprime el resultado del logaritmo
    printf("Resultado de potencia: %.2f\n", resultados[2]); // Imprime el resultado de la potencia
    printf("Resultado de exponencial: %.2f\n", resultados[3]); // Imprime el resultado del exponencial
    
    return 0;
}