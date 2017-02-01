package model;


/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Position {

    private Integer x, y, z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Position(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    //@pure
    public int getX() {
        return x;
    }

    //@pure
    public int getY() {
        return y;
    }

    //@pure
    public int getZ() {
        return z;
    }
}
