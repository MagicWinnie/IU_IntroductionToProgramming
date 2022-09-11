// include libraries
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define N 100 // max amount of names
#define L 101 // max length of a name + \0
#define DEBUG 0

int cmp(const void *p1, const void *p2);

int main()
{
    // open files
    FILE *inp = fopen("input.txt", "r");
    FILE *out = fopen("output.txt", "w");

    // read n and check it
    int n;
    fscanf(inp, "%d\n", &n);
    if (n < 1 || n > 100)
    {
        if (DEBUG)
            printf("Exit from pnt #1\n");
        fprintf(out, "Error in the input.txt");
        // close files
        fclose(inp);
        fclose(out);
        return 0;
    }
    // read names
    char names[N][2 * L], buf[2 * L];
    int num_lines = 0;
    while (fgets(buf, 2 * L, inp))
    {
        buf[strcspn(buf, "\n")] = 0;
        if (strlen(buf) >= 1)
        {
            strcpy(names[num_lines], buf);
            num_lines++;
        }
    }
    // check that actual number of lines = n
    if (num_lines != n)
    {
        if (DEBUG)
            printf("Exit from pnt #2\n");
        fprintf(out, "Error in the input.txt");
        // close files
        fclose(inp);
        fclose(out);
        return 0;
    }
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < strlen(names[i]); j++)
        {
            // check if first letter is upper
            // check if others are lower
            // else write error
            if (j == 0 && names[i][j] >= 65 && names[i][j] <= 90)
                continue;
            else if (j != 0 && names[i][j] >= 97 && names[i][j] <= 122)
                continue;
            else
            {
                if (DEBUG)
                    printf("Exit from pnt #3\n");
                fprintf(out, "Error in the input.txt");
                // close files
                fclose(inp);
                fclose(out);
                return 0;
            }
        }
    }
    // qsort names
    qsort(names, n, sizeof(names[0]), cmp);
    // print names
    for (int i = 0; i < n; i++)
        fprintf(out, "%s\n", names[i]);

    // close files
    fclose(inp);
    fclose(out);

    return 0;
}

int cmp(const void *p1, const void *p2)
{
    return strcmp(p1, p2);
}
