package mx.com.account.manager.form;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import mx.com.account.manager.domain.User;
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
public class AccountManager {
	private static final Logger LOGGER = Logger.getLogger(AccountManager.class);
	
	private JFrame frame;
	private JTextField txtFilter;
	private DefaultTableModel model;
	private JTable table;
	private TableRowSorter <TableModel> sorter;
	private JScrollPane jsp;
	private JMenuBar menuBar;
	private JMenu mnAccount;
	private JMenu mnOptions;
	private JMenuItem smnNew;
	private JMenuItem smnEdit;
	private JMenuItem smnChangePassword;
	private JMenuItem smnConfiguration;
	private JMenuItem smnDelete;
	
	private AccountService accountService;
	private User user;
	
	/**
	 * Create the application.
	 */
	public AccountManager() {
		LOGGER.info("Inicia AccountManager...");
		accountService=new AccountServiceImpl();
		initialize();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountManager window = new AccountManager();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					LOGGER.error("Error",e);
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createFrame();
		login();
		topPanel();
		bottonPanel();		
		createMenu();
		listener();
	}
	
	private void topPanel() {
		txtFilter = new JTextField(15);
		txtFilter.setBorder(Utils.getBorder());
		JLabel lblFilter = new JLabel("Filtrar:");
		JPanel topPanel = new JPanel();
		topPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		frame.getContentPane().add(topPanel,BorderLayout.NORTH);
		
		FlowLayout flowLayout =new FlowLayout();
		topPanel.setLayout(flowLayout);
		topPanel.add(lblFilter);
		topPanel.add(txtFilter);
	}
	
	private void bottonPanel() {
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loadTable();
		jsp = new JScrollPane(table);
		frame.getContentPane().add(jsp,BorderLayout.CENTER);
	}
	
	private void createFrame() {
		frame = new JFrame("Cuentas...");
		frame.setBounds(100, 100, 655, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
	}
	
	private void login() {
		LoginForm login=new LoginForm();
		user =new User();
		login.open(user);
	}
	
	private void createMenu() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnAccount = new JMenu("Cuenta");
		mnOptions = new JMenu("Opciones");
		menuBar.add(mnAccount);
		menuBar.add(mnOptions);
		
		smnNew = new JMenuItem("Nuevo");
		smnEdit = new JMenuItem("Editar");
		smnDelete=new JMenuItem("Eliminar");
		mnAccount.add(smnNew);
		mnAccount.add(smnEdit);
		mnAccount.add(smnDelete);
		
		smnChangePassword = new JMenuItem("Cambiar password");
		mnOptions.add(smnChangePassword);
		smnConfiguration = new JMenuItem("Configuración");
		mnOptions.add(smnConfiguration);
	}
	
	private void listener() {
		txtFilter.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(txtFilter.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(txtFilter.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(txtFilter.getText());
			}

			public void search(String str) {
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});
		
		smnNew.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountForm cuantaDialog = new AccountForm();
				InputOutputParameters params =new InputOutputParameters();
				params.setTittle("Alta de cuenta...");
				params.setMode(Utils.MODE_NEW);
				cuantaDialog.open(params);
				if(Utils.SUCCCESS.equals(params.getStatus())){
					loadTable();
				}
			}
		});
		
		smnEdit.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if(row>=0) {
					Integer id=(Integer) table.getValueAt(row, 1);
					AccountForm cuantaDialog = new AccountForm();
					InputOutputParameters params =new InputOutputParameters();
					params.setTittle("Modificación de cuenta...");
					params.setAccountId(id);
					params.setMode(Utils.MODE_EDIT);
					cuantaDialog.open(params);
					if(Utils.SUCCCESS.equals(params.getStatus())){
						loadTable();
					}
				}else{
					JOptionPane.showMessageDialog(frame, "Debe seleccionar un registro!", "Mensaje",Utils.MESSAGE_WARNING);
				}
			}
		});
		
		smnDelete.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if(row>=0) {
					int answer=JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro seleccionado?");
					if(answer==0) {
						Integer id=(Integer) table.getValueAt(row, 1);
						try {
							accountService.removeAccount(id);
							loadTable();
						} catch (ServiceException e1) {
							LOGGER.error("Error",e1);
							JOptionPane.showMessageDialog(frame, "Ocurrió un error al intentar recuperar las Cuentas!", "Mensaje",Utils.MESSAGE_ERROR);
						}
					}				
				}else{
					JOptionPane.showMessageDialog(frame, "Debe seleccionar un registro!", "Mensaje",Utils.MESSAGE_WARNING);
				}
			}
		});
		
		smnChangePassword.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangePasswordForm cp=new ChangePasswordForm();
				cp.open(user);
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			 public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		       
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		            int row = table.rowAtPoint(point);
		            Integer id=(Integer) table.getValueAt(row, 1);
		            String account=(String) table.getValueAt(row, 2);
		            
		            InputOutputParameters params =new InputOutputParameters();
		            params.setTittle(account);
		            params.setAccountId(id);
		            AccountDetailManager accountDetail=new AccountDetailManager();
		            accountDetail.open(params);
		        }
			 }
		});
	}
	
	private void loadTable() {
		String[] columnNames = {" ","Id","Cuenta","Creación", "Información" };
		
		Object[][] rowData =getData();
		
		model = new DefaultTableModel(rowData, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override 
		    public boolean isCellEditable(int row, int column){
		        return false;
		    }
		};
		
		
		sorter = new TableRowSorter<TableModel>(model);
		table.setModel(model);
		table.setRowSorter(sorter);
		
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);

		table.getColumnModel().getColumn(1).setWidth(0);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0); 
		
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(250);
		txtFilter.setText("");
	}
	
	private Object[][] getData() {
		try {
			return accountService.getAllAccounts();
		} catch (ServiceException e) {
			LOGGER.error("Error",e);
			JOptionPane.showMessageDialog(frame, "Ocurrió un error al intentar recuperar las Cuentas!", "Mensaje",Utils.MESSAGE_ERROR);
		}
		
		return null;
	}
}
