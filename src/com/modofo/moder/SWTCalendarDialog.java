package com.modofo.moder;import org.eclipse.swt.SWT;import org.eclipse.swt.layout.RowLayout;import org.eclipse.swt.widgets.Button;import org.eclipse.swt.widgets.Display;import org.eclipse.swt.widgets.Shell;import org.vafada.swtcalendar.SWTCalendar;import org.vafada.swtcalendar.SWTCalendarListener;import java.util.Calendar;import java.util.Date;public class SWTCalendarDialog {	private Shell shell;		private SWTCalendar swtcal;	private Display display;	public SWTCalendarDialog(Display display, Button button) {		this.display = display;		shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.CLOSE);		shell.setLayout(new RowLayout());		if (button != null) {			//FIXME hardcode, to adjust the position			shell.setLocation(button.getLocation().x+button.getSize().x					,button.getLocation().y+button.getSize().y);		}		swtcal = new SWTCalendar(shell);	}	public void close() {		shell.dispose();	}	public void open() {		shell.pack();		shell.open();		while (!shell.isDisposed()) {			if (!display.readAndDispatch())				display.sleep();		}	}	public Calendar getCalendar() {		return swtcal.getCalendar();	}	public void setDate(Date date) {		Calendar calendar = Calendar.getInstance();		calendar.setTime(date);		swtcal.setCalendar(calendar);	}	public void addDateChangedListener(SWTCalendarListener listener) {		swtcal.addSWTCalendarListener(listener);	}}