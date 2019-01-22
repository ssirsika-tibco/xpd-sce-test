/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.explicit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.service.IConstraintFilter;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.EMFValidator;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.internal.WorkspaceResourceValidationManager;
import com.tibco.xpd.validation.internal.engine.ValidationEngine;
import com.tibco.xpd.validation.provider.IFileFilter;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IssueInfo;
import com.tibco.xpd.validation.provider.ValidationException;
import com.tibco.xpd.validation.utils.ValidationProblemUtil;

/**
 * Class for explicitly calling validation for a destination environment.
 * <p>
 * Since 3.1 a new method {@link #runEMFValidation(WorkingCopy)
 * runEMFValidation} has been added to run the EMF batch validation on a given
 * <code>WorkingCopy</code>. This will return the <code>IStatus</code> result of
 * the validation run.
 * </p>
 * <p>
 * Since 3.3 it is <strong>strongly recommended</strong> that builders should
 * not run validation explicitly. Instead use {@link ValidationProblemUtil} to
 * identify problem markers on a given resource.
 * </p>
 * 
 * @author nwilson
 */
public class Validator {
    /** The validation engine. */
    private final ValidationEngine engine;

    private final IndexerServiceImpl indexerService;

    /** The destination environment. */
    private Collection<Destination> destinations;

    private IFileFilter filter;

    /**
     * Run this validator for the given destination.
     * 
     * @param destination
     *            The destination environment.
     */
    public Validator(Destination destination) {
        this(Collections.singletonList(destination));
    }

    /**
     * @param destinations
     *            The destination environments.
     */
    public Validator(Collection<Destination> destinations) {
        this.destinations = destinations;
        indexerService =
                (IndexerServiceImpl) XpdResourcesPlugin.getDefault()
                        .getIndexerService();
        engine = ValidationManager.getInstance().getValidationEngine();
        filter = ValidationManager.getInstance().getFileFilter();
    }

    /**
     * Validate the given resource.
     * 
     * @param resource
     *            The resource to validate.
     * @return The issues generated.
     * @throws ValidationException
     *             If there was a problem during validation.
     */
    public Collection<IIssue> validate(IResource resource)
            throws ValidationException {

        return validate(resource, false, false);
    }

    /**
     * Validate the given resource (also including workspace validation
     * providers).
     * 
     * @param resource
     *            The resource to validate. <code>true</code> to update the
     *            validation problem markers on the resource.
     * @param clean
     *            <code>true</code> to clean all existing markers on the
     *            resource before validating (ignored if <i>affectMarkers</i> is
     *            set to <code>false</code>).
     * @return The issues generated.
     * @throws ValidationException
     *             If there was a problem during validation.
     * @since 3.7.0
     */
    public Collection<IIssue> validateAll(IResource resource,
            boolean affectMarkers, boolean clean) throws ValidationException {
        WorkspaceResourceValidationManager resourceValidator =
                new WorkspaceResourceValidationManager(resource.getProject());
        ArrayList<IIssue> allIssues =
                new ArrayList<IIssue>(resourceValidator.validate(resource));
        allIssues.addAll(validate(resource, affectMarkers, clean));
        return allIssues;
    }

    /**
     * Validate the given resource.
     * 
     * @param resource
     *            The resource to validate.
     * @param affectMarkers
     *            <code>true</code> to update the validation problem markers on
     *            the resource.
     * @param clean
     *            <code>true</code> to clean all existing markers on the
     *            resource before validating (ignored if <i>affectMarkers</i> is
     *            set to <code>false</code>).
     * @return The issues generated.
     * @throws ValidationException
     *             If there was a problem during validation.
     */
    public Collection<IIssue> validate(IResource resource,
            boolean affectMarkers, boolean clean) throws ValidationException {
        return doValidate(resource, affectMarkers, clean, false);
    }

    /**
     * Index and validate the given resource.
     * 
     * @param resource
     *            the resource to index and validate
     * @param affectMarkers
     *            <code>true</code> to update the validation problem markers on
     *            the resource.
     * @param clean
     *            <code>true</code> to clean all existing markers on the
     *            resource before validating (ignored if <i>affectMarkers</i> is
     *            set to <code>false</code>).
     * @return The issues generated.
     * @throws ValidationException
     *             If there was a problem during validation.
     * @since 3.3
     */
    public Collection<IIssue> indexAndValidate(IResource resource,
            boolean affectMarkers, boolean clean) throws ValidationException {
        /*
         * XPD-1950: When forcing a re-index and validate of an affect file
         * during build we didn't used to check the destination file filter.
         * This meant that you would get odd behaviour in that a destination
         * contribution's file filter would prevent validation on WC change BUT
         * if the file was returned by AbstractIncrementalProjectBuilder then it
         * would come thru here and get validated EVEN if it was not included by
         * destination's file filter.
         * 
         * Validation WorkingCopyMonitor / ValidationBuilder normally does this
         * prior to calling this class but incremental project builder does not.
         * 
         * Only validate the resource if it matches the file filters.
         */
        if (!(resource instanceof IFile) || filter.accept((IFile) resource)) {
            return doValidate(resource, affectMarkers, clean, true);
        }
        return Collections.emptyList();
    }

    /**
     * Validate the given index, optionally the resource can be indexed.
     * 
     * @param resource
     * @param affectMarkers
     *            <code>true</code> to update the validation problem markers on
     *            the resource.
     * @param clean
     *            <code>true</code> to clean all existing markers on the
     *            resource before validating (ignored if <i>affectMarkers</i> is
     *            set to <code>false</code>).
     * @param doIndex
     *            <code>true</code> if the resource should be indexed before
     *            validating.
     * @return The issues generated.
     * @throws ValidationException
     *             If there was a problem during validation.
     * @since 3.3
     */

    private Collection<IIssue> doValidate(IResource resource,
            boolean affectMarkers, boolean clean, boolean doIndex)
            throws ValidationException {
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
        if (wc == null) {
            throw new ValidationException(String.format(Messages.Validator_0,
                    resource.getLocation().toString()));
        }

        if (doIndex) {
            indexerService.index(wc);
        }

        ExplicitValidationItem item = new ExplicitValidationItem(wc);
        item.setAffectMarkers(affectMarkers);
        item.setClean(clean);
        Collection<IIssue> issues = engine.validate(item, destinations);
        return issues;
    }

    /**
     * @param issue
     *            The issue.
     * @return The associated IssueInfo.
     */
    public IssueInfo getIssueInfo(IIssue issue) {
        return engine.getIssueInfo(issue.getId());
    }

    /**
     * Run the EMF batch validation on the given <code>WorkingCopy</code>.
     * 
     * @param wc
     *            <code>WorkingCopy</code>.
     * @return validation status.
     * @since 3.1
     */
    public static IStatus runEMFValidation(WorkingCopy wc) {
        IStatus status = Status.OK_STATUS;

        if (wc != null) {
            status = EMFValidator.getInstance().runBatchValidation(wc);
        }

        return status;
    }

    /**
     * Run the EMF batch validation on the given <code>WorkingCopy</code>.
     * 
     * @param wc
     *            <code>WorkingCopy</code>.
     * @param filters
     *            Collections of filters to filter the constraints/client
     *            context run for this validation.
     * @return validation status.
     * @since 3.2
     */
    public static IStatus runEMFValidation(WorkingCopy wc,
            Collection<IConstraintFilter> filters) {
        IStatus status = Status.OK_STATUS;

        if (wc != null) {
            status = EMFValidator.getInstance().runBatchValidation(wc, filters);
        }

        return status;
    }
}
