/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.pe.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.amf.sca.componenttype.BindingService;
import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.util.BindingUtil;
import com.tibco.amf.sca.model.componenttype.BindingAdjunct;
import com.tibco.amf.sca.model.composite.ComponentService;
import com.tibco.amf.sca.model.composite.PromotedService;
import com.tibco.amf.sca.model.extensionpoints.Binding;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.amf.sca.model.extensionpoints.SCAExtensionsFactory;
import com.tibco.amf.sca.model.extensionpoints.Wsdl11Interface;
import com.tibco.corona.binding.bindingsoapmodel.BindingType;
import com.tibco.corona.binding.bindingsoapmodel.BindingsoapmodelFactory;
import com.tibco.corona.binding.bindingsoapmodel.HttpTransportConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.JMSInboundTransportConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.JMSOutboundTransportConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.SOAPOperationConfiguration;
import com.tibco.corona.binding.bindingsoapmodel.SOAPServiceBinding;
import com.tibco.xpd.n2.pe.util.SoapBindingUtil.Encoding;
import com.tibco.xpd.n2.pe.util.SoapBindingUtil.SetPropertyAdjuncts;
import com.tibco.xpd.n2.pe.util.SoapBindingUtil.Style;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * This class was copied from 'com.tibco.bx.composite.core' plugin
 * 
 * @author kupadhya
 * 
 *         Taken out bits from SoapBindingUtil and made it specific to Service
 *         Bindings
 * 
 * @author bharge
 * @since 27 Apr 2012
 */
public class SoapServiceBindingUtil {

    private static final String SOAP_BINDING_ID = "soap.binding";//$NON-NLS-1$

    public static void configureServiceBindings(IProject project,
            PromotedService promotedService, String timeStamp,
            boolean replaceWithTS) {

        ComponentService componentService = promotedService.getPromotion();

        PortType portType =
                ((Wsdl11Interface) promotedService.getInterface())
                        .getPortType();

        Participant participant =
                SoapBindingUtil.getParticipantForService(project,
                        componentService);
        /*
         * Get from participant resource model all the binding configuration
         * info. for every specified soap-http binding/soap-jms binding add one
         * below.
         */

        ParticipantSharedResource participantSharedRes =
                (ParticipantSharedResource) Xpdl2ModelUtil
                        .getOtherElement(participant,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantSharedResource());

        if (null != participantSharedRes
                && null != participantSharedRes.getWebService()
                && null != participantSharedRes.getWebService().getInbound()) {

            List<SOAPServiceBinding> soapServiceBindingList =
                    new ArrayList<SOAPServiceBinding>();

            int bindingsSize =
                    participantSharedRes.getWebService().getInbound()
                            .getAllBindings().size();
            /*
             * consider one less binding ignoring the virutalization default
             * binding
             */
            for (int i = 0; i < bindingsSize - 1; i++) {

                SOAPServiceBinding soapServiceBinding =
                        createBindings(promotedService,
                                portType,
                                timeStamp,
                                replaceWithTS,
                                participant);
                soapServiceBindingList.add(soapServiceBinding);
            }

            EList<WsSoapHttpInboundBinding> soapHttpBinding =
                    participantSharedRes.getWebService().getInbound()
                            .getSoapHttpBinding();
            /*
             * for a given participant shared resource iterate thru http
             * bindings first to configure http service binding from the service
             * bindings list
             */
            int i = 0;

            for (; i < soapHttpBinding.size();) {

                SOAPServiceBinding soapServiceBinding =
                        soapServiceBindingList.get(i);
                configureSoapHttpBinding(soapServiceBinding,
                        portType,
                        timeStamp,
                        replaceWithTS,
                        participant,
                        soapHttpBinding.get(i));
                updateHttpBindingAdjunct(soapServiceBinding,
                        participant,
                        soapHttpBinding.get(i));
                i++;
            }

            /*
             * when all the http bindings are configured take the rest of the
             * bindings from the service bindings list to configure jms service
             * binding
             */
            EList<WsSoapJmsInboundBinding> soapJmsBinding =
                    participantSharedRes.getWebService().getInbound()
                            .getSoapJmsBinding();
            int j = 0;

            for (; i < soapServiceBindingList.size(); i++) {

                SOAPServiceBinding soapServiceBinding =
                        soapServiceBindingList.get(i);
                configureSoapJmsBinding(soapServiceBinding,
                        portType,
                        timeStamp,
                        replaceWithTS,
                        participant,
                        soapJmsBinding.get(j));
                updateJmsBindingAdjunct(soapServiceBinding,
                        participant,
                        soapJmsBinding.get(j));
                j++;
            }
        }
    }

