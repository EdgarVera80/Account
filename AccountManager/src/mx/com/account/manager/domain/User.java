package mx.com.account.manager.domain;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class User {
	
	public static final String FIELD_ID ="id";
	public static final String FIELD_USER ="user";
	public static final String FIELD_PASSWORD ="password";
	
	public static final String QUERY_GET_BY_USER_AND_PASSWORD = "select * from user where user = ? and password=?";
	public static final String QUERY_UPDATE_PASSWORD = "update user set password=? where user=?";
	
	private Integer id;
	private String user;
	private String password;
	
	public User() {
		
	}
	
	public User(String user, String password) {
		this.user=user;
		this.password=password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("User[\n");
		sb.append("\tUser:      ").append(user).append("\n");
		sb.append("\tPassword:  ").append(password).append("\n");
		sb.append("]");
		
		return sb.toString();
	}
	
}
