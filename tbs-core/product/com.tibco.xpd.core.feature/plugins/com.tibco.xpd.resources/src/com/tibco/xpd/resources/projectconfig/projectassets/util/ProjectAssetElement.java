/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;

/**
 * New XPD project asset extension. This is the extension of the extension point
 * <code>com.tibco.xpd.resources.ui.newProjectAsset</code>
 * 
 * @author njpatel
 */
public class ProjectAssetElement implements IProjectAsset {

    private static final String ATT_ID = "id"; //$NON-NLS-1$

    private static final String ATT_NAME = "name"; //$NON-NLS-1$

    private static final String ATT_VALUE = "value"; //$NON-NLS-1$

    private static final String ATT_CONFIGURATOR = "configurator"; //$NON-NLS-1$

    private static final String ATT_CONFIGURATION = "configuration"; //$NON-NLS-1$

    private static final String ATT_VISIBLE = "visible"; //$NON-NLS-1$

    private static final String ATT_SHOW_CONFIG_WIZ_PAGE_WHEN_INVISIBLE =
            "showConfigPageWhenInvisible"; //$NON-NLS-1$

    private static final String ATT_EXTENDS = "extends"; //$NON-NLS-1$

    private static final String TAG_DESCRIPTION = "description"; //$NON-NLS-1$

    private static final String TAG_WIZARDPAGE = "wizardpage"; //$NON-NLS-1$

    private static final String TAG_CUSTOMPARAM = "customParam"; //$NON-NLS-1$

    private static final String ATT_PAGE = "page"; //$NON-NLS-1$

    private static final String TAG_DEPENDENCY = "dependsOn"; //$NON-NLS-1$

    private static final String ATT_ASSETID = "assetId"; //$NON-NLS-1$

    private static final String ATT_CATEGORYID = "category"; //$NON-NLS-1$

    private static final String ATT_ICON = "icon"; //$NON-NLS-1$

    private static final String TAG_BINDINGS = "bindings"; //$NON-NLS-1$

    private static final String TAG_GLOBAL_DESTINATION = "globalDestination"; //$NON-NLS-1$

    private static final String TAG_NATURE = "nature"; //$NON-NLS-1$

    private static final String TAG_STATIC_VERSION = "staticVersion"; //$NON-NLS-1$

    private static final String TAG_DYNAMIC_VERSION = "dynamicVersion"; //$NON-NLS-1$

    private static final String ATT_VERSION = "version"; //$NON-NLS-1$

    private static final String ATT_VERSION_PROVIDER = "versionProvider"; //$NON-NLS-1$

    private final IConfigurationElement element;

    /*
     * Cache of all content of this extension
     */
    private String id, name, description, extendId, categoryId;

    private Object configuration;

    private IAssetWizardPage[] pages;

    private IAssetConfigurator configurator;

    private Set<String> dependencies = null;

    private IProjectAssetCategory category;

    private ImageDescriptor imageDescriptor;

    private String[] natures;

    private String[] globalDestinations;

    private Integer version;

    private IProjectAssetVersionProvider versionProvider;

    /*
     * Extending asset types
     */
    private final List<ProjectAssetElement> extensions =
            new ArrayList<ProjectAssetElement>();

    /*
     * Make sure the API information section of the extension point schema is
     * updated with details if new values are added to this custom parameter.
     */
    public enum CustomParam {
        NOT_APPLIED_BY_DEFAULT("notAppliedByDefault"); //$NON-NLS-1$

        private String parameterName;

        CustomParam(String parameterName) {
            this.parameterName = parameterName;
        }

        /**
         * @return the parameterName
         */
        public String getParameterName() {
            return parameterName;
        }
    }

    /**
     * Constructor - expects an <code>IConfigurationElement</code>
     * 
     * @param element
     */
    public ProjectAssetElement(IConfigurationElement element) {
        this.element = element;
    }

