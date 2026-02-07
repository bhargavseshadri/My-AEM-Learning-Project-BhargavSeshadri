package com.aem.geeks.core.javarelatedcodes.junitrelated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddClassTest {

    @Test
    void basicTestMethod() {
        System.out.println("This I my test method");
    }

    //Test Method for Add Method
    @Test
    void addTestMethod (){
        AddClass addClassObj = new AddClass();

        int expected = 10;
        int actual = addClassObj.add(5, 5);
        assertEquals(expected, actual);

    }

}