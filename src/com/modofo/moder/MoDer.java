package com.modofo.moder;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.vafada.swtcalendar.SWTCalendarEvent;
import org.vafada.swtcalendar.SWTCalendarListener;


public class MoDer {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Menu menuBar = null;
	private Label principallabel = null;
	private Text principaltext = null;
	private Label startdatelabel = null;
	private Text startdatetext = null;
	private Button startdatebutton = null;
	private Label ratelabel = null;
	private Text ratetext = null;
	private Label enddatelabel = null;
	private Text enddatetext = null;
	private Button enddatebutton = null;
	private Label compoundlabel = null;
	private Button compoundcheckBox = null;
	private Label periodlabel = null;
	private Combo periodcombo = null;
	private Text interesttext = null;
	private Text totaltext = null;
	private Calendar startcal=null,endcal = null;
	private ModerListener listener  =  new ModerListener();
	/**
	 * This method initializes sShell
	 */
	public void createSShell() {
		GridData gridData = new GridData();
		gridData.verticalSpan = 0;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		sShell = new Shell();
		sShell.setText("GaoDan");
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(495, 239));
		principallabel = new Label(sShell, SWT.NONE);
		principallabel.setText("Principal sum");
		principaltext = new Text(sShell, SWT.BORDER);
		principaltext.addModifyListener(listener);
		Label filler1 = new Label(sShell, SWT.NONE);
		
		startdatelabel = new Label(sShell, SWT.NONE);
		startdatelabel.setText("Start date");
		startdatetext = new Text(sShell, SWT.BORDER);
		startdatetext.setEditable(false);
		startdatetext.addModifyListener(listener);
		
		startdatebutton = new Button(sShell, SWT.NONE);
		startdatebutton.setText("select");
		startdatebutton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
            	final SWTCalendarDialog c = new SWTCalendarDialog(sShell.getDisplay(),startdatebutton);
				
		        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		        startcal = c.getCalendar();
		        startdatetext.setText(df.format(c.getCalendar().getTime()));
		        c.addDateChangedListener(new SWTCalendarListener() {
			            public void dateChanged(SWTCalendarEvent calendarEvent) {
			                Locale _locale = Locale.getDefault();
			                DateFormat df2 = DateFormat.getDateInstance(DateFormat.SHORT, _locale);
			                startcal = c.getCalendar();
			                startdatetext.setText(df2.format(calendarEvent.getCalendar().getTime()));
			            }
		        	});
		        c.open();
			}
		});

		
		ratelabel = new Label(sShell, SWT.NONE);
		ratelabel.setText("Interest rate(%)");
		ratetext = new Text(sShell, SWT.BORDER);
		ratetext.addModifyListener(listener);
		
		Label filler2 = new Label(sShell, SWT.NONE);
		
		enddatelabel = new Label(sShell, SWT.NONE);
		enddatelabel.setText("End date");
		enddatetext = new Text(sShell, SWT.BORDER);
		enddatetext.setEditable(false);
		enddatetext.addModifyListener(listener);
		
		enddatebutton = new Button(sShell, SWT.NONE);
		enddatebutton.setText("select");
		enddatebutton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
				final SWTCalendarDialog c = new SWTCalendarDialog(sShell.getDisplay(),enddatebutton);
				
		        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		        endcal = c.getCalendar();
		        enddatetext.setText(df.format(c.getCalendar().getTime()));
		        c.addDateChangedListener(new SWTCalendarListener() {
			            public void dateChanged(SWTCalendarEvent calendarEvent) {
			                Locale _locale = Locale.getDefault();
			                DateFormat df2 = DateFormat.getDateInstance(DateFormat.SHORT, _locale);
			                endcal = c.getCalendar();
			                enddatetext.setText(df2.format(calendarEvent.getCalendar().getTime()));
			            }
		        	});
		        c.open();
		  	}
		});
		
		compoundlabel = new Label(sShell, SWT.NONE);
		compoundlabel.setText("Compound");
		compoundcheckBox = new Button(sShell, SWT.CHECK);
		compoundcheckBox.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				onEvent();
			}

			public void widgetSelected(SelectionEvent e) {
				onEvent();
			}
		});

		Label filler4 = new Label(sShell, SWT.NONE);
		
		periodlabel = new Label(sShell, SWT.NONE);
		periodlabel.setText("Period");
		createPeriodcombo();
		Label filler5 = new Label(sShell, SWT.NONE);
		
