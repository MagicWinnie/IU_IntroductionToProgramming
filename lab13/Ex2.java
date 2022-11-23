import java.util.HashSet;
import java.util.Objects;

abstract class Animal {
    private String nickname;

    Animal(String name) {
        this.nickname = name;
    }

    public abstract void voice();

    public void setNickname(String name) {
        this.nickname = name;
    }

    public String getNickname() {
        return this.nickname;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nickname.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Animal)) {
            return false;
        }
        Animal animal = (Animal) obj;
        return this.nickname == animal.nickname;
    }
}

class Cat extends Animal {
    private int purLoudness;
    
    Cat(String name) {
        super(name);
        this.purLoudness = 0;
    }
    
    @Override
    public void voice() {
        System.out.println("Meow");
    }

    public void setPurLoudness(int pur) {
        this.purLoudness = pur;
    }

    public int getPurLoudness() {
        return this.purLoudness;
    }
}

class Dog extends Animal {
    private int barkingLoudness;
    
    Dog(String name) {
        super(name);
        this.barkingLoudness = 0;
    }
    
    @Override
    public void voice() {
        System.out.println("Woof");
    }

    public void setBarkingLoudness(int pur) {
        this.barkingLoudness = pur;
    }

    public int getBarkingLoudness() {
        return this.barkingLoudness;
    }
}

public class Ex2<T> {
    HashSet<T> animals;
    public void displayAnimals() {
        System.out.println(this.animals);
    }
    public void makeTalk(Animal animal) {
        animal.voice();
    }
    public void addAnimals(T t) {
        this.animals.add(t);
    }
    public static void main(String[] args) {
        
    }
}
