/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.n2.resources.refactoring.WSMappingBOMElementRefactorHelper.DataMappingChange;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.refactoring.PreviewDescription;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Change for the WebService Data Mappings Refactoring process
 * @author mtorres
 * 
 */
public class WSMappingBOMReferenceChange extends Change {

    private RenameArguments args = null;

    private EObject element;

    private DataMapping dataMapping;

    private EditingDomain editingDomain;
    
    private DataMappingChange newDataMapping;

    public WSMappingBOMReferenceChange(RenameArguments args,
            EObject element, DataMapping dataMapping,
            EditingDomain editingDomain) {
        this.args = args;
        this.element = element;
        this.dataMapping = dataMapping;
        this.editingDomain = editingDomain;
        this.newDataMapping = performDataMappingModifications();
    }

    @Override
    public Object getModifiedElement() {
        return element;
    }

    @Override
    public String getName() {
        String message = Messages.WSMappingBOMReferenceChange_DataMapping;
        String dmName = dataMapping.getFormal();
        if (dmName == null) {
            dmName = "";//$NON-NLS-1$
        }
        return String.format(message, dmName);
    }

    @Override
    public void initializeValidationData(IProgressMonitor pm) {

    }

    @Override
    public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        Command command = getRefactorCommand();
        RefactoringStatus status = new RefactoringStatus();
        final WorkingCopy wc = getWorkingCopy();
        RefactoringStatusContext context = new RefactoringStatusContext() {
            @Override
            public Object getCorrespondingElement() {
                return wc;
            }
        };

        pm.beginTask(String.format(Messages.BOMReferenceChange_ValidatingChange, getName()), 2);

        if (!wc.isExist()) {
            status.addError(String.format(Messages.BOMReferenceChange_ResourceDoesnotExist, wc
                    .getName()), context);
        } else if (wc.isInvalidFile()) {
            status
                    .addWarning(String
                            .format(Messages.BOMReferenceChange_CannotUpdate,
                                    wc.getName()),
                            context);
        } else if (wc.isReadOnly()) {
            status.addWarning(String
                    .format(Messages.BOMReferenceChange_ReadOnly, wc
                            .getName()), context);
        }
        pm.worked(1);

        if (command == null) {
            status.addError(String.format(Messages.BOMReferenceChange_NoCommandFound,
                    getName()), context);
        } else if (!command.canExecute()) {
            status.addError(String
                    .format(Messages.BOMReferenceChange_NoCommandSet,
                            getName()), context);
        }
        pm.worked(1);

        return status;
    }
    
    private WorkingCopy getWorkingCopy() {
        return WorkingCopyUtil.getWorkingCopyFor(dataMapping);
    }
    
    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {
        if (isValid(pm).isOK()) {
            pm.beginTask(String.format(Messages.WSMappingBOMReferenceChange_UpdatingDataMapping, getName()), 1);
            Command cmd = getRefactorCommand();
            if (cmd != null && cmd.canExecute()) {
                WorkingCopy wc = getWorkingCopy();
                if (wc != null) {
                    boolean workingCopyDirty = wc.isWorkingCopyDirty();
                    editingDomain.getCommandStack().execute(cmd);
                    if (!workingCopyDirty) {
                        try {
                            wc.save();
                        } catch (IOException e) {
                            XpdResourcesPlugin.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }
            }
            pm.worked(1);
        }
        return null;
    }

    public boolean containsModifications() {        
        return true;
    }

    public PreviewDescription getCurrentValue() {
        return new PreviewDescription(Messages.BOMReferenceChange_CurrentValue, getCurrentValueDescription());
    }
    
    private String getCurrentValueDescription() {
        StringBuffer currentValueDescription = new StringBuffer();
        if (dataMapping != null) {
            String oldActual = null;
            String oldFormal = null;
            if (dataMapping != null) {
                oldFormal = dataMapping.getFormal();
                if (dataMapping.getActual() != null) {
                    oldActual = dataMapping.getActual().getText();
                }
            }
            if (oldActual != null) {
                currentValueDescription.append(oldActual);
            }
            if (oldFormal != null) {
                currentValueDescription.append("\n" + oldFormal); //$NON-NLS-1$
            }
        }
        return currentValueDescription.toString();
    }

    public PreviewDescription getRefactoredValue() {
        return new PreviewDescription(Messages.BOMReferenceChange_RefactoredValue, getRefactoredValueDescription());
    }
    
    private String getRefactoredValueDescription(){
        StringBuffer currentValueDescription = new StringBuffer();
        if (dataMapping != null) {
            String oldActual = null;
            String oldFormal = null;
            String newActual = this.newDataMapping.getActual();
            String newFormal = this.newDataMapping.getFormal();
            if (dataMapping != null) {
                oldFormal = dataMapping.getFormal();
                if (dataMapping.getActual() != null) {
                    oldActual = dataMapping.getActual().getText();
                }
            }
            if (newActual != null) {
                currentValueDescription.append(newActual);
            } else if (oldActual != null) {
                currentValueDescription.append(oldActual);
            }
            if (newFormal != null) {
                currentValueDescription.append("\n" + newFormal); //$NON-NLS-1$
            } else if (oldFormal != null) {
                currentValueDescription.append("\n" + oldFormal); //$NON-NLS-1$
            }
        }
        return currentValueDescription.toString();
    }

    private DataMappingChange performDataMappingModifications() {
        String newName = args.getNewName();
        if (newName != null) {
            return WSMappingBOMElementRefactorHelper
                    .performDataMappingRefactoring(newName, dataMapping, element);
        }
        return null;
    }

    private Command getRefactorCommand() {
        CompoundCommand cmd = new CompoundCommand();
        if (this.newDataMapping != null) {
            String oldActual = null;
            String oldFormal = null;
            String oldScriptGrammar = null;
            String newActual = this.newDataMapping.getActual();
            String newFormal = this.newDataMapping.getFormal();
            if (dataMapping != null) {
                oldFormal = dataMapping.getFormal();
                if (dataMapping.getActual() != null) {
                    oldActual = dataMapping.getActual().getText();
                    oldScriptGrammar = dataMapping.getActual().getScriptGrammar();
                }
            }
            if (oldActual != null && newActual != null
                    && oldScriptGrammar != null && !oldActual.equals(newActual)) {
                // Perform actual change
                Expression newActualExpression =
                        Xpdl2Factory.eINSTANCE.createExpression();
                newActualExpression.getMixed().add(XMLTypePackage.eINSTANCE
                        .getXMLTypeDocumentRoot_Text(),
                        newActual);
                newActualExpression.setScriptGrammar(oldScriptGrammar);
                cmd.append(SetCommand.create(editingDomain,
                        dataMapping,
                        Xpdl2Package.eINSTANCE.getDataMapping_Actual(),
                        newActualExpression));
            }
            if (oldFormal != null && newFormal != null
                    && !oldFormal.equals(newFormal)) {

                cmd.append(SetCommand.create(editingDomain,
                        dataMapping,
                        Xpdl2Package.eINSTANCE.getDataMapping_Formal(),
                        newFormal));
            }
            if (!cmd.isEmpty()) {
                return cmd;
            }
        }
        return null;
    }    

    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    protected EObject getDataMappingContainer() {
        return dataMapping;
    }

    protected Process getProcess() {
        if (getDataMappingContainer() != null) {
            return Xpdl2ModelUtil.getProcess(getDataMappingContainer());
        }
        return null;
    }

}