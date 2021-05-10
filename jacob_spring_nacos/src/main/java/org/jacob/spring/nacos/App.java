package org.jacob.spring.nacos;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(10);
        list.add("333");

        for (String s : list) {
            System.out.println(s);
        }
        for (String s : list) {
            System.out.println(s);
            System.out.println("s = " + s);
            System.out.println("s = " + s);
        }
    }


}
