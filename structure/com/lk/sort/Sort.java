package com.lk.sort;

/**
 * 排序,都从小到大
 * 
 * @author lkj41110
 * @version time：2017年2月24日 下午2:45:49
 */
public class Sort {
	/**
	 * 选择排序
	 */
	public static void selectionSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int min = arr[i];
			int k = i;
			// 找出最小值
			for (int j = i + 1; j < arr.length; j++) {
				if (min > arr[j]) {
					min = arr[j];
					k = j;
				}
			}
			// 交换位置
			if (k != i) {
				int temp = arr[k];
				arr[k] = arr[i];
				arr[i] = temp;
			}
		}
	}

	/**
	 * 插入排序
	 */
	public static void bubbleSort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			int k = i - 1, temp = arr[i];
			while (k >= 0) {
				if (temp >= arr[k]) {
					break;
				}
				arr[k + 1] = arr[k];
				k--;
			}
			arr[k + 1] = temp;
		}
	}

	/**
	 * 希尔排序
	 */
	public static void shellSort(int[] arr) {
		int n;// 步长
		for (n = arr.length / 2; n > 0; n /= 2) {
			// 插入排序
			for (int i = n; i < arr.length; i++) {
				int temp = arr[i];
				int k = i - n;
				while (k >= 0 && temp < arr[k]) {
					arr[k + n] = arr[k];
					k = k - n;
				}
				arr[k + n] = temp;
			}
		}
	}

	/**
	 * 快速排序
	 */
	public static void quicksort(int[] arr) {
		quicksort0(arr, 0, arr.length - 1);
	}

	private static void quicksort0(int[] arr, int s, int e) {
		if (s >= e)
			return;
		int temp = arr[s];
		int i = s, j = e;
		while (i < j) {
			while (i < j && arr[j] >= temp) {
				j--;
			}
			if (i < j) {
				arr[i] = arr[j];
			}
			while (i < j && arr[i] <= temp) {
				i++;
			}
			if (i < j) {
				arr[j] = arr[i];
			}
		}
		arr[i] = temp;
		quicksort0(arr, s, i - 1);
		quicksort0(arr, i + 1, e);
	}

	// 归并排序
	public static void mergesort(int[] arr) {
		mergesort0(arr, 0, arr.length-1);
	}

	private static void mergesort0(int[] arr, int s, int e) {
		if (s < e) {
			int mid = (s + e) / 2;
			mergesort0(arr, s, mid); // 左边有序
			mergesort0(arr, mid + 1, e); // 右边有序
			mergearray(arr, s, mid, e); // 再将二个有序数列合并
		}
	}

	private static void mergearray(int[] arr, int s, int mid, int e) {
		// 临时数组
		int[] arr2 = new int[e - s + 1];
		int i = s, j = mid + 1;
		int k = 0;
		while (i <= mid && j <= e) {
			if (arr[i] < arr[j]) {
				arr2[k++] = arr[i++];
			} else {
				arr2[k++] = arr[j++];
			}
		}
		while (i <= mid) {
			arr2[k++] = arr[i++];
		}
		while (j <= e) {
			arr2[k++] = arr[j++];
		}
		for (int m = 0; m < arr2.length; m++) {
			arr[s + m] = arr2[m];
		}
	}

	public static void main(String[] args) {
//		int[] arr = { -10, 0, 12, 1, 100, 32, 1, -200 };
		int[] arr={3,2,1};
		mergesort(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}

	}
}
