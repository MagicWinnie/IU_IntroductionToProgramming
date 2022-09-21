#include <stdio.h>

typedef union number
{
    unsigned long long n;
    unsigned char str[8];
} Number;

void encrypt(Number *n)
{
    for (int i = 7; i >= 0; i -= 2)
    {
        unsigned char tmp = n->str[i - 1];
        n->str[i - 1] = n->str[i];
        n->str[i] = tmp;
    }
}

void print(Number n)
{
    for (int i = 7; i >= 0; i--)
        printf("%d", n.str[i]);
    printf("\n");
}

int main()
{
    Number n;
    scanf("%llu", &n.n);
    printf("Original message: %llu >>> ", n.n); print(n);
    encrypt(&n);
    printf("Encrypted message: %llu >>> ", n.n); print(n);
    encrypt(&n);
    printf("Decrypted message: %llu >>> ", n.n); print(n);
    return 0;
}