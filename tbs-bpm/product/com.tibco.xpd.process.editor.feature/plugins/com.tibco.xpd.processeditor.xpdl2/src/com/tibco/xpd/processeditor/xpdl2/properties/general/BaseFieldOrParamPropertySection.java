package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection;
import com.tibco.xpd.analyst.resources.xpdl2.properties.general.UIBasicTypes;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Simple class to be sub-classed by data field or formal parameter property
 * section. It simply provides the extra controls for listing references to the
 * field/param elsewhere in process.
 * 
 * @author aallway
 */
public abstract class BaseFieldOrParamPropertySection extends BaseTypeSection {

    protected static final String ALPHA_REGEX = "[a-zA-Z]"; //$NON-NLS-1$   

    /**
     * Constructor
     * 
     * @param eclass
     */
    public BaseFieldOrParamPropertySection(EClass eclass) {
        super(eclass, true, true);
    }

    /**
     * Constructor to specify whether we need to show initial values and/or
     * references section or not.
     * 
     * @param eclass
     * @param wantInitialValuesSection
     * @param wantReferencesSection
     */
    public BaseFieldOrParamPropertySection(EClass eclass,
            boolean wantInitialValuesSection, boolean wantReferencesSection) {

        super(eclass, wantInitialValuesSection, wantReferencesSection);
    }

