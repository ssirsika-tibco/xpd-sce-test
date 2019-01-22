/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

public class FormalParameterGroup extends AbstractAssetGroup {

    private static final String TITLE = Messages.FormalParameterGroup_TITLE;

    public FormalParameterGroup(EObject parent) {
        super(parent, ProcessEditorElementType.formal_parameter);
    }

    @Override
    public EStructuralFeature getFeature() {
        return Xpdl2Package.eINSTANCE
                .getFormalParametersContainer_FormalParameters();
    }

    @Override
    public Image getImage() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.ICON_FORMALPARAMETER);
    }

    @Override
    public String getText() {
        return TITLE;
    }

    @Override
    public EClass getReferenceType() {
        return Xpdl2Package.eINSTANCE.getFormalParameter();
    }

    @Override
    public List getChildren() {
        if (getParent() instanceof com.tibco.xpd.xpdl2.Process) {
            return ProcessInterfaceUtil
                    .getAllFormalParameters((Process) getParent());
        } else if (getParent() instanceof ProcessInterface) {
            return ((ProcessInterface) getParent()).getFormalParameters();
        }
        return null;
    }
}
