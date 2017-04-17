package com.ysu.zyw.tc.demos.bin;

import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class TcSortAlgorithmsBin {

    public static void insertionSort() {

    }

    public static void insertionSortRecursive() {

    }

    public static <T> void bubbleSort(@Nonnull T[] array, @Nonnull Comparator<T> comparator) {
        checkNotNull(array);
        checkNotNull(comparator);

        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (comparator.compare(array[i], array[j]) > 0) {
                    swap(array, i, j);
                }
            }
        }
    }

    public static void bubbleSortRecursive() {

    }

    public static void selectionSort() {

    }

    public static void selectionSortRecursive() {

    }

    public static void mergeSort() {

    }

    public static void quickSort() {

    }

    public static void heapSort() {

    }

    public static void countingSort() {

    }

    public static void bucketSort() {

    }

    private static <T> void swap(@Nonnull T[] array, int lt, int rt) {
        checkNotNull(array);
        checkArgument(lt < array.length);
        checkArgument(rt < array.length);

        T temp = array[lt];
        array[lt] = array[rt];
        array[rt] = temp;
    }

    public static void main(String[] args) {
        final int ARRAY_LENGTH = 20;
        Integer[] array = new Integer[ARRAY_LENGTH];
        Stream.generate(() -> RandomUtils.nextInt(0, 20))
                .limit(ARRAY_LENGTH)
                .collect(Collectors.toList())
                .toArray(array);

        bubbleSort(array, (Comparator.comparingInt(o -> o)));

        System.out.println(Arrays.toString(array));
    }

}
