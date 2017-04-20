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

    public static <T> void quickSort(T[] arr, Comparator<T> comparator) {
        quickSort(arr, 0, arr.length - 1, comparator);
    }

    public static <T> void quickSort3Ways(@Nonnull T[] arr, Comparator<T> comparator) {
        quickSort(arr, 0, arr.length - 1, comparator);
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

    //对arr[left...right]部分进行快速排序
    private static <T> void quickSort(T[] arr, int left, int right, Comparator<T> comparator) {
        if (left >= right)
            return;
        int p = setPartition2(arr, left, right, comparator);
        quickSort(arr, left, p, comparator);
        quickSort(arr, p + 1, right, comparator);
    }

    //对arr[l...r]部分进行patition操作
    //返回p，使得arr[l...p-1] < arr[p],arr[p+1...r] > arr[p]
    private static <T> int setPartition(T[] arr, int l, int r, Comparator<T> comparator) {

        //快速排序优化-随即化快速排序法
        swap(arr, l, RandomUtils.nextInt(l, r));

        T v = arr[l];
        //arr[l+1...j] < v, arr[j+1...i) > v
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (comparator.compare(arr[i], v) < 0) {
                swap(arr, j + 1, i);
                j++;
            }
        }
        swap(arr, l, j);
        return j;
    }

    //对arr[l...r]部分进行patition操作
    //返回p，使得arr[l...p-1] < arr[p],arr[p+1...r] > arr[p]
    //双路快速排序
    private static <T> int setPartition2(T[] arr, int l, int r, Comparator<T> comparator) {

        swap(arr, l, RandomUtils.nextInt(l, r));

        T v = arr[l];
        int i = l + 1, j = r;
        while (true) {
            while (i <= r && comparator.compare(arr[i], v) < 0)
                i++;
            while (j >= l + 1 && comparator.compare(arr[j], v) > 0)
                j--;
            if (i > j)
                break;
            swap(arr, i, j);
            i++;
            j--;
        }
        swap(arr, l, j);
        return j;
    }

    // 三路快速排序处理 arr[l...r]
    // 将arr[l...r]分为 <v ; ==v ; >v 三部分
    // 之后递归对 <v ; >v 两部分继续进行三路递归排序
    private static <T> void quickSort3Ways(T[] arr, int l, int r, Comparator<T> comparator) {
        if (l >= r)
            return;

        // patition
        swap(arr, l, RandomUtils.nextInt(l, r));
        T v = arr[l];

        int lt = l; //arr[l+1...lt] < v
        int gt = r + 1; //arr[gt...r] > v
        int i = l + 1; //arr[lt+1...i) == v
        while (i < gt) {
            if (comparator.compare(arr[i], v) < 0) {
                swap(arr, lt + 1, i);
                lt++;
                i++;
            } else if (comparator.compare(arr[i], v) > 0) {
                swap(arr, i, gt - 1);
                gt--;
            } else {
                i++;
            }
        }
        swap(arr, l, lt);

        quickSort3Ways(arr, l, lt - 1, comparator);
        quickSort3Ways(arr, gt, r, comparator);
    }

    public static void main(String[] args) {
        final int ARRAY_LENGTH = 1000000;
        Integer[] array = new Integer[ARRAY_LENGTH];
        Stream.generate(() -> RandomUtils.nextInt(0, 20000))
                .limit(ARRAY_LENGTH)
                .collect(Collectors.toList())
                .toArray(array);

        long timing = TcUtils.doWithTiming(() -> {
            quickSort(array, Comparator.comparingInt(o -> o));
            //bubbleSort(array, (Comparator.comparingInt(o -> o)));
        });
        System.out.println(timing);


        //System.out.println(Arrays.toString(array));
    }

}
