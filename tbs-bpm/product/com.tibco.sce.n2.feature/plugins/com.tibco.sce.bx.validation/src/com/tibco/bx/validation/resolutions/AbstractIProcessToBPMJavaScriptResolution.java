/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.n2.resources.script.IProcessScriptToBPMJavaScriptConverter;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution that will partially convert all IProcessScript grammar expressions
 * in the associated Package/Process/Activity/Transition to an approximation of
 * BPM JavaScript
 * 
 * @author aallway
 * @since 8 Jun 2011
 */
public abstract class AbstractIProcessToBPMJavaScriptResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    /**
     * 
     */
    public AbstractIProcessToBPMJavaScriptResolution() {
    }

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

        List<EObject> targetObjects = new ArrayList<EObject>();

        CompoundCommand cmd =
                createCommandFromResolutionTarget(target, targetObjects);

        if (cmd != null) {

            for (EObject scriptContainer : targetObjects) {
                /*
                 * Iterate thru entire content of object looking for Expression
                 * objects with iProcessScript grammar.
                 */
                appendConversionCommands(editingDomain, scriptContainer, cmd);
            }
        }

        return cmd;
    }

    /**
     * @param target
     * @param returnTargetObjects
     *            The set of objects within the issue target object that should
     *            be checked for iProcessScript scripts
     * 
     * @return command for given target of resolution or <code>null</code> if
     *         unexpected target. Also returns the set of objects to check for
     *         iProcessScripts in returnTargetObjects
     */
    protected abstract CompoundCommand createCommandFromResolutionTarget(
            EObject target, List<EObject> returnTargetObjects);

    /**
     * Append commands to cmd to convert an all scripts in xpdl:Expression
     * elements that are decsendents of given root element.
     * 
     * @param editingDomain
     * @param scriptContainer
     * @param cmd
     */
    public static void appendConversionCommands(EditingDomain editingDomain,
            EObject scriptContainer, CompoundCommand cmd) {
        for (Iterator iterator = scriptContainer.eAllContents(); iterator
                .hasNext();) {
            EObject eo = (EObject) iterator.next();

            if (eo instanceof Expression) {
                Expression expression = (Expression) eo;

                if (AbstractIProcessToJavaScriptConverter.IPROCESSSCRIPT_GRAMMAR
                        .equals(expression.getScriptGrammar())) {

                    convertScript(editingDomain, cmd, expression);
                }
            }
        }
    }

    /**
     * @param expression
     * 
     * @return <code>true</code> if the expression represents a simple type
     *         mapping.
     */
    private static boolean isSimpleMappingExpression(Expression expression) {
        /* Simple type mapping must have DataMapping as parent element. */
        if (expression.eContainer() instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) expression.eContainer();

            /*
             * And the DataMapping must not also contain a script information
             * (which denotes the xpdl2:Actual (expression) as being a complex
             * user defined script.
             */
            Object scriptInfo =
                    Xpdl2ModelUtil.getOtherElement(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());

            if (scriptInfo == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Append command to cmd to convert an individual expression script from
     * iProcess to JavaScript.
     * 
     * @param editingDomain
     * @param cmd
     * @param expression
     */
    private static void convertScript(EditingDomain editingDomain,
            CompoundCommand cmd, Expression expression) {

        cmd.append(SetCommand.create(editingDomain,
                expression,
                Xpdl2Package.eINSTANCE.getExpression_ScriptGrammar(),
                ProcessJsConsts.JAVASCRIPT_GRAMMAR));

        /*
         * XPD-4572: If expression is a simple (non user defined script) data
         * mapping then we should ONLY change the grammar (otherwise script
         * converter will append a semi colon and invalidate the mapping.
         */
        if (isSimpleMappingExpression(expression)) {
            return;
        }

        String sourceIProcessScript = expression.getText();

        if (sourceIProcessScript != null && sourceIProcessScript.length() > 0) {

            IProcessScriptToBPMJavaScriptConverter scriptConverter =
                    new IProcessScriptToBPMJavaScriptConverter(
                            sourceIProcessScript, expression);

            String javaScript = scriptConverter.getJavaScriptApproximation();

            Entry textEntry =
                    FeatureMapUtil.createEntry(XMLTypePackage.eINSTANCE
                            .getXMLTypeDocumentRoot_Text(), javaScript);

            /* Find existing text feature in feature map. */
            int textIndex = -1;
            for (int i = 0; i < expression.getMixed().size(); i++) {
                if (XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text()
                        .equals(expression.getMixed().getEStructuralFeature(i))) {
                    textIndex = i;
                    break;
                }
            }

            /* If it already exists then replace it. */
            if (textIndex != -1) {
                Command setTextCmd =
                        SetCommand.create(editingDomain,
                                expression,
                                Xpdl2Package.eINSTANCE.getExpression_Mixed(),
                                textEntry,
                                textIndex);
                cmd.append(setTextCmd);
            }
        }
        return;
    }

    public static class ActivityIProcessToBPMJavaScriptResolution extends
            AbstractIProcessToBPMJavaScriptResolution {
        @Override
        protected CompoundCommand createCommandFromResolutionTarget(
                EObject target, List<EObject> returnTargetObjects) {
            CompoundCommand cmd = null;
            if (target instanceof Activity) {
                cmd =
                        new CompoundCommand(
                                Messages.ConvertIProcessScriptToBPMJavaScriptResolution_ConvertTaskIProcessScripts_menu);

                returnTargetObjects.add(target);

            }
            return cmd;
        }
    }

    public static class ConditionalFlowIProcessToBPMJavaScriptResolution extends
            AbstractIProcessToBPMJavaScriptResolution {
        @Override
        protected CompoundCommand createCommandFromResolutionTarget(
                EObject target, List<EObject> returnTargetObjects) {
            CompoundCommand cmd = null;
            if (target instanceof Transition) {
                cmd =
                        new CompoundCommand(
                                Messages.ConvertIProcessScriptToBPMJavaScriptResolution_ConvertFlowIProcessScripts_menu);

                returnTargetObjects.add(target);

            }
            return cmd;
        }
    }

    public static class ProcessIProcessToBPMJavaScriptResolution extends
            AbstractIProcessToBPMJavaScriptResolution {
        @Override
        protected CompoundCommand createCommandFromResolutionTarget(
                EObject target, List<EObject> returnTargetObjects) {
            CompoundCommand cmd = null;
            target = Xpdl2ModelUtil.getAncestor(target, Process.class);
            if (target instanceof Process) {
                cmd =
                        new CompoundCommand(
                                Messages.ConvertIProcessScriptToBPMJavaScriptResolution_ConvertIProcessScriptInProcess_menu);

                returnTargetObjects.addAll(Xpdl2ModelUtil
                        .getAllActivitiesInProc((Process) target));
                returnTargetObjects.addAll(Xpdl2ModelUtil
                        .getAllTransitionsInProc((Process) target));

            }
            return cmd;
        }
    }

    public static class PackageIProcessToBPMJavaScriptResolution extends
            AbstractIProcessToBPMJavaScriptResolution {
        @Override
        protected CompoundCommand createCommandFromResolutionTarget(
                EObject target, List<EObject> returnTargetObjects) {
            CompoundCommand cmd = null;
            target = Xpdl2ModelUtil.getAncestor(target, Package.class);
            if (target instanceof Package) {
                cmd =
                        new CompoundCommand(
                                Messages.ConvertIProcessScriptToBPMJavaScriptResolution_ConvertIProcessScriptInPackage_menu);

                for (Process process : ((Package) target).getProcesses()) {
                    if (DestinationUtil
                            .getEnabledGlobalDestinationTypes(process)
                            .contains(N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                        returnTargetObjects.addAll(Xpdl2ModelUtil
                                .getAllActivitiesInProc(process));
                        returnTargetObjects.addAll(Xpdl2ModelUtil
                                .getAllTransitionsInProc(process));
                    }
                }
            }
            return cmd;
        }
    }

}
