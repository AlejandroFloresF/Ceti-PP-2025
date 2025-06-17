/*
    Práctica 1 - MIMD
    Fernando Zazir Gómez Corona
    22110227
*/

#include <stdio.h>
#include <pthread.h>

int datos[3] = {6, 13, 10};

// Procesos que se ejecutarán para cada hilo
void* cuadrado(void* arg) {
    int idx = *(int*)arg;
    printf("cuadrado: %d^2 = %d\n", datos[idx], datos[idx] * datos[idx]);
    return NULL;
}

void* cubo(void* arg) {
    int idx = *(int*)arg;
    printf("cubo: %d^3 = %d\n", datos[idx], datos[idx] * datos[idx] * datos[idx]);
    return NULL;
}

void* doble(void* arg) {
    int idx = *(int*)arg;
    printf("doble: %d * 2 = %d\n", datos[idx], datos[idx] * 2);
    return NULL;
}

int main() {
    // MIMD: Cada hilo hace una instrucción diferente sobre su propio dato
    pthread_t h1, h2, h3;             // Creamos los hilos para cada proceso
    int idx1 = 0, idx2 = 1, idx3 = 2; // Indice del dato en el arreglo

    // Definimos la ejecución de las instrucciones sobre el dato específico al que referimos
    pthread_create(&h1, NULL, cuadrado, &idx1);  // instrucción 1 sobre datos[0]
    pthread_create(&h2, NULL, cubo, &idx2);      // instrucción 2 sobre datos[1]
    pthread_create(&h3, NULL, doble, &idx3);     // instrucción 3 sobre datos[2]

    pthread_join(h1, NULL);
    pthread_join(h2, NULL);
    pthread_join(h3, NULL);

    return 0;
}