/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ConvertFieldToParameterCommand;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * CorrelationDataToParameterResolution
 * 
 * 
 * @authorimport com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil; aallway
 * @since 3.3 (9 Nov 2009)
 */
public class DataFieldToParameterResolution extends
        AbstractWorkingCopyResolution {

    public static final String MARKERINFO_ACTIVITY_DATAREFERENCE_ID =
            "activity.reference.dataid"; //$NON-NLS-1$

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            /* Get data field id from marker. */
            Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
            if (addInfo != null) {
                String dataId =
                        addInfo.getProperty(MARKERINFO_ACTIVITY_DATAREFERENCE_ID);
                if (dataId != null && dataId.length() > 0) {
                    List<ProcessRelevantData> allData =
                            ProcessInterfaceUtil
                                    .getAllAvailableRelevantDataForActivity((Activity) target);
                    for (ProcessRelevantData data : allData) {
                        if (dataId.equals(data.getId())) {
                            /*
                             * Reset the target object to the referenced data
                             * field.
                             */
                            target = data;
                            break;
                        }
                    }
                }
            }
        }

        if (target instanceof DataField) {
            DataField field = (DataField) target;

            Process process = Xpdl2ModelUtil.getProcess(field);
            if (process != null) {
                return new ConvertFieldToParameterCommand(editingDomain, field,
                        process);
            }
        }
        return null;
    }

}
