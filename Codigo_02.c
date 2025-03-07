/*Single Instruction, Multiple Data*/

#include <stdio.h>
#include <omp.h>

int main () {
    int i, n = 100;
    int sum = 0;
    int array[100];

    for(i = 0; i < n; i++) {
        array[i] = i + 1;
    }

    #pragma omp parallel for reduction(+:sum) 
    for(i = 0; i < n; i++) {
        sum += array[i]; 
    }

    printf("Suma total SIMD: %d\n", sum);
    return 0;
}



