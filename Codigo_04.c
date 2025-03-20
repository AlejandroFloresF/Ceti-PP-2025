//Multiple Instruction, Multiple Data
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <omp.h>

void ordenamientoBurbuja(int *, int );
void ordenamientoBurbujaInv(int *, int );
void imprimirArreglo(int *, int);


int main(){
    srand(time(NULL)); //genera una semilla aleatoria de acuerdo al tiempo del computador, para no tener la misma secuencia de numero aleatorios en cada ejecucion
    int n = 200; //numero de elementos del arreglo de calificaciones
    int n2 = 20; //numero de elementos de los arreglos que seran ordenados con burbuja
    int suma = 0; //almacenara la suma de todas las calificaciones
    float promedio; //tendra el promedio de las calificaciones
    int calificaciones[n]; //arreglo de calificaciones
    int arreglo1[n2]; //arreglo a ordenar por burbuja
    int arreglo2[n2]; //arreglo a ordenar por burbuja

    printf("Ejemplo uno de aplicar MIMD:\n\n");

    for(int i = 0; i < n; i++){ //se llena el arreglo con numeros aleatorios del 1 al 100
        calificaciones[i] = 1 + rand()%100; //se le da un numero aleatorioa del 1 al 100 a dicho elemento del arreglo
        printf("Calificacion %i: %d\n", i, calificaciones[i]); //se imprime en pantalla la calificacion que se le dio
    }

    //Se hace MIMD dividiendo las iteraciones del for entre varios hilos, aunque cada hilo esta ejecutando la misma instrucción identica
    //que esta dentro del for, cada uno tiene su propio flujo de instrucción, y al dividirse las iteraciones del ciclo, cada uno esta actuando
    //sobre una parte distinta del arreglo, por lo que no estan tratando de modificar el mismo conjunto de datos a la vez. Por lo tanto, al tener multiples
    //flujos de instrucciones y al estar actuando sobre distintas partes del arreglo; teniendo una variable suma privada para cada hilo, se tiene MIMD.
    // A nivel de cada hilo se aplicara vectorización, haciendo que se traten varios
    //datos a la vez con una sola instrucción; como ejecutar cuatro sumas en una instrucción. Con esto se tiene MIMD y SIMD a nivel de hilo.
    #pragma omp parallel for simd reduction(+:suma) //Al final, las variables privadas suma de cada hilo se sumarán en un variable global suma.
    for(int i = 0; i < n; i++){ //se suman todas las calificaciones
        suma = suma + calificaciones[i];
    }

    promedio = suma/n; //se divide entre el numero total para obtener el promedio
    printf("La suma es %d\n", suma); //se imprimen los resultados en pantalla
    printf("El promedio del grupo es: %.2f\n", promedio);

    printf("\n\nVersion dos de MIMD:\n\n");

    for(int i = 0; i < n2; i++){ //se rellenan los dos arreglos con numeros aleatorios del 1 al 50
        arreglo1[i] = 1 + rand()%50;
        arreglo2[i] = 1 + rand()%50;
    }

    #pragma omp parallel sections //crea una seccion paralela, que es en donde habra varios hilos de ejecucion
    {
        #pragma omp section //un hilo ejecutara el ordenamiento burbuja sobre el arreglo uno
            ordenamientoBurbuja(arreglo1, n2);
        #pragma omp section //otro hilo ejecutar el ordenamiento burbuja sobre el arreglo dos
            ordenamientoBurbujaInv(arreglo2, n2);
    } //Se tiene dos flujos de instrucciones operando sobre distintos conjuntos de datos, por lo que es MIMD.
    printf("\n\n");
    printf("Arreglo1:");
    imprimirArreglo(arreglo1, n2); //se imprime el arreglo1
    printf("Arreglo2:");
    imprimirArreglo(arreglo2, n2); //se imprime el arreglo2
    return 0;
}


void ordenamientoBurbuja(int *arreglo, int n){//ordena los elementos de menor a mayor
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

void ordenamientoBurbujaInv(int *arreglo, int n){//ordena los elementos de mayor a menor
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