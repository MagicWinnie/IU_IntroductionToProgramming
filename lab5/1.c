#include <stdio.h>

struct Birthday
{
    unsigned short day:5;
    unsigned short month:4;
    unsigned short year:7;
};

int main()
{
    struct Birthday bd = {.day = 10, .month = 8, .year = 2004 - 1912};
    printf("Birth date: %d %d %d\n", bd.day, bd.month, 1912 + bd.year);
    printf("Size: %d\n", sizeof(bd));

    return 0;
}