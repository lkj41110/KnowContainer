package com.lk.offer.test;

import java.util.ArrayList;

/**
 * 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,
 */
public class Solution13 {
	public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
		ArrayList<Integer> list=new ArrayList<>();
		if(input.length<k) return list;
		quicksort(input,0,input.length-1,list,k);
		for(int i=0;i<k;i++){
			list.add(input[i]);
		}
		return list;
	}
	//类似快速排序
	private void quicksort(int[] input, int s, int end, ArrayList<Integer> list,int k) {
		if(s>end) return ;
		if(s==end){
			return ;
		}
		int temp=input[s];
		int i=s,e=end;
		//快速排序
		while(i<e){
			while(e>i&&temp<=input[e]){
				e--;
			}
			input[i++]=input[e];
			while(e>i&&temp>=input[i]){
				i++;
			}
			input[e]=input[i];
		}
		input[e]=temp;
		//如果k==e，说明e之前的都是小于e的，k〉e还需要排序后面的，e〉k要排序前面的
		if(e==k)
			return;
		else if(e<k){
			quicksort(input, e+1, end, list, k);
		}else{
			quicksort(input, s, e-1, list, k);
		}
	}

	public static void main(String[] args) {
		int[] a={4,5,1,6,2,7,3,8};
		Solution13 b = new Solution13();
		System.out.println(b.GetLeastNumbers_Solution(a,4));
	}
}
