package com.example.addresslookup.pojo.model;

import java.io.Serializable;

public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5251234224127182863L;
	
	private String line1 ; 
	private String line2 ; 
	private String townOrCity ; 
	private String country ; 
	private String postalCode ;
	
	public Address() { 
		
	}
	
	public Address(String line1, String line2, String townOrCity,
			String country, String postalCode) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.townOrCity = townOrCity;
		this.country = country;
		this.postalCode = postalCode;
	}

	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getTownOrCity() {
		return townOrCity;
	}
	public void setTownOrCity(String townOrCity) {
		this.townOrCity = townOrCity;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	} 
	
}
