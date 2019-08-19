package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.DeleteCaseReferenceOperationType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;

/**
 * Converts the removed "Delete by Case ID/Composite Case ID" operations to "Delete by Case Reference" operations.
 *
 * @author nwilson
 * @since 16 Aug 2019
 */
public class ConvertDeleteByCaseIdToReferenceResolution extends AbstractWorkingCopyResolution implements IResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain, EObject target, IMarker marker)
            throws ResolutionException {
        CompoundCommand cmd = null;
        if (target instanceof Activity) {
            GlobalDataOperation operation = getGlobalDataOperation((Activity) target);
            if (operation != null) {
                CaseReferenceOperationsType refOperations =
                        XpdExtensionFactory.eINSTANCE.createCaseReferenceOperationsType();
                DeleteCaseReferenceOperationType deleteByCaseRefOperation =
                        XpdExtensionFactory.eINSTANCE.createDeleteCaseReferenceOperationType();
                refOperations.setDelete(deleteByCaseRefOperation);
                cmd = new CompoundCommand(Messages.ConvertDeleteByCaseIdToReferenceResolution_commandLabel);
                cmd.append(SetCommand.create(editingDomain,
                        operation,
                        XpdExtensionPackage.eINSTANCE.getGlobalDataOperation_CaseAccessOperations(),
                        null));
                cmd.append(SetCommand.create(editingDomain,
                        operation,
                        XpdExtensionPackage.eINSTANCE.getGlobalDataOperation_CaseReferenceOperations(),
                        refOperations));
            }
        }
        return cmd;
    }

    /**
     * Get the GlobalDataOperation from the given task service.
     * 
     * @return {@link GlobalDataOperation} or <code>null</code> if not set.
     */
    private GlobalDataOperation getGlobalDataOperation(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            if (task.getTaskService() != null) {
                return (GlobalDataOperation) TaskServiceExtUtil.getExtendedModel(task.getTaskService(),
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_GlobalDataOperation());
            }
        }
        return null;
    }
}
