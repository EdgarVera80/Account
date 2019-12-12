package mx.com.account.manager.service;

import mx.com.account.manager.dao.PropertyDao;
import mx.com.account.manager.dao.PropertyDaoImpl;
import mx.com.account.manager.exception.DaoException;
import mx.com.account.manager.exception.ServiceException;

public class PropertyServiceImpl implements PropertyService{

	private PropertyDao propertyDao;
	
	public PropertyServiceImpl() {
		this.propertyDao = new PropertyDaoImpl();
	}
	
	@Override
	public String getPropertyValue(String property) throws ServiceException {
		try {
			return propertyDao.getPropertyValue(property);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(),e.getMessage());
		}
	}
}
