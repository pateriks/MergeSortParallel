package com.company;

import java.util.concurrent.*;

public class MergeExecutor implements Runnable {
    private final ExecutorService pool;
    private Integer [] workArray;
    Future<Integer[]> result;

    public Integer[] getWorkArray() {
        return workArray;
    }

    public MergeExecutor(int[] array){
        pool = Executors.newFixedThreadPool(array.length/2+array.length/4);
        workArray = new Integer[array.length];
        for(int i = 0; i < array.length; i++) {
            workArray[i] = array[i];
        }
    }

    @Override
    public void run() {
        try {
            result = pool.submit(new MergeHandler(pool, workArray));
        } catch (Exception e){
            pool.shutdown();
        }
    }

    public class MergeHandler implements Callable<Integer[]> {
        ExecutorService pool;
        Integer[] array;
        public MergeHandler (ExecutorService pool, Integer [] array){
            this.pool = pool;
            this.array = array;
        }

        @Override
        public Integer[] call() throws Exception {
            if(array.length>1){
                if(array.length%2 == 0) {
                    Integer[] firstHalf = new Integer[array.length / 2];
                    Integer[] secondHalf = new Integer[array.length / 2];
                    for(int i = 0; i < array.length; i++){
                        if(i < array.length/2){
                            firstHalf[i] = array[i];
                        }else{
                            secondHalf[i-(array.length/2)] = array[i];
                        }
                    }
                    Future<Integer[]> result = pool.submit(new MergeHandler(pool, firstHalf));
                    Future<Integer[]> result2 = pool.submit(new MergeHandler(pool, secondHalf));
                    //wait
                    firstHalf = result.get();
                    secondHalf = result2.get();
                    array = combine(firstHalf, secondHalf);
                    return array;
                }else{
                    Integer[] firstHalf = new Integer[array.length / 2];
                    Integer[] secondHalf = new Integer[array.length / 2 + 1];
                    for(int i = 0; i < array.length; i++){
                        if(i < array.length/2){
                            firstHalf[i] = array[i];
                        }else{
                            secondHalf[i-(array.length/2)] = array[i];
                        }
                    }
                    Future<Integer[]> result = pool.submit(new MergeHandler(pool, firstHalf));
                    Future<Integer[]> result2 = pool.submit(new MergeHandler(pool, secondHalf));
                    System.err.println(Thread.currentThread().toString() + " waiting... ");
                    //wait
                    firstHalf = result.get();
                    secondHalf = result2.get();
                    array = combine(firstHalf, secondHalf);
                    return array;
                }
            }else {
                return array;
            }
        }
    }

    public void await (){
        while (true) {
            try {
                workArray = result.get(10000, TimeUnit.MILLISECONDS);
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            } catch (ExecutionException e) {
                e.printStackTrace();
                break;
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public static Integer[] combine(Integer[] first, Integer[] second) {
        Integer[] combined = new Integer[first.length + second.length];
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

