package com.tibco.xpd.daa.internal.util;

import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.amf.sca.componenttype.BindingService;
import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.util.BindingUtil;
import com.tibco.amf.sca.model.componenttype.BaseReference;
import com.tibco.amf.sca.model.componenttype.BaseService;
import com.tibco.amf.sca.model.componenttype.BindingAdjunct;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.ComponentService;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.composite.PromotedReference;
import com.tibco.amf.sca.model.composite.PromotedService;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.amf.sca.model.extensionpoints.SCAExtensionsFactory;
import com.tibco.amf.sca.model.extensionpoints.Wsdl11Interface;
import com.tibco.corona.binding.bindingsoapmodel.BindingType;
import com.tibco.corona.binding.bindingsoapmodel.BindingsoapmodelFactory;
import com.tibco.corona.binding.bindingsoapmodel.HttpTransportConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.SOAPBinding;
import com.tibco.corona.binding.bindingsoapmodel.SOAPOperationConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.SOAPReferenceBinding;
import com.tibco.corona.binding.bindingsoapmodel.SOAPServiceBinding;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.wsdlgen.transform.XtendTransformerXpdl2Wsdl;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdl2.Participant;

/**
 * This class was copied from 'com.tibco.bx.composite.core' plugin
 * 
 * @author kupadhya
 * 
 *         SID XPD-4984 - Note that this is not referenced in BPM Studio
 *         features anymore and I _was_ going to remove it _but_ I see from
 *         history that this may be for use by Studio Decisions Add-In
 * @deprecated Please consider using 'com.tibco.xpd.n2.pe.util.SoapBindingUtil' instead.
 */
@Deprecated
public class SoapBindingUtil {

    public static final String HTTP_SR_NS =
            "http://xsd.tns.tibco.com/amf/models/sharedresource/httpclient";//$NON-NLS-1$    

    /**
     * Copied from com.tibco.amf.spline.api.ISplineSOAPMessage
     */
    public enum Style {
        NONE, DOCUMENT, RPC
    }

    public enum Encoding {
        NONE, LITERAL, SOAP_ENCODED
    }

    private static final String SOAP_BINDING_ID = "soap.binding";//$NON-NLS-1$

    private static final String DEFAULT_BINDING_STYLE = Style.DOCUMENT.name();

    public static void configCompositeInterfaceBindings(Composite composite,
            String timeStamp, boolean replaceWithTS) {

        // promote all services, references and properties.
        for (PromotedService promotedService : composite.getServices()) {
            configureServiceBindings(promotedService, timeStamp, replaceWithTS);
        }

        // Kamlesh: 21/07/2011 no need to worry about references as decisions
        // flow do not make WS calls
        // for (PromotedReference promotedReference : composite.getReferences())
        // {
        // configureReferenceBindings(promotedReference);
        // }
    }

    private static void configureServiceBindings(
            PromotedService promotedService, String timeStamp,
            boolean replaceWithTS) {
        BindingService bindingService =
                ComponentTypeActivator.getDefault().getBindingService();
        ComponentService componentService = promotedService.getPromotion();
        Participant participant = getParticipantForService(componentService);

        PortType portType =
                ((Wsdl11Interface) promotedService.getInterface())
                        .getPortType();
        SOAPServiceBinding binding =
                (SOAPServiceBinding) bindingService.addBinding(promotedService,
                        SOAP_BINDING_ID,
                        portType.getQName().getLocalPart() + "_EP", null);//$NON-NLS-1$
        BindingUtil.setAdditionalBindingConfiguration(binding,
                null,
                false,
                null);
        String endpointUri = null;
        String configuredBindingStyle = null;
        ParticipantSharedResourceModel apiParticipantSharedResource =
                getApiParticipantSharedResource(participant, timeStamp);
        if (apiParticipantSharedResource != null) {
            endpointUri = apiParticipantSharedResource.getEndPointURL();
            configuredBindingStyle =
                    apiParticipantSharedResource.getBindingStyle();
        } else {
            endpointUri = "";
            configuredBindingStyle = SoapBindingUtil.Style.RPC.name();
        }
        // old approach of getting end point URI + binding information
        // endpointUri =
        // getServiceEndpointUri(componentService, participant, timeStamp);
        // configuredBindingStyle = getConfiguredBindingStyle(componentService);
        if (replaceWithTS) {
            endpointUri = replaceQualifierWithTS(endpointUri, timeStamp);
        }
        configureSoapBinding(portType,
                participant,
                binding,
                endpointUri,
                configuredBindingStyle);
    }

