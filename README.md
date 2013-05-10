Scrabble Solver
==============

A basic GWT web application which allows the user to define the arrangement of a Boggle board then solves for all the words that exist on that board.

The Boggle dictionary was downloaded from http://www.andrew.cmu.edu/user/jyung/Boggle/BoggleDictionary.txt . The Boggle tile icon was downloaded from Flickr [here](http://farm5.staticflickr.com/4013/4668032105_7c6a5500b5_o.jpg). The code for the Trie implementation is slightly modified from the version available [here](http://ken-soft.com/2012/03/20/java-trie-prefix-tree/).

## Solver Approach

I visualized the Boggle board as a graph in which each Boggle Cube was a node. Per the rules of Boggle, each cube can be connected to any cube which is adjacent (including diagonally) to the original cube. The only other constrint on a path is that each node can only be visited at most one time in a single path (word).

A brute-force algorithm for word detection would traverse every possible path in the graph and check for the existence of a word in the dictionary for each possible path. If the dictionary scan were implemented as a linear scan, each lookup would involve `O(n)` work where `n` is the number of words in the dictionary. The number of valid paths through this graph is non-obious to me, but I'd estimate that it's on the order of 1,000 paths (the dictionary, for comparison, has 66,625 words). If `m` is the number of valid paths through the graph (some super-linear function on the size of the graph, in this case `4x4`), then this algorithm would be `O(n * m)`. 

A better approach would be to use a more efficient search through the dictionary. A binary search, for instance, through a pre-sorted dictionary could reduce the complexity to `O(lg(n) * m)`.

Even better would be to store the dictionary as a prefix tree, which can be traversed in `O(k)` time, where `k` is the length of the longest word in the dictionary (`23`, in our case). This has the additional benefit that we can prune our possible space of paths in the graph once we've arrived at a sequence of letters which we know is not the prefix of any word in the dictionary. This will surely reduce the search space of the algorithm substantially (though the order of magnitude would likely depend on the distribution of letters in the dictionary. We'll just call it a much lower-polynomial `O(m)` process).

In total, this reduces the algorithm to (low-polynomial) `O(k * m)`.
