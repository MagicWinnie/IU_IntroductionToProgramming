import java.util.Scanner;

public class Exercise6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int s = sc.nextInt();
        System.out.println((s - 32) * 5 / 9.0f);

        sc.close();
    }
}