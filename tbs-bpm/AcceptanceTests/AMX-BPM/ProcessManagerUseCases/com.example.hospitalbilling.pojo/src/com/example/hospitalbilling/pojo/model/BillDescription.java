package com.example.hospitalbilling.pojo.model;

import java.io.Serializable;
import java.util.Date;

public class BillDescription implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1739867239863157390L;
	private Date billDate;
	private double billAmount;
	private String billDescription;
	private Date paymentDueDate;
	
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	public String getBillDescription() {
		return billDescription;
	}
	public void setBillDescription(String billDescription) {
		this.billDescription = billDescription;
	}
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	
}
