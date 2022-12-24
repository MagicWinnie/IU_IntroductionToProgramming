import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    private Scanner scanner;

    private Animal receiveAnimal(String[] args) throws InvalidNumberOfAnimalParametersException,
            WeightOutOfBoundsException, SpeedOutOfBoundsException, EnergyOutOfBoundsException,
            InvalidInputsException {
        final int corrLength = 4;
        final int three = 3;
        if (args.length != corrLength) {
            throw new InvalidNumberOfAnimalParametersException();
        }
        String type = args[0];
        float weight;
        float speed;
        float energy;
        try {
            weight = Float.parseFloat(args[1]);
            speed = Float.parseFloat(args[2]);
            energy = Float.parseFloat(args[three]);
        } catch (Exception exception) {
            throw new InvalidInputsException();
        }
        Animal animal;
        switch (type) {
            case "Lion":
                animal = new Lion(weight, speed, energy);
                break;
            case "Zebra":
                animal = new Zebra(weight, speed, energy);
                break;
            case "Boar":
                animal = new Boar(weight, speed, energy);
                break;
            default:
                throw new InvalidInputsException();
        }
        return animal;
    }

    private List<Animal> readAnimals() throws InvalidNumberOfAnimalParametersException, WeightOutOfBoundsException,
            SpeedOutOfBoundsException, EnergyOutOfBoundsException, InvalidInputsException {
        List<Animal> animalList = new ArrayList<>();
        int n = 0;
        try {
            n = Integer.parseInt(this.scanner.nextLine());
        } catch (NumberFormatException | NoSuchElementException exception) {
            throw new InvalidInputsException();
        }
        final int maxN = 20;
        if (n < 1 || n > maxN) {
            throw new InvalidInputsException();
        }
        for (int i = 0; i < n; i++) {
            if (!this.scanner.hasNext()) {
                throw new InvalidInputsException();
            }
            try {
                String[] args = this.scanner.nextLine().split(" ");
                animalList.add(this.receiveAnimal(args));
            } catch (NumberFormatException | NoSuchElementException exception) {
                throw new InvalidNumberOfAnimalParametersException();
            }
        }
        return animalList;
    }

    private void removeDeadAnimals(List<Animal> lAnimals) {
        int index = 0;
        while (index < lAnimals.size()) {
            Animal a = lAnimals.get(index);
            if (a.getEnergy() <= 0F) {
                lAnimals.remove(index);
            } else {
                index++;
            }
        }
    }

    private void printAnimals(List<Animal> lAnimals) {
        for (Animal animal : lAnimals) {
            animal.makeSound();
        }
    }

    private void initScanner(String path) {
        try {
            this.scanner = new Scanner(new File(path));
        } catch (FileNotFoundException exception) {
            System.out.println("Input file does not exist");
            System.exit(0); // file always exists
        }
    }

    private void runSimulation(int d, float g, List<Animal> lAnimals) throws GrassOutOfBoundsException,
            InvalidInputsException {
        Field field = new Field(g);
        this.removeDeadAnimals(lAnimals);
        for (int i = 0; i < d; i++) {
            for (Animal animal : lAnimals) {
                if (animal.getEnergy() <= 0F) {
                    continue;
                }
                try {
                    animal.eat(lAnimals, field);
                } catch (SelfHuntingException | CannibalismException | TooStrongPreyException exception) {
                    System.out.println(exception.getMessage());
                }
            }
            for (Animal animal : lAnimals) {
                animal.decrementEnergy();
            }
            field.grassGrow();
            this.removeDeadAnimals(lAnimals);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.initScanner("input.txt");
        int d = 0;
        float g = 0F;
        final int maxDays = 30;
        try {
            d = Integer.parseInt(main.scanner.nextLine());
            if (d < 1 || d > maxDays) {
                throw new InvalidInputsException();
            }
            g = Float.parseFloat(main.scanner.nextLine());
        } catch (InvalidInputsException | NumberFormatException | NoSuchElementException exception) {
            System.out.println(new InvalidInputsException().getMessage());
            System.exit(0);
        }
        try {
            List<Animal> lAnimals = main.readAnimals();
            main.runSimulation(d, g, lAnimals);
            main.printAnimals(lAnimals);
        } catch (GrassOutOfBoundsException | InvalidNumberOfAnimalParametersException
                | InvalidInputsException | WeightOutOfBoundsException
                | SpeedOutOfBoundsException | EnergyOutOfBoundsException exception) {
            System.out.println(exception.getMessage());
            System.exit(0);
        }
        main.scanner.close();
    }
}

