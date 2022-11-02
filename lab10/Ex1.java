import java.util.ArrayList;

abstract class Creature {
    /**
     * Creature's name.
     */
    private String name = null;
    /**
     * If creature is alive.
     */
    private boolean isAlive = false;
    /**
     * Give birth to a creature.
     * @param newName creature name
     */
    public abstract void bear(String newName);
    /**
     * Kill a creature.
     */
    public abstract void die();
    public void shoutName() {
        if (this.name != null) {
            System.out.println(this.name);
        } else {
            System.out.println("Name is null");
        }
    }
    protected void setName(String newName) {
        this.name = newName;
    }
    protected String getName() {
        return this.name;
    }
    protected void setAlive(boolean state) {
        this.isAlive = state;
    }
    protected boolean getAlive() {
        return this.isAlive;
    }
}

class Human extends Creature {
    public void bear(String newName) {
        this.setName(newName);
        this.setAlive(true);
        System.out.println("The " + this.getClass().getName() + " " + this.getName() + " was born");
    }
    public void die() {
        this.setAlive(false);
        System.out.println("The " + this.getClass().getName() + " " + this.getName() + " has died");
    }
}

class Dog extends Creature {
    public void bear(String newName) {
        this.setName(newName);
        this.setAlive(true);
        System.out.println("The " + this.getClass().getName() + " " + this.getName() + " was born");
    }
    public void die() {
        this.setAlive(false);
        System.out.println("The " + this.getClass().getName() + " " + this.getName() + " has died");
    }
    public void bark() {
        System.out.println("The " + this.getClass().getName() + " " + this.getName() + " has barked");
    }
}

class Alien extends Creature {
    public void bear(String newName) {
        this.setName(newName);
        this.setAlive(true);
        System.out.println("The " + this.getClass().getName() + " " + this.getName() + " was born");
    }
    public void die() {
        this.setAlive(false);
        System.out.println("The " + this.getClass().getName() + " " + this.getName() + " has died");
    }
}

public class Ex1 {
    protected Ex1() {
    }
    /**
     * Main method.
     * @param args string args
     */
    public static void main(String[] args) {
        ArrayList<Creature> creatures = new ArrayList<Creature>();
        creatures.add(new Human());
        creatures.get(0).bear("ABC");
        creatures.add(new Dog());
        creatures.get(1).bear("DEF");
        ((Dog) creatures.get(1)).bark();
        creatures.add(new Alien());
        creatures.get(2).bear("GHI");
        for (Creature creature : creatures) {
            creature.die();
        }
    }
}
