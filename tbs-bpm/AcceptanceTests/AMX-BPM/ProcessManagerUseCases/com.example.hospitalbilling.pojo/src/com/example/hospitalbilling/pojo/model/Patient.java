package com.example.hospitalbilling.pojo.model;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2882580251171764113L;
	private String insuranceId5;
	private String name;
	private Date dob;
	private boolean diabetic5;
	
	public String getinsuranceId5() {
		return insuranceId5;
	}
	public void setinsuranceId5(String insuranceId5) {
		this.insuranceId5 = insuranceId5;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public boolean isdiabetic5() {
		return diabetic5;
	}
	public void setdiabetic5(boolean diabetic5) {
		this.diabetic5 = diabetic5;
	}
}
