/*22110223*/
/*multiple instruction, multiple data*/

#include <stdio.h>
#include <omp.h>
#include <math.h>

/// @brief funcion tangente
/// @param x 
/// @return tangente de x
double funcion(double x, int x_n){
    return pow(x, x_n); //f(x) = x^n
}

//prototipos
double trapecios(unsigned long, double, double, int);
double simpson(unsigned long, double, double, int);

/// @brief Funcion main
/// @return 0
int main(){
    double a = 0.0, b = 0.0; //limites
    int p = 0, x_n = 0; //potencia de intervalos y de x en la funcion
    unsigned long n = 10; //intervalos
    double resT = 0.0, resS = 0.0; //resultados
    double t_i, t_f; //tiempos

    printf("Ingrese el valor del limite inferior: ");
    scanf("%lf", &a); //recibe a
    printf("Ingrese el valor del limite superior: ");
    scanf("%lf", &b); //recibe b
    printf("Ingrese la potencia de 10 de los intervalos: ");
    scanf("%d", &p); //potencia de intervalos
    n = pow(n,p);
    printf("Ingrese n de la funcion x^n: ");
    scanf("%d", &x_n); //potencia de la x en la funcion

    t_i = omp_get_wtime();
    //se ejecutan los 2 metodos en paralelo
    #pragma omp parallel sections
    {
        #pragma omp section //seccion trapecios
        {
            double inicio = omp_get_wtime();
            resT = trapecios(n, a, b, x_n);
            double fin = omp_get_wtime();
            printf("calculo con trapecios completado en %.6f segundos\n", fin-inicio);
        }

        #pragma omp section //seccion simpson
        {
            double inicio = omp_get_wtime();
            resS = simpson(n, a, b, x_n);
            double fin = omp_get_wtime();
            printf("calculo con regla de Simpson completado en %.6f segundos\n", fin-inicio);
        }
    }
    t_f = omp_get_wtime();

    //mostrar resultados
    printf("Se obtuvo el resultado de %f con trapecios \n", resT);
    printf("Se obtuvo el resultado de %f con regla de Simpson \n", resS);
    printf("El tiempo total fue de: %.3f segundos \n", t_f-t_i);

    return 0;
}

/// @brief calcula una integral con trapecios (paralelizada)
/// @param n iteraciones
/// @param a limite inferior
/// @param b limite superior
/// @param x_n potencia de la funcion
/// @return resultado de la integracion
double trapecios(unsigned long n, double a, double b, int x_n) {
    double h = (b - a) / n;
    double suma = 0.0;

    #pragma omp parallel for reduction(+:suma)
    for (int i = 1; i < n; i++) {
        suma += funcion(a + i * h, x_n); //suma parelizada
    }

    suma += (funcion(a, x_n) + funcion(b, x_n)) / 2.0;
    return h * suma;
}

/// @brief calcula una integral con el metodo de simpson (paralelizada)
/// @param n iteraciones
/// @param a limite inferior
/// @param b limite superior
/// @param x_n potencia de la funcion
/// @return resultado de la integracion
double simpson(unsigned long n, double a, double b, int x_n) {
    if (n % 2 != 0) n++; // Simpson requiere n par
    double h = (b - a) / n;
    double suma = funcion(a, x_n) + funcion(b, x_n);

    #pragma omp parallel for reduction(+:suma)
    for (int i = 1; i < n; i++) {
        if (i % 2 == 0) {
            suma += 2 * funcion(a + i * h, x_n); //suma paralelizada
        } else {
            suma += 4 * funcion(a + i * h, x_n); //suma paralelizada
        }
    }

    return h * suma / 3.0;
}