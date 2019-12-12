package mx.com.account.manager.service;

import mx.com.account.manager.domain.Account;
import mx.com.account.manager.exception.ServiceException;

public interface AccountService {
	void saveAccount(Account account) throws ServiceException;
	void updateAccount(Account account) throws ServiceException;
	void removeAccount(Integer accountId) throws ServiceException;
	Object[][] getAllAccounts() throws ServiceException;
	Account getAccountById(Integer id) throws ServiceException;
	Account getAccountByAccount(String accountName) throws ServiceException;
	Object[][] getAccountByIdArray(Integer id) throws ServiceException;
	boolean existAccountName(String accountName) throws ServiceException;
	boolean existAccountNameOtherId(String account, Integer id) throws ServiceException;
	boolean existWithOtherId(String account, Integer id) throws ServiceException;
}
