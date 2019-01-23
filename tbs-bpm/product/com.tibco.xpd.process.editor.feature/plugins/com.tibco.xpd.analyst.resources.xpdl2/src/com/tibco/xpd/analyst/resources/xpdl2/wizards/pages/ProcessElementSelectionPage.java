/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.wizards.pages;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.BpmContentLabelProvider;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Project, package and process selection page used by the New Wizards. This
 * extends <code>PackageSelectionPage</code>.
 * 
 * @see PackageSelectionPage
 * 
 * @author njpatel
 */
public class ProcessElementSelectionPage extends PackageSelectionPage {

    private static final int NO_OF_COLUMNS = 3;

    private Text txtElement;

    private Button btnElementBrowse;

    private boolean wantProcess = true;

    private boolean wantProcessInterface = true;

    // selectedElement can be either Process or Process Interface
    private NamedElement selectedElement;

    public ProcessElementSelectionPage(boolean wantProcess,
            boolean wantProcessInterface) {
        super();

        this.wantProcess = wantProcess;
        this.wantProcessInterface = wantProcessInterface;

        if (wantProcess && !wantProcessInterface) {
            setTitle(Messages.ProcessSelectionPage_TITLE);
            setDescription(Messages.ProcessSelectionPage_DESC);

        } else if (wantProcessInterface && !wantProcess) {
            setTitle(Messages.ProcessInterfaceSelectionPage_TITLE);
            setDescription(Messages.ProcessInterfaceSelectionPage_DESC);

        } else {
            setTitle(Messages.ProcessOrInterfaceSelectionPage_TITLE);
            setDescription(Messages.ProcessOrInterfaceSelectionPage_DESC);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#init
     * (org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IStructuredSelection selection) {

        super.init(selection);

        if (selection != null && !selection.isEmpty()) {
            Object selectedElement = selection.getFirstElement();

            if (selectedElement instanceof AbstractAssetGroup
                    || selectedElement instanceof EObject) {
                // Process asset group / EObject selected so should be able to
                // get process if any

                if (selectedElement instanceof AbstractAssetGroup) {
                    AbstractAssetGroup group =
                            (AbstractAssetGroup) selectedElement;

                    // If the parent of the group is a Process then set
                    // selectedProcess
                    selectedElement = group.getParent();

                } else if (!(selectedElement instanceof Process)
                        && !(selectedElement instanceof ProcessInterface)) {
                    // If not an asset group and not a process or inteface then
                    // maybe its a sibling (i.e. a data field / participant in
                    // same process etc). So try the parent object's container.
                    if (selectedElement instanceof EObject) {
                        selectedElement =
                                ((EObject) selectedElement).eContainer();
                    }
                }

                // Having resolved the selectedElement as best we can, validate
                // it for type of object we want.
                if ((selectedElement instanceof Process) && wantProcess) {
                    this.selectedElement = (NamedElement) selectedElement;

                } else if ((selectedElement instanceof ProcessInterface)
                        && wantProcessInterface) {
                    this.selectedElement = (NamedElement) selectedElement;

                }
            }
        }

        setPageComplete(packagesFolderContainer != null && packageFile != null
                && selectedElement != null);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageSelectionPage
     * #getEContainer()
     */
    public EObject getEContainer() {
        // Return the selected process
        return selectedElement;
    }

    private ModifyListener procOrIfcListener = new ModifyListener() {
        public void modifyText(ModifyEvent e) {
            setPageComplete(validatePage());
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.analyst.resources.xpdl2.wizards.pages.
     * PackageOrProcessSelectionPage
     * #createOptionSelection(org.eclipse.swt.widgets.Composite)
     */
    protected void createOptionSelection(Composite parent) {
        // Process selection
        Composite elementContainer = new Composite(parent, SWT.NULL);
        elementContainer.setLayout(new GridLayout(NO_OF_COLUMNS, false));
        elementContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        String labelText = null;

        if (wantProcess && !wantProcessInterface) {
            labelText = Messages.ProcessSelectionPage_SelectedObject_label;
        } else if (wantProcessInterface && !wantProcess) {
            labelText =
                    Messages.ProcessInterfaceSelectionPage_SelectedObject_label;
        } else {
            labelText =
                    Messages.ProcessOrInterfaceSelectionPage_SelectedObject_label;
        }

        createLabel(elementContainer, labelText, 30);

        txtElement = createText(elementContainer);
        txtElement.setEditable(false);
        addProcessOrInterfaceModifyListeners(procOrIfcListener);

        btnElementBrowse = new Button(elementContainer, SWT.NONE);
        btnElementBrowse.setText(Messages.ProcessSelectionPage_3);
        btnElementBrowse.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                browseForProcessElement();
            }
        });

        if (selectedElement != null) {
            txtElement.setText(Xpdl2ModelUtil
                    .getDisplayNameOrName(selectedElement));
        }
    }

    /**
     * 
     * @param modifyListener
     */
    public void addProcessOrInterfaceModifyListeners(
            ModifyListener modifyListener) {
        if (txtElement != null) {
            txtElement.addModifyListener(modifyListener);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#validatePage
     * ()
     */
    protected boolean validatePage() {
        // Call the super version to validate the main part of this page
        boolean ret = super.validatePage();

        // Validate the process
        if (ret) {
            String selectedElementName = txtElement.getText();

            if (selectedElementName == null || selectedElementName.length() < 1) {

                if (wantProcess && !wantProcessInterface) {
                    setErrorMessage(Messages.ProcessSelectionPage_MustSelect_longdesc);
                } else if (wantProcessInterface && !wantProcess) {
                    setErrorMessage(Messages.ProcessInterfaceSelectionPage_MustSelect_longdesc);
                } else {
                    setErrorMessage(Messages.ProcessOrInterfaceSelectionPage_MustSelect_longdesc);
                }

                ret = false;
            } else {
                // Try to get the selected process
                selectedElement =
                        getProcessElement(packageFile, selectedElementName);

                if (selectedElement == null) {
                    String msg;

                    if (wantProcess && !wantProcessInterface) {
                        msg =
                                MessageFormat
                                        .format(Messages.ProcessSelectionPage_CannotAccessObject_longdesc,
                                                new Object[] { selectedElementName });
                    } else if (wantProcessInterface && !wantProcess) {
                        msg =
                                MessageFormat
                                        .format(Messages.ProcessInterfaceSelectionPage_CannotAccessObject_longdesc,
                                                new Object[] { selectedElementName });
                    } else {
                        msg =
                                MessageFormat
                                        .format(Messages.ProcessOrInterfaceSelectionPage_CannotAccessObject_longdesc,
                                                new Object[] { selectedElementName });
                    }

                    setErrorMessage(msg);
                    return false;
                }
            }
        }

        // If valid then clear error messages
        if (ret) {
            setErrorMessage(null);
        }

        return ret;
    }

    /**
     * Display the process picker
     * 
     */
    protected void browseForProcessElement() {
        if (packageFile != null) {
            WorkingCopy wc = getWorkingCopy(packageFile);

            if (wc != null) {
                ProcessGroup processGroup =
                        new ProcessGroup(wc.getRootElement());

                ProcessInterfaceGroup interfaceGroup =
                        new ProcessInterfaceGroup(wc.getRootElement());

                if (processGroup != null || interfaceGroup != null) {
                    ElementListSelectionDialog dialog =
                            new ElementListSelectionDialog(getShell(),
                                    new BpmContentLabelProvider());

                    String dialogTitleText = null;
                    String dialogMessageText = null;

                    if (wantProcess && !wantProcessInterface) {
                        dialogTitleText =
                                Messages.ProcessSelectionPage_BrowseDialog_title;
                        dialogMessageText =
                                Messages.ProcessSelectionPage_BrowseDialog_shortdesc;
                    } else if (wantProcessInterface && !wantProcess) {
                        dialogTitleText =
                                Messages.ProcessInterfaceSelectionPage_BrowseDialog_title;
                        dialogMessageText =
                                Messages.ProcessInterfaceSelectionPage_BrowseDialog_shortdesc;
                    } else {
                        dialogTitleText =
                                Messages.ProcessOrInterfaceSelectionPage_BrowseDialog_title;
                        dialogMessageText =
                                Messages.ProcessOrInterfaceSelectionPage_BrowseDialog_shortdesc;
                    }

                    dialog.setTitle(dialogTitleText);
                    dialog.setMessage(dialogMessageText);

                    List childrenElements = new ArrayList();

                    if (processGroup != null && wantProcess) {
                        childrenElements.addAll(processGroup.getChildren());
                    }

                    if (interfaceGroup != null && wantProcessInterface) {
                        childrenElements.addAll(interfaceGroup.getChildren());
                    }

                    dialog.setElements(childrenElements.toArray());
                    dialog.setHelpAvailable(false);

                    if (selectedElement != null) {
                        dialog
                                .setInitialSelections(new Object[] { selectedElement });
                    }

                    if (dialog.open() == Dialog.OK) {
                        // Update the selected process
                        selectedElement =
                                (NamedElement) dialog.getFirstResult();

                        if (selectedElement != null) {
                            // MR 39259 - begin
                            // txtElement.setText(WorkingCopyUtil
                            // .getText(selectedElement));
                            txtElement.setText(Xpdl2ModelUtil
                                    .getDisplayNameOrName(selectedElement));
                            // MR 39259 - end
                        } else {
                            txtElement.setText(""); //$NON-NLS-1$
                        }
                    }

                } else {
                    MessageDialog
                            .openError(getShell(),
                                    Messages.ProcessSelectionPage_CantBuildBrowseList_title,
                                    Messages.ProcessSelectionPage_CantBuildBrowseList_message);
                }
            }
        }

        return;
    }

    /**
     * Get the process or process Interface with the given name
     * 
     * @param resource
     * @param xpdlProcessName
     * 
     * @return <code>NamedElement</code> with the given process name, if not
     *         found then <strong>null</strong> will be returned.
     */
    private NamedElement getProcessElement(IResource resource,
            String xpdlProcessName) {
        NamedElement processElement = null;

        if (resource != null && xpdlProcessName != null) {
            WorkingCopy wc = getWorkingCopy(resource);

            if (wc != null) {
                List processElements = new ArrayList();

                // Get the process group of the Package
                if (wantProcess) {
                    ProcessGroup pg = new ProcessGroup(wc.getRootElement());
                    processElements.addAll(pg.getChildren());
                    pg.dispose();
                }

                if (wantProcessInterface) {
                    ProcessInterfaceGroup processInterfaceGroup =
                            new ProcessInterfaceGroup(wc.getRootElement());
                    processElements.addAll(processInterfaceGroup.getChildren());
                    processInterfaceGroup.dispose();
                }

                for (Iterator iter = processElements.iterator(); iter.hasNext()
                        && processElement == null;) {
                    NamedElement element = (NamedElement) iter.next();
                    // MR 39259 - begin
                    // if (element != null
                    // && element.getName().equals(xpdlProcessName))

                    if (element != null
                            && xpdlProcessName.equals(Xpdl2ModelUtil
                                    .getDisplayNameOrName(element))) {
                        processElement = element;
                    }
                    // MR 39259 - end
                }
            }
        }

        return processElement;
    }
}
