/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * If an activity refers to wsdl from a project that is not a BPM project (i.e a
 * project which does not have wsdl bom nature) then the mapping grammar for
 * that activity must be set to XPath
 * 
 * @author bharge
 * @since 9 Aug 2012
 */
public class SetXPathGrammarResolution extends AbstractWorkingCopyResolution {

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

        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            CompoundCommand command = new CompoundCommand();
            boolean inXPathScriptSet = false;
            boolean outXPathScriptSet = false;
            String grammar = ScriptGrammarFactory.XPATH;

            List<?> otherElementList =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());

            for (Object other : otherElementList) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    /*
                     * user has selected xpath script grammar in input mapping
                     * section from the drop down
                     */
                    if (DirectionType.IN_LITERAL.equals(information
                            .getDirection())) {

                        inXPathScriptSet = true;

                        Expression expression = information.getExpression();
                        if (null != expression) {

                            if (!ScriptGrammarFactory.XPATH.equals(expression
                                    .getScriptGrammar())) {
                                setXPathScript(editingDomain,
                                        expression,
                                        command,
                                        grammar,
                                        information);
                            }
                        }
                    }
                    /*
                     * user has selected xpath script grammar in output mapping
                     * section from the drop down
                     */
                    if (DirectionType.OUT_LITERAL.equals(information
                            .getDirection())) {

                        outXPathScriptSet = true;

                        Expression expression = information.getExpression();
                        if (null != expression) {

                            if (!ScriptGrammarFactory.XPATH.equals(expression
                                    .getScriptGrammar())) {
                                setXPathScript(editingDomain,
                                        expression,
                                        command,
                                        grammar,
                                        information);
                            }
                        }
                    }
                }
            }
            /*
             * script grammar in neither input nor output mappings section is
             * set
             */
            if (!inXPathScriptSet || !outXPathScriptSet) {
                /*
                 * XPD-3422: Saket: Check if the activity is an incoming request
                 * activity because then we check only for OUT mappings as reply
                 * activities never have IN mappings.
                 * 
                 * Note: we don't do anything special about reply-immediate
                 * start events because there IN (from-web-svc) mappings are
                 * forced to be XPath anyway by the UI.
                 */
                if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
                    if (!outXPathScriptSet) {
                        setXPathGrammar(activity,
                                editingDomain,
                                DirectionType.OUT_LITERAL,
                                command);
                    }
                }
                /*
                 * XPD-3422: Saket: Check if the activity is a reply activity
                 * because then we check only for IN mappings as reply
                 * activities never have OUT mappings.
                 */
                else if (ReplyActivityUtil.isReplyActivity(activity)) {
                    if (!inXPathScriptSet) {
                        setXPathGrammar(activity,
                                editingDomain,
                                DirectionType.IN_LITERAL,
                                command);
                    }
                }
                /*
                 * XPD-3422: Saket: Check if the activity is an
                 * invoke-one-way-activity because then we check only for IN
                 * mappings.
                 */
                else if (Xpdl2ModelUtil.isSendOneWayRequest(activity)) {
                    if (!inXPathScriptSet) {
                        setXPathGrammar(activity,
                                editingDomain,
                                DirectionType.IN_LITERAL,
                                command);
                    }
                }
                /*
                 * XPD-3422: Saket: By default, we check for both IN and OUT
                 * mappings.
                 */
                else {
                    if (!inXPathScriptSet) {
                        setXPathGrammar(activity,
                                editingDomain,
                                DirectionType.IN_LITERAL,
                                command);
                    }
                    if (!outXPathScriptSet) {
                        setXPathGrammar(activity,
                                editingDomain,
                                DirectionType.OUT_LITERAL,
                                command);
                    }
                }
            }
            return command;

        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * 
     * @param editingDomain
     * @param expression
     * @param command
     * @param grammar
     * @param information
     */
    private void setXPathScript(EditingDomain editingDomain,
            Expression expression, CompoundCommand command, String grammar,
            ScriptInformation information) {

        expression = Xpdl2ModelUtil.createExpression(""); //$NON-NLS-1$
        expression.setScriptGrammar(grammar);
        Object feature =
                XpdExtensionPackage.eINSTANCE.getScriptInformation_Expression();
        command.append(SetCommand.create(editingDomain,
                information,
                feature,
                expression));
    }

    /**
     * @param activity
     * @param editingDomain
     * @param directionType
     * @param command
     */
    private void setXPathGrammar(Activity activity,
            EditingDomain editingDomain, DirectionType directionType,
            CompoundCommand command) {

        ScriptInformation grammarScript =
                XpdExtensionFactory.eINSTANCE.createScriptInformation();
        grammarScript.setDirection(directionType);
        Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
        expression.setScriptGrammar(ScriptGrammarFactory.XPATH);
        grammarScript.setExpression(expression);
        command.append(Xpdl2ModelUtil.getAddOtherElementCommand(editingDomain,
                activity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                grammarScript));

    }

}
