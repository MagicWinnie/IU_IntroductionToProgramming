#include <stdio.h>
#include <stdlib.h>
#define MAX_VALUE 1001

int main()
{
    int n;
    scanf("%d", &n);
    int *arr = (int *)calloc(n, sizeof(int));
    int *freq = (int *)calloc(MAX_VALUE, sizeof(int));
    for (int i = 0; i < n; i++)
        scanf("%d", &arr[i]);
    for (int i = 0; i < n; i++)
    {
        if (!freq[arr[i]])
        {
            printf("%d ", arr[i]);
            freq[arr[i]]++;
        }
    }
    printf("\n");
    free(arr);
    return 0;
}