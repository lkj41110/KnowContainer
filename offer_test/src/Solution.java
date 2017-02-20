import java.util.Random;

/**
 * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号
 */
public class Solution {
	public static void main(String[] args) {
		//测试加法正确性
		test1();
		System.out.println(decrease(-1, 1));
		
		
	}
	private static void test1() {
		for (int i = 0; i < 1000; i++) {
			Random random = new Random();
			int n = random.nextInt(10000);
			int result1 = n + i;
			int result2 = Solution.add(n, i);
			if (result1 != result2){
				System.out.println("add wrong");
				break;
			}
		}
		for (int i = 0; i < 1000; i++) {
			Random random = new Random();
			int n = random.nextInt(10000);
			int result1 = n - i;
			int result2 = Solution.decrease(n, i);
			if (result1 != result2){
				System.out.println("decrease wrong");
				break;
			}
		}
	}
	//加法
	private static int add(int n1, int n2) {
		int num, temp;
		do {
			num = n1 ^ n2;
			temp = (n1 & n2) << 1;

			n1 = num;
			n2 = temp;
		} while (n2 != 0); 
		return num;
	}
	//减法
	private static int decrease(int n1, int n2) {
		int result=add(n1,~n2+1);
		return result;
	}
}
