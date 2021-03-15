package ubc.cosc322;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    private int[][] board;
    private int playerQueenNum = 1;

    public Board(ArrayList<Integer> boardList) {
        setBoard(boardList);
    }
    public Board(){
        this.board = new int[10][10];
    }

    public void setBoard(ArrayList<Integer> boardList){
        this.board = new int[10][10];
        int row = 0;
        int column = 0;
        for (int i = 12; i < boardList.size(); i++) {
            this.board[row][column] = boardList.get(i);
            column = ++column % 10;
            if ((i + 1) % 11 == 0) {
                ++i;
                ++row;
            }
        }
    }

    public int[][] getBoard(){
        return this.board;
    }

    public int getPlayerQueenNum(){
        return playerQueenNum;
    }

    public void setPlayerQueenNum(int num){
        this.playerQueenNum = num;
    }

    public void printBoard(){
        String output = "";
        for(int r = 0; r< this.board.length; ++r){
            for(int c = 0; c<this.board[r].length; ++c)
                output += this.board[r][c] + " ";
            output += "\n";
        }
        System.out.println(output);
        System.out.println();
    }

    public void printMoves(){
        ArrayList<Move> moves = getPossibleMoves();
        for(Move i: moves){
            System.out.print(i.toString());
        }
    }

    public ArrayList<Coordinate> getQueenCoordinates(){
        ArrayList<Coordinate> pos = new ArrayList();
        int queensFound = 0;
        for(int r=0; r< board.length; ++r)
            for(int c=0; c<board[r].length; ++c)
                if(board[r][c] == this.playerQueenNum){
                    pos.add(new Coordinate(r, c));
                    ++queensFound;
                }
        return pos;
    }

    public ArrayList<Coordinate> getReachableCoordinates(Coordinate queenCurr){
        ArrayList<Coordinate> moves = new ArrayList<>();
        //Straights
        moves.addAll(getStraightLeftCoordinates(queenCurr));
        moves.addAll(getStraightRightCoordinates(queenCurr));
        moves.addAll(getStraightUpCoordinates(queenCurr));
        moves.addAll(getStraightDownCoordinates(queenCurr));
        //Diagonals
        moves.addAll(getDownLeftCoordinates(queenCurr));
        moves.addAll(getDownRightCoordinates(queenCurr));
        moves.addAll(getUpLeftCoordinates(queenCurr));
        moves.addAll(getUpRightCoordinates(queenCurr));

        return moves;
    }

    public ArrayList<Coordinate> getStraightLeftCoordinates(Coordinate queenPos){
        ArrayList<Coordinate> leftMoves = new ArrayList<>();
        for(int newX=queenPos.getX()-1; newX>=0; --newX){
            if(board[queenPos.getY()][newX] != 0)
                break;
            leftMoves.add(new Coordinate(queenPos.getY(), newX));
        }
        return leftMoves;
    }

    public ArrayList<Coordinate> getStraightRightCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> rightMoves = new ArrayList<>();
        for (int newX = queenPos.getX() + 1; newX < 10; ++newX) {
            if (board[queenPos.getY()][newX] != 0)
                break;
            rightMoves.add(new Coordinate(queenPos.getY(), newX));
        }
        return rightMoves;
    }

    public ArrayList<Coordinate> getStraightUpCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> upMoves = new ArrayList<>();
        for (int newY = queenPos.getY() - 1; newY >= 0; --newY) {
            if (board[newY][queenPos.getX()] != 0)
                break;
            upMoves.add(new Coordinate(newY, queenPos.getX()));
        }
        return upMoves;
    }

    public ArrayList<Coordinate> getStraightDownCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> downMoves = new ArrayList<>();
        for (int newY = queenPos.getY() + 1; newY < 10; ++newY) {
            if (board[newY][queenPos.getX()] != 0)
                break;
            downMoves.add(new Coordinate(newY, queenPos.getX()));
        }
        return downMoves;
    }

    public ArrayList<Coordinate> getDownLeftCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> downLeftMoves = new ArrayList<>();
        for (int count = 1; count < 10; ++count) {
            int newY = queenPos.getY()+count;
            int newX = queenPos.getX()-count;
            if (newY<0 || newY>9 || newX<0 || newX>9 || board[newY][newX] != 0)
                break;
            downLeftMoves.add(new Coordinate(newY, newX));
        }
        return downLeftMoves;
    }

    public ArrayList<Coordinate> getDownRightCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> downRightMoves = new ArrayList<>();
        for (int count = 1; count < 10; ++count) {
            int newY = queenPos.getY()+count;
            int newX = queenPos.getX()+count;
            if (newY<0 || newY>9 || newX<0 || newX>9 || board[newY][newX] != 0)
                break;
            downRightMoves.add(new Coordinate(newY, newX));
        }
        return downRightMoves;
    }

    public ArrayList<Coordinate> getUpLeftCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> upLeftMoves = new ArrayList<>();
        for (int count = 1; count < 10; ++count) {
            int newY = queenPos.getY()-count;
            int newX = queenPos.getX()-count;
            if (newY<0 || newY>9 || newX<0 || newX>9 || board[newY][newX] != 0)
                break;
            upLeftMoves.add(new Coordinate(newY, newX));
        }
        return upLeftMoves;
    }

    public ArrayList<Coordinate> getUpRightCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> upRightMoves = new ArrayList<>();
        for (int count = 1; count < 10; ++count) {
            int newY = queenPos.getY()-count;
            int newX = queenPos.getX()+count;
            if (newY<0 || newY>9 || newX<0 || newX>9 || board[newY][newX] != 0)
                break;
            upRightMoves.add(new Coordinate(newY, newX));
        }
        return upRightMoves;
    }

    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> moves = new ArrayList();
        for(Coordinate queenCurr: getQueenCoordinates())
            for (Coordinate queenMove : getReachableCoordinates(queenCurr)) {
                for (Coordinate arrow : getReachableCoordinates(queenMove))
                    moves.add(new Move(queenCurr, queenMove, arrow));
            }
        System.out.println("There are " + moves.size() + " moves from this position");
        return moves;
    }

    public Move pickMove(){
        return getPossibleMoves().get(0);
    }

    public void updateGameState(Move move){
        board[move.getQueenCurr().getY()][move.getQueenCurr().getX()] = 0;
        board[move.getQueenMove().getY()][move.getQueenMove().getY()]= this.playerQueenNum;
        board[move.getArrow().getY()][move.getArrow().getX()] = 3;}
}
