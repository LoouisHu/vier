package exceptions;

public class IllegalIntegerException extends Exception {

	private static final long serialVersionUID = 8428474670973757422L;
	public final int number;
	
	public IllegalIntegerException(int number) {
		this.number = number;
	}
	
	@Override
	public String getMessage() {
		return "This is an illegal integer: " + number;
	}
	
}
