package com.example.administrator.myrestfulapp;

import android.util.Log;

import java.util.Arrays;

/**
 * 快速排序算法：
 * 对于任意一个无序数组， 我们随机选一个元素作为基准元素，
 * 将小于或等于 基准元素的放在基准元素左边， 将大于基准元素的放在基准元素的右边，当全部
 * 比较完后，基准元素在数组中的位置就确定下来了， 然后分别对左边数组 和右边数组递归进行
 * 上面的操作， 直到每个元素的位置唯一确定下来。
 *{2, 3, 7, 8, 5, 1, 9, 6}
 *{2, 3, 7, 8, 5, 1, 9, 6}
 *{2, 3, 7, 8, 5, 1, 9, 6}
 *{2, 3, 7, 8, 5, 1, 9, 6}
 *{2, 3, 5, 8, 7, 1, 9, 6}
 *{2, 3, 5, 1, 7, 8, 9, 6}
 *{2, 3, 5, 1, 6, 8, 9, 7}
 */


public class FastSort {

    public static void main(String[] args) {
        int[] arr = {2, 3, 7, 8, 5, 1, 9, 6};
        findBasePos(arr, 0, arr.length - 1);
        Log.d("FastSort", Arrays.toString(arr));
    }


    public static void quickStart(int[] arr, int start, int end){
        if (end - start < 1){
            return;
        }
        int basePos = findBasePos(arr, start, end);

        if (basePos == start) {
            findBasePos(arr, basePos + 1, end);
        }else if(basePos == end){
            findBasePos(arr, basePos, end - 1);
        }else {
            findBasePos(arr, start, basePos - 1);
            findBasePos(arr, basePos + 1, end);
        }

    }

    public static int findBasePos(int[] arr, int start, int end) {
        //选取基准值 暂时我们选取数组的最后一个为基准值
        int base = arr[end];

        //这里我们假设要比较的元素都不小于基准元素，这样通过比较
        //就把小于基准元素的数据全部找到，n=start表示的就是默认没有小于基准元素
        int n = start;

        for (int i = start; i < end; i++) {
            if (arr[i] < base) {
                if (i != n){
                    exchangeIndex(arr, i, n);
                }
                n++;
            }
        }
        exchangeIndex(arr, end, n);
        return n;
    }


    public static void exchangeIndex(int[] arr, int index1, int index2) {
        if (index1 == index2) return;
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}


