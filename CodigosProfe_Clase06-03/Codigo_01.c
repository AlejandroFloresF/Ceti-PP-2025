

// Single Instruction, Single Data (SISD)

#include <stdio.h>

int main()
{

    int array[100];
    int sum = 0;

    for (int i = 0; i < 100; i++)
    {
        array[i] = i;
    }

    for (int i = 0; i < 100; i++)
    {
        sum += array[i];
    }

    printf("Suma total = %d\n", sum);

    return 0;
}