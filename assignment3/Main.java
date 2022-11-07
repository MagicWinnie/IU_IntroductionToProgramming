// import packages
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class.
 */
public class Main {
    /**
     * Lower bound for n.
     */
    private final int nLowerBound = 1;
    /**
     * Upper bound for n.
     */
    private final int nUpperBound = 50;
    /**
     * Calculator object.
     */
    private Calculator calc;
    /**
     * Input scanner.
     */
    private Scanner scanner;

    /**
     * This method reads the operand types and parses them.
     * @return operand type that has been read
     */
    private CalculatorType readCalculator() {
        String type = this.scanner.nextLine(); // read calculator type
        CalculatorType calcType;
        switch (type) {
            case "DOUBLE":
                calcType = CalculatorType.DOUBLE;
                this.calc = new DoubleCalculator();
                break;
            case "INTEGER":
                calcType = CalculatorType.INTEGER;
                this.calc = new IntegerCalculator();
                break;
            case "STRING":
                calcType = CalculatorType.STRING;
                this.calc = new StringCalculator();
                break;
            default:
                calcType = CalculatorType.INCORRECT;
                this.reportFatalError("Wrong calculator type");
                break;
        }
        return calcType;
    }

    /**
     * This method reads amount of commands.
     * @return number of commands
     */
    private int readCommandsNumber() {
        int n = 0;
        try {
            n = this.scanner.nextInt();
        } catch (NumberFormatException ex) {
            this.reportFatalError("Amount of commands is Not a Number");
        } catch (InputMismatchException ex) {
            this.reportFatalError("Amount of commands is Not a Number");
        }
        if (n < this.nLowerBound || n > this.nUpperBound) {
            this.reportFatalError("Amount of commands is Not a Number");
        }
        return n;
    }

    /**
     * This method prints error to console and exits program.
     * @param err Error string
     */
    private void reportFatalError(String err) {
        System.out.println(err);
        this.scanner.close();
        System.exit(0);
    }

    /**
     * This method return OperationType enum field based on String.
     * @param operation operation string
     * @return operation enum
     */
    private OperationType parseOperation(String operation) {
        OperationType operType;
        switch (operation) {
            case "+":
                operType = OperationType.ADDITION;
                break;
            case "-":
                operType = OperationType.SUBTRACTION;
                break;
            case "/":
                operType = OperationType.DIVISION;
                break;
            case "*":
                operType = OperationType.MULTIPLICATION;
                break;
            default:
                operType = OperationType.INCORRECT;
                break;
        }
        return operType;
    }

    /**
     * This method reads a command line and processes it.
     */
    private void calculate() {
        String command = this.scanner.next();
        String operand1 = this.scanner.next();
        String operand2 = this.scanner.next();
        OperationType operType = this.parseOperation(command);
        switch (operType) {
            case ADDITION:
                System.out.println(calc.add(operand1, operand2));
                break;
            case SUBTRACTION:
                System.out.println(calc.subtract(operand1, operand2));
                break;
            case MULTIPLICATION:
                System.out.println(calc.multiply(operand1, operand2));
                break;
            case DIVISION:
                System.out.println(calc.divide(operand1, operand2));
                break;
            default:
                System.out.println("Wrong operation type");
                break;
        }
    }
    /**
     * The main method.
     * It reads the inputs.
     * @param args execution arguments
     */
    public static void main(String[] args) {
        Main my = new Main(); // create an instance of ourself
        my.scanner = new Scanner(System.in); // assign a new scanner
        my.readCalculator(); // read calculator type
        int n = my.readCommandsNumber(); // read number of commands
        for (int i = 0; i < n; i++) {
            my.calculate(); // read line and calculate the result
        }
        my.scanner.close(); // close the scanner
    }
}

/**
 * Calculator types.
 */
enum CalculatorType {
    /**
     * Calculator works with integer operands.
     */
    INTEGER,
    /**
     * Calculator works with double operands.
     */
    DOUBLE,
    /**
     * Calculator works with string operands.
     */
    STRING,
    /**
     * Invalid operand type.
     */
    INCORRECT
}

/**
 * Operation types.
 */
enum OperationType {
    /**
     * Addition operation.
     */
    ADDITION,
    /**
     * Subtraction operation.
     */
    SUBTRACTION,
    /**
     * Multiplication operation.
     */
    MULTIPLICATION,
    /**
     * Division operation.
     */
    DIVISION,
    /**
     * Incorrect operation.
     */
    INCORRECT
}

/**
 * Abstract class for Calculator of different types.
 */
