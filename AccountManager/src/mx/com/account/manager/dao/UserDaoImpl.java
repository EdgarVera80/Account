package mx.com.account.manager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.com.account.manager.db.DataBaseHandler;
import mx.com.account.manager.domain.User;
import mx.com.account.manager.exception.DaoException;
import mx.com.account.manager.util.Numbers;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class UserDaoImpl extends DataBaseHandler implements UserDao{
	private ResultSet result;
	
	public UserDaoImpl() {
		/**
		 * Default Constructor
		 */
	}
	
	@Override
	public boolean validUser(User user) throws DaoException {
		boolean existe=false;
		try {
			open();
			prepareStatement(User.QUERY_GET_BY_USER_AND_PASSWORD);
			setString(Numbers.ONE.getValue(), user.getUser());
			setString(Numbers.TWO.getValue(), user.getPassword());
			result = executeQuery();
            while (result.next()) {
            	existe=true;
            	break;
            }	
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return existe;
	}

	@Override
	public void changePassword(User user) throws DaoException {
		try {
			open();
        	prepareStatement(User.QUERY_UPDATE_PASSWORD);
            setString(Numbers.ONE.getValue(), user.getPassword());
            setString(Numbers.TWO.getValue(), user.getUser());
			execute();
			commit();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
	}
}
