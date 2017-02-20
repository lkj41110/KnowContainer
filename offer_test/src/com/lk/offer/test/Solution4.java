package com.lk.offer.test;
/**
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
 */
public class Solution4 {
	public static void main(String[] args) {
		int[] n={1,2,3,5,6,7,4};
		System.out.println(solution(n, 0, n.length-1));
	}
	
	//判断从start到end是否符合二叉树性质
	private static boolean solution(int[] n,int start,int end){
		if(start>=end) return true;
		int last=n[end],i=start;
		while(n[i]<last&&i<end){
			i++;
		}
		int mid=i-1;
		while(n[i]>=last&&i<end){
			i++;
		}
		if(i==end&&solution(n,start,mid)&&solution(n, mid+1, end-1)){
			return true;
		}
		return false;
	}
	
}
