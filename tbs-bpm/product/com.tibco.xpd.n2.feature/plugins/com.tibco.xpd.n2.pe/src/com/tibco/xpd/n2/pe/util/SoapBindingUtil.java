package com.tibco.xpd.n2.pe.util;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.amf.sca.componenttype.BindingService;
import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.util.BindingUtil;
import com.tibco.amf.sca.model.componenttype.BaseReference;
import com.tibco.amf.sca.model.componenttype.BaseService;
import com.tibco.amf.sca.model.componenttype.BindingAdjunct;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.composite.PromotedReference;
import com.tibco.amf.sca.model.composite.PromotedService;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.amf.sca.model.extensionpoints.SCAExtensionsFactory;
import com.tibco.amf.sca.model.extensionpoints.Wsdl11Interface;
import com.tibco.corona.binding.bindingsoapmodel.BindingType;
import com.tibco.corona.binding.bindingsoapmodel.BindingsoapmodelFactory;
import com.tibco.corona.binding.bindingsoapmodel.HttpTransportConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.JMSDeliveryMode;
import com.tibco.corona.binding.bindingsoapmodel.JMSMessagePriority;
import com.tibco.corona.binding.bindingsoapmodel.JMSOutboundTransportConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.SOAPBinding;
import com.tibco.corona.binding.bindingsoapmodel.SOAPOperationConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.SOAPReferenceBinding;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.xpdExtension.DeliveryMode;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdl2.Participant;

/**
 * This class was copied from 'com.tibco.bx.composite.core' plugin
 * 
 * @author kupadhya
 * 
 */
public class SoapBindingUtil {

    public static final String HTTP_SR_NS =
            "http://xsd.tns.tibco.com/amf/models/sharedresource/httpclient";//$NON-NLS-1$    

    public static final String JMS_SR_NS =
            "http://xsd.tns.tibco.com/amf/models/sharedresource/jms"; //$NON-NLS-1$

    public static final String XSD_TYPE_STRING_NS =
            "http://www.w3.org/2001/XMLSchema"; //$NON-NLS-1$

    private static final boolean GENERATE_SOAP_OVER_HTTP_CLIENT_RT = false;

    private static final boolean GENERATE_SOAP_OVER_JMS_CLIENT_RT = false;

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

    static final String DEFAULT_BINDING_STYLE = Style.DOCUMENT.name();

    public static void configCompositeInterfaceBindings(IProject project,
            Composite composite, String timeStamp, boolean replaceWithTS) {

        /* promote all services, references and properties. */
        for (PromotedService promotedService : composite.getServices()) {
            SoapServiceBindingUtil.configureServiceBindings(project,
                    promotedService,
                    timeStamp,
                    replaceWithTS);
        }

        for (PromotedReference promotedReference : composite.getReferences()) {
            configureReferenceBindings(project, promotedReference);
        }
    }

