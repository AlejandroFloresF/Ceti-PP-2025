/*Single instruction, multiple data*/

#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <omp.h>

/*int main(){ 

    int i, n = 100;
    int sum = 0;
    int array[100];

    for (i = 0; i < n; i++){
        array[i] = i + 1; 
    }

    #pragma omp parallel for reduction(+:sum) // Lo que hace es que cada hilo tiene su propia copia de la variable sum, y al final de la ejecución de cada hilo, se suman los valores
    for (i = 0; i < n; i++){
        sum += array[i];
    }

    printf("Suma total SIMD = %d\n", sum);
    
    return 0;
}*/ 


void formula_cuadratica(float *A, float *B, float *C, int cantidadEcuaciones) { // Funcion para calcular las raices de una ecuacion cuadratica 
    float raiz1Real[cantidadEcuaciones], raiz1Imag[cantidadEcuaciones]; // Array para almacenar las raices reales y imaginarias
    float raiz2Real[cantidadEcuaciones], raiz2Imag[cantidadEcuaciones]; // Array para almacenar las raices reales y imaginarias
    int esCuadraticaValida[cantidadEcuaciones]; // Array para almacenar si la ecuación es válida

    #pragma omp parallel for simd // Se divide el trabajo entre los hilos 
    for (int i = 0; i < cantidadEcuaciones; i++) { // Se ejecuta en un hilo 
        if (A[i] == 0) { // Si A es 0, no es una ecuación cuadrática válida
            esCuadraticaValida[i] = 0; // Se marca como inválida
        } else { // Si A no es 0, es una ecuación cuadrática válida
            esCuadraticaValida[i] = 1; // Se marca como válida
            float denominadorComun = 2 * A[i];  // Denominador común
            float valorDiscriminante = B[i] * B[i] - 4 * A[i] * C[i]; // Calculo del valor del discriminante
            float parteRealSolucion = -B[i] / denominadorComun; // Calculo de la parte real de la solucion
            if (valorDiscriminante >= 0) { // Si el valor del discriminante es 0 o positivo, hay 2 soluciones reales
                float valorRaizDiscriminante = sqrt(valorDiscriminante); // Calculo del valor de la raiz
                raiz1Real[i] = parteRealSolucion + valorRaizDiscriminante / denominadorComun; // Calculo de la primera raiz
                raiz2Real[i] = parteRealSolucion - valorRaizDiscriminante / denominadorComun; // Calculo de la segunda raiz
                raiz1Imag[i] = 0; // La primera raiz es real
                raiz2Imag[i] = 0; // La segunda raiz es real
            } else { // Si el valor del discriminante es negativo, hay 2 soluciones imaginarias
                float valorRaizDiscriminante = sqrt(-valorDiscriminante); // Calculo del valor de la raiz imaginaria
                raiz1Real[i] = parteRealSolucion; // Calculo de la primera raiz
                raiz2Real[i] = parteRealSolucion; // Calculo de la segunda raiz
                raiz1Imag[i] = valorRaizDiscriminante / denominadorComun; // Calculo de la primera raiz imaginaria
                raiz2Imag[i] = -valorRaizDiscriminante / denominadorComun; // Calculo de la segunda raiz imaginaria
            }
        }
    }

    // Impresión de resultados (se realiza de forma serial)
    printf("Resultados:\n");
    for (int i = 0; i < cantidadEcuaciones; i++) { // Se recorre el array de ecuaciones mostrando los resultados
        printf("\nEcuación %d: %.2f x² + %.2f x + %.2f = 0\n", i + 1, A[i], B[i], C[i]); // Se muestra la ecuación
        if (esCuadraticaValida[i] == 0) { // Si A es 0, no es una ecuación cuadrática válida    
            printf("No es una ecuación cuadrática válida\n"); 
        } else { // Si A no es 0, es una ecuación cuadrática válida
            if (raiz1Imag[i] == 0 && raiz2Imag[i] == 0) { // Si las raices son reales 
                if (fabs(raiz1Real[i] - raiz2Real[i]) < 0.0001) // Si las raices son iguales significa que es una raiz doble
                    printf("Solución (raíz doble): x = %.4f\n", raiz1Real[i]);
                else // Si las raices son diferentes son 2 soluciones
                    printf("Soluciones: x1 = %.4f, x2 = %.4f\n", raiz1Real[i], raiz2Real[i]);
            } else { // Si las raices son imaginarias 
                printf("Soluciones: x1 = %.4f + %.4fi, x2 = %.4f - %.4fi\n", // Imprime las 2 soluciones
                       raiz1Real[i], raiz1Imag[i], raiz2Real[i], fabs(raiz2Imag[i])); // fabs es para obtener el valor absoluto
            }
        }
    }
}


int main() {
    float a[] = {1, 2, 3, 1, 5, 6, 7, 8, 0, 2}; // Coeficientes de x²
    float b[] = {-3, -1, 6, 2, 10, -6, 8, -12, 3, -4}; // Coeficientes de x
    float c[] = {2, -1, 2, -3, 5, 8, -2, 9, 4, 2}; // Coeficientes de 1
    int numEcuaciones = sizeof(a) / sizeof(a[0]); // Cantidad de ecuaciones

    formula_cuadratica(a, b, c, numEcuaciones); // Se llama a la funcion para calcular las raices de las ecuaciones usando SIMD
         
    return 0;
}