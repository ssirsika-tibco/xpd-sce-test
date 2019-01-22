/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.fragments;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.osgi.framework.Bundle;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.FragmentsContributor;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.util.PackageVersionProblemException;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;

/**
 * @author rsomayaj
 * 
 */
public abstract class BaseXpdlTemplatesContributor extends FragmentsContributor {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * 
     */
    private static final String SVN_FOLDER_DIR = ".svn"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String DEST_ENV = "DEST_ENV"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String FEEDBACK_RECT = "FEEDBACK_RECT"; //$NON-NLS-1$

    /**
     * 
     * @return
     */
    protected abstract String getTemplatesLocation();

    /**
     * 
     * @return
     */
    protected abstract String getSampleDescription();

    /**
     * 
     * @return
     */
    protected abstract String getFragmentFileName();

    /**
     * 
     * @return
     */
    protected abstract String getContributorId();

    /**
     * 
     * @return
     */
    protected abstract String getSystemFragmentsLocation();

    @Override
    public void initialize(IProgressMonitor monitor) throws CoreException {
        IPath sysFragPath = new Path(getTemplatesLocation());
        try {
            URL fragUrl =
                    FileLocator.find(getPluginBundle(), sysFragPath, null);
            if (fragUrl != null) {
                URL fileURL = FileLocator.toFileURL(fragUrl);
                File rootFile = new File(fileURL.getFile());
                IFragmentCategory rootCategory =
                        FragmentsActivator.getDefault()
                                .getRootCategory(getContributorId());
                createFragmentInternals(rootCategory, rootFile, monitor);
            } else {
                LOG.warn("Fragment frag URL null");
            }
        } catch (CoreException coreException) {
            LOG.error(coreException);
        } catch (IOException ioException) {
            LOG.error(ioException);
        }

    }

    /**
     * The bundle which contains the fragments.
     * 
     * @return
     */
    protected Bundle getPluginBundle() {
        return ProcessFragmentsPlugin.getDefault().getBundle();
    }

    /**
     * @param rootCategory
     * @param rootFile
     * @param monitor
     * @throws CoreException
     * @throws IOException
     */
    private void createFragmentInternals(IFragmentCategory category,
            File rootFile, IProgressMonitor monitor) throws CoreException,
            IOException {
        List<File> fileList = Arrays.asList(rootFile.listFiles());
        SubProgressMonitor subProgressMonitor =
                new SubProgressMonitor(monitor, 2);
        IProject tempProject =
                createHiddenProject(".tempHidden", subProgressMonitor);
        subProgressMonitor.worked(1);
        tempProject.open(subProgressMonitor);
        subProgressMonitor.worked(2);
        try {
            for (File file : fileList) {
                if (isFragmentFolder(file)) {
                    File fragmentFile = getFragmentFile(file);
                    String fragmentData = null;
                    try {
                        fragmentData =
                                getFragmentData(tempProject,
                                        fragmentFile,
                                        subProgressMonitor);
                    } catch (TransformerConfigurationException e) {
                        LOG.error(e);
                    } catch (SAXException e) {
                        LOG.error(e);
                    } catch (ParserConfigurationException e) {
                        LOG.error(e);
                    } catch (PackageVersionProblemException e) {
                        LOG.error(e);
                    }
                    if (fragmentData != null) {
                        createFragment(category,
                                getPlatformRelativePath(file.getAbsolutePath()),
                                file.getName(),
                                getSampleDescription(),
                                fragmentData,
                                null,
                                monitor);
                    }

                } else if (file.isDirectory()) {
                    if (SVN_FOLDER_DIR.equals(file.getName())) {
                        continue;
                    }
                    IFragmentCategory fragmentCategory =
                            createCategory(category,
                                    getPlatformRelativePath(file
                                            .getAbsolutePath()),
                                    file.getName(),
                                    getSampleDescription(),
                                    monitor);
                    createFragmentInternals(fragmentCategory, file, monitor);
                }
            }
        } finally {
            deleteHiddenProject(tempProject, new SubProgressMonitor(monitor, 1));
        }
    }

    /**
     * @param tempProject
     * @param subProgressMonitor
     */
    private void deleteHiddenProject(IProject tempProject,
            SubProgressMonitor subProgressMonitor) {
        if (tempProject.exists()) {
            try {
                tempProject.delete(true, subProgressMonitor);
            } catch (CoreException e) {
                LOG.error("Cannot delete temporary hidden project");
            }
            subProgressMonitor.worked(1);
        }
    }

    /**
     * @param subProgressMonitor
     * @param string
     * @return
     */
    private IProject createHiddenProject(String projectName,
            SubProgressMonitor subProgressMonitor) {
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        try {
            if (!project.exists()) {
                project.create(subProgressMonitor);
                subProgressMonitor.worked(1);
            }
        } catch (Exception exception) {
            LOG.error("Cannot create temporary project");
        }

        return project;
    }

