/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.advancedproperties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.DropDownPropertyDescriptor;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.WsdlGeneration;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Advanced Property on the Process to determine the binding type that should be
 * used while generating the WSDL.
 * 
 * @author rsomayaj
 * @since 3.3 (6 May 2010)
 */
public class WsdlGenAdvProperty extends AbstractAdvancedModelFeatureProperty {

    private String[] labels =
            new String[] { "RPC Literal", "Document Literal" };

    private Object[] values =
            new Object[] { SoapBindingStyle.RPC_LITERAL,
                    SoapBindingStyle.DOCUMENT_LITERAL };

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
        EObject container = getPropertyModelContainer(input);

        if (input instanceof com.tibco.xpd.xpdl2.Package) {
            com.tibco.xpd.xpdl2.Package package1 =
                    (com.tibco.xpd.xpdl2.Package) input;

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(package1);
            if (processInterfaces != null
                    && processInterfaces.getProcessInterface().size() == 1) {
                input = processInterfaces.getProcessInterface().get(0);
            }

        }
        if (container == null && input instanceof ProcessInterface) {
            OtherElementsContainer procOrProcIfc =
                    (OtherElementsContainer) input;
            container = XpdExtensionFactory.eINSTANCE.createWsdlGeneration();
            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    procOrProcIfc,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_WsdlGeneration(),
                    container));
        }
        return container;
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
        Command cmd = null;
        if (input instanceof ProcessInterface) {
            OtherElementsContainer procOrProcIfc =
                    (OtherElementsContainer) input;
            Object other =
                    Xpdl2ModelUtil.getOtherElement(procOrProcIfc,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_WsdlGeneration());
            if (other instanceof WsdlGeneration) {
                cmd =
                        Xpdl2ModelUtil
                                .getRemoveOtherElementCommand(editingDomain,
                                        procOrProcIfc,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_WsdlGeneration(),
                                        other);
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getPropertyModelContainer(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    protected EObject getPropertyModelContainer(EObject input) {
        EObject container = null;

        if (input instanceof com.tibco.xpd.xpdl2.Package) {
            com.tibco.xpd.xpdl2.Package package1 =
                    (com.tibco.xpd.xpdl2.Package) input;

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(package1);
            if (processInterfaces != null
                    && processInterfaces.getProcessInterface().size() == 1) {
                input = processInterfaces.getProcessInterface().get(0);
            }

        }
        if (input instanceof ProcessInterface) {
            OtherElementsContainer procOrProcIfc =
                    (OtherElementsContainer) input;
            Object other =
                    Xpdl2ModelUtil.getOtherElement(procOrProcIfc,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_WsdlGeneration());
            if (other instanceof WsdlGeneration) {
                container = (WsdlGeneration) other;
            }
        }

        return container;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getPropertyModelFeature()
     * 
     * @return
     */
    @Override
    protected EStructuralFeature getPropertyModelFeature() {
        return XpdExtensionPackage.eINSTANCE
                .getWsdlGeneration_SoapBindingStyle();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractAdvancedModelFeatureProperty#getSetPropertyCommandLabel()
     * 
     * @return
     */
    @Override
    protected String getSetPropertyCommandLabel() {
        return "Set WSDL Generation Binding Type";
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
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#getDefaultValue(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public Object getDefaultValue(EObject input) {
        Object defaultVal = SoapBindingStyle.RPC_LITERAL;
        return defaultVal;
    }

    /**
     * Applicable to Business processes only.
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public boolean isApplicable(EObject input) {
        if (input instanceof ProcessInterface) {
            if (ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessInterfaceDestinations((ProcessInterface) input)) {
                return true;
            }
        } else if (input instanceof com.tibco.xpd.xpdl2.Package) {
            com.tibco.xpd.xpdl2.Package pkg1 =
                    (com.tibco.xpd.xpdl2.Package) input;
            // Need to initialize this if when this called when there is only
            // one process.

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg1);
            // Or if there is only 1 process interface
            if (processInterfaces != null
                    && processInterfaces.getProcessInterface().size() == 1) {
                return true;
            }

        }
        return false;
    }

}
