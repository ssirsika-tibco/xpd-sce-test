/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.imports.validations;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.swt.widgets.ProgressBar;

/**
 * Interface used by the Bpm conversion Validators to assist them with the
 * validation of the source files and target Containers and to return the Errors
 * if any on the source files/ containers.
 * 
 * @author kthombar
 * @since 09-Jun-2014
 */
public interface IBpmConversionValidator {

    /**
     * Performs the validation. Implementers are expected to appropriately use
     * the {@link IProgressMonitor} and {@link ProgressBar} to visually show the
     * progress of validation.
     * 
     * @param monitor
     *            the {@link IProgressMonitor} to track the progress of the
     *            validation
     * @throws OperationCanceledException
     *             If the {@link IProgressMonitor} was cancelled by the user.
     */
    public List<ImportValidationError> validate(IProgressMonitor monitor)
            throws OperationCanceledException;

}
