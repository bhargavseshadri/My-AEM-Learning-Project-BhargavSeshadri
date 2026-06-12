package com.aem.geeks.core.javarelatedcodes.hardConcepts.association.ExampleOne;

public class Main {

    public static void main(String[] args) {

        Department engineering = new Department("Engineering");
        Employee john = new Employee("John", engineering, new IDCard("EMP101"));
        Employee david = new Employee("David", engineering, new IDCard("EMP102"));
        engineering.addEmployee(john);
        engineering.addEmployee(david);


        Department qa = new Department("QA");
        IDCard idCard = new IDCard("EMP103");
        Employee mike = new Employee("Mike", qa, idCard);
        qa.addEmployee(mike);


        Project aem = new Project("AEM Migration");
        Project cloud = new Project("Cloud Upgrade");

        Manager manager = new Manager("Robert");


        manager.assignProject(john, cloud);
        manager.assignProject(david, cloud);
        manager.assignProject(mike, aem);

        john.displayDetails();
        david.displayDetails();
        mike.displayDetails();

        engineering.displayEmployees();
        qa.displayEmployees();
    }
}
