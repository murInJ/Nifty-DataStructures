import java.util.ArrayList;
import java.util.Arrays;

/**
 * @title: sortTest
 * @Author MurInj
 * @Date: 2022/9/1 1:39
 * @Version 1.0
 */

public class sort {
    public static void main(String[] args) {
        int len = 10000;
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) arr[i] = (int) (Math.random() * 100000);

        int[] arr1 = arr.clone();
        long startTime = System.currentTimeMillis();
        BubbleSort_origin(arr1);
        long endTime = System.currentTimeMillis();
        System.out.println("BubbleSort_origin耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        bubbleSort_upgrade(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("BubbleSort_upgrade耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        selectionSort_oneWay(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("selectionSort_oneWay耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        selectionSort_twoWay(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("selectionSort_twoWay耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        insertionSort(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("insertionSort耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        ShellSort(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("ShellSort耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        mergeSort(arr1,0,arr1.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("mergeSort耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        quickSort(arr1,0,arr1.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("quickSort耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        HeapSort(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("heapSort耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        CountingSort(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("CountingSort耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        bucketsort(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("bucketSort耗时: " + (endTime - startTime) + " ns");

        arr1 = arr.clone();
        startTime = System.currentTimeMillis();
        RadixSort(arr1);
        endTime = System.currentTimeMillis();
        System.out.println("RadixSort耗时: " + (endTime - startTime) + " ns");

//        System.out.println(Arrays.toString(arr1));

    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void BubbleSort_origin(int[] arr) {
        int len = arr.length;
        if (len == 0) return;
        for (int i = 0; i < len; i++)
            for (int j = 0; j < len - 1 - i; j++)
                if (arr[j + 1] < arr[j]) swap(arr, j, j + 1);
    }

    public static void bubbleSort_upgrade(int[] arr) {
        if (arr == null) return;
        int lastExchangeIndex = 0;
        int sortBorder = arr.length - 1;

        for (int i = 0; i < arr.length - 1; i++) {
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (arr[j + 1] < arr[j]) {
                    isSorted = false;
                    swap(arr, j, j + 1);
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if (isSorted) break;
        }

    }

    public static void selectionSort_oneWay(int[] arr) {
        if (arr.length == 0) return;
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i; j < arr.length; j++)
                if (arr[j] < arr[minIndex]) minIndex = j;
            swap(arr, minIndex, i);
        }
    }

    public static void selectionSort_twoWay(int[] arr) {
        int begin = 0;
        int end = arr.length - 1;
        while (begin <= end) {
            int minIndex = begin;
            int maxIndex = end;
            for (int i = begin; i <= end; i++) {
                if (arr[i] < arr[minIndex]) minIndex = i;
                if (arr[i] > arr[maxIndex]) maxIndex = i;
            }
            swap(arr, minIndex, begin);
            if (maxIndex == begin) maxIndex = minIndex;
            swap(arr, maxIndex, end);
            begin++;
            end--;
        }

    }

    public static void insertionSort(int[] arr) {
        if (arr.length == 0) return;
        int current;
        for (int i = 0; i < arr.length - 1; i++) {
            current = arr[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && current < arr[preIndex]) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
    }

    public static void ShellSort(int[] array) {
        int len = array.length;
        int temp, gap = len >> 1;
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && array[preIndex] > temp) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
            }
            gap >>= 1;
        }
    }

    public static void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) >> 1;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
            int i_start = start;
            int j_start = mid + 1;
            int[] temp = new int[end - start + 1];
            int len = 0;
            while (i_start <= mid && j_start <= end) {
                temp[len++]=arr[i_start]<arr[j_start]?arr[i_start++]:arr[j_start++];
            }

            while (i_start <= mid) temp[len++] = arr[i_start++];
            while (j_start <= end) temp[len++] = arr[j_start++];

            System.arraycopy(temp, 0, arr, start, temp.length);
        }
    }


    public static void quickSort(int[] arr, int begin, int end){
        if(begin > end) return;
        int tmp = arr[begin];
        int i = begin;
        int j = end;
        while (i!=j){
            while (arr[j] >= tmp && j > i) j--;
            while(arr[i] <= tmp && j > i) i++;
            if(j > i) swap(arr,i,j);
        }
        swap(arr,i,begin);
        quickSort(arr,begin,i-1);
        quickSort(arr,i+1,end);
    }

   public static void HeapSort(int[] arr){
        for(int i = arr.length / 2 - 1;i >= 0;i--)  adjustHeap(arr,i,arr.length);
        for(int j = arr.length - 1;j > 0;j--){
            swap(arr,0,j);
            adjustHeap(arr,0,j);
        }
   }

   public static void adjustHeap(int[] arr,int i,int length){
        int tmp = arr[i];
        for(int k = i * 2 + 1;k < length;k = k*2+1){
            if(k+1<length && arr[k] < arr[k+1]) k++;
            if(arr[k] > tmp){
                arr[i] = arr[k];
                i = k;
            }
            else break;
        }
        arr[i] = tmp;
   }

    public static void CountingSort(int[] arr) {
        if (arr.length == 0) return;
        int maxValue = arr[0];
        for (int value : arr) maxValue = Math.max(maxValue,value);
        int bucketLen = maxValue + 1;
        int[] bucket = new int[bucketLen];
        for (int value : arr) bucket[value]++;
        int sortedIndex = 0;
        for (int j = 0; j < bucketLen; j++) {
            while (bucket[j] > 0) {
                arr[sortedIndex++] = j;
                bucket[j]--;
            }
        }
    }

    public static void bucketsort(int[] arr) {
        int maxValue = arr[0];
        int minValue = arr[0];
        for (int value : arr){
            maxValue = Math.max(maxValue,value);
            minValue = Math.min(minValue,value);
        }
        ArrayList<Integer>[] bucket = new ArrayList[(maxValue - minValue) / arr.length + 1];
        for (int i = 0; i < bucket.length; i++) bucket[i] = new ArrayList<Integer>();

        for (int j : arr) bucket[(j - minValue) / (arr.length)].add(j);

        int sortedIndex = 0;
        for (ArrayList<Integer> arrayList : bucket) {
            arrayList.sort(null);
            for (int o : arrayList) {// 遍历桶中的元素并输出
                arr[sortedIndex++] = o;
            }
        }
    }

    public static void RadixSort(int[] array) {
        if (array == null || array.length < 2) return;
        int max = array[0];
        for (int i = 1; i < array.length; i++) max = Math.max(max, array[i]);
        int maxDigit = 0;
        while (max != 0) {
            max /= 10;
            maxDigit++;
        }

        int mod = 10, div = 1;
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 10; i++) bucketList.add(new ArrayList<Integer>());
        for (int i = 0; i < maxDigit; i++, mod *= 10, div *= 10) {
            for (int value : array) {
                int num = (value % mod) / div;
                bucketList.get(num).add(value);
            }
            int index = 0;
            for (ArrayList<Integer> integers : bucketList) {
                for (Integer integer : integers) array[index++] = integer;
                integers.clear();
            }
        }
    }
}