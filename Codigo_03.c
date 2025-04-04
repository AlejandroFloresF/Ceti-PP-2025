/*Multiple Instruction, Single Data*/

#include <stdio.h>
#include <omp.h>

void verificar_peso (int *juegos, int n) {
    for(int i = 0; i < n; i++) {
        if (juegos[i]>80){
            printf("Juego %d: Es un juego pesado.\n", i + 1);
        }else{
            printf("Juego %d: Es un juego ligero.\n", i + 1);
        }
    }
}

void verificar_espacio (int *juegos, int n, int capacidad_disponible) {
    for(int i = 0; i < n; i++) {
        if (juegos[i] <= capacidad_disponible) {
            printf("Juego %d: Cabe en el disco duro.\n", i + 1);
        } else {
            printf("Juego %d: No cabe en el disco duro.\n", i + 1);
        }
    }
}


int main () {

    int n = 5; //la cantidad de juegos a escanear
    int juegos[5]={120,40,69,71,200};
    int capacidad_disponible= 80; //fijamos el espacio libre del dispositivo

//borramos el for que llenaba el anterior arreglo llamado data ya que lo llenamos anteriormente con pesos fijos.

    #pragma omp parallel sections 
    {
        #pragma omp section
        verificar_peso(juegos, n); // Comprobar si los juegos son pesados

        #pragma omp section 
        verificar_espacio(juegos, n, capacidad_disponible);//comprobamos si tenemos la memoria suficiente
    }

    return 0;
}