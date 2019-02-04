/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.xml.namespace.QName;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.amf.model.daaspec.DistributedApplicationArchiveDescriptor;
import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.ComponentTypeService;
import com.tibco.amf.sca.componenttype.CompositeModelBuilder;
import com.tibco.amf.sca.model.componenttype.BindingAdjunct;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.ComponentService;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.composite.PromotedReference;
import com.tibco.amf.sca.model.composite.PromotedService;
import com.tibco.amf.sca.model.composite.Wire;
import com.tibco.amf.sca.model.extensionpoints.Binding;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.amf.sca.model.extensionpoints.PolicySet;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.amf.sca.model.extensionpoints.Wsdl11Interface;
import com.tibco.amf.sca.model.extensionpoints.impl.SCAExtensionsPackageImpl;
import com.tibco.amf.sca.model.externalpolicy.ExternalPolicySetContainerDocument;
import com.tibco.amf.sca.model.systempolicy.PlatformIntents;
import com.tibco.amf.tools.packager.IPackagerService;
import com.tibco.amf.tools.packager.PackagerPlugin;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;
import com.tibco.corona.binding.bindingsoapmodel.SOAPServiceBinding;
import com.tibco.neo.svar.svarmodel.SubstitutionDataType;
import com.tibco.neo.svar.svarmodel.SubstitutionVariable;
import com.tibco.neo.svar.svarmodel.SubstitutionVariables;
import com.tibco.neo.svar.svarmodel.SvarmodelFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.bom.gen.internal.BOMGenProjectBuilder;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.ICustomCompositeContributor;
import com.tibco.xpd.daa.internal.BaseProjectDAAGenerator;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.preferences.DAAGenPreferences;
import com.tibco.xpd.daa.internal.util.CompositeContributorsExtensionManager;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.daa.internal.util.DAANamingUtils;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.n2.daa.internal.packager.CustomFeaturePackagerParticipant;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.daa.utils.PolicySetUtil;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdl2.Participant;

/**
 * Generates SCA composite and DAA archive for a specified eclipse project.
 * 
 * @author Jan Arciuchiewicz
 */
public class ProjectDAAGenerator extends BaseProjectDAAGenerator {

    private static final String SCA_NS = "http://www.osoa.org/xmlns/sca/1.0"; //$NON-NLS-1$

    QName transactedOneWay = new QName(SCA_NS, "transactedOneWay"); //$NON-NLS-1$

    QName managedGlobalTransaction =
            new QName(SCA_NS, "managedTransaction.global"); //$NON-NLS-1$

    private static final String PE_IMPL_TYPE = "implementation.bx"; //$NON-NLS-1$

    QName atLeastOnce = new QName(SCA_NS, "atLeastOnce"); //$NON-NLS-1$

    /** */
    private static final String SVAR_SUBSTVAR_BINDING_FILE = "svar.substvar"; //$NON-NLS-1$

    /** Shared singleton instance. */
    private static final ProjectDAAGenerator INSTANCE =
            new ProjectDAAGenerator();

    public static final Logger LOG = Activator.getDefault().getLogger();

    private final static String PLATFORM_PLUGIN_RES_URI_PREFIX =
            "platform:/plugin/" //$NON-NLS-1$
                    + Activator.PLUGIN_ID + "/resources/"; //$NON-NLS-1$

    /**
     * Enables mapping through substitution variables. If set to true all
     * referenced resource instances will be using substitution variables
     * instead mapping directly to values.
     */
    private boolean mapThroughSubVars = false;

    /**
     * Private constructor. Use {@link #getInstance()} to obtain shared
     * instance.
     */
    protected ProjectDAAGenerator() {
    }

    /**
     * Gets reference to the shared instance.
     * 
     * @return
     */
    public static ProjectDAAGenerator getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("restriction")
    protected String getQualifierReplacement(IProject project) {

        if (BOMUtils.isBusinessDataProject(project)) {

            return BOMUtils.getBuildVersionTimeStamp(project);
        }
        return ProjectUtil2.getAutogeneratedQualifier();
    }

