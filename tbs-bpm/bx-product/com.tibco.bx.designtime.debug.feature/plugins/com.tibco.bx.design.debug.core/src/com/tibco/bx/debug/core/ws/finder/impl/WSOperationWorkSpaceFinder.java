package com.tibco.bx.debug.core.ws.finder.impl;


import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Operation;

import com.tibco.bx.debug.core.ws.finder.IWSOperationFinder;
import com.tibco.bx.debug.core.ws.finder.WSProperties;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;

public class WSOperationWorkSpaceFinder implements IWSOperationFinder {

	@Override
	public Operation findWSDLOperation(EObject startActivity , WSProperties properties,
									   Map<?,?> parameters) {
		WsdlServiceKey wsKey = new WsdlServiceKey(properties.getServiceName(),
				properties.getPortTypeName(),
				properties.getOperationName());
		URI processUri = startActivity.eResource().getURI();
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(processUri.toPlatformString(true));
		return (Operation) WsdlIndexerUtil.getOperation(resource.getProject(),wsKey,true,true);
		
	}

	@Override
	public Operation findWSDLOperation(String url, WSProperties properties) throws CoreException {
		return null;
	}
}
