package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Resolution to remove associated individual data field from activity
 * interface.
 * 
 * 
 * @author rsawant
 * @since 17-Apr-2013
 */
public class DisAssociateActivityInterfaceSingleDataFieldResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.DisAssociateActivityInterfaceSingleDataFieldResolution_RemoveDataFieldFromActivityInterface);
        if (target instanceof Activity) {
            /* Get data field id from marker. */
            EObject dataField = null;
            Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
            if (addInfo != null) {
                String dataId =
                        addInfo.getProperty(DataFieldToParameterResolution.MARKERINFO_ACTIVITY_DATAREFERENCE_ID);
                if (dataId != null && dataId.length() > 0) {
                    List<ProcessRelevantData> allData =
                            ProcessInterfaceUtil
                                    .getAllAvailableRelevantDataForActivity((Activity) target);
                    for (ProcessRelevantData data : allData) {
                        if (dataId.equals(data.getId())) {

                            dataField = data;
                            break;
                        }
                    }
                }
            }

            if (dataField instanceof DataField) {
                DataField field = (DataField) dataField;
                AssociatedParameters associatedParams = null;
                EObject obj =
                        ((Activity) target)
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedParameters()
                                        .getName());

                if (obj instanceof AssociatedParameters
                        && !((AssociatedParameters) obj)
                                .getAssociatedParameter().isEmpty()) {
                    associatedParams = (AssociatedParameters) obj;
                    List<AssociatedParameter> associatedParamList =
                            associatedParams.getAssociatedParameter();

                    AssociatedParameter associatedParameter =
                            getAssociatedParameter(field, associatedParamList);

                    if (associatedParameter != null) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                associatedParameter));
                    }
                }
            }
        }
        return cmd;
    }

    /**
     * @param field
     * @param associatedParamList
     * @return
     */
    private AssociatedParameter getAssociatedParameter(DataField field,
            List<AssociatedParameter> associatedParamList) {

        for (AssociatedParameter parameter : associatedParamList) {
            if (parameter.getFormalParam().equals(field.getName())) {
                return parameter;
            }
        }
        return null;
    }
}