    /**
     * @param soapServiceBinding
     * @param participant
     * @param wsSoapJmsInboundBinding
     */
    private static void updateJmsBindingAdjunct(
            SOAPServiceBinding soapServiceBinding, Participant participant,
            WsSoapJmsInboundBinding wsSoapJmsInboundBinding) {

        BindingAdjunct bindingAdjunct =
                BindingUtil.getBindingAdjunctFor(soapServiceBinding);
        EList<Property> properties = bindingAdjunct.getProperties();

        /*
         * Sid XPD-5001. Platform has change to support JMSConnectionFactory and
         * JMSDestination resource instances for provider (application inbound)
         * bindings. So they now add these as expected properties to the
         * bindingAdjunct whenever there is a concrete SOAP/JMS binding for
         * service in the WSDL. That does not match this BOPM Studio provider
         * participant's properties that we store
         * (JMSConnectionFactoryConfiguration and JMSDestinationConfiguration).
         * 
         * Given that the Studio participant UI requests the configuration
         * parameters Inbound:
         * 
         * JMS Connection Factory Configuration JMS Destination Configuration
         * Outbound (reply/fault message) queue: JMS Connection Factory. And
         * that these still function correctly, and the only thing causing a
         * problem is that the set of expected properties assigned by underlying
         * platform code is at odds with the properties that Studio BPM code is
         * expecting.
         * 
         * Then the solution is for BPM Studio to ignore and clear the default
         * set of expected properties added by platform and add the set of
         * properties that matches our bpm participant configuration.
         */

        properties.clear();

        Property property1 = SCAExtensionsFactory.INSTANCE.createProperty();
        Property property2 = SCAExtensionsFactory.INSTANCE.createProperty();
        Property property3 = SCAExtensionsFactory.INSTANCE.createProperty();

        property1.setSimpleValue(wsSoapJmsInboundBinding
                .getInboundConnectionFactoryConfiguration());

        property2.setSimpleValue(wsSoapJmsInboundBinding
                .getInboundDestination());

        property3.setSimpleValue(wsSoapJmsInboundBinding
                .getOutboundConnectionFactory());

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
        properties.add(2, SetPropertyAdjuncts
                .setupServiceProviderOutboundJMSConnectionFactory(property3,
                        wsSoapJmsInboundBinding));

    }

    /**
     * @param soapServiceBinding
     * @param participant
     * @param wsSoapHttpInboundBinding
     */
    private static void updateHttpBindingAdjunct(
            SOAPServiceBinding soapServiceBinding, Participant participant,
            WsSoapHttpInboundBinding wsSoapHttpInboundBinding) {

        BindingAdjunct bindingAdjunct =
                BindingUtil.getBindingAdjunctFor(soapServiceBinding);
        EList<Property> properties = bindingAdjunct.getProperties();

        /*
         * The wsdl has jms bindings and the participant has combination of
         * additional bindings (i.e. http and jms). The above api looks at the
         * wsdl and returns the properties matching for jms. The count of jms
         * properties is more than one (and http has only one property) so
         * verifying the size and clearing the properties to add the suitable
         * ones for http. TODO: need to find a better approach than rely on this
         * size i think
         */
        if (properties.size() > 1) {
            properties.clear();
        }
        Property property = null;

        if (properties.isEmpty()) {

            property = SCAExtensionsFactory.INSTANCE.createProperty();
            property.setMustSupply(true);
            property.setName("HttpInboundConnectionConfig"); //$NON-NLS-1$
            property.setType(new QName(SoapBindingUtil.XSD_TYPE_STRING_NS,
                    "string", "xsd")); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            property = properties.get(0);
        }

        String httpConnectorInstanceName =
                wsSoapHttpInboundBinding.getHttpConnectorInstanceName();

        if (null != httpConnectorInstanceName
                && httpConnectorInstanceName.length() > 0) {

            if (null != property.getSimpleValue()) {
                if (!httpConnectorInstanceName
                        .equals(property.getSimpleValue())) {
                    property.setSimpleValue(httpConnectorInstanceName);
                }
            } else {
                property.setSimpleValue(httpConnectorInstanceName);
                properties.add(property);
            }
        }

    }

