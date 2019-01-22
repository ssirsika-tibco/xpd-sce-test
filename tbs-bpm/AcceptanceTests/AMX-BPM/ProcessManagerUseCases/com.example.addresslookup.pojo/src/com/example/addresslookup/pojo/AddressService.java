package com.example.addresslookup.pojo;

import com.example.addresslookup.pojo.model.Address;
import com.example.addresslookup.pojo.model.AddressQuery;


public class AddressService {

	public String lookup(String postalCode) { 
		System.out.println("Looking up address for: " + postalCode) ; 
		
		StringBuilder addr = new StringBuilder() ; 

		if ("SN2 8BL".equals(postalCode)) {
			System.out.println("Determine address is Swindon office");
			addr.append("4, Apple Walk, ");
			addr.append("Kembrey Park, ");
			addr.append("Swindon. ");
//			addr.append("UK");
			addr.append(postalCode);
		} else if ("94304".equals(postalCode)) {
			System.out.println("Determine address is Palo Alto office");
			addr.append("3303 Hillview Avenue, ");
			addr.append("Palo Alto. ");
			addr.append("CA ");
//			addr.append(query.getCountry());
			addr.append(postalCode);
		} else {
			System.out.println("Cannot determine address");
			addr.append("ADDRESS NOT FOUND: ");
			addr.append(postalCode);
		}

		return addr.toString();
	}
	
	public Address lookupTestBDS(AddressQuery addressQuery) { 
		System.out.println("Looking up address for: " + addressQuery.getPostalCode()) ; 
		
		Address addr = null;

		if ("SN2 8BL".equals(addressQuery.getPostalCode())) {
			System.out.println("Determine address is Swindon office");
			addr = new Address("4, Apple Walk, ","Kembrey Park, ","Swindon. ", "UK",addressQuery.getPostalCode());
		} else if ("94304".equals(addressQuery.getPostalCode())) {
			System.out.println("Determine address is Palo Alto office");
			addr = new Address("3303 Hillview Avenue, ","Palo Alto. ","CA ","USA", addressQuery.getPostalCode());
		} else {
			System.out.println("Cannot determine address");
		}

		return addr;
	}
}
