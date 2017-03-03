package com.lk.offer.test;

/**
 * 输入两个链表，找出它们的第一个公共结点。
 */
public class Solution14 {
	public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
		int l1=0,l2=0;
		ListNode n1,n2;
		n1=pHead1;
		n2=pHead2;
		//求两条链表的长度
		while(n1!=null){
			l1++;
			n1=n1.next;
		}
		while(n2!=null){
			l2++;
			n2=n2.next;
		}
		//让两条链表一样长，在比较第一个一样的
		if(l1>l2){
			int temp=l1-l2;
			while(temp-->0){
				pHead1=pHead1.next;
			}
		}else{
			int temp=l2-l1;
			while(temp-->0){
				pHead2=pHead2.next;
			}
		}
		while(pHead1!=null&&pHead2!=null){
			if(pHead1.val==pHead2.val)
				return pHead1;
			pHead1=pHead1.next;
			pHead2=pHead2.next;
		}
		return null;
	}

	public static void main(String[] args) {
		int[] a = { 4, 5, 1, 6, 2, 7, 3, 8 };
		Solution14 b = new Solution14();
		// System.out.println(b.GetLeastNumbers_Solution(a,10));
	}
}
