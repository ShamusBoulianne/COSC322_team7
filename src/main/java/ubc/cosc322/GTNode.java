package ubc.cosc322;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GTNode implements Comparable<GTNode>{
    private final int maxDepth = 3;
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

        makeBoard();
        this.board.updateGameState(moveToGetHere);
        this.board.setPlayerQueenNum(this.playerQueenNum);

        this.heuristic = this.board.getHeuristic();
        this.depth = this.parent.getDepth() + 1;
        
        //System.out.println(this.toString());
    }

    public GTNode(Board board){
        //used to create the root

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

    public GTNode getParent() {
        return parent;
    }

    public Children getChildren() {
        return children;
    }

    public int getPlayerQueenNum() {
        return playerQueenNum;
    }

    public int getDepth() {
        return depth;
    }

    private void updateHeuristic(){
        if(this.depth < maxDepth){
            this.children = new Children(this.playerQueenNum != 1);
            ArrayList<Move> makeableMoves = this.board.getPossibleMoves();
            for(Move move : makeableMoves){
                this.children.addFirstPass(new GTNode(this, move));
            }
            for(GTNode node: this.children.getFirstPass())
                node.updateHeuristic();
            this.heuristic = this.children.getBestChild().getHeuristic();
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
