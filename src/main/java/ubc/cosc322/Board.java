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

    public double getHeuristic(Move moveMade, int playerQueenNum){
        Coordinate currentPos = moveMade.getQueenCurr();
        Coordinate newPos = moveMade.getQueenMove();
        Coordinate arrowPos = moveMade.getArrow();
        // Total value of the move
        // Positive is good for player 1, negative is good for player 2
        // The reasoning for the positive/negative decision is described in GTNode.java
        double moveValue = 0;

        // See if moving closer or further from placed arrows
        if(playerQueenNum == 1){
            // Arrow distances are particularly useful early on, less so in the late game
            moveValue += getNearestArrowDistance(newPos)-getNearestArrowDistance(currentPos);
            moveValue += checkSurroundings(newPos, playerQueenNum) - checkSurroundings(currentPos, playerQueenNum);
            moveValue += arrowAiming(arrowPos, playerQueenNum);
            moveValue += getNumberOfMoves(newPos);
        }
        else {
            moveValue -= getNearestArrowDistance(newPos)-getNearestArrowDistance(currentPos);
            moveValue -= checkSurroundings(newPos, playerQueenNum) - checkSurroundings(currentPos, playerQueenNum);
            moveValue -= arrowAiming(arrowPos, playerQueenNum);
            moveValue -= getNumberOfMoves(newPos);
        }


        return moveValue;
    }

    // Determine the number of possible moves a queen will have in a space
    private double getNumberOfMoves(Coordinate queenPos){
        int numMoves = 0;
        for (Coordinate queenMove : getReachableCoordinates(queenPos)) {
            for (Coordinate arrow : getReachableCoordinates(queenMove))
                // Ensure that the number of moves is weighted heavily
                numMoves += 3;
        }

        return numMoves;
    }

    // Method to turn arrow coordinates back into the absolute distance
    private double getNearestArrowDistance(Coordinate queenPos){
        Coordinate arrowPos = getNearestArrowPosition(queenPos);
        int xdif = Math.abs(arrowPos.getX()-queenPos.getX());
        int ydif = Math.abs(arrowPos.getY()-queenPos.getY());
        if (xdif == 0 && ydif == 0)
            return 0;
        return Math.sqrt((xdif*xdif)+(ydif*ydif));
    }

    // Method for determining Coordinates of arrow
    private Coordinate getNearestArrowPosition(Coordinate queenPos){
        double distanceToArrow = 100;
        Coordinate arrowPos = queenPos;

        // Run through all Y rows
        for (int y = 0; y<10; y++){
            // Run through all X columns
            for (int x = 0; x<10; x++){
                if (board[y][x] == 3){
                    int xdif = Math.abs(x-queenPos.getX());
                    int ydif = Math.abs(y-queenPos.getY());
                    // Determine absolute distance
                    double newDistance = Math.sqrt((xdif*xdif)+(ydif*ydif));
                    if (newDistance < distanceToArrow) {
                        distanceToArrow = newDistance;
                        arrowPos = new Coordinate(y,x);
                    }
                }
            }
        }
        return arrowPos;
    }

    // Method for determining value of an arrow placement
    private double arrowAiming(Coordinate arrowPos, int playerQueenNum){
        double distanceToQueen = 100;
        int queenTeam = playerQueenNum;

        // Run through all Y rows
        for (int y = 0; y<10; y++){
            // Run through all X columns
            for (int x = 0; x<10; x++){
                // Check if any queen is on the space, the computer will then determine if that's good or bad
                if (board[y][x] == 1 || board[y][x] == 2){
                    // Determine which team is on the space
                    int queenCheck = board[y][x];
                    int xdif = Math.abs(x-arrowPos.getX());
                    int ydif = Math.abs(y-arrowPos.getY());
                    // Determine absolute distance
                    double aimDistance = Math.sqrt((xdif*xdif)+(ydif*ydif));
                    // If two queens are the same distance from the arrow, focus on the queen on the player's team
                    if (aimDistance <= distanceToQueen && queenCheck == playerQueenNum) {
                        queenTeam = playerQueenNum;
                        distanceToQueen = aimDistance;
                    }
                    else if (aimDistance < distanceToQueen && queenCheck != playerQueenNum){
                        queenTeam = queenCheck;
                        distanceToQueen = aimDistance;
                    }
                }
            }
        }

        // The move is very poor if the arrow is placed close to a friendly queen
        if (queenTeam == playerQueenNum){
            distanceToQueen = distanceToQueen * -0.5;
        }

        // Simple equation so that the value of placing an arrow closer to a queen is greater magnitude
        return 10/distanceToQueen;
    }

    // Method for determining how surrounded the queen is or will be
    private int checkSurroundings(Coordinate queenPos, int playerQueenNum){
        int totalValue = 0;
        int xPos = queenPos.getX();
        int yPos = queenPos.getY();

        // If not along an edge
        if(yPos < 9 && yPos > 0 && xPos < 9 && xPos >0){
            for(int a = 0; a < 3; a++){
                // Don't want to move all queens into the same spot
                if (board[yPos+1][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos+1][(xPos-1)+a] != 0)
                    totalValue -=1;
                // Space is clear
                else
                    totalValue +=1;
            }
            for (int a = 0; a < 3; a++){
                // Ignore current position
                if (a == 1)
                    continue;

                // Don't want to move all queens into the same spot
                if (board[yPos][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
            for (int a = 0; a < 3; a++){
                // Don't want to move all queens into the same spot
                if(board[yPos-1][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos-1][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
        }
        // If along the upper edge
        else if (yPos == 9 && xPos < 9 && xPos >0){
            // Account for missing spaces
            totalValue -= 3;
            for (int a = 0; a < 3; a++){
                // Ignore current position
                if (a == 1)
                    continue;

                // Don't want to move all queens into the same spot
                if (board[yPos][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
            for (int a = 0; a < 3; a++){
                // Don't want to move all queens into the same spot
                if(board[yPos-1][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos-1][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
        }
        // If along the lower edge
        else if (yPos == 0 && xPos < 9 && xPos >0){
            // Account for missing spaces
            totalValue -= 3;
            for (int a = 0; a < 3; a++){
                // Ignore current position
                if (a == 1)
                    continue;

                // Don't want to move all queens into the same spot
                if (board[yPos][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
            for (int a = 0; a < 3; a++){
                // Don't want to move all queens into the same spot
                if(board[yPos+1][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos+1][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
        }
        // If along the right edge
        else if(yPos < 9 && yPos > 0 && xPos == 9){
            totalValue -= 3;
            for(int a = 0; a < 2; a++){
                // Don't want to move all queens into the same spot
                if (board[yPos+1][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos+1][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
            for (int a = 0; a < 2; a++){
                // Ignore current position
                if (a == 1)
                    continue;

                // Don't want to move all queens into the same spot
                if (board[yPos][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
            for (int a = 0; a < 2; a++){
                // Don't want to move all queens into the same spot
                if(board[yPos-1][(xPos-1)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos-1][(xPos-1)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
        }
        // If along the left edge
        else if(yPos < 9 && yPos > 0 && xPos == 0){
            totalValue -= 3;
            for(int a = 0; a < 2; a++){
                // Don't want to move all queens into the same spot
                if (board[yPos+1][(xPos)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos+1][(xPos)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
            for (int a = 0; a < 2; a++){
                // Ignore current position
                if (a == 0)
                    continue;

                // Don't want to move all queens into the same spot
                if (board[yPos][(xPos)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos][(xPos)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
            for (int a = 0; a < 2; a++){
                // Don't want to move all queens into the same spot
                if(board[yPos-1][(xPos)+a] == playerQueenNum)
                    totalValue -= 2;
                else if (board[yPos-1][(xPos)+a] != 0)
                    totalValue -=1;
                    // Space is clear
                else
                    totalValue +=1;
            }
        }
        // Avoid corners, they restrict movement significantly
        else
            totalValue -= 7;

        return totalValue;
    }


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
