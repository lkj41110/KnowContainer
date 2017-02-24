package com.lk.offer.test;

/**
 * 根据二叉树的先序和中序遍历，得出树的结构
 */
public class Solution5 {
	public static void main(String[] args) {
		int[] pre={1,2,4,7,3,5,6,8};
		int[] in={4,7,2,1,5,3,8,6};
		Solution5 a=new Solution5();
		TreeNode no=a.reConstructBinaryTree(pre,in);
		System.out.println(no);
	}

	public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
		return preIn(pre, 0, pre.length - 1, in, 0, in.length - 1);
	}

	public TreeNode preIn(int[] pre, int pre_s, int pre_e, int[] in, int in_s, int in_e) {
		if(pre_s>pre_e)
			return null;
		else if(pre_s==pre_e){
			return new TreeNode(pre[pre_s]);
		}
		TreeNode n = new TreeNode(pre[pre_s]);
		int j = in_s;
		while (j <= in_e) {
			if (in[j] == pre[pre_s]) {
				break;
			}
			j++;
		}
		n.left = preIn(pre, pre_s + 1, j - in_s + pre_s, in, in_s, j-1);
		n.right = preIn(pre, j - in_s + 1 + pre_s, pre_e, in, j + 1, in_e);
		return n;
	}
}

class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	TreeNode(int x) {
		val = x;
	}
}
