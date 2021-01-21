package designPatterns;

public class FactoryPattern {
    public static void main(String[] args){
        ShapeFactory.getShapeInstance("R").draw();
        ShapeFactory.getShapeInstance("C").draw();
        ShapeFactory.getShapeInstance("S").draw();
        ShapeFactory.getShapeInstance("X").draw();
    }
}

interface Shape {
    void draw();
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("This is a rectangle.");
    }
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("This is a circle");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("This is a square.");
    }
}

class ShapeFactory {
    public static Shape getShapeInstance(String shape) {
        if (shape.equals("")) return null;
        else if (shape.equals("R")) return new Rectangle();
        else if (shape.equals("C")) return new Circle();
        else if (shape.equals("S")) return new Square();
        else throw new RuntimeException("Undefined shape");
    }
}