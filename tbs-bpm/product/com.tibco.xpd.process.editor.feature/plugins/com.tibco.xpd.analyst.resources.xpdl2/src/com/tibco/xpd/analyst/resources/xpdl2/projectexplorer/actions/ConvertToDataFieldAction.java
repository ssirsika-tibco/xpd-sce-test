/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.actions.SelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Converts parameters / correlation data fields into standard data fields.
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
public class ConvertToDataFieldAction extends SelectionListenerAction {

    public ConvertToDataFieldAction(String text) {
        super(text);
        setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.ICON_PARAMTOFIELD));
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
                CompoundCommand cmd = new CompoundCommand(
                        Messages.ConvertToDataFieldAction_FromCorrelationDataActionCommand);
                cmd.append(SetCommand.create(ed,
                        field,
                        Xpdl2Package.eINSTANCE.getDataField_Correlation(),
                        Boolean.FALSE));

                // Remove correlation associations.
                Process process = Xpdl2ModelUtil.getProcess(field);
                for (Activity activity : Xpdl2ModelUtil
                        .getAllActivitiesInProc(process)) {
                    if (Xpdl2ModelUtil.canHaveCorrelationAssociated(activity)) {
                        Object other = Xpdl2ModelUtil.getOtherElement(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedCorrelationFields());
                        if (other instanceof AssociatedCorrelationFields) {
                            AssociatedCorrelationFields fieldContainer =
                                    (AssociatedCorrelationFields) other;
                            List<AssociatedCorrelationField> fieldList =
                                    fieldContainer
                                            .getAssociatedCorrelationField();
                            for (AssociatedCorrelationField correlation : fieldList) {
                                if (field.getName()
                                        .equals(correlation.getName())) {
                                    cmd.append(RemoveCommand.create(ed,
                                            correlation));
                                }
                            }
                        }
                    }
                }
                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }
    }

    private void replaceDataFieldWithParameter(FormalParameter parameter) {
        EditingDomain ed =
                WorkingCopyUtil.getEditingDomain(parameter.eContainer());
        org.eclipse.emf.common.command.Command cmd =
                new ParameterToDataFieldCommand(ed, parameter);
        if (cmd.canExecute()) {
            ed.getCommandStack().execute(cmd);

        } else {
            System.err.println("ConvertToDataFieldAction: can't execute"); //$NON-NLS-1$
        }
        cmd.dispose();

    }

}
