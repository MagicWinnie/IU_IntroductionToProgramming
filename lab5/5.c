#include <stdio.h>
#include <stdlib.h>
#include <string.h>

enum role
{
    STUDENT,
    TA,
    PROFESSOR
};

enum degree
{
    SECONDARY,
    BACHELOR,
    MASTER,
    PHD
};

struct moodle_member
{
    char name[128];
    enum degree dg;
    enum role rl;
};

int main()
{
    int n;
    printf("How many members do you want to enter? ");
    scanf("%d", &n);
    struct moodle_member *members = (struct moodle_member *)malloc(sizeof(struct moodle_member) * n);
    for (int i = 0; i < n; i++)
    {
        char name[128];
        enum role rl;
        enum degree dg;
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
                struct moodle_member tmp = members[i];
                members[i] = members[j];
                members[j] = tmp;
            }
        }
    }
    for (int i = 0; i < n; i++)
        printf("Name: %s, Degree: %d, Role: %d\n", members[i].name, members[i].dg, members[i].rl);
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