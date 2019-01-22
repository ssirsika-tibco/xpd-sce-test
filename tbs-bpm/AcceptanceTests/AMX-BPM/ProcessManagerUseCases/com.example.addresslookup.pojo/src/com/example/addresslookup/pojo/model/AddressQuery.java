package com.example.addresslookup.pojo.model;

import java.io.Serializable;

public class AddressQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3598369321532448210L;
	
	private String postalCode ;

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	} 
	
	
}
