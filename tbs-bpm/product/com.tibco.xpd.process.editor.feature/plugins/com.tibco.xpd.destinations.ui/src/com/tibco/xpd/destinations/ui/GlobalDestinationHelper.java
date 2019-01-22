/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.destinations.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.ValidationException;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * The GlobalDestinationHelper class provides a public API for handling global
 * destinations.
 * 
 * @see GlobalDestinationUtil
 * 
 * @author aallway, nwilson
 */
public class GlobalDestinationHelper {

    // FIXME JA: This class should be moved to core destination plug-in.
    // hachCode() and equals() methods implemented.
    public static class GlobalDestinationInfo {
        private String id;

        private String name;

        private List<GlobalDestinationComponentInfo> components;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<GlobalDestinationComponentInfo> getComponents() {
            return components;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            String str =
                    "GlobalDestination: " + name + "(" + id + "): Components: "; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            if (components != null) {
                boolean first = true;
                for (GlobalDestinationComponentInfo c : components) {
                    if (!first) {
                        str += ", "; //$NON-NLS-1$
                    }

                    str += c.getComponentId();

                    first = false;
                }
            }
            return str;
        }
    }

    // FIXME JA: This class should be moved to core destination plug-in.
    // hachCode() and equals() methods implemented.
    public static class GlobalDestinationComponentInfo {
        private String componentId;

        /**
         * This is the validation destination id for this component which is
         * EFFECTIVELY the version of the component as each validation
         * destination of a component represents a set of rules specific to that
         * version.
         */
        private String validationDestinationId;

        private boolean isEnabled;

        public String getComponentId() {
            return componentId;
        }

        public String getValidationDestinationId() {
            return validationDestinationId;
        }

        public boolean isEnabled() {
            return isEnabled;
        }

        @Override
        public String toString() {
            return "Component(" + (isEnabled ? "enabled" : "disabled") + "): " //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
                    + componentId
                    + " (ValidationDestId: " + validationDestinationId + ")"; //$NON-NLS-1$ //$NON-NLS-2$
        }

    }

    /**
     * Checks to see if any Global Destination with the given id is enabled for
     * the package.
     * 
     * @param pkg
     *            The package to check.
     * @param globalDestinationId
     *            The id of Global Destinations to check for.
     * @return true if any destinations are enabled.
     */
    public static boolean isGlobalDestinationEnabled(Package pkg,
            String globalDestinationId) {
        boolean enabled = false;
        if (pkg != null && globalDestinationId != null) {
            Set<String> names =
                    DestinationUtil.getEnabledGlobalDestinations(pkg);
            DestinationPreferences preferences =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();
            for (String name : names) {
                String id = preferences.getGlobalDestinationId(name);
                if (globalDestinationId.equals(id)) {
                    enabled = true;
                    break;
                }
            }
        }
        return enabled;
    }

