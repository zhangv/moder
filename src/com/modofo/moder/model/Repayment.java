package com.modofo.moder.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Repayment {
	@Id @GeneratedValue
	private int id;
	
	@Basic private int ledgerId;
	
	@Basic private double repaysum;
	
	@Temporal(TemporalType.TIME) private Date repayDate;
	
	@Basic private String type; //principal / interest

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLedgerId() {
		return ledgerId;
	}

	public void setLedgerId(int ledgerId) {
		this.ledgerId = ledgerId;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public double getRepaysum() {
		return repaysum;
	}

	public void setRepaysum(double repaysum) {
		this.repaysum = repaysum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
