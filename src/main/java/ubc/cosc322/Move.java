package ubc.cosc322;

import java.util.ArrayList;

public class Move {

    private ArrayList<Integer> queenCurr;
    private ArrayList<Integer> queenMove;
    private ArrayList<Integer> arrow;

    public ArrayList<Integer> getQueenCurr() {
        return queenCurr;
    }

    public void setQueenCurr(ArrayList<Integer> queenCurr) {
        if(isOnBoard(queenCurr))
            this.queenCurr = queenCurr;
    }

    public ArrayList<Integer> getQueenMove() {
        return queenMove;
    }

    public void setQueenMove(ArrayList<Integer> queenMove) {
        if(isOnBoard(queenMove))
            this.queenMove = queenMove;
    }

    public ArrayList<Integer> getArrow() {
        return arrow;
    }

    public void setArrow(ArrayList<Integer> arrow) {
        if(isOnBoard(arrow))
            this.arrow = arrow;
    }

    private boolean isOnBoard(ArrayList<Integer> space){
        if(space.get(0)< 0 || space.get(0) > 9 || space.get(1) < 0 || space.get(1) > 9)
            return false;
        return true;
    }
}
