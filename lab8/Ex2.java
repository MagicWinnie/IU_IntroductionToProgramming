import java.util.Scanner;

public class Ex2 {
    protected Ex2() {
    }
    public enum Money {
        /**
         * Money denominations.
         */
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
        /**
         * Money denomination.
         */
        private final int denomination;
        Money(int newDenomination) {
            this.denomination = newDenomination;
        }
        /**
         * Get denomination.
         * @return money
         */
        public int getDenomination() {
            return this.denomination;
        }
    }
    public enum Drinks {
        /**
         * Drinks.
         */
        COCA_COLA("Coca-Cola", 3), SPRITE("Sprite", 7), FANTA("Fanta", 4);
        /**
         * Drink name.
         */
        private final String name;
        /**
         * Drink price.
         */
        private final int price;
        /**
         * Drink constructor.
         * @param newName
         * @param newPrice
         */
        Drinks(String newName, int newPrice) {
            this.name = newName;
            this.price = newPrice;
        }
        /**
         * Get name.
         * @return name
         */
        public String getName() {
            return this.name;
        }
        /**
         * Get price.
         * @return price
         */
        public int getPrice() {
            return this.price;
        }
    }
    /**
     * Print menu.
     */
    public static void printMenu() {
        System.out.println("[1] - Coca-Cola");
        System.out.println("[2] - Sprite");
        System.out.println("[3] - Fanta");
    }
    /**
     * Return drink enum.
     * @param val
     * @return Drink enum
     * @throws Exception
     */
    public static Drinks returnDrink(int val) throws Exception {
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
    /**
     * This method returns money.
     * @param val money value
     * @return Money enum
     * @throws Exception
     */
    public static Money returnMoney(int val) throws Exception {
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
    /**
     * Print ask money text.
     * @param end
     * @param start
     */
    public static void printAskMoney(int end, int start) {
        System.out.println("Please insert $" + (end - start));
    }
    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printMenu();
        Drinks drink;
        while (true) {
            try {
                drink = returnDrink(sc.nextInt());
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        int moneyTotal = 0;
        while (moneyTotal < drink.getPrice())
        {
            printAskMoney(drink.getPrice(), moneyTotal);
            Money money;
            try {
                money = returnMoney(sc.nextInt());
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
                continue;
            }
            moneyTotal += money.getDenomination();
        }
        int moneyToReturn = moneyTotal - drink.getPrice();
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
