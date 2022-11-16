import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Ex4 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("[ERROR] Not enough arguments. Help: java Ex1 file_url output_directory");
            System.exit(-1);
        }
        try {
            URL url = new URL(args[0]);
            String fileName = url.getFile();
            String destName = args[1] + fileName.substring(fileName.lastIndexOf("/"));
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destName);
            byte[] b = new byte[is.available()];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (MalformedURLException ex) {
            System.out.println("[ERROR] Bad URL.");
            System.exit(-1);
        } catch (IOException ex) {
            System.out.println("[ERROR] Unexpected error.");
            System.exit(-1);
        }
    }
}
