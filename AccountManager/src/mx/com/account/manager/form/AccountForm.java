package mx.com.account.manager.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import mx.com.account.manager.domain.Account;
import mx.com.account.manager.exception.ServiceException;
import mx.com.account.manager.service.AccountService;
import mx.com.account.manager.service.AccountServiceImpl;
import mx.com.account.manager.util.InputOutputParameters;
import mx.com.account.manager.util.Utils;


/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class AccountForm extends JDialog{
	private static final Logger LOGGER = Logger.getLogger(AccountForm.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -8533448513124469814L;

	private JTextField txtAccount;
	private JTextArea txaInfo;
	private JButton btnSave ;
	private JButton btnCancel;
	private InputOutputParameters params;
	private transient AccountService accountService;
	private transient Account account;
	private String pattern = "dd/MM/yyyy";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	public AccountForm() {
		accountService=new AccountServiceImpl();
		initialize();
	}
	
	private void initialize() {
		
		getContentPane().setLayout(null);
		setModal(true);
		setBounds(100, 100, 430, 220);
		setLocationRelativeTo(null);
		
		JLabel lblCuenta = new JLabel("Cuenta: ");
		lblCuenta.setBounds(30, 31, 46, 14);
		getContentPane().add(lblCuenta);
		
		txtAccount = new JTextField();
		txtAccount.setBounds(130, 28, 250, 20);
		getContentPane().add(txtAccount);
		txtAccount.setColumns(10);
	
				
		JLabel lblInfo = new JLabel("Información: ");
		lblInfo.setBounds(30, 65, 80, 14);
		getContentPane().add(lblInfo);
				
		txaInfo = new JTextArea();
		txaInfo.setBounds(130, 65, 250, 40);
		
		txtAccount.setBorder(Utils.getBorder());
		txaInfo.setBorder(Utils.getBorder());
	    
		getContentPane().add(txaInfo);
		
		btnSave = new JButton("Guardar");
		btnCancel = new JButton("Cancelar");
		
		btnSave.setBounds(100, 140, 89, 23);
		btnCancel.setBounds(230, 140, 89, 23);
		getContentPane().add(btnSave);
		getContentPane().add(btnCancel);
		
		listeners();
	}
	
	private void listeners(){
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateFields();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				params.setStatus(Utils.ERROR);
				setVisible(false);
			}
		});
	}
	
	private void validateFields() {
		String cuenta=txtAccount.getText().trim();
		String info=txaInfo.getText().trim();
		
		if(cuenta.trim().length()==0) {
			JOptionPane.showMessageDialog(this, "El campo Cuenta es obligatorio!", "Mensaje",Utils.MESSAGE_WARNING);
			txtAccount.requestFocus();
			return;
		}
		
		if(params.getMode().equals(Utils.MODE_EDIT)) {
			account.setAccount(cuenta);
			account.setInformation(info);
			try {
				if(accountService.existWithOtherId(account.getAccount(), account.getId())){
					JOptionPane.showMessageDialog(this, "Ya existe un registro con el mismo nombre de cuenta!", "Mensaje",Utils.MESSAGE_WARNING);
					return;
				}
				updateAccount();
			} catch (ServiceException e) {
				LOGGER.error("Error",e);
				JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar actualizar el registro!", "Mensaje",Utils.MESSAGE_ERROR);
			}
		}else {
			try {
				if(accountService.existAccountName(cuenta)){
					JOptionPane.showMessageDialog(this, "Ya existe un registro con el mismo nombre de cuenta!", "Mensaje",Utils.MESSAGE_WARNING);
					return;
				}
				String date = simpleDateFormat.format(new Date());
				account=new Account(cuenta,date,info);
				saveAccount();
			} catch (ServiceException e) {
				LOGGER.error("Error",e);
				JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar guardar el registro!", "Mensaje",Utils.MESSAGE_ERROR);
			}
		}
	}
	
	public void saveAccount() throws ServiceException {
		this.accountService.saveAccount(account);
		params.setStatus(Utils.SUCCCESS);
		JOptionPane.showMessageDialog(this, "El registro ha sido guardado correctamente!", "Mensaje",Utils.MESSAGE_INFO);
		dispose();
	}
	
	public void updateAccount() throws ServiceException{
		this.accountService.updateAccount(account);
		params.setStatus(Utils.SUCCCESS);
		JOptionPane.showMessageDialog(this, "El registro ha sido actualizado correctamente!", "Mensaje",Utils.MESSAGE_INFO);
		dispose();
	}
	
	public void open(InputOutputParameters params) {
		this.params=params;
		setTitle(params.getTittle());
		if(params.getMode().equals(Utils.MODE_EDIT)) {
			getAccount();
		}
		setVisible(true);
	}
	
	private void getAccount() {
		try {
			account =accountService.getAccountById(params.getAccountId());
			if(account!=null) {
				txtAccount.setText(account.getAccount());
				txaInfo.setText(account.getInformation());
			}
		} catch (ServiceException e) {
			LOGGER.error("Error",e);
			JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar obtener la cuenta!", "Mensaje",Utils.MESSAGE_ERROR);
		}
	}
}
