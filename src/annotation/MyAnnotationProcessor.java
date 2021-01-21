package annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyAnnotationProcessor {

    public static void process(Object object) {
        Class clazz = object.getClass();
        processMyAnnotation(clazz);
    }

    public static void process(String className) {
        Class clazz = null;
        try {
            clazz = MyAnnotationProcessor.class.getClassLoader().loadClass(className);
            processMyAnnotation(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void processMyAnnotation(Class clazz) {

        // analyse field annotations
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(MyAnnotation.class)) continue;
            MyAnnotation annotation = field.getAnnotation(MyAnnotation.class);
            System.out.println("annotation name:" + annotation.name() + " value:" + annotation.value() + " description:" + annotation.description());
        }

        // analyse method annotations
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(MyAnnotation.class)) continue;
            MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
            System.out.println("annotation name:" + annotation.name() + " value:" + annotation.value() + " description:" + annotation.description());
        }

    }
}
