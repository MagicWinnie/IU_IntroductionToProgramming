import java.util.Scanner;

public class Exercise5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if ("aouie".indexOf(s.charAt(i)) != -1)
                count++;
        }
        System.out.println(count);

        sc.close();
    }
}