//Single Instruction, Single Data

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

//Programa que obtendra calificaciones aleatorias y sacara su promedio.
int main(){
    srand(time(NULL)); //genera una semilla aleatoria de acuerdo al tiempo del computador
    int n = 200; //tamaño del arreglo de calificaciones
    int suma = 0; //almacena la suma de todas las calificaciones.
    float promedio; //guarda el promedio de las calificaciones
    int calificaciones[n]; //arreglo de calificaciones

    for(int i = 0; i < n; i++){ //se llena el arreglo con numeros aleatorios del 1 al 100
        calificaciones[i] = 1 + rand()%100; //se le da un numero aleatorioa del 1 al 100 a dicho elemento del arreglo
        printf("Calificacion %i: %d\n", i, calificaciones[i]); //se imprime en pantalla la calificacion que se le dio
    }

    for(int i = 0; i < n; i++){ //se suman todas las calificaciones
        suma = suma + calificaciones[i];
    }

    promedio = suma/n; //se divide entre el numero total para obtener el promedio
    printf("La suma es %d\n", suma); //se imprimen los resultados en pantalla
    printf("El promedio del grupo es: %.2f\n", promedio);
    return 0;
}