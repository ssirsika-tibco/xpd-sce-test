package com.tibco.bx.emulation.core.validator.util;

import com.tibco.bx.emulation.model.EmulationElement;

public class EmulationValidationUtil {

	public static String getLocation(EmulationElement element){
	    return element.eClass().getName();
	}
	
	public static String getURIFragment(EmulationElement element){
	    return element.eResource().getURIFragment(element);
	}
}
