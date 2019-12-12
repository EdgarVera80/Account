package mx.com.account.manager.dao;

import mx.com.account.manager.exception.DaoException;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public interface PropertyDao {
	String getPropertyValue(String property) throws DaoException;
}
