package com.ysu.zyw.tc.demos.bin;

import com.ysu.zyw.tc.base.utils.TcUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nonnull;
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

    }

    public static void bubbleSortRecursive() {

    }

    public static <T> void selectionSort(@Nonnull T[] array, @Nonnull Comparator<T> comparator) {
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

    public static void selectionSortRecursive() {

    }

    public static <T> void mergeSort(@Nonnull T[] array, Comparator<T> comparator) {
        mergeSort(array, 0, array.length - 1, comparator);
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

    private static <T> void mergeSort(@Nonnull T[] array, int left, int right, Comparator<T> comparator) {
        if (left >= right) {
            return;
        }

        int middle = (right + left) / 2;
        mergeSort(array, left, middle, comparator);
        mergeSort(array, middle + 1, right, comparator);
        merge(array, left, middle, right, comparator);

    }

    private static <T> void merge(@Nonnull T[] array, int left, int middle, int right, Comparator<T> comparator) {
        //noinspection unchecked
        T[] aux = (T[]) new Object[right - left + 1];
        System.arraycopy(array, left, aux, 0, right + 1 - left);
        int leftIndex = left, rightIndex = middle + 1;
        for (int real = left; real <= right; real++) {
            if (leftIndex > middle) {
                array[real] = aux[rightIndex - left];
                rightIndex++;
            } else if (rightIndex > right) {
                array[real] = aux[leftIndex - left];
                leftIndex++;
            } else if (comparator.compare(aux[leftIndex - left], aux[rightIndex - left]) < 0) {
                array[real] = aux[leftIndex - left];
                leftIndex++;
            } else {
                array[real] = aux[rightIndex - left];
                rightIndex++;
            }
        }
    }

    public static void main(String[] args) {
        final int ARRAY_LENGTH = 20000;
        Integer[] array = new Integer[ARRAY_LENGTH];
        Stream.generate(() -> RandomUtils.nextInt(0, 20000))
                .limit(ARRAY_LENGTH)
                .collect(Collectors.toList())
                .toArray(array);

        long timing = TcUtils.doWithTiming(() -> {
            mergeSort(array, Comparator.comparingInt(o -> o));
            //bubbleSort(array, (Comparator.comparingInt(o -> o)));
        });
        System.out.println(timing);


        //System.out.println(Arrays.toString(array));
    }

}
