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

    public Board duplicateBoard(){
        Board outboard = new Board();
        outboard.setBoard(this.getBoard());
        outboard.setPlayerQueenNum(this.getPlayerQueenNum());
        return outboard;
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

    public void setBoard(int[][] board){
        for(int y=0; y< board.length; ++y)
            for(int x=0; x<board[y].length; ++x)
                this.board[y][x] = board[y][x];
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

    public ArrayList<Coordinate> getQueenCoordinates(int queenNum){
        ArrayList<Coordinate> pos = new ArrayList();
        for(int r=0; r< board.length; ++r)
            for(int c=0; c<board[r].length; ++c)
                if(board[r][c] == queenNum){
                    pos.add(new Coordinate(r, c));
                }
        return pos;
    }

    public ArrayList<Coordinate> getArrowCoordinates(){
        ArrayList<Coordinate> pos = new ArrayList();
        for(int r=0; r< board.length; ++r)
            for(int c=0; c<board[r].length; ++c)
                if(board[r][c] == 3){
                    pos.add(new Coordinate(r, c));
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

    private ArrayList<Coordinate> getStraightLeftCoordinates(Coordinate queenPos){
        ArrayList<Coordinate> leftMoves = new ArrayList<>();
        for(int newX=queenPos.getX()-1; newX>=0; --newX){
            if(board[queenPos.getY()][newX] != 0)
                break;
            leftMoves.add(new Coordinate(queenPos.getY(), newX));
        }
        return leftMoves;
    }

    private ArrayList<Coordinate> getStraightRightCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> rightMoves = new ArrayList<>();
        for (int newX = queenPos.getX() + 1; newX < 10; ++newX) {
            if (board[queenPos.getY()][newX] != 0)
                break;
            rightMoves.add(new Coordinate(queenPos.getY(), newX));
        }
        return rightMoves;
    }

    private ArrayList<Coordinate> getStraightUpCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> upMoves = new ArrayList<>();
        for (int newY = queenPos.getY() - 1; newY >= 0; --newY) {
            if (board[newY][queenPos.getX()] != 0)
                break;
            upMoves.add(new Coordinate(newY, queenPos.getX()));
        }
        return upMoves;
    }

    private ArrayList<Coordinate> getStraightDownCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> downMoves = new ArrayList<>();
        for (int newY = queenPos.getY() + 1; newY < 10; ++newY) {
            if (board[newY][queenPos.getX()] != 0)
                break;
            downMoves.add(new Coordinate(newY, queenPos.getX()));
        }
        return downMoves;
    }

    private ArrayList<Coordinate> getDownLeftCoordinates(Coordinate queenPos) {
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

    private ArrayList<Coordinate> getDownRightCoordinates(Coordinate queenPos) {
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

    private ArrayList<Coordinate> getUpLeftCoordinates(Coordinate queenPos) {
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

    private ArrayList<Coordinate> getUpRightCoordinates(Coordinate queenPos) {
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
        for(Coordinate queenCurr: getQueenCoordinates(this.playerQueenNum))
            for (Coordinate queenMove : getReachableCoordinates(queenCurr)) {
                for (Coordinate arrow : getReachableCoordinates(queenMove))
                    moves.add(new Move(queenCurr, queenMove, arrow));
            }
        //System.out.println("There are " + moves.size() + " moves from this position");
        return moves;
    }

    // Deprecated test function
    public int countPossibleMoves(int queenNum){
        int count = 0;
        for(Coordinate queenCurr: getQueenCoordinates(queenNum))
            for (Coordinate queenMove : getReachableCoordinates(queenCurr)) {
                for (Coordinate arrow : getReachableCoordinates(queenMove))
                    ++count;
            }
        return count;
    }

    public void updateGameState(Move move){
        board[move.getQueenCurr().getY()][move.getQueenCurr().getX()] = 0;
        board[move.getQueenMove().getY()][move.getQueenMove().getX()]= this.playerQueenNum;
        board[move.getArrow().getY()][move.getArrow().getX()] = 3;
    }

    // NOTE: Consider moving heuristic calculations to its own class

    public double getHeuristic(){
        // Total value of the board
        // Positive is good for player 1, negative is good for player 2
        // The reasoning for the positive/negative decision is described in GTNode.java
        double moveValue = 0;

        for (int team = 1; team<3;team++){
            // valueCounter checks the value of the board for each team individually
            // then adds it the total value
            int valueCounter = 0;
            valueCounter += getNumberOfMoves(team);
            valueCounter += arrowAimingIntermediary(team);
            // Change the sign for the value of team 2 moves
            if (team == 2)
                valueCounter = valueCounter*-1;
            moveValue += valueCounter;
        }

        return moveValue;
    }

    // Determine the number of possible moves a team will have
    private double getNumberOfMoves(int queenNum){
        int numMoves = 0;
        for(Coordinate queenCurr: getQueenCoordinates(queenNum))
            for (Coordinate queenMove : getReachableCoordinates(queenCurr)) {
                for (Coordinate arrow : getReachableCoordinates(queenMove))
                    // Ensure the number of moves is heavily weighted
                    numMoves+=2;
            }
        return numMoves;
    }

    // Determine the number of possible moves a team will have
    private double arrowAimingIntermediary(int queenNum){
        int arrowValues = 0;
        for (Coordinate arrow : getArrowCoordinates())
            arrowValues += arrowAiming(arrow, queenNum);
        return arrowValues;
    }

    // Method for determining value of an arrow placement
    // Previously checked what team the nearest queen was,
    // will now check what queen an arrow is near
    private double arrowAiming(Coordinate arrowPos, int playerQueenNum){
        double arrowValue = 0;

        // Run through all Y rows
        for (int y = 0; y<10; y++){
            // Run through all X columns
            for (int x = 0; x<10; x++){
                // Check if any queen is on the space, the computer will then determine if that's good or bad
                if ((board[y][x] == 1 || board[y][x] == 2) &&
                        ((x == arrowPos.getX()-1 || x == arrowPos.getX()+1 || x == arrowPos.getX()) &&
                        (y == arrowPos.getY()-1 || y == arrowPos.getY()+1 || y == arrowPos.getY()))){
                    // Ignore space the arrow is on
                    if(x == arrowPos.getX() && y == arrowPos.getY())
                        continue;

                    // Determine which team is on the space
                    int queenCheck = board[y][x];

                    // Heavily penalize arrows next to a friendly queen
                    if (queenCheck == playerQueenNum) {
                        arrowValue -= 0.5;
                    }

                    // Support arrows next to enemy
                    else if (queenCheck != 0 && queenCheck != 3){
                        arrowValue += 0.1;
                    }
                }
            }
        }
        return arrowValue;
    }


    // Deprecated test function
    public double getRatioOurMovesToOpponentMoves(){
        double blackMoves = countPossibleMoves(2);
        double whiteMoves = countPossibleMoves(1);
        if(blackMoves == 0){
            return 5000;
        }
        if(whiteMoves == 0){
            return -5000;
        }
        return Math.log(whiteMoves / blackMoves);
    }
}
