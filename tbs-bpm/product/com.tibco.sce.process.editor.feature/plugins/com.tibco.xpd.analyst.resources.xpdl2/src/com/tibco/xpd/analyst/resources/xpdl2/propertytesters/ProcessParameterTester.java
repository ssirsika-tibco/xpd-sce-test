/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ProcessParameterTester extends PropertyTester {

	public ProcessParameterTester() {
	}

	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue3) {

		if (receiver instanceof FormalParameter) {
			FormalParameter formalParameter = (FormalParameter) receiver;
			if (formalParameter.eContainer() != null
					&& formalParameter.eContainer() instanceof Process) {
				return true;
			}
		}
		return false;
	}

}
