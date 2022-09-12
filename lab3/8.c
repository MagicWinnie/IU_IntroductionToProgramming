#include <stdio.h>

int main()
{
    int *p;
    int i;
    int k;
    i = 42;
    k = i;
    p = &i;

    *p = 75;
    printf("%d\n", i);
    return 0;
}
// D