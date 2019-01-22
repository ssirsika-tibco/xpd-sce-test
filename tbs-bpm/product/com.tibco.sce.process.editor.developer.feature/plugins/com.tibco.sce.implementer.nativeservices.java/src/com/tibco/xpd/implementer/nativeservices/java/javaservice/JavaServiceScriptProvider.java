/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.properties.script.AbstractProcessScriptProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;

/**
 * @author nwilson
 */
public class JavaServiceScriptProvider extends AbstractProcessScriptProvider {

    private MappingDirection direction;

    /**
     * @param mappingDirection
     */
    public JavaServiceScriptProvider(String scriptGrammar,
            MappingDirection direction) {
        setScriptGrammar(scriptGrammar);
        this.direction = direction;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getDecriptionLabel()
     */
    @Override
    public String getDecriptionLabel() {
        String description;
        if (MappingDirection.IN.equals(direction)) {
            description = Messages.JavaServiceScriptProvider_InputMappingLabel;
        } else {
            description = Messages.JavaServiceScriptProvider_OutputMappingLabel;
        }
        return description;
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
                Expression expression =
                        ((ScriptInformation) input).getExpression();
                if (expression != null) {
                    script = expression.getText();
                }
            } else if (parent instanceof DataMapping) {
                if (MappingDirection.IN.equals(direction)) {
                    DataMapping mapping = (DataMapping) parent;
                    Expression expression = mapping.getActual();
                    if (expression != null) {
                        script = expression.getText();
                    }
                } else {
                    Expression expression =
                            ((ScriptInformation) input).getExpression();
                    if (expression != null) {
                        script = expression.getText();
                    }
                }
            }
        }
        return script;
    }

    /**
     * @param input
     * @param script
     * @param grammar
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getSetScriptCommand(org.eclipse.emf.ecore.EObject,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public Command getSetScriptCommand(EditingDomain ed, EObject input,
            String script, String grammar) {
        Command cmd = null;
        if (input instanceof ScriptInformation) {
            ScriptInformation information = (ScriptInformation) input;
            String oldScript = getScript(information);
            if (script != null && !script.equals(oldScript)) {
                cmd =
                        JavaServiceMappingUtil
                                .getSetJavaServiceScriptCommand(ed,
                                        script,
                                        information,
                                        grammar);
            }
        }
        return cmd;
    }

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
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject scriptContainer, String scriptGrammar) {
        // TODO Ravi - Need to add logic from erstwhile
        // AbstractScriptEditorSection
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.AbstractProcessScriptProvider#getDefaultDestination()
     * 
     * @return
     */
    @Override
    protected String getDefaultDestination() {
        return ProcessJsConsts.JSCRIPT_DESTINATION;
    }

}
