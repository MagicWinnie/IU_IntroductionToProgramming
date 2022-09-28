#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_LEN 128

typedef struct
{
    char name[MAX_LEN];
    int amount;
} INGREDIENT;

typedef struct
{
    char name[MAX_LEN];
    int amount_ingredients;
    INGREDIENT *ingredients;
} RECIPE;

void add_recipe(RECIPE *rec, char *name, int amount);
void add_ingredient(INGREDIENT *ing, char *name, int amount);

int main()
{
    int n;
    printf("How many recipes will be entered: ");
    scanf("%d\n", &n);
    RECIPE *cookbook = (RECIPE *)malloc(sizeof(RECIPE) * n);

    for (int i = 0; i < n; i++)
    {
        int amount_ing;
        char name[MAX_LEN];
        printf("Enter name of dish: ");
        fgets(name, MAX_LEN, stdin);
        name[strcspn(name, "\n")] = 0;
        printf("Enter amount of ingredients: ");
        scanf("%d\n", &amount_ing);
        add_recipe(&cookbook[i], name, amount_ing);
        for (int j = 0; j < amount_ing; j++)
        {
            int amount;
            char name_ing[MAX_LEN];
            printf("Enter name of ingredient: ");
            fgets(name_ing, MAX_LEN, stdin);
            name_ing[strcspn(name_ing, "\n")] = 0;
            printf("Enter amount of ingredient: ");
            scanf("%d\n", &amount);
            add_ingredient(&cookbook[i].ingredients[j], name_ing, amount);
        }
        printf("HERE\n");
    }

    printf("RECIPES:\n");
    for (int i = 0; i < n; i++)
    {
        printf("\tRECIPE #%d:\n", i + 1);
        printf("\t\tName: %s\n", cookbook[i].name);
        printf("\t\tIngredients:\n");
        for (int j = 0; j < cookbook[i].amount_ingredients; j++)
        {
            printf("\t\t\tINGREDIENT #%d:\n", j + 1);
            printf("\t\t\t\tName: %s\n", cookbook[i].ingredients[j].name);
            printf("\t\t\t\tAmount: %d\n", cookbook[i].ingredients[j].amount);
        }
    }
    for (int i = 0; i < n; i++)
        free(cookbook[i].ingredients);
    free(cookbook);
    return 0;
}

void add_recipe(RECIPE *rec, char *name, int amount)
{
    strcpy(rec->name, name);
    rec->amount_ingredients = amount;
    rec->ingredients = (INGREDIENT *)malloc(sizeof(INGREDIENT) * amount);
}

void add_ingredient(INGREDIENT *ing, char *name, int amount)
{
    strcpy(ing->name, name);
    ing->amount = amount;
}

/*
3
tea
2
water
300
tea bag
1
pizza
10
flour
300
yeast
300
salt
1
olive oil
1
passata
100
basil
1
garlic
1
mozzarella
125
tomatoes
100
parmesan
90
pasta carbonara
8
pancetta
100
pecorino cheese
50
parmesan
50
eggs
3
spaghetti
350
garlic
2
unsalted butter
50
salt
1
*/