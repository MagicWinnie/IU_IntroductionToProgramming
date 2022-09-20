#include <stdio.h>

void func()
{
    static int x = 5;
    int y = 5;
    while (y < 10 && x < 10)
    {
        printf("x = %d, y = %d\n", x, y);
        x++;
        y++;
        func();
    }
}

int main()
{
    func();
}

/*
x = 5, y = 5
x = 6, y = 5
x = 7, y = 5
x = 8, y = 5
x = 9, y = 5
*/