    @Override
    public void setInput(Collection items) {
        super.setInput(items);

        // For new fields - Reset default name to something unique.
        // Ensure name is unique at the very start as we now check for
        // uniqueness we don't want user to be shown an error by default!
        ProcessRelevantData data = getProcessRelevantData();
        if (data != null && data.eContainer() == null) {
            String baseName = data.getName();
            String finalName = baseName;

            EObject container = getInputContainer();
            if (container != null) {
                int idx = 1;
                while (Xpdl2ModelUtil
                        .getDuplicateDisplayFieldOrParam(getInputContainer(),
                                data,
                                finalName) != null
                        || Xpdl2ModelUtil
                                .getDuplicateFieldOrParam(getInputContainer(),
                                        data,
                                        NameUtil.getInternalName(finalName,
                                                true)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                data.setName(NameUtil.getInternalName(finalName, true));
                Xpdl2ModelUtil.setOtherAttribute(data,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        finalName);
            }
        }
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection#createObjectReferencesSection()
     * 
     * @return
     */
    @Override
    protected Object createObjectReferencesSection() {
        return new FieldOrParamReferencesSection(getSite());
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection#createObjectReferencesControls(java.lang.Object,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param objectReferencesSection
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    protected Control createObjectReferencesControls(
            Object objectReferencesSection, Composite container,
            XpdFormToolkit toolkit) {
        return ((FieldOrParamReferencesSection) objectReferencesSection)
                .createControls(container, toolkit);
    }

    /**
     * Get the prop sheet input as a process relevant data object or null if it
     * isn't one!
     * 
     * @return
     */
    public ProcessRelevantData getProcessRelevantData() {
        if (getInput() instanceof ProcessRelevantData) {
            return ((ProcessRelevantData) getInput());
        }
        return null;
    }

    @Override
    protected void doLoadReferencesList(Object referencesSection) {
        if (referencesSection instanceof FieldOrParamReferencesSection) {
            ((FieldOrParamReferencesSection) referencesSection)
                    .setInput(Collections.singletonList(getInput()));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.xpdl2.edit.ui.properties.general.BaseTypeSection#
     * getModelBasicType()
     */
    @Override
    protected BasicType getModelBasicType() {
        DataType dt = getInputType();
        if (dt instanceof BasicType) {
            return (BasicType) dt;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#setDeclaredTypeCmd
     * (com.tibco.xpd.xpdlr2.DeclaredType)
     */
    @Override
    protected Command setDeclaredTypeCmd(DeclaredType declaredType) {
        boolean doIt = false;

        ProcessRelevantData data = getProcessRelevantData();

        if (data.getDataType() instanceof DeclaredType) {
            String currType =
                    ((DeclaredType) data.getDataType()).getTypeDeclarationId();

            String newType = declaredType.getTypeDeclarationId();

            if (currType == null || !currType.equals(newType)) {
                // None currently defined or new is different.
                doIt = true;
            }

        } else {
            // Complete change of type - do it.
            doIt = true;
        }

        if (doIt) {
            return setDataTypeCmd(declaredType);
        }

        return null;
    }

    /**
     * Get expression for the init value literal entered by user.
     * 
     * @return
     */
    protected Expression getBasicInitValue(String value) {
        Expression expression = Xpdl2Factory.eINSTANCE.createExpression();

        expression.getMixed()
                .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        value);

        return expression;
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
        boolean doIt = false;

        /*
         * Sid ACE-1094 - use the UIBasicType a this takes into account the
         * difference between FixedPoint and FLoatingPoint numbers and hence
         * will allow the types not to be considered as equal.
         */
        UIBasicTypes newBasicType = UIBasicTypes.fromBasicType(basicType);

        UIBasicTypes currentBasicType = UIBasicTypes.fromBasicType(getModelBasicType());
        if (currentBasicType != null) {
            if (!currentBasicType.equals(newBasicType)) {
                // type is changing.
                doIt = true;
            }
        } else {
            // curr type is other than basic type.
            doIt = true;
        }

        if (doIt) {
            return setDataTypeCmd(basicType);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#setExternalRefCmd
     * (com.tibco.xpd.xpdlr2.ExternalReference)
     */
    @Override
    protected Command setExternalRefCmd(ExternalReference externalReference) {
        return setDataTypeCmd(externalReference);
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection#setCaseRefTypeCmd(com.tibco.xpd.xpdl2.RecordType)
     * 
     * @param caseRefType
     * @return
     */
    @Override
    protected Command setCaseRefTypeCmd(RecordType caseRefType) {
        return setDataTypeCmd(caseRefType);
    }

    /**
     * Create command to set the data type
     * 
     * @param dType
     * @return Command to create data type for the data field
     */
    private Command setDataTypeCmd(DataType dType) {
        CompoundCommand cmd = new CompoundCommand();
        if (getInput() instanceof DataField) {
            cmd.setLabel(Messages.BaseFieldOrParamPropertySection_SetFieldType_menu);
        } else {
            cmd.setLabel(Messages.BaseFieldOrParamPropertySection_SetParameterType_menu);
        }
        cmd.append(SetCommand.create(getEditingDomain(),
                getInput(),
                Xpdl2Package.eINSTANCE.getProcessRelevantData_DataType(),
                dType));

        if (hasInitialValues()) {
            cmd.append(getRemoveAllInitialValuesCommand());
        }

        return cmd;
    }

    /**
     * @param initialValues
     * @return
     */
    protected String checkInitialValue(String[] initialValues) {
        String errorStr = null;
        BasicType basicType = getModelBasicType();
        if (basicType != null
                && basicType.getType().equals(BasicTypeType.FLOAT_LITERAL)) {
            for (String initialValue : initialValues) {
                if (initialValue.trim().length() > 0) {
                    try {
                        String standardisedFloat =
                                LocaleUtils
                                        .getStandardisedDoubleValue(initialValue);
                        Double.parseDouble(standardisedFloat);
                    } catch (NumberFormatException e) {
                        errorStr =
                                Messages.BaseFieldOrParamPropertySection_InvalidDecimal_tooltip;
                    }
                }
            }
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.INTEGER_LITERAL)) {
            for (String initialValue : initialValues) {
                if (initialValue.trim().length() > 0) {
                    try {
                        Integer.parseInt(initialValue);
                    } catch (NumberFormatException e) {
                        errorStr =
                                Messages.BaseFieldOrParamPropertySection_InvalidInteger_Tooltip;
                    }
                }
            }
        }
        return errorStr;
    }

    protected boolean hasInitialValues() {
        if (getInput() instanceof ProcessRelevantData) {
            Object initValues =
                    Xpdl2ModelUtil
                            .getOtherElement((ProcessRelevantData) getInput(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_InitialValues());
            if (initValues != null) {
                return true;
            }
        }
        return false;
    }

    protected Command getRemoveAllInitialValuesCommand() {
        Object initValues =
                Xpdl2ModelUtil
                        .getOtherElement((ProcessRelevantData) getInput(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_InitialValues());
        if (initValues != null) {
            return Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(getEditingDomain(),
                            (ProcessRelevantData) getInput(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues(),
                            initValues);
        }

        return UnexecutableCommand.INSTANCE;
    }
}
