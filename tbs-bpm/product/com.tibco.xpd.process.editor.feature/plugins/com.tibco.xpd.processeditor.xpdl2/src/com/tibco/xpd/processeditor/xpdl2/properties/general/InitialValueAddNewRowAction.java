/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredViewer;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

class InitialValueAddNewRowAction extends TableAddAction {

    private EditingDomain editingDomain;

    /**
     * @param viewer
     * @param label
     * @param tooltip
     */
    public InitialValueAddNewRowAction(StructuredViewer viewer, String label,
            String tooltip, EditingDomain editingDomain) {
        super(viewer, label, tooltip) ; 
        this.editingDomain = editingDomain;
    }

    /* (non-Javadoc)
     * @see com.tibco.xpd.resources.ui.components.actions.TableAddAction#addRow(org.eclipse.jface.viewers.StructuredViewer)
     */
    @Override
    protected Object addRow(StructuredViewer viewer) {
        Command cmd = null;
        String firstCellVal = getNewRowFirstCellVal();
        cmd = getAddValueCommand(editingDomain, firstCellVal);

        if (cmd.canExecute()) {
            editingDomain.getCommandStack().execute(cmd);
            Collection<?> affectedObjects = cmd.getAffectedObjects();
            for (Object obj : affectedObjects)
                if (obj instanceof String)
                    return obj;
        }

        return firstCellVal;
    }

    protected String getNewRowFirstCellVal() {
        ProcessRelevantData data = (ProcessRelevantData) getViewer().getInput();

        String value =
                Messages.ParameterPropertySection_DefaultInitialValue_value;

        BasicType basicType = ProcessDataUtil.getModelBasicType(data);
        if (basicType != null
                && basicType.getType().equals(BasicTypeType.DATETIME_LITERAL)) {
            value = ProcessDataUtil.localisedDateTimeFormat.format(new Date());
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.DATE_LITERAL)) {
            value = ProcessDataUtil.localisedDateFormat.format(new Date());
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.TIME_LITERAL)) {
            value = ProcessDataUtil.localisedTimeFormat.format(new Date());
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.BOOLEAN_LITERAL)) {
            value = ProcessDataUtil.UI_BOOLEAN_FALSE;
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.FLOAT_LITERAL)) {
            value = ProcessDataUtil.UI_DECIMAL_VALUE;
        } else if (basicType != null
                && basicType.getType().equals(BasicTypeType.INTEGER_LITERAL)) {
            value = ProcessDataUtil.UI_INTEGER_VALUE;
        }
        return value;
    }

    private Command getAddValueCommand(EditingDomain ed, String newValue) {

        CompoundCommand cmd = new CompoundCommand();

        ProcessRelevantData data = (ProcessRelevantData) getViewer().getInput();

        newValue =
                ProcessDataUtil.convertUIInitialValueToModelFormat(data,
                        newValue);

        if (newValue != null) {

            InitialValues initialValues =
                    (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            if (initialValues == null) {
                initialValues =
                        XpdExtensionFactory.eINSTANCE.createInitialValues();
                cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        data,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValues(),
                        initialValues));
            }
            Command createCmd =
                    AddCommand.create(ed,
                            initialValues,
                            XpdExtensionPackage.eINSTANCE
                                    .getInitialValues_Value(),
                            newValue);
            cmd.append(createCmd);

        }

        return cmd;
    }

}