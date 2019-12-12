package mx.com.account.manager.util;

import mx.com.account.manager.domain.Account;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class InputOutputParameters {
	private Integer accountId;			//input	
	private Integer accountDetailId;	//input	
	private String tittle; 				//input
	private String mode;				//input
	private String status;				//output	
	private String message;				//output
	private Account account;			//output
	
	public InputOutputParameters() {
		/**
		 * Default Constructor
		 */
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getAccountDetailId() {
		return accountDetailId;
	}
	public void setAccountDetailId(Integer accountDetailId) {
		this.accountDetailId = accountDetailId;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
}
