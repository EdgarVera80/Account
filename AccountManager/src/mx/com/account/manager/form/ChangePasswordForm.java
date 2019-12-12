package mx.com.account.manager.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

import mx.com.account.manager.domain.User;
import mx.com.account.manager.exception.SecurityExceptionHandler;
import mx.com.account.manager.exception.ServiceException;
import mx.com.account.manager.service.UserService;
import mx.com.account.manager.service.UserServiceImpl;
import mx.com.account.manager.util.SecurityUtil;
import mx.com.account.manager.util.Utils;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public class ChangePasswordForm extends JDialog {
	private static final Logger LOGGER = Logger.getLogger(ChangePasswordForm.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1976001226448951813L;

	private JPasswordField txtPassword;
	private JPasswordField txtNewPassword;
	private JPasswordField txtConfirmPassword;
	private JButton btnAccept;
	private JButton btnCancel;
	private transient UserService userService;
	private transient User user;
	
	public ChangePasswordForm() {
		userService = new UserServiceImpl();
		initialize();
	}
	
	private void initialize() {
		
		getContentPane().setLayout(null);
		setModal(true);
		setBounds(100, 100, 430, 220);
		setLocationRelativeTo(null);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(30, 31, 80, 14);
		getContentPane().add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(160, 28, 230, 20);
		getContentPane().add(txtPassword);
//		txtPassword.setColumns(10);
				
		JLabel lblNewPassword = new JLabel("Nuevo Password: ");
		lblNewPassword.setBounds(30, 70, 120, 14);
		getContentPane().add(lblNewPassword);
				
		txtNewPassword = new JPasswordField();
		txtNewPassword.setBounds(160, 65, 230, 20);

		getContentPane().add(txtNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirmar Password: ");
		lblConfirmPassword.setBounds(30, 105, 130, 14);
		getContentPane().add(lblConfirmPassword);
				
		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.setBounds(160, 100, 230, 20);

		getContentPane().add(txtConfirmPassword);
		
		txtPassword.setBorder(Utils.getBorder());
		txtNewPassword.setBorder(Utils.getBorder());
		txtConfirmPassword.setBorder(Utils.getBorder());
		
		btnAccept = new JButton("Aceptar");
		btnCancel = new JButton("Cancelar");
		
		btnAccept.setBounds(100, 140, 89, 23);
		btnCancel.setBounds(230, 140, 89, 23);
		getContentPane().add(btnAccept);
		getContentPane().add(btnCancel);
	}
	
	public void open(User user) {
		this.user=user;
		setTitle("Cambiar password...");
		
		listeners();
		setVisible(true);
	}
	
	private void listeners(){
		btnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateFields();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	
	private void validateFields() {
		String pass=new String(this.txtPassword.getPassword());
		try {
			String passHash = SecurityUtil.hashGenerator(pass);
			user.setPassword(passHash);
			boolean validUser=userService.validUser(user);
			if(!validUser) {
				JOptionPane.showMessageDialog(getLayeredPane(), "El password acctual es incorrecto!", "Mensaje",Utils.MESSAGE_WARNING);
				txtPassword.setText("");
				txtPassword.requestFocus();
				return;
			}
			
			String newPassword=new String(this.txtNewPassword.getPassword());
			if(newPassword==null || newPassword.trim().length()==0) {
				JOptionPane.showMessageDialog(getLayeredPane(), "Debe ingresar el nuevo password!", "Mensaje",Utils.MESSAGE_WARNING);
				txtNewPassword.requestFocus();
				return;
			}
			String confirmPassword=new String(this.txtConfirmPassword.getPassword());
			if(!newPassword.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(getLayeredPane(), "Los password no coinciden!", "Mensaje",Utils.MESSAGE_WARNING);
				txtNewPassword.setText("");
				txtConfirmPassword.setText("");
				txtNewPassword.requestFocus();
				return;
			}
			
			newPassword = SecurityUtil.hashGenerator(newPassword);
			user.setPassword(newPassword);
			userService.changePassword(user);
			
			JOptionPane.showMessageDialog(getLayeredPane(), "El password ha sido cambiado correctamente!", "Mensaje",Utils.MESSAGE_INFO);
			setVisible(false);
		} catch (SecurityExceptionHandler e) {
			LOGGER.error("Error",e);
			JOptionPane.showMessageDialog(getLayeredPane(), "Ocurrió un error al validar el usuario!", "Mensaje",Utils.MESSAGE_ERROR);
		} catch (ServiceException e) {
			LOGGER.error("Error",e);
			JOptionPane.showMessageDialog(getLayeredPane(), "Ocurrió un error al validar el usuario!", "Mensaje",Utils.MESSAGE_ERROR);
		}
	}
}
