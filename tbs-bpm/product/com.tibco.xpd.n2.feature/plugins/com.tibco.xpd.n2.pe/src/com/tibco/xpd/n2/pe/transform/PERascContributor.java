/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.pe.transform;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.PropertyValue;
import com.tibco.bpm.dt.rasc.Version;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.n2.bpel.builder.BPELOnDemandBuilder;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.pe.PEActivator;
import com.tibco.xpd.n2.pe.internal.Messages;
import com.tibco.xpd.rasc.core.RascAppSummary;
import com.tibco.xpd.rasc.core.RascContext;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.rasc.core.RascWriter;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.ondemand.BuildTargetSet;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * An implementation of RascContributor to add Process Engine and Page Flow BPEL
 * files in the RASC artifacts.
 *
 * @author pwatson
 * @since 19 Mar 2019
 */
public class PERascContributor implements RascContributor {

    /**
     * The unique identifier for this RascContribution implementation.
     */
    private static String ID = "com.tibco.xpd.n2.pe.transform.pe.contributor"; //$NON-NLS-1$

    private static final String LOG_BUILD_FAILED = "Project build failed."; //$NON-NLS-1$

    private static final String LINE_SEPARATOR = "line.separator"; //$NON-NLS-1$

    private static final String workModelVersionRange =
            "workModelVersionRange=\""; //$NON-NLS-1$

    private static final String workModelVersionRangeRegex =
            workModelVersionRange + "(.*?)\""; //$NON-NLS-1$

    private static final String BPEL_FILE_ENCODING = "UTF-8"; //$NON-NLS-1$

    /**
     * The name of the manifest attribute to which the collection of shared
     * resource references will be written.
     */
    public static final String SHARED_RESOURCE_MANIFEST_ATTR =
            "Resource-Dependencies"; //$NON-NLS-1$

    /**
     * The shared-resource manifest attribute property that identifies the type
     * of resource.
     */
    private static final String SHARED_RSRC_TYPE_PROP = "type"; //$NON-NLS-1$

    /**
     * The shared-resource manifest attribute property that describes the
     * resource.
     */
    private static final String SHARED_RSRC_DESC_PROP = "description"; //$NON-NLS-1$

    /**
     * The identifies a REST service shared-resource within the manifest attribute "Resource-Dependencies".
     */
    private static final String REST_SERVICE_SHARED_RSRC_TYPE = "HTTPClient"; //$NON-NLS-1$

    /**
     * The identifies an EMail service shared-resource within the manifest attribute "Resource-Dependencies".
     */
    private static final String EMAIL_SERVICE_SHARED_RSRC_TYPE = "EMail"; //$NON-NLS-1$

    /**
     * The identifies an Jdbc service shared-resource within the manifest attribute "Resource-Dependencies".
     */
    private static final String JDBC_SERVICE_SHARED_RSRC_TYPE = "Jdbc"; //$NON-NLS-1$

    /**
     * The MicroServices to which the Process Engine artifacts generated by this contributor will be delivered.
     */
    public static final MicroService[] BP_DESTINATION_SERVICES =
            { MicroService.BP };

    /**
     * The MicroServices to which the Page-Flow artifacts generated by this
     * contributor will be delivered.
     */
    public static final MicroService[] PF_DESTINATION_SERVICES =
            { MicroService.UP };

    /**
     * The MicroServices to which the BOM Data Dependency artifacts generated by
     * this contributor will be delivered.
     */
    public static final MicroService[] BOM_DATAINFO_SERVICES =
            { MicroService.BP };

