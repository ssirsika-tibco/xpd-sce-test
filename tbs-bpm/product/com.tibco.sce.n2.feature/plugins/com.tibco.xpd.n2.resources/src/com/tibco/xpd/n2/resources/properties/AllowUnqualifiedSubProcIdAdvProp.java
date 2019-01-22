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
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Overview: <li>In iProcess the dynamic sub-process feature is very similar to
 * the AMX BPM implementation. One difference is that currently in AMX BPM
 * dynamic sub-processes must be fully qualified as
 * project/packagename/name.xpdl.processname unless they are in same XPDL
 * package. However, on an iProcess node all processes must be named uniquely
 * and therefore _dynamic sub-process identification is based upon process name
 * only.</li> <li>
 * This will be difficult to deal with for iProcess to AMX BPM migration use
 * cases as it will not be feasible to alter the scripted dynamic sub-process
 * runtime identifier array assignment to use full qualification of process
 * names.</li> <li>Therefore we an enhancement to AMX BPM design time(XPD-5932)
 * and run time(BX-3176) to allow unqualified dynamic sub-process identification
 * even when the sub-process is not located in the same XPDL package (and
 * corresponding runtime module)...</li>
 * <p>
 * Changes in Studio(XPD-5932): If a sub-process is dynamic(i.e. process
 * interface selected), then the 'Resource Tab' of the sub-process will have
 * additional boolean property "Allow Unqualified Sub-Process Identification"(by
 * default set to <code>false</code> ) which when set to <code>true</code>
 * indicates the runtime that the sub-process names must be treated as
 * unqualified else if the property is
 * <code>false</false> then the names are treated as qualified.
 * 
 * @author kthombar
 * @since 03-Mar-2014
 */
public class AllowUnqualifiedSubProcIdAdvProp extends
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
     * returns <code>true</code> if the input is a {@link SubFlow} and
     * references a {@link ProcessInterface} else <code>false</code>.
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public boolean isApplicable(EObject input) {
        if (input instanceof Activity) {
            SubFlow subFlow = getSubFlow(input);

            if (subFlow != null) {
                EObject subProcess =
                        TaskObjectUtil
                                .getSubProcessOrInterface((Activity) input);

                if (subProcess instanceof ProcessInterface) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks if the input is a {@link SubFlow} and returns it, else returns
     * <code>null</null>
     * 
     * @param input
     * @return SubFlow or null if not a sub-process task.
     */
    private SubFlow getSubFlow(EObject input) {
        if (input instanceof Activity) {
            Activity activity = (Activity) input;

            if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {

                Implementation implementation = activity.getImplementation();
                if (implementation instanceof SubFlow) {

                    return (SubFlow) implementation;
                }
            }
        }
        return null;
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
     * @return <code>true</code> if the user has chosen to use unqualified
     *         dynamic sub-process names, else returns <code>false</code> if the
     *         user has chosen qualified names
     */
    @Override
    public Object getValue(EObject input, int sequenceIndex) {
        SubFlow subFlow = getSubFlow(input);
        if (subFlow != null) {
            /* return unqualified identification value */
            return Xpdl2ModelUtil
                    .getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AllowUnqualifiedSubProcessIdentification());

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
        /* default value is false. */
        return false;
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

        SubFlow subFlow = getSubFlow(input);

        if (subFlow != null && value instanceof Boolean) {

            CompoundCommand ccmd =
                    new CompoundCommand(
                            Messages.AllowUnqualifiedSubProcIdAdvProp_SetUnsetUnqualifiedNamesCommand_label);
            ccmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AllowUnqualifiedSubProcessIdentification(),
                            value));
            return ccmd;
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
        SubFlow subFlow = getSubFlow(input);

        if (subFlow != null) {

            return Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AllowUnqualifiedSubProcessIdentification(),
                            null);
        }
        return null;
    }
}
