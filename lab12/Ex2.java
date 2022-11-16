import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ex2 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("[ERROR] Not enough arguments. Help: java Ex1 input_file_name");
            System.exit(-1);
        }
        String inputFileName = args[0];
        
        FileInputStream in;
        String raw_str = "";
        try {
            in = new FileInputStream(inputFileName);
            byte[] buffer = in.readAllBytes();
            raw_str = new String(buffer);
        } catch (FileNotFoundException ex) {
            System.out.println("[WARNING] Input file not found. Writing empty output file.");
        } catch (IOException ex) {
            System.out.println("[WARNING] Input file has read-only permission. Writing empty output file.");
        }
        double a = 0, b = 0;
        try {
            a = Double.parseDouble(raw_str.split(" ")[0]);
            b = Double.parseDouble(raw_str.split(" ")[1]);
        } catch (NumberFormatException ex) {
            System.out.println("[ERROR] One of the inputs is not a number.");
            System.exit(-1);
        }
        if (b == 0.0d) {
            System.out.println("[ERROR] Division by zero.");
            System.exit(-1);
        }
        double c = a / b;
        System.out.println(c);
    }
}
