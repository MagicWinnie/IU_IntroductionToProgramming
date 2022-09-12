#include <stdio.h>
#include <stdlib.h>

int factorial(int n);

int main()
{
    int start, end;
    printf("Starting range of number: ");
    scanf("%d", &start);
    printf("Ending range of number: ");
    scanf("%d", &end);
    printf("The strong numbers are: ");
    for (int i = start; i <= end; i++)
    {
        int num = i, sum = 0;
        while (num > 0)
        {
            sum += factorial(num % 10);
            num /= 10;
        }
        if (sum == i)
            printf("%d ", i);
    }
    printf("\n");

    return 0;
}

int factorial(int n)
{
    int res = 1;
    for (int i = 1; i <= n; i++)
        res *= i;
    return res;
}