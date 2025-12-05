package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static final String INPUT_TXT = "/Day5input.txt";
    public static final String EX_TXT = "/Day5ex.txt";

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Day5 day = new Day5();
        try {
            day.executeFirst(INPUT_TXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}