/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.process;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.CaseClassPicker;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.general.CaseActionPrivilegeConfigSection;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider.TemplateProviderIdentifier;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.VisibleForCaseStates;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Case Service process property section.
 * 
 * @author bharge
 * @since 29 Jul 2014
 */
public class CaseServicePropertySection extends ProcessPropertySection {

    protected static final String CASE_SERVICE_SECTION = "caseService"; //$NON-NLS-1$

    private static final String NO_CASE_STATE_SET_ITEM =
            "_NO_CASE_STATE_SET_ITEM_"; //$NON-NLS-1$

    private CaseActionPrivilegeConfigSection privilegesConfigurationSection;

    private CaseClassPicker caseClassPickerCtrl;

    private Table caseStatesControl;

    private Button btnSpecificStates;

    private Button btnAllStates;

    /**
     * 
     */
    public CaseServicePropertySection() {

        privilegesConfigurationSection = new CaseActionPrivilegeConfigSection();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        privilegesConfigurationSection.setInput(part, selection);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#createWizardProcessTemplateProvider()
     * 
     * @return
     */
    @Override
    protected ProcessTemplateProvider createWizardProcessTemplateProvider() {

        return new ProcessTemplateProvider((Package) getInputContainer(),
                TemplateProviderIdentifier.CASESERIVCE) {
            /**
             * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#getApplicableInterfaces(com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup)
             * 
             * @param interfaceGroup
             * @return
             */
            @Override
            protected List getApplicableInterfaces(
                    ProcessInterfaceGroup interfaceGroup) {

                List<Object> processInterfaces = new ArrayList<Object>();

                List children = interfaceGroup.getChildren();
                /*
                 * XPD-7298: New Case Service creation wizard should only list
                 * process interface templates.
                 */
                for (Object object : children) {

                    if (object instanceof ProcessInterface) {

                        if (Xpdl2ModelUtil
                                .isProcessInterface((ProcessInterface) object)) {
                            processInterfaces.add(object);
                        }
                    }
                }
                return processInterfaces;
            }

            /**
             * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#isApplicableInterfaceType(java.lang.String)
             * 
             * @param type
             * @return
             */
            @Override
            protected boolean isApplicableInterfaceType(
                    ProcessResourceItemType processResourceType) {
                /*
                 * XPD-7298: New Case Service creation wizard should only list
                 * process interface templates.
                 */
                return ProcessResourceItemType.PROCESSINTERFACE
                        .equals(processResourceType);
            }
        };
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        boolean ok = false;
        EObject eo = getBaseSelectObject(toTest);
        if (eo instanceof Process) {

            Process process = (Process) eo;
            ok = Xpdl2ModelUtil.isCaseService(process);
        }
        return ok;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#addExpandableSections(com.tibco.xpd.ui.properties.ExpandableSectionStacker)
     * 
     * @param headerController
     */
    @Override
    protected void addExpandableSections(
            ExpandableSectionStacker headerController) {

        headerController.addSection(CASE_SERVICE_SECTION,
                Messages.PageflowPropertySection_CaseServiceSection_title,
                SWT.DEFAULT,
                true,
                true);

        super.addExpandableSections(headerController);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.ProcessPropertySection#createExpandableSectionContent(java.lang.String,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param sectionId
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {

        if (CASE_SERVICE_SECTION.equals(sectionId)) {

            return createCaseServiceControls(toolkit, container);
        } else {

            return super.createExpandableSectionContent(sectionId,
                    container,
                    toolkit);
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#doRefresh()
     * 
     */
    @Override
    public void doRefresh() {
        if (caseClassPickerCtrl != null) {

            CaseService caseService = getCaseService((Process) getInput());

            if (caseService instanceof CaseService) {
                CaseService cs = caseService;

                ExternalReference externalRef = cs.getCaseClassType();
                if (externalRef != null) {
                    caseClassPickerCtrl.setValue(ProcessUIUtil
                            .xpdl2RefToComplexDataTypeRef(externalRef));

                    /* create and initialise case state selection controls */
                    setCaseStateSectionControls(cs);
                }
            } else {
                caseClassPickerCtrl.setValue(null);
                disableCaseStateSectionControls();
            }
        }
        super.doRefresh();
    }

    /**
     * @param caseService
     */
    private void setCaseStateSectionControls(CaseService caseService) {

        ExternalReference extRef = caseService.getCaseClassType();
        IProject project = WorkingCopyUtil.getProjectFor(extRef);
        Class caseClass = getReferencedClass(project, extRef);

        if (caseClass == null) {

            disableCaseStateSectionControls();
            return;
        }

        List<EnumerationLiteral> caseStateList =
                getCaseStatesList(caseClass, extRef);

        /*
         * If case class has no case state, disable specific states selection
         * control
         */
        if (caseStateList == null || caseStateList.isEmpty()) {
            disableCaseStateSectionControls();
            return;

        }
        createCaseStatesTableItems(caseService, caseStateList);

        // enable/disable appropriate controls
        VisibleForCaseStates visForCaseStates =
                caseService.getVisibleForCaseStates();
        if (visForCaseStates == null) {

            btnAllStates.setSelection(true);
            btnAllStates.setEnabled(true);
            btnSpecificStates.setSelection(false);
            btnSpecificStates.setEnabled(true);
            caseStatesControl.setEnabled(false);

        } else {

            btnAllStates.setSelection(false);
            btnSpecificStates.setSelection(true);
            btnAllStates.setEnabled(true);
            btnSpecificStates.setEnabled(true);
            caseStatesControl.setEnabled(true);

            // select states that are in the model
            for (ExternalReference stateRef : visForCaseStates.getCaseState()) {
                for (TableItem item : caseStatesControl.getItems()) {
                    if (item.getData() instanceof ExternalReference) {
                        if (EcoreUtil.equals(stateRef,
                                (ExternalReference) item.getData())) {
                            item.setChecked(true);
                            break;
                        }
                    }
                }
            }
            /*
             * if visibleForUnsetCaseState is set in the model, then
             * check/uncheck the first table item accordingly
             */
            if (caseStatesControl != null
                    && caseStatesControl.getItemCount() > 0) {
                TableItem item = caseStatesControl.getItem(0);
                item.setChecked(visForCaseStates.isVisibleForUnsetCaseState());
            }
        }
    }

    /**
     * Reset and disable case state selection controls
     */
    private void disableCaseStateSectionControls() {

        btnAllStates.setSelection(true);
        btnAllStates.setEnabled(false);
        btnSpecificStates.setSelection(false);
        btnSpecificStates.setEnabled(false);
        caseStatesControl.setEnabled(false);
        caseStatesControl.removeAll();
    }

    /**
     * @param caseClass
     * @return list of case state enum literals for the given case class
     */
    private List<EnumerationLiteral> getCaseStatesList(Class caseClass,
            ExternalReference caseClassExtRef) {

        List<EnumerationLiteral> states = new ArrayList<>();
        Property caseState = getCaseState(caseClass);

        if (caseState != null && caseState.getType() instanceof Enumeration) {

            Enumeration c = (Enumeration) caseState.getType();
            states.addAll(c.getOwnedLiterals());
        }
        return states;
    }

    /**
     * @param caseClass
     * @return caseState attribute of the given caseClass or null
     */
    private Property getCaseState(Class caseClass) {

        Property caseState = null;
        for (Property property : caseClass.getAllAttributes()) {
            /*
             * If the property is a case state, then use it as we only allow one
             * case state per case class
             */
            if (GlobalDataProfileManager.getInstance().isCaseState(property)) {
                caseState = property;
                break;
            }
        }
        return caseState;
    }

    /**
     * @param toolkit
     * @param container
     * @return case service configuration controls.
     */
    private Control createCaseServiceControls(XpdFormToolkit toolkit,
            Composite container) {

        GridLayoutFactory.swtDefaults().applyTo(container);
        Composite caseServices = toolkit.createComposite(container);
        GridLayoutFactory.swtDefaults().numColumns(5).applyTo(caseServices);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(caseServices);

        // create composite for case class (1st column)
        Composite caseServicesComposite = toolkit.createComposite(caseServices);
        GridLayoutFactory.swtDefaults().numColumns(2)
                .applyTo(caseServicesComposite);
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(caseServicesComposite);
        createCaseServiceCompositeControls(caseServicesComposite, toolkit);

        // create separator
        GridDataFactory.fillDefaults()
                .applyTo(toolkit.createSeparator(caseServices, SWT.VERTICAL));

        // create composite for case states selection (2nd column)
        Composite caseStatesComposite = toolkit.createComposite(caseServices);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(caseStatesComposite);
        GridLayoutFactory.swtDefaults().numColumns(1)
                .applyTo(caseStatesComposite);
        createControlsForCaseStateSelection(caseStatesComposite, toolkit);

        // create separator
        GridDataFactory.fillDefaults()
                .applyTo(toolkit.createSeparator(caseServices, SWT.VERTICAL));

        // create composite privileges (3rd column)
        Composite privilgesComposite = toolkit.createComposite(caseServices);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(privilgesComposite);
        privilegesConfigurationSection.createControls(privilgesComposite,
                getPropertySheetPage());

        return caseServices;
    }

    /**
     * @param page
     * @param toolkit
     */
    private void createControlsForCaseStateSelection(final Composite parent,
            XpdFormToolkit toolkit) {

        Label caseStateSelectionMessage =
                toolkit.createLabel(parent,
                        Messages.CaseServicePropertySection_caseStateSelectionLabel2);
        caseStateSelectionMessage.setLayoutData(new GridData(SWT.LEFT, SWT.TOP,
                false, false));

        Composite caseStatesControlsComposite = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(2, false);
        caseStatesControlsComposite.setLayout(gl);
        caseStatesControlsComposite.setLayoutData(new GridData(SWT.LEFT,
                SWT.TOP, true, false));
        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(caseStatesControlsComposite);

        // create radio buttons for 'All States' and 'Specific States'
        btnAllStates =
                toolkit.createButton(caseStatesControlsComposite,
                        Messages.CaseServicePropertySection_labelAllStates,
                        SWT.RADIO);
        btnAllStates.setSelection(true);

        btnSpecificStates =
                toolkit.createButton(caseStatesControlsComposite,
                        Messages.CaseServicePropertySection_labelSpecificStates,
                        SWT.RADIO);
        manageControl(btnAllStates);
        manageControl(btnSpecificStates);

        // create a table to list case states
        Composite c = toolkit.createComposite(parent);
        c.setLayoutData(new GridData(GridData.FILL_BOTH));

        GridLayout gl2 = new GridLayout(1, false);
        gl2.marginLeft = 3;
        c.setLayout(gl2);

        caseStatesControl =
                toolkit.createTable(c,
                        SWT.FULL_SELECTION | SWT.CHECK,
                        "caseStatesSelectionTable"); //$NON-NLS-1$
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.minimumHeight = 75;
        caseStatesControl.setLayoutData(gd);

        TableLayout tl = new TableLayout();
        tl.addColumnData(new ColumnWeightData(20, false));

        caseStatesControl.setLayout(tl);
        GridDataFactory.fillDefaults().grab(true, true)
                .applyTo(caseStatesControl);

        manageControl(caseStatesControl);
    }

    /**
     * Adds case state enum items to the Case States Table Control
     * 
     * @param caseService
     * @param caseStateList
     */
    private void createCaseStatesTableItems(CaseService caseService,
            List<EnumerationLiteral> caseStateList) {

        if (caseService != null) {

            caseStatesControl.removeAll();

            // Add first item for selecting 'no case state set' option
            TableItem item = new TableItem(caseStatesControl, SWT.NONE);

            item.setData(NO_CASE_STATE_SET_ITEM);
            item.setText(Messages.CaseServicePropertySection_NoCaseStateSetLabel);
            item.setImage(Xpdl2ProcessEditorPlugin.getDefault()
                    .getImageRegistry()
                    .get(ProcessEditorConstants.ICON_ENUMLIT_GREY));

            // Create table items for case states
            for (EnumerationLiteral caseState : caseStateList) {
                item = new TableItem(caseStatesControl, SWT.NONE);
                item.setData(ProcessUIUtil
                        .getExternalRefForEnumLit(caseService, caseState));
                item.setText(PrimitivesUtil.getDisplayLabel(caseState));
                item.setImage(Xpdl2ProcessEditorPlugin.getDefault()
                        .getImageRegistry()
                        .get(ProcessEditorConstants.ICON_ENUMLIT));
            }
        }
    }

    /**
     * create controls to select case class for a case service
     * 
     * @param page
     * @param toolkit
     */
    private void createCaseServiceCompositeControls(final Composite parent,
            XpdFormToolkit toolkit) {

        Label lblMsg =
                toolkit.createLabel(parent,
                        Messages.CaseServicePropertySection_caseClassSelectionLabel);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;
        lblMsg.setLayoutData(gridData);

        final CLabel labelCaseRefType =
                toolkit.createCLabel(parent,
                        Messages.CaseServicePropertySection_labelCaseClass);
        GridData gd = new GridData();
        gd.horizontalAlignment = SWT.LEFT;
        gd.verticalAlignment = SWT.CENTER;
        labelCaseRefType.setLayoutData(gd);
        caseClassPickerCtrl =
                new CaseClassPicker(parent, SWT.NONE, toolkit,
                        getEditingDomain(), getSectionContainerType()) {

                    /**
                     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#getProject()
                     * 
                     * @return
                     */
                    @Override
                    protected IProject getProject() {
                        return CaseServicePropertySection.this.getProject();
                    }

                    /**
                     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#clearErrorTooltip()
                     * 
                     */
                    @Override
                    protected void clearErrorTooltip() {
                        labelCaseRefType.setImage(null);
                        labelCaseRefType.setToolTipText(null);
                    }

                    /**
                     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#setErrorTooltip(java.lang.String)
                     * 
                     * @param tooltip
                     */
                    @Override
                    protected void setErrorTooltip(String tooltip) {
                        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
                            labelCaseRefType.setImage(errIcon);
                            labelCaseRefType
                                    .setToolTipText(Messages.CaseServicePropertySection_noCaseClassSelectedMessage);
                        }
                    }

                    @Override
                    protected Command setCaseRefTypeCmd(RecordType recordType) {
                        return CaseServicePropertySection.this
                                .setCaseClassCmd(recordType);
                    }

                    /**
                     * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#createHyperlink(com.tibco.xpd.ui.properties.XpdFormToolkit,
                     *      org.eclipse.swt.widgets.Composite)
                     * 
                     * @param toolkit
                     * @param root
                     * @return
                     * 
                     *         Note: Overriding this to set the width of the
                     *         link, which grows bigger than the width of the
                     *         parent section if the text is long.
                     */
                    @Override
                    protected ImageHyperlink createHyperlink(
                            XpdFormToolkit toolkit, Composite root) {

                        ImageHyperlink link =
                                toolkit.createImageHyperlink(root,
                                        SWT.NONE,
                                        "type-name-hyperlink"); //$NON-NLS-1$

                        /*
                         * Make sure we set some text in hyperlink before
                         * initial layour otherwise hyperlink doiesn't size text
                         * space vertically big enough
                         */
                        link.setText(""); //$NON-NLS-1$

                        GridData data =
                                new GridData(SWT.LEFT, SWT.CENTER, true, false);
                        data.minimumWidth = 90;
                        /*
                         * Need to set widthHint otherwise the link becomes too
                         * big if the class name is long
                         */
                        data.widthHint = 200;
                        link.setLayoutData(data);

                        return link;
                    }
                };

        GridDataFactory.fillDefaults().grab(true, false)
                .applyTo(caseClassPickerCtrl);
    }

    /**
     * @param recordType
     * @return command to set the given case class reference to the case service
     */
    protected Command setCaseClassCmd(RecordType caseRefType) {
        CompoundCommand cmpCommand = new CompoundCommand();

        Process proc = (Process) getInput();

        ExternalReference externalRef =
                EcoreUtil.copy(caseRefType.getMember().get(0)
                        .getExternalReference());

        Object existingCaseService =
                Xpdl2ModelUtil.getOtherElement(proc,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CaseService());

        if (existingCaseService instanceof CaseService) {

            CaseService cs = (CaseService) existingCaseService;
            // remove existing case service
            cmpCommand.append(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(getEditingDomain(),
                            proc,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CaseService(),
                            cs));

        }

        /* create new case service and add to the process */

        CaseService caseService =
                XpdExtensionFactory.eINSTANCE.createCaseService();
        caseService.setCaseClassType(externalRef);
        cmpCommand.append(Xpdl2ModelUtil
                .getSetOtherElementCommand(getEditingDomain(),
                        proc,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CaseService(),
                        caseService));

        cmpCommand
                .setLabel(Messages.CaseServicePropertySection_setCaseClassCmdLabel);
        return cmpCommand;
    }

    /**
     * Get the class referenced in the given external reference.
     * 
     * @param project
     *            context project
     * @param extRef
     *            external reference
     * @return
     */
    protected static Class getReferencedClass(IProject project,
            ExternalReference extRef) {
        return (Class) ProcessUIUtil.getReferencedClassifier(extRef, project);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.process.PageflowPropertySection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public Command doGetCommand(Object obj) {

        CompoundCommand cmpCmd = new CompoundCommand();
        CaseService caseService = getCaseService((Process) getInput());

        if (caseService != null) {

            if (obj instanceof Button) {

                Button btn = (Button) obj;
                if (btn == btnAllStates
                        && caseService.getVisibleForCaseStates() != null) {

                    /*
                     * When 'All States' is selected, remove the
                     * visibleForCaseStates element from the case service
                     */
                    cmpCmd.append(SetCommand.create(getEditingDomain(),
                            caseService,
                            XpdExtensionPackage.eINSTANCE
                                    .getCaseService_VisibleForCaseStates(),
                            null));
                    cmpCmd.setLabel(Messages.CaseServicePropertySection_selectAllStatesCmdLabel);

                } else if (btn == btnSpecificStates
                        && caseService.getVisibleForCaseStates() == null) {

                    /*
                     * When 'Specific States' is selected, add the
                     * visibleForCaseStates element to the case service
                     */
                    VisibleForCaseStates visibleForCaseStates =
                            XpdExtensionFactory.eINSTANCE
                                    .createVisibleForCaseStates();
                    cmpCmd.append(SetCommand.create(getEditingDomain(),
                            caseService,
                            XpdExtensionPackage.eINSTANCE
                                    .getCaseService_VisibleForCaseStates(),
                            visibleForCaseStates));
                    cmpCmd.setLabel(Messages.CaseServicePropertySection_selectSpecificStatesCmdLabel);
                }

            }

            if (obj instanceof TableItem) {

                VisibleForCaseStates visibleForCaseStates =
                        caseService.getVisibleForCaseStates();

                TableItem tblItem = (TableItem) obj;
                cmpCmd.setLabel(Messages.CaseServicePropertySection_caseStateSelectionCmdLabel);

                /*
                 * if its a 'No Case State Set' item, then set the boolean flag
                 * accordingly
                 */
                if (NO_CASE_STATE_SET_ITEM.equals(tblItem.getData())) {

                    boolean checked = tblItem.getChecked();
                    cmpCmd.append(SetCommand
                            .create(getEditingDomain(),
                                    visibleForCaseStates,
                                    XpdExtensionPackage.eINSTANCE
                                            .getVisibleForCaseStates_VisibleForUnsetCaseState(),
                                    checked));

                } else {

                    ExternalReference itemExtRef =
                            (ExternalReference) tblItem.getData();

                    /*
                     * if the case state item is checked in the table, add it to
                     * the model
                     */
                    if (tblItem.getChecked()) {

                        cmpCmd.append(AddCommand.create(getEditingDomain(),
                                visibleForCaseStates,
                                XpdExtensionPackage.eINSTANCE
                                        .getVisibleForCaseStates_CaseState(),
                                itemExtRef));
                    } else {

                        /*
                         * if the case state is unchecked, find the actual
                         * external reference element in the model and remove it
                         */

                        ExternalReference extRefToRemove = null;

                        for (ExternalReference extRef : visibleForCaseStates
                                .getCaseState()) {
                            if (EcoreUtil.equals(extRef, itemExtRef)) {
                                extRefToRemove = extRef;
                                break;
                            }
                        }

                        if (extRefToRemove != null) {
                            cmpCmd.append(RemoveCommand
                                    .create(getEditingDomain(), extRefToRemove));
                        }
                    }

                }
            }

            if (cmpCmd.canExecute()) {

                return cmpCmd;
            }
        }
        return super.doGetCommand(obj);
    }

    /**
     * 
     * @return case service set in the given process or null
     */
    public static CaseService getCaseService(Process process) {

        if (process != null) {
            Object caseService =
                    Xpdl2ModelUtil.getOtherElement(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CaseService());
            if (caseService instanceof CaseService) {
                return (CaseService) caseService;
            }
        }
        return null;
    }
}
