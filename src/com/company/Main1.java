package com.company;


import java.util.Arrays;
import java.util.Random;

public class Main1 {

    public static void main(String[] args) {
	    int[] toSort = new int[10000];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++){
            toSort[i] = r.nextInt(1000);
        }
        System.err.close();
        long start = System.currentTimeMillis();
        System.out.println("Input " + Arrays.toString(toSort));
	    toSort = MergeSort.sort(toSort);
	    long end = System.currentTimeMillis();
	    System.out.println("Time " + (end-start));
        System.out.println("Result " + Arrays.toString(toSort));
    }
}
