package ubc.cosc322;

import java.util.TreeSet;

public class Children {
    final int maxSize = 100;

    double bestHeuristic;
    double worstHeuristic;
    GTNode bestChild;
    TreeSet<GTNode> childList;
    boolean isWhite;

    public Children(boolean isWhite){
        this.isWhite = isWhite;
        this.bestHeuristic = isWhite? Double.NEGATIVE_INFINITY: Double.POSITIVE_INFINITY;
        this.worstHeuristic = isWhite? Double.POSITIVE_INFINITY: Double.NEGATIVE_INFINITY;
        this.childList = new TreeSet();
    }

    public void addFirstPass(GTNode node){
        if (childList.size() < maxSize) {
            childList.add(node);
            if(isWhite)
                worstHeuristic = childList.first().getNodeHeuristic();
            else
                bestHeuristic = childList.first().getNodeHeuristic();
        }
        else{
            if(isWhite) {
                if (node.getNodeHeuristic() < bestHeuristic) {
                    childList.pollLast();
                    childList.add(node);
                    bestHeuristic = childList.last().getNodeHeuristic();
                }
            } else {
                    if (node.getNodeHeuristic() > worstHeuristic) {
                        childList.pollFirst();
                        childList.add(node);
                        worstHeuristic = childList.first().getNodeHeuristic();
                    }
            }
        }
    }

    public TreeSet<GTNode> getFirstPass(){
        return this.childList;
    }

    public GTNode getBestChild(){
        if(isWhite)
            getLowestChild();
        else
            getHighestChild();
        return this.bestChild;
    }

    private void getLowestChild(){
        double worstFound = Double.POSITIVE_INFINITY;
        for(GTNode node: this.childList){
            if(node.getNodeHeuristic() < worstFound){
                bestChild = node;
                worstFound = node.getNodeHeuristic();
            }
        }
    }

    private void getHighestChild(){
        double bestFound = Double.NEGATIVE_INFINITY;
        for(GTNode node: this.childList){
            if(node.getNodeHeuristic() > bestFound){
                bestChild = node;
                bestFound = node.getNodeHeuristic();
            }
        }
    }
}
