#include <stdio.h>

struct fields
{
    unsigned version:4;
    unsigned IHL:4;
    unsigned DSCP:6;
    unsigned ECN:2;
    unsigned total_length:16; 
};

union IP
{
    struct fields field;
    unsigned access;
};

int main()
{
    union IP ip;

    printf("Enter header: ");
    scanf("%d", &ip.access);

    printf("Version: %d\n", ip.field.version);
    printf("IHL: %d\n", ip.field.IHL);
    printf("DSCP: %d\n", ip.field.DSCP);
    printf("ECN: %d\n", ip.field.ECN);
    printf("Total length: %d\n", ip.field.total_length);
    return 0;
}