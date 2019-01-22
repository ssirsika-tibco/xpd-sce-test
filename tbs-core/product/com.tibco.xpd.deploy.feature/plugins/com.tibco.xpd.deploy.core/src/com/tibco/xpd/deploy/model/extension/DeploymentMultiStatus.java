/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.util.ArrayList;
import java.util.List;

/**
 * Multiple status implementation. Severity of the status depends on severity of
 * children and it is the maximal severity of all children.
 * <p>
 * <i>Created: 17 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeploymentMultiStatus extends DeploymentSimpleStatus {

    List<DeploymentStatus> children;

    private Severity maxChildrenSeverity;

    /**
     * The constructor. Severity depends on children. If any of the children
     * will have greater severity then this grater severity will overwrite the
     * default starting severity defined by the constructor.
     * 
     * @param severity
     *            default initial severity.
     * @param message
     *            initial message.
     * @param exception
     */
    public DeploymentMultiStatus(Severity severity, String message,
            Throwable exception) {
        super(severity, message, exception);
        children = new ArrayList<DeploymentStatus>();
    }

    /**
     * The constructor. Severity depends on children. If any the children will
     * have greater severity then this grater severity will overwrite the
     * default starting severity defined by the constructor.
     * 
     * @param severity
     *            default initial severity.
     * @param message
     *            initial message.
     * @param exception
     */
    public DeploymentMultiStatus(Severity severity, String message,
            Throwable exception, List<DeploymentStatus> children) {
        super(Severity.OK, message, exception);
        if (children != null) {
            this.children = new ArrayList<DeploymentStatus>(children);
        } else {
            this.children = new ArrayList<DeploymentStatus>();
        }
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus#getChildren()
     */
    @Override
    public List<DeploymentStatus> getChildren() {
        return children;
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus#getSeverity()
     */
    @Override
    public Severity getSeverity() {
        if (maxChildrenSeverity == null) {
            maxChildrenSeverity = Severity.OK;
            for (DeploymentStatus child : children) {
                Severity childSeverity = child.getSeverity();
                if (childSeverity.compareTo(maxChildrenSeverity) > 0) {
                    maxChildrenSeverity = childSeverity;
                }
            }
        }
        Severity thisSeverity = super.getSeverity();
        return (thisSeverity.compareTo(maxChildrenSeverity) > 0) ? thisSeverity
                : maxChildrenSeverity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus#isMultiStatus
     * ()
     */
    @Override
    public boolean isMultiStatus() {
        return true;
    }

    public void add(DeploymentStatus child) {
        if (child != null) {
            children.add(child);
            if (maxChildrenSeverity == null) {
                maxChildrenSeverity = Severity.OK;
            }
            Severity childSeverity = child.getSeverity();
            if (childSeverity.compareTo(maxChildrenSeverity) > 0) {
                maxChildrenSeverity = childSeverity;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus#getErrorsCount
     * ()
     */
    @Override
    public int getErrorsCount() {
        int errors = 0;
        for (DeploymentStatus child : children) {
            if (child != null) {
                errors += child.getErrorsCount();
            }
        }
        if (errors > 0) {
            return errors;
        }
        return super.getErrorsCount();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus#getWarningsCount
     * ()
     */
    @Override
    public int getWarningsCount() {
        int warnings = 0;
        for (DeploymentStatus child : children) {
            if (child != null) {
                warnings += child.getWarningsCount();
            }
        }
        if (warnings > 0) {
            return warnings;
        }
        return super.getWarningsCount();
    }
}
