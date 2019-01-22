package com.tibco.bx.debug.core.ws.finder.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.invoke.constants.ProcessConstants;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.core.ws.util.WsdlUtil;

public class WSOperationRemoteFinder extends AbstractFinder{

	public WSOperationRemoteFinder() {
		
	}

    @Override
    String getEndpoint(EObject startActivity,Map<?,?> parameters) {
        String endPoint = WsdlUtil.getEndpointUrl(
                (String)parameters.get(ProcessConstants.HTTP_PROTOCOL) ,
                (String)parameters.get(ProcessConstants.HTTP_HOST) , 
                (String)parameters.get(ProcessConstants.HTTP_PORT) ,
                ProcessUtil.getWebServiceUri(startActivity));
        
        if(endPoint.matches("^.*/qualifier")){ //$NON-NLS-1$
        	endPoint = endPoint.replaceAll("qualifier" , (String)parameters.get(WsdlUtil.CREATE_DATE)); //$NON-NLS-1$
        }
        endPoint += "?wsdl";  //$NON-NLS-1$
//        System.out.println(endPoint);
        return endPoint;
    }
	
}
