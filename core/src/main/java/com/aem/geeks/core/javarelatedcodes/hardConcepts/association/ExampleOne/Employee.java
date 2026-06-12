package com.aem.geeks.core.javarelatedcodes.hardConcepts.association.ExampleOne;

import java.util.ArrayList;
import java.util.List;


//Not Alone -
class Employee {

    private String name;

    // Many-to-One
    private Department department;

    // One-to-One
    private IDCard idCard;

    // Many-to-Many
    private List<Project> projects = new ArrayList<>();

    public Employee(String name, Department department, IDCard idCard) {
        this.name = name;
        this.department = department;
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void displayDetails() {

        System.out.println("\nEmployee : " + name);

        System.out.println("Department : " + department.getName());

        System.out.println("ID Card : " + idCard.getCardNumber());

        System.out.println("Projects:");

        for (Project p : projects) {
            System.out.println("- " + p.getName());
        }
    }
}
