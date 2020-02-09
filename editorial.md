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

### Beautiful stairs
- 
### Power String
