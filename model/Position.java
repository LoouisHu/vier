package com.company.model;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Position {

    int x, y, z;

    public Position(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
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

    public void setXYZ(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
