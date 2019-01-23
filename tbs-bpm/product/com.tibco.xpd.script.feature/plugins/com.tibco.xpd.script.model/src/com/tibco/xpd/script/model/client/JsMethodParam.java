/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import org.eclipse.uml2.uml.Parameter;

/**
 * 
 * <p>
 * <i>Created: 3 May 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public interface JsMethodParam {
	String getName();

	String getType();

	/**
	 * Returns whether the method parameter should be passed by reference
	 * 
	 * @return
	 */
	boolean isPassedByReference();

	/**
	 * Returns whether the method parameter should be passed by literal value
	 * 
	 * @return
	 */
	boolean isPassedByLiteral();

	boolean isReturnType();

	boolean canRepeat();

	int getMaxRepeatCount();

	int getMinRepeatCount();

	Parameter getUMLParameter();

	/**
	 * Returns an appropriate script relevant data for the return type of
	 * methods. Provided to support dynamic type checking in TIBCO Form product.
	 * The default implementation returns null.
	 * 
	 * @return Appropriate script relevant data for the parameter type.
	 * @since 3.x.
	 */
	public IScriptRelevantData getScriptRelevantData();

}
