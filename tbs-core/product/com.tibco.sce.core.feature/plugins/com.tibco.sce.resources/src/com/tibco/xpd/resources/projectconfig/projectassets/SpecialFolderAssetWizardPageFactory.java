/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;

/**
 * Project asset wizard page that should be used by any asset that needs to set
 * up a special folder.
 * <p>
 * The following initialisation data can be provided:
 * <ul>
 * <li>pageName - name to give this wizard page</li>
 * <li>defaultFolder - the default folder name for the special folder</li>
 * <li>title - the title of the wizard page</li>
 * </ul>
 * <p>
 * If the above data is not provided then the pageName will be generated, the
 * title will be based on the name of the project asset and an empty default
 * folder name will be used.
 * </p>
 * <p>
 * For information on how to provide the initialisation data see
 * <code>{@link IExecutableExtension#setInitializationData(IConfigurationElement, String, Object)}</code>
 * </p>
 * <p>
 * <b>NOTE</b>: If this wizard page is used in the project asset extension then
 * the configuration object should be set to
 * <code>{@link SpecialFolderAssetConfiguration}</code> (or a class that extends
 * this class) and the configurator should be set to
 * <code>{@link SpecialFolderAssetConfigurator}</code>.
 * </p>
 * 
 * 
 * @see SpecialFolderAssetConfiguration
 * @see SpecialFolderAssetConfigurator
 * @see AbstractSpecialFolderAssetWizardPage
 * 
 * @author njpatel
 * 
 * @deprecated Since 3.2 it is recommended that if an asset is only contributing
 *             a special folder then it should not contribute any wizard pages.
 *             This will ensure that all assets of this kind will have their
 *             special folders set via a single page in the new project wizard.
 *             This is to avoid having a number of pages in the wizard with only
 *             a single control. See {@link SpecialFolderAssetConfigurator} for
 *             details on how to specify the default folder name.
 */