//		menuBar = new Menu(sShell, SWT.BAR);
//		MenuItem push = new MenuItem(menuBar, SWT.PUSH);
//		push.setText("exit");
//		sShell.setMenuBar(menuBar);
		
		Label filler6 = new Label(sShell, SWT.NONE);
		interesttext = new Text(sShell, SWT.BORDER);
		interesttext.setEditable(false);
		totaltext = new Text(sShell, SWT.BORDER);
		totaltext.setEditable(false);
		
		sShell.pack();
	}
	/**
	 * This method initializes periodcombo	
	 *
	 */
	private void createPeriodcombo() {
		periodcombo = new Combo(sShell, SWT.NONE);
		periodcombo.setItems(new String[]{"monthly","yearly","daily"});
		periodcombo.select(0);
		periodcombo.addModifyListener(listener);
	}

	private class ModerListener implements ModifyListener{
		public void modifyText(ModifyEvent e) {
			onEvent();
		}
		
	}
	
	private void onEvent(){
		if(principaltext == null || startdatetext==null || ratetext == null ||enddatetext==null){
			return;
		}
		
		if(principaltext.getText().equals("") || startdatetext.getText().equals("") 
				|| ratetext.getText().equals("") ||enddatetext.getText().equals("")){
			return;
		}
		
		String str = principaltext.getText();
		double principal = 0.0;
		try{
			principal = Double.parseDouble(str);
		}catch (Exception ex){
			principaltext.setText("");
			return;
		}
		double rate = 0.0;
		str = ratetext.getText();
		try{
			rate = Double.parseDouble(str);
			rate /= 100;
		}catch (Exception ex){
			ratetext.setText("");
			return;
		}
		str = startdatetext.getText();
		
		boolean compound = compoundcheckBox.getSelection();
		String period = periodcombo.getItem(periodcombo.getSelectionIndex());
		
		double interest = calculate(principal,rate,startcal,endcal,compound,period);
		interesttext.setText(interest+"");
		totaltext.setText((principal+interest)+"");
	}
	public static double calculate(double principal,double rate,Calendar startcal,Calendar endcal,boolean compound,String period){
		//get cycle count (startdate-enddate)/ year,month,day
		int years = endcal.get(Calendar.YEAR) - startcal.get(Calendar.YEAR);
		if(years<0) return 0.0;
		
		int months = endcal.get(Calendar.MONTH) - startcal.get(Calendar.MONTH);
		if(months < 0){
			years --;
			months +=12;
		}
		int days = endcal.get(Calendar.DATE) - startcal.get(Calendar.DATE);
		if(days <0){//say Jan 10 to Mar 1 ,should be 2months and 20 days
			months --;
			days = 30+days;
		}
		System.out.println("years:"+years+",month:"+months+",days:"+days);
		
		double cycles = 0;
		if(period.equals("yearly")) cycles = years;
		else if(period.equals("monthly")) cycles = (double)(years*12) + (double)months+((double)days/(double)30);
		else if(period.equals("daily")) cycles = years*365 + months*30+days;
		System.out.println("cycles:"+cycles+",period:"+period);
		
		double sum = 0.0;
		if(compound){
			sum = principal;
			for(int i=0;i<cycles;i++){
				sum = sum*(1+rate);
			}
			sum -= principal;
		}else{
			sum = principal*rate*cycles;
		}
		System.out.println("result:"+sum);
		return sum;
	}
	
	public static void main(String[] args) {
		Display display = Display.getDefault();
		
		MoDer main = new MoDer();
		main.createSShell();
		main.sShell.open();

		while (!main.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	
	public static void main0(String[] args) {
		double test = 0.0;
		Calendar startcal = Calendar.getInstance();
		startcal.roll(Calendar.YEAR, 1);
		startcal.roll(Calendar.MONTH, 1);
		startcal.roll(Calendar.DAY_OF_MONTH, -9);
		Calendar endcal = Calendar.getInstance();
		test = MoDer.calculate(100, 0.1, startcal, endcal, false, "monthly"); //230
		test = MoDer.calculate(100, 0.1, startcal, endcal, true, "monthly"); //345
		
		startcal = Calendar.getInstance();
		startcal.roll(Calendar.MONTH, 1);
		endcal = Calendar.getInstance();
		test = MoDer.calculate(100, 0.1, startcal, endcal, false, "monthly"); //110
		
		startcal = Calendar.getInstance();
		startcal.roll(Calendar.YEAR, 1);
		startcal.roll(Calendar.MONTH, 1);
		endcal = Calendar.getInstance();
		test = MoDer.calculate(1000000, 0.02, startcal, endcal, false, "monthly"); 
		test = MoDer.calculate(1000000, 0.02, startcal, endcal, true, "monthly");
		
		System.out.println(  ((double)3) / ((double)5));
	}
	public void open(Display display) {
		sShell.open();
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();		
	}
	
	
}