abstract class Calculator {
    /**
     * This method calculates result of a + b.
     * @param a String for operand 1.
     * @param b String for operand 2.
     * @return Returns a string, that is a result of a + b or error message if unsupported.
     */
    public abstract String add(String a, String b);
    /**
     * This method calculates result of a - b.
     * @param a String for operand 1.
     * @param b String for operand 2.
     * @return Returns a string, that is a result of a + b or error message if unsupported.
     */
    public abstract String subtract(String a, String b);
    /**
     * This method calculates result of a * b.
     * @param a String for operand 1.
     * @param b String for operand 2.
     * @return Returns a string, that is a result of a + b or error message if unsupported.
     */
    public abstract String multiply(String a, String b);
    /**
     * This method calculates result of a / b.
     * @param a String for operand 1.
     * @param b String for operand 2.
     * @return Returns a string, that is a result of a + b or error message if unsupported.
     */
    public abstract String divide(String a, String b);

    /**
     * This method checks if string is integer type.
     * @param n String that probably contains an integer.
     * @return True if it is an integer else False
     */
    protected boolean isInteger(String n) {
        try {
            Integer.parseInt(n);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * This method checks if string is double type.
     * @param n String that probably contains an double.
     * @return True if it is an double else False
     */
    protected boolean isDouble(String n) {
        try {
            Double.parseDouble(n);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * This method checks if string is integer or double type.
     * @param n String that probably contains an integer or double.
     * @return True if it is an integer or double else False
     */
    protected boolean isNumber(String n) {
        return this.isInteger(n) || this.isDouble(n);
    }
}

/**
 * Class for integer calculator.
 */
class IntegerCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        if (!this.isNumber(a) || !this.isNumber(b)) {
            return "Wrong argument type";
        }
        int operand1 = Integer.parseInt(a);
        int operand2 = Integer.parseInt(b);
        int result = operand1 + operand2;
        return Integer.toString(result);
    }
    @Override
    public String subtract(String a, String b) {
        if (!this.isNumber(a) || !this.isNumber(b)) {
            return "Wrong argument type";
        }
        int operand1 = Integer.parseInt(a);
        int operand2 = Integer.parseInt(b);
        int result = operand1 - operand2;
        return Integer.toString(result);
    }
    @Override
    public String multiply(String a, String b) {
        if (!this.isNumber(a) || !this.isNumber(b)) {
            return "Wrong argument type";
        }
        int operand1 = Integer.parseInt(a);
        int operand2 = Integer.parseInt(b);
        int result = operand1 * operand2;
        return Integer.toString(result);
    }
    @Override
    public String divide(String a, String b) {
        if (!this.isNumber(a) || !this.isNumber(b)) {
            return "Wrong argument type";
        }
        int operand1 = Integer.parseInt(a);
        int operand2 = Integer.parseInt(b);
        if (operand2 == 0) {
            return "Division by zero";
        }
        int result = operand1 / operand2;
        return Integer.toString(result);
    }
}

/**
 * Class for double calculator.
 */
class DoubleCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        if (!this.isNumber(a) || !this.isNumber(b)) {
            return "Wrong argument type";
        }
        double operand1 = Double.parseDouble(a);
        double operand2 = Double.parseDouble(b);
        double result = operand1 + operand2;
        return Double.toString(result);
    }
    @Override
    public String subtract(String a, String b) {
        if (!this.isNumber(a) || !this.isNumber(b)) {
            return "Wrong argument type";
        }
        double operand1 = Double.parseDouble(a);
        double operand2 = Double.parseDouble(b);
        double result = operand1 - operand2;
        return Double.toString(result);
    }
    @Override
    public String multiply(String a, String b) {
        if (!this.isNumber(a) || !this.isNumber(b)) {
            return "Wrong argument type";
        }
        double operand1 = Double.parseDouble(a);
        double operand2 = Double.parseDouble(b);
        double result = operand1 * operand2;
        return Double.toString(result);
    }
    @Override
    public String divide(String a, String b) {
        if (!this.isNumber(a) || !this.isNumber(b)) {
            return "Wrong argument type";
        }
        double operand1 = Double.parseDouble(a);
        double operand2 = Double.parseDouble(b);
        if (operand2 == 0D) {
            return "Division by zero";
        }
        double result = operand1 / operand2;
        return Double.toString(result);
    }
}

/**
 * Class for string calculator.
 */
class StringCalculator extends Calculator {
    @Override
    public String add(String a, String b) {
        return a + b;
    }
    @Override
    public String subtract(String a, String b) {
        return "Unsupported operation for strings";
    }
    @Override
    public String multiply(String a, String b) {
        if (!isInteger(b) || (isInteger(b) && Integer.parseInt(b) < 1)) {
            return "Wrong argument type";
        }
        // repeat string b times
        String s = "";
        for (int i = 0; i < Integer.parseInt(b); i++) {
            s += a;
        }
        return s;
    }
    @Override
    public String divide(String a, String b) {
        return "Unsupported operation for strings";
    }
}