    // private static void configureReferenceBindings(
    // PromotedReference promotedReference) {
    // BindingService bindingService =
    // ComponentTypeActivator.getDefault().getBindingService();
    // List<ComponentReference> componentReferences =
    // promotedReference.getPromotions();
    // ComponentReference cr = componentReferences.get(0);
    // PortType portType =
    // ((Wsdl11Interface) promotedReference.getInterface())
    // .getPortType();
    // Participant participant = getParticipantForReference(promotedReference);
    // if (WSDLUtils.getBinding(portType, participant) == null) {
    // // this is an abstract WSDL; skip
    // return;
    // }
    // ReferenceInfo referenceInfo = getReferenceInfo(cr);
    // if (referenceInfo != null) {
    // SOAPReferenceBinding binding =
    // (SOAPReferenceBinding) bindingService
    // .addBinding(promotedReference,
    // SOAP_BINDING_ID,
    //                                    portType.getQName().getLocalPart() + "_EP", null);//$NON-NLS-1$
    // BindingUtil.setAdditionalBindingConfiguration(binding,
    // null,
    // false,
    // null);
    // if (isSupportedSOAPBinding(binding)) {
    // configureSoapBinding(portType,
    // participant,
    // binding,
    // referenceInfo.getEndpointUri(),
    // null);
    // addBindingPropertyForReference(binding,
    // referenceInfo.getHttpClientJndi());
    // } else {
    // throw new RuntimeException(
    //                        "Binding to SOAP other then 1.1 is not supported."); //$NON-NLS-1$
    // }
    // }
    // }

