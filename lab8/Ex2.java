import java.util.Scanner;

public class Ex2 {
    public enum Money {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
        private final int denomination;
        Money(int denomination)
        {
            this.denomination = denomination;
        }
        public int getDenomination()
        {
            return this.denomination;
        }
    }
    public enum Drinks {
        COCA_COLA("Coca-Cola", 3), SPRITE("Sprite", 7), FANTA("Fanta", 4);
        private final String name;
        private final int price;
        Drinks(String name, int price)
        {
            this.name = name;
            this.price = price;
        }
        public String getName()
        {
            return this.name;
        }
        public int getPrice()
        {
            return this.price;
        }
    }
    public static void printMenu()
    {
        System.out.println("[1] - Coca-Cola");
        System.out.println("[2] - Sprite");
        System.out.println("[3] - Fanta");
    }
    public static Drinks returnDrink(int val) throws Exception
    {
        Drinks drink;
        switch (val) {
            case 1:
                drink = Drinks.COCA_COLA;
                break;
            case 2:
                drink = Drinks.SPRITE;
                break;
            case 3:
                drink = Drinks.FANTA;
                break;
            default:
                throw new Exception("This drink does not exist!\nTry one more time");
        }
        System.out.println("You have chosen " + drink.getName());
        System.out.println("It costs $" + drink.getPrice());
        return drink;
    }
    public static Money returnMoney(int val) throws Exception
    {
        Money money;
        switch (val) {
            case 1:
                money = Money.ONE;
                break;
            case 2:
                money = Money.TWO;
                break;
            case 3:
                money = Money.THREE;
                break;
            case 4:
                money = Money.FOUR;
                break;
            case 5:
                money = Money.FIVE;
                break;
            default:
                throw new Exception("Banknote with this denomination does not exist!\nTry one more time");
        }
        return money;
    }
    public static void printAskMoney(int end, int start)
    {
        System.out.println("Please insert $" + (end - start));
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printMenu();
        Drinks drink;
        while (true)
        {
            try {
                drink = returnDrink(sc.nextInt());
                break;
            } 
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        int money_total = 0;
        while (money_total < drink.getPrice())
        {
            printAskMoney(drink.getPrice(), money_total);
            Money money;
            try {
                money = returnMoney(sc.nextInt());
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
                continue;
            }
            money_total += money.getDenomination();
        }
        int moneyToReturn = money_total - drink.getPrice();
        while (moneyToReturn > 0)
        {
            if (moneyToReturn > 4)
            {
                System.out.println("Returned $" + 5);
                moneyToReturn -= 5;
            }
            else if (moneyToReturn > 3)
            {
                System.out.println("Returned $" + 4);
                moneyToReturn -= 4;
            }
            else if (moneyToReturn > 2)
            {
                System.out.println("Returned $" + 3);
                moneyToReturn -= 3;
            }
            else if (moneyToReturn > 1)
            {
                System.out.println("Returned $" + 2);
                moneyToReturn -= 2;
            }
            else if (moneyToReturn > 0)
            {
                System.out.println("Returned $" + 1);
                moneyToReturn -= 1;
            }
        }
        sc.close();
    }
}
