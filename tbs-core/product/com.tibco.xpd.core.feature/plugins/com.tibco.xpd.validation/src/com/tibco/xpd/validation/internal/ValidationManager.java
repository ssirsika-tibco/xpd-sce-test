/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IMarkerResolution;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.CompositeFileFilter;
import com.tibco.xpd.validation.IValidationListener;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.engine.DefaultScopeProvider;
import com.tibco.xpd.validation.internal.engine.PreProcessor;
import com.tibco.xpd.validation.internal.engine.Provider;
import com.tibco.xpd.validation.internal.engine.ValidationEngine;
import com.tibco.xpd.validation.internal.engine.ValidationQueue;
import com.tibco.xpd.validation.internal.engine.WorkspaceResourceValidatorExt;
import com.tibco.xpd.validation.internal.validation.ProjectLifecycleValidator;
import com.tibco.xpd.validation.provider.IFileFilter;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;
import com.tibco.xpd.validation.provider.IPreferenceGroup;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IssueInfo;
import com.tibco.xpd.validation.resolutions.GenericMarkerResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation manager. This is responsible for reading all Validation extensions
 * and providing access to these extensions. It also provides access to the
 * {@link ValidationEngine} and {@link ValidationQueue}. Originally this was
 * done in the {@link ValidationActivator} but to internalize it has now been
 * moved here.
 * <p>
 * Use the singleton {@link #getInstance() instance} to access this manager.
 * </p>
 * 
 * @author njpatel
 * 
 * @since 3.1
 */
public final class ValidationManager {

    private static final ValidationManager INSTANCE = new ValidationManager();

    /** The validation engine. */
    private final ValidationEngine engine;

    /** The validation queue. */
    private final ValidationQueue queue;

    /** The list of pre-processors. */
    private final Map<Class<? extends IPreProcessor>, PreProcessor> preProcessors;

    /** The collection of file filters. */
    private final Map<Destination, IFileFilter> fileFilters;

    /** Map of issue IDs to a list of resolutions. */
    private final Map<String, List<IMarkerResolution>> resolutionsMap;

    private final Map<String, ReusableResolution> reusableResolutions;

    /** The destination environments keyed on destination ID. */
    private Map<String, Destination> destinations;

    /**
     * Private constructor.
     */
    private ValidationManager() {
        queue = new ValidationQueue();
        engine = new ValidationEngine();
        preProcessors =
                new HashMap<Class<? extends IPreProcessor>, PreProcessor>();
        fileFilters = new HashMap<Destination, IFileFilter>();
        resolutionsMap = new HashMap<String, List<IMarkerResolution>>();
        reusableResolutions = new HashMap<String, ReusableResolution>();

    }

    /**
     * Get the singleton instance of this manager.
     * 
     * @return <code>ValidationManager</code>.
     */
    public static final ValidationManager getInstance() {
        return INSTANCE;
    }

    /**
     * Initialize the manager. This will read all validation extensions.
     * 
     */
    public void initialize() {
        loadDestinations();
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            loadReusableResolutions();
            loadResolutions();
        }

        loadProviders();
        loadPreProcessors();

        /*
         * Register the project lifecycle validator that will check for
         * duplicate project ids. This needs to be done here so that it can
         * listen for projects being added and removed from the workspace.
         */
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        if (workspace != null) {
            workspace
                    .addResourceChangeListener(new ProjectLifecycleValidator());
        }
    }

    /**
     * Get the validation queue.
     * 
     * @return The validation queue.
     */
    public ValidationQueue getValidationQueue() {
        return queue;
    }

    private Logger getLogger() {
        return ValidationActivator.getDefault().getLogger();
    }

    /**
     * Loads the provider list from the extension points.
     */
    private void loadProviders() {

        Map<String, List<IConfigurationElement>> providerExtensions =
                loadProviderExtensionRules();

        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                "provider"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();

        for (IConfigurationElement config : configs) {
            if ("provider".equals(config.getName())) { //$NON-NLS-1$
                String id = config.getAttribute("id"); //$NON-NLS-1$
                String name = config.getAttribute("name"); //$NON-NLS-1$
                String markerType = config.getAttribute("markerType"); //$NON-NLS-1$
                if (markerType == null || markerType.length() == 0) {
                    markerType = XpdConsts.VALIDATION_MARKER_TYPE;
                }
                IScopeProvider scope = null;
                try {
                    Object scopeObject =
                            config.createExecutableExtension("scopeProvider"); //$NON-NLS-1$
                    if (scopeObject instanceof IScopeProvider) {
                        scope = (IScopeProvider) scopeObject;
                    }
                } catch (CoreException e) {
                    getLogger()
                            .error(e,
                                    String.format(Messages.ValidationManager_scopeProviderNotFound_message,
                                            config.getContributor().getName(),
                                            id));
                }
                if (scope == null) {
                    scope = new DefaultScopeProvider();
                }
                Provider provider = new Provider(id, name, scope, markerType);
                IConfigurationElement[] rules = config.getChildren("rule"); //$NON-NLS-1$

                // add additional rules from the validation provider extensions

                List<IConfigurationElement> extendedRules =
                        providerExtensions.get(id);
                if (extendedRules != null) {
                    IConfigurationElement[] newRules =
                            new IConfigurationElement[rules.length
                                    + extendedRules.size()];
                    System.arraycopy(rules, 0, newRules, 0, rules.length);
                    for (int i = rules.length, j = 0; i < newRules.length; i++, j++) {
                        newRules[i] = extendedRules.get(j);
                    }
                    rules = newRules;
                }

                for (IConfigurationElement rule : rules) {
                    try {
                        Object ruleObject =
                                rule.createExecutableExtension("class"); //$NON-NLS-1$

                        if (ruleObject instanceof IValidationRule) {

                            boolean validateOnSaveOnly = false;
                            String validateOnSaveOnlyAttribute =
                                    rule.getAttribute(IIssue.VALIDATE_ON_SAVE_ONLY);

                            if (validateOnSaveOnlyAttribute != null
                                    && validateOnSaveOnlyAttribute
                                            .equalsIgnoreCase(Boolean.TRUE
                                                    .toString())) {
                                validateOnSaveOnly = true;
                            }

                            ValidationRule validationRule =
                                    new ValidationRule(
                                            (IValidationRule) ruleObject,
                                            validateOnSaveOnly);
                            provider.addRule(validationRule);
                        } else {
                            getLogger()
                                    .error(String.format(Messages.ValidationManager_ruleClassNotImplementingIValidationRule_message,
                                            ruleObject.getClass().getName()));
                        }
                    } catch (CoreException e) {
                        getLogger()
                                .error(e,
                                        String.format(Messages.ValidationManager_ruleClassNotFound_message,
                                                config.getContributor()
                                                        .getName(),
                                                id));
                    }
                }
                engine.addProvider(provider);
            } else if ("issue".equals(config.getName())) { //$NON-NLS-1$
                IssueInfo info = createIssueInfo(config);
                if (info != null) {
                    engine.addIssueInfo(info);
                }
            } else if ("preferenceGroup".equals(config.getName())) { //$NON-NLS-1$
                engine.addPreferenceGroup(new PreferenceGroup(config));
            }
        }

        // Load workspace validators
        extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                "workspaceResourceValidationProvider"); //$NON-NLS-1$
        configs = extensionPoint.getConfigurationElements();

        for (IConfigurationElement config : configs) {
            if ("provider".equals(config.getName())) { //$NON-NLS-1$
                String id = config.getAttribute("id"); //$NON-NLS-1$

                if (id != null) {
                    IConfigurationElement[] children = config.getChildren();
                    List<WorkspaceResourceValidatorExt> exts =
                            new ArrayList<WorkspaceResourceValidatorExt>();
                    for (IConfigurationElement child : children) {
                        if ("validator".equals(child.getName())) { //$NON-NLS-1$
                            exts.add(new WorkspaceResourceValidatorExt(child));
                        }
                    }
                    engine.addWorkspaceResourceValidationProvider(id, exts);
                }
            } else if ("issues".equals(config.getName())) { //$NON-NLS-1$
                IConfigurationElement[] children = config.getChildren();

                for (IConfigurationElement child : children) {
                    if ("issue".equals(child.getName())) { //$NON-NLS-1$
                        IssueInfo info = createIssueInfo(child);
                        if (info != null) {
                            engine.addIssueInfo(info);
                        }
                    }
                }
            }
        }
    }

    /**
     * Create <code>IssueInfo</code> from the given extension configuration
     * element.
     * 
     * @param config
     * @return
     */
    private IssueInfo createIssueInfo(IConfigurationElement config) {
        IssueInfo info = null;

        if (config != null) {
            String issueId = config.getAttribute("id"); //$NON-NLS-1$
            String issueMessage = config.getAttribute("message"); //$NON-NLS-1$
            String preferenceGroupid = config.getAttribute("preferenceGroupId"); //$NON-NLS-1$
            String preferenceDesc =
                    config.getAttribute("preferenceDescription"); //$NON-NLS-1$
            int issueSeverity = parseSeverity(config.getAttribute("severity")); //$NON-NLS-1$
            info =
                    new IssueInfo(issueId, issueMessage, issueSeverity,
                            preferenceGroupid, preferenceDesc);

            addIssueResolutions(issueId, config);
        }
        return info;
    }

    /**
     * Add any reusableResolutions that are referenced from the give Issue
     * element's resolution children to the resolutionsMap.
     * <p>
     * This allows resolutions to be contributed and then re-used from any other
     * validationProvider/issue. i.e. Each issue can now optionally list one or
     * more resolutions RATHER than having to re-contribute a resolution for
     * every issue it may apply to!
     * 
     * @param issueId
     * @param config
     * 
     * @since v3.3.0
     */
    private void addIssueResolutions(String issueId,
            IConfigurationElement config) {

        // Resolutions will not be loaded in headless mode so no need to run
        // this method
        if (XpdResourcesPlugin.isInHeadlessMode()) {
            return;
        }

        IConfigurationElement[] children = config.getChildren();

        for (IConfigurationElement child : children) {
            if ("resolution".equals(child.getName())) { //$NON-NLS-1$

                String reusableResolutionId = child.getAttribute("id"); //$NON-NLS-1$

                if (reusableResolutionId != null
                        && reusableResolutionId.length() > 0) {

                    /* Load the referenced resulableResolution. */
                    ReusableResolution reusableResolution =
                            reusableResolutions.get(reusableResolutionId);

                    if (reusableResolution != null) {
                        /*
                         * Create the marker resolution
                         */
                        boolean applyToMultiple = false;
                        String applyToMultipleValue =
                                child.getAttribute("canApplyToMultiple"); //$NON-NLS-1$
                        if (applyToMultipleValue != null) {
                            applyToMultiple =
                                    Boolean.parseBoolean(applyToMultipleValue);
                        }

                        GenericMarkerResolution markerResolution =
                                new GenericMarkerResolution(issueId,
                                        reusableResolution.label,
                                        reusableResolution.resolution,
                                        applyToMultiple);

                        markerResolution
                                .setDescription(reusableResolution.description);

                        if (reusableResolution.imageRegistryPath != null
                                && reusableResolution.imageRegistryPath
                                        .length() > 0) {
                            markerResolution
                                    .setImageKey(reusableResolution.imageRegistryPath);
                        }

                        /* Add it to the issueId->resolution cache */
                        addIssueResolutionMapEntry(issueId, markerResolution);

                    } else {
                        getLogger()
                                .error(String.format(Messages.ValidationManager_BadResolutionRefInIssue_description,
                                        issueId,
                                        reusableResolutionId,
                                        config.getContributor().getName()));
                    }
                }
            }
        }

        return;
    }

    /**
     * Loads all provider extensions rules as their configuration elements
     * 
     * @return
     */
    private Map<String, List<IConfigurationElement>> loadProviderExtensionRules() {
        Map<String, List<IConfigurationElement>> result =
                new HashMap<String, List<IConfigurationElement>>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                "providerExtension"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();
        for (IConfigurationElement config : configs) {
            if ("providerExtension".equals(config.getName())) { //$NON-NLS-1$
                String parentId = config.getAttribute("providerId"); //$NON-NLS-1$
                IConfigurationElement[] rules = config.getChildren("rule"); //$NON-NLS-1$
                List<IConfigurationElement> rulesList = result.get(parentId);
                if (rulesList == null) {
                    rulesList = new ArrayList<IConfigurationElement>();
                    result.put(parentId, rulesList);
                }
                for (IConfigurationElement configurationElement : rules) {
                    rulesList.add(configurationElement);
                }
            }
        }
        return result;
    }

    /**
     * Loads the pre-processor extensions.
     */
    private void loadPreProcessors() {
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                "preProcessor"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();
        for (IConfigurationElement config : configs) {
            try {
                Object processorObject =
                        config.createExecutableExtension("factory"); //$NON-NLS-1$
                if (processorObject instanceof IPreProcessorFactory) {
                    IPreProcessorFactory processor =
                            (IPreProcessorFactory) processorObject;
                    List<String> dependencyList = new ArrayList<String>();
                    Class<? extends IPreProcessor> clss =
                            processor.getToolClass();
                    PreProcessor preProcessor =
                            new PreProcessor(clss, processor, dependencyList);
                    preProcessors.put(clss, preProcessor);
                }
            } catch (CoreException e) {
                getLogger()
                        .error(e,
                                String.format(Messages.ValidationManager_preProcessorClassNotFound_message,
                                        config.getContributor().getName()));
            }
        }
    }

    /**
     * Load the reusable resolutions from the new new extension point.
     * <p>
     * This allows resolutions to be contributed and then re-used from any other
     * validationProvider/issue. i.e. Each issue can now optionally list one or
     * more resolutions RATHER than having to re-contribute a resolution for
     * every issue it may apply to!
     * 
     * @since v3.3.0
     */
    private void loadReusableResolutions() {
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                "reusableResolutions"); //$NON-NLS-1$

        if (extensionPoint != null) {
            IConfigurationElement[] configs =
                    extensionPoint.getConfigurationElements();
            if (configs != null) {
                for (IConfigurationElement config : configs) {
                    if ("resolution".equals(config.getName())) { //$NON-NLS-1$
                        /*
                         * Create a ReusableResoltion object for each config
                         * element.
                         */
                        try {
                            ReusableResolution reusableResolution =
                                    new ReusableResolution(config);

                            /* Add add to our set */
                            if (!reusableResolutions
                                    .containsKey(reusableResolution.id)) {
                                reusableResolutions.put(reusableResolution.id,
                                        reusableResolution);

                            } else {
                                getLogger()
                                        .error(String.format(Messages.ValidationManager_DuplicateResolution_description,
                                                config.getAttribute("id"), //$NON-NLS-1$
                                                config.getContributor()
                                                        .getName()));
                            }

                        } catch (Exception e) {
                            getLogger()
                                    .error(e,
                                            String.format(Messages.ValidationManager_BadReusableResolutionContribution_description,
                                                    config.getAttribute("id"), //$NON-NLS-1$
                                                    config.getContributor()
                                                            .getName()));
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * Loads generic type resolutions from the extension points.
     */
    private void loadResolutions() {
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                "resolution"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();
        for (IConfigurationElement config : configs) {
            String id = config.getAttribute("id"); //$NON-NLS-1$
            String label = config.getAttribute("label"); //$NON-NLS-1$
            String description = config.getAttribute("description"); //$NON-NLS-1$
            String imagePath = config.getAttribute("image"); //$NON-NLS-1$
            /*
             * Since 3.1 this will default to false as Ganymede has done away
             * with "Find Similar" in the quick fix and instead lists all
             * similar problems by default.
             */
            boolean applyToMultiple = false;
            String applyToMultipleValue =
                    config.getAttribute("canApplyToMultiple"); //$NON-NLS-1$

            if (applyToMultipleValue != null) {
                applyToMultiple = Boolean.parseBoolean(applyToMultipleValue);
            }

            try {
                Object resolutionObject =
                        config.createExecutableExtension("class"); //$NON-NLS-1$
                if (resolutionObject instanceof IResolution) {
                    IResolution resolution = (IResolution) resolutionObject;
                    GenericMarkerResolution generic =
                            new GenericMarkerResolution(id, label, resolution,
                                    applyToMultiple);

                    if (description != null && description.length() > 0) {
                        generic.setDescription(description);
                    }

                    String regImagePath =
                            addContributedImageToRegistry(config, imagePath);

                    if (regImagePath != null && regImagePath.length() > 0) {
                        generic.setImageKey(regImagePath);
                    }

                    addIssueResolutionMapEntry(id, generic);

                } else {
                    getLogger()
                            .error(String.format(Messages.ValidationManager_resolutionNotImplementingIResolution_message,
                                    config.getContributor().getName(),
                                    id));
                }
            } catch (CoreException e) {
                getLogger()
                        .error(e,
                                String.format(Messages.ValidationManager_invalidResolution_message,
                                        config.getContributor().getName(),
                                        id));
            }
        }
    }

    /**
     * Add the given resolution to the cache of resolutions for the given issue.
     * 
     * @param issueId
     * @param markerResolution
     */
    private void addIssueResolutionMapEntry(String issueId,
            GenericMarkerResolution markerResolution) {
        List<IMarkerResolution> resolutions = resolutionsMap.get(issueId);

        if (resolutions == null) {
            /*
             * If there no resolution list yet for the issue then add one to
             * cache
             */
            resolutions = new ArrayList<IMarkerResolution>();
            resolutionsMap.put(issueId, resolutions);
        }

        resolutions.add(markerResolution);

        return;
    }

    /**
     * Load the contributed image and add it to the validation plug-in's image
     * resitry
     * 
     * @param config
     * @param imagePath
     * 
     * @return the image registry key for the image
     */
    private static String addContributedImageToRegistry(
            IConfigurationElement config, String imagePath) {
        String regImagePath = null;
        if (imagePath != null && imagePath.length() > 0) {
            String contributingPluginId =
                    config.getDeclaringExtension().getNamespaceIdentifier();

            // Create a UNIQUE id for the image based on plugin id
            // as well as imagepath (else might override some of our
            // own)!!
            regImagePath = contributingPluginId + "/" + imagePath; //$NON-NLS-1$

            // Check if we already have this image cached.
            if (ValidationActivator.getDefault().getImageRegistry()
                    .getDescriptor(regImagePath) == null) {
                // Nope! So lets add it to the registry.
                ImageDescriptor descriptor =
                        ValidationActivator
                                .imageDescriptorFromPlugin(contributingPluginId,
                                        imagePath);

                if (descriptor != null) {
                    ValidationActivator.getDefault().getImageRegistry()
                            .put(regImagePath, descriptor);
                }
            }
        }
        return regImagePath;
    }

    /**
     * Convert the <i>severity</i> to an {@link IMarker} severity value.
     * 
     * @param severity
     *            The severity string.
     * @return The corresponding <code>IMarker</code> severity integer.
     */
    private int parseSeverity(String severity) {
        int level = IMarker.SEVERITY_ERROR;
        if ("WARNING".equals(severity)) { //$NON-NLS-1$
            level = IMarker.SEVERITY_WARNING;
        } else if ("INFO".equals(severity)) { //$NON-NLS-1$
            level = IMarker.SEVERITY_INFO;
        }
        return level;
    }

    /**
     * Get the associated {@link PreProcessor} of the given class.
     * 
     * @param clss
     *            The pre-processor class.
     * @return The associated pre-processor.
     */
    public PreProcessor getPreProcessor(Class<? extends IPreProcessor> clss) {
        return preProcessors.get(clss);
    }

    /**
     * Get the validation engine.
     * 
     * @return The validation engine.
     */
    public ValidationEngine getValidationEngine() {
        return engine;
    }

    /**
     * Get the file filters.
     * 
     * @return The complete collection of file filters.
     */
    public IFileFilter getFileFilter() {
        return new CompositeFileFilter(fileFilters.values());
    }

    /**
     * Get file filter defined for the given destination.
     * 
     * @param destination
     *            The destination.
     * @return The file filter for the destination or null.
     */
    public IFileFilter getFileFilter(Destination destination) {
        return fileFilters.get(destination);
    }

    /**
     * Get the issue resolution with the given id.
     * 
     * @param id
     *            The issue ID.
     * @return An array of resolutions.
     */
    public IMarkerResolution[] getResolutions(String id) {
        IMarkerResolution[] array = new IMarkerResolution[0];
        List<IMarkerResolution> resolutions = resolutionsMap.get(id);
        if (resolutions != null) {
            array = new IMarkerResolution[resolutions.size()];
            resolutions.toArray(array);
        }
        return array;
    }

    /**
     * Loads the destination environment list.
     */
    private synchronized void loadDestinations() {
        if (destinations == null) {
            destinations = new HashMap<String, Destination>();
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                    "destinations"); //$NON-NLS-1$
            IConfigurationElement[] configs =
                    extensionPoint.getConfigurationElements();
            for (IConfigurationElement config : configs) {
                if ("destination".equals(config.getName())) { //$NON-NLS-1$
                    loadDestination(config);
                } else if ("workspaceResourceDestination".equals(config.getName())) { //$NON-NLS-1$
                    loadWorkspaceResourceDestination(config);
                }
            }
            loadDestinationExtensions();
        }
    }

    /**
     * Adds providers to destinations (contributed by 'destinationExtension').
     */
    private void loadDestinationExtensions() {
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                "destinationExtension"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();
        for (IConfigurationElement config : configs) {
            if ("destinationExtension".equals(config.getName())) { //$NON-NLS-1$
                String destId = config.getAttribute("destinationId"); //$NON-NLS-1$
                if (destId != null && destinations.containsKey(destId)) {
                    Destination currentDestination = destinations.get(destId);
                    Collection<String> validationProviders =
                            new ArrayList<String>(
                                    currentDestination.getValidationProviders());
                    IConfigurationElement[] providers =
                            config.getChildren("provider"); //$NON-NLS-1$
                    for (IConfigurationElement provider : providers) {
                        String providerId = provider.getAttribute("providerId"); //$NON-NLS-1$
                        validationProviders.add(providerId);
                    }

                    Destination replacementDestination =
                            new Destination(currentDestination.getId(),
                                    currentDestination.getName(),
                                    currentDestination.getVersion(),
                                    currentDestination.isSelectable(),
                                    validationProviders);
                    destinations.put(destId, replacementDestination);

                    /*
                     * Must reset the destination in the file filter for it to
                     * work.
                     */
                    IFileFilter iFileFilter =
                            fileFilters.get(currentDestination);
                    if (iFileFilter != null) {
                        fileFilters.remove(currentDestination);
                        fileFilters.put(replacementDestination, iFileFilter);
                    }

                } else {
                    getLogger()
                            .warn(String.format("The 'com.tibco.xpd.validation.destinationExtension' " + //$NON-NLS-1$
                                    "extension exstend not existing destination %s.", //$NON-NLS-1$
                                    destId));
                }
            }
        }

    }

    /**
     * Load the destination extension from the configuration element.
     * 
     * @param config
     */
    private void loadDestination(IConfigurationElement config) {
        String id = config.getAttribute("id"); //$NON-NLS-1$
        String name = config.getAttribute("name"); //$NON-NLS-1$
        String version = config.getAttribute("version"); //$NON-NLS-1$
        boolean selectable =
                Boolean.parseBoolean(config.getAttribute("selectable")); //$NON-NLS-1$
        Collection<String> validationProviders = new ArrayList<String>();
        IConfigurationElement[] providers =
                config.getChildren("validationProvider"); //$NON-NLS-1$
        for (IConfigurationElement provider : providers) {
            String providerId = provider.getAttribute("id"); //$NON-NLS-1$
            validationProviders.add(providerId);
        }
        IFileFilter fileFilter = null;
        IConfigurationElement[] filters = config.getChildren("filter"); //$NON-NLS-1$
        for (IConfigurationElement filter : filters) {
            try {
                Object executable = filter.createExecutableExtension("class"); //$NON-NLS-1$
                if (executable instanceof IFileFilter) {
                    fileFilter = (IFileFilter) executable;
                    break;
                }
            } catch (CoreException e) {
                // Ignore, the filter is optional.
            }
        }
        try {
            Destination destination =
                    new Destination(id, name, version, selectable,
                            validationProviders);
            destinations.put(id, destination);
            if (fileFilter != null) {
                fileFilters.put(destination, fileFilter);
            }
        } catch (Exception ex) {
            getLogger().error(ex,
                    "Can't load destination environment definitions"); //$NON-NLS-1$
        }
    }

    /**
     * Load the workspace resource destiation from the configuration element.
     * 
     * @param config
     */
    private void loadWorkspaceResourceDestination(IConfigurationElement config) {
        String id = config.getAttribute("id"); //$NON-NLS-1$
        String name = config.getAttribute("name"); //$NON-NLS-1$
        String version = config.getAttribute("version"); //$NON-NLS-1$
        String assetId = config.getAttribute("assetId"); //$NON-NLS-1$
        Collection<String> validationProviders = new ArrayList<String>();
        IConfigurationElement[] providers =
                config.getChildren("validationProvider"); //$NON-NLS-1$
        for (IConfigurationElement provider : providers) {
            String providerId = provider.getAttribute("id"); //$NON-NLS-1$
            validationProviders.add(providerId);
        }

        destinations.put(id, new WorkspaceResourceValidationDestination(id,
                name, version, assetId, validationProviders));
    }

    /**
     * Get all destinations.
     * 
     * @return A collection of available destination environments.
     */
    public Collection<Destination> getDestinations() {
        return destinations.values();
    }

    /**
     * Get the destination with the given id.
     * 
     * @param id
     *            The destination environment ID.
     * @return The associated Destination object.
     */
    public Destination getDestination(String id) {
        return destinations.get(id);
    }

    /**
     * Add a validation listener.
     * 
     * @param listener
     *            The listener to add.
     */
    public void addValidationListener(IValidationListener listener) {
        engine.addValidationListener(listener);
    }

    /**
     * Remove a validation listener.
     * 
     * @param listener
     *            The listener to remove.
     */
    public void removeValidationListener(IValidationListener listener) {
        engine.removeValidationListener(listener);
    }

    /**
     * Construct and return validation marker additional information map.
     * 
     * @param marker
     *            The marker to get additional information for.
     * @return additional information, or empty map if not found.
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getAdditionalInfoMap(IMarker marker) {
        if (marker.exists()) {
            try {
                String additionalInfo =
                        String.valueOf(marker
                                .getAttribute(XpdConsts.VALIDATION_MARKER_ADDITIONAL_INFO));
                if (additionalInfo != null && additionalInfo.length() > 0) {
                    ByteArrayInputStream stream =
                            new ByteArrayInputStream(additionalInfo.getBytes());
                    try {
                        Properties props = new Properties();
                        props.load(stream);
                        Map<String, String> map = new HashMap<String, String>();
                        map.putAll((Map) props);
                        return map;
                    } catch (IOException e) {

                    }
                }
            } catch (CoreException e) {
                getLogger().error(e);
            }
        }
        return Collections.emptyMap();
    }

    /**
     * Implementation of the <code>IPreferenceGroup</code> interface.
     * 
     * @author njpatel
     */
    private class PreferenceGroup implements IPreferenceGroup {

        private final IConfigurationElement element;

        /**
         * Constructor.
         * 
         * @param element
         *            configuration element of this group.
         */
        public PreferenceGroup(IConfigurationElement element) {
            this.element = element;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.validation.provider.IPreferenceGroup#getId()
         */
        public String getId() {
            return getAttribute("id", element); //$NON-NLS-1$
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.validation.provider.IPreferenceGroup#getIssues()
         */
        public IssueInfo[] getIssues() {
            List<IssueInfo> issues = new ArrayList<IssueInfo>();

            IssueInfo[] allIssues = getValidationEngine().getAllIssues();
            String grpId = getId();

            for (IssueInfo issue : allIssues) {
                if (issue.getPreferenceGroupId() != null
                        && issue.getPreferenceGroupId().equals(grpId)) {
                    issues.add(issue);
                }
            }

            return issues.toArray(new IssueInfo[issues.size()]);
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.validation.provider.IPreferenceGroup#getName()
         */
        public String getName() {
            return getAttribute("name", element); //$NON-NLS-1$
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.validation.provider.IPreferenceGroup#getPreferenceId()
         */
        public String getPreferenceId() {
            return getAttribute("preferenceId", element); //$NON-NLS-1$
        }

        /**
         * Get the attribute value of the given name.
         * 
         * @param name
         *            attribute
         * @return value of the attribute. Empty string if no value found.
         */
        private String getAttribute(String name, IConfigurationElement element) {
            String value = null;

            if (element != null && name != null) {
                value = element.getAttribute(name);
            }

            return value != null ? value : ""; //$NON-NLS-1$
        }

    }

    /**
     * ReusableResolution
     * 
     * 
     * @author aallway
     * @since 3.3 (12 Nov 2009)
     */
    private static class ReusableResolution {
        String id;

        String label;

        String description;

        String imageRegistryPath;

        IResolution resolution;

        public ReusableResolution(IConfigurationElement element)
                throws Exception {

            id = element.getAttribute("id"); //$NON-NLS-1$
            if (id == null || id.length() == 0) {
                throw new IllegalArgumentException(
                        "Invalid reusableResolution id attribute"); //$NON-NLS-1$
            }

            label = element.getAttribute("label"); //$NON-NLS-1$
            if (label == null || label.length() == 0) {
                throw new IllegalArgumentException(
                        "Invalid reusableResolution label attribute"); //$NON-NLS-1$
            }

            Object clazz = null;
            try {
                clazz = element.createExecutableExtension("class"); //$NON-NLS-1$
            } catch (CoreException e) {
                e.printStackTrace();
            }

            if (!(clazz instanceof IResolution)) {
                throw new IllegalArgumentException(
                        "Invalid reusableResolution class attribute"); //$NON-NLS-1$
            }
            resolution = (IResolution) clazz;

            description = element.getAttribute("description"); //$NON-NLS-1$
            if (description == null) {
                description = label;
            }

            imageRegistryPath =
                    addContributedImageToRegistry(element,
                            element.getAttribute("image")); //$NON-NLS-1$
        }
    }
}
