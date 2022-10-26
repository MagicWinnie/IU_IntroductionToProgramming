public class Ex1 {
    static class Animal {
        private String name, color, sound;
        private int height, weight;
        private int soundWeightLoose, nightHeightGrowth;

        public Animal(String name, String color, String sound, int height, int weight, int soundWeightLoose, int nightHeightGrowth) {
            setName(name);
            setColor(color);
            setHeight(height);
            setWeight(weight);
            setSound(sound);
            setSoundWeightLoose(soundWeightLoose);
            setNightHeightGroth(nightHeightGrowth);
        }
        
        public void eat(int food) {
            System.out.println("You have eaten and gained " + food + " weight");
            this.weight += food;
        }

        public void sleep() {
            System.out.println("You have sleeped and gained " + this.nightHeightGrowth + " in height");
            this.height += this.nightHeightGrowth;
        }

        public void makeSound() {
            System.out.println(this.sound + "! and you have lost " + this.soundWeightLoose + " weight");
            this.weight -= this.soundWeightLoose;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        
        public void setColor(String color) {
            this.color = color;
        }
        public String getColor() {
            return this.color;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }
        public String getSound() {
            return this.sound;
        }

        public void setHeight(int height) {
            this.height = height;
        }
        public int getHeight() {
            return this.height;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
        public int getWeight() {
            return this.weight;
        }

        public void setNightHeightGroth(int h) {
            this.nightHeightGrowth = h;
        }
        public int getNightHeightGroth() {
            return this.nightHeightGrowth;
        }

        public void setSoundWeightLoose(int w) {
            this.soundWeightLoose = w;
        }
        public int getSoundWeightLoose() {
            return this.soundWeightLoose;
        }
    }

    static class Cow extends Animal {
        public Cow(int height, int weight, int soundWeightLoose, int nightHeightGrowth) {
            super("Cow", "Black and white", "Moo", height, weight, soundWeightLoose, nightHeightGrowth);
        }
    }

    static class Cat extends Animal {
        public Cat(int height, int weight, int soundWeightLoose, int nightHeightGrowth) {
            super("Cat", "Red", "Meow", height, weight, soundWeightLoose, nightHeightGrowth);
        }
    }

    static class Dog extends Animal {
        public Dog(int height, int weight, int soundWeightLoose, int nightHeightGrowth) {
            super("Dog", "White", "Woof", height, weight, soundWeightLoose, nightHeightGrowth);
        }
    }
    
    public static void main(String[] args) {
        Dog dog = new Dog(120, 230, 10, 21);
        dog.makeSound();
    }
}
