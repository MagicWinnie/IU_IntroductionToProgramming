import java.util.Scanner;

/**
 * Simple calculator implementation
 */
class Calculator {
    /**
     * This method checks whether numbers are positive
     * @param _a First number
     * @param _b Second number
     */
    private void check_numbers(int _a, int _b)
    {
        if (_a >= 0 && _b >= 0)
            return;
        throw new ArithmeticException("Calculator accepts only positive integers");
    }
    /**
     * This method checks whether the operator is implemented
     * @param _op Operator
     */
    private void check_operator(char _op)
    {
        if ("+-*/".indexOf(_op) != -1)
            return;
        throw new ArithmeticException("Calculator accepts only +, -, * and /");
    }
    /**
     * Performs operations depending on _op
     * @param _a First number
     * @param _b Second number
     * @param _op Operator
     */
    public void calculate(int _a, int _b, char _op)
    {
        check_numbers(_a, _b);
        check_operator(_op);

        if (_op == '+')
            System.out.println(_a + _b);
        else if (_op == '-')
            System.out.println(_a - _b);
        else if (_op == '*')
            System.out.println(_a * _b);
        else
            if (_b == 0)
                System.out.println(-1);
            else 
                System.out.println((float)_a / _b);
    }
}

public class Ex1 {
    /**
     * Main method that reads numbers and operator
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