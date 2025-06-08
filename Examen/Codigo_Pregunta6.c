// Pregunta 6 - mutex

#include <stdio.h>
#include <pthread.h>

int contador = 0; // Variable global 
pthread_mutex_t mutex; // Mutex para proteger el acceso a la variable global

void *sumaContadores(void *arg) {
    int i;
    for (i = 0; i < 100000; i++) {
        pthread_mutex_lock(&mutex); // Bloquea el mutex antes de acceder a la variable global
            contador++; // Incrementa el contador
        pthread_mutex_unlock(&mutex); // Desbloquea el mutex después de acceder a la variable global
    }
    return NULL;
}

int main() {
    pthread_t threads[4]; // Arreglo de hilos
     (&mutex, NULL); // Inicializa el mutex

    int i;
    for (i = 0; i < 4; i++) {
        pthread_create(&threads[i], NULL, sumaContadores, NULL); // Crea los hilos se define la función a ejecutar
    }

    for (i = 0; i < 4; i++) {
        pthread_join(threads[i], NULL);// Espera a que los hilos terminen 
    }

    printf("El valor final del contador es: %d\n", contador); // Imprime el valor final del contador
    pthread_mutex_destroy(&mutex); // Destruye el mutex

    return 0; // Fin del programa
}