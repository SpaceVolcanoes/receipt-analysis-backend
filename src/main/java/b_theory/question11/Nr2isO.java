package b_theory.question11;

public class Nr2isO {
    // A
    // What does O stand for in SOLID? Explain the principle.
    //      O stands for Open-closed principle.
    //      It means that software pieces should be open for extension but remain closed modification.
    //      aka you should build your classes in a way that allows you to extend them via child classes and inheritance
    //      and once created they no longer need to be changed.
    //      This principle allows to scale the code without wasting time on legacy classes.
}

// B
// Give an example. Write actual or pseudo code.

// a VIOLATION of the Open-closed principle
// if we want to add 10 more shapes we would have to modify this function and it would become very big

/*
public double perimeter(Shape[] shapes) {
    double perimeter = 0;
    for (Shape shape : shapes) {
        if (shape.getClass() == Rectangle) {
            Rectangle rect = (Rectangle) shape;
            perimeter += (rect.width + rect.height) * 2;
        } else {
            Circle circle = (Circle) shape;
            perimeter += Math.PI * circle.radius * 2;
        }
    }
    return perimeter;
}
*/

// the RIGHT WAY of doing this, using the Open-closed principle
// if we need to add more shapes the perimeter method stays the same
// perimeter method is closed for modification
/*
abstract class Shape {
    public abstract double perimeter();
}
class Rectangle extends Shape {
    public double width, height;
    @Override
    public double perimeter() {
        return 2 * (width + height);
    }
}
class Circle extends Shape {
    public double radius;
    @Override
    public double perimeter() {
        return Math.PI * radius * 2;
    }
}
public double perimeter(Shape[] shapes) {
    double perimeter = 0;
    for (Shape shape : shapes) {
        perimeter += shape.perimeter();
    }
    return perimeter;
}
*/
