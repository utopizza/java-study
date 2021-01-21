package designPatterns;

public class AdapterPattern {
    public static void main(String[] args) {
        Duck myDuck = new YellowDuck();
        myDuck.quack();

        Duck fakeDuck = new TurkeyToDuckAdapter(new WildTurkey());
        fakeDuck.quack();
    }
}

interface Duck {
    void quack();
}

interface Turkey {
    void gobble();
}

class YellowDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("Quacking.");
    }
}

class WildTurkey implements Turkey {
    @Override
    public void gobble() {
        System.out.println("Gobbling.");
    }
}

class TurkeyToDuckAdapter implements Duck {
    Turkey turkey;

    public TurkeyToDuckAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }
}