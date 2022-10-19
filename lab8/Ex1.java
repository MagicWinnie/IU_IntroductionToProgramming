import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        while (true)
        {
            System.out.println("[0] - exit");
            System.out.println("[1] - print current string");
            System.out.println("[2] - append the string");
            System.out.println("[3] - insert the string to the current");
            System.out.println("[4] - reverse current string");
            System.out.println("[5] - delete substring from the current string");
            System.out.println("[6] - replace substring in the current string");
            int command = sc.nextInt();
            if (command == 1)
                System.out.println(str);
            else if (command == 2)
            {
                System.out.print("Enter string: ");
                String s = sc.next();
                str.append(s);
            }
            else if (command == 3)
            {
                System.out.print("Enter offset string: ");
                int offset = sc.nextInt();
                String s = sc.next();
                str.insert(offset, s);
            }
            else if (command == 4)
                str.reverse();
            else if (command == 5)
            {
                System.out.print("Enter start end: ");
                int start = sc.nextInt();
                int end = sc.nextInt();
                str.delete(start, end);
            }
            else if (command == 6)
            {
                System.out.print("Enter start end string: ");
                int start = sc.nextInt();
                int end = sc.nextInt();
                String s = sc.next();
                str.replace(start, end, s);
            }
            else 
                break;
        }
        sc.close();
    }
}