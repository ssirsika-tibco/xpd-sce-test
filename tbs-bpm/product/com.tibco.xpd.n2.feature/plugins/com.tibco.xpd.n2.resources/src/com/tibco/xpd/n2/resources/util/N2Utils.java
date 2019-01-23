/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.resources.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.utils.ValidationProblemUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author mtorres
 * 
 */
public class N2Utils {

    public static final String N2_GLOBAL_DESTINATION_ID =
            "com.tibco.xpd.n2.core.n2GlobalDestination"; //$NON-NLS-1$

    public static final String N2_PROBLEM_MARKER_ID_KEY =
            "N2_PROBLEM_MARKER_ID_KEY"; //$NON-NLS-1$

    public static final String REGEX_XPDL_FILENAME_INVALID_SUBSEQUENCES =
            "&(amp;)?|#"; //$NON-NLS-1$

    public static void addN2ErrorMarker(IResource resource, String msg,
            String problemMarkerType, String errorMarkerId, List<Object> args)
            throws CoreException {
        String formattedMsg = String.format(msg, args.toArray());
        IMarker marker = resource.createMarker(problemMarkerType);
        marker.setAttribute(IMarker.MESSAGE, formattedMsg);

        marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
        marker.setAttribute(IMarker.LOCATION, resource.getProjectRelativePath()
                .toString());
        marker.setAttribute(N2_PROBLEM_MARKER_ID_KEY, errorMarkerId);
    }

    public static List<IMarker> getN2ErrorMarkersOfType(IResource resource,
            String problemMarkerType, String errorMarkerId)
            throws CoreException {
        if (resource != null && errorMarkerId != null) {
            IMarker[] markers =
                    resource.findMarkers(problemMarkerType,
                            true,
                            IResource.DEPTH_INFINITE);
            if (markers != null && markers.length > 0) {
                List<IMarker> errorMarkers = new ArrayList<IMarker>();
                for (IMarker marker : markers) {
                    if (marker != null) {
                        String markerId =
                                (String) marker
                                        .getAttribute(N2Utils.N2_PROBLEM_MARKER_ID_KEY);
                        if (markerId != null && markerId.equals(errorMarkerId)) {
                            errorMarkers.add(marker);
                        }
                    }
                }
                return errorMarkers;
            }
        }
        return Collections.emptyList();
    }

    public static URI getCompositeBaseURI(URI compositeURI) {
        return URI.createURI("../../").resolve(compositeURI); //$NON-NLS-1$
    }

