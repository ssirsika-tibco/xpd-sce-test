package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory;
import org.eclipse.wst.wsdl.WSDLFactory;

import com.tibco.xpd.xpdl2.PartnerLinkType;
import com.tibco.xpd.xpdl2.Role;

public class ConvertPartnerLinkType {

	/** 
	 * Convert the XPDL partnerLinkTypes parameter to BPEL.
	 * @param context 
     * @param partnerLinkTypes A List of PartnerLinkTypes to convert to BPEL.
     */
    public static void convertPartnerLinkTypesToBPEL(
    		final ConverterContext context, final List<PartnerLinkType> partnerLinkTypes) {
        if (partnerLinkTypes != null) {
            for (PartnerLinkType partnerLinkType : partnerLinkTypes) {
            	org.eclipse.bpel.model.partnerlinktype.PartnerLinkType bpelPartnerLinkType = convertPartnerLinkTypeToBPEL(partnerLinkType);
            	context.addPartnerLinkType(bpelPartnerLinkType);
    		}
        }
    }

	/** 
	 * Convert the XPDL partnerLinkType to BPEL.
     * @param partnerLinkType the PartnerLinkType instance to convert.
     */
    public static org.eclipse.bpel.model.partnerlinktype.PartnerLinkType convertPartnerLinkTypeToBPEL(PartnerLinkType partnerLinkType) {
		org.eclipse.bpel.model.partnerlinktype.PartnerLinkType bpelPartnerLinkType = PartnerlinktypeFactory.eINSTANCE.createPartnerLinkType();
		bpelPartnerLinkType.setName(partnerLinkType.getName());

		List<Role> roles = partnerLinkType.getRole();
		for (Role role : roles) {
			org.eclipse.bpel.model.partnerlinktype.Role bpelRole = convertRoleToBPEL(role);
			bpelPartnerLinkType.getRole().add(bpelRole);
		}
        
        return bpelPartnerLinkType;
	}
    
    private static org.eclipse.bpel.model.partnerlinktype.Role convertRoleToBPEL(Role role) {
		org.eclipse.bpel.model.partnerlinktype.Role bpelRole = PartnerlinktypeFactory.eINSTANCE.createRole();
		bpelRole.setName(role.getName());
		javax.wsdl.PortType portType = WSDLFactory.eINSTANCE.createPortType();
		portType.setQName(QName.valueOf(role.getPortType()));
		bpelRole.setPortType(portType);
		return bpelRole;
    }

}
