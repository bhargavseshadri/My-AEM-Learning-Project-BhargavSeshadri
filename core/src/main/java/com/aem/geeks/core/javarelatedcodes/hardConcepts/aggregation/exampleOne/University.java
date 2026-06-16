package com.aem.geeks.core.javarelatedcodes.hardConcepts.aggregation.exampleOne;

class University {
    String universityName;
    Professor[] professors; // Aggregation

    University(String universityName, Professor[] professors) {
        this.universityName = universityName;
        this.professors = professors;
    }

    void showProfessors() {
        System.out.println("University: " + universityName);

        for (Professor p : professors) {
            System.out.println(p.name);
        }
    }
}
