#include <stdio.h>

enum WEEK
{
    MONDAY = 1,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
};

int main()
{
    enum WEEK wk;
    printf("Enter week number: ");
    scanf("%d", &wk);
    switch (wk)
    {
    case MONDAY:
        printf("1 is Monday\n");
        break;
    case TUESDAY:
        printf("2 is Tuesday\n");
        break;
    case WEDNESDAY:
        printf("3 is Wednesday\n");
        break;
    case THURSDAY:
        printf("4 is Thursday\n");
        break;
    case FRIDAY:
        printf("5 is Friday\n");
        break;
    case SATURDAY:
        printf("6 is Saturday\n");
        break;
    case SUNDAY:
        printf("7 is Sunday\n");
        break;
    default:
        printf("Not a week day\n");
        break;
    }

    return 0;
}