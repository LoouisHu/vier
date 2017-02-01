package model;

import java.util.Objects;

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
    
    @Override
    public int hashCode() {
    	return Objects.hash(x, y, z);
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o == this) {
    		return true;
    	}
    	if (o == null || !(o instanceof Position)) {
    		return false;
    	}
    	Position p = Position.class.cast(o);
    	return x.equals(p.x) && y.equals(p.y) && z.equals(p.z);
    }
}
