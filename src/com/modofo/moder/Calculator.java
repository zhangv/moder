package com.modofo.moder;

import java.util.Calendar;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import com.modofo.moder.model.LedgerEntry;

public class Calculator {
	
	public static double calculateInterest(LedgerEntry e) {
		double principal = e.getPrincipal();
		Calendar startdate = Calendar.getInstance();
		startdate.setTime(e.getStartDate());
		Calendar enddate = Calendar.getInstance();
		
		double rate = e.getRate();
		rate/=100;
		String period = e.getPeriod();
		boolean compound = e.getCompound().equalsIgnoreCase("Y");
		return Calculator.calculateInterest(principal, rate, startdate, enddate, compound, period);
	}
	public static double calculateInterest(double principal,double rate,Calendar startcal,Calendar endcal,boolean compound,String period){
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
}
