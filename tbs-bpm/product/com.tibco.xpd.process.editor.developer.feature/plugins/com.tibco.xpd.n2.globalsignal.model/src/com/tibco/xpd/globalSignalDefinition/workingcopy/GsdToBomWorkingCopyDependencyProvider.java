package com.tibco.xpd.globalSignalDefinition.workingcopy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.resources.DependencyProxyResource;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.RecordType;

/**
 * GSD to BOM working copy dependency provider.
 * 
 * 
 * @author kthombar
 * @since Jul 3, 2015
 */
public class GsdToBomWorkingCopyDependencyProvider implements
        IWorkingCopyDependencyProvider {

    private static final String BOM_SPECIALFOLDER_KIND =
            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;

    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {

        return GsdWorkingCopy.class;
    }

    /**
     * 
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     * @return the List of BOM Files that the GSD File depends upon.
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Set<IResource> resources = new HashSet<IResource>();

        if (wc != null && wc.getEclipseResources() != null
                && !wc.getEclipseResources().isEmpty()) {

            IProject project = wc.getEclipseResources().get(0).getProject();
            EObject element = wc.getRootElement();
            Set<String> locations = new HashSet<String>();

            if (element instanceof GlobalSignalDefinitions) {

                GlobalSignalDefinitions globalSignalDefinitions =
                        (GlobalSignalDefinitions) element;

                /* Get all BOM external references locations in the GSD */
                Set<String> theLocations =
                        getLocations(globalSignalDefinitions);

                if (!theLocations.isEmpty()) {
                    locations.addAll(theLocations);
                }
            }

            if (!locations.isEmpty()) {

                for (String location : locations) {

                    IFile file =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(project,
                                            BOM_SPECIALFOLDER_KIND,
                                            location,
                                            true);
                    if (file != null) {

                        resources.add(file);
                    } else {

                        /* Add proxy resource */
                        resources.add(new DependencyProxyResource(new Path(
                                location), BOM_SPECIALFOLDER_KIND));
                    }
                }
            }
        }

        return resources;
    }

    /**
     * 
     * 
     * @param eo
     * @return all locations in external references(to BOM files) of any payload
     *         data in passed {@link GlobalSignalDefinitions}
     */
    private Set<String> getLocations(
            GlobalSignalDefinitions globalSignalDefinitions) {
        Set<String> locations = new HashSet<String>();

        if (globalSignalDefinitions != null) {

            List<ExternalReference> refs =
                    getBOMExternalReferences(globalSignalDefinitions);
            if (refs != null && !refs.isEmpty()) {

                for (ExternalReference ref : refs) {

                    String location = ref.getLocation();
                    if (location != null) {

                        locations.add(location);
                    }
                }
            }
        }

        return locations;
    }

    /**
     * 
     * @param globalSignalDefinitions
     * @return all the external references to BOM files from the Payload Data of
     *         the passed {@link GlobalSignalDefinitions}
     */
    private List<ExternalReference> getBOMExternalReferences(
            GlobalSignalDefinitions globalSignalDefinitions) {
        List<ExternalReference> externalReferences =
                new ArrayList<ExternalReference>();

        EList<GlobalSignal> globalSignals =
                globalSignalDefinitions.getGlobalSignals();

        for (GlobalSignal globalSignal : globalSignals) {

            EList<PayloadDataField> payloadDataFields =
                    globalSignal.getPayloadDataFields();

            for (PayloadDataField payloadDataField : payloadDataFields) {
                ExternalReference externalReference = null;
                /*
                 * Check for external ref to normal as well as case class.
                 */
                if (payloadDataField.getDataType() instanceof ExternalReference) {

                    externalReference =
                            (ExternalReference) payloadDataField.getDataType();

                } else if (payloadDataField.getDataType() instanceof RecordType) {

                    RecordType recordType =
                            (RecordType) payloadDataField.getDataType();
                    EList<Member> memberList = recordType.getMember();
                    Member member = memberList.get(0);

                    externalReference = member.getExternalReference();
                }

                if (null != externalReference) {
                    /*
                     * check if external reference is valid
                     */
                    if (ProcessUIUtil
                            .isValidExternalReference(externalReference)) {
                        externalReferences.add(externalReference);
                    }
                }
            }
        }
        return externalReferences;
    }
}
