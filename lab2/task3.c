#include <stdio.h>

int main()
{
    int n;
    scanf("%d", &n);
    for (int i = 1; i <= n; i++)
    {
        for (int j = 0; j < i; j++)
            printf("*");
        printf("\n");
    }
    scanf("%d", &n);
    for (int i = 1; i <= (n + 1) / 2; i++)
    {
        for (int j = 0; j < i; j++)
            printf("*");
        printf("\n");
    }
    for (int i = (n + 1) / 2; i >= 1; i--)
    {
        for (int j = 0; j < i; j++)
            printf("*");
        printf("\n");
    }
    int h, w;
    scanf("%d %d", &h, &w);
    for (int i = 0; i < h; i++)
    {
        for (int j = 0; j < w; j++)
            printf("*");
        printf("\n");
    }
    return 0;
}