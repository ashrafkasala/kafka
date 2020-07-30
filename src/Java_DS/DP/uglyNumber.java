package Java_DS.DP;
//Ugly numbers are numbers whose only prime factors are 2, 3 or 5. The sequence 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, … shows the first 11 ugly numbers. By convention, 1 is included.
//
//Given a number n, the task is to find n’th Ugly number.
public class uglyNumber {
    public static void main(String[] args) {
        uglyNumber u = new uglyNumber();
        int input = 150;
        System.out.println(u.findnthugly(input));
    }

    int findnthugly(int n){
        int dp[]=new int[n];
        dp[0]=1;
        int index2 = 0;
        int index3 = 0;
        int index5 = 0;
        int nextmul2 = dp[0]*2;
        int nextmul3 = dp[0]*3;
        int nextmul5 = dp[0]*5;
        int min = 0;
        for(int i=1;i<n;i++){
            min = Math.min(Math.min(nextmul2,nextmul3),nextmul5);
            dp[i] =min;
            if(min == nextmul2){
                index2++;
                nextmul2 = dp[index2]*2;
            }
            if(min == nextmul3){
                index3++;
                nextmul3  = dp[index3]*3;

            }
            if(min == nextmul5){
                index5++;
                nextmul5  = dp[index5]*5;

            }
        }
return dp[n-1];
    }
}
