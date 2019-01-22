/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.bds.designtime.validator.resolution;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.bds.designtime.validator.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.refactoring.RenameBOMElementProcessor;
import com.tibco.xpd.bom.resources.ui.refactoring.RenameBOMElementWizard;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution to refactor duplicate names in {@link Model Models} for
 * factories in script tasks.
 * 
 * @author njpatel
 * 
 */
public class RefactorModelResolution extends AbstractWorkingCopyResolution
        implements IResolution {

    public RefactorModelResolution() {
    }

    @Override
    public void run(IMarker marker) throws ResolutionException {
        try {
            EObject target = getTarget(marker);
            IResource resource = marker.getResource();

            if (target instanceof NamedElement) {
                NamedElement namedElement = (NamedElement) target;
                /*
                 * get the model name to check later if it matches with the bom
                 * file name
                 */
                String modelName = namedElement.getName();

                if (namedElement != null) {

                    RenameBOMElementWizard refactoringWizard =
                            new RenameBOMElementWizard(namedElement);
                    RefactoringWizardOpenOperation op =
                            new RefactoringWizardOpenOperation(
                                    refactoringWizard);
                    try {
                        Shell activeShell =
                                Display.getDefault().getActiveShell();
                        op.run(activeShell,
                                Messages.RefactorModelResolution_RenameElement);
                        /*
                         * if the model name and bom file name are same, then
                         * rename the file
                         */
                        RenameBOMElementProcessor processor =
                                (RenameBOMElementProcessor) refactoringWizard
                                        .getRefactoring()
                                        .getAdapter(RenameBOMElementProcessor.class);
                        String newElementName = processor.getNewElementName();

                        if (resource instanceof IFile
                                && !BOMValidationUtil.isGeneratedBom(resource)) {
                            final IFile iFile = (IFile) resource;

                            final IPath srcPath = resource.getFullPath();
                            if (resource.getName().endsWith("." //$NON-NLS-1$
                                    + BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                                String resourceName =
                                        resource.getName()
                                                .substring(0,
                                                        resource.getName()
                                                                .length() - 4);
                                /*
                                 * check if the model name and bom file name
                                 * without the extension are same. if they are
                                 * same only then we will rename the bom file
                                 * name with the new model name
                                 */
                                if (modelName.equalsIgnoreCase(resourceName)) {

                                    IPath destPath =
                                            srcPath.removeLastSegments(1)
                                                    .append("/" //$NON-NLS-1$
                                                            + newElementName
                                                            + "." //$NON-NLS-1$
                                                            + BOMResourcesPlugin.BOM_FILE_EXTENSION);
                                    try {
                                        iFile.move(destPath,
                                                true,
                                                true,
                                                new NullProgressMonitor());
                                    } catch (CoreException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                }
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }

    }

    @Override
    protected EObject getTarget(IMarker marker) throws CoreException {
        EObject target = null;
        XpdProjectResourceFactory factory =
                XpdResourcesPlugin.getDefault()
                        .getXpdProjectResourceFactory(marker.getResource()
                                .getProject());
        WorkingCopy workingCopy = factory.getWorkingCopy(marker.getResource());
        if (workingCopy != null) {
            // Clear the uri cache
            workingCopy.clearUriCache();
            String location = (String) marker.getAttribute(IMarker.LOCATION);
            EObject rootObject = workingCopy.getRootElement();
            Resource resource = null;
            if (rootObject != null) {
                resource = rootObject.eResource();
            }
            if (resource != null) {
                target = workingCopy.resolveEObject(location);
            }
        }
        return target;
    }

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        return null;
    }

    /***
     * this quick fix must not be shown on boms/models from generated business
     * objects folder
     */
    @Override
    public boolean select(Object marker) {

        if (null != marker) {
            IResource resource = ((IMarker) marker).getResource();
            if (resource instanceof IFile
                    && BOMValidationUtil.isGeneratedBom(resource)) {
                return false;
            }
        }

        return true;

    }
}
