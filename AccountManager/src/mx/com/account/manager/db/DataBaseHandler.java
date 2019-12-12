package mx.com.account.manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.account.manager.exception.DaoException;
import mx.com.account.manager.util.Numbers;
import mx.com.account.manager.util.Utils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class DataBaseHandler {
	private static final Logger LOGGER = Logger.getLogger(DataBaseHandler.class);
	
	private static final String ERROR_TEXT="Error";
	
	private Connection connect;
	private PreparedStatement statement;
	private ResultSet result;
	
	public DataBaseHandler() {
		/**
		 * Default Constructor
		 */
	}
	
	protected void open() throws DaoException{
		try {
			Class.forName(Utils.JDBC_CLASS);
			connect = DriverManager.getConnection("jdbc:sqlite:"+Utils.DB_PATH);
			connect.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new DaoException(Numbers.ONE.getValue(),e.getMessage());
		} catch (SQLException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new DaoException(Numbers.TWO.getValue(),e.getMessage());
		}
	}
	
	protected void close() throws DaoException{
		if(result!=null) {
			try {
				result.close();
			} catch (SQLException e) {
				LOGGER.error(ERROR_TEXT,e);
				throw new DaoException(Numbers.TWO.getValue(),"Error al cerrar el ResultSet");
			}
		}
		if(statement!=null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.error(ERROR_TEXT,e);
				throw new DaoException(Numbers.TWO.getValue(),"Error al cerrar la conexión");
			}
		}
		if(connect!=null) {
			try {
				connect.close();
			} catch (SQLException e) {
				LOGGER.error(ERROR_TEXT,e);
				throw new DaoException(Numbers.TWO.getValue(),"Error al cerrar la conexión");
			}
		}
	}
	
	protected void rollback() throws DaoException{
		if(connect!=null) {
			try {
				connect.rollback();
			} catch (SQLException e) {
				LOGGER.error(ERROR_TEXT,e);
				throw new DaoException(Numbers.TWO.getValue(),"Error al realizar el rollback");
			}
		}
	}
	
	protected void commit() throws DaoException{
		try {
			connect.commit();
		} catch (SQLException e) {
			throw new DaoException(Numbers.TWO.getValue(),"Error al realizar el commit");
		}
	}
	
	protected void prepareStatement(String query) throws SQLException {
		statement=connect.prepareStatement(query);
	}
	
	protected ResultSet executeQuery() throws SQLException {
		result = statement.executeQuery();
		return result;
	}
	
	protected int getInt(String value) throws SQLException{
		return result.getInt(value);
	}
	
	protected String getString(String value) throws SQLException{
		return result.getString(value);
	}
	
	protected void setInt(int parameterIndex, int value) throws SQLException{
		statement.setInt(parameterIndex, value);
	}
	
	protected void setString(int parameterIndex, String value) throws SQLException{
		statement.setString(parameterIndex, value);
	}
	
	protected void execute() throws SQLException{
		statement.execute();
	}
}
