/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Message;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fault Message</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Element added to xpdl2:ResultError for throw error end event when that event 
 * is configured to throw a fault message defined for the operation in the referenced
 * incoming message request activity (ref'd via xpdl2:ResultError/xpdExt:RequestActivityId).
 * <!-- end-model-doc -->
 *
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFaultMessage()
 * @model extendedMetaData="name='FaultMessage' kind='elementOnly'"
 * @generated
 */
public interface FaultMessage extends Message
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

} // FaultMessage
