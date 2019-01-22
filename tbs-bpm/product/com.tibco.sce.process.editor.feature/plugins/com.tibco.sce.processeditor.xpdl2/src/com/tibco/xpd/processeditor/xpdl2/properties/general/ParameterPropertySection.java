/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Parameter property section
 * 
 * @author kamleshu
 * 
 */
public class ParameterPropertySection extends BaseFieldOrParamPropertySection {

    private Button inBtn;

    private Button outBtn;

    private Button inOutBtn;

    private FormalParameter formalParameter;

    private static final int TABLE_WITH_BUTTONS_HEIGHT = 120;

    private ScrolledPageBook basicTypeExtrasPageBook = null;

    private static final String BASIC_TYPE_EXTRAS_INITIAL_VALUES_PAGE =
            "initialValuesPage"; //$NON-NLS-1$

    private static final String BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUES_PAGE =
            "initialBooleanValuesPage"; //$NON-NLS-1$

    private static final String BASIC_TYPE_DATETIME_INIT_VALUES_PAGE =
            "dateTimeArrayInitValuesPage"; //$NON-NLS-1$

    private Button btnRequired;

    private Button btnIsReadOnly;

    private DateTimeInitArrayTable dateTimeArrayTable;

    private BooleanInitArrayTable booleanInitArrayTable;

    private InitValueArrayTable initValueArrayTable;

