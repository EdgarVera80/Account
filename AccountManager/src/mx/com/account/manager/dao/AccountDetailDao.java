package mx.com.account.manager.dao;

import java.util.List;

import mx.com.account.manager.domain.AccountDetail;
import mx.com.account.manager.exception.DaoException;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public interface AccountDetailDao {
	List<AccountDetail> getAllByAccountId(Integer accountId) throws DaoException;
	void saveAccountDetail(AccountDetail accountDetail) throws DaoException;
	void updateAccountDetail(AccountDetail accountDetail) throws DaoException;
	void removeAccountDetail(Integer id) throws DaoException;
	AccountDetail getAccountDetailById(Integer id) throws DaoException;
	boolean existPropertyOtherId(String property, Integer id) throws DaoException;
	List<AccountDetail> getByAccountIdExceptId(Integer accountId,Integer id) throws DaoException;
}
