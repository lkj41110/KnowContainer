����������Ϊ��������ʱ����ԭ���İ汾ʹ�õĹ鲢�����Ժ󽫻�ɾ�� ��������ʹ�õ�timSort��

```
  public static void sort(Object[] a) {
        if (LegacyMergeSort.userRequested)
            legacyMergeSort(a);
        else
            ComparableTimSort.sort(a);
    }
    //�Ժ������
    /** To be removed in a future release. */
    private static void legacyMergeSort(Object[] a) {
        Object[] aux = a.clone();
        mergeSort(aux, a, 0, a.length, 0);
    }
```
����������Ҫ���� ComparableTimSort.sort(Object[] a)����Ϊ���漸����Ҫ���裺

#### �������С��32�����
1. �ж�����Ĵ�С��С��32ʹ�ö��ֲ�������
```
static void sort(Object[] a, int lo, int hi) {
        //���lo��hi�ĵ�׼ȷ��
        rangeCheck(a.length, lo, hi);
        int nRemaining  = hi - lo;
        
        //������Ϊ0��1ʱ��Զ�����Ѿ�����״̬
        if (nRemaining < 2)
            return;

        // ����С��ʱ��
        if (nRemaining < MIN_MERGE) {
            //�ҳ����������������
            int initRunLen = countRunAndMakeAscending(a, lo, hi);
            //���ֲ�������
            binarySort(a, lo, hi, lo + initRunLen);
            return;
        }
        
        //�������32��ʱ
       ......
```
2. �ҳ����ĵ������ߵݼ��ĸ���������ݼ�����˶������ϸ�һ�·���

```
  private static int countRunAndMakeAscending(Object[] a, int lo, int hi) {
        int runHi = lo + 1;
        if (runHi == hi)
            return 1;

        // Find end of run, and reverse range if descending
        if (((Comparable) a[runHi++]).compareTo(a[lo]) < 0) { // �ݼ�
            while (runHi < hi && ((Comparable) a[runHi]).compareTo(a[runHi - 1]) < 0)
                runHi++;
            //����˳��
            reverseRange(a, lo, runHi);
        } else {                              // ����
            while (runHi < hi && ((Comparable) a[runHi]).compareTo(a[runHi - 1]) >= 0)
                runHi++;
        }

        return runHi - lo;
    }
```

3. ʹ����ʹ��==���ֲ���==λ�ã����в�������==start==֮ǰΪȫ���������飬��==start+1==��ʼ���в��룬����λ��ʹ�ö��ַ����ҡ��������ƶ��ĸ���ʹ�ò�ͬ���ƶ�������

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
            
            int n = start - left;  // Ҫ�ƶ��ĸ���
            // �ƶ��ķ���
            switch (n) {
                case 2:  a[left + 2] = a[left + 1];
                case 1:  a[left + 1] = a[left];
                         break;
                //native�������鷽��
                default: System.arraycopy(a, left, a, left + 1, n);
            }
            a[left] = pivot;
        }
    }
```

#### �����������32�����
�������32ʱ�� �����һ�����ʵĴ�С���ڽ����밴������ͽ����ص�����˷��������������ĵ�λ����һ�������������֣�����һ�����Ŀ�-����������ÿһ��������һ��run�������Щ run ���У�ÿ����һ��run������������кϲ���ÿ�κϲ��Ὣ����run�ϲ���һ�� run���ϲ��Ľ�����浽ջ�С��ϲ�ֱ�����ĵ����е�run����ʱ��ջ��ʣ��� run�ϲ���ֻʣһ�� run Ϊֹ����ʱ�����ʣ�� run �����ź���Ľ����

```
    static void sort(Object[] a, int lo, int hi) {
        //С��32
        ......
        //����32�����
        ComparableTimSort ts = new ComparableTimSort(a);
        
        //�����run�ĳ���
        int minRun = minRunLength(nRemaining);
        do {
            //�ҳ����������������
            int runLen = countRunAndMakeAscending(a, lo, hi);

            // ���run����С�ڹ涨��minRun���ȣ��Ƚ��ж��ֲ�������
            if (runLen < minRun) {
                int force = nRemaining <= minRun ? nRemaining : minRun;
                binarySort(a, lo, lo + force, lo + runLen);
                runLen = force;
            }

            // Push run onto pending-run stack, and maybe merge
            ts.pushRun(lo, runLen);
            //���й鲢
            ts.mergeCollapse();

    
            lo += runLen;
            nRemaining -= runLen;
        } while (nRemaining != 0);

        // �鲢���е�run
        ts.mergeForceCollapse();
    }
