package mx.com.account.manager.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import mx.com.account.manager.domain.AccountDetail;
import mx.com.account.manager.exception.ServiceException;
import mx.com.account.manager.service.AccountDetailService;
import mx.com.account.manager.service.AccountDetailServiceImpl;
import mx.com.account.manager.util.InputOutputParameters;
import mx.com.account.manager.util.Utils;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class AccountDetailForm extends JDialog{
	private static final Logger LOGGER = Logger.getLogger(AccountDetailForm.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 8653794636729318109L;
	
	private static final String ERROR_TEXT="Error";
	private static final String MENSAGE_TEXT="Mensaje";
	
	
	private JTextField txtProperty;
	private JTextField txtValue;
	private JButton btnSave;
	private JButton btnCancel;
	private transient InputOutputParameters params;
	private transient AccountDetailService accountDetailService;
	private transient AccountDetail accountDetail;
	
	public AccountDetailForm(){
		accountDetailService =new AccountDetailServiceImpl();
		initialize();
	}
	
	private void initialize() {
		
		getContentPane().setLayout(null);
		setModal(true);
		setBounds(100, 100, 430, 200);
		setLocationRelativeTo(null);
		
		JLabel lblCuenta = new JLabel("Propiedad: ");
		lblCuenta.setBounds(30, 31, 65, 14);
		getContentPane().add(lblCuenta);
		
		txtProperty = new JTextField();
		txtProperty.setBounds(130, 28, 250, 20);
		getContentPane().add(txtProperty);
		txtProperty.setColumns(10);
				
		JLabel lblInfo = new JLabel("Valor: ");
		lblInfo.setBounds(30, 65, 80, 14);
		getContentPane().add(lblInfo);
				
		txtValue = new JTextField();
		txtValue.setBounds(130, 65, 250, 20);
		
		txtProperty.setBorder(Utils.getBorder());
		txtValue.setBorder(Utils.getBorder());
	    
		getContentPane().add(txtValue);
		
		btnSave = new JButton("Guardar");
		btnCancel = new JButton("Cancelar");
		
		btnSave.setBounds(100, 120, 89, 23);
		btnCancel.setBounds(230, 120, 89, 23);
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
		String property=txtProperty.getText().trim();
		String value=txtValue.getText().trim();
		
		if(property.length()==0) {
			JOptionPane.showMessageDialog(this, "El campo Propiedad es obligatorio!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
			txtProperty.requestFocus();
			return;
		}
		if(value.length()==0) {
			JOptionPane.showMessageDialog(this, "El campo Valor es obligatorio!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
			txtProperty.requestFocus();
			return;
		}
		
		if(params.getMode().equals(Utils.MODE_EDIT)) {
			accountDetail.setProperty(property);
			accountDetail.setValue(value);
			try {
				if(accountDetailService.existPropertyByAccountIdExceptId(property, accountDetail.getAccountId(), accountDetail.getId())){
					JOptionPane.showMessageDialog(this, "Ya existe un registro con el nombre de propiedad!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
					return;
				}
				updateAccountDetail();
			} catch (ServiceException e) {
				LOGGER.error(ERROR_TEXT,e);
				JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar actualizar el registro!", MENSAGE_TEXT,Utils.MESSAGE_ERROR);
			}
		}else {
			try {
				if(accountDetailService.existProperty(property,params.getAccountId())){
					JOptionPane.showMessageDialog(this, "Ya existe un registro con el mismo nombre de propiedad!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
					return;
				}
				accountDetail=new AccountDetail(params.getAccountId(),property,value);
				saveAccountDetail();
			} catch (ServiceException e) {
				LOGGER.error(ERROR_TEXT,e);
				JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar guardar el registro!", MENSAGE_TEXT,Utils.MESSAGE_ERROR);
			}
		}
	}
	
	public void saveAccountDetail() throws ServiceException {
		this.accountDetailService.saveAccountDetail(accountDetail);
		params.setStatus(Utils.SUCCCESS);
		JOptionPane.showMessageDialog(this, "El registro ha sido guardado correctamente!", MENSAGE_TEXT,Utils.MESSAGE_INFO);
		dispose();
	}
	
	public void updateAccountDetail() throws ServiceException {
		this.accountDetailService.updateAccountDetail(accountDetail);
		params.setStatus(Utils.SUCCCESS);
		JOptionPane.showMessageDialog(this, "El registro ha sido actualizado correctamente!", MENSAGE_TEXT,Utils.MESSAGE_INFO);
		dispose();
	}
	
	public void open(InputOutputParameters params) {
		this.params=params;
		setTitle(params.getTittle());
		if(params.getMode().equals(Utils.MODE_EDIT)) {
			getAccountDetail();
		}
		setVisible(true);
	}
	
	private void getAccountDetail() {
		try {
			accountDetail =accountDetailService.getAccountDetailById(params.getAccountDetailId());
			if(accountDetail!=null) {
				txtProperty.setText(accountDetail.getProperty());
				txtValue.setText(accountDetail.getValue());
			}
		} catch (ServiceException e) {
			LOGGER.error(ERROR_TEXT,e);
			JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar recuperta el registro!", MENSAGE_TEXT,Utils.MESSAGE_ERROR);
		}
	}
	
}