    /**
     * @param portType
     * @param participant
     * @param binding
     */
    private static void configureSoapJmsRefBinding(PortType portType,
            Participant participant, SOAPBinding binding) {

        binding.setTransportBindingType("JMS"); //$NON-NLS-1$

        JMSOutboundTransportConfiguration jmsOutboundTransportConfig =
                BindingsoapmodelFactory.eINSTANCE
                        .createJMSOutboundTransportConfiguration();

        WsOutbound wsResourceOutbound =
                SharedResourceUtil.getWsResourceOutbound(participant);
        /*
         * XPD-6974: Set delivery mode, message expiration and message priority
         */
        if (null != wsResourceOutbound
                && null != wsResourceOutbound.getSoapJmsBinding()) {

            WsSoapJmsOutboundBinding soapJmsBinding =
                    wsResourceOutbound.getSoapJmsBinding();

            if (soapJmsBinding.isSetDeliveryMode()) {

                /*
                 * Set JMSDeliveryMode on the outbound configuration from jms
                 * binding delivery mode
                 */
                DeliveryMode deliveryMode = soapJmsBinding.getDeliveryMode();
                if (DeliveryMode.PERSISTENT.equals(deliveryMode)) {

                    jmsOutboundTransportConfig
                            .setDeliveryMode(JMSDeliveryMode.PERSISTENT);
                } else if (DeliveryMode.NON_PERSISTENT.equals(deliveryMode)) {

                    jmsOutboundTransportConfig
                            .setDeliveryMode(JMSDeliveryMode.NON_PERSISTENT);
                }
            }
            /*
             * Set message expiration on the outbound configuration from jms
             * binding message expiration
             */
            if (soapJmsBinding.isSetMessageExpiration()) {

                int messageExpiration = soapJmsBinding.getMessageExpiration();
                /*
                 * convert message expiration seconds to milli seconds as the
                 * jms outbound runtime model defaults the expiration units to
                 * milli seconds
                 */
                messageExpiration = messageExpiration * 1000;
                jmsOutboundTransportConfig.setExpiration(messageExpiration);
            }
            /*
             * Set JMSMessagePriority on the outbound configuration from jms
             * binding message priority
             */
            if (soapJmsBinding.isSetPriority()) {

                setPriorityOnJMSOutboundConfig(jmsOutboundTransportConfig,
                        soapJmsBinding);
            }
        }
        binding.setOutboundConfiguration(jmsOutboundTransportConfig);

        if (binding.getDefaultBindingType() == null) {
            BindingType bindingType =
                    BindingsoapmodelFactory.eINSTANCE.createBindingType();
            binding.setDefaultBindingType(bindingType);
        }
        binding.getDefaultBindingType().setEncoding(Encoding.LITERAL.name());

        EList<SOAPOperationConfiguration> operationConfiguration =
                binding.getOperationConfiguration();

        for (SOAPOperationConfiguration configuration : operationConfiguration) {

            org.eclipse.wst.wsdl.Operation operation =
                    configuration.getOperation();
            String encoding =
                    WSDLUtils.getOperationEncoding(operation, participant);
            configuration.getBindingType().setEncoding(encoding != null
                    && encoding.length() > 0 ? encoding.toUpperCase()
                    : Encoding.LITERAL.name());
        }
    }

