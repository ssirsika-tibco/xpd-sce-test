package com.tibco.bx.debug.core.ws.finder;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Operation;
/**
 * Find the WS Operation according to Activity and process.
 * @author Jet Geng, Will Wang
 *
 */
public interface IWSOperationFinder {

	public  Operation findWSDLOperation(EObject startActivity ,WSProperties properties, Map<?,?> parameters) throws CoreException;
	
	public  Operation findWSDLOperation(String url, WSProperties properties) throws CoreException;
}
