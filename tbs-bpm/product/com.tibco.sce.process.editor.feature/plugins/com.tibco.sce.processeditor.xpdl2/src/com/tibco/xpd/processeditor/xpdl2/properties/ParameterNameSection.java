/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.edit.util.ControlUtils;

/**
 * @author NWilson
 * 
 */
public class ParameterNameSection extends ProcessRelevantDataNameSection {

    private boolean implementedType = false;

    public boolean select(Object toTest) {
        boolean select = super.select(toTest);
        if (!(toTest instanceof FormalParameter)) {
            select = false;
        }
        return select;
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        setImplementedType(false);
        if (part instanceof CommonNavigator) {
            TreeItem[] selectionList =
                    ((CommonNavigator) part).getCommonViewer().getTree()
                            .getSelection();
            for (TreeItem item : selectionList)
                if (item.getParentItem().getParentItem().getData() instanceof com.tibco.xpd.xpdl2.Process
                        && ((EObject) item.getData()).eContainer() instanceof ProcessInterface)
                    setImplementedType(true);
        }
    }

    @Override
    protected void doRefresh() {
        if (!isImplementedType()) {
            nameLabel.setText(Messages.ParameterPropertySection_name_label);
            ((GridData) nameLabel.getLayoutData()).widthHint = 60;
            ControlUtils.forceWidgetVisible(nameLabel, nameLabel.getText());
            nameLabel.getParent().layout();
            name.setEnabled(true);
            name.setEditable(true);
            display.setEnabled(true);
            display.setEditable(true);
            nameLabel.getParent().redraw();
        } else {
            nameLabel
                    .setText(Messages.ParameterPropertySection_InterfaceParam_label);
            ControlUtils.forceWidgetVisible(nameLabel, nameLabel.getText());
            nameLabel.getParent().layout();
            ((GridData) nameLabel.getLayoutData()).widthHint = 110;
            name.setEnabled(false);
            name.setEditable(false);
            display.setEnabled(false);
            display.setEditable(false);
            nameLabel.getParent().redraw();
        }
        super.doRefresh();
    }

    public boolean isImplementedType() {
        return implementedType;
    }

    public void setImplementedType(boolean implementedType) {
        this.implementedType = implementedType;
    }

}
