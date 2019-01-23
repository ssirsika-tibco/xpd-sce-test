/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processinterface.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.event.ImplicitAssociatedParamObject;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Label provider for the associated parameters, provides color to table cells
 * to distinguish between editable and non-editable cells.
 * 
 * @author rsomayaj
 * 
 */
public class AssociatedParamLabelProvider implements ITableLabelProvider,
        ITableColorProvider {

    private Object input;

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        if (columnIndex == 0) {
            if (element instanceof AssociatedParameter) {
                ProcessRelevantData parameter =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam((AssociatedParameter) element);

                return parameter != null ? WorkingCopyUtil.getImage(parameter)
                        : Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2ResourcesConsts.ICON_FORMALPARAMETER);

            } else if (element instanceof ImplicitAssociatedParamObject) {
                return ((ImplicitAssociatedParamObject) element).getImage();
            }

        } else if (columnIndex == 3) {
            if (element instanceof AssociatedParameter) {
                AssociatedParameter param = (AssociatedParameter) element;

                if (param.isMandatory()) {
                    return CheckboxCellEditor.getImgChecked();

                } else {
                    return CheckboxCellEditor.getImgUnchecked();
                }

            }
        }

        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        if (element instanceof AssociatedParameter) {
            AssociatedParameter param = (AssociatedParameter) element;

            if (columnIndex == 0) {
                return WorkingCopyUtil.getText((EObject) element);
            } else if (columnIndex == 1) {
                ModeType paramMode = param.getMode();
                // ModeType paramMode = ProcessInterfaceUtil
                // .getAssocParamModeType(param);
                switch (paramMode.getValue()) {
                case ModeType.IN:
                    return Messages.AssociatedParamLabelProvider_InLabel;
                case ModeType.OUT:
                    return Messages.AssociatedParamLabelProvider_OutLabel;
                case ModeType.INOUT:
                    return Messages.AssociatedParamLabelProvider_InOutLabel;
                }
                return paramMode.toString();
            } else if (columnIndex == 2) {
                if (param.getDescription() != null) {
                    String val = param.getDescription().getValue();
                    if (val != null) {
                        String ret = null;
                        int idx = val.indexOf('\n');
                        if (idx != -1) {
                            // When showing label for multi-line value then
                            // append
                            // chevron to indicate that there is more.
                            ret = val.substring(0, idx - 1) + " >>"; //$NON-NLS-1$
                        } else {
                            ret = val;
                        }
                        return ret;
                    }
                }
            }

        } else if (element instanceof ImplicitAssociatedParamObject) {
            if (columnIndex == 0) {
                return ((ImplicitAssociatedParamObject) element).getParamName();
            }
        }

        return ""; //$NON-NLS-1$
    }

    @Override
    public void addListener(ILabelProviderListener listener) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {

    }

    @Override
    public Color getBackground(Object element, int columnIndex) {
        return Display.getDefault().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
    }

    @Override
    public Color getForeground(Object element, int columnIndex) {
        if (element instanceof AssociatedParameter) {
            AssociatedParameter associatedParameter =
                    (AssociatedParameter) element;
            if (associatedParameter.eContainer() instanceof AssociatedParametersContainer
                    && input instanceof Activity) {
                return Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
            }

        } else if (element instanceof ImplicitAssociatedParamObject) {
            return Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
        }
        return Display.getDefault().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
    }

    public void setInput(Object input) {
        this.input = input;
    }

}