    /**
     * Get the extension configuration element.
     * 
     * @return
     * @since 3.5
     */
    /* public */IConfigurationElement getConfigurationElement() {
        return element;
    }

    /**
     * Add an extension asset element.
     * 
     * @param element
     */
    public void addExtendingAsset(ProjectAssetElement element) {
        extensions.add(element);
    }

    /**
     * Get the assets that extend this asset.
     * 
     * @return List of extending asset types, empty list if none.
     */
    @Override
    public ProjectAssetElement[] getExtendingAssets() {
        return extensions.toArray(new ProjectAssetElement[extensions.size()]);
    }

    /**
     * Get the id set in the extension
     * 
     * @return ID of the extension
     */
    @Override
    public String getId() {
        if (id == null) {
            id = getAttribute(ATT_ID);
        }
        return id;
    }

    /**
     * Get the name set in the extension
     * 
     * @return name of the extension
     */
    @Override
    public String getName() {
        if (name == null) {
            name = getAttribute(ATT_NAME);
        }

        return name;
    }

    /**
     * Get the extends value set in the extension. This is the id of the asset
     * that this asset wishes to extend.
     * 
     * @return id of the asset type being extended, or empty string if not
     *         extending.
     */
    public String getExtends() {
        if (extendId == null) {
            extendId = getAttribute(ATT_EXTENDS);
        }

        return extendId;
    }

    /**
     * Get the ids of assets that this asset type depends on.
     * 
     * @return Asset IDs of the assets that this asset depends on. This will
     *         also include implicit dependencies (dependecies of any extensions
     *         of this asset type). Empty array will be returned if no
     *         dependencies are present.
     */
    public String[] getDependencies() {
        if (dependencies == null) {
            dependencies = new HashSet<String>();
            IConfigurationElement[] children =
                    element.getChildren(TAG_DEPENDENCY);

            if (children != null) {
                for (IConfigurationElement child : children) {
                    String assetId = child.getAttribute(ATT_ASSETID);

                    if (assetId != null) {
                        dependencies.add(assetId);
                    }
                }
            }

            // Add dependencies of any extending asset types
            for (ProjectAssetElement ext : extensions) {
                addDependenciesFromAsset(ext);
            }
        }

        return dependencies.toArray(new String[dependencies.size()]);
    }

