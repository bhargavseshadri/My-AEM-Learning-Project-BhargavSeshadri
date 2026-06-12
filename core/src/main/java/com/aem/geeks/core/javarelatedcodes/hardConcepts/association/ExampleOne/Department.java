package com.aem.geeks.core.javarelatedcodes.hardConcepts.association.ExampleOne;

import java.util.ArrayList;
import java.util.List;

//Not Alone
class Department {

    private String name;

    private List<Employee> employees = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void displayEmployees() {

        System.out.println( "\nDepartment : " + name);

        for(Employee e : employees) {
            System.out.println(e.getName());
        }
    }
}
