/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.documentoperationsservice.CaseDocLinkUnlinkOperationPage.OPERATION_TYPE;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.CommandContentAssistTextHandler;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The Details Section for the Case Document Operations Service Task.
 * 
 * @author aprasad
 * @since 12-Aug-2014
 */
public class DocumentOperationsTaskServiceSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution {

    /**
     * {@link XpdExtensionPackage} instance.
     */
    private static final XpdExtensionPackage XPD_EXT_PKG =
            XpdExtensionPackage.eINSTANCE;

    /**
     * {@link XpdExtensionFactory} instance.
     */
    private static final XpdExtensionFactory XPD_EXT_FAC =
            XpdExtensionFactory.eINSTANCE;

    /**
     * Map of Case Document Operation Feature reference and respective Property
     * Detail Section Page Contributions.
     */
    private final Map<EReference, AbstractDocumentOperationPage<EObject>> caseDocOperationPagesMap;

    /**
     * Pagebook to display the pages for the various Case Document Operations.
     */
    private ScrolledPageBook caseDocOperationPageBook;

    /**
     * Root composite container.
     */
    private Composite rootContainer;

    /**
     * Drop Down Combo for Case Document Operation selection.
     */
    private ComboViewer caseDocOperationsCombo;

    /**
     * Label Control displaying 'Operations'
     */
    private Label operationLabel;

