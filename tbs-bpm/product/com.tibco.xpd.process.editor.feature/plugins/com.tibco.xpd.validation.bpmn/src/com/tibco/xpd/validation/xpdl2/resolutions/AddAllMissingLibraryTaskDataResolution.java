/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.validation.bpmn.rules.ReferenceTaskRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation problem resolution to add all data required by a library task into
 * a referencing process.
 * 
 * @author aallway
 * @since 3.2
 */
public class AddAllMissingLibraryTaskDataResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            Activity referenceTask = (Activity) target;

            Process localProcess = referenceTask.getProcess();

            Activity libraryTask = getLibraryTask(marker);

            if (libraryTask != null) {
                List<ProcessRelevantData> referencedLibraryData =
                        ReferenceTaskUtil
                                .getLibraryTaskReferencedData(libraryTask);

                Map<String, ProcessRelevantData> localProcessNameToDataMap =
                        ProcessRelevantDataUtil
                                .getProcessDataNameMap(localProcess);

                // Build list of data that is not present in local process.
                List<ProcessRelevantData> missingData =
                        new ArrayList<ProcessRelevantData>();

                for (ProcessRelevantData libraryData : referencedLibraryData) {
                    if (!localProcessNameToDataMap.containsKey(libraryData
                            .getName())) {
                        missingData.add(libraryData);
                    }
                }

                if (!missingData.isEmpty()) {
                    return ReferenceTaskUtil
                            .getAddTaskLibraryDataToProcessCommand(editingDomain,
                                    localProcess,
                                    missingData);
                }
            }
        }
        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        if (propertiesLabel != null) {
            Activity libraryTask = getLibraryTask(marker);

            if (libraryTask != null) {
                return String.format(propertiesLabel,
                        Xpdl2ModelUtil.getDisplayNameOrName(libraryTask
                                .getProcess()),
                        Xpdl2ModelUtil.getDisplayNameOrName(libraryTask));
            }
        }
        return super.getResolutionLabel(propertiesLabel, marker);
    }

    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        return getResolutionLabel(propertiesDescription, marker);
    }

    private Activity getLibraryTask(IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            String libraryTaskId =
                    addInfo
                            .getProperty(ReferenceTaskRule.ADDINFO_LIBRARYTASK_ID);

            if (libraryTaskId != null) {
                return Xpdl2WorkingCopyImpl
                        .locateTaskLibraryTask(libraryTaskId);
            }
        }

        return null;
    }
}
