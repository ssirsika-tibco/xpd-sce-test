/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.properties.description;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Description property section
 * 
 * @author njpatel
 * 
 */
public class DescriptionSection extends AbstractDescriptionSection {

    private boolean implementedParameter;

    /**
     * Description property section
     */
    public DescriptionSection() {
        super(Xpdl2Package.eINSTANCE.getDescribedElement());
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.description.AbstractDescriptionSection#getDescribedElement()
     * 
     * @return
     */
    @Override
    protected DescribedElement getDescribedElement() {
        // The description and the documentation url are populated from that of
        // the implemented event, and the textboxes are disabled for edit.
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {

                InterfaceMethod implementedMethod =
                        ProcessInterfaceUtil.getImplementedMethod(activity);

                if (implementedMethod != null) {
                    return implementedMethod;
                }
            }
        }

        if (getInput() instanceof DescribedElement) {
            return (DescribedElement) getInput();
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.description.AbstractDescriptionSection#getOrCreateDescribedElement(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param editingDomain
     * @param cmd
     * @return
     */
    @Override
    protected DescribedElement getOrCreateDescribedElement(
            EditingDomain editingDomain, CompoundCommand cmd) {
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {

                InterfaceMethod implementedMethod =
                        ProcessInterfaceUtil.getImplementedMethod(activity);

                if (implementedMethod != null) {
                    return implementedMethod;
                }
            }
        }

        if (getInput() instanceof DescribedElement) {
            return (DescribedElement) getInput();
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.description.AbstractDescriptionSection#canEdit()
     * 
     * @return
     */
    @Override
    protected boolean canEdit() {
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                return false;
            }
        }

        if (implementedParameter) {
            return false;
        }

        return true;
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        implementedParameter = false;
        if (selection != null
                && selection instanceof IStructuredSelection
                && ((IStructuredSelection) selection).getFirstElement() instanceof FormalParameter
                && part instanceof CommonNavigator) {
            TreeItem[] selectionList =
                    ((CommonNavigator) part).getCommonViewer().getTree()
                            .getSelection();
            for (TreeItem item : selectionList)
                if (item.getParentItem().getParentItem().getData() instanceof com.tibco.xpd.xpdl2.Process
                        && ((EObject) item.getData()).eContainer() instanceof ProcessInterface)
                    implementedParameter = true;
        }
    }

}
