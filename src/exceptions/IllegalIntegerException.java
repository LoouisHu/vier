package exceptions;

public class IllegalIntegerException extends Exception {

	public final int number;
	
	public IllegalIntegerException(int number) {
		this.number = number;
		System.out.println("Illegal integer: " + number);
	}
	
//	@Override
//	public String getMessage() {
//		return "This is an illegal integer: " + number;
//	}
	
}
