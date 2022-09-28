#include <stdio.h>
#include <string.h>

typedef struct
{
    char name[128];
    int amount_ingredients;
    struct { char name[128]; int amount; } ingredients[10];
} RECIPE;

int main()
{
    RECIPE cookbook[3];

    /////// RECIPE #1 ///////
    strcpy(cookbook[0].name, "tea");
    cookbook[0].amount_ingredients = 2;
    ////// INGREDIENT #1 //////
    strcpy(cookbook[0].ingredients[0].name, "water");
    cookbook[0].ingredients[0].amount = 300;
    ////// INGREDIENT #2 //////
    strcpy(cookbook[0].ingredients[1].name, "tea bag");
    cookbook[0].ingredients[1].amount = 1;
    /////// RECIPE #2 ///////
    strcpy(cookbook[1].name, "pizza");
    cookbook[1].amount_ingredients = 10;
    ////// INGREDIENT #1 //////
    strcpy(cookbook[1].ingredients[0].name, "flour");
    cookbook[1].ingredients[0].amount = 300;
    ////// INGREDIENT #2 //////
    strcpy(cookbook[1].ingredients[1].name, "yeast");
    cookbook[1].ingredients[1].amount = 1;
    ////// INGREDIENT #3 //////
    strcpy(cookbook[1].ingredients[2].name, "salt");
    cookbook[1].ingredients[2].amount = 1;
    ////// INGREDIENT #4 //////
    strcpy(cookbook[1].ingredients[3].name, "olive oil");
    cookbook[1].ingredients[3].amount = 1;
    ////// INGREDIENT #5 //////
    strcpy(cookbook[1].ingredients[4].name, "passata");
    cookbook[1].ingredients[4].amount = 100;
    ////// INGREDIENT #6 //////
    strcpy(cookbook[1].ingredients[5].name, "basil");
    cookbook[1].ingredients[5].amount = 1;
    ////// INGREDIENT #7 //////
    strcpy(cookbook[1].ingredients[6].name, "garlic");
    cookbook[1].ingredients[6].amount = 1;
    ////// INGREDIENT #8 //////
    strcpy(cookbook[1].ingredients[7].name, "mozzarella");
    cookbook[1].ingredients[7].amount = 125;
    ////// INGREDIENT #9 //////
    strcpy(cookbook[1].ingredients[8].name, "tomatoes");
    cookbook[1].ingredients[8].amount = 100;
    ////// INGREDIENT #10 //////
    strcpy(cookbook[1].ingredients[9].name, "parmesan");
    cookbook[1].ingredients[9].amount = 90;
    /////// RECIPE #3 ///////
    strcpy(cookbook[2].name, "pasta carbonara");
    cookbook[2].amount_ingredients = 8;
    ////// INGREDIENT #1 //////
    strcpy(cookbook[2].ingredients[0].name, "pancetta");
    cookbook[2].ingredients[0].amount = 100;
    ////// INGREDIENT #2 //////
    strcpy(cookbook[2].ingredients[1].name, "pecorino cheese");
    cookbook[2].ingredients[1].amount = 50;
    ////// INGREDIENT #3 //////
    strcpy(cookbook[2].ingredients[2].name, "parmesan");
    cookbook[2].ingredients[2].amount = 50;
    ////// INGREDIENT #4 //////
    strcpy(cookbook[2].ingredients[3].name, "eggs");
    cookbook[2].ingredients[3].amount = 3;
    ////// INGREDIENT #5 //////
    strcpy(cookbook[2].ingredients[4].name, "spaghetti");
    cookbook[2].ingredients[4].amount = 350;
    ////// INGREDIENT #6 //////
    strcpy(cookbook[2].ingredients[5].name, "garlic");
    cookbook[2].ingredients[5].amount = 2;
    ////// INGREDIENT #7 //////
    strcpy(cookbook[2].ingredients[6].name, "unsalted butter");
    cookbook[2].ingredients[6].amount = 50;
    ////// INGREDIENT #8 //////
    strcpy(cookbook[2].ingredients[7].name, "salt");
    cookbook[2].ingredients[7].amount = 1;

    printf("RECIPES:\n");
    for (int i = 0; i < 3; i++)
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
    return 0;
}