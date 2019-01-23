/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.scriptdescriptor.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Class;
import org.osgi.framework.Version;

import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.n2.scriptdescriptor.Activator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * @author Jan Arciuchiewicz
 */
public class ScriptDescriptorUtils {

    private static final Logger LOG = Activator.getDefault().getLogger();

    public static final String SCRIPT_DESCRIPTOR_FILE_EXT = ".scripts"; //$NON-NLS-1$

    /**
     * Returns collection of all processes with N2 destination environment.
     * 
     * @param project
     *            the context project.
     * @return collection of all processes with N2 destination environment.
     */
    public static Collection<Process> getN2Processes(IProject project) {
        Collection<Process> processes = new ArrayList<Process>();
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {
            EList<IFolder> packageFolders =
                    config.getSpecialFolders()
                            .getEclipseIFoldersOfKind(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
            try {
                SpecialFolderVisitor specialFolderVisitor =
                        new SpecialFolderVisitor(processes);
                for (IFolder folder : packageFolders) {
                    folder.accept(specialFolderVisitor);
                }
            } catch (CoreException e) {
                LOG.error(e);
            }
        }

        return processes;
    }

    private static class SpecialFolderVisitor implements IResourceVisitor {
        private static final Object XPDL_EXTENSION = "xpdl"; //$NON-NLS-1$

        private final Collection<Process> processes;

        /**
         * Constructor takes the reference to the collection of processes to
         * which processes will be added during traversal.
         */
        public SpecialFolderVisitor(Collection<Process> proceses) {
            this.processes = proceses;
        }

        /** {@inheritDoc} */
        public boolean visit(IResource resource) throws CoreException {
            if (resource.getType() == IResource.FILE) {
                IFile file = (IFile) resource;
                String fileExtension = file.getFileExtension();
                if (fileExtension != null
                        && XPDL_EXTENSION.equals(fileExtension.toLowerCase())) {
                    WorkingCopy wc =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(resource);
                    if (wc instanceof Xpdl2WorkingCopyImpl) {
                        Package xpdlPackage = (Package) wc.getRootElement();
                        if (xpdlPackage != null) {
                            for (Process p : xpdlPackage.getProcesses()) {
                                if (p != null && XPDLUtils.hasN2Destination(p)) {
                                    processes.add(p);
                                }
                            }
                        } else {
                            LOG.error(String
                                    .format("Could not load XPDL WC for '%s'.", //$NON-NLS-1$
                                            file.getName()));
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }

    /**
     * Returns the map of default save options for XML files used by EMF
     * resources serialization
     * 
     * @return the map of default save options for XML files used by EMF
     *         resources serialization.
     * @deprecated Use {@link N2Utils#getDefaultXMLSaveOptions()} instead.
     */
    @Deprecated
    public static Map<Object, Object> getDefaultXMLSaveOptions() {
        return N2Utils.getDefaultXMLSaveOptions();
    }

    /**
     * given a Project returns the script descriptor file in the staging area.
     * if it does not exists then returns null.
     * 
     * @param folder
     * @return
     */
    public static IFile getScriptDescriptorFromStagingArea(IProject project) {
        IFile file =
                N2PENamingUtils.getFileFromStagingArea(project,
                        new Path(ScriptDescriptorUtils
                                .getScriptDescriptorFileName(project)));
        if (file.isAccessible()) {
            return file;
        }
        return null;
    }

    /**
     * @param project
     * @param fileName
     * @return
     */
    public static IFile getScriptDescriptorFromStagingArea(IProject project,
            String fileName) {

        IFolder compositeOut =
                N2PENamingUtils.getModuleOutputFolder(project, false);
        IFile file = compositeOut.getFile(new Path(fileName));

        return ((file != null) && file.isAccessible()) ? file : null;
    }

    public static String getScriptDescriptorFileName(IProject project) {
        String projectId = ProjectUtil.getProjectId(project);
        String fileName =
                projectId + ScriptDescriptorUtils.SCRIPT_DESCRIPTOR_FILE_EXT;
        return fileName;
    }

    public static String getScriptDescriptorFileName(IProject project,
            String fileExt) {
        String projectId = ProjectUtil.getProjectId(project);
        String fileName = projectId + fileExt;
        return fileName;
    }

    // All below.
    // TODO JA Revisit later. Copied to get rid of cyclic dependency.
    public static final String PROPERTY_QUALIFIER = "qualifier"; //$NON-NLS-1$

    public static String getUpdatedBundleVersion(String bundleVersion,
            String timeStamp) {
        String createNewVersion = createNewVersion(bundleVersion, timeStamp);
        return createNewVersion;
    }

    private static String createNewVersion(String oldVersionStr,
            String timeStamp) {
        String newVersionStr = oldVersionStr;
        Version parseVersion = Version.parseVersion(oldVersionStr);
        String qualifier = parseVersion.getQualifier();
        if (isOldTimeStamp(qualifier) || PROPERTY_QUALIFIER.equals(qualifier)) {
            Version newVersion =
                    new Version(parseVersion.getMajor(),
                            parseVersion.getMinor(), parseVersion.getMicro(),
                            timeStamp);
            newVersionStr = newVersion.toString();
        }
        return newVersionStr;
    }

    /**
     * revisit this & see if we can come up with some logic to figure out
     * whether it is a date
     * 
     * @param qualifier
     * @return
     */
    private static boolean isOldTimeStamp(String qualifier) {
        boolean isOldTS = false;
        if (qualifier != null && qualifier.length() == 12) {
            try {
                Long.parseLong(qualifier);
                isOldTS = true;
            } catch (NumberFormatException nfe) {
            }
        }
        return isOldTS;
    }

    /**
     * @param outFolder
     * @return
     */
    public static URI getScriptDescriptorResourceURI(IFolder outFolder,
            IProject project) {
        return getScriptDescriptorResourceURI(outFolder,
                project,
                SCRIPT_DESCRIPTOR_FILE_EXT);
    }

    /**
     * @param outFolder
     * @return
     */
    public static URI getScriptDescriptorResourceURI(IFolder outFolder,
            IProject project, String fileExt) {
        final String filePath =
                outFolder
                        .getFullPath()
                        .append(ScriptDescriptorUtils
                                .getScriptDescriptorFileName(project, fileExt))
                        .toPortableString();
        return URI.createPlatformResourceURI(filePath, true);
    }

    // TODO remove once BDS API is available OR modify to call BDS API
    public static String getCACScriptingName(Class bomClass) {
        // <scriptingName>cac_com_example_claimmodel_Claim</scriptingName>
        StringBuffer cacScriptingName =
                new StringBuffer(bomClass.getQualifiedName().replace('.', '_')
                        .replace("::", "_"));
        cacScriptingName.insert(0, "cac_");
        return cacScriptingName.toString();
    }

    // TODO remove once BDS API is available OR modify to call BDS API
    public static String getCACCanonicalName(Class bomClass) {
        // <canonicalClassName>com.example.claimmodel.si.cac.impl.ClaimCACImpl</canonicalClassName>
        StringBuffer cacScriptingName =
                new StringBuffer(bomClass.getPackage().getQualifiedName()
                        .replaceAll("::", "."));
        cacScriptingName.append(".si.cac.impl." + bomClass.getName());
        cacScriptingName.append("CACImpl");
        return cacScriptingName.toString();
    }

}
