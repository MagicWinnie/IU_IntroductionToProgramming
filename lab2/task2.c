#include <stdio.h>

int main()
{
    int n;
    scanf("%d", &n);
    int width = 2 * n - 1;
    for (int i = 1; i <= n; i++)
    {
        int curr_width = 2 * i - 1;
        for (int j = 0; j < (width - curr_width) / 2; j++)
            printf(" ");
        for (int j = 0; j < curr_width; j++)
            printf("*");
        printf("\n");
    }
    return 0;
}