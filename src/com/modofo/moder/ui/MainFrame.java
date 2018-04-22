package com.modofo.moder.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import org.eclipse.swt.widgets.Display;

import com.modofo.moder.Calculator;
import com.modofo.moder.Consts;
import com.modofo.moder.LedgerEntryDao;
import com.modofo.moder.MoDer;
import com.modofo.moder.model.LedgerEntry;
import com.modofo.util.BaseJFrame;


public class MainFrame extends BaseJFrame {

	private static final long serialVersionUID = 1L;
	
	private MainFrame mainFrame = null;
	
	private JPanel jContentPane = null;

	private JPanel headerPanel = null;

	private JPanel contentPanel = null;

	private JScrollPane resultScrollPane = null;

	private JTable resultTable = null;
	
	private List searchResult = null;
	
	private JLabel nameLabel = null;

	private JTextField nameTextField = null;

	private JButton searchButton = null;

	private JMenu fileMenu;

	private JMenuItem exitMenuItem ;

	private JPopupMenu popupMenu;

	private JToolBar toolBar = null;  
	
	private AddEntryDialog addEntryDlg;
	private RepayDialog repayDlg;
	private RepayHistoryDialog repayHistoryDlg;
	
	private LedgerEntryDao ledgerEntryDao;
	private LedgerEntry selectedEntry;  //  @jve:decl-index=0:
	static private final int WINDOW_HEIGHT = 500;
	static private final int WINDOW_WIDTH = 650;
	private String[] periodLabels;
	private double principalSum,interestSum,interestPaidSum;
	
	public LedgerEntryDao getLedgerEntryDao() {
		return ledgerEntryDao;
	}

	public void setLedgerEntryDao(LedgerEntryDao ledgerEntryDao) {
		this.ledgerEntryDao = ledgerEntryDao;
	}

	public AddEntryDialog getAddEntryDlg() {
		return addEntryDlg;
	}

	public void setAddEntryDlg(AddEntryDialog addEntryDlg) {
		this.addEntryDlg = addEntryDlg;
	}

	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle(getLocaleMessage("title"));
		this.useAlloyTheme();
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame = this;
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
			
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(getHeaderPanel(), BorderLayout.NORTH);
			mainPanel.add(getContentPanel(), BorderLayout.CENTER);
			
