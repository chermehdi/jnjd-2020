#include <cstdio>
#include <iostream>
#include <algorithm>
#include <tuple>
#include <vector>

using namespace std;

struct Contestant {
	int p;
	int t;
	string name;
};

vector<Contestant> conts;

int main(void) {
	int N;
	cin >> N;

	Contestant cont;
	for(int i = 0; i < N; i++) {
		int p, t;
		string name;
		cin >> name >> p >> t;
		cont.p = p;
		cont.t = t;
		cont.name = name;
		conts.push_back(cont);
	}

	sort(conts.begin(), conts.end(), [&](Contestant c1, Contestant c2){
		if(c1.p == c2.p) {
			if(c1.t == c2.t) {
				if(c1.name > c2.name) return false;
				else return true;
			} else {
				return c1.t < c2.t;
			} 
		} else {
			return c1.p > c2.p;
		} 
	});
	

	for(int i = 0; i < conts.size(); i++) {
		cout << conts[i].name << endl;
	}

	return ;
}0
