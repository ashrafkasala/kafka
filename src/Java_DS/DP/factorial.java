package Java_DS.DP;

import java.util.Arrays;

public class factorial {
    static int input = 10;
    static int dp[] = new int[input+1];
    static  int tab[]= new int[input+1];

    public static void main(String... args){
     //memoization top down
        Arrays.fill(dp,-1);
        System.out.println(memo(input));
        System.out.println(Arrays.toString(dp));
        //tabulation
        System.out.println(tabulation(input));
    }

    static int memo(int n){
           if(n==0){
               return  1;
           }
           if(dp[n]!=-1){
               return  dp[n];
           }
           dp[n] = n * memo(n-1);
           return dp[n];
    }
    static int tabulation(int n){
        tab[0] =1;
        for(int i=1;i<=n;i++){
            tab[i] = i*tab[i-1];
        }
        return  tab[n];
    }
}
