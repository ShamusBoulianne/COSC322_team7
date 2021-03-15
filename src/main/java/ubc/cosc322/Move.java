package ubc.cosc322;

import java.util.ArrayList;

public class Move {

    private Coordinate queenCurr;
    private Coordinate queenMove;
    private Coordinate arrow;

    public Move(Coordinate curr, Coordinate move, Coordinate arrowspot){
        setQueenCurr(curr);
        setQueenMove(move);
        setArrow(arrowspot);
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

}
