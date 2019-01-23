/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Operation;
import javax.wsdl.Port;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.preferences.BpmBindingPreferenceUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.WsVirtualBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 *         Utility class that generates a Web Service Participant for a given
 *         wsdl service key and activity
 * 
 */
public class WSSystemParticSharedResUtil {

    /**
     * 
     */
    private static final int PARTIC_NAME_MAX_LENGTH = 48;

    /**
     * This class encapsulates the info for a system participant if it acts as
     * Provider/Consumer with Virtualisation/Soap Http/Soap Jms binding for an
     * abstract/concrete wsdl
     * 
     * */
    private static class ParticipantInfo {

        private WSParticRole participantRole;

        private WSTransportType transportType;

        private WsdlOpType wsdlOpType;

        private WsdlClassification wsdlClassification;

        private WsdlServiceKey key;

        private String endPointUrl;

        private IFile wsdlFile;

        /**
         * @return the wsdlFile
         */
        public IFile getWsdlFile() {
            return wsdlFile;
        }

        /**
         * @param wsdlFile
         *            the wsdlFile to set
         */
        public void setWsdlFile(IFile wsdlFile) {
            this.wsdlFile = wsdlFile;
        }

        /**
         * @return the endPointUrl
         */
        public String getEndPointUrl() {
            return endPointUrl;
        }

        /**
         * @param endPointUrl
         *            the endPointUrl to set
         */
        public void setEndPointUrl(String endPointUrl) {
            this.endPointUrl = endPointUrl;
        }

        /**
         * @return the key
         */
        public WsdlServiceKey getKey() {
            return key;
        }

        /**
         * @param key
         */
        public ParticipantInfo(WsdlServiceKey key) {
            super();
            this.key = key;
        }

        /**
         * @return the wsdlClassification
         */
        public WsdlClassification getWsdlClassification() {
            return wsdlClassification;
        }

        /**
         * @param wsdlClassification
         *            the wsdlClassification to set
         */
        public void setWsdlClassification(WsdlClassification wsdlClassification) {
            this.wsdlClassification = wsdlClassification;
        }

        /**
         * @return the participantRole
         */
        public WSParticRole getParticipantRole() {
            return participantRole;
        }

        /**
         * @param participantRole
         *            the participantRole to set
         */
        public void setParticipantRole(WSParticRole participantRole) {
            this.participantRole = participantRole;
        }

        /**
         * @return the transportType
         */
        public WSTransportType getTransportType() {
            return transportType;
        }

        /**
         * @param transportType
         *            the transportType to set
         */
        public void setTransportType(WSTransportType transportType) {
            this.transportType = transportType;
        }

        /**
         * @return the wsdlType
         */
        public WsdlOpType getWsdlOpType() {
            return wsdlOpType;
        }

        /**
         * @param wsdlOpType
         *            the wsdl Operation Type to set
         */
        public void setWsdlOpType(WsdlOpType wsdlOpType) {
            this.wsdlOpType = wsdlOpType;
        }

    }

    public enum WSParticRole {
        CONSUMER,
        PROVIDER
    }

    public enum WSTransportType {
        SERVICE_VIRTUALISATION,
        SOAP_OVER_HTTP,
        SOAP_OVER_JMS
    }

    public enum WsdlOpType {
        ABSTRACT,
        CONCRETE
    }

    public enum WsdlClassification {
        GENERATED,
        USER_DEFINED
    }

    /**
     * 
     */
    private static final String LOCATION = "location"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String NAME = "name"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String ADDRESS = "address"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String CONTEXT = "context"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String TARGET_ADDRESS = "targetAddress"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String JAVA_NAMING_PROVIDER_URL =
            "java.naming.provider.url"; //$NON-NLS-1$

    /**
     * Generated participant is acting as a provider for abstract operation
     * */
    private static final String PROVIDER_ABSTRACT = "_Provider";

    /**
     * Generated participant is acting as a provider for concrete operation
     * */
    private static final String PROVIDER_CONCRETE = "_Provider";

    /**
     * Generated participant is acting as a consumer for abstract operation with
     * virtualisation binding
     * */
    private static final String CONSUMER_VIRTUAL = "_Consumer";

    /**
     * Generated participant is acting as a consumer for concrete operation with
     * soap over http binding
     * */
    private static final String CONSUMER_HTTP = "_Consumer";

    private static final String SLASH = "/"; //$NON-NLS-1$

    /**
     * For a participant to be generated for the given WsdlServiceKey and
     * activity, return the participant info if it is a Provider/Consumer with
     * Virtualisation/Soap over HTTP binding invoking a Abstract/Concrete
     * operation for a Generated/User defined wsdl
     * 
     * @param key
     *            WsdlServiceKey
     * @param activity
     * @param project
     * 
     * @return ParticipantInfo
     * */
    private static ParticipantInfo getParticipantInfo(WsdlServiceKey key,
            Activity activity, IProject project) {

        String endPointUrl = null;

        String serviceName = key.getService();
        String portName = key.getPort();

        ParticipantInfo participantInfo = new ParticipantInfo(key);

        /* decide whether the participant will act as Provider or Consumer */
        if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
            participantInfo.setParticipantRole(WSParticRole.PROVIDER);
        } else {
            participantInfo.setParticipantRole(WSParticRole.CONSUMER);
        }

        /*
         * check if the activity is referring a generated wsdl or user defined
         * wsdl
         */
        IFile wsdlFile = null;

        IndexerItem item =
                WsdlIndexerUtil.getOperationItem(project, key, true, true);
        if (item != null) {
            wsdlFile = WsdlIndexerUtil.getWsdlFile(item);
        }

        if (null != wsdlFile) {

            participantInfo.setWsdlFile(wsdlFile);

            if (Xpdl2ModelUtil.isWsdlStudioGenerated(wsdlFile)) {
                participantInfo
                        .setWsdlClassification(WsdlClassification.GENERATED);
            } else {
                participantInfo
                        .setWsdlClassification(WsdlClassification.USER_DEFINED);
            }
        }

