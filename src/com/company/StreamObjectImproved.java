package com.company;

import javafx.concurrent.Task;

public class StreamObjectImproved extends Task<int[]>{

    int[] workArray;
    int start;
    int end;

    public StreamObjectImproved (int[] workArray, int start, int end){
        this.workArray = workArray;
        this.start = start;
        this.end = end;
    }

    protected int[] call() throws Exception {
        return sort();
    }

    private int[] sort(){
        int count = start;
        int interval = end-start;
        int[] first = new int[interval/2];
        int[] second = new int[interval/2];
        int place = 0;
        while (count < end-interval/2) {
            first[place++] = workArray[count++];
        }
        place = 0;
        System.err.println("End is: " + end);
        while (count < end){
            second[place++] = workArray[count++];
        }

        return combine(first, second);

    }
    public static int[] combine(int[] first, int[] second) {
        int[] combined = new int[first.length + second.length];
        int i = 0;
        int j = 0;
        int places = 0;
        while (places < first.length + second.length
                && i < first.length && j < second.length){
            if(first[i] > second[j]) {
                combined[places++] = second[j++];
            }else{
                combined[places++] = first[i++];
            }
        }
        while (i < first.length && places < first.length + second.length){
            combined[places++] = first[i++];
        }
        while (j < second.length && places < first.length + second.length){
            combined[places++] = second[j++];
        }
        System.err.println("combine variable i = " + i);
        System.err.println("combine variable j = " + j);
        System.err.println("combine variable places = " + places);
        return combined;
    }
}