    /**
     * Returns the migrated fragment string
     * 
     * @param project
     * 
     * @param fragData
     * @param stream
     * @param subProgressMonitor
     * 
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws PackageVersionProblemException
     * @throws CoreException
     * @throws TransformerConfigurationException
     */
    private String getFragmentData(IProject project, File fragmentFile,
            SubProgressMonitor subProgressMonitor) throws SAXException,
            IOException, ParserConfigurationException,
            TransformerConfigurationException, CoreException,
            PackageVersionProblemException {
        int fileFormatVersion = XpdlMigrate.getFileFormatVersion(fragmentFile);

        // check if the fragment file xpdl format version is the latest, if not
        // migrate it
        if (fileFormatVersion != Integer
                .parseInt(XpdlMigrate.FORMAT_VERSION_ATT_VALUE)) {
            final IFile file = project.getFile("Test.xpdl");

            final FileInputStream stream = new FileInputStream(fragmentFile);
            if (!file.exists()) {

                SubProgressMonitor monitor =
                        new SubProgressMonitor(subProgressMonitor, 1);
                WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

                    @Override
                    protected void execute(IProgressMonitor monitor)
                            throws CoreException, InvocationTargetException,
                            InterruptedException {
                        try {
                            file.create(stream, IResource.FORCE, monitor);
                        } catch (CoreException e) {
                            LOG.error(e,
                                    "Temporary Fragment File Cannot be created");
                        }

                    }
                };
                try {
                    op.run(monitor);
                } catch (Exception e) {
                    LOG.error(e, "Unable to create temp fragment file: ");
                }
                monitor.worked(1);
            } else {
                System.err.println("TEST FILE ALREADY EXISTS!!");
            }
            if (file.exists()) {
                XpdlMigrate.migrate(file);

                ResourceSet rSet = new ResourceSetImpl();
                Resource resource =
                        rSet.getResource(URI.createPlatformResourceURI(file
                                .getFullPath().toString(), false), true);
                String migratedData = null;
                if (resource != null) {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    try {
                        resource.save(os, Collections.EMPTY_MAP);
                    } catch (IOException e) {
                        LOG.error(e);
                    }
                    migratedData = os.toString();
                    closeOutputStream(os);
                }
                closeInputStream(stream);
                SubProgressMonitor monitor =
                        new SubProgressMonitor(subProgressMonitor, 1);
                try {
                    file.delete(true, monitor);
                } catch (CoreException e) {
                    LOG.error(e, "Exception while deleting fragment file");
                }
                if (migratedData != null) {
                    return migratedData;
                }
            }
        } else {
            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            FileInputStream source = new FileInputStream(fragmentFile);
            String fragData = null;

            if (source != null) {
                byte[] buffer = new byte[512];
                int bytesRead;

                while ((bytesRead = source.read(buffer)) != -1) {
                    oStream.write(buffer, 0, bytesRead);
                }

                fragData = oStream.toString();
            }
            closeInputStream(source);
            closeOutputStream(oStream);
            return fragData;
        }
        return null;
    }

    /**
     * @param file
     * @return
     */
    private File getFragmentFile(File file) {
        List<File> fileList = Arrays.asList(file.listFiles());
        for (File f : fileList) {
            if (getFragmentFileName().equals(f.getName())) {
                return f;
            }
        }

        return null;
    }

    /**
     * @param rootFile
     * @return
     */
    private boolean isFragmentFolder(File rootFile) {
        if (rootFile.isDirectory()) {
            List<String> list = Arrays.asList(rootFile.list());
            if (list.contains(getFragmentFileName())) {
                return true;
            }
        }
        return false;
    }

    private String getPlatformRelativePath(String absolutePath) {
        String platformString = null;
        StringBuffer strBuffer = new StringBuffer(absolutePath);
        int indexOf = strBuffer.lastIndexOf(getSystemFragmentsLocation());
        if (indexOf != -1) {
            platformString = strBuffer.substring(indexOf, strBuffer.length());
        }
        if (platformString == null) {
            platformString = absolutePath;
        }
        return platformString;
    }

    /**
     * @param stream
     */
    private void closeOutputStream(ByteArrayOutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }

    /**
     * @param source
     */
    private void closeInputStream(FileInputStream source) {
        if (source != null) {
            try {
                source.close();
            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }

    @Override
    public void updateContent(IFragmentCategory rootCategory,
            String fragmentVersion, IProgressMonitor monitor)
            throws CoreException {
        Collection<IFragmentElement> children = rootCategory.getChildren();
        for (IFragmentElement element : children) {
            if (element.isSystem()) {
                // Migrate xpdl files would be a better option i think rather
                // than delete and insert.
                deleteFragmentElement(element, monitor);
            }
        }
        initialize(monitor);
    }

    @Override
    public UpdateResult updateContent(IFragment fragment, String fragmentVersion) {
        return null;
    }

    @Override
    public void copyToClipboard(IFragment fragment) {

        Package fragmentPackage =
                Xpdl2ProcessorUtil.getFragmentPackage(fragment
                        .getLocalizedData());
        Process fragmentProcess =
                Xpdl2ProcessorUtil.getFragmentProcess(fragmentPackage);
        Collection<EObject> fragObjects =
                Xpdl2ProcessorUtil.getProcessElements(fragmentProcess);

        Collection<EObject> copiedFragObjs = EcoreUtil.copyAll(fragObjects);
        if (fragObjects.size() > 0) {
            Clipboard clp =
                    new Clipboard(PlatformUI.getWorkbench().getDisplay());
            try {
                // Add both the process fragment transfer type AND the EMF
                // object collection to clipboard.
                FragmentLocalSelectionTransfer frag =
                        FragmentLocalSelectionTransfer.getTransfer();
                frag.getProperties().put(FEEDBACK_RECT,
                        Xpdl2ProcessorUtil.getFeedbackRectangles(fragment));
                frag.getProperties().put(DEST_ENV,
                        Xpdl2ProcessorUtil.getDestinationEnvs(fragment));

                LocalSelectionTransfer lt =
                        LocalSelectionTransfer.getTransfer();
                frag.setSelection(new StructuredSelection(copiedFragObjs));
                clp.setContents(new Object[] { copiedFragObjs, lt },
                        new Transfer[] { frag, frag });

            } finally {
                clp.dispose();
            }

        }

    }
}
