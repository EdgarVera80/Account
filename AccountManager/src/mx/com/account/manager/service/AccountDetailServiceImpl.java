package mx.com.account.manager.service;

import java.util.List;

import mx.com.account.manager.dao.AccountDetailDao;
import mx.com.account.manager.dao.AccountDetailDaoImpl;
import mx.com.account.manager.domain.AccountDetail;
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
public class AccountDetailServiceImpl implements AccountDetailService{
	
	private AccountDetailDao accountDetailDao;
	private static final int ARRAY_SIZE=5;
	
	public AccountDetailServiceImpl() {
		this.accountDetailDao=new AccountDetailDaoImpl();
	}
	
	@Override
	public Object[][] getAllByAccountId(Integer accountId) throws ServiceException {

		
		List<AccountDetail> accountDetails=null;
		try {
			accountDetails = accountDetailDao.getAllByAccountId(accountId);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
		
		Object[][] lista=null;
		if(accountDetails!=null && !accountDetails.isEmpty()) {
			lista= new Object[accountDetails.size()][ARRAY_SIZE];
			try {
				for(int i=0;i<accountDetails.size();i++) {
					lista[i][Numbers.ZERO.getValue()]="";
					lista[i][Numbers.ONE.getValue()]=accountDetails.get(i).getId();
					lista[i][Numbers.TWO.getValue()]=accountDetails.get(i).getAccountId();
					lista[i][Numbers.THREE.getValue()]=decrypt(accountDetails.get(i).getProperty());
					lista[i][Numbers.FOUR.getValue()]= decrypt(accountDetails.get(i).getValue());
				}		
			} catch (EncryptException e) {
				throw new ServiceException(e.getErrorCode(), e.getMessage());
			}
		}
		
		return lista;
	}

	@Override
	public void saveAccountDetail(AccountDetail accountDetail) throws ServiceException {
		try {
			accountDetail.setProperty(encrypt(accountDetail.getProperty()));
			accountDetail.setValue(encrypt(accountDetail.getValue()));
			accountDetailDao.saveAccountDetail(accountDetail);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		} catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public void updateAccountDetail(AccountDetail accountDetail) throws ServiceException {
		try {
			accountDetail.setProperty(encrypt(accountDetail.getProperty()));
			accountDetail.setValue(encrypt(accountDetail.getValue()));
			accountDetailDao.updateAccountDetail(accountDetail);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		} catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public AccountDetail getAccountDetailById(Integer id) throws ServiceException {
		try {
			AccountDetail accountDetail =accountDetailDao.getAccountDetailById(id);
			accountDetail.setProperty(decrypt(accountDetail.getProperty()));
			accountDetail.setValue(decrypt(accountDetail.getValue()));
			return accountDetail;
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		} catch (EncryptException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public boolean existPropertyByAccountIdExceptId(String property, Integer accountId, Integer id) throws ServiceException {
		try {
			List<AccountDetail> lista =accountDetailDao.getByAccountIdExceptId(accountId, id);
			if(lista!=null && !lista.isEmpty()) {
				for(int i=0;i<lista.size();i++) {
					String aux=decrypt(lista.get(i).getProperty());
					if(aux.toUpperCase().equals(property.toUpperCase())) {
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
	public void removeAccountDetail(Integer id) throws ServiceException {
		try {
			accountDetailDao.removeAccountDetail(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getErrorCode(), e.getMessage());
		}
	}
	
	@Override
	public boolean existProperty(String property, Integer accountId) throws ServiceException {
		try {	
			List<AccountDetail> lista =accountDetailDao.getAllByAccountId(accountId);
			if(lista!=null && !lista.isEmpty()) {
				for(int i=0;i<lista.size();i++) {
					String aux=decrypt(lista.get(i).getProperty());
					if(aux.toUpperCase().equals(property.toUpperCase())) {
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
	
	private static String encrypt(String text) throws EncryptException {
		return EncryptUtil.encrypt(Utils.KEY, Utils.IV,text);
	}
	
	private static String decrypt(String text) throws EncryptException {
		return EncryptUtil.decrypt(Utils.KEY, Utils.IV,text);
	}
}
