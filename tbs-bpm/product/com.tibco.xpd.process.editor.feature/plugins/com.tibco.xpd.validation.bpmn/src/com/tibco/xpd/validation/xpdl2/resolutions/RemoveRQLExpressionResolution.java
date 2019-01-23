/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution class to remove the RQL expression.
 * 
 * @author sajain
 * @since Dec 10, 2014
 */
public class RemoveRQLExpressionResolution extends
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

        /*
         * Check if target is a process relevant data instance.
         */
        if (target instanceof ProcessRelevantData) {

            ProcessRelevantData prd = (ProcessRelevantData) target;

            /*
             * Check if datatype of this field is BasicType.
             */
            if (prd.getDataType() instanceof BasicType) {

                BasicType basicType = (BasicType) prd.getDataType();

                /*
                 * Fetch Participant query.
                 */
                Object pQueryObject =
                        Xpdl2ModelUtil.getOtherElement(basicType,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery());

                /*
                 * Performer field used for allocate to offer-set member
                 * identifier cannot have RQL initial value.
                 */
                if (pQueryObject instanceof Expression) {

                    Expression expression = (Expression) pQueryObject;

                    /*
                     * Check if grammar is 'RQL'.
                     */
                    if (ScriptGrammarFactory.RQL.equals(expression
                            .getScriptGrammar())) {

                        /*
                         * Remove participant query.
                         */

                        return Xpdl2ModelUtil
                                .getRemoveOtherElementCommand(editingDomain,
                                        basicType,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantQuery(),
                                        pQueryObject);

                    }
                }
            }
        }

        return null;
    }

}
