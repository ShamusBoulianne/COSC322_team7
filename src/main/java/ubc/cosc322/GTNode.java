package ubc.cosc322;

import java.util.PriorityQueue;

public class GTNode implements Comparable<GTNode>{
    private double heuristic;
    private Board board;
    private Move moveToGetHere;
    private GTNode parent;
    private PriorityQueue<GTNode> children;
    private int playerQueenNum;
    private int depth;

    public GTNode(GTNode parent, Move moveToGetHere){
        this.parent = parent;
        this.moveToGetHere = moveToGetHere;

        this.playerQueenNum = (this.parent.getBoard().getPlayerQueenNum()+1) %2;

        this.board = new Board();
        this.board.setBoard(this.parent.getBoard().getBoard());
        this.board.setPlayerQueenNum(this.playerQueenNum);
        this.board.updateGameState(moveToGetHere);

        this.heuristic = this.board.getHeuristic();
        this.depth = this.parent.getDepth() + 1;
    }

    public double getHeuristic() {
        return heuristic;
    }

    public Board getBoard() {
        return board;
    }

    public Move getMoveToGetHere() {
        return moveToGetHere;
    }

    public GTNode getParent() {
        return parent;
    }

    public PriorityQueue<GTNode> getChildren() {
        return children;
    }

    public int getPlayerQueenNum() {
        return playerQueenNum;
    }

    public int getDepth() {
        return depth;
    }

    public void makeChildren(){
        if(this.depth<2)
            for(Move move: this.board.getPossibleMoves())
                this.children.add(new GTNode(this, move));
    }

    public int compareTo(GTNode other){
        if(playerQueenNum == 1)
            if(heuristic > other.heuristic)
                return -1;
            else
                return 1;
        return 1;
    }

}
