package designPatterns;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyPattern {
    public static void main(String[] args) {
        //System.out.println("Static proxy:");
        //TeacherProxy teacherProxy = new TeacherProxy(new EnglishTeacher());
        //teacherProxy.teach();
        //StudentProxy studentProxy = new StudentProxy(new LazyStudent());
        //studentProxy.study();

        System.out.println("Dynamic proxy:");
        // way 1
        Teacher dynamicTeacherProxy = (Teacher) Proxy.newProxyInstance(Teacher.class.getClassLoader(), new Class[]{Teacher.class}, new DynamicHandler(new EnglishTeacher()));
        dynamicTeacherProxy.teach();
        // way 2
        Student s = new GoodStudent();
        Student dynamicStudentProxy = (Student) Proxy.newProxyInstance(s.getClass().getClassLoader(), s.getClass().getInterfaces(), new DynamicHandler(s));
        dynamicStudentProxy.study();
    }
}

interface Teacher {
    void teach();

    void rest();
}

interface Student {
    void study();

    void rest();
}


class EnglishTeacher implements Teacher {

    @Override
    public void teach() {
        System.out.println("I am English teacher. I am teaching English.");
    }

    @Override
    public void rest() {
        System.out.println("I am English teacher. I am resting.");
    }
}

class HistoryTeacher implements Teacher {
    @Override
    public void teach() {
        System.out.println("I am history teacher. I am teaching history.");
    }

    @Override
    public void rest() {
        System.out.println("I am history teacher. I am resting.");
    }
}

class GoodStudent implements Student {
    @Override
    public void study() {
        System.out.println("I am a good student. I am studying hard.");
    }

    @Override
    public void rest() {
        System.out.println("I am a good student. I don't need to rest.");
    }
}

class LazyStudent implements Student {
    @Override
    public void study() {
        System.out.println("I am a lazy student. I don't like studying.");
    }

    @Override
    public void rest() {
        System.out.println("I am a lazy student. I am resting.");
    }
}


// static proxy: You have to implement a proxy class for each interface
class TeacherProxy implements Teacher {

    Teacher teacher;

    public TeacherProxy(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public void teach() {
        before();
        teacher.teach();
        after();
    }

    @Override
    public void rest() {
        before();
        teacher.rest();
        after();
    }

    public void before() {
        System.out.println("I am teacher proxy. This is before function.");
    }

    public void after() {
        System.out.println("I am teacher proxy. This is after function.");
    }
}

class StudentProxy implements Student {
    Student student;

    public StudentProxy(Student student) {
        this.student = student;
    }

    @Override
    public void study() {
        before();
        student.study();
        after();
    }

    @Override
    public void rest() {
        before();
        student.rest();
        after();
    }

    public void before() {
        System.out.println("I am student proxy. This is before function.");
    }

    public void after() {
        System.out.println("I am student proxy. This is after function.");
    }
}


// dynamic proxy: You can just implement only one handler for all interfaces
class DynamicHandler implements InvocationHandler {
    Object object;

    public DynamicHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(object, args);
        after();
        return null;
    }

    public void before() {
        System.out.println("I am dynamic proxy. This is before function.");
    }

    public void after() {
        System.out.println("I am dynamic proxy. This is after function.");
    }
}