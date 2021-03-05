package ubc.cosc322;

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

    public void printMoves(int[] queenPos){
        ArrayList<int[]> moves = getPossibleMoves(queenPos);
        for(int[] i: moves){
            System.out.print(" (" + i[0] + ", " + i[1] + ") ,");
        }
    }

    public int [][] getQueenPositions(){
        int[][] queenPositions = new int[4][2];
        int queensFound = 0;
        for(int r=0; r< board.length; ++r)
            for(int c=0; c<board[r].length; ++c)
                if(board[r][c] == this.playerQueenNum){
                    queenPositions[queensFound][0] = r;
                    queenPositions[queensFound][1] = c;
                    ++queensFound;
                }
        return queenPositions;
    }

    public ArrayList<int[]> getPossibleMoves(int[] queenCurr){
        int[][] queenPos = getQueenPositions();
        ArrayList<int[]> moves = new ArrayList<int[]>();
        //Straights
        moves.addAll(getStraightLeftMoves(queenCurr));
        moves.addAll(getStraightRightMoves(queenCurr));
        moves.addAll(getStraightUpMoves(queenCurr));
        moves.addAll(getStraightDownMoves(queenCurr));
        //Diagonals
        moves.addAll(getDownLeftMoves(queenCurr));
        moves.addAll(getDownRightMoves(queenCurr));
        moves.addAll(getUpLeftMoves(queenCurr));
        moves.addAll(getUpRightMoves(queenCurr));

        return moves;
    }

    public ArrayList<int[]> getStraightLeftMoves(int[] queenPos){
        ArrayList<int[]> leftMoves = new ArrayList<int[]>();
        for(int newCol=queenPos[1]-1; newCol>=0; --newCol){
            if(board[queenPos[0]][newCol] != 0)
                break;
            int[] move = {queenPos[0], newCol};
            leftMoves.add(move);
        }
        return leftMoves;
    }

    public ArrayList<int[]> getStraightRightMoves(int[] queenPos) {
        ArrayList<int[]> rightMoves = new ArrayList<int[]>();
        for (int newCol = queenPos[1] + 1; newCol < 10; ++newCol) {
            if (board[queenPos[0]][newCol] != 0)
                break;
            int[] move = {queenPos[0], newCol};
            rightMoves.add(move);
        }
        return rightMoves;
    }

    public ArrayList<int[]> getStraightUpMoves(int[] queenPos) {
        ArrayList<int[]> upMoves = new ArrayList<int[]>();
        for (int newRow = queenPos[0] - 1; newRow >= 0; --newRow) {
            if (board[newRow][queenPos[1]] != 0)
                break;
            int[] move = {newRow, queenPos[1]};
            upMoves.add(move);
        }
        return upMoves;
    }

    public ArrayList<int[]> getStraightDownMoves(int[] queenPos) {
        ArrayList<int[]> downMoves = new ArrayList<int[]>();
        for (int newRow = queenPos[0] + 1; newRow < 10; ++newRow) {
            if (board[newRow][queenPos[1]] != 0)
                break;
            int[] move = {newRow, queenPos[1]};
            downMoves.add(move);
        }
        return downMoves;
    }

    public ArrayList<int[]> getDownLeftMoves(int[] queenPos) {
        ArrayList<int[]> downLeftMoves = new ArrayList<int[]>();
        //Used to ensure that position moves to the side as much as it does down
        int leftCount = 1;
        for (int newRow = queenPos[0] + 1; newRow < 10; ++newRow) {
            if (board[newRow][queenPos[1]-leftCount] != 0)
                break;
            int[] move = {newRow, queenPos[1]-leftCount};
            downLeftMoves.add(move);
            leftCount++;
        }
        return downLeftMoves;
    }

    public ArrayList<int[]> getDownRightMoves(int[] queenPos) {
        ArrayList<int[]> downRightMoves = new ArrayList<int[]>();
        //Used to ensure that position moves to the side as much as it does down
        int rightCount = 1;
        for (int newRow = queenPos[0] + 1; newRow < 10; ++newRow) {
            if (board[newRow][queenPos[1]+rightCount] != 0)
                break;
            int[] move = {newRow, queenPos[1]+rightCount};
            downRightMoves.add(move);
            rightCount++;
        }
        return downRightMoves;
    }

    public ArrayList<int[]> getUpLeftMoves(int[] queenPos) {
        ArrayList<int[]> upLeftMoves = new ArrayList<int[]>();
        //Used to ensure that position moves to the side as much as it does down
        int leftCount = 1;
        for (int newRow = queenPos[0] - 1; newRow >= 0; --newRow) {
            if (board[newRow][queenPos[1]-leftCount] != 0)
                break;
            int[] move = {newRow, queenPos[1]-leftCount};
            upLeftMoves.add(move);
            leftCount++;
        }
        return upLeftMoves;
    }

    public ArrayList<int[]> getUpRightMoves(int[] queenPos) {
        ArrayList<int[]> upRightMoves = new ArrayList<int[]>();
        //Used to ensure that position moves to the side as much as it does down
        int rightCount = 1;
        for (int newRow = queenPos[0] - 1; newRow >= 0; --newRow) {
            if (board[newRow][queenPos[1]+rightCount] != 0)
                break;
            int[] move = {newRow, queenPos[1]+rightCount};
            upRightMoves.add(move);
            rightCount++;
        }
        return upRightMoves;
    }
}
