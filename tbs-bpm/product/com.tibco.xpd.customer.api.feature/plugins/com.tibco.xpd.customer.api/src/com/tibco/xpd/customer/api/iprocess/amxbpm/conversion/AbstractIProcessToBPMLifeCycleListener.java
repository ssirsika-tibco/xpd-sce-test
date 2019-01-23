/*
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.customer.api.iprocess.amxbpm.conversion;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.xpdl2.Package;

/**
 * Contributors can contribute a life-cycle listener to the
 * <code>com.tibco.xpd.customer.api.iProcessBpmConversion</code> extension
 * point.
 * <p>
 * During a conversion of TIBCO iProcess Modeler XPDL to TIBCO Business Studio
 * XPDL for Active Matrix BPM, life-cycle listeners will be invoked at various
 * significant milestones with in each conversion. The contributor should
 * sub-class this class and override any of the following methods to be notified
 * of the life-cycle events indicated...
 * <li>When Initial import and migration to Business Studio XPDL for iProcess
 * destination is completed ({@link #importAndMigrationComplete(Collection)}).</li>
 * <li>When separation of all processes into separate (in memory) process
 * packages (including removal of incoming duplicates) is complete (
 * {@link #packageSeparationComplete(Collection)}).</li>
 * <li>When conversion to XPDL for BPM destination environment is complete (i.e.
 * all conversion extensions performed) (
 * {@link #conversionExtensionsComplete(Collection, Collection)})</li>
 * <li>When conversion is complete and final XPDL file set has been committed to
 * the work space (
 * {@link #conversionComplete(Collection, Collection, Collection)}).</li>
 * </p>
 * <p>
 * <br/>
 * One instance of each life-cycle listener contribution is created
 * <b>per-conversion use case</b> (i.e. for each batch of XPDLs selected for
 * import/conversion by the user). Therefore it is possible to persist
 * information gathered through the various life-cycle milestones.
 * </p>
 * <p>
 * <br/>
 * Additionally, this is also true of each conversion extension. Therefore it
 * should be possible for an individual 3rd party contributor to persist
 * information discovered during each conversion extension and the (for
 * instance) make use of that information once the conversionExtensionsComplete
 * method is invoked, by iterating the given set of executed extensions looking
 * for instances the known 3rd party contributions. For example, the 3rd party
 * contributor may wish to preserve information about XPDL process constructs
 * that may need further user attention after import is complete and then
 * produce a report on these at the end of conversion.
 * </p>
 * <p>
 * <br/>
 * Each method in the life-cycle is called <b>exactly once</b> when a full
 * conversion is executed without interruption by the user or other fatal error
 * that cause conversion to finish unexpectedly.
 * </p>
 * 
 * <br/>
 * 
 * @author TIBCO Software Inc
 * @since TIBCO Business Studio BPM Edition v3.8
 */
public abstract class AbstractIProcessToBPMLifeCycleListener {
    /**
     * Must have a default constructor in order to be loaded from an extension
     * point
     */
    public AbstractIProcessToBPMLifeCycleListener() {
    }

    /**
     * Invoked when the initial import and migration to Business Studio XPDL for
     * iProcess destination is completed (and prior to separation of processes
     * into individual in-memory process packages and removal of duplicate
     * processes in the incoming set).
     * 
     * @param initialFileSet
     *            The original set of files imported
     * @param monitor
     *            Standard Eclipse monitor for progress status updates (see
     *            {@link IProgressMonitor} for more information. This method
     *            should call at least monitor.beginTask() at the start of the
     *            method and monitor.done() at the end. To overwrite the current
     *            progress status message completely, please use only
     *            monitor.subTask(string).
     * 
     * @return IStatus.OK is the nominal return, Istatus.INFO / WARNING can be
     *         used to return information messages (that will be logged with
     *         debug mode switched on). IStatus.ERROR status will stop the
     *         conversion process and report the error.Use
     *         {@link ImportDisplayStatus} status type for messages to be
     *         displayed to the user at the end of the complete conversion of
     *         the XPDL.For more details see {@link ImportDisplayStatus}
     */
    public abstract IStatus importAndMigrationComplete(
            Collection<IFile> initialFileSet, IProgressMonitor monitor);