    /**
     * @param soapServiceBinding
     * @param portType
     * @param timeStamp
     * @param replaceWithTS
     * @param participant
     * @param wsSoapJmsInboundBinding
     */
    private static void configureSoapJmsBinding(
            SOAPServiceBinding soapServiceBinding, PortType portType,
            String timeStamp, boolean replaceWithTS, Participant participant,
            WsSoapJmsInboundBinding wsSoapJmsInboundBinding) {

        String bindingStyleName =
                wsSoapJmsInboundBinding.getBindingStyle().getName();

        if (null != bindingStyleName) {

            if (bindingStyleName.toUpperCase()
                    .startsWith(SoapBindingUtil.Style.RPC.name())) {
                bindingStyleName = SoapBindingUtil.Style.RPC.name();
            } else if (bindingStyleName.toUpperCase()
                    .startsWith(SoapBindingUtil.Style.DOCUMENT.name())) {
                bindingStyleName = SoapBindingUtil.Style.DOCUMENT.name();
            }

        } else {
            /* defaulting to RPC */
            bindingStyleName = SoapBindingUtil.Style.RPC.name();
        }

        soapServiceBinding.setAttachmentStyle(bindingStyleName);
        soapServiceBinding.setTransportBindingType("JMS"); //$NON-NLS-1$

        String soapVersion = wsSoapJmsInboundBinding.getSoapVersion();
        if (soapVersion != null && !soapVersion.trim().isEmpty()) {
            soapServiceBinding.setSoapVersion(soapVersion);
        }

        JMSInboundTransportConfiguration jmsInboundTransportConfig =
                BindingsoapmodelFactory.eINSTANCE
                        .createJMSInboundTransportConfiguration();
        soapServiceBinding.setInboundConfiguration(jmsInboundTransportConfig);

        JMSOutboundTransportConfiguration jmsOutboundTransportConfig =
                BindingsoapmodelFactory.eINSTANCE
                        .createJMSOutboundTransportConfiguration();
        soapServiceBinding.setOutboundConfiguration(jmsOutboundTransportConfig);

        if (soapServiceBinding.getDefaultBindingType() == null) {
            BindingType bindingType =
                    BindingsoapmodelFactory.eINSTANCE.createBindingType();
            soapServiceBinding.setDefaultBindingType(bindingType);
        }
        soapServiceBinding.getDefaultBindingType().setStyle(bindingStyleName);
        soapServiceBinding.getDefaultBindingType()
                .setEncoding(Encoding.LITERAL.name());

        EList<SOAPOperationConfiguration> operationConfiguration =
                soapServiceBinding.getOperationConfiguration();

        for (SOAPOperationConfiguration configuration : operationConfiguration) {

            org.eclipse.wst.wsdl.Operation operation =
                    configuration.getOperation();
            String operationStyle =
                    WSDLUtils.getOperationStyle(operation, participant);

            if (operationStyle != null && operationStyle.length() > 0) {
                configuration.getBindingType()
                        .setStyle(operationStyle.toUpperCase());
            } else {
                configuration.getBindingType().setStyle(bindingStyleName);
            }

            String encoding =
                    WSDLUtils.getOperationEncoding(operation, participant);
            configuration.getBindingType().setEncoding(encoding != null
                    && encoding.length() > 0 ? encoding.toUpperCase()
                    : Encoding.LITERAL.name());
        }

    }

