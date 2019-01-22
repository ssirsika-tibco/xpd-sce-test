package com.tibco.bx.debug.core.ws.wss.util;

public class WSConstants {
   
   public static final String SOAPMESSAGE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0"; //$NON-NLS-1$
    
   public static final String WSSE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"; //$NON-NLS-1$
  
   public static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"; //$NON-NLS-1$
   
   public static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/"; //$NON-NLS-1$
   
   public static final String WSU_PREFIX = "wsu"; //$NON-NLS-1$
   
   public static final String USERNAME_TOKEN_LN = "UsernameToken"; //$NON-NLS-1$
   
   public static final String USERNAME_LN = "Username"; //$NON-NLS-1$
   public static final String PASSWORD_LN = "Password"; //$NON-NLS-1$
   
   public static final String NONCE_LN = "Nonce"; //$NON-NLS-1$
   public static final String CREATED_LN = "Created"; //$NON-NLS-1$
   
   public static final String USERNAMETOKEN_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0"; //$NON-NLS-1$
   
   public static final String PW_DIGEST = "PasswordDigest"; //$NON-NLS-1$

   public static final String PASSWORD_DIGEST = USERNAMETOKEN_NS + "#PasswordDigest"; //$NON-NLS-1$

   public static final String PW_TEXT = "PasswordText"; //$NON-NLS-1$
   
   public static final String PASSWORD_TEXT = USERNAMETOKEN_NS + "#PasswordText"; //$NON-NLS-1$
   
   public static final String BASE64_ENCODING =  WSConstants.SOAPMESSAGE_NS + "#Base64Binary"; //$NON-NLS-1$
   
   //
   // SOAP-ENV Namespaces
   //
   public static final String URI_SOAP11_ENV =
           "http://schemas.xmlsoap.org/soap/envelope/"; //$NON-NLS-1$
   public static final String URI_SOAP12_ENV =
           "http://www.w3.org/2003/05/soap-envelope"; //$NON-NLS-1$

   public static final String[] URIS_SOAP_ENV = {
       URI_SOAP11_ENV,
       URI_SOAP12_ENV,
   };
   
   // Misc SOAP Namespaces / URIs
   public static final String URI_SOAP11_NEXT_ACTOR =
           "http://schemas.xmlsoap.org/soap/actor/next"; //$NON-NLS-1$
   public static final String URI_SOAP12_NEXT_ROLE =
           "http://www.w3.org/2003/05/soap-envelope/role/next"; //$NON-NLS-1$
   public static final String URI_SOAP12_NONE_ROLE =
           "http://www.w3.org/2003/05/soap-envelope/role/none"; //$NON-NLS-1$
   public static final String URI_SOAP12_ULTIMATE_ROLE =
           "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver"; //$NON-NLS-1$
   
   public static final String ELEM_ENVELOPE = "Envelope"; //$NON-NLS-1$
   public static final String ELEM_HEADER = "Header"; //$NON-NLS-1$
   public static final String ELEM_BODY = "Body"; //$NON-NLS-1$
   
   public static final String ATTR_MUST_UNDERSTAND = "mustUnderstand"; //$NON-NLS-1$
   public static final String ATTR_ACTOR = "actor"; //$NON-NLS-1$
   public static final String ATTR_ROLE = "role"; //$NON-NLS-1$
   
   // SOAP Version
   public static final String SOAP11 = "1.1"; //$NON-NLS-1$
   public static final String SOAP12 = "1.2"; //$NON-NLS-1$
}
