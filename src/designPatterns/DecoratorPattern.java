package designPatterns;

public class DecoratorPattern {
    public static void main(String[] args) {
        // I want a Espresso with Mocha*2 and Whip*1
        Beverage order1 = new WhipDecorator(new MochaDecorator(new MochaDecorator(new Espresso())));
        System.out.println(order1.getDescription() + " ; Cost : " + order1.cost());

        // I want a HouseBlend with Soy*1 and Milk*1
        Beverage order2 = new MilkDecorator(new SoyDecorator(new HouseBlend()));
        System.out.println(order2.getDescription() + " ; Cost : " + order2.cost());
    }
}

// abstract class
abstract class Beverage {
    String description = "Unknown Beverage.";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}

abstract class Decorator extends Beverage {
    public abstract String getDescription();
}


// basic beverage
class Espresso extends Beverage {
    public Espresso() {
        description = "basic beverage : Espresso";
    }

    public double cost() {
        return 2.5;
    }
}

class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "basic beverage : HouseBlend";
    }

    public double cost() {
        return 3.3;
    }
}


// decorator
class MochaDecorator extends Decorator {
    Beverage beverage;

    public MochaDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription() + " , added Mocha";
    }

    public double cost() {
        return beverage.cost() + 3.33;
    }
}

class SoyDecorator extends Decorator {
    Beverage beverage;

    public SoyDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription() + " , added Soy";
    }

    public double cost() {
        return beverage.cost() + 4.44;
    }
}

class WhipDecorator extends Decorator {
    Beverage beverage;

    public WhipDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription() + " , added Whip";
    }

    public double cost() {
        return beverage.cost() + 5.54;
    }
}

class MilkDecorator extends Decorator {
    Beverage beverage;

    public MilkDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription() + " , added Milk";
    }

    public double cost() {
        return beverage.cost() + 9.9;
    }
}