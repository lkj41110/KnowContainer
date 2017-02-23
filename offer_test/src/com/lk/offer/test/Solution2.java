package com.lk.offer.test;

/**
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
 * 判断数组中是否含有该整数。
 */
public class Solution2 {
	public boolean Find1(int target, int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (target == array[i][j])
					return true;
			}
		}
		return false;
	}

	public boolean Find2(int target, int[][] array) {
		int row = 0;
		int col = array[0].length - 1;
		while (row <= array.length - 1 && col >= 0) {
			if (target == array[row][col])
				return true;
			else if (target > array[row][col])
				row++;
			else
				col--;
		}
		return false;
	}
}
