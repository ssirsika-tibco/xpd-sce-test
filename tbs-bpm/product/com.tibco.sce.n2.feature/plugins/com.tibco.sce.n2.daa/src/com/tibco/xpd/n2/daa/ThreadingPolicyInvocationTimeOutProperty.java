/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.daa;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.IntegerPropertyDescriptor;
import com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class that sets invocation timeout for all incoming request activities of all
 * business processes in a process package
 * 
 * @author bharge
 * @since 22 Jul 2014
 */
public class ThreadingPolicyInvocationTimeOutProperty extends
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

        IntegerPropertyDescriptor descriptor =
                new IntegerPropertyDescriptor(descriptorId, displayName);
        descriptor.setLabelProvider(new LabelProvider() {

            @Override
            public String getText(Object element) {

                String strValue =
                        Messages.BpmRuntimeConfig_ThreadingPolicy_InvocationTimeout_default_value;

                if ((element != null) && (element instanceof Integer)) {

                    strValue = Integer.toString((Integer) element);
                }

                return strValue;
            }
        });

        return descriptor;
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

            BpmRuntimeConfiguration bpmRuntimeConfig =
                    (BpmRuntimeConfiguration) Xpdl2ModelUtil
                            .getOtherElement((OtherElementsContainer) input,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_BpmRuntimeConfiguration());
            if (null != bpmRuntimeConfig) {

                Integer incomingRequestTimeout =
                        bpmRuntimeConfig.getIncomingRequestTimeout();

                return incomingRequestTimeout;
            }
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

        if (value instanceof Integer && 0 == ((Integer) value).intValue()) {
            /* 0 == No Timeout - which we represent as Unset in model. */
            return getRemoveValueCommand(editingDomain, input);
        }

        if (input instanceof Package) {

            CompoundCommand cmd = new CompoundCommand();
            Package procPkg = (Package) input;

            BpmRuntimeConfiguration bpmRuntimeConfiguration =
                    (BpmRuntimeConfiguration) Xpdl2ModelUtil
                            .getOtherElement(procPkg,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_BpmRuntimeConfiguration());
            if (null == bpmRuntimeConfiguration) {

                bpmRuntimeConfiguration =
                        XpdExtensionFactory.eINSTANCE
                                .createBpmRuntimeConfiguration();
                cmd.append(Xpdl2ModelUtil
                        .getAddOtherElementCommand(editingDomain,
                                procPkg,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_BpmRuntimeConfiguration(),
                                bpmRuntimeConfiguration));
            }

            cmd.append(SetCommand
                    .create(editingDomain,
                            bpmRuntimeConfiguration,
                            XpdExtensionPackage.eINSTANCE
                                    .getBpmRuntimeConfiguration_IncomingRequestTimeout(),
                            value));

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

        if (input instanceof Package) {

            BpmRuntimeConfiguration bpmRuntimeConfig =
                    (BpmRuntimeConfiguration) Xpdl2ModelUtil
                            .getOtherElement((OtherElementsContainer) input,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_BpmRuntimeConfiguration());
            if (null != bpmRuntimeConfig) {

                Integer incomingRequestTimeout =
                        bpmRuntimeConfig.getIncomingRequestTimeout();

                if (null != incomingRequestTimeout) {

                    return true;
                }
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

        if (input instanceof Package) {

            CompoundCommand cmd = new CompoundCommand();
            Package procPkg = (Package) input;
            BpmRuntimeConfiguration bpmRuntimeConfig =
                    (BpmRuntimeConfiguration) Xpdl2ModelUtil
                            .getOtherElement(procPkg,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_BpmRuntimeConfiguration());
            if (null != bpmRuntimeConfig) {

                Integer incomingRequestTimeout =
                        bpmRuntimeConfig.getIncomingRequestTimeout();

                if (null != incomingRequestTimeout) {

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    bpmRuntimeConfig,
                                    XpdExtensionPackage.eINSTANCE
                                            .getBpmRuntimeConfiguration_IncomingRequestTimeout(),
                                    SetCommand.UNSET_VALUE));
                    return cmd;
                }
            }
        }
        return null;
    }

}
