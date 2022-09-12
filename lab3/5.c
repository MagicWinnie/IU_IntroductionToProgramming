#include <stdio.h>
#include <string.h>
#define N 4
#define START 32
#define END 126

int main()
{
    char passwd[N], guess[N] = "\0";
    printf("password = ");
    gets(passwd);

    int n = END - START + 1 + 1;
    char alphabet[n];
    alphabet[0] = '\0';
    for (int i = START; i <= END; i++)
        alphabet[i - START + 1] = (char)i;
    int no_of_attempts = 0;
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            for (int k = 0; k < n; k++)
            {
                guess[0] = alphabet[i];
                guess[1] = alphabet[j];
                guess[2] = alphabet[k];
                no_of_attempts++;
                if (strcmp(passwd, guess) == 0)
                {
                    printf("found = %s\n", guess);
                    printf("number of attempts = %d\n", no_of_attempts);
                    return 0;
                }
            }
        }
    }

    return 0;
}