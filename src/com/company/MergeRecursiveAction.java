package com.company;

import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

public class MergeRecursiveAction extends RecursiveAction {

    int[] array;
    public MergeRecursiveAction(int[] array){
        this.array = array;
    }

    @Override
    protected void compute() {
        if(array.length>1){
            if(array.length%2 == 0) {
                int[] firstHalf = new int[array.length / 2];
                int[] secondHalf = new int[array.length / 2];
                for(int i = 0; i < array.length; i++){
                    if(i < array.length/2){
                        firstHalf[i] = array[i];
                    }else{
                        secondHalf[i-(array.length/2)] = array[i];
                    }
                }
                MergeRecursiveAction first = new MergeRecursiveAction(firstHalf);
                MergeRecursiveAction second = new MergeRecursiveAction(secondHalf);

                first.fork();
                second.fork();

                System.err.println(Thread.currentThread().toString() + " waiting... ");
                //wait
                first.join();
                second.join();
                array = combine(first.array, second.array);

            }else{
                int[] firstHalf = new int[array.length / 2];
                int[] secondHalf = new int[array.length / 2 + 1];
                for(int i = 0; i < array.length; i++){
                    if(i < array.length/2){
                        firstHalf[i] = array[i];
                    }else{
                        secondHalf[i-(array.length/2)] = array[i];
                    }
                }
                MergeRecursiveAction first = new MergeRecursiveAction(firstHalf);
                MergeRecursiveAction second = new MergeRecursiveAction(secondHalf);

                first.fork();
                second.fork();
                //wait
                System.err.println(Thread.currentThread().toString() + " waiting... ");
                first.join();
                second.join();
                array = combine(first.array, second.array);
            }
        }else {
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
