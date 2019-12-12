package mx.com.account.manager.domain;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class Account {
	public static final String FIELD_ID="id";
	public static final String FIELD_ACCOUNT="account";
	public static final String FIELD_CREATION="creation";
	public static final String FIELD_INFORMATION="information";
	public static final String QUERY_SAVE="insert into account (account,creation, information) values (?,?,?)";
	public static final String QUERY_UPDATE="update account set account=?, information=? where id=?";
	public static final String QUERY_GET_ALL="select * from account Order by account asc";
	public static final String QUERY_GET_BY_ID="select * from account where id=?";
	public static final String QUERY_GET_BY_ACCOUNT="select * from account where account=?";
	public static final String QUERY_DELETE="delete from account where id=?";
	public static final String QUERY_BY_ACCOUNT="select * from account where upper(account) like ?";
	public static final String QUERY_BY_ACCOUNT_AND_ID="select * from account where upper(account) like ? and id != ?";
	public static final String QUERY_EXCEPT_ID="select account from account where id != ?";
	
	private Integer id;
	private String account;
	private String creation;
	private String information;
	
	public Account() {
		
	}
	
	public Account(Integer id,String account,String creation, String information) {
		this.id=id;
		this.account=account;
		this.creation=creation;
		this.information=information;
	}
	
	public Account(String account, String creation,String information) {
		this.account=account;
		this.creation=creation;
		this.information=information;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}

	public String getCreation() {
		return creation;
	}

	public void setCreation(String creation) {
		this.creation = creation;
	}
	
	public String toString() {
		StringBuilder sb=new StringBuilder();
		
		sb.append("Account:[\n");
		sb.append("\tId:          ").append(id).append("\n");
		sb.append("\tAccount:     ").append(account).append("\n");
		sb.append("\tCreation:    ").append(creation).append("\n");
		sb.append("\tInformation: ").append(information).append("\n");
		sb.append("]");
		return sb.toString();
	}
	
}
