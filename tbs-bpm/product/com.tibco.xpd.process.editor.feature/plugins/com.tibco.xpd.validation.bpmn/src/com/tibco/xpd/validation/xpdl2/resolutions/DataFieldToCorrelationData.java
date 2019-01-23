/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.validation.bpmn.rules.CorrelationDataRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author NWilson
 * 
 */
public class DataFieldToCorrelationData extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     *      getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        CompoundCommand cmd = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            Properties properties = ValidationUtil.getAdditionalInfo(marker);
            if (properties != null) {
                String name =
                        properties.getProperty(CorrelationDataRule.FIELD_KEY);
                ConceptPath path =
                        ConceptUtil.resolveConceptPath(activity, name);
                if (path != null) {
                    Object item = path.getItem();
                    if (item instanceof EObject) {
                        target = (EObject) item;
                    }
                }
            }
        }
        if (target instanceof DataField) {
            DataField field = (DataField) target;
            cmd =
                    new CompoundCommand(
                            Messages.DataFieldToCorrelationData_ConvertCommand);
            cmd.append(SetCommand.create(ed, field, Xpdl2Package.eINSTANCE
                    .getDataField_Correlation(), Boolean.TRUE));
        }
        return cmd;
    }
}
