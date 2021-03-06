package com.lk.offer.test;
/**
 * HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。今天测试组开完会后,他又发话了:在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？
 * 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。你会不会被他忽悠住？
 */
public class Solution3 {
	public static void main(String[] args) {
//		int[] n = {1, -2, 3, 10, -4, 7, 2, -5};  
//        int[] n = {-2, -8, -1, -5, -9};  
        int[] n = {2, 8, 1, 5, 9};
		System.out.println("最大值为:" +solution(n));
	}
	
	private static int solution(int[] n){
		int max=Integer.MIN_VALUE,sum=max;
		for(int i=0;i<n.length;i++){
			if(sum<0){
				sum=0;
			}
			sum+=n[i];
			if(sum>max){
				max=sum;
			}
		}
		return max;
	}
	
}
