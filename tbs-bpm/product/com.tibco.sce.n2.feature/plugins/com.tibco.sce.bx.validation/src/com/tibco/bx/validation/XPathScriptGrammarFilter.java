package com.tibco.bx.validation;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchWsdlErrorEventUtil;
import com.tibco.xpd.script.ui.internal.IScriptGrammarFilter;
import com.tibco.xpd.xpdl2.Activity;

/**
 * 
 * Script Grammar filter for Web Service Task, filters out XPath Grammar for
 * Catch Error Event Attached to a WebService which is set to catch
 * TimeoutException.This helps exclude XPath grammar for catch Error Mapper when
 * set to catch TimeoutException.
 * 
 * @author aprasad
 * @since 21-Jan-2015
 */
public class XPathScriptGrammarFilter implements IScriptGrammarFilter {

    public XPathScriptGrammarFilter() {

    }

    public boolean select(EObject eObject, String grammar) {

        if (eObject instanceof Activity) {

            Activity act = (Activity) eObject;

            /* Exclude for Catch Error Event set to catch TimeoutException */
            if (CatchWsdlErrorEventUtil.isTimeoutExceptionSelectedForSoapJMSConsumer(act)) {
                return false;
            }
        }
        /*
         * In all other cases Return true , to leave all other existing
         * scenarios as they are
         */
        return true;
    }

}