    /**
     * Generates the SCA composite and DAA archive for a specified project.
     * 
     * @param project
     *            the XPD project.
     * @param monitor
     *            the monitor to report the progress.
     * @param replaceWithTS
     *            flag to decide whether qualifier must be replaced with time
     *            stamp
     */
    @Override
    public IStatus generateDAA(IProject project, final IProgressMonitor monitor,
            final boolean replaceWithTS) {

        IChangeRecorder changeRecorder = new AmxBpmChangeRecorder();
        try {
            monitor.beginTask(String.format(
                    Messages.ProjectDAAGenerator_GeneratingDAAFor_message,
                    project.getName()), 2);

            // System.err
            // .println("***************START*******************************");
            // //$NON-NLS-1$

            long beforeWholeTimeMillis = System.currentTimeMillis();
            // JA: Cleaning was moved outside of workspace modify operation
            // (into MultiProjectDAAGenerationWithProgress) to process resource
            // deletion notifications working copies depends on.
            // clean(project, new NullProgressMonitor());

            IFolder compositeOut =
                    N2PENamingUtils.getModuleOutputFolder(project, true);
            Composite[] compositeArray = new Composite[1];
            long beforeCompositeTimeMillis = System.currentTimeMillis();
            String timeStamp = getQualifierReplacement(project);
            DAANamingUtils.setTimeStampOnProject(project, timeStamp);

            // TODO JA: Workaround to avoid CDS full project builds called form
            // AMX code.
            // CDS composite contributor replaces "qualifier" with the "TS" and
            // if CDS full build happens after that and before custom feature is
            // built then DAAgen fails (as the expected "TS" is replaced back to
            // "qualifier").
            // The builds are switch ON again in the finally clause.
            BOMGenProjectBuilder.setNoFullBuilds(true);

            Collection<String> featureIDsRegistered = new ArrayList<String>();

            IStatus status = buildComposite(project,
                    compositeOut,
                    compositeArray,
                    SubProgressMonitorEx.createNestedSubProgressMonitor(monitor,
                            1),
                    timeStamp,
                    replaceWithTS,
                    changeRecorder,
                    featureIDsRegistered);

            // TODO Remove when SOAPBinding will stop changing file system
            // outside of workspace. Refresh ".bpm" for the time being.
            compositeOut.refreshLocal(IResource.DEPTH_INFINITE, null);

            long afterCompositeTimeMillis = System.currentTimeMillis();
            long timeTakenToGenerateComposite =
                    afterCompositeTimeMillis - beforeCompositeTimeMillis;
            LOG.debug("The time taken to generate a Composite " //$NON-NLS-1$
                    + timeTakenToGenerateComposite + " milliseconds"); //$NON-NLS-1$

            if (status.getSeverity() > IStatus.WARNING) {
                clean(project, new NullProgressMonitor());
                MultiStatus compositeBuildStatus = new MultiStatus(
                        Activator.PLUGIN_ID, 0,
                        Messages.ProjectDAAGenerator_CompositeGenSkipped_message,
                        null);
                compositeBuildStatus.add(status);
                return compositeBuildStatus;
            }

            if (compositeArray[0] != null) {
                // build DAA
                String compositeFilePath = compositeOut.getFullPath()
                        .append(CompositeUtil.getCompositeName(project))
                        .toPortableString();
                IFile compositeResource = ResourcesPlugin.getWorkspace()
                        .getRoot().getFile(new Path(compositeFilePath));

                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(compositeResource).reLoad();

                long beforeDAATimeMillis = System.currentTimeMillis();

                IStatus daaBuildStatus = buildDAA(project,
                        compositeArray[0],
                        compositeResource,
                        compositeOut,
                        SubProgressMonitorEx
                                .createNestedSubProgressMonitor(monitor, 1),
                        timeStamp,
                        featureIDsRegistered);
                // Set svar file as derived.
                if (compositeResource != null
                        && compositeResource.isAccessible()) {
                    IFile svarFile = compositeResource.getParent()
                            .getFile(new Path(SVAR_SUBSTVAR_BINDING_FILE));
                    if (svarFile.isAccessible()) {
                        svarFile.setDerived(true);
                    }
                }

                long afterDAATimeMillis = System.currentTimeMillis();
                long timeTakenForDAAGeneration =
                        afterDAATimeMillis - beforeDAATimeMillis;
                long timeTakenForWholeProcess =
                        afterDAATimeMillis - beforeWholeTimeMillis;

                System.err.println("*********The time taken to package DAA " //$NON-NLS-1$
                        + timeTakenForDAAGeneration + " milliseconds"); //$NON-NLS-1$
                // System.err
                // .println("*********The time taken for the whole process "
                // //$NON-NLS-1$
                // + timeTakenForWholeProcess + " milliseconds"); //$NON-NLS-1$
                // System.err
                // .println("***************END*******************************");
                // //$NON-NLS-1$

                if (status.getSeverity() > IStatus.OK) {
                    List<IStatus> children = new ArrayList<IStatus>();
                    children.add(status);
                    if (daaBuildStatus.getSeverity() > IStatus.OK) {
                        children.add(daaBuildStatus);
                    }
                    return new MultiStatus(Activator.PLUGIN_ID, 0,
                            children.toArray(new IStatus[children.size()]),
                            Messages.ProjectDAAGenerator_3, null);
                }
                return daaBuildStatus;

            } else {
                return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                        String.format(
                                Messages.ProjectDAAGenerator_CompositeGenForProjectProblem_message,
                                project));
            }
        } catch (CoreException e) {
            LOG.error(e);
            return new Status(IStatus.ERROR, Activator.PLUGIN_ID, String.format(
                    Messages.ProjectDAAGenerator_CompositeGenForProjectError_message,
                    project), e);
        } finally {

            // TODO JA: Workaround to allow again CDS full builds.
            BOMGenProjectBuilder.setNoFullBuilds(false);

            changeRecorder.revertProjectChanges(project);
            // As the custom feature file refers to plug-ins with updated
            // versions (qualifier replaced with TS, which has been reverted
            // back) so validation problems are reported. To avoid any
            // confusion, delete custom feature file
            /*
             * in case of global data there will be two custom feature files
             * (*.model.bds.customfeature and *.da.bds.customfeature). going
             * thru all the files under .bpm folder to get all custom feature
             * files and remove them
             */
            N2PENamingUtils.deleteFileUnderBpmFolder(project);

            // reset all project versions
            monitor.done();
        }
    }

    private IStatus buildComposite(final IProject project,
            final IFolder outFolder, Composite[] createdCompositeArray,
            IProgressMonitor monitor, final String timeStamp,
            final boolean replaceWithTS, IChangeRecorder changeRecorder,
            Collection<String> featureIDsRegistered) {

        WorkingCopy compositeWC = null;
        try {
            final List<CompositeContributor> compositeContributors =
                    CompositeContributorsExtensionManager.getInstance()
                            .getCompositeContributors(
                                    getCompositeContributorContext());

            monitor.beginTask(String.format(
                    Messages.ProjectDAAGenerator_BuildingComposite_message_2,
                    project.getName()), (compositeContributors.size() * 2) + 7);

            MultiStatus status = new MultiStatus(Activator.PLUGIN_ID, 0,
                    Messages.ProjectDAAGenerator_componentsProblems_message2,
                    null);

            for (CompositeContributor contributor : compositeContributors) {

                /* register any features to be added by contributor */
                if (contributor instanceof ICustomCompositeContributor) {
                    Collection<String> customFeatureIds =
                            ((ICustomCompositeContributor) contributor)
                                    .getCustomFeatureIds(project);
                    featureIDsRegistered.addAll(customFeatureIds);
                }

                /* Validate project before composite creation. */
                IStatus s = contributor.validate(project,
                        SubProgressMonitorEx
                                .createNestedSubProgressMonitor(monitor, 1));
                if (s.getSeverity() == IStatus.CANCEL) {
                    monitor.setCanceled(true);
                    return s;
                }
                if (s.getSeverity() > IStatus.OK) {
                    status.add(s);
                }
            }

            if (status.getSeverity() > IStatus.WARNING) {
                return new MultiStatus(Activator.PLUGIN_ID, 0,
                        status.getChildren(),
                        Messages.ProjectDAAGenerator_componentProblem_message,
                        null);
            }

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            final ComponentTypeActivator cts =
                    ComponentTypeActivator.getDefault();
            final CompositeModelBuilder mb = cts.getModelBuilder();

            final URI compositeLocation =
                    URI.createPlatformResourceURI(
                            outFolder.getFullPath()
                                    .append(CompositeUtil
                                            .getCompositeName(project))
                                    .toPortableString(),
                            true);
            final Composite composite = mb.createComposite(compositeLocation);

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            TransactionalEditingDomain editingDomain =
                    TransactionUtil.getEditingDomain(composite);

            /*
             * XPD-7030: Give chance to the Contributors to reset the name of
             * the composite as required.
             */
            resetCompositeName(composite,
                    compositeContributors,
                    project,
                    editingDomain);

            /*
             * Sid XPD-7543: Force load of working copy before we continue. REST
             * BT underlying stuff tries to get working copy to do things and
             * unless the adapters have been aded to EMF objects it cannot do
             * that.
             */
            compositeWC = WorkingCopyUtil.getWorkingCopyFor(composite, true);
            if (!(compositeWC instanceof AbstractWorkingCopy)) {
                LOG.error(new Exception(),
                        "Cannot force load of Composite working copy!"); //$NON-NLS-1$
            } else {
                /*
                 * Disable working copy notifciations otherwise we get thread
                 * locks (we were run from ui, composite model change notifies
                 * UI whilst UI is loading indexes for copied XSDs for the bpel
                 * folders etc).
                 */
                ((AbstractWorkingCopy) compositeWC)
                        .setNotificationsEnabled(false);
            }

            /* Contribute (and configure) components. */
            for (CompositeContributor contributor : compositeContributors) {
                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }

                IStatus s = contributor.contributeToComposite(project,
                        outFolder,
                        composite,
                        compositeLocation,
                        timeStamp,
                        replaceWithTS,
                        changeRecorder,
                        SubProgressMonitorEx
                                .createNestedSubProgressMonitor(monitor, 1));
                if (s.getSeverity() == IStatus.CANCEL) {
                    monitor.setCanceled(true);
                    return s;
                }
                if (s.getSeverity() > IStatus.OK) {
                    status.add(s);
                }
            }

            /* check if the composite is not empty */
            if (composite.getComponents().isEmpty()) {
                return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                        Messages.ProjectDAAGenerator_emptyComposite_message);
            }
            if (status.getSeverity() > IStatus.WARNING) {
                return new MultiStatus(Activator.PLUGIN_ID, 0,
                        status.getChildren(),
                        Messages.ProjectDAAGenerator_componentProblem_message,
                        null);
            }

            /*
             * Sid: Changed setting of composite version number inside
             * UnprotectedWriteOperation later (instaead of executing a command
             * to do it on comnmand stack!)
             */
            final String compositeVersion =
                    CompositeUtil.getVersionNumber(project);

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            /* Set up WRM policy (providing Client Authentication). */
            final PolicySet wrmPolicy = getCreateSharedPolicySet(outFolder,
                    "WRMPolicySetsResource.policysets"); //$NON-NLS-1$

            /*
             * Set up policy to make message delivery reliable (message always
             * send through message bus, and will be persisted).
             */
            final PolicySet atLeastOncePolicy =
                    getCreateSharedPolicySet(outFolder,
                            "AtLeastOncePolicy.policysets"); //$NON-NLS-1$

            /*
             * Set up policy to create thread pools for services to prevent fail
             * over See: XPD-2037.
             */

            /*
             * XPD-7154: thread policy that was being added to component
             * reference is moved to
             * createThreadingPolicyForComponentReference()
             */
            // final PolicySet outboundThreadingPolicy =
            // getCreateSharedPolicySet(outFolder,
            // "OutboundThreadingPolicy.policysets"); //$NON-NLS-1$

            final PolicySet prepareBeforeUndeployPolicy =
                    Activator.getDefault().getCreatePolicySet(outFolder,
                            Activator.PREPARE_BEFORE_UNDEPLOY_POLCICYSET);

            final PolicySet transactedOneWayPolicy =
                    getCreateSharedPolicySet(outFolder,
                            "TransactedOneWay.policysets"); //$NON-NLS-1$

            final PolicySet managedTransactionPolicy =
                    getCreateSharedPolicySet(outFolder,
                            "ManagedTransaction.policysets"); //$NON-NLS-1$

            final PolicySet startServiceFirstPolicy =
                    getCreateSharedPolicySet(outFolder,
                            "StartServiceFirstPolicy.policysets"); //$NON-NLS-1$

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            /* Substitution Variables and Policies. */
            UnprotectedWriteOperation compositeAdditionalOp =
                    new UnprotectedWriteOperation(editingDomain) {
                        @SuppressWarnings("unchecked")
                        @Override
                        protected Object doExecute() {
                            /*
                             * Sid moved from above setCommand on command stack.
                             */
                            composite.setVersion(compositeVersion);
                            /**
                             * XPD-7030: The code for setting of Composite name
                             * for BDS projects is moved to
                             * {@link BDSCompositeContributor#resetCompositeName(Composite, IProject)}
                             */

                            /**
                             * Sid XPD-5193. Re-instated code to identify
                             * concrete outbound SOAP/HTTP and SOAP/JMS bindings
                             * to support new policy configuration
                             * 
                             * - Virtualised outbound wires will continue to
                             * have the At Least Once policy set applied (and
                             * will not have threading policy applied).
                             * 
                             * - Concrete SOAP/HTTP and SOAP/JMS outbound wires
                             */
                            Set<ComponentReference> concreteOutboundServiceBindings =
                                    new HashSet<ComponentReference>();
                            for (PromotedReference promotedRef : composite
                                    .getReferences()) {
                                if (!promotedRef.getBindings().isEmpty()) {
                                    concreteOutboundServiceBindings.addAll(
                                            promotedRef.getPromotions());
                                }
                            }

                            for (Component comp : composite.getComponents()) {
                                /*
                                 * Add PREPARE_BEFORE_UNDEPLOY intent and policy
                                 * for all components.
                                 */
                                if (!shouldSuppressPrepareBeforeUndeplyPolicy(
                                        comp.getName())) {
                                    /*
                                     * XPD-7030 : Only add the prepare before
                                     * undeploy policy if we have not explicitly
                                     * Suppressed it from the composite
                                     * contribution.
                                     */
                                    List<QName> qNameList = (List<QName>) comp
                                            .eGet(SCAExtensionsPackageImpl.Literals.POLICY_ENABLED__INTENTS,
                                                    true);
                                    qNameList.add(
                                            PlatformIntents.PREPARE_BEFORE_UNDEPLOY);
                                    comp.getPolicySets()
                                            .add(prepareBeforeUndeployPolicy);
                                }

                                for (ComponentService compService : comp
                                        .getServices()) {
                                    /*
                                     * XPD-6039: The requestOnly condition
                                     * should be checked per service (port type)
                                     * not whole component.
                                     */
                                    if (isRequestOnlyService(compService)) {

                                        compService.getIntents()
                                                .add(transactedOneWay);
                                        compService.getPolicySets()
                                                .add(transactedOneWayPolicy);
                                    } else {
                                        compService.getIntents()
                                                .add(atLeastOnce);
                                        compService.getPolicySets()
                                                .add(atLeastOncePolicy);
                                    }
                                    /*
                                     * XPD-6069: thread policy that was being
                                     * added to component service is moved to
                                     * PECompositeContributor
                                     */
                                }
                                /*
                                 * XPD-6039 The 'managedTransactionPolicy'
                                 * policy is no longer applied on the PE
                                 * components as 'transactedOneWay' should not
                                 * require it (the SDS validation needs to be
                                 * disabled, see:
                                 * com.tibco.xpd.daa.internal.validation
                                 * .EmfValidationDisablerStartup).
                                 */
                                // if (isAllComponentServiceRequestOnly
                                // && isN2PEComponent(comp)) {
                                // comp.getPolicySets()
                                // .add(managedTransactionPolicy);
                                // comp.getIntents()
                                // .add(managedGlobalTransaction);
                                // }

                            }

                            /*
                             * Go thru all outbound service invocations (wires)
                             * referenced from each compontent in composite
                             * adding necessary policysets to outbound wires.
                             */
                            for (Component comp : composite.getComponents()) {
                                for (ComponentReference compRef : comp
                                        .getReferences()) {

                                    /*
                                     * XPD-5193 Virtualised outbound wires will
                                     * have the At Least Once policy set applied
                                     * (and will not have threading policy
                                     * applied).
                                     */
                                    if (!concreteOutboundServiceBindings
                                            .contains(compRef)) {
                                        compRef.getIntents().add(atLeastOnce);
                                        compRef.getPolicySets()
                                                .add(atLeastOncePolicy);

                                        /*
                                         * XPD-3726 Also adding
                                         * StartServiceFirst policy to all
                                         * references without binding
                                         * (virtualized).
                                         */
                                        compRef.getPolicySets()
                                                .add(startServiceFirstPolicy);

                                    } else {
                                        /*
                                         * XPD-5193 Concrete SOAP/HTTP and
                                         * SOAP/JMS outbound wires will have the
                                         * OutboundThreadingPolicySet added.
                                         */
                                        PolicySet outboundThreadingPolicy =
                                                createThreadingPolicyForComponentReference(
                                                        project,
                                                        outFolder,
                                                        compRef);
                                        compRef.getPolicySets()
                                                .add(outboundThreadingPolicy);
                                    }
                                }
                            }

                            /*
                             * XPD-2444 check for wires present in the composite
                             * model. If a wire's target has managedTransaction
                             * policy then just apply the same to the source
                             * (reference) & remove the atLeastOncePolicy
                             * applied just above
                             */
                            List<Wire> compositeWires = composite.getWires();
                            if (compositeWires != null) {

                                for (Wire wire : compositeWires) {

                                    ComponentReference source =
                                            wire.getSource();
                                    ComponentService target = wire.getTarget();
                                    List<PolicySet> sourcePolicySets =
                                            source.getPolicySets();
                                    List<PolicySet> targetPolicySets =
                                            target.getPolicySets();
                                    boolean contains = targetPolicySets
                                            .contains(transactedOneWayPolicy);
                                    if (contains) {

                                        sourcePolicySets
                                                .remove(atLeastOncePolicy);
                                        source.getIntents().remove(atLeastOnce);
                                        source.getIntents()
                                                .add(transactedOneWay);
                                        sourcePolicySets
                                                .add(transactedOneWayPolicy);
                                    }
                                }
                            }

                            // SoapBindingUtil
                            // .configCompositeInterfaceBindings(composite,
                            // timeStamp);

                            /*
                             * Apply WRM (authentication) policy (and intent) to
                             * bindings.
                             */
                            QName requiresUsernameToken = new QName(
                                    SCA_EXTENSIONS_NAMESPACE,
                                    CLIENT_AUTHENTICATION_USERNAME_TOKEN);

                            for (PromotedService promotedService : composite
                                    .getServices()) {
                                for (Binding binding : promotedService
                                        .getBindings()) {
                                    if (binding instanceof SOAPServiceBinding) {
                                        binding.getIntents()
                                                .add(requiresUsernameToken);
                                        binding.getPolicySets().add(wrmPolicy);
                                    }
                                }
                            }

                            if (mapThroughSubVars) {
                                SubstitutionVariables svars =
                                        introduceSubstitutionVariables(
                                                composite);
                                if (!svars.getSubstitutionVariables()
                                        .isEmpty()) {
                                    Resource compositeResource =
                                            composite.eResource();
                                    ResourceSet rs =
                                            compositeResource.getResourceSet();
                                    URI sVarResourceURI = URI.createURI(
                                            "./" + SVAR_SUBSTVAR_BINDING_FILE) //$NON-NLS-1$
                                            .resolve(
                                                    compositeResource.getURI());

                                    Resource oldRes =
                                            rs.getResource(sVarResourceURI,
                                                    false);
                                    if (oldRes != null) {
                                        if (oldRes.isLoaded()) {
                                            oldRes.unload();
                                        }
                                        rs.getResources().remove(oldRes);
                                    }

                                    Resource svarResource =
                                            rs.createResource(sVarResourceURI);
                                    svarResource.getContents().add(svars);
                                    try {
                                        svarResource.save(null);
                                        LOG.debug(
                                                "Created 'svar.substvar' file."); //$NON-NLS-1$
                                    } catch (IOException ioex) {
                                        ioex.printStackTrace();
                                    }
                                    return svars;
                                }
                            }

                            return null;
                        }

                    };

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            SubstitutionVariables svars =
                    (SubstitutionVariables) compositeAdditionalOp.execute();
            if (mapThroughSubVars) {
                if (svars != null) {
                    IFile file = WorkspaceSynchronizer
                            .getUnderlyingFile(svars.eResource());
                    if (file != null) {
                        try {
                            file.refreshLocal(IResource.DEPTH_ZERO, null);
                            // JA: Can't be derived as packager stops working.
                            // Probably
                            // the indexer is not working.
                            // file.setDerived(true);

                            WorkingCopy wc = XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(file);
                            if (wc != null) {
                                wc.reLoad();
                            }

                            LOG.debug(String.format("Local file" //$NON-NLS-1$
                                    + " '%s' refreshed.", file.getFullPath())); //$NON-NLS-1$
                        } catch (CoreException e) {
                            LOG.error(e);
                        }
                    }
                }
            }

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            // Saving composite.
            UnprotectedWriteOperation setContentsOp =
                    new UnprotectedWriteOperation(editingDomain) {
                        @Override
                        protected Object doExecute() {
                            try {
                                composite.eResource().save(null);
                                LOG.debug(String.format //
                                ("Composite " + "'%s' saved.", // //$NON-NLS-1$ //$NON-NLS-2$
                                        composite.eResource().getURI()
                                                .toString()));
                            } catch (IOException ioex) {
                                LOG.error(ioex);
                            }
                            return composite;
                        }
                    };
            setContentsOp.execute();

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            if (composite != null) {
                IFile file = WorkspaceSynchronizer
                        .getUnderlyingFile(composite.eResource());
                if (file != null) {
                    try {
                        file.refreshLocal(IResource.DEPTH_ZERO, null);
                        file.setDerived(true);
                        LOG.debug(String.format("Local file" //$NON-NLS-1$
                                + " '%s' refreshed.", file.getFullPath())); //$NON-NLS-1$
                    } catch (CoreException e) {
                        LOG.error(e);
                    }
                }
            }

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            monitor.worked(1);

            createdCompositeArray[0] = composite;
            return status;

        } finally {
            /* Sid XPD-7543 */
            if (compositeWC instanceof AbstractWorkingCopy) {
                ((AbstractWorkingCopy) compositeWC)
                        .setNotificationsEnabled(true);
            }
            monitor.done();
        }
    }

    /**
     * Gives chance to the Contributors to reset the name of the composite as
     * required.
     * 
     * @param composite
     * @param project
     * @param compositeContributors
     * @param editingDomain
     */
    private void resetCompositeName(final Composite composite,
            final List<CompositeContributor> compositeContributors,
            final IProject project, TransactionalEditingDomain editingDomain) {

        UnprotectedWriteOperation resetCompositeName =
                new UnprotectedWriteOperation(editingDomain) {
                    @Override
                    protected Object doExecute() {
                        /*
                         * Give chance to the Contributors to reset the name of
                         * the composite as required.
                         */
                        for (CompositeContributor contributor : compositeContributors) {
                            contributor.resetCompositeName(composite, project);
                        }
                        return null;
                    }
                };
        resetCompositeName.execute();
    }

    /**
     * @param componentName
     * @return <code>true</code> if the component name matches with the
     *         component name for whome the 'prepare before undeploy' policy
     *         sets should be suppressed, else return <code>false</code>
     */
    @SuppressWarnings("restriction")
    protected boolean shouldSuppressPrepareBeforeUndeplyPolicy(
            String componentName) {

        List<Pattern> compiledSuppressPrepareBeforeUndeployPolicyPatterns =
                CompositeContributorsExtensionManager.getInstance()
                        .getCompiledSuppressPrepareBeforeUndeployPolicyPatterns();

        for (Pattern pattern : compiledSuppressPrepareBeforeUndeployPolicyPatterns) {

            if (pattern.matcher(componentName).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates the threading policies for component reference(s) (consumers)in
     * the composite. Time out threading policy is created for soap jms invoker
     * if time out is configured.
     * 
     * @param project
     * @param stagingArea
     * @param componentRef
     */
    private PolicySet createThreadingPolicyForComponentReference(
            IProject project, IContainer stagingArea,
            ComponentReference componentRef) {

        /*
         * Set up policy to create thread pools for services to prevent fail
         * over. See: XPD-2037.
         */

        /*
         * XPD-6974: get the invocation time out value from participant shared
         * resource. If the invocation time out is specified then create policy
         * set with invocation time out value, else create the standard policy
         * set
         */
        Participant participantForReference = N2PECompositeUtil
                .getParticipantForReference(project, componentRef);
        if (null != participantForReference) {
            WsOutbound wsOutbound = SharedResourceUtil
                    .getWsResourceOutbound(participantForReference);
            if (wsOutbound != null && wsOutbound.getSoapJmsBinding() != null) {
                WsSoapJmsOutboundBinding soapJmsBinding =
                        wsOutbound.getSoapJmsBinding();
                boolean isInvocationTimeoutSet =
                        soapJmsBinding.isSetInvocationTimeout();
                if (isInvocationTimeoutSet) {
                    /*
                     * get the invocation timeout from soap jms binding
                     */
                    Integer invocationTimeout =
                            soapJmsBinding.getInvocationTimeout();

                    if (null != invocationTimeout.toString()) {

                        PolicySet outboundTimeoutThreadingPolicy =
                                getPolicySetForOutboundTimeoutConfiguration(
                                        participantForReference,
                                        stagingArea,
                                        invocationTimeout.toString());
                        if (null != outboundTimeoutThreadingPolicy) {

                            return outboundTimeoutThreadingPolicy;
                        }
                    }
                }
            }
        }
        /*
         * if timeout is not specified and timeout policy is not added, then add
         * the standard policy set
         */
        final PolicySet outboundThreadingPolicy =
                getCreateSharedPolicySet(stagingArea,
                        "OutboundThreadingPolicy.policysets"); //$NON-NLS-1$

        return outboundThreadingPolicy;
    }

    /**
     * 
     * @param procPkg
     * @param stagingArea
     * @param invocationTimeoutValue
     * @return the policy set with time out configuration values replaced in the
     *         template using the substitution variables
     */
    private PolicySet getPolicySetForOutboundTimeoutConfiguration(
            Participant participant, IContainer stagingArea,
            String invocationTimeoutValue) {

        Map<String, String> substitutions = new HashMap<String, String>();

        IFolder policyFolder = ((IFolder) stagingArea).getFolder("resources"); //$NON-NLS-1$

        if (null != participant && null != participant.getId()) {

            String participantId = participant.getId();
            String threadPolicyTemplate = PolicySetUtil.getTemplate(
                    "OutboundThreadingPolicyWithTimeout.policysets", //$NON-NLS-1$
                    PLATFORM_PLUGIN_RES_URI_PREFIX);
            /*
             * ThreadingPolicy.policysets file must be created with
             * ThreadingPolicy_<participant_id>.policysets
             */
            String threadPolicyFileName = String.format(
                    "OutboundThreadingPolicyWithTimeout_%1$s.policysets", //$NON-NLS-1$
                    participantId);
            /*
             * sca:policySet/Name must be suffixed with _<participant_id>
             */
            substitutions.put("$$PolicySetName$$", //$NON-NLS-1$
                    String.format("Outbound_ThreadingPolicy_WRMComponent_%1$s", //$NON-NLS-1$
                            participantId));
            /*
             * systempolicy:threadingPolicy/scaext:label will be suffixed with
             * the <participant_id>
             */
            substitutions.put("$$ThreadingPolicyLabel$$", //$NON-NLS-1$
                    String.format("Outbound Threading Policy %1$s", //$NON-NLS-1$
                            participantId));

            /* replace the time out value */
            substitutions.put("$$invocationTimeOut$$", invocationTimeoutValue); //$NON-NLS-1$

            if (null != threadPolicyTemplate) {

                PolicySet timeoutThreadPolicySet = PolicySetUtil
                        .getCreatePolicySetFromTemplate(policyFolder,
                                threadPolicyFileName,
                                threadPolicyTemplate,
                                substitutions);
                return timeoutThreadPolicySet;
            }

        }
        return null;
    }

    /**
     * 
     * @param compService
     * @return
     */
    protected boolean isRequestOnlyService(ComponentService compService) {
        if (compService == null || compService.getInterface() == null) {
            return false;
        }
        PortType portType =
                ((Wsdl11Interface) compService.getInterface()).getPortType();
        if (portType == null) {
            return false;
        }
        List wsdlOperationList = portType.getOperations();
        if (wsdlOperationList == null || wsdlOperationList.isEmpty()) {
            return false;
        }
        for (Object wsdlOpr : wsdlOperationList) {
            Operation wsdlOperation = (Operation) wsdlOpr;
            Output output = wsdlOperation.getOutput();
            if (output != null) {
                return false;
            }
        }
        return true;
    }

    private SubstitutionVariables introduceSubstitutionVariables(
            Composite composite) {

        SvarmodelFactory svarFactory = SvarmodelFactory.eINSTANCE;

        SubstitutionVariables svars = svarFactory.createSubstitutionVariables();

        for (PromotedReference ref : composite.getReferences()) {
            for (BindingAdjunct bindingAdjunct : ref.getBindingAdjuncts()) {
                for (Property p : bindingAdjunct.getProperties()) {
                    if (shouldSubstitute(p)) {
                        createSubstitutionVariableWithReference(ref.getName(),
                                p,
                                svars);
                    }
                }
            }
        }
        {
            for (PromotedService service : composite.getServices()) {
                for (BindingAdjunct bindingAdjunct : service
                        .getBindingAdjuncts()) {
                    for (Property p : bindingAdjunct.getProperties()) {
                        if (shouldSubstitute(p)) {
                            createSubstitutionVariableWithReference(service
                                    .getName(), p, svars);
                        }
                    }
                }
            }
        }
        for (Property p : composite.getProperties()) {
            if (shouldSubstitute(p)) {
                createSubstitutionVariableWithReference(p.getName(), p, svars);
            }
        }
        return svars;
    }

    private boolean shouldSubstitute(Property property) {
        QName type = property.getType();
        String value = property.getSimpleValue();
        if (N2PECompositeUtil.SUBSTITUABLE_PROPERTY_TYPES.contains(type)
                && !N2PECompositeUtil.SVAR_PATTERN.matcher(value).matches()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private SubstitutionVariable createSubVar(SubstitutionVariables svars,
            String name, String defaultValue) {
        SubstitutionVariable svar =
                SvarmodelFactory.eINSTANCE.createSubstitutionVariable();
        String varName = getUniqueName(name, svars);
        svar.setName(varName);
        svar.setDescription(String.format(
                Messages.ProjectDAAGenerator_svarDesctiption_message,
                varName));
        svar.setType(SubstitutionDataType.STRING_LITERAL);
        svar.setDefaultValue(defaultValue);
        svars.getSubstitutionVariables().add(svar);
        return svar;
    }

    private void createSubstitutionVariableWithReference(String name,
            Property p, SubstitutionVariables svars) {
        SubstitutionVariable svar =
                createSubVar(svars, name, p.getSimpleValue());
        String subst = "%%" + svar.getName() + "%%"; //$NON-NLS-1$ //$NON-NLS-2$
        p.setSimpleValue(subst);
    }

    @SuppressWarnings("unchecked")
    private String getUniqueName(String name, SubstitutionVariables svars) {
        HashSet<String> usedNames = new HashSet<String>();
        for (SubstitutionVariable v : (EList<SubstitutionVariable>) svars
                .getSubstitutionVariables()) {
            usedNames.add(v.getName());
        }
        for (int i = 1; usedNames.contains(name); i++) {
            name = name + i;
        }
        return name;
    }

    private IStatus buildDAA(IProject project, Composite composite,
            IFile compositeFile, IFolder outFolder, IProgressMonitor monitor,
            String timeStamp, Collection<String> featureIDsRegistered) {
        try {
            monitor.beginTask("", 3); //$NON-NLS-1$

            if (!containsDaaErrors(project)) {
                IPackagerService packagerService =
                        PackagerPlugin.getDefault().getPackagerService();

                IFile[] modelFiles = new IFile[1];
                // TODO place new version info properties file here as another
                // modelFile ?!?
                modelFiles[0] = compositeFile;

                String[] featureIds = null;
                if (isCustomFeatureFilesAvailable(project,
                        featureIDsRegistered)) {

                    String msg =
                            "Unexpected .customfeature files found: BOM jar caching is enabled, therefore expected their features to have been prebuilt."; //$NON-NLS-1$
                    LOG.debug(msg);

                    // PAA: would like to know if situation ever occurs
                    assert (!DAAGenPreferences.shouldCacheBomJars()) : msg;

                    featureIds = featureIDsRegistered
                            .toArray(new String[featureIDsRegistered.size()]);
                }

                DistributedApplicationArchiveDescriptor generateDaaDescriptor;
                try {
                    generateDaaDescriptor =
                            packagerService.generateDaaDescriptor(modelFiles,
                                    false,
                                    featureIds,
                                    new NullProgressMonitor());
                    generateDaaDescriptor.setQualifier(timeStamp);
                    String pathToAppend = ProjectUtil.getProjectId(project);
                    pathToAppend = pathToAppend + "." //$NON-NLS-1$
                            + DAANamingUtils.DAA_FILE_EXTENSION;
                    IPath daaFilePath =
                            outFolder.getFullPath().append(pathToAppend);
                    generateDaaDescriptor.setDaaSaveLocationInWorkspace(
                            daaFilePath.toPortableString());

                    Set<String> sharedResources = getSharedResources(composite);
                    if (sharedResources != null) {
                        generateDaaDescriptor.getPackagedApplications().get(0)
                                .getSharedResources().addAll(sharedResources);
                    }

                    // JA: It should not be necessary to add this resource.
                    //
                    // generateDaaDescriptor.getPackagedApplications().get(0)
                    // .getSharedResources()
                    // .add("_compiled_svar.substvar"); //$NON-NLS-1$
                    Map<String, Object> optionsMap =
                            new HashMap<String, Object>();
                    optionsMap.put(
                            IPackagerService.PLUGIN_BUILD_SCHEDULING_RULE,
                            new MutexRule());

                    // TODO JA: Replace with constant when the it will be in
                    // Target
                    // Platform.
                    // optionsMap.put(IPackagerService.WAIT_FOR_AUTOBUILD_JOBS,
                    // Boolean.FALSE);

                    monitor.worked(1);

                    optionsMap.put("packager.service.wait.for.autobuild.jobs", //$NON-NLS-1$
                            Boolean.FALSE);

                    // JA: Needs to be called to prevent addition of the custom
                    // feature multiple times.
                    CustomFeaturePackagerParticipant.reset();

                    IStatus status = packagerService.generateDaa(
                            generateDaaDescriptor,
                            optionsMap,
                            SubProgressMonitorEx
                                    .createSubTaskProgressMonitor(monitor, 1));

                    if (!status.isOK()) {
                        if (status.getException() != null) {
                            System.err.println("DAA Status exception is " //$NON-NLS-1$
                                    + status.getException().toString());
                            XpdResourcesPlugin.getDefault().getLogger().error(
                                    status.getException(),
                                    "DAA Status exception is " //$NON-NLS-1$
                                            + status.getException().toString()
                                            + " in plugin " //$NON-NLS-1$
                                            + status.getPlugin());
                        }
                        String message = status.getMessage();
                        System.err.println("DAA Status message is " + message); //$NON-NLS-1$
                        XpdResourcesPlugin.getDefault().getLogger().error(
                                "DAA Packager Status message: " + message); //$NON-NLS-1$
                    } else {
                        // TODO place new version info properties file here
                        // using ZipOutputStream or some such ?!?
                    }

                    IFile daaFile = project.getWorkspace().getRoot()
                            .getFile(daaFilePath);
                    if (daaFile != null && daaFile.exists()) {
                        daaFile.setDerived(true);
                    }
                    // cleanDaabinFolder(project, new NullProgressMonitor());
                    monitor.worked(1);
                    return status;

                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }

            }
            return new Status(IStatus.ERROR, Activator.PLUGIN_ID, String.format(
                    Messages.ProjectDAAGenerator_CompositeGenProjectErrorsExists_message,
                    project));

        } finally {
            monitor.done();
        }
    }

    public void cleanAllProjects(IProgressMonitor monitor)
            throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        int work = root.getProjects().length * 10;
        monitor.beginTask(Messages.ProjectDAAGenerator_cleaningProjects_message,
                work);
        try {
            for (IProject project : root.getProjects()) {
                if (monitor.isCanceled())
                    throw new OperationCanceledException();
                if (project.isAccessible()
                        && ProjectUtil.isStudioProject(project)
                        && GlobalDestinationUtil.isGlobalDestinationEnabled(
                                project,
                                N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                    clean(project, new NullProgressMonitor());
                    monitor.worked(1);
                }
            }
        } finally {
            monitor.done();
        }

    }

    private static Set<String> getSharedResources(Composite composite) {
        HashSet<String> sharedResources = new HashSet<String>();
        List<PromotedReference> references = composite.getReferences();
        for (PromotedReference reference : references) {
            EList<BindingAdjunct> bindingAdjuncts =
                    reference.getBindingAdjuncts();
            for (BindingAdjunct adjunct : bindingAdjuncts) {
                EList<Property> properties = adjunct.getProperties();
                for (Property p : properties) {
                    if (p.getType().getNamespaceURI()
                            .equals(BxCompositeCoreConstants.HTTP_SR_NS)) {
                        String resourceName = p.getSimpleValue();
                        if (resourceName != null
                                && !N2PECompositeUtil.SVAR_PATTERN
                                        .matcher(resourceName).matches()) {
                            // It's not a substitution variable.
                            sharedResources.add(resourceName);
                        }
                    }
                }
            }
        }
        return sharedResources;
    }

    private boolean containsDaaErrors(IProject project) {
        try {
            if (N2PENamingUtils.getAllDaaErrorMarkers(project,
                    IResource.DEPTH_ZERO).length > 0) {
                return true;
            }
        } catch (CoreException e) {
            LOG.error(e);
        }
        return false;
    }

    private static class MutexRule implements ISchedulingRule {
        @Override
        public boolean isConflicting(ISchedulingRule rule) {
            return rule == this;
        }

        @Override
        public boolean contains(ISchedulingRule rule) {
            return rule == this;
        }
    }

    /**
     * Tests to see whether a .customfeature files exist for the feature IDs
     * declared
     * 
     * @param project
     * @return
     */
    private static boolean isCustomFeatureFilesAvailable(IProject project,
            Collection<String> featureIDsRegistered) {
        if (project == null || !project.isAccessible()) {
            return false;
        }
        IFolder compositeOut =
                N2PENamingUtils.getModuleOutputFolder(project, false);
        if (compositeOut == null || !compositeOut.isAccessible()) {
            return false;
        }

        for (String featureId : featureIDsRegistered) {
            String customFeatureFileName =
                    N2PENamingUtils.getCustomFeatureFileName(featureId);
            IFile file = compositeOut.getFile(customFeatureFileName);
            if (file == null || !file.isAccessible()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 
     * @param stagingArea
     * @param policyFileName
     * @return
     */
    private static PolicySet getCreateSharedPolicySet(IContainer stagingArea,
            String policyFileName) {
        IFolder policyFolder = ((IFolder) stagingArea).getFolder("resources"); //$NON-NLS-1$
        try {
            if (!policyFolder.exists()) {
                ProjectUtil.createFolder(policyFolder, true, null);
            }
            IFile policyFile = policyFolder.getFile(policyFileName);
            if (!policyFile.exists()) {

                InputStream policyInputStream =
                        new ResourceSetImpl().getURIConverter()
                                .createInputStream(URI.createURI(
                                        PLATFORM_PLUGIN_RES_URI_PREFIX
                                                + policyFileName));
                // policyFile.create(policyInputStream, IResource.FORCE
                // | IResource.DERIVED, null);
                policyFile.create(policyInputStream, IResource.FORCE, null);
            }
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(policyFile);
            // wc.reLoad();
            EObject root = wc.getRootElement();
            if (root instanceof ExternalPolicySetContainerDocument) {
                ExternalPolicySetContainerDocument policyContainerDocument =
                        (ExternalPolicySetContainerDocument) root;
                return policyContainerDocument.getPolicySetContainer()
                        .getEmbeddedPolicySets().get(0);
            }

        } catch (CoreException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
        return null;
    }

    private boolean isN2PEComponent(Component comp) {
        Implementation compImplementation = comp.getImplementation();
        if (compImplementation == null) {
            return false;
        }
        ComponentTypeService componentTypeService =
                ComponentTypeActivator.getDefault().getComponentTypeService();
        if (componentTypeService == null) {
            return false;
        }
        String implementationType =
                componentTypeService.getImplementationType(compImplementation);
        if (PE_IMPL_TYPE.equals(implementationType)) {
            return true;
        }
        return false;
    }

    /** Client authentication usernameToken intent. */
    private static final String CLIENT_AUTHENTICATION_USERNAME_TOKEN =
            "clientAuthentication.usernameToken"; //$NON-NLS-1$

    /** SCA extensions namespace. (Used for TIBCO's policy intents) */
    private static final String SCA_EXTENSIONS_NAMESPACE =
            "http://xsd.tns.tibco.com/amf/models/sca/extensions"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.daa.internal.BaseProjectDAAGenerator#getModuleOutputFolder(org.eclipse.core.resources.IProject,
     *      boolean)
     * 
     * @param project
     * @param create
     * @return
     */
    @Override
    public IFolder getModuleOutputFolder(IProject project, boolean create) {
        return N2PENamingUtils.getModuleOutputFolder(project, create);
    }

    /**
     * @see com.tibco.xpd.daa.internal.BaseProjectDAAGenerator#getCompositeContributorContext()
     * 
     * @return
     */
    @Override
    protected String getCompositeContributorContext() {
        return N2PENamingUtils.BPM_COMPOSITE_CONTRIBUTION_CONTEXT;
    }
}