    /**
     * Invoked after all the incoming processes have been placed in separate
     * (in-memory) process packages. (after initial import and conversion to
     * Business Studio XPDL for the iProfcess destination).
     * 
     * @param studioIProcessPackages
     *            The initial set of process packages (each containing a single
     *            process/process interface configured as if for the iProcess
     *            destination environment).
     * @param monitor
     *            Standard Eclipse monitor for progress status updates (see
     *            {@link IProgressMonitor} for more information. This method
     *            should call at least monitor.beginTask() at the start of the
     *            method and monitor.done() at the end. To overwrite the current
     *            progress status message completely, please use only
     *            monitor.subTask(string).
     * 
     * @return IStatus.OK is the nominal return, Istatus.INFO / WARNING can be
     *         used to return information messages (that will be logged with
     *         debug mode switched on). IStatus.ERROR status will stop the
     *         conversion process and report the error.Use
     *         {@link ImportDisplayStatus} status type for messages to be
     *         displayed to the user at the end of the complete conversion of
     *         the XPDL.For more details see {@link ImportDisplayStatus}
     */
    public abstract IStatus packageSeparationComplete(
            Collection<Package> studioIProcessPackages, IProgressMonitor monitor);

    /**
     * Invoked after the in-memory conversion to Business Studio XPDL for the
     * BPM destination environment is complete (all conversion extension
     * contributions have been executed and the in-memory models are ready to
     * commit to the workspace).
     * 
     * @param studioBPMPackages
     *            The set of process packages (each containing a single
     *            process/process interface configured as if for the BPM
     *            destination environment).
     * @param executedExtensions
     *            The conversion extensions that were executed; as each is
     *            created for a given conversion-run and persisted until this
     *            method is called, it is possible to preserve information
     *            during their execution to gather here when needed.
     * @param monitor
     *            Standard Eclipse monitor for progress status updates (see
     *            {@link IProgressMonitor} for more information. This method
     *            should call at least monitor.beginTask() at the start of the
     *            method and monitor.done() at the end. To overwrite the current
     *            progress status message completely, please use only
     *            monitor.subTask(string).
     * 
     * @return IStatus.OK is the nominal return, Istatus.INFO / WARNING can be
     *         used to return information messages (that will be logged with
     *         debug mode switched on). IStatus.ERROR status will stop the
     *         conversion process and report the error.Use
     *         {@link ImportDisplayStatus} status type for messages to be
     *         displayed to the user at the end of the complete conversion of
     *         the XPDL.For more details see {@link ImportDisplayStatus}
     */
    public abstract IStatus conversionExtensionsComplete(
            Collection<Package> studioBPMPackages,
            Collection<AbstractIProcessToBPMContribution> executedExtensions,
            IProgressMonitor monitor);

    /**
     * Invoked when the conversion is complete and the final XPDL file set has
     * been committed to the workspace (including ignoring of incoming XPDL for
     * which processes already exist in the workspace)
     * 
     * @param finalFileSet
     *            The final file set. The set of converted processes for the BPM
     *            destination environments.
     * @param studioBPMPackages
     *            The set of process packages (each containing a single
     *            process/process interface configured as if for the BPM
     *            destination environment).
     * @param ignoredDuplicatePackages
     *            The set of the final processes that were ignored because there
     *            is an existing BPM destination process in the workspace with
     *            the same name.
     * @param monitor
     *            Standard Eclipse monitor for progress status updates (see
     *            {@link IProgressMonitor} for more information. This method
     *            should call at least monitor.beginTask() at the start of the
     *            method and monitor.done() at the end. To overwrite the current
     *            progress status message completely, please use only
     *            monitor.subTask(string).
     * 
     * @return IStatus.OK is the nominal return, Istatus.INFO / WARNING can be
     *         used to return information messages (that will be logged with
     *         debug mode switched on). IStatus.ERROR status will stop the
     *         conversion process and report the error.Use
     *         {@link ImportDisplayStatus} status type for messages to be
     *         displayed to the user at the end of the complete conversion of
     *         the XPDL.For more details see {@link ImportDisplayStatus}
     */
    public abstract IStatus conversionComplete(Collection<IFile> finalFileSet,
            Collection<Package> studioBPMPackages,
            Collection<Package> ignoredDuplicatePackages,
            IProgressMonitor monitor);

}
