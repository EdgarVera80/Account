package mx.com.account.manager.service;

import mx.com.account.manager.dao.UserDao;
import mx.com.account.manager.dao.UserDaoImpl;
import mx.com.account.manager.domain.User;
import mx.com.account.manager.exception.DaoException;
import mx.com.account.manager.exception.ServiceException;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class UserServiceImpl implements UserService{

	private UserDao userDao;
	
	public UserServiceImpl() {
		userDao=new UserDaoImpl();
	}
	
	@Override
	public boolean validUser(User user) throws ServiceException{
		try {
			return userDao.validUser(user);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public void changePassword(User user) throws ServiceException {
		try {
			userDao.changePassword(user);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}
}
