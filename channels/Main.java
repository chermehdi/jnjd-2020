import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int q = in.nextInt();

        SegementTree t = new SegementTree(n);

        for (int i = 0; i < q; ++i) {
            int type = in.nextInt();

            if (type >= 3) {
                System.out.println((type == 3 ? t.getMax() : t.getMin()) + 1);
            } else {
                int x = in.nextInt();
                int y = in.nextInt() - 1;
                if (type == 2) x *= -1;

                t.update(y, x);
            }
        }
    }
}

class SegementTree {
    int[] mint, maxt, f;
    int n;
    
    public SegementTree(int n) {
        this.n= n;
        this.f = new int[n];
        int size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) n) / Math.log(2.0)) + 1)));
        this.mint = new int[size];
        this.maxt = new int[size];
        build(1, 0, n);
    }

    private void updateSegTrees(int v, int left, int right) {
            // Updateing mint
        if (f[mint[left]] < f[mint[right]]) {
            mint[v] = mint[left];
        } else if (f[mint[left]] > f[mint[right]]) {
            mint[v] = mint[right];
        } else {
            mint[v] = Math.min(mint[left], mint[right]);
        }

        // updating maxt
        if (f[maxt[left]] > f[maxt[right]]) {
            maxt[v] = maxt[left];
        } else if (f[maxt[left]] < f[maxt[right]]) {
            maxt[v] = maxt[right];
        } else {
            maxt[v] = Math.min(maxt[left], maxt[right]);
        }
    }

    private void build(int v, int from , int size) {
        if (size == 1) {
            mint[v] = maxt[v] = from;
        } else {
            int left = v << 1;
            int right = (v << 1) + 1;
            build(left, from, size / 2);
            build(right, from + size / 2, size - size/2);
            
            updateSegTrees(v, left, right);
        }
    }

    public int getMax() {
        return maxt[1];
    }

    public int getMin() {
        return mint[1];
    }

    public void update(int u, int x) {
        update(u, x, 1, 0, this.n);
    }

    private void update(int u, int x, int v, int from, int size) {
        if (size == 1) {
            assert u == from; 
            f[u] += x;
        } else {
            if (u >= from + size / 2) update(u, x, (v << 1) + 1, from + size / 2, size - size/2);
            else update(u, x, v << 1, from, size / 2);

            updateSegTrees(v, v << 1, (v << 1) + 1);
        }
    }

} 
