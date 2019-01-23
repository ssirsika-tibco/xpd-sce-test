/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ConvertFieldToParameterCommand
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Nov 2009)
 */
public class ConvertFieldToParameterCommand extends AbstractCommand {

    CompoundCommand delegateCmd;

    public ConvertFieldToParameterCommand(EditingDomain editingDomain,
            DataField field, FormalParametersContainer formalParametersContainer) {
        super();

        FormalParameter parameter = createParameter(editingDomain, field);

        delegateCmd =
                new CompoundCommand(
                        Messages.ConvertFieldToParameterCommand_ConvertFieldToParameter_menu);

        delegateCmd.append(RemoveCommand.create(editingDomain, field));
        delegateCmd.append(AddCommand.create(editingDomain,
                formalParametersContainer,
                Xpdl2Package.eINSTANCE
                        .getFormalParametersContainer_FormalParameters(),
                parameter));

    }

    private FormalParameter createParameter(EditingDomain editingDomain,
            DataField dataField) {
        // To be on the safest side possible, create a deep copy of the data
        // field. Then when we set Parameter elements from dataField elements we
        // are borrowing from a COPY of the original NOT the originals (so that
        // we don't AT ANY TIME have 2 things referencing the same children.
        Command cc = CopyCommand.create(editingDomain, dataField);
        cc.execute();
        DataField copyOfDataField =
                (DataField) cc.getResult().iterator().next();

        FormalParameter parameter =
                Xpdl2Factory.eINSTANCE.createFormalParameter();

        parameter.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                copyOfDataField.getId());

        if (copyOfDataField.getName() != null) {
            parameter.setName(copyOfDataField.getName());
        }

        EAttribute ea =
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName();
        if (ea != null) {
            Xpdl2ModelUtil.setOtherAttribute(parameter,
                    ea,
                    Xpdl2ModelUtil.getOtherAttribute(copyOfDataField, ea));
        }

        if (copyOfDataField.isSetReadOnly()) {
            parameter.setReadOnly(copyOfDataField.isReadOnly());
        }

        if (copyOfDataField.getDataType() != null) {
            parameter.setDataType(copyOfDataField.getDataType());
        }

        if (copyOfDataField.getDescription() != null) {
            parameter.setDescription(copyOfDataField.getDescription());
        }

        if (copyOfDataField.getLength() != null) {
            parameter.setLength(copyOfDataField.getLength());
        }
        // MR 40703 - begin
        if (copyOfDataField.isIsArray()) {
            parameter.setIsArray(copyOfDataField.isIsArray());
        }
        // MR 40703 - end
        parameter.setRequired(Boolean.FALSE);
        parameter.setMode(ModeType.INOUT_LITERAL);

        return parameter;
    }

    @Override
    public void execute() {
        delegateCmd.execute();

    }

    @Override
    public void redo() {
        delegateCmd.redo();
    }

    @Override
    public void undo() {
        delegateCmd.undo();
    }

    @Override
    public boolean canExecute() {
        return delegateCmd.canExecute();
    }

}
