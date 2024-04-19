/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.script.AbstractProcessScriptProvider;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapper Script provider implementation for Catch Signals (Local and Global).
 *
 * @author sajain
 * @since Jul 22, 2019
 */
public abstract class AbstractCatchSignalMapperScriptProvider extends AbstractProcessScriptProvider {

    /**
     * 
     * @param grammar
     * @param direction
     */
    public AbstractCatchSignalMapperScriptProvider(String grammar,
            MappingDirection direction) {
        setScriptGrammar(grammar);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getDecriptionLabel()
     */
    @Override
    public String getDecriptionLabel() {
        return Messages.CatchGlobalSignalMapperScriptProvider_CatchGlobalSignalScriptMAppingSection_title;
    }

    /**
     * @param input
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getScript(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public String getScript(EObject input) {
        String script = ""; //$NON-NLS-1$
        if (input instanceof ScriptInformation) {
            ScriptInformation information = (ScriptInformation) input;
            EObject parent = information.eContainer();
            if (parent instanceof Activity) {
                Expression expression = ((ScriptInformation) input).getExpression();
                if (expression != null) {
                    script = expression.getText();
                }

            } else if (parent instanceof DataMapping) {
                Expression expression = ((ScriptInformation) input).getExpression();
                if (expression != null) {
                    script = expression.getText();
                }
            }
        }
        return script;
    }

    /**
     * @param input
     * @param script
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getSetScriptCommand(org.eclipse.emf.ecore.EObject,
     *      java.lang.String)
     */
    @Override
    public Command getSetScriptCommand(EditingDomain ed, EObject input, String script, String grammar) {
        CompoundCommand cmd =
                new CompoundCommand(getCommandLabel());

        if (input instanceof ScriptInformation) {
            ScriptInformation information = (ScriptInformation) input;
            String oldScript = getScript(information);
            if (script != null && !script.equals(oldScript)) {

                EObject parent = information.eContainer();

                if (parent instanceof Activity) {
                    Expression expression = Xpdl2ModelUtil.createExpression(script);
                    expression.setScriptGrammar(grammar);
                    cmd.append(SetCommand.create(ed,
                            information,
                            XpdExtensionPackage.eINSTANCE.getScriptInformation_Expression(),
                            expression));
                } else if (parent == null) {
                    Activity activity = information.getActivity();
                    if (activity != null) {
                        information.setActivity(null);
                        information
                                .setName(ScriptInformationUtil.getNextScriptName(activity, information.getDirection()));
                        Expression expression = Xpdl2ModelUtil.createExpression(script);
                        expression.setScriptGrammar(grammar);
                        information.setExpression(expression);
                        cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                                activity,
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                                information));
                    }
                } else if (parent instanceof DataMapping) {
                    Expression expression = Xpdl2ModelUtil.createExpression(script == null ? "" //$NON-NLS-1$
                            : script);
                    expression.setScriptGrammar(grammar);

                    cmd.append(SetCommand.create(ed,
                            information,
                            XpdExtensionPackage.eINSTANCE.getScriptInformation_Expression(),
                            expression));
                }
            }
        }

        return cmd;
    }

    /**
     * Get the command label for SET script command.
     * 
     * @return the command label for SET script command.
     */
    protected abstract String getCommandLabel();

    /**
     * @see com.tibco.xpd.script.ui.internal.IScriptDetailsProvider#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String)
     * 
     * @param editingDomain
     * @param scriptContainer
     * @param scriptGrammar
     * @return
     */
    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain, EObject scriptContainer,
            String scriptGrammar) {

        return null;
    }

    /**
     * @see com.tibco.xpd.process.js.model.script.AbstractProcessScriptProvider#getDefaultDestination()
     * 
     * @return
     */
    @Override
    protected String getDefaultDestination() {
        return ProcessJsConsts.JSCRIPT_DESTINATION;
    }
}
