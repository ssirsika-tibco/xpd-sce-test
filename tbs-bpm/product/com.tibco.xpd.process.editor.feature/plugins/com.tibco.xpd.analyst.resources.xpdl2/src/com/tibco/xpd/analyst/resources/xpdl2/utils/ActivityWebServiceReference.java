package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.wsdl.Operation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.indexing.ActivityWebServiceReferenceIndexProvider;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility data class to make life easier for various users of activity web
 * service index.
 * 
 * 
 * @author aallway
 * @since 3.3 (22 Feb 2010)
 */
public class ActivityWebServiceReference {
    private String xpdlPath;

    private String activityId;

    private String activityUri;

    private String displayName;

    private String wsdlNamespace;

    private String portTypeNamespace;

    private String portName;

    private String portTypeName;

    private String operation;

    private String wsdlFileLocation;

    private String participantId;

    private String participantName;

    private Boolean isApiActivity;

    private String projectName;

    private String transportType;

    private Boolean isBusinessServiceInvoke;

    private ActivityWebServiceReference() {
    }

    private static final Logger LOG = Xpdl2ResourcesPlugin.getDefault()
            .getLogger();

    /**
     * Create activity web service reference information from previously stored
     * indexer item.
     * 
     * @param item
     * @return Class that stores information about activity web service
     *         reference.
     */
    public static ActivityWebServiceReference createActivityWebServiceReference(
            IndexerItem item) {
        ActivityWebServiceReference actWebSvcReference =
                new ActivityWebServiceReference();

        actWebSvcReference.projectName =
                item.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);

        actWebSvcReference.xpdlPath =
                item.get(IndexerServiceImpl.ATTRIBUTE_PATH);

