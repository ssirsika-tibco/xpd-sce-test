/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.actions.SelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ConvertFieldToParameterCommand;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Converts data fields into formal parameters.
 * 
 * <p>
 * Sid XPD-8381: Converted to extend SelectionListenerAction not WorkspaceAction
 * (the latter has certain requirements of a selection, like it must be able to
 * relate to a resource properly etc which
 * org.eclipse.bpel.validator.factory.AdapterFactory spoils because it fails to
 * do so). Anyway, it's not a workspace resource change so should not have been
 * a WorkspaceAction in the first place.
 *
 * @author rsomayaj
 */
public class ConvertToParameterAction extends SelectionListenerAction {

    public ConvertToParameterAction(String text) {
        super(text);
        setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.ICON_FIELDTOPARAM));
    }

    @Override
    public void run() {
        Iterator iterator = getStructuredSelection().iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof DataField) {
                DataField dataField = (DataField) obj;
                replaceDataFieldWithParameter(dataField);
            }
        }
    }

    private void replaceDataFieldWithParameter(DataField dataField) {
        //
        // First Prepare a new formal parameter from the data field.
        //
        EditingDomain ed =
                WorkingCopyUtil.getEditingDomain(dataField.eContainer());

        Command cmd = new ConvertFieldToParameterCommand(ed, dataField,
                Xpdl2ModelUtil.getProcess(dataField));

        if (cmd.canExecute()) {
            ed.getCommandStack().execute(cmd);
        } else {
            System.err.println("ConvertToParameterAction: can't execute"); //$NON-NLS-1$
        }

    }

}
