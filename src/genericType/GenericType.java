package genericType;

import java.util.ArrayList;
import java.util.List;

public class GenericType {
    public static void main(String[] args) {

    }
}

class ReasonToUseGenericType {

    // only work for String class
    class Box {
        private String item;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }
    }

    // can work for all kind of class
    // but you have to change the item type to Object
    // and check type by yourself everywhere
    class Box2 {
        private Object item;

        public Object getItem() {
            return item;
        }

        public void setItem(Object item) {
            this.item = item;
        }
    }

    // can work for all kind of class
    // let user to define what kind of class this container will work for
    // compiler will do the check type job
    class Box3<T> {
        private T item;

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }
    }

}

class ExtendsAndSuper {

    static class Food {
    }

    static class Fruit extends Food {
    }

    static class Apple extends Fruit {
    }

    public void Extends() {
        List<? extends Fruit> fruits = new ArrayList<>();

        // 因为接受的类型是Fruit的任意子类（超集），此时add方法是向下转型，编译器拒绝这种不安全转型
        //fruits.add(new Apple());
        //fruits.add(new Fruit());
        //fruits.add(new Food());
        fruits.add(null);

        // 父类引用可以指向子类对象，所以可以取出元素
        //Apple apple = fruits.get(0);
        Fruit fruit = fruits.get(0);
        Food food = fruits.get(0);
    }

    public void Super() {
        List<? super Fruit> fruits = new ArrayList<>();

        // 因为接受的类型是Fruit的任意父类（子集），此时add方法是向上转型，是安全的
        fruits.add(new Apple());
        fruits.add(new Fruit());
        //fruits.add(new Food());

        // 子类引用不能指向父类对象，因此无法取出元素
        //Apple apple = fruits.get(0);
        //Fruit fruit = fruits.get(0);
        //Food food = fruits.get(0);
    }
}