/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.editor;

import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.forms.editor.FormEditor;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.task.TaskScriptSection;
import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Section for PSL scripts.
 *
 * 
 * @author ssirsika
 * @since 29-Jan-2024
 */
public class ProcessScriptLibraryScriptSection extends TaskScriptSection
{

	private FormEditor editor;

	public ProcessScriptLibraryScriptSection(FormEditor editor)
	{
		super();
		/*
		 * Save the editor for later because we will need it to get the UI site, which we need for refresh purposes see
		 * {@link #getSite()} below for more details
		 */
		this.editor = editor;
	}

	@Override
	public String getScriptContext()
	{
		return ProcessScriptContextConstants.PROCESS_SCRIPT_LIBRARY_FUNCTION;
	}

	/**
	 * @see org.eclipse.ui.IPluginContribution#getLocalId()
	 *
	 * @return
	 */
	@Override
	public String getLocalId()
	{
		return "processscriptlibrary.editor"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.ui.IPluginContribution#getPluginId()
	 *
	 * @return
	 */
	@Override
	public String getPluginId()
	{
		return ProcessScriptLibraryResourcePluginActivtor.PLUGIN_ID;
	}

	/**
	 * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptDefinitionLabel()
	 *
	 * @return
	 */
	@Override
	protected String getScriptDefinitionLabel()
	{
		Object editorInput = getEditorInput();
		if (editorInput instanceof Activity)
		{
			return PslEditorUtil.generateMethodSignature((Activity) editorInput, true, true);
		}
		return super.getScriptDefinitionLabel();
	}

	/**
	 * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#shouldParseScriptDefinitionLabel()
	 *
	 * @return
	 */
	@Override
	protected boolean shouldParseScriptDefinitionLabel()
	{
		// Parse as method signature is html form.
		return true;
	}

	/**
	 * @see com.tibco.xpd.ui.properties.AbstractXpdSection#getSite()
	 * 
	 *      Sid - If we don't override the getSite() function then, ultimately, the section refresh will not function
	 *      correctly.
	 * 
	 *      This is because although the section adds itself to the editingdomain postcommit listeners (see
	 *      {@link AbstractTransactionalSection#createResourceSetListener()}) AND the listener is called it's
	 *      resourceSetChanged() method will not ultimately call the section refresh() method BECAUSE getSite() returns
	 *      null because the default implementation returns the site via the TabbedPropertySheetPage stored in the
	 *      section.
	 * 
	 *      But WE don't use the constructor that provides a TabbedPropertySheetPage so WE must provide the editor site.
	 * 
	 *      AbstractTransacitonalSection resource set listener must have access to workbench site so that it can
	 *      guarantee running the refresh in UI associated with the editor site.
	 * 
	 * @return
	 */
	@Override
	protected IWorkbenchSite getSite()
	{
		IWorkbenchSite site = super.getSite();
		if (site == null)
		{
			site = editor.getSite();
		}
		return site;
	}

	/**
	 * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#isUseOwnScriptInfoProviderInstance()
	 *
	 *      Force creation of individual ScriptInfoprovider instances for each and every script library function editor
	 *      (rather than the standard 'share same cached singleton instance for every section')
	 *      
	 *      Each can then have it's own input function model object without interfering with other editors.
	 *
	 * @return
	 */
	@Override
	protected boolean isUseOwnScriptInfoProviderInstance()
	{
		return true;
	}
}
