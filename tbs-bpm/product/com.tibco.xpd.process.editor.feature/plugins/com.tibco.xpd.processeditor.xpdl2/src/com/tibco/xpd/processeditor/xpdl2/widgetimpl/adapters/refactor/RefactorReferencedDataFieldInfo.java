package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * RefactorReferencedDataFieldInfo
 *
 * Refactoring information about a referenced data field.
 */
public class RefactorReferencedDataFieldInfo {
    public ProcessRelevantData dataFieldOrParam = null;

    // If not ref'd elsewhere then give user choice to move or duplicate.
    public Boolean referencedElseWhere = false;

    // If referenced ONLY in selected objects then given choice to Move to
    // new process or duplicate in new process.
    public Boolean moveDataField = false;

}
