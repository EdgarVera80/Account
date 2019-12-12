package mx.com.account.manager.exception;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class EncryptException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2431212719767568829L;
	private int errorCode;
	
	public EncryptException(int errorCode,String errorMessage) {
		super(errorMessage);
		this.errorCode =errorCode;
	}
	
	public EncryptException(Exception e) {
		super(e);
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}
