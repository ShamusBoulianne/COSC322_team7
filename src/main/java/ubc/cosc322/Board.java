package ubc.cosc322;

import java.util.ArrayList;

public class Board {
    private int[][] boardArray;
    private int playerQueenNum = 1;
    private double heuristic;

    public Board(){
        this.boardArray = new int[10][10];
    }

    public Board(Board prevBoard, Move moveToGetHere){
        this();
        this.setBoard(prevBoard.getBoardArray());
        this.updateGameState(moveToGetHere);
        this.setPlayerQueenNum(prevBoard.getPlayerQueenNum()%2+1);
        calculateHeuristic();
    }

    public Board duplicateBoard(){
        Board outboard = new Board();
        outboard.setBoard(this.getBoardArray());
        outboard.setPlayerQueenNum(this.getPlayerQueenNum());
        return outboard;
    }

    public void setBoardArray(ArrayList<Integer> boardList){
        this.boardArray = new int[10][10];
        int row = 0;
        int column = 0;
        for (int i = 12; i < boardList.size(); i++) {
            this.boardArray[row][column] = boardList.get(i);
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
                this.boardArray[y][x] = board[y][x];
    }

    public int[][] getBoardArray(){
        return this.boardArray;
    }

    public int getPlayerQueenNum(){
        return playerQueenNum;
    }

    public void setPlayerQueenNum(int num){
        this.playerQueenNum = num;
    }

    public double getHeuristic(){
        return this.heuristic;
    }

    public void printBoard(){
        String output = "";
        for(int r = 0; r< this.boardArray.length; ++r){
            for(int c = 0; c<this.boardArray[r].length; ++c)
                output += this.boardArray[r][c] + " ";
            output += "\n";
        }
        System.out.println(output);
        System.out.println();
    }

    private void setTile(Coordinate tile, int numToSetTo){
        boardArray[tile.getY()][tile.getX()] = numToSetTo;
    }

    private int getTile(Coordinate tile){return boardArray[tile.getY()][tile.getX()];}

    public ArrayList<Coordinate> getQueenCoordinates(int queenNum){
        ArrayList<Coordinate> pos = new ArrayList();
        for(int r = 0; r< boardArray.length; ++r)
            for(int c = 0; c< boardArray[r].length; ++c)
                if(boardArray[r][c] == queenNum){
                    pos.add(new Coordinate(r, c));
                }
        return pos;
    }

    private ArrayList<Coordinate> getReachableCoordinates(Coordinate queenCurr){
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
            if(boardArray[queenPos.getY()][newX] != 0)
                break;
            leftMoves.add(new Coordinate(queenPos.getY(), newX));
        }
        return leftMoves;
    }

    private ArrayList<Coordinate> getStraightRightCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> rightMoves = new ArrayList<>();
        for (int newX = queenPos.getX() + 1; newX < 10; ++newX) {
            if (boardArray[queenPos.getY()][newX] != 0)
                break;
            rightMoves.add(new Coordinate(queenPos.getY(), newX));
        }
        return rightMoves;
    }

    private ArrayList<Coordinate> getStraightUpCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> upMoves = new ArrayList<>();
        for (int newY = queenPos.getY() - 1; newY >= 0; --newY) {
            if (boardArray[newY][queenPos.getX()] != 0)
                break;
            upMoves.add(new Coordinate(newY, queenPos.getX()));
        }
        return upMoves;
    }

    private ArrayList<Coordinate> getStraightDownCoordinates(Coordinate queenPos) {
        ArrayList<Coordinate> downMoves = new ArrayList<>();
        for (int newY = queenPos.getY() + 1; newY < 10; ++newY) {
            if (boardArray[newY][queenPos.getX()] != 0)
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
            if (newY<0 || newY>9 || newX<0 || newX>9 || boardArray[newY][newX] != 0)
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
            if (newY<0 || newY>9 || newX<0 || newX>9 || boardArray[newY][newX] != 0)
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
            if (newY<0 || newY>9 || newX<0 || newX>9 || boardArray[newY][newX] != 0)
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
            if (newY<0 || newY>9 || newX<0 || newX>9 || boardArray[newY][newX] != 0)
                break;
            upRightMoves.add(new Coordinate(newY, newX));
        }
        return upRightMoves;
    }

    private ArrayList<Coordinate> getReachableArrowCoordinates(Coordinate queenMove, Coordinate queenCurr){
        int temp = getTile(queenCurr);
        setTile(queenCurr, 0);
        ArrayList<Coordinate> moves = getReachableCoordinates(queenMove);
        setTile(queenCurr, temp);
        return moves;
    }

    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> moves = new ArrayList();
        for(Coordinate queenCurr: getQueenCoordinates(this.playerQueenNum))
            for (Coordinate queenMove : getReachableCoordinates(queenCurr)) {
                for (Coordinate arrow : getReachableArrowCoordinates(queenMove, queenCurr))
                    moves.add(new Move(queenCurr, queenMove, arrow));
            }
        //System.out.println("There are " + moves.size() + " moves from this position");
        return moves;
    }

    private int countPossibleMoves(int queenNum){
        int count = 0;
        for(Coordinate queenCurr: getQueenCoordinates(queenNum))
            for (Coordinate queenMove : getReachableCoordinates(queenCurr)) {
                for (Coordinate arrow : getReachableArrowCoordinates(queenMove, queenCurr))
                    ++count;
            }
        return count;
    }

    public void updateGameState(Move move){
        boardArray[move.getQueenCurr().getY()][move.getQueenCurr().getX()] = 0;
        boardArray[move.getQueenMove().getY()][move.getQueenMove().getX()]= this.playerQueenNum;
        boardArray[move.getArrow().getY()][move.getArrow().getX()] = 3;
    }

    public void calculateHeuristic(){
        // Estimate of value of the board, + is good for white, - is good for black. Infinity means there are no more moves
        this.heuristic = getMoveDifference();
    }

    private double getMoveDifference() {
        // The difference in the number of moves between the teams
        int whiteMoves = countPossibleMoves(1);
        int blackMoves = countPossibleMoves(2);

        //Return infinity if a team has won
        if(whiteMoves == 0)
            return Double.NEGATIVE_INFINITY;
        if(blackMoves == 0)
            return Double.POSITIVE_INFINITY;

        return countPossibleMoves(1)-countPossibleMoves(2);
    }

}
