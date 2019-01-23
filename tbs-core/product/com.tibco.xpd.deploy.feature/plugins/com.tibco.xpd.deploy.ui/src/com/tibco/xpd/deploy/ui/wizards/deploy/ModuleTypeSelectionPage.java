/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Page used for selecting the module type and associated deployment wizard.
 * <p>
 * <i>Created: 30 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ModuleTypeSelectionPage extends WizardSelectionPage {

    /** Name of the page used to identify the page within a wizard. */
    public static final String PAGE_NAME = "ModulesTypeSelection"; //$NON-NLS-1$

    private static final String NAME_COLUMN = "Name"; //$NON-NLS-1$

    private TreeViewer modulesViewer;

    protected ModuleTypeSelectionPage() {
        super(PAGE_NAME);
        setTitle(Messages.ModuleTypeSelectionPage_Page_title);
        setDescription(Messages.ModuleTypeSelectionPage_Page_longdesc);
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(composite, getTitle());
        composite.setLayout(gridLayout);

        Group modulesGroup = new Group(composite, SWT.NULL);
        modulesGroup
                .setText(Messages.ModuleTypeSelectionPage_ModuleSelectionGroup_label);
        GridData gridData2 = new GridData(GridData.FILL_BOTH);
        modulesGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 1;
        modulesGroup.setLayout(paramGroupLayout);

        Tree modulesTable = new Tree(modulesGroup, SWT.SINGLE | SWT.BORDER);
        GridData gridData3 = new GridData(GridData.FILL_BOTH);
        modulesTable.setLayoutData(gridData3);

        modulesViewer = new TreeViewer(modulesTable);
        modulesViewer.setColumnProperties(new String[] { NAME_COLUMN });

        modulesViewer.setContentProvider(new ModuleTypesContentProvider());
        modulesViewer.setLabelProvider(new ModuleTypesLabelProvider());
        modulesViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        IStructuredSelection selection =
                                (IStructuredSelection) event.getSelection();
                        if (selection != null) {
                            Object obj = selection.getFirstElement();
                            if (obj instanceof IWizardNode) {
                                IWizardNode wizardNode = (IWizardNode) obj;
                                setSelectedNode(wizardNode);
                            }
                        }
                    }
                });
        modulesViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                IStructuredSelection selection =
                        (IStructuredSelection) event.getSelection();
                if (selection != null) {
                    Object obj = selection.getFirstElement();
                    if (obj instanceof IWizardNode) {
                        IWizardNode wizardNode = (IWizardNode) obj;
                        setSelectedNode(wizardNode);
                        IWizardPage page = getNextPage();
                        if (page != null) {
                            getWizard().getContainer().showPage(page);
                        }
                    }
                }
            }
        });
        modulesViewer.setInput(((DeployModuleWizard) getWizard()).getServer());
        init();
        setPageComplete(false);
        setControl(composite);

    }

    private void init() {
        // deployProjectModuleWizard.setServer(((DeployModuleWizard)getWizard()).getServer());
        // deployExternalModuleWizard.setServer(((DeployModuleWizard)getWizard()).getServer());
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
    public boolean isPageComplete() {
        boolean bRet = false;
        IWizardNode node = getSelectedNode();
        if (getSelectedNode() != null) {
            IWizard nestedWizard = node.getWizard();
            // every nested wizard should have at least one page.
            if (nestedWizard.getPageCount() > 0) {
                bRet = nestedWizard.canFinish();
            }
        }

        return bRet;
    }

}
