/*Single Instruction, Multiple Data*/

#include <stdio.h>
#include <omp.h>

int main () {
    int luces[5];

    //pense en un local con 5 luces en total
    for(int i = 0; i < 5; i++) {
        luces[i] = 1;
    }
    //esto hara que en lugar de secuencial , se van a realizar
    //todas al mismo tiempo
    #pragma omp parallel for 
    for(int i = 0; i < 5; i++) {
        luces[i]=0;
    }
    //comprobamos el estado de cada una de las luces
    for (int i = 0; i < 5; i++) {
        if (luces[i] == 0) {
            printf("Luz %d: Apagada\n", i + 1);
        } else {
            printf("Luz %d: Encendida\n", i + 1);
        }
    }  
    return 0;
}