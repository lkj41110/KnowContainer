package com.lk.offer.test;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
 */
public class Solution8 {

	// 斐波那契数列，只能上1或2级的方法
	public int JumpFloor(int target) {
		if (target <= 2)
			return target;
		int[] n = new int[target + 1];
		n[1] = 1;
		n[2] = 2;
		for (int i = 3; i <= target; i++)
			n[i] = n[i - 1] + n[i - 2];
		return n[target];
	}

	// 斐波那契数列，只能上n级的方法
	public int JumpFloor1(int target) {
		if (target <= 2)
			return target;
		int[] n = new int[target + 1];
		n[1] = 1;
		n[2] = 2;
		for (int i = 3; i <= target; i++){
			n[i]=1;
			for(int j=0;j<i;j++){
				n[i]+=n[j];
			}
		}
		return n[target];
	}

	public static void main(String[] args) {
		Solution8 a = new Solution8();
		System.out.println(a.JumpFloor1(4));
	}
}
