/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class converts a parameter to a data field. Basically deletes the formal
 * parameter from the model and adds a new data field to the model
 * 
 * @author NWilson
 */
public class ParameterToDataFieldCommand extends CompoundCommand {
    private FormalParameter parameter;

    private Process process;

    private EditingDomain editingDomain;

    public ParameterToDataFieldCommand() {
        super();
    }

    public ParameterToDataFieldCommand(EditingDomain editingDomain,
            FormalParameter parameter) {
        this();
        this.editingDomain = editingDomain;
        this.parameter = parameter;
    }

    /**
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     * 
     * @return
     */
    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        // Create a data field from formal parameter
        DataField dataField2 = createDataField(parameter);

        process = (Process) parameter.eContainer();

        // Remove formal parameter from process.
        this.appendAndExecute(RemoveCommand.create(editingDomain, parameter));

        // Add data field to process.
        this.appendAndExecute(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getDataFieldsContainer_DataFields(),
                dataField2));

    }

    @Override
    public String getLabel() {
        return Messages.ConvertToDataFieldAction_ParameterToDataFieldCmd_label;
    }

    /**
     * 
     */
    protected DataField createDataField(FormalParameter parameter) {
        DataField dataField = Xpdl2Factory.eINSTANCE.createDataField();
        dataField.setName(parameter.getName());
        EAttribute ea =
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName();
        Xpdl2ModelUtil.setOtherAttribute(dataField,
                ea,
                Xpdl2ModelUtil.getOtherAttribute(parameter, ea));

        dataField.setDataType((DataType) myCopy(parameter.getDataType()));
        dataField.setReadOnly(parameter.isReadOnly());
        // Description might be null
        if (parameter.getDescription() != null) {
            dataField.setDescription((Description) myCopy(parameter
                    .getDescription()));
        }
        // Length might be null
        if (parameter.getLength() != null) {
            dataField.setLength(parameter.getLength());
        }
        dataField.setIsArray(parameter.isIsArray());
        dataField.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                parameter.getId());
        return dataField;
    }

    private EObject myCopy(EObject eo) {
        if (eo != null) {
            return EcoreUtil.copy(eo);
        }
        return null;
    }

}
