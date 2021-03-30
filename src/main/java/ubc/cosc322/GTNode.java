package ubc.cosc322;

import java.util.ArrayList;

public class GTNode implements Comparable<GTNode>{
    private final int maxDepth = 3;
    private final int milliSecondsToFinish = 5*1000;
    private double finishTime;
    private double heuristic;
    private Board board;
    private Move moveToGetHere;
    private GTNode parent;
    private Children children;
    private int playerQueenNum;
    private int depth;

    public GTNode(GTNode parent, Move moveToGetHere){
        this.parent = parent;
        this.moveToGetHere = moveToGetHere;
        this.playerQueenNum = ((this.parent.getBoard().getPlayerQueenNum()) %2) +1;
        this.finishTime = this.parent.finishTime;

        makeBoard();
        this.board.updateGameState(moveToGetHere);
        this.board.setPlayerQueenNum(this.playerQueenNum);

        this.heuristic = this.board.getHeuristic();
        this.depth = this.parent.getDepth() + 1;
        
        //System.out.println(this.toString());
    }

    public GTNode(Board board){
        //used to create the root
        this.finishTime = milliSecondsToFinish + System.currentTimeMillis();

        this.board = new Board();
        this.board.setBoard(board.getBoard());
        this.board.setPlayerQueenNum(board.getPlayerQueenNum());
        this.depth = 0;
        this.playerQueenNum = this.board.getPlayerQueenNum();
        updateHeuristic();
    }

    public void makeBoard(){
        if(this.board == null) {
            this.board = this.parent.getBoard().duplicateBoard();
            this.board.updateGameState(this.moveToGetHere);
            this.board.setPlayerQueenNum(this.playerQueenNum);
        }

    }

    public double getHeuristic(){ return heuristic;}

    public Board getBoard() {
        return board;
    }

    public Move getMoveToGetHere() {
        return moveToGetHere;
    }

    public Children getChildren() {
        return children;
    }

    public int getDepth() {
        return depth;
    }

    private void updateHeuristic(){
        if(this.depth < maxDepth){
            if(this.heuristic == Double.POSITIVE_INFINITY)
                return;
            if(this.heuristic == Double.NEGATIVE_INFINITY)
                return;
            this.children = new Children(this.playerQueenNum != 1);
            ArrayList<Move> makeableMoves = this.board.getPossibleMoves();
            for(Move move : makeableMoves){
                if(System.currentTimeMillis()>finishTime)
                    break;
                this.children.addFirstPass(new GTNode(this, move));
            }
            try {
                for (GTNode node : this.children.getFirstPass()) {
                    if (System.currentTimeMillis() > finishTime)
                        break;
                    node.updateHeuristic();
                }
                this.heuristic = this.children.getBestChild().getHeuristic();
            }catch (NullPointerException e){
                System.out.print("Null pointer caught in updateHeuristic");
            }

        }
    }

    public int compareTo(GTNode other){
        if(playerQueenNum == 1)
            if(heuristic > other.heuristic)
                return -1;
            else
                return 1;
        else{
            if(heuristic >= other.heuristic)
                return 1;
            else
                return -1;
        }
    }

    public String toString(){
        if(moveToGetHere == null)
            return "Root node";
        return "Node created: Depth:" + this.getDepth() + "  Move to get here:" + this.moveToGetHere.toString() + "  Heuristic:" + this.getHeuristic();
    }

}
