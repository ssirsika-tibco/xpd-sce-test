/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.editor;

import java.util.Collections;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInput;
import com.tibco.xpd.ui.properties.XpdPropertiesFormToolkit;

/**
 * Form page to show the main script editor section to handle script editing.
 *
 * @author ssirsika
 * @since 01-Feb-2024
 */
public class ProcessScriptLibraryFormPage extends FormPage
{

	private XpdPropertiesFormToolkit xpdFormToolkit;
	private FormToolkit formToolKit;

	private ProcessScriptLibraryScriptSection	pslScriptSection;

	/**
	 * @param editor
	 * @param id
	 * @param title
	 */
	public ProcessScriptLibraryFormPage(FormEditor editor, String id, String title)
	{
		super(editor, id, title);
	}

	/**
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 *
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm)
	{
		ScrolledForm scrolledForm = managedForm.getForm();
		formToolKit = new FormToolkit(scrolledForm.getDisplay());
		scrolledForm.getBody().setLayout(new GridLayout());

		Form form = formToolKit.createForm(scrolledForm.getBody());
		form.setLayoutData(new GridData(GridData.FILL_BOTH));
		form.getBody().setLayout(new GridLayout());
		formToolKit.decorateFormHeading(form);

		xpdFormToolkit = new XpdPropertiesFormToolkit(new TabbedPropertySheetWidgetFactory());

		pslScriptSection = new ProcessScriptLibraryScriptSection(this.getEditor());
		pslScriptSection
		.setInput(Collections.singleton(((ProcessScriptLibraryEditorInput) getEditorInput()).getActivity()));
		pslScriptSection.createControls(form.getBody(), xpdFormToolkit);
		pslScriptSection.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	@Override
	public void dispose()
	{
		formToolKit.dispose();
		xpdFormToolkit.dispose();
		super.dispose();
	}

	/**
	 * @see org.eclipse.ui.forms.editor.FormPage#setFocus()
	 *
	 */
	@Override
	public void setFocus()
	{
		super.setFocus();
		if (pslScriptSection != null)
		{
			pslScriptSection.refresh();
		}
	}
}
