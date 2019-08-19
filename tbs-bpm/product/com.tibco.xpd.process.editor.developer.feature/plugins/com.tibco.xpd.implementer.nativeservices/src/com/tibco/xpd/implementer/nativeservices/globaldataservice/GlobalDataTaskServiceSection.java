/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.CaseClassPicker;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.globaldataservice.AbstractOperationPage.CaseRefDataTypeProposalProvider;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.util.CommandContentAssistTextHandler;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Global data service task implementation type section.
 * 
 * @author aallway
 * @since 13 Nov 2013
 */
public class GlobalDataTaskServiceSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution {

    private static final XpdExtensionPackage EXT_PACKAGE =
            XpdExtensionPackage.eINSTANCE;

    private static final XpdExtensionFactory EXT_FACTORY =
            XpdExtensionFactory.eINSTANCE;

    /**
     * Root composite container.
     */
    private Composite root;

    /**
     * Case Access Class Operations operation controls...
     */
    private Button caseAccessOpsButton;

    private Composite caseAccessOpsContainer;

    private ComboViewer caseAccessOperations;

    private CaseClassPicker caseAccessClassPickerCtrl;

    private ScrolledPageBook cacOperationPageBook;

    /**
     * Case Class Reference operation controls
     */
    private Button caseRefOpsButton;

    private Composite caseRefOpsContainer;

    private ContentAssistText caseRefPickerCtrl;

    private ComboViewer caseRefOperations;

    private ScrolledPageBook crefOperationPageBook;

    private final Map<EReference, AbstractOperationPage<CaseReferenceOperationsType>> caseRefOperationPages;

    private final Map<EReference, AbstractOperationPage<CaseAccessOperationsType>> caseAccOperationPages;

    /**
     * Pages that were removed for SCE, but may still be selected in the model after migration.
     */
    private final List<EReference> removedCaseAccOperationPages;

    /**
     * Constructor
     */
    public GlobalDataTaskServiceSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        caseRefOperationPages =
                new LinkedHashMap<EReference, AbstractOperationPage<CaseReferenceOperationsType>>();
        caseAccOperationPages =
                new LinkedHashMap<EReference, AbstractOperationPage<CaseAccessOperationsType>>();
        removedCaseAccOperationPages = new ArrayList<>();
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
        root = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(1, false);
        gl.marginTop += 10;
        root.setLayout(gl);

        // lastSelectCaseAccessClass = null;

        createCaseRefOperationsSection(root, toolkit);

        createCaseAccessOperationsSection(root, toolkit);

