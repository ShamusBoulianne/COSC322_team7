# COSC322_team7

## Documentation/Report

### Board
The board is a representation of the game state. It contains a 10x10 2d array with the top left of the board being 0,0. This array contains 0 for empty spaces, 1 for white queens, 2 for black queens, and 3 for arrows. The board class also contains an integer to refer to which queen is going to play next, stored as thisPlayerQueenNum. Finally, when each board is created, a heuristic calculation is made to determine which team we think is winning. This is simply done by taking the log of the ratio of the number of available moves for white by the number of moves available for black. This heuristic will be positive when white has more available moves, i.e. white is winning, and negative when black is winning. If white has no moves the heuristic is set to negative infinity and if black has no moves then it is set to positive infinity.


### GTNode
A node in the game tree, this class holds information about a possible game state and all the relevant information it took to achieve that state. Each node has a parent node and a moveToGetHere, which combine to create the new board that is the backbone of each node. When each node is created, its heuristic is initially set to the heuristic of the board that it contains. Then, if we have not yet reached the max depth of the search tree, and the finishTime for the search has not been reached, we initialize the children variable and populate it according to the rules set out below. After the children class has been populated, we update the heuristic of this node with the max/min value according to the depth first minimax search covered in class.


### Children
This class is used to track the children of each node and contains a sorted list of GTNodes that can be reached from their parent board. For each possible game state reachable from the parent node, we create a new GTNode and add it to the sorted list, sorting lowest to highest based on heuristic. This list has a maximum length which helps to prune the tree. If the parent node has white moving next, and we are playing as white, the children list keeps the highest heuristic values it finds. If we are playing as white and the parent node has black moving next, Children keeps the lowest values found. The reverse is true if we are playing as black. Finally, since the heuristics of the nodes change as we populate the tree, there is a function that returns the node with the  current highest/lowest heuristic. 


### CS322Test
This is the driving class that contains the main function, and the functions to interact with the server and the GUI. There is an instance of board which always reflects the current state of the game. When we receive a move from the server, this board is updated and is used to create the root node of the search tree. Then, just before we send a move to the server, this move is applied to our board. This is the main loop of the program, receiving a move, finding the best ove from the current board state, and sending the best move back to the server.

Since we have our internal board and tree associated with a 10x10 board labeled 0-9 with the top left being (0,0), whereas the server/GUI uses 1-10 with (1,1) in the bottom right, some manipulation is necessary to make our moves line up with the server and the GUI. There are two functions, one which takes a move from the server and converts it into the corresponding move on our internal board, and a second function that does the reverse.


## Game Tree Search

The game tree is calculated each time we make a move, using the current game state as the board for the root node. To make the children of this tree, for every reachable game state, we create a node with the associated board, and give the node an initial heuristic from its board. Then, since our Children class only keeps the 100 best moves, most of this level of the tree is ignored. This is the only pruning that takes place, but it significantly reduces the size of the tree in the early and middle stages of the game where it is common to have +1000 possible moves. Then, on these 100 best candidates for the best moves, we continue the process of creating children, keeping up to the 100 best heuristics found. 




Here is a simplified example where we have a max depth of 2 and Children only keeps the best 2. From the root node, there are a number of possible board states, all given initial heuristic values. From here only the best 2, in this case the lowest (those that the opponent is likely to play) will be explored further. The dotted line represents a node that was pruned after not making the list of best nodes. Then, when we reach the maximum depth, the children are created and pruned in the same fashion, and the best node is taken as the heuristic value of the parent. In the case of the nodes on the left, the two best nodes for black, 4 & 12, are considered and the minimum is chosen and the parent node is given the final value of 4. This is repeated and propagated up the tree to the root. When all nodes have had their heuristics updated, the root node chooses the child with the highest heuristic and relays the move to make that board to the server.
