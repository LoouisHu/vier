package model;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class Mark {
    private char mark;
    private Position p;

    public Mark(char newMark) {
        this.mark = newMark;
    }
    
    public Mark(char mark, Position p) {
    	this.mark = mark;
    	this.p = p;
    }

    public char getMarkChar() {
        return mark;
    }

    public Position getPosition() {
    	return p;
    }
    
}
