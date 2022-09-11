#include <stdio.h>

int main()
{
    char str[50];
    scanf("%s", str);

    int size = 0;
    while (str[size] != '\0') size++;
    
    for (int i = size - 1; i >= 0; i--) printf("%c", str[i]);
    printf("\n");
    
    return 0;
}