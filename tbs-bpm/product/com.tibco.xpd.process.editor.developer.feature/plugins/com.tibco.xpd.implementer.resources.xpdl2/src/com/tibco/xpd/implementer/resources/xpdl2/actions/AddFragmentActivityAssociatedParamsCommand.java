/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Late execute command class to add the required associated parameters for
 * Identifier task in the generated business service
 * 
 * @author bharge
 * @since 13 Aug 2014
 */
public class AddFragmentActivityAssociatedParamsCommand extends
        AbstractLateExecuteCommand {

    Process process;

    /**
     * @param editingDomain
     * @param contextObject
     * @param abstractCaseProcessCreator
     * 
     */
    public AddFragmentActivityAssociatedParamsCommand(
            EditingDomain editingDomain, Process process) {

        super(editingDomain, process);
        this.process = process;
    }

    /**
     * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object)
     * 
     * @param editingDomain
     * @param contextObject
     * @return
     */
    @Override
    protected Command createCommand(EditingDomain editingDomain,
            Object contextObject) {

        if (null != process) {

            CompoundCommand cmd = new CompoundCommand();

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : allActivitiesInProc) {

                if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))) {

                    EList<ExtendedAttribute> extendedAttributes =
                            activity.getExtendedAttributes();
                    if (!extendedAttributes.isEmpty()) {

                        ExtendedAttribute extendedAttribute =
                                extendedAttributes.get(0);
                        if ("IdentifierTask".equalsIgnoreCase(extendedAttribute //$NON-NLS-1$
                                .getValue())) {

                            AssociatedParameters associatedParameters =
                                    XpdExtensionFactory.eINSTANCE
                                            .createAssociatedParameters();

                            for (DataField dataField : process.getDataFields()) {

                                EList<ExtendedAttribute> dfExtAttributes =
                                        dataField.getExtendedAttributes();
                                if (!dfExtAttributes.isEmpty()) {

                                    ExtendedAttribute dfExtAttribute =
                                            dfExtAttributes.get(0);
                                    if ("CaseIdField".equalsIgnoreCase(dfExtAttribute.getValue())) { //$NON-NLS-1$

                                        AssociatedParameter associatedParam =
                                                ProcessInterfaceUtil
                                                        .createAssociatedParam(dataField);
                                        associatedParam.setMandatory(true);

                                        associatedParameters
                                                .getAssociatedParameter()
                                                .add(associatedParam);
                                    }
                                }
                            }
                            if (!associatedParameters.getAssociatedParameter()
                                    .isEmpty()) {

                                cmd.append(Xpdl2ModelUtil
                                        .getSetOtherElementCommand(editingDomain,
                                                activity,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_AssociatedParameters(),
                                                associatedParameters));
                            }
                        }
                    }
                }
            }

            return cmd;
        }
        return null;
    }

}