package com.company;
import com.company.*;


public class Main {

    public static void main(String[] args) {
        AutomatonGrid toRun = new AutomatonGridType2(10, 10);
        toRun.gridFill(.2);
        System.out.println("Initial Grid: \n");
        System.out.println(toRun.toString() + "\n");
        for (int i = 0; i < 100; i++) {
            toRun.runIteration();
            System.out.println(toRun.toString() + "\n");
        }

    }
}
