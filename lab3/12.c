#include <stdio.h>
#define N 3
#define M 4

int main()
{
    int matrix[N][M];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            scanf("%d", *(matrix + i) + j);
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < M; j++)
            printf("%d ", *(*(matrix + i) + j));
        printf("\n");
    }
    return 0;
}