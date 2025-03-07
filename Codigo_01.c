/*Single Instruction, Single Data*/

#include <stdio.h>

int main () {
    int sum = 0;
    int array[100];

    for(int i = 0; i < 100; i++) {
        array[i] = i + 1;
    }

    for(int i = 0; i < 100; i++) {
        sum += array[i];
    }

    printf("Suma total: %d\n", sum);

    return 0; 
}