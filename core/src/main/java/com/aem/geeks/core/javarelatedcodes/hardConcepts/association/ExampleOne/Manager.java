package com.aem.geeks.core.javarelatedcodes.hardConcepts.association.ExampleOne;


//Not Alone - using method association
class Manager {

    private String name;

    public Manager(String name) {
        this.name = name;
    }

    public void assignProject(Employee employee, Project project) {

        employee.addProject(project);

        System.out.println(name + " assigned " + project.getName() + " to " + employee.getName());
    }
}
