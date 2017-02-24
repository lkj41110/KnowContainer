package com.lk.offer.test;

/**
 * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项。 n<=39
 */
public class Solution7 {
	// 递归版
	public int Fibonacci(int n) {
		if (n == 1 || n == 2)
			return 1;
		return Fibonacci(n - 1) + Fibonacci(n - 2);
	}

	public int Fibonacci1(int n) {
		if (n <= 1)
			return n;
		int[] a = new int[n + 1];
		a[1] = 1;
		a[2] = 1;
		for (int i = 3; i <= n; i++) {
			a[i] = a[i - 1] + a[i - 2];
		}
		return a[n];
	}

	public static void main(String[] args) {
		Solution7 a = new Solution7();
		System.out.println(a.Fibonacci1(2));
	}
}
