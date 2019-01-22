/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.util;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.bindingtype.rest.model.RESTReferenceBinding;
import com.tibco.amf.sca.componenttype.BindingService;
import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.util.BindingUtil;
import com.tibco.amf.sca.model.componenttype.BindingAdjunct;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.composite.PromotedReference;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.amf.sca.model.extensionpoints.SCAExtensionsFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdl2.Participant;

/**
 * Helper class to create and deal with REST bindings.
 *
 * @author jarciuch
 * @since 6 May 2015
 */
public class RestBindingUtil {

    /**
     * Configure all REST composite bindings (for REST services) on promoted
     * references and services.
     * <p>
     * This method should be called from within EMF write transaction.
     * </p>
     * 
     * @param project
     *            the context project.
     * @param composite
     *            the context composite.
     * @param timeStamp
     *            timestamp of the composite generation.
     * @param replaceWithTS
     *            'true' if timestamp should be replaced.
     * @return status of the operation. {@link Status#OK_STATUS} if operation
     *         was successful.
     */
    public static IStatus configCompositeRestBindings(IProject project,
            Composite composite, String timeStamp, boolean replaceWithTS) {

        /* Only REST references are supported. */
        for (PromotedReference promotedReference : composite.getReferences()) {
            Participant participant =
                    N2PECompositeUtil.getParticipantForReference(project,
                            promotedReference);
            if (SharedResourceUtil.isRestConsumer(participant)) {
                configRestReferenceBinding(project,
                        promotedReference,
                        participant);
            }
        }
        return Status.OK_STATUS;
    }

    /**
     * Configures REST binding for a promoted reference.
     * 
     * @param project
     *            the context project.
     * @param promotedReference
     *            the promoted ref. that should have the binding configured.
     * @param participant
     *            the context participant.
     */
    private static void configRestReferenceBinding(IProject project,
            PromotedReference promotedReference, Participant participant) {
        BindingService bindingService =
                ComponentTypeActivator.getDefault().getBindingService();

        // Prevents shared resource template (file) generation.
        boolean generateSharedResourceTemplate = false;
        BindingUtil.setAdditionalBindingConfiguration(promotedReference,
                null,
                generateSharedResourceTemplate,
                null);

        // Adds and configure rest binding on the promoted reference.
        RESTReferenceBinding binding =
                (RESTReferenceBinding) bindingService
                        .addBinding(promotedReference, "rest.binding", //$NON-NLS-1$
                                "RESTReference_Binding", //$NON-NLS-1$
                                null);
        binding.setPassthrough(true);

        // Adds the binding adjunct property used for configuration of
        // HttpClient shared resource.
        RestServiceResource restResource =
                SharedResourceUtil.getRestConsumerResource(participant);
        assert restResource != null : "Participant's REST Clinet resource is not configured."; //$NON-NLS-1$
        String httpClientInstanceName =
                restResource.getHttpClientInstanceName();
        addHttpClinetBindingPropertyForReference(binding,
                httpClientInstanceName);

    }

    /**
     * Adds (or replaces) property to the binding adjunct of the specified
     * binding.
     * 
     * @param binding
     *            the context binding.
     * @param simpleValue
     *            value of the property. It will be the name of the HttpClient
     *            shared resource.
     */
    private static void addHttpClinetBindingPropertyForReference(
            RESTReferenceBinding binding, String simpleValue) {

        final String httpClientPropertyName = "HttpOutboundConnectionConfig"; //$NON-NLS-1$
        BindingAdjunct bindingAdjunct =
                BindingUtil.getBindingAdjunctFor(binding);
        EList<Property> properties = bindingAdjunct.getProperties();

        // Remove property(ies) if it already exists.
        for (Iterator<Property> iter = properties.iterator(); iter.hasNext();) {
            Property p = iter.next();
            if (httpClientPropertyName.equals(p.getName())) {
                iter.remove();
            }
        }
        // Adds property to the binding adjunct.
        if (simpleValue != null) {
            Property property = SCAExtensionsFactory.INSTANCE.createProperty();
            property.setName(httpClientPropertyName);
            property.setType(new QName(SoapBindingUtil.HTTP_SR_NS,
                    "HttpClientConfiguration")); //$NON-NLS-1$
            property.setSimpleValue(simpleValue);
            property.setMustSupply(true);
            properties.add(property);
        }
    }
}
