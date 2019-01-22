/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.pickers;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Dialog to pick an om special folder
 * 
 * @author glewis
 * 
 */
public class PackageFolderPicker extends BaseObjectPicker implements
        ISelectionStatusValidator {

    public PackageFolderPicker(Shell parent) {
        super(parent);
        setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
        // Only include BPM nature projects
        addFilter(new XpdNatureProjectsOnly());
        // Include no files
        Set extensions = new HashSet();
        addFilter(new FileExtensionInclusionFilter(extensions));
        // Include only packages folders
        Set<String> packageFolders = Collections
                .singleton(OMResourcesActivator.OM_SPECIAL_FOLDER_KIND);
        addFilter(new SpecialFoldersOnlyFilter(packageFolders));

        setTitle(Messages.PackageFolderPicker_dialogTitle);
        setMessage(Messages.PackageFolderPicker_dialogMessage);
        setValidator(this);
    }

    @Override
    public void setInput(Object input) {
        if (input instanceof EObject) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) input);
            input = wc.getEclipseResources().get(0).getProject();
        }
        super.setInput(input);
    }

    // Validate the selection
    public IStatus validate(Object[] selection) {

        if (selection != null && selection.length == 1) {
            SpecialFolder sFolder = null;
            String location = ""; //$NON-NLS-1$

            if (selection[0] instanceof SpecialFolder) {
                sFolder = (SpecialFolder) selection[0];
                location = sFolder.getLocation();

            } else if (selection[0] instanceof IFolder) {
                IFolder folder = (IFolder) selection[0];
                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(folder.getProject());

                if (config != null) {
                    sFolder = config.getSpecialFolders().getFolderContainer(
                            folder);

                    location = folder.getProjectRelativePath().toString();
                }
            }

            if (sFolder != null
                    && sFolder.getKind().equals(
                            OMResourcesActivator.OM_SPECIAL_FOLDER_KIND)) {
                IFolder folder = sFolder.getFolder();
                IProject project = null;

                if (folder != null) {
                    project = folder.getProject();
                }

                String szStatusMessage = MessageFormat.format(
                        Messages.PackageFolderPicker_statusMessageSuccess,
                        new Object[] { (project != null ? project.getName()
                                + IPath.SEPARATOR : "") //$NON-NLS-1$
                                + location });

                return new Status(Status.OK, XpdResourcesUIActivator.ID,
                        Status.OK, szStatusMessage, null);
            }
        }
        return new Status(Status.ERROR, XpdResourcesUIActivator.ID,
                Status.ERROR, Messages.PackageFolderPicker_statusMessageError,
                null);
    }
}
