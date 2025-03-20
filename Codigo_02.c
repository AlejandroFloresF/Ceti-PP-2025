//Single Instruction, Multiple Data
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

int main(){
    srand(time(NULL)); //genera una semilla aleatoria de acuerdo al tiempo del computador, para no tener la misma secuencia de numero aleatorios en cada ejecucion
    int n = 200; //tamaño del arreglo de calificaciones
    int suma = 0; //almacena la suma de todas las calificaciones.
    float promedio; //guarda el promedio de las calificaciones
    int calificaciones[n]; //arreglo de calificaciones

    for(int i = 0; i < n; i++){//se llena el arreglo con numeros aleatorios del 1 al 100
        calificaciones[i] = 1 + rand()%100; //se le da un numero aleatorioa del 1 al 100 a dicho elemento del arreglo
        printf("Calificacion %i: %d\n", i, calificaciones[i]); //se imprime en pantalla la calificacion que se le dio
    }
    //El parallel for reduction no es SIMD, sin importar que multiples hilos esten ejecutando la misma instruccion
    //ya que como cada hilo esta ejecutando dicha instruccion de manera independiente, existe un multiple flujo
    //de instrucciones, aunque sean identicas, ademas de cada hilo opera sobre su propio conjunto de datos,
    //por lo que parallel for reduction es en realidad MIMD.

    //La directiva de abajo nos da el SIMD. El SIMD es un solo flujo de instrucciones, ejecutado por un solo hilo,
    //que opera sobre varios datos paralalemente. Esto es gracias a las tecnicas de vectorizacion de los procesadores
    //modernos, que consisten en tener en el procesador registros anchos que se llaman vectores, cuales pueden
    //guardar multiples datos a la vez. Con estos vectores se podrian hacer cuatro sumas en una sola instruccion y
    //guardar los resultados en otro vector para después pasarlos al arreglo de nuestro programa destinado a almacenarlos
    //que reside en la memoria principal. Esto requiere menos instrucciones que hacer cada suma de forma secuencial,
    //almacenar los resultados en un registro del procesador a parte y despues pasarlos a la posicion de nuestro
    //arreglo que esta en la memoria principal, necesitando una instruccion por cada uno de estos pasos.

    #pragma omp simd //Indica al compilador que intente vectorizar el bucle de abajo
    for(int i = 0; i < n; i++){ //Se suman todas las calificaciones del arreglo
        suma += calificaciones[i];
    }

    promedio = suma/n; //se divide entre el numero total para obtener el promedio
    printf("La suma es %d\n", suma); //se imprimen los resultados en pantalla
    printf("El promedio del grupo es: %.2f\n", promedio);
    return 0;
}