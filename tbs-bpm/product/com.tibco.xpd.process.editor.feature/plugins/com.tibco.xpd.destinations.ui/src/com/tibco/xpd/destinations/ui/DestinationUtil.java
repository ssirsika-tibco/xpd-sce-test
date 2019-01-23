/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.destinations.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.internal.Messages;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public final class DestinationUtil {

    /** Process Destination Environment extension point Id. */
    private static final String EXTPOINT_ID = "processDestinationEnvironments"; //$NON-NLS-1$

    /** Process Destination Environment extension point element. */
    private static final String PDE_EL = "processDestinationEnvironment"; //$NON-NLS-1$

    /** Extension point Id attribute */
    private static final String ID_ATTR = "id"; //$NON-NLS-1$

    /** Extension point name attribute */
    private static final String NAME_ATTR = "name"; //$NON-NLS-1$

    /** Extension point user selectable attribute */
    private static final String USERSELECTABLE_ATTR = "userSelectable"; //$NON-NLS-1$

    /** Extension point validationDestinationId attribute */
    private static final String VDE_ATTR = "validationDestinationId"; //$NON-NLS-1$

    /** Extension point version attribute */
    private static final String VERSION_ATTR = "version"; //$NON-NLS-1$

    /** Extension point user selectable attribute */
    private static final String INCLUDE_XPATH_ERRORS_ATTR =
            "includeXPathErrors"; //$NON-NLS-1$

    /** The cached list of available process destination environment */
    private static final Collection<ProcessDestinationEnvironment> processDestinationEnvironments =
            loadProcessDestEnvs();

    /** The destination environment xpdl2 extended attribute name. */
    public static final String DESTINATION_ATTRIBUTE = "Destination"; //$NON-NLS-1$

    /**
     * Private Constructor.
     */
    private DestinationUtil() {
    }

    /**
     * @param process
     *            The process to check.
     * @param destinationName
     *            The destination to check.
     * @return true if the destination is enabled for the process.
     */
    public static boolean isGlobalDestinationEnabled(Process process,
            String destinationName) {
        boolean enabled = false;
        if (destinationName != null) {
            List attributes = process.getExtendedAttributes();
            for (Object next : attributes) {
                ExtendedAttribute attribute = (ExtendedAttribute) next;
                if (DESTINATION_ATTRIBUTE.equals(attribute.getName())) {
                    if (destinationName.equals(attribute.getValue())) {
                        enabled = true;
                        break;
                    }
                }
            }
        }
        return enabled;
    }

    /**
     * @param processInterface
     *            The process interface to check.
     * @param destinationName
     *            The destination to check.
     * @return true if the destination is enabled for the process interface.
     */
    public static boolean isGlobalDestinationEnabled(
            ProcessInterface processInterface, String destinationName) {
        boolean enabled = false;
        if (destinationName != null) {
            List attributes = processInterface.getExtendedAttributes();
            for (Object next : attributes) {
                ExtendedAttribute attribute = (ExtendedAttribute) next;
                if (DESTINATION_ATTRIBUTE.equals(attribute.getName())) {
                    if (destinationName.equals(attribute.getValue())) {
                        enabled = true;
                        break;
                    }
                }
            }
        }
        return enabled;
    }

    /**
     * Checks all of the processes and the interfaces in the package to see if
     * any of them have the specified destination environment enabled.
     * 
     * @param pckg
     *            The package to check.
     * @param destinationName
     *            The destination to check for.
     * @return true if one or more processes or process interfaces in the
     *         package have the destination.
     */
    public static boolean isGlobalDestinationEnabled(Package pckg,
            String destinationName) {
        boolean enabled = false;
        Set<String> enabledDestinations = getEnabledGlobalDestinations(pckg);
        if (enabledDestinations != null && destinationName != null) {
            for (String enabledDestination : enabledDestinations) {
                if (enabledDestination.equals(destinationName)) {
                    enabled = true;
                    break;
                }
            }
        }
        return enabled;
    }

    /**
     * @param process
     *            The process to check.
     * @param destinationId
     *            The destination to check.
     * @return true if the destination is enabled for the process.
     */
    public static boolean isValidationDestinationEnabled(Process process,
            String destinationId) {
        boolean enabled = false;
        if (destinationId != null) {
            List attributes = process.getExtendedAttributes();
            DestinationPreferences preferences =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();
            for (Object next : attributes) {
                ExtendedAttribute attribute = (ExtendedAttribute) next;
                if (DESTINATION_ATTRIBUTE.equals(attribute.getName())) {
                    String name = attribute.getValue();
                    enabled =
                            preferences.isValidationDestinationSelected(name,
                                    destinationId);
                    if (enabled) {
                        break;
                    }
                }
            }
        }
        return enabled;
    }

    /**
     * @param processInterface
     *            The process interface to check.
     * @param destinationId
     *            The destination to check.
     * @return true if the destination is enabled for the process interface.
     */
    public static boolean isValidationDestinationEnabled(
            ProcessInterface processInterface, String destinationId) {
        boolean enabled = false;
        if (destinationId != null) {
            List attributes = processInterface.getExtendedAttributes();
            DestinationPreferences preferences =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();
            for (Object next : attributes) {
                ExtendedAttribute attribute = (ExtendedAttribute) next;
                if (DESTINATION_ATTRIBUTE.equals(attribute.getName())) {
                    String name = attribute.getValue();
                    enabled =
                            preferences.isValidationDestinationSelected(name,
                                    destinationId);
                    if (enabled) {
                        break;
                    }
                }
            }
        }
        return enabled;
    }

    /**
     * Checks all of the processes and the interfaces in the package to see if
     * any of them have the specified destination environment enabled.
     * 
     * @param pckg
     *            The package to check.
     * @param destinationId
     *            The destination to check for.
     * @return true if one or more processes or process interfaces in the
     *         package have the destination.
     */
    public static boolean isValidationDestinationEnabled(Package pckg,
            String destinationId) {
        boolean enabled = false;
        Set<String> enabledDestinations =
                getEnabledValidationDestinations(pckg);
        if (enabledDestinations != null && destinationId != null) {
            if (enabledDestinations.contains(destinationId)) {
                enabled = true;
            }
        }
        return enabled;
    }

    /**
     * @param pckg
     *            The package to check.
     * @param destinationId
     *            The destination to check for.
     * @return The collection of processes enabled for the destination.
     */
    public static Collection<Process> getEnabledProcesses(Package pckg,
            String destinationId) {
        Collection<String> destinationIds = new ArrayList<String>();
        destinationIds.add(destinationId);
        return getEnabledProcesses(pckg, destinationIds);
    }

    /**
     * @param pckg
     *            The package to check.
     * @param destinationIds
     *            The destinations to check for.
     * @return The collection of processes enabled for the destination.
     */
    public static Collection<Process> getEnabledProcesses(Package pckg,
            Collection<String> destinationIds) {
        Collection<Process> processes = new ArrayList<Process>();
        if (pckg != null) {
            List pckgProcesses = pckg.getProcesses();
            for (Object nextP : pckgProcesses) {
                Process process = (Process) nextP;
                Set<String> enabledValidationDestinations =
                        getEnabledValidationDestinations(process);
                boolean destinationOfInterest =
                        isDestinationOfInterest(enabledValidationDestinations,
                                destinationIds);
                if (destinationOfInterest) {
                    processes.add(process);
                }

            }
        }
        return processes;
    }

    private static boolean isDestinationOfInterest(
            Set<String> enabledGlobalDestinations,
            Collection<String> destinationIds) {
        boolean toReturn = false;
        for (String eachDest : destinationIds) {
            boolean enabled = enabledGlobalDestinations.contains(eachDest);
            if (enabled) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    /**
     * @param pckg
     *            The package to check.
     * @param destinationIds
     *            The destinations to check for.
     * @return The collection of processes enabled for the destination.
     */
    public static Collection<ProcessInterface> getEnabledProcessInterfaces(
            Package pckg, Collection<String> destinationIds) {

        Collection<ProcessInterface> returnList =
                new ArrayList<ProcessInterface>();
        if (pckg != null) {
            ProcessInterfaces processInterfaces = getProcessInterfaces(pckg);
            if (processInterfaces != null) {
                EList ifcList = processInterfaces.getProcessInterface();

                for (Object nextP : ifcList) {
                    ProcessInterface procIfc = (ProcessInterface) nextP;

                    /*
                     * XPD-216 Used to compare the passed VALIDATION
                     * destinations with the ExtendedAttribute Destination which
                     * is a PROCESS destination - so just plain did not work.
                     * 
                     * MUST compare the passed validaiton destinations with the
                     * enabled validations for proc ifc
                     */
                    Set<String> enabledValidationDestinations =
                            getEnabledValidationDestinations(procIfc);
                    boolean destinationOfInterest =
                            isDestinationOfInterest(enabledValidationDestinations,
                                    destinationIds);
                    if (destinationOfInterest) {
                        returnList.add(procIfc);
                    }
                    /* XPD-216 END */
                }
            }
        }
        return returnList;
    }

    /**
     * Return a list of enabled destinations for the given context object.
     * <p>
     * If the object has an ancestor of a process/process interface then the
     * enabled destinations of the process/interface are returned.
     * <p>
     * Otherwise if the ancestor is a a package then all enabled destinations of
     * all processes / interfaces are returned.
     * 
     * @param eObject
     * @return
     */
    public static Set<String> getEnabledValidationDestinations(EObject eObject) {

        EObject parent = eObject;
        while (parent != null && !(parent instanceof Process)
                && !(parent instanceof ProcessInterface)) {
            parent = parent.eContainer();
        }

        if (parent != null) {
            // have a process or interface ancestor, use its enabled
            // destinations.
            if (parent instanceof Process) {
                return getEnabledValidationDestinations((Process) parent);
            } else {
                return getEnabledValidationDestinations((ProcessInterface) parent);
            }
        }

        // not a proces or intefrace ancestor, find package instead.
        parent = eObject;
        while (parent != null && !(parent instanceof Package)) {
            parent = parent.eContainer();
        }

        if (parent instanceof Package) {
            return getEnabledValidationDestinations((Package) parent);
        }

        return Collections.EMPTY_SET;
    }

    /**
     * Return a list of enabled destinations for the given context object.
     * <p>
     * If the object has an ancestor of a process/process interface then the
     * enabled destinations of the process/interface are returned.
     * <p>
     * Otherwise if the ancestor is a a package then all enabled destinations of
     * all processes / interfaces are returned.
     * 
     * @param eObject
     * @return
     */
    public static Set<String> getEnabledGlobalDestinations(EObject eObject) {

        EObject parent = eObject;
        while (parent != null && !(parent instanceof Process)
                && !(parent instanceof ProcessInterface)) {
            parent = parent.eContainer();
        }

        if (parent != null) {
            // have a process or interface ancestor, use its enabled
            // destinations.
            if (parent instanceof Process) {
                return getEnabledGlobalDestinations((Process) parent);
            } else {
                return getEnabledGlobalDestinations((ProcessInterface) parent);
            }
        }

        // not a proces or intefrace ancestor, find package instead.
        parent = eObject;
        while (parent != null && !(parent instanceof Package)) {
            parent = parent.eContainer();
        }

        if (parent instanceof Package) {
            return getEnabledGlobalDestinations((Package) parent);
        }

        return Collections.EMPTY_SET;
    }

    /**
     * Get the globalDestinationId (i.e. the base contributed global destination
     * types - not the names of the copies).
     * 
     * @param eObject
     * 
     * @return Set of enabled global destination types.
     */
    public static Set<String> getEnabledGlobalDestinationTypes(EObject eObject) {
        Set<String> dests = getEnabledGlobalDestinations(eObject);

        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();

        Set<String> globalDests = new HashSet<String>();

        for (String dst : dests) {
            String glob = preferences.getGlobalDestinationId(dst);
            if (glob != null && glob.length() > 0) {
                globalDests.add(glob);
            }
        }

        return globalDests;
    }

    /**
     * @param globalDestinationName
     * @return The global detsination type id for the given global destinatipon
     *         name.
     */
    public static String getGlobalDestinationType(String globalDestinationName) {
        String destTypeId = null;

        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        if (preferences != null) {
            destTypeId =
                    preferences.getGlobalDestinationId(globalDestinationName);
        }

        return destTypeId != null ? destTypeId : ""; //$NON-NLS-1$ 
    }

    /**
     * Get a list of enabled process destination environment id's.
     * 
     * @param process
     *            Process to get destinations from.
     * 
     * @return set of destination id strings.
     */
    public static Set<String> getEnabledValidationDestinations(Process process) {
        Set<String> result = new HashSet<String>();

        if (process != null) {
            List destinationList = process.getExtendedAttributes();
            DestinationPreferences preferences =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();
            for (Object next : destinationList) {
                if (next instanceof ExtendedAttribute) {
                    ExtendedAttribute ea = (ExtendedAttribute) next;
                    String name = ea.getValue();
                    if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                        for (String componentId : preferences
                                .getEnabledComponents(ea.getValue())) {
                            result.add(preferences
                                    .getValidationDestinationId(name,
                                            componentId));
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Get a list of enabled process destination environment id's.
     * 
     * @param process
     *            Process to get destinations from.
     * 
     * @return set of destination id strings.
     */
    public static Set<String> getEnabledGlobalDestinations(Process process) {
        Set<String> result = new HashSet<String>();

        if (process != null) {
            List destinationList = process.getExtendedAttributes();
            for (Object next : destinationList) {
                if (next instanceof ExtendedAttribute) {
                    ExtendedAttribute ea = (ExtendedAttribute) next;
                    if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                        result.add(ea.getValue());
                    }
                }
            }
        }

        return result;
    }

    /**
     * Get a combined list of all destination environments used in all
     * processes.
     * 
     * @param pkg
     *            Package to get destinations from.
     * 
     * @return set of destination id strings.
     */
    public static Set<String> getEnabledValidationDestinations(Package pkg) {
        Set<String> result = new HashSet<String>();

        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        /*
         * when processes get added to a package (say for instance business
         * service generation from a case class), iterating thru processes on
         * the package getting modified causes ConcurrentModificationException.
         * So take the snapshot of processes in the pkg to avoid getting this
         * exception
         */
        EList<Process> processes = pkg.getProcesses();
        List<Process> processList = new ArrayList<Process>(processes);
        for (Iterator iter = processList.iterator(); iter.hasNext();) {
            Process process = (Process) iter.next();

            List destinationList = process.getExtendedAttributes();
            for (Object next : destinationList) {
                if (next instanceof ExtendedAttribute) {
                    ExtendedAttribute ea = (ExtendedAttribute) next;
                    String name = ea.getValue();
                    if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                        for (String componentId : preferences
                                .getEnabledComponents(ea.getValue())) {
                            result.add(preferences
                                    .getValidationDestinationId(name,
                                            componentId));
                        }
                    }
                }
            }
        }

        ProcessInterfaces procIfcs = getProcessInterfaces(pkg);
        if (procIfcs != null) {
            for (Iterator iterator = procIfcs.getProcessInterface().iterator(); iterator
                    .hasNext();) {
                ProcessInterface procIfc = (ProcessInterface) iterator.next();

                List destinationList = procIfc.getExtendedAttributes();
                for (Object next : destinationList) {
                    if (next instanceof ExtendedAttribute) {
                        ExtendedAttribute ea = (ExtendedAttribute) next;
                        String name = ea.getValue();
                        if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                            for (String componentId : preferences
                                    .getEnabledComponents(ea.getValue())) {
                                result.add(preferences
                                        .getValidationDestinationId(name,
                                                componentId));
                            }
                        }
                    }
                }

            }
        }

        return result;
    }

    /**
     * Get a combined list of all destination environments used in all
     * processes.
     * 
     * @param pkg
     *            Package to get destinations from.
     * 
     * @return set of destination id strings.
     */
    public static Set<String> getEnabledGlobalDestinations(Package pkg) {

        Class[] clazzesArr = { Process.class, ProcessInterface.class };
        Set<Class> clazzes = new HashSet<Class>(Arrays.asList(clazzesArr));

        return getEnabledGlobalDestinations(pkg, clazzes);

    }

    /**
     * Get a combined list of all destination environments used in all
     * processes.
     * 
     * @param pkg
     *            Package to get destinations from.
     * @param classes
     *            Declared types in which the search for enabled global
     *            destinations should be carried out on
     * @return
     */
    public static Set<String> getEnabledGlobalDestinations(Package pkg,
            Set<Class> classes) {

        Set<String> result = new HashSet<String>();

        // Processes
        if (classes.contains(Process.class)) {
            for (Iterator iter = pkg.getProcesses().iterator(); iter.hasNext();) {
                Process process = (Process) iter.next();

                List destinationList = process.getExtendedAttributes();
                for (Object next : destinationList) {
                    if (next instanceof ExtendedAttribute) {
                        ExtendedAttribute ea = (ExtendedAttribute) next;
                        if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                            result.add(ea.getValue());
                        }
                    }
                }
            }
        }

        // Process Interfaces
        if (classes.contains(ProcessInterface.class)) {
            ProcessInterfaces procIfcs = getProcessInterfaces(pkg);
            if (procIfcs != null) {
                for (Iterator iterator =
                        procIfcs.getProcessInterface().iterator(); iterator
                        .hasNext();) {
                    ProcessInterface procIfc =
                            (ProcessInterface) iterator.next();

                    List destinationList = procIfc.getExtendedAttributes();
                    for (Object next : destinationList) {
                        if (next instanceof ExtendedAttribute) {
                            ExtendedAttribute ea = (ExtendedAttribute) next;
                            if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                                result.add(ea.getValue());
                            }
                        }
                    }

                }
            }
        }

        return result;
    }

    /**
     * Retrieves {@link ProcessInterface} for a given {@link Package}.
     * 
     * @param pkg
     * @return
     */
    private static ProcessInterfaces getProcessInterfaces(Package pkg) {
        ProcessInterfaces processInterfaces = null;
        if (pkg != null
                && pkg.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ProcessInterfaces().getName()) != null) {
            processInterfaces =
                    (ProcessInterfaces) pkg
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessInterfaces()
                                    .getName());
        }
        return processInterfaces;
    }

    /**
     * Get a list of enabled process destination environment id's.
     * 
     * @param processInterface
     *            Process to get destinations from.
     * 
     * @return set of destination id strings.
     */
    public static Set<String> getEnabledValidationDestinations(
            ProcessInterface processInterface) {
        Set<String> result = new HashSet<String>();

        List destinationList = processInterface.getExtendedAttributes();
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        for (Object next : destinationList) {
            if (next instanceof ExtendedAttribute) {
                ExtendedAttribute ea = (ExtendedAttribute) next;
                String name = ea.getValue();
                if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                    for (String componentId : preferences
                            .getEnabledComponents(ea.getValue())) {
                        result.add(preferences.getValidationDestinationId(name,
                                componentId));
                    }
                }
            }
        }

        return result;
    }

    /**
     * Get a list of enabled process destination environment id's.
     * 
     * @param processInterface
     *            Process to get destinations from.
     * 
     * @return set of destination id strings.
     */
    public static Set<String> getEnabledGlobalDestinations(
            ProcessInterface processInterface) {
        Set<String> result = new HashSet<String>();

        List destinationList = processInterface.getExtendedAttributes();
        for (Object next : destinationList) {
            if (next instanceof ExtendedAttribute) {
                ExtendedAttribute ea = (ExtendedAttribute) next;
                if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                    result.add(ea.getValue());
                }
            }
        }

        return result;
    }

    /**
     * Return command to enable / disable the given process destination
     * environment for the given process.
     * 
     * @param editingDomain
     * @param process
     * @param processDestEnvId
     * @param enabled
     * @return
     */
    public static Command getSetDestinationEnabledCommand(
            EditingDomain editingDomain, Process process,
            String processDestEnvId, boolean enabled) {
        return getSetDestCmd(editingDomain,
                process,
                processDestEnvId,
                enabled,
                Messages.DestinationUtil_SetDestEnv_menu);
    }

    /**
     * Return command to enable / disable the given process interface
     * destination environment for the given process.
     * 
     * @param processInterface
     * @param pdeId
     * @param enabled
     * @return
     */
    public static Command getSetDestinationEnabledCommand(
            EditingDomain editingDomain, ProcessInterface processInterface,
            String pdeId, boolean enabled) {
        return getSetDestCmd(editingDomain,
                processInterface,
                pdeId,
                enabled,
                Messages.DestinationUtil_SetDestEnv_menu);
    }

    /**
     * Return command to enable / disable the given package destination
     * environment for the given process.
     * 
     * Note: This is currently not used as we don't allow setting of destination
     * env for entire package, but we may one day.
     * 
     * @param process
     * @param pdeId
     * @param enabled
     * @return
     */
    @Deprecated
    public static Command getSetDestinationEnabledCommand(
            EditingDomain editingDomain, Package pkg, String pdeId,
            boolean enabled) {
        return getSetDestCmd(editingDomain,
                pkg,
                pdeId,
                enabled,
                Messages.DestinationUtil_SetPackageDest_menu);
    }

    /**
     * Return command to enable / disable the given process destination
     * environment for the given process.
     * 
     * @param container
     * @param pdeId
     * @param enabled
     * @return
     */
    private static Command getSetDestCmd(EditingDomain editingDomain,
            ExtendedAttributesContainer container, String pdeId,
            boolean enabled, String label) {

        CompoundCommand command = new CompoundCommand();
        command.setLabel(label);

        if (enabled) {
            ExtendedAttribute att =
                    Xpdl2Factory.eINSTANCE.createExtendedAttribute();
            att.setName(DESTINATION_ATTRIBUTE);
            att.setValue(pdeId);
            command.append(AddCommand.create(editingDomain,
                    container,
                    Xpdl2Package.eINSTANCE.getExtendedAttribute(),
                    att));
        } else {
            ExtendedAttribute attribute = null;
            List destinationList = container.getExtendedAttributes();
            for (Object next : destinationList) {
                if (next instanceof ExtendedAttribute) {
                    ExtendedAttribute ea = (ExtendedAttribute) next;
                    if (DESTINATION_ATTRIBUTE.equals(ea.getName())) {
                        if (pdeId.equals(ea.getValue())) {
                            attribute = ea;
                        }
                    }
                }
            }
            if (attribute != null) {
                command.append(RemoveCommand.create(editingDomain,
                        container,
                        Xpdl2Package.eINSTANCE.getExtendedAttribute(),
                        attribute));
            }
        }

        return command;
    }

    /**
     * Return a list of the available process destination environments as
     * contributed to the
     * com.tibco.xpd.destinations.ui.processDestinationEnvironment extension
     * point.
     * 
     * @return List of available (contributed) destination environments.
     */
    public static Collection<ProcessDestinationEnvironment> getAvailableProcessDestinationEnvironments() {
        return processDestinationEnvironments;
    }

    /**
     * Load the list of destination environments (this is done ONCE only on
     * initialisation because list will be used VERY frequently.
     * 
     * @return
     */
    private static Collection<ProcessDestinationEnvironment> loadProcessDestEnvs() {
        List<ProcessDestinationEnvironment> procDestEnvs =
                new ArrayList<ProcessDestinationEnvironment>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DestinationsUIPlugIn.PLUGIN_ID,
                                EXTPOINT_ID);
        if (point != null) {
            //
            // Go thru each contribution to this extension point.
            //
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {

                for (IExtension ext : extensions) {
                    loadSingleContribution(ext, procDestEnvs);
                }
            }
        }

        return procDestEnvs;
    }

    /**
     * Load a single process destination contribution.
     * 
     * @param ext
     * @param procDestEnvs
     */
    private static void loadSingleContribution(IExtension ext,
            List<ProcessDestinationEnvironment> procDestEnvs) {

        Collection<IConfigurationElement> pdes =
                ExtPointUtil.getConfigElements(ext, PDE_EL, true);

        for (Iterator iter = pdes.iterator(); iter.hasNext();) {
            IConfigurationElement config = (IConfigurationElement) iter.next();

            ProcessDestinationEnvironment pde =
                    new ProcessDestinationEnvironment();

            String id = ExtPointUtil.getConfigAttribute(config, ID_ATTR, true);
            if (id != null) {
                pde.setId(id);

                String name =
                        ExtPointUtil
                                .getConfigAttribute(config, NAME_ATTR, true);
                if (name != null) {
                    pde.setName(name);

                    String strSelectable =
                            ExtPointUtil.getConfigAttribute(config,
                                    USERSELECTABLE_ATTR,
                                    true);
                    if (strSelectable != null) {
                        pde.setUserSelectable(Boolean
                                .parseBoolean(strSelectable));

                        String vdeId =
                                ExtPointUtil.getConfigAttribute(config,
                                        VDE_ATTR,
                                        false);
                        if (vdeId != null) {
                            pde.setValidationDestinationId(vdeId);
                        }

                        String version =
                                ExtPointUtil.getConfigAttribute(config,
                                        VERSION_ATTR,
                                        false);
                        if (version != null) {
                            pde.setVersion(version);
                        }

                        String includeXPathErrors =
                                ExtPointUtil.getConfigAttribute(config,
                                        INCLUDE_XPATH_ERRORS_ATTR,
                                        false);
                        if (includeXPathErrors != null
                                && !includeXPathErrors.equals("")) {//$NON-NLS-1$
                            pde.setIncludeXPathErrors(Boolean
                                    .parseBoolean(includeXPathErrors));
                        }

                        procDestEnvs.add(pde);

                    }

                }
            }
        }
        return;
    }

    /**
     * Adds the passed destinations to the {@link Process} <b>ONLY</b> if it is
     * not already present. This method is only supposed to be used for
     * {@link Process} which are created for Wizards and not associated to the
     * model. So, that is the reason it is updating the models without commands.
     * 
     * @param enabledDestinations
     * @param newProcess
     */
    public static void addPassedDestinationToProcess(
            Set<String> enabledDestinations, Process newProcess) {

        Set<String> enabledDestinationsOnProcess =
                getEnabledGlobalDestinations(newProcess);

        for (String dest : enabledDestinations) {
            /*
             * XPD-7449: add only those destinations which are not already
             * present.
             */
            if (!enabledDestinationsOnProcess.contains(dest)) {
                ExtendedAttribute att =
                        Xpdl2Factory.eINSTANCE.createExtendedAttribute();
                att.setName(DESTINATION_ATTRIBUTE);
                att.setValue(dest);
                newProcess.getExtendedAttributes().add(att);
            }
        }
    }

}
