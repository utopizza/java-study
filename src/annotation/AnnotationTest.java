package annotation;

public class AnnotationTest {
    public static void main(String[] args) {

        //Student student = new Student();
        //MyAnnotationProcessor.process(student);

        MyAnnotationProcessor.process("annotation.Student");
    }
}

class Student {

    @MyAnnotation(name = "studentId", value = 9527, description = "identity of a student")
    private int id;

    @MyAnnotation(name = "student method", description = "the student is studying")
    public void study() {
        System.out.println("I am studying.");
    }

    @MyAnnotation(name = "student method", description = "the student is playing")
    public void play() {
        System.out.println("I am playing.");
    }

}