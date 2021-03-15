//test
// We had a group member drop the course on march 8th,
package ubc.cosc322;

import java.lang.reflect.Array;
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
 
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	GamePlayer player = new COSC322Test("Team 07", "pass");
    	
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
    		board.printBoard();
    		gamegui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
    	}
    	if (GameMessage.GAME_ACTION_START.compareTo(messageType)==0) {
    		System.out.println("player-black: " + msgDetails.get(AmazonsGameMessage.PLAYER_BLACK));
    		System.out.println("player-white: " + msgDetails.get(AmazonsGameMessage.PLAYER_WHITE));
    		if(this.userName == msgDetails.get(AmazonsGameMessage.PLAYER_BLACK))
    			board.setPlayerQueenNum(1);
    		else
    			board.setPlayerQueenNum(2);
			System.out.println("We go first, making move...");
			if(board.getPlayerQueenNum() == 1)
				makeMove();
    	}
    	if (GameMessage.GAME_ACTION_MOVE.compareTo(messageType)==0) {
    		System.out.println("Opponent Move recieved");
    		Move opponentMove = new Move((ArrayList)msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR),
										 (ArrayList)msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT),
										 (ArrayList)msgDetails.get(AmazonsGameMessage.ARROW_POS));

    		updateGameState(opponentMove);
    		makeMove();
    		System.out.println("Move made");
    		board.printBoard();
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
    	Move move = board.pickMove();
    	updateGameState(move);
    	gameClient.sendMoveMessage(move.getQueenCurr().getArrayList(), move.getQueenMove().getArrayList(), move.getArrow().getArrayList());
	}

	public void updateGameState(Move move){
		gamegui.updateGameState(move.getQueenCurr().getArrayList(), move.getQueenMove().getArrayList(), move.getArrow().getArrayList());
		board.updateGameState(move);
		System.out.println("Boards updated...");
	}

 
}//end of class
