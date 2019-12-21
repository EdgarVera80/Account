package mx.com.account.manager.form;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
public class LoginForm extends JDialog{
	private static final Logger LOGGER = Logger.getLogger(LoginForm.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2717827534659727091L;
	
	private static final String ERROR_TEXT="Error";
	private static final String MENSAGE_TEXT="Mensaje";
	
	private JButton btnAccept = new JButton("Aceptar");
	private JButton btnCancel = new JButton("Cancelar");
	private JTextField txtUser = new JTextField();
	private JPasswordField txtPassword = new JPasswordField();
	
	private transient UserService userService;
	private transient User user;
	
	public LoginForm(){
		userService = new UserServiceImpl();
		createDialog() ;
	}
	
	private void createDialog() {
		setTitle("Login...");
		setModal(true);
		setBounds(300, 300, 300, 200);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setlisteners();
		setLocationRelativeTo(null);
		
		Panel panelBotones = new Panel();

		Label titulo = new Label();
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		FlowLayout flowLayout1 = new FlowLayout();

		// propiedades de los controles
		titulo.setText("Login");
		titulo.setFont(new Font("Times-Roman", Font.BOLD + Font.ITALIC, 16));

		this.txtUser.setColumns(25);
		this.txtPassword.setColumns(25);
		this.txtUser.setBorder(Utils.getBorder());
		this.txtPassword.setBorder(Utils.getBorder());
		
		// gestor gridBaglayout
		getContentPane().setLayout(gbl);

		// primera fila - título
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(titulo, gbc);
		
		// segunda fila - nombre
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(new Label("Usuario:"), gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(txtUser, gbc);
		
		// tercera fila - dirección
		gbc.gridwidth = 1;
		add(new Label("Password:"), gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(txtPassword, gbc);
		
		// panel de los botones
		panelBotones.setLayout(flowLayout1);
		
		panelBotones.add(btnAccept);
		panelBotones.add(btnCancel);

		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.insets = new Insets(15, 0, 0, 0);
		add(panelBotones, gbc);
	}
	
	private void add(Component comp, GridBagConstraints gb) {
		getContentPane().add(comp, gb);
	}
	
	private void setlisteners() {
		this.btnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateUser() ;
			}
		});
		
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		txtPassword.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				/**
				 * Unused
				 */
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10) {
					validateUser();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				/**
				 * Unused
				 */
			}
		});
		
		txtUser.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				/**
				 * Unused
				 */
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10) {
					validateUser();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				/**
				 * Unused
				 */
			}
		});
	}
	
	public void open(User user) {
		this.user=user;
		setVisible(true); 
	}
	
	private void validateUser() {
		String user=this.txtUser.getText();
		String pass=new String(this.txtPassword.getPassword());
		
		if(user.length()==0 || pass.length()==0) {
			JOptionPane.showMessageDialog(this, "Usuario o Password incorrecto!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
			this.txtUser.setText("");
			this.txtPassword.setText("");
			this.txtUser.requestFocus();
			return;
		}
		
		try {
			String userHash = SecurityUtil.hashGenerator(user);
			String passHash = SecurityUtil.hashGenerator(pass);
			if(userService.validUser(new User(userHash,passHash))) {
				this.user.setUser(userHash);
				dispose();
			}else {
				JOptionPane.showMessageDialog(this, "Usuario o Password incorrecto!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
				this.txtUser.setText("");
				this.txtPassword.setText("");
				this.txtUser.requestFocus();
			}
		} catch (ServiceException|SecurityExceptionHandler e) {
			LOGGER.error(ERROR_TEXT,e);
			JOptionPane.showMessageDialog(this, "Ocurrió un error al validar el usuario!", MENSAGE_TEXT,Utils.MESSAGE_ERROR);
		}
	}
}
