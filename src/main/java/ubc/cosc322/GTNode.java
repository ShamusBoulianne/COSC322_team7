package ubc.cosc322;

import java.util.ArrayList;

public class GTNode implements Comparable<GTNode>{
    private final int maxDepth = 11;
    private final int milliSecondsToFinish = 15*1000;
    private double finishTime;
    private double nodeHeuristic;
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

        if(this.board == null){
            board = new Board(parent.getBoard(), moveToGetHere);
        }

        this.nodeHeuristic = this.board.getHeuristic();
        this.depth = this.parent.getDepth() + 1;
        
        //System.out.println(this.toString());
    }

    public GTNode(Board board){
        //used to create the root
        this.finishTime = milliSecondsToFinish + System.currentTimeMillis();

        this.board = new Board();
        this.board.setBoard(board.getBoardArray());
        this.board.setPlayerQueenNum(board.getPlayerQueenNum());
        this.depth = 0;
        this.playerQueenNum = this.board.getPlayerQueenNum();
        updateHeuristic();
    }

    public double getNodeHeuristic(){ return nodeHeuristic;}

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
            if(this.nodeHeuristic == Double.POSITIVE_INFINITY || this.nodeHeuristic == Double.NEGATIVE_INFINITY)
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
                this.nodeHeuristic = this.children.getBestChild().getNodeHeuristic();
            }catch (NullPointerException e){}
        }
    }

    public int compareTo(GTNode other){
        return (this.nodeHeuristic < other.nodeHeuristic)? -1:1;
    }

    public String toString(){
        if(moveToGetHere == null)
            return "Root node";
        return "Node created: Depth:" + this.getDepth() + "  Move to get here:" + this.moveToGetHere.toString() + "  Heuristic:" + this.getNodeHeuristic();
    }

}
