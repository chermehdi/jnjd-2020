import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in Actual solution is at the top
 *
 * @author NMouad21
 */
public class YoGiOhNMouad21 {

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    MCPC19YoGiOh solver = new MCPC19YoGiOh();
    solver.solve(1, in, out);
    out.close();
  }

  static class MCPC19YoGiOh {

    public void solve(int testNumber, InputReader in, PrintWriter ut) {
      int t = in.nextInt();
      while (t-- > 0) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] e = in.nextIntMatrix(m, 2);
        int a = get(e, n);
        if (a == -1) {
          out.println(-1);
          continue;
        }
        int b = get(GraphUtils.reverseEdgesDirection(e), n);
        if (b == -1) {
          out.println(-1);
          continue;
        }
        out.println(a + " " + b);
      }
    }

    private int get(int[][] e, int n) {
      int[][] g = GraphUtils.packDirectedUnweighted(e, n);
      int[] order = TopologicalSorter.sortRec(g, n);

      boolean[] vis = new boolean[n + 1];
      int[] queue = new int[n + 1];
      int addPt = 0, pollPt = 0, count = 0;

      queue[addPt++] = order[0];
      vis[order[0]] = true;
      ++count;
      while (addPt > pollPt) {
        for (int v : g[queue[pollPt++]]) {
          if (!vis[v]) {
            queue[addPt++] = v;
            vis[v] = true;
            ++count;
          }
        }
      }

      if (count != n) {
        return -1;
      }
      return order[0];
    }

  }

  static final class GraphUtils {

    private GraphUtils() {
      throw new RuntimeException("DON'T");
    }

    public static final int[][] packDirectedUnweighted(int[][] edges, int n) {
      int[][] g = new int[n + 1][];
      int[] size = new int[n + 1];
      for (int[] edge : edges) {
        ++size[edge[0]];
      }
      for (int i = 0; i <= n; i++) {
        g[i] = new int[size[i]];
      }
      for (int[] edge : edges) {
        g[edge[0]][--size[edge[0]]] = edge[1];
      }
      return g;
    }

    public static final int[][] reverseEdgesDirection(int[][] edges) {
      int[][] reversedEdges = new int[edges.length][];
      for (int i = 0; i < edges.length; i++) {
        int[] edge = edges[i];
        reversedEdges[i] = new int[edge.length];
        System.arraycopy(edge, 0, reversedEdges[i], 0, edge.length);
        reversedEdges[i][0] = edge[1];
        reversedEdges[i][1] = edge[0];
      }
      return reversedEdges;
    }

  }

  static final class TopologicalSorter {

    private TopologicalSorter() {
      throw new RuntimeException("DON'T");
    }

    public static final int[] sortRec(int[][] g, int n) {
      int[] order = new int[n];
      int[] orderIdx = new int[]{n - 1};
      boolean[] vis = new boolean[n + 1];

      TopologicalSorter.TopologicalSortDfs dfs = new TopologicalSorter.TopologicalSortDfs() {

        public void go(int u) {
          vis[u] = true;
          for (int v : g[u]) {
            if (!vis[v]) {
              go(v);
            }
          }
          order[orderIdx[0]--] = u;
        }
      };

      for (int i = 1; i <= n; i++) {
        if (!vis[i]) {
          dfs.go(i);
        }
      }

      return order;
    }

    private interface TopologicalSortDfs {

      void go(int u);

    }

  }

  static final class InputReader {

    private final InputStream stream;
    private final byte[] buf = new byte[1 << 20];
    private int curChar;
    private int numChars;

    public InputReader() {
      this.stream = System.in;
    }

    public InputReader(final InputStream stream) {
      this.stream = stream;
    }

    private final int read() {
      if (this.numChars == -1) {
        throw new UnknownError();
      } else {
        if (this.curChar >= this.numChars) {
          this.curChar = 0;

          try {
            this.numChars = this.stream.read(this.buf);
          } catch (IOException ex) {
            throw new InputMismatchException();
          }

          if (this.numChars <= 0) {
            return -1;
          }
        }

        return this.buf[this.curChar++];
      }
    }

    public final int nextInt() {
      int c;
      for (c = this.read(); isSpaceChar(c); c = this.read()) {
      }

      byte sgn = 1;
      if (c == 45) { // 45 == '-'
        sgn = -1;
        c = this.read();
      }

      int res = 0;

      while (c >= 48 && c <= 57) { // 48 == '0', 57 == '9'
        res *= 10;
        res += c - 48; // 48 == '0'
        c = this.read();
        if (isSpaceChar(c)) {
          return res * sgn;
        }
      }

      throw new InputMismatchException();
    }

    private static final boolean isSpaceChar(final int c) {
      return c == 32 || c == 10 || c == 13 || c == 9
          || c == -1; // 32 == ' ', 10 == '\n', 13 == '\r', 9 == '\t'
    }

    public final int[][] nextIntMatrix(final int n, final int m) {
      int[][] arr = new int[n][m];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          arr[i][j] = nextInt();
        }
      }
      return arr;
    }

  }
}

o
