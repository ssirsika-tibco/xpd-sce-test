/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ActivityPickerDialog;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * TaskInvokeBusinessProcessSection
 * 
 * 
 * @author bharge
 * @since 3.3 (10 Mar 2010)
 */
public class TaskInvokeBusinessProcessSection extends
        AbstractFilteredTransactionalSection {

    private CLabel lblActivityName;

    private CLabel lblBusinessProcessName;

    private CLabel lblPackageName;

    private Text txtActivityName;

    private Text txtBusinessProcessName;

    private Text txtPackageName;

    private Button activityBrowse;

    private Color defaultTextColor;

    private FormText mainflowLink;

    private static final String BUSINESSPROC_HYPERLINK_HREF =
            "BusinessProcess.HyperLink"; //$NON-NLS-1$

    private static final String BUSINESSPROC_HYPERLINK_FORMATSTR =
            "<text><p>%s: (<a href='%s'>%s</a>)</p></text>"; //$NON-NLS-1$

    /**
     * 
     */
    public TaskInvokeBusinessProcessSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout gridLayout = new GridLayout(3, false);
        root.setLayout(gridLayout);

        GridData gridData = new GridData();
        gridData.horizontalSpan = 3;
        mainflowLink = toolkit.createFormText(root, true);
        mainflowLink.setData("name", "linkSubprocessInvocation"); //$NON-NLS-1$ //$NON-NLS-2$

        mainflowLink
                .setText(String
                        .format(BUSINESSPROC_HYPERLINK_FORMATSTR,
                                Messages.TaskInvokeBusinessProcessSection_BusinessProcInvocation_hyperlink_desc,
                                BUSINESSPROC_HYPERLINK_HREF,
                                Messages.TaskInvokeBusinessProcessSection_BusinessProcInvocation_hyperlink_action),
                        true,
                        false);

        mainflowLink.setLayoutData(gridData);
        manageControl(mainflowLink);

        lblActivityName =
                toolkit.createCLabel(root,
                        Messages.TaskInvokeBusinessProcessSection_BusinessProcess_Activity_Label,
                        SWT.NONE);
        lblActivityName.setLayoutData(new GridData());

        gridData = new GridData(GridData.FILL_HORIZONTAL);

        txtActivityName = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$
        txtActivityName.setData("name", "textActivityName"); //$NON-NLS-1$//$NON-NLS-2$
        txtActivityName.setLayoutData(gridData);

        activityBrowse =
                toolkit.createButton(root,
                        com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.TaskGeneralSection_ELIPSES,
                        SWT.NONE);
        activityBrowse.setData("name", "buttonBrowseActivity"); //$NON-NLS-1$//$NON-NLS-2$
        manageControl(activityBrowse);

        lblBusinessProcessName =
                toolkit.createCLabel(root,
                        Messages.TaskInvokeBusinessProcessSection_BusinessProcess_Name_Label,
                        SWT.NONE);
        lblBusinessProcessName.setLayoutData(new GridData());

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        txtBusinessProcessName = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$
        txtBusinessProcessName.setData("name", "textBusinessprocessName"); //$NON-NLS-1$ //$NON-NLS-2$        
        gridData.horizontalSpan = 2;
        txtBusinessProcessName.setLayoutData(gridData);

        lblPackageName =
                toolkit.createCLabel(root,
                        Messages.TaskInvokeBusinessProcessSection_Package_Name_Label,
                        SWT.NONE);
        lblPackageName.setLayoutData(new GridData());

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        txtPackageName = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$
        txtPackageName.setData("name", "textPackageName"); //$NON-NLS-1$ //$NON-NLS-2$
        gridData.horizontalSpan = 2;
        txtPackageName.setLayoutData(gridData);

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Activity activity = getActivity();
        EditingDomain ed = getEditingDomain();

        if (obj == activityBrowse
                || (BUSINESSPROC_HYPERLINK_HREF.equals(obj) && TaskObjectUtil
                        .getSubprocessDescription(activity) == null)) {

            CompoundCommand command = new CompoundCommand();

            Activity activityFromPicker =
                    ActivityPickerDialog.getActivityFromPicker(getSite()
                            .getShell(), activity);

            if (activityFromPicker != null) {

                /*
                 * generate wsdl info for send task invoking a business process
                 */

                ProcessDeveloperUtil.generateWsdlInfo(ed,
                        command,
                        activityFromPicker.getProcess(),
                        activity,
                        activityFromPicker);

                if (null != command && command.canExecute()) {
                    return command;
                }

            }
        } else if (BUSINESSPROC_HYPERLINK_HREF.equals(obj)) {
            Activity businesProcActivity =
                    TaskObjectUtil
                            .getInvokeBusinessProcessReferencedActivity(activity);
            revealObject(businesProcActivity);
        }

        return null;
    }

    /**
     * @param businesProcActivity
     */
    public boolean revealObject(Activity businesProcActivity) {
        try {
            IConfigurationElement facConfig =
                    XpdResourcesUIActivator
                            .getEditorFactoryConfigFor(businesProcActivity
                                    .getProcess());
            String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            EditorInputFactory f =
                    (EditorInputFactory) facConfig
                            .createExecutableExtension("factory"); //$NON-NLS-1$
            IEditorInput input = f.getEditorInputFor(businesProcActivity);

            IEditorPart part = IDE.openEditor(page, input, editorID);
            if (part instanceof IGotoEObject) {
                ((IGotoEObject) part).gotoEObject(true, businesProcActivity);
            }

            return true;
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @return
     */
    private Activity getActivity() {
        EObject input = getInput();
        if (input instanceof Activity) {
            return (Activity) input;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        Activity activity = getActivity();
        Image labelIcon = null;
        Color txtColor = defaultTextColor;
        lblActivityName.setToolTipText(""); //$NON-NLS-1$
        lblBusinessProcessName.setToolTipText(""); //$NON-NLS-1$
        lblPackageName.setToolTipText(""); //$NON-NLS-1$
        // XPD-4978:Display Process details when ONLY Activity is not
        // resolved/set
        boolean processOrActivityNotSelected = false;
        boolean unresolvedProcessORActivity = false;
        BusinessProcess businessProcess = null;

        if (activity != null) {
            businessProcess =
                    WebServiceOperationUtil.getBusinessProcess(activity);
            Process refProcess = null;

            if (businessProcess != null
                    && businessProcess.getProcessId() != null
                    && !businessProcess.getProcessId().isEmpty()) {

                refProcess =
                        ProcessUIUtil.getProcesById(businessProcess
                                .getProcessId());
            }
            // referenced process not found
            if ((refProcess == null && businessProcess != null)
                    && businessProcess.getActivityId() != null) {
                unresolvedProcessORActivity = true;
            } else {
                // business process element found in the invoke task
                // implementation
                if (businessProcess != null) {
                    // Display process info.
                    String name = WorkingCopyUtil.getText(refProcess);
                    txtBusinessProcessName
                            .setText(name == null ? com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.TaskGeneralSection_UNDEFINED
                                    : name);
                    // Display package info.
                    name = WorkingCopyUtil.getFile(refProcess).getName();

                    txtPackageName
                            .setText(name == null ? com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.TaskGeneralSection_UNDEFINED
                                    : name);
                    Activity refActivity = null;

                    // referenced activity check
                    if (businessProcess.getActivityId() != null) {
                        refActivity =
                                Xpdl2ModelUtil.getActivityById(refProcess,
                                        businessProcess.getActivityId());

                        if (refActivity != null) {
                            // Display Activity Info
                            name = WorkingCopyUtil.getText(refActivity);
                            txtActivityName
                                    .setText(name == null ? com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.TaskGeneralSection_UNDEFINED
                                            : name);
                        } else {
                            unresolvedProcessORActivity = true;
                        }

                    } else {
                        processOrActivityNotSelected = true;
                    }
                } else {

                    processOrActivityNotSelected = true;
                }
            }
        }

        if (unresolvedProcessORActivity) {
            if (businessProcess == null) {
                // set only when referenced process is not resolved
                setProcessMessage(Messages.Process_UnresolvedReferences_message,
                        Messages.Process_UnresolvedReferences_message);
            }
            txtActivityName
                    .setText(Messages.Process_UnresolvedReferences_message);
            lblActivityName
                    .setToolTipText(Messages.Process_UnresolvedReferences_message);
            txtColor = ColorConstants.lightGray;
            labelIcon =
                    Xpdl2UiPlugin.getDefault().getImageRegistry()
                            .get(Xpdl2UiPlugin.IMG_ERROR);

        } else if (processOrActivityNotSelected) {
            labelIcon =
                    Xpdl2UiPlugin.getDefault().getImageRegistry()
                            .get(Xpdl2UiPlugin.IMG_ERROR);
            lblActivityName
                    .setToolTipText(Messages.TaskInvokeBusinessProcessSection_MustSelectAnActivity_tooltip);
            txtActivityName
                    .setText(com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.TaskGeneralSection_UNDEFINED);
            if (businessProcess == null) {
                // set only if the process is not resolved
                setProcessMessage(Messages.TaskInvokeBusinessProcessSection_MustSelectAnActivity_tooltip,
                        com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages.TaskGeneralSection_UNDEFINED);
            }
            txtColor = ColorConstants.lightGray;
        }

        // activity details should be reset for unresolved reference raised by
        // both process OR activity.
        if (txtColor != txtActivityName.getForeground()) {
            txtActivityName.setForeground(txtColor);
        }
        if (txtColor != txtBusinessProcessName.getForeground()) {
            txtBusinessProcessName.setForeground(txtColor);
        }
        if (txtColor != txtPackageName.getForeground()) {
            txtPackageName.setForeground(txtColor);
        }
        setLabelIcon(lblActivityName, labelIcon);
        setLabelIcon(lblBusinessProcessName, labelIcon);
        setLabelIcon(lblPackageName, labelIcon);

    }

    /**
     * This method sets the label Icon to the one provided.Applies the changes
     * if it is different than the current.
     * 
     * @param label
     */
    private void setLabelIcon(CLabel label, Image labelIcon) {
        if (label.getImage() != labelIcon) {
            label.setImage(labelIcon);
            label.setLayoutData(new GridData());
            label.getParent().layout();
        }
    }

    /**
     * This method sets the message for process section, for the Invoke task.
     * 
     * @param taskGeneralSection_UNDEFINED
     * 
     */
    private void setProcessMessage(String tooltip, String messageText) {
        if (!txtPackageName.getText().equalsIgnoreCase(messageText)) {
            lblPackageName.setToolTipText(tooltip);
            lblBusinessProcessName.setToolTipText(tooltip);
            txtPackageName.setText(messageText);
            txtBusinessProcessName.setText(messageText);
        }

    }
}
