package com.modofo.moder.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.modofo.moder.LedgerEntryDao;
import com.modofo.moder.model.LedgerEntry;
import com.modofo.util.BaseJDialog;
import com.toedter.calendar.JDateChooser;

public class AddEntryDialog extends BaseJDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel headerPanel = null;

	private JPanel contentPanel = null;

	private JLabel titleLabel = null;

	private JLabel nameLabel = null;

	private JTextField nameTextField = null;

	private JLabel principalLabel = null;

	private JTextField principalTextField = null;

	private JLabel startdateLabel = null;

	private JDateChooser startDateChooser = null;

	private JLabel periodLabel = null;

	private JComboBox periodComboBox = null;

	private JLabel compoundLabel = null;

	private JCheckBox compoundCheckBox = null;

	private JButton saveButton = null;

	private JButton cancelButton = null;
	
	private LedgerEntryDao ledgerEntryDao;  //  @jve:decl-index=0:
	private MainFrame parentWindow;
	private JDialog selfDlg;

	private LedgerEntry currentEntry;  //  @jve:decl-index=0:

	private JLabel rateLabel = null;

	private JTextField rateTextField = null;
	private String[] periodLabels;
	
	public AddEntryDialog() {
		//use init-method in spring bean def
		//initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void 
	 */
	private void initialize() {
		this.setSize(250, 250);
		this.setContentPane(getJContentPane());
		this.selfDlg = this;
		periodLabels = new String[LedgerEntry.PERIOD.length];
		periodLabels[0] = getLocaleMessage("option.monthly");
		periodLabels[1] = getLocaleMessage("option.yearly");
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
			headerPanel = new JPanel();
			headerPanel.setLayout(new BoxLayout(getHeaderPanel(), BoxLayout.X_AXIS));
			titleLabel = new JLabel();
			headerPanel.add(titleLabel, null);
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
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 2;
			rateLabel = new JLabel();
			rateLabel.setText(getLocaleMessage("label.rate"));
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.gridy = 6;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 6;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridwidth = 2;
			gridBagConstraints9.gridy = 5;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 5;
			compoundLabel = new JLabel();
			compoundLabel.setText(getLocaleMessage("label.compound"));
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridy = 4;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.gridwidth = 2;
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 4;
			periodLabel = new JLabel();
			periodLabel.setText(getLocaleMessage("label.period"));
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridwidth = 2;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 3;
			startdateLabel = new JLabel();
			startdateLabel.setText(getLocaleMessage("label.startdate"));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			principalLabel = new JLabel();
			principalLabel.setText(getLocaleMessage("label.principal"));
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
			contentPanel.add(principalLabel, gridBagConstraints2);
			contentPanel.add(getPrincipalTextField(), gridBagConstraints3);
			contentPanel.add(startdateLabel, gridBagConstraints4);
			contentPanel.add(getStartDateChooser(), gridBagConstraints5);
			contentPanel.add(periodLabel, gridBagConstraints6);
			contentPanel.add(getPeriodComboBox(), gridBagConstraints7);
			contentPanel.add(compoundLabel, gridBagConstraints8);
			contentPanel.add(getCompoundCheckBox(), gridBagConstraints9);
			contentPanel.add(getSaveButton(), gridBagConstraints10);
			contentPanel.add(getCancelButton(), gridBagConstraints11);
			contentPanel.add(rateLabel, gridBagConstraints12);
			contentPanel.add(getRateTextField(), gridBagConstraints21);
			
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
			nameTextField.setColumns(30);
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
			
		}
		return principalTextField;
	}

	/**
	 * This method initializes startdateTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JDateChooser getStartDateChooser() {
		if (startDateChooser == null) {
			startDateChooser = new JDateChooser(new Date());
		}
		return startDateChooser;
	}

	/**
	 * This method initializes periodComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getPeriodComboBox() {
		if (periodComboBox == null) {
			periodComboBox = new JComboBox(new String[]{getLocaleMessage("option.monthly"),getLocaleMessage("option.yearly")});
			periodComboBox.setSelectedIndex(0);
		}
		return periodComboBox;
	}

	/**
	 * This method initializes compoundCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCompoundCheckBox() {
		if (compoundCheckBox == null) {
			compoundCheckBox = new JCheckBox();
		}
		return compoundCheckBox;
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
					LedgerEntry m = validateInput();
					if(m!=null){
						try {
							if(m.getId()==0)
								ledgerEntryDao.addEntry(m);
							else
								ledgerEntryDao.updateEntry(m);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						selfDlg.dispose();
						parentWindow.refreshTable();
					}
				}
			});
		}
		return saveButton;
	}
	
	private LedgerEntry validateInput(){
		String vName = getNameTextField().getText().trim();
		String vprcpl = getPrincipalTextField().getText().trim();
		String vrate = getRateTextField().getText().trim();
		Date vstartdt = getStartDateChooser().getDate();
		
		String vprd = LedgerEntry.PERIOD[getPeriodComboBox().getSelectedIndex()];
		
		boolean vcmpd = getCompoundCheckBox().isSelected();
		
		if(currentEntry == null) currentEntry = new LedgerEntry();
		LedgerEntry m = currentEntry;
		m.setPeriod(vprd);
		
		if(vcmpd){
			m.setCompound("Y");
		}else{
			m.setCompound("N");
		}
		
		StringBuffer sb = new StringBuffer();
		if(vName == null || "".equals(vName)){
			sb.append(getLocaleMessage("alert.name.required")).append("\n");
		}else{
			m.setName(vName);
		}
		if(vprcpl == null || "".equals(vprcpl)){
			sb.append(getLocaleMessage("alert.principal.required")).append("\n");
		}else{
			double vpr = 0.0;
			try{
				vpr = Double.parseDouble(vprcpl);
			}catch(Exception e){
				sb.append(getLocaleMessage("alert.principal.invalid")).append("\n");
			}
			m.setPrincipal(vpr);
		}
		
		//validate rate
		if(vrate == null || "".equals(vrate)){
			sb.append(getLocaleMessage("alert.rate.required")).append("\n");
		}else{
			double vrt = 0.0;
			try{
				vrt = Double.parseDouble(vrate);
			}catch(Exception e){
				sb.append(getLocaleMessage("alert.rate.invalid")).append("\n");
			}
			m.setRate(vrt);
		}
		
		if(vstartdt == null){
			sb.append(getLocaleMessage("alert.startdate.invalid")).append("\n");
		}else{
			m.setStartDate(vstartdt);
		}
		
		if(sb.length() > 0){
			JOptionPane.showMessageDialog(selfDlg,sb.toString());
			return null;
		}else{
			return m;
		}
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

	public void setLedgerEntry(LedgerEntry e) {
		if(e == null){
			titleLabel.setText(getLocaleMessage("title.addentry"));
			currentEntry = new LedgerEntry();
		}
		else {
			titleLabel.setText(getLocaleMessage("title.editentry"));
			currentEntry = e;
//			getNameTextField().setEditable(false);
//			getPrincipalTextField().setEditable(false);
//			getStartDateChooser().setEnabled(false);
//			getCompoundCheckBox().setEnabled(false);
//			getRateTextField().setEditable(false);
//			getPeriodComboBox().setEnabled(false);
		}
		getNameTextField().setText(currentEntry.getName());
		getPrincipalTextField().setText(currentEntry.getPrincipal()+"");
		getRateTextField().setText(currentEntry.getRate()+"");
		getStartDateChooser().setDate(currentEntry.getStartDate());
		for(int i=0;i<periodLabels.length;i++){
			if(currentEntry.getPeriod().equals(LedgerEntry.PERIOD[i])){
				getPeriodComboBox().setSelectedIndex(i);
				break;
			}
		}
		getCompoundCheckBox().setSelected(currentEntry.getCompound().equalsIgnoreCase("y"));
	}

	/**
	 * This method initializes rateTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRateTextField() {
		if (rateTextField == null) {
			rateTextField = new JTextField();
		}
		return rateTextField;
	}

}  //  @jve:decl-index=0:visual-constraint="20,14"
