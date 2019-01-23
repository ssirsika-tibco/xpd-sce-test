/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;

/**
 * EndEventScriptsSection
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Dec 2009)
 */
public class EndEventScriptsSection extends AbstractEventAuditScriptSection {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.script.
     * AbstractEventAuditScriptSection#wantCompletedScript()
     */
    @Override
    protected boolean wantCompletedScript() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.script.
     * AbstractEventAuditScriptSection#wantCancelScript()
     */
    @Override
    protected boolean wantCancelScript() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.script.
     * AbstractEventAuditScriptSection#wantInitiatedScript()
     */
    @Override
    protected boolean wantInitiatedScript() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.script.
     * AbstractEventAuditScriptSection#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            EObject baseObj = getBaseSelectObject(toTest);
            if (baseObj instanceof Activity) {
                Activity act = (Activity) baseObj;

                if (act.getEvent() instanceof EndEvent) {
                    return true;
                }
            }
        }
        return false;
    }

}
