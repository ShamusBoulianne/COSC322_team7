//test
// We had a group member drop the course on march 8th,
package ubc.cosc322;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.*;
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

    private Board board;
    private GameTree gametree;
    private int moveNum = 1;
 
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	GamePlayer player = new COSC322Test("Ryan", "pass");
    	
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

    	this.gamegui = new BaseGameGUI(this);
	board = new Board();
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

    	if (GameMessage.GAME_STATE_BOARD.compareTo(messageType)==0) {
			board.setBoard((ArrayList)msgDetails.get(AmazonsGameMessage.GAME_STATE));
    		board.setPlayerQueenNum(1);// Set player num as our number
    		gamegui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
    		//makeMove();
    	}
    	if (GameMessage.GAME_ACTION_START.compareTo(messageType)==0) {
    		System.out.println("player-black: " + msgDetails.get(AmazonsGameMessage.PLAYER_BLACK));
    		System.out.println(this.userName);
    		System.out.println("player-white: " + msgDetails.get(AmazonsGameMessage.PLAYER_WHITE));
    		if(this.userName.equalsIgnoreCase((String)msgDetails.get(AmazonsGameMessage.PLAYER_BLACK))) //Black plays first
    			board.setPlayerQueenNum(2);
    		else
    			board.setPlayerQueenNum(1);
			if(board.getPlayerQueenNum() == 2) {
				System.out.println("We go first, making move...");
				makeMove();
			}
    	}
    	if (GameMessage.GAME_ACTION_MOVE.compareTo(messageType)==0) {
			System.out.println("\n----------------------------------\n");
    		System.out.println("Opponent Move recieved. Move Number " + moveNum++);
    		Move opponentMove = new Move((ArrayList)msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR),
										 (ArrayList)msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT),
										 (ArrayList)msgDetails.get(AmazonsGameMessage.ARROW_POS));
    		System.out.println("Move received from Server: " + opponentMove.toString());
			board.setPlayerQueenNum(board.getPlayerQueenNum()%2+1);
    		updateInternalGameState(formatMoveFromServer(opponentMove));
			System.out.println("\n----------------------------------\n");
			board.setPlayerQueenNum(board.getPlayerQueenNum()%2+1);
			try {
				TimeUnit.SECONDS.sleep((long) 3.0);
			}catch( Exception e)
			{}
    		makeMove();
    		//board.printBoard();
    	}
    	return true;   	
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

	public void makeMove(){
    	gametree = new GameTree(new GTNode(board), board.getPlayerQueenNum());
    	gametree.populateTree();
    	//gametree.getRoot().getChildren().printList();
    	Move move = gametree.getBestMove();
    	System.out.println("\n----------------------------------\n");
    	updateInternalGameState(move);

    	move = formatMoveToServer(move);
		gameClient.sendMoveMessage(move.getQueenCurr().getArrayList(),
				move.getQueenMove().getArrayList(),
				move.getArrow().getArrayList()       );
		System.out.println("The move sent to the server is:" + move.toString());
		System.out.println("Move number" + moveNum++);
		System.out.println("\n----------------------------------\n");
	}

	//Our internal board uses indexing 0-9, the server uses 1-10
	public Move formatMoveToServer(Move original){
    	Coordinate updatedCurr = new Coordinate(original.getQueenCurr().getX()+1, 10-original.getQueenCurr().getY());
    	Coordinate updatedQueen = new Coordinate(original.getQueenMove().getX()+1, 10-original.getQueenMove().getY());
    	Coordinate updatedArrow = new Coordinate(original.getArrow().getX()+1, 10-original.getArrow().getY());
    	return new Move(updatedCurr, updatedQueen, updatedArrow);
	}

	//Our internal board uses indexing 0-9, the server uses 1-10
	public Move formatMoveFromServer(Move original){
		Coordinate updatedCurr = new Coordinate(10-original.getQueenCurr().getX(), original.getQueenCurr().getY()-1);
		Coordinate updatedQueen = new Coordinate(10-original.getQueenMove().getX(), original.getQueenMove().getY()-1);
		Coordinate updatedArrow = new Coordinate(10-original.getArrow().getX(), original.getArrow().getY()-1);
		return new Move(updatedCurr, updatedQueen, updatedArrow);
	}

	public void updateInternalGameState(Move move){

		board.updateGameState(move);
		System.out.println("The internal move was" + move.toString());
		System.out.println("Internal Board Updated");

		move = formatMoveToServer(move);
		gamegui.updateGameState(move.getQueenCurr().getArrayList(),
				move.getQueenMove().getArrayList(),
				move.getArrow().getArrayList()       );

		board.printBoard();
	}

 
}//end of class
