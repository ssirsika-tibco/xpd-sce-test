/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.processeditor.xpdl2.packageeditor.HyperLinkListViewer;
import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInput;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * Main page for {@link ProcessScriptLibraryPackageEditor}
 *
 * @author ssirsika
 * @since 28-Mar-2024
 */
public class ProcessScriptLibraryPackageEditorFormPage extends FormPage
{

	private FormToolkit			formToolKit;

	private Form				form;

	private HyperLinkListViewer	functionLinksViewer;

	private WorkingCopy			workingCopy;

	private AddFunctionAction	addFnAction;

	/**
	 * The constructor.
	 *
	 * @param id
	 *            a unique page identifier
	 * @param title
	 *            a user-friendly page title
	 */
	public ProcessScriptLibraryPackageEditorFormPage(String id, String title)
	{
		super(id, title);
	}

	/**
	 * A constructor.
	 *
	 * @param editor
	 *            the parent editor
	 * @param id
	 *            the unique identifier
	 * @param title
	 *            the page title
	 * @param workingCopy
	 *            Current working to access the model.
	 */
	public ProcessScriptLibraryPackageEditorFormPage(FormEditor editor, String id, String title,
			WorkingCopy workingCopy)
	{
		super(editor, id, title);
		this.workingCopy = workingCopy;
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
		scrolledForm.getBody().setLayout(new FillLayout());

		form = formToolKit.createForm(scrolledForm.getBody());

		form.setText(PslEditorUtil.removePSLExtension(getTitle()));

		form.setImage(ProcessScriptLibraryResourcePluginActivtor.getDefault().getImageRegistry()
				.get(ProcessScriptLibraryConstants.IMG_SCRIPT_FILE));

		form.getBody().setLayout(new GridLayout());

		formToolKit.decorateFormHeading(form);

		Composite editorPageComposite = formToolKit.createComposite(form.getBody());
		editorPageComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		editorPageComposite.setLayout(new GridLayout());

		addFnAction = new AddFunctionAction();

		createFunctionSection(editorPageComposite);

		form.getToolBarManager().add(addFnAction);

		form.getToolBarManager().update(true);
	}

