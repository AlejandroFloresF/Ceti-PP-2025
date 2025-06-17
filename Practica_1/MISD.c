/*
    Práctica 1 - MISD
    Fernando Zazir Gómez Corona
    22110227
*/

#include <stdio.h>
#include <pthread.h>

int dato = 10; // El mismo dato para diferentes procesos

// Procesos que se ejecutarán para cada hilo
void* tarea1(void* arg) {
    printf("Tarea 1: dato + 2 = %d\n", dato + 2);   // suma
    return NULL;
}

void* tarea2(void* arg) {
    printf("Tarea 2: dato * 3 = %d\n", dato * 3);   // multiplicación
    return NULL;
}

void* tarea3(void* arg) {
    printf("Tarea 3: dato ^ 2 = %d\n", dato * dato); // cuadrado
    return NULL;
}

int main() {
    // MISD: Varios hilos hacen cosas distintas con el mismo dato
    pthread_t h1, h2, h3;                     // Primero creamos hilos para cada proceso

    pthread_create(&h1, NULL, tarea1, NULL);  // Después asigamos un proceso
    pthread_create(&h2, NULL, tarea2, NULL);  // es decir, cada hilo hace algo distinto con el mismo dato
    pthread_create(&h3, NULL, tarea3, NULL);

    pthread_join(h1, NULL);                   // Finalmente devolvemos el hilo,
    pthread_join(h2, NULL);                   // de esta manera liberamos memoria y nos  
    pthread_join(h3, NULL);                   // aseguramos de haber ejecutado correctamente los hilos

    return 0;
}