        actWebSvcReference.activityId =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_ACTIVITY_ID);

        actWebSvcReference.activityUri = item.getURI();

        actWebSvcReference.displayName =
                item.get(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME);

        actWebSvcReference.wsdlNamespace =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_WSDL_NAMESPACE);

        actWebSvcReference.portTypeNamespace =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_PORT_TYPE_NAMESPACE);

        actWebSvcReference.portTypeName =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_PORT_TYPE_NAME);

        actWebSvcReference.portName =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_PORT_NAME);

        actWebSvcReference.operation =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_OPERATION_NAME);

        actWebSvcReference.wsdlFileLocation =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_WSDLFILE_LOCATION);

        actWebSvcReference.transportType =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_TRANSPORT_TYPE);

        actWebSvcReference.participantId =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_ENDPOINT_PARTICIPANT_ID);

        actWebSvcReference.participantName =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_ENDPOINT_PARTICIPANT_NAME);

        actWebSvcReference.isApiActivity = new Boolean(false);
        String tmp =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_IS_API_ACTIVITY);
        if (tmp != null) {
            actWebSvcReference.isApiActivity = Boolean.parseBoolean(tmp);
        }

        actWebSvcReference.isBusinessServiceInvoke = new Boolean(false);
        tmp =
                item.get(ActivityWebServiceReferenceIndexProvider.COLUMN_IS_BIZ_SERVICE_INVOKE);
        if (tmp != null) {
            actWebSvcReference.isBusinessServiceInvoke =
                    Boolean.parseBoolean(tmp);
        }

        return actWebSvcReference;
    }

    /**
     * Create activity web service reference information from given activity.
     * 
     * @param activity
     * 
     * @return Class that stores information about activity web service
     *         reference.
     */
    public static ActivityWebServiceReference createActivityWebServiceReference(
            Activity activity) {
        /*
         * Build up info direct from the activity.
         */
        IFile xpdlFile = WorkingCopyUtil.getFile(activity);
        if (xpdlFile != null) {

            IProject project = WorkingCopyUtil.getProjectFor(activity);
            if (project != null) {
                WsdlServiceKey wsdlKey =
                        ProcessUIUtil.getWsdlServiceKey(activity);
                if (wsdlKey != null) {

                    IFile wsdlFile =
                            getWsdlFile(activity, wsdlKey, project, xpdlFile);
                    if (wsdlFile != null) {

                        WorkingCopy wc =
                                WorkingCopyUtil.getWorkingCopy(xpdlFile);
                        if (wc != null) {
                            ActivityWebServiceReference actWebSvcReference =
                                    new ActivityWebServiceReference();

                            actWebSvcReference.projectName = project.getName();

                            IResource resource =
                                    wc.getEclipseResources().get(0);
                            actWebSvcReference.xpdlPath =
                                    resource.getFullPath().toPortableString();

                            actWebSvcReference.activityId = activity.getId();

                            actWebSvcReference.activityUri =
                                    ProcessUIUtil.getURIString(activity, true);

                            actWebSvcReference.displayName =
                                    Xpdl2ModelUtil
                                            .getDisplayNameOrName(activity);

                            actWebSvcReference.wsdlNamespace =
                                    calculateWsdlNamespace(activity,
                                            wsdlFile,
                                            wsdlKey);
                            /*
                             * this would have i)the same value as
                             * wsdl_namespace if the port type operation is in
                             * the same wsdl or ii)the namespace of the imported
                             * wsdl where the port type actually is defined
                             */
                            actWebSvcReference.portTypeNamespace =
                                    calculatePortTypeNamespace(activity,
                                            wsdlFile,
                                            wsdlKey);

                            actWebSvcReference.portName = wsdlKey.getPort();

                            actWebSvcReference.portTypeName =
                                    wsdlKey.getPortType();

                            actWebSvcReference.operation =
                                    wsdlKey.getPortTypeOperation();
                            /*
                             * Kapil: XPD-4455: If the WSDL does not exists(is
                             * Generated) then set the wsdlFileLocation to to
                             * Wsdl file name.
                             */
                            Boolean wsdlExists = wsdlFile.exists();
                            if (!wsdlExists) {
                                actWebSvcReference.wsdlFileLocation =
                                        wsdlFile.getName();
                            } else {

                                actWebSvcReference.wsdlFileLocation =
                                        SpecialFolderUtil
                                                .getSpecialFolderRelativePath(wsdlFile,
                                                        Activator.WSDL_SPECIALFOLDER_KIND)
                                                .toString();
                            }

                            WebServiceOperation wso =
                                    Xpdl2ModelUtil
                                            .getWebServiceOperation(activity);
                            if (wso != null) {
                                actWebSvcReference.transportType =
                                        (String) Xpdl2ModelUtil
                                                .getOtherAttribute(wso,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_Transport());
                            }

                            Participant participant =
                                    getEndPointAliasParticipant(activity);

                            actWebSvcReference.participantId =
                                    participant != null ? participant.getId()
                                            : ""; //$NON-NLS-1$

                            actWebSvcReference.participantName =
                                    participant != null ? participant.getName()
                                            : ""; //$NON-NLS-1$

                            actWebSvcReference.isApiActivity =
                                    new Boolean(
                                            Xpdl2ModelUtil
                                                    .isProcessAPIActivity(activity));

                            actWebSvcReference.isBusinessServiceInvoke =
                                    new Boolean(
                                            isBusinessServiceInvokeActivity(activity));

                            return actWebSvcReference;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param activity
     * @return True if the given activity is defined as a business web service
     *         task.
     */
    private static boolean isBusinessServiceInvokeActivity(Activity activity) {
        if (activity.getImplementation() instanceof Task) {
            OtherAttributesContainer implTypeElement =
                    ((Task) activity.getImplementation()).getTaskSend();

            if (implTypeElement == null) {
                implTypeElement =
                        ((Task) activity.getImplementation()).getTaskService();
            }

            if (implTypeElement != null) {
                String implExtId =
                        (String) Xpdl2ModelUtil
                                .getOtherAttribute(implTypeElement,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ImplementationType());
                if (ActionUtil.INVOKE_BUSINESS_PROCESS.equals(implExtId)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * @param activity
     * @param wsdlFile
     * @param wsdlKey
     * 
     * @return The target namespace for the activity / wsdl
     */
    private static String calculateWsdlNamespace(Activity activity,
            IFile wsdlFile, WsdlServiceKey wsdlKey) {
        /*
         * For our purposes, if it's a reply activity then we'll use the request
         * activity when considering whether the service is generated.
         */
        if (ReplyActivityUtil.isReplyActivity(activity)) {
            Activity req =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
            if (req != null) {
                activity = req;
            }
        }

        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
            /*
             * For generated service activity the WSDL may not exist at all yet)
             * so make up the name as we go along.
             */
            return Xpdl2ModelUtil.getDerivedWsdlNamespace(Xpdl2ModelUtil
                    .getPackage(activity));

        } else {
            Definition wsdlDefinition = WsdlIndexerUtil.getDefinition(wsdlFile);
            if (wsdlDefinition != null) {
                return wsdlDefinition.getTargetNamespace();
            } else if (wsdlFile != null) {
                Activity operationSourceActivity =
                        findGenWsdlSourceActivity(wsdlFile.getProject(),
                                wsdlKey);
                if (operationSourceActivity != null) {
                    return Xpdl2ModelUtil
                            .getDerivedWsdlNamespace(Xpdl2ModelUtil
                                    .getPackage(operationSourceActivity));
                }
            }
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * @param wsdlKey
     * @param wsdlFile
     * @param activity
     * @return Namespace for the PORT TYPE for the operation referenced by
     *         activity (even if the operation referenced is in a concrete port
     *         that references port-type in another WSDL!).
     */
    private static String calculatePortTypeNamespace(Activity activity,
            IFile wsdlFile, WsdlServiceKey wsdlKey) {

        /*
         * For our purposes, if it's a reply activity then we'll use the request
         * activity when considering whether the service is generated.
         */
        if (ReplyActivityUtil.isReplyActivity(activity)) {
            Activity req =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
            if (req != null) {
                activity = req;
            }
        }

        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
            /*
             * For generated service activity the WSDL may not exist at all yet)
             * so make up the name as we go along.
             */

            /*
             * If the activity's process implements a process interface, then
             * get the process interface's package
             */
            Process process = activity.getProcess();
            ProcessInterface implementedProcessInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);
            if (null != implementedProcessInterface) {

                if (ProcessInterfaceUtil.isEventImplemented(activity)) {

                    Package processInterfacePackage =
                            ProcessInterfaceUtil
                                    .getProcessInterfacePackage(implementedProcessInterface);
                    return Xpdl2ModelUtil
                            .getDerivedWsdlNamespace(processInterfacePackage);
                }
            }

            return Xpdl2ModelUtil.getDerivedWsdlNamespace(Xpdl2ModelUtil
                    .getPackage(activity));

        } else {
            String targetNameSpace =
                    getPortTypeTargetNameSpace(activity, wsdlKey, wsdlFile);

            if (null != targetNameSpace && !targetNameSpace.isEmpty()) {
                return targetNameSpace;
            } else if (wsdlFile != null) {
                Activity operationSourceActivity =
                        findGenWsdlSourceActivity(wsdlFile.getProject(),
                                wsdlKey);
                if (operationSourceActivity != null) {
                    return Xpdl2ModelUtil
                            .getDerivedWsdlNamespace(Xpdl2ModelUtil
                                    .getPackage(operationSourceActivity));
                }
            }
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Returns namespace of PortType for the operation referenced by activity
     * (even if the operation referenced is in a concrete Port and references
     * port-type in another WSDL!).
     * 
     * @param activity
     *            XPDL activity referencing an operation either in Port
     *            (concrete) or PortType (abstract).
     * @param wsdlKey
     *            the WSDL key created for the activity.
     * @param wsdlFile
     *            the WSDL file containing referenced operation.
     * @return Namespace for the PORT TYPE for the operation referenced by
     *         activity (even if the operation referenced is in a concrete port
     *         that references port-type in another WSDL!).
     */
    private static String getPortTypeTargetNameSpace(Activity activity,
            WsdlServiceKey wsdlKey, IFile wsdlFile) {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(wsdlFile);
        String opName = wsdlKey.getOperation();
        String portTypeOpName = wsdlKey.getPortTypeOperation();
        String portTypeName = wsdlKey.getPortType();
        String portName = wsdlKey.getPort();
        String serviceName = wsdlKey.getService();

        if (wc instanceof WsdlWorkingCopy && !wc.isInvalidFile()) {
            Definition definition = (Definition) wc.getRootElement();

            /* Get port operation reference from WSDL model. */
            Operation portTypeOperation = null;
            if (opName != null && !opName.isEmpty() && portName != null
                    && !portName.isEmpty() && serviceName != null
                    && !serviceName.isEmpty()) {
                /* Port operation in service - concrete case. */
                BindingOperation bindingOperation = null;
                outer: for (Object sObject : definition.getServices().values()) {
                    if (sObject instanceof Service) {
                        Service s = (Service) sObject;
                        if (s.getQName() != null
                                && serviceName.equals(s.getQName()
                                        .getLocalPart())) {
                            for (Object pObject : s.getPorts().values()) {
                                if (pObject instanceof Port) {
                                    Port p = (Port) pObject;
                                    if (portName.equals(p.getName())
                                            && p.getBinding() != null) {
                                        for (Object boObject : p.getBinding()
                                                .getBindingOperations()) {
                                            if (boObject instanceof BindingOperation) {
                                                BindingOperation bo =
                                                        (BindingOperation) boObject;
                                                if (opName.equals(bo.getName())) {
                                                    bindingOperation = bo;
                                                    break outer;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (bindingOperation != null) {
                    portTypeOperation = bindingOperation.getOperation();
                } else {
                    /*
                     * JA: XPD-4182: Error is not thrown, but only logged to
                     * enable rectification of the situation by saving updated
                     * WSDL reference.
                     */
                    AssertionError e =
                            new AssertionError(
                                    String.format("WSDL port operation '%1$s' referenced form activity '%2$s' was not found in WSDL '%3$s' file.", //$NON-NLS-1$
                                            opName,
                                            activity.getName(),
                                            wsdlFile.getName()));
                    LOG.warn(e, e.getLocalizedMessage());
                }
            } else if (portTypeName != null && !portTypeName.isEmpty()
                    && portTypeOpName != null && !portTypeOpName.isEmpty()) {
                /* Port type operation - abstract case. */
                outer: for (Object ptObject : definition.getPortTypes()
                        .values()) {
                    if (ptObject instanceof PortType) {
                        PortType pt = (PortType) ptObject;
                        if (portTypeName.equals(pt.getQName().getLocalPart())) {
                            for (Object ptoObject : pt.getOperations()) {
                                org.eclipse.wst.wsdl.Operation pto =
                                        (org.eclipse.wst.wsdl.Operation) ptoObject;
                                if (portTypeOpName.equals(pto.getName())) {
                                    portTypeOperation = pto;
                                    break outer;
                                }
                            }
                        }
                    }
                }
            }
            /*
             * Get a namespace of a definition enclosing operation from a port
             * type. Could potentially be different then one of the operation
             * from a port.
             */
            if (portTypeOperation != null) {
                Definition enclosingDefinition =
                        ((org.eclipse.wst.wsdl.Operation) portTypeOperation)
                                .getEnclosingDefinition();
                if (null != enclosingDefinition) {
                    String targetNamespace =
                            enclosingDefinition.getTargetNamespace();
                    if (null != targetNamespace) {
                        return targetNamespace;
                    }
                }
            }

            AssertionError e =
                    new AssertionError(
                            String.format("WSDL port operation namespace for activity '%1$s' could not be found.", //$NON-NLS-1$
                                    activity.getName()));
            /*
             * JA: XPD-4182: Error is not thrown, but only logged to enable
             * rectification of the situation by saving updated WSDL reference.
             */
            LOG.warn(e, e.getLocalizedMessage());
        }
        /*
         * The WSDL might not be present in a generated case then "" is
         * returned, but it should be updated when WSDL appears.
         */
        return ""; //$NON-NLS-1$
    }

    /**
     * @param activity
     * @param wsdlKey
     * @param project
     * @param xpdlFile
     * @return The IFile for the given web service operation key.
     */
    private static IFile getWsdlFile(Activity activity, WsdlServiceKey wsdlKey,
            IProject project, IFile xpdlFile) {

        /*
         * For our purposes, if it's a reply activity then we'll use the request
         * activity when considering whether the service is generated.
         */
        if (ReplyActivityUtil.isReplyActivity(activity)) {
            Activity req =
                    ReplyActivityUtil
                            .getRequestActivityForReplyActivity(activity);
            if (req != null) {
                activity = req;
            }
        }

        if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
            /*
             * For generated service activity the WSDL may not exist at all yet)
             * so make up the name as we go along.
             */
            IFile derivedWsdl =
                    ProcessInterfaceUtil.getDerivedWsdlFile(activity);

            return derivedWsdl;

        } else {

            IFile wsdlFile =
                    SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                            Activator.WSDL_SPECIALFOLDER_KIND,
                            wsdlKey.getFilePath(),
                            true);
            if (wsdlFile != null && wsdlFile.isAccessible()) {

                return wsdlFile;
            } else {
                /*
                 * For generated service activity the WSDL may not exist at all
                 * yet) so make up the file handler as we go along.
                 */
                Activity operationSourceActivity =
                        findGenWsdlSourceActivity(project, wsdlKey);
                if (operationSourceActivity != null) {
                    wsdlFile =
                            ProcessInterfaceUtil
                                    .getDerivedWsdlFile(operationSourceActivity);
                }
                return wsdlFile;
            }
        }
    }

    /**
     * This method assumes that the wsdlKey is pointing to an operation in a
     * generated (derived) WSDL and tries to find the corresponding starting
     * activity for it.
     * 
     * @param project
     *            the context project.
     * @param wsdlKey
     *            the key with operation reference.
     * @return Starting activity (source of operation) if the WSDL was generated
     *         or 'null' otherwise.
     */
    private static Activity findGenWsdlSourceActivity(IProject project,
            WsdlServiceKey wsdlKey) {
        /*
         * JA: Find if it could be a generated WSDL by searching for the
         * possible source xpdl with the same name and then (if found) assume
         * that it will have a generated WSDL.
         * 
         * We are just trying to make a best guess here because the indexer
         * could run when the derived files are not ready yet.
         */
        IPath xpdlLocationPath =
                new Path(wsdlKey.getFilePath()).removeFileExtension()
                        .addFileExtension(Xpdl2ResourcesConsts.XPDL_EXTENSION);
        IFile genSourceXpdlFile =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                        xpdlLocationPath.toPortableString(),
                        true);
        if (genSourceXpdlFile != null && genSourceXpdlFile.isAccessible()) {
            // JA: Additional checks on xpdl (so there is a better
            // chance that
            // it is really a generated wsdl).
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(genSourceXpdlFile);

            if (wc instanceof Xpdl2WorkingCopyImpl && !wc.isInvalidFile()
                    && wc.getRootElement() instanceof Package) {

                Package xpdlPackage = (Package) wc.getRootElement();

                for (Process p : xpdlPackage.getProcesses()) {

                    for (Activity candidateRequestactivity : Xpdl2ModelUtil
                            .getAllActivitiesInProc(p)) {

                        boolean generatedRequestActivity =
                                Xpdl2ModelUtil
                                        .isGeneratedRequestActivity(candidateRequestactivity);
                        /*
                         * check if the activity is a generated activity.
                         */
                        if (generatedRequestActivity) {
                            /*
                             * XPD-7705: get port type name and operation name
                             * from the request activity itself rather than
                             * guessing it from the process name because there
                             * might be scenarios that the process implements
                             * interface and the port type name is the process
                             * interface name.
                             */
                            PortTypeOperation portTypeOperation =
                                    Xpdl2ModelUtil
                                            .getPortTypeOperation(candidateRequestactivity);

                            if (portTypeOperation != null) {

                                if (portTypeOperation.getPortTypeName() != null
                                        && wsdlKey.getPortType() != null
                                        && portTypeOperation.getPortTypeName()
                                                .equals(wsdlKey.getPortType())
                                        && portTypeOperation.getOperationName() != null
                                        && wsdlKey.getPortTypeOperation() != null
                                        && portTypeOperation
                                                .getOperationName()
                                                .equals(wsdlKey.getPortTypeOperation())) {

                                    return candidateRequestactivity;
                                }
                            }
                        }
                    }
                }

            } else {
                // XpdResourcesPlugin
                // .getDefault()
                // .getLogger()
                //                        .error(String.format("Unable to index activity web service reference, because WSDL source xpdl WC is not loaded: '%1$s'", genSourceXpdlFile.toString())); //$NON-NLS-1$
            }
        }

        /*
         * SID XPD-6180. We need to do the same as above for WSDLs that may be
         * generated from Decision Flow activities.
         */
        IPath dflowLocationPath =
                new Path(wsdlKey.getFilePath()).removeFileExtension()
                        .addFileExtension("dflow"); //$NON-NLS-1$
        IFile genSourceDflowFile =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                        "decisions", //$NON-NLS-1$
                        dflowLocationPath.toPortableString(),
                        true);
        if (genSourceDflowFile != null && genSourceDflowFile.isAccessible()) {
            // JA: Additional checks on xpdl (so there is a better
            // chance that
            // it is really a generated wsdl).
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(genSourceDflowFile);
            if (wc instanceof Xpdl2WorkingCopyImpl && !wc.isInvalidFile()
                    && wc.getRootElement() instanceof Package) {
                Package xpdlPackage = (Package) wc.getRootElement();

                for (Process p : xpdlPackage.getProcesses()) {

                    for (Activity candidateRequestactivity : Xpdl2ModelUtil
                            .getAllActivitiesInProc(p)) {

                        boolean generatedRequestActivity =
                                Xpdl2ModelUtil
                                        .isGeneratedRequestActivity(candidateRequestactivity);
                        /*
                         * check if the activity is a generated activity.
                         */
                        if (generatedRequestActivity) {
                            /*
                             * XPD-7705: get port type name and operation name
                             * from the request activity itself rather than
                             * guessing it from the process name because there
                             * might be scenarios that the process implements
                             * interface and the port type name is the process
                             * interface name.
                             */
                            PortTypeOperation portTypeOperation =
                                    Xpdl2ModelUtil
                                            .getPortTypeOperation(candidateRequestactivity);

                            if (portTypeOperation != null) {

                                if (portTypeOperation.getPortTypeName() != null
                                        && wsdlKey.getPortType() != null
                                        && portTypeOperation.getPortTypeName()
                                                .equals(wsdlKey.getPortType())
                                        && portTypeOperation.getOperationName() != null
                                        && wsdlKey.getPortTypeOperation() != null
                                        && portTypeOperation
                                                .getOperationName()
                                                .equals(wsdlKey.getPortTypeOperation())) {

                                    return candidateRequestactivity;
                                }
                            }
                        }
                    }
                }
            } else {
                // XpdResourcesPlugin
                // .getDefault()
                // .getLogger()
                //                        .error(String.format("Unable to index activity web service reference, because WSDL source xpdl WC is not loaded: '%1$s'", genSourceXpdlFile.toString())); //$NON-NLS-1$
            }
        }

        return null;
    }

    /**
     * @param activity
     * 
     * @return The participant set as the endpoint alias for web-service related
     *         activity.
     */
    private static Participant getEndPointAliasParticipant(Activity activity) {
        return Xpdl2ModelUtil.getEndPointAliasParticipant(activity);
    }

    /**
     * @return the xpdlPath
     */
    public String getXpdlPath() {
        return xpdlPath != null ? xpdlPath : ""; //$NON-NLS-1$
    }

    /**
     * @return the activityId
     */
    public String getActivityId() {
        return activityId != null ? activityId : ""; //$NON-NLS-1$
    }

    /**
     * @return the activityUri
     */
    public String getActivityUri() {
        return activityUri != null ? activityUri : ""; //$NON-NLS-1$
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName != null ? displayName : ""; //$NON-NLS-1$
    }

    /**
     * @return the wsdlNamespace
     */
    public String getWsdlNamespace() {
        return wsdlNamespace != null ? wsdlNamespace : ""; //$NON-NLS-1$
    }

    /**
     * @return the portTypeNamespace
     */
    public String getPortTypeNamespace() {
        return portTypeNamespace != null ? portTypeNamespace : ""; //$NON-NLS-1$
    }

    /**
     * @return the portType
     */
    public String getPortTypeName() {
        return portTypeName != null ? portTypeName : ""; //$NON-NLS-1$
    }

    /**
     * @return the portType
     */
    public String getPortName() {
        return portName != null ? portName : ""; //$NON-NLS-1$
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation != null ? operation : ""; //$NON-NLS-1$
    }

    /**
     * @return the wsdlFileLocation
     */
    public String getWsdlFileLocation() {
        return wsdlFileLocation != null ? wsdlFileLocation : ""; //$NON-NLS-1$
    }

    /**
     * @return the participantId
     */
    public String getParticipantId() {
        return participantId != null ? participantId : ""; //$NON-NLS-1$
    }

    /**
     * @return the participantName
     */
    public String getParticipantName() {
        return participantName != null ? participantName : ""; //$NON-NLS-1$
    }

    /**
     * @return the isApiActivity
     */
    public Boolean getIsApiActivity() {
        return isApiActivity != null ? isApiActivity : Boolean.FALSE;
    }

    /**
     * @return the isBusinessServiceInvoc
     */
    public Boolean getIsBusinessServiceInvoke() {
        return isBusinessServiceInvoke != null ? isBusinessServiceInvoke
                : Boolean.FALSE;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @return the transportType
     */
    public String getTransportType() {
        return transportType != null ? transportType : ""; //$NON-NLS-1$
    }

    /**
     * Returns activity referenced by this reference.
     * 
     * @return activity for this reference or <code>null</code>.
     */
    public Activity getActivity() {
        if (activityUri != null) {
            URI activityURI = URI.createURI(activityUri);
            Activity activity =
                    (Activity) XpdResourcesPlugin.getDefault()
                            .getEditingDomain().getResourceSet()
                            .getEObject(activityURI, true);
            return activity;
        }
        return null;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return String
                .format("ActivityWebServiceRef: Act(%s::%s (%s) : %s)  Partic(%s : %s)  WebSvc(%s::%s/%s)", //$NON-NLS-1$
                        getXpdlPath(),
                        getActivityId(),
                        (isApiActivity ? "isApi" : "nonApi"), //$NON-NLS-1$ //$NON-NLS-2$
                        getDisplayName(),
                        getParticipantId(),
                        getParticipantName(),
                        getWsdlFileLocation(),
                        (getPortName() != null && getPortName().length() != 0 ? getPortName()
                                : getPortTypeName()),
                        getOperation());
    }

    public static void reportWebRefIndexes(IProject project) {
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();

        additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_PROJECT,
                project.getName());

        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_TYPE, null,
                        additionalAttributes);

        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_ID,
                                criteria);

        System.out
                .println("==> " + ActivityWebServiceReference.class.getSimpleName() + ".ActivityWebServiceReferences: "); //$NON-NLS-1$ //$NON-NLS-2$

        if (result != null) {
            for (IndexerItem indexerItem : result) {
                ActivityWebServiceReference ref =
                        ActivityWebServiceReference
                                .createActivityWebServiceReference(indexerItem);
                System.out.println("  " + ref.toString()); //$NON-NLS-1$
            }
        }
        System.out.println("<== ActivityWebServiceReferences: "); //$NON-NLS-1$
        return;
    }
}