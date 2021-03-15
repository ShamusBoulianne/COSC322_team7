package ubc.cosc322;

import java.util.ArrayList;

public class Coordinate extends ArrayList {
    ArrayList<Integer> coordinate;

    public Coordinate(int x, int y){
        this.coordinate = new ArrayList();
        setX(x);
        setY(y);
    }

    public void setX(int x){
        if(is0to9(x))
           this.coordinate.set(0, x);
    }

    public void setY(int y){
        if(is0to9(y))
            this.coordinate.set(1, y);
    }

    public boolean is0to9(int value){
        if(value<0 || value>10)
            return false;
        return true;
    }
}