class WeightOutOfBoundsException extends Exception {
    WeightOutOfBoundsException() {
    }

    @Override
    public String getMessage() {
        return "The weight is out of bounds";
    }
}

class SpeedOutOfBoundsException extends Exception {
    SpeedOutOfBoundsException() {
    }

    @Override
    public String getMessage() {
        return "The speed is out of bounds";
    }
}

class EnergyOutOfBoundsException extends Exception {
    EnergyOutOfBoundsException() {
    }

    @Override
    public String getMessage() {
        return "The energy is out of bounds";
    }
}

class GrassOutOfBoundsException extends Exception {
    GrassOutOfBoundsException() {
    }

    @Override
    public String getMessage() {
        return "The grass is out of bounds";
    }
}

class InvalidInputsException extends Exception {
    InvalidInputsException() {
    }

    @Override
    public String getMessage() {
        return "Invalid inputs";
    }
}

class InvalidNumberOfAnimalParametersException extends Exception {
    InvalidNumberOfAnimalParametersException() {
    }

    @Override
    public String getMessage() {
        return "Invalid number of animal parameters";
    }
}

class SelfHuntingException extends Exception {
    SelfHuntingException() {
    }

    @Override
    public String getMessage() {
        return "Self-hunting is not allowed";
    }
}

class TooStrongPreyException extends Exception {
    TooStrongPreyException() {
    }

    @Override
    public String getMessage() {
        return "The prey is too strong or too fast to attack";
    }
}

class CannibalismException extends Exception {
    CannibalismException() {
    }

    @Override
    public String getMessage() {
        return "Cannibalism is not allowed";
    }
}

enum AnimalSound {
    LION("Roar"), ZEBRA("Ihoho"), BOAR("Oink");

    private final String sound;

    AnimalSound(String newSound) {
        this.sound = newSound;
    }

    public String getSound() {
        return this.sound;
    }
}

abstract class Animal {
    private final int minWeight = 5;
    private final int maxWeight = 200;
    private final int minSpeed = 5;
    private final int maxSpeed = 60;
    private final int minEnergy = 0;
    private final int maxEnergy = 100;

    private float weight;
    private float speed;
    private float energy;

    protected AnimalSound animalSound;

    protected Animal(float newWeight, float newSpeed, float newEnergy) throws WeightOutOfBoundsException,
            SpeedOutOfBoundsException, EnergyOutOfBoundsException {
        if (newWeight < this.minWeight || newWeight > this.maxWeight) {
            throw new WeightOutOfBoundsException();
        }
        if (newSpeed < this.minSpeed || newSpeed > this.maxSpeed) {
            throw new SpeedOutOfBoundsException();
        }
        if (newEnergy < this.minEnergy || newEnergy > this.maxEnergy) {
            throw new EnergyOutOfBoundsException();
        }
        this.weight = newWeight;
        this.speed = newSpeed;
        this.energy = newEnergy;
    }

    public void decrementEnergy() {
        this.energy -= 1F;
        if (this.energy < this.minEnergy) {
            this.energy = 0F;
        }
    }

    public float getWeight() {
        return this.weight;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getEnergy() {
        return this.energy;
    }

    public void setEnergy(float newEnergy) {
        this.energy = newEnergy;
    }

    public void makeSound() {
        System.out.println(this.animalSound.getSound());
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + this.energy + "," + this.speed + "," + this.weight + ")";
    }

    public abstract void eat(List<Animal> listAnimals, Field field) throws SelfHuntingException,
            TooStrongPreyException, CannibalismException, InvalidInputsException;
}

interface Carnivore<T> {
    default Animal choosePrey(List<Animal> listAnimals, T hunter) {
        int index = listAnimals.indexOf(hunter);
        int delta = 1;
        Animal nextAnimal = listAnimals.get((index + delta) % listAnimals.size());
        while (nextAnimal.getEnergy() <= 0F) {
            delta++;
            nextAnimal = listAnimals.get((index + delta) % listAnimals.size());
        }
        return nextAnimal;
    }

