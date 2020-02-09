#include <bits/stdc++.h>

using namespace std;

int main() {
  ios_base::sync_with_stdio(false); cin.tie(0);
  string s; cin >> s;
  sort(s.begin(), s.end());
  string ans = s;
  int l = 0, r = s.size()-1, x = 1;
  for(auto c:s) {
    if(x&1) ans[l++] = c;
    else ans[r--] = c;
    x^=1;
  }
  cout << ans << '\n';
  int count = 0;
  for(int i=0 ; i<ans.size() ; i++) {
    for(int j=i+2 ; j<ans.size() ; j++) {
      for(int k=i+1 ; k<j ; k++) {
        if(ans[k]>ans[i] && ans[k]>ans[j]) count++;
      }
    }
  }
  cout << count << endl;
  return 0;
}

