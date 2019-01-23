package com.sample.surgerylookup;

import org.osoa.sca.annotations.Destroy;
import org.osoa.sca.annotations.Init;

import com.tibco.studio33.surgeryAddressService.SurgeryAddressQueryType;
import com.tibco.studio33.surgeryAddressService.SurgeryAddressResultType;

/**
 * Implementation of SurgeryLookUp component.
 *
 */
public class SurgeryLookUp extends AbstractSurgeryLookUp {

	/**
	 * Initialization of SurgeryLookUp component.
	 */
	@Init
	public void init() {
		// Component initialization code.
		// All properties are initialized and references are injected.
	}

	/**
	 * Disposal of SurgeryLookUp component.
	 */
	@Destroy
	public void destroy() {
		// Component disposal code.
		// All properties are disposed.
	}

	/**
	 * Implementation of the WSDL operation: LookupSurgeryAddress	 */
	public SurgeryAddressResultType lookupSurgeryAddress(
			SurgeryAddressQueryType surgeryAddressQuery) {
		SurgeryAddressResultType address = SurgeryAddressResultType.Factory.newInstance();
		if (surgeryAddressQuery != null) {
			String postCode = surgeryAddressQuery.getPostalCode();
			String country = surgeryAddressQuery.getCountry();
			if (postCode.endsWith("SN2 8BL") && country.equals("UK")) {
				address.setAddrLine1("4, Apple Walk");
				address.setAddrLine2("Kembrey Park");
				address.setCity("Swindon");
				address.setProvince("Wiltshire");
				address.setPostalCode(postCode);
				address.setCountry("UK");
				
			} else {
				address.setAddrLine1("--Addr1--");
				address.setAddrLine2("--Addr2--");
				address.setCity("--City--");
				address.setProvince("--Province--");
				address.setPostalCode(postCode);
				address.setCountry(country);
			}
		}
		return address;
	}

}
