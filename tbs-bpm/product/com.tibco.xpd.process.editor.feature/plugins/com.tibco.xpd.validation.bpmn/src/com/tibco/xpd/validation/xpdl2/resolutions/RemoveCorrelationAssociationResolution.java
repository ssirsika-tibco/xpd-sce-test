/*
 * Copyright (c) TIBCO Software Inc 2004, 2022. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Sid ACE-6266 Quick fix to remove an individual correlation data association
 *
 * @author aallway
 * @since Oct 2022
 */
public class RemoveCorrelationAssociationResolution extends AbstractWorkingCopyResolution {

    public static final String FIELD_NAME = "AceCorrelationAssociationsValidationRules.fieldName"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     *
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     * @throws CoreException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain, EObject target, IMarker marker)
            throws ResolutionException {
        CompoundCommand cmd = new CompoundCommand(Messages.RemoveCorrelationAssociations_RemoveCommand);

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            Object other = Xpdl2ModelUtil.getOtherElement(activity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_AssociatedCorrelationFields());

            if (other instanceof AssociatedCorrelationFields) {
                String fieldName = null;

                Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
                if (addInfo != null) {
                    fieldName = addInfo.getProperty(FIELD_NAME);
                }

                if (fieldName != null) {

                    EList<AssociatedCorrelationField> correlations = ((AssociatedCorrelationFields) other)
                            .getAssociatedCorrelationField();
                    
                    for (AssociatedCorrelationField correlation : correlations) {
                        if (fieldName.equals(correlation.getName())) {

                            /*
                             * If it's the last correlation field remove whole element else just the single association
                             */
                            if (correlations.size() == 1) {
                                cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(editingDomain,
                                        activity,
                                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_AssociatedCorrelationFields(),
                                        other));
                                break;

                            } else {
                                cmd.append(RemoveCommand.create(editingDomain, correlation));
                                break;
                            }
                        }
                    }
                }
            }
        }
        return cmd.isEmpty() ? null : cmd;
    }

}
