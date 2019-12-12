package mx.com.account.manager.util;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public enum Numbers {
	ZERO(0),
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5);
	
	private final int value;
	
	Numbers(int value) {
		this.value=value;
	}
	
	public int getValue() {
		return value;
	}
	
}
