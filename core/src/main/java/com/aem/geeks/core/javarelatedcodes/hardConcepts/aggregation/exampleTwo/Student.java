package com.aem.geeks.core.javarelatedcodes.hardConcepts.aggregation.exampleTwo;

class Student {

    private String name;
    private Teacher teacher;

    public Student(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public void display() {
        System.out.println(name + " is taught by " + teacher.getName());
    }
}
