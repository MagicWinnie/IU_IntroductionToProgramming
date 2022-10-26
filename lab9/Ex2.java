import java.lang.Math;

public class Ex2 {
    abstract class Shape {
        abstract float area();
        abstract float perimeter();
    }
    class Ellipse extends Shape {
        private final float a, b;

        public Ellipse(float a, float b) {
            this.a = a;
            this.b = b;
        }
        public float area() {
            return (float)Math.PI * this.a * this.b;
        }
        public float perimeter() {
            return 2F * (float)Math.PI * (float)Math.sqrt((this.a * this.a + this.b * this.b) / 2F);
        }
    }
    class Circle extends Ellipse {
        public Circle(float a) {
            super(a, a);
        }
    }
    class Rectangle extends Shape {
        private final float a, b;
        public Rectangle(float a, float b) {
            this.a = a;
            this.b = b;
        }
        public float area() {
            return this.a * this.b;
        }
        public float perimeter() {
            return 2F * (this.a + this.b);
        }
    }
    class Square extends Rectangle {
        public Square(float a) {
            super(a, a);
        }
    }
    class Triangle extends Shape {
        private final float a, b, c;
        public Triangle(float a, float b, float c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        public float area() {
            float p = this.perimeter() / 2F;
            return (float)Math.sqrt(p * (p - a) * (p - b) * (p - c));
        }
        public float perimeter() {
            return this.a + this.b + this.c;
        }
    }
    public static void main(String[] args) {

    }
}
