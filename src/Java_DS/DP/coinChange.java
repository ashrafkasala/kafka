package Java_DS.DP;

public class coinChange {
    public static void main(String[] args) {
        coinChange c = new coinChange();
        int inp = 10;
        int coinArr[] = {2,3,5,6};
        System.out.println(c.findways(inp,coinArr));
        System.out.println(c.minCoins(coinArr,coinArr.length,inp));

    }
    int  findways(int n,int[] S){
        int table[]=new int[n+1];
        // Base case (If given value is 0)
        table[0] = 1;
        int m = S.length;

        for(int i=0; i<m; i++)
            for(int j=S[i]; j<=n; j++)
                table[j] += table[j-S[i]];//T[i] = T[i]+T[i-coinVal]///ie including coin how many ways and with out including coin how many ways

        return table[n];
    }
     int minCoins(int coins[], int m, int V)
    {
        // table[i] will be storing
        // the minimum number of coins
        // required for i value. So
        // table[V] will have result
        int table[] = new int[V + 1];
        // Base case (If given value V is 0)
        table[0] = 0;

        // Initialize all table values as Infinite
        for (int i = 1; i <= V; i++)
            table[i] = Integer.MAX_VALUE;
        // Compute minimum coins required for all
        // values from 1 to V
        for (int i = 1; i <= V; i++)
        {
            // Go through all coins smaller than i
            for (int j = 0; j < m; j++)
                if (coins[j] <= i)
                {
                    int sub_res = table[i - coins[j]];
                    if (sub_res != Integer.MAX_VALUE
                            && sub_res + 1 < table[i])
                        table[i] = sub_res + 1;


                }

        }
        return table[V];

    }
}
