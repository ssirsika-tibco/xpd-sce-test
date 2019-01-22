/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.firstclassprofiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Manager class for the first-class profile extension point. This is a
 * singleton class so use {@link #getInstance()} to access this class.
 * 
 * @author njpatel
 * 
 */
public final class FirstClassProfileManager {

    /**
     * Prefix used to denote first-class profiles in the diagram.
     */
    public static final String FIRST_CLASS_PREFIX = "FirstClassProfile::"; //$NON-NLS-1$

    private static final String EXTENSION_POINT = "firstClassProfile"; //$NON-NLS-1$

    private static final FirstClassProfileManager INSTANCE =
            new FirstClassProfileManager();

    private List<IFirstClassProfileExtension> extensions;

    /**
     * Private constructor. This is a singleton class and be accessed using
     * {@link #getInstance()}.
     */
    private FirstClassProfileManager() {

    }

    /**
     * Get the singleton instance of this manager.
     * 
     * @return
     */
    public static FirstClassProfileManager getInstance() {
        return INSTANCE;
    }

    /**
     * Get all the first-class profile extensions.
     * 
     * @return
     */
    public List<IFirstClassProfileExtension> getExtensions() {
        if (extensions == null) {
            extensions = loadExtensions();
        }
        return extensions;
    }

    /**
     * Get the first-class profile extension with the given id.
     * 
     * @param id
     * @return
     */
    public IFirstClassProfileExtension getExtensionById(String id) {
        if (id != null) {
            List<IFirstClassProfileExtension> exts = getExtensions();
            for (IFirstClassProfileExtension ext : exts) {
                if (id.equals(ext.getId())) {
                    return ext;
                }
            }
        }
        return null;
    }

    /**
     * Check if a first-class {@link Profile} is applied to the given
     * {@link Model}.
     * 
     * @see #getAppliedFirstClassProfile(Model)
     * @see FirstClassProfileManager#isFirstClassProfileApplied(Model, String)
     * 
     * @param model
     * @return <code>true</code> if first-class profile is applied,
     *         <code>false</code> otherwise.
     */
    public boolean isFirstClassProfileApplied(Model model) {
        return getAppliedFirstClassProfile(model) != null;
    }

    /**
     * Check if the given first-class profile extension is applied to the given
     * model.
     * 
     * @see #getAppliedFirstClassProfile(Model)
     * @see #isFirstClassProfileApplied(Model)
     * 
     * @param model
     * @param firstClassExtensionId
     * @return <code>true</code> if the given profile is applied,
     *         <code>false</code> otherwise
     */
    public boolean isFirstClassProfileApplied(Model model,
            String firstClassExtensionId) {
        IFirstClassProfileExtension ext = getAppliedFirstClassProfile(model);

        return ext != null && firstClassExtensionId != null
                && firstClassExtensionId.equals(ext.getId());
    }

    /**
     * Get the first-class profile extension if applied to the given
     * {@link Model}.
     * 
     * @param model
     * @return profile extension if set, <code>null</code> otherwise.
     */
    public IFirstClassProfileExtension getAppliedFirstClassProfile(Model model) {
        if (model != null) {

            // Check that a 1st Class Support Viewpoint is applied
            String vp = model.getViewpoint();
            String extensionID;

            if (vp != null && vp.startsWith(FIRST_CLASS_PREFIX)) {
                // Then check that the profile ID in the viewpoint matches
                // a profile ID in an extension
                extensionID = getExtensionIDFromViewpoint(vp);
                if (extensionID != null) {
                    return getExtensionById(extensionID);
                }
            }
        }

        return null;
    }

    /**
     * Get the first-class profile extension id from the given view point.
     * 
     * @param viewpoint
     * @return
     */
    private String getExtensionIDFromViewpoint(String viewpoint) {
        String extID = null;
        int idx = viewpoint.indexOf("::"); //$NON-NLS-1$
        if (idx != -1) {
            extID = viewpoint.substring(idx + 2);
        }
        return extID;
    }

    /**
     * Load the extensions.
     * 
     * @return
     */
    private List<IFirstClassProfileExtension> loadExtensions() {
        List<IFirstClassProfileExtension> exts =
                new ArrayList<IFirstClassProfileExtension>();
        Set<String> ids = new HashSet<String>();

        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(BOMResourcesPlugin.PLUGIN_ID,
                                EXTENSION_POINT);
        Assert.isNotNull(extensionPoint,
                "Cannot find extension: " + EXTENSION_POINT); //$NON-NLS-1$
        if (extensionPoint != null) {
            IConfigurationElement[] elements =
                    extensionPoint.getConfigurationElements();
            if (elements != null) {
                for (IConfigurationElement element : elements) {
                    FirstClassProfileExt ext =
                            new FirstClassProfileExt(element);
                    if (ext.getId() != null) {
                        if (!ids.contains(ext.getId())) {
                            ids.add(ext.getId());
                            // Add the extension to the list
                            exts.add(ext);
                        } else {
                            BOMResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error("Duplicate first-class profile extension found with id: " //$NON-NLS-1$
                                            + ext.getId());
                        }
                    } else {
                        BOMResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error("A first-class profile extension has been found without an id"); //$NON-NLS-1$
                    }
                }
            }
        }

        return exts;
    }

    /**
     * Implementation of the first class profile extension.
     * 
     * @author njpatel
     * 
     */
    private class FirstClassProfileExt implements IFirstClassProfileExtension {

        private static final String ATT_ID = "id"; //$NON-NLS-1$

        private static final String ATT_LABEL = "label"; //$NON-NLS-1$

        private static final String ATT_ICON = "icon"; //$NON-NLS-1$

        private static final String ATT_URI = "uri"; //$NON-NLS-1$

        private static final String ATT_DIAGRAMIMAGE = "diagramImage"; //$NON-NLS-1$

        private static final String ATT_SHOWOPERATIONS = "showOperations"; //$NON-NLS-1$

        private static final String ATT_SHOWBOMPALETTEELEMENTS =
                "showBOMPaletteElements"; //$NON-NLS-1$

        private final IConfigurationElement element;

        private final Profile profile = null;

        private final Object NOT_SET = new Object();

        private Object icon;

        private Object diagramImage;

        /**
         * First class profile extension
         * 
         * @param element
         */
        public FirstClassProfileExt(IConfigurationElement element) {
            this.element = element;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.bom.resources.firstclassprofiles.
         * IFirstClassProfileExtension#getIconUri()
         */
        public ImageDescriptor getIcon() {
            if (icon == null) {
                String attribute = element.getAttribute(ATT_ICON);
                if (attribute != null && attribute.length() > 0) {
                    icon =
                            AbstractUIPlugin.imageDescriptorFromPlugin(element
                                    .getNamespaceIdentifier(), attribute);
                } else {
                    icon = NOT_SET;
                }
            }
            return (ImageDescriptor) (icon instanceof ImageDescriptor ? icon
                    : null);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.bom.resources.firstclassprofiles.
         * IFirstClassProfileExtension#getId()
         */
        public String getId() {
            return element.getAttribute(ATT_ID);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.bom.resources.firstclassprofiles.
         * IFirstClassProfileExtension#getImageUri()
         */
        public ImageDescriptor getDiagramImage() {
            if (diagramImage == null) {
                String attribute = element.getAttribute(ATT_DIAGRAMIMAGE);
                if (attribute != null && attribute.length() > 0) {
                    diagramImage =
                            AbstractUIPlugin.imageDescriptorFromPlugin(element
                                    .getNamespaceIdentifier(), attribute);
                } else {
                    // No image specified
                    diagramImage = NOT_SET;
                }
            }
            return (ImageDescriptor) (diagramImage instanceof ImageDescriptor ? diagramImage
                    : null);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.bom.resources.firstclassprofiles.
         * IFirstClassProfileExtension#getLabel()
         */
        public String getLabel() {
            return element.getAttribute(ATT_LABEL);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.bom.resources.firstclassprofiles.
         * IFirstClassProfileExtension#getProfileUri()
         */
        public URI getProfileUri() {
            URI uri = null;
            String attribute = element.getAttribute(ATT_URI);
            if (attribute != null && attribute.length() > 0) {
                uri = URI.createURI(attribute, true);
            }
            return uri;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.bom.resources.firstclassprofiles.
         * IFirstClassProfileExtension#getProfile()
         */
        public Profile getProfile() {
            if (profile == null) {
                URI uri = getProfileUri();
                if (uri != null) {
                    Resource res =
                            XpdResourcesPlugin.getDefault().getEditingDomain()
                                    .loadResource(uri.toString());
                    if (res != null && res.getContents() != null) {
                        EList<EObject> contents = res.getContents();
                        for (EObject content : contents) {
                            if (content instanceof Profile) {
                                return (Profile) content;
                            }
                        }

                        // No profile was found so log error
                        BOMResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error("Cannot find profile specified in first-class profile extension: " //$NON-NLS-1$
                                        + getId());
                    }
                }
            }
            return profile;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.bom.resources.firstclassprofiles.
         * IFirstClassProfileExtension#showBomPaletteElements()
         */
        public boolean showBomPaletteElements() {
            boolean show = true;
            String attribute = element.getAttribute(ATT_SHOWBOMPALETTEELEMENTS);
            if (attribute != null) {
                show = Boolean.parseBoolean(attribute);
            }
            return show;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.bom.resources.firstclassprofiles.
         * IFirstClassProfileExtension#showOperations()
         */
        public boolean showOperations() {
            boolean show = true;
            String attribute = element.getAttribute(ATT_SHOWOPERATIONS);
            if (attribute != null) {
                show = Boolean.parseBoolean(attribute);
            }
            return show;
        }
    }
}
