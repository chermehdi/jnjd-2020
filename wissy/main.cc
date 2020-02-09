#include <vector>
#include <iostream>
#include <numeric>
#include <assert.h>
#include <cmath>

using namespace std;

const long long inf = 1e18;

class solver {
public:
  solver(const vector<long long>& values) {
    vector<long long> presum(values.size());
    partial_sum(values.begin(), values.end(), presum.begin());
    st.resize(4 * presum.size());
    build(1, 0, presum.size()-1, presum, values);
  }

  int count_shifts(int i, int j) {
    node answer = query(1, 0, st.size()/4-1, i, j);
    // cerr << answer.minvalue << ' ' << answer.frequency << ' ' << answer.sum << "  ";
    if(answer.sum != 0) return 0;
    return answer.frequency;
  }

private:
  struct node {
    node() {}

    node(long long value, long long frequency, long long sum) :
     minvalue(value), frequency(frequency), sum(sum) {}

    node(const node& other) :
      minvalue(other.minvalue), frequency(other.frequency), sum(other.sum) {}
    
    static node combine(const node& lhs, const node& rhs) {
      node result = node(lhs);
      result.sum += rhs.sum;
      if(lhs.minvalue > rhs.minvalue) {
        result = node(rhs);
        result.sum += lhs.sum;
      }
      else if(lhs.minvalue == rhs.minvalue) {
        result.frequency += rhs.frequency;
      }
      return result;
    }

    mutable long long minvalue;
    mutable long long frequency;
    mutable long long sum;
  };

  vector<node> st;

  void build(int p, int l, int r, const vector<long long>& presum, const vector<long long>& values) {
    if(l == r) {
      st[p] = node(presum[l], 1, values[l]);
      return;
    }
    build(2*p, l, (r+l)/2, presum, values);
    build(2*p+1, (r+l)/2+1, r, presum, values);
    st[p] = node::combine(st[2*p], st[2*p+1]);
  }

  node query(int p, int l, int r, int i, int j) {
    if(l > j || i > r) return node(inf, 0, 0);
    if(l >= i && r <= j) return st[p];
    return node::combine(query(2*p, l, (r+l)/2, i, j),
                         query(2*p+1, (r+l)/2+1, r, i, j));
  }
};

int main() {
  ios_base::sync_with_stdio(false); cin.tie(0);
  int n, q; cin >> n >> q;
  assert(n <= 100000 && q <= 300000);
  vector<long long> values(n);
  for(int i=0 ; i<n ; i++) {
    cin >> values[i];
    assert(abs(values[i]) <= 1000000000);
  }
  solver* ds = new solver(values);
  while(q--) {
    int i, j; cin >> i >> j;
    i--, j--;
    assert(i <= j);
    cout << ds->count_shifts(i, j) << endl;
  }
  return 0;
}
 
