import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Built using CHelper plug-in Actual solution is at the top
 *
 * @author MaxHeap
 */
public class Main {

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    BusyDrivers solver= new BusyDrivers();
    solver.solve(1, in, out);
    out.close();
  }

  static class BusyDrivers {

    boolean[][] g; 
    boolean[] visited;
    int[] prev;
    int n;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
      n = in.nextInt();
      BusyDrivers.Request[] requests = new BusyDrivers.Request[n];
      for (int i = 0; i < n; ++i) {
        requests[i] = new BusyDrivers.Request(in.nextLong(), in.nextLong(), in.nextLong(),
            in.nextLong(),
            in.nextLong());
      }
    
      g = new boolean[n][n];
      visited = new boolean[n];
      prev = new int[n];
      for(int i = 0; i < n; ++i) {
        prev[i] = -1;
      }

      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          if(i == j) continue;
          if (requests[i].canDoAfter(requests[j])) {
            g[i][j] = true;
          }
        }
      }

      int ans = 0;
      for(int i = 0; i < n; ++i) {
        Arrays.fill(visited, false);
        if(!findNext(i)) ++ans;
      }
      out.println(ans);
    }

    boolean findNext(int u) {
      if(u < 0) return true;
      if(visited[u]) return false;
      visited[u] = true;
      for(int i = 0; i < n; ++i) {
        if(g[u][i]) {
          if(findNext(prev[i])) {
            prev[i] = u;
            return true;
          }
        }
      }
      return false;
    }

    static class Request {

      long fx;
      long fy;
      long tx;
      long ty;
      long start;

      public Request(long fx, long fy, long tx, long ty, long duration) {
        this.fx = fx;
        this.fy = fy;
        this.tx = tx;
        this.ty = ty;
        this.start = duration;
      }

      public boolean canDoAfter(BusyDrivers.Request that) {
        return start + distance(fx, fy, tx, ty) + distance(tx, ty, that.fx, that.fy) <= that.start;
      }

      static long distance(long a, long b, long x, long y) {
        return Math.abs(a - x) + Math.abs(b - y);
      }
    }
  }

  static class Edge implements Comparable<Edge> {

    public int to;
    public long capacity;

    public Edge(int to, long capacity) {
      this.to = to;
      this.capacity = capacity;
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Edge edge = (Edge) o;
      return to == edge.to &&
          capacity == edge.capacity;
    }

    public int hashCode() {
      return Objects.hash(to, capacity);
    }

    public int compareTo(Edge o) {
      return Long.compare(capacity, o.capacity);
    }

  }

  static class MaxFlow {

    static boolean bfs(List<Edge>[] g, int s, int t, long[][] capacity, int[] parent) {
      Queue<Integer> q = new ArrayDeque<>();
      boolean[] visited = new boolean[g.length];
      parent[s] = -1;
      visited[s] = true;
      q.add(s);
      while (!q.isEmpty()) {
        int u = q.poll();
        for (Edge e : g[u]) {
          int v = e.to;
          if (!visited[v] && capacity[u][v] > 0) {
            parent[v] = u;
            visited[v] = true;
            q.add(v);
          }
        }
      }
      return visited[t];
    }

    public static long maxFlow(List<Edge>[] g, int s, int t) {
      long max = 0;
      long[][] capacity = getCapacityMatrix(g);
      int[] parent = new int[g.length];
      while (bfs(g, s, t, capacity, parent)) {
        long curFlow = Long.MAX_VALUE;
        int v = t;
        while (v != s) {
          int u = parent[v];
          curFlow = Math.min(curFlow, capacity[u][v]);
          v = u;
        }
        for (v = t; v != s; v = parent[v]) {
          int u = parent[v];
          capacity[u][v] -= curFlow;
          capacity[v][u] += curFlow;
        }
        max += curFlow;
      }
      return max;
    }

    private static long[][] getCapacityMatrix(List<Edge>[] g) {
      int n = g.length;
      long[][] cap = new long[n][n];
      for (int i = 0; i < n; ++i) {
        for (Edge v : g[i]) {
          cap[i][v.to] = v.capacity;
        }
      }
      return cap;
    }

  }

  static class InputReader {

    private InputStream stream;
    private static final int DEFAULT_BUFFER_SIZE = 1 << 16;
    private static final int EOF = -1;
    private byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
    private int curChar;
    private int numChars;

    public InputReader(InputStream stream) {
      this.stream = stream;
    }

    public int read() {
      if (this.numChars == EOF) {
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
            return EOF;
          }
        }

        return this.buf[this.curChar++];
      }
    }

    public int nextInt() {
      int c;
      for (c = this.read(); isSpaceChar(c); c = this.read()) {
      }

      byte sgn = 1;
      if (c == 45) {
        sgn = -1;
        c = this.read();
      }

      int res = 0;

      while (c >= 48 && c <= 57) {
        res *= 10;
        res += c - 48;
        c = this.read();
        if (isSpaceChar(c)) {
          return res * sgn;
        }
      }

      throw new InputMismatchException();
    }

    public long nextLong() {
      int c;
      for (c = this.read(); isSpaceChar(c); c = this.read()) {
      }

      byte sgn = 1;
      if (c == 45) {
        sgn = -1;
        c = this.read();
      }

      long res = 0;

      while (c >= 48 && c <= 57) {
        res *= 10L;
        res += c - 48;
        c = this.read();
        if (isSpaceChar(c)) {
          return res * sgn;
        }
      }
      throw new InputMismatchException();
    }

    public static boolean isSpaceChar(int c) {
      return c == 32 || c == 10 || c == 13 || c == 9 || c == EOF;
    }

  }

  static abstract class Graphs {

    public static List<Edge>[] createWightedGraph(int n) {
      List<Edge>[] res = new ArrayList[n];
      for (int i = 0; i < n; ++i) {
        res[i] = new ArrayList<>();
      }
      return res;
    }

  }
}

 
