/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.ui.internal;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.sourceviewer.client.CommandProvider;
import com.tibco.xpd.script.sourceviewer.client.IScriptInfoProvider;
import com.tibco.xpd.script.sourceviewer.client.IScriptInfoProviderExt;

/**
 * Abstract Script Provider implements the interfaces that provide inputs to the
 * Script Editor section. These methods need to be over-riden to provide
 * appropriate implementation.
 * 
 * @author rsomayaj
 * 
 */
/**
 * @author rsomayaj
 * 
 */
public abstract class AbstractScriptInfoProvider implements
        IScriptDetailsProvider, IScriptInfoProvider, IScriptInfoProviderExt, CommandProvider {

    private EObject input;

    private String scriptContext;

    private String scriptGrammar;
    
    private List<IScriptRelevantData> cachedSrdList = null;

    /**
     * @see com.tibco.xpd.script.ui.internal.IScriptDetailsProvider#getDecriptionLabel()
     * 
     * @return
     */
    @Override
	public String getDecriptionLabel() {
        return null;
    }

    /**
     * XPD-1440:
     * 
     * Return the descriptive label for the script (defaults to
     * {@link #getDecriptionLabel()} unless overridden.
     * 
     * @param input
     * @return descriptive label.
     */
    public String getDescriptionLabel(EObject input) {
        return getDecriptionLabel();
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.IScriptDetailsProvider#getRelevantEObject(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
	public EObject getRelevantEObject(EObject input) {
        return null;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.IScriptDetailsProvider#getScript(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
	public String getScript(EObject input) {
        return null;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.IScriptDetailsProvider#getSetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
     * 
     * @param ed
     * @param input
     * @param script
     * @param grammar
     * @return
     */
    @Override
	public Command getSetScriptCommand(EditingDomain ed, EObject input,
            String script, String grammar) {
        // Subclass implementation required.
        return UnexecutableCommand.INSTANCE;
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
        // Subclass implementation required.
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.IScriptDetailsProvider#isNewScriptInformation(org.eclipse.emf.ecore.EObject)
     * 
     * @param object
     * @return
     */
    @Override
	public Boolean isNewScriptInformation(EObject object) {
        return Boolean.FALSE;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.client.IScriptInfoProvider#getComplexScriptRelevantData()
     * 
     * @return
     */
    @Override
	public List getComplexScriptRelevantData() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.client.IScriptInfoProvider#getScriptRelevantData()
     * 
     * @return
     */
    @Override
	public List<IScriptRelevantData> getScriptRelevantData() {
        return Collections.EMPTY_LIST;
    }

    /**
	 * @see com.tibco.xpd.script.sourceviewer.client.CommandProvider#executeSaveCommand(org.eclipse.jface.text.IDocument)
	 * 
	 * @param document
	 * @return {true} if the save command is executed sucessfully or else returns {false} in when save command is not
	 *         executed or aborted. Since AbstractScriptInfoProvider implements executeSaveCommand as default , we have
	 *         defaulted it to true. (i.e. SubClasses of AbstractScriptInfoProvider can implement their own version of
	 *         executeSaveCommand)
	 */
    @Override
	public boolean executeSaveCommand(IDocument document)
	{
		return true;
    }

    /**
     * @return the input
     */
    public EObject getInput() {
        return input;
    }

    /**
     * Sets the input object
     * 
     * @param input
     */
    public void setInput(EObject input) {
        this.input = input;
    }

    /**
     * @return the scriptContext
     */
    public String getScriptContext() {
        return scriptContext;
    }

    /**
     * @return the scriptGrammar
     */
    public String getScriptGrammar() {
        return scriptGrammar;
    }

    /**
     * @param scriptContext
     *            the scriptContext to set
     */
    public void setScriptContext(String scriptContext) {
        this.scriptContext = scriptContext;
    }

    /**
     * @param scriptGrammar
     *            the scriptGrammar to set
     */
    public void setScriptGrammar(String scriptGrammar) {
        this.scriptGrammar = scriptGrammar;
    }
    

    public List<IScriptRelevantData> getCachedSrdList() {
        return cachedSrdList;
    }
    
    public void setCachedSrdList(List<IScriptRelevantData> cachedSrdList) {
        this.cachedSrdList = cachedSrdList;
    }
    
    @Override
	public void clearCachedInfo() {
//        ParseUtil.nulifySRDReferences(getCachedSrdList());
        setCachedSrdList(null);
    }

	/**
	 * @see com.tibco.xpd.script.sourceviewer.client.CommandProvider#getScriptFromModel()
	 *
	 * @return
	 */
	@Override
	public String getScriptFromModel()
	{
		return getScript(getInput());
	}
}
