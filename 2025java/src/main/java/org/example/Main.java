package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static final String DAY_1_FIRST_TXT = "/Day1first.txt";
    public static final String DAY_1_EX_TXT = "/Day1ex.txt";

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Day1 day1 = new Day1();
        try {
            day1.executeSecond(DAY_1_FIRST_TXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}