    /**
     * Constructor
     */
    public DocumentOperationsTaskServiceSection() {

        super(Xpdl2Package.eINSTANCE.getActivity());

        caseDocOperationPagesMap =
                new HashMap<EReference, AbstractDocumentOperationPage<EObject>>();

    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     * 
     * @return
     */
    @Override
    public String getLocalId() {
        return "DocumentOperations"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    @Override
    public String getPluginId() {

        return NativeServicesActivator.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        /* Get the Case Document Operation for the input Activity */
        DocumentOperation caseDocumentOperationForInputActivity =
                getDocumentOperationForInputActivity();

        if (caseDocumentOperationForInputActivity != null) {
            /* Get Case Document Find Operation */
            CaseDocFindOperations caseDocFindOperation =
                    caseDocumentOperationForInputActivity
                            .getCaseDocFindOperations();

            /* Get the Case Document Reference Operation */
            CaseDocRefOperations caseDocRefOperation =
                    caseDocumentOperationForInputActivity
                            .getCaseDocRefOperation();

            /* Link System Document Operation */
            LinkSystemDocumentOperation linkSysDocOperation =
                    caseDocumentOperationForInputActivity
                            .getLinkSystemDocumentOperation();

            IStructuredSelection select = null;
            AbstractDocumentOperationPage<EObject> pageToShow = null;
            EObject operation = null;

            Object selectedOpr = null;

            if (caseDocFindOperation != null) {

                selectedOpr = CaseDocFindOperationPage.FIND_OPERATION_FEATURE;
                operation = caseDocFindOperation;

            } else if (caseDocRefOperation != null) {

                if (caseDocRefOperation.getMoveCaseDocOperation() != null) {

                    selectedOpr =
                            CaseDocMoveOperationPage.MOVE_OPERATION_FEATURE;

                } else if (caseDocRefOperation.getLinkCaseDocOperation() != null) {

                    selectedOpr =
                            CaseDocLinkUnlinkOperationPage.LINK_OPERATION_FEATURE;

                } else if (caseDocRefOperation.getUnlinkCaseDocOperation() != null) {

                    selectedOpr =
                            CaseDocLinkUnlinkOperationPage.UNLINK_OPERATION_FEATURE;

                } else if (caseDocRefOperation.getDeleteCaseDocOperation() != null) {

                    selectedOpr =
                            CaseDocDeleteOperationPage.DELETE_OPERATION_FEATURE;
                }

                operation = caseDocRefOperation;
            } else if (linkSysDocOperation != null) {

                operation = linkSysDocOperation;
                selectedOpr =
                        LinkSystemDocumentOperationPage.LINK_SYS_DOC_OPERATION_FEATURE;
            }
            /* Refresh appropriate Operation Page */
            if (selectedOpr != null) {
                select =
                        new StructuredSelection(
                                caseDocOperationPagesMap.get(selectedOpr));
                caseDocOperationsCombo.setSelection(select, true);
                pageToShow = caseDocOperationPagesMap.get(selectedOpr);
                pageToShow.update(operation);
                caseDocOperationPageBook.showPage(pageToShow);
                rootContainer.layout();
            }

        } else {
            /* Show empty Page in all invalid cases */
            caseDocOperationsCombo.setSelection(new StructuredSelection());
            caseDocOperationPageBook.showEmptyPage();
            rootContainer.layout();
        }

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return root container for this section.
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        rootContainer = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(2, false);
        rootContainer.setLayout(gl);

        Label label =
                toolkit.createLabel(rootContainer,
                        Messages.CaseDocumentTaskServiceSection_Description);
        GridData gData =
                GridDataFactory.swtDefaults().span(2, 1).indent(5, 0).create();
        label.setLayoutData(gData);

        /* Operations Label & DropDown */
        operationLabel = toolkit.createLabel(rootContainer, "Operation:"); //$NON-NLS-1$
        gData = GridDataFactory.swtDefaults().indent(5, 5).create();
        operationLabel.setLayoutData(gData);

        caseDocOperationsCombo =
                new ComboViewer(toolkit.createCCombo(rootContainer,
                        SWT.READ_ONLY));

        caseDocOperationsCombo.getCCombo().setLayoutData(GridDataFactory
                .fillDefaults().grab(true, false).indent(18, 5).create());

        caseDocOperationsCombo.setContentProvider(new ArrayContentProvider());
        caseDocOperationsCombo
                .setLabelProvider(new CaseDocOperationsLabelProvider(
                        caseDocOperationPagesMap));

        manageControl(caseDocOperationsCombo.getCCombo());

        /* Operation Specific pages */

        Label filler = toolkit.createLabel(rootContainer, ""); //$NON-NLS-1$

        filler.setLayoutData(GridDataFactory.copyData((GridData) operationLabel
                .getLayoutData()));

        Composite subPagesContainer = toolkit.createComposite(rootContainer);
        subPagesContainer.setLayout(new FillLayout());
        GridData gdData = new GridData(GridData.FILL_BOTH);
        subPagesContainer.setBackground(ColorConstants.gray);
        gdData.horizontalIndent = 12;
        subPagesContainer.setLayoutData(gdData);

        createCaseDocOperationsSectionPages(subPagesContainer, toolkit);

        /* Sort the input before setting it to the drop down */
        List<AbstractDocumentOperationPage> pages =
                new ArrayList<AbstractDocumentOperationPage>(
                        caseDocOperationPagesMap.values());
        Collections.sort(pages);
        caseDocOperationsCombo.setInput(pages);

        // Register all text controls
        for (AbstractDocumentOperationPage<EObject> page : caseDocOperationPagesMap
                .values()) {

            /* Collect all controls of the SubPages to manage */
            for (Control control : page.getManagedControls()) {
                if (control instanceof CCombo) {
                    manageControl((CCombo) control);
                } else if (control instanceof Text) {
                    manageControl((Text) control);
                } else if (control instanceof Button) {
                    manageControl((Button) control);
                }
            }
            for (ContentAssistText text : page.getContentAssistTexts()) {
                manageControl(text);
            }
        }
        return rootContainer;
    }

    protected void manageControl(final ContentAssistText control) {
        new CommandContentAssistTextHandler(control, this);
    }

    /**
     * Creates the Pages for the case Document operations
     * 
     * @param container
     *            parent {@link Composite}.
     * @param toolkit
     *            {@link XpdFormToolkit} to create the Controls.
     */
    private void createCaseDocOperationsSectionPages(Composite container,
            XpdFormToolkit toolkit) {

        Composite sectionContainer = toolkit.createComposite(container);
        // sectionContainer.setBackground(ColorConstants.green);
        GridLayout gl = new GridLayout(2, false);
        gl.horizontalSpacing = 0;

        sectionContainer.setLayout(gl);

        /*
         * Pagebook for each operation type's controls
         */
        caseDocOperationPageBook =
                toolkit.createPageBook(sectionContainer, SWT.NONE);
        caseDocOperationPageBook.setExpandHorizontal(true);
        caseDocOperationPageBook.setExpandVertical(true);

        int minHeight = 0;

        /* Page For FindByName */
        AbstractDocumentOperationPage operationPage =
                new CaseDocFindOperationPage(this);

        int height =
                registerPage(toolkit,
                        CaseDocFindOperationPage.FIND_OPERATION_FEATURE,
                        operationPage);
        minHeight = Math.max(minHeight, height);

        /* Page For Move */
        operationPage = new CaseDocMoveOperationPage(this);

        height =
                registerPage(toolkit,
                        XPD_EXT_PKG
                                .getCaseDocRefOperations_MoveCaseDocOperation(),
                        operationPage);
        minHeight = Math.max(minHeight, height);

        /* Page For Unlink */
        operationPage =
                new CaseDocLinkUnlinkOperationPage(this, OPERATION_TYPE.UNLINK);

        height =
                registerPage(toolkit,
                        XPD_EXT_PKG
                                .getCaseDocRefOperations_UnlinkCaseDocOperation(),
                        operationPage);
        minHeight = Math.max(minHeight, height);

        /* Page For Link */
        operationPage =
                new CaseDocLinkUnlinkOperationPage(this, OPERATION_TYPE.LINK);

        height =
                registerPage(toolkit,
                        XPD_EXT_PKG
                                .getCaseDocRefOperations_LinkCaseDocOperation(),
                        operationPage);
        minHeight = Math.max(minHeight, height);

        /* Page For Delete */
        operationPage = new CaseDocDeleteOperationPage(this);

        height =
                registerPage(toolkit,
                        XPD_EXT_PKG
                                .getCaseDocRefOperations_DeleteCaseDocOperation(),
                        operationPage);
        minHeight = Math.max(minHeight, height);

        /* Page For Link System Document */
        operationPage = new LinkSystemDocumentOperationPage(this);

        height =
                registerPage(toolkit,
                        LinkSystemDocumentOperationPage.LINK_SYS_DOC_OPERATION_FEATURE,
                        operationPage);

        minHeight = Math.max(minHeight, height);

        /* Set Layout for PageBook */

        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.grabExcessHorizontalSpace = true;
        gd.minimumHeight = minHeight;
        caseDocOperationPageBook.setLayoutData(gd);

    }

    /**
     * Creates and registers the page with the pagebook.
     * 
     * @param toolkit
     * @param operationERef
     *            , feature reference of the Operation the page represents.
     * @param page
     *            , page to register.
     * 
     * @return minHeight for this particular page control
     */
    private int registerPage(XpdFormToolkit toolkit, EReference operationERef,
            AbstractDocumentOperationPage page) {

        caseDocOperationPagesMap.put(operationERef, page);

        Control pageControl =
                page.createPage(caseDocOperationPageBook.getContainer(),
                        toolkit);

        caseDocOperationPageBook.registerPage(page, pageControl);

        return pageControl.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return Appropriate Command for the object, null if does not match the
     *         criteria, i.e not a Control belonging to any of the pages in the
     *         Section.
     */
    @Override
    protected Command doGetCommand(Object obj) {

        if (getInput() instanceof Activity && obj instanceof Control) {
            Control control = (Control) obj;
            Activity activity = (Activity) getInput();
            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

            boolean docOprTaskAct = false;

            if (TaskType.SERVICE_LITERAL.equals(taskType)) {

                String extensionId =
                        TaskObjectUtil
                                .getTaskImplementationExtensionId(activity);

                if (TaskImplementationTypeDefinitions.DOCUMENT_OPERATIONS
                        .equals(extensionId)) {
                    docOprTaskAct = true;
                }

            }
            if (!docOprTaskAct) {
                /*
                 * Return null if input is not a Document Operations Activity
                 */
                return null;
            }

            /* return null when no operation is selected in the combo */
            /* get Selection From the Combo */
            ISelection selection = caseDocOperationsCombo.getSelection();

            if (selection.isEmpty()
                    || !(selection instanceof IStructuredSelection)) {
                return null;
            }

            /* get Selected Page */
            Object sel = ((IStructuredSelection) selection).getFirstElement();

            AbstractDocumentOperationPage pageAsPerComboSel =
                    (AbstractDocumentOperationPage) sel;

            Control currentPage = caseDocOperationPageBook.getCurrentPage();

            if (pageAsPerComboSel != null) {

                /* handle operation combo selection change */
                if (caseDocOperationsCombo.getCCombo().equals(control)) {
                    /*
                     * do nothing and return if selection is same as last
                     * selection
                     */
                    if (pageAsPerComboSel != null
                            && pageAsPerComboSel.getRootContainer()
                                    .equals(currentPage)) {
                        return null;
                    }

                    TaskService taskService =
                            ((Task) activity.getImplementation())
                                    .getTaskService();
                    // Reset Operation in the Service Task
                    return getResetOperationCmd(pageAsPerComboSel.getOperationFeatureRef(),
                            taskService);
                } else {

                    /* Let the pages handle for their own controls */
                    for (AbstractDocumentOperationPage page : caseDocOperationPagesMap
                            .values())

                        if (page.containsControl(control)) {

                            DocumentOperation caseDocumentOperationForInputActivity =
                                    getDocumentOperationForInputActivity();

                            /*
                             * Although it won't be null, as it is created on
                             * combo selection change
                             */
                            if (caseDocumentOperationForInputActivity != null) {

                                EObject operation =
                                        page.getOperation(caseDocumentOperationForInputActivity);

                                if (operation != null) {
                                    return page.getCommand(getEditingDomain(),
                                            operation,
                                            control);
                                }
                            }
                        }

                }
            }

        }

        return null;
    }

    /**
     * Returns the command to reset the Service task with a new Case Document
     * Operation.
     * 
     * @param operationFeatureRef
     *            , Featurer reference for the Case Document Operation.
     * @param taskService
     *            , Service Task to reset the Case Document Operation.
     * @return, command to reset the Case Document Operation for the given
     *          Service Task.
     */
    private Command getResetOperationCmd(EReference operationFeatureRef,
            TaskService taskService) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.CaseDocumentTaskServiceSection_SetCaseDocumentOperationTypeCommandLabel);

        DocumentOperation caseDocOpr = XPD_EXT_FAC.createDocumentOperation();

        cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(getEditingDomain(),
                taskService,
                XPD_EXT_PKG.getDocumentRoot_DocumentOperation(),
                caseDocOpr));

        Command subCommand = null;

        for (AbstractDocumentOperationPage<EObject> page : caseDocOperationPagesMap
                .values()) {

            if (subCommand == null) {

                subCommand =
                        page.getResetOperationCommand(operationFeatureRef,
                                caseDocOpr);

                if (subCommand != null) {
                    break;
                }
            }

        }

        if (subCommand != null) {
            cmd.append(subCommand);
        }

        return cmd;
    }

    /**
     * @return {@link DocumentOperation} for the input activity for this
     *         Section.Returns null if not found.
     */
    private DocumentOperation getDocumentOperationForInputActivity() {
        EObject eObject = getInput();

        if (eObject instanceof Activity) {

            return CaseDocumentOperationsHelpUtiliy
                    .getDocumentOperation((Activity) eObject);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#getPluginContributon()
     */
    @Override
    public IPluginContribution getPluginContributon() {
        return this;
    }

    /**
     * Simple label provider for the operations combos.
     * 
     * 
     * @author aprasad
     */
    private static class CaseDocOperationsLabelProvider extends LabelProvider {

        private final Map<EReference, ?> map;

        public CaseDocOperationsLabelProvider(Map<EReference, ?> map) {
            this.map = map;

        }

        @Override
        public String getText(Object element) {

            if (element instanceof EReference) {

                Object page = map.get(element);

                if (page instanceof AbstractDocumentOperationPage<?>) {
                    return ((AbstractDocumentOperationPage<?>) page).getLabel();
                }

            } else if (element instanceof AbstractDocumentOperationPage) {
                return ((AbstractDocumentOperationPage<?>) element).getLabel();
            }

            return super.getText(element);
        }
    }

}
