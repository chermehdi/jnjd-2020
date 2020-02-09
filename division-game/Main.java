import java.util.*;
import java.io.*;

public class Main {
  private static final String[] players = {"A", "B"};
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int t = in.nextInt();
    while (t-->0) {
      int n = in.nextInt();
      int ans = solve(n);
      System.out.println(players[ans]);
    }
  }

  // returns 0 if the first player wins or 1 otherwise
  private static int solve(int n) {
    int factors = 0;
    int xor = 0;
    int cnt = 0;

    if (n % 2 == 0) {
      ++factors;
      cnt = 0;
      while (n % 2 == 0) {
        n >>= 1;
        ++cnt;
      }
      xor ^= cnt;
    }

    for (int i = 3; i * i <= n; i += 2) {
      if (n % i == 0) {
        ++factors;
        cnt = 0;
        while (n %i == 0) {
          n /= i;
          ++cnt;
        }
        xor ^= cnt;
      }
    }

    if (n > 1) { ++factors; xor ^= 1;}
    // this line is not necessary any more;
    if (factors == 1) return 0; // The first player always wins if its a prime power
    return xor != 0 ? 0 : 1; // The current player (first) is in a winning position
  }
} 
