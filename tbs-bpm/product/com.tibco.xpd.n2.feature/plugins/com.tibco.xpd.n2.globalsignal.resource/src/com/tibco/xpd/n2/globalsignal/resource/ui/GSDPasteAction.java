/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.PasteAction;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.ui.GSDClipboardUtils.GSDClipboardDataList;
import com.tibco.xpd.n2.globalsignal.resource.util.GSDModelUtil;

/**
 * GSD paste action.
 * 
 * @author sajain
 * @since Feb 18, 2015
 */
public class GSDPasteAction extends PasteAction {

    /**
     * Check whether the source context of clipboard is valid for the target
     * context
     * 
     * @param targetContext
     * @param clipboardObjects
     * 
     * @return <code>false</code> if not <code>true</code> if it is.
     */
    @Override
    protected boolean isClipboardContentSuitableForTargetContext(
            EObject targetContext, Collection<Object> clipboardObjects) {

        /*
         * Get source parent GSD/Signal of clipboard objects for context (if
         * available)
         */
        GSDClipboardDataList sourceContext =
                GSDClipboardUtils.getSourceContextForClipboardContent();

        if (sourceContext != null
                && sourceContext.getSourceGSDOrSignal() != null) {

            for (Object object : clipboardObjects) {

                /*
                 * If the paste is not valid, then simply return false.
                 */
                if (!isValidPaste(targetContext, sourceContext, object)) {

                    return false;

                }
            }
        }

        /*
         * If we've reached here, then paste has to be valid. So return true.
         */
        return true;

    }

    /**
     * Return <code>true</code> if the clipboard content object is NOT valid to
     * be pasted for the specified source and target contexts,
     * <code>false</code> otherwise.
     * 
     * @param targetContext
     * @param sourceContext
     * @param object
     * 
     * @return <code>true</code> if the clipboard content object isn't valid to
     *         be pasted for the specified source and target contexts,
     *         <code>false</code> otherwise.
     */
    private boolean isValidPaste(EObject targetContext,
            GSDClipboardDataList sourceContext, Object object) {

        if (object instanceof GlobalSignal) {

            if (sourceContext.getSourceGSDOrSignal() instanceof GlobalSignalDefinitions) {

                if (targetContext instanceof GlobalSignalDefinitions
                        || targetContext instanceof GlobalSignal) {

                    return true;
                }
            }

        } else if (object instanceof PayloadDataField) {

            if (sourceContext.getSourceGSDOrSignal() instanceof GlobalSignal) {

                return true;
            }
        }

        return false;
        // return ((object instanceof GlobalSignal
        // && sourceContext.getSourceGSDOrSignal() instanceof
        // GlobalSignalDefinitions && (targetContext instanceof
        // GlobalSignalDefinitions || targetContext instanceof GlobalSignal)) ||
        // (object instanceof PayloadDataField && sourceContext
        // .getSourceGSDOrSignal() instanceof GlobalSignal));
    }

    /**
     * Create paste command.
     * 
     * @param target
     * @param destEObject
     * @param editingDomain
     * @param copyObjects
     * @return
     */
    @Override
    protected CommandContainer createPasteCommand(Object target,
            EObject destEObject, EditingDomain editingDomain,
            Collection copyObjects, boolean handleProjectReferences) {

        return GSDModelUtil.paste(destEObject,
                copyObjects,
                editingDomain,
                target,
                handleProjectReferences);
    }

}
