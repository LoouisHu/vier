package exceptions;

/**
 * Created by Glorious Louis on 14/11/2016.
 */
public class IllegalStringException extends Exception {

	private static final long serialVersionUID = 8428281089780440175L;
	public final String string;
	
    public IllegalStringException(String string) {
    	super();
    	this.string = string;
    }
    
    @Override
    public String getMessage() {
		return "String must contain only [A-Z][a-z][0-9][.-_]: " + string;
    }
}
