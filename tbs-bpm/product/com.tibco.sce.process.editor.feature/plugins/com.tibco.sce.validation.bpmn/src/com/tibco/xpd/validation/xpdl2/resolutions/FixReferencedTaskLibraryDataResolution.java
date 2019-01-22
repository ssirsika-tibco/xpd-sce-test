/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
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
 * Validation problem resolution to fix differences between task library data
 * and the matching process data in process that references the library task in
 * the library.
 * <p>
 * It is always the process data that is changed to match the library data
 * 
 * @author aallway
 * @since 3.2
 */
public class FixReferencedTaskLibraryDataResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof ProcessRelevantData) {
            Process localProcess = getReferingProcess(marker);
            if (localProcess != null) {
                Activity libraryTask = getLibraryTask(marker);

                if (libraryTask != null) {
                    ProcessRelevantData libData =
                            getLibraryData(marker, libraryTask);
                    if (libData != null) {
                        return ReferenceTaskUtil
                                .getFixTaskLibraryDataInProcessCommand(editingDomain,
                                        localProcess,
                                        Collections.singletonList(libData));
                    }
                }
            }
        }
        return null;
    }

    private Process getReferingProcess(IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            String processID =
                    addInfo
                            .getProperty(ReferenceTaskRule.ADDINFO_REFERINGPROCESS_ID);

            if (processID != null) {
                return Xpdl2WorkingCopyImpl.locateProcess(processID);
            }
        }

        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        if (propertiesLabel != null) {
            Activity libraryTask = getLibraryTask(marker);

            if (libraryTask != null) {
                ProcessRelevantData libData =
                        getLibraryData(marker, libraryTask);
                if (libData != null) {
                    return String.format(propertiesLabel, Xpdl2ModelUtil
                            .getDisplayNameOrName(libData), Xpdl2ModelUtil
                            .getDisplayNameOrName(libraryTask.getProcess()));
                }
            }
        }
        return super.getResolutionLabel(propertiesLabel, marker);
    }

    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        return getResolutionLabel(propertiesDescription, marker);
    }

    private ProcessRelevantData getLibraryData(IMarker marker,
            Activity libraryTask) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            String libraryDataName =
                    addInfo
                            .getProperty(ReferenceTaskRule.ADDINFO_LIBRARYDATA_NAME);

            if (libraryDataName != null) {
                Process process = libraryTask.getProcess();

                List data =
                        ProcessInterfaceUtil.getAllProcessRelevantData(process);
                for (Iterator iterator = data.iterator(); iterator.hasNext();) {
                    ProcessRelevantData d =
                            (ProcessRelevantData) iterator.next();
                    if (libraryDataName.equals(d.getName())) {
                        return d;
                    }
                }
            }
        }

        return null;
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
