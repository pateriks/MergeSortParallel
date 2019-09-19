package com.company;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class StreamObject extends Task<int[]> {
    int[] array;
    boolean done = false;
    public StreamObject(int[] array){
        this.array = array;
    }

    public int[] getArray() {
        while(done==false){
        }
        return array;
    }

    protected int[] call() {
        if (array.length > 1) {
            if (array.length % 2 == 0) {
                int[] firstHalf = new int[array.length / 2];
                int[] secondHalf = new int[array.length / 2];
                for (int i = 0; i < array.length; i++) {
                    if (i < array.length / 2) {
                        firstHalf[i] = array[i];
                    } else {
                        secondHalf[i - (array.length / 2)] = array[i];
                    }
                }
                StreamObject first = new StreamObject(firstHalf);
                StreamObject second = new StreamObject(secondHalf);

                List<StreamObject> list = new ArrayList<StreamObject>();
                list.add(first);
                list.add(second);

                list.parallelStream().forEach(new Consumer<StreamObject>() {
                    @Override
                    public void accept(StreamObject streamObject) {
                        streamObject.call();
                    }
                });

                array = combine(first.getArray(), second.getArray());

                done = true;
                return array;
            } else {
                int[] firstHalf = new int[array.length / 2];
                int[] secondHalf = new int[array.length / 2 + 1];
                for (int i = 0; i < array.length; i++) {
                    if (i < array.length / 2) {
                        firstHalf[i] = array[i];
                    } else {
                        secondHalf[i - (array.length / 2)] = array[i];
                    }
                }
                StreamObject first = new StreamObject(firstHalf);
                StreamObject second = new StreamObject(secondHalf);

                List<StreamObject> list = new ArrayList<StreamObject>();
                list.add(first);
                list.add(second);

                list.parallelStream().forEach(new Consumer<StreamObject>() {
                    @Override
                    public void accept(StreamObject streamObject) {
                        streamObject.call();
                    }
                });

                array = combine(first.getArray(), second.getArray());
                done = true;
                return array;
            }
        } else {
            done = true;
            return array;
        }
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