    /**
     * Sid ACE-4134 For Asset-Categories property if any processes added to the RASC.
     */
    private static final String PROCESS_RASC_ASSET_ID = "com.tibco.asset.process"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.rasc.core.RascContributor#getId()
     */
    @Override
    public String getId() {
        return PERascContributor.ID;
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascContributor#hasContributionsFor(org.eclipse.core.resources.IProject)
     */
    @Override
    public boolean hasContributionsFor(IProject aProject) {
        List<IResource> xpdlResources = SpecialFolderUtil
                .getAllDeepResourcesInSpecialFolderOfKind(aProject,
                        N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                        N2PENamingUtils.XPDL_FILE_EXTENSION,
                        false);
        if ((xpdlResources != null) && (!xpdlResources.isEmpty())) {
            /*
             * Sid ACE-5179 No need for RASCs for process projects with no processes in (for example, process-interfaces
             * only)
             */
            boolean hasProcesses = false;

            for (IResource xpdlResource : xpdlResources) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlResource);

                if (wc != null) {
                    EObject root = wc.getRootElement();

                    if (root instanceof Package) {
                        EList<Process> processes = ((Package) root).getProcesses();

                        /* Sid ACE-6411 Fixed incorrectly coded check for ANY xpdl has processes */
                        if (!processes.isEmpty()) {
                            hasProcesses = true;
                            break;
                        }
                    }
                }
            }

            return hasProcesses;
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascContributor#process(org.eclipse.core.resources.IProject,
     *      com.tibco.xpd.rasc.core.RascContext,
     *      org.eclipse.core.runtime.IProgressMonitor,
     *      com.tibco.xpd.rasc.core.RascWriter)
     *
     * @param aProject
     * @param aContext
     * @param aProgressMonitor
     * @param aWriter
     * @throws Exception
     */
    @Override
    public void process(IProject aProject, RascContext aContext,
            IProgressMonitor aProgressMonitor, RascWriter aWriter)
            throws Exception {

        SubMonitor monitor = SubMonitor.convert(aProgressMonitor,
                Messages.PERascContributor_BuildingProcesses_status,
                2);
        monitor.subTask(Messages.PERascContributor_BuildingProcesses_status);

        // refresh the generated BPEL files for the given project
        BPELOnDemandBuilder bpelBuilder = new BPELOnDemandBuilder(aProject);

        IStatus status = bpelBuilder.buildProject(monitor.split(1));
        if (!status.isOK()) {
            Logger logger = Xpdl2ResourcesPlugin.getDefault().getLogger();
            logger.debug(PERascContributor.LOG_BUILD_FAILED);
            logger.log(status);
            monitor.done();
            return;
        }

        /* Sid ACE-4134 add to Asset-Categories property if any processes added to the RASC. */
        addBpelToRasc(monitor.split(1), aWriter, bpelBuilder, aContext.getVersion());

        // add the shared-resource references to the RASC
        addSharedResources(aWriter, getSharedResources(aProject));

        // add the BOM dependency references to the RASC
        addBomDependencies(aWriter, aContext.getAppSummary());

        monitor.subTask(""); //$NON-NLS-1$
        monitor.done();
    }

