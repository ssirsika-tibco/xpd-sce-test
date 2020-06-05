/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.TextValidator;
import com.tibco.xpd.xpdl2.edit.util.ControlUtils;
import com.tibco.xpd.xpdl2.impl.DataFieldImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Data Field property section
 * 
 * @author njpatel
 * 
 */
public class DataFieldPropertySection extends BaseFieldOrParamPropertySection {

    private TextValidator initialValueValidator;

    private boolean implementedParameter = false;

    private DataField dataField;

    private ScrolledPageBook initialValuesPageBook = null;

    private CLabel singleInitialValueLabel = null;

    private Text singleInitialValueText = null;

    private DateTimeInitControl dateTimeControl;

    private CCombo initialBooleanValueCombo = null;

    private TableViewer propertyTableViewer;

    private TableViewer propertyBooleanTableViewer;

    private DateTimeInitArrayTable dateTimeArrayTable;

    private InitValueArrayTable initValueArrayTable;

    private BooleanInitArrayTable booleanInitArrayTable;

    private CLabel singleBooleanInitialValueLabel;

    private static final String BASIC_TYPE_EXTRAS_INITIAL_VALUE_PAGE =
            "initialValuePage"; //$NON-NLS-1$

    private static final String BASIC_TYPE_EXTRAS_INITIAL_VALUES_PAGE =
            "initialValuesPage"; //$NON-NLS-1$

    private static final String BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUE_PAGE =
            "initialBooleanValuePage"; //$NON-NLS-1$

    private static final String BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUES_PAGE =
            "initialBooleanValuesPage"; //$NON-NLS-1$

    private static final String BASIC_TYPE_EXTRAS_INITIAL_PERFORMER_VALUE_PAGE =
            "initialPerformerValuePage"; //$NON-NLS-1$

    private static final String BASIC_TYPE_DATETIME_INIT_VALUE_PAGE =
            "dateTimeInitValuePage"; //$NON-NLS-1$

    private static final String BASIC_TYPE_DATETIME_INIT_VALUES_PAGE =
            "dateTimeArrayInitValuesPage"; //$NON-NLS-1$

    private boolean initialValueBad = false;

    /**
     * Keeps track of, if initial Values section is created
     */
    private boolean createInitialValuesSection = true;

    private Button btnIsReadOnly;

    private OrgModelQueryScriptSection orgModelQueryScriptSection;

    /**
     * Data Field property section
     */
    public DataFieldPropertySection() {
        super(Xpdl2Package.eINSTANCE.getDataField());
        setShowArrayField(true);

    }

    /**
     * Data Field property section
     */
    public DataFieldPropertySection(EClass dataFieldEClass) {
        super(dataFieldEClass);
        setShowArrayField(true);

    }

    /**
     * Data Field property section constructor to specify whether we need to
     * show initial values and/or references section or not.
     * 
     * @param eclass
     * @param wantInitialValuesSection
     * @param wantReferencesSection
     */
    public DataFieldPropertySection(EClass eclass,
            boolean wantInitialValuesSection, boolean wantReferencesSection) {

        super(eclass, wantInitialValuesSection, wantReferencesSection);
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
        Command cmd = null;

        cmd = super.doGetCommand(obj);
        if (obj instanceof Button) {
            Button btn = (Button) obj;
            if (btn == chkArrays[BASICTYPE]) {
                cmd = setBasicArrayCmd(chkArrays[BASICTYPE].getSelection());
            } else if (btn == btnIsReadOnly) {
                cmd =
                        SetCommand.create(getEditingDomain(),
                                getDataField(),
                                Xpdl2Package.eINSTANCE
                                        .getProcessRelevantData_ReadOnly(),
                                btnIsReadOnly.getSelection());
            }
        } else if (obj instanceof Text && obj.equals(singleInitialValueText)) {
            cmd = setBasicInitValue(singleInitialValueText.getText());

        } else if (obj instanceof Text && dateTimeControl != null
                && obj.equals(dateTimeControl.getTextControl())) {

            /*
             * XPD-7612: Saket: We might not need 'Initial values' section for a
             * few DataField derivatives (like Global Signal's Payload Data) and
             * hence we won't create dateTimeControl for them. Therefore it can
             * be null at this point when the DataFieldPropertySection is used
             * for those DataField derivatives. So simply check for null before
             * we process further.
             */

            cmd = setBasicInitValue(dateTimeControl.getText());

        } else if (obj instanceof CCombo
                && obj.equals(initialBooleanValueCombo)) {
            String text = initialBooleanValueCombo.getText();
            cmd = setBasicInitValue(text);
        }

        return cmd;
    }

