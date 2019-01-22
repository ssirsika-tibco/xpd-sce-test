/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.destinations.preferences.DestinationComponentSetting;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.preferences.VersionComparator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.validation.ValidationActivator;

/**
 * Utility class for accessing the global destination extension point data.
 * 
 * @author nwilson
 * 
 */
public class GlobalDestinationUtil {

    /**
     * Private constructor.
     */
    private GlobalDestinationUtil() {
    }

    public static String getGlobalDestinationName(String globalId) {
        String name = ""; //$NON-NLS-1$
        if (globalId != null) {
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                    "globalDestinationEnvironment"); //$NON-NLS-1$
            for (IConfigurationElement element : extensionPoint
                    .getConfigurationElements()) {
                String elementName = element.getName();
                if ("globalDestination".equals(elementName)) { //$NON-NLS-1$
                    String id = element.getAttribute("id"); //$NON-NLS-1$
                    if (globalId.equals(id)) {
                        name = element.getAttribute("name"); //$NON-NLS-1$
                    }
                }
            }
        }
        return name;
    }

    public static String[] getAllGlobalDestinations() {
        List<String> globalIds = new ArrayList<String>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                "globalDestinationEnvironment"); //$NON-NLS-1$
        for (IConfigurationElement element : extensionPoint
                .getConfigurationElements()) {
            String elementName = element.getName();
            if ("globalDestination".equals(elementName)) { //$NON-NLS-1$
                String id = element.getAttribute("id"); //$NON-NLS-1$
                globalIds.add(id);
            }
        }
        return globalIds.toArray(new String[globalIds.size()]);
    }

    /**
     * @param project
     * @param wantNames
     *            true if want names rather than id's of destination.
     * 
     * @return The id's of the global destinations that are enabled in the given
     *         project.
     */
    public static Set<String> getEnabledGlobalDestinations(IProject project,
            boolean wantNames) {
        if (wantNames) {
            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            Set<String> destinationsForProject = new HashSet<String>();
            ProjectDetails projectDetails = projectConfig.getProjectDetails();
            if (projectDetails != null) {
                Destinations globalDestinations =
                        projectDetails.getGlobalDestinations();
                if (globalDestinations != null) {
                    List<com.tibco.xpd.resources.projectconfig.Destination> destinationList =
                            globalDestinations.getDestination();
                    for (com.tibco.xpd.resources.projectconfig.Destination destination : destinationList) {
                        String type = destination.getType();
                        destinationsForProject.add(type);
                    }
                }
            }
            return destinationsForProject;

        } else {
            Set<String> enabledGlobalDestinations = new HashSet<String>();

            String[] allGlobalDestinations = getAllGlobalDestinations();
            if (allGlobalDestinations != null) {
                for (String globalDestinationId : allGlobalDestinations) {
                    if (isGlobalDestinationEnabled(project, globalDestinationId)) {
                        if (wantNames) {
                            globalDestinationId =
                                    getGlobalDestinationName(globalDestinationId);
                        }
                        enabledGlobalDestinations.add(globalDestinationId);
                    }
                }
            }
            return enabledGlobalDestinations;
        }
    }

    /**
     * @param globalDestName
     * 
     * @return array of validation destination id's that are included within the
     *         given global destination.
     */
    public static String[] getselectedvalidationdestinations(
            String globalDestName) {
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();

        return preferences.getSelectedValidationDestinations(globalDestName);
    }

    /**
     * Return list of all enabled validation destination id's.
     * <p>
     * i.e. all validation destiantions that are enabled via destination
     * components in the project's enabled global destinations
     * 
     * @param project
     * @return
     */
    public static String[] getSelectedValidationDestinations(IProject project) {
        Set<String> allValidationDests = new HashSet<String>();

        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();

        Set<String> enabledGlobalDests =
                getEnabledGlobalDestinations(project, true);
        for (String globalDestName : enabledGlobalDests) {
            String[] validationDests =
                    preferences
                            .getSelectedValidationDestinations(globalDestName);

            if (validationDests != null) {
                for (String vDest : validationDests) {
                    allValidationDests.add(vDest);
                }
            }
        }

        return allValidationDests
                .toArray(new String[allValidationDests.size()]);
    }

    public static String[] getAllComponents(String globalId) {
        List<ComponentNameAndId> componentNamesAndIds =
                new ArrayList<ComponentNameAndId>();
        if (globalId != null) {
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                    "globalDestinationEnvironment"); //$NON-NLS-1$
            for (IConfigurationElement element : extensionPoint
                    .getConfigurationElements()) {
                String elementName = element.getName();
                if ("globalDestinationComponent".equals(elementName)) { //$NON-NLS-1$
                    String destinationId =
                            element.getAttribute("globalDestinationId"); //$NON-NLS-1$
                    if (globalId.equals(destinationId)) {
                        String id = element.getAttribute("id"); //$NON-NLS-1$
                        String name = element.getAttribute("name"); //$NON-NLS-1$
                        componentNamesAndIds.add(new ComponentNameAndId(name,
                                id));
                    }
                }
            }
        }

        Collections.sort(componentNamesAndIds,
                new Comparator<ComponentNameAndId>() {
                    @Override
                    public int compare(ComponentNameAndId o1,
                            ComponentNameAndId o2) {
                        return o1.name.compareTo(o2.name);
                    }
                });

        List<String> componentIds =
                new ArrayList<String>(componentNamesAndIds.size());

        for (ComponentNameAndId c : componentNamesAndIds) {
            componentIds.add(c.id);
        }

        return componentIds.toArray(new String[componentNamesAndIds.size()]);
    }

    /**
     * Returns all the components irrespective of whether they are visible or
     * not. An existence of a certain component(which may be hidden) is used to
     * trigger builders.
     * 
     * @param globalId
     * @return array of strings that contains all component ids irrespective of
     *         whether they are visible or not.
     */
    public static String[] getAllComponentsWithoutHidden(String globalId) {
        List<ComponentNameAndId> componentNamesAndIds =
                new ArrayList<ComponentNameAndId>();
        if (globalId != null) {
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                    "globalDestinationEnvironment"); //$NON-NLS-1$
            for (IConfigurationElement element : extensionPoint
                    .getConfigurationElements()) {
                String elementName = element.getName();
                if ("globalDestinationComponent".equals(elementName)) { //$NON-NLS-1$
                    String destinationId =
                            element.getAttribute("globalDestinationId"); //$NON-NLS-1$
                    if (globalId.equals(destinationId)) {
                        String visible = element.getAttribute("visible"); //$NON-NLS-1$
                        if (visible == null
                                || visible.equals(Boolean.TRUE.toString())) {
                            String id = element.getAttribute("id"); //$NON-NLS-1$
                            String name = element.getAttribute("name"); //$NON-NLS-1$
                            componentNamesAndIds.add(new ComponentNameAndId(
                                    name, id));
                        }
                    }
                }
            }
        }

        Collections.sort(componentNamesAndIds,
                new Comparator<ComponentNameAndId>() {
                    @Override
                    public int compare(ComponentNameAndId o1,
                            ComponentNameAndId o2) {
                        return o1.name.compareTo(o2.name);
                    }
                });

        List<String> componentIds =
                new ArrayList<String>(componentNamesAndIds.size());

        for (ComponentNameAndId c : componentNamesAndIds) {
            componentIds.add(c.id);
        }

        return componentIds.toArray(new String[componentIds.size()]);
    }

    public static String[] getAllComponentVersions(String globalComponentId) {
        List<String> versionIds = new ArrayList<String>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                "globalDestinationEnvironment"); //$NON-NLS-1$
        for (IConfigurationElement element : extensionPoint
                .getConfigurationElements()) {
            String elementName = element.getName();
            if ("globalDestinationComponentVersion".equals(elementName)) { //$NON-NLS-1$
                String destinationId =
                        element.getAttribute("globalDestinationComponentId"); //$NON-NLS-1$
                if (globalComponentId.equals(destinationId)) {
                    String id = element.getAttribute("validationDestinationId"); //$NON-NLS-1$
                    versionIds.add(id);
                }
            }
        }
        return versionIds.toArray(new String[versionIds.size()]);
    }

    public static String getComponentName(String globalComponentId) {
        String name = ""; //$NON-NLS-1$
        if (globalComponentId != null) {
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                    "globalDestinationEnvironment"); //$NON-NLS-1$
            for (IConfigurationElement element : extensionPoint
                    .getConfigurationElements()) {
                String elementName = element.getName();
                if ("globalDestinationComponent".equals(elementName)) { //$NON-NLS-1$
                    String id = element.getAttribute("id"); //$NON-NLS-1$
                    if (globalComponentId.equals(id)) {
                        name = element.getAttribute("name"); //$NON-NLS-1$
                    }
                }
            }
        }
        return name;
    }

    public static String getValidationDestinationName(String validationId) {
        String name = ""; //$NON-NLS-1$
        if (validationId != null) {
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(ValidationActivator.PLUGIN_ID,
                                    "destinations"); //$NON-NLS-1$
            for (IConfigurationElement element : extensionPoint
                    .getConfigurationElements()) {
                if ("destination".equals(element.getName())) { //$NON-NLS-1$
                    String id = element.getAttribute("id"); //$NON-NLS-1$
                    if (validationId.equals(id)) {
                        name = element.getAttribute("version"); //$NON-NLS-1$
                    }
                }
            }
        }
        return name;
    }

    public static String getDefaultValdationDestinationId(
            String globalComponentId) {
        String defaultVersionId = null;

        /*
         * Sid: Use the extension point contribution defined default dest
         * component version not just explicitly the last version in a sorted
         * list!!
         */
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();

        DestinationComponentSetting defaultDestinationComponentVersion =
                preferences
                        .getDefaultDestinationComponentVersion(globalComponentId);

        if (defaultDestinationComponentVersion != null) {
            defaultVersionId =
                    defaultDestinationComponentVersion
                            .getValidationDestinationId();
        }

        /*
         * Fall back on last in sorted list only if there is no
         * defaultGlobalDestinationComponent contribution
         */
        if (defaultVersionId == null || defaultVersionId.length() == 0) {
            String[] all = getAllComponentVersions(globalComponentId);
            if (all.length > 0) {
                Arrays.sort(all, new VersionComparator());
                defaultVersionId = all[all.length - 1];
            }
        }
        return defaultVersionId;
    }

    public static boolean isDisabledComponentVersion(String validationId) {
        boolean isDisabled = false;
        if (validationId != null) {
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                    "globalDestinationEnvironment"); //$NON-NLS-1$
            for (IConfigurationElement element : extensionPoint
                    .getConfigurationElements()) {
                String elementName = element.getName();
                if ("globalDestinationComponentVersion".equals(elementName)) { //$NON-NLS-1$
                    String id = element.getAttribute("validationDestinationId"); //$NON-NLS-1$
                    if (validationId.equals(id)) {
                        isDisabled =
                                Boolean.parseBoolean(element
                                        .getAttribute("disabled")); //$NON-NLS-1$
                    }
                }
            }
        }
        return isDisabled;
    }

    /**
     * @param project
     *            - The Project to check
     * @param validationDestination
     *            - the destination id to check
     * @return true if the destination is enabled for the project.
     */
    public static boolean isValidationDestinationEnabled(IProject project,
            String validationDestination) {
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        Set<String> globalNames =
                preferences
                        .getGlobalDestinationsForValidationDestination(validationDestination);
        if (!globalNames.isEmpty()) {
            if (project != null) {
                ProjectDetails projectDetails =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project).getProjectDetails();
                if (projectDetails != null) {
                    Destinations destinations =
                            projectDetails.getGlobalDestinations();
                    if (destinations != null
                            && !destinations.getDestination().isEmpty()) {
                        for (Destination dest : destinations.getDestination()) {
                            String destType = dest.getType();
                            if (globalNames.contains(destType)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifies whether a given project has the provided destination enabled.
     * Destinations can be created per workspace. This method has an additional
     * check whether the given destination exists in the workspace and is
     * enabled.
     * 
     * @param project
     * @param globalDestinationId
     * @return
     */

    public static boolean isGlobalDestinationEnabled(IProject project,
            String globalDestinationId) {
        boolean enabled = false;

        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (projectConfig != null) {
            Set<String> destinationsForProject = new HashSet<String>();
            ProjectDetails projectDetails = projectConfig.getProjectDetails();
            if (projectDetails != null) {
                Destinations globalDestinations =
                        projectDetails.getGlobalDestinations();
                if (globalDestinations != null) {
                    List<com.tibco.xpd.resources.projectconfig.Destination> destinationList =
                            globalDestinations.getDestination();
                    for (com.tibco.xpd.resources.projectconfig.Destination destination : destinationList) {
                        String type = destination.getType();
                        destinationsForProject.add(type);
                    }
                }
            }
            DestinationPreferences preferences =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();
            for (String name : destinationsForProject) {
                String id = preferences.getGlobalDestinationId(name);
                if (globalDestinationId.equals(id)) {
                    enabled = true;
                    break;
                }
            }
        }

        return enabled;
    }

    private static class ComponentNameAndId {
        String name;

        String id;

        public ComponentNameAndId(String name, String id) {
            super();
            this.id = id;
            this.name = name;
        }

    }
}