    /**
     * @param soapServiceBinding
     * @param portType
     * @param timeStamp
     * @param replaceWithTS
     * @param participant
     * @param wsSoapHttpInboundBinding
     */
    private static void configureSoapHttpBinding(
            SOAPServiceBinding soapServiceBinding, PortType portType,
            String timeStamp, boolean replaceWithTS, Participant participant,
            WsSoapHttpInboundBinding wsSoapHttpInboundBinding) {

        final String DEFAULT_BINDING_STYLE = Style.DOCUMENT.name();

        String defaultBindingStyle =
                wsSoapHttpInboundBinding.getBindingStyle().getName();

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

        if (null != bindingStyle) {

            if (bindingStyle.toUpperCase()
                    .startsWith(SoapBindingUtil.Style.RPC.name())) {
                bindingStyle = SoapBindingUtil.Style.RPC.name();
            } else if (bindingStyle.toUpperCase()
                    .startsWith(SoapBindingUtil.Style.DOCUMENT.name())) {
                bindingStyle = SoapBindingUtil.Style.DOCUMENT.name();
            }
        } else {
            // defaulting to RPC
            bindingStyle = SoapBindingUtil.Style.RPC.name();
        }

        soapServiceBinding.setAttachmentStyle(bindingStyle);
        soapServiceBinding.setTransportBindingType("HTTP"); //$NON-NLS-1$

        String soapVersion = wsSoapHttpInboundBinding.getSoapVersion();
        if (soapVersion != null && !soapVersion.trim().isEmpty()) {
            soapServiceBinding.setSoapVersion(soapVersion);
        }

        HttpTransportConfiguration transportConfiguration =
                BindingsoapmodelFactory.eINSTANCE
                        .createHttpTransportConfiguration();

        if (null != wsSoapHttpInboundBinding.getEndpointUrlPath()) {
            transportConfiguration.setEndpointURI(wsSoapHttpInboundBinding
                    .getEndpointUrlPath());
        }

        if (soapServiceBinding instanceof SOAPServiceBinding) {
            soapServiceBinding.setInboundConfiguration(transportConfiguration);

            if (soapServiceBinding.getDefaultBindingType() == null) {
                BindingType bindingType =
                        BindingsoapmodelFactory.eINSTANCE.createBindingType();
                soapServiceBinding.setDefaultBindingType(bindingType);
            }
            soapServiceBinding.getDefaultBindingType().setStyle(bindingStyle);
            soapServiceBinding.getDefaultBindingType()
                    .setEncoding(Encoding.LITERAL.name());
        }

        EList<SOAPOperationConfiguration> operationConfiguration =
                soapServiceBinding.getOperationConfiguration();

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

    /**
     * @param promotedService
     * @param portType
     * @param timeStamp
     * @param replaceWithTS
     * @param participant
     */
    private static SOAPServiceBinding createBindings(
            PromotedService promotedService, PortType portType,
            String timeStamp, boolean replaceWithTS, Participant participant) {

        BindingService bindingService =
                ComponentTypeActivator.getDefault().getBindingService();

        String baseName = portType.getQName().getLocalPart() + "_EP"; //$NON-NLS-1$
        String finalName = baseName;
        int idx = 0;

        EList<Binding> bindings = promotedService.getBindings();
        for (Binding binding : bindings) {
            if (binding instanceof SOAPServiceBinding) {
                String bindingName = binding.getName();
                if (null != bindingName) {
                    if (bindingName.contains(baseName)) {
                        idx++;
                        finalName = bindingName + idx;
                    }
                }
            }
        }

        /* XPD-4676: had to change the order of api calls */
        BindingUtil.setAdditionalBindingConfiguration(promotedService,
                null,
                false,
                null);
        SOAPServiceBinding binding =
                (SOAPServiceBinding) bindingService.addBinding(promotedService,
                        SOAP_BINDING_ID,
                        finalName,
                        null);
        return binding;
    }

}
