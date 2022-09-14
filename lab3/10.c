#include <stdio.h>
#define MAX_SIZE 128

int main()
{
    char str[MAX_SIZE];
    fgets(str, MAX_SIZE, stdin);

    char *ptr = str;
    while (*ptr != '\0')
        ptr++;
    int size = ptr - str;
    printf("%d\n", size);
    return 0;
}