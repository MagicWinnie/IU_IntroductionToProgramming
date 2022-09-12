#include <stdio.h>
#define MAX_SIZE 128

int main()
{
    char str[MAX_SIZE], new_str[MAX_SIZE];
    gets(str);
    
    char *ptr = str;
    char *new_ptr = new_str;
    while (*ptr != '\0')
    {
        *new_ptr = *ptr;
        new_ptr++;
        ptr++;
    }
    printf("%s %x\n", str, &str);
    printf("%s %x\n", new_str, &new_str);

    return 0;
}