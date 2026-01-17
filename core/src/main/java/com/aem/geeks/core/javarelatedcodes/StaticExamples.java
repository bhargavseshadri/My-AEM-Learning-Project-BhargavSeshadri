package com.aem.geeks.core.javarelatedcodes;

public class StaticExamples {

    //accessing Static variable and static method in non static method
    static int count = 5;        // static variable

    static void showCount() {    // static method
        System.out.println("Count = " + count);
    }

    void display() {             // non-static method
        // Accessing static variable
        System.out.println(count);

        // Accessing static method
        showCount();
    }

    /********************************************************************************/

    //accessing non-Static variable and non-static method in static method
    int x = 10;          // non-static variable
    void showX() {       // non-static method
        System.out.println(x);
    }

    static void ExampleStaticMethod() {   // static method

        // create object
        StaticExamples obj = new StaticExamples();

        // Access non-static variable
        System.out.println(obj.x);

        // Access non-static method
        obj.showX();
    }
}
