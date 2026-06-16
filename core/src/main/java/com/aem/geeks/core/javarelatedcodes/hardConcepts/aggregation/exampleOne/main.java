package com.aem.geeks.core.javarelatedcodes.hardConcepts.aggregation.exampleOne;

public class main {
    public static void main(String[] args) {

        Professor p1 = new Professor("Dr. Kumar");
        Professor p2 = new Professor("Dr. Sharma");

        Professor[] profs = {p1, p2};

        University u = new University("ABC University", profs);

        u.showProfessors();
    }
}