    /**
     * Add the BPEL files to the RASC
     * 
     * @param aProgressMonitor
     * @param aWriter
     * @param bpelBuilder
     * @param version
     * @throws CoreException
     * 
     * @return <code>true</code> if any processes added to the RASC
     */
    private boolean addBpelToRasc(IProgressMonitor aProgressMonitor,
            final RascWriter aWriter, BPELOnDemandBuilder bpelBuilder,
            Version version) throws CoreException {
        boolean processesAdded = false;

        /*
         * Get the target derived file info - we will then output content to the
         * RASC according to these
         */
        Collection<BuildTargetSet> builtTargets = bpelBuilder.getBuiltTargets();

        // create a monitor for the number of processes (plus one for the packages)
        int numTargets = countBpelResources(builtTargets) + 1;
        SubMonitor monitor = SubMonitor.convert(aProgressMonitor,
                Messages.PERascContributor_AddingRuntimeProcesses,
                numTargets);
        monitor.subTask(Messages.PERascContributor_AddingRuntimeProcesses);

        try {
            // keep a record of the process packages - keyed on their target location
            Map<IContainer, PackageDeployment> includedPackages = new HashMap<>();

            /*
             * Iterate the build targets. Output the BPEL files to the
             * appropriate micro-services
             */
            for (BuildTargetSet buildTargetSet : builtTargets) {
                for (Entry<IResource, Object> targetAndSource : buildTargetSet
                        .getTargetToSourceObjectMap().entrySet()) {

                    IResource targetResource = targetAndSource.getKey();
                    Object sourceObject = targetAndSource.getValue();

                    if (sourceObject instanceof Process
                            && BPELN2Utils.BPEL_FILE_EXTENSION.equals(
                                    targetResource.getFileExtension())) {

                        Process sourceProcess = (Process) sourceObject;

                        // determine its recipient MicroServices
                        MicroService[] destinations =
                                getDestinations(targetResource);

                        if (destinations != null) {
                            addRascBpelResource(aWriter,
                                    (IFile) targetResource,
                                    sourceProcess,
                                    destinations,
                                    version);
                            
                            processesAdded = true;

                            // add process's package to package collection
                            IContainer location = targetResource.getParent();
                            Package pkg = sourceProcess.getPackage();
                            PackageDeployment packageDeployment = includedPackages.get(location);
                            if (packageDeployment != null) {
                                packageDeployment.addDestinations(destinations);
                            } else {
                                includedPackages.put(location,
                                        new PackageDeployment(pkg, targetResource.getParent(), destinations));
                            }
                        }
                    }

                    monitor.worked(1);
                }
            }

            // add any package files
            for (Entry<IContainer, PackageDeployment> pkg : includedPackages.entrySet()) {
                addPackageToRasc(aWriter, pkg.getValue());
            }
            monitor.worked(1);
        } finally {
            monitor.subTask(""); //$NON-NLS-1$
            monitor.done();
        }

        return processesAdded;
    }

