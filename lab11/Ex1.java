import java.util.ArrayList;
import java.util.List;

interface Swimmable {
    void swim();

    void stopSwimming();
}

interface Flyable {
    void fly();

    void stopFlying();
}

interface Living {
    default void live() {
        System.out.println(this.getClass().getName() + " lives");
    }
}

class Submarine implements Swimmable {
    private boolean swimming = false;

    public void swim() {
        if (this.swimming) {
            System.out.println("The submarine is already swimming!");
        } else {
            this.swimming = true;
            System.out.println("The submarine started swimming!");
        }
    }

    public void stopSwimming() {
        if (this.swimming) {
            this.swimming = false;
            System.out.println("The submarine stopped swimming!");
        } else {
            System.out.println("The submarine is not swimming!");
        }
    }
}

class Duck implements Swimmable, Flyable, Living {
    private boolean swimming = false;
    private boolean flying = false;

    public void swim() {
        if (this.swimming) {
            System.out.println("The duck is already swimming!");
        } else {
            this.swimming = true;
            System.out.println("The duck started swimming!");
        }
    }

    public void stopSwimming() {
        if (this.swimming) {
            this.swimming = false;
            System.out.println("The duck stopped swimming!");
        } else {
            System.out.println("The duck is not swimming!");
        }
    }

    public void fly() {
        if (this.flying) {
            System.out.println("The duck is already flying!");
        } else {
            this.flying = true;
            System.out.println("The duck started flying!");
        }
    }

    public void stopFlying() {
        if (this.flying) {
            this.flying = false;
            System.out.println("The duck stopped flying!");
        } else {
            System.out.println("The duck is not flying!");
        }
    }
}

class Penguin implements Swimmable, Living {
    private boolean swimming = false;

    public void swim() {
        if (this.swimming) {
            System.out.println("The penguin is already swimming!");
        } else {
            this.swimming = true;
            System.out.println("The penguin started swimming!");
        }
    }

    public void stopSwimming() {
        if (this.swimming) {
            this.swimming = false;
            System.out.println("The penguin stopped swimming!");
        } else {
            System.out.println("The penguin is not swimming!");
        }
    }
}

public class Ex1 {
    public static void main(String[] args) {
        List<Living> arr = new ArrayList<>();
        arr.add(new Duck());
        arr.add(new Penguin());
        for (Living living : arr) {
            living.live();
            ((Swimmable) living).swim();
        }
        for (Living living : arr) {
            ((Swimmable) living).stopSwimming();
        }
        // arr.add(new Submarine); // doesnt work
    }
}
