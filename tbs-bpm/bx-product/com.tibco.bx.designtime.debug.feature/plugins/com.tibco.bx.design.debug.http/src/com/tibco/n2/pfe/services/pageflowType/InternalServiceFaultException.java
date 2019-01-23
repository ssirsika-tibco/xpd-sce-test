
package com.tibco.n2.pfe.services.pageflowType;

import javax.xml.ws.WebFault;

public class InternalServiceFaultException extends Exception {
    public static final long serialVersionUID = 20120717093451L;
    
    private com.tibco.n2.pfe.services.pageflowType.DetailedException internalServiceFault;

    public InternalServiceFaultException() {
        super();
    }
    
    public InternalServiceFaultException(String message) {
        super(message);
    }
    
    public InternalServiceFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServiceFaultException(String message, com.tibco.n2.pfe.services.pageflowType.DetailedException internalServiceFault) {
        super(message);
        this.internalServiceFault = internalServiceFault;
    }

    public InternalServiceFaultException(String message, com.tibco.n2.pfe.services.pageflowType.DetailedException internalServiceFault, Throwable cause) {
        super(message, cause);
        this.internalServiceFault = internalServiceFault;
    }

    public com.tibco.n2.pfe.services.pageflowType.DetailedException getFaultInfo() {
        return this.internalServiceFault;
    }
}
