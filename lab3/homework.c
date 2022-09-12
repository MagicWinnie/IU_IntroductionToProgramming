#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_SIZE 256

int main()
{
    int n, k = 1, prev_line = 0;
    scanf("%d", &n);
    char line[MAX_SIZE], buffer[10];
    for (int i = 1; i <= n; i++)
    {
        line[0] = '\0';
        buffer[0] = '\0';
        for (int j = 0; j < n - i + 1; j++)
            printf(" ");
        for (int j = 0; j < i; j++)
        {
            sprintf(buffer, "%d", k);
            strcat(line, buffer);
            strcat(line, " ");
            k++;
            if ((int)strlen(line) - prev_line > 1)
            {
                prev_line = strlen(line);
                break;
            }
        }
        printf("%s", line);
        printf("\n");
    }
    return 0;
}