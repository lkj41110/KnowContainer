package com.lk.offer.test;

/**
 * 请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
 */
public class Solution11 {
	public void Mirror(TreeNode root) {
		Mirror0(root);
	}
	//递归
	public void Mirror0(TreeNode root) {
		if(root==null)
			return;
		if(root.left!=null)
			Mirror0(root.left);
		if(root.right!=null)
			Mirror0(root.right);
		TreeNode temp=root.left;
		root.left=root.right;
		root.right=temp;
	}
	//非递归
	public void Mirror1(TreeNode root) {
		
	}
}
