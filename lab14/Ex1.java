import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Book implements Serializable {
    private String author;
    private String title;
    private int issueYear;
    private int pageNumber;
    private int bookmark;
    private static final long serialVersionUID = 1L;
    Book(String author, String title, int issueYear, int pageNumber, int bookmark) {
        this.setAuthor(author);
        this.setTitle(title);
        this.setIssueYear(issueYear);
        this.setPageNumber(pageNumber);
        this.setBookmark(bookmark);
    }
    void setAuthor(String author) {
        this.author = author;
    }
    void setTitle(String title) {
        this.title = title;
    }
    void setIssueYear(int issueYear) {
        this.issueYear = issueYear;
    }
    void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }
    String getAuthor() {
        return this.author;
    }
    String getTitle() {
        return this.title;
    }
    int getIssueYear() {
        return this.issueYear;
    }
    int getPageNumber() {
        return this.pageNumber;
    }
    int getBookmark() {
        return this.bookmark;
    }
    @Override
    public String toString() {
        return "Book(" + this.author + " " + this.title + " " + this.issueYear + " " + this.pageNumber + " " + this.bookmark + ")";
    }
}

class ReaderLibrary {
    void addMyReadBooks(List<Book> books, String serializedFilePath) {
        try (FileOutputStream fileOut = new FileOutputStream(serializedFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(books);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    List<Book> getMySerializedBooks(String serializedFilePath) {
        List<Book> books;
        try (FileInputStream fileIn = new FileInputStream(serializedFilePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);) {
            books = (List<Book>) in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
        return books;
    }
}

public class Ex1 {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("A", "AA", 1, 22, 222));
        books.add(new Book("B", "BB", 2, 22, 222));
        books.add(new Book("C", "CC", 2, 22, 222));
        books.add(new Book("D", "DD", 2, 22, 222));
        ReaderLibrary rl = new ReaderLibrary();
        rl.addMyReadBooks(books, "books.dat");
        List<Book> deser = rl.getMySerializedBooks("books.dat");
        System.out.println(deser);
    }
}