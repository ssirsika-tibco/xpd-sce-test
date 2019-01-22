package com.tibco.xpd.bom.xsdtransform.utils;

/**
 * Thrown by XSDDerivedBOMNameAssigner to indicate that a name assignment
 * operation failed, generally due to invalid content in the supplied Model.
 * 
 * @author smorgan
 * 
 */
public class NameAssignmentException extends Exception {

    private static final long serialVersionUID = 7789187360774723432L;

    public NameAssignmentException(String message) {
        super(message);
    }

    public NameAssignmentException(String message, Throwable cause) {
        super(message, cause);
    }

}
