/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.SWT;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AbstractInitColumn
 * 
 * 
 * @author bharge
 * @since 3.3 (27 Nov 2009)
 */
public abstract class AbstractInitColumn extends AbstractColumn {
    private EditingDomain editingDomain;

    /**
     * @param editingDomain
     * @param viewer
     * @param style
     * @param heading
     * @param width
     */
    public AbstractInitColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String columnLabel) {
        super(editingDomain, viewer, SWT.NONE, columnLabel, 350);
        this.editingDomain = editingDomain;
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    private int getItemIndex(String modelValue, ProcessRelevantData data) {
        List<String> values = null;
        InitialValues oldInitialValues =
                (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValues());
        if (oldInitialValues != null) {
            values = oldInitialValues.getValue();
            int i = 0;
            for (Iterator<String> iterator = values.iterator(); iterator
                    .hasNext();) {
                String string = (String) iterator.next();
                if (string.equals(modelValue)) {
                    return i;
                }
                i++;
            }
        }

        return -1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
     * (java.lang.Object, java.lang.Object)
     */
    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        CompoundCommand cmd = null;
        if (value instanceof String) {
            String newValue = (String) value;
            ProcessRelevantData data = (ProcessRelevantData) getInput();

            String modelValue =
                    ProcessDataUtil.convertUIInitialValueToModelFormat(data,
                            (String) element);

            InitialValues initialValues =
                    (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            newValue =
                    ProcessDataUtil.convertUIInitialValueToModelFormat(data,
                            newValue);

            int itemIndex = getItemIndex(modelValue, data);
            if (itemIndex >= 0) {
                cmd = new CompoundCommand();
                cmd.append(SetCommand.create(editingDomain,
                        initialValues,
                        XpdExtensionPackage.eINSTANCE.getInitialValues_Value(),
                        newValue,
                        itemIndex));
                /*
                 * XPD-5368: Saket- Ensuring that the implementations of
                 * AbstractColumn.getSetValueCommand() return the command and do
                 * not execute it.
                 */
            }
        }
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang
     * .Object)
     */
    @Override
    protected String getText(Object element) {
        String text = null;
        if (element instanceof String) {
            text = (String) element;
        }
        return text != null ? text : ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
     * (java.lang.Object)
     */
    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

}
