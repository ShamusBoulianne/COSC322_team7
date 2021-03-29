package ubc.cosc322;

public class GameTree {
    /*private GTNode root;
    private int playerQueenNum;

    public GameTree(GTNode root, int playerQueenNum){
        this.root = root;
        this.playerQueenNum = playerQueenNum;
    }

    public void populateTree(){
        this.root.updateHeuristic();
    }

    public GTNode getRoot(){
        return this.root;
    }

    public Move getBestMove(){
        GTNode bestChild = this.root.getChildren().head.gtNode;
        this.root = bestChild;

        System.out.println("The heuristic of bestChild is " + bestChild.getHeuristic());
        return bestChild.getMoveToGetHere();
    }

    public void displayFirstMoves(){
        GTNode lookAt = root;
        System.out.println(lookAt.toString());
        root.getBoard().printBoard();
        while(lookAt.getChildren()!= null){
            lookAt = lookAt.getChildren().head.gtNode;
            System.out.println(lookAt.toString());
            if(lookAt.getBoard() != null)
                lookAt.getBoard().printBoard();
        }
    }
     */
}
