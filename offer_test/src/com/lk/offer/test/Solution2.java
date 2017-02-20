package com.lk.offer.test;
/**
 * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
 */
public class Solution2 {
	public static void main(String[] args) {
		System.out.println(add(10));
	}
	
	private static int add(int n){
		int result=n;
		boolean flag=(n>0)&&((result+=add(n-1))>0);
		return result;
	}
	
}