```
1.�����run����С�ĳ���minRun

a) ��������СΪ2��N���ݣ��򷵻�16��MIN_MERGE / 2��

b) ��������£���λ����λ�ƣ�������2����ֱ���ҵ�����16��32���һ����

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
2.����С�����ĳ��ȣ��������С��minRun��ʹ�ò������򲹳䵽minRun�ĸ�����������С��32�ĸ�����һ����
3.��stack��¼ÿ��run�ĳ��ȣ����������������һ������ʱ�鲢��ֱ����������
  runLen[i - 3] > runLen[i - 2] + runLen[i - 1]
  runLen[i - 2] > runLen[i - 1]

```
 private void mergeCollapse() {
        while (stackSize > 1) {
            int n = stackSize - 2;
            if (n > 0 && runLen[n-1] <= runLen[n] + runLen[n+1]) {
                if (runLen[n - 1] < runLen[n + 1])
                    n--;
                //����Ĺ鲢����
                mergeAt(n);
            } else if (runLen[n] <= runLen[n + 1]) {
                mergeAt(n);
            } else {
                break; // Invariant is established
            }
        }
    }
```
���ڹ鲢�����Ͷ�һ��Ĺ鲢���������˼򵥵��Ż����������� run �� run1,run2 ������ gallopRight�� run1 ��ʹ�� binarySearch ����run2 ��Ԫ�� ��λ��k, ��ô run1 �� k ǰ���Ԫ�ؾ��Ǻϲ�����С����ЩԪ�ء�Ȼ����run2 �в���run1 βԪ�� ��λ�� len2 ����ôrun2 �� len2 �������ЩԪ�ؾ��Ǻϲ���������ЩԪ�ء���󣬸���len1 ��len2 ��С������mergeLo ���� mergeHi ��ʣ��Ԫ�غϲ���

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
4.���鲢����û�й鲢��run��֪��run������Ϊ1

#### ����
Ϊ����ʾ���㣬�ҽ�TimSort�е�minRunֱ������Ϊ2�������Ҳ����ú�С��������ʾ������ͬʱ��MIN_MERGEҲ�ĳ�2��Ĭ��Ϊ32������������ֱ�ӽ�����ֲ�������

1. ��ʼ����Ϊ[7,5,1,2,6,8,10,12,4,3,9,11,13,15,16,14]

2. Ѱ�ҵ�һ�������Ľ�����������У�[1,5,7] [2,6,8,10,12,4,3,9,11,13,15,16,14]

3. stackSize=1�����Բ��ϲ��������ҵڶ���run

4. �ҵ�һ���ݼ����У���������[1,5,7] [2,6,8,10,12] [4,3,9,11,13,15,16,14]

5. ��ΪrunLen[0]<=runLen[1]���Թ鲢
	1) gallopRight��Ѱ��run1�ĵ�һ��Ԫ��Ӧ������run0���ĸ�λ�ã���2��Ӧ�����롱1��֮�󣩣�Ȼ��Ϳ��Ժ���֮ǰrun0��Ԫ�أ�����run1�ĵ�һ��Ԫ��С��
	2) gallopLeft��Ѱ��run0�����һ��Ԫ��Ӧ������run1���ĸ�λ�ã���7��Ӧ�����롱8��֮ǰ����Ȼ��Ϳ��Ժ���֮��run1��Ԫ�أ�����run0�����һ��Ԫ�ش�
	������Ҫ�����Ԫ�ؾͽ�ʣ��[5��7] [2,6]��Ȼ�����mergeLow ���֮��Ľ���� [1,2,5,6,7,8,10,12] [4,3,9,11,13,15,16,14]

6. Ѱ�������Ľ������������[1,2,5,6,7,8,10,12] [3,4] [9,11,13,15,16,14] 

7. �����й鲢������ΪrunLen[0]>runLen[1]

8. Ѱ�������Ľ�����������У�[1,2,5,6,7,8,10,12] [3,4] [9,11,13,15,16] [14]

9. ��ΪrunLen[1]<=runLen[2]��������Ҫ�鲢

10. ʹ��gallopRight������Ϊ����˳�򡣵�[1,2,5,6,7,8,10,12] [3,4,9,11,13,15,16] [14]

11. ���ֻʣ��[14]���Ԫ�أ�[1,2,5,6,7,8,10,12] [3,4,9,11,13,15,16] [14]

12. ��ΪrunLen[0]<=runLen[1]+runLen[2]���Ժϲ�����ΪrunLen[0]>runLen[2]�����Խ�run1��run2�Ⱥϲ���������run0��run1�Ⱥϲ���
���֮��Ľ���� [1,2,5,6,7,8,10,12] [3,4,9,11,13,14,15,16]

13. ���֮��Ľ����[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]



**�ο���http://blog.csdn.net/bruce_6/article/details/38299199**


