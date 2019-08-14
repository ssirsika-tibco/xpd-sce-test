/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.bom.resources.internal.businessdata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.AbstractIncrementalProjectBuilder;

/**
 * Business Data Version builder that updates the last build timestamp if the
 * project has changed.
 * 
 * @author njpatel
 */
public class BusinessDataVersionBuilder extends
        AbstractIncrementalProjectBuilder {

    /**
     * Regular expression string to detect line feed without carriage return.In
     * other words, it will not match '\r\n' but will match '\n',' \n','abc\n'
     * etc.
     */
    private static Pattern LF_WITHOUT_CR = Pattern.compile("(?<!\\r)\\n"); //$NON-NLS-1$

    public static final String ID = BOMResourcesPlugin.PLUGIN_ID
            + ".businessDataVersionBuilder"; //$NON-NLS-1$

    private final ResourceSet rSet;

    public BusinessDataVersionBuilder() {
        rSet = new ResourceSetImpl();
        /*
         * Update the local resource set with all registered EMF packages
         */
        Registry registry =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet().getPackageRegistry();
        for (Entry<String, Object> entry : registry.entrySet()) {
            rSet.getPackageRegistry().put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @see com.tibco.xpd.validation.AbstractIncrementalProjectBuilder#doBuild(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param kind
     * @param args
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    protected IProject[] doBuild(int kind, Map<?, ?> args,
            IProgressMonitor monitor) throws CoreException {

        monitor.beginTask("Calculate Business Data project checksum", 2);

        IProject project = getProject();
        IResourceDelta delta = getDelta(project);

        boolean isAffectingDelta = false;
        if (kind == FULL_BUILD || isFullBuildRequired(delta)) {
            isAffectingDelta = true;
        } else {
            DeltaVisitor visitor = new DeltaVisitor();
            delta.accept(visitor);
            isAffectingDelta = visitor.isAffectingDelta();
        }

        monitor.worked(1);

        if (isAffectingDelta) {
            updateTimestamp(project);
        }

        monitor.done();

        return null;
    }

    /**
     * Update the change timestamp if the checksum calculation on the project
     * differs from the previous checksum.
     * 
     * @param project
     */
    private void updateTimestamp(IProject project) {

        String newChecksum = calculateChecksum(project);
        if (newChecksum != null) {

            String previousChecksum =
                    BusinessDataBuildVersionUtil.getCheckSum(project);
            if (!newChecksum.equals(previousChecksum)) {
                /*
                 * Update the checksum value and also the timestamp for this
                 * project
                 */
                BusinessDataBuildVersionUtil.setCheckSum(project, newChecksum);
                BusinessDataBuildVersionUtil.updateBuildTimeStamp(project);
            }
        }
    }

    /**
     * Calculate the checksum of this project.
     * 
     * @param project
     * @return
     */
    private String calculateChecksum(IProject project) {

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        if (config != null && config.getProjectDetails() != null) {

            final CRC32 crc = new CRC32();
            ProjectDetails details = config.getProjectDetails();

            if (details.getId() != null) {
                crc.update(details.getId().getBytes());
            }

            if (details.getVersion() != null) {
                crc.update(details.getVersion().getBytes());
            }

            final List<IFile> bomFiles = getBomFiles(project);

            /*
             * For each BOM file load the resource and remove the Diagram (UI)
             * model and then checksum the remainder of the model.
             */
            for (IFile bomFile : bomFiles) {

                Resource resource =
                        rSet.getResource(URI.createPlatformResourceURI(bomFile
                                .getFullPath().toString(), true), true);

                if (resource != null) {

                    List<Object> itemsToRemove = new ArrayList<Object>();
                    for (Object content : resource.getContents()) {

                        if (content instanceof Diagram) {

                            itemsToRemove.add(content);
                        }
                    }

                    resource.getContents().removeAll(itemsToRemove);

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    try {

                        resource.save(os, null);
                        /*
                         * XPD-7992 : Convert bytes to string and replace all \n
                         * by \r\n
                         */
                        String decodedString =
                                new String(os.toByteArray(),
                                        StandardCharsets.UTF_8);
                        decodedString =
                                LF_WITHOUT_CR.matcher(decodedString)
                                        .replaceAll("\r\n"); //$NON-NLS-1$
                        crc.update(decodedString
                                .getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {

                        BOMResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Unable to generate a stream from '%s' to calculate checksum.",
                                                bomFile.getFullPath()
                                                        .toString()));
                    } finally {

                        try {

                            os.close();
                        } catch (IOException e) {
                            // Do nothing
                        }

                        resource.unload();
                        rSet.getResources().remove(resource);
                    }

                    /*
                     * XPD-7509: App upgrade fails as the checksum is not
                     * updated on bom file rename. So update/calculate the
                     * checksum on bom file name
                     */
                    crc.update(bomFile.getName().getBytes());
                }
            }
            return String.valueOf(crc.getValue());
        }
        return null;
    }

    /**
     * Get all the direct/indirect referenced BOMs from BOM special folder(s)
     * for the given project.
     * 
     * @param project
     * @return List of BOM files, empty list if no BOMs are found in the
     *         project.
     */
    private List<IFile> getBomFiles(IProject project) {

        List<IFile> models = new ArrayList<IFile>();
        List<IResource> resources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                                BOMResourcesPlugin.BOM_FILE_EXTENSION,
                                true);

        for (IResource res : resources) {

            if (res instanceof IFile) {

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(res);
                if (wc != null && wc.getRootElement() instanceof Model) {

                    models.add((IFile) res);
                }

                /* XPD-7498: Get direct/indirect dependent boms */
                Set<IResource> deepDependencies =
                        WorkingCopyUtil.getDeepDependencies(res);
                for (IResource iResource : deepDependencies) {

                    if (iResource instanceof IFile) {

                        models.add((IFile) iResource);
                    }
                }
            }
        }

        return models;
    }

    private class DeltaVisitor implements IResourceDeltaVisitor {

        private boolean isAffectingDelta;

        /**
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         * 
         * @param delta
         * @return
         * @throws CoreException
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {

            IResource resource = delta.getResource();
            if (resource instanceof IFolder) {
                /*
                 * XPD-7465: It used to check for non generated bom special
                 * folders only. This check is no longer valid with the
                 * introduction of Service Descriptors folder in business data
                 * project. The delta must be of interest if a BOM file has been
                 * added, removed or modified in user defined bom or generated
                 * bom
                 */
                for (IResourceDelta child : delta
                        .getAffectedChildren(IResourceDelta.ALL_WITH_PHANTOMS)) {

                    IResource childres = child.getResource();
                    /*
                     * If not a BOM or a change was detected in a BOM file but
                     * content has not changed then continue looking at next
                     * resource
                     */
                    if (!BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(childres
                            .getFileExtension())
                            || (child.getKind() == IResourceDelta.CHANGED && (child
                                    .getFlags() & IResourceDelta.CONTENT) == 0)) {

                        continue;
                    }
                    isAffectingDelta = true;

                    /* No point looking at any other change */
                    return false;
                }
            }
            return true;
        }

        /**
         * @return <code>true</code> if this delta shows that the checksum
         *         should be checked for change in project.
         */
        public boolean isAffectingDelta() {
            return isAffectingDelta;
        }

    }
}
