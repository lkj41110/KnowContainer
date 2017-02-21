package com.lk.tree;

import java.util.Comparator;

public class MyRBTree<E> {
	// 定义节点
	private static class Node<E> {
		E e;
		Node<E> parent;
		Node<E> left;
		Node<E> right;
		boolean color;

		public Node(E e, Node<E> parent, Node<E> left, Node<E> right, boolean color) {
			this.e = e;
			this.left = left;
			this.right = right;
			this.color = color;
		}
	}

	// 如果调用了不带参数的构造函数，则使用该内部类作为比较器，
	// 但此时泛型E需要继承Comparable接口,否则运行时会抛出异常
	private static class Cpr<T> implements Comparator<T> {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public int compare(T e1, T e2) {
			return ((Comparable) e1).compareTo(e2);
		}

	}

	private final boolean RED = true;
	private final boolean BlACK = false;
	private final Comparator<E> cpr;

	private Node<E> root = null;
	private int size = 0;

	// 不带比较器的构造函数
	public MyRBTree() {
		cpr = new Cpr<E>();
	}

	// 带比较器的构造函数
	public MyRBTree(Comparator<E> cpr) {
		if (cpr == null) {
			throw new IllegalArgumentException();
		}
		this.cpr = cpr;
	}

	/**
	 * 先类似二叉树添加，在调整
	 */
	public E add(E e) {
		Node<E> node = root, parent;
		if (node == null) {
			root = new Node<E>(e, null, null, null, BlACK);
			size++;
			return null;
		}
		int cmp;
		// 有无比较器
		do {
			parent = node;
			cmp = cpr.compare(e, node.e);
			if (cmp < 0) {
				node = node.left;
			} else if (cmp > 0) {
				node = node.right;
			} else {
				// 已经有了
				return e;
			}
		} while (node != null);
		Node<E> n = new Node<E>(e, parent, null, null, RED);
		if (cmp < 0) {
			parent.left = n;
		} else {
			parent.right = n;
		}
		// 插入
		size++;
		// 调整
		fixAfterInsertion(n);
		return null;
	}

	/**
	 * 红黑树插入后调整 1.父黑或者自己为根结点，正常 2.父红叔红（分左右） 3.父红叔黑（分左右) 也可以参考TreeMap的调整发
	 */
	private void fixAfterInsertion(Node<E> e) {
		e.color = RED;
		while (e != null && e != root && e.color == RED) {
			Node<E> u,p,g;
			
			if (e.parent == e.parent.parent.left) {
				uncle = e.parent.parent.right;
			} else
				uncle = e.parent.parent.left;
			// 父红叔红
			if (uncle.color == RED) {
				uncle.color = BlACK;
				e.parent.color = BlACK;
				e.parent.parent.color = RED;
				// 向上回溯
				e = e.parent.parent;
			} else {
				// 父红叔黑，需要旋转，四种情况
				if (e.parent == e.parent.parent.left) {
					if (e == e.parent.left) {
						rotateRight(e.parent);
						
					} else {

					}
				} else {
					if (e == e.parent.left) {

					} else {

					}
				}

			}
		}
		root.color = BlACK;
	}

	/**
	 * 右旋
	 */
	private void rotateRight(Node<E> p) {
		if (p != null) {
			Node<E> l = p.left;
			p.left = l.right;
			if (l.right != null)
				l.right.parent = p;
			l.parent = p.parent;
			if (p.parent == null)
				root = l;
			else if (p.parent.right == p)
				p.parent.right = l;
			else
				p.parent.left = l;
			l.right = p;
			p.parent = l;
		}
	}

	/**
	 * 左旋
	 */
	private void rotateLeft(Node<E> p) {
		if (p != null) {
			Node<E> r = p.right;
			p.right = r.left;
			if (r.left != null)
				r.left.parent = p;
			r.parent = p.parent;
			if (p.parent == null)
				root = r;
			else if (p.parent.left == p)
				p.parent.left = r;
			else
				p.parent.right = r;
			r.left = p;
			p.parent = r;
		}
	}

	/**
	 * 删除
	 */
	public boolean delect(E e) {
		return true;
	}

	/**
	 * 先序遍历
	 */
	public void preorderTraverse() {
		preorderTraverse0(root, 0);
	}

	private void preorderTraverse0(Node<E> n, int x) {
		if (n == null)
			return;
		for (int i = 0; i < x; i++)
			System.out.print("-");
		System.out.println(n.e);
		preorderTraverse0(n.left, x + 1);
		preorderTraverse0(n.right, x + 1);
	}

	// 测试
	public static void main(String[] args) {
		MyRBTree<Integer> rbTree = new MyRBTree<>();
		rbTree.add(5);
		rbTree.add(13);
		rbTree.add(1);
		rbTree.add(4);
		rbTree.preorderTraverse();
	}

}
