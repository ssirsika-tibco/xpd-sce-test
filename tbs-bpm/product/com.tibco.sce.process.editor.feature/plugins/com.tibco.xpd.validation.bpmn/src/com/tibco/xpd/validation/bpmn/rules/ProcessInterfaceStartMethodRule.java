/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * @author rsomayaj
 *
 * 
 */
public class ProcessInterfaceStartMethodRule extends InterfaceBaseValidationRule{

	private static final String ID_START_METHOD_MANDATE = "bpmn.processinterface.startmethodmandate"; //$NON-NLS-1$
	
	@Override
	public void validate(ProcessInterface processInterface) {
		if(processInterface.getStartMethods().isEmpty())
			addIssue(ID_START_METHOD_MANDATE, processInterface);
	}

}
