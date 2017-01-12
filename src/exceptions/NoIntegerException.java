package exceptions;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class NoIntegerException extends Exception {

	private static final long serialVersionUID = 8428281089780440175L;
	public final String string;
	
    public NoIntegerException(String string) {
    	super();
    	this.string = string;
    }
    
    @Override
    public String getMessage() {
		return "This should suppose to be an int: " + string;
    }
}
