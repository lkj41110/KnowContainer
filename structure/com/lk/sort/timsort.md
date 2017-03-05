当参数类型为对象数组时，在原来的版本使用的归并排序（以后将会删除 ），现在使用的timSort。

```
  public static void sort(Object[] a) {
        if (LegacyMergeSort.userRequested)
            legacyMergeSort(a);
        else
            ComparableTimSort.sort(a);
    }
    //以后会抛弃
    /** To be removed in a future release. */
    private static void legacyMergeSort(Object[] a) {
        Object[] aux = a.clone();
        mergeSort(aux, a, 0, a.length, 0);
    }
```
所以排序主要用了 ComparableTimSort.sort(Object[] a)。分为下面几个主要步骤：

#### 数组个数小于32的情况
1. 判断数组的大小，小于32使用二分插入排序
```
static void sort(Object[] a, int lo, int hi) {
        //检查lo，hi的的准确性
        rangeCheck(a.length, lo, hi);
        int nRemaining  = hi - lo;
        
        //当长度为0或1时永远都是已经排序状态
        if (nRemaining < 2)
            return;

        // 数组小的时候
        if (nRemaining < MIN_MERGE) {
            //找出连续升序的最大个数
            int initRunLen = countRunAndMakeAscending(a, lo, hi);
            //二分插入排序
            binarySort(a, lo, hi, lo + initRunLen);
            return;
        }
        
        //数组大于32的时
       ......
```
2. 找出最大的递增或者递减的个数，如果递减，则此段数组严格反一下方向

```
  private static int countRunAndMakeAscending(Object[] a, int lo, int hi) {
        int runHi = lo + 1;
        if (runHi == hi)
            return 1;

        // Find end of run, and reverse range if descending
        if (((Comparable) a[runHi++]).compareTo(a[lo]) < 0) { // 递减
            while (runHi < hi && ((Comparable) a[runHi]).compareTo(a[runHi - 1]) < 0)
                runHi++;
            //调整顺序
            reverseRange(a, lo, runHi);
        } else {                              // 递增
            while (runHi < hi && ((Comparable) a[runHi]).compareTo(a[runHi - 1]) >= 0)
                runHi++;
        }

        return runHi - lo;
    }
```

3. 使用在使用==二分查找==位置，进行插入排序。==start==之前为全部递增数组，从==start+1==开始进行插入，插入位置使用二分法查找。最后根据移动的个数使用不同的移动方法。

```
 private static void binarySort(Object[] a, int lo, int hi, int start) {
        
        if (start == lo)
            start++;
        for ( ; start < hi; start++) {
            
            Comparable<Object> pivot = (Comparable) a[start];

            int left = lo;
            int right = start;
            
            while (left < right) {
                int mid = (left + right) >>> 1;
                if (pivot.compareTo(a[mid]) < 0)
                    right = mid;
                else
                    left = mid + 1;
            }
            
            int n = start - left;  // 要移动的个数
            // 移动的方法
            switch (n) {
                case 2:  a[left + 2] = a[left + 1];
                case 1:  a[left + 1] = a[left];
                         break;
                //native复制数组方法
                default: System.arraycopy(a, left, a, left + 1, n);
            }
            a[left] = pivot;
        }
    }
```

#### 数组个数大于32的情况
数组大于32时， 先算出一个合适的大小，在将输入按其升序和降序特点进行了分区。排序的输入的单位不是一个个单独的数字，而是一个个的块-分区。其中每一个分区叫一个run。针对这些 run 序列，每次拿一个run出来按规则进行合并。每次合并会将两个run合并成一个 run。合并的结果保存到栈中。合并直到消耗掉所有的run，这时将栈上剩余的 run合并到只剩一个 run 为止。这时这个仅剩的 run 便是排好序的结果。

```
    static void sort(Object[] a, int lo, int hi) {
        //小于32
        ......
        //大于32的情况
        ComparableTimSort ts = new ComparableTimSort(a);
        
        //计算出run的长度
        int minRun = minRunLength(nRemaining);
        do {
            //找出连续升序的最大个数
            int runLen = countRunAndMakeAscending(a, lo, hi);

            // 如果run长度小于规定的minRun长度，先进行二分插入排序
            if (runLen < minRun) {
                int force = nRemaining <= minRun ? nRemaining : minRun;
                binarySort(a, lo, lo + force, lo + runLen);
                runLen = force;
            }

            // Push run onto pending-run stack, and maybe merge
            ts.pushRun(lo, runLen);
            //进行归并
            ts.mergeCollapse();

    
            lo += runLen;
            nRemaining -= runLen;
        } while (nRemaining != 0);

        // 归并所有的run
        ts.mergeForceCollapse();
    }
```
1.计算出run的最小的长度minRun

a) 如果数组大小为2的N次幂，则返回16（MIN_MERGE / 2）

b) 其他情况下，逐位向右位移（即除以2），直到找到介于16和32间的一个数

