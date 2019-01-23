package com.tibco.xpd.bom.gen.biz;

import java.util.List;

import com.tibco.xpd.validation.provider.IIssue;

/**
 * Exception which indicate IllegalState(to start CDS Generation) of a BOM.An
 * example of an illegalState is when a given BOM does not have any classes
 * 
 * @author jaugusti
 * 
 */
public class BOMIllegalStateException extends Exception {

    private static final long serialVersionUID = -405286028630971234L;
    private List<IIssue> issues;
    
    public BOMIllegalStateException(String message) {
        super(message);
    }
    
    public BOMIllegalStateException(String message, List<IIssue> issues) {
        super(message);
        this.issues = issues;
    }

    public BOMIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public List<IIssue> getIssues() {
        return issues;
    }
}
