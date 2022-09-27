#include <stdio.h>
#define MAX_LEN 128

struct student
{
    char name[MAX_LEN], surname[MAX_LEN];
    int group_number;
    struct {int day, year; char month[MAX_LEN];} exam;
};

int main()
{
    struct student s;
    printf("Enter day (int) month (str) and year (int) of exam: ");
    scanf("%d %s %d", &s.exam.day, s.exam.month, &s.exam.year);
    printf("Enter name (str) and surname (str) of student: ");
    scanf("%s %s", s.name, s.surname);
    printf("Enter group number (int): ");
    scanf("%d", &s.group_number);

    printf("Exam date: %d %s %d\n", s.exam.day, s.exam.month, s.exam.year);
    printf("Name student: %s %s\n", s.name, s.surname);
    printf("Group number: %d\n", s.group_number);

    return 0;
}