import java.util.List;
import java.util.ArrayList;

class Library<T> {
    /**
     * Media instance.
     */
    private Media<T> media = null;
    Library(Media<T> t) {
        this.media = t;
    }
}

abstract class Media<T> {
    /**
     * List that contains media.
     */
    protected List<T> media;
    Media() {
        this.media = new ArrayList<T>();
    }
    public abstract void add(T t);

    public abstract List<T> get();
}

class Book<T> extends Media<T> {
    Book() {
        super();
    }
    public void add(T t) {
        this.media.add(t);
    }
    public List<T> get() {
        return this.media;
    }
}

class Video<T> extends Media<T> {
    Video() {
        super();
    }
    public void add(T t) {
        this.media.add(t);
    }
    public List<T> get() {
        return this.media;
    }
}

class Newspaper<T> extends Media<T> {
    Newspaper() {
        super();
    }
    public void add(T t) {
        this.media.add(t);
    }
    public List<T> get() {
        return this.media;
    }
}

/**
 * Main class.
 */
public final class Ex1 {
    private Ex1() {

    }
    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        Library<Integer> library;
    }
}
