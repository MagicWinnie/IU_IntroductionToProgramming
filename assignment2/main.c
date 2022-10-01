#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <limits.h>
#define MAX_LEN 128 // allocated size for strings
#define DEBUG 0     // allows printing checkpoints into output

// struct for storing player data
typedef struct
{
    char name[MAX_LEN];
    int team, power, visibility, ingame;
} Player;

void invalid_input(int n);
void check_number(int n, int a, int b);
void check_string(char *s);
int return_player_index_by_name(char *name, Player *players, int m);

FILE *inp, *out;

int main()
{
    // open files
    inp = fopen("input.txt", "r");
    out = fopen("output.txt", "w");

    // read value n and check if it is valid
    int n;
    fscanf(inp, "%d\n", &n);
    check_number(n, 1, 10);
    // create dynamic array for names of magicians
    char **magicians = (char **)malloc(sizeof(char *) * n);
    // read magicians' names, validate them and put into the array
    for (int i = 0; i < n; i++)
    {
        char name[MAX_LEN];
        fscanf(inp, "%s\n", name);
        check_number(strlen(name), 1, 20); // to teachers: why lower bound is 1 and not 2 as in the problem ???
        check_string(name);

        for (int j = 0; j < i; j++)
            if (strcmp(magicians[j], name) == 0)
                invalid_input(12);

        magicians[i] = (char *)malloc(sizeof(char) * MAX_LEN);
        strcpy(magicians[i], name);
    }
    // read value m and check if it is valid
    int m;
    fscanf(inp, "%d\n", &m);
    check_number(m, n, 100);
    // create dynamic array for players
    Player *players = (Player *)malloc(sizeof(Player) * m);
    // read player data, validate them and put into the array
    for (int i = 0; i < m; i++)
    {
        char name[MAX_LEN];
        fscanf(inp, "%s\n", name);
        check_number(strlen(name), 1, 20); // to teachers: why lower bound is 1 and not 2 as in the problem ???
        check_string(name);

        if (return_player_index_by_name(name, players, i) != -1)
            invalid_input(13);

        int t;
        fscanf(inp, "%d\n", &t);
        check_number(t, 0, n - 1);

        int p;
        fscanf(inp, "%d\n", &p);
        check_number(p, 0, 1000);

        int v;
        char visibility_s[MAX_LEN];
        fscanf(inp, "%s\n", visibility_s);
        if (strcmp(visibility_s, "True") == 0)
            v = 1;
        else if (strcmp(visibility_s, "False") == 0)
            v = 0;
        else
            invalid_input(1);

        strcpy(players[i].name, name);
        players[i].team = t;
        players[i].power = p;
        players[i].visibility = v;
        players[i].ingame = 1;
    }
    
    // read player moves until the end of file
    int line = 0, k_super = 0;
    while (!feof(inp))
    {
        char attack[MAX_LEN], name_i[MAX_LEN], name_j[MAX_LEN];
        fscanf(inp, "%s", attack);
        if (strcmp(attack, "attack") == 0)
        {
            // read names and check if they exist
            fscanf(inp, "%s %s\n", name_i, name_j);
            int ind_i = return_player_index_by_name(name_i, players, m);
            int ind_j = return_player_index_by_name(name_j, players, m);
            if (ind_i == -1 || ind_j == -1)
                invalid_input(2);
            // doing the things stated in the problem
            if (!players[ind_i].visibility)
            {
                fprintf(out, "This player can't play\n");
            }
            else if (!players[ind_i].power)
            {
                fprintf(out, "This player is frozen\n");
            }
            else if (!players[ind_j].visibility)
            {
                players[ind_i].power = 0;
            }
            else if (players[ind_i].power > players[ind_j].power)
            {
                players[ind_i].power += players[ind_i].power - players[ind_j].power;
                // max power is 1000
                if (players[ind_i].power > 1000)
                    players[ind_i].power = 1000;
                players[ind_j].power = 0;
            }
            else if (players[ind_i].power < players[ind_j].power)
            {
                players[ind_j].power += players[ind_j].power - players[ind_i].power;
                // max power is 1000
                if (players[ind_j].power > 1000)
                    players[ind_j].power = 1000;
                players[ind_i].power = 0;
            }
            else if (players[ind_i].power == players[ind_j].power)
            {
                players[ind_i].power = 0;
                players[ind_j].power = 0;
            }
        }
        else if (strcmp(attack, "flip_visibility") == 0)
        {
            // read name and check if it exists
            fscanf(inp, "%s\n", name_i);
            int ind_i = return_player_index_by_name(name_i, players, m);
            if (ind_i == -1)
                invalid_input(3);
            // doing the things stated in the problem
            if (!players[ind_i].power)
            {
                fprintf(out, "This player is frozen\n");
            }
            else
            {
                players[ind_i].visibility = !players[ind_i].visibility;
            }
        }
        else if (strcmp(attack, "heal") == 0)
        {
            // read names and check if they exist
            fscanf(inp, "%s %s\n", name_i, name_j);
            int ind_i = return_player_index_by_name(name_i, players, m);
            int ind_j = return_player_index_by_name(name_j, players, m);
            if (ind_i == -1 || ind_j == -1)
                invalid_input(4);
            // doing the things stated in the problem
            if (!players[ind_i].visibility)
            {
                fprintf(out, "This player can't play\n");
            }
            else if (!players[ind_i].power)
            {
                fprintf(out, "This player is frozen\n");
            }
            else if (players[ind_i].team != players[ind_j].team)
            {
                fprintf(out, "Both players should be from the same team\n");
            }
            else if (strcmp(players[ind_i].name, players[ind_j].name) == 0)
            {
                fprintf(out, "The player cannot heal itself\n");
            }
            else
            {
                int half = 0;
                if (players[ind_i].power % 2 == 0)
                {
                    half = players[ind_i].power / 2;
                    players[ind_i].power -= half;
                    players[ind_j].power += half;
                }
                else
                {
                    half = players[ind_i].power / 2;
                    players[ind_i].power -= half;
                    players[ind_j].power += half + 1;
                }
                // max power is 1000
                if (players[ind_j].power > 1000)
                    players[ind_j].power = 1000;
            }
        }
        else if (strcmp(attack, "super") == 0)
        {
            // read names and check if they exist
            fscanf(inp, "%s %s\n", name_i, name_j);
            int ind_i = return_player_index_by_name(name_i, players, m);
            int ind_j = return_player_index_by_name(name_j, players, m);
            if (ind_i == -1 || ind_j == -1)
                invalid_input(5);
            // doing the things stated in the problem
            if (!players[ind_i].visibility)
            {
                fprintf(out, "This player can't play\n");
            }
            else if (!players[ind_i].power)
            {
                fprintf(out, "This player is frozen\n");
            }
            else if (players[ind_i].team != players[ind_j].team)
            {
                fprintf(out, "Both players should be from the same team\n");
            }
            else if (strcmp(players[ind_i].name, players[ind_j].name) == 0)
            {
                fprintf(out, "The player cannot do super action with itself\n");
            }
            else
            {
                int power = players[ind_i].power + players[ind_j].power;
                // max power is 1000
                if (power > 1000)
                    power = 1000;
                // instead of deleting players, I create instead of player_i
                // the new player S_k, and set ingame for player_j to false
                // when doing stuff with players, I skip not ingame players
                players[ind_j].ingame = 0;
                players[ind_i].power = power;
                players[ind_i].visibility = 1;
                char index_tmp[MAX_LEN];
                sprintf(index_tmp, "S_%d", k_super);
                strcpy(players[ind_i].name, index_tmp);
                k_super++;
            }
        }
        else
            invalid_input(6);
        line++;
    }
    // validate number of moves
    check_number(line, 0, 1000);

    // get sum of power for all teams
    int *teams = (int *)calloc(n, sizeof(int));
    for (int i = 0; i < m; i++)
        if (players[i].ingame)
            teams[players[i].team] += players[i].power;
    // find max power
    int greatest_power = -1, index_gp = 0;
    for (int i = 0; i < n; i++)
        if (teams[i] > greatest_power)
        {
            greatest_power = teams[i];
            index_gp = i;
        }
    // count number of occurances of max power
    int count = 0;
    for (int i = 0; i < n; i++)
        if (teams[i] == greatest_power)
            count++;
    // print appropiate output based on count
    if (count > 1)
        fprintf(out, "It's a tie\n");
    else
        fprintf(out, "The chosen wizard is %s\n", magicians[index_gp]);

    // free dynamic memory
    for (int i = 0; i < n; i++)
        free(magicians[i]);
    free(magicians);
    free(players);
    free(teams);

    // close files
    fclose(inp);
    fclose(out);

    return 0;
}

void invalid_input(int n)
{
    // Writes invalid input into output
    // if invalid input, then we rewrite previous outputs
    fclose(out);
    out = fopen("output.txt", "w");

    if (DEBUG)
        fprintf(out, "Invalid inputs #%d\n", n);
    else
        fprintf(out, "Invalid inputs\n");
    exit(0);
}

void check_number(int n, int a, int b)
{
    // Checks whether n is greater or equal to a and lower or equal to b
    if (n >= a && n <= b)
        return;
    invalid_input(7);
}

void check_string(char *s)
{
    // Checks whether string s starts with upper alpha and other are alpha
    if (!isupper(s[0]))
        invalid_input(8);
    for (int i = 1; i < strlen(s); i++)
    {
        if (!isalpha(s[i]))
            invalid_input(9);
    }
}

int return_player_index_by_name(char *name, Player *players, int m)
{
    // Returns players index in array by its name
    for (int i = 0; i < m; i++)
        if (strcmp(players[i].name, name) == 0 && players[i].ingame)
            return i;
    return -1;
}
