/*Multiple Instruction, Multiple Data*/

#include <stdio.h>
#include <omp.h>

/*void func1(int *data, int n) {
    for (int i = 0; i < n; i++) {
        data[i] += 1;
        printf("Soy Func1: %d\n", data[i]);
    }
}

void func2(int *data, int n) {
    for (int i = 0; i < n; i++) {
        data[i] *= 2;
        printf("Soy Func2: %d\n", data[i]);
    }
}

int main() {
    int n = 100;
    int data1[100], data2[100];

    for (int i = 0; i < n; i++) {
        data1[i] = data2[i] = i + 1;
    }

    #pragma omp parallel sections
    {
        #pragma omp section
        func1(data1, n);

        #pragma omp section
        func2(data2, n);
    }

    printf("\nResultados finales:\n");
    for (int i = 0; i < n; i++) {
        printf("data1[%d] = %d, data2[%d] = %d\n", i, data1[i], i, data2[i]);
    }

    return 0;
}*/ 

void ordenamiento(int *arreglo, int n) { // Funcion para ordenar un arreglo 
    int i, j, temp; // Variables para el ordenamiento 
    for(i = 0; i < n - 1; i++) { // Bubble sort 
        for(j = 0; j < n - i - 1; j++) { 
            if(arreglo[j] > arreglo[j + 1]) { // Si el elemento actual es mayor al siguiente 
                temp = arreglo[j]; // Intercambiar elementos 
                arreglo[j] = arreglo[j + 1]; 
                arreglo[j + 1] = temp; // Se guarda el valor intercambiado 
            }
        }
    }
}

int busqueda(int *arreglo, int n, int valorBuscado) {
    int encontrado = 0;
    // Se usa reducción para simplificar la búsqueda en paralelo
    #pragma omp parallel for reduction(max:encontrado)
    for (int i = 0; i < n; i++) { // Se recorre el arreglo 
        if(arreglo[i] == valorBuscado)  // Si el valor buscado es encontrado 
            encontrado = 1; // Se marca como encontrado 
    }
    return encontrado; // Retornar el resultado de la búsqueda
}

int main(){
    int arreglo_1[] = {23, 45, 67, 89, 12, 34, 56, 78};
    int arreglo_2[] = {91, 83, 75, 62, 54, 46, 38, 20};
    int valorBuscado = 38; // Definir el valor a buscar
    int encontrado; // Variable para almacenar el resultado de la búsqueda

    #pragma omp parallel sections // Se divide el trabajo entre los hilos 
    {
        #pragma omp section // Se ejecuta en un hilo 
        {
            ordenamiento(arreglo_1, 8); // Se llama a la funcion ordenamiento 
        }
        #pragma omp section // Se ejecuta en otro hilo 
        {
            encontrado = busqueda(arreglo_2, 8, valorBuscado); // Se llama a la funcion busqueda y se guarda el resultado
        }
    }

    // Imprimir el arreglo ordenado
    printf("Arreglo ordenado: ");
    for(int i = 0; i < 8; i++) { // Se recorre el arreglo 
        printf("%d ", arreglo_1[i]); // Se imprimen los elementos del arreglo 
    }
    printf("\n");

    // Imprimir el resultado de la búsqueda 
    if(encontrado) { // Si el valor buscado es encontrado 
        printf("El valor %d se encuentra en el arreglo\n", valorBuscado); // Se imprime el valor buscado 
    } else { // Si el valor buscado no es encontrado 
        printf("El valor %d no se encuentra en el arreglo\n", valorBuscado); // Se imprime el valor buscado 
    }   
    
    return 0;
}