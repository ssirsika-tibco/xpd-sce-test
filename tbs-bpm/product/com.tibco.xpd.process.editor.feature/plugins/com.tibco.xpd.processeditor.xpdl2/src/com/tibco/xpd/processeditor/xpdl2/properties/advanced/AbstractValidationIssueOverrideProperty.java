/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ValidationControlUtil;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdl2.OtherElementsContainer;

/**
 * Advanced property for overriding validation issues.
 * <p>
 * Sub-class need only provide the issue id. And that class then need only be
 * contributed to to the AdvancedPropertyContribution extension point.
 * 
 * @author aallway
 * @since 17 Jul 2012
 */
public abstract class AbstractValidationIssueOverrideProperty extends
        AbstractContributedAdvancedProperty {

    public static final Object VALIDATION_OVERRIDE_UNSET = new Object();

    private Object[] validationOverrideTypes = new Object[] {
            VALIDATION_OVERRIDE_UNSET,
            ValidationIssueOverrideType.SUPPRESS_UNTIL_NEXT_FLOW_CHANGE,
            ValidationIssueOverrideType.SUPPRESS_UNTIL_MANUAL_REACTIVATION };

    private String[] validationOverrideLabels =
            new String[] {
                    Messages.AbstractValidationIssueOverrideProperty_OverrideTypeValidate_label,
                    Messages.AbstractValidationIssueOverrideProperty_OverrideTypeUntilFlowChange_label2,
                    Messages.AbstractValidationIssueOverrideProperty_OverrideTypeNoValidate_label2 };

    /**
     * @return The validation issue id that this property is the override for.
     */
    protected abstract String getValidationIssueId();

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#createPropertyDescriptor(java.lang.String,
     *      java.lang.String)
     * 
     * @param descriptorId
     * @param displayName
     * @return
     */
    @Override
    public PropertyDescriptor createPropertyDescriptor(String descriptorId,
            String displayName) {
        return new DropDownPropertyDescriptor(descriptorId, displayName,
                getDropDownItemLabels(), getDropDownItemValues());
    }

    /**
     * @return The array of internal values for each item in drop down list.
     */
    protected Object[] getDropDownItemValues() {
        return validationOverrideTypes;
    }

    /**
     * @return The array of labels for each data item in drop down list.
     */
    protected String[] getDropDownItemLabels() {
        return validationOverrideLabels;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getSequenceCount(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public int getSequenceCount(EObject input) {
        return 1;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getValue(org.eclipse.emf.ecore.EObject,
     *      int)
     * 
     * @param input
     * @param sequenceIndex
     * @return
     */
    @Override
    public Object getValue(EObject input, int sequenceIndex) {
        if (input instanceof OtherElementsContainer) {
            ValidationIssueOverrideType overrideType =
                    ValidationControlUtil
                            .getValidationIssueOverrideType((OtherElementsContainer) input,
                                    getValidationIssueId());

            if (overrideType != null) {
                return overrideType;
            }
        }

        return VALIDATION_OVERRIDE_UNSET;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getDefaultValue(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public Object getDefaultValue(EObject input) {
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object, int)
     * 
     * @param editingDomain
     * @param input
     * @param value
     * @param sequenceIndex
     * @return
     */
    @Override
    public Command getSetValueCommand(EditingDomain editingDomain,
            EObject input, Object value, int sequenceIndex) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AbstractValidationIssueOverrideProperty_ValidationIssueOverride_menu);

        if (input instanceof OtherElementsContainer) {
            OtherElementsContainer container = (OtherElementsContainer) input;

            cmd.append(ValidationControlUtil
                    .getSetValidationIssueOverrideCommand(editingDomain,
                            container,
                            getValidationIssueId(),
                            (value instanceof ValidationIssueOverrideType) ? (ValidationIssueOverrideType) value
                                    : null));
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isSet(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public boolean isSet(EObject input) {
        if (input instanceof OtherElementsContainer) {
            if (ValidationControlUtil
                    .getValidationIssueOverride((OtherElementsContainer) input,
                            getValidationIssueId()) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getRemoveValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject)
     * 
     * @param editingDomain
     * @param input
     * @return
     */
    @Override
    public Command getRemoveValueCommand(EditingDomain editingDomain,
            EObject input) {
        if (input instanceof OtherElementsContainer) {
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.AbstractValidationIssueOverrideProperty_RemoveValidationOverride_menu);

            cmd.append(ValidationControlUtil
                    .getRemoveValidationIssueOverrideCommand(editingDomain,
                            (OtherElementsContainer) input,
                            getValidationIssueId()));

            return cmd;

        }
        return null;
    }

}
