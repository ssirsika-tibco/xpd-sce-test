/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.process.om.Activator;
import com.tibco.xpd.process.om.actions.RefactorToOMAction;
import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;
import com.tibco.xpd.xpdl2.Package;

/**
 * This class extends <code>AbstractRefactorXPDLToOMWizard</code> to provide a
 * wizard that refactors XPDL files into an Organisation Model.
 * <p>
 * For more details on using this export wizard class see
 * <code>AbstractRefactorXPDLToOMWizard</code>.
 * </p>
 * 
 * @see AbstractXPDLSelectionWizard
 * 
 * @author glewis
 * 
 */
public class RefactorXPDLToOMWizard extends AbstractXPDLSelectionWizard {

    // input file extension
    private String inputFileExt = "xpdl"; //$NON-NLS-1$

    private static final String[] outputFileExtFilter = new String[] { "om" }; //$NON-NLS-1$

    protected RefactorToOMAction action;

    protected IStructuredSelection selection;

    /**
     * 
     */
    public RefactorXPDLToOMWizard() {
        super(
                true,
                Messages.AbstractRefactorXPDLToOMWizard_refactorSuccessDialog_title,
                Messages.AbstractRefactorXPDLToOMWizard_refactorSuccessDialog_message);
        setWindowTitle(Messages.RefactorXPDLToOMWizard_title);
        setInputFileExt(inputFileExt);
    }

    /**
     * Set the extension of the refactored file, e.g. "xpdl". Do not include the
     * extension separator.
     * 
     * @param ext
     */
    public void setInputFileExt(String ext) {

        ext = ext.trim();
        // Remove any leading period
        if (ext.charAt(0) == '.') {
            ext = ext.substring(1);
        }

        inputFileExt = ext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    @Override
    public boolean canFinish() {
        boolean bRet = true;

        if (bRet
                && (getSelectedFiles() == null || getSelectedFiles().size() == 0)
                || getDestinationFolder() == null
                || getDestinationFolder().getFullPath().toPortableString()
                        .length() == 0 || !pageIO.isPageComplete()) {
            bRet = false;
        }

        return bRet;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.process.om.wizards.AbstractRefactorXPDLToOMWizard#
     * getRefactorFileExt()
     */
    @Override
    public String getRefactorFileExt() {
        return inputFileExt;
    }

    /**
     * @return
     */
    public String[] getOutputFileExtensionFilter() {
        return outputFileExtFilter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.process.om.wizards.AbstractRefactorXPDLToOMWizard#getFilters
     * ()
     */
    @Override
    protected ViewerFilter[] getFilters() {
        ViewerFilter[] filters = new ViewerFilter[4];

        // Only show xpdl files
        Set extensions = Collections.singleton("xpdl"); //$NON-NLS-1$
        filters[0] = new FileExtensionInclusionFilter(extensions);

        // Only show packages folders
        Set<String> projFolderKind = Collections
                .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
        filters[1] = new SpecialFoldersOnlyFilter(projFolderKind);

        // Only include XPD nature projects
        filters[2] = new XpdNatureProjectsOnly();

        // Don't drill down into the xpdl files
        filters[3] = new NoFileContentFilter();

        return filters;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.process.om.wizards.AbstractRefactorXPDLToOMWizard#
     * performOperation(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {
        final String defaultOMName = getDefaultOMName();
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                synchronized (RefactorXPDLToOMWizard.class) {
                    if (action == null) {
                        action = new RefactorToOMAction();
                    }
                }
                selection = getSelectedFiles();
                action.selectionChanged(selection);
                String relativeRoot = getDestinationFolder().getFullPath()
                        .toPortableString();
                action.setOutputPath(relativeRoot + IPath.SEPARATOR
                        + defaultOMName);

                // run the transformation action
                if (action.isEnabled()) {
                    boolean isNeedProjectDependency = false;
                    Iterator<IFile> iter = selection.iterator();
                    // look at each selected xpdl file in turn
                    while (iter.hasNext()) {
                        IFile xpdlFile = iter.next();

                        // get the corresponding xpdl2 package and look at all
                        // its participants
                        WorkingCopy wc = XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(xpdlFile);
                        Package xpdl2Package = (Package) wc.getRootElement();

                        // see if needs a project dependency
                        IProject referencingProject = xpdlFile.getProject();
                        IProject referencedProject = getDestinationFolder()
                                .getProject();
                        boolean isProjectReferenced = false;
                        try {
                            if (referencingProject != null
                                    && referencedProject != null
                                    && ProjectUtil.isProjectReferenced(
                                            referencingProject,
                                            referencedProject)) {
                                isProjectReferenced = true;
                            }
                        } catch (CoreException e) {
                            Activator.getDefault().getLogger().error(e);
                        }
                        if (!isProjectReferenced) {
                            isNeedProjectDependency = true;
                            break;
                        }
                    }
                    isRunnable = true;
                    if (isNeedProjectDependency) {
                        getShell().getDisplay().syncExec(new Runnable() {
                            public void run() {
                                MessageBox messageBox = new MessageBox(
                                        getShell(), SWT.ICON_QUESTION | SWT.YES
                                                | SWT.NO);
                                messageBox
                                        .setText(Messages.RefactorXPDLToOMWizard_projectRefDialog_title);
                                messageBox
                                        .setMessage(Messages.RefactorXPDLToOMWizard_projectRefDialog_message);
                                if (messageBox.open() != SWT.YES) {
                                    isRunnable = false;
                                }
                            }
                        });

                    }
                    if (isRunnable) {
                        action.run();
                        if (action.getErrors().size() > 0) {
                            String message = ""; //$NON-NLS-1$
                            Iterator<Exception> errIter = action.getErrors()
                                    .iterator();
                            while (errIter.hasNext()) {
                                Exception exception = errIter.next();
                                message += exception.toString();
                                message += "\r\n"; //$NON-NLS-1$                                                 
                            }
                            CoreException coreException = new CoreException(
                                    new Status(IStatus.ERROR,
                                            Activator.PLUGIN_ID, message));
                            InvocationTargetException invocationTargetException = new InvocationTargetException(
                                    coreException);
                            invocationTargetException.setStackTrace(action
                                    .getErrors().get(0).getStackTrace());
                            throw invocationTargetException;
                        }
                    }
                }
            }
        };
        try {
            getContainer().run(false, true, op);
        } catch (InterruptedException e) {
            throw e;
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof CoreException) {
                throw (CoreException) e.getTargetException();
            } else {
                CoreException coreException = new CoreException(
                        new Status(
                                IStatus.ERROR,
                                Activator.PLUGIN_ID,
                                e.getTargetException().toString()
                                        + "\r\n\r\n" + Messages.RefactorXPDLToOMWizard_generalHelp_message, e)); //$NON-NLS-1$
                throw coreException;
            }
        }
    }

}
