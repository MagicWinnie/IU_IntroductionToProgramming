#include <stdio.h>
void swap(int *ap, int *bp)
{
    int *temp = ap;
    ap = bp;
    bp = temp;
}
int main()
{
    int a = 1, *ap = &a;
    int b = 2, *bp = &b;
    swap(ap, bp);
    printf("%d%d%d%d\n", a, *ap, b, *bp);
    return 0;
}
// 1122