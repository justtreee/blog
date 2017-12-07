package generic;

import org.junit.Test;

/**
 * @Author Treee
 * @E-mail ss673290035ss@gmail.com
 * @Date -2017/12/6-
 */

public class Prac1 {
    //填写一个泛型方法，实现指定位置上的数组元素交换
    public <T> void swap(T arr[], int p1, int p2){
        T tmp = arr[p1];
        arr[p1] = arr[p2];
        arr[p2] = tmp;
    }

    //编写一个泛型方法，接受一个任意数组，并颠倒数组中的所有元素
    public <T> void reverse(T arr[]){
        int l = 0;
        int r = arr.length-1;

        while(l<r){
            T tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;

            l++;r--;
        }

    }
}
