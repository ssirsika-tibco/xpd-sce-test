package com.tibco.bx.xpdl2bpel.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.uml2.uml.Class;
import org.osgi.framework.Version;
import org.w3c.dom.Element;

import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.ProcessManagerScript;
import com.tibco.bx.bpelExtension.extensions.ProcessManagerScriptType;
import com.tibco.bx.bpelExtension.extensions.Task;
import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.internal.ConverterContext;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataContextReferenceResolver;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bpm.om.BPMProcessOrgModelUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.MaxRetryActionType;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.Retry;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.SystemErrorActionType;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

public class XPDLUtils {

    /**
     * Constant defined in
     * com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts:
     */
    public static final String PROCESSES_SPECIAL_FOLDER_KIND =
            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND;

    /**
     * Constant defined in com.tibco.xpd.process.js.model.ProcessJsConsts:
     */
    public static final String JAVASCRIPT_GRAMMAR = "JavaScript"; //$NON-NLS-1$

    /**
     * Constant defined in com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl:
     * name of extended attribute of external package element that holds
     * location of the referenced file.
     */
    private static final String ATTR_LOCATION = "location"; //$NON-NLS-1$
    
    /**
     * Constant for the name of the invoke type attribute.
     */
    public static final String ATTR_INVOKE_TYPE = "invokeType"; //$NON-NLS-1$
    
    /**
     * Enums for types of invocation (values of "invokeType" attribute).
     */
    public enum InvokeType{
        REST("REST"); //$NON-NLS-1$
        
        private String name;

        InvokeType(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }; 
    
    /**
     * Constant for the name of the sharedRedourceType attribute.
     */
    public static final String ATTR_SHARED_RESOURCE_TYPE = "sharedResourceType"; //$NON-NLS-1$
    
    
    /**
     * Enums for types of shared resource (values of "sharedResourceType" attribute).
     */
    public enum SharedResourceType{
        HTTP_CLIENT("HTTPClient"), //$NON-NLS-1$
        EMAIL("EMail"); //$NON-NLS-1$
        
        private String name;
        
        SharedResourceType(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }; 
    
    /**
     * Constant for the name of the sharedResourceName (tibex) attribute.
     */
    public static final String ATTR_SHARED_RESOURCE_NAME = "sharedResourceName"; //$NON-NLS-1$
    
    /**
     * Constant for the name of the sharedResourceDesctiption (tibex) attribute.
     */
    public static final String ATTR_SHARED_RESOURCE_DESC = "sharedResourceDescription"; //$NON-NLS-1$
    

    public static boolean isSupportedXpdlFile(Path path) {
        IResource resource =
                ResourcesPlugin.getWorkspace().getRoot().getFile(path);
        return resource != null ? isSupportedXpdlFile(resource) : false;
    }

    public static boolean isSupportedXpdlFile(IResource file) {
        return file.exists()
                && file.getType() == IResource.FILE
                && Xpdl2ResourcesConsts.XPDL_EXTENSION.equalsIgnoreCase(file
                        .getFileExtension()) //$NON-NLS-1$
                && isProcessSpecialFolder(file.getParent());
    }

    public static boolean isProcessSpecialFolder(IResource folder) {
        return folder instanceof IFolder
                && PROCESSES_SPECIAL_FOLDER_KIND.equals(SpecialFolderUtil
                        .getSpecialFolderKind((IFolder) folder));
    }

    public static boolean hasN2Destination(com.tibco.xpd.xpdl2.Process process) {
        return GlobalDestinationHelper.isGlobalDestinationEnabled(process,
                N2PEConstants.N2_GLOBAL_DESTINATION_ID);
    }