```
 private static int minRunLength(int n) {
        int r = 0;      // Becomes 1 if any 1 bits are shifted off
        while (n >= MIN_MERGE) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }
```
2.求最小递增的长度，如果长度小于minRun，使用插入排序补充到minRun的个数，操作和小于32的个数是一样。
3.用stack记录每个run的长度，当下面的条件其中一个成立时归并，直到数量不变
  runLen[i - 3] > runLen[i - 2] + runLen[i - 1]
  runLen[i - 2] > runLen[i - 1]

```
 private void mergeCollapse() {
        while (stackSize > 1) {
            int n = stackSize - 2;
            if (n > 0 && runLen[n-1] <= runLen[n] + runLen[n+1]) {
                if (runLen[n - 1] < runLen[n + 1])
                    n--;
                //具体的归并操作
                mergeAt(n);
            } else if (runLen[n] <= runLen[n + 1]) {
                mergeAt(n);
            } else {
                break; // Invariant is established
            }
        }
    }
```
关于归并方法和对一般的归并排序做出了简单的优化。假设两个 run 是 run1,run2 ，先用 gallopRight在 run1 里使用 binarySearch 查找run2 首元素 的位置k, 那么 run1 中 k 前面的元素就是合并后最小的那些元素。然后，在run2 中查找run1 尾元素 的位置 len2 ，那么run2 中 len2 后面的那些元素就是合并后最大的那些元素。最后，根据len1 与len2 大小，调用mergeLo 或者 mergeHi 将剩余元素合并。

```
 private void mergeAt(int i) {
    
        int base1 = runBase[i];
        int len1 = runLen[i];
        int base2 = runBase[i + 1];
        int len2 = runLen[i + 1];
       
        runLen[i] = len1 + len2;
        if (i == stackSize - 3) {
            runBase[i + 1] = runBase[i + 2];
            runLen[i + 1] = runLen[i + 2];
        }
        stackSize--;

        int k = gallopRight((Comparable<Object>) a[base2], a, base1, len1, 0);
        assert k >= 0;
        base1 += k;
        len1 -= k;
        if (len1 == 0)
            return;

        
        len2 = gallopLeft((Comparable<Object>) a[base1 + len1 - 1], a,
                base2, len2, len2 - 1);
        assert len2 >= 0;
        if (len2 == 0)
            return;

        if (len1 <= len2)
            mergeLo(base1, len1, base2, len2);
        else
            mergeHi(base1, len1, base2, len2);
    }
```
4.最后归并还有没有归并的run，知道run的数量为1

#### 例子
为了演示方便，我将TimSort中的minRun直接设置为2，否则我不能用很小的数组演示。。。同时把MIN_MERGE也改成2（默认为32），这样避免直接进入二分插入排序。

1. 初始数组为[7,5,1,2,6,8,10,12,4,3,9,11,13,15,16,14]

2. 寻找第一个连续的降序或升序序列：[1,5,7] [2,6,8,10,12,4,3,9,11,13,15,16,14]

3. stackSize=1，所以不合并，继续找第二个run

4. 找到一个递减序列，调整次序：[1,5,7] [2,6,8,10,12] [4,3,9,11,13,15,16,14]

5. 因为runLen[0]<=runLen[1]所以归并
	1) gallopRight：寻找run1的第一个元素应当插入run0中哪个位置（”2”应当插入”1”之后），然后就可以忽略之前run0的元素（都比run1的第一个元素小）
	2) gallopLeft：寻找run0的最后一个元素应当插入run1中哪个位置（”7”应当插入”8”之前），然后就可以忽略之后run1的元素（都比run0的最后一个元素大）
	这样需要排序的元素就仅剩下[5，7] [2,6]，然后进行mergeLow 完成之后的结果： [1,2,5,6,7,8,10,12] [4,3,9,11,13,15,16,14]

6. 寻找连续的降序或升序序列[1,2,5,6,7,8,10,12] [3,4] [9,11,13,15,16,14] 

7. 不进行归并排序，因为runLen[0]>runLen[1]

8. 寻找连续的降序或升序序列：[1,2,5,6,7,8,10,12] [3,4] [9,11,13,15,16] [14]

9. 因为runLen[1]<=runLen[2]，所以需要归并

10. 使用gallopRight，发现为正常顺序。得[1,2,5,6,7,8,10,12] [3,4,9,11,13,15,16] [14]

11. 最后只剩下[14]这个元素：[1,2,5,6,7,8,10,12] [3,4,9,11,13,15,16] [14]

12. 因为runLen[0]<=runLen[1]+runLen[2]所以合并。因为runLen[0]>runLen[2]，所以将run1和run2先合并。（否则将run0和run1先合并）
完成之后的结果： [1,2,5,6,7,8,10,12] [3,4,9,11,13,14,15,16]

13. 完成之后的结果：[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]



**参考：http://blog.csdn.net/bruce_6/article/details/38299199**


