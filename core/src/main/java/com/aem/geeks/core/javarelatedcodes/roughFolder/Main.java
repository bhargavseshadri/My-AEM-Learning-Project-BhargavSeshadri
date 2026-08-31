package com.aem.geeks.core.javarelatedcodes.roughFolder;

import java.util.*;


public class Main {

    public static void main(String[] args) {




        Main.process(154);
    }

    public static void process(Object data) {

        if (data instanceof String) {
            String value = (String) data;
            System.out.println("The Val is String : "+ value);
        }

        if (data instanceof Integer) {
            int value = (int) data;
            System.out.println("The Val is Integer : "+ value);
        }
    }
}
