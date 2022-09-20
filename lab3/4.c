#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#define MAX_SIZE 128
#define HIST_TYPE 0 // 0 -- vertical; 1 -- horizontal
#define ONLY_ALPHA 1 // 0 -- all chars; 1 -- only alpha

int count[128] = {0};
int cmp(const void *a, const void *b);

int main()
{
    char str[MAX_SIZE];
    fgets(str, MAX_SIZE, stdin);
    int n = strlen(str);
    for (int i = 0; i < n; i++)
    {
        if ((ONLY_ALPHA && isalpha(str[i])) || !ONLY_ALPHA)
            count[(int)str[i]]++;
    }
    char unique[MAX_SIZE];
    for (int i = 0; i < n; i++)
    {
        char tmp[2] = "\0";
        tmp[0] = str[i];
        if (!strstr(unique, tmp) && ((ONLY_ALPHA && isalpha(str[i])) || !ONLY_ALPHA))
            strcat(unique, tmp);
    }
    qsort(unique, strlen(unique), sizeof(char), cmp);
    if (HIST_TYPE)
    {
        for (int i = 0; i < strlen(unique); i++)
            printf("%c ", unique[i]);
        printf("\n");
        int do_task = 1;
        while (do_task)
        {
            int count_on_line_after = 0;
            for (int i = 0; i < strlen(unique); i++)
            {
                if (!count[unique[i]])
                {
                    printf("  ");
                    continue;
                }
                count[unique[i]]--;
                if (count[unique[i]])
                    count_on_line_after++;
                printf(". ");
            }
            if (count_on_line_after)
                printf("\n");
            else
                do_task = 0;
        }
        printf("\n");
    }
    else
    {
        for (int i = 0; i < strlen(unique); i++)
        {
            printf("%c ", unique[i]);
            for (int j = 0; j < count[unique[i]]; j++)
                printf(".");
            printf("\n");
        }
    }

    return 0;
}

int cmp(const void *a_, const void *b_)
{
    const char *a = a_, *b = b_;
    if (count[*a] == count[*b] && *a < *b)
        return -1;
    else if (count[*a] == count[*b] && *a >= *b)
        return 1;
    else if (count[*a] < count[*b])
        return 1;
    else
        return -1;
}