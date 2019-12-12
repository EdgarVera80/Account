package mx.com.account.manager.service;

import mx.com.account.manager.domain.AccountDetail;
import mx.com.account.manager.exception.ServiceException;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public interface AccountDetailService {
	Object[][] getAllByAccountId(Integer accountId) throws ServiceException;
	void saveAccountDetail(AccountDetail accountDetail) throws ServiceException;
	void updateAccountDetail(AccountDetail accountDetail) throws ServiceException;
	void removeAccountDetail(Integer id) throws ServiceException;
	AccountDetail getAccountDetailById(Integer id) throws ServiceException;
	boolean existPropertyByAccountIdExceptId(String property,Integer accountId, Integer id) throws ServiceException;
	boolean existProperty(String property, Integer accountId) throws ServiceException;
}
