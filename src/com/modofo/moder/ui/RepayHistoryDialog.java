package com.modofo.moder.ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import com.modofo.moder.Calculator;
import com.modofo.moder.Consts;
import com.modofo.moder.LedgerEntryDao;
import com.modofo.moder.model.LedgerEntry;
import com.modofo.moder.model.Repayment;
import com.modofo.util.BaseJDialog;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

public class RepayHistoryDialog extends BaseJDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel headerPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable historyTable = null;
	
	private List historyResult = null;

	private JLabel titleLabel = null;

	private JLabel nameLabel = null;

	private JTextField nameTextField = null;

	private JLabel principalLabel = null;

	private JTextField principalTextField = null;

	private JLabel interestLabel = null;

	private JTextField interestTextField = null;
	
	private JDialog selfDlg;
	private LedgerEntryDao ledgerEntryDao;  
	private MainFrame parentWindow;

	public RepayHistoryDialog() {
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(280, 500);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getHeaderPanel(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes headerPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getHeaderPanel() {
		if (headerPanel == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridwidth = 5;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.gridx = 5;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 4;
			gridBagConstraints5.gridy = 1;
			interestLabel = new JLabel();
			interestLabel.setText(getLocaleMessage("table.header.interest.unpaid"));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.gridy = 1;
			principalLabel = new JLabel();
			principalLabel.setText(getLocaleMessage("label.principal"));
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 1;
			nameLabel = new JLabel();
			nameLabel.setText(getLocaleMessage("label.name"));
			titleLabel = new JLabel();
			titleLabel.setText(getLocaleMessage("title.repay.history"));
			headerPanel = new JPanel();
			headerPanel.setLayout(new GridBagLayout());
			headerPanel.add(titleLabel, gridBagConstraints7);
			headerPanel.add(nameLabel, gridBagConstraints);
			headerPanel.add(getNameTextField(), gridBagConstraints1);
			headerPanel.add(principalLabel, gridBagConstraints2);
			headerPanel.add(getPrincipalTextField(), gridBagConstraints3);
			headerPanel.add(interestLabel, gridBagConstraints5);
			headerPanel.add(getInterestTextField(), gridBagConstraints6);
		}
		return headerPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getHistoryTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes historyTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getHistoryTable() {
		if (historyTable == null) {
			DefaultTableColumnModel cm = new DefaultTableColumnModel();
			
			String[] tableHeaders = new String[]{
					getLocaleMessage("table.header.repaydate"),
					getLocaleMessage("table.header.repaysum"),
					getLocaleMessage("table.header.repaytype")
			};
			int[] columnwidths = {80,120,80};
			
			TableColumn tc =null;
			for(int i=0;i<tableHeaders.length;i++){
				tc = new TableColumn(i);
				tc.setHeaderValue(tableHeaders[i]);
				tc.setMaxWidth(columnwidths[i]);
				cm.addColumn(tc);
			}
			
			historyTable = new JTable(new HistoryTableModel(), cm);

			historyTable.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					
				}
			});
		}
		return historyTable;
	}
	class HistoryTableModel extends AbstractTableModel {
		public int getRowCount() {
			if (historyResult == null)
				return 0;
			return historyResult.size();
		}

		public int getColumnCount() {
			return 3;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if (historyResult == null || historyResult.size() == 0)
				return null;
			
			Repayment repay = (Repayment) historyResult.get(rowIndex);
			switch (columnIndex) {
			case 0:{
				Date d =repay.getRepayDate();
				return new SimpleDateFormat("yyyy-MM-dd").format(d);
			}
			case 1:
				return repay.getRepaysum();
			case 2:{
				if(repay.getType().equalsIgnoreCase(Consts.REPAY_TYPE_INTEREST)){
					return getLocaleMessage("label.interest");
				}else if(repay.getType().equalsIgnoreCase(Consts.REPAY_TYPE_PRINCIPAL)){
					return getLocaleMessage("label.principal");
				}
			}
			default:
				return "";
			}
		}
	}
	/**
	 * This method initializes nameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField();
			nameTextField.setEditable(false);
		}
		return nameTextField;
	}

	/**
	 * This method initializes principalTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPrincipalTextField() {
		if (principalTextField == null) {
			principalTextField = new JTextField();
			principalTextField.setEditable(false);
		}
		return principalTextField;
	}

	/**
	 * This method initializes interestTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getInterestTextField() {
		if (interestTextField == null) {
			interestTextField = new JTextField();
			interestTextField.setEditable(false);
		}
		return interestTextField;
	}
	
	public void setLedgerEntry(LedgerEntry e) {
		getNameTextField().setText(e.getName());
		getPrincipalTextField().setText(e.getPrincipal()+"");
		getInterestTextField().setText((e.getInterest()+Calculator.calculateInterest(e)-e.getPaidInterest())+"");
		
		historyResult = ledgerEntryDao.getRepayment(e);
		historyTable.updateUI();
	}

	public LedgerEntryDao getLedgerEntryDao() {
		return ledgerEntryDao;
	}

	public void setLedgerEntryDao(LedgerEntryDao ledgerEntryDao) {
		this.ledgerEntryDao = ledgerEntryDao;
	}

	public MainFrame getParentWindow() {
		return parentWindow;
	}

	public void setParentWindow(MainFrame parentWindow) {
		this.parentWindow = parentWindow;
	}

}
