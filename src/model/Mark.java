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

    public void setPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public char getMark() {
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
