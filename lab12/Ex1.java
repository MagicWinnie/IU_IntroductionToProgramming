import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ex1 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("[ERROR] Not enough arguments. Help: java Ex1 input_file_name output_file_name");
            System.exit(-1);
        }
        String inputFileName = args[0];
        String outputFileName = args[1];
        
        FileInputStream in;
        FileOutputStream out;
        byte[] buffer = new byte[0];
        try {
            in = new FileInputStream(inputFileName);
            buffer = new byte[in.available()];
            in.read(buffer, 0, buffer.length);
            in.close();
        } catch (FileNotFoundException ex) {
            System.out.println("[WARNING] Input file not found. Writing empty output file.");
        } catch (IOException ex) {
            System.out.println("[WARNING] No permission to read the input file. Writing empty output file.");
        }
        try {
            out = new FileOutputStream(outputFileName);
            out.write(buffer, 0, buffer.length);
            out.close();
        } catch (IOException ex) {
            System.out.println("[ERROR] Cannot create an output file.");
            System.exit(-1);
        }
    }
}
