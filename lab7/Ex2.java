import java.util.Locale;
import java.util.Scanner;

class Author {
    /**
     * Author name.
     */
    private String name;
    /**
     * Author email.
     */
    private String email;
    /**
     * Author gender.
     */
    private char gender;

    /**
     * Author constructor.
     * @param newName author name
     * @param newEmail author email
     * @param newGender author gender
     */
    Author(String newName, String newEmail, char newGender) {
        this.name = newName;
        this.email = newEmail;
        this.gender = newGender;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setGender(char newGender) {
        this.gender = newGender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Author[name=" + name + ",email=" + email + ",gender=" + gender + "]";
    }
}

class Book {
    /**
     * Book name.
     */
    private String name;
    /**
     * Book author.
     */
    private Author author;
    /**
     * Book price.
     */
    private double price;
    /**
     * Book quantity.
     */
    private int qty = 0;

    Book(String newName, Author newAuthor, double newPrice) {
        this.name = newName;
        this.author = newAuthor;
        this.price = newPrice;
    }

    Book(String newName, Author newAuthor, double newPrice, int newQty) {
        this.name = newName;
        this.author = newAuthor;
        this.price = newPrice;
        this.qty = newQty;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int newQty) {
        this.qty = newQty;
    }

    @Override
    public String toString() {
        return "Book[name=" + name + "," + author + ",price=" + price + ",qty=" + qty + "]";
    }
}

class Library {
    /**
     * Book array.
     */
    private Book[] library;

    Library(int size) {
        library = new Book[size];
    }

    public Book[] getLibrary() {
        return library;
    }

    public Book getBook(int ind) {
        return library[ind];
    }

    public void setBook(int ind, Book book) {
        library[ind] = book;
    }

    @Override
    public String toString() {
        String output = "Library[";
        for (int i = 0; i < library.length - 1; i++) {
            output += library[i] + ",";
        }
        output += library[library.length - 1] + "]";
        return output;
    }
}

public class Ex2 {
    protected Ex2() {

    }
    /**
     * Main method.
     * @param args program args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);
        System.out.print("Enter number of books: ");
        int n = sc.nextInt();
        Library lib = new Library(n);
        for (int i = 0; i < n; i++) {
            System.out.print("Enter book name: ");
            String bookName = sc.next();
            System.out.print("Enter author name: ");
            String authorName = sc.next();
            System.out.print("Enter email: ");
            String email = sc.next();
            System.out.print("Enter gender (f/m): ");
            char gender = sc.next().charAt(0);
            System.out.print("Enter price: ");
            double price = sc.nextDouble();
            System.out.print("Enter qty: ");
            int qty = sc.nextInt();

            Author at = new Author(authorName, email, gender);
            Book bk = new Book(bookName, at, price, qty);
            lib.setBook(i, bk);
        }
        System.out.println(lib);
        sc.close();
    }
}
