/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.process.om.Activator;
import com.tibco.xpd.process.om.actions.ImportParticipantsFromOMElementsAction;
import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.process.om.wizards.pages.OMSelectionWizardPageIO;
import com.tibco.xpd.process.om.wizards.pages.XPDLSelectionWizardPageIO;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * This class extends <code>AbstractRefactorXPDLToOMWizard</code> to provide a
 * wizard that imports om elements and creates participants from the om types within them.
 * These are then imported into the selected xpdl files.
 * <p>
 * For more details on using this export wizard class see
 * <code>AbstractXPDLSelectionWizard</code>.
 * </p>
 * 
 * @see AbstractXPDLSelectionWizard
 * 
 * @author glewis
 * 
 */
public class ImportParticipantsFromOMElementsWizard extends AbstractXPDLSelectionWizard {

    // input file extension
    private String inputFileExt = "xpdl"; //$NON-NLS-1$    

    protected ImportParticipantsFromOMElementsAction action;

    protected IStructuredSelection selection;
    
    protected OMSelectionWizardPageIO omPageIO = null;

    /**
     * 
     */
    public ImportParticipantsFromOMElementsWizard() {
        super(false,Messages.ImportParticipantsFromOMElementsWizard_importSuccessDialog_title, Messages.ImportParticipantsFromOMElementsWizard_importSuccessDialog_message);
        setWindowTitle(Messages.ImportParticipantsFromOMElementsWizard_title);
        setInputFileExt(inputFileExt);
    }

    /**
     * Set the extension of the imported file, e.g. "xpdl". Do not include the
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
    public boolean canFinish() {
        boolean bRet = true;

        if (bRet
                && (getSelectedFiles() == null || getSelectedFiles().size() == 0)
        ) {
            bRet = false;
        }
        
        if (bRet
                && (omPageIO.getSelectedFiles() == null || omPageIO.getSelectedFiles().size() == 0)
        ) {
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
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.process.om.wizards.AbstractRefactorXPDLToOMWizard#getFilters
     * ()
     */    
    protected ViewerFilter[] getXpdlFilters() {
        ViewerFilter[] filters = new ViewerFilter[4];

        // Only show xpdl files
        Set extensions = Collections.singleton("xpdl"); //$NON-NLS-1$
        filters[0] = new FileExtensionInclusionFilter(extensions);

        // Only show packages folders
        Set<String> projFolderKind =
                Collections
                        .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
        filters[1] = new SpecialFoldersOnlyFilter(projFolderKind);

        // Only include XPD nature projects
        filters[2] = new XpdNatureProjectsOnly();

        // Don't drill down into the xpdl files
        filters[3] = new NoFileContentFilter();

        return filters;
    }
        
    /**
     * @return
     */
    protected ViewerFilter[] getOmFilters() {
        ViewerFilter[] filters = new ViewerFilter[4];

        // Only show om files
        Set extensions = Collections.singleton("om"); //$NON-NLS-1$
        filters[0] = new FileExtensionInclusionFilter(extensions);

        // Only show packages folders
        Set<String> projFolderKind =
                Collections
                        .singleton("om");
        filters[1] = new SpecialFoldersOnlyFilter(projFolderKind);

        // Only include XPD nature projects
        filters[2] = new XpdNatureProjectsOnly();
        
        // Don't drill down into the om files
        filters[3] = new NoFileContentFilter();

        return filters;
    }
    
    /**
     * Get the <code>ViewerSorter</code> objects to apply to the tree viewer.
     * The default implementation sets the default sorter - sort content in
     * alphabetical order.
     * 
     * @return A <code>ViewerSorter</code> to apply to the tree viewer.
     */
    private ViewerSorter getSorter() {
        // Default implementation is to sort alphabetically. Subclass can
        // override this to provide a sorter.
        return new ViewerSorter();
    }
    
    /**
     * Get the tree viewer input. This is the workspace root by default.
     * Subclasses can override this to provide their own input.
     * 
     * @return Input for the tree viewer.
     */
    private Object getInput() {
        // Default implementation is to set the workspace root, subclasses can
        // override this to provide their own input.
        return ResourcesPlugin.getWorkspace().getRoot();
    }   
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    public void addPages() {
        // Add the om selection/filtered types page
        omPageIO = new OMSelectionWizardPageIO(getInput(),
                getSorter(), getOmFilters());
        omPageIO.setTitle(getWindowTitle());
        omPageIO.setDescription(windowMessage);
        addPage(omPageIO);
        
        // Add the xpdl selection page
        pageIO = new XPDLSelectionWizardPageIO(initialSelection, getInput(),
                getSorter(), getXpdlFilters(), bIsDestinationRequired);
        pageIO.setTitle(getWindowTitle());
        pageIO.setDescription(windowMessage);
        addPage(pageIO);
    }

    
    /* (non-Javadoc)
     * @see com.tibco.xpd.process.om.wizards.AbstractXPDLSelectionWizard#performOperation(org.eclipse.core.runtime.IProgressMonitor)
     */
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {
        final Object[] filteredOMTypes = omPageIO.getFilteredOMTypes();
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                synchronized (ImportParticipantsFromOMElementsWizard.class) {
                    if (action == null) {
                        action = new ImportParticipantsFromOMElementsAction(filteredOMTypes);
                    }
                }
                selection = getSelectedFiles();
                action.selectionChanged(selection);
                action.setOMFiles(omPageIO.getSelectedFiles());
                
                // run the transformation action
                if (action.isEnabled()) {
                    action.run();
                    if (action.getErrors().size() > 0) {
                        String message = ""; //$NON-NLS-1$
                        Iterator<Exception> errIter =
                                action.getErrors().iterator();
                        while (errIter.hasNext()) {
                            Exception exception = errIter.next();
                            message += exception.toString();
                            message += "\r\n"; //$NON-NLS-1$                                                 
                        }
                        CoreException coreException =
                                new CoreException(new Status(IStatus.ERROR,
                                        Activator.PLUGIN_ID, message));
                        InvocationTargetException invocationTargetException =
                                new InvocationTargetException(coreException);
                        invocationTargetException.setStackTrace(action
                                .getErrors().get(0).getStackTrace());
                        throw invocationTargetException;
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
                CoreException coreException =
                        new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        e.getTargetException().toString()
                                                + "\r\n\r\n" + Messages.ImportParticipantsFromOMElementsWizard_generalHelp_message, e)); //$NON-NLS-1$
                throw coreException;
            }
        }
    }

    @Override
    protected ViewerFilter[] getFilters() {
        return null;
    }    
}
