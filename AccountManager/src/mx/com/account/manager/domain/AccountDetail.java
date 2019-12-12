package mx.com.account.manager.domain;


/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class AccountDetail {
	
	public static final String FIELD_ID="id";
	public static final String FIELD_ACCOUNT_ID="account_id";
	public static final String FIELD_PROPERTY="property";
	public static final String FIELD_VALUE="value";
	public static final String QUERY_GET_BY_ACCOUNT_ID="select * from account_detail where account_id =?";
	public static final String QUERY_SAVE="insert into account_detail (account_id, property, value) values (?,?,?)";
	public static final String QUERY_UPDATE="update account_detail set property=?, value=? where id=?";
	public static final String QUERY_DELETE="delete from account_detail where id=?";
	public static final String QUERY_BY_PROPERTY_AND_ACCOUNT_ID="select * from account_detail where upper(property) like ? and account_id = ?";
	public static final String QUERY_GET_BY_ID="select * from account_detail where id=?";
	public static final String QUERY_GET_BY_PROPERTY_AND_ID="select * from account_detail where upper(property) like ? and id != ?";
	public static final String QUERY_GET_BY_ACCOUNTID="select property from account_detail where account_id =?";
	public static final String QUERY_GET_BY_ACCOUNTID_EXCEPT_ID="select property from account_detail where account_id =? and id!=?";
	
	private Integer id;
	private Integer accountId;
	private String property;
	private String value;
	
	public AccountDetail() {
		
	}
	
	public AccountDetail(Integer accountId, String property,String value) {
		this.accountId=accountId;
		this.property=property;
		this.value=value;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("AccountDetail:[\n");
		sb.append("\tId:       ").append(id).append("\n");
		sb.append("\tAccounId: ").append(accountId).append("\n");
		sb.append("\tProperty: ").append(property).append("\n");
		sb.append("\tValue:    ").append(value).append("\n");
		sb.append("]");
		
		return sb.toString();		
	}
}