    /**
     * @deprecated Use
     *             {@link GlobalDestinationUtil#isGlobalDestinationEnabled(IProject,String)}
     *             instead
     */
    @Deprecated
    public static boolean isGlobalDestinationEnabled(IProject project,
            String globalDestinationId) {
        return GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                globalDestinationId);
    }

    /**
     * Checks to see if any Global Destination with the given id is enabled for
     * the process.
     * 
     * @param process
     *            The process to check.
     * @param globalDestinationId
     *            The id of Global Destinations to check for.
     * @return true if any destinations are enabled.
     */
    public static boolean isGlobalDestinationEnabled(Process process,
            String globalDestinationId) {
        boolean enabled = false;
        if (process != null && globalDestinationId != null) {
            Set<String> names =
                    DestinationUtil.getEnabledGlobalDestinations(process);
            DestinationPreferences preferences =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();
            for (String name : names) {
                String id = preferences.getGlobalDestinationId(name);
                if (globalDestinationId.equals(id)) {
                    enabled = true;
                    break;
                }
            }
        }
        return enabled;
    }

    /**
     * Provided a destination Id, this utility method return a list of global
     * destination names in the workspace.
     * 
     * @param globalDestinationId
     * @return
     */
    public static Collection<String> getGlobalDestinationNamesForId(
            String globalDestinationId) {
        Collection<String> globalDestinationNames = new ArrayList<String>();

        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        List<String> globalDestinations = preferences.getGlobalDestinations();

        for (String name : globalDestinations) {
            String id = preferences.getGlobalDestinationId(name);
            if (globalDestinationId.equals(id)) {
                globalDestinationNames.add(name);
            }
        }
        return globalDestinationNames;
    }

    /**
     * Calls explicit validation on a resource for a set of global destinations.
     * This will run rules for all of the component versions associated with
     * each of the global destinations provided and return a collection of
     * issues raised by those rules.
     * 
     * @param resource
     *            The resource to validate.
     * @param globalDestinationInfos
     *            The global destination infos.
     * @return A collection of issues raised.
     * @throws ValidationException
     *             if there was a problem invoking validation.
     */
    public static Collection<IIssue> validateByGlobalDestinationInfos(
            IResource resource,
            Collection<GlobalDestinationInfo> globalDestinationInfos)
            throws ValidationException {
        Collection<String> globalDestinationNames = new HashSet<String>();
        for (GlobalDestinationInfo info : globalDestinationInfos) {
            globalDestinationNames.add(info.getName());
        }
        return validateByGlobalDestinationNames(resource,
                globalDestinationNames);
    }

    /**
     * Calls explicit validation on a resource for a set of global destinations.
     * This will run rules for all of the component versions associated with
     * each of the global destinations provided and return a collection of
     * issues raised by those rules.
     * 
     * @param resource
     *            The resource to validate.
     * @param globalDestinationNames
     *            The global destination names as defined in the preferences.
     * @return A collection of issues raised.
     * @throws ValidationException
     *             if there was a problem invoking validation.
     */
    public static Collection<IIssue> validateByGlobalDestinationNames(
            IResource resource, Collection<String> globalDestinationNames)
            throws ValidationException {
        Set<String> validationIds = new HashSet<String>();
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        for (String globalDestinationName : globalDestinationNames) {
            for (String validationId : preferences
                    .getSelectedValidationDestinations(globalDestinationName)) {
                validationIds.add(validationId);
            }
        }
        List<Destination> destinations = new ArrayList<Destination>();
        for (String validationId : validationIds) {
            Destination destination =
                    ValidationActivator.getDefault()
                            .getDestination(validationId);
            destinations.add(destination);
        }
        Validator validator = new Validator(destinations);
        return validator.validate(resource);
    }

    /**
     * Returns a global destination info for each global destination that is
     * enabled for the given package. <b>Note that there may be more that ONE
     * global destination PER destination ID.</b>
     * 
     * @param pkg
     *            The package to check.
     * @return A collection of GlobalDestinationInfo objects.
     */
    public static Collection<GlobalDestinationInfo> getEnabledGlobalDestinationsInfo(
            Package pkg) {
        return internalGetEnabledGlobalDestinationsInfo(pkg);
    }

    /**
     * Returns a global destination info for each global destination that is
     * enabled for the given process. <b>Note that there may be more that ONE
     * global destination PER destination ID.</b>
     * 
     * @param process
     *            The process to check.
     * @return A collection of GlobalDestinationInfo objects.
     */
    public static Collection<GlobalDestinationInfo> getEnabledGlobalDestinationsInfo(
            Process process) {
        return internalGetEnabledGlobalDestinationsInfo(process);
    }

    /**
     * Returns a global destination info for each global destination that is
     * enabled for the given process. <b>Note that there may be more that ONE
     * global destination PER destination ID.</b>
     * 
     * @param processInterface
     *            The {@link ProcessInterface} to check.
     * @return A collection of GlobalDestinationInfo objects.
     */
    public static Collection<GlobalDestinationInfo> getEnabledGlobalDestinationsInfo(
            ProcessInterface processInterface) {
        return internalGetEnabledGlobalDestinationsInfo(processInterface);
    }

    /**
     * Returns a global destination info for each global destination with the
     * specified id that is enabled for the given package. <b>Note that there
     * may be more that ONE global destination PER destination ID.</b>
     * 
     * @param pkg
     *            The package to check.
     * @param globalDestinationId
     *            The global destination id to filter by.
     * @return A collection of GlobalDestinationInfo objects.
     */
    public static Collection<GlobalDestinationInfo> getEnabledGlobalDestinationsInfo(
            Package pkg, String globalDestinationId) {
        Collection<GlobalDestinationInfo> filtered =
                new ArrayList<GlobalDestinationInfo>();
        if (globalDestinationId != null) {
            Collection<GlobalDestinationInfo> infos =
                    internalGetEnabledGlobalDestinationsInfo(pkg);
            for (GlobalDestinationInfo info : infos) {
                if (globalDestinationId.equals(info.getId())) {
                    filtered.add(info);
                }
            }
        }
        return filtered;
    }

    /**
     * Returns a global destination info for each global destination with the
     * specified id that is enabled for the given process. <b>Note that there
     * may be more that ONE global destination PER destination ID.</b>
     * 
     * @param process
     *            The process to check.
     * @param globalDestinationId
     *            The global destination id to filter by.
     * @return A collection of GlobalDestinationInfo objects.
     */
    public static Collection<GlobalDestinationInfo> getEnabledGlobalDestinationsInfo(
            Process process, String globalDestinationId) {
        Collection<GlobalDestinationInfo> filtered =
                new ArrayList<GlobalDestinationInfo>();
        if (globalDestinationId != null) {
            Collection<GlobalDestinationInfo> infos =
                    internalGetEnabledGlobalDestinationsInfo(process);
            for (GlobalDestinationInfo info : infos) {
                if (globalDestinationId.equals(info.getId())) {
                    filtered.add(info);
                }
            }
        }
        return filtered;
    }

    private static Collection<GlobalDestinationInfo> internalGetEnabledGlobalDestinationsInfo(
            EObject eo) {
        Collection<GlobalDestinationInfo> infos =
                new ArrayList<GlobalDestinationInfo>();
        Collection<String> names =
                DestinationUtil.getEnabledGlobalDestinations(eo);
        for (String name : names) {
            GlobalDestinationInfo info = createGlobalDestinationInfo(name);
            infos.add(info);
        }
        return infos;
    }

    private static GlobalDestinationInfo createGlobalDestinationInfo(String name) {
        GlobalDestinationInfo info = new GlobalDestinationInfo();
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        info.id = preferences.getGlobalDestinationId(name);
        info.name = name;
        info.components = new ArrayList<GlobalDestinationComponentInfo>();
        String[] componentIds = GlobalDestinationUtil.getAllComponents(info.id);
        for (String componentId : componentIds) {
            String validationId =
                    preferences.getValidationDestinationId(name, componentId);
            GlobalDestinationComponentInfo component =
                    new GlobalDestinationComponentInfo();
            component.componentId = componentId;
            component.validationDestinationId = validationId;
            component.isEnabled =
                    preferences.isComponentEnabled(name, componentId);
            info.components.add(component);
        }
        return info;
    }
}
