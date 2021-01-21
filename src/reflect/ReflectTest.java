package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {
    public static void main(String[] args){
        try {
            // get class
            Class clazz = Text.class;
            System.out.println(clazz == new Text().getClass());

            // new instance by constructor without param
            Text t0 = (Text) clazz.newInstance();
            System.out.println(t0.id + "," + t0.content);

            // new instance by constructor with param
            Constructor constructor = clazz.getDeclaredConstructor(new Class[]{int.class, String.class});
            constructor.setAccessible(true);
            Text t1 = (Text) constructor.newInstance(new Object[]{1, "ABC"});
            System.out.println(t1.id + "," + t1.content);

            // get methods
            Method method = clazz.getDeclaredMethod("updateContent", String.class);
            method.setAccessible(true);
            method.invoke(t1, new Object[]{"DEF"});
            System.out.println(t1.id + "," + t1.content);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class Text {
    int id;
    String content;

    public Text() {
        this.id = 0;
        this.content = "NULL";
    }

    private Text(int id, String content) {
        this.id = id;
        this.content = content;
    }

    private void updateContent(String content) {
        this.content = content;
    }
}