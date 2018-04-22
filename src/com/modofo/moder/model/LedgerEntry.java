package com.modofo.moder.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "t_ledger_entry")
public class LedgerEntry {
	
	final public static String[] PERIOD = {"monthly","yearly"};
	
	@Id
	@GeneratedValue
	private int id;
	
	@Basic
	private String name;
	
	@Basic
	private double principal;
	
	@Basic
	private double rate;
	
	@Temporal(TemporalType.TIME)
	private Date startDate;
	
	@Basic
	private String period = "monthly";
	
	@Basic
	private String compound="N";
	
	@Basic
	private int status;
	
	@Basic
	private double interest;
	
	@Basic
	private double paidInterest;
	
	public String getCompound() {
		return compound;
	}
	public void setCompound(String compound) {
		this.compound = compound;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public double getPrincipal() {
		return principal;
	}
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getPaidInterest() {
		return paidInterest;
	}
	public void setPaidInterest(double paidInterest) {
		this.paidInterest = paidInterest;
	}
	
}
