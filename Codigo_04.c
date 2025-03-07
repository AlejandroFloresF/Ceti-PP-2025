#include <stdio.h>
#include <omp.h>
//Multiple Instruction, Multiple Data
void task1(int *data, int n) {
    for (int i = 0; i < n; i++) {
        data[i] += 1;
    }
}

void task2(int *data, int n) {
    for (int i = 0; i < n; i++) {
        data[i] *= 2;
    }
}

int main() {
    int n = 100;
    int data1[100], data2[100];

    for (int i = 0; i < n; i++) {
        data1[i] = i + 1;
        data2[i] = i + 1;
    }

    #pragma omp parallel sections
    {
        #pragma omp section
        task1(data1, n);
        
        #pragma omp section
        task2(data2, n);
    }

    printf("Resultados de data1:\n");
    for (int i = 0; i < n; i++) {
        printf("%d ", data1[i]);
    }
    printf("\n");

    printf("Resultados de data2:\n");
    for (int i = 0; i < n; i++) {
        printf("%d ", data2[i]);
    }
    printf("\n");

    return 0;
}