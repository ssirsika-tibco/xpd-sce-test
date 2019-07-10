/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.properties.general;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.DigitTextVerifyListener;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesSection;
import com.tibco.xpd.xpdl2.edit.util.ControlUtils;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract class that defines the type selection for the data field and type
 * declaration property sections
 * 
 * @author njpatel
 * 
 */
public abstract class BaseTypeSection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    /*
     * Sid ACE-484 switch on suppress unsupported types for ACE.
     */
    public final static boolean suppressAceUnsupportedTypes = true;

    // Data Types;
    protected static final int BASICTYPE = 0;

    protected static final int DECLAREDTYPE = 1;

    protected static final int EXTERNALREFERENCE = 2;

    protected static final int CASEREFTYPE = 3;

    protected static String[] TYPES = new String[] {
            Messages.BaseTypeSection_basicTypeCmb_label,
            Messages.BaseTypeSection_declaredTypeCmb_label,
            Messages.BaseTypeSection_bomTypeCmb_label,
            Messages.BaseTypeSection_caseTypeRefCmb_label };

    // Radio buttons for type selection
    protected Button[] btnTypes = null;

    // Checkboxes for each type
    protected Button[] chkArrays = null;

    protected boolean showArrayField = false;

    private ScrolledPageBook book = null;

    /** Basic type length. */
    private Text txtBasicLength = null;

    /** Basic type length. */
    private Text txtBasicScale = null;

    // Basic type control
    private CCombo cmbBasicTypes = null;

    // Type declaration control
    private CCombo cmbDeclarationIds = null;

    private static final String DECLAREDIDPREFIX = "ID"; //$NON-NLS-1$


    // External reference controls
    private CLabel labelExternalRef = null;

    // case ref type label control
    private CLabel labelCaseRefType = null;

    private BOMTypePicker bomTypePickerCtrl;

    private CaseClassPicker caseClassPickerCtrl;

    private ComplexDataTypeReference complexDataTypeRef = null;

    protected LinkedHashMap<UIBasicTypes, String> typeNameMap = new LinkedHashMap<UIBasicTypes, String>();

    private ComplexDataTypesMergedInfo _complexTypesInfo = null;

    private Composite root;

    private boolean implementedType = false;

    private boolean wantReferencesSection;

    private ExpandableSectionStacker expandableHeaderController;

    private Object referencesSectionHandle = null;

    public final static String TYPECTRLS_SECTION = "dataTypes"; //$NON-NLS-1$

    public static final String INIT_VALUES_HEADER_ID = "initialValues"; //$NON-NLS-1$

    public static final String REFERENCES_HEADER_ID = "references"; //$NON-NLS-1$

    private ScrolledComposite scrolledContainer;

    private Control expanablesContainer;

    /**
     * Constructor
     * 
     * @param eclass
     */
    public BaseTypeSection(EClass eclass, boolean wantInitialValuesSection,
            boolean wantReferencesSection) {
        super(eclass);

        this.wantReferencesSection = wantReferencesSection;

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        // When changing input, de-cache complex types extensions info.
        clearComplexTypesInfo();

        super.setInput(items);

        // Only load the reference list on set input. It is unnecessary to
        // refresh it every time something about the data field refreshes.
        if (referencesSectionHandle != null) {
            doLoadReferencesList(referencesSectionHandle);
        }

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

        if (obj instanceof Button) {
            Button btn = (Button) obj;

            // Update the declaration type to the selected option
            if (btn.equals(btnTypes[BASICTYPE])) {
                // Set the new selection
                cmd = setBasicTypeCmd(getBasicType());

            } else if (btn.equals(btnTypes[DECLAREDTYPE])) {
                // Set the new selection
                DeclaredType declType = getDeclaredType(true);
                if (declType != null) {
                    cmd = setDeclaredTypeCmd(declType);
                }

            } else if (btn.equals(btnTypes[EXTERNALREFERENCE])) {
                // Set the external reference
                cmd = setExternalRefCmd(getExternalReference());
            } else if (btn.equals(btnTypes[CASEREFTYPE])) {
                // enable Case Class Reference only when the Global Data is
                // Enabled
                cmd = setCaseRefTypeCmd(getCaseRefType());
            } else if (btn.equals(chkArrays[BASICTYPE])) {
                cmd = setBasicArrayCmd(chkArrays[BASICTYPE].getSelection());
            } else if (btn.equals(chkArrays[DECLAREDTYPE])) {
                cmd = setBasicArrayCmd(chkArrays[DECLAREDTYPE].getSelection());
            } else if (btn.equals(chkArrays[EXTERNALREFERENCE])) {
                cmd =
                        setBasicArrayCmd(chkArrays[EXTERNALREFERENCE]
                                .getSelection());
            } else if (btn.equals(chkArrays[CASEREFTYPE])) {
                // enable Case Class Reference only when the Global Data is
                // Enabled
                cmd = setBasicArrayCmd(chkArrays[CASEREFTYPE].getSelection());
            }
        } else if (obj instanceof CCombo) {
            // If basic type changed then update it
            if (obj.equals(cmbBasicTypes)) {
                // Update basic type
                cmd = setBasicTypeCmd(getBasicType());
            } else if (obj.equals(cmbDeclarationIds)) {
                // Update declared type
                DeclaredType declType = getDeclaredType(false);
                if (declType != null) {
                    cmd = setDeclaredTypeCmd(declType);
                }
            }
        } else if (obj instanceof Text) {
            // For numeric fields the length is stored as the precision
            if (obj == txtBasicLength) {
                if (getModelBasicType() != null
                        && getModelBasicType().getType()
                                .equals(BasicTypeType.STRING_LITERAL)) {
                    if (isEmptyBasicLength()) {
                        cmd = setBasicLength(null);
                    } else if (getBasicLength() != null) {
                        cmd = setBasicLength(getBasicLength());
                    } else if (getModelBasicType().getLength() != null
                            && getModelBasicType().getLength().getValue() != null) {
                        cmd =
                                setBasicLength(safeParseShort(getModelBasicType()
                                        .getLength().getValue()));
                    }
                } else {
                    cmd = setBasicPrecision(getBasicLength());
                }
            } else if (obj == txtBasicScale) {
                cmd = setBasicScale(getBasicScale());
            }

        }
        return cmd;
    }

    /**
     * Get the Basic Type (if defined) from the XPDL2 model for the section
     * input.
     * 
     * @return The type from the model or null if input does not have it.
     */
    protected abstract BasicType getModelBasicType();

    /**
     * Set length to the given value, or ensure the length element is removed.
     * 
     * @param basicLength
     *            or null.
     * @return command or null.
     */
    protected Command setBasicLength(Short basicLength) {
        BasicType modelBasicType = getModelBasicType();
        if (basicLength != null) {
            Length ln = Xpdl2Factory.eINSTANCE.createLength();
            ln.setValue(Short.toString(basicLength));

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.BaseTypeSection_SetFieldLen_menu);
            cmd.append(SetCommand.create(getEditingDomain(),
                    modelBasicType,
                    Xpdl2Package.eINSTANCE.getBasicType_Length(),
                    ln));
            return cmd;

        } else if (modelBasicType.getLength() != null) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.BaseTypeSection_SetFieldLen_menu);
            cmd.append(RemoveCommand.create(getEditingDomain(),
                    modelBasicType.getLength()));
            return cmd;
        }
        return null;
    }

    /**
     * Set precision to the given value, or ensure the precision element is
     * removed.
     * 
     * @param basicPrecision
     *            or null.
     * @return command or null.
     */
    protected Command setBasicPrecision(Short basicPrecision) {
        BasicType modelBasicType = getModelBasicType();
        if (basicPrecision != null) {
            Precision prec = Xpdl2Factory.eINSTANCE.createPrecision();
            prec.setValue(basicPrecision.shortValue());

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.BaseTypeSection_SetFieldLen_menu);
            cmd.append(SetCommand.create(getEditingDomain(),
                    modelBasicType,
                    Xpdl2Package.eINSTANCE.getBasicType_Precision(),
                    prec));
            return cmd;

        } else if (modelBasicType.getPrecision() != null) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.BaseTypeSection_SetFieldLen_menu);
            cmd.append(RemoveCommand.create(getEditingDomain(),
                    modelBasicType.getPrecision()));
            return cmd;
        }
        return null;
    }

    /**
     * Set scale to the given value, or ensure the scale element is removed.
     * 
     * @param basicScale
     *            or null.
     * @return command or null.
     */
    protected Command setBasicScale(Short basicScale) {
        BasicType modelBasicType = getModelBasicType();
        if (basicScale != null) {
            Scale scale = Xpdl2Factory.eINSTANCE.createScale();
            scale.setValue(basicScale.shortValue());

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.BaseTypeSection_SetFieldDecimals_menu);
            cmd.append(SetCommand.create(getEditingDomain(),
                    modelBasicType,
                    Xpdl2Package.eINSTANCE.getBasicType_Scale(),
                    scale));
            return cmd;

        } else if (modelBasicType.getScale() != null) {
            /*
             * Sid ACE-1094 Don't want to allow decimals to be unset because
             * that would cause it to switch to a FloatingPoint number
             * unexpectedly.
             * 
             * So instead we will set a scale to zero
             */
            Scale scale = Xpdl2Factory.eINSTANCE.createScale();
            scale.setValue((short) 0);

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.BaseTypeSection_SetFieldDecimals_menu);
            cmd.append(SetCommand.create(getEditingDomain(),
                    modelBasicType,
                    Xpdl2Package.eINSTANCE.getBasicType_Scale(),
                    scale));
            return cmd;
        }
        return null;
    }

    private Short getBasicLength() {
        String text = txtBasicLength.getText();
        return safeParseShort(text);
    }

    private boolean isEmptyBasicLength() {
        return "".equals(txtBasicLength.getText()); //$NON-NLS-1$
    }

    /**
     * Return Integer for string, or null if cannot convert.
     * 
     * @param text
     * @return
     */
    private Short safeParseShort(String text) {
        try {
            return Short.valueOf(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Short getBasicScale() {
        String text = txtBasicScale.getText();
        return safeParseShort(text);
    }

    /**
     * Get the input data type.
     * 
     * @return <code>BasicType</code>, <code>DeclaredType</code> or
     *         <code>ExternalReference</code>
     */
    protected abstract DataType getInputType();

    /**
     * Return the command to set the Basic Type
     * 
     * @param basicType
     * 
     * @return Command to set the Basic Type
     */
    protected abstract Command setBasicTypeCmd(BasicType basicType);

    /**
     * Return the command to set the Declared Type
     * 
     * @param declaredType
     * 
     * @return Command to set the Declared Type
     */
    protected abstract Command setDeclaredTypeCmd(DeclaredType declaredType);

    /**
     * Return the command to set the External Reference
     * 
     * @param externalReference
     * 
     * @return Command to set the External Reference
     */
    protected abstract Command setExternalRefCmd(
            ExternalReference externalReference);

    /***
     * 
     * @param caseRefType
     * @return
     */
    protected abstract Command setCaseRefTypeCmd(RecordType caseRefType);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        root = toolkit.createComposite(scrolledContainer);
        GridLayout gl = new GridLayout(1, false);
        gl.marginLeft = 4;
        gl.marginWidth = 0;
        root.setLayout(gl);

        //
        // Set up the stack of stack of expandable sections (type/Init
        // Vals/References (store different prefs for main data type and
        // separate for wizard / properties too.
        String sectPrefId =
                getSectionContainerType() + "." + getSectionPreferencesId(); //$NON-NLS-1$

        expandableHeaderController = new ExpandableSectionStacker(sectPrefId);

        expandableHeaderController.addSection(TYPECTRLS_SECTION,
                Messages.BaseTypeSection_FieldTypeSectionHeader_label,
                SWT.DEFAULT,
                true,
                false);
        // MR 38533 - begin
        contributeToExpandables(expandableHeaderController);

        /**
         * ABSTRACT TO SUB if (wantInitialValuesSection) {
         * expandableHeaderController.addSection(INIT_VALUES_HEADER_ID,
         * getInitialValuesTitle(), 100, false, true); }
         */
        // MR 38533 - end
        if (wantReferencesSection
                && getSectionContainerType() != ContainerType.WIZARD) {
            expandableHeaderController.addSection(REFERENCES_HEADER_ID,
                    Messages.BaseFieldOrParamPropertySection_References_label,
                    60,
                    false,
                    true);
        }

        expanablesContainer =
                expandableHeaderController.createExpandableSections(root,
                        toolkit,
                        this);

        GridData gd =
                new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                        | GridData.GRAB_HORIZONTAL);
        expanablesContainer.setLayoutData(gd);

        // set contents of sections on to the scrolled composite
        scrolledContainer.setContent(root);

        Point prefSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        setMinimumHeight(prefSize.y);

        return scrolledContainer;
    }

    @Override
    public void expandableSectionStateChanged(String sectionId) {
        Point sz = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(sz);
    }

    /**
     * Create the type/initvals/references section detail.
     */
    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {

        if (TYPECTRLS_SECTION.equals(sectionId)) {
            Composite cmp = toolkit.createComposite(container);

            GridLayout gl = new GridLayout(2, false);
            gl.marginHeight = 0;
            gl.marginWidth = 0;
            cmp.setLayout(gl);

            doCreatePreTypeControls(cmp, toolkit);
            createTypesControl(cmp, toolkit);

            return cmp;

        } else if (REFERENCES_HEADER_ID.equals(sectionId)) {

            referencesSectionHandle = createObjectReferencesSection();

            return createObjectReferencesControls(referencesSectionHandle,
                    container,
                    toolkit);
        } else {
            return createContributedExpandable(sectionId, container, toolkit);
        }

    }

    protected abstract void contributeToExpandables(
            ExpandableSectionStacker expandableHeaderController2);

    protected abstract Control createContributedExpandable(String sectionId,
            Composite container, XpdFormToolkit toolkit);

    /**
     * Create the object reference section object
     * 
     * @return The object reference section itself (this will be passed back to
     *         {@link #createObjectReferencesControls(Object, Composite, XpdFormToolkit)}
     *         and {@link #doLoadReferencesList(ObjectReferencesSection)} later.
     */
    protected abstract Object createObjectReferencesSection();

    /**
     * Create the controls in the object references section into the given
     * container
     * 
     * @param objectReferencesSection
     *            The object returned by
     *            {@link #createObjectReferencesSection()}
     * @param container
     * @param toolkit
     * 
     * @return The root control.
     */
    protected abstract Control createObjectReferencesControls(
            Object objectReferencesSection, Composite container,
            XpdFormToolkit toolkit);

    /**
     * Create extra controls before the type definition controls.
     * <p>
     * Parent has grid layout of 2 columns.
     * 
     * @param parent
     * @param toolkit
     */
    protected abstract void doCreatePreTypeControls(Composite parent,
            XpdFormToolkit toolkit);

    /**
     * Create the controls for the initial values section.
     * 
     * @param root
     * @param toolkit
     * @return
     */
    protected abstract Control doCreateInitialValuesControls(Composite root,
            XpdFormToolkit toolkit);

    /**
     * Return a unique id for preference storage.
     * 
     * @return
     */
    protected abstract String getSectionPreferencesId();

    /**
     * Load the object references into the given ObjectReferencesSection.
     * 
     * @param referencesSection
     *            As previously returned by
     *            {@link #createObjectReferencesSection()}
     */
    protected abstract void doLoadReferencesList(Object referencesSection);

    /**
     * Tell subclass to refresh any extra controls it has added to the base
     * section controls added via createPreTypeControls() /
     * createInitialValuesControls()
     * 
     */
    protected abstract void doRefreshExtraDetails();

    /**
     * 
     * @return the text for the initial values expandable header control.
     */
    protected abstract String getInitialValuesTitle();

    @Override
    protected void doRefresh() {
        EObject inputType = getInputType();

        if (inputType != null) {
            // Update the details section
            doRefreshExtraDetails();

            for (Button element : btnTypes) {
                element.setSelection(false);
            }

            // Clear all input options in the pages
            updateCCombo(cmbBasicTypes, null);
            updateCCombo(cmbDeclarationIds, null);

            complexDataTypeRef = null;

            // Disable declared type selection if there aren't any available.
            // (Else user is able to create invalid xml because declaredType->Id
            // must be a valid reference.
            Collection<?> declaredTypes = null;

            EObject container = getInputContainer();
            if (container != null) {
                if (container instanceof Process) {
                    container = container.eContainer();
                } else if (container instanceof ProcessInterface) {
                    container = Xpdl2ModelUtil.getPackage(container);
                }

                if (container instanceof Package) {
                    declaredTypes = ((Package) container).getTypeDeclarations();
                }
            }

            boolean typesAvailable = false;

            if (declaredTypes != null && declaredTypes.size() > 0) {
                // if There are any type declarations check whether any but the
                // current input object AND build the combo list.
                cmbDeclarationIds.removeAll();

                for (Iterator<?> iter = declaredTypes.iterator(); iter
                        .hasNext();) {
                    TypeDeclaration type = (TypeDeclaration) iter.next();

                    if (!type.equals(getInput())) {
                        // There are declared types (other than the current
                        // input object) available
                        typesAvailable = true;

                        int index = cmbDeclarationIds.getItemCount();

                        String name = WorkingCopyUtil.getText(type);
                        cmbDeclarationIds.add(name, index);
                        cmbDeclarationIds.setData(DECLAREDIDPREFIX + index,
                                type.getId());

                    }
                }
            }

            if (typesAvailable) {
                btnTypes[DECLAREDTYPE].setEnabled(true);
            } else {
                btnTypes[DECLAREDTYPE].setEnabled(false);
            }

            /*
             * Update the type choice
             */
            switch (inputType.eClass().getClassifierID()) {

            // Basic Type
            case Xpdl2Package.BASIC_TYPE:
                BasicType basicType = (BasicType) inputType;

                btnTypes[BASICTYPE].setSelection(true);
                if (chkArrays[BASICTYPE] != null) {
                    chkArrays[BASICTYPE].setSelection(isBasicArray());
                }

                // Select the basic type
                UIBasicTypes uiBasicType = UIBasicTypes.fromBasicType(basicType);
                updateCCombo(cmbBasicTypes,
                        typeNameMap.get(uiBasicType));

                // For numeric types length is stored in precision field.
                if (!isImplementedType()) {
                    enableBasicLengthFields(uiBasicType);
                }
                if (txtBasicLength.isEnabled() || isImplementedType()) {
                    if (basicType.getType()
                            .equals(BasicTypeType.STRING_LITERAL)) {
                        updateText(txtBasicLength,
                                basicType.getLength() == null ? "" : basicType //$NON-NLS-1$
                                        .getLength().getValue());
                    } else if (basicType.getType()
                            .equals(BasicTypeType.INTEGER_LITERAL)
                            || basicType.getType()
                                    .equals(BasicTypeType.FLOAT_LITERAL)) {

                        /*
                         * Sid ACE-1417 Didn't used to update the length control
                         * if Precision model was unset, that meant if switched
                         * from field with it set to one without then the old
                         * field's precision (length) would still be shown.
                         * 
                         * So now always set.
                         */
                        updateText(txtBasicLength,
                                basicType.getPrecision() == null ? "" //$NON-NLS-1$
                                        : Short.toString(
                                                basicType
                                .getPrecision().getValue()));
                    } else {
                        updateText(txtBasicLength,
                                basicType.getPrecision() == null ? Messages.BaseTypeSection_NotApplicable_text
                                        : Short.toString(basicType
                                                .getPrecision().getValue()));
                    }
                }
                if (txtBasicScale.isEnabled() || isImplementedType()) {
                    if (basicType.getType().equals(BasicTypeType.FLOAT_LITERAL)) {
                        /*
                         * Sid ACE-1417 Didn't used to update the length control
                         * if Scale (decimals) model was unset, that meant if switched
                         * from field with it set to one without then the old
                         * field's Scale (decimals) would still be shown.
                         * 
                         * So now always set.
                         */
                        updateText(txtBasicScale,
                                basicType.getScale() == null ? "" //$NON-NLS-1$
                                        : Short.toString(basicType.getScale()
                                                .getValue()));
                    } else {
                        updateText(txtBasicScale,
                                basicType.getScale() == null ? Messages.BaseTypeSection_NotApplicable_text
                                        : Short.toString(basicType.getScale()
                                                .getValue()));
                    }
                }

                book.showPage(new Integer(BASICTYPE));
                break;

            // Declared type
            case Xpdl2Package.DECLARED_TYPE:
                /*
                 * Show the page before setting up the controls - that way the
                 * text in combo doesn't behave oddly (scrolled left) when first
                 * select DelcaredType in data field wizard.
                 */
                book.showPage(new Integer(DECLAREDTYPE));

                DeclaredType declaredType = (DeclaredType) inputType;

                btnTypes[DECLAREDTYPE].setSelection(true);
                if (chkArrays[DECLAREDTYPE] != null) {
                    chkArrays[DECLAREDTYPE].setSelection(isBasicArray());
                }

                int cnt = cmbDeclarationIds.getItemCount();
                String szSelectedTypeDeclarationID =
                        declaredType.getTypeDeclarationId();
                boolean visited = false;
                if (szSelectedTypeDeclarationID != null) {
                    for (int i = 0; i < cnt; i++) {
                        String typeId =
                                (String) cmbDeclarationIds
                                        .getData(DECLAREDIDPREFIX + i);
                        if (szSelectedTypeDeclarationID.equals(typeId)) {
                            cmbDeclarationIds.select(i);
                            visited = true;
                            break;
                        }
                    }
                    // check if the deleted declared type is being referenced in
                    // the available type declarations. if so then it must be
                    // shown as unresolved reference
                    if (!visited
                            && declaredType.getTypeDeclarationId().length() > 0) {
                        boolean isInputTypeReferred =
                                checkInputTypeReferred(declaredType,
                                        declaredTypes);
                        if (isInputTypeReferred) {
                            cmbDeclarationIds
                                    .add(com.tibco.xpd.xpdl2.edit.ui.internal.Messages.BaseTypeSection_UnresolvedReference,
                                            0);
                            cmbDeclarationIds.select(0);
                        }
                    }
                }

                break;

            // External reference
            case Xpdl2Package.EXTERNAL_REFERENCE:
                btnTypes[EXTERNALREFERENCE].setSelection(true);
                if (chkArrays[EXTERNALREFERENCE] != null) {
                    chkArrays[EXTERNALREFERENCE].setSelection(isBasicArray());
                }

                refreshComplexDataTypeName((ExternalReference) inputType);

                // Show the page
                book.showPage(new Integer(EXTERNALREFERENCE));

                break;

            // Case Ref Type
            case Xpdl2Package.RECORD_TYPE:
                btnTypes[CASEREFTYPE].setSelection(true);
                if (null != chkArrays[CASEREFTYPE]) {
                    chkArrays[CASEREFTYPE].setSelection(isBasicArray());
                }
                refreshCaseRefTypeName((RecordType) inputType);
                book.showPage(new Integer(CASEREFTYPE));
                break;
            }

        }
    }

    /**
     * @param deletedDeclaredType
     * @param availableDeclaredTypes
     * @return
     */
    private boolean checkInputTypeReferred(DeclaredType deletedDeclaredType,
            Collection<?> availableDeclaredTypes) {
        if (null != deletedDeclaredType && null != availableDeclaredTypes
                && availableDeclaredTypes.size() > 0) {
            for (Iterator<?> iter = availableDeclaredTypes.iterator(); iter
                    .hasNext();) {
                TypeDeclaration availableTypeDeclaration =
                        (TypeDeclaration) iter.next();
                if (null != availableTypeDeclaration.getDeclaredType()) {
                    if (availableTypeDeclaration.getDeclaredType()
                            .getTypeDeclarationId()
                            .equals(deletedDeclaredType.getTypeDeclarationId())) {
                        // this means deleted declared type is being referred in
                        // the available type declarations. return true so it
                        // can shown as unresolved reference
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Enable/disable length, presion and scale field dependent on type.
     * 
     * @param uiBasicType
     */
    private void enableBasicLengthFields(UIBasicTypes uiBasicType) {
        
        if (UIBasicTypes.FixedPointNumber.equals(uiBasicType)) {
            txtBasicLength.setEnabled(true);
            txtBasicLength
                    .setToolTipText(Messages.BaseTypeSection_FloatLength_tooltip);
            txtBasicScale.setEnabled(true);
            
        } else if (UIBasicTypes.FloatingPointNumber.equals(uiBasicType)) {
            updateText(txtBasicLength,
                    Messages.BaseTypeSection_NotApplicable_text);
            txtBasicLength.setEnabled(false);
            
            updateText(txtBasicScale,
                    Messages.BaseTypeSection_NotApplicable_text);
            txtBasicScale.setEnabled(false);
            
        } else if (UIBasicTypes.Integer.equals(uiBasicType)) {
            
            txtBasicLength.setEnabled(true);
            txtBasicLength
                    .setToolTipText(Messages.BaseTypeSection_IntegerLength_tooltip);
            updateText(txtBasicScale,
                    Messages.BaseTypeSection_NotApplicable_text);
            txtBasicScale.setEnabled(false);
            
        } else if (UIBasicTypes.String.equals(uiBasicType)) {
            
            txtBasicLength.setEnabled(true);
            txtBasicLength
                    .setToolTipText(Messages.BaseTypeSection_StringLength_tooltip);
            updateText(txtBasicScale,
                    Messages.BaseTypeSection_NotApplicable_text);
            txtBasicScale.setEnabled(false);

        } else {

            updateText(txtBasicLength,
                    Messages.BaseTypeSection_NotApplicable_text);
            txtBasicLength.setEnabled(false);
            txtBasicLength.setToolTipText(""); //$NON-NLS-1$
            updateText(txtBasicScale,
                    Messages.BaseTypeSection_NotApplicable_text);
            txtBasicScale.setEnabled(false);
        }

    }

    /**
     * Create types option controls
     * 
     * @param parent
     * @param toolkit
     */
    private void createTypesControl(Composite parent, XpdFormToolkit toolkit) {
        GridData gridData = null;

        Composite cmpTypes = toolkit.createComposite(parent);

        GridLayout mainGL = new GridLayout(3, false);
        mainGL.marginWidth = 0;

        cmpTypes.setLayout(mainGL);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        cmpTypes.setLayoutData(gd);

        // Create the types radio buttons
        btnTypes = new Button[TYPES.length];

        // Add the first types option
        btnTypes[0] = toolkit.createButton(cmpTypes, TYPES[0], SWT.RADIO);
        manageControl(btnTypes[0]);

        // Add separator
        gridData = new GridData(GridData.FILL_VERTICAL);
        gridData.verticalSpan = TYPES.length;
        toolkit.createSeparator(cmpTypes, SWT.VERTICAL).setLayoutData(gridData);

        // Add the book control (bookMinSizeComp is there to tell wizard / prop
        // sheet min size we want this page book to be else page book always
        // allows itself to be shrunk to 10x10
        Composite bookMinSizeComp = toolkit.createComposite(cmpTypes);
        GridLayout gl = new GridLayout(1, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        bookMinSizeComp.setLayout(gl);
        // We'll set layout data on bookMinSizeCompo when we know preferred size
        // of all pages.

        book = toolkit.createPageBook(bookMinSizeComp, SWT.NONE);

        // Add the rest of the types option
        for (int idx = 1; idx < TYPES.length; idx++) {
            btnTypes[idx] =
                    toolkit.createButton(cmpTypes, TYPES[idx], SWT.RADIO);
            manageControl(btnTypes[idx]);
        }

        // Create the type declaration options pages
        Point minSize = createTypeOptionPages(toolkit);

        // Book should fill it's container composite.
        gridData = new GridData(GridData.FILL_BOTH);
        book.setLayoutData(gridData);

        // book container composite should be min size for pages.
        // This ensure that the effect parent of book is not told that min size
        // of book is 10x10.
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.verticalSpan = TYPES.length;
        gridData.widthHint = minSize.x;
        gridData.heightHint = minSize.y;
        bookMinSizeComp.setLayoutData(gridData);

        // book.layout(true);
    }

    /**
     * Create type options pages
     * 
     * @param toolkit
     */
    private Point createTypeOptionPages(XpdFormToolkit toolkit) {
        Composite page = null;

        Point minSize = new Point(0, 0);

        chkArrays = new Button[TYPES.length];
        for (int idx = 0; idx < TYPES.length; idx++) {
            page = toolkit.createComposite(book.getContainer());

            // Create pages for each type declaration type
            switch (idx) {
            case BASICTYPE:
                createBasicTypePage(page, toolkit);
                break;

            case DECLAREDTYPE:
                createDeclaredTypePage(page, toolkit);
                break;

            case EXTERNALREFERENCE:
                createExternalReferencePage(page, toolkit);
                break;

            case CASEREFTYPE:
                createCaseReferenceTypePage(page, toolkit);
                break;

            }

            Point min = page.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            if (min.x > minSize.x) {
                minSize.x = min.x;
            }
            if (min.y > minSize.y) {
                minSize.y = min.y;
            }

            book.registerPage(new Integer(idx), page);
        }

        return minSize;
    }

    /**
     * @param page
     * @param toolkit
     */
    private void createCaseReferenceTypePage(final Composite parent,
            XpdFormToolkit toolkit) {
        GridData gridData = null;

        parent.setLayout(new GridLayout(3, false));

        Label lblMsg =
                toolkit.createLabel(parent,
                        Messages.BaseTypeSection_setCaseRefType_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        lblMsg.setLayoutData(gridData);
        ControlUtils.forceWidgetVisible(lblMsg, lblMsg.getText());

        labelCaseRefType =
                toolkit.createCLabel(parent,
                        Messages.BaseTypeSection_caseTypeReference_label);
        setCaseRefLabelLayoutData();
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
                        return BaseTypeSection.this.getProject();
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
                                    .setToolTipText(Messages.BaseTypeSection_caseTypeReferenceNotSet_longdesc);
                        }
                    }

                    @Override
                    protected Command setCaseRefTypeCmd(RecordType recordType) {
                        return BaseTypeSection.this
                                .setCaseRefTypeCmd(recordType);
                    }
                };

        caseClassPickerCtrl
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        if (isShowArrayField()) {
            chkArrays[CASEREFTYPE] =
                    toolkit.createButton(parent,
                            Messages.BaseTypeSection_basicArray_label,
                            SWT.CHECK);
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.horizontalSpan = 2;
            gridData.horizontalIndent = 3;
            chkArrays[CASEREFTYPE].setLayoutData(gridData);
            manageControl(chkArrays[CASEREFTYPE]);
        }
    }

    /**
     * Create the basic type options page
     * 
     * @param parent
     * @param toolkit
     */
    private void createBasicTypePage(Composite parent, XpdFormToolkit toolkit) {
        GridLayout layout = new GridLayout(getNumberOfColumns(), false);
        layout.marginHeight = 0;
        layout.marginBottom = 2;
        parent.setLayout(layout);

        Composite basicTypeComposite = toolkit.createComposite(parent);
        layout = new GridLayout(2, false);
        layout.marginHeight = 2;
        basicTypeComposite.setLayout(layout);

        GridData gridData =
                new GridData(GridData.FILL_HORIZONTAL
                        | GridData.VERTICAL_ALIGN_BEGINNING);
        basicTypeComposite.setLayoutData(gridData);

        Label lblMsg =
                toolkit.createLabel(basicTypeComposite,
                        Messages.BaseTypeSection_setBasicType_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
        gridData.horizontalSpan = 2;
        lblMsg.setLayoutData(gridData);
        ControlUtils.forceWidgetVisible(lblMsg, lblMsg.getText());

        // Basic Type label
        Label basicTypeLabel =
                toolkit.createLabel(basicTypeComposite,
                        Messages.BaseTypeSection_basicType_label);
        basicTypeLabel.setLayoutData(new GridData());
        ControlUtils.forceWidgetVisible(basicTypeLabel,
                basicTypeLabel.getText());

        cmbBasicTypes = toolkit.createCCombo(basicTypeComposite, SWT.NONE);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        cmbBasicTypes.setLayoutData(gridData);
        cmbBasicTypes.setEditable(false);

        // ***********************************************************
        // NOTE: THESE TYPE NAMES ARE DELIBERATLY NON TRANSLATEABLE
        // It is currently intentional that all languages use the same type name
        // terminology.
        // Sid / Tim 14/01/2009
        // ***********************************************************

        /*
         * XPD-7263: The human readable names are moved to ProcessDataUtil
         * .getBasicTypeLabel() so that they can be consistently used at all
         * places.
         */
        /*
         * Sid ACE-1094 - use UIBasicTypes so that we can distinguish betwen
         * fixed and floating point numbers.
         * 
         * Also, re-ordered the list into something more sensible (alphabetic
         * basic types - then with BOM/CaseRef/TypeDecl tagged on) = matches
         * data folder table editor ordering.
         */
        typeNameMap.put(UIBasicTypes.Boolean,
                ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.Boolean.getDefaultBasicType()));

        typeNameMap.put(UIBasicTypes.Date, ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.Date.getDefaultBasicType()));

        typeNameMap.put(UIBasicTypes.DateTime,
                ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.DateTime.getDefaultBasicType()));

        typeNameMap.put(UIBasicTypes.FixedPointNumber,
                ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.FixedPointNumber.getDefaultBasicType()));

        typeNameMap.put(UIBasicTypes.FloatingPointNumber,
                ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.FloatingPointNumber.getDefaultBasicType()));

        /*
         * Sid ACE-484 suppress Integer type for ACE.
         */
        if (!suppressAceUnsupportedTypes) {
            typeNameMap.put(UIBasicTypes.Integer,
                    ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.Integer.getDefaultBasicType()));
        }

        typeNameMap.put(UIBasicTypes.Performer,
                ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.Performer.getDefaultBasicType()));

        typeNameMap.put(UIBasicTypes.String,
                ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.String.getDefaultBasicType()));

        typeNameMap.put(UIBasicTypes.Time, ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.Time.getDefaultBasicType()));

        /* Sid ACE-1192 - added URI data type. */
        typeNameMap.put(UIBasicTypes.URI, ProcessDataUtil.getBasicTypeLabel(UIBasicTypes.URI.getDefaultBasicType()));

        // ***********************************************************
        // NOTE: THE ABOVE TYPE NAMES ARE DELIBERATLY NON TRANSLATEABLE
        // It is currently intentional that all languages use the same type name
        // terminology.
        // Sid / Tim 14/01/2009
        // ***********************************************************

        for (Map.Entry<UIBasicTypes, String> entry : typeNameMap.entrySet()) {
            cmbBasicTypes.add(entry.getValue());
            cmbBasicTypes.setData(entry.getValue(), entry.getKey());
        }

        cmbBasicTypes.setVisibleItemCount(typeNameMap.size());
        Label basicTypeLengthLabel =
                toolkit.createLabel(basicTypeComposite,
                        Messages.BaseTypeSection_basicLength_label);
        basicTypeLengthLabel.setLayoutData(new GridData());
        ControlUtils.forceWidgetVisible(basicTypeLengthLabel,
                basicTypeLengthLabel.getText());

        txtBasicLength = toolkit.createText(basicTypeComposite, ""); //$NON-NLS-1$
        txtBasicLength.addVerifyListener(new DigitTextVerifyListener());
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        txtBasicLength.setLayoutData(gridData);

        Label basicTypeDecimalLabel =
                toolkit.createLabel(basicTypeComposite,
                        Messages.BaseTypeSection_basicScale_label);
        basicTypeDecimalLabel.setLayoutData(new GridData());
        ControlUtils.forceWidgetVisible(basicTypeDecimalLabel,
                basicTypeDecimalLabel.getText());

        txtBasicScale = toolkit.createText(basicTypeComposite, ""); //$NON-NLS-1$
        txtBasicScale.addVerifyListener(new DigitTextVerifyListener());
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        txtBasicScale.setLayoutData(gridData);

        manageControl(cmbBasicTypes);
        manageControl(txtBasicLength);
        manageControl(txtBasicScale);

        if (isShowArrayField()) {
            chkArrays[BASICTYPE] =
                    toolkit.createButton(basicTypeComposite,
                            Messages.BaseTypeSection_basicArray_label,
                            SWT.CHECK);
            gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
            gridData.horizontalSpan = 2;
            chkArrays[BASICTYPE].setLayoutData(gridData);
            manageControl(chkArrays[BASICTYPE]);
        }

    }

    protected int getNumberOfColumns() {
        return 1;
    }

    protected CCombo getBasicTypeControl() {
        return cmbBasicTypes;
    }

    protected Text getBasicLengthControl() {
        return txtBasicLength;
    }

    protected Text getBasicScaleControl() {
        return txtBasicScale;
    }

    /**
     * Create the declared type option page
     * 
     * @param parent
     * @param toolkit
     */
    private void createDeclaredTypePage(Composite parent, XpdFormToolkit toolkit) {
        int columns =
                ContainerType.WIZARD.equals(getSectionContainerType()) ? 1 : 2;
        parent.setLayout(new GridLayout(columns, false));

        Label lblMsg =
                toolkit.createLabel(parent,
                        Messages.BaseTypeSection_setDeclaredType_label);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = columns;
        lblMsg.setLayoutData(gridData);
        ControlUtils.forceWidgetVisible(lblMsg, lblMsg.getText());

        Label label =
                toolkit.createLabel(parent,
                        Messages.BaseTypeSection_declaredTypeID_label);
        label.setLayoutData(new GridData());
        ControlUtils.forceWidgetVisible(label, label.getText());

        cmbDeclarationIds = toolkit.createCCombo(parent);
        cmbDeclarationIds.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        manageControl(cmbDeclarationIds);

        if (isShowArrayField()) {
            chkArrays[DECLAREDTYPE] =
                    toolkit.createButton(parent,
                            Messages.BaseTypeSection_basicArray_label,
                            SWT.CHECK);
            gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
            gridData.horizontalSpan = columns;
            chkArrays[DECLAREDTYPE].setLayoutData(gridData);
            manageControl(chkArrays[DECLAREDTYPE]);
        }
    }

    /**
     * Create the external reference option page
     * 
     * @param parent
     * @param toolkit
     */
    private void createExternalReferencePage(final Composite parent,
            XpdFormToolkit toolkit) {
        GridData gridData = null;

        parent.setLayout(new GridLayout(3, false));

        Label lblMsg =
                toolkit.createLabel(parent,
                        Messages.BaseTypeSection_setBOMTypeDomain_label);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        lblMsg.setLayoutData(gridData);
        ControlUtils.forceWidgetVisible(lblMsg, lblMsg.getText());

        labelExternalRef =
                toolkit.createCLabel(parent,
                        Messages.BaseTypeSection_bomType_label);
        setExtRefLabelLayoutData();
        bomTypePickerCtrl =
                new BOMTypePicker(parent, SWT.NONE, toolkit,
                        getEditingDomain(), getSectionContainerType()) {

                    /**
                     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#getProject()
                     * 
                     * @return
                     */
                    @Override
                    protected IProject getProject() {
                        return BaseTypeSection.this.getProject();
                    }

                    @Override
                    protected Command setExternalRefCmd(ExternalReference extRef) {
                        return BaseTypeSection.this.setExternalRefCmd(extRef);
                    }

                    @Override
                    protected void clearErrorTooltip() {
                        labelExternalRef.setImage(null);
                        labelExternalRef.setToolTipText(null);
                    }

                    @Override
                    protected void setErrorTooltip(String tooltip) {
                        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
                            labelExternalRef.setImage(errIcon);
                            labelExternalRef.setToolTipText(tooltip);
                        }
                    }

                };

        bomTypePickerCtrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        if (isShowArrayField()) {
            chkArrays[EXTERNALREFERENCE] =
                    toolkit.createButton(parent,
                            Messages.BaseTypeSection_basicArray_label,
                            SWT.CHECK);
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.horizontalSpan = 2;
            gridData.horizontalIndent = 3;
            chkArrays[EXTERNALREFERENCE].setLayoutData(gridData);
            manageControl(chkArrays[EXTERNALREFERENCE]);
        }
    }

    private void setExtRefLabelLayoutData() {
        labelExternalRef.setLayoutData(new GridData());
    }

    private void setCaseRefLabelLayoutData() {
        labelCaseRefType.setLayoutData(new GridData());
    }

    /**
     * Get the currently selected basic type
     * 
     * @return The BasicType
     */
    private BasicType getBasicType() {
        /*
         * Sid ACE-1094 - new UIBasicType enum makes life a bit easier to set
         * type.
         */
        UIBasicTypes uiBasicType = (UIBasicTypes) cmbBasicTypes.getData(cmbBasicTypes.getText());

        if (uiBasicType == null) {
            uiBasicType = UIBasicTypes.String;
        }
        
        return uiBasicType.cloneDefaultBasicType();
    }

    /**
     * Get the currently selected declared type
     * 
     * @return The DeclaredType
     */
    private DeclaredType getDeclaredType(boolean createDefault) {

        DeclaredType declaredType = null;

        int index = cmbDeclarationIds.getSelectionIndex();
        if (index >= 0) {
            String szID =
                    (String) cmbDeclarationIds
                            .getData(DECLAREDIDPREFIX + index);

            if (szID != null) {
                declaredType = Xpdl2Factory.eINSTANCE.createDeclaredType();
                declaredType.setTypeDeclarationId(szID);
            }
        }

        if (declaredType == null && createDefault) {
            // Always attempt to set a default id else can end up with invalid
            // xpdl.
            if (cmbDeclarationIds.getItemCount() > 0) {
                String szID =
                        (String) cmbDeclarationIds
                                .getData(DECLAREDIDPREFIX + 0);
                declaredType = Xpdl2Factory.eINSTANCE.createDeclaredType();
                declaredType.setTypeDeclarationId(szID);
            }
        }

        return declaredType;
    }

    /**
     * Get the currently selected external reference
     * 
     * @return The ExternalReference
     */
    private ExternalReference getExternalReference() {
        ExternalReference extReference = null;

        // Set the external reference data
        if (complexDataTypeRef != null) {
            extReference = complexDataTypeRefToXpdl2Ref(complexDataTypeRef);

        } else {
            // No external reference specified so set location to empty string
            extReference = Xpdl2Factory.eINSTANCE.createExternalReference();

            extReference.setLocation(""); //$NON-NLS-1$
        }

        return extReference;
    }

    /***
     * get the currently selected case class type
     * 
     * @return
     */
    private RecordType getCaseRefType() {

        RecordType caseRefType = null;
        Member extRefMember = null;
        ExternalReference extReference = getExternalReference();

        if (null != extReference) {
            caseRefType = Xpdl2Factory.eINSTANCE.createRecordType();
            EList<Member> member = caseRefType.getMember();

            if (member.size() > 0) {
                extRefMember = member.get(0);
            } else {
                extRefMember = Xpdl2Factory.eINSTANCE.createMember();
            }
        }

        if (null != extRefMember) {
            extRefMember.setExternalReference(extReference);
            caseRefType.getMember().add(extRefMember);
        }

        return caseRefType;
    }

    /**
     * @return the complexTypesInfo
     */
    public ComplexDataTypesMergedInfo getComplexTypesInfo() {
        if (_complexTypesInfo == null) {
            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
        }
        return _complexTypesInfo;
    }

    /**
     * When changing input, de-cache complex types extensions info.
     */
    private void clearComplexTypesInfo() {
        _complexTypesInfo = null;

    }

    /**
     * Convert a complex data type extension point reference to an xpdl2
     * External reference.
     * 
     * @param extRef
     * @return
     */
    private ExternalReference complexDataTypeRefToXpdl2Ref(
            ComplexDataTypeReference reference) {

        ExternalReference extReference =
                Xpdl2Factory.eINSTANCE.createExternalReference();

        extReference.setLocation(reference.getLocation());
        extReference.setXref(reference.getXRef());
        extReference.setNamespace(reference.getNameSpace());

        return extReference;

    }

    /**
     * Get the descriptive name for the given selected external reference
     * complex data type.
     * 
     * @return
     */
    private void refreshComplexDataTypeName(ExternalReference extRef) {
        bomTypePickerCtrl.setToolTipText(""); //$NON-NLS-1$
        labelExternalRef.setToolTipText(""); //$NON-NLS-1$

        //
        // Use new pluggable complex data type extensions
        ComplexDataTypesMergedInfo complexTypesInfo = getComplexTypesInfo();
        if (complexTypesInfo != null && bomTypePickerCtrl != null
                && !bomTypePickerCtrl.isDisposed()) {
            bomTypePickerCtrl.setValue(ProcessUIUtil
                    .xpdl2RefToComplexDataTypeRef(extRef));
            return;
        }

        // Update the text control with the class name
        bomTypePickerCtrl.setValue(null);

        if (labelExternalRef.getImage() != null) {
            labelExternalRef.setImage(null);
            setExtRefLabelLayoutData();
            labelExternalRef.getParent().layout();

        }

        return;
    }

    /***
     * 
     * Get the descriptive name for the given selected case reference type
     * pointing to external reference complex data type.
     * 
     * @param caseRefType
     */
    private void refreshCaseRefTypeName(RecordType caseRefType) {
        caseClassPickerCtrl.setToolTipText(""); //$NON-NLS-1$
        labelCaseRefType.setToolTipText(""); //$NON-NLS-1$

        //
        // Use new pluggable complex data type extensions
        ComplexDataTypesMergedInfo complexTypesInfo = getComplexTypesInfo();
        if (complexTypesInfo != null && caseClassPickerCtrl != null
                && !caseClassPickerCtrl.isDisposed()) {
            if (null != caseRefType) {
                ExternalReference extRef =
                        caseRefType.getMember().get(0).getExternalReference();
                caseClassPickerCtrl.setValue(ProcessUIUtil
                        .xpdl2RefToComplexDataTypeRef(extRef));
                return;
            }
        }

        // Update the text control with the class name
        caseClassPickerCtrl.setValue(null);

        if (labelCaseRefType.getImage() != null) {
            labelCaseRefType.setImage(null);
            setCaseRefLabelLayoutData();
            labelCaseRefType.getParent().layout();

        }

        return;
    }

    public Composite getRoot() {
        return root;
    }

    public boolean isImplementedType() {
        return implementedType;
    }

    public void setImplementedType(boolean implementedType) {
        this.implementedType = implementedType;
    }

    /**
     * The basic array indicator from the process relevant data in the model.
     * 
     * @return
     */
    protected boolean isBasicArray() {
        boolean isBasicArray = false;
        EObject input2 = getInput();
        if (input2 instanceof ProcessRelevantData) {
            isBasicArray = ((ProcessRelevantData) input2).isIsArray();
        }
        return isBasicArray;
    }

    /**
     * Create command to set the array indicator.
     * 
     * @param basicArray
     * @return
     */
    private Command setBasicArrayCmd(boolean basicArray) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.DataFieldPropertySection_SetArrayType_menu);

        cmd.append(SetCommand.create(getEditingDomain(),
                getInput(),
                Xpdl2Package.eINSTANCE.getProcessRelevantData_IsArray(),
                Boolean.valueOf(basicArray)));
        return cmd;
    }

    /**
     * Returns whether to show the array check box or not (depending on data
     * field or property view)
     * 
     * @return
     */
    public boolean isShowArrayField() {
        return showArrayField;
    }

    /**
     * Sets boolean value indicating whether to show the array check box or not
     * (depending on data field or property view)
     * 
     * @param showArray
     */
    public void setShowArrayField(boolean showArray) {
        this.showArrayField = showArray;
    }

    /**
     * @return the expandableHeaderController
     */
    public ExpandableSectionStacker getExpandableHeaderController() {
        return expandableHeaderController;
    }

    /**
     * Picker control to set the external reference.
     * 
     * @author njpatel
     */
    private static abstract class BOMTypePicker extends
            AbstractPickerControl<ComplexDataTypeReference> {

        private String errTxt;

        private String label;

        protected final Image errIcon;

        private ComplexDataTypesMergedInfo complexTypesInfo = null;

        public BOMTypePicker(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain,
                ContainerType containerType) {
            super(parent, style, toolkit, editingDomain, false);
            errIcon =
                    Xpdl2UiPlugin.getDefault().getImageRegistry()
                            .get(Xpdl2UiPlugin.IMG_ERROR);
            setBrowseTooltip(Messages.BaseTypeSection_selectBOMType_browse_tooltip);
            setLabelProvider(new LabelProvider() {
                @Override
                public String getText(Object element) {
                    return label != null ? label : ""; //$NON-NLS-1$
                }
            });
            // Only allow hyperlink in properties view
            setHyperlinkActive(containerType == ContainerType.PROPERTYVIEW);
        }

        /**
         * Get the current project.
         * 
         * @return the project
         */
        protected abstract IProject getProject();

        @Override
        protected ComplexDataTypeReference doBrowse(Control control) {

            Shell shell = control.getShell();
            ComplexDataTypeReference theResult = null;
            IProject project = getProject();
            PickerTypeQuery[] queries =
                    new PickerTypeQuery[] { new BOMTypeQuery(project,
                            BOMTypeQuery.CLASS_TYPE,
                            BOMTypeQuery.PRIMITIVE_TYPE,
                            BOMTypeQuery.ENUMERATION_TYPE,
                            BOMTypeQuery.CASE_CLASS_TYPE,
                            BOMTypeQuery.GLOBAL_CLASS_TYPE) };

            // XPD-3129:using project filter,restricts the picker to Type from
            // BOMs in same Project only, which is not desired.
            IFilter[] filters = new IFilter[] {};

            Object result =
                    PickerService.getInstance().openSinglePickerDialog(shell,
                            queries,
                            null,
                            null,
                            null,
                            filters);
            if (result instanceof Type
                    && ComplexDataUIUtil.checkProjectDependencies(shell,
                            project,
                            (Type) result,
                            ((Type) result).getName(),
                            Messages.PickerUtil_pickComplexType_label)) {

                Classifier classifier = (Classifier) result;
                ComplexDataTypesMergedInfo complexDataTypesMergedInfo =
                        getComplexTypesInfo();

                theResult =
                        ComplexDataUIUtil
                                .resolveSelectionToReference(classifier,
                                        project,
                                        complexDataTypesMergedInfo);
            }
            return theResult;

        }

        /**
         * @return the complexTypesInfo
         */
        protected ComplexDataTypesMergedInfo getComplexTypesInfo() {
            if (complexTypesInfo == null) {
                complexTypesInfo =
                        ComplexDataTypeExtPointHelper
                                .getAllComplexDataTypesMergedInfo();
            }
            return complexTypesInfo;
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                ComplexDataTypeReference value) {
            // Clear button not shown so this will not get called
            return null;
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                ComplexDataTypeReference value) {
            return setExternalRefCmd(getExternalReference(value));
        }

        protected abstract Command setExternalRefCmd(ExternalReference extRef);

        /**
         * Get the currently selected external reference
         * 
         * @param ref
         * 
         * @return The ExternalReference
         */
        private ExternalReference getExternalReference(
                ComplexDataTypeReference ref) {
            ExternalReference extReference = null;

            // Set the external reference data
            if (ref != null) {
                extReference = complexDataTypeRefToXpdl2Ref(ref);

            } else {
                // No external reference specified so set location to empty
                // string
                extReference = Xpdl2Factory.eINSTANCE.createExternalReference();

                extReference.setLocation(""); //$NON-NLS-1$
            }

            return extReference;
        }

        /**
         * Convert a complex data type extension point reference to an xpdl2
         * External reference.
         * 
         * @param extRef
         * @return
         */
        protected ExternalReference complexDataTypeRefToXpdl2Ref(
                ComplexDataTypeReference reference) {

            ExternalReference extReference =
                    Xpdl2Factory.eINSTANCE.createExternalReference();

            extReference.setLocation(reference.getLocation());
            extReference.setXref(reference.getXRef());
            extReference.setNamespace(reference.getNameSpace());

            return extReference;

        }

        @Override
        public void setValue(ComplexDataTypeReference value) {
            errTxt = null;
            label = null;
            clearErrorTooltip();
            if (value != null) {
                ComplexDataTypesMergedInfo info = getComplexTypesInfo();
                IProject project = getProject();
                if (project != null && info != null) {
                    if (!calculateReference(value, project, info)) {
                        value = null;
                        setErrorTooltip(Messages.BaseTypeSection_UnknownDataType_longdesc);
                    }
                }
            } else {
                // Reference not set
                setErrorTooltip(Messages.BaseTypeSection_bomTypeNotSet_longdesc);
                errTxt = Messages.BaseTypeSection_noReferenceSet_shortdesc;
            }
            super.setValue(value);
        }

        private boolean calculateReference(ComplexDataTypeReference element,
                IProject project, ComplexDataTypesMergedInfo complexTypesInfo) {

            errTxt = Messages.BaseTypeSection_UnresolvedReference;

            label =
                    complexTypesInfo.getComplexDataTypeDescriptiveName(element,
                            project);
            if (label == null || label.length() == 0) {
                // Not one of our supported complex data types.
                // Try and resolve the name from any complex data type
                // but prefix with <Unsupported>
                label =
                        ComplexDataTypeExtPointHelper
                                .getComplexDataTypeDescriptiveName(element,
                                        project);
                if (label != null && label.length() > 0) {
                    errTxt =
                            Messages.BaseTypeSection_UnsupportedDataType_shortDesc
                                    + label;
                    label = null;
                    setErrorTooltip(Messages.BaseTypeSection_UnsupportedDataType_longdesc);
                } else {
                    // Try to resolve against referenced projects
                    try {
                        Set<IProject> refProjects =
                                ProjectUtil2
                                        .getReferencedProjectsHierarchy(project,
                                                true);

                        for (IProject refProject : refProjects) {
                            label =
                                    complexTypesInfo
                                            .getComplexDataTypeDescriptiveName(element,
                                                    refProject);

                            if (label == null || label.length() == 0) {
                                // Not one of our supported complex data types.
                                // Try and resolve the name from any complex
                                // data type but prefix with <Unsupported>
                                label =
                                        ComplexDataTypeExtPointHelper
                                                .getComplexDataTypeDescriptiveName(element,
                                                        refProject);
                                if (label != null && label.length() > 0) {
                                    errTxt =
                                            Messages.BaseTypeSection_UnsupportedDataType_shortDesc
                                                    + label;
                                    label = null;
                                    setErrorTooltip(Messages.BaseTypeSection_UnsupportedDataType_longdesc);
                                    break;
                                }
                            } else {
                                // Resolved
                                break;
                            }
                        }
                    } catch (CyclicDependencyException e1) {
                        label = null;
                    }
                }
            }
            if (label != null && label.length() == 0) {
                label = null;
            } else if (label != null) {
                errTxt = null;
            }
            return label != null;
        }

        @Override
        protected String getClearText() {
            return errTxt != null ? errTxt
                    : Messages.BaseTypeSection_noReferenceSet_shortdesc;
        }

        @Override
        protected void hyperlinkActivated(ComplexDataTypeReference value) {
            if (value != null) {
                ComplexDataTypesMergedInfo info = getComplexTypesInfo();
                IProject project = getProject();
                if (info != null && project != null) {
                    Object obj =
                            info.getComplexDataTypeFromReference(value, project);

                    if (obj instanceof EObject) {
                        if (!openInEditor(obj)) {
                            selectInProjectExplorer(obj);
                        }
                    }
                }
            }
        }

        /**
         * Clear the label's tooltip and error icon. Default implementation does
         * nothing. Subclasses may extend to clear tooltip on the accompanying
         * label.
         */
        protected void clearErrorTooltip() {
        }

        /**
         * Set the label's tooltip and error icon. Default implementation does
         * nothing. Subclasses may extend to set tooltip on the accompanying
         * label.
         */
        protected void setErrorTooltip(String tooltip) {
        }
    }

    /**
     * This picker control allows the user to select Case Classes for the given
     * BOM model(s) in a project
     * 
     * @author bharge
     * @since 30 Nov 2012
     */
    public static abstract class CaseClassPicker extends BOMTypePicker {

        /**
         * @param parent
         * @param style
         * @param toolkit
         * @param editingDomain
         * @param containerType
         */
        public CaseClassPicker(Composite parent, int style,
                XpdFormToolkit toolkit, EditingDomain editingDomain,
                ContainerType containerType) {

            super(parent, style, toolkit, editingDomain, containerType);
            setBrowseTooltip(Messages.BaseTypeSection_selectCaseTypeReference_browse_tooltip);
        }

        /**
         * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#doBrowse(org.eclipse.swt.widgets.Control)
         * 
         * @param control
         * @return
         */
        @Override
        protected ComplexDataTypeReference doBrowse(Control control) {

            ComplexDataTypeReference theResult = null;
            IProject project = getProject();

            PickerTypeQuery[] queries =
                    new PickerTypeQuery[] { new BOMTypeQuery(project,
                            BOMTypeQuery.CASE_CLASS_TYPE) };
            // XPD-3129:using project filter,restricts the picker to Type from
            // BOMs in same Project only, which is not desired.
            IFilter[] filters = new IFilter[] {};

            Shell shell = control.getShell();
            Object result =
                    PickerService.getInstance().openSinglePickerDialog(shell,
                            queries,
                            null,
                            null,
                            null,
                            filters,
                            null);
            if (result instanceof Type
                    && ComplexDataUIUtil.checkProjectDependencies(shell,
                            project,
                            (Type) result,
                            ((Type) result).getName(),
                            Messages.PickerUtil_pickComplexType_label)) {

                Classifier classifier = (Classifier) result;
                ComplexDataTypesMergedInfo complexDataTypesMergedInfo =
                        getComplexTypesInfo();

                theResult =
                        ComplexDataUIUtil
                                .resolveSelectionToReference(classifier,
                                        project,
                                        complexDataTypesMergedInfo);
            }
            return theResult;
        }

        /**
         * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference)
         * 
         * @param editingDomain
         * @param value
         * @return
         */
        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                ComplexDataTypeReference value) {

            return setCaseRefTypeCmd(getCaseRefType(value));

        }

        /**
         * Get the command to set the RecordType.
         * 
         * @param recordType
         * @return
         */
        protected abstract Command setCaseRefTypeCmd(RecordType recordType);

        /**
         * @param ref
         * @return
         */
        private RecordType getCaseRefType(ComplexDataTypeReference ref) {

            RecordType caseRefType = null;
            Member extRefMember = null;
            ExternalReference extReference = null;

            if (null != ref) {
                extReference = complexDataTypeRefToXpdl2Ref(ref);
            } else {
                /*
                 * No external reference specified so set location to empty
                 * string
                 */
                extReference = Xpdl2Factory.eINSTANCE.createExternalReference();

                extReference.setLocation(""); //$NON-NLS-1$
            }

            if (null != extReference) {
                caseRefType = Xpdl2Factory.eINSTANCE.createRecordType();
                EList<Member> member = caseRefType.getMember();

                if (member.size() > 0) {
                    extRefMember = member.get(0);
                } else {
                    extRefMember = Xpdl2Factory.eINSTANCE.createMember();
                }
            }

            if (null != extRefMember) {
                extRefMember.setExternalReference(extReference);
                caseRefType.getMember().add(extRefMember);
            }

            return caseRefType;

        }

        /**
         * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#setExternalRefCmd(com.tibco.xpd.xpdl2.ExternalReference)
         * 
         * @param extRef
         * @return
         */
        @Override
        protected Command setExternalRefCmd(ExternalReference extRef) {
            // Not used in this class. "setCaseRefTypeCmd" used instead.
            return null;
        }
    }

}
