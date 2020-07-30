package Java_DS.DP;

import java.util.Iterator;
import java.util.PriorityQueue;

public class superuglyNumber {
    public static void main(String[] args) {
        superuglyNumber s = new superuglyNumber();
        int inp = 1000;
        int primeArr[] = {2,3,5};
        System.out.println(s.findNth(inp,primeArr));
    }
   int  findNth(int n,int[] primeArr){
       // Creating empty priority queue
       int uglyArr[] =  new int[n];
       uglyArr[0]=1;
       int index=n;
       int min  =0 ;
       PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
       for(int  i=0;i<primeArr.length;i++){
           pq.add(primeArr[i]);
       }
      int count=1,no=1;

       while(count < n){
           // Get the minimum value from priority_queue
           no = pq.peek();
           pq.poll();

           // If top of pq is no then don't increment count. This to avoid duplicate counting of same no.
           if(no != pq.peek())
           {
               count++;

               //Push all the multiples of no. to priority_queue
               for(int i = 0; i < primeArr.length; i++){
                   pq.add(no * primeArr[i]);
                   //    cnt+=1;
               }
           }
       }
       // Return nth super ugly number
       return no;

   }
}