        /*
         * find whether operation selected is abstract or concrete
         * 
         * service name and port name are null for abstract operation selection
         */

        if (serviceName != null && portName != null) {

            Port port =
                    WsdlIndexerUtil.getPort(project,
                            serviceName,
                            portName,
                            key.getFilePath(),
                            true,
                            true);
            if (port != null) {
                participantInfo.setWsdlOpType(WsdlOpType.CONCRETE);

                /*
                 * XPD-1778: when a concrete operation from bw service is
                 * selected, we want a consumer virtualised participant to be
                 * created. (currently we do not support soap over jms binding)
                 */
                if (Xpdl2WsdlUtil.SOAP_OVER_JMS_URL.equals(participantInfo
                        .getKey().getTransportURI())) {
                    participantInfo
                            .setTransportType(WSTransportType.SOAP_OVER_JMS);
                } else {
                    participantInfo
                            .setTransportType(WSTransportType.SOAP_OVER_HTTP);
                    endPointUrl = getLocalWsdlTargetAddress(port);
                    participantInfo.setEndPointUrl(endPointUrl);
                }

            }

        } else {
            /*
             * considering that its a abstract wsdl operation, so setting
             * WsdlOpType to abstract and transport type to service
             * virtualisation
             */
            endPointUrl = key.getPortType();
            if (isValidKeyAndUrl(key, endPointUrl)) {
                participantInfo.setWsdlOpType(WsdlOpType.ABSTRACT);
                /* for provider participants transport is soap over http */
                if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {

                    javax.wsdl.Definition definition =
                            Xpdl2WsdlUtil.getDefinition(wsdlFile);

                    if (null != definition) {
                        /* wsdl has bindings */
                        if (definition.getBindings().size() > 0) {
                            boolean isBWSvc = isBWService(key, project);

                            if (isBWSvc) {
                                participantInfo
                                        .setTransportType(WSTransportType.SOAP_OVER_JMS);
                            } else {
                                participantInfo
                                        .setTransportType(WSTransportType.SOAP_OVER_HTTP);
                                participantInfo.setEndPointUrl(endPointUrl);
                            }
                        } else if (definition.getBindings().size() == 0) {
                            /*
                             * wsdl is abstract with no bindings. in such case
                             * we generate a provider participant with service
                             * virtualization binding and soap over http /soap
                             * over jms binding based on the preference user has
                             * selected
                             */
                            if (BpmBindingPreferenceUtil.isHttpBinding()) {
                                participantInfo
                                        .setTransportType(WSTransportType.SOAP_OVER_HTTP);
                                participantInfo.setEndPointUrl(endPointUrl);

                            } else if (BpmBindingPreferenceUtil.isJmsBinding()) {
                                participantInfo
                                        .setTransportType(WSTransportType.SOAP_OVER_JMS);
                            }
                        }

                    }

                } else {
                    participantInfo
                            .setTransportType(WSTransportType.SERVICE_VIRTUALISATION);
                    if (null == key.getTransportURI()) {
                        key.setTransportURI(Xpdl2WsdlUtil.SERVICE_VIRTUALIZATION_URL);
                    }
                    participantInfo.setEndPointUrl(endPointUrl);
                }
            }
        }

