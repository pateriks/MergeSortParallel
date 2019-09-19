package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ParallelMain4 {

    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool();
        int[] toSort = new int[16384];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 16384; i++){
            toSort[i] = r.nextInt(1000);
        }
        System.err.close();

        System.out.println("Input " + Arrays.toString(toSort));

        long start = System.currentTimeMillis();

        List<StreamObjectImproved> toSortList = new ArrayList<>();


        int i = 0;
        int interval = 2;
        while (interval<=toSort.length) {
            i = 0;
            System.err.println("Check loop");
            while (i+interval <= toSort.length) {
                StreamObjectImproved insert = new StreamObjectImproved(toSort, i, i + interval);
                toSortList.add(insert);
                i += interval;
            }
            System.err.println("Check loop 2");
            toSortList.forEach(streamObjectImproved -> {
                try {
                    int[] replace = streamObjectImproved.call();
                    int count = 0;
                    while (count < streamObjectImproved.end-streamObjectImproved.start) {
                        toSort[streamObjectImproved.start + count] = replace[count];
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            System.err.println("Check loop 3");

            toSortList.clear();
            interval = 2*interval;
        }

        long end = System.currentTimeMillis();
        System.out.println("Result in " + (end - start) + " \nResult" + Arrays.toString(toSort));
    }
}
