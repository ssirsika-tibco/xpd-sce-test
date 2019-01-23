/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.properties;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.DropDownPropertyDescriptor;
import com.tibco.xpd.xpdExtension.SystemErrorActionType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Advanced property describing the system action to take when a process
 * activity errors. Filters on processes only.
 * 
 * @author patkinso
 * @since 25 Jun 2012
 */
public class AdvancedPropertiesFaultConfiguration extends
        AbstractAdvancedModelFeatureProperty {

    protected static final String DEFAULT_DROPDOWN_VALUE =
            Messages.AdvancedPropertiesFaultConfiguration_UnspecifiedDropDownValue;

    protected static final String[] labels;

    protected static final Object[] values;

    static {
        Map<String, Object> labelValueMap = new LinkedHashMap<String, Object>();

        labelValueMap.put(DEFAULT_DROPDOWN_VALUE, DEFAULT_DROPDOWN_VALUE);
        labelValueMap
                .put(Messages.AdvancedPropertiesFaultConfiguration_HaltDropDownValue,
                        SystemErrorActionType.HALT);
        labelValueMap
                .put(Messages.AdvancedPropertiesFaultConfiguration_ErrorDropDownValue,
                        SystemErrorActionType.ERROR);

        labels =
                labelValueMap.keySet()
                        .toArray(new String[labelValueMap.size()]);
        values =
                labelValueMap.values()
                        .toArray(new Object[labelValueMap.size()]);
    }

    /**
     * 
     */
    public AdvancedPropertiesFaultConfiguration() {
        super();
        this.commandLabel =
                Messages.AdvancedPropertiesFaultConfiguration_SystemErrorAction;
        this.feature =
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_SystemErrorAction();
    }

    private String commandLabel;

    private EStructuralFeature feature;

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getPropertyModelFeature()
     * 
     * @return
     */
    @Override
    protected EStructuralFeature getPropertyModelFeature() {
        return feature;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getSetPropertyCommandLabel()
     * 
     * @return
     */
    @Override
    protected String getSetPropertyCommandLabel() {
        return commandLabel;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getPropertyModelContainer(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    protected EObject getPropertyModelContainer(EObject input) {
        return (input instanceof Process) ? input : null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getPropertyContainerCleanupCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject)
     * 
     * @param editingDomain
     * @param input
     * @return
     */
    @Override
    protected Command getPropertyContainerCleanupCommand(
            EditingDomain editingDomain, EObject input) {

        return null;
    }

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
                labels, values);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public boolean isApplicable(EObject input) {
        return (input instanceof Process);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getDefaultValue(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public Object getDefaultValue(EObject input) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getOrCreatePropertyContainer(org.eclipse.emf.ecore.EObject,
     *      org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param input
     * @param editingDomain
     * @param cmd
     * @return
     */
    @Override
    protected EObject getOrCreatePropertyContainer(EObject input,
            EditingDomain editingDomain, CompoundCommand cmd) {

        return getPropertyModelContainer(input);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
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

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(getSetPropertyCommandLabel());

        OtherAttributesContainer process =
                (OtherAttributesContainer) getPropertyModelContainer(input);

        if (isValueEligibleForUnsetting(value)) {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            process,
                            getPropertyModelFeature(),
                            null));
        } else {
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            process,
                            getPropertyModelFeature(),
                            value));
        }

        return cmd;

    }

    /**
     * @param value
     * @return
     */
    protected boolean isValueEligibleForUnsetting(Object value) {
        return (value == null) || (value.equals(DEFAULT_DROPDOWN_VALUE));
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getValue(org.eclipse.emf.ecore.EObject,
     *      int)
     * 
     * @param input
     * @param sequenceIndex
     * @return
     */
    @Override
    public Object getValue(EObject input, int sequenceIndex) {

        Object value = super.getValue(input, sequenceIndex);
        if (value == null) {
            return DEFAULT_DROPDOWN_VALUE;
        }

        return value;
    }

}
