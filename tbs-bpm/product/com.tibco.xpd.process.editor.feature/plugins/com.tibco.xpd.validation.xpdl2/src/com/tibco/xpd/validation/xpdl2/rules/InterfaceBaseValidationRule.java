/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.rules;

import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * @author rsomayaj
 *
 * 
 */
public abstract class InterfaceBaseValidationRule extends Xpdl2ValidationRule {
	
	@Override
	protected void validate(Object o) {
		if(o instanceof ProcessInterface){
			validate((ProcessInterface)o);
		}
	}
	/**
     * @return The class type on which this rule will operate.
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */

	public Class<?> getTargetClass() {
		return ProcessInterface.class;
	}
	
	/**
     * @param processInterface The processInterface to validate.
     */
    public abstract void validate(ProcessInterface processInterface);

}
