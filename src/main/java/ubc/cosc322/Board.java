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

    public ArrayList<Move> getPossibleMoves(int[] queenCurr){
        ArrayList<Move> moves = new ArrayList<>();
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

    public ArrayList<ArrayList<Integer>> getStraightLeftMoves(int[] queenPos){
        ArrayList<ArrayList<Integer>> leftMoves = new ArrayList<>();
        for(int newCol=queenPos[1]-1; newCol>=0; --newCol){
            if(board[queenPos[0]][newCol] != 0)
                break;
            ArrayList<Integer> move = new ArrayList<>();
            move.add(queenPos[0]);
            move.add(newCol);
            leftMoves.add(move);
        }
        return leftMoves;
    }

    public ArrayList<ArrayList<Integer>> getStraightRightMoves(int[] queenPos) {
        ArrayList<ArrayList<Integer>> rightMoves = new ArrayList<>();
        for (int newCol = queenPos[1] + 1; newCol < 10; ++newCol) {
            if (board[queenPos[0]][newCol] != 0)
                break;
            ArrayList<Integer> move = new ArrayList<>();
            move.add(queenPos[0]);
            move.add(newCol);
            rightMoves.add(move);
        }
        return rightMoves;
    }

    public ArrayList<ArrayList<Integer>> getStraightUpMoves(int[] queenPos) {
        ArrayList<ArrayList<Integer>> upMoves = new ArrayList<>();
        for (int newRow = queenPos[0] - 1; newRow >= 0; --newRow) {
            if (board[newRow][queenPos[1]] != 0)
                break;
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newRow);
            move.add(queenPos[1]);
            upMoves.add(move);
        }
        return upMoves;
    }

    public ArrayList<ArrayList<Integer>> getStraightDownMoves(int[] queenPos) {
        ArrayList<ArrayList<Integer>> downMoves = new ArrayList<>();
        for (int newRow = queenPos[0] + 1; newRow < 10; ++newRow) {
            if (board[newRow][queenPos[1]] != 0)
                break;
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newRow);
            move.add(queenPos[1]);
            downMoves.add(move);
        }
        return downMoves;
    }

    public ArrayList<ArrayList<Integer>> getDownLeftMoves(int[] queenPos) {
        ArrayList<ArrayList<Integer>> downLeftMoves = new ArrayList<>();
        //Used to ensure that position moves to the side as much as it does down
        int leftCount = 1;
        for (int newRow = queenPos[0] + 1; newRow < 10; ++newRow) {
            if (board[newRow][queenPos[1]-leftCount] != 0)
                break;
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newRow);
            move.add(queenPos[1]-leftCount);
            downLeftMoves.add(move);
            leftCount++;
        }
        return downLeftMoves;
    }

    public ArrayList<ArrayList<Integer>> getDownRightMoves(int[] queenPos) {
        ArrayList<ArrayList<Integer>> downRightMoves = new ArrayList<>();
        //Used to ensure that position moves to the side as much as it does down
        int rightCount = 1;
        for (int newRow = queenPos[0] + 1; newRow < 10; ++newRow) {
            if (board[newRow][queenPos[1]+rightCount] != 0)
                break;
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newRow);
            move.add(queenPos[1]+rightCount);
            downRightMoves.add(move);
            rightCount++;
        }
        return downRightMoves;
    }

    public ArrayList<ArrayList<Integer>> getUpLeftMoves(int[] queenPos) {
        ArrayList<ArrayList<Integer>> upLeftMoves = new ArrayList<>();
        //Used to ensure that position moves to the side as much as it does down
        int leftCount = 1;
        for (int newRow = queenPos[0] - 1; newRow >= 0; --newRow) {
            if (board[newRow][queenPos[1]-leftCount] != 0)
                break;
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newRow);
            move.add(queenPos[1]-leftCount);
            upLeftMoves.add(move);
            leftCount++;
        }
        return upLeftMoves;
    }

    public ArrayList<ArrayList<Integer>> getUpRightMoves(int[] queenPos) {
        ArrayList<ArrayList<Integer>> upRightMoves = new ArrayList<>();
        //Used to ensure that position moves to the side as much as it does down
        int rightCount = 1;
        for (int newRow = queenPos[0] - 1; newRow >= 0; --newRow) {
            if (board[newRow][queenPos[1]+rightCount] != 0)
                break;
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newRow);
            move.add(queenPos[1]+rightCount);
            upRightMoves.add(move);
            rightCount++;
        }
        return upRightMoves;
    }

    public ArrayList<Integer>[] pickMove(){
        int[] queenCurr = getQueenPositions()[0];
        int[] queenMove = getPossibleMoves(queenCurr).get(0);
        int[] arrow = getPossibleMoves(queenMove).get(0);

        ArrayList<Integer>[] move = (ArrayList<Integer>[])new ArrayList[3];
        move[0] = new ArrayList<Integer>();
        move[0].add(queenCurr[0]);
        move[0].add(queenCurr[1]);

        move[1] = new ArrayList<Integer>();
        move[1].add(queenMove[0]);
        move[1].add(queenMove[1]);

        move[2] = new ArrayList<Integer>();
        move[2].add(arrow[0]);
        move[2].add(arrow[1]);

        return move;
    }

    public void updateGameState(ArrayList<Integer> queenCurr, ArrayList<Integer> queenMove, ArrayList<Integer> arrow ){
        board[queenCurr.get(0)][queenCurr.get(1)] = 0;
        board[queenMove.get(0)][queenMove.get(1)]= this.playerQueenNum;
        board[arrow.get(0)][arrow.get(1)] = 3;}
}
