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
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Project, package and process selection page used by the New Wizards. This
 * extends <code>PackageSelectionPage</code>.
 * 
 * @see PackageSelectionPage
 * 
 * @author njpatel
 */
public class InterfaceMethodSelectionPage extends PackageSelectionPage {

    private static final int NO_OF_COLUMNS = 3;

    private Text txtElement;

    private Button btnElementBrowse;

    private boolean isProcessInterface = false;

    // selectedElement can be either Process or Process Interface
    private NamedElement selectedElement;

    private Text txtInterfaceMethod;

    private Button btnInterfaceMethodBrowse;

    public InterfaceMethodSelectionPage() {
        super();
        // Set the title and message for this page
        setTitle(Messages.ProcessInterfaceMethodSelectionPage_TITLE);
        setDescription(Messages.ProcessInterfaceMethodSelectionPage_DESC);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#init
     * (org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IStructuredSelection selection) {

        super.init(selection);

        /* Sid ACE-3341 if super set page incomplete then we should hnour that here too. */
        boolean superIsComplete = isPageComplete();

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
                    if (group.getParent() instanceof InterfaceMethod) {
                        this.selectedElement = (NamedElement) group.getParent();
                    }
                } else {
                    // If the selected item is a Process then set
                    // selectedProcess
                    if (selectedElement instanceof InterfaceMethod) {
                        this.selectedElement = (NamedElement) selectedElement;
                    } else {
                        // This must be the data field / participant object
                        // so it's container should be the Process
                        EObject eo = (EObject) selectedElement;

                        if (eo.eContainer() instanceof ProcessInterface) {
                            this.selectedElement =
                                    (NamedElement) eo.eContainer();
                        }
                    }
                }
            }
        }

        /* Sid ACE-3341 if super set page incomplete then we should honour that here too. */

        setPageComplete(packagesFolderContainer != null && packageFile != null
                && selectedElement != null && superIsComplete);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.PackageSelectionPage
     * #getEContainer()
     */
    @Override
    public EObject getEContainer() {
        // Return the selected process
        return selectedElement;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.analyst.resources.xpdl2.wizards.pages.
     * PackageOrProcessSelectionPage
     * #createOptionSelection(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createOptionSelection(Composite parent) {
        // Process selection
        Composite elementContainer = new Composite(parent, SWT.NULL);
        elementContainer.setLayout(new GridLayout(NO_OF_COLUMNS, false));
        elementContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        String labelText =
                Messages.ProcessInterfaceSelectionPage_SelectedObject_label;
        createLabel(elementContainer, labelText, 30);

        txtElement = createText(elementContainer);
        txtElement.setEditable(false);
        txtElement.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });

        btnElementBrowse = new Button(elementContainer, SWT.NONE);
        btnElementBrowse.setText(Messages.ProcessSelectionPage_3);
        btnElementBrowse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                browseForProcessIfcElement();
            }
        });

        String labelIfcMethod =
                Messages.InterfaceMethodSelectionPage_InterfaceMethodDialog_label;
        createLabel(elementContainer, labelIfcMethod, 30);

        txtInterfaceMethod = createText(elementContainer);
        txtInterfaceMethod.setEditable(false);
        txtInterfaceMethod.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });
        btnInterfaceMethodBrowse = new Button(elementContainer, SWT.NONE);
        btnInterfaceMethodBrowse.setText(Messages.ProcessSelectionPage_3);
        btnInterfaceMethodBrowse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                browseForInterfaceMethod();
            }
        });

        if (selectedElement instanceof InterfaceMethod) {
            txtInterfaceMethod.setText(Xpdl2ModelUtil
                    .getDisplayName(selectedElement));
            ProcessInterface processInterface =
                    ProcessInterfaceUtil.getProcessInterface(selectedElement);
            if (processInterface != null) {
                txtElement.setText(Xpdl2ModelUtil
                        .getDisplayName(processInterface));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CreationWizardContainerSelectionPage#validatePage
     * ()
     */
    @Override
    protected boolean validatePage() {
        // Call the super version to validate the main part of this page
        boolean ret = super.validatePage();

        // Validate the process
        if (ret) {
            String procIfcName = txtElement.getText();

            if (procIfcName == null || procIfcName.length() < 1) {
                setErrorMessage(Messages.InterfaceMethodSelectionPage_SpecifyProcIfc_shortdesc);
                ret = false;
            } else {
                // Try to get the selected process

                NamedElement elementSelected =
                        getProcessInterfaceElement(packageFile, procIfcName);
                if (elementSelected == null) {
                    String msg =
                            MessageFormat
                                    .format(Messages.InterfaceMethodSelectionPage_ProcIfcDoesNotExistErr_shortdesc,
                                            new Object[] { procIfcName });
                    setErrorMessage(msg);
                    return false;
                } else {

                    String interfaceMethodName = txtInterfaceMethod.getText();
                    if (interfaceMethodName == null
                            || interfaceMethodName.length() < 1) {
                        setErrorMessage(Messages.InterfaceMethodSelectionPage_SpecifyEvent_message0);

                        return false;
                    }
                }
            }
        }

        // If valid then clear error messages
        if (ret)
            setErrorMessage(null);

        return ret;
    }

    protected void browseForInterfaceMethod() {
        ProcessInterface processInterface = null;
        if (selectedElement instanceof InterfaceMethod) {

            InterfaceMethod interfaceMethod = (InterfaceMethod) selectedElement;
            processInterface =
                    ProcessInterfaceUtil.getProcessInterface(interfaceMethod);
        } else if (selectedElement instanceof ProcessInterface) {
            processInterface = (ProcessInterface) selectedElement;
        } else {
            return;
        }
        List<InterfaceMethod> interfaceMethods =
                new ArrayList<InterfaceMethod>();
        if (processInterface != null) {
            interfaceMethods.addAll(processInterface.getStartMethods());
            interfaceMethods.addAll(processInterface.getIntermediateMethods());
        }

        ElementListSelectionDialog dialog =
                new ElementListSelectionDialog(getShell(),
                        new BpmContentLabelProvider());
        dialog.setTitle(Messages.InterfaceMethodSelectionPage_IfcSelDialog_title);
        dialog.setMessage(Messages.InterfaceMethodSelectionPage_IfcSelDialog_shortdesc);
        dialog.setElements(interfaceMethods.toArray());
        dialog.setHelpAvailable(false);
        if (dialog.open() == Dialog.OK) {
            // Update the selected process
            selectedElement = (NamedElement) dialog.getFirstResult();

            if (selectedElement != null) {
                txtInterfaceMethod.setText(Xpdl2ModelUtil
                        .getDisplayName(selectedElement));
            } else {
                txtInterfaceMethod.setText(""); //$NON-NLS-1$
            }
        }
    }

    /**
     * Display the process picker
     * 
     */
    protected void browseForProcessIfcElement() {
        if (packageFile != null) {
            WorkingCopy wc = getWorkingCopy(packageFile);

            if (wc != null) {
                ProcessInterfaceGroup interfaceGroup =
                        new ProcessInterfaceGroup(wc.getRootElement());
                if (interfaceGroup != null) {
                    ElementListSelectionDialog dialog =
                            new ElementListSelectionDialog(getShell(),
                                    new BpmContentLabelProvider());

                    String dialogTitleText = null;
                    String dialogMessageText = null;

                    dialogTitleText =
                            Messages.ProcessInterfaceSelectionPage_BrowseDialog_title;
                    dialogMessageText =
                            Messages.ProcessInterfaceSelectionPage_BrowseDialog_shortdesc;

                    dialog.setTitle(dialogTitleText);
                    dialog.setMessage(dialogMessageText);

                    List childrenElements = new ArrayList();
                    if (interfaceGroup != null)
                        childrenElements.addAll(interfaceGroup.getChildren());

                    dialog.setElements(childrenElements.toArray());
                    dialog.setHelpAvailable(false);

                    if (selectedElement != null) {
                        dialog.setInitialSelections(new Object[] { selectedElement });
                    }

                    if (dialog.open() == Dialog.OK) {
                        // Update the selected process
                        selectedElement =
                                (NamedElement) dialog.getFirstResult();

                        if (selectedElement != null) {
                            txtElement.setText(Xpdl2ModelUtil
                                    .getDisplayName(selectedElement));
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

    }

    /**
     * Get the Process Interface with the given name
     * 
     * @param resource
     * @param xpdlProcessName
     * 
     * @return <code>NamedElement</code> with the given process name, if not
     *         found then <strong>null</strong> will be returned.
     */
    private NamedElement getProcessInterfaceElement(IResource resource,
            String xpdlProcessName) {
        NamedElement processElement = null;

        if (resource != null && xpdlProcessName != null) {
            WorkingCopy wc = getWorkingCopy(resource);

            if (wc != null && wc.getRootElement() instanceof Package) {
                List procIfcElements = new ArrayList();

                Package pkg = (Package) wc.getRootElement();
                ProcessInterfaces processInterfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);
                if (processInterfaces != null) {
                    procIfcElements.addAll(processInterfaces
                            .getProcessInterface());
                }

                for (Iterator iter = procIfcElements.iterator(); iter.hasNext()
                        && processElement == null;) {
                    NamedElement element = (NamedElement) iter.next();

                    if (element != null
                            && xpdlProcessName.equals(Xpdl2ModelUtil
                                    .getDisplayName(element))) {
                        processElement = element;
                        break;
                    }
                }
            }
        }

        return processElement;
    }

    /**
     * @param procOrIfcNameListener
     */
    public void addProcessOrInterfaceModifyListeners(ModifyListener listener) {
        if (txtInterfaceMethod != null) {
            txtInterfaceMethod.addModifyListener(listener);
        }
    }
}
