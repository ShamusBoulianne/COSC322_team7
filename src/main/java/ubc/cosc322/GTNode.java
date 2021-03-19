package ubc.cosc322;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GTNode implements Comparable<GTNode>{
    private final int maxDepth = 1;
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

        this.playerQueenNum = ((this.parent.getBoard().getPlayerQueenNum()-1) %2) +1;

        this.heuristic = parent.board.getHeuristic(moveToGetHere, playerQueenNum);
        this.depth = this.parent.getDepth() + 1;

        System.out.println(this.toString());

        this.makeChildren();
    }

    public GTNode(Board board){
        //used to create the root

        this.board = new Board();
        this.board.setBoard(board.getBoard());
        this.board.setPlayerQueenNum(board.getPlayerQueenNum());
        this.depth = 0;
        this.playerQueenNum = this.board.getPlayerQueenNum();
    }

    public void makeBoard(){
        if(this.board == null) {
            this.board = new Board();
            this.board.setBoard(this.parent.getBoard().getBoard());
            this.board.setPlayerQueenNum(this.playerQueenNum);
            this.board.updateGameState(moveToGetHere);
        }

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
        if(this.depth<maxDepth) {
            makeBoard();
            this.children = new PriorityQueue<>();
            ArrayList<Move> makeableMoves = this.board.getPossibleMoves();
            for (Move move : makeableMoves)
                this.children.add(new GTNode(this, move));
        }
    }

    public int compareTo(GTNode other){
        if(playerQueenNum == 1)
            if(heuristic > other.heuristic)
                return -1;
            else
                return 1;
        else{
            if(heuristic > other.heuristic)
                return 1;
            else
                return -1;
        }
    }

    public String toString(){
        return "Node created: Depth:" + this.getDepth() + "  Move to get here:" + this.moveToGetHere.toString() + "  Heuristic:" + this.getHeuristic();
    }

}