    /**
     * @param xpdlResource
     * 
     * @return true if given xpdl resource has N2 destination processes /
     *         process interfaces.
     */
    public static boolean hasN2ProcessesOrInterfaces(IResource xpdlResource) {
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlResource);
        if (workingCopy != null) {
            if (workingCopy.getRootElement() instanceof Package) {
                Package pkg = (Package) workingCopy.getRootElement();
                return isN2DestinationEnabled(pkg);
            }
        }
        return false;
    }

    /**
     * @param pkg
     *            Package in which to search for N2 processes
     * 
     * @return <code>true</code> if N2 (AMX BPM) destination type is enabled on
     *         one or more processes contained in the package. Process
     *         interfaces are not considered here.
     */
    public static boolean hasN2Processes(Package pkg) {

        boolean n2ProcessesFound = false;

        if (pkg != null) {

            Set<Class> clazzes = new HashSet<Class>();
            clazzes.add(Process.class);
            Set<String> destinations =
                    DestinationUtil.getEnabledGlobalDestinations(pkg, clazzes);

            DestinationPreferences preferences =
                    DestinationsActivator.getDefault()
                            .getDestinationPreferences();

            Set<String> globalDests = new HashSet<String>();
            for (String dst : destinations) {
                String glob = preferences.getGlobalDestinationId(dst);
                if (glob != null && glob.length() > 0) {
                    globalDests.add(glob);
                }
            }

            n2ProcessesFound = globalDests.contains(N2_GLOBAL_DESTINATION_ID);
        }

        return n2ProcessesFound;
    }

    /**
     * @param processPkgOrContent
     *            Process-Package, process or process interface (or some content
     *            within one of these).
     * 
     * @return <code>true</code> if N2 (AMX BPM) destination type is enabled on
     *         the process (or ancestor process if content of one is passed) or
     *         process-interface. If the object is a process package or content
     *         of a process package (but not process/interface) then returns
     *         <code>true</code> if ANY process/process interface is returned.
     */
    public static boolean isN2DestinationEnabled(EObject processPkgOrContent) {
        Set<String> enabledGlobalDestinations =
                DestinationUtil
                        .getEnabledGlobalDestinationTypes(processPkgOrContent);
        if (enabledGlobalDestinations != null
                && enabledGlobalDestinations.contains(N2_GLOBAL_DESTINATION_ID)) {
            return true;
        }
        return false;
    }

    /**
     * Check if the given XPDL file resource has N2 related validation problems
     * 
     * @param xpdlResource
     * 
     * @return true if the given XPDL file resource has N2 related validation
     *         problems
     */
    public static boolean hasN2ValidationProblems(IResource xpdlResource) {

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlResource);
        if (workingCopy != null) {
            if (workingCopy.getRootElement() instanceof Package) {
                Package pkg = (Package) workingCopy.getRootElement();

                /*
                 * Get all the validation destination id's included in all the
                 * enabled N2 BPM global destination environments.
                 */
                Set<String> validationDestIds = new HashSet<String>();
                validationDestIds.add("bpmn1"); //$NON-NLS-1$
                validationDestIds.add("bmpn1dev"); //$NON-NLS-1$

                Set<String> enabledGlobalDestinations =
                        DestinationUtil.getEnabledGlobalDestinations(pkg);
                for (String globDest : enabledGlobalDestinations) {
                    if (N2_GLOBAL_DESTINATION_ID.equals(DestinationUtil
                            .getGlobalDestinationType(globDest))) {
                        String[] validationDestsInGlobDest =
                                GlobalDestinationUtil
                                        .getselectedvalidationdestinations(globDest);
                        for (String validationDestId : validationDestsInGlobDest) {
                            validationDestIds.add(validationDestId);
                        }
                    }
                }

                /*
                 * Check the resource for validation problems
                 */
                try {
                    Collection<IMarker> markers =
                            ValidationProblemUtil
                                    .getProblemMarkers(validationDestIds,
                                            IMarker.SEVERITY_ERROR,
                                            true,
                                            xpdlResource);
                    if (!markers.isEmpty()) {
                        // Found error problems
                        return true;
                    }
                } catch (CoreException e1) {
                    BundleActivator.getDefault().getLogger()
                            .error(e1, "Explicit N2 Validation failed.");
                }
            }
        }

        return false;
    }

    /**
     * Get fully qualified name of the given package.
     * 
     * @param parentPackage
     * @return
     */
    public static String getPackageQualifier(
            org.eclipse.uml2.uml.Package bomPackage) {
        String qualifier = bomPackage.getName();

        if (bomPackage.eContainer() instanceof org.eclipse.uml2.uml.Package) {
            org.eclipse.uml2.uml.Package parentPackage =
                    (org.eclipse.uml2.uml.Package) bomPackage.eContainer();
            qualifier = parentPackage.getName() + "." + qualifier; //$NON-NLS-1$
            if (parentPackage.eContainer() instanceof org.eclipse.uml2.uml.Package) {
                String parent =
                        getPackageQualifier((org.eclipse.uml2.uml.Package) parentPackage
                                .eContainer());

                if (parent.length() > 0) {
                    qualifier = parent + "." + qualifier; //$NON-NLS-1$
                }
            }
        }

        return qualifier;
    }

    /**
     * @param pckg
     * @return all participants in package (including process participants
     */
    public static Collection<Participant> getPackageParticipants(Package pckg) {
        List<Participant> pkgParticipants = new ArrayList<Participant>();

        pkgParticipants.addAll(pckg.getParticipants());

        for (Process process : pckg.getProcesses()) {
            pkgParticipants.addAll(process.getParticipants());

        }
        return pkgParticipants;
    }

    /**
     * @param targetProjectName
     * @return
     */
    public static List<IndexerItem> getProjectParticipants(
            IProject targetProject) {
        String targetProjectName = targetProject.getName();

        List<IndexerItem> projectPartics =
                new ArrayList<IndexerItem>(
                        ProcessUIUtil.getAllParticipantIndexerItems());

        /* Remove any not in same project. */
        for (Iterator iterator = projectPartics.iterator(); iterator.hasNext();) {
            IndexerItem indexerItem = (IndexerItem) iterator.next();

            String projectName =
                    indexerItem.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);
            if (!targetProjectName.equals(projectName)) {
                iterator.remove();
            }
        }

        return projectPartics;
    }

    /**
     * Compare the system participant with all other participants.
     * 
     * @param participant
     * @param sortedProjectPartics
     * @param targetProject
     */
    public static boolean isParticipantExistsInOtherXpdls(Package pckg,
            Participant participant, List<IndexerItem> sortedProjectPartics,
            IProject targetProject) {
        if (ParticipantType.SYSTEM_LITERAL
                .equals(getParticipantType(participant))) {
            String particName = participant.getName();
            if (particName != null) {
                /*
                 * Check that all participants with the same name as this one
                 * have the same configuration.
                 */
                for (IndexerItem ii : sortedProjectPartics) {
                    String iiName = ii.get(Xpdl2ResourcesPlugin.ATTRIBUTE_NAME);

                    URI uri = URI.createURI(ii.getURI());

                    if (uri != null) {
                        EObject o = ProcessUIUtil.getEObjectFrom(uri);
                        if (o instanceof Participant) {
                            Participant otherPartic = (Participant) o;

                            /* don't care if other partic is not a system one. */
                            if (ParticipantType.SYSTEM_LITERAL
                                    .equals(getParticipantType(otherPartic))) {
                                /*
                                 * Don't care if it's exactly the same
                                 * participant.
                                 */
                                if (!otherPartic.equals(participant)) {

                                    if (particName.equals(iiName)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param participant
     * @return participant type.
     */
    public static ParticipantType getParticipantType(Participant participant) {
        ParticipantTypeElem particTypeData = participant.getParticipantType();
        if (particTypeData != null) {
            return particTypeData.getType();
        }
        return null;
    }

    /** Save options for N2 XML files. */
    private static Map<Object, Object> XML_SAVE_OPTIONS;
    static {
        XML_SAVE_OPTIONS = new HashMap<Object, Object>();
        XMIResource resource = new XMIResourceImpl();
        // default save options.
        XML_SAVE_OPTIONS.putAll(resource.getDefaultSaveOptions());
        XML_SAVE_OPTIONS.put(XMIResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
        XML_SAVE_OPTIONS.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
    }

    /**
     * Returns the map of default save options for XML files used by EMF
     * resources serialization
     * 
     * @return the map of default save options for XML files used by EMF
     *         resources serialization.
     */
    public static Map<Object, Object> getDefaultXMLSaveOptions() {
        return XML_SAVE_OPTIONS;
    }
}
