/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;

import com.tibco.xpd.bom.gen.api.GeneratorData;
import com.tibco.xpd.bom.gen.api.GeneratorData.BuildType;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Delete refactoring participant that will remove generated resources of a BOM
 * file being deleted.
 * 
 * @author njpatel
 * 
 */
public class BOMGenDeleteParticipant extends DeleteParticipant {

    protected final List<IFile> bomFilesToClean;

    private BOMGenerator2Extension[] strategies;

    /**
     * BOM File delete refactaring participant that will remove any generated
     * artifacts.
     */
    public BOMGenDeleteParticipant() {
        strategies = BOMGenerator2ExtensionHelper.getInstance().getExtensions();
        bomFilesToClean = new ArrayList<IFile>();
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {
        return new RefactoringStatus();
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        return new GeneratorChange(bomFilesToClean);
    }

    @Override
    public String getName() {
        return Messages.BOMGenDeleteParticipant_name;
    }

    @Override
    protected boolean initialize(Object element) {
        if (strategies != null && strategies.length > 0) {
            try {
                List<IFile> selection = null;
                if (element instanceof IProject) {
                    selection = getAllBOMFiles((IProject) element);
                } else if (element instanceof IFolder) {
                    String kind =
                            SpecialFolderUtil
                                    .getSpecialFolderKind((IFolder) element);
                    if (kind != null
                            && kind.equals(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
                        selection = getAllBOMFiles((IFolder) element);
                    }
                } else if (element instanceof IFile) {
                    selection = new ArrayList<IFile>(1);
                    selection.add((IFile) element);
                }

                if (selection != null && !selection.isEmpty()) {
                    bomFilesToClean.addAll(selection);
                    List<IFile> filesAlreadyProcessed = new ArrayList<IFile>();
                    for (IFile file : selection) {
                        bomFilesToClean.addAll(getDependentResources(file,
                                filesAlreadyProcessed));

                    }

                    return true;
                }
            } catch (CoreException e) {
                // TODO
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Get all BOM files from the given project. This will only return the files
     * from the BOM special folder.
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    private List<IFile> getAllBOMFiles(IProject project) throws CoreException {
        List<IFile> files = new ArrayList<IFile>();

        List<SpecialFolder> allFolders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        if (allFolders != null) {
            for (SpecialFolder sf : allFolders) {
                files.addAll(getAllBOMFiles(sf.getFolder()));
            }
        }

        return files;
    }

    /**
     * Get all BOM files from the given folder. It will be assumed that the
     * folder is a BOM special folder.
     * 
     * @param folder
     * @return
     * @throws CoreException
     */
    private List<IFile> getAllBOMFiles(IFolder folder) throws CoreException {
        BOMProxyVisitor visitor = new BOMProxyVisitor();
        if (folder != null && folder.isAccessible()) {
            folder.accept(visitor, 0);
        }
        return visitor.getFiles();
    }

    /**
     * Get all the BOM resources that depend on the given file. This will return
     * the complete dependency tree.
     * 
     * @param file
     * @return
     */
    private List<IFile> getDependentResources(IFile file,
            List<IFile> filesAlreadyProcessed) {
        List<IFile> files = new ArrayList<IFile>();
        Collection<IResource> affectedResources =
                WorkingCopyUtil.getAffectedResources(file);
        if (affectedResources != null && !filesAlreadyProcessed.contains(file)) {
            filesAlreadyProcessed.add(file);
            for (IResource resource : affectedResources) {
                if (resource instanceof IFile && !files.contains(resource)
                        && isBOMModel((IFile) resource)) {
                    files.add((IFile) resource);
                }
            }

            if (!files.isEmpty()) {
                List<IFile> dependencies = new ArrayList<IFile>();
                for (IFile dependent : files) {
                    dependencies.addAll(getDependentResources(dependent,
                            filesAlreadyProcessed));
                }
                if (!dependencies.isEmpty()) {
                    files.addAll(dependencies);
                }
            }
        }

        return files;
    }

    /**
     * Check if the given file has the BOM file extension.
     * 
     * @param file
     * @return
     */
    private boolean isBOMModel(IFile file) {
        if (file != null
                && file.getFileExtension() != null
                && file.getFileExtension()
                        .equals(BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
            // If the BOM file is in the BOM special folder then return true
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(file.getProject());
            if (config != null) {
                SpecialFolder sf =
                        config.getSpecialFolders().getFolderContainer(file);
                return sf != null
                        && sf.getKind()
                                .equals(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
            }
        }
        return false;
    }

    /**
     * The refactor change that will clean all generated artifacts of the BOM
     * resource being deleted.
     * 
     * @author njpatel
     * 
     */
    private class GeneratorChange extends Change {

        private List<IFile> resourcesToRemove;

        public GeneratorChange(List<IFile> bomFilesToClean) {
            this.resourcesToRemove = bomFilesToClean;
        }

        @Override
        public Object getModifiedElement() {
            return null;
        }

        @Override
        public String getName() {
            return Messages.BOMGenDeleteParticipant_change_name;
        }

        @Override
        public void initializeValidationData(IProgressMonitor pm) {
        }

        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            return new RefactoringStatus();
        }

        @Override
        public Change perform(IProgressMonitor pm) throws CoreException {
            if (resourcesToRemove != null) {
                for (BOMGenerator2Extension st : strategies) {
                    st.getGenerator().clean(resourcesToRemove,
                            new GeneratorData(BuildType.CLEAN,
                                    resourcesToRemove),
                            pm);
                }
            }
            return new NullChange();
        }
    }

    /**
     * Resource visitor that will find all BOM files.
     * 
     * @author njpatel
     * 
     */
    private class BOMProxyVisitor implements IResourceProxyVisitor {

        private final List<IFile> files;

        public BOMProxyVisitor() {
            files = new ArrayList<IFile>();
        }

        /**
         * Get all BOM files that were found.
         * 
         * @return
         */
        public List<IFile> getFiles() {
            return files;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IResourceProxyVisitor#visit(org.eclipse
         * .core.resources.IResourceProxy)
         */
        @Override
        public boolean visit(IResourceProxy proxy) throws CoreException {
            if (proxy.getType() == IResource.FILE) {
                if (proxy.getName().endsWith("." //$NON-NLS-1$
                        + BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                    IResource resource = proxy.requestResource();
                    if (resource instanceof IFile) {
                        files.add((IFile) resource);
                    }
                }
                return false;
            }
            return true;
        }
    }
}
