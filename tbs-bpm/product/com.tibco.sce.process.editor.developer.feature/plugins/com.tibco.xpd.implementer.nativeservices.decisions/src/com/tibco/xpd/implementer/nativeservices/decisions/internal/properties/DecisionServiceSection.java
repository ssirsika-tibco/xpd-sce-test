/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.decisions.internal.properties;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
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
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormText;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DecisionFlowFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DecisionFlowFilterPicker.DecisionFlowPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.implementer.nativeservices.decisions.internal.Messages;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;


/**
 * @author mtorres
 * 
 */
public class DecisionServiceSection extends
        AbstractFilteredTransactionalSection {

    private static final String DECISIONFLOW_HYPERLINK_HREF = "DecisionFlow.HyperLink"; //$NON-NLS-1$

    private static final String DECISIONFLOW_HYPERLINK_FORMATSTR =
            "<text><p>%s: (<a href='%s'>%s</a>)</p></text>"; //$NON-NLS-1$

    private static final String DEFAULT_DECISIONFLOWID = "-unknown-"; //$NON-NLS-1$

    private Color defaultTextColor;

    private CLabel subflowPkgLabel;

    private Text subflowPackage;

    private CLabel subflowProcessLabel;

    private Text subflowProcess;

    private Button subflowBrowse;

    private FormText subflowLink;

    public DecisionServiceSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);

        GridLayout gl = new GridLayout(3, false);
        gl.marginHeight = 0;
        gl.marginBottom = 5;
        root.setLayout(gl);

        GridData data = new GridData();
        data.horizontalSpan = 3;
        subflowLink = toolkit.createFormText(root, true);
        subflowLink.setData("name", "linkDecFlowInvocation"); //$NON-NLS-1$ //$NON-NLS-2$

        subflowLink
                .setText(String
                        .format(DECISIONFLOW_HYPERLINK_FORMATSTR,
                                Messages.DecisionServiceSection_DecFlowInvocation_hyperlink_desc,
                                DECISIONFLOW_HYPERLINK_HREF,
                                Messages.DecisionServiceSection_DecFlowInvocation_hyperlink_action),
                        true,
                        false);

        subflowLink.setLayoutData(data);
        manageControl(subflowLink);

        subflowPkgLabel =
                toolkit.createCLabel(root,
                        Messages.DecisionServiceSection_DECFLOW_LOCATION_LABEL,
                        SWT.NONE);
        subflowPkgLabel.setLayoutData(new GridData());

        subflowPackage = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$
        subflowPackage.setData("name", "textSubprocessLoaction"); //$NON-NLS-1$ //$NON-NLS-2$

        data = new GridData(GridData.FILL_HORIZONTAL);
        subflowPackage.setLayoutData(data);

        subflowBrowse =
                toolkit.createButton(root,
                        Messages.DecisionServiceSection_ELIPSES,
                        SWT.NONE);
        subflowBrowse.setData("name", "buttonBrowseSubprocess"); //$NON-NLS-1$ //$NON-NLS-2$
        manageControl(subflowBrowse);

        int height = subflowPackage.getLineHeight();
        data = new GridData(height + 4, height + 4);
        data.verticalSpan = 2;
        subflowBrowse.setLayoutData(data);

        subflowProcessLabel =
                toolkit.createCLabel(root,
                        Messages.DecisionServiceSection_DECFLOW_NAME_LABEL,
                        SWT.NONE);
        subflowProcessLabel.setLayoutData(new GridData());

        data = new GridData(GridData.FILL_HORIZONTAL);
        subflowProcess = toolkit.createText(root, "", SWT.READ_ONLY); //$NON-NLS-1$
        subflowProcess.setData("name", "textSubprocessName"); //$NON-NLS-1$ //$NON-NLS-2$
        subflowProcess.setLayoutData(data);        

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        Activity act = getActivity();
        EditingDomain ed = getEditingDomain();

        if (act != null && ed != null) {
            // If no sub-process is set then the open sub-process hyper-link
            // should perform a browse.
            if (obj == subflowBrowse
                    || (DECISIONFLOW_HYPERLINK_HREF.equals(obj) && DecisionTaskObjectUtil
                            .getDecFlowDescription(act) == null)) {
                // Show process picker to user
                EObject decisionFlow = getDecisionFlowFromPicker(act);
                if (decisionFlow != null) {                    
                    cmd =
                            DecisionTaskObjectUtil.getSetDecFlowCommand(ed,
                                    act,
                                    decisionFlow);
                }

            } else if (DECISIONFLOW_HYPERLINK_HREF.equals(obj)) {
                EObject subproc = DecisionTaskObjectUtil.getDecisionFlow(act);

                // if (subproc instanceof Process) {
                // Show the subprocess
                revealObject((NamedElement) subproc);
                // }
            }
        }

        return cmd;
    }

    private String getDecFlowId(Activity act) {
        String processId = null;
        if (act != null && act.getImplementation() instanceof Task) {
            Task task = (Task) act.getImplementation();
            if (task.getTaskService() != null) {
                TaskService taskService = task.getTaskService();
                SubFlow decisionService =
                        (SubFlow) Xpdl2ModelUtil
                                .getOtherElement(taskService,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DecisionService());
                if (decisionService != null) {
                    processId = decisionService.getProcessId();
                    if (processId != null
                            && processId.equals(DEFAULT_DECISIONFLOWID)) {
                        processId = null;
                    }
                }
            }
        }
        return processId;
    }

    @Override
    protected void doRefresh() {
        Activity act = getActivity();
        
        Image labelIcon = null;
        Color txtColor = defaultTextColor;
        Color pkgTextColor = defaultTextColor;

        subflowPkgLabel.setToolTipText(""); //$NON-NLS-1$
        subflowProcessLabel.setToolTipText(""); //$NON-NLS-1$

        if (act != null) {

            String proc = DecisionTaskObjectUtil.getDecFlowDescription(act);
            EObject subproc = DecisionTaskObjectUtil.getDecisionFlow(act);            

            if (subproc == null && getDecFlowId(act) != null) {
                subflowProcess
                        .setText(Messages.DecisionServiceSection_UnresolvedReferences_message);
                subflowProcessLabel
                        .setToolTipText(Messages.DecisionServiceSection_UnresolvedReferences_message); 
                subflowPackage
                        .setText(Messages.DecisionServiceSection_UnresolvedReferences_message); 
                subflowPkgLabel
                        .setToolTipText(Messages.DecisionServiceSection_UnresolvedReferences_message); 

                txtColor = ColorConstants.lightGray;
                pkgTextColor = ColorConstants.lightGray;

                labelIcon =
                        Xpdl2UiPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2UiPlugin.IMG_ERROR);

            } else {
                subflowProcess
                        .setText(proc == null ? Messages.DecisionServiceSection_UNDEFINED
                                : proc);
                String loc =
                        DecisionTaskObjectUtil.getDecFlowLocationDescription(act);
                subflowPackage
                        .setText(proc == null && loc == null ? Messages.DecisionServiceSection_UNDEFINED 
                                : loc == null ? Messages.DecisionServiceSection_THE_SAME_PACKAGE
                                        : loc);

                if (loc == null) {
                    pkgTextColor = ColorConstants.lightGray;
                }

                if (proc == null) {
                    labelIcon =
                            Xpdl2UiPlugin.getDefault().getImageRegistry()
                                    .get(Xpdl2UiPlugin.IMG_ERROR);
                    subflowProcessLabel
                            .setToolTipText(Messages.DecisionServiceSection_MustSelectADecFlow_tooltip);
                    subflowPkgLabel
                            .setToolTipText(Messages.DecisionServiceSection_MustSelectADecFlow_tooltip);
                    txtColor = ColorConstants.lightGray;
                }
            }
            
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
     * Get the process from a process picker displayed to user
     * 
     * @return
     */
    private EObject getDecisionFlowFromPicker(Activity act) {
        EObject decisionFlow = null;

        if (act != null) {
            DecisionFlowFilterPicker picker =
                    new DecisionFlowFilterPicker(getSite().getShell(),
                            DecisionFlowPickerType.DECISIONFLOW, false);

            Object curSel = DecisionTaskObjectUtil.getDecisionFlow(act);
            if (curSel != null) {
                picker.setInitialElementSelections(Collections
                        .singletonList(curSel));
            }

            decisionFlow =
                    ProcessUIUtil.getResultFromPicker(picker, getSite()
                            .getShell(), act);
        }

        return decisionFlow;
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

    public String getLocalId() {
        return "analyst.indieSupProcSection"; //$NON-NLS-1$    
    }

    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
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

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean ret = false;

        ret = super.shouldRefresh(notifications);
        if (!ret) {
            // Check for add / delete of process interface - so that if
            // undo/redo deleted interface then we will refresh.
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    switch (notification.getEventType()) {
                    case Notification.ADD:
                        if (notification.getNewValue() instanceof ProcessInterface
                                || notification.getNewValue() instanceof Process
                                || notification.getNewValue() instanceof Package) {
                            ret = true;
                        }
                        break;
                    case Notification.REMOVE:
                        if (notification.getOldValue() instanceof ProcessInterface
                                || notification.getOldValue() instanceof Process
                                || notification.getOldValue() instanceof Package) {
                            ret = true;
                        }
                        break;
                    }

                }
            }
        }

        return ret;
    }
    
    
}