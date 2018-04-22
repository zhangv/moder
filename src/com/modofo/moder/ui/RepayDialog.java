package com.modofo.moder.ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.modofo.moder.Calculator;
import com.modofo.moder.Consts;
import com.modofo.moder.LedgerEntryDao;
import com.modofo.moder.model.LedgerEntry;
import com.modofo.util.BaseJDialog;

public class RepayDialog extends BaseJDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel headerPanel = null;

	private JPanel contentPanel = null;

	private JLabel titleLabel = null;

	private JLabel nameLabel = null;

	private JTextField nameTextField = null;

	private JLabel sumLabel = null;

	private JTextField sumTextField = null;

	private JLabel repaysumLabel = null;

	private JTextField repaysumTextField = null;

	private JButton saveButton = null;

	private JButton cancelButton = null;

	private LedgerEntry currentEntry;
	private JDialog selfDlg;
	private LedgerEntryDao ledgerEntryDao;  //  @jve:decl-index=0:
	private String repayType;  //  @jve:decl-index=0:
	private MainFrame parentWindow;
	
	public RepayDialog() {
//		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(263, 193);
		this.setContentPane(getJContentPane());
		this.selfDlg = this;
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
			jContentPane.add(getContentPanel(), BorderLayout.CENTER);
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
			titleLabel = new JLabel();
			headerPanel = new JPanel();
			headerPanel.setLayout(new GridBagLayout());
			headerPanel.add(titleLabel, new GridBagConstraints());
		}
		return headerPanel;
	}

	/**
	 * This method initializes contentPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getContentPanel() {
		if (contentPanel == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 2;
			gridBagConstraints7.gridy = 3;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridwidth = 2;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			repaysumLabel = new JLabel();
			repaysumLabel.setText(getLocaleMessage("label.repaysum"));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			sumLabel = new JLabel();
			sumLabel.setText(getLocaleMessage("label.sum"));
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			nameLabel = new JLabel();
			nameLabel.setText(getLocaleMessage("label.name"));
			contentPanel = new JPanel();
			contentPanel.setLayout(new GridBagLayout());
			contentPanel.add(nameLabel, gridBagConstraints);
			contentPanel.add(getNameTextField(), gridBagConstraints1);
			contentPanel.add(sumLabel, gridBagConstraints2);
			contentPanel.add(getSumTextField(), gridBagConstraints3);
			contentPanel.add(repaysumLabel, gridBagConstraints4);
			contentPanel.add(getRepaysumTextField(), gridBagConstraints5);
			contentPanel.add(getSaveButton(), gridBagConstraints6);
			contentPanel.add(getCancelButton(), gridBagConstraints7);
		}
		return contentPanel;
	}

	/**
	 * This method initializes nameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField();
		}
		return nameTextField;
	}

	/**
	 * This method initializes sumTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSumTextField() {
		if (sumTextField == null) {
			sumTextField = new JTextField();
		}
		return sumTextField;
	}

	/**
	 * This method initializes repaysumTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRepaysumTextField() {
		if (repaysumTextField == null) {
			repaysumTextField = new JTextField();
		}
		return repaysumTextField;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText(getLocaleMessage("button.save"));
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String repaystr = getRepaysumTextField().getText();
					double repaysum = 0.0;
					try{
						repaysum = Double.parseDouble(repaystr);
						if(repaysum <=0){ 
							JOptionPane.showMessageDialog(selfDlg,getLocaleMessage("alert.repaysum.invalid"));
							return;
						}
						double totalsum = Double.parseDouble(getSumTextField().getText());
						if(repaysum > totalsum){ 
							JOptionPane.showMessageDialog(selfDlg,getLocaleMessage("alert.repaysum.greater.than.total"));
							return;
						}
					}catch(Exception ex){
						ex.printStackTrace();
						JOptionPane.showMessageDialog(selfDlg,getLocaleMessage("alert.repaysum.invalid"));
						return;
					}
					if(repayType.equals(Consts.REPAY_TYPE_INTEREST)){
						ledgerEntryDao.repayInterest(currentEntry, repaysum);
					}else if(repayType.equals(Consts.REPAY_TYPE_PRINCIPAL)){
						ledgerEntryDao.repayPrincipal(currentEntry, repaysum);
					}
					selfDlg.dispose();
					parentWindow.refreshTable();
				}
			});
		}
		return saveButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(getLocaleMessage("button.cancel"));
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selfDlg.dispose();
				}
			});
		}
		return cancelButton;
	}

	public void setLedgerEntry(LedgerEntry e,String repaytype) {
		currentEntry = e;
		repayType = repaytype;
		getNameTextField().setText(currentEntry.getName());
		getRepaysumTextField().setText("");
		if(repayType.equals(Consts.REPAY_TYPE_INTEREST)){
			titleLabel.setText(getLocaleMessage("title.repay.interest"));
			getSumTextField().setText((e.getInterest()+Calculator.calculateInterest(e)-e.getPaidInterest())+"");
		}else if(repayType.equals(Consts.REPAY_TYPE_PRINCIPAL)){
			titleLabel.setText(getLocaleMessage("title.repay.principal"));
			getSumTextField().setText(e.getPrincipal()+"");
		}
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
}  //  @jve:decl-index=0:visual-constraint="10,10"
