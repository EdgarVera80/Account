package mx.com.account.manager.service;

import java.util.List;

import mx.com.account.manager.dao.AccountDao;
import mx.com.account.manager.dao.AccountDaoImpl;
import mx.com.account.manager.domain.Account;
import mx.com.account.manager.exception.DaoException;
import mx.com.account.manager.exception.EncryptException;
import mx.com.account.manager.exception.ServiceException;
import mx.com.account.manager.util.EncryptUtil;
import mx.com.account.manager.util.Numbers;
import mx.com.account.manager.util.Utils;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class AccountServiceImpl implements AccountService {
	
	private AccountDao accountDao;
	private static final int ARRAY_SIZE=5;
	
	public AccountServiceImpl() {
		accountDao = new AccountDaoImpl();
	}
	
	@Override
	public void saveAccount(Account account) throws ServiceException {
		try {
			account.setAccount(encrypt(account.getAccount()));
			account.setInformation(encrypt(account.getInformation()));
			accountDao.saveAccount(account);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		} catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public void updateAccount(Account account) throws ServiceException {
		try {
			account.setAccount(encrypt(account.getAccount()));
			account.setInformation(encrypt(account.getInformation()));
			accountDao.updateAccount(account);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		} catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public Object[][] getAllAccounts() throws ServiceException {
		
		List<Account> accounts=null;
		try {
			accounts = accountDao.getAllAccounts();
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
		
		Object[][] lista=null;
		if(accounts!=null && !accounts.isEmpty()) {
			lista= new Object[accounts.size()][ARRAY_SIZE];
			try {
				for(int i=0;i<accounts.size();i++) {
					lista[i][Numbers.ZERO.getValue()]="";
					lista[i][Numbers.ONE.getValue()]=accounts.get(i).getId();
					lista[i][Numbers.TWO.getValue()]=decrypt(accounts.get(i).getAccount());
					lista[i][Numbers.THREE.getValue()]=accounts.get(i).getCreation();
					lista[i][Numbers.FOUR.getValue()]=decrypt(accounts.get(i).getInformation());
				}		
			} catch (EncryptException e) {
				throw new ServiceException(e.getErrorCode(), e.getMessage());
			}
		}
		
		return lista;
	}

	@Override
	public Account getAccountById(Integer id) throws ServiceException {
		try {
			Account account = accountDao.getAccountById(id);
			account.setAccount(decrypt(account.getAccount()));
			account.setInformation(decrypt(account.getInformation()));
			return account;
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public void removeAccount(Integer accountId) throws ServiceException {
		try {
			accountDao.removeAccount(accountId);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public boolean existAccountName(String accountName) throws ServiceException {
		try {
			return accountDao.existAccountName(encrypt(accountName));
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		} catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public boolean existAccountNameOtherId(String account, Integer id) throws ServiceException {
		try {
			account = encrypt(account);
			return accountDao.existAccountNameOtherId(account, id);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		} catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}
	
	@Override
	public boolean existWithOtherId(String account, Integer id) throws ServiceException {
		try {
			List<Account> accounts=accountDao.getAccountsExceptId(id);
			if(accounts!=null && !accounts.isEmpty()) {
				for(int i=0;i<accounts.size();i++) {
					String aux=decrypt(accounts.get(i).getAccount());
					if(aux.toUpperCase().equals(account.toUpperCase())) {
						return true;
					}
				}			
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		} catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
		
		return false;
	}
	
	@Override
	public Object[][] getAccountByIdArray(Integer id) throws ServiceException {
		
		Account account=null;
		try {
			account = accountDao.getAccountById(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
		
		Object[][] lista=null;
		if(account!=null ) {
			lista= new Object[1][ARRAY_SIZE];
			try {
				lista[0][Numbers.ZERO.getValue()]="";
				lista[0][Numbers.ONE.getValue()]=account.getId();
				lista[0][Numbers.TWO.getValue()]=decrypt(account.getAccount());
				lista[0][Numbers.THREE.getValue()]=account.getCreation();
				lista[0][Numbers.FOUR.getValue()]=decrypt(account.getInformation());
			} catch (EncryptException e) {
				throw new ServiceException(e.getErrorCode(), e.getMessage());
			}
		}
		
		return lista;
	}

	@Override
	public Account getAccountByAccount(String accountName) throws ServiceException {
		try {
			Account account = accountDao.getAccountByAccount(accountName);
			account.setAccount(decrypt(account.getAccount()));
			account.setInformation(decrypt(account.getInformation()));
			return account;
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}


	private static String encrypt(String text) throws EncryptException {
		return EncryptUtil.encrypt(Utils.KEY, Utils.IV,text);
	}
	
	private static String decrypt(String text) throws EncryptException {
		return EncryptUtil.decrypt(Utils.KEY, Utils.IV,text);
	}

}
