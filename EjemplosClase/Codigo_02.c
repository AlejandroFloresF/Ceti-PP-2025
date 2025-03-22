#include <stdio.h>
#include <omp.h>

int main() {
    int i, n = 100;
    int sum = 0;
    int array[100];

    for(int i = 0; i < 100; i++) {
        array[i] = i + 1;
    }

    #pragma omp parallel for reduction(+:sum)
    for(int i = 0; i < 100; i++) {
        sum += array[i];
    }

    printf("Suma total: %d\n", sum);

    return 0;
}