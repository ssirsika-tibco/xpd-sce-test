/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethodParam;

/**
 *
 * 
 * @author mtorres
 * 
 */
public interface PojoJsMethodParam extends JsMethodParam{
	boolean isReferenceReturnType();
	JsClass getReferencedReturnType();
}
