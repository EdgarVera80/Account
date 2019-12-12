package mx.com.account.manager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.com.account.manager.db.DataBaseHandler;
import mx.com.account.manager.domain.Account;
import mx.com.account.manager.exception.DaoException;
import mx.com.account.manager.util.Numbers;


/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class AccountDaoImpl extends DataBaseHandler implements AccountDao{
	private ResultSet result;
	
	public AccountDaoImpl() {
		/**
		 * Default Constructor
		 */
	}
	
	@Override
	public void saveAccount(Account account) throws DaoException {
		try {
			open();
			prepareStatement(Account.QUERY_SAVE);
            setString(Numbers.ONE.getValue(), account.getAccount());
            setString(Numbers.TWO.getValue(), account.getCreation());
            setString(Numbers.THREE.getValue(), account.getInformation());
			execute();
			commit();
		} catch (SQLException e) {
			rollback();
			throw new DaoException(e);
		} finally {
			close();
		}
	}

	@Override
	public void updateAccount(Account account) throws DaoException {
		
		try {
			open();
			prepareStatement(Account.QUERY_UPDATE);
            setString(Numbers.ONE.getValue(), account.getAccount());
            setString(Numbers.TWO.getValue(), account.getInformation());
            setInt(Numbers.THREE.getValue(), account.getId());
			execute();
			commit();
		} catch (SQLException e) {
			rollback();
			throw new DaoException(e);
		}finally{
			close();
		}
	}

	@Override
	public List<Account> getAllAccounts() throws DaoException {
		List<Account> accounts=null;
		try {
			open();
			prepareStatement(Account.QUERY_GET_ALL);
			result = executeQuery();
			boolean hasRecords=false;
            while (result.next()) {
            	if(!hasRecords) {
            		accounts=new ArrayList<>();
            		hasRecords=true;
            	}
            	
            	Account account=new Account();
            	account.setId(getInt(Account.FIELD_ID));
            	account.setAccount(getString(Account.FIELD_ACCOUNT));
            	account.setCreation(getString(Account.FIELD_CREATION));
            	account.setInformation(getString(Account.FIELD_INFORMATION));
            	accounts.add(account);
            }	
           
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return accounts;
	}

	@Override
	public Account getAccountById(Integer id) throws DaoException {
		Account account=null;
		
		try {
			open();
			prepareStatement(Account.QUERY_GET_BY_ID);
			setInt(1, id);
			result = executeQuery();
            while (result.next()) {
            	account=new Account();
            	account.setId(getInt(Account.FIELD_ID));
            	account.setAccount(getString(Account.FIELD_ACCOUNT));
            	account.setCreation(getString(Account.FIELD_CREATION));
            	account.setInformation(getString(Account.FIELD_INFORMATION));
            }	
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return account;
	}

	@Override
	public void removeAccount(Integer accountId) throws DaoException {
		try {
			open();
			prepareStatement(Account.QUERY_DELETE);
            setInt(Numbers.ONE.getValue(), accountId);
			execute();
			commit();
		} catch (SQLException e) {
			rollback();
			throw new DaoException(e);
		} finally {
			close();
		}
	}

	@Override
	public boolean existAccountName(String accountName) throws DaoException {
		boolean existe=false;
		
		try {
			open();
			prepareStatement(Account.QUERY_BY_ACCOUNT);
			setString(1, accountName.toUpperCase());
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
	public boolean existAccountNameOtherId(String account, Integer id) throws DaoException {
		boolean existe=false;
		
		try {
			open();
			prepareStatement(Account.QUERY_BY_ACCOUNT_AND_ID);
			setString(Numbers.ONE.getValue(),account.toUpperCase());
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
	public List<Account> getAccountsExceptId(Integer id) throws DaoException {
		List<Account> accounts=null;
		
		try {
			open();
			prepareStatement(Account.QUERY_EXCEPT_ID);
			setInt(Numbers.ONE.getValue(),id);
			result = executeQuery();
			boolean hasRecords=false;
            while (result.next()) {
            	if(!hasRecords) {
            		accounts=new ArrayList<>();
            		hasRecords=true;
            	}
            	
            	Account account=new Account();
            	account.setAccount(getString(Account.FIELD_ACCOUNT));
            	accounts.add(account);
            }	
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return accounts;
	}

	@Override
	public Account getAccountByAccount(String accountName) throws DaoException {
		Account account=null;
		
		try {
			open();
			prepareStatement(Account.QUERY_GET_BY_ACCOUNT);
			setString(1,accountName );
			result = executeQuery();
            while (result.next()) {
            	account=new Account();
            	account.setId(getInt(Account.FIELD_ID));
            	account.setAccount(getString(Account.FIELD_ACCOUNT));
            	account.setCreation(getString(Account.FIELD_CREATION));
            	account.setInformation(getString(Account.FIELD_INFORMATION));
            }	
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			close();
		}
		
		return account;
	}
}
