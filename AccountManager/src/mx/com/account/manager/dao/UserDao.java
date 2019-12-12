package mx.com.account.manager.dao;

import mx.com.account.manager.domain.User;
import mx.com.account.manager.exception.DaoException;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public interface UserDao {
	boolean validUser(User user) throws DaoException;
	void changePassword(User user) throws DaoException;
}