    /**
     * Only SOAP 1.1 is supported.
     * 
     * @param binding
     * @return true if it is SOAP v 1.1.
     */
    private static boolean isSupportedSOAPBinding(SOAPReferenceBinding binding) {
        if (binding != null && binding.getPort() != null
                && binding.getPort().getBinding() != null) {
            List extElements =
                    binding.getPort().getBinding().getExtensibilityElements();
            for (Object o : extElements) {
                if (o instanceof ExtensibilityElement) {
                    ExtensibilityElement extElement = (ExtensibilityElement) o;
                    if (new QName("http://schemas.xmlsoap.org/wsdl/soap/", //$NON-NLS-1$
                            "binding").equals(extElement.getElementType())) { //$NON-NLS-1$
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void configureSoapBinding(PortType portType,
            Participant participant, SOAPBinding binding, String endpointUri,
            String defaultBindingStyle) {
        String bindingStyle = WSDLUtils.getBindingStyle(portType, participant);
        if (bindingStyle == null || bindingStyle.length() == 0) {
            if (defaultBindingStyle != null
                    && defaultBindingStyle.trim().length() > 0) {
                bindingStyle = defaultBindingStyle;
            } else {
                bindingStyle = DEFAULT_BINDING_STYLE;
            }
            // use RPC style for multi-part operations
        } else {
            bindingStyle = bindingStyle.toUpperCase();
        }

        String transport = WSDLUtils.getBindingTransport(portType, participant);
        transport =
                transport != null && transport.length() > 0 ? transport
                        .toUpperCase() : "HTTP";// NON-NLS-1$

        binding.setAttachmentStyle(bindingStyle);
        binding.setTransportBindingType(transport);

        HttpTransportConfiguration transportConfiguration =
                BindingsoapmodelFactory.eINSTANCE
                        .createHttpTransportConfiguration();
        if (endpointUri != null) {
            transportConfiguration.setEndpointURI(endpointUri);
        }

        if (binding instanceof SOAPReferenceBinding) {
            binding.setOutboundConfiguration(transportConfiguration);
        } else if (binding instanceof SOAPServiceBinding) {
            binding.setInboundConfiguration(transportConfiguration);

            if (binding.getDefaultBindingType() == null) {
                BindingType bindingType =
                        BindingsoapmodelFactory.eINSTANCE.createBindingType();
                binding.setDefaultBindingType(bindingType);
            }
            binding.getDefaultBindingType().setStyle(bindingStyle);
            binding.getDefaultBindingType()
                    .setEncoding(Encoding.LITERAL.name());
        }

        EList<SOAPOperationConfiguration> operationConfiguration =
                binding.getOperationConfiguration();
        for (SOAPOperationConfiguration configuration : operationConfiguration) {
            org.eclipse.wst.wsdl.Operation operation =
                    configuration.getOperation();
            String operationStyle =
                    WSDLUtils.getOperationStyle(operation, participant);
            if (operationStyle != null && operationStyle.length() > 0) {
                configuration.getBindingType()
                        .setStyle(operationStyle.toUpperCase());
            } else {
                configuration.getBindingType().setStyle(bindingStyle);
            }
            String encoding =
                    WSDLUtils.getOperationEncoding(operation, participant);
            configuration.getBindingType().setEncoding(encoding != null
                    && encoding.length() > 0 ? encoding.toUpperCase()
                    : Encoding.LITERAL.name());
        }
    }

    private static void addBindingPropertyForReference(SOAPBinding binding,
            String simpleValue) {
        BindingAdjunct bindingAdjunct =
                BindingUtil.getBindingAdjunctFor(binding);
        EList<Property> properties = bindingAdjunct.getProperties();
        if (properties.isEmpty()) {
            Property property = SCAExtensionsFactory.INSTANCE.createProperty();
            property.setName("HttpOutboundConnectionConfig");
            property.setType(new QName(SoapBindingUtil.HTTP_SR_NS,
                    "HttpClientConfiguration"));
            property.setSimpleValue(simpleValue);

            properties.add(property);
        } else {
            properties.get(0).setSimpleValue(simpleValue);
        }
    }

    private static Participant getParticipantForService(BaseService service) {
        PortType pt = ((Wsdl11Interface) service.getInterface()).getPortType();
        return getParticipantForPortType(pt, service.getName(), true);
    }

    public static Participant getParticipantForReference(BaseReference reference) {
        PortType pt =
                ((Wsdl11Interface) reference.getInterface()).getPortType();
        String participantName = reference.getName();
        if (reference instanceof PromotedReference) {
            // In case of promoted reference the name is different then
            // participant. But it should be one-to-one match for component
            // reference.
            // The assumption is that there cannot be two participants for same
            // port type and transport within the project OR if there are they
            // are exact copies.
            PromotedReference promotedReference = (PromotedReference) reference;
            for (ComponentReference cr : promotedReference.getPromotions()) {
                participantName = cr.getName();
            }
        }
        return getParticipantForPortType(pt, participantName, false);
    }

    /**
     * 
     * @param pt
     * @param nameDerivedFromParticipant
     * @param isService
     *            , true if this method is called for promoted services
     * @return
     */
    private static Participant getParticipantForPortType(PortType pt,
            String nameDerivedFromParticipant, boolean isService) {
        String portNsURI = pt.getQName().getNamespaceURI();
        String portLocalName = pt.getQName().getLocalPart();
        IndexerItemImpl queryItem = new IndexerItemImpl();
        queryItem.set("wsdl_namespace", portNsURI); //$NON-NLS-1$
        queryItem.set("port_type_name", portLocalName); //$NON-NLS-1$
        Participant indexerParticipant =
                getIndexerParticipant(queryItem, nameDerivedFromParticipant);
        if (indexerParticipant == null && isService) {
            // XPD-1829 no result found & is Service
            // we need to strip out checksum from the namespace URI when this
            // method is called for promoted services. The reason being,
            // ActivityWebServiceReferenceIndexProvider runs when WSDL might not
            // be present & at that time guesses the WSDL namespace URI
            portNsURI =
                    XtendTransformerXpdl2Wsdl
                            .getNameSpaceWithoutCheckSum(portNsURI);
            queryItem.set("wsdl_namespace", portNsURI); //$NON-NLS-1$
            indexerParticipant =
                    getIndexerParticipant(queryItem, nameDerivedFromParticipant);
        }
        return indexerParticipant;
    }

    private static Participant getIndexerParticipant(IndexerItemImpl queryItem,
            String nameDerivedFromParticipant) {
        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query("com.tibco.xpd.analyst.resources.xpdl2.indexing.webServiceReference", //$NON-NLS-1$
                                queryItem);
        for (IndexerItem item : result) {
            URI uri = URI.createURI(item.getURI());
            String participantId = item.get("endpoint_participant_id"); //$NON-NLS-1$
            URI participantURI =
                    uri.trimFragment().appendFragment(participantId);
            Participant participant =
                    (Participant) XpdResourcesPlugin.getDefault()
                            .getEditingDomain().getResourceSet()
                            .getEObject(participantURI, true);
            if (nameDerivedFromParticipant.equals(participant.getName())) {
                return participant;
            }
        }
        return null;
    }

    // private static ReferenceInfo getReferenceInfo(
    // ComponentReference componentReference) {
    // if (!(componentReference.getInterface() instanceof Wsdl11Interface)) {
    // return null;
    // }
    // Component component = componentReference.getComponent();
    // if (!(component.getImplementation() instanceof BxServiceImplementation))
    // {
    // return null;
    // }
    //
    // PortType portType =
    // ((Wsdl11Interface) componentReference.getInterface())
    // .getPortType();
    // BxServiceImplementation bxImpl =
    // (BxServiceImplementation) component.getImplementation();
    // EList<Process> processes = bxImpl.getServiceModel().getProcesses();
    // for (Process process : processes) {
    // IFile bpelFile =
    // ResourcesPlugin.getWorkspace().getRoot()
    // .getFile(new Path(process.getProcessFileName()));
    // try {
    // QualifiedName key =
    // new QualifiedName(portType.getQName().toString(),
    // componentReference.getName());
    // String endpointUri = bpelFile.getPersistentProperty(key);
    //
    // key =
    // new QualifiedName(portType.getQName().toString(),
    // componentReference.getName() + "JNDI");
    // String httpClientJndi = bpelFile.getPersistentProperty(key);
    //
    // if (endpointUri != null || httpClientJndi != null) {
    // return new ReferenceInfo(endpointUri, httpClientJndi);
    // }
    // } catch (CoreException e) {
    // BxCompositeCoreActivator.log(e.getStatus());
    // }
    // }
    //
    // return null;
    // }

    private static ParticipantSharedResourceModel getApiParticipantSharedResource(
            Participant apiParticipant, String timeStamp) {
        if (apiParticipant == null) {
            return null;
        }
        WsInbound wsResourceInbound =
                SharedResourceUtil.getWsResourceInbound(apiParticipant);
        if (wsResourceInbound == null) {
            return null;
        }
        EList<WsSoapHttpInboundBinding> soapHttpBinding =
                wsResourceInbound.getSoapHttpBinding();
        if (soapHttpBinding.size() > 0) {
            WsSoapHttpInboundBinding soapBinding = soapHttpBinding.get(0);
            String endpointUrlPath = soapBinding.getEndpointUrlPath();
            /*
             * XPD-1722: If the end point URI has '/qualifier' at the end of it,
             * it will not be replaced by time stamp during DAA generation
             */
            if (endpointUrlPath == null || endpointUrlPath.trim().length() == 0) {
                endpointUrlPath = "";
            }
            String bindingStyleName = soapBinding.getBindingStyle().getName();
            if (bindingStyleName != null
                    && bindingStyleName.toUpperCase()
                            .startsWith(SoapBindingUtil.Style.RPC.name())) {
                bindingStyleName = SoapBindingUtil.Style.RPC.name();
            } else if (bindingStyleName != null
                    && bindingStyleName.toUpperCase()
                            .startsWith(SoapBindingUtil.Style.DOCUMENT.name())) {
                bindingStyleName = SoapBindingUtil.Style.DOCUMENT.name();
            } else {
                // defaulting to RPC
                bindingStyleName = SoapBindingUtil.Style.RPC.name();
            }
            ParticipantSharedResourceModel participantSharedResourceModel =
                    new ParticipantSharedResourceModel(endpointUrlPath,
                            bindingStyleName);
            return participantSharedResourceModel;

        }
        return null;
    }

    private static String replaceQualifierWithTS(String endpointUri,
            String timeStamp) {
        String replacedQualifierWithTS =
                endpointUri.replaceAll("(/)qualifier((/|$))", "$1" + timeStamp
                        + "$2");
        return replacedQualifierWithTS;
    }

    private static class ParticipantSharedResourceModel {
        private final String endPointURL;

        private final String bindingStyle;

        public ParticipantSharedResourceModel(String endPointURL,
                String bindingStyle) {
            this.endPointURL = endPointURL;
            this.bindingStyle = bindingStyle;
        }

        public String getEndPointURL() {
            return endPointURL;
        }

        public String getBindingStyle() {
            return bindingStyle;
        }

    }

    private static class ReferenceInfo {
        private final String endpointUri;

        private final String httpClientJndi;

        public ReferenceInfo(String endpointUri, String httpClientJndi) {
            this.endpointUri = endpointUri;
            this.httpClientJndi = httpClientJndi;
        }

        public String getEndpointUri() {
            return endpointUri;
        }

        public String getHttpClientJndi() {
            return httpClientJndi;
        }
    }

}
