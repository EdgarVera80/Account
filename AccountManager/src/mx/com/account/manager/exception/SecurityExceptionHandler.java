package mx.com.account.manager.exception;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class SecurityExceptionHandler extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 94999380355750132L;
	private int errorCode=0;
	
	public SecurityExceptionHandler(int errorCode,String errorMessage) {
        super(errorMessage);
        this.errorCode=errorCode;
    }

	public SecurityExceptionHandler(Exception e) {
		super(e);
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
