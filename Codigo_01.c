/*Single Instruction, single data*/

#include <stdio.h>

/*int main(){
    int n = 100;
    int sum = 0;
    int array[100];

    for (int i = 0; i < n; i++){
        array[i] = i + 1;
    }

    for (int i = 0; i < n; i++){
        sum += array[i];
    }

    printf("Suma total = %d\n", sum);

    return 0;
}*/

// Funcion para calcular fibonacci 
void fibonacci(int n){
    unsigned long long fibo[n]; // Array para almacenar los numeros de fibonacci

    if (n <= 0) { // Si n es 0 o menor, no hay numeros de fibonacci
        printf("No hay numeros de fibonacci para %d\n", n); 
        return;
    }

    fibo[0] = 0; // Primer numero de fibonacci
    fibo[1] = 1; // Segundo numero de fibonacci

    for (int i = 2; i < n; i++){  // Calculo de los siguientes numeros de fibonacci
        fibo[i] = fibo[i - 1] + fibo[i - 2]; // Suma de los dos anteriores y guardo el resultado en cada iteracion 
    }

    printf("Los primeros %d numeros de fibonacci son: ", n); // Imprime los primeros n numeros de fibonacci 
    for (int i = 0; i < n; i++){
        printf("%llu ", fibo[i]); // Imprime cada numero de fibonacci 
    }
    
    printf("\n");
}

int main(){

    // Prueba de la funcion fibonacci con diferentes valores de n usando SISD (Single Instruction, Single Data)
    fibonacci(0); // 0
    fibonacci(2); // 0 1
    fibonacci(5); // 0 1 1 2 3
    fibonacci(10); // 0 1 1 2 3 5 8 13 21 34
    fibonacci(50); // 0 1 1 2 3 5 8 13 21 34 ...
    
    return 0;
}