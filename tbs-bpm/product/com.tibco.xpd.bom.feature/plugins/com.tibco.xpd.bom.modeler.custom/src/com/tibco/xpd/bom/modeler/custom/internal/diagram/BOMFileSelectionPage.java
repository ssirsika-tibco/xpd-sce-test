/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.diagram;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.dialogs.FileSelectionBrowserControl;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Wizard page for the {@link NewDiagramWizard} to select a BOM file.
 * 
 * @author njpatel
 * 
 */
public class BOMFileSelectionPage extends AbstractXpdWizardPage {

    private final IStructuredSelection selection;

    private IFile selectedBomFile;

    private FileSelectionBrowserControl browserControl;

    protected BOMFileSelectionPage(String pageName,
            IStructuredSelection selection) {
        super(pageName);
        this.selection = selection;
        browserControl = new FileSelectionBrowserControl();
        browserControl.setListener(new FileBrowserListener());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new FillLayout());

        browserControl.createControl(root);
        browserControl.addFilter(new XpdNatureProjectsOnly());
        browserControl.addFilter(new SpecialFoldersOnlyFilter(Collections
                .singleton(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)));
        browserControl.addFilter(new FileExtensionInclusionFilter(Collections
                .singleton(BOMResourcesPlugin.BOM_FILE_EXTENSION)));
        browserControl.addFilter(new NoFileContentFilter());

        /*
         * XPD-7888: Calling browserControl.setInitialSelection() somehow could
         * not determine the parent of the passed selection(bom file in the
         * tree). Hence using browserControl
         * .forceSetInitialSelection(selection.getFirstElement()); as it is
         * designed to solve such issues.
         */
        if (selection != null && selection.getFirstElement() != null) {
            browserControl
                    .forceSetInitialSelection(selection.getFirstElement());
        }

        setControl(root);
        setPageComplete(validatePage());
    }

    /**
     * Get the file selected in this page.
     * 
     * @return
     */
    public IFile getSelectedFile() {
        return selectedBomFile;
    }

    /**
     * Validate the input on this page.
     * 
     * @return
     */
    private boolean validatePage() {

        Object selection = browserControl.getSelection();
        selectedBomFile = null;
        if (selection instanceof IFile) {
            selectedBomFile = (IFile) selection;
            setErrorMessage(null);
            return true;
        }

        setErrorMessage(Messages.BOMFileSelectionPage_noBOMSelected_error_message);
        return false;
    }

    /**
     * Listener to selection changes in the file browser.
     * 
     * @author njpatel
     * 
     */
    private class FileBrowserListener implements Listener {

        @Override
        public void handleEvent(Event event) {
            setPageComplete(validatePage());

            // Update the wizard with the selected model so that the next page
            // is updated
            Model model = null;
            IFile selectedFile = getSelectedFile();
            if (selectedFile != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(selectedFile);
                if (wc instanceof BOMWorkingCopy) {
                    EObject root = wc.getRootElement();
                    if (root instanceof Model) {
                        model = (Model) root;
                    }
                }
            }

            ((NewDiagramWizard) getWizard()).setSelection(model);
        }
    }

}
