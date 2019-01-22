/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
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
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormText;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker.ProcessPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.processeditor.xpdl2.properties.adhoc.AdhocConfigurationSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.misc.ControlDecorationSafe;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Call sub-process activity's details expandable section.
 * 
 * @author sajain
 * @since Dec 22, 2014
 */
public class CallSubProcessDetailsSection extends
        AbstractFilteredTransactionalSection implements SashSection {

    private static final String SUBPROC_HYPERLINK_HREF = "SubProcess.HyperLink"; //$NON-NLS-1$

    private static final String SUBPROC_HYPERLINK_FORMATSTR =
            "<text><p>%s: (<a href='%s'>%s</a>)</p></text>"; //$NON-NLS-1$

    private static final String DEFAULT_SUBPROCID = "-unknown-"; //$NON-NLS-1$

    private Button transactionalBut;

    private Color defaultTextColor;

    private CLabel subflowPkgLabel;

    private Text subflowPackage;

    private CLabel subflowProcessLabel;

    private Text subflowProcess;

    private Text subflowRuntimeIdentifier;

    private Button subflowBrowse;

    private Button runtimeIdentifierFieldBrowse;

    private FormText subflowLink;

    private ControlDecoration subflowRuntimeIdentifierDecoration;

    /**
     * Call sub-process activity's details expandable section.
     */
    public CallSubProcessDetailsSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return <code>true</code> always, let
     *         {@link AdhocConfigurationSection#select(Object)} do the
     *         filtering.
     */
    @Override
    public boolean select(Object toTest) {

        return true;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        Activity act = getActivity();

        Image labelIcon = null;
        Color txtColor = defaultTextColor;
        Color pkgTextColor = defaultTextColor;

        subflowPkgLabel.setToolTipText(""); //$NON-NLS-1$
        subflowProcessLabel.setToolTipText(""); //$NON-NLS-1$

        if (act != null) {

            if (ProcessDestinationUtil.isBPMDestinationSelected(act
                    .getProcess())) {

                boolean isUnqualifiedNameAllowed = false;
                Implementation implementation = act.getImplementation();

                if (implementation instanceof SubFlow) {

                    SubFlow subFlow = (SubFlow) implementation;

                    isUnqualifiedNameAllowed =
                            Xpdl2ModelUtil
                                    .getOtherAttributeAsBoolean(subFlow,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AllowUnqualifiedSubProcessIdentification());

                }

                if (isUnqualifiedNameAllowed) {

                    /*
                     * If unqualified names are allowed then show the
                     * appropriate description.
                     */
                    subflowRuntimeIdentifierDecoration
                            .setDescriptionText(Messages.TaskTypeIndependentSubProcSection_DynamicSubFlowUnqualifiedNamesAllowedRuntimeIdBPM_message);

                } else {

                    subflowRuntimeIdentifierDecoration
                            .setDescriptionText(Messages.TaskTypeIndependentSubProcSection_SubFlowRuntimeIdBPM_message2);
                }
            } else {

                subflowRuntimeIdentifierDecoration
                        .setDescriptionText(Messages.TaskTypeIndependentSubProcSection_SubFlowRuntimeIdGeneral_message);
            }

            String proc = TaskObjectUtil.getSubprocessDescription(act);
            EObject subproc = TaskObjectUtil.getSubProcessOrInterface(act);

            if (!(subproc instanceof ProcessInterface)) {

                runtimeIdentifierFieldBrowse.setEnabled(false);
            } else {

                runtimeIdentifierFieldBrowse.setEnabled(true);
            }

            if (subproc == null && getSubProcessId(act) != null) {

                subflowProcess
                        .setText(com.tibco.xpd.processeditor.xpdl2.internal.Messages.Process_UnresolvedReferences_message);
                subflowProcessLabel
                        .setToolTipText(com.tibco.xpd.processeditor.xpdl2.internal.Messages.Process_UnresolvedReferences_message);
                subflowPackage
                        .setText(com.tibco.xpd.processeditor.xpdl2.internal.Messages.Process_UnresolvedReferences_message);
                subflowPkgLabel
                        .setToolTipText(com.tibco.xpd.processeditor.xpdl2.internal.Messages.Process_UnresolvedReferences_message);

                txtColor = ColorConstants.lightGray;
                pkgTextColor = ColorConstants.lightGray;

                labelIcon =
                        Xpdl2UiPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2UiPlugin.IMG_ERROR);

            } else {
                subflowProcess
                        .setText(proc == null ? Messages.TaskGeneralSection_UNDEFINED
                                : proc);
                String loc =
                        TaskObjectUtil.getSubprocessLocationDescription(act);
                subflowPackage
                        .setText(proc == null && loc == null ? Messages.TaskGeneralSection_UNDEFINED
                                : loc == null ? Messages.TaskGeneralSection_THE_SAME_PACKAGE
                                        : loc);

                if (loc == null) {

                    pkgTextColor = ColorConstants.lightGray;
                }

                if (proc == null) {

                    labelIcon =
                            Xpdl2UiPlugin.getDefault().getImageRegistry()
                                    .get(Xpdl2UiPlugin.IMG_ERROR);

                    subflowProcessLabel
                            .setToolTipText(Messages.TaskTypeIndependentSubProcSection_MustSelectAProcess_tooltip);

                    subflowPkgLabel
                            .setToolTipText(Messages.TaskTypeIndependentSubProcSection_MustSelectAProcess_tooltip);

                    txtColor = ColorConstants.lightGray;
                }
            }
            String ident = getRuntimeIdentifierField();

            // Set the description of the Runtime identifier
            subflowRuntimeIdentifier
                    .setText(ident == null ? Messages.TaskGeneralSection_UNDEFINED
                            : ident);

            subflowRuntimeIdentifier
                    .setForeground(ident != null ? ColorConstants.black
                            : ColorConstants.lightGray);

            // Set the transactional flag.
            transactionalBut.setSelection(TaskObjectUtil
                    .getSubprocessIsTransactional(act));

        }

        if (txtColor != subflowProcess.getForeground()) {

            subflowProcess.setForeground(txtColor);
        }

        if (pkgTextColor != subflowPackage.getForeground()) {

            subflowPackage.setForeground(pkgTextColor);
        }

        if (subflowPkgLabel.getImage() != labelIcon) {

            subflowPkgLabel.setImage(labelIcon);
            subflowPkgLabel.setLayoutData(new GridData());
            subflowProcessLabel.setImage(labelIcon);
            subflowProcessLabel.setLayoutData(new GridData());
            forceLayout();
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        Composite root = toolkit.createComposite(parent);

        GridLayout gl = new GridLayout(3, false);
        gl.marginHeight = 0;
        gl.marginBottom = 5;
        root.setLayout(gl);

        root.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridData data = new GridData();
        data.horizontalSpan = 3;

        transactionalBut =
                toolkit.createButton(root,
                        Messages.TaskGeneralSection_SUBPROC_TRANSACTIONAL,
                        SWT.CHECK);

        transactionalBut.setData("name", "buttonIsSubprocessTransaction"); //$NON-NLS-1$ //$NON-NLS-2$
        manageControl(transactionalBut);
        transactionalBut.setLayoutData(data);

        data = new GridData();
        data.horizontalSpan = 3;
        data.horizontalIndent = 3;
        subflowLink = toolkit.createFormText(root, true);
        subflowLink.setData("name", "linkSubprocessInvocation"); //$NON-NLS-1$ //$NON-NLS-2$

        subflowLink
                .setText(String
                        .format(SUBPROC_HYPERLINK_FORMATSTR,
                                Messages.TaskGeneralSection_SubprocInvocation_hyperlink_desc,
                                SUBPROC_HYPERLINK_HREF,
                                Messages.TaskGeneralSection_SubprocInvocation_hyperlink_action),
                        true,
                        false);

        subflowLink.setLayoutData(data);
        manageControl(subflowLink);

        subflowPkgLabel =
                toolkit.createCLabel(root,
                        Messages.TaskGeneralSection_SUBPROCESS_LOCATION_LABEL,
                        SWT.NONE);
        subflowPkgLabel.setLayoutData(new GridData());

        subflowPackage = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$
        subflowPackage.setData("name", "textSubprocessLoaction"); //$NON-NLS-1$ //$NON-NLS-2$

        data = new GridData(GridData.FILL_HORIZONTAL);
        subflowPackage.setLayoutData(data);

        subflowBrowse =
                toolkit.createButton(root,
                        Messages.TaskGeneralSection_ELIPSES,
                        SWT.NONE);
        subflowBrowse.setData("name", "buttonBrowseSubprocess"); //$NON-NLS-1$ //$NON-NLS-2$
        manageControl(subflowBrowse);

        int height = subflowPackage.getLineHeight();
        data = new GridData(height + 4, height + 4);
        data.verticalSpan = 2;
        subflowBrowse.setLayoutData(data);

        subflowProcessLabel =
                toolkit.createCLabel(root,
                        Messages.TaskGeneralSection_SUBFLOW_NAME_LABEL,
                        SWT.NONE);
        subflowProcessLabel.setLayoutData(new GridData());

        data = new GridData(GridData.FILL_HORIZONTAL);
        subflowProcess = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$
        subflowProcess.setData("name", "textSubprocessName"); //$NON-NLS-1$ //$NON-NLS-2$
        subflowProcess.setLayoutData(data);

        CLabel runtimeIdLabel =
                toolkit.createCLabel(root,
                        Messages.TaskGeneralSection_Runtime_Identifier_Field);

        data = new GridData(GridData.FILL_HORIZONTAL);
        subflowRuntimeIdentifier = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$

        data.horizontalIndent =
                FieldDecorationRegistry.getDefault()
                        .getMaximumDecorationWidth();
        subflowRuntimeIdentifierDecoration =
                new ControlDecorationSafe(subflowRuntimeIdentifier, SWT.TOP
                        | SWT.LEFT);
        FieldDecoration infoFieldIndicator =
                FieldDecorationRegistry
                        .getDefault()
                        .getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION);
        subflowRuntimeIdentifierDecoration.setImage(infoFieldIndicator
                .getImage());

        subflowRuntimeIdentifier.setData("name", "textSubprocessRuntimeId"); //$NON-NLS-1$ //$NON-NLS-2$
        subflowRuntimeIdentifier.setLayoutData(data);

        int height2 = subflowRuntimeIdentifier.getLineHeight();
        data = new GridData(height2 + 4, height2 + 4);
        // data.verticalSpan = 2;
        runtimeIdentifierFieldBrowse =
                toolkit.createButton(root,
                        Messages.TaskGeneralSection_ELIPSES,
                        SWT.NONE);
        runtimeIdentifierFieldBrowse.setData("name", "buttonBrowseRuntimeId"); //$NON-NLS-1$ //$NON-NLS-2$
        runtimeIdentifierFieldBrowse.setLayoutData(data);
        manageControl(runtimeIdentifierFieldBrowse);

        List<Control> labels = new ArrayList<Control>();
        labels.add(subflowPkgLabel);
        labels.add(subflowProcessLabel);
        labels.add(runtimeIdLabel);
        setSameGridDataWidth(labels, null);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {

        Command cmd = null;

        Activity act = getActivity();
        EditingDomain ed = getEditingDomain();

        if (act != null && ed != null) {
            // If no sub-process is set then the open sub-process hyper-link
            // should perform a browse.
            if (obj == runtimeIdentifierFieldBrowse) {
                // Show process picker to user
                ProcessRelevantData runtimeIdentifier =
                        getRuntimeIdentifierFromPicker(act);
                if (runtimeIdentifier != null) {
                    Implementation implementation = act.getImplementation();
                    if (implementation instanceof SubFlow) {
                        SubFlow subFlow = (SubFlow) implementation;
                        cmd =
                                Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(ed,
                                                subFlow,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ProcessIdentifierField(),
                                                runtimeIdentifier.getName());
                    }
                }
            } else if (obj == subflowBrowse
                    || (SUBPROC_HYPERLINK_HREF.equals(obj) && TaskObjectUtil
                            .getSubprocessDescription(act) == null)) {
                // Show process picker to user
                EObject subProcess = getProcessFromPicker(act);
                if (subProcess != null) {
                    if (!(subProcess instanceof ProcessInterface)) {
                        runtimeIdentifierFieldBrowse.setEnabled(false);
                    } else {
                        runtimeIdentifierFieldBrowse.setEnabled(true);
                    }
                    cmd =
                            TaskObjectUtil.getSetSubprocessCommand(ed,
                                    act,
                                    subProcess);
                }

            } else if (obj == transactionalBut) {
                // Get transactional sub-process
                cmd =
                        TaskObjectUtil
                                .getSetSubProcessIsTransactionalCommand(ed,
                                        act,
                                        transactionalBut.getSelection());

            } else if (SUBPROC_HYPERLINK_HREF.equals(obj)) {
                EObject subproc = TaskObjectUtil.getSubProcessOrInterface(act);

                revealObject((NamedElement) subproc);

            }
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashSection#shouldSashSectionRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    public boolean shouldSashSectionRefresh(List<Notification> notifications) {

        return shouldRefresh(notifications);
    }

    /**
     * Get xpdl2 Activity from the input
     * 
     * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    private String getSubProcessId(Activity act) {
        String processId = null;
        if (act != null) {
            Implementation implementation = act.getImplementation();
            if (implementation instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) implementation;
                processId = subFlow.getProcessId();
                if (processId != null && processId.equals(DEFAULT_SUBPROCID)) {
                    processId = null;
                }
            }
        }
        return processId;
    }

    private String getRuntimeIdentifierField() {
        String ident = null;
        Implementation implementation = getActivity().getImplementation();
        if (implementation instanceof SubFlow) {
            SubFlow subFlow = (SubFlow) implementation;
            Object obj =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessIdentifierField());
            if (obj instanceof String) {
                ident = (String) obj;
            }
        }
        return ident;
    }

    /**
     * @return grid data for start strategy container.
     */
    protected GridData createBusinessprocessConfigGridData() {
        GridData data;
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        return data;
    }

    /**
     * Get the process from a process picker displayed to user
     * 
     * @return
     */
    private EObject getProcessFromPicker(Activity act) {

        EObject processFromPicker = null;
        if (act != null) {

            /*
             * ABPM-897: Saket: Display processes according to the type of
             * process activity is contained in.
             */

            ProcessFilterPicker picker = null;

            if (Xpdl2ModelUtil.isPageflowOrSubType(act.getProcess())) {

                picker =
                        new ProcessFilterPicker(getSite().getShell(),
                                ProcessPickerType.ALL_PROCESS_TYPES, false);
            } else {

                picker =
                        new ProcessFilterPicker(getSite().getShell(),
                                ProcessPickerType.BUSINESS_OR_SERVICE_PROCESS,
                                false);
            }

            Object curSel = TaskObjectUtil.getSubProcessOrInterface(act);
            if (curSel != null) {

                picker.setInitialElementSelections(Collections
                        .singletonList(curSel));
            }

            processFromPicker =
                    ProcessUIUtil.getResultFromPicker(picker, getSite()
                            .getShell(), act);
        }

        return processFromPicker;
    }

    /**
     * Get the process from a process picker displayed to user
     * 
     * @return
     */
    private ProcessRelevantData getRuntimeIdentifierFromPicker(Activity act) {
        ProcessRelevantData pickerSelection = null;

        if (act != null) {
            DataFilterPicker picker =
                    new DataFilterPicker(getSite().getShell(),
                            DataPickerType.DATAFIELD_FORMALPARAMETER, false);
            picker.setScope(act);

            String field = getRuntimeIdentifierField();
            if (field != null) {
                List<ProcessRelevantData> available =
                        ProcessInterfaceUtil
                                .getAllAvailableRelevantDataForActivity(act);
                for (ProcessRelevantData data : available) {
                    if (field.equals(data.getName())) {
                        picker.setInitialElementSelections(Collections
                                .singletonList(data));
                        break;
                    }
                }

            }
            if (picker.open() == DataPicker.OK) {
                if (picker.getFirstResult() instanceof ProcessRelevantData) {
                    pickerSelection =
                            (ProcessRelevantData) picker.getFirstResult();
                }
            }
        }

        return pickerSelection;
    }

    public boolean revealObject(NamedElement subproc) {
        try {
            IConfigurationElement facConfig =
                    XpdResourcesUIActivator.getEditorFactoryConfigFor(subproc);
            String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            EditorInputFactory f =
                    (EditorInputFactory) facConfig
                            .createExecutableExtension("factory"); //$NON-NLS-1$
            IEditorInput input = f.getEditorInputFor(subproc);
            page.openEditor(input, editorID);
            return true;
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }
}
