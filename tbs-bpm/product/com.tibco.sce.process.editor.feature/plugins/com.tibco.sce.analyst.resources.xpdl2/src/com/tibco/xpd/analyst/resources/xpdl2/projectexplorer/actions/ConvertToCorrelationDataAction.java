/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.ui.actions.SelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Converts parameters / data fields into correlation data fields.
 * 
 * <p>
 * Sid XPD-8381: Converted to extend SelectionListenerAction not WorkspaceAction
 * (the latter has certain requirements of a selection, like it must be able to
 * relate to a resource properly etc which
 * org.eclipse.bpel.validator.factory.AdapterFactory spoils because it fails to
 * do so). Anyway, it's not a workspace resource change so should not have been
 * a WorkspaceAction in the first place.
 * 
 * @author nwilson
 */
public class ConvertToCorrelationDataAction extends SelectionListenerAction {

    public ConvertToCorrelationDataAction(IShellProvider shellProvider,
            String text) {
        super(text);
        setImageDescriptor(Xpdl2ResourcesPlugin.getImageDescriptor(
                Xpdl2ResourcesConsts.ICON_DATATOCORRELATION));
    }

    @Override
    public void run() {
        Iterator<?> iterator = getStructuredSelection().iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof FormalParameter) {
                FormalParameter formalParameter = (FormalParameter) obj;
                replaceDataFieldWithParameter(formalParameter);
            } else if (obj instanceof DataField) {
                DataField field = (DataField) obj;

                EditingDomain ed = WorkingCopyUtil.getEditingDomain(field);
                // MR 39264 - begin
                CompoundCommand cCmd = new CompoundCommand(
                        Messages.ConvertToCorrelationDataAction_DataFieldToCorrelationDataCommand);
                cCmd.append(SetCommand.create(ed,
                        field,
                        Xpdl2Package.eINSTANCE
                                .getProcessRelevantData_ReadOnly(),
                        true));
                // MR 39264 - end
                cCmd.append(SetCommand.create(ed,
                        field,
                        Xpdl2Package.eINSTANCE.getDataField_Correlation(),
                        Boolean.TRUE));
                if (cCmd.canExecute()) {
                    ed.getCommandStack().execute(cCmd);
                }
            }
        }
    }

    private void replaceDataFieldWithParameter(FormalParameter parameter) {
        EditingDomain ed =
                WorkingCopyUtil.getEditingDomain(parameter.eContainer());
        org.eclipse.emf.common.command.Command cmd =
                new ParameterToCorrelationDataCommand(ed, parameter);
        if (cmd.canExecute()) {
            ed.getCommandStack().execute(cmd);

        } else {
            System.err.println("ConvertToDataFieldAction: can't execute"); //$NON-NLS-1$
        }
        cmd.dispose();

    }

    /**
     * Converts the Parameter Field to Correlation Data. Basically deletes the
     * formal parameter from the model and adds a Correlation data field to the
     * model
     * 
     * @author kthombar
     * @since 09-May-2013
     */
    class ParameterToCorrelationDataCommand
            extends ParameterToDataFieldCommand {

        public ParameterToCorrelationDataCommand(EditingDomain ed,
                FormalParameter parameter) {
            super(ed, parameter);

        }

        @Override
        public String getLabel() {
            return Messages.ConvertToCorrelationDataAction_ParameterToCorrelationDataCommand;
        }

        /**
         * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.ParameterToDataFieldCommand#createDataField(com.tibco.xpd.xpdl2.FormalParameter)
         * 
         * @param parameter
         * @return
         */
        @Override
        protected DataField createDataField(FormalParameter parameter) {
            DataField dataField = super.createDataField(parameter);
            dataField.setCorrelation(true);

            return dataField;
        }

    }

}
