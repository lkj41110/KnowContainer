package co.lk.sort;

/**
 * 排序,都从小到大
 * 
 * @author lkj41110
 * @version time：2017年2月24日 下午2:45:49
 */
public class Sort {
	/**
	 * 选择排序
	 */
	public static void SelectionSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int min = arr[i];
			int k = i;
			// 找出最小值
			for (int j = i + 1; j < arr.length; j++) {
				if (min > arr[j]) {
					min = arr[j];
					k = j;
				}
			}
			// 交换位置
			if (k != i) {
				int temp = arr[k];
				arr[k] = arr[i];
				arr[i] = temp;
			}
		}
	}

	/**
	 * 插入排序
	 */
	public static void BubbleSort(int[] arr){
		for(int i=1;i<arr.length;i++){
			int k=i-1,temp=arr[i];
			while(k>=0){
				if(temp>=arr[k]){
					break;
				}
				arr[k+1]=arr[k];
				k--;
			}
			arr[k+1]=temp;
		}
	}
	
	/**
	 * 希尔排序
	 */
	public static void ShellSort(int[] arr){
		
	}
	
	public static void main(String[] args) {
		int[] arr={-10,0,12,1,100,32,1,-200};
		BubbleSort(arr);
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
		}
		
		
	}
}
