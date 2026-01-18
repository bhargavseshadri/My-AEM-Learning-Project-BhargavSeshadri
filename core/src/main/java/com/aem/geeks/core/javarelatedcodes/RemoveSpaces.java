package com.aem.geeks.core.javarelatedcodes;

public class RemoveSpaces {
    String text = "Hello World Java";
    String result = text.replace(" ", "");
     //System.out.println(result); //Output:  HelloWorldJava


    //Removing leading and trailing spaces
    String textString = "   Hello World   ";
    String resultString = text.trim();
    //System.out.println(resultString);  // Hello World


}
