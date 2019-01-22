/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Section explicitly designed to be used as initial page in "Generate Pageflow"
 * option wizard - allows user to select data to pass to/from pageflow
 * 
 * @author aallway
 * @since 3.2
 */
public class GeneratePageflowDataSelectionSection extends
        AbstractGenerateDataSelectionSection {
    /**
     * @param availableData
     * @param associatedData
     */
    public GeneratePageflowDataSelectionSection(
            List<ProcessRelevantData> availableData,
            List<ProcessRelevantData> associatedData) {
        super(availableData, associatedData);
    }

    @Override
    protected String getOtherAvailableDataFolderName() {
        return Messages.NewPageflowDataSelectionPage_OtherAvailableData_label;
    }

    @Override
    protected String getAssociatedDataTaskFolderName() {
        return Messages.NewPageflowDataSelectionPage_UserTaskInterfaceData_label;
    }

}