        return participantInfo;
    }

    /**
     * @param key
     * @param project
     * @return
     */
    private static boolean isBWService(WsdlServiceKey key, IProject project) {
        boolean isBWSvc = false;

        Operation operation =
                WsdlIndexerUtil.getOperation(project, key, true, true);
        Definition wsdlDef = ((WSDLElement) operation).getEnclosingDefinition();

        if (wsdlDef != null) {
            Collection services = wsdlDef.getServices().values();
            if (services.size() > 0) {
                javax.wsdl.Service service =
                        (javax.wsdl.Service) services.iterator().next();
                Collection<?> ports = service.getPorts().values();
                if (ports.size() > 0) {
                    org.eclipse.wst.wsdl.Port port =
                            (org.eclipse.wst.wsdl.Port) ports.iterator().next();

                    List<ExtensibilityElement> elems =
                            port.getEExtensibilityElements();
                    for (ExtensibilityElement elem : elems) {

                        if (elem instanceof EObject) {
                            EObject eo = elem;

                            Object eoValue =
                                    eo.eGet(WSDLPackage.eINSTANCE
                                            .getWSDLElement_Element());

                            if (eoValue instanceof Element) {
                                Element domElement = (Element) eoValue;
                                if (TARGET_ADDRESS.equals(domElement
                                        .getLocalName())) {
                                    if (domElement.getFirstChild() instanceof Text) {
                                        Text textContent =
                                                (Text) domElement
                                                        .getFirstChild();

                                        if (textContent.getData().length() > 0) {
                                            isBWSvc = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return isBWSvc;
    }

    /**
     * @param key
     * @param endPointUrl
     * @return
     */
    private static boolean isValidKeyAndUrl(WsdlServiceKey key,
            String endPointUrl) {
        return null != endPointUrl && null != key.getPortTypeOperation()
                && endPointUrl.trim().length() > 0
                && key.getPortTypeOperation().trim().length() > 0;
    }

    /**
     * Gets the JMS Target URI for a given wsdl file - it is basically a
     * combination of the target address and the provider url of the selected
     * port in the specified service of the wsdl.
     * 
     * @param portName
     *            The port name.
     * @return The target address.
     */
    protected static String getLocalWsdlTargetAddress(Port port) {
        String localWsdlTargetAddress = ""; //$NON-NLS-1$
        boolean isTargetAddressSet = false;
        boolean isProviderURLSet = false;
        String targetAddress = null;
        if (port != null) {
            List<?> elems = port.getExtensibilityElements();
            for (Object elem : elems) {

                if (elem instanceof WSDLElement) {
                    WSDLElement eo = (WSDLElement) elem;

                    Object eoValue =
                            eo.eGet(WSDLPackage.eINSTANCE
                                    .getWSDLElement_Element());

                    if (eoValue instanceof Element) {
                        Element domElement = (Element) eoValue;
                        if (TARGET_ADDRESS.equals(domElement.getLocalName())
                                && !isTargetAddressSet) {
                            if (domElement.getFirstChild() instanceof Text) {
                                Text textContent =
                                        (Text) domElement.getFirstChild();
                                if (textContent.getLength() > 0) {
                                    targetAddress =
                                            textContent.getTextContent();
                                    isTargetAddressSet = true;
                                    break;
                                }
                            }
                        } else if (CONTEXT.equals(domElement.getLocalName())
                                && !isProviderURLSet) {
                            NodeList nodeList = domElement.getChildNodes();

                            for (int count = 0; count < nodeList.getLength(); count++) {
                                Node item = nodeList.item(count);
                                NamedNodeMap attributes = item.getAttributes();
                                if (attributes != null) {
                                    for (int count2 = 0; count2 < attributes
                                            .getLength(); count2++) {

                                        Node node = attributes.item(count2);
                                        if (node != null
                                                && NAME.equals(node
                                                        .getNodeName())) {
                                            String nodeValue =
                                                    node.getNodeValue();
                                            if (JAVA_NAMING_PROVIDER_URL
                                                    .equals(nodeValue)) {
                                                String textContent =
                                                        item.getTextContent();
                                                if (textContent.length() > 0) {
                                                    localWsdlTargetAddress =
                                                            textContent;
                                                    isProviderURLSet = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        } else if (ADDRESS.equals(domElement.getLocalName())) {
                            /* Assuming it is soap address */
                            String textContent = domElement.getTextContent();
                            if (textContent.length() > 0) {
                                localWsdlTargetAddress = textContent;
                                break;
                            } else {
                                /*
                                 * Assuming that if the element content is null
                                 * then the WSDL isn't a BW WSDL and the
                                 * location URI will be passed to create the
                                 * system participant
                                 */
                                NamedNodeMap attributes =
                                        domElement.getAttributes();
                                Node namedItem =
                                        attributes.getNamedItem(LOCATION);
                                if (namedItem != null) {
                                    localWsdlTargetAddress =
                                            namedItem.getNodeValue();
                                }
                            }
                        }
                    }
                }
            }
        }
        if (targetAddress != null && targetAddress.length() > 0) {
            return targetAddress + "_" + localWsdlTargetAddress; //$NON-NLS-1$
        } else {
            return localWsdlTargetAddress;
        }

    }

    /**
     * Return a Provider/Consumer participant as per the participant info
     * created for the given WsdlServiceKey and activity (this is called when a
     * operation is dragged from service descriptors folder in project explorer
     * and dropped on to the editor to create a task)
     * 
     * @param process
     * @param activity
     * @param key
     *            - the WsdlServiceKey
     * @param objectsToCreate
     *            - list
     * 
     * @return Participant
     * */
    public static Participant autoAssignEndpointParticipantCommand(
            Process process, Activity activity, WsdlServiceKey key,
            List<Object> objectsToCreate) {

        Participant sysParticipant = null;

        if (process != null) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
            IResource res = wc.getEclipseResources().get(0);

            if (res != null) {
                IProject project = res.getProject();
                ParticipantInfo participantInfo =
                        getParticipantInfo(key, activity, project);

                /* provider participants */
                if (WSParticRole.PROVIDER.equals(participantInfo
                        .getParticipantRole())) {
                    sysParticipant =
                            getOrCreateProviderParticipant(participantInfo,
                                    process,
                                    activity,
                                    objectsToCreate);
                } else if (WSParticRole.CONSUMER.equals(participantInfo
                        .getParticipantRole())) {
                    sysParticipant =
                            getOrCreateConsumerParticipant(participantInfo,
                                    process,
                                    activity,
                                    objectsToCreate);
                }

            }
        }
        return sysParticipant;
    }

    /**
     * Return a Provider/Consumer participant as per the participant info
     * created for the given WsdlServiceKey and activity (this is called when a
     * operation is dragged from service descriptors folder in project explorer
     * and dropped on to the task already present on the editor or when a
     * operation is selected from the properties section)
     * 
     * @param editingDomain
     * @param process
     * @param activity
     * @param key
     *            - WsdlServiceKey
     * @param cpdCmd
     *            - CompoundCommand
     * 
     * @return Participant
     * */
    public static Participant autoAssignEndpointParticipantCommand(
            EditingDomain editingDomain, Process process, Activity activity,
            WsdlServiceKey key, CompoundCommand cpdCmd) {

        Participant sysParticipant = null;

        if (process != null) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
            IResource res = wc.getEclipseResources().get(0);

            if (res != null) {
                IProject project = res.getProject();
                ParticipantInfo participantInfo =
                        getParticipantInfo(key, activity, project);

                /* provider participants */
                if (WSParticRole.PROVIDER.equals(participantInfo
                        .getParticipantRole())) {
                    sysParticipant =
                            getOrCreateProviderParticipant(participantInfo,
                                    editingDomain,
                                    process,
                                    activity,
                                    cpdCmd);

                } else if (WSParticRole.CONSUMER.equals(participantInfo
                        .getParticipantRole())) {
                    /*
                     * XPD-3040: generate the participant in the package that
                     * this consuming/invoking activity's process belongs to
                     */
                    if (activity.getProcess().getPackage() == process
                            .getPackage()) {
                        sysParticipant =
                                getOrCreateConsumerParticipant(participantInfo,
                                        editingDomain,
                                        process,
                                        activity,
                                        cpdCmd);
                    } else {
                        sysParticipant =
                                getOrCreateConsumerParticipant(participantInfo,
                                        editingDomain,
                                        activity.getProcess(),
                                        activity,
                                        cpdCmd);
                    }
                }
            }
        }
        return sysParticipant;
    }

    /**
     * @param participantInfo
     * @param editingDomain
     * @param process
     * @param activity
     * @param cpdCmd
     *            - CompoundCommand
     * 
     * @return Participant
     */
    private static Participant getOrCreateProviderParticipant(
            ParticipantInfo participantInfo, EditingDomain editingDomain,
            Process process, Activity activity, CompoundCommand cpdCmd) {

        Participant sysParticipant = null;

        if (WsdlOpType.ABSTRACT.equals(participantInfo.getWsdlOpType())
                || WsdlOpType.CONCRETE.equals(participantInfo.getWsdlOpType())) {

            /* get the participant if it exists */
            sysParticipant =
                    findParticipant(participantInfo, process, activity);

            if (!isParticipantInScope(sysParticipant, process)) {

                sysParticipant =
                        createWSParticipantForUserDefinedWsdl(participantInfo,
                                process,
                                activity);
                if (null != sysParticipant) {
                    commandToAddParticipant(participantInfo,
                            editingDomain,
                            process,
                            cpdCmd,
                            sysParticipant);
                }
            }

        }
        return sysParticipant;
    }

    /**
     * @param participantInfo
     * @param process
     * @param activity
     * @param objectsToCreate
     *            - list
     * 
     * @return Participant
     */
    private static Participant getOrCreateProviderParticipant(
            ParticipantInfo participantInfo, Process process,
            Activity activity, List<Object> objectsToCreate) {

        Participant sysParticipant = null;

        if (WsdlOpType.ABSTRACT.equals(participantInfo.getWsdlOpType())
                || WsdlOpType.CONCRETE.equals(participantInfo.getWsdlOpType())) {

            /* get the participant if it exists */
            sysParticipant =
                    findParticipant(participantInfo, process, activity);

            if (!isParticipantInScope(sysParticipant, process)) {

                if (WsdlClassification.USER_DEFINED.equals(participantInfo
                        .getWsdlClassification())) {
                    sysParticipant =
                            createWSParticipantForUserDefinedWsdl(participantInfo,
                                    process,
                                    activity);
                    if (null != sysParticipant) {
                        objectsToCreate.add(sysParticipant);
                    }
                }
            }

        }
        return sysParticipant;
    }

    /**
     * @param participantInfo
     * @param editingDomain
     * @param process
     * @param activity
     * @param cpdCmd
     * 
     * @return Participant
     */
    private static Participant getOrCreateConsumerParticipant(
            ParticipantInfo participantInfo, EditingDomain editingDomain,
            Process process, Activity activity, CompoundCommand cpdCmd) {

        Participant sysParticipant = null;

        if (WsdlOpType.ABSTRACT.equals(participantInfo.getWsdlOpType())
                || WsdlOpType.CONCRETE.equals(participantInfo.getWsdlOpType())) {

            /* get the participant if it exists */
            sysParticipant =
                    findParticipant(participantInfo, process, activity);

            if (!isParticipantInScope(sysParticipant, process)) {
                sysParticipant =
                        createWSParticipantForUserDefinedWsdl(participantInfo,
                                process,
                                activity);
                if (null != sysParticipant) {
                    commandToAddParticipant(participantInfo,
                            editingDomain,
                            process,
                            cpdCmd,
                            sysParticipant);
                }
            }

        }
        return sysParticipant;
    }

    /**
     * @param participantInfo
     * @param process
     * @param activity
     * @param objectsToCreate
     * 
     * @return Participant
     */
    private static Participant getOrCreateConsumerParticipant(
            ParticipantInfo participantInfo, Process process,
            Activity activity, List<Object> objectsToCreate) {

        Participant sysParticipant = null;

        if (WsdlOpType.ABSTRACT.equals(participantInfo.getWsdlOpType())
                || WsdlOpType.CONCRETE.equals(participantInfo.getWsdlOpType())) {

            /* get the participant if it exists */
            sysParticipant =
                    findParticipant(participantInfo, process, activity);

            if (!isParticipantInScope(sysParticipant, process)) {
                sysParticipant =
                        createWSParticipantForUserDefinedWsdl(participantInfo,
                                process,
                                activity);
                if (null != sysParticipant) {
                    objectsToCreate.add(sysParticipant);
                }
            }

        }
        return sysParticipant;
    }

    /**
     * @param participantInfo
     * @param process
     * @param activity
     */
    protected static Participant findParticipant(
            ParticipantInfo participantInfo, Process process, Activity activity) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
        IResource res = wc.getEclipseResources().get(0);
        IProject project = res.getProject();

        /* Get participants in scope of this process */
        Map<String, Participant> partics = new HashMap<String, Participant>();
        for (Participant p : process.getParticipants()) {
            partics.put(p.getId(), p);
        }
        for (Participant p : process.getPackage().getParticipants()) {
            partics.put(p.getId(), p);
        }

        List<ActivityWebServiceReference> webServiceReferences =
                getIndexedWebServiceReferences(project,
                        participantInfo,
                        process,
                        activity);
        for (ActivityWebServiceReference webServiceRef : webServiceReferences) {

            /* Check not null and port/transport type matches. */
            if (isNotNull(participantInfo, webServiceRef)
                    && isEqual(participantInfo, webServiceRef)) {

                /* Check participant is in scope of target process. */
                Participant participant =
                        partics.get(webServiceRef.getParticipantId());
                if (participant != null) {
                    return participant;
                }
            }
        }

        return null;
    }

    /**
     * @param participantInfo
     * @param webServiceRef
     * @return
     */
    private static boolean isEqual(ParticipantInfo participantInfo,
            ActivityWebServiceReference webServiceRef) {
        if (WsdlOpType.ABSTRACT.equals(participantInfo.getWsdlOpType())) {
            if (WSParticRole.CONSUMER.equals(participantInfo
                    .getParticipantRole())) {
                if (Xpdl2WsdlUtil.SOAP_OVER_JMS_URL.equals(webServiceRef
                        .getTransportType())) {
                    return participantInfo.getKey().getPortType()
                            .equals(webServiceRef.getPortTypeName());
                }
                return participantInfo.getKey().getPortType()
                        .equals(webServiceRef.getPortTypeName())
                        && participantInfo.getKey().getTransportURI()
                                .equals(webServiceRef.getTransportType());
            } else if (WSParticRole.PROVIDER.equals(participantInfo
                    .getParticipantRole())) {
                return participantInfo.getKey().getPortType()
                        .equals(webServiceRef.getPortTypeName());
            }
        } else if (WsdlOpType.CONCRETE.equals(participantInfo.getWsdlOpType())) {
            /*
             * XPD-1778: it is a concrete operation with soap over jms binding,
             * then we want a consumer virtualised participant
             */
            if (WSTransportType.SOAP_OVER_JMS.equals(participantInfo
                    .getTransportType())) {
                return participantInfo.getKey().getPortType()
                        .equals(webServiceRef.getPortTypeName());
            }
            return participantInfo.getKey().getPort()
                    .equals(webServiceRef.getPortName())
                    && participantInfo.getKey().getTransportURI()
                            .equals(webServiceRef.getTransportType());
        }
        return false;
    }

    /**
     * @param participantInfo
     * @param webServiceRef
     * @return
     */
    private static boolean isNotNull(ParticipantInfo participantInfo,
            ActivityWebServiceReference webServiceRef) {
        return isPortOrPortTypeValid(participantInfo, webServiceRef)
                && isTransportValid(participantInfo, webServiceRef);
    }

    /**
     * @param participantInfo
     * @param webServiceRef
     * @return
     */
    private static boolean isTransportValid(ParticipantInfo participantInfo,
            ActivityWebServiceReference webServiceRef) {
        /*
         * XPD-1778: it is a concrete operation with soap over jms binding, then
         * we want a consumer virtualised participant
         */
        if (WSParticRole.CONSUMER.equals(participantInfo.getParticipantRole())) {
            if (WSTransportType.SOAP_OVER_HTTP.equals(participantInfo
                    .getTransportType())) {
                return null != participantInfo.getKey().getTransportURI()
                        && null != webServiceRef.getTransportType();
            }
        }
        return true;
    }

    /**
     * @param participantInfo
     * @param webServiceRef
     * @return
     */
    private static boolean isPortOrPortTypeValid(
            ParticipantInfo participantInfo,
            ActivityWebServiceReference webServiceRef) {
        if (WsdlOpType.CONCRETE.equals(participantInfo.getWsdlOpType())) {
            /*
             * XPD-1778: it is a concrete operation with soap over jms binding,
             * then we want a consumer virtualised participant
             */
            if (!WSTransportType.SOAP_OVER_JMS.equals(participantInfo
                    .getTransportType())) {
                return null != participantInfo.getKey().getPort()
                        && null != webServiceRef.getPortName();
            }
        }
        return null != participantInfo.getKey().getPortType()
                && null != webServiceRef.getPortTypeName();
    }

    /**
     * @param project
     * @param participantInfo
     * @param activity
     * 
     * @return List of web service reference info.
     */
    private static List<ActivityWebServiceReference> getIndexedWebServiceReferences(
            IProject project, ParticipantInfo participantInfo, Process process,
            Activity activity) {

        List<ActivityWebServiceReference> webSvcRefs =
                new ArrayList<ActivityWebServiceReference>();

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();

        additionalAttributes
                .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_IS_API_ACTIVITY,
                        Boolean.toString(WSParticRole.PROVIDER
                                .equals(participantInfo.getParticipantRole())));

        /*
         * PROVIDER participants have both virtualisation and soap http
         * transport type so we will not set the criteria in the indexer for
         * transport type
         */
        if (WSParticRole.CONSUMER.equals(participantInfo.getParticipantRole())) {
            /*
             * transport uri is null in case of service virtualisation and we
             * dont want to search for transport uri in case of soap over jms
             * binding
             */

            if (null != participantInfo.getKey().getTransportURI()
                    && (WSTransportType.SOAP_OVER_HTTP.equals(participantInfo
                            .getTransportType()) || WSTransportType.SOAP_OVER_JMS
                            .equals(participantInfo.getTransportType()))) {
                additionalAttributes
                        .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_TRANSPORT_TYPE,
                                participantInfo.getKey().getTransportURI());
            }
        }

        if (WsdlOpType.ABSTRACT.equals(participantInfo.getWsdlOpType())) {
            additionalAttributes
                    .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_PORT_TYPE_NAME,
                            participantInfo.getKey().getPortType());
        } else if (WsdlOpType.CONCRETE.equals(participantInfo.getWsdlOpType())) {
            /*
             * XPD-1778: it is a concrete operation with soap over jms binding,
             * then we want a consumer virtualised participant
             */
            if (WSTransportType.SOAP_OVER_JMS.equals(participantInfo
                    .getTransportType())) {
                additionalAttributes
                        .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_PORT_TYPE_NAME,
                                participantInfo.getKey().getPortType());
            } else {
                additionalAttributes
                        .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_PORT_NAME,
                                participantInfo.getKey().getPort());
            }
        }

        additionalAttributes
                .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_WSDL_FILEPATH,
                        participantInfo.getKey().getFilePath());

        /* We only want to find participants in the same xpdl file. */
        IFile xpdlFile = WorkingCopyUtil.getFile(process);
        additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_PATH, xpdlFile
                .getFullPath().toPortableString());

        IFile wsdlFile = participantInfo.getWsdlFile();

        if (null != wsdlFile) {
            String wsdlNamespace = calculateWsdlNamespace(activity, wsdlFile);

            additionalAttributes
                    .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_WSDL_NAMESPACE,
                            wsdlNamespace);
        }

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

        if (result != null && !result.isEmpty()) {
            for (IndexerItem item : result) {
                ActivityWebServiceReference ref =
                        ActivityWebServiceReference
                                .createActivityWebServiceReference(item);
                if (ref != null) {
                    webSvcRefs.add(ref);
                }
            }
        }

        return webSvcRefs;
    }

    /**
     * @param activity
     * @param wsdlFile
     * 
     * @return The target namespace for the activity / wsdl
     */
    private static String calculateWsdlNamespace(Activity activity,
            IFile wsdlFile) {
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
            }
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * @param participant
     * @return <code>true</code> if the given participant is not
     *         <code>null</code> and it is in scope of given process (i.e. is in
     *         process's package or process itself).
     */
    private static boolean isParticipantInScope(Participant participant,
            Process process) {
        if (participant != null) {
            if (participant.eContainer() instanceof Process) {
                if (participant.eContainer().equals(process)) {
                    return true;
                }
            } else if (participant.eContainer() instanceof Package) {
                if (participant.eContainer().equals(process.getPackage())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param participantInfo
     * @param editingDomain
     * @param process
     * @param cpdCmd
     * @param sysParticipant
     */
    private static void commandToAddParticipant(
            ParticipantInfo participantInfo, EditingDomain editingDomain,
            Process process, CompoundCommand cpdCmd, Participant sysParticipant) {
        cpdCmd.append(AddCommand.create(editingDomain,
                process.getPackage(),
                Xpdl2Package.eINSTANCE.getParticipantsContainer_Participants(),
                sysParticipant));
        /*
         * SHouldn't come here for generated api activities but if one day we do
         * this will ensure that we set the api participant id in the process.
         * The process api endpoint participant reference is ONLY for generated
         * participant.
         */
        if (WSParticRole.PROVIDER.equals(participantInfo.getParticipantRole())
                && WsdlClassification.GENERATED.equals(participantInfo
                        .getWsdlClassification())) {
            /*
             * Set the process to reference the new api activity (that refers to
             * user wsdl) endpoint participant.
             */
            cpdCmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ApiEndPointParticipant(),
                            sysParticipant.getId()));
        }
    }

    /**
     * @param participantInfo
     * @param process
     * @param activity
     * @return Participant
     */
    protected static Participant createWSParticipantForUserDefinedWsdl(
            ParticipantInfo participantInfo, Process process, Activity activity) {

        String displayName = getParticLabel(participantInfo);

        /*
         * Create system participant even if the shared resource uri path is
         * empty string
         */
        Participant sysParticipant = Xpdl2Factory.eINSTANCE.createParticipant();

        String uniqueDisplayNameInSet =
                Xpdl2ModelUtil.getUniqueDisplayNameInSet(displayName,
                        getPackageParticipants(process.getPackage()),
                        false);
        String participantName = uniqueDisplayNameInSet;
        sysParticipant.setName(participantName);
        Xpdl2ModelUtil.setOtherAttribute(sysParticipant,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                uniqueDisplayNameInSet);

        /* Set system participant type */
        ParticipantTypeElem typeElem =
                Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
        typeElem.setType(ParticipantType.SYSTEM_LITERAL);
        sysParticipant.setParticipantType(typeElem);

        /* Add the sharedResource extension */
        ParticipantSharedResource sharedRes =
                createWsParticipantSharedResource(participantInfo,
                        activity,
                        participantName,
                        participantInfo.getKey().getPort());

        Xpdl2ModelUtil.setOtherElement(sysParticipant,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ParticipantSharedResource(),
                sharedRes);

        return sysParticipant;
    }

    /**
     * Creates the model for participant shared resource that is added to the
     * Participant Xpd Extension model.
     * 
     * @param participantInfo
     * @param activity
     * @param sharedResUriPath
     * @param participantName
     * @param portName
     * @return
     */
    protected static ParticipantSharedResource createWsParticipantSharedResource(
            ParticipantInfo participantInfo, Activity activity,
            String participantName, String portName) {

        /* Add the sharedResource extension */
        ParticipantSharedResource sharedRes =
                XpdExtensionFactory.eINSTANCE.createParticipantSharedResource();

        WsResource wsResource =
                XpdExtensionFactory.eINSTANCE.createWsResource();

        if (WSParticRole.CONSUMER.equals(participantInfo.getParticipantRole())) {
            WsOutbound wsOutbound =
                    XpdExtensionFactory.eINSTANCE.createWsOutbound();
            createConsumerParticSharedRes(participantInfo,
                    participantName,
                    portName,
                    wsResource,
                    wsOutbound);

        } else if (WSParticRole.PROVIDER.equals(participantInfo
                .getParticipantRole())) {

            createProviderParticSharedRes(participantInfo,
                    participantName,
                    wsResource);
        }

        sharedRes.setSharedResource(wsResource);
        return sharedRes;
    }

    /**
     * @param participantInfo
     * @param participantName
     * @param sharedResUriPath
     * @param wsResource
     */
    private static void createProviderParticSharedRes(
            ParticipantInfo participantInfo, String participantName,
            WsResource wsResource) {

        WsInbound wsInBound =
                XpdExtensionFactory.eINSTANCE.createWsInboundDefault();
        wsInBound.setVirtualBinding(XpdExtensionFactory.eINSTANCE
                .createWsVirtualBindingDefault());

        /*
         * XPD-1766: generated participant must honour the binding style
         * (Document/RPC) according to the wsdl operation selected
         */
        Definition definition =
                (Definition) Xpdl2WsdlUtil.getDefinition(participantInfo
                        .getWsdlFile());
        SoapBindingStyle bindingStyle = null;
        if (definition != null) {
            bindingStyle = WsdlUtil.getBindingStyle(definition);
        }

        if (WSTransportType.SOAP_OVER_JMS.equals(participantInfo
                .getTransportType())) {

            createSOAPJMSProviderSharedRes(participantName,
                    wsInBound,
                    bindingStyle);

        } else {

            createSOAPHTTPProviderSharedRes(wsInBound,
                    bindingStyle,
                    participantInfo);
        }
        wsResource.setInbound(wsInBound);
    }

    /**
     * @param participantInfo
     * @param sharedResUriPath
     * @return
     */
    private static String getSharedResUriPath(ParticipantInfo participantInfo,
            String sharedResUriPath) {
        String wsdlNameWithoutExtn =
                getWsdlNameWithoutSpacesWithoutExtn(participantInfo);

        Path path = new Path(SLASH);
        Path wsdlNamePath = (Path) path.append(wsdlNameWithoutExtn);

        Path sharedResPath = new Path(sharedResUriPath);
        if (!sharedResPath.isPrefixOf(path)) {
            sharedResPath = (Path) path.append(sharedResUriPath);
        }

        /*
         * XPD-1766: composite requires the URI to be in format /A/B i.e. /<wsdl
         * file name>/<port type name> or <port name>. for both abstract and
         * concrete operations (XPD-2915) the endpoint url must have the
         * required format
         */
        sharedResUriPath =
                wsdlNamePath.toPortableString()
                        + sharedResPath.toPortableString();
        return sharedResUriPath;
    }

    /**
     * @param sharedResUriPath
     * @param wsInBound
     * @param bindingStyle
     */
    private static void createSOAPHTTPProviderSharedRes(WsInbound wsInBound,
            SoapBindingStyle bindingStyle, ParticipantInfo participantInfo) {
        String sharedResUriPath = getSharedResourceURI(participantInfo);

        sharedResUriPath =
                getSharedResUriPath(participantInfo, sharedResUriPath);

        WsSoapHttpInboundBinding wsSoapHttpInboundBindingDefault =
                XpdExtensionFactory.eINSTANCE
                        .createWsSoapHttpInboundBindingDefault();

        wsSoapHttpInboundBindingDefault.setEndpointUrlPath(sharedResUriPath);
        /*
         * XPD-1594: soap http binding list must be cleared before adding new
         * binding details
         */
        wsInBound.getSoapHttpBinding().clear();
        wsInBound.getSoapHttpBinding().add(wsSoapHttpInboundBindingDefault);
        /*
         * TODO: if binding style is null default it to rpc literal now. but
         * will have to change to document literal when we decide document
         * literal has to be the default binding style
         */
        if (null != bindingStyle
                && SoapBindingStyle.DOCUMENT_LITERAL.equals(bindingStyle)) {
            wsSoapHttpInboundBindingDefault
                    .setBindingStyle(SoapBindingStyle.DOCUMENT_LITERAL);
        } else {
            wsSoapHttpInboundBindingDefault
                    .setBindingStyle(SoapBindingStyle.RPC_LITERAL);

        }
    }

    /**
     * @param wsInBound
     * @param bindingStyle
     */
    private static void createSOAPJMSProviderSharedRes(String participantName,
            WsInbound wsInBound, SoapBindingStyle bindingStyle) {

        WsSoapJmsInboundBinding wsSoapJmsInboundBindingDefault =
                XpdExtensionFactory.eINSTANCE
                        .createWsSoapJmsInboundBindingDefault();
        wsSoapJmsInboundBindingDefault
                .setInboundConnectionFactoryConfiguration(wsSoapJmsInboundBindingDefault
                        .getInboundConnectionFactoryConfiguration());
        wsSoapJmsInboundBindingDefault
                .setInboundDestination(wsSoapJmsInboundBindingDefault
                        .getInboundDestination());
        wsSoapJmsInboundBindingDefault
                .setOutboundConnectionFactory(wsSoapJmsInboundBindingDefault
                        .getOutboundConnectionFactory());

        wsInBound.getSoapJmsBinding().clear();
        wsInBound.getSoapJmsBinding().add(wsSoapJmsInboundBindingDefault);

        /*
         * TODO: if binding style is null default it to rpc literal now. but
         * will have to change to document literal when we decide document
         * literal has to be the default binding style
         */
        if (null != bindingStyle
                && SoapBindingStyle.DOCUMENT_LITERAL.equals(bindingStyle)) {
            wsSoapJmsInboundBindingDefault
                    .setBindingStyle(SoapBindingStyle.DOCUMENT_LITERAL);
        } else {
            wsSoapJmsInboundBindingDefault
                    .setBindingStyle(SoapBindingStyle.RPC_LITERAL);
        }
    }

    /**
     * @param participantInfo
     * @param participantName
     * @param portName
     * @param wsResource
     * @param wsOutbound
     */
    private static void createConsumerParticSharedRes(
            ParticipantInfo participantInfo, String participantName,
            String portName, WsResource wsResource, WsOutbound wsOutbound) {

        if (portName != null
                && WSTransportType.SOAP_OVER_HTTP.equals(participantInfo
                        .getTransportType())) {
            /*
             * If port name is available - then it is a concrete operation
             * selection. so adding a http outbound binding
             */
            WsSoapHttpOutboundBinding wsSoapHttpOutboundBinding =
                    XpdExtensionFactory.eINSTANCE
                            .createWsSoapHttpOutboundBinding();
            wsSoapHttpOutboundBinding
                    .setHttpClientInstanceName(participantName);
            wsOutbound.setSoapHttpBinding(wsSoapHttpOutboundBinding);

        } else if (null != portName
                && WSTransportType.SOAP_OVER_JMS.equals(participantInfo
                        .getTransportType())) {
            WsSoapJmsOutboundBinding wsSoapJmsOutboundBindingDefault =
                    XpdExtensionFactory.eINSTANCE
                            .createWsSoapJmsOutboundBindingDefault();
            wsOutbound.setSoapJmsBinding(wsSoapJmsOutboundBindingDefault);

        } else if (WSTransportType.SERVICE_VIRTUALISATION
                .equals(participantInfo.getTransportType())) {
            WsVirtualBinding virtualBindingDefault =
                    XpdExtensionFactory.eINSTANCE
                            .createWsVirtualBindingDefault();

            wsOutbound.setBinding(virtualBindingDefault);
        }
        wsResource.setOutbound(wsOutbound);
    }

    private static String getWsdlNameWithoutSpacesWithoutExtn(
            ParticipantInfo participantInfo) {
        Path path = new Path(participantInfo.getWsdlFile().getName());
        String wsdlName = path.removeFileExtension().toPortableString();
        /*
         * encode any special characters appropriately. eg. replace spaces with
         * %20
         */
        org.eclipse.emf.common.util.URI wsdlURI =
                org.eclipse.emf.common.util.URI.createURI(wsdlName, true);
        return wsdlURI.toString();
    }

    /**
     * 
     * @param pckg
     * 
     * @return all participants in package (including process participants
     */

    protected static Collection<Participant> getPackageParticipants(
            com.tibco.xpd.xpdl2.Package pckg) {
        List<Participant> pkgParticipants = new ArrayList<Participant>();
        pkgParticipants.addAll(pckg.getParticipants());

        for (Process process : pckg.getProcesses()) {

            pkgParticipants.addAll(process.getParticipants());

        }

        return pkgParticipants;

    }

    /**
     * Calculate the value for xpdExt:SharedResource/URI
     * <p>
     * This is the path after the protocol, host and port
     * 
     * @param participantInfo
     * 
     * @return shared resource uri.
     */
    protected static String getSharedResourceURI(ParticipantInfo participantInfo) {
        String sharedResUriPath = null;

        String endPointUrl = participantInfo.getEndPointUrl();
        String portName = participantInfo.getKey().getPort();

        try {
            /*
             * we want shared resource uri path only in case of http binding not
             * for jms binding
             */
            if (BpmBindingPreferenceUtil.isHttpBinding()
                    || WSTransportType.SOAP_OVER_HTTP.equals(participantInfo
                            .getTransportType())) {
                if (null == endPointUrl) {
                    endPointUrl = ""; //$NON-NLS-1$
                }
                URI uri = new URI(endPointUrl);
                sharedResUriPath = uri.getRawPath();
            }

        } catch (URISyntaxException e) {
            sharedResUriPath = endPointUrl;
        }

        if (sharedResUriPath == null) {
            sharedResUriPath = ""; //$NON-NLS-1$
        } else {
            if (sharedResUriPath.equals(SLASH)
                    || sharedResUriPath.trim().length() == 0) {
                /*
                 * If the endpoint URL is of the form http://www.xyz.com/ then
                 * the shared resource uri path is going to be just "/" which is
                 * going to cause problems. Therefore, if that is the case,
                 * setting the shared res uri path to the port name
                 */
                return portName;
            }
        }

        return sharedResUriPath;
    }

    /**
     * 
     * Participant name/label cannot be greater than 57 characters. Create a
     * calculated label for the participant to be generated as per the
     * participant info.
     * 
     * @param participantInfo
     * @return String
     * */
    private static String getParticLabel(ParticipantInfo participantInfo) {

        StringBuilder sb = new StringBuilder();

        if (WSParticRole.PROVIDER.equals(participantInfo.getParticipantRole())) {

            if (WsdlOpType.ABSTRACT.equals(participantInfo.getWsdlOpType())) {
                String portType = participantInfo.getKey().getPortType();
                if (portType.length() > PARTIC_NAME_MAX_LENGTH) {
                    portType = portType.substring(0, PARTIC_NAME_MAX_LENGTH);
                }
                String internalName = NameUtil.getInternalName(portType, true);
                sb.append(internalName);
                sb.append(PROVIDER_ABSTRACT);
                return sb.toString();

            } else if (WsdlOpType.CONCRETE.equals(participantInfo
                    .getWsdlOpType())) {
                String port = participantInfo.getKey().getPort();
                if (port.length() > PARTIC_NAME_MAX_LENGTH) {
                    port = port.substring(0, PARTIC_NAME_MAX_LENGTH);
                }
                String internalName = NameUtil.getInternalName(port, true);
                sb.append(internalName);
                sb.append(PROVIDER_CONCRETE);
                return sb.toString();
            }

        } else if (WSParticRole.CONSUMER.equals(participantInfo
                .getParticipantRole())) {

            if (WsdlOpType.ABSTRACT.equals(participantInfo.getWsdlOpType())) {
                String portType = participantInfo.getKey().getPortType();
                if (portType.length() > PARTIC_NAME_MAX_LENGTH) {
                    portType = portType.substring(0, PARTIC_NAME_MAX_LENGTH);
                }
                String internalName = NameUtil.getInternalName(portType, true);
                sb.append(internalName);
                sb.append(CONSUMER_VIRTUAL);
                return sb.toString();

            } else if (WsdlOpType.CONCRETE.equals(participantInfo
                    .getWsdlOpType())) {
                String port = participantInfo.getKey().getPort();
                if (port.length() > PARTIC_NAME_MAX_LENGTH) {
                    port = port.substring(0, PARTIC_NAME_MAX_LENGTH);
                }
                String internalName = NameUtil.getInternalName(port, true);
                sb.append(internalName);

                sb.append(CONSUMER_HTTP);
                return sb.toString();
            }

        }
        return ""; //$NON-NLS-1$
    }

}
