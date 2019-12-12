package mx.com.account.manager.service;

import mx.com.account.manager.domain.User;
import mx.com.account.manager.exception.ServiceException;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public interface UserService {
	boolean validUser(User user) throws ServiceException;
	void changePassword(User user) throws ServiceException;
}
