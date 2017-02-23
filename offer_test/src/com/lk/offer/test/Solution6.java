package com.lk.offer.test;

import java.util.Stack;

/**
 * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 */
public class Solution6 {
	Stack<Integer> stack1 = new Stack<Integer>();
	Stack<Integer> stack2 = new Stack<Integer>();
	boolean push = true;

	public void push(int node) {
		if (!push) {
			transform(stack2, stack1);
		}
		push=true;
		stack1.push(node);
	}

	public int pop() {
		if (push) {
			transform(stack1, stack2);
		}
		push=false;
		if(stack2.isEmpty())
			return 0;
		return stack2.pop();
	}
	
	private void transform(Stack<Integer> s1, Stack<Integer> s2) {
		while (!s1.isEmpty()) {
			s2.push(s1.pop());
		}
	}
	
	public static void main(String[] args) {
		Solution6 s=new Solution6();
		s.push(1);
		s.push(3);
		System.out.println(s.pop());
		s.push(4);
		s.push(6);
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.pop());
		
	}
}
