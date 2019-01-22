/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import java.util.HashSet;
import java.util.Set;

import javax.wsdl.Fault;

import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.extensions.IInternalFaultMessageNameProvider;
import com.tibco.xpd.xpdl2.Activity;

/**
 * FaultMessageNameProviderContribution
 * <p>
 * The fault message name provider contributed to the internal
 * operationInformationProvider extension so that its the process developer
 * feature that contributes the list of fault names that are selectable.
 * 
 * @author aallway
 * @since 3.3 (25 Nov 2009)
 */
public class FaultMessageNameProviderContribution implements
        IInternalFaultMessageNameProvider {

    public Set<String> getFaultMessageNames(Activity webServiceActivity) {
        Set<String> faultNames = new HashSet<String>();

        Set<Fault> faults =
                Xpdl2WsdlUtil.getWsdlOperationFaults(webServiceActivity);
        if (faults != null) {
            for (Fault fault : faults) {
                faultNames.add(fault.getName());
            }
        }
        return faultNames;
    }

}
