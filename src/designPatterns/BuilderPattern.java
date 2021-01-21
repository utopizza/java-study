package designPatterns;

public class BuilderPattern {
    public static void main(String[] args) {
        Builder hamburgerBuilder = new HamburgerBuilder();
        Director director = new Director(hamburgerBuilder);
        director.construct();

        Builder sandwichBuilder = new SandwichBuilder();
        director = new Director(sandwichBuilder);
        director.construct();
    }
}


interface Product {
}

interface Part {
}

interface Builder {
    void buildPartA();

    void buildPartB();

    void buildPartC();

    Product getResult();
}


// director of builders
class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product construct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}


// parts
class Bread implements Part {
    public Bread() {
        System.out.println("You build the bread.");
    }
}

class Vegetable implements Part {
    public Vegetable() {
        System.out.println("You build the vegetable.");
    }
}

class Beef implements Part {
    public Beef() {
        System.out.println("You build the beef.");
    }
}

class Fish implements Part {
    public Fish() {
        System.out.println("You build the fish.");
    }
}


// products
class Hamburger implements Product {
    Bread bread;
    Vegetable vegetable;
    Beef beef;
}

class Sandwich implements Product {
    Vegetable vegetable;
    Bread bread;
    Fish fish;
}


// builders
class HamburgerBuilder implements Builder {

    Hamburger hamburger;

    public HamburgerBuilder() {
        hamburger = new Hamburger();
    }

    @Override
    public void buildPartA() {
        hamburger.bread = new Bread();
    }

    @Override
    public void buildPartB() {
        hamburger.vegetable = new Vegetable();
    }

    @Override
    public void buildPartC() {
        hamburger.beef = new Beef();
    }

    @Override
    public Product getResult() {
        System.out.println("You finish building the hamburger!");
        return hamburger;
    }
}

class SandwichBuilder implements Builder {

    Sandwich sandwich;

    public SandwichBuilder() {
        sandwich = new Sandwich();
    }

    @Override
    public void buildPartA() {
        sandwich.bread = new Bread();
    }

    @Override
    public void buildPartB() {
        sandwich.vegetable = new Vegetable();
    }

    @Override
    public void buildPartC() {
        sandwich.fish = new Fish();
    }

    @Override
    public Product getResult() {
        System.out.println("You finish building the sandwich!");
        return sandwich;
    }
}



