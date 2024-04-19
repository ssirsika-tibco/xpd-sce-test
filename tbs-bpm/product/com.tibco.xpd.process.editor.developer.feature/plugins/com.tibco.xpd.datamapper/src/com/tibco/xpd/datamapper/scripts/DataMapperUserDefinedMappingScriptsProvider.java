/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.datamapper.scripts;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;

import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.process.js.model.script.AbstractProcessScriptProvider;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script provider for Process Data Mapper mapping script section - that is the
 * <b>User defined mapping script section</b> (not the data mapper script
 * section).
 * <p>
 * Sub-classes provide {@link AbstractScriptDataMapperEditorProvider} on construction
 * that allows this class to generically acces/create the
 * xpdExt:ScriptDataMapper element for the sub-class' given script context
 * (which in turn governs whe4re the ScripDataMapper element is stored.
 * 
 * @author Ali
 * @since 21 Jan 2015
 */
public final class DataMapperUserDefinedMappingScriptsProvider extends
        AbstractProcessScriptProvider {

    private AbstractScriptDataMapperEditorProvider scriptDataMapperEditorProvider;

    /**
     * @param scriptDataMapperEditorProvider
     */
    public DataMapperUserDefinedMappingScriptsProvider(
            AbstractScriptDataMapperEditorProvider scriptDataMapperEditorProvider) {
        super();
        this.scriptDataMapperEditorProvider = scriptDataMapperEditorProvider;
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getDecriptionLabel()
     */
    @Override
    public String getDecriptionLabel() {
        return Messages.DataMapperScriptsContentProvider_MapperScriptEditorTitle;
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
            // Script is always stored inside scripInformation instead
            // of Actual (which was the case before DataMapper feature)
            ScriptInformation information = (ScriptInformation) input;
            Expression expression = information.getExpression();
            if (expression != null) {
                script = expression.getText();
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
    public Command getSetScriptCommand(EditingDomain ed, EObject input,
            String script, String grammar) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.DataMapperScriptsContentProvider_EditMappingScript);

        if (input instanceof ScriptInformation) {
            ScriptInformation information = (ScriptInformation) input;
            String oldScript = getScript(information);
            if (script != null && !script.equals(oldScript)) {

                EObject parent = information.eContainer();

                if (parent instanceof ScriptDataMapper) {
                    Expression expression =
                            Xpdl2ModelUtil.createExpression(script);
                    expression.setScriptGrammar(grammar);
                    cmd.append(SetCommand.create(ed,
                            information,
                            XpdExtensionPackage.eINSTANCE
                                    .getScriptInformation_Expression(),
                            expression));

                } else if (parent == null) {

                    ScriptDataMapper scriptDataMapper =
                            scriptDataMapperEditorProvider
                                    .getOrCreateScriptDataMapper(information
                                            .getActivity(), ed, cmd);

                    information.setActivity(null);
                    information.setName(ScriptInformationUtil
                            .getNextScriptName(scriptDataMapper));
                    Expression expression =
                            Xpdl2ModelUtil.createExpression(script);
                    expression.setScriptGrammar(grammar);
                    information.setExpression(expression);
                    cmd.append(AddCommand.create(ed,
                            scriptDataMapper,
                            XpdExtensionPackage.eINSTANCE
                                    .getScriptDataMapper_UnmappedScripts(),
                            information));

                } else if (parent instanceof DataMapping) {

                    // We will always store script inside the scripInfo instead
                    // of Actual (which was the case before DataMapper feature)
                    Expression expression =
                            Xpdl2ModelUtil.createExpression(script == null ? "" //$NON-NLS-1$
                                    : script);
                    expression.setScriptGrammar(grammar);

                    cmd.append(SetCommand.create(ed,
                            information,
                            XpdExtensionPackage.eINSTANCE
                                    .getScriptInformation_Expression(),
                            expression));
                }
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
        // Not required as we don't show grammar selection inside the process
        // data mapper script section and it will be javascript only.
        return null;
    }

    /**
     * @see com.tibco.xpd.process.js.model.script.AbstractProcessScriptProvider#getDefaultDestination()
     * 
     * @return
     */
    @Override
    protected String getDefaultDestination() {
        return JsConsts.JSCRIPT_DESTINATION;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#isNewScriptInformation(org.eclipse.emf.ecore.EObject)
     * 
     * @param object
     * @return
     */
    @Override
    public Boolean isNewScriptInformation(EObject object) {
        boolean isNewSI = false;
        if (object instanceof ScriptInformation) {
            ScriptInformation scriptInformation = (ScriptInformation) object;
            if (scriptInformation.eContainer() == null) {
                isNewSI = true;
            }
        }
        return isNewSI;
    }

    /**
     * @see com.tibco.xpd.process.js.model.script.AbstractProcessScriptProvider#executeSaveCommand(org.eclipse.jface.text.IDocument)
     * 
     * @param document
     */
    @Override
	public void doExecuteSaveCommand(IDocument document)
	{
        String modifiedScript = document.get();
        if (getInput() == null) {
            return;
        }
        EObject eObject = getInput();
        if (eObject != null) {
            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(eObject);
            if (editingDomain == null && eObject instanceof ScriptInformation) {
                ScriptInformation script = (ScriptInformation) eObject;
                editingDomain =
                        WorkingCopyUtil.getEditingDomain(script.getActivity());

            }
            if (editingDomain != null) {
                Command setTaskScriptCommand =
                        getSetScriptCommand(editingDomain,
                                eObject,
                                modifiedScript,
                                getScriptGrammar());
                if (setTaskScriptCommand != null
                        && setTaskScriptCommand.canExecute()) {
                    editingDomain.getCommandStack()
                            .execute(setTaskScriptCommand);
                }
            }
        }
    }
}
