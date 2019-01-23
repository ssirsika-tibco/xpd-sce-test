/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.errorEvents;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.xpdExtension.ErrorThrowerType;

/**
 * Object representing an error event that can be caught by a Catch Error
 * Intermediate Event attached to a task boundary.
 * <p>
 * A hierarchy of these can be created using
 * {@link BpmnCatchableErrorUtil#getCatchableErrors(com.tibco.xpd.xpdl2.Activity)}
 * 
 * @author aallway
 * @since 3.2
 */
public final class BpmnCatchableError implements IBpmnCatchableErrorTreeItem {

    private IBpmnCatchableErrorsContributor errorInfoProvider;

    private String errorCode;

    private String errorLabel;

    private Object errorThrower;

    private BpmnCatchableErrorFolder parentFolder;

    private ErrorThrowerType errorThrowerType;

    /**
     * Constructor for instances where the label is the same as the error code.
     * 
     * @param errorThrower
     *            The thrower of the error.
     * @param throwerType
     *            The type of activity throwing the error.
     * @param errorCode
     *            The error code.
     * @param errorProvider
     *            The error provider.
     */
    public BpmnCatchableError(Object errorThrower,
            ErrorThrowerType throwerType, String errorCode,
            IBpmnCatchableErrorsContributor errorProvider) {
        this(errorThrower, throwerType, errorCode, null, errorProvider);
    }

    /**
     * Constructor to allow a name to be appended to the error code for UI
     * labels.
     * 
     * @param errorThrower
     *            The thrower of the error.
     * @param throwerType
     *            The type of activity throwing the error.
     * @param errorCode
     *            The error code.
     * @param errorName
     *            The name of the error.
     * @param errorProvider
     *            The error provider.
     */
    public BpmnCatchableError(Object errorThrower,
            ErrorThrowerType throwerType, String errorCode, String errorName,
            IBpmnCatchableErrorsContributor errorProvider) {
        super();

        this.errorThrower = errorThrower;
        this.errorThrowerType = throwerType;
        this.errorCode = errorCode;
        errorLabel =
                errorName == null ? errorCode : errorCode + " (" + errorName //$NON-NLS-1$
                        + ")"; //$NON-NLS-1$
        this.errorInfoProvider = errorProvider;
    }

    @Override
    public Image getImage() {
        return errorInfoProvider.getErrorImage(errorThrower, errorCode);
    }

    @Override
    public String getLabel() {
        return errorLabel;
    }

    /**
     * @return Always the same as getLabel() and should be used for the
     *         errorCode value that is place in the XPDL file.
     */
    public String getErrorCode() {
        return errorCode;
    }

    public ErrorThrowerType getErrorThrowerType() {
        return errorThrowerType;
    }

    public String getErrorThrowerId() {
        return errorInfoProvider.getErrorThrowerId(errorThrower);
    }

    public Object getErrorThrower() {
        return errorThrower;
    }

    public String getErrorThrowerContainerId() {
        return errorInfoProvider.getErrorThrowerContainerId(errorThrower);
    }

    /**
     * An error itself cannot have children.
     */
    @Override
    public final boolean hasChildren() {
        return false;
    }

    /**
     * An error itself cannot have children.
     */
    @Override
    public final Collection<IBpmnCatchableErrorTreeItem> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public BpmnCatchableErrorFolder getParentFolder() {
        return parentFolder;
    }

    @Override
    public void setParent(BpmnCatchableErrorFolder parentFolder) {
        this.parentFolder = parentFolder;
    }

    @Override
    public String toString() {
        return errorLabel;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof BpmnCatchableError) {
            BpmnCatchableError other = (BpmnCatchableError) obj;

            String e1 = getErrorCode();
            if (e1 == null) {
                e1 = ""; //$NON-NLS-1$
            }

            if (e1.equals(other.getErrorCode())) {
                e1 = getErrorThrowerId();

                if (e1 == null) {
                    e1 = ""; //$NON-NLS-1$
                }

                if (e1.equals(other.getErrorThrowerId())) {
                    e1 = getErrorThrowerContainerId();

                    if (e1 == null) {
                        e1 = ""; //$NON-NLS-1$
                    }

                    if (e1.equals(other.getErrorThrowerContainerId())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
