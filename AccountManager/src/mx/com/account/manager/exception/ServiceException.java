package mx.com.account.manager.exception;


/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class ServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7947078299582305659L;
	
	private int errorCode=0;
	
	public ServiceException(int errorCode,String errorMessage) {
		super(errorMessage);
		this.errorCode=errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
