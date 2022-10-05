import java.util.Scanner;

public class Exercise4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s1 = sc.nextLine();
        String s2 = sc.nextLine();

        if (s1.compareTo(s2) == -1)
            System.out.println("<");
        else if (s1.compareTo(s2) == 0)
            System.out.println("==");
        else
            System.out.println(">");

        sc.close();        
    }
}