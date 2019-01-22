/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.internal;

import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.validation.provider.IValidationItem2;

/**
 * EMF validation controller. Use {@link #getInstance()} to get a singleton
 * instance of this class.
 * 
 * @author njpatel
 * 
 */
public class EMFValidator {

    /**
     * EMF validation marker id.
     */
    public static final String MARKER_ID = "com.tibco.xpd.validation.emfMarker"; //$NON-NLS-1$

    /**
     * Validation client data key for accessing the <code>WorkingCopy</code>.
     */
    public static final String LISTENER_WC_KEY = "WorkingCopy"; //$NON-NLS-1$

    /**
     * Singleton instance of this class.
     */
    private static final EMFValidator INSTANCE = new EMFValidator();

    /**
     * Validation mode - Live or Batch.
     */
    private enum ValidationMode {
        LIVE, BATCH;
    }

    /**
     * Get the singleton instance of this class.
     * 
     * @return
     */
    public static EMFValidator getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor. Use {@link #getInstance()} to get singleton instance
     * of this class.
     */
    private EMFValidator() {
        // Private constructor
    }

    /**
     * Create the LIVE validation service.
     * 
     * @return live validator.
     */
    protected ILiveValidator createLiveValidator() {
        ModelValidationService service = ModelValidationService.getInstance();
        ILiveValidator liveValidator =
                service.newValidator(EvaluationMode.LIVE);
        liveValidator.setReportSuccesses(true);
        return liveValidator;

    }

    /**
     * Create the BATCH validation service.
     * 
     * @return batch validator.
     */
    protected IBatchValidator createBatchValidator() {
        ModelValidationService service = ModelValidationService.getInstance();
        IBatchValidator batchValidator =
                service.newValidator(EvaluationMode.BATCH);
        batchValidator.setReportSuccesses(true);
        // Run live constraints when batch validation is called
        batchValidator.setIncludeLiveConstraints(true);
        return batchValidator;

    }

    /**
     * Run the EMF live validation.
     * 
     * @see ILiveValidator#validate(Collection)
     * 
     * @param item
     *            {@link IValidationItem} being validated.
     * @return validation {@link IStatus result}.
     */
    public IStatus runLiveValidation(IValidationItem item) {
        IStatus status = Status.CANCEL_STATUS;
        /*
         * Only run live validation when a model has been modified. The
         * WorkingCopyMonitor will 'call' live validation after batch validation
         * has occurred because it listens for working copy PROP_DIRTY changes -
         * this would cause an extra live validation after the batch validation.
         */
        if (item instanceof IValidationItem2
                && ((IValidationItem2) item).getNotifications() != null) {
            status = validate(item, ValidationMode.LIVE);
        }
        return status;

    }

    /**
     * Run the EMF batch validation. This will also include the live validation
     * constraints.
     * 
     * @see IBatchValidator#validate(org.eclipse.emf.ecore.EObject)
     * 
     * @param item
     *            {@link IValidationItem} being validated.
     * @return validation {@link IStatus result}.
     */
    public IStatus runBatchValidation(IValidationItem item) {
        return validate(item, ValidationMode.BATCH);
    }

    /**
     * Clean the given resource. This will remove all EMF validation marker
     * problems from the resource.
     * 
     * @param resource
     *            resource to clean
     * @param monitor
     *            progress monitor, <code>null</code> if no progress required
     */
    public void clean(final IResource resource, IProgressMonitor monitor) {
        if (resource != null && resource.exists()) {
            try {
                ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

                    public void run(IProgressMonitor monitor)
                            throws CoreException {
                        resource.deleteMarkers(MARKER_ID,
                                true,
                                IResource.DEPTH_ZERO);
                    }

                },
                        monitor);
            } catch (CoreException e) {
                ValidationActivator.getDefault().getLogger().error(e);
            }
        }
    }

    /**
     * Run batch validation on the given <code>WorkingCopy</code>.
     * 
     * @param wc
     *            <code>WorkingCopy</code>
     * @return validation status.
     * 
     */
    public IStatus runBatchValidation(WorkingCopy wc) {
        return runBatchValidation(wc, null);
    }

    /**
     * Run batch validation on the given <code>WorkingCopy</code>.
     * 
     * @param wc
     *            <code>WorkingCopy</code>
     * @param filters
     *            Collection of constraint filters, can be <code>null</code>.
     * @return validation status.
     * 
     * @since 3.2
     */
    public IStatus runBatchValidation(WorkingCopy wc,
            Collection<IConstraintFilter> filters) {
        IStatus status = Status.OK_STATUS;
        if (wc != null && wc.getRootElement() != null) {
            IBatchValidator batchValidator = createBatchValidator();
            // Pass the working copy to the listeners
            batchValidator.putClientData(LISTENER_WC_KEY, wc);
            if (filters != null && !filters.isEmpty()) {
                for (IConstraintFilter filter : filters) {
                    batchValidator.addConstraintFilter(filter);
                }
            }
            status = batchValidator.validate(wc.getRootElement());
        }
        return status;
    }

    /**
     * Run the EMF validation service.
     * 
     * @param item
     *            item to validate.
     * @param vMode
     *            validation mode.
     */
    private IStatus validate(IValidationItem item, ValidationMode vMode) {
        IStatus status = Status.OK_STATUS;
        // Run the EMF Validation
        switch (vMode) {
        case LIVE:
            if (item instanceof IValidationItem2) {
                Collection<Notification> notifications =
                        ((IValidationItem2) item).getNotifications();

                if (notifications != null) {
                    ILiveValidator liveValidator = createLiveValidator();
                    // Pass the working copy to the listeners
                    liveValidator.putClientData(LISTENER_WC_KEY, item
                            .getWorkingCopy());
                    status = liveValidator.validate(notifications);
                }
            }
            break;

        case BATCH:
            WorkingCopy wc = item.getWorkingCopy();
            status = runBatchValidation(wc);
            break;
        }
        return status;
    }

}