    default void huntPrey(Animal hunter, Animal victim) throws SelfHuntingException,
            TooStrongPreyException, CannibalismException, InvalidInputsException {
        if (hunter == null || victim == null) {
            throw new InvalidInputsException();
        } else if (hunter == victim) {
            throw new SelfHuntingException();
        } else if (hunter.getClass().equals(victim.getClass())) {
            throw new CannibalismException();
        } else if (victim.getSpeed() >= hunter.getSpeed() && victim.getEnergy() >= hunter.getEnergy()) {
            throw new TooStrongPreyException();
        }
        final int maxAmount = 100;
        hunter.setEnergy(hunter.getEnergy() + victim.getWeight());
        if (hunter.getEnergy() > maxAmount) {
            hunter.setEnergy(maxAmount);
        }
        victim.setEnergy(0F);
    }
}

interface Herbivore {
    default void grazeInTheField(Animal animal, Field field) {
        final float coeff = 0.1F;
        final int maxAmount = 100;
        if (field.getGrassAmount() > coeff * animal.getWeight()) {
            animal.setEnergy(animal.getEnergy() + coeff * animal.getWeight());
            if (animal.getEnergy() > maxAmount) {
                animal.setEnergy(maxAmount);
            }
            field.setGrassAmount(field.getGrassAmount() - coeff * animal.getWeight());
        }
    }
}

interface Omnivore<T> extends Carnivore<T>, Herbivore {
}

class Lion extends Animal implements Carnivore<Lion> {
    Lion(float newWeight, float newSpeed, float newEnergy) throws WeightOutOfBoundsException,
            SpeedOutOfBoundsException, EnergyOutOfBoundsException {
        super(newWeight, newSpeed, newEnergy);
        this.animalSound = AnimalSound.LION;
    }

    public void eat(List<Animal> listAnimals, Field field) throws SelfHuntingException,
            TooStrongPreyException, CannibalismException, InvalidInputsException {
        Animal toEat = this.choosePrey(listAnimals, this);
        this.huntPrey(this, toEat);
    }
}

class Zebra extends Animal implements Herbivore {
    Zebra(float newWeight, float newSpeed, float newEnergy) throws WeightOutOfBoundsException,
            SpeedOutOfBoundsException, EnergyOutOfBoundsException {
        super(newWeight, newSpeed, newEnergy);
        this.animalSound = AnimalSound.ZEBRA;
    }

    public void eat(List<Animal> listAnimals, Field field) throws SelfHuntingException,
            TooStrongPreyException, CannibalismException, InvalidInputsException {
        this.grazeInTheField(this, field);
    }
}

class Boar extends Animal implements Omnivore<Boar> {
    Boar(float newWeight, float newSpeed, float newEnergy) throws WeightOutOfBoundsException,
            SpeedOutOfBoundsException, EnergyOutOfBoundsException {
        super(newWeight, newSpeed, newEnergy);
        this.animalSound = AnimalSound.BOAR;
    }

    public void eat(List<Animal> listAnimals, Field field) throws SelfHuntingException,
            TooStrongPreyException, CannibalismException, InvalidInputsException {
        this.grazeInTheField(this, field);
        Animal toEat = this.choosePrey(listAnimals, this);
        this.huntPrey(this, toEat);
    }
}

class Field {
    private final int minAmountOfGrass = 0;
    private final int maxAmountOfGrass = 100;
    private float grassAmount;

    Field(float newGrassAmount) throws GrassOutOfBoundsException {
        if (newGrassAmount < this.minAmountOfGrass || newGrassAmount > this.maxAmountOfGrass) {
            throw new GrassOutOfBoundsException();
        }
        this.grassAmount = newGrassAmount;
    }

    public void grassGrow() {
        this.grassAmount *= 2;
        if (this.grassAmount > this.maxAmountOfGrass) {
            this.grassAmount = this.maxAmountOfGrass;
        }
    }

    public void setGrassAmount(float newGrassAmount) {
        this.grassAmount = newGrassAmount;
    }

    public float getGrassAmount() {
        return this.grassAmount;
    }
}