    /**
     * check there is process with N2 destination or not in this xpdlFile.
     * 
     * @param xpdlFile
     * @return
     */
    public static boolean hasN2Process(IFile xpdlFile) {
        WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
        if (workingCopy != null
                && workingCopy.getRootElement() instanceof Package) {
            EList<com.tibco.xpd.xpdl2.Process> processes =
                    ((Package) workingCopy.getRootElement()).getProcesses();
            for (com.tibco.xpd.xpdl2.Process p : processes) {
                if (hasN2Destination(p)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check there is process with N2 destination or not in these xpdlFiles.
     * 
     * @param xpdlFile
     * @return
     */
    public static boolean hasN2Process(IFile[] xpdlFiles) {
        for (IFile f : xpdlFiles) {
            if (hasN2Process(f)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLoopActivity(Activity xpdlActivity) {
        Loop loop = xpdlActivity.getLoop();
        if (loop != null) {
            return true;
        }
        // Studio using incorrect XPDL format to signify mulitple instance loop
        if (xpdlActivity.getStartQuantity() != null
                && xpdlActivity.getStartQuantity().intValue() == -1) {
            return true;
        }
        return false;
    }

    /**
     * Get the wait for value for a discriminator (complex gateway)
     * 
     * @param xpdlActivity
     *            discriminator gateway
     * @return wait for value. null if none
     */
    public static Integer getDiscriminatorWaitFor(Activity xpdlActivity) {
        Route route = xpdlActivity.getRoute();
        Discriminator discriminator = null;
        FeatureMap featureMap = route.getOtherElements();
        FeatureMap.ValueListIterator iter = featureMap.valueListIterator();
        while (iter.hasNext()) {
            EObject obj = (EObject) iter.next();
            if (obj instanceof Discriminator) {
                discriminator = (Discriminator) obj;
                break;
            }
        }
        if (discriminator != null) {
            StructuredDiscriminator structuredDiscriminator =
                    discriminator.getStructuredDiscriminator();
            if (structuredDiscriminator != null && structuredDiscriminator.getWaitForIncomingPath() != null) {
                return structuredDiscriminator.getWaitForIncomingPath().intValue();
            }
        }
        return null;
    }

    /**
     * Get the WSDL location (a URI) out of the parameter.
     * 
     * @param project
     * @param wso
     *            the operation to get the WSDL location from.
     * @return The WSDL URI.
     */
    public static URI getWSDLURI(IProject project, final WebServiceOperation wso) {
        if (wso != null) {
            Service service = wso.getService();
            if (service != null) {
                EndPoint endPoint = service.getEndPoint();
                EndPointTypeType endPointType = endPoint.getEndPointType();
                if (endPointType != null
                        && endPointType.getValue() == EndPointTypeType.WSDL) {
                    ExternalReference externalReference =
                            endPoint.getExternalReference();
                    if (externalReference != null) {
                        String location = externalReference.getLocation();
                        URI uri = URI.createURI(location);
                        if (!uri.isRelative()) {
                            return uri;
                        }

                        IFile file =
                                getFileInSpecialFolder(project,
                                        uri.path(),
                                        "wsdl"); //$NON-NLS-1$
                        if (file != null) {
                            uri =
                                    URI.createPlatformResourceURI(file
                                            .getFullPath().toString(), false);
                            return uri;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Little utility to get the package path location from ExternalPackage
     * element.
     * 
     * @param extPkg
     * @return location or null on error.
     */
    public static String getExternalPackageLocation(ExternalPackage extPkg) {
        EList<ExtendedAttribute> extendedAttributes =
                extPkg.getExtendedAttributes();
        for (ExtendedAttribute attribute : extendedAttributes) {
            if (ATTR_LOCATION.equals(attribute.getName())) {
                return attribute.getValue();
            }
        }
        return null;
    }

    public static String getExternalPackageLocation(Package pkg, String href) {
        if (href != null) {
            // Get the external packages
            EList<ExternalPackage> externalPackages = pkg.getExternalPackages();
            for (ExternalPackage ePkg : externalPackages) {
                // find package with proper href
                if (href.equals(ePkg.getHref())) {
                    return getExternalPackageLocation(ePkg);
                }
            }
        }
        return null;
    }

    public static String getParticipantName(WebServiceOperation wso) {
        String alias =
                (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias());
        if (alias != null && alias.length() > 0) {
            String participantName =
                    TaskObjectUtil.getParticipantNameForPassedId(alias, wso);
            if (participantName != null && participantName.length() > 0) {
                return participantName;
            }
        }
        return null;

    }

    public static boolean isChained(OtherAttributesContainer container) {
        Object chained =
                Xpdl2ModelUtil.getOtherAttribute(container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_IsChained());
        return Boolean.TRUE.equals(chained);
    }

    public static PortTypeOperation getPortTypeOperation(
            OtherElementsContainer container) {
        return (PortTypeOperation) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_PortTypeOperation());
    }

    public static PortTypeOperation getPortTypeOperation(WebServiceOperation wso) {
        return (PortTypeOperation) Xpdl2ModelUtil
                .getOtherElement((OtherElementsContainer) wso.eContainer(),
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PortTypeOperation());
    }

    public static String getSecurityProfile(WebServiceOperation wso) {
        String securityProfile =
                (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SecurityProfile());
        if (securityProfile != null && securityProfile.length() > 0) {
            return securityProfile;
        }
        return null;
    }

    public static CatchErrorMappings getCatchErrorMappings(
            ResultError resultError) {
        return (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(resultError,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_CatchErrorMappings());
    }

    public static Message getFaultMessage(ResultError resultError) {
        return (Message) Xpdl2ModelUtil.getOtherElement(resultError,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_FaultMessage());
    }

    public static ErrorThrowerInfo getErrorThrowerInfo(ResultError resultError) {
        return (ErrorThrowerInfo) Xpdl2ModelUtil.getOtherElement(resultError,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ErrorThrowerInfo());
    }

    public static String getRequestActivityId(ResultError resultError) {
        return (String) Xpdl2ModelUtil.getOtherAttribute(resultError,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_RequestActivityId());
    }

    public static String getDisplayName(OtherAttributesContainer container) {
        return (String) Xpdl2ModelUtil.getOtherAttribute(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName());
    }

    public static InitialValues getInitialValues(
            OtherElementsContainer container) {
        return (InitialValues) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_InitialValues());
    }

    public static Expression getParticipantQuery(
            OtherElementsContainer container) {
        return (Expression) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ParticipantQuery());
    }

    public static ScriptInformation getScriptInformation(
            OtherElementsContainer container) {
        return (ScriptInformation) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
    }

    public static ScriptDataMapper getScriptDataMapper(
            OtherElementsContainer container) {
        return (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings());
    }

    public static void save(EObject eObject) throws IOException {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eObject);
        if (wc == null) {
            Resource resource = eObject.eResource();
            URI uri = resource.getURI();
            String path = uri.path();
            IPath filePath = new Path(path);
            IFile file =
                    ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
            if (file != null)
                wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        }
        if (wc != null)
            wc.save();
        else {
            Resource resource = eObject.eResource();
            if (resource != null)
                resource.save(null);
        }
    }

    public static Map<String, CorrelationMode> getCorrelationDataFields(
            Activity xpdlActivity) {
        Map<String, CorrelationMode> correlationDataFields =
                new HashMap<String, CorrelationMode>();
        FeatureMap.ValueListIterator others =
                xpdlActivity.getOtherElements().valueListIterator();
        while (others.hasNext()) {
            EObject obj = (EObject) others.next();
            if (obj instanceof AssociatedCorrelationFields) {
                EList<AssociatedCorrelationField> fields =
                        ((AssociatedCorrelationFields) obj)
                                .getAssociatedCorrelationField();
                for (AssociatedCorrelationField field : fields) {
                    CorrelationMode mode = field.getCorrelationMode();
                    String name = field.getName();
                    correlationDataFields.put(name, mode);
                }
            }
        }
        return correlationDataFields;
    }

    public static Participant resolveXpdlSystemParticipant(Activity xpdlActivity) {
        EList<Performer> performerList = xpdlActivity.getPerformerList();
        if (performerList != null) {
            com.tibco.xpd.xpdl2.Process parentProcess =
                    XpdlSearchUtil.findParentProcess(xpdlActivity);
            if (parentProcess != null) {
                List<Participant> participants = new ArrayList<Participant>();
                participants.addAll(parentProcess.getParticipants());
                participants.addAll(parentProcess.getPackage()
                        .getParticipants());
                for (Performer performer : performerList) {
                    if (performer != null) {
                        String performerId = performer.getValue();
                        if (performerId != null) {
                            for (Participant participant : participants) {
                                if (participant != null
                                        && participant.getId()
                                                .equals(performerId)) {
                                    ParticipantTypeElem participantType =
                                            participant.getParticipantType();
                                    if (participantType != null
                                            && participantType
                                                    .getType()
                                                    .equals(ParticipantType.SYSTEM_LITERAL)) {
                                        return participant;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Since the Endpoint URI is only available for a provider SOAP over HTTP
     * binding, assuming that the xpdlActivity is a process API activity
     * 
     * @param xpdlActivity
     * @return
     */
    public static String resolveEncodedURI(Activity xpdlActivity) {
        if (xpdlActivity != null) {
            ParticipantSharedResource sharedResource =
                    getActivitySharedResource(xpdlActivity);
            if (sharedResource != null) {
                WsResource webService = sharedResource.getWebService();
                if (webService != null) {
                    WsInbound inbound = webService.getInbound();
                    if (inbound != null) {
                        List<WsSoapHttpInboundBinding> soapHttpBinding =
                                inbound.getSoapHttpBinding();
                        if (soapHttpBinding.size() > 0) {
                            WsSoapHttpInboundBinding wsSoapHttpInboundBinding =
                                    soapHttpBinding.get(0);
                            return wsSoapHttpInboundBinding
                                    .getEndpointUrlPath();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * The database shared resource name is persisted as the jdbc instance name.
     * 
     * @param xpdlActivity
     * @return
     */
    public static String resolveDatabaseSimpleValue(Activity xpdlActivity) {
        if (xpdlActivity != null) {
            ParticipantSharedResource sharedResource =
                    getActivitySharedResource(xpdlActivity);
            if (sharedResource != null) {
                JdbcResource jdbc = sharedResource.getJdbc();
                if (jdbc != null) {
                    return jdbc.getInstanceName();
                }
            }
        }
        return null;
    }

    /**
     * Email shared resource name is persisted as the email instance name.
     * 
     * @param xpdlActivity
     * @return
     */
    public static String resolveEmailSimpleValue(Activity xpdlActivity) {
        EmailResource emailResource = getEmailResource(xpdlActivity);
        if (emailResource != null) {
            return emailResource.getInstanceName();
        }
        return null;
    }
    
    /**
     * Return email shared resource from associated participant.
     * 
     * @param xpdlActivity the context activity.
     * @return email shared resource from associated participant or 'null' if it can't be resolved.
     */
    public static EmailResource getEmailResource(Activity xpdlActivity) {
        if (xpdlActivity != null) {
            ParticipantSharedResource sharedResource =
                    getActivitySharedResource(xpdlActivity);
            if (sharedResource != null) {
                return sharedResource.getEmail();
            }
        }
        return null;
    }
    
    /**
     * Based on the Web service shared resource configuration - if the
     * participant is configured as a Provider the Http connector instance name
     * is returned otherwise the Http client instance name is returned.
     * 
     * @param xpdlActivity
     * @return
     */
    public static String resolveWebServiceSimpleValue(Activity xpdlActivity) {
        if (xpdlActivity != null) {
            ParticipantSharedResource sharedResource =
                    getActivitySharedResource(xpdlActivity);
            if (sharedResource == null) {
                return null;
            }
            WsResource webService = sharedResource.getWebService();
            if (webService != null) {
                WsInbound inbound = webService.getInbound();
                if (inbound != null) {
                    List<WsSoapHttpInboundBinding> soapHttpBinding =
                            inbound.getSoapHttpBinding();

                    if (!soapHttpBinding.isEmpty()) {
                        // XXX: For the moment, there can be only one SOAP over
                        // HTTP binding. Therefore returning the HTTP Connector
                        // Instance Name for the first binding found.
                        WsSoapHttpInboundBinding wsSoapHttpInboundBinding =
                                soapHttpBinding.get(0);
                        return wsSoapHttpInboundBinding
                                .getHttpConnectorInstanceName();
                    }
                } else if (webService.getOutbound() != null) {
                    WsOutbound outbound = webService.getOutbound();
                    if (outbound != null) {
                        WsSoapHttpOutboundBinding soapHttpBinding =
                                outbound.getSoapHttpBinding();
                        if (soapHttpBinding != null) {
                            return soapHttpBinding.getHttpClientInstanceName();
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String resolveWebServiceUri(Activity xpdlActivity) {
        if (xpdlActivity != null) {

            ParticipantSharedResource sharedResource =
                    getActivitySharedResource(xpdlActivity);
            if (sharedResource == null) {
                return null;
            }
            WsResource webService = sharedResource.getWebService();
            if (webService != null) {
                WsInbound inbound = webService.getInbound();
                if (inbound != null) {
                    List<WsSoapHttpInboundBinding> soapHttpBinding =
                            inbound.getSoapHttpBinding();

                    if (!soapHttpBinding.isEmpty()) {
                        // XXX: For the moment, there can be only one SOAP over
                        // HTTP binding. Therefore returning the HTTP Connector
                        // Instance Name for the first binding found.
                        WsSoapHttpInboundBinding wsSoapHttpInboundBinding =
                                soapHttpBinding.get(0);
                        return wsSoapHttpInboundBinding.getEndpointUrlPath();
                    }
                }
            }

        }
        return null;
    }
    
    /**
     * Returns RestServiceResource for the associated participant or 'null' if not resolved.
     * 
     * @param xpdlActivity the context activity.
     * @return RestServiceResource for the associated participant or 'null' if not resolved.
     */
    public static RestServiceResource getRestServiceResource(
            Activity xpdlActivity) {
        if (xpdlActivity != null) {
            ParticipantSharedResource sharedResource =
                    getActivitySharedResource(xpdlActivity);
            if (sharedResource != null) {
                return sharedResource.getRestService();
            }
        }
        return null;
    }

    private static ParticipantSharedResource getActivitySharedResource(
            Activity xpdlActivity) {
        if (xpdlActivity != null) {
            Participant participant =
                    resolveXpdlSystemParticipant(xpdlActivity);
            if (participant != null) {
                Object otherElement =
                        Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());
                if (otherElement instanceof ParticipantSharedResource) {
                    return (ParticipantSharedResource) otherElement;
                }

            }
        }
        return null;
    }

    public static IFile getFileInSpecialFolder(IProject project,
            String specialFolderRelativePath, String specialFolderKind) {
        IFile resolvedFile =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                        specialFolderKind,
                        specialFolderRelativePath,
                        true);
        return resolvedFile != null && resolvedFile.exists() ? resolvedFile
                : null;
    }

    public static Class getBomClass(ExternalReference reference) {
        String location = reference.getLocation();
        IProject project = WorkingCopyUtil.getProjectFor(reference);
        IFile bomFile =
                getFileInSpecialFolder(project,
                        location,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        if (bomFile != null) {
            WorkingCopy bomCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
            EObject element = bomCopy.resolveEObject(reference.getXref());
            if (element instanceof Class) {
                return (Class) element;
            }
        }
        return null;
    }

    public static String getBomClassName(ExternalReference reference) {
        String location = reference.getLocation();
        IProject project = WorkingCopyUtil.getProjectFor(reference);
        IFile bomFile =
                getFileInSpecialFolder(project,
                        location,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        if (bomFile != null) {
            WorkingCopy bomCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
            EObject root = bomCopy.getRootElement();
            EObject eo = root.eResource().getEObject(reference.getXref());
            if (eo instanceof Class) {
                return BOMWorkingCopy.getQualifiedClassName((Class) eo);
            }
        }
        return null;
    }

    public static String getPackageCapabilityId(IProject project, Package pkg) {
        String projectId = ProjectUtil.getProjectId(project);
        String capabilityId =
                String
                        .format("%s.%s.pe.capability", new Object[] { projectId, pkg.getName() }); //$NON-NLS-1$
        return capabilityId;
    }

    public static String getPackageVersionRange(Package pkg) {
        /*
         * Sid ACE-1354 - GIVEN that there is a validation rule that has always
         * ensured that the process package version exactly matches the project
         * version THEN we can get rid of the process package version altogether
         * and use the parent Project version instead.
         */

        // BX-788: turn "1.2.3.qualifier" into "[1.2.3,2.0.0)"
        // BX-2627: turn "1.2.3.qualifier" into "[1.0.0,2.0.0)"
        Version version = new Version(ProjectUtil
                .getProjectVersion(WorkingCopyUtil.getProjectFor(pkg)));

        String versionRange = String.format("[%d.0.0,%d.0.0)", //$NON-NLS-1$
                new Object[] { version.getMajor(), version.getMajor() + 1 });
        return versionRange;
    }
    
    /**
     * Copied from Xpdl2ModelUtil.isEventHandlerActivity().
     * An event handler is any intermediate event with no incoming flow.
     * Although this breaks the initial rules laid out in BPMN 1.2 specification
     * (that these must have a single incoming flow) it matches the correlation
     * between BPMN 1.2 and BPEL trigger events in the same specification.
     * 
     * @param activity
     * @return <code>true</code> if the given activity is an event handler
     *         activity
     */
    public static boolean isEventHandlerActivity(Activity activity) {
        if (activity != null) {
            if (activity.getEvent() instanceof IntermediateEvent) {
                if (!Xpdl2ModelUtil.isEventAttachedToTask(activity)) {
                    if (activity.getIncomingTransitions().isEmpty()) {
                        return true;
                    }
                }
            }
        }
 
        return false;
    }
    public static CalendarReference getCalendarReference(OtherElementsContainer container) {
        return (CalendarReference) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_CalendarReference());
    }

    public static Retry getRetry(OtherElementsContainer container) {
        return (Retry) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Retry());
    }

	public static void configureRetry(org.eclipse.bpel.model.ExtensibleElement bpelElement, OtherElementsContainer container) {
		Retry retry = XPDLUtils.getRetry(container);
        if (retry != null) {
        	if (retry.getInitialPeriod() > 0) {
            	BPELUtils.addExtensionAttribute(bpelElement, N2PEConstants.RETRY_INITIAL_PERIOD, String.valueOf(retry.getInitialPeriod()));
        	}
        	if (retry.getMax() >= 0) {
            	BPELUtils.addExtensionAttribute(bpelElement, N2PEConstants.RETRY_MAX, String.valueOf(retry.getMax()));
        	}
        	if (retry.getPeriodIncrement() >= 0) {
            	BPELUtils.addExtensionAttribute(bpelElement, N2PEConstants.RETRY_PERIOD_INCREMENT, String.valueOf(retry.getPeriodIncrement()));
        	}
        }
	}
    
	public static void configureHaltOnError(org.eclipse.bpel.model.ExtensibleElement bpelElement, OtherElementsContainer container) {
		Retry retry = XPDLUtils.getRetry(container);
        if (retry != null &&retry.isSetMaxRetryAction()) {
        	if (MaxRetryActionType.HALT.equals(retry.getMaxRetryAction())) {
            	BPELUtils.addExtensionAttribute(bpelElement, N2PEConstants.RETRY_MAX_RETRY_ACTION, "true"); //$NON-NLS-1$
        	} else {
            	BPELUtils.addExtensionAttribute(bpelElement, N2PEConstants.RETRY_MAX_RETRY_ACTION, "false"); //$NON-NLS-1$
        	}
        }
	}
    
    public static SystemErrorActionType getSystemErrorAction(OtherAttributesContainer container) {
        return (SystemErrorActionType) Xpdl2ModelUtil.getOtherAttribute(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SystemErrorAction());
    }

	public static void configureSystemErrorAction(org.eclipse.bpel.model.ExtensibleElement bpelElement, OtherAttributesContainer container) {
		SystemErrorActionType systemErrorAction = XPDLUtils.getSystemErrorAction(container);
        if (systemErrorAction != null) {
        	BPELUtils.addExtensionAttribute(bpelElement, N2PEConstants.RETRY_MAX_RETRY_ACTION, 
        			String.valueOf(SystemErrorActionType.HALT.equals(systemErrorAction))); 
        }
	}
    
	public static void configureCalendarReference(
			org.eclipse.bpel.model.ExtensibleElement bpelElement, 
			OtherElementsContainer container,
			List<ProcessRelevantData> variables) {
		CalendarReference calendarRef = XPDLUtils.getCalendarReference(container);
        if (calendarRef != null) {
        	if (calendarRef.getAlias() != null && calendarRef.getAlias().length() > 0) {
            	BPELUtils.addExtensionAttribute(bpelElement, N2PEConstants.CALENDAR_REFERENCE, calendarRef.getAlias());
        	} else if (calendarRef.getDataFieldId() != null) {
        		String dataFieldId = calendarRef.getDataFieldId();
        		String dataFieldName = null;
        		for (ProcessRelevantData prd : variables) {
					if (dataFieldId.equals(prd.getId())) {
						dataFieldName = prd.getName();
						break;
					}
				}
            	BPELUtils.addExtensionAttribute(bpelElement, N2PEConstants.CALENDAR_REFERENCE, "%" + dataFieldName +  "%"); //$NON-NLS-1$ //$NON-NLS-2$
        	}
        }
	}
    
    public static EventHandlerFlowStrategy getEventHandlerFlowStrategy(OtherAttributesContainer container) {
        return (EventHandlerFlowStrategy) Xpdl2ModelUtil.getOtherAttribute(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy());
    }

    public static SubProcessStartStrategy getSubProcessStartStrategy(OtherAttributesContainer container) {
        return (SubProcessStartStrategy) Xpdl2ModelUtil.getOtherAttribute(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_StartStrategy());
    }
    
    public static XpdModelType getXpdModelType(OtherAttributesContainer container) {
        return (XpdModelType) Xpdl2ModelUtil.getOtherAttribute(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType());
    }

    /**
     * Configure the Participant information for a given user task
     */
    public static Element addUserTaskParticipant(org.eclipse.bpel.model.ExtensibleElement bpelElement, Activity activity) {
    	Element extElement = null;
    	EObject[] performers = TaskObjectUtil.getActivityPerformers(activity);
    	if (performers.length == 1 && performers[0] instanceof Participant) {
    		Participant participant = (Participant) performers[0];
            ModelElement participantModel = BPMProcessOrgModelUtil.getOMModelElement(participant);
            if (participantModel != null) {
            	extElement = BPELUtils.makeExtensionElement(bpelElement, "userTaskParticipant");
            	extElement.setAttribute("uniqueId", participantModel.getId()); //$NON-NLS-1$
            	int modelVersion = BPMProcessOrgModelUtil.getOMModelVersion(participantModel);
            	extElement.setAttribute("modelVersion", String.valueOf(modelVersion)); //$NON-NLS-1$
            	String participantType = ""; //$NON-NLS-1$
            	if (participantModel instanceof Group) {
            		participantType = "GROUP"; //$NON-NLS-1$
            	} else if (participantModel instanceof Organization) {
            		participantType = "ORGANIZATION"; //$NON-NLS-1$
            	} else if (participantModel instanceof OrgUnit) {
            		participantType = "ORGANIZATIONAL_UNIT"; //$NON-NLS-1$
            	} else if (participantModel instanceof Position) {
            		participantType = "POSITION"; //$NON-NLS-1$
            	} else if (participantModel instanceof com.tibco.xpd.om.core.om.Resource) {
            		participantType = "RESOURCE"; //$NON-NLS-1$
            	} else if (participantModel instanceof Location) {
            		participantType = "LOCATION"; //$NON-NLS-1$
            	} else if (participantModel instanceof Capability) {
            		participantType = "CAPABILITY"; //$NON-NLS-1$
            	} else if (participantModel instanceof Privilege) {
            		participantType = "PRIVILEGE"; //$NON-NLS-1$
            	}
				extElement.setAttribute("entity", participantType); //$NON-NLS-1$
            }
    	}
    	return extElement;
    }

	public static String getActivityDeadlineEventId(Activity xpdlActivity) {
        return (String) Xpdl2ModelUtil.getOtherAttribute(xpdlActivity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_ActivityDeadlineEventId());
	}

    public static SignalData getSignalData(OtherElementsContainer container) {
        return (SignalData) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());
    }

    public static Long getMessageTimeout(Activity xpdlActivity) {
    	ConstantPeriod timeoutPeriod = (ConstantPeriod) xpdlActivity.getOtherElement("correlationTimeout");
    	Long seconds = new Long(0);
    	if (timeoutPeriod!=null) {
        	if (timeoutPeriod.getDays()!=null) {
        		seconds = seconds + 86400*timeoutPeriod.getDays().longValue();
        	}
        	if (timeoutPeriod.getHours()!=null) {
        		seconds = seconds + 3600*timeoutPeriod.getHours().longValue();
        	}
            if (timeoutPeriod.getMinutes()!=null) {
            	seconds = seconds + 60*timeoutPeriod.getMinutes().longValue();
            }
            if (timeoutPeriod.getSeconds()!=null) {
            	seconds = seconds + timeoutPeriod.getSeconds().longValue();
            }
        }
    	return seconds;
    }
    
    public static Boolean getCorrelateImmediately(OtherAttributesContainer container) {
        return Xpdl2ModelUtil.getOtherAttributeAsBoolean(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelateImmediately());
	}
    
	public static Boolean getSuspendResumeWithParent(OtherAttributesContainer container) {
        return Xpdl2ModelUtil.getOtherAttributeAsBoolean(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SuspendResumeWithParent());
	}
	
	public static AsyncExecutionMode getAsyncExecutionMode(OtherAttributesContainer container) {
        return (AsyncExecutionMode) Xpdl2ModelUtil.getOtherAttribute(container,
        		XpdExtensionPackage.eINSTANCE.getDocumentRoot_AsyncExecutionMode());
	}
	
	public static boolean isGlobalSignalType(OtherAttributesContainer container) {
        return SignalType.GLOBAL.equals((SignalType) Xpdl2ModelUtil.getOtherAttribute(container,
        		XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalType()));
	}

	public static boolean isCaseDataSignalType(OtherAttributesContainer container) {
        return SignalType.CASE_DATA.equals((SignalType) Xpdl2ModelUtil.getOtherAttribute(container,
        		XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalType()));
	}
	
	public static void findReferencedData(ConverterContext context, Activity xpdlActivity, String taskName, DataReferenceContext dataReferenceContext) {
		ProcessDataContextReferenceResolver resolver = ConverterActivator.getProcessDataContextReferenceResolver();
		Set<ProcessRelevantData> dataReferences = resolver.getDataReferences(xpdlActivity, dataReferenceContext);
		if (!dataReferences.isEmpty()) {
			Task task = context.getTaskDescriptor(taskName);
			for (ProcessRelevantData prd : dataReferences) {
				task.getStartingVariables().add(prd.getName());
			}
		}
	}

	public static void findReferencedDataInScript(ConverterContext context, Activity xpdlActivity, String taskName, DataReferenceContext dataReferenceContext) {
		ProcessDataContextReferenceResolver resolver = ConverterActivator.getProcessDataContextReferenceResolver();
		Set<ProcessRelevantData> dataReferences = resolver.getDataReferences(xpdlActivity, dataReferenceContext);
		if (!dataReferences.isEmpty()) {
			Task task = context.getTaskDescriptor(taskName);
			ProcessManagerScript script = ExtensionsFactory.eINSTANCE.createProcessManagerScript();
			if (DataReferenceContext.CONTEXT_INITIATE_SCRIPT.equals(dataReferenceContext)) {
				script.setType(ProcessManagerScriptType.INITIATE_LITERAL);
			} else if (DataReferenceContext.CONTEXT_COMPLETE_SCRIPT.equals(dataReferenceContext)) {
				script.setType(ProcessManagerScriptType.COMPLETE_LITERAL);
			} else if (DataReferenceContext.CONTEXT_CANCEL_SCRIPT.equals(dataReferenceContext)) {
				script.setType(ProcessManagerScriptType.CANCEL_LITERAL);
			} else if (DataReferenceContext.CONTEXT_TIMEOUT_SCRIPT.equals(dataReferenceContext)) {
				script.setType(ProcessManagerScriptType.TIMEOUT_LITERAL);
			} else if (DataReferenceContext.CONTEXT_ADHOC_PRECONDITION.equals(dataReferenceContext)) {
				script.setType(ProcessManagerScriptType.PRECONDITION_LITERAL);
			}
			for (ProcessRelevantData prd : dataReferences) {
				script.getVariables().add(prd.getName());
			}
			task.getScripts().add(script);
		}
	}

    public static boolean isReplyImmediately(OtherAttributesContainer container) {
        return Xpdl2ModelUtil.getOtherAttributeAsBoolean(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_ReplyImmediately());
    }

    public static ReplyImmediateDataMappings getReplyImmediateDataMappings(OtherElementsContainer container) {
        return (ReplyImmediateDataMappings) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_ReplyImmediateDataMappings());
    }

    public static CorrelationDataMappings getCorrelationDataMappings(OtherElementsContainer container) {
        return (CorrelationDataMappings) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelationDataMappings());
    }

    public static EventHandlerInitialisers getEventHandlerInitialisers(OtherElementsContainer container) {
        return (EventHandlerInitialisers) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerInitialisers());
    }

    public static String getImplements(OtherAttributesContainer otherAttributesContainer) {
        return (String) Xpdl2ModelUtil.getOtherAttribute(otherAttributesContainer,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements());
    }

    public static boolean getSignalHandlerAsynchronous(OtherAttributesContainer otherAttributesContainer) {
        return Xpdl2ModelUtil.getOtherAttributeAsBoolean(otherAttributesContainer,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalHandlerAsynchronous());
    }
    
    public static ProcessDataWorkItemAttributeMappings getProcessDataWorkItemAttributeMappings(OtherElementsContainer container) {
        return (ProcessDataWorkItemAttributeMappings) Xpdl2ModelUtil.getOtherElement(container,
        		XpdExtensionPackage.eINSTANCE.getDocumentRoot_ProcessDataWorkItemAttributeMappings());
    }

    public static AdHocTaskConfigurationType getAdHocTaskConfiguration(OtherElementsContainer container) {
        return (AdHocTaskConfigurationType) Xpdl2ModelUtil.getOtherElement(container,
        		XpdExtensionPackage.eINSTANCE.getDocumentRoot_AdHocTaskConfiguration());
    }

    public static boolean isEventSubProcess(OtherAttributesContainer otherAttributesContainer) {
        return Xpdl2ModelUtil.getOtherAttributeAsBoolean(otherAttributesContainer,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsEventSubProcess());
    }

    public static boolean isNonInterruptingEvent(OtherAttributesContainer otherAttributesContainer) {
        return Xpdl2ModelUtil.getOtherAttributeAsBoolean(otherAttributesContainer,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_NonInterruptingEvent());
    }

    /**
     * Check whether the process package containing the given element is
     * configured to use the old pre-AMX-BPM-2.0 Correlation PropertyName scheme
     * 
     * @param anyProcessPackageElement
     * 
     * @return <code>true</code> if the pre-AMX-BPM-2.0 correlation PropertyName
     *         scheme (i.e. <b>just the correlation datafield name</b>) should
     *         be used or <code>false</code> to use the new post-AMX-BPM-2.0
     *         schema should be used
     *         <b>PropertyName=ProcessName_CorrelationDataName</b>
     */
    public static boolean useUnqualifiedCorrelationPropertyNames(
            EObject anyProcessPackageElement) {
        /*
         * Process packages migrated from pre-AMX-BPM-2.0 (Studio v3.5.10) that
         * have correlation mappings will have had the
         * xpdExt:BxUseUnqualifiedPropertyNames attribute set to true. These we
         * want to use old preamx-bpm-2.0 (correlation data field name only)
         * for.
         * 
         * Packages created after v3.5.10 will not have the attribute at all so
         * we use new schema (ProcessName_CorrelationName) for.
         * 
         * Otherwise the user may have set the property either way explicitly
         * via advanced Properties ui.
         */
        Package pkg = (Package) Xpdl2ModelUtil.getAncestor(anyProcessPackageElement, Package.class);

        if (pkg != null) {
            Boolean useUnqualifiedPropertyNames = (Boolean) Xpdl2ModelUtil.getOtherAttribute(
            		pkg, XpdExtensionPackage.eINSTANCE.getDocumentRoot_BxUseUnqualifiedPropertyNames());

            if (useUnqualifiedPropertyNames != null) {
                /*
                 * Use UNqualified (just correlation name) for correlation property names.
                 */
                return useUnqualifiedPropertyNames;
            }
        }

        /* Use qualified (with process name) for correlation property names. */
        return false;
    }
    
	public static boolean getAllowUnqualifiedSubProcessIdentification(OtherAttributesContainer container) {
        return Xpdl2ModelUtil.getOtherAttributeAsBoolean(container, 
        		XpdExtensionPackage.eINSTANCE.getDocumentRoot_AllowUnqualifiedSubProcessIdentification());
	}

	public static Set<String> findOptionalVariables(Activity xpdlActivity) {
		Set<String> result = new HashSet<String>();
		
		Collection<ActivityInterfaceData> activityInterfaceData = ActivityInterfaceDataUtil.getActivityInterfaceData(xpdlActivity);
		for (ActivityInterfaceData interfaceData : activityInterfaceData) {
			findOptionalVariable(interfaceData.getData(), result);
		}
		Collection<ProcessRelevantData> allData = ProcessInterfaceUtil.getAllDataDefinedInProcess(xpdlActivity.getProcess());
		for (ProcessRelevantData prd : allData) {
			findOptionalVariable(prd, result);
		}
		
		return result;
	}

	private static void findOptionalVariable(ProcessRelevantData prd, Set<String> optionalVariables) {
		if (prd.getName() == null) {
			return;
		}
		
		if (prd instanceof FormalParameter && ((FormalParameter)prd).isRequired()) {
			return;
		}
		
		optionalVariables.add(prd.getName());
	}
	
    public static boolean isGlobalDataTask(Activity task) {
        if (task != null) {
            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(task);
            if (taskType == TaskType.SERVICE_LITERAL) {
                String extensionId = TaskObjectUtil.getTaskImplementationExtensionId(task);
                return TaskImplementationTypeDefinitions.GLOBAL_DATA.equals(extensionId);
            }
        }

        return false;
    }
    public static String getDataMapperScript(Expression expr) { 
    	DataMapperJavascriptGenerator datamapperjavascriptgenerator = new DataMapperJavascriptGenerator();
    	return datamapperjavascriptgenerator.convertMappingsToJavascript(expr);
    }
}
