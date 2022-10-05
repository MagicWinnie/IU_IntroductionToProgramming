import java.util.Scanner;

public class Exercise1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();

        System.out.println(a + " " + b);
        
        int tmp = b;
        b = a;
        a = tmp;
        
        System.out.println(a + " " + b);

        s.close();
    }
}