/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.channeltypes.ChannelTypes;
import com.tibco.xpd.presentation.channeltypes.ChannelTypesFactory;
import com.tibco.xpd.presentation.channeltypes.EnumLiteral;
import com.tibco.xpd.presentation.channeltypes.Implementation;
import com.tibco.xpd.presentation.channeltypes.NamedElement;
import com.tibco.xpd.presentation.channeltypes.Presentation;
import com.tibco.xpd.presentation.channeltypes.Target;
import com.tibco.xpd.presentation.resources.ui.PresentationResourcesUIActivator;

/**
 * The resource for ChannelTypes.
 * <p>
 * <i>Created: 11 Mar 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ChannelTypesResourceImpl extends ResourceImpl {

    /** */
    private static final String ENUM_LITERAL_ELEMENT = "enumLiteral"; //$NON-NLS-1$

    /** */
    private static final String ATTRIBUTE_ELEMENT = "attribute"; //$NON-NLS-1$

    /** */
    private static final String DEFAULT_VALUE_ATTRIBUTE = "defaultValue"; //$NON-NLS-1$

    /** */
    private static final String TYPE_ATTRIBUTE = "type"; //$NON-NLS-1$

    /** */
    private static final String CHANNEL_TYPE_ELEMENT = "channelType"; //$NON-NLS-1$

    /** */
    private static final String IMPLEMENTATION_ELEMENT = "implementation"; //$NON-NLS-1$

    /** */
    private static final String PRESENTATION_ELEMENT = "presentation"; //$NON-NLS-1$

    /** */
    private static final String TARGET_ELEMENT = "target"; //$NON-NLS-1$

    /** */
    private static final String CHANNEL_TYPES_EXTENSION_POINT = "channelTypes"; //$NON-NLS-1$

    /** */
    private static final String DESCRIPTION_ELEMENT = "description"; //$NON-NLS-1$

    /** */
    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    /** */
    private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

    /** */
    private static final String IMPLEMENTATION_ID_ATTRIBUTE =
            "implementationId"; //$NON-NLS-1$

    /** */
    private static final String PRESENTATION_ID_ATTRIBUTE = "presentationId"; //$NON-NLS-1$

    /** */
    private static final String TARGET_ID_ATTRIBUTE = "targetId"; //$NON-NLS-1$

    private static final String REQUIRED_ATTRIBUTE = "required"; //$NON-NLS-1$

    private static final String RUNTIME_VERSION_ID_ATTRIBUTE = "runtimeVersion"; //$NON-NLS-1$

    // XPD-4427: Introduced channelType-Destination Binding support.
    private static final String CHANNEL_DESTINATION = "channelDestination"; //$NON-NLS-1$

    private static final String CHANNEL_DESTINATION_BINDING =
            "channelDestinationBinding"; //$NON-NLS-1$

    private static final String CHANNEL_ID = "channelId"; //$NON-NLS-1$

    private static final String DESTINATION_ID = "destinationId"; //$NON-NLS-1$

    /**
     * Creates an instance of the resource.
     * 
     * @param uri
     *            the URI of the new resource.
     */
    public ChannelTypesResourceImpl(URI uri) {
        super(uri);
    }

    /** {@inheritDoc} */
    @Override
    public void load(Map<?, ?> options) throws IOException {
        if (!isLoaded) {
            Notification notification = setLoaded(true);
            isLoading = true;

            if (errors != null) {
                errors.clear();
            }

            if (warnings != null) {
                warnings.clear();
            }
            try {
                getContents().add(createChannelTypesFromExtension());
            } catch (Exception e) {
                unload();
                throw (RuntimeException) (e instanceof RuntimeException ? e
                        : new RuntimeException(e));
            } finally {
                isLoading = false;

                if (notification != null) {
                    eNotify(notification);
                }

                setModified(false);
                Long timeStamp = Long.valueOf(System.currentTimeMillis());
                if (timeStamp != null) {
                    setTimeStamp(timeStamp);
                }
            }
        }
    }

    private ChannelTypes createChannelTypesFromExtension() {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElements =
                registry.getConfigurationElementsFor(PresentationResourcesUIActivator.PLUGIN_ID,
                        CHANNEL_TYPES_EXTENSION_POINT);
        ChannelTypesFactory f = ChannelTypesFactory.eINSTANCE;
        ChannelTypes channelTypes = f.createChannelTypes();
        // XPD-4427: To collect the channelType-Destination bindings to be
        // processed after the channelTypes are loaded.
        List<IConfigurationElement> channelDestinationBindings =
                new ArrayList<IConfigurationElement>();
        Map<String, ChannelDestination> destinationsMap =
                new HashMap<String, ChannelDestination>();
        // channel types' components
        for (IConfigurationElement e : configurationElements) {
            String elementName = e.getName();
            if (TARGET_ELEMENT.equals(elementName)) {
                Target target = f.createTarget();
                fillNamedElement(target, e);
                channelTypes.getTargets().add(target);
            } else if (PRESENTATION_ELEMENT.equals(elementName)) {
                Presentation presentation = f.createPresentation();
                fillNamedElement(presentation, e);
                channelTypes.getPresentations().add(presentation);
            } else if (IMPLEMENTATION_ELEMENT.equals(elementName)) {
                Implementation implementation = f.createImplementation();
                fillNamedElement(implementation, e);
                channelTypes.getImplementations().add(implementation);
            } else if (CHANNEL_DESTINATION_BINDING.equals(elementName)) {
                // XPD-4427: collect all ChannelType-Destination Bindings to be
                // processed after all ChannelTypes are loaded.
                channelDestinationBindings.add(e);
            } else if (CHANNEL_DESTINATION.equals(elementName)) {
                // XPD-4427: Channel-Destination
                ChannelDestination destination = f.createChannelDestination();
                fillNamedElement(destination, e);
                channelTypes.getChannelDestinations().add(destination);
                destinationsMap.put(destination.getId(), destination);
            }
        }

        // channel types
        for (IConfigurationElement e : configurationElements) {
            String elementName = e.getName();
            if (CHANNEL_TYPE_ELEMENT.equals(elementName)) {
                ChannelType channelType = f.createChannelType();
                fillNamedElement(channelType, e);
                String targetId = e.getAttribute(TARGET_ID_ATTRIBUTE);
                channelType.setTarget(channelTypes.getTarget(targetId));

                String presentationId =
                        e.getAttribute(PRESENTATION_ID_ATTRIBUTE);
                channelType.setPresentation(channelTypes
                        .getPresentation(presentationId));

                String implementationId =
                        e.getAttribute(IMPLEMENTATION_ID_ATTRIBUTE);
                channelType.setImplementation(channelTypes
                        .getImplementation(implementationId));

                String runtimeVersion =
                        e.getAttribute(RUNTIME_VERSION_ID_ATTRIBUTE);
                channelType.setRuntimeVersion(runtimeVersion);
                IConfigurationElement[] attributes =
                        e.getChildren(ATTRIBUTE_ELEMENT);
                for (IConfigurationElement attributeElement : attributes) {
                    Attribute channelTypeAttr = f.createAttribute();
                    fillNamedElement(channelTypeAttr, attributeElement);
                    channelTypeAttr.setType(AttributeType.get(attributeElement
                            .getAttribute(TYPE_ATTRIBUTE)));
                    if (channelTypeAttr.getType() == AttributeType.ENUM
                            || channelTypeAttr.getType() == AttributeType.ENUM) {
                        for (IConfigurationElement enumLiteralElement : attributeElement
                                .getChildren(ENUM_LITERAL_ELEMENT)) {
                            EnumLiteral enumLiteral = f.createEnumLiteral();
                            fillNamedElement(enumLiteral, enumLiteralElement);
                            channelTypeAttr.getEnumLiterals().add(enumLiteral);
                        }
                        // TODO default value(s) attributes for enum and enum
                        // sets.
                    } else {
                        channelTypeAttr.setDefaultValue(attributeElement
                                .getAttribute(DEFAULT_VALUE_ATTRIBUTE));
                    }
                    channelTypeAttr.setRequired(Boolean
                            .parseBoolean(attributeElement
                                    .getAttribute(REQUIRED_ATTRIBUTE)));
                    channelType.getAttributes().add(channelTypeAttr);

                }
                channelTypes.getChannelTypes().add(channelType);
            }
        }
        // XPD-4427: Process and load all ChannelType-Destinations bindings.
        for (IConfigurationElement e : channelDestinationBindings) {
            // search the channelType
            ChannelType channelType =
                    getChannelWithId(e.getAttribute(CHANNEL_ID),
                            channelTypes.getChannelTypes());
            if (channelType != null) {
                // add destination to the channelType, when found.
                ChannelDestination destination =
                        destinationsMap.get(e.getAttribute(DESTINATION_ID));
                if (destination != null) {
                    // if destination is found
                    channelType.getDestinations().add(destination);
                }
            }
        }

        return channelTypes;
    }

    /**
     * Searches for ChhanelType with given Id.Returns null if not found.
     * 
     * @param channelTypeId
     * @param eList
     * @return
     */
    private ChannelType getChannelWithId(String channelTypeId,
            EList<ChannelType> eList) {
        for (ChannelType channelType : eList) {
            if (channelType.getId().equalsIgnoreCase(channelTypeId)) {
                return channelType;
            }
        }
        return null;
    }

    /**
     * Fill named element form extension.
     * 
     * @param namedElement
     *            the element to fill.
     * @param e
     *            element from extension representing named element.
     */
    private void fillNamedElement(NamedElement namedElement,
            IConfigurationElement e) {
        String id = e.getAttribute(ID_ATTRIBUTE);
        if (id != null)
            namedElement.setId(id);
        namedElement.setName(e.getAttribute(NAME_ATTRIBUTE));
        namedElement.setDisplayName(e.getAttribute(NAME_ATTRIBUTE));
        IConfigurationElement[] descElements =
                e.getChildren(DESCRIPTION_ELEMENT);
        namedElement.setDescription(descElements.length > 0 ? descElements[0]
                .getValue() : null);
    }

} // ChannelTypesResourceImpl
