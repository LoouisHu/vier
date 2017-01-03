package model;

public class EmptySpace {

	int x, y, z;

    public EmptySpace(int x, int y, int z) {
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
