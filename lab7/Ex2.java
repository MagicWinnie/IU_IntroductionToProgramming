import java.util.Locale;
import java.util.Scanner;

class Author {
    private String name, email;
    private char gender;

    public Author(String name, String email, char gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public void SetEmail(String email) {
        this.email = email;
    }

    public void SetGender(char gender) {
        this.gender = gender;
    }

    public String GetName() {
        return name;
    }

    public String GetEmail() {
        return email;
    }

    public char GetGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Author[name=" + name + ",email=" + email + ",gender=" + gender + "]";
    }
}

class Book {
    private String name;
    private Author author;
    private double price;
    private int qty = 0;

    public Book(String name, Author author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public Book(String name, Author author, double price, int qty) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.qty = qty;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Book[name=" + name + "," + author + ",price=" + price + ",qty=" + qty + "]";
    }
}

class Library {
    private Book[] library;

    public Library(int size) {
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
        for (int i = 0; i < library.length - 1; i++)
            output += library[i] + ",";
        output += library[library.length - 1] + "]";
        return output;
    }
}

public class Ex2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);
        System.out.print("Enter number of books: ");
        int n = sc.nextInt();
        Library lib = new Library(n);
        for (int i = 0; i < n; i++)
        {
            System.out.print("Enter book name: ");
            String book_name = sc.next();
            System.out.print("Enter author name: ");
            String author_name = sc.next();
            System.out.print("Enter email: ");
            String email = sc.next();
            System.out.print("Enter gender (f/m): ");
            char gender = sc.next().charAt(0);
            System.out.print("Enter price: ");
            double price = sc.nextDouble();
            System.out.print("Enter qty: ");
            int qty = sc.nextInt();

            Author at = new Author(author_name, email, gender);
            Book bk = new Book(book_name, at, price, qty);
            lib.setBook(i, bk);
        }
        System.out.println(lib);
        sc.close();
    }
}