    /**
     * Create command to set the array indicator.
     * 
     * @param basicArray
     * @return
     */
    private Command setBasicArrayCmd(boolean basicArray) {
        ProcessRelevantData data = getDataField();

        if (basicArray != data.isIsArray()) {

            CompoundCommand cmd = new LateExecuteCompoundCommand();

            cmd.setLabel(Messages.DataFieldPropertySection_SetArrayType_menu);

            cmd.append(SetCommand.create(getEditingDomain(),
                    data,
                    Xpdl2Package.eINSTANCE.getProcessRelevantData_IsArray(),
                    Boolean.valueOf(basicArray)));

            InitialValues initialValues =
                    (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());

            if (!basicArray) {
                if (initialValues != null
                        && initialValues.getValue().size() > 1) {
                    /*
                     * When change from array to non-array remove all but first
                     * initial value.
                     */
                    String firstVal = initialValues.getValue().get(0);

                    Command initValCmd = setBasicInitValue(firstVal);
                    if (initValCmd != null) {
                        cmd.append(initValCmd);
                    }
                }
            }

            showBasicExtrasPage(basicArray, getModelBasicType());

            return cmd;
        }

        return null;
    }

    /**
     * @param basicArray
     * @param basicTypeType
     */
    private void showBasicExtrasPage(boolean basicArray, BasicType basicType) {
        if (initialValuesPageBook != null) {
            BasicTypeType basicTypeType = null;
            boolean isBooleanType = false;
            boolean isPerformerType = false;
            boolean isDateTimeType = false;

            if (basicType != null) {
                basicTypeType = basicType.getType();
                if (basicTypeType != null
                        && basicType.getType()
                                .equals(BasicTypeType.BOOLEAN_LITERAL)) {
                    isBooleanType = true;
                } else if (basicTypeType != null
                        && basicType.getType()
                                .equals(BasicTypeType.PERFORMER_LITERAL)) {
                    isPerformerType = true;
                } else if (basicTypeType != null
                        && basicType.getType()
                                .equals(BasicTypeType.DATE_LITERAL)
                        || basicType.getType()
                                .equals(BasicTypeType.DATETIME_LITERAL)
                        || basicType.getType()
                                .equals(BasicTypeType.TIME_LITERAL)) {
                    isDateTimeType = true;
                }
            }
            if (basicArray) {
                if (isBooleanType) {
                    initialValuesPageBook
                            .showPage(BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUES_PAGE);
                } else if (isPerformerType) {
                    initialValuesPageBook
                            .showPage(BASIC_TYPE_EXTRAS_INITIAL_PERFORMER_VALUE_PAGE);
                } else if (isDateTimeType) {
                    initialValuesPageBook
                            .showPage(BASIC_TYPE_DATETIME_INIT_VALUES_PAGE);
                } else {
                    initialValuesPageBook
                            .showPage(BASIC_TYPE_EXTRAS_INITIAL_VALUES_PAGE);
                }
            } else {
                if (isBooleanType) {
                    initialValuesPageBook
                            .showPage(BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUE_PAGE);
                } else if (isPerformerType) {
                    initialValuesPageBook
                            .showPage(BASIC_TYPE_EXTRAS_INITIAL_PERFORMER_VALUE_PAGE);
                } else if (isDateTimeType) {
                    initialValuesPageBook
                            .showPage(BASIC_TYPE_DATETIME_INIT_VALUE_PAGE);
                } else {
                    initialValuesPageBook
                            .showPage(BASIC_TYPE_EXTRAS_INITIAL_VALUE_PAGE);
                }
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

        // MR 38533 - end
        Command removeCmd = getRemoveConditionalExpressionCommand();
        if (removeCmd != null) {
            compoundCmd.append(removeCmd);
        }
        // MR 38533 - end

        return compoundCmd;
    }

    @Override
    public void dispose() {
        if (initialValueValidator != null) {
            initialValueValidator.dispose();
        }
        super.dispose();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.general.BaseFieldOrParamPropertySection#doRefreshExtraDetails()
     * 
     */
    @Override
    protected void doRefreshExtraDetails() {
        if (initialValuesPageBook == null) {
            return;
        }
        enableInitValuesSection();
        if (dataField != getDataField()) {
            if (propertyTableViewer != null) {
                propertyTableViewer.setInput(getDataField());
            }
            if (propertyBooleanTableViewer != null) {
                propertyBooleanTableViewer.setInput(getDataField());
            }
        } else {
            initValueArrayTable.getViewer().refresh();
            dateTimeArrayTable.getViewer().refresh();
            booleanInitArrayTable.getViewer().refresh();
        }

        dataField = getDataField();
        if (dateTimeArrayTable != null
                && dateTimeArrayTable.getViewer() != null) {
            dateTimeArrayTable.getViewer().cancelEditing();
            dateTimeArrayTable.getViewer().setInput(dataField);
        }
        if (initValueArrayTable != null
                && initValueArrayTable.getViewer() != null) {
            initValueArrayTable.getViewer().cancelEditing();
            initValueArrayTable.getViewer().setInput(dataField);
        }
        if (booleanInitArrayTable != null
                && booleanInitArrayTable.getViewer() != null) {
            booleanInitArrayTable.getViewer().cancelEditing();
            booleanInitArrayTable.getViewer().setInput(dataField);
        }

        boolean isReadOnlyAttribute = dataField.isReadOnly();

        if (isReadOnlyAttribute) {
            btnIsReadOnly.setSelection(true);
        } else {
            btnIsReadOnly.setSelection(false);
        }

        BasicType basicType = getModelBasicType();

        if (!dataField.isIsArray() && hasInitialValues()) {
            List<String> initialValues =
                    ProcessDataUtil.getDataInitialValues(dataField, true);

            if (initialValues.size() > 0) {
                if (basicType != null
                        && basicType.getType() != null
                        && basicType.getType()
                                .equals(BasicTypeType.BOOLEAN_LITERAL)) {
                    String tmpInitialValue = initialValues.get(0);
                    Object modelFalse =
                            initialBooleanValueCombo
                                    .getData(ProcessDataUtil.UI_BOOLEAN_FALSE);
                    Object modelTrue =
                            initialBooleanValueCombo
                                    .getData(ProcessDataUtil.UI_BOOLEAN_TRUE);
                    if (tmpInitialValue.equals(modelFalse)) {
                        initialBooleanValueCombo
                                .setText(ProcessDataUtil.UI_BOOLEAN_FALSE);
                    } else if (tmpInitialValue.equals(modelTrue)) {
                        initialBooleanValueCombo
                                .setText(ProcessDataUtil.UI_BOOLEAN_TRUE);
                    } else {
                        initialBooleanValueCombo.setText(tmpInitialValue);
                    }
                } else if (basicType != null
                        && basicType.getType() != null
                        && (basicType.getType()
                                .equals(BasicTypeType.DATE_LITERAL)
                                || basicType.getType()
                                        .equals(BasicTypeType.DATETIME_LITERAL) || basicType
                                .getType().equals(BasicTypeType.TIME_LITERAL))) {
                    updateText(dateTimeControl.getTextControl(),
                            initialValues.get(0));
                } else {
                    updateText(singleInitialValueText, initialValues.get(0));
                }
            } else {
                singleInitialValueText.setText(""); //$NON-NLS-1$
                initialBooleanValueCombo.setText("");//$NON-NLS-1$
                dateTimeControl.getTextControl().setText(""); //$NON-NLS-1$
            }

        } else {
            singleInitialValueText.setText(""); //$NON-NLS-1$
            initialBooleanValueCombo.setText("");//$NON-NLS-1$
            dateTimeControl.getTextControl().setText(""); //$NON-NLS-1$
        }

        singleInitialValueLabel
                .setText(Messages.DataFieldPropertySection_InitialValue_label);

        if (basicType != null && basicType.getType() != null) {
            if (basicType.getType().equals(BasicTypeType.DATETIME_LITERAL)) {
                dateTimeControl.setType(DateType.DATETIME);

            } else if (basicType.getType().equals(BasicTypeType.DATE_LITERAL)) {
                dateTimeControl.setType(DateType.DATE);

            } else if (basicType.getType().equals(BasicTypeType.TIME_LITERAL)) {
                dateTimeControl.setType(DateType.TIME);
            } else if (basicType.getType()
                    .equals(BasicTypeType.PERFORMER_LITERAL)) {
                /*
                 * Sid: orgModelQueryScriptSection maybe null (new field
                 * wizard).
                 */
                if (orgModelQueryScriptSection != null) {
                    if (orgModelQueryScriptSection.getInput() == null
                            || !orgModelQueryScriptSection.getInput()
                                    .equals(getInput())) {
                        orgModelQueryScriptSection.setInput(Collections
                                .singleton(getInput()));
                        orgModelQueryScriptSection.refresh();
                    }
                }
            }

        }

        // disableAll(getRoot(), !implementedParameter);

        EObject inputType = getInputType();
        if (inputType != null) {
            switch (inputType.eClass().getClassifierID()) {
            // Basic Type
            case Xpdl2Package.BASIC_TYPE:
                showBasicExtrasPage(isBasicArray(), getModelBasicType());
                break;
            case Xpdl2Package.DECLARED_TYPE:
                if (initialValuesPageBook != null) {
                    if (isBasicArray())
                        initialValuesPageBook
                                .showPage(BASIC_TYPE_EXTRAS_INITIAL_VALUES_PAGE);
                    else
                        initialValuesPageBook
                                .showPage(BASIC_TYPE_EXTRAS_INITIAL_VALUE_PAGE);
                }
                break;
            case Xpdl2Package.EXTERNAL_REFERENCE:
                if (initialValuesPageBook != null) {
                    if (isBasicArray())
                        initialValuesPageBook
                                .showPage(BASIC_TYPE_EXTRAS_INITIAL_VALUES_PAGE);
                    else
                        initialValuesPageBook
                                .showPage(BASIC_TYPE_EXTRAS_INITIAL_VALUE_PAGE);
                }
                break;

            // Declared type / ext ref
            default:
                initialValuesPageBook.showEmptyPage();
                break;
            }
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
                if (Xpdl2ModelUtil.getProcess(getInput()) == null
                        && basicType != null
                        && basicType.getType()
                                .equals(BasicTypeType.PERFORMER_LITERAL)) {
                    section.setExpanded(false);
                    section.setEnabled(false);
                } 
                /*
                 * Sid ACE-3665: For date / time fields disable initial values section (unless there is already an initial value
                 * in which case have to show it to allow the user to remove it.
                 */
                else if (basicType != null && (BasicTypeType.DATETIME_LITERAL.equals(basicType.getType())
                        || BasicTypeType.DATE_LITERAL.equals(basicType.getType())
                        || BasicTypeType.TIME_LITERAL.equals(basicType.getType()))) {

                    InitialValues initValues = (InitialValues) Xpdl2ModelUtil.getOtherElement(getDataField(),
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_InitialValues());

                    if (initValues == null || initValues.getValue().isEmpty()) {
                        section.setExpanded(false);
                        section.setEnabled(false);
                    } else {
                        section.setEnabled(true);
                    }

                }
                else {
                    section.setEnabled(true);
                }
            }
        }
    }

    /**
     * The basic array indicator from the process relevant data in the model.
     * 
     * @return
     */
    @Override
    protected boolean isBasicArray() {
        return getDataField().isIsArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#getInputType()
     */
    @Override
    protected DataType getInputType() {
        return getDataField().getDataType();
    }

    /**
     * Get the DataField input
     * 
     * @return
     */
    protected DataField getDataField() {
        return (DataField) getInput();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.xpdl2.edit.ui.properties.general.BaseTypeSection#
     * setBasicInitValue(com.tibco.xpd.xpdl2.Expression)
     */
    protected Command setBasicInitValue(String newValue) {
        // MR 40048 - don't allow set if there is no data except whitespace!
        if (newValue != null && newValue.length() > 0) {
            if (newValue.trim().length() == 0) {
                return null;
            }
        }

        DataField data = getDataField();

        /* Convert UI format to internal format. */
        newValue =
                ProcessDataUtil.convertUIInitialValueToModelFormat(data,
                        newValue);
        if (newValue == null) {
            newValue = ""; //$NON-NLS-1$
        }

        String oldValue = null;
        List<String> values = null;

        InitialValues oldInitialValues =
                (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValues());
        if (oldInitialValues != null) {
            values = oldInitialValues.getValue();
        }

        if (values == null) {
            values = Collections.emptyList();
        } else if (values.size() > 0) {
            oldValue = values.get(0);
        }

        if (oldValue == null) {
            oldValue = ""; //$NON-NLS-1$
        }

        /*
         * Build command
         */
        CompoundCommand cmd = null;

        if (newValue.length() == 0 && oldInitialValues != null) {
            /* initial value emptied; remove it */
            cmd = new LateExecuteCompoundCommand();
            cmd.setLabel(Messages.DataFieldPropertySection_SetInitValue_menu);

            cmd.append(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(getEditingDomain(),
                            (ProcessRelevantData) getInput(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues(),
                            oldInitialValues));

        } else if (values.size() > 1 || !newValue.equals(oldValue)) {
            /* Need to reduce to single entry or old value different. */
            cmd = new LateExecuteCompoundCommand();
            cmd.setLabel(Messages.DataFieldPropertySection_SetInitValue_menu);

            /* Easiest just to replace the whole initialValues element. */
            if (oldInitialValues != null) {
                cmd.append(Xpdl2ModelUtil
                        .getRemoveOtherElementCommand(getEditingDomain(),
                                (ProcessRelevantData) getInput(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_InitialValues(),
                                oldInitialValues));
            }

            if (newValue.length() > 0) {
                InitialValues initialValues =
                        XpdExtensionFactory.eINSTANCE.createInitialValues();

                initialValues.getValue().add(newValue);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(getEditingDomain(),
                                data,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_InitialValues(),
                                initialValues));
            }
        }

        return cmd;
    }

    private void checkInitialValue() {
        if (!isBasicArray() && initialValuesPageBook != null) {
            String errorStr = null;
            String initialValueStr = singleInitialValueText.getText();
            // TODO: uncomment the below code if the dateTimeControl has to be
            // editable
            // if (initialValueStr == null || initialValueStr.length() == 0
            //                    || initialValueStr.equals("")) { //$NON-NLS-1$
            // initialValueStr = dateTimeControl.getText();
            // }
            errorStr = checkInitialValue(new String[] { initialValueStr });

            // If we are in a new field wizard then this will prevent
            // Finish being performed if we currently have duplicate name.
            setCanFinish((errorStr == null), errorStr);

            if (errorStr != null) {
                initialValueBad = true;
                singleInitialValueLabel.setImage(Xpdl2UiPlugin.getDefault()
                        .getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));
                singleInitialValueLabel.setToolTipText(errorStr);

                singleBooleanInitialValueLabel.setImage(Xpdl2UiPlugin
                        .getDefault().getImageRegistry()
                        .get(Xpdl2UiPlugin.IMG_ERROR));
                singleBooleanInitialValueLabel.setToolTipText(errorStr);

            } else {
                initialValueBad = false;

                singleInitialValueLabel.setImage(null);
                singleInitialValueLabel.setToolTipText(""); //$NON-NLS-1$

                singleBooleanInitialValueLabel.setImage(null);
                singleBooleanInitialValueLabel.setToolTipText(""); //$NON-NLS-1$

            }

        }
    }

    @Override
    protected String getInitialValuesTitle() {
        return Messages.BaseFieldOrParamPropertySection_InitialValues_label;
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

        initialValuesPageBook =
                toolkit.createPageBook(comp, SWT.FLAT | SWT.TOP);
        initialValuesPageBook.setLayoutData(new GridData(GridData.FILL_BOTH));

        //
        // setup initial value page with Text box
        //
        Composite singleInitialValueComposite =
                initialValuesPageBook
                        .createPage(BASIC_TYPE_EXTRAS_INITIAL_VALUE_PAGE);
        singleInitialValueComposite.setLayout(new GridLayout(1, true));
        GridData layoutData = new GridData();
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.grabExcessVerticalSpace = true;
        singleInitialValueComposite.setLayoutData(layoutData);

        Composite textComp =
                toolkit.createComposite(singleInitialValueComposite);
        textComp.setLayout(new GridLayout(2, false));
        textComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        singleInitialValueLabel =
                toolkit.createCLabel(textComp,
                        Messages.DataFieldPropertySection_InitialValue_label);
        ControlUtils.forceWidgetVisible(singleInitialValueLabel,
                singleInitialValueLabel.getText());

        GridData labelGridData = new GridData();
        singleInitialValueLabel.setLayoutData(labelGridData);

        singleInitialValueText = toolkit.createText(textComp, ""); //$NON-NLS-1$ //, SWT.BORDER
        GridData textGridData = new GridData(GridData.FILL_HORIZONTAL);
        singleInitialValueText.setLayoutData(textGridData);

        manageControlUpdateOnDeactivate(singleInitialValueText);
        singleInitialValueText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                checkInitialValue();
            }
        });

        //
        // setup initial value page with Boolean Combo box
        //
        Composite singleBooleanInitialValueComposite =
                initialValuesPageBook
                        .createPage(BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUE_PAGE);
        singleBooleanInitialValueComposite.setLayout(new GridLayout(1, true));
        GridData layoutData2 = new GridData();
        layoutData2.grabExcessHorizontalSpace = true;
        layoutData2.grabExcessVerticalSpace = true;
        singleBooleanInitialValueComposite.setLayoutData(layoutData2);

        Composite boolComp =
                toolkit.createComposite(singleBooleanInitialValueComposite);
        boolComp.setLayout(new GridLayout(2, false));
        boolComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        singleBooleanInitialValueLabel =
                toolkit.createCLabel(boolComp,
                        Messages.DataFieldPropertySection_InitialValue_label);

        ControlUtils.forceWidgetVisible(singleBooleanInitialValueLabel,
                singleBooleanInitialValueLabel.getText());

        singleInitialValueLabel.setLayoutData(new GridData());

        initialBooleanValueCombo = toolkit.createCCombo(boolComp); // SWT.BORDER

        initialBooleanValueCombo.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        initialBooleanValueCombo.setEditable(false);
        initialBooleanValueCombo.add("");//$NON-NLS-1$
        initialBooleanValueCombo.add(ProcessDataUtil.UI_BOOLEAN_FALSE);
        initialBooleanValueCombo.add(ProcessDataUtil.UI_BOOLEAN_TRUE);

        initialBooleanValueCombo.setData("", ""); //$NON-NLS-1$ //$NON-NLS-2$
        initialBooleanValueCombo.setData(ProcessDataUtil.UI_BOOLEAN_FALSE,
                ProcessDataUtil.MODEL_BOOLEAN_FALSE);
        initialBooleanValueCombo.setData(ProcessDataUtil.UI_BOOLEAN_TRUE,
                ProcessDataUtil.MODEL_BOOLEAN_TRUE);

        manageControl(initialBooleanValueCombo);

        //
        // setup initial values page with List box (For arrays I think)
        //
        Composite multipleInitValuesComposite =
                initialValuesPageBook
                        .createPage(BASIC_TYPE_EXTRAS_INITIAL_VALUES_PAGE);
        multipleInitValuesComposite.setLayout(new GridLayout(1, true));
        GridData layoutData3 = new GridData();
        layoutData3.grabExcessHorizontalSpace = true;
        layoutData3.grabExcessVerticalSpace = true;
        multipleInitValuesComposite.setLayoutData(layoutData3);

        initValueArrayTable =
                new InitValueArrayTable(multipleInitValuesComposite, toolkit,
                        getEditingDomain(),
                        Messages.DataFieldPropertySection_InitialValues_label);
        initValueArrayTable.setBackground(ColorConstants.white);
        initValueArrayTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        //
        // setup initial array Boolean values page with List box
        //
        Composite multipleBooleanInitialValuesComposite =
                initialValuesPageBook
                        .createPage(BASIC_TYPE_EXTRAS_INITIAL_BOOLEAN_VALUES_PAGE);
        multipleBooleanInitialValuesComposite.setLayout(layout);
        multipleBooleanInitialValuesComposite.setLayoutData(new GridData(
                GridData.FILL_BOTH));

        booleanInitArrayTable =
                new BooleanInitArrayTable(
                        multipleBooleanInitialValuesComposite, toolkit,
                        getEditingDomain(),
                        Messages.DataFieldPropertySection_InitialValues_label);
        booleanInitArrayTable.setBackground(ColorConstants.white);
        booleanInitArrayTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        //
        // setup initial value page with RQL Editor Section for performer
        //
        Composite performerInitialValueComposite =
                initialValuesPageBook
                        .createPage(BASIC_TYPE_EXTRAS_INITIAL_PERFORMER_VALUE_PAGE);
        performerInitialValueComposite.setLayout(layout);
        performerInitialValueComposite.setLayoutData(new GridData(
                GridData.FILL_BOTH));
        createQueryScriptControl(performerInitialValueComposite, toolkit);

        // setup initial value page for Date/Time/DateTime data field
        Composite dateTimeInitValueComposite =
                initialValuesPageBook
                        .createPage(BASIC_TYPE_DATETIME_INIT_VALUE_PAGE);
        dateTimeInitValueComposite.setLayout(new GridLayout(1, true));
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        dateTimeInitValueComposite.setLayoutData(gridData);

        Composite root = toolkit.createComposite(dateTimeInitValueComposite);
        root.setLayout(new GridLayout(4, false));
        root.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        singleInitialValueLabel =
                toolkit.createCLabel(root,
                        Messages.DataFieldPropertySection_InitialValue_label);
        ControlUtils.forceWidgetVisible(singleInitialValueLabel,
                singleInitialValueLabel.getText());
        GridData gData = new GridData();
        singleInitialValueLabel.setLayoutData(gData);

        dateTimeControl = new DateTimeInitControl(toolkit, root);
        manageControl(dateTimeControl.getTextControl());
        // TODO: uncomment the below code if the dateTimeControl has to be
        // editable
        // dateTimeControl.getTextControl().addModifyListener(new
        // ModifyListener() {
        // public void modifyText(ModifyEvent e) {
        // checkInitialValue();
        // }
        // });

        //
        // setup initial array Date/Time/DateTime values page with List box
        //
        Composite dateTimeArrayInitValuesComposite =
                initialValuesPageBook
                        .createPage(BASIC_TYPE_DATETIME_INIT_VALUES_PAGE);
        dateTimeArrayInitValuesComposite.setLayout(layout);
        dateTimeArrayInitValuesComposite.setLayoutData(new GridData(
                GridData.FILL_BOTH));

        dateTimeArrayTable =
                new DateTimeInitArrayTable(dateTimeArrayInitValuesComposite,
                        toolkit, getEditingDomain(),
                        Messages.DataFieldPropertySection_InitialValues_label);
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
        implementedParameter = false;
        if (part instanceof CommonNavigator) {
            TreeItem[] selectionList =
                    ((CommonNavigator) part).getCommonViewer().getTree()
                            .getSelection();
            for (TreeItem item : selectionList)
                if (item.getParentItem().getParentItem().getData() instanceof com.tibco.xpd.xpdl2.Process
                        && ((EObject) item.getData()).eContainer() instanceof ProcessInterface)
                    implementedParameter = true;
        }

        if (getDataField() != null) {
            checkInitialValue();
        }
    }

    /**
     * Returns help context for sections to show individual help details
     * 
     * @return
     */
    public String getHelpContextID() {
        return "com.tibco.xpd.process.analyst.doc.DataField2"; //$NON-NLS-1$
    }

    @Override
    protected void doCreatePreTypeControls(Composite parent,
            XpdFormToolkit toolkit) {
        Label label =
                toolkit.createLabel(parent,
                        Messages.ParameterPropertySection_ReadOnly_label);
        label.setLayoutData(new GridData());
        btnIsReadOnly = toolkit.createButton(parent, "", SWT.CHECK); //$NON-NLS-1$
        btnIsReadOnly.setLayoutData(new GridData());

        manageControl(btnIsReadOnly);
    }

    @Override
    protected String getSectionPreferencesId() {
        return this.getClass().getName();
    }

    @Override
    protected void contributeToExpandables(
            ExpandableSectionStacker expandableHeaderController2) {
        expandableHeaderController2.addSection(INIT_VALUES_HEADER_ID,
                getInitialValuesTitle(),
                100,
                false,
                true);

    }

    // MR 38533 - begin
    @Override
    protected Control createContributedExpandable(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        if (INIT_VALUES_HEADER_ID.equals(sectionId)) {
            return doCreateInitialValuesControls(container, toolkit);
        }
        return null;
    }

    private Command getRemoveConditionalExpressionCommand() {
        Command removeCommand = null;
        DataField myDF = getDataField();
        EReference feature =
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ConditionalParticipant();
        Object otherElement = Xpdl2ModelUtil.getOtherElement(myDF, feature);
        if (otherElement != null) {
            removeCommand =
                    Xpdl2ModelUtil
                            .getSetOtherElementCommand(getEditingDomain(),
                                    myDF,
                                    feature,
                                    null);
        }
        return removeCommand;
    }

    // MR 38533 - end

    /**
     * @param container
     * @param toolkit
     * @return
     */
    private Control createQueryScriptControl(Composite container,
            XpdFormToolkit toolkit) {
        if (getPropertySheetPage() != null) {
            orgModelQueryScriptSection =
                    new OrgModelQueryScriptSection(
                            Xpdl2Package.eINSTANCE.getDataField());

            orgModelQueryScriptSection.createControls(container,
                    getPropertySheetPage());
        }
        return container;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        if (toTest instanceof DataField) {
            if (toTest.getClass() == DataFieldImpl.class) {
                return true;
            }
        }

        return false;
    }

}
