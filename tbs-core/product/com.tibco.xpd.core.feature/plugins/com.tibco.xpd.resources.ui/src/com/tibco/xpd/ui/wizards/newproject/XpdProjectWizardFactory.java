/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * XPD project wizard factory (implements
 * <code>IExecutableExtensionFactory</code> and
 * <code>IExecutableExtension</code>) that will create the new wizard with the
 * adapter data provided. This class should be specified as the class in the new
 * wizard extension <code>org.eclipse.ui.newWizards</code>. The following
 * parameters are expected as the adapter data:
 * <ul>
 * <li><b>title</b> - the title of the project wizard. This is an optional
 * parameter, if the title is not provided then the name of the wizard will be
 * used to derive the title,</li>
 * <li><b><del>assets</del>*</b> - a list of asset ids to enable in the wizard
 * by default. This can be either a space, semi-colon or comma separated string.
 * If this parameter is not defined then all asset types will be selected by
 * default.</li>
 * <li><b>assertTypeSorter</b> - a class that extends {@link AssetTypeSorter} to
 * sort the order of appearance of the asset type contributions in the project
 * wizard (Note, this does not affect the asset selection page).</li>
 * <li><b>hideAssetSelection</b> - no value expected with this parameter. This
 * parameter will indicate that the asset type selection page in the wizard
 * should not be shown.</li>
 * <li><b>hideDestinationEnv</b> - no value expected with this parameter. This
 * parameter will indicate that the destination environment selection in the new
 * project wizard should not be shown.</li>
 * <li><b>hideProjectLifecycle</b> - no value expected with this parameter. This
 * parameter will indicate that the project lifecycle section in the new project
 * wizard should not be shown (section to set id, version, status and
 * destination environment). (If this parameter is set then
 * <code>hideDestinationEnv</code> will be ignored if set.</li>
 * <li><b>hideProjectVersion</b> - hide just the version part of project
 * lifecycle</li>
 * <li><b>defaultProjectVersion</b> - the default version of the project. This
 * parameter will set the initial version of the new project. If not provided
 * then it will default to "1.0.0.qualifier".</li>
 * <li><b>setDestinationEnv</b> - The destination name of the dfault destination
 * to set. Forces hide on destination selection controls.</li>
 * </ul>
 * <p>
 * <b>*NOTE: This method of binding the asset types to a new project wizard has
 * been deprecated. Use extension point
 * <i>com.tibco.xpd.resources.projectAssetBinding</i> instead.</b>
 * </p>
 * <p>
 * The adapter data can be specified using two methods:
 * </p>
 * <ul>
 * 1. By specifying adapter data as part of the implementation class attribute
 * value. The Java class name (this class) has to be followed by a ":"
 * separator, followed by the adapter data in string form. Example shown
 * below:<br/>
 * <br/>
 * <code>
 * &lt;extension point="org.eclipse.ui.newWizards"&gt;<br/> &lt;wizard<br/>
 * &nbsp;&nbsp;category="com.tibco.xpd.resources.ui.category.tibco"<br/> <b>
 * &nbsp;&nbsp;class="com.tibco.xpd.ui.wizards.newproject.XpdProjectWizardFactory:-title New BPM/SOA Developer Project -assets
 * com.tibco.xpd.asset.businessProcess, com.tibco.xpd.asset.cm,
 * com.tibco.xpd.asset.wsdl"<br/> </b>
 * &nbsp;&nbsp;finalPerspective="com.tibco.xpd.branding.perspective"<br/>
 * &nbsp;&nbsp;icon="icons/Project 16 n p.png"<br/>
 * &nbsp;&nbsp;id="com.tibco.xpd.resources.ui.newDeveloperProject.wizard"<br/>
 * &nbsp;&nbsp;name="BPM/SOA Developer Project"<br/>
 * &nbsp;&nbsp;preferredPerspectives="com.tibco.xpd.branding.perspective"<br/>
 * &nbsp;&nbsp;project="true"/&gt;<br/> &lt;/extension&gt;
 * </code>
 * </ul>
 * <ul>
 * 2. By converting the attribute used to specify the executable extension to a
 * child element of the original configuration element, and specifying the
 * adapter data in the form of xml markup. Using this form, the example above
 * would become<br/>
 * <br/>
 * <code>
 * &lt;extension point="org.eclipse.ui.newWizards"&gt;<br/>
 * &lt;wizard<br/>
 * &nbsp;&nbsp;category="com.tibco.xpd.resources.ui.category.tibco"<br/>
 * &nbsp;&nbsp;finalPerspective="com.tibco.xpd.branding.perspective"<br/>
 * &nbsp;&nbsp;icon="icons/Project 16 n p.png"<br/>
 * &nbsp;&nbsp;id="com.tibco.xpd.resources.ui.newDeveloperProject.wizard"<br/>
 * &nbsp;&nbsp;name="BPM/SOA Developer Project"<br/>
 * &nbsp;&nbsp;preferredPerspectives="com.tibco.xpd.branding.perspective"<br/>
 * &nbsp;&nbsp;project="true"&gt;<br/>
 * <b>
 * &nbsp;&nbsp;&lt;class class="com.tibco.xpd.ui.wizards.newproject.XpdProjectWizardFactory"&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter name="title" value="New BPM/SOA Developer Project"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter name="assets"  value="com.tibco.xpd.asset.businessProcess, com.tibco.xpd.asset.cm, com.tibco.xpd.asset.wsdl"/&gt;<br/>
 * &nbsp;&nbsp;&lt;/class&gt;<br/>
 * </b>
 * &lt;/wizard&gt;<br/>
 * &lt;/extension&gt;<br/>
 * </code>
 * </ul>
 * <p>
 * This factory creates an instance of <code>{@link XpdProjectWizard}</code> and
 * initialises it with the adapter data provided for each new wizard extension
 * that uses this factory.
 * </p>
 * 
 * @see IExecutableExtensionFactory
 * @see IExecutableExtension
 * @see XpdProjectWizard
 * 
 * @author njpatel
 * 
 */
public class XpdProjectWizardFactory implements IExecutableExtensionFactory,
        IExecutableExtension {

    private class ConfigurationData {
        public static final String PARAM_TITLE = "title"; //$NON-NLS-1$

        public static final String PARAM_ASSETS = "assets"; //$NON-NLS-1$

        public static final String PARAM_SORTER = "assetTypeSorter"; //$NON-NLS-1$

        public static final String PARAM_HIDEASSETSELECTION =
                "hideAssetSelection"; //$NON-NLS-1$

        public static final String PARAM_HIDEDESTINATIONENV =
                "hideDestinationEnv"; //$NON-NLS-1$

        public static final String PARAM_PROJECTLIFECYCLE =
                "hideProjectLifecycle"; //$NON-NLS-1$

        public static final String PARAM_DEFAULT_PROJECT_VERSION =
                "defaultProjectVersion"; //$NON-NLS-1$

        /*
         * Sid ACE-441 - new param to force setting of a specific destination
         * env.
         */
        public static final String PARAM_PRESET_DESTINATION_ENV =
                "presetDestinationEnv"; //$NON-NLS-1$

        /*
         * Sid ACE-441 - new param to hide project version
         */
        public static final String PARAM_HIDE_PROJECT_VERSION =
                "hideProjectVersion"; //$NON-NLS-1$

        public String title;

        public String[] assets;

        public AssetTypeSorter sorter;

        public boolean hideAssetSelectionPage;

        public boolean hideDestinationEnv;

        public boolean hideProjectLifecycle;

        public String defaultProjectVersion;

        public String presetDestinationEnv;

        public boolean hideProjectVersion;
    }

    private ConfigurationData configData;

    private IConfigurationElement config;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IExecutableExtensionFactory#create()
     */
    @Override
    @SuppressWarnings("deprecation")
    public Object create() throws CoreException {
        XpdProjectWizard wizard = new XpdProjectWizard(config);

        if (configData != null) {
            if (configData.title != null) {
                wizard.setWindowTitle(configData.title);
            }

            wizard.setAssetIdsToEnable(configData.assets);

            if (configData.sorter != null) {
                wizard.setSorter(configData.sorter);
            }

            if (configData.hideAssetSelectionPage) {
                wizard.hideAssetSelectionPage();
            }

            if (configData.hideProjectLifecycle) {
                wizard.hideProjectLifecycle();
            } else if (configData.hideDestinationEnv) {
                wizard.hideDestinationEnv();
            }

            if (configData.defaultProjectVersion != null) {
                wizard.setDefaultProjectVersion(configData.defaultProjectVersion);
            }

            if (configData.presetDestinationEnv != null) {
                wizard.setDestinationEnv(configData.presetDestinationEnv);
            }

            if (configData.hideProjectVersion) {
                wizard.hideProjectVersion();
            }

        } else {
            throw new CoreException(new Status(IStatus.ERROR,
                    XpdResourcesUIActivator.ID, IStatus.OK,
                    Messages.XpdProjectWizardFactory_noDataConfigErr_message,
                    null));
        }

        return wizard;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {

        this.config = config;
        Map<String, String> configMap = null;

        /*
         * Extract the adapter data
         */
        if (data instanceof String) {
            String[] confData = ((String) data).split("-"); //$NON-NLS-1$
            configMap = new HashMap<String, String>();

            for (String conf : confData) {
                if (conf != null && conf.length() > 0) {
                    if (conf.indexOf(' ') > 0) {
                        String param =
                                conf.substring(0, conf.indexOf(' ')).trim();
                        configMap
                                .put(param,
                                        conf.substring(conf.indexOf(' '),
                                                conf.length()).trim());
                    } else {
                        configMap.put(conf, null);
                    }
                }
            }
        } else if (data instanceof Map) {
            configMap = (Map<String, String>) data;
        }

        validateConfigData(config.getContributor().getName(), configMap);

        // Set the config values from the data
        configData = new ConfigurationData();

        // Set title
        if (configMap != null
                && configMap.containsKey(ConfigurationData.PARAM_TITLE)) {
            configData.title = configMap.get(ConfigurationData.PARAM_TITLE);
        } else {
            // Create title from the name specified for the wizard in the
            // extension
            String name = config.getAttribute("name"); //$NON-NLS-1$

            if (name != null) {
                configData.title =
                        String.format(Messages.XpdProjectWizardFactory_title,
                                name);
            }
        }

        // Set assets
        if (configMap != null) {
            String value = null;
            if (configMap.containsKey(ConfigurationData.PARAM_ASSETS)) {
                value = configMap.get(ConfigurationData.PARAM_ASSETS);
                if (value != null) {
                    configData.assets = value.split("[\\s,;]+"); //$NON-NLS-1$
                }
            }

            /* Default project version */
            if (configMap
                    .containsKey(ConfigurationData.PARAM_DEFAULT_PROJECT_VERSION)) {
                value =
                        configMap
                                .get(ConfigurationData.PARAM_DEFAULT_PROJECT_VERSION);
                if (value != null) {
                    configData.defaultProjectVersion = value;
                }
            }

            // Set whether the asset selection page should be hidden
            configData.hideAssetSelectionPage =
                    configMap
                            .containsKey(ConfigurationData.PARAM_HIDEASSETSELECTION);

            // Set whether the destination env selection should be hidden
            configData.hideDestinationEnv =
                    configMap
                            .containsKey(ConfigurationData.PARAM_HIDEDESTINATIONENV);

            // Set whether the project lifecycle section should be hidden
            configData.hideProjectLifecycle =
                    configMap
                            .containsKey(ConfigurationData.PARAM_PROJECTLIFECYCLE);

            // Set whether the just the version number in project lifecycle
            // section should be hidden
            configData.hideProjectVersion = configMap
                    .containsKey(ConfigurationData.PARAM_HIDE_PROJECT_VERSION);

            // Record the default destination to set.
            value = configMap.get(ConfigurationData.PARAM_PRESET_DESTINATION_ENV);
            if (value != null) {
                configData.presetDestinationEnv = value;
                configData.hideDestinationEnv = true;
            }

            if (configMap.containsKey(ConfigurationData.PARAM_SORTER)) {
                value = configMap.get(ConfigurationData.PARAM_SORTER);
                if (value != null && value.length() > 0) {
                    try {
                        Bundle bundle =
                                Platform.getBundle(config.getContributor()
                                        .getName());
                        if (bundle != null) {
                            Class cls = bundle.loadClass(value);
                            if (cls != null) {
                                Object instance = cls.newInstance();
                                if (instance instanceof AssetTypeSorter) {
                                    configData.sorter =
                                            (AssetTypeSorter) instance;
                                }
                            }
                        } else {
                            throw new CoreException(
                                    new Status(
                                            IStatus.ERROR,
                                            XpdResourcesUIActivator.ID,
                                            String.format(Messages.XpdProjectWizardFactory_unableToAccessBundle_error_shortdesc,
                                                    config.getContributor()
                                                            .getName())));
                        }
                    } catch (ClassNotFoundException e) {
                        throw new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        XpdResourcesUIActivator.ID,
                                        String.format(Messages.XpdProjectWizardFactory_cannotFindAssetTypeSorter_error_shortdesc,
                                                value), e));
                    } catch (InstantiationException e) {
                        throw new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        XpdResourcesUIActivator.ID,
                                        String.format(Messages.XpdProjectWizardFactory_cannotInstantiateSorter_error_shortdesc,
                                                value), e));
                    } catch (IllegalAccessException e) {
                        throw new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        XpdResourcesUIActivator.ID,
                                        String.format(Messages.XpdProjectWizardFactory_cannotAccessSorter_error_shortdesc,
                                                value), e));
                    }
                }
            }
        }
    }

    /**
     * Validate the input parameters. If any parameter is not recogines it will
     * be logged in the error log.
     * 
     * @param configMap
     */
    private void validateConfigData(String id, Map<String, String> configMap) {
        if (configMap != null) {
            for (String param : configMap.keySet()) {
                if (!isValidParameter(param)) {
                    XpdResourcesUIActivator
                            .getDefault()
                            .getLogger()
                            .error(String.format("XpdProjectWizardFactory contributed by '%1$s' has an invalid parameter '%2$s' specified.", //$NON-NLS-1$
                                    id,
                                    param));
                }
            }
        }
    }

    /**
     * Check if the given parameter is valid.
     * 
     * @param param
     * @return
     */
    private boolean isValidParameter(String param) {
        if (param.equals(ConfigurationData.PARAM_ASSETS)
                || param.equals(ConfigurationData.PARAM_HIDEASSETSELECTION)
                || param.equals(ConfigurationData.PARAM_HIDEDESTINATIONENV)
                || param.equals(ConfigurationData.PARAM_PROJECTLIFECYCLE)
                || param.equals(ConfigurationData.PARAM_SORTER)
                || param.equals(ConfigurationData.PARAM_TITLE)
                || param.equals(ConfigurationData.PARAM_PRESET_DESTINATION_ENV)
                || param.equals(ConfigurationData.PARAM_HIDE_PROJECT_VERSION)) {
            return true;
        }
        return false;
    }
}
