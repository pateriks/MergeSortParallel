package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoindMain3 {
    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool();
        int[] toSort = new int[10000];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++){
            toSort[i] = r.nextInt(1000);
        }
        System.err.close();
        System.out.println("Input " + Arrays.toString(toSort));
        long start = System.currentTimeMillis();
        MergeRecursiveAction sortAction = new MergeRecursiveAction(toSort);
        pool.invoke(sortAction);
        sortAction.join();
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end-start));
        System.out.println("Result " + Arrays.toString(sortAction.array));

    }
}
