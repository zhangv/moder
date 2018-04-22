package com.modofo.moder;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.modofo.moder.model.LedgerEntry;
import com.modofo.moder.model.Repayment;

public class LedgerEntryDao extends SqlMapClientDaoSupport{
	public List searchByName(LedgerEntry entry) throws SQLException {
		return this.getSqlMapClientTemplate().queryForList("searchbyname", entry);
	}

	public void addEntry(LedgerEntry entry) throws SQLException {
		this.getSqlMapClientTemplate().insert("addEntry", entry);
	}

	public int updateEntry(LedgerEntry entry) throws SQLException {
		return this.getSqlMapClientTemplate().update("updateEntry", entry);
	}
	
	public int deleteEntry(LedgerEntry entry) throws SQLException {
		return this.getSqlMapClientTemplate().delete("deleteEntry",entry);
	}
	
	public void repayInterest(LedgerEntry entry,double repaysum){
		Repayment rp = new Repayment();
		rp.setRepayDate(new Date());
		rp.setRepaysum(repaysum);
		rp.setLedgerId(entry.getId());
		rp.setType(Consts.REPAY_TYPE_INTEREST);
		this.getSqlMapClientTemplate().insert("addRepayment",rp);
	}
	
	@Transactional
	public void repayPrincipal(LedgerEntry entry,double repaysum){
		Repayment rp = new Repayment();
		rp.setRepayDate(new Date());
		rp.setRepaysum(repaysum);
		rp.setLedgerId(entry.getId());
		rp.setType(Consts.REPAY_TYPE_PRINCIPAL);
		//add repayment record
		this.getSqlMapClientTemplate().insert("addRepayment",rp);
		
		LedgerEntry le = entry;
		// update interest
		le.setInterest(le.getInterest()+Calculator.calculateInterest(le));
		//update principal and start date
		le.setPrincipal(le.getPrincipal()-repaysum);
		le.setStartDate(new Date());
		this.getSqlMapClientTemplate().update("updateEntry",le);
	}

	public List getRepayment(LedgerEntry e){
		return this.getSqlMapClientTemplate().queryForList("getRepayment",e);
	}
}
