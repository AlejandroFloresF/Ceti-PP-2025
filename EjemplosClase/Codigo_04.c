
#include <stdio.h>
#include <omp.h>

/*void func1(intdata, int n) {
    for (int i = 0; i < n; i++) {
        data1[i] += 1;
        printf("Soy Func1: %d\n", data[i]);
    }
}

void func2(int data, int n) {
    for (int i = 0; i < n; i++) {
        data2[i]= 2;
        printf("Soy Func2: %d\n", data[i]);
    }
}

int main() {
    int n = 100;
    int data1[100], data2[100];

    for (int i = 0; i < n; i++) {
        data1[i] = data2[i] = i + 1;
    }

    #pragma omp parallel sections
    {
        #pragma omp section
        func1(data1, n);

        #pragma omp section
        func2(data2, n);
    }

    printf("\nResultados finales:\n");
    for (int i = 0; i < n; i++) {
        printf("data1[%d] = %d, data2[%d] = %d\n", i, data1[i], i, data2[i]);
    }

    return 0;
}*/