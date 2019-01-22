/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.script.transform.template;

import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;


/**
 * Helper class for performing more complex operations for the import xsd transformation process.
 * @author mtorres
 *
 */
public class TransformHelper {
    
	/**
	 * Outputs text to system console
	 * @param temp
	 * @return
	 */
	public static String traceMe(String temp){		
		System.out.println(temp);
		return ""; //$NON-NLS-1$ 
	}
	
    public static Type getBomPrimitiveType(PrimitiveType primType) { 
//      Type retPrimType = PrimitivesUtil.getBasePrimitiveType(primType);
//      // retPrimType = packagedElements.get(0);
//      return retPrimType;
      return null;
  } 


}
