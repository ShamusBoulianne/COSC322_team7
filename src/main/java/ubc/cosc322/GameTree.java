package ubc.cosc322;

public class GameTree {
    private GTNode root;
    private int maxDepthToSearch;
    private int playerQueenNum;

    public GameTree(GTNode root, int maxDepthToSearch, int playerQueenNum){
        this.root = root;
        this.maxDepthToSearch = maxDepthToSearch;
        this.playerQueenNum = playerQueenNum;
    }

    public void populateTree(){
        this.root.makeChildren();
    }

    public Move getBestMove(){
        GTNode bestChild = this.root.getChildren().poll();
        this.root = bestChild;

        System.out.println("The heuristic of bestChild is " + bestChild.getHeuristic());
        return bestChild.getMoveToGetHere();
    }
}
