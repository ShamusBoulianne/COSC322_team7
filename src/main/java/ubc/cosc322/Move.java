package ubc.cosc322;

import java.util.ArrayList;
// A set of the Coordinates required to send a move to the server

public class Move {

    private Coordinate queenCurr;
    private Coordinate queenMove;
    private Coordinate arrow;


    public Move(ArrayList<Integer> curr, ArrayList<Integer> move, ArrayList<Integer> arrow){
        Coordinate queenCurr = new Coordinate(curr.get(1), curr.get(0));
        setQueenCurr(queenCurr);
        Coordinate queenMove = new Coordinate(move.get(1), move.get(0));
        setQueenMove(queenMove);
        Coordinate arrowSpot = new Coordinate(arrow.get(1), arrow.get(0));
        setArrow(arrowSpot);
    }
    public Move(Coordinate curr, Coordinate move, Coordinate arrow){
        setQueenCurr(curr);
        setQueenMove(move);
        setArrow(arrow);
    }



    public Coordinate getQueenCurr() {
        return queenCurr;
    }

    public void setQueenCurr(Coordinate queenCurr) {
            this.queenCurr = queenCurr;
    }

    public Coordinate getQueenMove() {
        return queenMove;
    }

    public void setQueenMove(Coordinate queenMove) {
            this.queenMove = queenMove;
    }

    public Coordinate getArrow() {
        return arrow;
    }

    public void setArrow(Coordinate arrow) {
            this.arrow = arrow;
    }

    public String toString(){
        return "  (queenCurr: " + this.queenCurr.toString() + " , queenMove: " + this.queenMove.toString()
                + " , arrow: " + this.arrow.toString() + ")  ";
    }
}
