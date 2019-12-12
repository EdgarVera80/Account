package mx.com.account.manager.domain;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class Property {
	
	public static final String ID="id";
	public static final String PROPERTY="property";
	public static final String VALUE="value";
	public static final String QUERY_GET_BY_PROPERTY="select value from property where property =?";
	
	private Integer id;
	private String property;
	private String value;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Property:[\n");
		sb.append("\tId:       ").append(id).append("\n");
		sb.append("\tProperty: ").append(property).append("\n");
		sb.append("\tValue:    ").append(value).append("\n");
		sb.append("]");
		return sb.toString();
	}
	
}