public class SpecialFolderAssetWizardPageFactory implements
        IExecutableExtensionFactory, IExecutableExtension {

    private static final String NAME_KEY = "pageName"; //$NON-NLS-1$

    private static final String FOLDER_KEY = "defaultFolder"; //$NON-NLS-1$

    private static final String TITLE_KEY = "title"; //$NON-NLS-1$

    private Properties properties;

    private IConfigurationElement config;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IExecutableExtensionFactory#create()
     */
    public Object create() throws CoreException {
        String pageName = (String) properties.get(NAME_KEY);
        if (pageName == null) {
            // Add default name based on plugin id
            if (config != null
                    && config.getParent() instanceof IConfigurationElement) {
                IConfigurationElement parent = (IConfigurationElement) config
                        .getParent();

                String id = parent.getAttribute("id"); //$NON-NLS-1$

                if (id != null) {
                    pageName = id;

                    IConfigurationElement[] children = parent
                            .getChildren("wizardpage"); //$NON-NLS-1$

                    if (children != null) {
                        int idx = 1;
                        for (IConfigurationElement child : children) {
                            if (child.equals(config)) {
                                break;
                            }

                            ++idx;
                        }

                        pageName += "." + idx; //$NON-NLS-1$
                    }
                }
            }
        }

        return new WizardPage(pageName != null ? pageName : "assetPage"); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {

        this.config = config;
        if (data instanceof String) {
            properties = loadProperties((String) data);
        } else if (data instanceof Hashtable) {
            properties = new Properties();
            properties.putAll((Map<?, ?>) data);
        } else {
            properties = new Properties();
        }

        if (!properties.containsKey(TITLE_KEY)) {
            // Create the title from the asset name
            if (config.getParent() instanceof IConfigurationElement) {
                String assetName = ((IConfigurationElement) config.getParent())
                        .getAttribute("name"); //$NON-NLS-1$

                if (assetName != null && assetName.length() > 0) {
                    properties.setProperty(TITLE_KEY, assetName);
                }

            }
        }

    }

    /**
     * Create properties from the adapter data string given.
     * 
     * @param data
     * @return
     */
    private Properties loadProperties(String data) {
        Properties prop = new Properties();

        if (data != null) {
            String[] pairings = data.split("(\\A|\\s)+-"); //$NON-NLS-1$

            for (String pair : pairings) {
                String[] value = pair.trim().split("\\s+", 2); //$NON-NLS-1$

                if (value.length == 2) {
                    prop.put(value[0], value[1].replaceAll("\\-", "-")); //$NON-NLS-1$  //$NON-NLS-2$
                }
            }
        }

        return prop;
    }

    /**
     * Extension of the {@link AbstractSpecialFolderAssetWizardPage} to
     * configure a special folder.
     * 
     * @author njpatel
     * 
     */
    private class WizardPage extends AbstractSpecialFolderAssetWizardPage {

        private ProjectConfig projectConfig;

        private IProject project;

        public WizardPage(String id) {
            super(id);

            // Set the title.
            String title = properties.getProperty(TITLE_KEY);

            if (title != null) {
                setTitle(title);
                setMessage(String
                        .format(
                                Messages.SpecialFolderAssetWizardPageFactory_messageWithTitle_message,
                                title));
            } else {
                setTitle(Messages.SpecialFolderAssetWizardPageFactory_title);
                setMessage(Messages.SpecialFolderAssetWizardPageFactory_message);
            }
        }

        @Override
        public void init(Object config) {
            super.init(config);

            // If a selection is made then get project config object so that
            // existing special folders can be detected
            SpecialFolderAssetConfiguration sfConfig = getSpecialFolderConfig();

            if (sfConfig != null && sfConfig.getSelection() != null) {
                Object firstElement = sfConfig.getSelection().getFirstElement();

                if (firstElement instanceof IResource) {
                    project = ((IResource) firstElement).getProject();

                    if (project != null) {
                        projectConfig = XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);
                    }
                }
            }
        }

        @Override
        protected String getDefaultSpecialFolderName() {
            SpecialFolderAssetConfiguration config = getSpecialFolderConfig();
            String folderName = null;

            if (config != null) {
                /*
                 * If a selection is set then override the default folder name
                 * with the selection
                 */
                if (config.getSelection() != null
                        && config.getSelection().getFirstElement() instanceof IFolder) {
                    folderName = ((IFolder) config.getSelection()
                            .getFirstElement()).getProjectRelativePath()
                            .toString();
                } else if (properties.containsKey(FOLDER_KEY)) {
                    folderName = properties.getProperty(FOLDER_KEY);
                }
            }

            return folderName != null ? folderName : ""; //$NON-NLS-1$
        }

        public void updateConfiguration() {
            SpecialFolderAssetConfiguration config = getSpecialFolderConfig();

            if (config != null) {
                config.setSpecialFolderName(getSpecialFolderName());
            }
        }

        @Override
        protected boolean validatePage() {
            boolean valid = super.validatePage();

            if (valid) {
                // Check that the folder selected isn't already marked as a
                // special folder
                if (project != null && projectConfig != null) {
                    String specialFolderName = getSpecialFolderName();

                    if (specialFolderName != null
                            && specialFolderName.length() > 0) {
                        IFolder folder = project.getFolder(specialFolderName);

                        if (folder != null && folder.exists()) {
                            valid = projectConfig.getSpecialFolders()
                                    .getFolder(folder) == null;

                            if (!valid) {
                                setErrorMessage(String
                                        .format(
                                                Messages.SpecialFolderAssetWizardPageFactory_alreadySpecialFolderError_message,
                                                specialFolderName));
                            }
                        }
                    }
                }
            }

            return valid;
        }

        /**
         * Get the special folder configuration object.
         * 
         * @return if the configuration object associated with this asset is an
         *         instanceof <code>SpecialFolderConfiguration</code> then it
         *         will be returned, <code>null</code> otherwise.
         */
        private SpecialFolderAssetConfiguration getSpecialFolderConfig() {
            Object configuration = getConfiguration();

            if (configuration instanceof SpecialFolderAssetConfiguration) {
                return (SpecialFolderAssetConfiguration) configuration;
            }

            return null;
        }
    }
}
