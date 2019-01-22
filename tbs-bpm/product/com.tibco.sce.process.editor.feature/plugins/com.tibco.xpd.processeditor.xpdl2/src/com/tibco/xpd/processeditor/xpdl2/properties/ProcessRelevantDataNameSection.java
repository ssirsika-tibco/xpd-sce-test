/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;

/**
 * @author NWilson
 * 
 */
public class ProcessRelevantDataNameSection extends NamedElementSection {

    @Override
    protected String getDuplicateNameMessage(EObject duplicate) {
        String errorStr = null;
        if (duplicate.eContainer() instanceof Package) {
            errorStr =
                    Messages.BaseFieldOrParamPropertySection_DuplicateNameInPackage_longdesc
                            + ((Package) duplicate.eContainer()).getName();
        } else if (duplicate.eContainer() instanceof Process) {
            errorStr =
                    Messages.BaseFieldOrParamPropertySection_DuplicateNameInprocess_longdesc
                            + ((Process) duplicate.eContainer()).getName();
        } else if (duplicate.eContainer() instanceof ProcessInterface) {
            errorStr =
                    Messages.BaseFieldOrParamPropertySection_DuplicateNameInprocessInterface_longdesc
                            + ((ProcessInterface) duplicate.eContainer())
                                    .getName();
        } else if (duplicate.eContainer() instanceof Activity) {
            errorStr =
                    Messages.BaseFieldOrParamPropertySection_DuplicateNameInEmbeddedSubProc
                            + ((Activity) duplicate.eContainer()).getName();
        } else {
            errorStr = super.getDuplicateNameMessage(duplicate);
        }
        return errorStr;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection#
     * getDuplicateDisplayNameMesage(org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected String getDuplicateDisplayNameMesage(EObject duplicate) {
        String errorStr = null;
        if (duplicate.eContainer() instanceof Package) {
            errorStr =
                    Messages.BaseFieldOrParamPropertySection_DuplicateLabelInPackage_longdesc
                            + ((Package) duplicate.eContainer()).getName();
        } else if (duplicate.eContainer() instanceof Process) {
            errorStr =
                    Messages.BaseFieldOrParamPropertySection_DuplicateLabelInprocess_longdesc
                            + ((Process) duplicate.eContainer()).getName();
        } else if (duplicate.eContainer() instanceof ProcessInterface) {
            errorStr =
                    Messages.BaseFieldOrParamPropertySection_DuplicateLabelInprocessInterface_longdesc
                            + ((ProcessInterface) duplicate.eContainer())
                                    .getName();
        } else if (duplicate.eContainer() instanceof Activity) {
            errorStr =
                    Messages.BaseFieldOrParamPropertySection_DuplicateLabelInEmbeddedSubProc
                            + ((Activity) duplicate.eContainer()).getName();
        } else {
            errorStr = super.getDuplicateNameMessage(duplicate);
        }
        return errorStr;
    }

    @Override
    protected boolean allowLeadingNumerics() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection#
     * isReservedWord(java.lang.String)
     */
    @Override
    /*
     * @param nameText
     * 
     * @return
     */
    protected boolean isReservedWord(String nameText) {
        List<String> symbolTableKeywords =
                ReservedWords.getSymbolTableKeyWords();
        if (symbolTableKeywords.contains(nameText)) {
            return true;
        }
        return false;
    }

}
