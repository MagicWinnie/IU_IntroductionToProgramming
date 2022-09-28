#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef enum
{
    STUDENT,
    TA,
    PROFESSOR
} role;

typedef enum
{
    SECONDARY,
    BACHELOR,
    MASTER,
    PHD
} degree;

typedef struct
{
    char name[128];
    degree dg;
    role rl;
} moodle_member;

int main()
{
    int n;
    printf("How many members do you want to enter? ");
    scanf("%d", &n);
    moodle_member *members = (moodle_member *)malloc(sizeof(moodle_member) * n);
    for (int i = 0; i < n; i++)
    {
        char name[128];
        role rl;
        degree dg;
        printf("Enter name: ");
        scanf("%s", name);
        printf("Enter degree: ");
        scanf("%d", &dg);
        printf("Enter role: ");
        scanf("%d", &rl);
        strcpy(members[i].name, name);
        members[i].dg = dg;
        members[i].rl = rl;
    }
    for (int i = 0; i < n; i++)
    {
        for (int j = i + 1; j < n; j++)
        {
            if (members[i].rl > members[j].rl || (members[i].rl == members[j].rl && members[i].dg > members[j].dg) || (members[i].rl == members[j].rl && members[i].dg == members[j].dg && strcmp(members[i].name, members[j].name) > 0))
            {
                moodle_member tmp = members[i];
                members[i] = members[j];
                members[j] = tmp;
            }
        }
    }
    for (int i = 0; i < n; i++)
        printf("Name: %s\tDegree: %d\tRole: %d\n", members[i].name, members[i].dg, members[i].rl);
    free(members);
    return 0;
}

/*
6
Semen
2
0
Alexander
2
0
Dmitriy
1
2
Alena
0
3
Viktoria
0
1
Ivan
0
2
*/