/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.InterfaceBaseValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.FormalParameter;

/**
 * Rule to ensure that formal parameters declared for Process interfaces are of
 * simple types.
 * 
 * @author rsomayaj
 * 
 */
public class SimpleTypeIfcParamRule extends InterfaceBaseValidationRule {
	private final String ID = "bpmn.processIfcSimpleTypeParam"; //$NON-NLS-1$

	@Override
	public void validate(ProcessInterface processInterface) {
		List<FormalParameter> params = processInterface.getFormalParameters();
		for (FormalParameter param : params) {
			if (!(param.getDataType() instanceof BasicType)) {
				addIssue(ID, processInterface);
			}
		}
	}

}
