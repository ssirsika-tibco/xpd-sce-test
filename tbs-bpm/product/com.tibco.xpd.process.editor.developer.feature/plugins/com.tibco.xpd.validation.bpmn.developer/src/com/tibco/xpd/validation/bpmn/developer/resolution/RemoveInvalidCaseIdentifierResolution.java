/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.validation.bpmn.developer.rules.GlobalDataTaskRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CompositeIdentifierType;
import com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;

/**
 * Quick fix resolution to remove an invalid case identifier from the delete
 * from composite case identifier operation of a Global Data service task.
 * 
 * @author njpatel
 */
public class RemoveInvalidCaseIdentifierResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            GlobalDataOperation operation =
                    getGlobalDataOperation((Activity) target);
            if (operation != null) {
                return getRemoveInvalidCaseIdCommand(editingDomain,
                        operation.getCaseAccessOperations(),
                        marker);
            }
        }
        return null;
    }

    /**
     * @param editingDomain
     * @param caseAccessOperations
     * @param marker
     * @return
     */
    private Command getRemoveInvalidCaseIdCommand(EditingDomain editingDomain,
            CaseAccessOperationsType caseAccessOps, IMarker marker) {

        if (marker.exists() && marker.getResource() != null) {
            String caseClassId = null;
            String idName = null;
            Properties info = ValidationUtil.getAdditionalInfo(marker);
            if (info != null) {
                caseClassId =
                        info.getProperty(GlobalDataTaskRule.CASE_CLASS_ID);
                idName = info.getProperty(GlobalDataTaskRule.ID);
            }

            if (caseClassId != null && idName != null && caseAccessOps != null) {
                ExternalReference extRef =
                        caseAccessOps.getCaseClassExternalReference();
                if (extRef != null) {
                    Class caseClass =
                            (Class) ProcessUIUtil
                                    .getReferencedClassifier(extRef, marker
                                            .getResource().getProject());

                    if (caseClass != null
                            && caseClassId.equals(EcoreUtil.getURI(caseClass)
                                    .fragment())) {
                        // Confirm that the case id is not valid
                        Property caseId =
                                caseClass.getOwnedAttribute(idName, null);

                        if (caseId == null
                                || !BOMGlobalDataUtils.isCompositeCID(caseId)) {
                            // Not a valid composite case id so create command
                            // to remove it
                            DeleteByCompositeIdentifiersType identifiersType =
                                    caseAccessOps
                                            .getDeleteByCompositeIdentifiers();

                            if (identifiersType != null) {
                                EList<CompositeIdentifierType> identifiers =
                                        identifiersType
                                                .getCompositeIdentifier();

                                for (CompositeIdentifierType identifier : identifiers) {
                                    if (idName.equals(identifier
                                            .getIdentifiername())) {
                                        return RemoveCommand
                                                .create(editingDomain,
                                                        identifiersType,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDeleteByCompositeIdentifiersType_CompositeIdentifier(),
                                                        identifier);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
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
                return (GlobalDataOperation) TaskServiceExtUtil
                        .getExtendedModel(task.getTaskService(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_GlobalDataOperation());
            }
        }
        return null;
    }

}
