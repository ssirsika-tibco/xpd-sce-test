package com.tibco.bx.debug.core.ws.finder.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.util.WSDLResourceFactoryRegistry;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.ws.finder.IWSOperationFinder;
import com.tibco.bx.debug.core.ws.finder.WSProperties;

public abstract class AbstractFinder implements IWSOperationFinder {

	Map<String, Definition> definitionMap = new HashMap<String, Definition>();;

	protected Definition getDefinition(String endPoint) throws IOException {
		// FIXME This is a long time task, should put it into a job
		Definition definition;
		if (endPoint == null) {
			return null;
		}
		definition = definitionMap.get(endPoint);
		if (definition == null) {
			ResourceSet rs = new ResourceSetImpl();
			WSDLResourceFactoryRegistry registry = new WSDLResourceFactoryRegistry(Resource.Factory.Registry.INSTANCE);
			rs.setResourceFactoryRegistry(registry);

			Resource resource = rs.createResource(URI.createURI(endPoint));
			try {
				resource.load(null);
			} catch (Exception e) {
				definition = null;
			}
			if (resource.getContents().size() > 0) {
				definition = ((Definition) resource.getContents().get(0));
				definitionMap.put(endPoint, definition);
			}
		}
		return definition;
	}

	abstract String getEndpoint(EObject startActivity, Map<?, ?> parameters);

	@Override
	public Operation findWSDLOperation(EObject startActivity, WSProperties properties, Map<?, ?> parameters) throws CoreException {
		try {
			Definition definition = getDefinition(getEndpoint(startActivity, parameters));
			if (definition != null) {
				Map portTypes = definition.getPortTypes();
				for (Object item : portTypes.values()) {
					PortType portType = (PortType) item;
					if (StringUtils.equal(properties.getPortTypeName(), portType.getQName().getLocalPart())) {
						for (Iterator iterator = portType.getOperations().iterator(); iterator.hasNext();) {
							Operation operaton = (Operation) iterator.next();
							if (StringUtils.equal(operaton.getName(), properties.getOperationName())) {
								return operaton;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			throw new CoreException(new org.eclipse.core.runtime.Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID,
					Messages.getString("AbstractFinder.resourceError") + e.getMessage(), null)); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public Operation findWSDLOperation(String wsdlUrl, WSProperties properties) throws CoreException {
		try {
			Definition definition = getDefinition(wsdlUrl);
			if (definition != null) {
				Map portTypes = definition.getPortTypes();
				for (Object item : portTypes.values()) {
					PortType portType = (PortType) item;
					if (StringUtils.equal(properties.getPortTypeName(), portType.getQName().getLocalPart())) {
						for (Iterator iterator = portType.getOperations().iterator(); iterator.hasNext();) {
							Operation operaton = (Operation) iterator.next();
							if (StringUtils.equal(operaton.getName(), properties.getOperationName())) {
								return operaton;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			throw new CoreException(new org.eclipse.core.runtime.Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID,
					Messages.getString("AbstractFinder.resourceError") + e.getMessage(), null)); //$NON-NLS-1$
		}
		return null;
	}

}