    /**
     * Adds the record for the given process package to the RASC. This consists of a simple properties file containing
     * identification information for the given package. The artifact's location within the RASC, and the destination
     * MicroServices, are dentified within the PackageDeployment.
     * 
     * @param aWriter
     *            the RASC writer to be updated.
     * @param aPackage
     *            the process package to be added to the RASC.
     * @param aDeployment
     *            the location and destinations for the process package.
     * @throws CoreException
     */
    private void addPackageToRasc(RascWriter aWriter, PackageDeployment aDeployment)
            throws CoreException {
        try {
            Package pkg = aDeployment.getPackage();

            // add artifact content to the RASC
            String label = Xpdl2ModelUtil.getDisplayName(pkg);
            OutputStream output = aWriter
                    .addContent(aDeployment.getPath(), label, pkg.getName(), aDeployment.getDestinations());
            try {
                // the artifact is a properties format file
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, RascContributor.DEFAULT_ENCODING));
                try {
                    writer.printf("name=%1$s", pkg.getName()).println(); //$NON-NLS-1$
                    writer.printf("label=%1$s", label).println(); //$NON-NLS-1$

                    /*
                     * Sid ACE-3126 Add the package property - this should be exactly the same path as the Xpdl2Bpel
                     * converter uses in the callProcess/packageRef attribute (i.e. this is the full workspace path of
                     * the xpdl file including project and parent folder and xpdl file)
                     */
                    String packageRefPath = WorkingCopyUtil.getFile(pkg).getFullPath().toString();

                    writer.printf("package=%1$s", packageRefPath).println(); //$NON-NLS-1$

                } finally {
                    writer.close();
                }
            } finally {
                output.close();
            }
        } catch (Exception e) {
            throw new CoreException(new Status(Status.ERROR, "PE RASC Contributor Plug-in", //$NON-NLS-1$
                    e.getMessage(), e));
        }
    }

    /**
     * Add the given target BPEL resource to the RASC.
     * 
     * @param aWriter
     *            The RASC writer
     * @param bpelFile
     *            The BPEL resource
     * @param sourceProcess
     *            The process that BPEL was derived from.
     * @param destinations
     *            The destination micro-service
     * @param version
     * @throws CoreException
     */
    private void addRascBpelResource(RascWriter aWriter, IFile bpelFile,
            Process sourceProcess, MicroService[] destinations, Version version)
            throws CoreException {

        // find the real location of the BPEL file
        URI uri = bpelFile.getLocationURI();
        if (uri == null) {
            return; // ignore this file
        }

        try {
            // copy the BPEL file to the RASC
            InputStream input =
                    replaceUserTaskModelsVersionRange(bpelFile, version);

            try {
                String relativePath =
                        bpelFile.getProjectRelativePath().toString();

                /*
                 * The temp build folder is Project/.processOut; in the RASC we
                 * just want "processOut"
                 */
                if (relativePath.charAt(0) == '.') {
                    relativePath = relativePath.substring(1);
                }

                // create a RASC artifact with the BPEL file name
                OutputStream output = aWriter.addContent(relativePath,
                        Xpdl2ModelUtil.getDisplayName(sourceProcess),
                        sourceProcess.getName(),
                        destinations);
                try {
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = input.read(buffer)) > 0) {
                        output.write(buffer, 0, len);
                    }
                } finally {
                    output.close();
                }

            } finally {
                input.close();
            }
        } catch (Exception e) {
            IStatus status =
                    new Status(Status.ERROR, "PE RASC Contributor Plug-in", //$NON-NLS-1$
                            e.getMessage(), e);
            throw new CoreException(status);
        }
    }

    /**
     * In the generated BPEL file (which may have been generated and cached on
     * disk some time ago) there are <model:UserTaskDataModelType> elements with
     * version number ranges referencing work model elements. These need to be
     * replaced during streaming to RASC.
     * 
     * Sid ACE-883: For reference, this code copied from BPM Studio's
     * N2PEPackagerPartcipant class then modified for RASC env'
     * 
     * @param bpelFile
     * @param version
     * 
     * @return An input stream for the modified file.
     */
    private InputStream replaceUserTaskModelsVersionRange(IFile bpelFile,
            Version version) throws CoreException {
        /*
         * Load the existing content into a string builder.
         */
        StringBuilder strBuilder = new StringBuilder();

        InputStream contentsInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            contentsInputStream = bpelFile.getContents();
            inputStreamReader = new InputStreamReader(contentsInputStream,
                    Charset.forName(BPEL_FILE_ENCODING));

            bufferedReader = new BufferedReader(inputStreamReader);
            String expLine = null;
            while ((expLine = bufferedReader.readLine()) != null) {
                strBuilder.append(expLine);
                strBuilder.append(System.getProperty(LINE_SEPARATOR));
            }

        } catch (IOException e) {
            throw new CoreException(new Status(IStatus.ERROR,
                    PEActivator.PLUGIN_ID,
                    "IOException whilst reading BPEL file: " + e.getMessage())); //$NON-NLS-1$

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new CoreException(new Status(IStatus.ERROR,
                            PEActivator.PLUGIN_ID,
                            "IOException whilst closing BPEL bufferedReader: " //$NON-NLS-1$
                                    + e.getMessage()));
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    throw new CoreException(new Status(IStatus.ERROR,
                            PEActivator.PLUGIN_ID,
                            "IOException whilst closing BPEL inputStreamReader: " //$NON-NLS-1$
                                    + e.getMessage()));
                }
            }

            if (contentsInputStream != null) {
                try {
                    contentsInputStream.close();
                } catch (IOException e) {
                    throw new CoreException(new Status(IStatus.ERROR,
                            PEActivator.PLUGIN_ID,
                            "IOException whilst closing BPEL contentsInputStream: " //$NON-NLS-1$
                                    + e.getMessage()));
                }
            }
        }

        /*
         * Rebuild the property with the correct version number from the RASC
         * generation context.
         */

        String newAttribute =
                String.format("workModelVersionRange=\"[%1$s,%1$s]\"", //$NON-NLS-1$
                        version.toString());

        String origBpelContents = strBuilder.toString();
        String changedBpelContents = origBpelContents
                .replaceAll(workModelVersionRangeRegex, newAttribute);

        byte[] bytes = changedBpelContents
                .getBytes(Charset.forName(BPEL_FILE_ENCODING));

        return new ByteArrayInputStream(bytes);
    }

    /**
     * Determines the MicroServices to which the given resource is to be
     * delivered. If the response is <code>null</code>, the resource will not be
     * included in the RASC artifacts.
     * 
     * @param aResource
     *            the resource that is to be delivered to the MicroServices.
     * @return the list of MicroService destinations. May be <code>null</code>.
     */
    private MicroService[] getDestinations(IResource aResource) {
        if (BPELN2Utils.BP_OUTPUTFOLDER_NAME.equals(aResource.getName())) {
            return PERascContributor.BP_DESTINATION_SERVICES;
        }
        if (BPELN2Utils.PF_OUTPUTFOLDER_NAME.equals(aResource.getName())) {
            return PERascContributor.PF_DESTINATION_SERVICES;
        }
        IContainer parent = aResource.getParent();
        return (parent == null) ? null : getDestinations(parent);
    }

    /**
     * @param builtTargets
     * @return The total number of target resources built for the project.
     */
    private int countBpelResources(Collection<BuildTargetSet> builtTargets) {
        int count = 0;

        for (BuildTargetSet buildTargetSet : builtTargets) {
            count += buildTargetSet.getTargetResources().size();
        }
        return count;
    }

    /**
     * Adds the given collection of shared resource references to the RASC (via
     * the given RascWriter).
     * 
     * @param aWriter
     *            the accessor for the RASC.
     * @param aSharedResources
     *            the collection of shared resource referencs.
     */
    private void addSharedResources(RascWriter aWriter,
            Collection<ParticipantSharedResource> aSharedResources) {
        if (aSharedResources.isEmpty()) {
            return;
        }

        // create a list of RASC manifest PropertyValues for each resource
        /*
         * Sid ACE-4005 only output one resource dependency per resource name. For each resource instance we will create
         * a key 'type::name' and ensure that we only add a resource-dependency of given type and name once.
         */
        Set<String> alreadyDone = new HashSet<String>();

        ArrayList<PropertyValue> attrValue = new ArrayList<>();
        for (ParticipantSharedResource sharedResource : aSharedResources) {

            RestServiceResource restService = sharedResource.getRestService();
            if (restService != null) {
                String key = REST_SERVICE_SHARED_RSRC_TYPE + "::" + restService.getResourceName();
                if (!alreadyDone.contains(key)) {
                    alreadyDone.add(key);

                    PropertyValue property = new PropertyValue();
                    property.setValue(restService.getResourceName());
                    property.setAttribute(SHARED_RSRC_TYPE_PROP, REST_SERVICE_SHARED_RSRC_TYPE);
                    property.setAttribute(SHARED_RSRC_DESC_PROP, restService.getDescription());
                    attrValue.add(property);
                    continue;
                }
            }

            EmailResource email = sharedResource.getEmail();
            if (email != null) {
                String key = EMAIL_SERVICE_SHARED_RSRC_TYPE + "::" + email.getInstanceName();
                if (!alreadyDone.contains(key)) {
                    alreadyDone.add(key);

                    PropertyValue property = new PropertyValue();
                    property.setValue(email.getInstanceName());
                    property.setAttribute(SHARED_RSRC_TYPE_PROP, EMAIL_SERVICE_SHARED_RSRC_TYPE);
                    attrValue.add(property);
                    continue;
                }
            }

            /* Sid ACE-6506 (ACE-7229) Add JDBC type resource dependencies. */
            JdbcResource jdbc = sharedResource.getJdbc();
            if (jdbc != null) {
                String key = JDBC_SERVICE_SHARED_RSRC_TYPE + "::" + jdbc.getInstanceName();
                if (!alreadyDone.contains(key)) {
                    alreadyDone.add(key);

                    PropertyValue property = new PropertyValue();
                    property.setValue(jdbc.getInstanceName());
                    property.setAttribute(SHARED_RSRC_TYPE_PROP, JDBC_SERVICE_SHARED_RSRC_TYPE);
                    attrValue.add(property);
                    continue;
                }

            }

        }

        if (attrValue.isEmpty()) {
            return;
        }

        // add the references to the RASC manifest
        PropertyValue[] values =
                attrValue.toArray(new PropertyValue[attrValue.size()]);
        aWriter.setManifestAttribute(SHARED_RESOURCE_MANIFEST_ATTR, values);
    }

    /**
     * Find all SharedResource instances within the given project. Returns an
     * empty collection if none are found.
     * 
     * @param aProject
     *            the project to search.
     * @return the collection of shared resource found.
     */
    private Collection<ParticipantSharedResource> getSharedResources(
            IProject aProject) {
        Collection<Package> packages = getProcessPackages(aProject);
        if ((packages == null) || (packages.isEmpty())) {
            return Collections.emptyList();
        }

        Collection<ParticipantSharedResource> result = new ArrayList<>();
        for (Package pkg : packages) {
            result.addAll(getSharedResources(pkg.getParticipants()));

            for (Process process : pkg.getProcesses()) {
                result.addAll(getSharedResources(process.getParticipants()));
            }
        }

        return result;
    }

    /**
     * Return all instances of the given Participants that reference shared
     * resources. If there are no shared resource references the return value
     * will be an empty collection.
     * 
     * @param aParticipants
     *            the participants to search.
     * @return the collection of shared resource references.
     */
    private Collection<ParticipantSharedResource> getSharedResources(
            Collection<Participant> aParticipants) {
        if ((aParticipants == null) || (aParticipants.isEmpty())) {
            return Collections.emptyList();
        }

        Collection<ParticipantSharedResource> result = new ArrayList<>();
        ParticipantSharedResource sharedResource;
        for (Participant participant : aParticipants) {
            sharedResource = SharedResourceUtil
                    .getParticipantSharedResource(participant);
            if (sharedResource != null) {
                result.add(sharedResource);
            }
        }

        return result;
    }

    /**
     * Looks through the resources within the given project and returns any
     * Process Packages it may find. If there are no Process Packages the return
     * value will be an empty collection.
     * 
     * @param aProject
     *            the Project whose resources are to be searched.
     * @return the collection of Process Packages found.
     */
    private Collection<Package> getProcessPackages(IProject aProject) {
        return getProcessPackages(SpecialFolderUtil
                .getAllDeepResourcesInSpecialFolderOfKind(aProject,
                        N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                        N2PENamingUtils.XPDL_FILE_EXTENSION,
                        false));
    }

    /**
     * Looks through the given resources and returns any Process Packages it may
     * find. If there are no Process Packages the return value will be an empty
     * collection.
     * 
     * @param aResources
     *            the Resources to be searched.
     * @return the collection of Process Packages found.
     */
    private Collection<Package> getProcessPackages(
            Collection<IResource> aResources) {
        if ((aResources == null) || (aResources.isEmpty())) {
            return Collections.emptyList();
        }

        Collection<Package> result = new ArrayList<>();
        for (IResource resource : aResources) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
            Package xpdlPackage = (Package) wc.getRootElement();
            if (xpdlPackage == null) {
                continue;
            }

            result.add(xpdlPackage);
        }

        return result;
    }

    /**
     * Adds an artifact, if required, that lists the references to those project
     * dependencies that contain BOM assets.
     * 
     * @param aWriter
     *            the RascWriter to which the artifact is to be written.
     * @param aAppSummary
     *            the summary of the deployed application; from which
     *            dependencies can be derived.
     * @throws Exception
     */
    private void addBomDependencies(RascWriter aWriter,
            RascAppSummary aAppSummary) throws Exception {
        // look for any direct/indirect dependencies containing BOMs
        Collection<RascAppSummary> bomDependencies =
                getBomDependencies(aAppSummary, null);

        // if none found
        if (bomDependencies.isEmpty()) {
            return;
        }

        // output JSON to the RASC
        OutputStream output = aWriter.addContent("dataInfo.di", //$NON-NLS-1$
                "data-dependencies", //$NON-NLS-1$
                "dataInfo.di", //$NON-NLS-1$
                PERascContributor.BOM_DATAINFO_SERVICES);
        try {
            write(aAppSummary, bomDependencies, output);
        } finally {
            output.close();
        }
    }

    /**
     * Recursively traverses the given RascDependency objects to return those
     * that contain a BOM asset.
     * 
     * @param aDependencies
     *            the dependencies to be visited.
     * @param aVisited
     *            the on-going collection of visited dependencies (to prevent
     *            circular references).
     * @return the collection of dependencies with BOM assets.
     * @throws CoreException
     */
    private Collection<RascAppSummary> getBomDependencies(
            RascAppSummary aApplication,
            Collection<RascAppSummary> aVisited) throws CoreException {
        if (aVisited == null) {
            aVisited = new HashSet<>();
        }

        // if we've already seen this application
        else if (!aVisited.add(aApplication)) {
            // don't bother checking it again
            return Collections.emptyList();
        }

        Collection<RascAppSummary> result = new ArrayList<>();

        // if the application has BOM assets
        if (aApplication.hasAssetType(
                com.tibco.xpd.bom.resources.ui.Activator.BOM_ASSET_ID)) {
            // add it to the result
            result.add(aApplication);
        }

        // check all nested projects
        for (RascAppSummary dependency : aApplication.getReferencedProjects()) {
            // visit nested dependencies
            result.addAll(getBomDependencies(dependency, aVisited));
        }

        return result;
    }

    /**
     * Writes the given project dependency references, as a JSON packet, to the
     * given OutputStream.
     * 
     * @param aDependencies
     *            the project dependency references.
     * @param aOutput
     *            the OutputStream to which to write the references.
     * @throws IOException
     */
    private void write(RascAppSummary aAppSummary,
            Collection<RascAppSummary> aDependencies,
            OutputStream aOutput) throws IOException {
        // build a collection of the dependency references
        Collection<Map<String, String>> references = new ArrayList<>();
        for (RascAppSummary dependency : aDependencies) {
            Map<String, String> entry = new HashMap<>();
            entry.put("projectId", dependency.getInternalName()); //$NON-NLS-1$
            entry.put("version", dependency.getDependencyRange().toString()); //$NON-NLS-1$

            references.add(entry);
        }

        // put within a Map for JSON marshalling
        Map<String, Collection<?>> jsonMap = new HashMap<>();
        jsonMap.put("dataProjectDependencies", references); //$NON-NLS-1$

        // SId ACE-5646 Pretty print for consistency across all JOSN files
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonMap);
        aOutput.write(json.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * A simple data object to record a process package that is to be included in the RASC. A process package will be
     * included if any process for that package has been included in the RASC.
     */
    private static class PackageDeployment {
        // the package to be included
        private Package deployedPackage;

        // the location to which the process package artifact is to be placed in the RASC
        private IContainer location;

        // the micro-services to which the process package artifact is to be delivered.
        private Set<MicroService> destinations;

        public PackageDeployment(Package aPackage, IContainer aLocation, MicroService[] aDestinations) {
            deployedPackage = aPackage;
            location = aLocation;
            destinations = new HashSet<>(Arrays.asList(aDestinations));
        }

        /**
         * @return the package being deployed.
         */
        public Package getPackage() {
            return deployedPackage;
        }

        /**
         * @return the location
         */
        public IContainer getLocation() {
            return location;
        }

        /**
         * @return the destinations
         */
        public MicroService[] getDestinations() {
            return destinations.toArray(new MicroService[destinations.size()]);
        }

        /**
         * Updates the list of destination services to include the given list.
         * 
         * @param aServices
         *            the destinations to be added to this entry.
         */
        public void addDestinations(MicroService[] aServices) {
            for (MicroService service : aServices) {
                destinations.add(service);
            }
        }

        public String getPath() {
            IContainer location = getLocation();
            String result = location.getProjectRelativePath().toString();
            /*
             * The temp build folder is Project/.processOut; in the RASC we just want "processOut"
             */
            if (result.charAt(0) == '.') {
                result = result.substring(1);
            }

            // append a name to the file path
            result += "/package.pkg"; //$NON-NLS-1$
            return result;
        }

        @Override
        public String toString() {
            return getPath();
        }
    }
}
