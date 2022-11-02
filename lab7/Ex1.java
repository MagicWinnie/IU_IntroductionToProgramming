import java.util.Scanner;

/**
 * Simple calculator implementation.
 */
class Calculator {
    /**
     * This method checks whether numbers are positive.
     * @param a First number
     * @param b Second number
     */
    private void checkNumbers(int a, int b) {
        if (a >= 0 && b >= 0) {
            return;
        }
        throw new ArithmeticException("Calculator accepts only positive integers");
    }
    /**
     * This method checks whether the operator is implemented.
     * @param op Operator
     */
    private void checkOperator(char op) {
        if ("+-*/".indexOf(op) != -1) {
            return;
        }
        throw new ArithmeticException("Calculator accepts only +, -, * and /");
    }
    /**
     * Performs operations depending on op.
     * @param a First number
     * @param b Second number
     * @param op Operator
     */
    public void calculate(int a, int b, char op) {
        checkNumbers(a, b);
        checkOperator(op);

        if (op == '+') {
            System.out.println(a + b);
        } else if (op == '-') {
            System.out.println(a - b);
        } else if (op == '*') {
            System.out.println(a * b);
        } else {
            if (b == 0) {
                System.out.println(-1);
            } else {
                System.out.println((float) a / b);
            }
        }
    }
}

/**
 * Main class.
 */
public class Ex1 {
    protected Ex1() {

    }
    /**
     * Main method that reads numbers and operator.
     * @param args Array of strings containing parameters
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator calc = new Calculator();
        System.out.print("Enter first number: ");
        int a = sc.nextInt();
        System.out.print("Enter operator: ");
        char op = sc.next().charAt(0);
        System.out.print("Enter second number: ");
        int b = sc.nextInt();
        calc.calculate(a, b, op);
        sc.close();
    }
}
