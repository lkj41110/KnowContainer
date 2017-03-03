package com.lk.offer.test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 */
public class test {

	static class ListNode {
		int val;
		ListNode next = null;

		ListNode(int val) {
			this.val = val;
		}
	}

	public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
		ArrayList<Integer> list = new ArrayList<>();
		if (root == null)
			return list;
		LinkedList<TreeNode> linkedList = new LinkedList<>();
		linkedList.add(root);
		while (!linkedList.isEmpty()) {
			TreeNode n=linkedList.removeFirst();
			list.add(n.val);
			if(n.left!=null)
				linkedList.add(n.left);
			if(n.right!=null)
				linkedList.add(n.right);
		}
		return list;
	}
	public static void main(String[] args) {
		test a=new test();
	}

}
