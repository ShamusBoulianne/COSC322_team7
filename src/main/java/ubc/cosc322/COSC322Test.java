//test
package ubc.cosc322;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;
import ygraph.ai.smartfox.games.amazons.HumanPlayer;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;

    private int userQueen = 1;
 
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test("Team07", "11");
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and passwd
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    	gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }

	@Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
    	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document. 
    	Board board = new Board();

    	if (GameMessage.GAME_STATE_BOARD.compareTo(messageType)==0) {
    		//System.out.println("game-state:" + displayBoard((ArrayList)msgDetails.get(AmazonsGameMessage.GAME_STATE)));
			board.setBoard((ArrayList)msgDetails.get(AmazonsGameMessage.GAME_STATE));
    		board.printBoard();
    		//board.printMoves(board.getQueenPositions()[1]);
    		gamegui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
    	}
    	if (GameMessage.GAME_ACTION_START.compareTo(messageType)==0) {
    		printBoard((ArrayList) msgDetails.get(AmazonsGameMessage.GAME_STATE));
    		System.out.println("player-black: " + msgDetails.get(AmazonsGameMessage.PLAYER_BLACK));
    		System.out.println("player-white: " + msgDetails.get(AmazonsGameMessage.PLAYER_WHITE));
    		if(this.userName == msgDetails.get(AmazonsGameMessage.PLAYER_BLACK))
    			board.setPlayerQueenNum(1);
    		else
    			board.setPlayerQueenNum(2);
    		board.setBoard((ArrayList)msgDetails.get(AmazonsGameMessage.GAME_STATE));

			ArrayList<Integer> queenCurr = new ArrayList<>();
			int[] queenToMove = board.getQueenPositions()[1];
			queenCurr.add(queenToMove[0]);
			queenCurr.add(queenToMove[1]);

			ArrayList<Integer> toMoveTo = new ArrayList<>();
			int[] newSpace = board.getPossibleMoves(queenToMove).get(0);
			toMoveTo.add(newSpace[0]);
			toMoveTo.add(newSpace[1]);

			ArrayList<Integer> arrowSpot = new ArrayList<>();
			int[] arrowNew = board.getPossibleMoves(queenToMove).get(1);
			arrowSpot.add(arrowNew[0]);
			arrowSpot.add(arrowNew[1]);

			gamegui.updateGameState(queenCurr, toMoveTo, arrowSpot );
    	}
    	if (GameMessage.GAME_ACTION_MOVE.compareTo(messageType)==0) {
    		System.out.println("queen-pos-curr: " + msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR));
    		System.out.println("queen-pos-next: " + msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT));
    		System.out.println("arrow-pos: " + msgDetails.get(AmazonsGameMessage.ARROW_POS));
    		gamegui.updateGameState(msgDetails);
    	}
    	return true;   	
    }

    public int[][] get2x2Board(ArrayList<Integer> board){
    	int[][] boardOut = new int[10][10];
    	int row = 0;
    	int column = 0;
    	for(int i=12; i<board.size(); i++){
    		boardOut[row][column] = board.get(i);
    		column = ++column%10;
    		if((i+1)%11 == 0){
    			++i;
    			++row;
			}
		}
    	return boardOut;
	}

	public void printMoves(ArrayList<int[]> moves){
		for(int[] i: moves){
			System.out.print(" (" + i[0] + ", " + i[1] + ") ,");
		}
	}

	public void printBoard(int[][] board){
    	String output = "";
    	for(int r = 0; r< board.length; ++r){
    		for(int c = 0; c<board[r].length; ++c)
    			output += board[r][c] + " ";
    		output += "\n";
		}
    	System.out.println(output);
	}

    public void printBoard(ArrayList<Integer> board){
    	String output = "";
    	int counter = 0;
    	for(int square: board){
    		output += square + " ";
    		if(counter++ % 11 == 0)
    			output += "\n";
		}
    	System.out.println(output);
	}

	public int [][] getQueenPositions(int[][] board){
    	int[][] queenPositions = new int[4][2];
    	int queensFound = 0;
    	for(int r=0; r< board.length; ++r)
    		for(int c=0; c<board[r].length; ++c)
    			if(board[r][c] == this.userQueen){
    				queenPositions[queensFound][0] = r;
    				queenPositions[queensFound][1] = c;
    				++queensFound;
				}
    	return queenPositions;
	}

	public ArrayList<int[]> getPossibleMoves(int[][] board, int[] queenCurr){
    	int[][] queenPos = getQueenPositions(board);
    	ArrayList<int[]> moves = new ArrayList<int[]>();
    	moves.addAll(getStraightLeftMoves(queenCurr, board));
    	moves.addAll(getStraightRightMoves(queenCurr, board));
    	moves.addAll(getStraightUpMoves(queenCurr, board));
    	moves.addAll(getStraightDownMoves(queenCurr, board));

    	return moves;
	}

	public ArrayList<int[]> getStraightLeftMoves(int[] queenPos, int[][] board){
    	ArrayList<int[]> leftMoves = new ArrayList<int[]>();
    	for(int newCol=queenPos[1]-1; newCol>=0; --newCol){
    		if(board[queenPos[0]][newCol] != 0)
    			break;
    		int[] move = {queenPos[0], newCol};
    		leftMoves.add(move);
		}
    	return leftMoves;
	}

	public ArrayList<int[]> getStraightRightMoves(int[] queenPos, int[][] board) {
		ArrayList<int[]> rightMoves = new ArrayList<int[]>();
		for (int newCol = queenPos[1] + 1; newCol < 10; ++newCol) {
			if (board[queenPos[0]][newCol] != 0)
				break;
			int[] move = {queenPos[0], newCol};
			rightMoves.add(move);
		}
		return rightMoves;
	}

	public ArrayList<int[]> getStraightUpMoves(int[] queenPos, int[][] board) {
		ArrayList<int[]> upMoves = new ArrayList<int[]>();
		for (int newRow = queenPos[0] - 1; newRow >= 0; --newRow) {
			if (board[newRow][queenPos[1]] != 0)
				break;
			int[] move = {newRow, queenPos[1]};
			upMoves.add(move);
		}
		return upMoves;
	}

	public ArrayList<int[]> getStraightDownMoves(int[] queenPos, int[][] board) {
		ArrayList<int[]> downMoves = new ArrayList<int[]>();
		for (int newRow = queenPos[0] + 1; newRow < 10; ++newRow) {
			if (board[newRow][queenPos[1]] != 0)
				break;
			int[] move = {newRow, queenPos[1]};
			downMoves.add(move);
		}
		return downMoves;
	}

    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return this.gamegui;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class
