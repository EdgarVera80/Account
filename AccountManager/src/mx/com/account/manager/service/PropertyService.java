package mx.com.account.manager.service;

import mx.com.account.manager.exception.ServiceException;

public interface PropertyService {
	String getPropertyValue(String property) throws ServiceException;
}
