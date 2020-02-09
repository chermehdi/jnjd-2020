import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        
        long sum = 1, left=1, right=1; //(long)(Math.sqrt(n));
        while(sum != n) {
            while(sum<n) {
                right++;
                sum += right;
            }
            while(sum>n) {
                sum -= left;
                left++;
            }
        }
        
        System.out.println(String.format("%d %d", left, right));
    }

}

