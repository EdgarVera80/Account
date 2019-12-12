package mx.com.account.manager.util;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public final class Utils {
	
	public static final int MESSAGE_ERROR=0;
	public static final int MESSAGE_INFO=1;
	public static final int MESSAGE_WARNING=2;
	public static final String MODE_NEW ="NEW";
	public static final String MODE_EDIT ="EDIT";
	public static final String MODE_CONSULT ="CONSULT";
	public static final String JDBC_CLASS="org.sqlite.JDBC";
	public static final String DB_PATH="accountsdb.db";
	public static final String SUCCCESS="SUCCESSS";
	public static final String ERROR="ERROR";
	public static final String KEY = "98565*EDA#$%RRT#"; //llave
	public static final String IV = "0123456789ABCDEF"; // vector de inicialización
	
	public static final String LOAD_ACCOUNTS_PROPERTY = "LOAD_ACCOUNTS";
			
	private Utils() {
		
	}
	
	public static CompoundBorder getBorder(){
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		return BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}
}
