/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.ITaskImplementationInitializer;
import com.tibco.xpd.processeditor.xpdl2.extensions.TaskImplementationElement;
import com.tibco.xpd.processeditor.xpdl2.extensions.TaskImplementationService;
import com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding.Bindings;
import com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding.TaskReference;
import com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding.TasksBindingService;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Task type independent sub-process section
 * 
 * @author aallway
 */
@SuppressWarnings("deprecation")
public class TaskTypeUserSection extends AbstractFilteredTransactionalSection
        implements IPluginContribution {

    /**
     * The user task implementation type id for taskImplementation contribution
     * of Form related controls.
     */
    public static final String PROCESS_FORM = "ProcessForm"; //$NON-NLS-1$

    public static final String PAGEFLOW_PROCESS_FORM = "PageflowProcessForm"; //$NON-NLS-1$

    private static final String FORM_LINK = "Form.Link"; //$NON-NLS-1$

    private static final String PAGEFLOW_LINK = "Pageflow.Link"; //$NON-NLS-1$

    private static final int SUBSECTION_INSET = 16;

    //
    // Form Controls...
    //
    private Button formRadio;

    private Composite formControls;

    private CLabel formLabel;

    private Text formText;

    private Composite formHyperContainer;

    private FormText formHyperLink;

    private Button formBrowse;

    //
    // Pageflow controls.
    //
    private Button pageflowRadio;

    private Composite pageflowControls;

    private CLabel pageflowLabel;

    private Text pageflowText;

    private Composite pageflowHyperContainer;

    private FormText pageflowHyperLink;

    private Button pageflowBrowse;

    //
    // User Defined
    //
    private Button userRadio;

    private Composite userControls;

    private CLabel userLabel;

    private Text userText;

    //
    // None Defined
    //
    private Button noneRadio;

    // Horrible kludge to get round a layout problem that seems to happen when a
    // force layout is performed after first created.
    private boolean firstRefreshEver = true;

    private Composite rootContainer;

    private PageBook formTypeBook;

    private Composite defaultFormControlsPage;

    private ISection contributedFormSection;

    private ISection contributedPageflowFormSection;

    private Composite contributedFormControlsPage;

    private Composite contributedPageflowFormControlsPage;

    public TaskTypeUserSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);

        /*
         * Pass the input onto the appropriate contributed forms control
         * section.
         */
        if (getInput() instanceof Activity) {
            Activity act = (Activity) getInput();

            if (contributedPageflowFormSection != null) {
                contributedPageflowFormSection.setInput(part, selection);
            }

            if (contributedFormSection != null) {
                contributedFormSection.setInput(part, selection);
            }
        }

        return;
    }

    @Override
    public void dispose() {
        if (contributedPageflowFormSection != null) {
            contributedPageflowFormSection.dispose();
        }

        if (contributedFormSection != null) {
            contributedFormSection.dispose();
        }

        super.dispose();

        return;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        rootContainer = toolkit.createComposite(parent);

        GridLayout pageLayout = new GridLayout(1, false);
        pageLayout.marginTop = 8;
        pageLayout.marginHeight = 2;
        rootContainer.setLayout(pageLayout);

        //
        // User Defined URL...
        noneRadio =
                toolkit.createButton(rootContainer,
                        Messages.TaskTypeUserSection_UseDefaultForm_button,
                        SWT.RADIO);
        noneRadio.setLayoutData(new GridData());
        manageControl(noneRadio);

        Composite noneFiller = toolkit.createComposite(rootContainer);
        GridData ngd = new GridData(GridData.FILL_HORIZONTAL);
        ngd.heightHint = 1;
        noneFiller.setLayoutData(ngd);

        //
        // User Defined URL...
        userRadio =
                toolkit.createButton(rootContainer,
                        Messages.TaskTypeUserSection_UserDefinedURL_button,
                        SWT.RADIO);
        userRadio.setLayoutData(new GridData());
        manageControl(userRadio);

        userControls =
                createUserDefinedURLSelectionControls(rootContainer, toolkit);
        userControls.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //
        // Form ...
        formRadio =
                toolkit.createButton(rootContainer,
                        Messages.TaskTypeUserSection_Form_button,
                        SWT.RADIO);

        manageControl(formRadio);

        formControls = createFormSelectionControls(rootContainer, toolkit);

        /*
         * Sid XPD-2769 Only show form radio if there are controls contributed
         * for it
         */
        setFormSelectionVisible(contributedFormSection != null);

        //
        // Pageflow...
        if (Xpdl2ResourcesPlugin.isPageflowAvailable()) {
            pageflowRadio =
                    toolkit.createButton(rootContainer,
                            Messages.TaskTypeUserSection_Pageflow_button,
                            SWT.RADIO);
            pageflowRadio.setLayoutData(new GridData());
            manageControl(pageflowRadio);

            pageflowControls =
                    createPageflowSelectionControls(rootContainer, toolkit);
            pageflowControls.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));
        }

        adjustLabelAlignment();

        //
        // Add a filler to bunch everything else above up.
        Composite filler = toolkit.createComposite(rootContainer);
        GridData fgd =
                new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL);
        fgd.heightHint = 5; // set height hint otherwise always grabs a default
        // 50 - 100 pixels!
        filler.setLayoutData(fgd);

        return rootContainer;
    }

    /**
     * Show or hide the form controls.
     * 
     * @param visible
     */
    private void setFormSelectionVisible(boolean visible) {
        if (formRadio != null && !formRadio.isDisposed()) {
            if (visible) {

                formRadio.setVisible(true);

                GridData gd = new GridData();
                formRadio.setLayoutData(gd);

                GridData fcgd = new GridData(GridData.FILL_HORIZONTAL);
                formControls.setLayoutData(fcgd);

                formRadio.getParent().layout(true);

            } else {

                formRadio.setVisible(false);

                GridData gd = new GridData();
                gd.heightHint = 1;
                formRadio.setLayoutData(gd);

                GridData fcgd = new GridData(GridData.FILL_HORIZONTAL);
                fcgd.heightHint = 1;
                formControls.setLayoutData(fcgd);

                formRadio.getParent().layout(true);

            }
        }
    }

    private Composite createUserDefinedURLSelectionControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(2, false);
        layout.marginLeft = SUBSECTION_INSET;
        layout.marginBottom = 0;
        layout.marginTop = 0;
        layout.marginHeight = 0;
        container.setLayout(layout);

        toolkit.paintBordersFor(container);

        userLabel =
                toolkit.createCLabel(container,
                        Messages.TaskTypeUserSection_UserDefinedFormId_label);
        userLabel.setLayoutData(new GridData());

        userText = toolkit.createText(container, ""); //$NON-NLS-1$
        GridData ugd = new GridData(GridData.FILL_HORIZONTAL);
        ugd.widthHint = 100;
        userText.setLayoutData(ugd);
        manageControl(userText);

        return container;
    }

    private Composite createFormSelectionControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(1, false);
        layout.marginLeft = SUBSECTION_INSET;
        layout.marginBottom = 0;
        layout.marginTop = 0;
        layout.marginHeight = 0;
        container.setLayout(layout);

        if (getSectionContainerType() != ContainerType.WIZARD) {

            /*
             * Create a pagebook containing the old default form controls, the
             * contributed section for user task forms for normal process and
             * same again for pageflow process.
             */
            formTypeBook = new PageBook(container, SWT.NONE);
            formTypeBook.setBackground(container.getBackground());
            GridData gData = new GridData(GridData.FILL_BOTH);
            formTypeBook.setLayoutData(gData);

            defaultFormControlsPage = createFormControlsPage(toolkit);
            createOldDefaultFormSelectionControls(defaultFormControlsPage,
                    toolkit);

            contributedFormSection = getContributedFormSection(PROCESS_FORM);
            if (contributedFormSection != null) {
                contributedFormControlsPage = createFormControlsPage(toolkit);
                contributedFormSection
                        .createControls(contributedFormControlsPage,
                                getPropertySheetPage());
            }

            contributedPageflowFormSection =
                    getContributedFormSection(PAGEFLOW_PROCESS_FORM);
            if (contributedPageflowFormSection != null) {
                contributedPageflowFormControlsPage =
                        createFormControlsPage(toolkit);
                contributedPageflowFormSection
                        .createControls(contributedPageflowFormControlsPage,
                                getPropertySheetPage());
            }

            formTypeBook.showPage(defaultFormControlsPage);
        }
        return container;
    }

    /**
     * @param toolkit
     * @return control for page.
     */
    private Composite createFormControlsPage(XpdFormToolkit toolkit) {
        Composite defaultFormControlsPage =
                toolkit.createComposite(formTypeBook, SWT.NONE);
        FillLayout fillLayout = new FillLayout();
        fillLayout.marginHeight = 0;
        fillLayout.marginWidth = 0;
        defaultFormControlsPage.setLayout(fillLayout);
        return defaultFormControlsPage;
    }

    /**
     * @return Contributed form controls section or null if none present.
     */
    private ISection getContributedFormSection(String implementationTypeId) {
        /*
         * For the moment we only allow for contribution to the BPMN process
         * dest env because we do not envisage any other usage.
         */
        Collection<String> enabledDestinations =
                Collections
                        .singletonList(ProcessEditorConstants.BPMN_DESTINATION);

        /*
         * Get the bindings between BPMN and User Task implementations.
         */
        Bindings bindings = TasksBindingService.INSTANCE.getBindings();

        Set<TaskReference> bindingsForUserTask =
                bindings.getTasks(enabledDestinations, TaskType.USER_LITERAL);

        /*
         * Look explicitly for a binding for the "ProcessForm" id.
         */
        boolean haveBpmnBindingToProcesForm = false;

        if (bindingsForUserTask != null && bindingsForUserTask.size() > 0) {
            for (TaskReference taskBindingRef : bindingsForUserTask) {
                if (implementationTypeId.equals(taskBindingRef.getId())) {
                    haveBpmnBindingToProcesForm = true;
                    break;
                }
            }
        }

        /*
         * Find the appropriate implementation for ProcessForm implementation
         * type.
         */
        if (haveBpmnBindingToProcesForm) {
            Set<TaskImplementationElement> allUserTaskImplementations =
                    TaskImplementationService.INSTANCE.getImplementations()
                            .getTasks(TaskType.USER_LITERAL);

            if (allUserTaskImplementations != null) {
                for (TaskImplementationElement taskImpl : allUserTaskImplementations) {
                    if (implementationTypeId.equals(taskImpl.getId())) {
                        try {
                            return taskImpl.getISectionExec();
                        } catch (CoreException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return null;
    }

    private Composite createOldDefaultFormSelectionControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(3, false);
        layout.marginLeft = 0;
        layout.marginBottom = 0;
        layout.marginTop = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        container.setLayout(layout);

        toolkit.paintBordersFor(container);

        formLabel =
                toolkit.createCLabel(container,
                        Messages.TaskImplementationPropertySection_FormURL_label);
        formLabel.setBackground(ColorConstants.lightGray);
        formLabel.setLayoutData(new GridData());
        formText = toolkit.createText(container, "", SWT.READ_ONLY); //$NON-NLS-1$

        GridData fgd = new GridData(GridData.FILL_HORIZONTAL);
        fgd.widthHint = 100;
        formText.setLayoutData(fgd);
        formText.setToolTipText(Messages.TaskTypeUserSection_ReferencedFormFile_tooltip);
        manageControl(formText);

        Point sz = formText.computeSize(SWT.DEFAULT, SWT.DEFAULT);

        formBrowse =
                toolkit.createButton(container,
                        Messages.TaskTypeUserSection_BrowseButtonLabel,
                        SWT.PUSH);
        GridData bgd = new GridData();
        bgd.heightHint = sz.y;
        formBrowse.setLayoutData(bgd);
        manageControl(formBrowse);

        Label filler = toolkit.createLabel(container, ""); //$NON-NLS-1$
        GridData fillerGd = new GridData();
        fillerGd.heightHint = 1;
        filler.setLayoutData(fillerGd);

        formHyperContainer = toolkit.createComposite(container);
        GridLayout ngl = new GridLayout(1, false);
        ngl.marginWidth = 0;
        ngl.marginHeight = 0;
        formHyperContainer.setLayout(ngl);

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        formHyperContainer.setLayoutData(gd);

        formHyperLink = toolkit.createFormText(formHyperContainer, true);
        formHyperLink.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        String linkText =
                "<form><p><a href = '" + FORM_LINK + "'>" //$NON-NLS-1$ //$NON-NLS-2$
                        + Messages.TaskTypeUserSection_HyperlinkText
                        + "</a></p></form>"; //$NON-NLS-1$
        formHyperLink.setText(linkText, true, false);
        manageControl(formHyperLink);

        return container;
    }

    private Composite createPageflowSelectionControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(3, false);
        layout.marginLeft = SUBSECTION_INSET;
        layout.marginBottom = 0;
        layout.marginTop = 0;
        layout.marginHeight = 0;
        container.setLayout(layout);

        toolkit.paintBordersFor(container);

        pageflowLabel =
                toolkit.createCLabel(container,
                        Messages.TaskTypeUserSection_Pageflow_label);
        pageflowLabel.setLayoutData(new GridData());
        pageflowText = toolkit.createText(container, "", SWT.READ_ONLY); //$NON-NLS-1$
        GridData pgd = new GridData(GridData.FILL_HORIZONTAL);
        pgd.widthHint = 100;
        pageflowText.setLayoutData(pgd);
        pageflowText
                .setToolTipText(Messages.TaskTypeUserSection_ReferencedPageflow_tooltip);
        manageControl(pageflowText);
        Point sz = formText.computeSize(SWT.DEFAULT, SWT.DEFAULT);

        pageflowBrowse =
                toolkit.createButton(container,
                        Messages.TaskTypeUserSection_BrowseButtonLabel,
                        SWT.PUSH);
        GridData bgd = new GridData();
        bgd.heightHint = sz.y;
        pageflowBrowse.setLayoutData(bgd);
        manageControl(pageflowBrowse);

        Label filler = toolkit.createLabel(container, ""); //$NON-NLS-1$
        GridData fillerGd = new GridData();
        fillerGd.heightHint = 1;
        filler.setLayoutData(fillerGd);

        pageflowHyperContainer = toolkit.createComposite(container);
        GridLayout ngl = new GridLayout(1, false);
        ngl.marginWidth = 0;
        ngl.marginHeight = 0;
        pageflowHyperContainer.setLayout(ngl);

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        pageflowHyperContainer.setLayoutData(gd);

        pageflowHyperLink =
                toolkit.createFormText(pageflowHyperContainer, true);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        pageflowHyperLink.setLayoutData(gd);

        String linkText =
                "<form><p><a href = '" + PAGEFLOW_LINK + "'>" //$NON-NLS-1$ //$NON-NLS-2$
                        + Messages.TaskTypeUserSection_OpenPageflowHyperLink_label
                        + "</a></p></form>"; //$NON-NLS-1$
        pageflowHyperLink.setText(linkText, true, false);
        manageControl(pageflowHyperLink);

        return container;
    }

    private void adjustLabelAlignment() {
        List<Control> labels = new ArrayList<Control>();
        labels.add(userLabel);
        labels.add(formLabel);

        if (pageflowLabel != null) {
            labels.add(pageflowLabel);
        }

        setSameGridDataWidth(labels, null);
        return;
    }

    @Override
    protected void doRefresh() {
        // If controls are disposed then unregister adapter
        if (formText.isDisposed()) {
            return;
        }

        Activity act = getActivity();

        if (act != null) {
            reportBadState(act);

            String formURI = null;
            FormImplementationType formType = null;
            boolean needsLayout = false;

            FormImplementation formImpl =
                    TaskObjectUtil.getUserTaskFormImplementation(act);
            if (formImpl != null) {
                formType = formImpl.getFormType();
                formURI = formImpl.getFormURI();
            }

            if (formType == null) {
                noneRadio.setSelection(true);
            } else {
                noneRadio.setSelection(false);
            }

            if (formURI == null) {
                formURI = ""; //$NON-NLS-1$
            }

            if (doRefreshUserDefinedURLSelectionControls(act, formType, formURI)) {
                needsLayout = true;
            }

            if (doRefreshFormSelectionControls(act, formType, formURI)) {
                needsLayout = true;
            }

            if (doRefreshPageflowSelectionControls(act, formType, formURI)) {
                needsLayout = true;
            }

            adjustLabelAlignment();

            if (needsLayout && !firstRefreshEver) {
                forceLayout();
            }

            firstRefreshEver = false;
        }

        return;
    }

    /**
     * This method checks for consistency of user task FormImplementation and
     * bpmJspTask extended attribute (new and old model for form reference).
     * 
     * @param act
     */
    private void reportBadState(Activity act) {
        FormImplementation formImplementation =
                TaskObjectUtil.getUserTaskFormImplementation(act);
        ExtendedAttribute bpmJspTask =
                TaskObjectUtil.getExtendedAttributeByName(act,
                        TaskObjectUtil.USER_TASK_ATTR);

        boolean bad = false;

        if (formImplementation == null) {
            if (bpmJspTask != null) {
                bad = true;
            }

        } else if (bpmJspTask == null) {
            if (formImplementation != null) {
                bad = true;
            }

        } else {
            String extURI = bpmJspTask.getValue();
            String formURI = formImplementation.getFormURI();

            if (extURI == null) {
                if (formURI == null) {
                    bad = true;
                }
            } else if (formURI == null) {
                if (extURI == null) {
                    bad = true;
                }
            } else {
                if (!formURI.equals(extURI)) {
                    bad = true;
                }

                if (FormImplementationType.FORM.equals(formImplementation
                        .getFormType())) {
                    if (formURI.length() > 0
                            && !formURI.startsWith(TaskObjectUtil.FORM_SCHEMA)) {
                        bad = true;
                    }
                }
            }
        }

        if (bad) {
            System.err
                    .println("MISMATCHED User Task FormImplemtation And bpmJspTask ExtendedAttribute:"); //$NON-NLS-1$
            System.err.println("    " + formImplementation); //$NON-NLS-1$
            System.err.println("    " + bpmJspTask); //$NON-NLS-1$
            System.err.println(""); //$NON-NLS-1$
        }

    }

    private boolean doRefreshUserDefinedURLSelectionControls(Activity act,
            FormImplementationType formType, String formURI) {
        boolean needsLayout = false;

        if (FormImplementationType.USER_DEFINED.equals(formType)) {
            setEnabled(userControls, true);
            userRadio.setSelection(true);
            updateText(userText, formURI);

            //
            // Show the form selection controls if not currently shown.
            if (!userControls.getVisible()) {
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                userControls.setLayoutData(gd);
                userControls.setVisible(true);
                rootContainer.layout(true);
            }

        } else {
            setEnabled(userControls, false);
            userRadio.setSelection(false);
            updateText(userText, ""); //$NON-NLS-1$

            //
            // Hide the form selection controls if not currently hidden.
            if (userControls.getVisible()) {
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                gd.heightHint = 1;
                userControls.setLayoutData(gd);
                userControls.setVisible(false);
                rootContainer.layout(true);
            }
        }

        return needsLayout;
    }

    private boolean doRefreshFormSelectionControls(Activity act,
            FormImplementationType formType, String formURI) {
        boolean needsLayout = false;

        Image img = null;
        String tooltip = ""; //$NON-NLS-1$

        if (FormImplementationType.FORM.equals(formType)) {
            setEnabled(formControls, true);
            formRadio.setSelection(true);

            /*
             * Get the most appropriate page of controls to display
             */
            Composite formControlsPage = null;
            if (Xpdl2ModelUtil.isPageflow(act.getProcess())) {
                formControlsPage = contributedPageflowFormControlsPage;
            } else {
                formControlsPage = contributedFormControlsPage;
            }

            if (formControlsPage == null) {
                formControlsPage = defaultFormControlsPage;
            }

            formTypeBook.showPage(formControlsPage);

            //
            // Show the form selection controls if not currently shown.
            if (!formControls.getVisible()) {
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                formControls.setLayoutData(gd);
                formControls.setVisible(true);
                rootContainer.layout(true);
            }

            /*
             * Depending on which page is showing, update it.
             */
            if (formControlsPage == contributedPageflowFormControlsPage) {
                contributedPageflowFormSection.refresh();
            } else if (formControlsPage == contributedFormControlsPage) {
                contributedFormSection.refresh();
            } else {
                if (formURI == null || formURI.length() == 0) {
                    tooltip =
                            Messages.TaskTypeUserSection_MustSpecifyFormFile_tooltip;
                    img =
                            Xpdl2UiPlugin.getDefault().getImageRegistry()
                                    .get(Xpdl2UiPlugin.IMG_ERROR);
                    updateText(formText, ""); //$NON-NLS-1$

                } else {
                    IFile formFile = TaskObjectUtil.getUserTaskFormFile(act);
                    if (formFile == null || !formFile.exists()) {
                        tooltip =
                                Messages.TaskTypeUserSection_FormFileNotExist_tooltip;
                        img =
                                Xpdl2UiPlugin.getDefault().getImageRegistry()
                                        .get(Xpdl2UiPlugin.IMG_ERROR);
                        updateText(formText,
                                com.tibco.xpd.processeditor.xpdl2.internal.Messages.Process_UnresolvedReferences_message);

                    } else {
                        String disp = formURI;

                        if (disp.contains("//")) { //$NON-NLS-1$
                            disp = disp.split("//")[1]; //$NON-NLS-1$
                        }

                        updateText(formText, disp);
                    }
                }
            }

        } else {
            setEnabled(formControls, false);
            formRadio.setSelection(false);
            updateText(formText, ""); //$NON-NLS-1$

            //
            // Hide the form selection controls if not currently hidden.
            if (formControls.getVisible()) {
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                gd.heightHint = 1;
                formControls.setLayoutData(gd);
                formControls.setVisible(false);
                rootContainer.layout(true);
            }

        }

        formLabel.setToolTipText(tooltip);

        if (img != formLabel.getImage()) {
            formLabel.setImage(img);
            needsLayout = true;
        }

        return needsLayout;
    }

    private boolean doRefreshPageflowSelectionControls(Activity act,
            FormImplementationType formType, String formURI) {

        if (!Xpdl2ResourcesPlugin.isPageflowAvailable()) {
            return false;
        }

        boolean needsLayout = false;

        Image img = null;
        String tooltip = ""; //$NON-NLS-1$

        if (Xpdl2ModelUtil.isPageflow(Xpdl2ModelUtil.getProcess(getInput()))) {
            //
            // Note - shouldn't EVER have to set this back again because
            // property section is per-editor - so can never change from
            // pageflow to non-pageflow even when input changes.
            // That's why there's no counter-code in else clause.

            GridData gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = 1;
            pageflowControls.setLayoutData(gd);
            pageflowControls.setVisible(false);

            gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = 1;
            pageflowRadio.setLayoutData(gd);
            pageflowRadio.setVisible(false);
            rootContainer.layout(true);

        } else {
            if (FormImplementationType.PAGEFLOW.equals(formType)) {
                setEnabled(pageflowControls, true);
                pageflowRadio.setSelection(true);

                //
                // Show the form selection controls if not currently shown.
                if (!pageflowControls.getVisible()) {
                    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                    pageflowControls.setLayoutData(gd);
                    pageflowControls.setVisible(true);
                    rootContainer.layout(true);
                }

                if (formURI == null || formURI.length() == 0) {
                    tooltip =
                            Messages.TaskTypeUserSection_MustSelectPageflow_tooltip;
                    img =
                            Xpdl2UiPlugin.getDefault().getImageRegistry()
                                    .get(Xpdl2UiPlugin.IMG_ERROR);

                    updateText(pageflowText, formURI);

                } else {
                    Process pageflow =
                            TaskObjectUtil.getUserTaskPageflowProcess(act);
                    if (pageflow == null) {
                        tooltip =
                                Messages.TaskTypeUserSection_PageflowNotExist_tooltip;
                        img =
                                Xpdl2UiPlugin.getDefault().getImageRegistry()
                                        .get(Xpdl2UiPlugin.IMG_ERROR);

                        pageflowText.setForeground(ColorConstants.lightGray);
                        updateText(pageflowText,
                                com.tibco.xpd.processeditor.xpdl2.internal.Messages.Process_UnresolvedReferences_message);

                    } else {
                        String procName =
                                Xpdl2ModelUtil.getDisplayNameOrName(pageflow);
                        String pkgName =
                                Xpdl2ModelUtil.getDisplayNameOrName(pageflow
                                        .getPackage());
                        pageflowText
                                .setForeground(ColorConstants.listForeground);
                        updateText(pageflowText, pkgName + "/" + procName); //$NON-NLS-1$
                    }
                }

            } else {
                setEnabled(pageflowControls, false);
                pageflowRadio.setSelection(false);
                updateText(pageflowText, ""); //$NON-NLS-1$

                //
                // Hide the form selection controls if not currently hidden.
                if (pageflowControls.getVisible()) {
                    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                    gd.heightHint = 1;
                    pageflowControls.setLayoutData(gd);
                    pageflowControls.setVisible(false);
                    rootContainer.layout(true);
                }

            }

            pageflowLabel.setToolTipText(tooltip);

            if (img != pageflowLabel.getImage()) {
                pageflowLabel.setImage(img);
                needsLayout = true;
            }
        }
        return needsLayout;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        Activity act = getActivity();

        EditingDomain ed = getEditingDomain();

        if (act != null && ed != null) {
            String currFormURI = null;
            FormImplementationType currFormType = null;

            FormImplementation currFormImpl =
                    TaskObjectUtil.getUserTaskFormImplementation(act);
            if (currFormImpl != null) {
                currFormType = currFormImpl.getFormType();
                currFormURI = currFormImpl.getFormURI();
            }

            if (currFormURI == null) {
                currFormURI = ""; //$NON-NLS-1$
            }

            /*
             * Get the current used for controls section in case we need it.
             */
            ISection currFormControlSection = null;
            if (act.getProcess() != null
                    && Xpdl2ModelUtil.isPageflow(act.getProcess())) {
                currFormControlSection = contributedPageflowFormSection;
            } else {
                currFormControlSection = contributedFormSection;
            }

            if (obj == noneRadio) {
                if (currFormImpl != null) {
                    CompoundCommand ccmd = new CompoundCommand();
                    cmd = ccmd;

                    Command setFormImplCmd =
                            TaskObjectUtil
                                    .getUserTaskSetFormImplementationCommand(ed,
                                            act,
                                            null);

                    ccmd.append(setFormImplCmd);
                    ccmd.setLabel(setFormImplCmd.getLabel());

                    addCleanupFormImplCommand(ccmd,
                            act,
                            currFormImpl,
                            currFormControlSection);
                }

            } else if (obj == userRadio) {
                if (!FormImplementationType.USER_DEFINED.equals(currFormType)) {
                    CompoundCommand ccmd = new CompoundCommand();
                    cmd = ccmd;

                    Command setFormImplCmd =
                            TaskObjectUtil
                                    .getUserTaskSetFormImplementationCommand(ed,
                                            act,
                                            FormImplementationType.USER_DEFINED,
                                            ""); //$NON-NLS-1$
                    ccmd.append(setFormImplCmd);
                    ccmd.setLabel(setFormImplCmd.getLabel());

                    addCleanupFormImplCommand(ccmd,
                            act,
                            currFormImpl,
                            currFormControlSection);

                }

            } else if (obj == formRadio) {
                if (!FormImplementationType.FORM.equals(currFormType)) {
                    /*
                     * If the forms control section implements
                     * ITaskImplementationInitializer tell it that task has just
                     * been set to "form" type and allow it the chance to take
                     * control of the transition to FORM type.
                     */
                    cmd = null;

                    if (currFormControlSection instanceof ITaskImplementationInitializer) {
                        cmd =
                                ((ITaskImplementationInitializer) currFormControlSection)
                                        .getInitialStructureCommand(act);
                    }

                    /*
                     * If not provided a command by the form type implementation
                     * contribution then we'll just set the type ourselves.
                     */
                    if (cmd == null) {
                        cmd =
                                TaskObjectUtil
                                        .getUserTaskSetFormImplementationCommand(ed,
                                                act,
                                                FormImplementationType.FORM,
                                                ""); //$NON-NLS-1$
                    }

                }

            } else if (pageflowRadio != null && obj == pageflowRadio) {
                if (!FormImplementationType.PAGEFLOW.equals(currFormType)) {
                    CompoundCommand ccmd = new CompoundCommand();
                    cmd = ccmd;

                    Command setFormImplCmd =
                            TaskObjectUtil
                                    .getUserTaskSetFormImplementationCommand(ed,
                                            act,
                                            FormImplementationType.PAGEFLOW,
                                            ""); //$NON-NLS-1$
                    ccmd.append(setFormImplCmd);
                    ccmd.setLabel(setFormImplCmd.getLabel());

                    addCleanupFormImplCommand(ccmd,
                            act,
                            currFormImpl,
                            currFormControlSection);

                }

            } else if (obj == userText) {
                String newFormURI = userText.getText();
                if (newFormURI == null) {
                    newFormURI = ""; //$NON-NLS-1$
                }

                if (!newFormURI.equals(currFormURI)) {
                    cmd =
                            TaskObjectUtil
                                    .getUserTaskSetFormImplementationCommand(ed,
                                            act,
                                            FormImplementationType.USER_DEFINED,
                                            newFormURI);
                }

            } else if (obj == formBrowse) {
                cmd =
                        TaskObjectUtil
                                .getUserTaskSetFormFileFromPickerCommand(getSite()
                                        .getShell(),
                                        ed,
                                        act);

            } else if (pageflowBrowse != null && obj == pageflowBrowse) {
                cmd =
                        TaskObjectUtil
                                .getUserTaskSetPageflowProcessFromPickerCommand(getSite()
                                        .getShell(),
                                        ed,
                                        act);

            } else if (FORM_LINK.equals(obj)) {
                if (!openUrl(act, currFormImpl)) {
                    // If the URL cannot be opened then show the picker.
                    cmd = doGetCommand(formBrowse);
                }

            } else if (pageflowHyperLink != null && PAGEFLOW_LINK.equals(obj)) {
                if (!openUrl(act, currFormImpl)) {
                    // If the URL cannot be opened then show the picker.
                    cmd = doGetCommand(pageflowBrowse);
                }
            }

            /*
             * If form implementation radio button was used and the command is
             * unexecutable (for instance a form section contributor allowed
             * user to cancel) then we must force a refresh to put the controls
             * back how they were.
             */
            if (obj == noneRadio || obj == userRadio || obj == formRadio
                    || obj == pageflowRadio) {
                if (cmd != null) {
                    if (!cmd.canExecute()) {
                        Display.getDefault().asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    } else {
                        if (TaskObjectUtil.isTaskUsingDefaultIconForType(act)) {
                            /*
                             * Current set/unset icon is same as default so can
                             * reset to default for nest task type.
                             * 
                             * (But not til aftet the rest of command has
                             * changed task type.
                             */
                            CompoundCommand c = new CompoundCommand();
                            c.append(cmd);
                            c.setLabel(cmd.getLabel());
                            c.append(new TaskObjectUtil.LateExecSetDefTaskIconCommand(
                                    ed, act));
                            cmd = c;
                        }

                    }
                }
            }

        }

        return cmd;
    }

    /**
     * If switching away from Form implementation and the forms control section
     * implements ITaskImplementationInitializer tell it that task has just been
     * unset to "form" type.
     * 
     * @param ccmd
     * @param act
     * @param currFormImpl
     * @param currFormControlSection
     */
    private void addCleanupFormImplCommand(CompoundCommand ccmd, Activity act,
            FormImplementation currFormImpl, ISection currFormControlSection) {
        if (currFormImpl != null
                && FormImplementationType.FORM.equals(currFormImpl
                        .getFormType())) {
            if (currFormControlSection instanceof ITaskImplementationInitializer) {
                Command cleanupFormCmd =
                        ((ITaskImplementationInitializer) currFormControlSection)
                                .getCleanupCommand(act);
                if (cleanupFormCmd != null) {
                    ccmd.append(cleanupFormCmd);
                }
            }
        }

        return;
    }

    private boolean openUrl(Activity act, FormImplementation currFormImpl) {
        boolean opened = false;
        if (currFormImpl != null) {
            if (FormImplementationType.FORM.equals(currFormImpl.getFormType())) {
                IFile formFile = TaskObjectUtil.getUserTaskFormFile(act);
                if (formFile != null && formFile.exists()) {
                    IWorkbenchPage page =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage();
                    try {
                        IDE.openEditor(page, formFile);
                        opened = true;

                    } catch (PartInitException e) {
                        Xpdl2ProcessEditorPlugin.getDefault().getLogger()
                                .error(e);
                    }
                }

            } else if (FormImplementationType.PAGEFLOW.equals(currFormImpl
                    .getFormType())) {
                Process pageflowProcess =
                        TaskObjectUtil.getUserTaskPageflowProcess(act);
                if (pageflowProcess != null) {
                    IConfigurationElement facConfig =
                            XpdResourcesUIActivator
                                    .getEditorFactoryConfigFor(pageflowProcess);
                    if (facConfig != null) {
                        String editorId = facConfig.getAttribute("editorID"); //$NON-NLS-1$
                        try {
                            EditorInputFactory f =
                                    (EditorInputFactory) facConfig
                                            .createExecutableExtension("factory"); //$NON-NLS-1$
                            IEditorInput input =
                                    f.getEditorInputFor(pageflowProcess);
                            IWorkbenchPage page =
                                    PlatformUI.getWorkbench()
                                            .getActiveWorkbenchWindow()
                                            .getActivePage();
                            IEditorPart part =
                                    IDE.openEditor(page, input, editorId);
                            if (part instanceof IGotoEObject) {
                                ((IGotoEObject) part).gotoEObject(true,
                                        pageflowProcess);
                                opened = true;
                            }
                        } catch (CoreException e) {
                            Xpdl2ProcessEditorPlugin.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }
            }
        }
        return opened;
    }

    @Override
    public String getLocalId() {
        return "analyst.userSection"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
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

}
