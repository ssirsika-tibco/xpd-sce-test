/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.BooleanPropertyDescriptor;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * User task reschedule "Overwrite Modified Work Item Data" advanced property
 * contribution
 * <p>
 * <b>IMPORTANT NOTE: This advanced property sets the same model value as the
 * Task Boundary Catch Signal Event Map-from-signal property tab's
 * "Overwrite data already modified in work item."*
 * 
 * @author aallway
 * @since 4 Oct 2012
 */
public class UserTaskOverwriteModifiedDataAdvancedProperty extends
        AbstractContributedAdvancedProperty {

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
        return new BooleanPropertyDescriptor(descriptorId, displayName);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public boolean isApplicable(EObject input) {
        if (input instanceof Activity) {
            if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict((Activity) input))) {
                return true;
            }
        }
        return false;
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
        if (input instanceof Activity) {
            return Xpdl2ModelUtil
                    .getOtherAttribute((Activity) input,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OverwriteAlreadyModifiedTaskData());
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getDefaultValue(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public Object getDefaultValue(EObject input) {
        return Boolean.FALSE;
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

        if (input instanceof Activity
                && (value == null || value instanceof Boolean)) {
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.UserTaskOverwriteModifiedDataAdvancedProperty_SetOverwriteData_menu);

            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            (Activity) input,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OverwriteAlreadyModifiedTaskData(),
                            (Boolean) value));
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
        return getValue(input, 0) != null;
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
        if (input instanceof Activity) {
            return Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            (Activity) input,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OverwriteAlreadyModifiedTaskData(),
                            null);
        }
        return null;
    }

}
