/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * XPD-8168: Resolution for the rule:
 * 
 * bx.dataMapperNotgSupportForMultiInstSubProc=%s: Data Mapper mapping grammar
 * is not supported for multi-instance sub-processes.
 * 
 * Removes the Datamapper mappings (if there are any) and explicitly sets the
 * grammar to JavaScript for the map to sub-process, map from sub-process and
 * map-from error (for attached error event).
 * 
 * @author aallway
 * @since 12 Apr 2016
 */
public class SetSubProcessMappingsToJavaScriptResolution extends
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

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            CompoundCommand cmd = new LateExecuteCompoundCommand();
            cmd.setLabel(Messages.SetSubProcessMappingsToJavaScriptResolution_UseJavaScriptMappings_menu);

            if (activity.getImplementation() instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) activity.getImplementation();

                switchSubProcessToJavaScriptMappings(editingDomain,
                        activity,
                        cmd,
                        subFlow);

            } else if (activity.getEvent() instanceof IntermediateEvent) {
                /*
                 * Switch to javaSvcript for Catch specific sub-process error
                 * event.
                 */
                IntermediateEvent event =
                        (IntermediateEvent) activity.getEvent();

                if (event.getEventTriggerTypeNode() instanceof ResultError) {
                    ResultError resultError =
                            (ResultError) event.getEventTriggerTypeNode();

                    switchCatchErrorToJavaScriptMappings(editingDomain,
                            activity,
                            cmd,
                            resultError);

                }

            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }

        return null;
    }

    /**
     * Swicth catch error on on task boundary from datamapper to javascript
     * mappings.
     * 
     * @param editingDomain
     * @param activity
     * @param cmd
     * @param resultError
     */
    protected void switchCatchErrorToJavaScriptMappings(
            EditingDomain editingDomain, Activity activity,
            CompoundCommand cmd, ResultError resultError) {
        CatchErrorMappings catchErrorMappings =
                (CatchErrorMappings) Xpdl2ModelUtil
                        .getOtherElement(resultError,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CatchErrorMappings());
        /*
         * Remove dataMapper mappings.
         */
        if (catchErrorMappings != null
                && catchErrorMappings.getMessage() != null) {
            Object outputMappings =
                    Xpdl2ModelUtil.getOtherElement(catchErrorMappings
                            .getMessage(), XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_OutputMappings());

            if (outputMappings != null) {
                cmd.append(Xpdl2ModelUtil
                        .getRemoveOtherElementCommand(editingDomain,
                                catchErrorMappings.getMessage(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_OutputMappings(),
                                outputMappings));
            }
        }

        /*
         * Remove explicit data mapper setting.
         */
        FeatureMap otherElements = activity.getOtherElements();

        List<ScriptInformation> siToRemove = new ArrayList<ScriptInformation>();

        for (Iterator iterator = otherElements.iterator(); iterator.hasNext();) {
            Entry entry = (Entry) iterator.next();

            if (entry.getValue() instanceof ScriptInformation) {
                ScriptInformation si = (ScriptInformation) entry.getValue();

                Expression expression = si.getExpression();

                if (expression != null) {
                    if (ScriptGrammarFactory.DATAMAPPER.equals(expression
                            .getScriptGrammar())) {
                        siToRemove.add(si);
                    }
                }
            }
        }

        for (ScriptInformation si : siToRemove) {
            cmd.append(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            si));
        }

        /*
         * Add the explicit element to define that this is JavaScript input and
         * output.
         */

        ScriptInformation jsOutputGrammar =
                createExplicitGrammarSpecifier(DirectionType.OUT_LITERAL);

        cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(editingDomain,
                activity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                jsOutputGrammar));
    }

    /**
     * Switch catch error on on task boundary from datamapper to javascript
     * mappings.
     * 
     * @param editingDomain
     * @param activity
     * @param cmd
     * @param subFlow
     */
    protected void switchSubProcessToJavaScriptMappings(
            EditingDomain editingDomain, Activity activity,
            CompoundCommand cmd, SubFlow subFlow) {
        /* Remove the input mappings */
        Object inputMappings =
                Xpdl2ModelUtil.getOtherElement(subFlow,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InputMappings());

        if (inputMappings != null) {
            cmd.append(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(editingDomain,
                            subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InputMappings(),
                            inputMappings));
        }

        /* Remove the output mappings. */
        Object outputMappings =
                Xpdl2ModelUtil.getOtherElement(subFlow,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_OutputMappings());

        if (outputMappings != null) {
            cmd.append(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(editingDomain,
                            subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OutputMappings(),
                            outputMappings));
        }

        /*
         * Remove explicit data mapper setting.
         */
        FeatureMap otherElements = activity.getOtherElements();
        List<ScriptInformation> siToRemove = new ArrayList<ScriptInformation>();

        boolean haveJavaScriptInputSelectorAlready = false;
        boolean haveJavaScriptOutputSelectorAlready = false;

        for (Iterator iterator = otherElements.iterator(); iterator.hasNext();) {
            Entry entry = (Entry) iterator.next();

            if (entry.getValue() instanceof ScriptInformation) {
                ScriptInformation si = (ScriptInformation) entry.getValue();

                Expression expression = si.getExpression();

                if (expression != null) {
                    if (ScriptGrammarFactory.DATAMAPPER.equals(expression
                            .getScriptGrammar())) {
                        siToRemove.add(si);

                    } else if (ScriptGrammarFactory.JAVASCRIPT
                            .equals(expression.getScriptGrammar())) {
                        if (DirectionType.IN_LITERAL.equals(si.getDirection())) {
                            haveJavaScriptInputSelectorAlready = true;
                        } else if (DirectionType.OUT_LITERAL.equals(si
                                .getDirection())) {
                            haveJavaScriptOutputSelectorAlready = true;
                        }
                    }
                }
            }
        }

        for (ScriptInformation si : siToRemove) {
            cmd.append(Xpdl2ModelUtil
                    .getRemoveOtherElementCommand(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script(),
                            si));
        }

        /*
         * Add the explicit element to define that this is JavaScript input and
         * output.
         */
        if (!haveJavaScriptInputSelectorAlready) {
            ScriptInformation jsInputGrammar =
                    createExplicitGrammarSpecifier(DirectionType.IN_LITERAL);

            cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(editingDomain,
                    activity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                    jsInputGrammar));
        }

        if (!haveJavaScriptOutputSelectorAlready) {
            ScriptInformation jsOutputGrammar =
                    createExplicitGrammarSpecifier(DirectionType.OUT_LITERAL);

            cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(editingDomain,
                    activity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                    jsOutputGrammar));
        }
    }

    /**
     * @param direction
     * @return New ScriptInformation explicit grammar selector element for the
     *         given direction
     */
    protected ScriptInformation createExplicitGrammarSpecifier(
            DirectionType direction) {
        ScriptInformation jsInputGrammar =
                XpdExtensionFactory.eINSTANCE.createScriptInformation();

        jsInputGrammar.setDirection(direction);
        Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
        expression.setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
        jsInputGrammar.setExpression(expression);
        return jsInputGrammar;
    }

}