    /**
     * Get the custom parameter value for the given parameter, if set.
     * 
     * @param param
     * @return
     */
    public String getCustomParamValue(CustomParam param) {
        if (element != null) {
            IConfigurationElement[] children =
                    element.getChildren(TAG_CUSTOMPARAM);
            if (children != null) {
                for (IConfigurationElement child : children) {
                    if (param.getParameterName()
                            .equals(child.getAttribute(ATT_NAME))) {
                        return child.getAttribute(ATT_VALUE);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the Nature ids defined in the migration element.
     * 
     * @return array of Nature ids, empty array if none defined.
     * @since 3.5
     */
    public String[] getNatures() {
        if (natures == null) {
            List<String> children = getBindingChildren(TAG_NATURE);
            natures = children.toArray(new String[children.size()]);
        }
        return natures;
    }

    /**
     * Get the Global Destination types (names) defined in the migration
     * element.
     * 
     * @return array of Global Destination names, empty array if none defined.
     * @since 3.5
     */
    public String[] getGlobalDestinationIds() {
        if (globalDestinations == null) {
            List<String> children = getBindingChildren(TAG_GLOBAL_DESTINATION);
            globalDestinations = children.toArray(new String[children.size()]);
        }
        return globalDestinations;
    }

    @Override
    public int getVersion(IProject project) {
        if (project != null && project.isAccessible()) {
            if (version == null && versionProvider == null) {
                // Check if a static version has been specified
                IConfigurationElement[] children =
                        element.getChildren(TAG_STATIC_VERSION);
                if (children.length > 0) {
                    String value = children[0].getAttribute(ATT_VERSION);
                    if (value != null) {
                        try {
                            version = Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            XpdResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(String.format("Asset %1$s has specified an invalid version '%2$s'", //$NON-NLS-1$
                                            getId(),
                                            value));
                            version = 0;
                            return version;
                        }
                    }
                } else {
                    // If not static then check if dynamic vesion provider has
                    // been specified
                    children = element.getChildren(TAG_DYNAMIC_VERSION);
                    if (children.length > 0) {
                        try {
                            versionProvider =
                                    (IProjectAssetVersionProvider) children[0]
                                            .createExecutableExtension(ATT_VERSION_PROVIDER);
                        } catch (CoreException e) {
                            XpdResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            String.format("Problem instantiating the version provider for asset '%s'", //$NON-NLS-1$
                                                    getId()));
                        }
                    } else {
                        // No version provided so set to 0
                        version = 0;
                        return version;
                    }
                }
            }

            if (version != null) {
                return version;
            } else if (versionProvider != null) {
                return versionProvider.getVersion(project);
            }
        }

        return 0;
    }

    /**
     * Get the Id values of the given child type from the Migration element of
     * this configuration.
     * 
     * @param childName
     * @return
     */
    private List<String> getBindingChildren(String childName) {
        List<String> values = new ArrayList<String>();
        IConfigurationElement[] children = element.getChildren(TAG_BINDINGS);
        if (children.length > 0) {
            IConfigurationElement migrationElem = children[0];

            for (IConfigurationElement child : migrationElem
                    .getChildren(childName)) {
                String id = child.getAttribute(ATT_ID);
                if (id != null) {
                    values.add(id);
                }
            }
        }
        return values;
    }

    /**
     * Add to the dependencies list the dependencies of the given asset element.
     * 
     * @param ext
     *            Asset element.
     */
    private void addDependenciesFromAsset(ProjectAssetElement ext) {
        if (ext != null && ext.getDependencies() != null) {
            dependencies.addAll(Arrays.asList(ext.getDependencies()));

            // Check for extensions of this asset
            ProjectAssetElement[] extendingAssets = ext.getExtendingAssets();

            if (extendingAssets != null) {
                for (ProjectAssetElement asset : extendingAssets) {
                    addDependenciesFromAsset(asset);
                }
            }
        }
    }

    /**
     * Get the configurator of this extension
     * 
     * @return <code>IAssetConfigurator</code> object that will configure this
     *         asset type.
     * @throws CoreException
     */
    public IAssetConfigurator getConfigurator() throws CoreException {

        if (configurator == null) {
            if (element != null) {
                configurator =
                        (IAssetConfigurator) element
                                .createExecutableExtension(ATT_CONFIGURATOR);
            }
        }

        return configurator;
    }

    /**
     * Get the configuration object of this asset. This will be the object that
     * will be passed to each associated wizard page to configure the pages and
     * subsequently to the configurator to configure the project for this asset
     * type.
     * 
     * @return <code>Object</code> This value can be <b>null</b>.
     * @throws CoreException
     */
    public Object getConfiguration() throws CoreException {

        if (configuration == null) {
            if (element != null) {
                String className = getAttribute(ATT_CONFIGURATION);
                if (className != null && className.trim().length() > 0) {
                    configuration =
                            element.createExecutableExtension(ATT_CONFIGURATION);
                }
            }
        }

        return configuration;

    }

    /**
     * Get the visible attribute of this asset type.
     * 
     * @return <b>true</b> if the asset type should be displayed in the new
     *         project wizard, <b>false</b> if the asset type should be hidden
     *         in the wizard. The asset type should still be configured even if
     *         this value is set to false.
     */
    @Override
    public boolean isVisible() {
        boolean ret = true;
        String visible = getAttribute(ATT_VISIBLE);

        if (visible != null && visible != "") { //$NON-NLS-1$
            ret = Boolean.parseBoolean(visible);
        }

        return ret;
    }

    /**
     * Get the showAssetConfigPageWhenInvisible attribute of this asset type.
     * 
     * @return <b>true</b> if the asset configuration page for invisible asset
     *         should be displayed in the new project wizard , <b>false</b> if
     *         the asset configuration page for invisible asset should be hidden
     *         in the wizard. Also returns false when the attribute is not set
     *         to set the default behaviour to don't show.
     */
    @Override
    public boolean isShowConfigPageWhenInvisible() {
        boolean ret = false;
        String showAssetConfigPageWhenInvisible = getAttribute(ATT_SHOW_CONFIG_WIZ_PAGE_WHEN_INVISIBLE);

        if (showAssetConfigPageWhenInvisible != null && showAssetConfigPageWhenInvisible != "") { //$NON-NLS-1$
            ret = Boolean.parseBoolean(showAssetConfigPageWhenInvisible);
        }

        return ret;
    }

    /**
     * Get the wizard pages that will be added to the new XPD wizard to
     * configure this asset type for a project.
     * 
     * @return Array of <code>IAssetWizardaPage</code> objects. If no wizard
     *         pages are defined then an empty array will be returned.
     * @throws CoreException
     */
    public IAssetWizardPage[] getWizardPages() throws CoreException {

        if (pages == null) {
            List<IAssetWizardPage> wizardPages =
                    new ArrayList<IAssetWizardPage>();
            if (element != null) {
                IConfigurationElement[] children =
                        element.getChildren(TAG_WIZARDPAGE);

                for (IConfigurationElement child : children) {
                    IAssetWizardPage wizardPage =
                            (IAssetWizardPage) child
                                    .createExecutableExtension(ATT_PAGE);

                    if (wizardPage != null) {
                        wizardPages.add(wizardPage);
                    }
                }
            }

            pages =
                    wizardPages
                            .toArray(new IAssetWizardPage[wizardPages.size()]);
        }

        return pages;
    }

    /**
     * Get the description of this extension.
     * 
     * @return Description of this extension. If one is not set then <b>null</b>
     *         will be returned.
     */
    @Override
    public String getDescription() {
        if (description == null) {
            if (element != null) {
                IConfigurationElement[] children =
                        element.getChildren(TAG_DESCRIPTION);

                if (children != null && children.length > 0) {
                    description = children[0].getValue();
                }
            }
        }

        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s ('%s')", getName(), getId()); //$NON-NLS-1$
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = super.equals(obj);

        if (!equal && obj instanceof ProjectAssetElement) {
            ProjectAssetElement compareTo = (ProjectAssetElement) obj;

            equal =
                    compareTo.getId().equals(getId())
                            && compareTo.getName().equals(getName());
        }

        return equal;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result =
                prime * result
                        + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }

    /**
     * Get the value of the given attribute.
     * 
     * @param attribute
     * @return value of the attribute if set, empty string otherwise.
     */
    private String getAttribute(String attribute) {
        return element != null ? element.getAttribute(attribute) : ""; //$NON-NLS-1$
    }

    /*
     * @see
     * com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset#getCategory
     * ()
     */
    @Override
    public IProjectAssetCategory getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    void setCategory(IProjectAssetCategory category) {
        this.category = category;
    }

    /**
     * Get the category id set in the extension. This is the id of the category
     * that this belongs.
     * 
     * @return id of the asset type being extended
     */
    String getCategoryId() {
        if (categoryId == null) {
            categoryId = getAttribute(ATT_CATEGORYID);
        }

        return categoryId;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset#
     * getImageeDescriptor()
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        if (imageDescriptor == null) {
            imageDescriptor = getImageDescriptor(element, ATT_ICON);
        }
        return imageDescriptor;
    }

    private static ImageDescriptor getImageDescriptor(
            IConfigurationElement configElement, String attrName) {
        String iconPath = configElement.getAttribute(attrName);
        // Get the icon descriptor
        if (iconPath != null) {
            return AbstractUIPlugin.imageDescriptorFromPlugin(configElement
                    .getNamespaceIdentifier(), iconPath);
        }
        return null;
    }

}