			jContentPane.add(getToolBar(), BorderLayout.NORTH);
			jContentPane.add(mainPanel, BorderLayout.CENTER);
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
			nameLabel = new JLabel();
			nameLabel.setText(getLocaleMessage("label.search.name"));
			headerPanel = new JPanel();
			headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
			headerPanel.add(nameLabel, null);
			headerPanel.add(getNameTextField(), null);
			headerPanel.add(getSearchButton(), null);
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
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			contentPanel = new JPanel();
			contentPanel.setLayout(new GridBagLayout());
			contentPanel.add(getResultScrollPane(), gridBagConstraints);
		}
		return contentPanel;
	}

	/**
	 * This method initializes resultScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getResultScrollPane() {
		if (resultScrollPane == null) {
			resultScrollPane = new JScrollPane();
			resultScrollPane.setViewportView(getResultTable());
		}
		return resultScrollPane;
	}

	/**
	 * This method initializes resultTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getResultTable() {
		if (resultTable == null) {
			DefaultTableColumnModel cm = new DefaultTableColumnModel();
			
			String[] tableHeaders = new String[]{
					getLocaleMessage("table.header.name"),
					getLocaleMessage("table.header.principal"),
					getLocaleMessage("table.header.rate"),
					getLocaleMessage("table.header.startdate"),
					getLocaleMessage("table.header.period"),
					getLocaleMessage("table.header.compound"),
					getLocaleMessage("table.header.interest"),
					getLocaleMessage("table.header.interest.paid"),
					getLocaleMessage("table.header.interest.unpaid")
			};
			int[] columnwidths = {80,80,80,100,80,80,120,120,150};
			
			TableColumn tc =null;
			for(int i=0;i<tableHeaders.length;i++){
				tc = new TableColumn(i);
				tc.setHeaderValue(tableHeaders[i]);
				tc.setMaxWidth(columnwidths[i]);
				cm.addColumn(tc);
			}
			
			resultTable = new JTable(new ResultTableModel(), cm);

			resultTable.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					int rowId = resultTable.getSelectedRow();
					if (rowId == -1) {
						JOptionPane.showMessageDialog(mainFrame, getLocaleMessage("alert.pls.select.record"));
					} else {
						if (e.getButton() == MouseEvent.BUTTON3) { //right click
							Component c = e.getComponent();
							mainFrame.getPopupMenu()
									.show(c, e.getX(), e.getY());
						} else if (e.getButton() == MouseEvent.BUTTON1) {
							if(rowId==searchResult.size() ){ //sum row selected
								selectedEntry = null;
							}else{
								selectedEntry = (LedgerEntry) searchResult.get(rowId);
							}
						}
					}
				}
			});
		}
		return resultTable;
	}
	
	private JPopupMenu getPopupMenu() {
		if (popupMenu == null) {
			popupMenu = new JPopupMenu();
			
			newPopupMenuItem(popupMenu,new EditAction());
			newPopupMenuItem(popupMenu,new DeleteAction());
			newPopupMenuItem(popupMenu,new RepayInterestAction());
			newPopupMenuItem(popupMenu,new RepayPrincipalAction());
			
		}
		return popupMenu;
	}
	
	private void newPopupMenuItem(JPopupMenu menu, Action action) {
		JMenuItem mitem = new JMenuItem(action.getValue(Action.NAME).toString());
		mitem.setAction(action);
		menu.add(mitem);
	}

	class ResultTableModel extends AbstractTableModel {
		public int getRowCount() {
			if (searchResult == null)
				return 0;
			return searchResult.size()+1;
		}

		public int getColumnCount() {
			return 8;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if (searchResult == null || searchResult.size() == 0)
				return null;
			if(rowIndex == searchResult.size()){ // last row for sum
				switch (columnIndex) {
				case 0: return getLocaleMessage("table.header.sum");
				case 1:	return principalSum; //TODO principal sum
				case 2: return "";
				case 3: return "";
				case 4: return "";
				case 5: return "";
				case 6: return interestSum; //TODO interest sum
				case 7: return interestPaidSum; //TODO interest paid sum
				case 8: return (interestSum - interestPaidSum);//TODO interest unpaid sum 
				default:
					return "";
				}
			}
			LedgerEntry entry = (LedgerEntry) searchResult.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return entry.getName();
			case 1:
				return entry.getPrincipal();
			case 2:
				return entry.getRate();
			case 3:{
				Date d = entry.getStartDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(d);
			}
			case 4:{
				String pd = entry.getPeriod();
				for(int i=0;i<LedgerEntry.PERIOD.length;i++){
					if(pd.equals(LedgerEntry.PERIOD[i])) return periodLabels[i];
				}
				return "";
			}
			case 5:
				return entry.getCompound();
			case 6:
				return Calculator.calculateInterest(entry)+entry.getInterest();
			case 7:
				return entry.getPaidInterest();
			case 8:
				return Calculator.calculateInterest(entry)+entry.getInterest()-entry.getPaidInterest();
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
			nameTextField.setColumns(20);
		}
		return nameTextField;
	}

	/**
	 * This method initializes searchButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = new JButton();
			searchButton.setText(getLocaleMessage("button.search"));
			searchButton.setAction(new SearchAction());
		}
		return searchButton;
	}

	/**
	 * This method initializes toolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	static Insets zeroInsets = new Insets(1, 1, 1, 1);
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			newButton(toolBar,new AddAction());
			newButton(toolBar,new EditAction());
			newButton(toolBar,new DeleteAction());
			newButton(toolBar,new RepayInterestAction());
			newButton(toolBar,new RepayPrincipalAction());
			newButton(toolBar,new RepayHistoryAction());
			
			newButton(toolBar,new ExitAction());
		}
		return toolBar;
	}
	private void newButton(JToolBar toolBar,Action action){
		JButton btn = new JButton();
		btn.setMargin(zeroInsets);
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setAction(action);
		toolBar.add(btn);
	}
	
	protected Icon getIcon(String iconfile) {
		ImageIcon icon = new ImageIcon(MainFrame.this.getClass().getResource("imgs/"+iconfile));
		if(icon.getIconHeight()>32){
			Image img = icon.getImage();
			BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			g.drawImage(img, 0, 0, 32,32, null);
			ImageIcon newIcon = new ImageIcon(bi);
			return newIcon;
		}else return icon;
	}
	
	class AddAction extends AbstractAction {
		public AddAction() {
			super(getLocaleMessage("button.add"), getIcon("new.png"));
		}

		public void actionPerformed(ActionEvent e) {
			setLocation(mainFrame, addEntryDlg);
			addEntryDlg.setLedgerEntry(null);
			addEntryDlg.setVisible(true);
		}
	}
	
	class SearchAction extends AbstractAction {
		public SearchAction() {
			super(getLocaleMessage("button.search"), getIcon("search.png"));
		}

		public void actionPerformed(ActionEvent e) {
			refreshTable();
		}
	}
	
	class EditAction extends AbstractAction {
		public EditAction() {
			super(getLocaleMessage("button.edit"), getIcon("edit.png"));
		}

		public void actionPerformed(ActionEvent e) {
			if (selectedEntry == null) {
				JOptionPane.showMessageDialog(mainFrame, getLocaleMessage("alert.pls.select.record"));
				return;
			}
			setLocation(mainFrame, addEntryDlg);
			addEntryDlg.setLedgerEntry(selectedEntry);
			addEntryDlg.setVisible(true);
		}
	}
	
	class RepayInterestAction extends AbstractAction {
		public RepayInterestAction() {
			super(getLocaleMessage("button.repay.interest"), getIcon("repayinterest.png"));
		}

		public void actionPerformed(ActionEvent e) {
			if (selectedEntry == null) {
				JOptionPane.showMessageDialog(mainFrame, getLocaleMessage("alert.pls.select.record"));
				return;
			}
			setLocation(mainFrame, repayDlg);
			repayDlg.setLedgerEntry(selectedEntry,Consts.REPAY_TYPE_INTEREST);
			repayDlg.setVisible(true);
		}
	}
	
	class RepayPrincipalAction extends AbstractAction {
		public RepayPrincipalAction() {
			super(getLocaleMessage("button.repay.principal"), getIcon("repayprincipal.png"));
		}

		public void actionPerformed(ActionEvent e) {
			if (selectedEntry == null) {
				JOptionPane.showMessageDialog(mainFrame, getLocaleMessage("alert.pls.select.record"));
				return;
			}
			setLocation(mainFrame, repayDlg);
			repayDlg.setLedgerEntry(selectedEntry,Consts.REPAY_TYPE_PRINCIPAL);
			repayDlg.setVisible(true);
		}
	}
	class RepayHistoryAction extends AbstractAction {
		public RepayHistoryAction() {
			super(getLocaleMessage("button.repay.history"), getIcon("repayhistory.png"));
		}

		public void actionPerformed(ActionEvent e) {
			if (selectedEntry == null) {
				JOptionPane.showMessageDialog(mainFrame, getLocaleMessage("alert.pls.select.record"));
				return;
			}
			setLocation(mainFrame, repayHistoryDlg);
			repayHistoryDlg.setLedgerEntry(selectedEntry);
			repayHistoryDlg.setVisible(true);
		}
	}
	
	class OpenCalcAction extends AbstractAction {
		public OpenCalcAction() {
			super(getLocaleMessage("button.calculator"), getIcon("calculator.png"));
		}
		
		// cannot mix with swing, will stuck the swing ui
		public void actionPerformed(ActionEvent e) {
			Display display = Display.getDefault();
			
			MoDer main = new MoDer();
			main.createSShell();
			main.open(display);
		}
	}
	
	
	class DeleteAction extends AbstractAction{
		public DeleteAction() {
			super(getLocaleMessage("button.delete"), getIcon("del.png"));
		}

		public void actionPerformed(ActionEvent e) {
			if (selectedEntry == null) {
				JOptionPane.showMessageDialog(mainFrame, getLocaleMessage("alert.pls.select.record"));
				return;
			}
			try {
				int cnfrm = JOptionPane.showConfirmDialog(mainFrame, getLocaleMessage("alert.confirm.delete"));
				if (cnfrm != JOptionPane.OK_OPTION)
					return;

				int rv = ledgerEntryDao.deleteEntry(selectedEntry);
				if (rv == 1) {
					JOptionPane.showMessageDialog(mainFrame,getLocaleMessage("alert.delete.success"));
					refreshTable();
				} else {
					JOptionPane.showMessageDialog(mainFrame, getLocaleMessage("alert.delete.fail"));
				}
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(mainFrame, getLocaleMessage("alert.delete.fail"));
				e1.printStackTrace();
			}

		}
	}
	class ExitAction extends AbstractAction {
		public ExitAction() {
			super(getLocaleMessage("button.exit"), getIcon("exit.png"));
		}

		public void actionPerformed(ActionEvent e) {
			int cnfrm = JOptionPane.showConfirmDialog(mainFrame, getLocaleMessage("alert.confirm.exit"));
			if (cnfrm != JOptionPane.OK_OPTION)
				return;
			mainFrame.dispose();
		}
	}
	public void refreshTable() {
		String inputName = nameTextField.getText();
		LedgerEntry e = new LedgerEntry();
		e.setName(inputName);
		try {
			searchResult = ledgerEntryDao.searchByName(e);
			resultTable.updateUI();
			resultTable.clearSelection();
			selectedEntry = null;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//sum
		principalSum = 0;
		interestSum = 0;
		interestPaidSum = 0;
		for(Object obj:searchResult){
			LedgerEntry le = (LedgerEntry)obj;
			principalSum +=le.getPrincipal();
			interestSum += (Calculator.calculateInterest(le)+le.getInterest());
			interestPaidSum +=le.getPaidInterest();
		}
	}

	public RepayDialog getRepayDlg() {
		return repayDlg;
	}
	public void setRepayDlg(RepayDialog repayDlg) {
		this.repayDlg = repayDlg;
	}

	public RepayHistoryDialog getRepayHistoryDlg() {
		return repayHistoryDlg;
	}

	public void setRepayHistoryDlg(RepayHistoryDialog repayHistoryDlg) {
		this.repayHistoryDlg = repayHistoryDlg;
	}

}
