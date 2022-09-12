#include <stdio.h>
int main()
{
    int a = 1, *ap = &a;
    int b = 2, *bp = &b;
    int *temp = ap;
    ap = bp;
    bp = temp;
    printf("%d%d%d%d\n", a, *ap, b, *bp);
    return 0;
}
// 1221