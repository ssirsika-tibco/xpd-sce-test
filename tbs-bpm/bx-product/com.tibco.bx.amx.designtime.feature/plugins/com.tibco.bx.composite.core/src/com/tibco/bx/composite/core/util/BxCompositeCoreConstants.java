package com.tibco.bx.composite.core.util;

public interface BxCompositeCoreConstants {
	
	String N2PE_SCA_IMPLEMENTATION_TYPE = "implementation.bx"; //$NON-NLS-1$
	
	String GLOBAL_SIGNAL_SCA_IMPLEMENTATION_TYPE = "global.signal.implementation.bx"; //$NON-NLS-1$
	
	String N2PE_CAPABILITY = "com.tibco.bx.capability.implementation.bx"; //$NON-NLS-1$
	
	String HTTP_CLIENT_CONFIGURATION = "HttpClientConfiguration"; //$NON-NLS-1$

	String HTTP_CLIENT_EXTENSION = "httpClient";//$NON-NLS-1$
	
	String CAPABILITY_ID = "com.tibco.bx.capability.implementation.bx"; //$NON-NLS-1$
	
	String BX_AMX_MODEL_SERVICE = "com.tibco.bx.amx.model.service"; //$NON-NLS-1$
	
	String CTC_WSTX_STAX = "com.ctc.wstx.stax"; //$NON-NLS-1$
	String BX_AMX_CONTAINER = "com.tibco.bx.amx.container"; //$NON-NLS-1$
	
	String DEFAULT_SR_NAME = "Shared Resources";
	String SHARED_RESOURCE_KIND = "sharedResource"; //$NON-NLS-1$
	String DEFAULT = "default"; //$NON-NLS-1$
	String XSD = "xsd"; //$NON-NLS-1$
	String WSDL = "wsdl"; //$NON-NLS-1$
	String BPEL = "bpel"; //$NON-NLS-1$
	
    String EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions"; //$NON-NLS-1$
    String DATABASE_EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions/database"; //$NON-NLS-1$
    String JAVA_EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions/java"; //$NON-NLS-1$
    String EMAIL_EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions/email"; //$NON-NLS-1$
    
	public static final String DATABASE_SR_NS = "http://xsd.tns.tibco.com/amf/models/sharedresource/jdbc";//$NON-NLS-1$
	public static final String EMAIL_SR_NS = "http://xsd.tns.tibco.com/amf/models/sharedresource/smtp"; //$NON-NLS-1$
	public static final String HTTP_SR_NS = "http://xsd.tns.tibco.com/amf/models/sharedresource/httpclient";//$NON-NLS-1$

}
