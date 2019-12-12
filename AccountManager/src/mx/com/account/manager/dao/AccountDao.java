package mx.com.account.manager.dao;

import java.util.List;

import mx.com.account.manager.domain.Account;
import mx.com.account.manager.exception.DaoException;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public interface AccountDao {
	void saveAccount(Account account) throws DaoException;
	void updateAccount(Account account) throws DaoException;
	void removeAccount(Integer accountId) throws DaoException;
	List<Account> getAllAccounts() throws DaoException;
	Account getAccountById(Integer id) throws DaoException;
	Account getAccountByAccount(String accountName) throws DaoException;
	boolean existAccountName(String accountName) throws DaoException;
	boolean existAccountNameOtherId(String account, Integer id) throws DaoException;
	List<Account> getAccountsExceptId(Integer id) throws DaoException;
}
