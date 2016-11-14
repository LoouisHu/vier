package com.company.model;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Mark {
    private Type type;
    private int x, y, z;

    public Mark(Type type) {
        this.type = type;
    }

    public void setPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String typeToString(Type type){
        if(type == Type.O){
            return "O";
        }
        if (type == Type.X) {
            return "X";
        }
        return null;
    }

    public Type getType() {
        return type;
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