    /**
     * Gets the priority from the soap jms binding configured on soap jms
     * consumer participant and sets it on the jms outbound transport
     * configuration
     * 
     * @param jmsOutboundTransportConfig
     * @param soapJmsBinding
     * @return
     */
    private static JMSOutboundTransportConfiguration setPriorityOnJMSOutboundConfig(
            JMSOutboundTransportConfiguration jmsOutboundTransportConfig,
            WsSoapJmsOutboundBinding soapJmsBinding) {

        int priority = soapJmsBinding.getPriority();
        switch (priority) {
        case 0:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.ZERO);
            break;
        case 1:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.ONE);
            break;
        case 2:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.TWO);
            break;
        case 3:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.THREE);
            break;
        case 5:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.FIVE);
            break;
        case 6:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.SIX);
            break;
        case 7:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.SEVEN);
            break;
        case 8:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.EIGHT);
            break;
        case 9:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.NINE);
            break;
        default:
            jmsOutboundTransportConfig.setPriority(JMSMessagePriority.FOUR);
            break;
        }
        return jmsOutboundTransportConfig;
    }

    private static void configureSoapHttpRefBinding(PortType portType,
            Participant participant, SOAPBinding binding, String endpointUri) {

        binding.setTransportBindingType("HTTP"); //$NON-NLS-1$

        HttpTransportConfiguration transportConfiguration =
                BindingsoapmodelFactory.eINSTANCE
                        .createHttpTransportConfiguration();

        if (endpointUri != null) {
            transportConfiguration.setEndpointURI(endpointUri);
        }

        if (binding instanceof SOAPReferenceBinding) {

            binding.setOutboundConfiguration(transportConfiguration);
            /*
             * XPD-4484: http binding do not require any inbound configuration.
             * in case of a wsdl with both http and jms bindings, we get the
             * inbound configuration set by the api we are using for getting the
             * binding details. so we set the inbound configuration to null if
             * it is has some value for any reason.
             */
            if (null != binding.getInboundConfiguration()) {
                binding.setInboundConfiguration(null);
            }
        }

        EList<SOAPOperationConfiguration> operationConfiguration =
                binding.getOperationConfiguration();

        for (SOAPOperationConfiguration configuration : operationConfiguration) {

            org.eclipse.wst.wsdl.Operation operation =
                    configuration.getOperation();
            String encoding =
                    WSDLUtils.getOperationEncoding(operation, participant);
            /*
             * XPD-5858: if all the operations for a port type are not present
             * for wsdl binding NPE is thrown. checking for not null to avoid
             * getting NPE and show the actual problem during DAA generation
             */
            if (null != configuration.getBindingType()) {

                configuration.getBindingType().setEncoding(encoding != null
                        && encoding.length() > 0 ? encoding.toUpperCase()
                        : Encoding.LITERAL.name());
            }
        }
    }

    private static void configureReferenceBindings(IProject project,
            PromotedReference promotedReference) {

        List<ComponentReference> componentReferences =
                promotedReference.getPromotions();
        ComponentReference cr = componentReferences.get(0);

        /*
         * Get from participant resource model with binding configuration and
         * for every specified soap-http binding add one below. and for every
         * specified soap-jms binding add new similar jms binding.
         */
        PortType portType =
                ((Wsdl11Interface) promotedReference.getInterface())
                        .getPortType();
        Participant participant =
                getParticipantForReference(project, promotedReference);

        if (WSDLUtils.getBinding(portType, participant) == null) {
            // this is an abstract WSDL; skip
            return;
        }

        String transport = WSDLUtils.getBindingTransport(portType, participant);
        if (PEN2Utils.JMS.equalsIgnoreCase(transport)) {
            createSoapJmsRefBinding(promotedReference,
                    cr,
                    portType,
                    participant);
        } else {
            createSoapHttpRefBinding(promotedReference,
                    cr,
                    portType,
                    participant);
        }
    }

    /**
     * @param promotedReference
     * @param cr
     * @param portType
     * @param participant
     */
    private static void createSoapJmsRefBinding(
            PromotedReference promotedReference, ComponentReference cr,
            PortType portType, Participant participant) {

        BindingService bindingService =
                ComponentTypeActivator.getDefault().getBindingService();

        /*
         * this api would disable the generation of resource templates for soap
         * over jms binding
         */
        BindingUtil.setAdditionalBindingConfiguration(promotedReference,
                null,
                GENERATE_SOAP_OVER_JMS_CLIENT_RT,
                null);

        SOAPReferenceBinding binding =
                (SOAPReferenceBinding) bindingService
                        .addBinding(promotedReference,
                                SOAP_BINDING_ID,
                                null,
                                null);

        if (isSupportedSOAPBinding(binding)) {

            configureSoapJmsRefBinding(portType, participant, binding);
            addBindingPropertyForJMS(binding, participant);

        } else {

            throw new RuntimeException(
                    "Binding to SOAP other than 1.1 is not supported."); //$NON-NLS-1$
        }

    }

    /**
     * @param promotedReference
     * @param cr
     * @param portType
     * @param participant
     */
    protected static void createSoapHttpRefBinding(
            PromotedReference promotedReference, ComponentReference cr,
            PortType portType, Participant participant) {

        BindingService bindingService =
                ComponentTypeActivator.getDefault().getBindingService();

        /*
         * XPD-4867: getting the endpoint uri location. earlier we used to get
         * it from BX. now we don't have to depend on BX as we handle the info
         * required for composite file in XPD
         */
        String bindingLocation =
                WSDLUtils.getBindingLocation(portType, participant);

        BindingUtil.setAdditionalBindingConfiguration(promotedReference,
                null,
                GENERATE_SOAP_OVER_HTTP_CLIENT_RT,
                null);

        SOAPReferenceBinding binding =
                (SOAPReferenceBinding) bindingService
                        .addBinding(promotedReference,
                                SOAP_BINDING_ID,
                                portType.getQName().getLocalPart() + "_EP", null);//$NON-NLS-1$      

        if (isSupportedSOAPBinding(binding)) {

            configureSoapHttpRefBinding(portType,
                    participant,
                    binding,
                    bindingLocation);
            WsOutbound wsResourceOutbound =
                    SharedResourceUtil.getWsResourceOutbound(participant);
            WsSoapHttpOutboundBinding soapHttpBinding =
                    wsResourceOutbound.getSoapHttpBinding();
            String httpClientInstanceName =
                    soapHttpBinding.getHttpClientInstanceName();
            addBindingPropertyForReference(binding, httpClientInstanceName);

        } else {
            throw new RuntimeException(
                    "Binding to SOAP other than 1.1 is not supported."); //$NON-NLS-1$
        }
    }

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

    private static void addBindingPropertyForReference(SOAPBinding binding,
            String simpleValue) {

        BindingAdjunct bindingAdjunct =
                BindingUtil.getBindingAdjunctFor(binding);
        EList<Property> properties = bindingAdjunct.getProperties();

        /*
         * XPD-4484: The wsdl has jms and http bindings and the participants are
         * generated for each binding when an operation is invoked from the
         * respective binding (i.e. http and jms). The above api looks at the
         * wsdl and returns the properties matching for jms. The count of jms
         * properties is more than one (and http has only one property) so
         * verifying the size and clearing the properties to add the suitable
         * ones for http. TODO: need to find a better approach than rely on this
         * size i think
         */

        if (properties.size() > 1) {
            properties.clear();
        }

        if (properties.isEmpty()) {

            Property property = SCAExtensionsFactory.INSTANCE.createProperty();
            property.setName("HttpOutboundConnectionConfig"); //$NON-NLS-1$
            property.setType(new QName(SoapBindingUtil.HTTP_SR_NS,
                    "HttpClientConfiguration")); //$NON-NLS-1$
            property.setSimpleValue(simpleValue);
            property.setMustSupply(true);
            properties.add(property);

        } else {

            properties.get(0).setSimpleValue(simpleValue);
        }
    }

    private static void addBindingPropertyForJMS(SOAPBinding binding,
            Participant participant) {

        WsInbound wsResourceInbound =
                SharedResourceUtil.getWsResourceInbound(participant);

        if (null != wsResourceInbound) {
            addInboundBindingPropertyForSoapJms(binding, wsResourceInbound);
        }

        WsOutbound wsResourceOutbound =
                SharedResourceUtil.getWsResourceOutbound(participant);
        if (null != wsResourceOutbound) {
            addOutboundBindingPropertyForSoapJms(binding, wsResourceOutbound);
        }
    }

    /**
     * @param binding
     * @param wsResourceOutbound
     */
    private static void addOutboundBindingPropertyForSoapJms(
            SOAPBinding binding, WsOutbound wsResourceOutbound) {

        WsSoapJmsOutboundBinding soapJmsBinding =
                wsResourceOutbound.getSoapJmsBinding();

        BindingAdjunct bindingAdjunct =
                BindingUtil.getBindingAdjunctFor(binding);
        EList<Property> properties = bindingAdjunct.getProperties();

        if (properties.size() == 1) {
            properties.clear();
        }

        if (null != soapJmsBinding) {

            if (properties.isEmpty()) {

                Property property1 =
                        SCAExtensionsFactory.INSTANCE.createProperty();
                Property property2 =
                        SCAExtensionsFactory.INSTANCE.createProperty();
                Property property3 =
                        SCAExtensionsFactory.INSTANCE.createProperty();

                properties.add(0, SetPropertyAdjuncts
                        .setupServiceConsumerOutboundJMSDestination(property1,
                                soapJmsBinding));
                properties
                        .add(1,
                                SetPropertyAdjuncts
                                        .setupServiceConsumerOutboundJMSConnectionFactory(property2,
                                                soapJmsBinding));

                if (null != soapJmsBinding.getInboundDestination()
                        && soapJmsBinding.getInboundDestination().length() > 0) {
                    properties
                            .add(2,
                                    SetPropertyAdjuncts
                                            .setupServiceConsumerInboundJMSDestination(property3,
                                                    soapJmsBinding));
                }
            } else {

                for (Iterator<Property> iterator = properties.iterator(); iterator
                        .hasNext();) {
                    Property property = iterator.next();

                    if (SetPropertyAdjuncts.SVC_CONSUMER_INBOUND_DESTINATION
                            .equalsIgnoreCase(property.getName())) {

                        if (null != soapJmsBinding.getInboundDestination()
                                && soapJmsBinding.getInboundDestination()
                                        .length() > 0) {
                            property.setSimpleValue(soapJmsBinding
                                    .getInboundDestination());
                        } else {
                            property.setSimpleValue(""); //$NON-NLS-1$
                        }
                        continue;
                    }

                    if (SetPropertyAdjuncts.SVC_CONSUMER_OUTBOUND_CONN_FACTORY
                            .equalsIgnoreCase(property.getName())) {

                        if (null != soapJmsBinding
                                .getOutboundConnectionFactory()) {

                            property.setSimpleValue(soapJmsBinding
                                    .getOutboundConnectionFactory());
                            continue;
                        }
                    }

                    if (SetPropertyAdjuncts.SVC_CONSUMER_OUTBOUND_DESTINATION
                            .equalsIgnoreCase(property.getName())) {
                        if (null != soapJmsBinding.getOutboundDestination()) {
                            property.setSimpleValue(soapJmsBinding
                                    .getOutboundDestination());
                        }
                    }
                }

            }
        }
    }

    /**
     * @param binding
     * @param wsResourceInbound
     */
    protected static void addInboundBindingPropertyForSoapJms(
            SOAPBinding binding, WsInbound wsResourceInbound) {

        EList<WsSoapJmsInboundBinding> soapJmsBinding =
                wsResourceInbound.getSoapJmsBinding();

        BindingAdjunct bindingAdjunct =
                BindingUtil.getBindingAdjunctFor(binding);
        EList<Property> properties = bindingAdjunct.getProperties();

        if (properties.size() == 1) {
            properties.clear();
        }

        for (WsSoapJmsInboundBinding wsSoapJmsInboundBinding : soapJmsBinding) {

            if (properties.isEmpty()) {

                for (int i = 0; i < soapJmsBinding.size(); i++) {

                    Property property1 =
                            SCAExtensionsFactory.INSTANCE.createProperty();
                    Property property2 =
                            SCAExtensionsFactory.INSTANCE.createProperty();
                    Property property3 =
                            SCAExtensionsFactory.INSTANCE.createProperty();

                    properties
                            .add(0,
                                    SetPropertyAdjuncts
                                            .setupServiceProviderInboundJMSConnectionFactoryConfiguration(property1,
                                                    wsSoapJmsInboundBinding));
                    properties
                            .add(1,
                                    SetPropertyAdjuncts
                                            .setupServiceProviderInboundJMSDestinationConfiguration(property2,
                                                    wsSoapJmsInboundBinding));
                    properties
                            .add(2,
                                    SetPropertyAdjuncts
                                            .setupServiceProviderOutboundJMSConnectionFactory(property3,
                                                    wsSoapJmsInboundBinding));
                }

            } else {

                for (Property property : properties) {
                    /*
                     * WSBT has changed the jms binding property names. changed
                     * the property adjunct names to match with theirs
                     */
                    if (SetPropertyAdjuncts.SVC_PROVIDER_INBOUND_CONN_FACTORY_CONFIG
                            .equals(property.getName())) {
                        property.setSimpleValue(wsSoapJmsInboundBinding
                                .getInboundConnectionFactoryConfiguration());
                    }
                    if (SetPropertyAdjuncts.SVC_PROVIDER_INBOUND_DESTINATION_CONFIG
                            .equals(property.getName())) {
                        property.setSimpleValue(wsSoapJmsInboundBinding
                                .getInboundDestination());
                    }
                    if (SetPropertyAdjuncts.SVC_PROVIDER_OUTBOUND_CONN_FACTORY
                            .equals(property.getName())) {
                        property.setSimpleValue(wsSoapJmsInboundBinding
                                .getOutboundConnectionFactory());
                    }
                }
            }
        }
    }

    public static Participant getParticipantForService(IProject project,
            BaseService service) {

        return N2PECompositeUtil.getParticipantForService(project, service);
    }

    public static Participant getParticipantForReference(IProject project,
            BaseReference reference) {

        return N2PECompositeUtil.getParticipantForReference(project, reference);
    }

    static String replaceQualifierWithTS(String endpointUri, String timeStamp) {
        String replacedQualifierWithTS =
                endpointUri.replaceAll("(/)qualifier((/|$))", "$1" + timeStamp //$NON-NLS-1$ //$NON-NLS-2$
                        + "$2"); //$NON-NLS-1$
        return replacedQualifierWithTS;
    }

    /**
     * Simple class to deal with pulling required SCA Binding properties out of
     * XPDL Participant Shared Resource model into appropriate sca properties
     * 
     * 
     * @author ??
     * @since ??
     */
    static class SetPropertyAdjuncts {

        /*
         * WSBT has changed the jms binding property names. changed the property
         * adjunct names to match with theirs
         */
        public static final String SVC_PROVIDER_INBOUND_CONN_FACTORY_CONFIG =
                "JmsInboundConnectionFactoryConfig"; //$NON-NLS-1$

        public static final String SVC_PROVIDER_OUTBOUND_CONN_FACTORY =
                "JmsOutboundConnectionFactory"; //$NON-NLS-1$

        public static final String SVC_PROVIDER_INBOUND_DESTINATION_CONFIG =
                "JmsInboundDestinationConfig"; //$NON-NLS-1$

        public static final String SVC_CONSUMER_INBOUND_DESTINATION =
                "JmsInboundDestination"; //$NON-NLS-1$

        public static final String SVC_CONSUMER_OUTBOUND_CONN_FACTORY =
                "JmsOutboundConnectionFactory"; //$NON-NLS-1$

        public static final String SVC_CONSUMER_OUTBOUND_DESTINATION =
                "JmsOutboundDestination"; //$NON-NLS-1$

        /**
         * Add appropriate configuration for a service provider's Inbound JMS
         * Connection Factory Configuration in the given property.
         * 
         * @param property
         * @param wsSoapJmsInboundBinding
         * 
         * @return returns given property parameter for convenience.
         */
        public static Property setupServiceProviderInboundJMSConnectionFactoryConfiguration(
                Property property,
                WsSoapJmsInboundBinding wsSoapJmsInboundBinding) {
            property.setMustSupply(true);
            /*
             * WSBT has changed the jms binding property names. changed the
             * property adjunct names to match with theirs
             */
            property.setName(SVC_PROVIDER_INBOUND_CONN_FACTORY_CONFIG);
            property.setType(getJMSConnectionFactoryConfigurationPropertyType());
            property.setSimpleValue(wsSoapJmsInboundBinding
                    .getInboundConnectionFactoryConfiguration());
            return property;
        }

        /**
         * Add appropriate configuration for a service provider's Inbound JMS
         * Destination Configuration in the given property.
         * 
         * @param property
         * @param wsSoapJmsInboundBinding
         * 
         * @return returns given property parameter for convenience.
         */
        public static Property setupServiceProviderInboundJMSDestinationConfiguration(
                Property property,
                WsSoapJmsInboundBinding wsSoapJmsInboundBinding) {
            property.setMustSupply(true);
            /*
             * WSBT has changed the jms binding property names. changed the
             * property adjunct names to match with theirs
             */
            property.setName(SVC_PROVIDER_INBOUND_DESTINATION_CONFIG);
            property.setType(getJMSDestinationConfigurationPropertyType());
            property.setSimpleValue(wsSoapJmsInboundBinding
                    .getInboundDestination());
            return property;
        }

        /**
         * Add appropriate configuration for a service provider's Outbound JMS
         * Connection Factory in the given property.
         * 
         * @param property
         * @param wsSoapJmsInboundBinding
         * 
         * @return returns given property parameter for convenience.
         */
        public static Property setupServiceProviderOutboundJMSConnectionFactory(
                Property property,
                WsSoapJmsInboundBinding wsSoapJmsInboundBinding) {
            property.setMustSupply(true);
            property.setName(SVC_PROVIDER_OUTBOUND_CONN_FACTORY);
            property.setType(getJMSConnectionFactoryPropertyType());
            property.setSimpleValue(wsSoapJmsInboundBinding
                    .getOutboundConnectionFactory());
            return property;
        }

        /**
         * Add appropriate configuration for a service consumer's Inbound JMS
         * Destination in the given property.
         * 
         * @param property
         * @param soapJmsBinding
         * 
         * @return returns given property parameter for convenience.
         */
        public static Property setupServiceConsumerInboundJMSDestination(
                Property property, WsSoapJmsOutboundBinding soapJmsBinding) {
            property.setMustSupply(false);
            property.setName(SVC_CONSUMER_INBOUND_DESTINATION);
            property.setType(getJMSDestinationPropertyType());
            property.setSimpleValue(soapJmsBinding.getInboundDestination());
            return property;
        }

        /**
         * Add appropriate configuration for a service consumer's Outbound JMS
         * Connection Factory in the given property.
         * 
         * @param property
         * @param soapJmsBinding
         * 
         * @return returns given property parameter for convenience.
         */
        public static Property setupServiceConsumerOutboundJMSConnectionFactory(
                Property property, WsSoapJmsOutboundBinding soapJmsBinding) {
            property.setMustSupply(true);
            property.setName(SVC_CONSUMER_OUTBOUND_CONN_FACTORY);
            property.setType(getJMSConnectionFactoryPropertyType());
            property.setSimpleValue(soapJmsBinding
                    .getOutboundConnectionFactory());
            return property;
        }

        /**
         * Add appropriate configuration for a service consumer's Outbound JMS
         * Destination in the given property.
         * 
         * @param property
         * @param soapJmsBinding
         * 
         * @return returns given property parameter for convenience.
         */
        public static Property setupServiceConsumerOutboundJMSDestination(
                Property property, WsSoapJmsOutboundBinding soapJmsBinding) {
            property.setMustSupply(true);
            property.setName(SVC_CONSUMER_OUTBOUND_DESTINATION);
            property.setType(getJMSDestinationPropertyType());
            property.setSimpleValue(soapJmsBinding.getOutboundDestination());
            return property;
        }

        /**
         * @return QName for JMSConnectionFactoryConfiguration sca property
         *         type.
         */
        private static QName getJMSConnectionFactoryConfigurationPropertyType() {
            return new QName(JMS_SR_NS, "JMSConnectionFactoryConfiguration"); //$NON-NLS-1$
        }

        /**
         * @return QName for JMSDestinationConfiguration sca property type.
         */
        private static QName getJMSDestinationConfigurationPropertyType() {
            return new QName(JMS_SR_NS, "JMSDestinationConfiguration"); //$NON-NLS-1$
        }

        /**
         * @return QName for JMSConnectionFactory sca property type.
         */
        private static QName getJMSConnectionFactoryPropertyType() {
            return new QName(JMS_SR_NS, "JMSConnectionFactory"); //$NON-NLS-1$
        }

        /**
         * @return QName for JMSDestination sca property type.
         */
        private static QName getJMSDestinationPropertyType() {
            return new QName(JMS_SR_NS, "JMSDestination"); //$NON-NLS-1$
        }
    }

}
