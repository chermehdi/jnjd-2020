# JNJD 2020 Editorial

## Doctor

- Just sort.

## Youtube channels

### Brute force solution
Keep track of an array with the number of followers for each channel.
- Queries of type 1 and 2: Updates in O(1) by updating the number of followers in the array.
- Queries of type 3 and 4: Looping through the array O(n) and keep the index and the number of the
followers corresponding the the channel with min (or max) followers.
Overall complexity: **O(q × n)**

### Using Segment Tree
Keep track of the number of the followers for each channel. Build two segment trees, one for the min and
one for the max. Each node in the segment tree contains the index of the channel with the min (or max)
number of followers, in the sub-array it represents.
- Queries of type 1 and 2: Update the value of a node by comparing the number of followers of the
channels in the sub left and right parts.
- Queries of type 3 and 4: The channel with the min (or max) number of followers is always at the
root of the corresponding segment tree.
Overall complexity: **O(q × log(n))**

## Beautiful stairs
- We can think of this problem as a simplified version of finding the subarray with given sum, which can be solved
by two pointers technique.

## Power String
- An optimal permutation must look like a unimodal function. It should be non-decreasing then non-increasing. More formally, there should not exist any three indices `i < j < k` such that `S_i > S_j` and `S_j < S_k`. `(e.g. aaabbbcc...ccccba)`

What remains to prove is the skewness of the distribution. The maximum power occurs when the string is symmetrical. In other words, if there are `cnt_A` occurrences of character `A`, then it is optimal to put `cnt_A / 2` on one end of the string, and `cnt_A / 2` on the other. 

## Division Game
- First note that each number can be decomposed into a product of prime numbers and its a unique
decomposition. So this mean that the set of numbers you have to choose from is fixed. 
- This will lead to Nim Game. You need to find the powers of each prime factor and then xor all the powers. The first player
is in a winning position if the xor of all the powers is not 0

## Towers 
- See PDF

## Yogioh
Finding vertex `b` is equivalent to finding vertex `a` on the reversed di-graph.

Assume we start a DFS from some arbitrary vertex of the given graph. If the DFS ends up visiting all vertices, then we found our answer. If it doesn't, then we know our answer, if it exists, is one of the remaining unvisited vertices. We thus relaunch the DFS from another arbitrary unvisited vertex until none remain.

It can be proven that the vertex `a`, if it exists, is the last vertex we launch a DFS from.
## Wissal's workout
- **Quadratic time per query**
For every query, naively check the correctness of every cyclic shift of the subarray. Since every subarray has
O(n) cyclic shifts, and checking each shift takes linear time, the complexity of this solution is *O(q×n×n)*

- **Linear time per query**
We need to be able to count the number of correct cyclic shifts in linear time. Let’s build the partial
sum array of the initial array. This would show us the relative increase or decrease between consecutive
elements of the subarray.
We must observe that the partial sums of the cyclic shifts of a subarray all have the same shape, and differ
by only a constant positive or negative shift. Thus, they all have the same number of absolute minima.
The number of absolute minima of the partial sums of any subarray with 0 net sum is the answer to the
query. These can be found in linear time. The complexity of this solution is O(q × n)

- **Logarithmic time per query**
Use a segment tree to count the minimas for each query.
*Overall complexity: O(q × log(n))*

## K sorted subarrays
The naive solution of this problem iterates over all possible permutations of the array, and checks each one for the given properties. The complexity of such solution is `O(N*N!)`, which is obviously too slow to pass!

The first observation to make is the actual values of the array do not matter since they're distinct, and thus can be remapped to a permutation of natural numbers from `1` to `N`. Moreover, the values left to use when building a partial solution do not matter either! What matters is the relative differences between elements only.

Assume we have a recurrence `F` defined as follows:

- `F(left, right, dir, streak)` is the number of permutations starting from an element with `left` values remaining that are less than te previously placed value, `right` values remaining that are greater than the previously placed value, values can only be placed in the increasing/decreasing direction depending on the value of the boolean `dir`, and the increasing/decreasing trend has continuously been going for `streak` consecutive values before the placement of the current one.
- `F(0, 0, dir, streak) = 1`
- `F(left, right, dir, streak) = 0` for any `streak >= k`
- If we are currently going in the increasing `dir`, we try all elements in `right`
- If we are currently going in the decreasing `dir`, we try all elements in `left`

Using dynamic programming, the answer to the problem `F(left[initial], right[initial], 0, 0) + F(left[initial], right[initial], 1, 0)` can be computed in `O(N^4)`
## Busy drivers
A key observation to solve this problem is to visualize it as a graph/network problem.

We can create a Graph with `N` nodes each representing a travel request, and there is an edge between two nodes in this graph if and only if request `a[i]` can be processed after request `a[i - 1]`.

Now what we can observe is the answer is the number of vertex disjoint paths in this graph, and we can see that this can be solved by matching each vertex with some ancestor let's call it `u`, and `u` will be matched to only the current vertex, or that request is the itself the start of a new path.

This can be solved by finding the Maximum Bipartite matching.

Complexity: `O(N^2)`
