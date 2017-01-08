package model;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Mark {
    private char mark;
    private int x, y, z;

    public Mark(char mark) {
        this.mark = mark;
    }
   
    public void setPosition(int setX, int setY, int setZ) {
        this.x = setX;
        this.y = setY;
        this.z = setZ;
    }

    public char getMarkChar() {
        return mark;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
