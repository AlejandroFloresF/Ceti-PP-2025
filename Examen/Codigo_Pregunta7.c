// Pregunta 7 - Suma de arreglos con hilos

#include <stdio.h>
#include <pthread.h>

pthread_mutex_t mutex; // Mutex para proteger la variable global suma
int arreglo[10] = {11, 22, 33, 44, 55, 66, 77, 88, 99, 100}; // Arreglo de enteros
int suma = 0; // Variable global para la suma

typedef struct { // Se define una estructura para pasar argumentos a los hilos y para que no se sobreescriba la variable
    int inicio;
    int fin;
} Rango;

void *sumaTotal(void *arg) {
    Rango *rangos = (Rango *)arg; // Convierte el argumento a tipo Rango
    int sumaAux = 0;            

    for (int i = rangos->inicio; i < rangos->fin; i++) { // Recorre el rango asignado al hilo
        sumaAux += arreglo[i];                           // Suma los elementos del arreglo en el rango
    }

    pthread_mutex_lock(&mutex);  // Bloquea el mutex antes de acceder a la variable global
    suma += sumaAux;             // Suma el resultado parcial a la variable global
    pthread_mutex_unlock(&mutex);// Desbloquea el mutex después de acceder a la variable global

    return NULL;
}

int main() {
    pthread_t threads[4]; // Arreglo de hilos
    Rango rangos[4];      // Arreglo de rangos para cada hilo

    pthread_mutex_init(&mutex, NULL); // Inicializa el mutex

    for (int i = 0; i < 4; i++) { // Crea los hilos
        rangos[i].inicio = i * 2.5;     // Asigna el inicio del rango
        rangos[i].fin = (i == 3) ? 10 : (i + 1) * 2.5; // Asigna el fin del rango
        pthread_create(&threads[i], NULL, sumaTotal, &rangos[i]);// Crea el hilo
    }

    for (int i = 0; i < 4; i++) { // Espera a que los hilos terminen
        pthread_join(threads[i], NULL);
    }

    printf("La suma de los numeros es: %d\n", suma); 
    pthread_mutex_destroy(&mutex); // Destruye el mutex
    
    return 0;
}
