package com.lk.offer.test;

import java.util.ArrayList;

/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14
 * 15 16 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 */
public class Solution12 {
	public ArrayList<Integer> printMatrix(int[][] matrix) {
		ArrayList<Integer> list = new ArrayList<>();
		if (matrix == null)
			return null;
		int h = matrix.length;
		int w = matrix[0].length;
		int circle = ((h < w ? h : w) - 1) / 2 + 1;
		for (int i = 0; i < circle; i++) {
			for(int j=i;j<w-i;j++)
				list.add(matrix[i][j]);
			for(int m=i+1;m<h-i;m++)
				list.add(matrix[m][w-i-1]);
			for(int n=w-2-i;n>=i&&(h-i-1!=i);n--)
				list.add(matrix[h-i-1][n]);
			for(int x=h-2-i;x>i&&(w-i-1!=i);x--)
				list.add(matrix[x][i]);
		}
		return list;
	}
	public static void main(String[] args) {
		int[][] a={{1},{2},{3},{4},{5}};
		Solution12 b=new Solution12();
		System.out.println(b.printMatrix(a));
	}
}
