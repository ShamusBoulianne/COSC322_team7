package ubc.cosc322;

public class GameTree {
    private GTNode root;
    private int maxDepthToSearch;
    private int playerQueenNum;

    public GameTree(GTNode root, int maxDepthToSearch, int playerQueenNum){
        this.root = root;
        this.maxDepthToSearch = maxDepthToSearch;
        this.playerQueenNum = playerQueenNum;
    }
}
