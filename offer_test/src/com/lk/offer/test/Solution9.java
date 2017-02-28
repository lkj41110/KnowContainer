package com.lk.offer.test;

/**
 * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
 */
public class Solution9 {

	public ListNode Merge(ListNode list1, ListNode list2) {
		ListNode root = null,n=null;
		ListNode temp=null;
		while (list1 != null && list2 != null) {
			if(list1.val<list2.val){
				temp=new ListNode(list1.val);
				list1=list1.next;
			}else{
				temp=new ListNode(list2.val);
				list2=list2.next;
			}
			if(n==null){
				n=temp;
				root=n;
			}else{
				n.next=temp;
				n=n.next;
			}
		}
		while(list1!=null){
			temp=new ListNode(list1.val);
			list1=list1.next;
			if(n==null){
				n=temp;
				root=n;
			}else{
				n.next=temp;
				n=n.next;
			}
		}
		while(list2!=null){
			temp=new ListNode(list2.val);
			list2=list2.next;
			if(n==null){
				n=temp;
				root=n;
			}else{
				n.next=temp;
				n=n.next;
			}
		}
		return root;
	}

	public static void main(String[] args) {
		ListNode n1=new ListNode(1);
		ListNode n3=new ListNode(3);
		ListNode n2=new ListNode(2);
		ListNode n4=new ListNode(4);
		n1.next=n3;
		n2.next=n4;
		Solution9 a=new Solution9();
		System.out.println(a.Merge(n1,n2));
	}
}

class ListNode {
	int val;
	ListNode next = null;

	ListNode(int val) {
		this.val = val;
	}
	
}
