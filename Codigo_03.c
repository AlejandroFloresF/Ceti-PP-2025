//Multiple Instruction, Single Data
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <omp.h>

void ordenamientoBurbuja(int *, int );
void ordenamientoBurbujaInv(int *, int );
void imprimirArreglo(int *, int);

int main(){
    srand(time(NULL));//genera una semilla aleatoria de acuerdo al tiempo del computador, para no tener la misma secuencia de numero aleatorios en cada ejecucion
    int n = 20; //numero de elementos de los arreglos que seran ordenados con burbuja
    int arreglo[n];//arreglo a ordenar por burbuja

    for(int i = 0; i < n; i++){ //se rellena el arreglo con numeros aleatorios del 1 al 50
        arreglo[i] = 1 + rand()%50;
    }

    imprimirArreglo(arreglo, n);

    #pragma omp parallel sections //crea una seccion paralela, que es en donde habra varios hilos de ejecucion
    {
        #pragma omp section //un hilo ejecutara el ordenamiento burbuja sobre el arreglo
            ordenamientoBurbuja(arreglo, n);
        
        #pragma omp section //otro hilo ejecutara el ordenamiento burbuja en orden inverso sobre el mismo arreglo
            ordenamientoBurbujaInv(arreglo, n);
    } //Se tiene multiples flujos de instrucciones operando sobre el mismo conjunto de datos, por lo que es MISD.
    printf("\n\n");
    printf("Arreglo:");
    imprimirArreglo(arreglo, n); //se imprime el arreglo
    return 0; 
}

void ordenamientoBurbuja(int *arreglo, int n){ //ordena los elementos de menor a mayor
    for(int i = 0; i < n - 1; i++){
        for(int f = 0; f < n - 1; f++){
            if(arreglo[f] > arreglo[f + 1]){
                int aux = arreglo[f];
                arreglo[f] = arreglo[f + 1];
                arreglo[f + 1] = aux;
            }
        }
        //La directiva de abajo se usa para que un solo hilo pueda estar imprimiendo el arreglo
        //a la vez y no se entremezclen la impresion de los dos hilos en una sola linea. De esta forma,
        //se puede ver mejor el proceso de ejecucion de cada hilo.
        #pragma omp critical
        imprimirArreglo(arreglo, n);
    }
}

void ordenamientoBurbujaInv(int *arreglo, int n){ //ordena los elementos de mayor a menor
    for(int i = 0; i < n - 1; i++){
        for(int f = 0; f < n - 1; f++){
            if(arreglo[f] < arreglo[f + 1]){
                int aux = arreglo[f];
                arreglo[f] = arreglo[f + 1];
                arreglo[f + 1] = aux;
            }
        }
        //La directiva de abajo se usa para que un solo hilo pueda estar imprimiendo el arreglo
        //a la vez y no se entremezclen la impresion de los dos hilos en una sola linea. De esta forma,
        //se puede ver mejor el proceso de ejecucion de cada hilo.
        #pragma omp critical
        imprimirArreglo(arreglo, n);
    }
}

void imprimirArreglo(int *arreglo, int n){ //Imprime los elementos del arreglo en una sola linea
    for(int i = 0; i < n; i++){
        if(i < n - 1){
            printf("%d-", arreglo[i]);
        }
        else{
            printf("%d\n", arreglo[i]);
        }
    }
}