package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static final String INPUT_TXT = "/Day8input.txt";
    public static final String EX_TXT = "/Day8ex.txt";

    public static void main(String[] args) {
        Day8 day = new Day8();
        try {
//            day.executeFirst(INPUT_TXT);
//            day.executeFirst(EX_TXT);
            day.executeSecond(INPUT_TXT);
//            day.executeSecond(INPUT_TXT);
//            day.executeSecond(EX_TXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}