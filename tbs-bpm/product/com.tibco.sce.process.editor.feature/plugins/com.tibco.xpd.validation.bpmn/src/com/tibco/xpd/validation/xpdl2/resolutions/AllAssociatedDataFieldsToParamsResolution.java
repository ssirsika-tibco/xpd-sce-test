package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ConvertFieldToParameterCommand;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to convert all data fields (invalidly associated to activity
 * interface) back to parameters
 * 
 * 
 * @author rsawant
 * @since 17-Apr-2013
 */
public class AllAssociatedDataFieldsToParamsResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.DisAssociateActivityInterfaceDataResolution_RemoveData);

        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            Map<String, ProcessRelevantData> allAvailableRelevantDataMapForActivity =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataMapForActivity(activity);
            AssociatedParameters associatedParams = null;
            EObject obj =
                    activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedParameters().getName());

            if (obj instanceof AssociatedParameters
                    && !((AssociatedParameters) obj).getAssociatedParameter()
                            .isEmpty()) {
                associatedParams = (AssociatedParameters) obj;
                List<AssociatedParameter> associatedParamList =
                        associatedParams.getAssociatedParameter();

                for (AssociatedParameter associatedData : associatedParamList) {
                    ProcessRelevantData relevantData =
                            allAvailableRelevantDataMapForActivity
                                    .get(associatedData.getFormalParam());

                    if (relevantData instanceof DataField) {
                        DataField field = (DataField) relevantData;
                        Process process =
                                Xpdl2ModelUtil.getProcess(associatedData);
                        cmd.append(new ConvertFieldToParameterCommand(
                                editingDomain, field, process));
                    }
                }
            }
        }
        return cmd;
    }
}
