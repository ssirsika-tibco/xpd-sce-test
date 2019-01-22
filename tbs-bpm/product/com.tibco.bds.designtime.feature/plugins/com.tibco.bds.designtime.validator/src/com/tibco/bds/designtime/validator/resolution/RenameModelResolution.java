/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.bds.designtime.validator.resolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.bds.designtime.validator.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.resolution.AbstractRenameResolution;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * Quick-fix resolution to rename duplicate names in {@link Model Models}.
 * 
 * @author njpatel
 * 
 */
public class RenameModelResolution extends AbstractRenameResolution implements
        IResolution {

    public RenameModelResolution() {
        super(Messages.RenameModelResolution_setModelName_label);
    }

    @Override
    protected IInputValidator getValidator() {
        // Get all model names
        IndexerItem[] allModels =
                BOMResourcesPlugin.getDefault().getIndexerService()
                        .getAllModels();
        final Set<String> names = new HashSet<String>();

        for (IndexerItem item : allModels) {
            names.add(item.getName());
        }

        return new IInputValidator() {

            @Override
            public String isValid(String newText) {
                if (newText.length() > 0) {
                    if (names.contains(newText)) {
                        return Messages.RenameModelResolution_modelNameAlreadyExists_shortdesc;
                    }
                } else {
                    return Messages.RenameModelResolution_invalidModelName_shortdesc;
                }
                return null;
            }

        };
    }

    @Override
    public void run(IMarker marker) throws ResolutionException {

        String[] duplicateResources = null;
        Properties info = ValidationUtil.getAdditionalInfo(marker);

        if (info != null) {
            Object value = info.get(ValidationActivator.LINKED_RESOURCE);
            if (value instanceof String) {
                duplicateResources = ((String) value).split(","); //$NON-NLS-1$
            }
        }

        super.run(marker);

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        final List<IFile> filesToTouch = new ArrayList<IFile>();
        if (duplicateResources != null && duplicateResources.length > 0) {
            for (String value : duplicateResources) {
                URI uri = URI.createURI(value);

                if (uri.isPlatformResource()) {
                    String path = uri.toPlatformString(true);
                    if (path != null) {
                        IResource member = root.findMember(path);
                        if (member instanceof IFile) {
                            filesToTouch.add((IFile) member);
                        }
                    }
                }
            }
        }

        /*
         * Cause the build of the other files that had the duplicate name to
         * rebuild their (generated) projects as they may be missing
         */
        if (!filesToTouch.isEmpty()) {
            try {
                if (filesToTouch.size() == 1) {
                    filesToTouch.get(0).touch(new NullProgressMonitor());
                } else {
                    root.getWorkspace().run(new IWorkspaceRunnable() {

                        @Override
                        public void run(IProgressMonitor monitor)
                                throws CoreException {
                            for (IFile file : filesToTouch) {
                                file.touch(monitor);
                            }
                        }

                    },
                            new NullProgressMonitor());
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }

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

        return super.select(marker);
    }

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        Command cmd = null;
        /*
         * get the resource from the marker. if the resource is not a generated
         * file and has the same name as the model name then we rename the file
         * with the new model name entered by the user. if the file name is
         * different from the model name, then we just rename the model but not
         * the file name.
         */
        IResource resource = marker.getResource();

        if (editingDomain != null && target instanceof NamedElement) {
            NamedElement elem = (NamedElement) target;
            String modelName = elem.getName();

            InputDialog dlg =
                    new InputDialog(Display.getCurrent().getActiveShell(),
                            getRenameTitle(), getDialogMessage(),
                            elem.getName(), getValidator());

            if (dlg.open() == InputDialog.OK) {

                final String newModelName = dlg.getValue();
                /*
                 * we are interested only in files that are not derived (i.e.
                 * that are not generated)
                 */
                if (resource instanceof IFile
                        && !BOMValidationUtil.isGeneratedBom(resource)) {
                    final IFile iFile = (IFile) resource;

                    final IPath srcPath = resource.getFullPath();

                    if (resource.getName().endsWith("." //$NON-NLS-1$
                            + BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                        String resourceName =
                                resource.getName().substring(0,
                                        resource.getName().length() - 4);
                        /*
                         * check if the model name and bom file name without the
                         * extension are same. if they are same only then we
                         * will rename the bom file name with the new model name
                         */
                        if (null != modelName
                                && resourceName.equalsIgnoreCase(modelName)) {
                            if (null != newModelName) {
                                IPath destPath =
                                        srcPath.removeLastSegments(1)
                                                .append("/" //$NON-NLS-1$
                                                        + newModelName
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
                                /* get the working copy of the new file */
                                IFile file =
                                        ResourcesPlugin.getWorkspace()
                                                .getRoot().getFile(destPath);
                                WorkingCopy workingCopy =
                                        WorkingCopyUtil.getWorkingCopy(file);
                                /* get the model from the newly renamed file */
                                if (null != workingCopy) {
                                    EObject rootElement =
                                            workingCopy.getRootElement();
                                    /* rename the model */
                                    if (rootElement instanceof NamedElement) {
                                        cmd =
                                                SetCommand
                                                        .create(editingDomain,
                                                                rootElement,
                                                                UMLPackage.eINSTANCE
                                                                        .getNamedElement_Name(),
                                                                dlg.getValue());
                                    }
                                }
                            }
                        } else {
                            /* just rename the model, don't rename the file */
                            cmd =
                                    SetCommand.create(editingDomain,
                                            elem,
                                            UMLPackage.eINSTANCE
                                                    .getNamedElement_Name(),
                                            dlg.getValue());
                        }
                    }
                }
            }
        }

        return cmd;

    }

}
