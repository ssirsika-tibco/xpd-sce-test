package com.tibco.bx.debug.core.ws.finder;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Operation;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.core.ws.finder.impl.WSOperationRemoteFinder;
import com.tibco.bx.debug.core.ws.finder.impl.WSOperationWorkSpaceFinder;
import com.tibco.bx.debug.core.ws.finder.impl.WSUrlFinder;
public class FinderFactory {
	
	private static final String NOT_REMOTE = "Not Remote"; //$NON-NLS-1$

	private static final String REMOTE = "Remote"; //$NON-NLS-1$
	
	private static final String URL = "url"; //$NON-NLS-1$

	private Map<String,IWSOperationFinder> finders  = null;
	
	public static FinderFactory instance = new FinderFactory(); 
	
	
	private FinderFactory() {
		finders = new HashMap<String, IWSOperationFinder>();
		finders.put(REMOTE, new WSOperationRemoteFinder());
		finders.put(NOT_REMOTE, new WSOperationWorkSpaceFinder());
		finders.put(URL, new WSUrlFinder());
	}
	
	public Operation findWSDLOperation(EObject startActivity ,String modelType, Map<?,?> paramters) throws CoreException{
		WSProperties properties = ProcessUtil.getWSProperties(startActivity);
		Assert.isNotNull(properties);
		IWSOperationFinder finder = null;
		if(properties.getIsRemoteService()){
			finder =  finders.get(REMOTE);
		}else{
			finder = finders.get(NOT_REMOTE);
		}
		return finder.findWSDLOperation(startActivity,properties, paramters);
	}
	
	public Operation findWSDLOperation(EObject startActivity, String wsdlUrl)throws CoreException{
		WSProperties properties = ProcessUtil.getWSProperties(startActivity);
		Assert.isNotNull(properties);
		IWSOperationFinder finder = null;
		if(properties.getIsRemoteService()){
			finder =  finders.get(REMOTE);
		}else{
			finder = finders.get(NOT_REMOTE);
		}
		return finder.findWSDLOperation(wsdlUrl, properties);
	}

    public Operation findWSDLOperationFromUrl(EObject startActivity, String wsdlUrl) throws CoreException {
        WSUrlFinder finder = (WSUrlFinder) finders.get(URL);
        finder.setUrl(wsdlUrl);
        WSProperties properties = ProcessUtil.getWSProperties(startActivity);
        Operation operation = finder.findWSDLOperation(wsdlUrl, properties);
        finder.setUrl(null);
        return operation;
    }
	
}
