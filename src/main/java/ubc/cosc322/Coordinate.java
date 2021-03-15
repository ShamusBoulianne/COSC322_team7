package ubc.cosc322;

import java.util.ArrayList;

//This class will be used to track the coordinate of an spot on the board (y,x)

// format is (y,x) to make it more similar to the int[][] used for the board

public class Coordinate {
    ArrayList<Integer> coordinate;

    public Coordinate(int y, int x){
        this.coordinate = new ArrayList();
        this.coordinate.add(0);
        this.coordinate.add(0);
        setX(y);
        setY(x);
    }

    public void setX(int x){
        if(is0to9(x))
           this.coordinate.set(1, x);
    }

    public void setY(int y){
        if(is0to9(y))
            this.coordinate.set(0, y);
    }

    public boolean is0to9(int value){
        if(value<0 || value>10)
            return false;
        return true;
    }

    public int getX(){
        return coordinate.get(0);
    }

    public int getY(){
        return coordinate.get(1);
    }

    public String toString(){
        return "(" + this.getY() + ", " + this.getX() + ")";
    }

    public ArrayList<Integer> getArrayList(){
        return coordinate;
    }
}
