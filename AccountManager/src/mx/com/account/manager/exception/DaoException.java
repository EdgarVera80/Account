package mx.com.account.manager.exception;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class DaoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 802395506673470197L;
	private int errorCode;
	
	public DaoException(int errorCode,String errorMessage) {
        super(errorMessage);
        this.errorCode=errorCode;
    }

	public DaoException(Exception e) {
        super(e);
    }
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