        return root;
    }

    /*
     * ==========================================================================
     * 
     * CASE REFERENCE OPERATION CONTROL METHODS.
     * 
     * ==========================================================================
     */

    /**
     * Create the controls for the case reference operations section.
     * 
     * @param parent
     * @param toolkit
     */
    private void createCaseRefOperationsSection(Composite parent,
            XpdFormToolkit toolkit) {
        GridLayout gl;
        GridData gd;
        Label l;

        /**
         * Case reference Operations controls...
         */
        caseRefOpsButton =
                toolkit.createButton(parent,
                        Messages.GlobalDataTaskServiceSection_ChangeOrDelCaseObjUsingCaseId_label,
                        SWT.RADIO);
        caseRefOpsButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(caseRefOpsButton);

        caseRefOpsContainer = toolkit.createComposite(parent);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 1;
        caseRefOpsContainer.setLayoutData(gd);
        caseRefOpsContainer.setVisible(false);

        gl = new GridLayout(1, false);
        gl.marginLeft = 11;
        gl.marginBottom += 10;
        caseRefOpsContainer.setLayout(gl);

        Control descText =
                createWrappedDescriptionText(toolkit,
                        caseRefOpsContainer,
                        Messages.GlobalDataTaskServiceSection_selectCaseRefField_longdesc);
        descText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite innerContainer = toolkit.createComposite(caseRefOpsContainer);
        innerContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        gl = new GridLayout(2, false);
        gl.marginTop = gl.marginHeight;
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        innerContainer.setLayout(gl);

        /*
         * Case reference field picker control.
         */
        l =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_caseRefField_label);
        l.setLayoutData(new GridData());

        caseRefPickerCtrl =
                new ContentAssistText(innerContainer, toolkit,
                        new CaseRefDataTypeProposalProvider(false) {
                            @Override
                            protected Activity getInput() {
                                EObject input =
                                        GlobalDataTaskServiceSection.this
                                                .getInput();
                                return (Activity) (input instanceof Activity ? input
                                        : null);
                            }
                        });

        manageControl(caseRefPickerCtrl);

        /*
         * Operation selection dropdown.
         */
        l =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_operation_label);
        l.setLayoutData(new GridData());

        caseRefOperations =
                new ComboViewer(toolkit.createCCombo(innerContainer,
                        SWT.READ_ONLY));

        caseRefOperations.setContentProvider(new ArrayContentProvider());
        caseRefOperations.setLabelProvider(new OperationsLabelProvider(
                caseRefOperationPages));

        manageControl(caseRefOperations.getCCombo());

        gd = new GridData();
        gd.horizontalIndent = 8;
        caseRefOperations.getCCombo().setLayoutData(gd);

        /*
         * Align pagebook for controls of each operation type under the
         * operations drop down rather than far left - so fill first column with
         * empty comp' Then make pagebook fill remaining 4 columns (below)
         */
        Composite leftOfPagebookFiller =
                toolkit.createComposite(innerContainer);
        gd = new GridData();
        gd.widthHint = 20;
        gd.heightHint = 1;
        leftOfPagebookFiller.setLayoutData(gd);

        /*
         * Pagebook for each operation type's controls
         */
        crefOperationPageBook =
                toolkit.createPageBook(innerContainer, SWT.NONE);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalIndent = 7;
        crefOperationPageBook.setLayoutData(gd);

        /*
         * Create Case Ref Update operation controls
         */
        CaseRefUpdateOperationPage updatePage =
                new CaseRefUpdateOperationPage(this);
        caseRefOperationPages.put(EXT_PACKAGE
                .getCaseReferenceOperationsType_Update(), updatePage);
        crefOperationPageBook.registerPage(updatePage, updatePage
                .createPage(crefOperationPageBook.getContainer(), toolkit));

        /*
         * Create Case Ref Link operation controls
         */
        CaseRefAddLinkOperationPage linkPage =
                new CaseRefAddLinkOperationPage(this);
        caseRefOperationPages
                .put(EXT_PACKAGE
                        .getCaseReferenceOperationsType_AddLinkAssociations(),
                        linkPage);
        crefOperationPageBook.registerPage(linkPage, linkPage
                .createPage(crefOperationPageBook.getContainer(), toolkit));

        /*
         * Create Case Ref UnLink operation controls
         */
        CaseRefRemoveLinkOperationPage unlinkPage =
                new CaseRefRemoveLinkOperationPage(this);
        caseRefOperationPages.put(EXT_PACKAGE
                .getCaseReferenceOperationsType_RemoveLinkAssociations(),
                unlinkPage);
        crefOperationPageBook.registerPage(unlinkPage, unlinkPage
                .createPage(crefOperationPageBook.getContainer(), toolkit));

        /*
         * Create Case Ref UnLink operation controls
         */
        CaseRefDeleteOperationPage delPage =
                new CaseRefDeleteOperationPage(this);
        caseRefOperationPages.put(EXT_PACKAGE
                .getCaseReferenceOperationsType_Delete(), delPage);
        crefOperationPageBook.registerPage(delPage, delPage
                .createPage(crefOperationPageBook.getContainer(), toolkit));

        caseRefOperations.setInput(caseRefOperationPages.keySet());

        /* Size Case picker according to combo box width for nicer sizing. */
        gd = new GridData(GridData.GRAB_HORIZONTAL);
        gd.widthHint =
                caseRefOperations.getCCombo().computeSize(SWT.DEFAULT,
                        SWT.DEFAULT).x - 11;
        caseRefPickerCtrl.setLayoutData(gd);

        // Register all text controls
        for (AbstractOperationPage<CaseReferenceOperationsType> page : caseRefOperationPages
                .values()) {
            for (Text txt : page.getManagedTextControls()) {
                manageControl(txt);
            }
        }

    }

    /**
     * Provides the same functionality for ContentAssistText fields as the
     * manageControl methods in AbstractXpdSection do for SWT Controls.
     * 
     * @param control
     *            The content assist control to manage.
     */
    protected void manageControl(final ContentAssistText control) {
        new CommandContentAssistTextHandler(control, this);
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

    /*
     * ==========================================================================
     * 
     * CASE ACCESS OPERATION CONTROL METHODS.
     * 
     * ==========================================================================
     */

    /**
     * Create the controls for the case access operations section.
     * 
     * @param parent
     * @param toolkit
     */
    private void createCaseAccessOperationsSection(Composite parent,
            XpdFormToolkit toolkit) {
        GridLayout gl;
        GridData gd;
        Label l;

        /**
         * Case Access Class Operations controls...
         */
        caseAccessOpsButton =
                toolkit.createButton(parent,
                        Messages.GlobalDataTaskServiceSection_createOrDeleteCaseObjByCaseId_longdesc,
                        SWT.RADIO);
        caseAccessOpsButton
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(caseAccessOpsButton);

        caseAccessOpsContainer = toolkit.createComposite(parent);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 1;
        caseAccessOpsContainer.setLayoutData(gd);
        caseAccessOpsContainer.setVisible(false);

        gl = new GridLayout(1, false);
        gl.marginLeft = 11;
        caseAccessOpsContainer.setLayout(gl);

        Control descText =
                createWrappedDescriptionText(toolkit,
                        caseAccessOpsContainer,
                        Messages.GlobalDataTaskServiceSection_selectCaseClassTypeToCreateOrDeleteRef_longdesc);
        descText.setLayoutData(gd = new GridData(GridData.FILL_HORIZONTAL));

        Composite innerContainer =
                toolkit.createComposite(caseAccessOpsContainer);
        innerContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        gl = new GridLayout(2, false);
        gl.marginTop = gl.marginHeight;
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        innerContainer.setLayout(gl);

        /*
         * Case class picker control.
         */
        l =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_caseClass_label);
        l.setLayoutData(new GridData());
        l.setToolTipText(""); //$NON-NLS-1$

        caseAccessClassPickerCtrl =
                new CaseClassPicker(innerContainer, SWT.NONE, toolkit,
                        getEditingDomain(), getSectionContainerType()) {

                    /**
                     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#getProject()
                     * 
                     * @return
                     */
                    @Override
                    protected IProject getProject() {
                        return GlobalDataTaskServiceSection.this.getProject();
                    }

                    @Override
                    protected Command setCaseRefTypeCmd(RecordType recordType) {
                        GlobalDataOperation dataOperation =
                                getGlobalDataOperation();

                        if (dataOperation != null
                                && dataOperation.getCaseAccessOperations() != null) {
                            ExternalReference currRef =
                                    dataOperation.getCaseAccessOperations()
                                            .getCaseClassExternalReference();

                            // If the value hasn't changed then do nothing
                            ExternalReference value =
                                    recordType.getMember().get(0)
                                            .getExternalReference();
                            if (currRef != null) {
                                if (value.getLocation()
                                        .equals(currRef.getLocation())
                                        && value.getNamespace()
                                                .equals(currRef.getNamespace())
                                        && value.getXref()
                                                .equals(currRef.getXref())) {
                                    return null;
                                }
                            }

                            ExternalReference ref =
                                    Xpdl2Factory.eINSTANCE
                                            .createExternalReference();
                            ref.setLocation(value.getLocation());
                            ref.setNamespace(value.getNamespace());
                            ref.setXref(value.getXref());

                            Command setCmd =
                                    SetCommand
                                            .create(getEditingDomain(),
                                                    dataOperation
                                                            .getCaseAccessOperations(),
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getCaseAccessOperationsType_CaseClassExternalReference(),
                                                    ref);

                            DeleteByCompositeIdentifiersType deleteByCompositeIdentifiers =
                                    dataOperation.getCaseAccessOperations()
                                            .getDeleteByCompositeIdentifiers();

                            if (deleteByCompositeIdentifiers != null
                                    && !deleteByCompositeIdentifiers
                                            .getCompositeIdentifier().isEmpty()) {
                                /*
                                 * If delete by composite ids is set and case
                                 * identifiers are mapped then clear these
                                 * values
                                 */

                                CompoundCommand ccmd =
                                        new CompoundCommand(
                                                setCmd.getDescription());
                                ccmd.append(SetCommand
                                        .create(getEditingDomain(),
                                                deleteByCompositeIdentifiers,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDeleteByCompositeIdentifiersType_CompositeIdentifier(),
                                                SetCommand.UNSET_VALUE));
                                ccmd.append(setCmd);

                                return ccmd;
                            }

                            return setCmd;
                        }
                        return null;
                    }

                };
        /*
         * Operation selection dropdown.
         */
        l =
                toolkit.createLabel(innerContainer,
                        Messages.GlobalDataTaskServiceSection_operation_label);
        l.setLayoutData(new GridData());

        caseAccessOperations =
                new ComboViewer(toolkit.createCCombo(innerContainer,
                        SWT.READ_ONLY));

        caseAccessOperations.getCCombo().setLayoutData(new GridData());

        caseAccessOperations.setContentProvider(new ArrayContentProvider());
        caseAccessOperations.setLabelProvider(new OperationsLabelProvider(
                caseAccOperationPages));

        manageControl(caseAccessOperations.getCCombo());

        /*
         * Align pagebook for controls of each operation type under the
         * operations drop down rather than far left - so fill first column with
         * empty comp' Then make pagebook fill remaining 4 columns (below)
         */
        Composite leftOfPagebookFiller =
                toolkit.createComposite(innerContainer);
        gd = new GridData();
        gd.widthHint = 20;
        gd.heightHint = 1;
        leftOfPagebookFiller.setLayoutData(gd);

        /*
         * Pagebook for each operation type's controls
         */
        cacOperationPageBook = toolkit.createPageBook(innerContainer, SWT.NONE);
        gd = new GridData(GridData.FILL_BOTH);
        cacOperationPageBook.setLayoutData(gd);

        /*
         * CAC Create case object operation controls.
         */
        CaseAccessCreateOperationPage createPage =
                new CaseAccessCreateOperationPage(this);
        caseAccOperationPages.put(EXT_PACKAGE
                .getCaseAccessOperationsType_Create(), createPage);
        cacOperationPageBook.registerPage(createPage, createPage
                .createPage(cacOperationPageBook.getContainer(), toolkit));

        removedCaseAccOperationPages.add(EXT_PACKAGE.getCaseAccessOperationsType_DeleteByCaseIdentifier());
        removedCaseAccOperationPages.add(EXT_PACKAGE.getCaseAccessOperationsType_DeleteByCompositeIdentifiers());

        caseAccessOperations.setInput(caseAccOperationPages.keySet());

        /* Size Case picker according to combo box width for nicer sizing. */
        gd = new GridData(GridData.GRAB_HORIZONTAL);
        gd.widthHint =
                caseAccessOperations.getCCombo().computeSize(SWT.DEFAULT,
                        SWT.DEFAULT).x + 8;
        caseAccessClassPickerCtrl.setLayoutData(gd);

        // Register all text controls
        for (AbstractOperationPage<CaseAccessOperationsType> page : caseAccOperationPages
                .values()) {
            for (Text txt : page.getManagedTextControls()) {
                manageControl(txt);
            }
        }

    }

    /**
     * Reset any cached values
     * 
     * @param pages
     */
    private void resetPages(Collection<?> pages) {
        for (Object page : pages) {
            if (page instanceof AbstractOperationPage<?>) {
                ((AbstractOperationPage<?>) page).reset();
            }
        }
    }

    /**
     * Get case identifier properties from the given case class.
     * 
     * @param caseClass
     * @return
     */
    protected static List<Property> getCaseIdentifiers(Class caseClass) {
        List<Property> caseIdentifiers = new ArrayList<Property>();

        for (Property property : caseClass.getAllAttributes()) {
            if (BOMGlobalDataUtils.isCID(property)) {
                caseIdentifiers.add(property);
            }
        }

        return caseIdentifiers;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        boolean isCaseAccess = false;
        boolean isCaseRef = false;
        caseAccessOpsButton.setSelection(false);
        caseRefOpsButton.setSelection(false);

        GlobalDataOperation dataOp = getGlobalDataOperation();
        if (dataOp != null) {
            isCaseAccess = dataOp.getCaseAccessOperations() != null;
            if (!isCaseAccess) {
                isCaseRef = dataOp.getCaseReferenceOperations() != null;
            }
        }

        if (isCaseAccess) {
            CaseAccessOperationsType caseAccessOp =
                    dataOp.getCaseAccessOperations();
            boolean doLayout = false;
            caseAccessOpsButton.setSelection(true);
            if (caseAccessOp.getCaseClassExternalReference() != null) {
                caseAccessClassPickerCtrl.setValue(ProcessUIUtil
                        .xpdl2RefToComplexDataTypeRef(caseAccessOp
                                .getCaseClassExternalReference()));
            } else {
                caseAccessClassPickerCtrl.setValue(null);
            }
            updateCaseAccessOperationCombo(caseAccessOperations,
                    caseAccOperationPages,
                    caseAccessOp);

            /*
             * Refresh Create section if set
             */
            for (AbstractOperationPage<CaseAccessOperationsType> page : caseAccOperationPages
                    .values()) {
                if (page != null) {
                    page.update(caseAccessOp);
                }
            }

            if (!caseAccessOpsContainer.getVisible()) {
                /*
                 * Show case access Operations controls if not already visible.
                 */
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                caseAccessOpsContainer.setLayoutData(gd);

                caseAccessOpsContainer.setVisible(true);

                doLayout = true;
            }

            /* Show correct page of controls for current selection in combo. */
            ScrolledPageBook operationPagebook = cacOperationPageBook;
            ComboViewer operationsCombo = caseAccessOperations;

            if (updateOperationsPagebook(operationPagebook,
                    operationsCombo,
                    caseAccOperationPages)) {
                doLayout = true;
            }

            if (doLayout) {
                root.layout();
            }

        } else if (!isCaseAccess && caseAccessOpsContainer.getVisible()) {
            /*
             * Hide case access Operations controls if not already invisible.
             */
            GridData gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = 1;
            caseAccessOpsContainer.setLayoutData(gd);

            caseAccessOpsContainer.setVisible(false);
            root.layout();
        }

        /*
         * Do case ref operation section separately (so that on first entry
         * prior to any selection by user, we collapse both operation sections.,
         */
        if (isCaseRef) {
            resetPages(caseAccOperationPages.values());
            CaseReferenceOperationsType refOperationsType =
                    dataOp.getCaseReferenceOperations();
            boolean doLayout = false;
            caseRefOpsButton.setSelection(true);
            updateCaseReferenceOperationCombo(caseRefOperations,
                    caseRefOperationPages,
                    refOperationsType);

            updateText(caseRefPickerCtrl.getText(),
                    refOperationsType.getCaseRefField());

            for (AbstractOperationPage<CaseReferenceOperationsType> page : caseRefOperationPages
                    .values()) {
                page.update(refOperationsType);
            }

            if (!caseRefOpsContainer.getVisible()) {
                /* Show case ref Operations controls if not already visible. */
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                caseRefOpsContainer.setLayoutData(gd);

                caseRefOpsContainer.setVisible(true);

                doLayout = true;
            }

            /* Show correct page of controls for current selection in combo. */
            ScrolledPageBook operationPagebook = crefOperationPageBook;
            ComboViewer operationsCombo = caseRefOperations;

            if (updateOperationsPagebook(operationPagebook,
                    operationsCombo,
                    caseRefOperationPages)) {
                doLayout = true;
            }

            if (doLayout) {
                root.layout();
            }

        } else if (!isCaseRef && caseRefOpsContainer.getVisible()) {
            /*
             * Hide case ref Operations controls if not already invisible.
             */
            GridData gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = 1;
            caseRefOpsContainer.setLayoutData(gd);

            caseRefOpsContainer.setVisible(false);

            root.layout();
        }
    }

    /**
     * Update the operations combo from the model.
     * 
     * @param viewer
     * @param options
     * @param src
     */
    private void updateCaseAccessOperationCombo(ComboViewer viewer,
            Map<EReference, ?> pages, EObject src) {
        EReference selection = null;

        // Clear out removed operation selections
        for (EReference eRef : removedCaseAccOperationPages) {
            caseAccOperationPages.remove(eRef);
        }

        for (EReference eRef : pages.keySet()) {
            if (src.eGet(eRef) != null) {
                selection = eRef;
                break;
            }
        }
        // Check if one of the removed operations is still selected
        if (selection == null) {
            for (EReference eRef : removedCaseAccOperationPages) {
                if (src.eGet(eRef) != null) {
                    selection = eRef;
                    // Temporarily add it to the dropdown content
                    caseAccOperationPages.put(selection, null);
                    break;
                }
            }
        }

        viewer.refresh();
        // viewer.setInput(caseAccOperationPages);
        viewer.setSelection(selection != null ? new StructuredSelection(
                selection) : StructuredSelection.EMPTY);
    }

    /**
     * Update the operations combo from the model.
     * 
     * @param viewer
     * @param options
     * @param src
     */
    private void updateCaseReferenceOperationCombo(ComboViewer viewer, Map<EReference, ?> pages, EObject src) {
        EReference selection = null;

        for (EReference eRef : pages.keySet()) {
            if (src.eGet(eRef) != null) {
                selection = eRef;
                break;
            }
        }

        viewer.setSelection(selection != null ? new StructuredSelection(selection) : StructuredSelection.EMPTY);
    }

    /**
     * Get the GlobalDataOperation from the given task service.
     * 
     * @return {@link GlobalDataOperation} or <code>null</code> if not set.
     */
    protected GlobalDataOperation getGlobalDataOperation() {
        TaskService taskService = getTaskServiceInput();
        if (taskService != null) {
            return (GlobalDataOperation) TaskServiceExtUtil
                    .getExtendedModel(taskService,
                            EXT_PACKAGE.getDocumentRoot_GlobalDataOperation());
        }
        return null;
    }

    /**
     * Update the given pagebook to the {@link OperationAndLabel} selection in
     * the given combo.
     * 
     * @param operationPagebook
     * @param operationsCombo
     * @return <code>true</code> if pagebook page has changed.
     */
    private boolean updateOperationsPagebook(
            ScrolledPageBook operationPagebook, ComboViewer operationsCombo,
            Map<EReference, ?> pagesMap) {

        EReference selOperation = null;

        ISelection selection = operationsCombo.getSelection();
        if (selection instanceof IStructuredSelection) {
            Object firstElement =
                    ((IStructuredSelection) selection).getFirstElement();

            if (firstElement instanceof EReference) {
                selOperation = ((EReference) firstElement);
            }
        }
        AbstractOperationPage<?> pageToShow = null;
        if (selOperation != null && pagesMap.get(selOperation) != null) {
            pageToShow = (AbstractOperationPage<?>) pagesMap.get(selOperation);
        }

        if (pageToShow != null) {
            operationPagebook.showPage(pagesMap.get(selOperation));
        } else {
            operationPagebook.showEmptyPage();
        }

        Control currentPage = operationPagebook.getCurrentPage();
        if (currentPage != null) {
            Point sz = currentPage.computeSize(SWT.DEFAULT, SWT.DEFAULT);

            GridData oldgd = (GridData) operationPagebook.getLayoutData();

            GridData gd = new GridData(GridData.FILL_BOTH);
            gd.minimumWidth = sz.x;
            gd.minimumHeight = sz.y;
            gd.horizontalIndent = oldgd.horizontalIndent;
            gd.verticalIndent = oldgd.verticalIndent;

            operationPagebook.setLayoutData(gd);

            return true;
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand cmd = new CompoundCommand();
        TaskService taskService = getTaskServiceInput();
        if (taskService != null) {
            GlobalDataOperation dataOp = getGlobalDataOperation();

            /*
             * If GlobalDataOperation not set then set it here.
             */
            if (dataOp == null) {
                dataOp = getSetGlobalDataOperationCommand(taskService, cmd);
            }

            if (dataOp != null) {
                if (obj == caseRefOpsButton
                        && dataOp.getCaseReferenceOperations() == null) {
                    cmd.setLabel(Messages.GlobalDataTaskServiceSection_setCaseRefOperations_label);
                    getSetCreateCaseRefOpCommand(dataOp, cmd);
                } else if (obj == caseAccessOpsButton) {
                    cmd.setLabel(Messages.GlobalDataTaskServiceSection_setCaseAccessOperations_label);
                    addCaseAccessOpToCommand(dataOp, cmd);
                } else if (obj == caseRefOperations.getCCombo()) {
                    cmd.setLabel(Messages.GlobalDataTaskServiceSection_setCaseRefOperation_label);
                    IStructuredSelection selection =
                            (IStructuredSelection) caseRefOperations
                                    .getSelection();

                    if (selection != null && !selection.isEmpty()) {
                        CaseReferenceOperationsType referenceOperations =
                                dataOp.getCaseReferenceOperations();

                        if (referenceOperations != null) {
                            getSetCaseRefOperationCommand(referenceOperations,
                                    (EReference) selection.getFirstElement(),
                                    cmd);
                        }
                    }
                } else if (obj == caseAccessOperations.getCCombo()) {
                    cmd.setLabel(Messages.GlobalDataTaskServiceSection_setCaseAccOperation_label);
                    IStructuredSelection selection =
                            (IStructuredSelection) caseAccessOperations
                                    .getSelection();

                    if (selection != null && !selection.isEmpty()) {
                        CaseAccessOperationsType accessOperations =
                                dataOp.getCaseAccessOperations();

                        if (accessOperations != null) {
                            getSetCaseAccessOperationCommand(accessOperations,
                                    (EReference) selection.getFirstElement(),
                                    cmd);
                        }
                    }
                } else if (obj == caseRefPickerCtrl.getText()) {
                    CaseReferenceOperationsType refOp =
                            dataOp.getCaseReferenceOperations();
                    if (refOp != null) {
                        String text = ((Text) obj).getText().trim();

                        if (!text.equals(refOp.getCaseRefField())) {
                            cmd.setLabel(Messages.GlobalDataTaskServiceSection_setCaseRefId_label);
                            cmd.append(SetCommand
                                    .create(getEditingDomain(),
                                            refOp,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getCaseReferenceOperationsType_CaseRefField(),
                                            text.isEmpty() ? SetCommand.UNSET_VALUE
                                                    : text));
                        }
                    }
                } else {
                    for (AbstractOperationPage<CaseAccessOperationsType> page : caseAccOperationPages
                            .values()) {
                        if (page.containsControl((Control) obj)) {
                            Command c =
                                    page.getCommand(getEditingDomain(),
                                            dataOp.getCaseAccessOperations(),
                                            obj);
                            if (c != null) {
                                cmd.append(c);
                            }
                        }
                    }

                    for (AbstractOperationPage<CaseReferenceOperationsType> page : caseRefOperationPages
                            .values()) {
                        if (page.containsControl((Control) obj)) {
                            Command c =
                                    page.getCommand(getEditingDomain(),
                                            dataOp.getCaseReferenceOperations(),
                                            obj);
                            if (c != null) {
                                cmd.append(c);
                            }
                        }
                    }
                }
            }
        }
        return cmd != null && !cmd.isEmpty() ? cmd : null;
    }

    /**
     * Add the command to set the case access operation to the given compound
     * command.
     * 
     * @param accessOperations
     * 
     * @param cmd
     */
    private void getSetCaseAccessOperationCommand(
            CaseAccessOperationsType accessOperations, EReference selection,
            CompoundCommand cmd) {

        /*
         * Only add command if the operation choice has actually changed
         */
        if (accessOperations.eGet(selection) != null) {
            // Already set
            return;
        }

        EObject eo = null;
        if (EXT_PACKAGE.getCaseAccessOperationsType_Create().equals(selection)) {

            eo = EXT_FACTORY.createCreateCaseOperationType();

        } else if (EXT_PACKAGE
                .getCaseAccessOperationsType_DeleteByCompositeIdentifiers()
                .equals(selection)) {

            eo = EXT_FACTORY.createDeleteByCompositeIdentifiersType();
        } else if (EXT_PACKAGE
                .getCaseAccessOperationsType_DeleteByCaseIdentifier()
                .equals(selection)) {

            eo = EXT_FACTORY.createDeleteByCaseIdentifierType();
        }

        if (eo != null) {

            /*
             * Unset previously set operation (as its a CHOICE).
             */
            for (EReference eRef : caseAccOperationPages.keySet()) {
                if (accessOperations.eGet(eRef) != null) {
                    cmd.append(SetCommand.create(getEditingDomain(),
                            accessOperations,
                            eRef,
                            SetCommand.UNSET_VALUE));
                }
            }

            cmd.append(SetCommand.create(getEditingDomain(),
                    accessOperations,
                    selection,
                    eo));
        }
    }

    /**
     * Add the command to set the case reference operation to the given compound
     * command.
     * 
     * @param refOperations
     * 
     * @param cmd
     */
    private void getSetCaseRefOperationCommand(
            CaseReferenceOperationsType refOperations, EReference selection,
            CompoundCommand cmd) {

        EObject eo = null;

        /*
         * Only add command if the operation choice has actually changed
         */
        if (refOperations.eGet(selection) != null) {
            // Already set
            return;
        }

        if (EXT_PACKAGE.getCaseReferenceOperationsType_Delete()
                .equals(selection)) {

            eo = EXT_FACTORY.createDeleteCaseReferenceOperationType();
        } else if (EXT_PACKAGE
                .getCaseReferenceOperationsType_AddLinkAssociations()
                .equals(selection)) {

            eo = EXT_FACTORY.createAddLinkAssociationsType();
        } else if (EXT_PACKAGE
                .getCaseReferenceOperationsType_RemoveLinkAssociations()
                .equals(selection)) {

            eo = EXT_FACTORY.createRemoveLinkAssociationsType();
        } else if (EXT_PACKAGE.getCaseReferenceOperationsType_Update()
                .equals(selection)) {

            eo = EXT_FACTORY.createUpdateCaseOperationType();
        }

        if (eo != null) {
            /*
             * Unset previously set operation (as its a CHOICE).
             */
            for (EReference eRef : caseRefOperationPages.keySet()) {
                if (refOperations.eGet(eRef) != null) {
                    cmd.append(SetCommand.create(getEditingDomain(),
                            refOperations,
                            eRef,
                            SetCommand.UNSET_VALUE));
                }
            }

            cmd.append(SetCommand.create(getEditingDomain(),
                    refOperations,
                    selection,
                    eo));
        }
    }

    /**
     * Add the command to set the global data operation in the compound command
     * given.
     * 
     * @param taskService
     * @param cmd
     * @return the new GlobalDataOperation being created.
     */
    private GlobalDataOperation getSetGlobalDataOperationCommand(
            TaskService taskService, CompoundCommand cmd) {
        GlobalDataOperation dataOp = EXT_FACTORY.createGlobalDataOperation();
        CompoundCommand addCmd =
                TaskServiceExtUtil.addExtendedModel(getEditingDomain(),
                        taskService,
                        EXT_PACKAGE.getDocumentRoot_GlobalDataOperation(),
                        dataOp);
        if (addCmd != null) {
            cmd.append(addCmd);
        }

        return dataOp;
    }

    /**
     * Add the command to set the Case Ref Operation to the given compound
     * command (will also unset the case access op if set on the global data
     * operation).
     * 
     * @param dataOp
     * @param cmd
     * @return newly created CaseReferenceOperationsType
     */
    private CaseReferenceOperationsType getSetCreateCaseRefOpCommand(
            GlobalDataOperation dataOp, CompoundCommand cmd) {
        CaseReferenceOperationsType refOp =
                EXT_FACTORY.createCaseReferenceOperationsType();

        if (dataOp.getCaseAccessOperations() != null) {
            cmd.append(SetCommand.create(getEditingDomain(),
                    dataOp,
                    EXT_PACKAGE.getGlobalDataOperation_CaseAccessOperations(),
                    SetCommand.UNSET_VALUE));
        }

        cmd.append(SetCommand.create(getEditingDomain(),
                dataOp,
                EXT_PACKAGE.getGlobalDataOperation_CaseReferenceOperations(),
                refOp));

        return refOp;
    }

    /**
     * Add the command to set the {@link CaseAccessOperationsType} to the given
     * compound command if the Case Access Operation is not already created.
     * (will also unset the case ref op if set on the global data operation).
     * 
     * @param dataOp
     * @param cmd
     *            Compound Command to which the Set Command is to be added
     * 
     */
    private void addCaseAccessOpToCommand(GlobalDataOperation dataOp,
            CompoundCommand cmd) {
        CaseAccessOperationsType accessOp =
                EXT_FACTORY.createCaseAccessOperationsType();

        if (dataOp.getCaseReferenceOperations() != null) {
            cmd.append(SetCommand.create(getEditingDomain(),
                    dataOp,
                    EXT_PACKAGE
                            .getGlobalDataOperation_CaseReferenceOperations(),
                    SetCommand.UNSET_VALUE));
        }
        /*
         * XPD-6046: Saket: We should create a new Case Access Operation only
         * when it is NOT already created, else we would land up creating a new
         * Case Access Operation each time we re-click the Case Access Button.
         */
        if (dataOp.getCaseAccessOperations() == null) {
            cmd.append(SetCommand.create(getEditingDomain(),
                    dataOp,
                    EXT_PACKAGE.getGlobalDataOperation_CaseAccessOperations(),
                    accessOp));
        }
    }

    /**
     * Get the TaskService input of this section.
     * 
     * @return
     */
    protected TaskService getTaskServiceInput() {
        EObject input = getInput();
        if (input instanceof Activity) {
            Activity act = (Activity) input;
            Implementation impl = act.getImplementation();
            if (impl instanceof Task) {
                return ((Task) impl).getTaskService();
            }
        }
        return null;
    }

    /**
     * @param toolkit
     * @param parent
     * @return control with some description text in that is wrapped with
     *         nominally enough space for a couple of lines
     */
    private Control createWrappedDescriptionText(XpdFormToolkit toolkit,
            Composite parent, String text) {
        Composite c = toolkit.createComposite(parent);

        final Label description = toolkit.createLabel(c, text, SWT.WRAP);

        /*
         * Simple fill layout that once we have a definitive width to work on we
         * use the wrapped description label to calculate our required size
         * (because we're in a scrolled container any wrapped control needs to
         * have it's width constricted some how)
         */
        c.setLayout(new Layout() {
            Point cacheSize = new Point(1, 1);

            @Override
            protected void layout(Composite composite, boolean flushCache) {
                Point size = composite.getSize();

                description.setLocation(0, 0);
                description.setSize(size);
            }

            @Override
            protected Point computeSize(Composite composite, int wHint,
                    int hHint, boolean flushCache) {
                if (wHint > 0) {
                    cacheSize = description.computeSize(wHint, SWT.DEFAULT);
                }

                return cacheSize;
            }
        });
        return c;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    @Override
    public String getLocalId() {
        return "GlobalData"; //$NON-NLS-1$
    }

    /**
     * Contributing plug-in identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     */
    @Override
    public String getPluginId() {
        return NativeServicesActivator.PLUGIN_ID;
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
     * @author aallway
     * @since 2 Dec 2013
     */
    private static class OperationsLabelProvider extends LabelProvider {

        private final Map<EReference, ?> map;

        public OperationsLabelProvider(Map<EReference, ?> map) {
            this.map = map;

        }

        @Override
        public String getText(Object element) {
            if (element instanceof EReference) {
                Object page = map.get(element);
                if (page instanceof AbstractOperationPage<?>) {
                    return ((AbstractOperationPage<?>) page).getLabel();
                } else if (page == null) {
                    // Handle references to removed operations
                    if ("deleteByCompositeIdentifiers".equals(((EReference) element).getName())) { //$NON-NLS-1$
                        return Messages.GlobalDataTaskServiceSection_deleteByCompositeIdentifier;
                    } else {
                        return Messages.GlobalDataTaskServiceSection_deleteByCaseIdentifier;
                    }

                }
            }
            return super.getText(element);
        }
    }

}
