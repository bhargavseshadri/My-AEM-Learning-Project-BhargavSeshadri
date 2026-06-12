package com.aem.geeks.core.javarelatedcodes.hardConcepts.association.ExampleTwo;

public class Main {

    public static void main(String[] args) {

        Teacher teacher = new Teacher("Ravi");

        Student student = new Student("John", teacher);

        student.display();
    }
}
