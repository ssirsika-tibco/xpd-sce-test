/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * @author rsomayaj
 * 
 * 
 */
public class UniqueMethodNamesRule extends InterfaceBaseValidationRule {

	private static final String ID_DUPLICATE_NAME = "bpmn.processinterface.duplicateMethodNames"; //$NON-NLS-1$
	
	@Override
	public void validate(ProcessInterface processInterface) {
		List<NamedElement> methods = new ArrayList<NamedElement>();
		List<String> namesList = new ArrayList<String>();
		methods.addAll(processInterface.getStartMethods());
		methods.addAll(processInterface.getIntermediateMethods());
		
		for (NamedElement namedElement: methods) {
			if (namesList.contains(namedElement.getName()))
				addIssue(ID_DUPLICATE_NAME, namedElement);
			namesList.add(namedElement.getName());
		}
		
		

	}

}
