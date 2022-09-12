#include <stdio.h>
int main()
{
    int array[] = {10, 20, 30};
    int *pointer = array;
    printf("%d\n", *pointer);
    printf("%p\n", pointer);
    printf("%d\n", *array);
    printf("%p\n", array);
    printf("%d\n", ++*pointer);
    printf("%d\n", *++pointer);
    int *pointer1 = array;
    int *pointer2 = array;
    printf("%d\n", *pointer1++ + ++*++pointer2);
    return 0;
}
// 10
// 0000000A067FFBBC
// 10
// 0000000A067FFBBC
// 11
// 20
// 32