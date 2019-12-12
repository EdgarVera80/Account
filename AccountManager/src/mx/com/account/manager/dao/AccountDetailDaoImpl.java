package mx.com.account.manager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.com.account.manager.db.DataBaseHandler;
import mx.com.account.manager.domain.AccountDetail;
import mx.com.account.manager.exception.DaoException;
import mx.com.account.manager.util.Numbers;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class AccountDetailDaoImpl extends DataBaseHandler implements AccountDetailDao{
	private ResultSet result;
	
	public AccountDetailDaoImpl() {
		/**
		 * Default Constructor
		 */
	}
	
	@Override
	public List<AccountDetail> getAllByAccountId(Integer accountId) throws DaoException {
		List<AccountDetail> accountDetails=null;
		
		try {
			open();
			prepareStatement(AccountDetail.QUERY_GET_BY_ACCOUNT_ID);
			setInt(Numbers.ONE.getValue(), accountId);
			result = executeQuery();
			boolean hasRecords=false;
            while (result.next()) {
            	if(!hasRecords) {
            		accountDetails=new ArrayList<>();
            		hasRecords=true;
            	}
            	
            	AccountDetail accountDetail=new AccountDetail();
            	accountDetail.setId(getInt(AccountDetail.FIELD_ID));
            	accountDetail.setAccountId(getInt(AccountDetail.FIELD_ACCOUNT_ID));
            	accountDetail.setProperty(getString(AccountDetail.FIELD_PROPERTY));
            	accountDetail.setValue(getString(AccountDetail.FIELD_VALUE));
            	accountDetails.add(accountDetail);
            }	
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return accountDetails;
	}

	@Override
	public void saveAccountDetail(AccountDetail accountDetail) throws DaoException {
		try {
			open();
        	prepareStatement(AccountDetail.QUERY_SAVE);
            setInt(Numbers.ONE.getValue(), accountDetail.getAccountId());
            setString(Numbers.TWO.getValue(), accountDetail.getProperty());
            setString(Numbers.THREE.getValue(), accountDetail.getValue());
			execute();
			commit();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
	}

	@Override
	public void updateAccountDetail(AccountDetail accountDetail) throws DaoException {
		try {
			open();
        	prepareStatement(AccountDetail.QUERY_UPDATE);
            setString(Numbers.ONE.getValue(), accountDetail.getProperty());
            setString(Numbers.TWO.getValue(), accountDetail.getValue());
            setInt(Numbers.THREE.getValue(), accountDetail.getId());
			execute();
			commit();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
	}

	@Override
	public AccountDetail getAccountDetailById(Integer id) throws DaoException {
		AccountDetail accountDetail=null;
		
		try {
			open();
			prepareStatement(AccountDetail.QUERY_GET_BY_ID);
			setInt(Numbers.ONE.getValue(), id);
			result = executeQuery();
            while (result.next()) {
            	accountDetail=new AccountDetail();
            	accountDetail.setId(getInt(AccountDetail.FIELD_ID));
            	accountDetail.setAccountId(getInt(AccountDetail.FIELD_ACCOUNT_ID));
            	accountDetail.setProperty(getString(AccountDetail.FIELD_PROPERTY));
            	accountDetail.setValue(getString(AccountDetail.FIELD_VALUE));
            }	
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return accountDetail;
	}

	@Override
	public boolean existPropertyOtherId(String property, Integer id) throws DaoException {
		boolean existe=false;
		
		try {
			open();
			prepareStatement(AccountDetail.QUERY_GET_BY_PROPERTY_AND_ID);
			setString(Numbers.ONE.getValue(), property.toUpperCase());
			setInt(Numbers.TWO.getValue(), id);
			result = executeQuery();
            while (result.next()) {
            	existe=true;
            	break;
            }	
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return existe;
	}

	@Override
	public void removeAccountDetail(Integer id) throws DaoException {
		try {
			open();
			prepareStatement(AccountDetail.QUERY_DELETE);
            setInt(Numbers.ONE.getValue(), id);
			execute();
			commit();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
	}

	@Override
	public List<AccountDetail> getByAccountIdExceptId(Integer accountId,Integer id) throws DaoException {
		List<AccountDetail> accountDetails=null;
		
		try {
			open();
			prepareStatement(AccountDetail.QUERY_GET_BY_ACCOUNTID_EXCEPT_ID);
			setInt(Numbers.ONE.getValue(), accountId);
			setInt(Numbers.TWO.getValue(), id);
			result = executeQuery();
			boolean hasRecords=false;
            while (result.next()) {
            	if(!hasRecords) {
            		accountDetails=new ArrayList<>();
            		hasRecords=true;
            	}
            	
            	AccountDetail accountDetail=new AccountDetail();
            	accountDetail.setProperty(getString(AccountDetail.FIELD_PROPERTY));
            	accountDetails.add(accountDetail);
            }	
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return accountDetails;
	}
}
