#include <stdio.h>
#include <string.h>
int main()
{
    char buf1[100] = "Hello";
    char buf2[100] = "World";
    char *ptr1 = buf1 + 2;
    char *ptr2 = buf2 + 3;
    strcpy(ptr1, buf2);
    strcpy(ptr2, buf1);
    printf("%s\n", buf1);
    printf("%s\n", ptr1);
    printf("%s\n", buf2);
    printf("%s\n", ptr2);
    return 0;
}
// HeWorld
// World
// WorHeWorld
// HeWorld