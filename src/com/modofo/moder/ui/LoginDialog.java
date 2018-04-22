package com.modofo.moder.ui;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Dimension;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel headerPanel = null;

	private JPanel contentPanel = null;

	private JLabel titleLabel = null;

	private JLabel nameLabel = null;

	private JTextField nameTextField = null;

	private JLabel pwdLabel = null;

	private JPasswordField passwordField = null;

	private JButton okButton = null;

	private JButton Button = null;

	/**
	 * @param owner
	 */
	public LoginDialog(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(219, 149);
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
			titleLabel.setText("Login");
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
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.gridy = 2;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			pwdLabel = new JLabel();
			pwdLabel.setText("password");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridx = 1;
			nameLabel = new JLabel();
			nameLabel.setText("username");
			contentPanel = new JPanel();
			contentPanel.setLayout(new GridBagLayout());
			contentPanel.add(nameLabel, new GridBagConstraints());
			contentPanel.add(getNameTextField(), gridBagConstraints);
			contentPanel.add(pwdLabel, gridBagConstraints1);
			contentPanel.add(getPasswordField(), gridBagConstraints2);
			contentPanel.add(getOkButton(), gridBagConstraints3);
			contentPanel.add(getButton(), gridBagConstraints4);
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
	 * This method initializes passwordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
		}
		return passwordField;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("ok");
			okButton.setActionCommand("");
		}
		return okButton;
	}

	/**
	 * This method initializes Button	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButton() {
		if (Button == null) {
			Button = new JButton();
			Button.setText("exit");
			Button.setActionCommand("");
		}
		return Button;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
