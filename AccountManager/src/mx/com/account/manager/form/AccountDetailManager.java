package mx.com.account.manager.form;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

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
public class AccountDetailManager extends JDialog{
	private static final Logger LOGGER = Logger.getLogger(AccountDetailManager.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 673664602183140763L;
	private static final String ERROR_TEXT="Error";
	private static final String MENSAGE_TEXT="Mensaje";
	
	private TableModel model;
	private JTable table;
	private TableRowSorter <TableModel> sorter;
	private JScrollPane jsp;
	private JMenuItem smnNew;
	private JMenuItem smnEdit;
	private JMenuItem smnDelete;
	private AccountDetailService accountDetailService;
	private Integer accountId;
	private boolean delete;
	
	public AccountDetailManager() {
		accountDetailService=new AccountDetailServiceImpl();
		createFrame();
		bottonPanel();	
		createMenu();
		listeners();		
	}
	
	public void open(InputOutputParameters params) {
		accountId=params.getAccountId();
		setTitle(params.getTittle()+"...");
		loadTable();
		setVisible(true); 
	}
	
	private void createFrame() {
		setBounds(100, 100, 680, 250);
		setTitle("Información...");
		setModal(true);
		setResizable(true);
		setLocationRelativeTo(null);
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnInfo = new JMenu("Propiedad");
		menuBar.add(mnInfo);
		
		smnNew = new JMenuItem("Nuevo");
		smnEdit = new JMenuItem("Editar");
		smnDelete = new JMenuItem("Eliminar");
		
		mnInfo.add(smnNew);
		mnInfo.add(smnEdit);
		mnInfo.add(smnDelete);
	}
	
	private void bottonPanel() {
		table = new JTable();
		jsp = new JScrollPane(table);
		getContentPane().add(jsp,BorderLayout.CENTER);
	}
	
	private void loadTable() {
		String[] columnNames = {" ","Id","AccountId","Propiedad", "Valor" };
		Object[][] rowData = getData(accountId);
		if(rowData==null && !delete) {
			JOptionPane.showMessageDialog(getLayeredPane(), "No se encontró ningún registro!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
		}
		model = new DefaultTableModel(rowData, columnNames);
		
		sorter = new TableRowSorter<>(model);
		table.setModel(model);
		table.setRowSorter(sorter);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setWidth(0);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0); 
		table.getColumnModel().getColumn(2).setPreferredWidth(0);
		table.getColumnModel().getColumn(2).setWidth(0);
		table.getColumnModel().getColumn(2).setMinWidth(0);
		table.getColumnModel().getColumn(2).setMaxWidth(0); 
		table.getColumnModel().getColumn(3).setPreferredWidth(280);
		table.getColumnModel().getColumn(4).setPreferredWidth(350);
	}
	
	private void listeners() {
		smnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				InputOutputParameters params = new InputOutputParameters();
				params.setTittle("Alta de Propiedad...");
				params.setMode(Utils.MODE_NEW);
				params.setAccountId(accountId);
				AccountDetailForm accountDetail = new AccountDetailForm();
				accountDetail.open(params);
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
					AccountDetailForm accountDetailForm = new AccountDetailForm();
					InputOutputParameters params =new InputOutputParameters();
					params.setTittle("Modificación de Propiedad...");
					params.setAccountDetailId(id);
					params.setMode(Utils.MODE_EDIT);
					accountDetailForm.open(params);
					if(Utils.SUCCCESS.equals(params.getStatus())){
						loadTable();
					}
				}else{
					JOptionPane.showMessageDialog(getLayeredPane(), "Debe seleccionar un registro!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
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
							accountDetailService.removeAccountDetail(id);
							delete=true;
							loadTable();
						} catch (ServiceException e1) {
							LOGGER.error(ERROR_TEXT,e1);
							JOptionPane.showMessageDialog(getLayeredPane(), "Ocurrió un error al intentar eliminar el registro!", MENSAGE_TEXT,Utils.MESSAGE_ERROR);
						}
					}				
				}else{
					JOptionPane.showMessageDialog(getLayeredPane(), "Debe seleccionar un registro!", MENSAGE_TEXT,Utils.MESSAGE_WARNING);
				}
			}
		});
	}
	
	private Object[][] getData(Integer accountId) {
		Object[][] lista=null;
		try {
			lista = accountDetailService.getAllByAccountId(accountId);
		} catch (ServiceException e) {
			LOGGER.error(ERROR_TEXT,e);
			JOptionPane.showMessageDialog(getLayeredPane(), "Ocurrió un error al recuperar el registro!", MENSAGE_TEXT,Utils.MESSAGE_ERROR);
		}
		return lista;
	}
}