	/**
	 * Create section for PSL functions
	 * 
	 * @param editorPageComposite
	 *            Parent composite
	 */
	private void createFunctionSection(Composite editorPageComposite)
	{
		Section functionSection = formToolKit.createSection(editorPageComposite,
				ExpandableComposite.EXPANDED | ExpandableComposite.TITLE_BAR);

		functionSection.setText(Messages.ScriptFunctionGroup_title);

		functionSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		functionLinksViewer = new HyperLinkListViewer(functionSection, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		functionLinksViewer.getControl().setLayoutData(new GridData());

		functionLinksViewer.setContentProvider(new ArrayContentProvider());
		functionLinksViewer.setLabelProvider(new FunctionLinksViewerLabelProvider(workingCopy.getAdapterFactory()));

		functionLinksViewer.setHyperLinkListener(new HyperlinkClickListener());

		functionLinksViewer.setInput(getLinkViewerInput());

		functionSection.setClient(functionLinksViewer.getControl());
	}

	/**
	 * @return Object array which represents input to the Link viewer.
	 */
	private Object[] getLinkViewerInput()
	{
		List<Object> result = new ArrayList<>();
		// Add function action
		result.add(addFnAction);

		// Separator
		result.add(HyperLinkListViewer.SEPARATOR);

		// Functions
		result.addAll(getSortedActivities());
		return result.toArray();
	}

	/**
	 * @return List of {@link Activity} i.e PSL functions present in the present working copy in natural alphabetical
	 *         order.
	 */
	private List<Activity> getSortedActivities()
	{
		Process process = getProcess();
		if (process != null)
		{
			List<Activity> activities = new ArrayList<>();
			activities.addAll(process.getActivities());
			Collections.sort(activities, new Comparator<Activity>()
			{
				@Override
				public int compare(Activity a1, Activity a2)
				{
					return a1.getName().compareTo(a2.getName());
				}
			});
			return activities;
		}
		return Collections.emptyList();
	}

	/**
	 * @return {@link Process} for present working copy
	 */
	private com.tibco.xpd.xpdl2.Process getProcess()
	{
		if (workingCopy != null)
		{
			EObject rootElement = workingCopy.getRootElement();
			if (rootElement instanceof com.tibco.xpd.xpdl2.Package)
			{
				return ((com.tibco.xpd.xpdl2.Package) rootElement).getProcesses().get(0);
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.ui.forms.editor.FormPage#setFocus()
	 * 
	 */
	@Override
	public void setFocus()
	{
		super.setFocus();
		if (form != null)
		{
			form.setFocus();
		}
	}

	/**
	 * Enable/disable actions according to read-only state.
	 */
	private void enableActions()
	{
		boolean enabled = (workingCopy != null && !workingCopy.isReadOnly());
		addFnAction.setEnabled(enabled);
	}

	/**
	 * Refresh the link viewer and update the action state.
	 */
	public void doRefreshSections()
	{
		Composite body = form.getBody();
		if (body != null && !body.isDisposed())
		{
			enableActions();
			functionLinksViewer.setInput(getLinkViewerInput());
			functionLinksViewer.getControl().requestLayout();
		}
	}

	/**
	 * @return the form
	 */
	public Form getForm()
	{
		return form;
	}

	/**
	 * @see org.eclipse.ui.forms.editor.FormPage#isDirty()
	 *
	 * @return
	 */
	@Override
	public boolean isDirty()
	{
		if (workingCopy != null)
		{
			return workingCopy.isWorkingCopyDirty();
		}
		return false;
	}

	@Override
	public void dispose()
	{
		if (functionLinksViewer != null)
		{
			functionLinksViewer.dispose();
		}

		formToolKit.dispose();
		super.dispose();
	}

	/**
	 * Open the PSL editor for passed activity. Note that passed 'anActivity' should not be NULL.
	 * 
	 * @param anActivity
	 *            activity on which PSL editor need to open.
	 */
	public void openPSLEditor(Activity anActivity)
	{
		if (workingCopy != null)
		{
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try
			{
				IDE.openEditor(page, new ProcessScriptLibraryEditorInput(workingCopy, anActivity),
						PslEditorUtil.PROCESS_SCRIPT_LIBRARY_EDITOR_ID);
			}
			catch (PartInitException e1)
			{
				ProcessScriptLibraryResourcePluginActivtor.getDefault().logError(Messages.ProcessScriptLibraryPackageEditorFormPage_PSLEditorOpenError, e1);
			}
		}
	}

	/**
	 * Click listener for the hyperlink.
	 *
	 * @author ssirsika
	 * @since 29-Mar-2024
	 */
	class HyperlinkClickListener extends HyperlinkAdapter
	{

		/**
		 * @see org.eclipse.ui.forms.events.HyperlinkAdapter#linkActivated(org.eclipse.ui.forms.events.HyperlinkEvent)
		 *
		 * @param e
		 */
		@Override
		public void linkActivated(HyperlinkEvent e)
		{
			Object href = e.getHref();
			if (href instanceof Activity)
			{
				Activity activity = (Activity) href;
				openPSLEditor(activity);
			}
			else if (href instanceof Action)
			{
				((Action) href).run();
			}
		}
	}

	/**
	 * Action to add new function to the process script library.
	 * 
	 * @author ssirsika
	 * @since 02-Apr-2024
	 */
	class AddFunctionAction extends Action
	{
		/**
		 * 
		 */
		public AddFunctionAction()
		{
			super(Messages.ProcessScriptLibraryPackageEditorFormPage_CreatePSLFnDesc, ProcessScriptLibraryResourcePluginActivtor.getDefault()
					.getImageRegistry().getDescriptor(ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION_NEW));
		}

		/**
		 * @see org.eclipse.jface.action.Action#run()
		 *
		 */
		@Override
		public void run()
		{
			Process process = getProcess();
			Activity newActivity = PslEditorUtil.addNewScriptFunctionInProcess(process);
			if (newActivity != null)
			{
				openPSLEditor(newActivity);
			}
		}
	}

	/**
	 * Label provider for LinksViewer which displays the PSL functions.
	 *
	 * @author ssirsika
	 * @since 02-Apr-2024
	 */
	class FunctionLinksViewerLabelProvider extends AdapterFactoryLabelProvider
	{

		/**
		 * @param adapterFactory
		 */
		public FunctionLinksViewerLabelProvider(AdapterFactory adapterFactory)
		{
			super(adapterFactory);
		}

		/**
		 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getText(java.lang.Object)
		 *
		 * @param object
		 * @return
		 */
		@Override
		public String getText(Object object)
		{
			if (object instanceof AddFunctionAction)
			{
				return ((AddFunctionAction) object).getText();
			}
			else if (object instanceof Activity)
			{
				return PslEditorUtil.generateMethodSignature((Activity) object, false, false);
			}
			return super.getText(object);
		}

		/**
		 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getImage(java.lang.Object)
		 *
		 * @param object
		 * @return
		 */
		@Override
		public Image getImage(Object object)
		{
			if (object instanceof AddFunctionAction)
			{
				return ProcessScriptLibraryResourcePluginActivtor.getDefault().getImageRegistry()
						.get(ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION_NEW);
			}

			return ProcessScriptLibraryResourcePluginActivtor.getDefault().getImageRegistry()
					.get(ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION);
		}
	}
}
