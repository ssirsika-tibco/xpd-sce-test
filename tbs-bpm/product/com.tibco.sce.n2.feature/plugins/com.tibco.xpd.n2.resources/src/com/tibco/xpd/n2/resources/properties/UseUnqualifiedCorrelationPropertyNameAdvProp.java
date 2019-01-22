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
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * In Studio v3.5.10 the JIRA BX-2404 rectified a known problem with differing
 * mapping usage of same-named correlation fields in different processes. This
 * was done by prefixing the PropertyName (correlation field name) for the
 * property alias derived from each correlation mapping with the process name.
 * Unfortunately this means that the WSDL that is deployed will change if any
 * correlation mappings are made in the process that references it. This means
 * that application upgrade for existing projects will always fail if it
 * contains correlation mappings (which most do).
 * 
 * BX-2536 has been raised to make this behaviour configurable and studio needs
 * to change to provide this configuration.
 * 
 * This will be done by adding a new (optional) extension attribute to a process
 * package xpdExt:BxUseUnqualifiedPropertyNames. If present and true then
 * xpdl2bpel will generate property names _exactly the same as it did prior to
 * version Studio 3.5.10 (iu.e. just the correlation data field name I believe).
 * IF absent or false then xpdl2bpel will use the new behaviour of generating
 * property names prefixed with the process name.
 * 
 * @author aallway
 * @since 28 Nov 2012
 */
public class UseUnqualifiedCorrelationPropertyNameAdvProp extends
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
        if (input instanceof Package) {
            return true;
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
        if (input instanceof Package) {
            return Xpdl2ModelUtil.getOtherAttribute((Package) input,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_BxUseUnqualifiedPropertyNames());
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

        if (input instanceof Package
                && (value == null || value instanceof Boolean)) {
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.UseUnqualifiedCorrelationPropertyNameAdvProp_SetUnsetProperty_menu);

            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            (Package) input,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BxUseUnqualifiedPropertyNames(),
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
        if (input instanceof Package) {
            return Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                    (Package) input,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_BxUseUnqualifiedPropertyNames(),
                    null);
        }
        return null;
    }

}
