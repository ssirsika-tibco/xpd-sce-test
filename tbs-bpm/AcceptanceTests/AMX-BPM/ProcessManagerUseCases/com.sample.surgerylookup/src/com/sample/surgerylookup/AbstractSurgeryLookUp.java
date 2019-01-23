package com.sample.surgerylookup;

import com.tibco.www.studio_3_3.SurgeryAddressService.LookUpSurgeryServicePortType;
import com.tibco.studio33.surgeryAddressService.SurgeryAddressQueryType;
import com.tibco.studio33.surgeryAddressService.SurgeryAddressResultType;

/**
 * Abstract interface generated for component "surgerylookup-saml".
 *
 * This class will be completely generated, add custom code to the subclass: 
 * {@link com.sample.surgerylookup.AbstractSurgeryLookUp AbstractSurgeryLookUp}
 *
 * @Generated TEMPL003
 */
public abstract class AbstractSurgeryLookUp
		implements
			LookUpSurgeryServicePortType {

	/**
	 * Implementation of the WSDL operation: LookupSurgeryAddress	 */
	public abstract SurgeryAddressResultType lookupSurgeryAddress(
			SurgeryAddressQueryType surgeryAddressQuery);

}
