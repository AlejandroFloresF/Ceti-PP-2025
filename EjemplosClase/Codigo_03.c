#include <stdio.h>
#include <omp.h>

void func1(int *data, int n) {
    for(int i = 0; i < n; i++) {
        data[i]  += 1;
        printf("Soy Func1: %d", data[i]);
        printf("\n");
    }

}

void func2(int *data, int n) {
    for(int i = 0; i < n; i++) {
        data[i]  *= 2;
        printf("Soy Func2: %d", data[i]);
        printf("\n");
    }
}   

int main() {
    int n = 100;
    int data[100];

    for(int i = 0; i < 100; i++) {
        data[i] = i + 1;
    }

    #pragma omp parallel sections 
    {
        #pragma omp section
        func1(data, n);

        #pragma omp section
        func2(data, n);
    }

    for (int i = 0; i < n; i++) {
        printf("Soy el hilo: %d y calculo %d", i, data[i]);
        printf("\n");
    }

    return 0;
}