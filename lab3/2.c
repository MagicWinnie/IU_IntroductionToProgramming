#include <stdio.h>
#include <stdlib.h>

int main()
{
    int n;
    scanf("%d", &n);
    int *arr = (int *)calloc(n, sizeof(int));
    int *visited = (int *)calloc(n, sizeof(int));
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &arr[i]);
        visited[i] = -1;
    }
    for (int i = 0; i < n; i++)
    {
        int have_seen = 0;
        for (int j = 0; j < n; j++)
            if (arr[i] == visited[j])
            {
                have_seen = 1;
                break;
            }
        if (!have_seen)
        {
            printf("%d ", arr[i]);
            visited[i] = arr[i];
        }
    }
    printf("\n");
    free(arr);
    free(visited);
    return 0;
}