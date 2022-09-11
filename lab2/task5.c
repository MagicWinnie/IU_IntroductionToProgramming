#include <stdio.h>
#include <stdlib.h>

int main()
{
    FILE *file = fopen("output.txt", "w");
    char str[100];
    scanf("%[^\n]", str);
    fprintf(file, "%s", str);
    fclose(file);
    return 0;
}