package com.company;

public class MergeSort {


    public static int[] sort(int [] array){
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
                firstHalf = sort(firstHalf);
                secondHalf = sort(secondHalf);
                array = combine(firstHalf, secondHalf);
                return array;

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
                firstHalf = sort(firstHalf);
                secondHalf = sort(secondHalf);
                array = combine(firstHalf, secondHalf);
                return array;
            }
        }else {
            return array;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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
