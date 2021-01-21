package designPatterns;

public class AbstractFactoryPattern {
    public static void main(String[] args) {
        FactoryProducer.getFactory("COLOR").getColor("R").paint();
    }
}

interface Color {
    void paint();
}

class Red implements Color {
    @Override
    public void paint() {
        System.out.println("You paint it red.");
    }
}

class Blue implements Color {
    @Override
    public void paint() {
        System.out.println("You paint it blue.");
    }
}

class Green implements Color {
    @Override
    public void paint() {
        System.out.println("You paint it green.");
    }
}

class ColorFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shape) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        if (color.equals("")) return null;
        else if (color.equals("R")) return new Red();
        else if (color.equals("B")) return new Blue();
        else if (color.equals("G")) return new Green();
        else throw new RuntimeException("Undefined Color");
    }
}


abstract class AbstractFactory {
    public abstract Shape getShape(String shape);

    public abstract Color getColor(String color);
}

class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {

        if (choice.equalsIgnoreCase("SHAPE")) {
            //return new ShapeFactory();
        } else if (choice.equalsIgnoreCase("COLOR")) {
            return new ColorFactory();
        }

        return null;
    }
}