package com.example.addresslookup.pojo2;

import com.example.addresslookup.lib.WrapLog4j;


public class AddressService {

	
	public String lookup(String postalCode) { 
		WrapLog4j.info("Looking up address for: " + postalCode) ; 
		
		StringBuilder addr = new StringBuilder() ; 

		if ("SN2 8BL".equals(postalCode)) {
			WrapLog4j.info("Determine address is Swindon office");
			addr.append("4, Apple Walk, ");
			addr.append("Kembrey Park, ");
			addr.append("Swindon. ");
//			addr.append("UK");
			addr.append(postalCode);
		} else if ("94304".equals(postalCode)) {
			WrapLog4j.info("Determine address is Palo Alto office");
			addr.append("3303 Hillview Avenue, ");
			addr.append("Palo Alto. ");
			addr.append("CA ");
//			addr.append(query.getCountry());
			addr.append(postalCode);
		} else {
			WrapLog4j.info("Cannot determine address");
			addr.append("ADDRESS NOT FOUND: ");
			addr.append(postalCode);
		}

		return addr.toString();
	}
	
}