    /**
     * Data Field property section
     */
    public ParameterPropertySection() {
        super(Xpdl2Package.eINSTANCE.getFormalParameter());
        setShowArrayField(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {
        CompoundCommand cmd = new CompoundCommand();

        // If name has changed then return command to update it
        // else call the super function
        if (obj == chkArrays[BASICTYPE]) {
            cmd = setBasicArrayCmd(chkArrays[BASICTYPE].getSelection());
        } else if (obj == inBtn) {
            cmd.append(SetCommand.create(getEditingDomain(),
                    getFormalParameter(),
                    Xpdl2Package.eINSTANCE.getFormalParameter_Mode(),
                    ModeType.IN_LITERAL));

        } else if (obj == outBtn) {
            cmd.append(SetCommand.create(getEditingDomain(),
                    getFormalParameter(),
                    Xpdl2Package.eINSTANCE.getFormalParameter_Mode(),
                    ModeType.OUT_LITERAL));

            if (hasInitialValues()) {
                cmd.append(getRemoveAllInitialValuesCommand());
            }

        } else if (obj == inOutBtn) {
            cmd.append(SetCommand.create(getEditingDomain(),
                    getFormalParameter(),
                    Xpdl2Package.eINSTANCE.getFormalParameter_Mode(),
                    ModeType.INOUT_LITERAL));

        } else if (obj == btnRequired) {
            cmd.append(SetCommand.create(getEditingDomain(),
                    formalParameter,
                    Xpdl2Package.eINSTANCE.getFormalParameter_Required(),
                    btnRequired.getSelection()));
        } else if (obj == btnIsReadOnly) {
            cmd.append(SetCommand.create(getEditingDomain(),
                    formalParameter,
                    Xpdl2Package.eINSTANCE.getProcessRelevantData_ReadOnly(),
                    btnIsReadOnly.getSelection()));
        } else {
            return super.doGetCommand(obj);
        }

        cmd.setLabel(Messages.ParameterPropertySection_SetParamMode_menu);
        return cmd;
    }

    /**
     * Create command to set the array indicator.
     * 
     * @param basicArray
     * @return
     */
    private CompoundCommand setBasicArrayCmd(boolean basicArray) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.DataFieldPropertySection_SetArrayType_menu);

        cmd.append(SetCommand.create(getEditingDomain(),
                getInput(),
                Xpdl2Package.eINSTANCE.getProcessRelevantData_IsArray(),
                Boolean.valueOf(basicArray)));
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#createDetailsControl
     * (org.eclipse.swt.widgets.Composite,
     * com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected void doCreatePreTypeControls(Composite parent,
            XpdFormToolkit toolkit) {

        Label label =
                toolkit.createLabel(parent,
                        Messages.ParameterPropertySection_mode_label);
        label.setLayoutData(new GridData());

        Composite btnCont = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(3, true);
        gl.marginWidth = 0;
        gl.marginHeight = 0;

        btnCont.setLayout(gl);
        GridData btnCompGD = new GridData(GridData.FILL_HORIZONTAL);

        btnCont.setLayoutData(btnCompGD);

        inBtn =
                toolkit.createButton(btnCont,
                        Messages.ParameterPropertySection_in_label,
                        SWT.RADIO);
        inBtn.setLayoutData(new GridData());
        manageControl(inBtn);

        outBtn =
                toolkit.createButton(btnCont,
                        Messages.ParameterPropertySection_out_label,
                        SWT.RADIO);
        outBtn.setLayoutData(new GridData());
        manageControl(outBtn);

        inOutBtn =
                toolkit.createButton(btnCont,
                        Messages.ParameterPropertySection_inout_label,
                        SWT.RADIO);
        inOutBtn.setLayoutData(new GridData());
        manageControl(inOutBtn);

        label =
                toolkit.createLabel(parent,
                        Messages.ParameterPropertySection_Mandatory_label);
        label.setLayoutData(new GridData());
        btnRequired = toolkit.createButton(parent, "", SWT.CHECK); //$NON-NLS-1$
        btnRequired.setLayoutData(new GridData());

        manageControl(btnRequired);

        label =
                toolkit.createLabel(parent,
                        Messages.ParameterPropertySection_ReadOnly_label);
        label.setLayoutData(new GridData());
        btnIsReadOnly = toolkit.createButton(parent, "", SWT.CHECK); //$NON-NLS-1$
        btnIsReadOnly.setLayoutData(new GridData());

        manageControl(btnIsReadOnly);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#doRefreshDetails
     * ()
     */
    @Override
    protected void doRefreshExtraDetails() {
        // super.doRefreshExtraDetails(); - moved this line to the bottom of the
        // method to retain refresh done in super
        switch (getFormalParameter().getMode().getValue()) {
        case ModeType.IN:
            outBtn.setSelection(false);
            inOutBtn.setSelection(false);
            inBtn.setSelection(true);
            break;

        case ModeType.OUT:
            inOutBtn.setSelection(false);
            inBtn.setSelection(false);
            outBtn.setSelection(true);
            break;

        default:
            inBtn.setSelection(false);
            outBtn.setSelection(false);
            inOutBtn.setSelection(true);
            break;

        }
        if (formalParameter != getFormalParameter()) {
            if (propertyTableViewer != null) {
                propertyTableViewer.setInput(getFormalParameter());
            }
            if (propertyBooleanTableViewer != null) {
                propertyBooleanTableViewer.setInput(getFormalParameter());
            }
        }

        formalParameter = getFormalParameter();
        if (dateTimeArrayTable != null
                && dateTimeArrayTable.getViewer() != null) {
            dateTimeArrayTable.getViewer().cancelEditing();
            dateTimeArrayTable.getViewer().setInput(formalParameter);
        }
        if (booleanInitArrayTable != null
                && booleanInitArrayTable.getViewer() != null) {
            booleanInitArrayTable.getViewer().cancelEditing();
            booleanInitArrayTable.getViewer().setInput(formalParameter);
        }
        if (initValueArrayTable != null
                && initValueArrayTable.getViewer() != null) {
            initValueArrayTable.getViewer().cancelEditing();
            initValueArrayTable.getViewer().setInput(formalParameter);
        }

        boolean requiredAttribute = formalParameter.isRequired();

        if (requiredAttribute) {
            btnRequired.setSelection(true);
        } else {
            btnRequired.setSelection(false);
        }

        boolean isReadOnlyAttribute = formalParameter.isReadOnly();

        if (isReadOnlyAttribute) {
            btnIsReadOnly.setSelection(true);
        } else {
            btnIsReadOnly.setSelection(false);
        }

        disableAll(getRoot(), !isImplementedType());

        if (!isImplementedType()) {
            if (getFormalParameter().getMode().getValue() == ModeType.OUT) {
                disableInitialValuesSection(false);
            } else {
                disableInitialValuesSection(true);
            }
        }

        GridData gridData = null;
        EObject inputType = getInputType();
        if (inputType != null) {

            switch (inputType.eClass().getClassifierID()) {
            // Basic Type
            case Xpdl2Package.BASIC_TYPE:
                gridData = new GridData(GridData.FILL_BOTH);
                gridData.heightHint = TABLE_WITH_BUTTONS_HEIGHT;
                gridData.widthHint = SWT.DEFAULT;
                gridData.horizontalSpan = 3;
                initialValueComposite.setLayoutData(gridData);

                initialValueComposite.getParent().layout(true);
                showBasicExtrasPage(getModelBasicType());
                break;

            // Declared type
            case Xpdl2Package.DECLARED_TYPE:
                gridData = new GridData();
                gridData.heightHint = 1;
                gridData.widthHint = 1;
                gridData.horizontalSpan = 3;
                initialValueComposite.setLayoutData(gridData);

                initialValueComposite.getParent().layout(true);
                basicTypeExtrasPageBook.showEmptyPage();
                break;

            // External reference
            case Xpdl2Package.EXTERNAL_REFERENCE:
                gridData = new GridData();
                gridData.heightHint = 1;
                gridData.widthHint = 1;
                gridData.horizontalSpan = 3;
                initialValueComposite.setLayoutData(gridData);

                initialValueComposite.getParent().layout(true);
                basicTypeExtrasPageBook.showEmptyPage();
                break;
            }
        }
    }

    /**
     * @param basicArray
     * @param basicTypeType
     */
    private void showBasicExtrasPage(BasicType basicType) {
        if (basicTypeExtrasPageBook != null) {
            BasicTypeType basicTypeType = null;
            boolean isBooleanType = false;
            boolean isDateTimeType = false;
            boolean isPerformerType = false;
            if (basicType != null) {
                basicTypeType = basicType.getType();
                if (basicTypeType != null
                        && basicType.getType()
                                .equals(BasicTypeType.BOOLEAN_LITERAL)) {
                    isBooleanType = true;
                } else if (basicTypeType != null
                        && basicType.getType()
                                .equals(BasicTypeType.DATE_LITERAL)
                        || basicType.getType()
                                .equals(BasicTypeType.DATETIME_LITERAL)
                        || basicType.getType()
                                .equals(BasicTypeType.TIME_LITERAL)) {
                    isDateTimeType = true;
                } else if (basicTypeType != null
                        && basicType.getType()
                                .equals(BasicTypeType.PERFORMER_LITERAL)) {
                    isPerformerType = true;
                }
            }
            if (isBooleanType) {
                basicTypeExtrasPageBook
                        .showPage(BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUES_PAGE);
            } else if (isDateTimeType) {
                basicTypeExtrasPageBook
                        .showPage(BASIC_TYPE_DATETIME_INIT_VALUES_PAGE);
            } else if (isPerformerType) {
                enableInitValuesSection();
            } else {
                basicTypeExtrasPageBook
                        .showPage(BASIC_TYPE_EXTRAS_INITIAL_VALUES_PAGE);
            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#setBasicTypeCmd
     * (com.tibco.xpd.xpdlr2.BasicType)
     */
    @Override
    protected Command setBasicTypeCmd(BasicType basicType) {
        CompoundCommand compoundCmd = new CompoundCommand();
        compoundCmd.append(super.setBasicTypeCmd(basicType));

        // MR 38533 - begin
        Command removeCmd = getRemoveConditionalExpressionCommand();
        if (removeCmd != null) {
            compoundCmd.append(removeCmd);
        }
        // MR 38533 - end

        return compoundCmd;
    }

    // MR 38533 - begin
    private Command getRemoveConditionalExpressionCommand() {
        Command removeCommand = null;
        FormalParameter myFP = getFormalParameter();
        EReference feature =
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ConditionalParticipant();
        Object otherElement = Xpdl2ModelUtil.getOtherElement(myFP, feature);
        if (otherElement != null) {
            removeCommand =
                    Xpdl2ModelUtil
                            .getSetOtherElementCommand(getEditingDomain(),
                                    myFP,
                                    feature,
                                    null);
        }
        return removeCommand;
    }

    // MR 38533 - end

    private void disableInitialValuesSection(boolean disabled) {
        // Disable the table with buttons
        if (initialValueComposite != null) {
            disableAll(initialValueComposite, disabled);
        }
    }

    private void disableAll(Control control, boolean disabled) {
        if (control instanceof Composite) {
            if (((Composite) control).getChildren().length > 0) {
                for (Control childControl : ((Composite) control).getChildren()) {
                    disableAll(childControl, disabled);
                }
            }
        }
        if (!(control instanceof Label)) {
            control.setEnabled(disabled);
        }
    }

    private void enableInitValuesSection() {
        ExpandableSectionStacker expandableHeaderController =
                getExpandableHeaderController();
        if (expandableHeaderController != null) {
            Section section =
                    expandableHeaderController
                            .getExpandableSection(INIT_VALUES_HEADER_ID);
            if (section != null) {
                BasicType basicType = getModelBasicType();
                if (basicType != null
                        && basicType.getType()
                                .equals(BasicTypeType.PERFORMER_LITERAL)) {
                    section.setExpanded(false);
                    section.setEnabled(false);
                } else {
                    section.setEnabled(true);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#getInputType()
     */
    @Override
    protected DataType getInputType() {
        FormalParameter input2 = getFormalParameter();
        if (input2 != null) {
            return input2.getDataType();
        } else {
            return null;
        }
    }

    /**
     * Get the DataField input
     * 
     * @return
     */
    private FormalParameter getFormalParameter() {
        if (getInput() instanceof FormalParameter) {
            return (FormalParameter) getInput();
        } else {
            return null;
        }

    }

    private TableViewer propertyTableViewer;

    private Composite initialValueComposite;

    private TableViewer propertyBooleanTableViewer;

    @Override
    protected String getInitialValuesTitle() {
        return Messages.BaseFieldOrParamPropertySection_AllowedValues_label;
    }

    /**
     * Create the basic type options page
     * 
     * @param parent
     * @param toolkit
     */
    @Override
    protected Control doCreateInitialValuesControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite comp = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        comp.setLayout(layout);

        basicTypeExtrasPageBook =
                toolkit.createPageBook(comp, SWT.FLAT | SWT.TOP);
        basicTypeExtrasPageBook.setLayoutData(new GridData(GridData.FILL_BOTH));

        // setup initial value page with Text box
        initialValueComposite =
                basicTypeExtrasPageBook
                        .createPage(BASIC_TYPE_EXTRAS_INITIAL_VALUES_PAGE);
        initialValueComposite.setLayout(layout);
        initialValueComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        initValueArrayTable =
                new InitValueArrayTable(initialValueComposite, toolkit,
                        getEditingDomain(),
                        Messages.ParameterPropertySection_InitialValues_label);
        initValueArrayTable.setBackground(ColorConstants.white);
        initValueArrayTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        // setup initial Boolean values page with List box
        initialValueComposite =
                basicTypeExtrasPageBook
                        .createPage(BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUES_PAGE);
        initialValueComposite.setLayout(layout);
        initialValueComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        booleanInitArrayTable =
                new BooleanInitArrayTable(initialValueComposite, toolkit,
                        getEditingDomain(),
                        Messages.ParameterPropertySection_InitialValues_label);
        booleanInitArrayTable.setBackground(ColorConstants.white);
        booleanInitArrayTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        //
        // setup initial array Date/Time/DateTime values page with List box
        //
        Composite dateTimeArrayInitValuesComposite =
                basicTypeExtrasPageBook
                        .createPage(BASIC_TYPE_DATETIME_INIT_VALUES_PAGE);
        dateTimeArrayInitValuesComposite.setLayout(layout);
        dateTimeArrayInitValuesComposite.setLayoutData(new GridData(
                GridData.FILL_BOTH));

        dateTimeArrayTable =
                new DateTimeInitArrayTable(dateTimeArrayInitValuesComposite,
                        toolkit, getEditingDomain(),
                        Messages.ParameterPropertySection_InitialValues_label);
        dateTimeArrayTable.setBackground(ColorConstants.white);
        dateTimeArrayTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));

        return comp;
    }

    @Override
    protected int getNumberOfColumns() {
        return 1;
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        setImplementedType(false);
        if (part instanceof CommonNavigator) {
            TreeItem[] selectionList =
                    ((CommonNavigator) part).getCommonViewer().getTree()
                            .getSelection();
            for (TreeItem item : selectionList)
                if (item.getParentItem().getParentItem().getData() instanceof com.tibco.xpd.xpdl2.Process
                        && ((EObject) item.getData()).eContainer() instanceof ProcessInterface)
                    setImplementedType(true);
        }
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        return super.doCreateControls(parent, toolkit);
    }

    /**
     * Returns help context for sections to show individual help details
     * 
     * @return
     */
    public String getHelpContextID() {
        return "com.tibco.xpd.process.analyst.doc.Parameter2"; //$NON-NLS-1$
    }

    @Override
    protected String getSectionPreferencesId() {
        return this.getClass().getName();
    }

    // MR 38533 - begin
    @Override
    protected void contributeToExpandables(
            ExpandableSectionStacker expandableHeaderController2) {
        expandableHeaderController2.addSection(INIT_VALUES_HEADER_ID,
                getInitialValuesTitle(),
                100,
                false,
                true);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.BaseFieldOrParamPropertySection#createContributedExpandable(java.lang.String,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param sectionId
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    protected Control createContributedExpandable(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        if (INIT_VALUES_HEADER_ID.equals(sectionId)) {
            return doCreateInitialValuesControls(container, toolkit);
        }
        return null;
    }
    // MR 38533 - end

}
