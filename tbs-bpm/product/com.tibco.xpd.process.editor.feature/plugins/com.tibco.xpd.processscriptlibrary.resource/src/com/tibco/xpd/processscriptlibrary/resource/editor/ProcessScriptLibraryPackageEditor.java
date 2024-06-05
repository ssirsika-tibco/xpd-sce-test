/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Package editor for process script library editor.
 *
 * @author ssirsika
 * @since 26-Mar-2024
 */
public class ProcessScriptLibraryPackageEditor extends FormEditor
		implements PropertyChangeListener, INotifyChangedListener, ISaveablePart2, ISaveablesSource, IGotoMarker
{

	private static final String	PSL_PACKAGE_EDITOR_PAGE_ID	= "ProcessScriptLibraryPackageEditorPageID";	//$NON-NLS-1$

	private WorkingCopy			workingCopy;

	private ProcessScriptLibraryPackageEditorFormPage	formPage;

	/**
	 * @see org.eclipse.ui.forms.editor.FormEditor#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 *
	 * @param site
	 * @param input
	 * @throws PartInitException
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		super.init(site, input);
		updateTitle();

		if (workingCopy != null && workingCopy.getRootElement() != null)
		{
			AdapterFactory adapterFactory = workingCopy.getAdapterFactory();
			if (adapterFactory != null)
			{
				ItemProviderAdapter ip = (ItemProviderAdapter) adapterFactory.adapt(workingCopy.getRootElement(),
						workingCopy.getRootElement().eClass().getEPackage());
				ip.addListener(this);
			}
			workingCopy.addListener(this);
		}
	}

	/**
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 *
	 */
	@Override
	protected void addPages()
	{
		try
		{
			formPage = new ProcessScriptLibraryPackageEditorFormPage(this,
					PSL_PACKAGE_EDITOR_PAGE_ID, this.getPartName(), getWorkingCopy());
			addPage(formPage);

		}
		catch (PartInitException e)
		{
			ProcessScriptLibraryResourcePluginActivtor.getDefault().logError("Error while creating page", e); //$NON-NLS-1$
		}
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 *
	 * @param monitor
	 */
	@Override
	public void doSave(IProgressMonitor monitor)
	{
		// Do nothing
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 *
	 */
	@Override
	public void doSaveAs()
	{
		// Do nothing
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 *
	 * @return
	 */
	@Override
	public boolean isSaveAsAllowed()
	{
		return false;
	}

	/**
	 * Internal method to update the editor title.
	 */
	private void updateTitle()
	{
		WorkingCopy wc = getWorkingCopy();
		String title = ""; //$NON-NLS-1$
		if (wc != null)
		{
			title = wc.getName();
			if (wc.isReadOnly())
			{
				title += " " //$NON-NLS-1$
						+ Messages.ProcessScriptLibraryEditor_ReadOnly;
			}
		}
		setPartName(title);
	}

	/**
	 * @return {@link WorkingCopy} for editor input. Returned result is stored in the class field and will be used if
	 *         already initialized.
	 * 
	 */
	private WorkingCopy getWorkingCopy()
	{
		if (workingCopy == null)
		{
			IEditorInput editorInput = getEditorInput();
			if (editorInput instanceof FileEditorInput)
			{
				workingCopy = WorkingCopyUtil.getWorkingCopy(((FileEditorInput) editorInput).getFile());
			}
		}
		return workingCopy;
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 *
	 * @param evt
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		String propName = evt.getPropertyName();

		// If Working Copy changed or removed then close editor
		// else if Working Copy dirtied then fire dirty property change
		if (propName.equals(WorkingCopy.PROP_RELOADED) || propName.equals(WorkingCopy.PROP_REMOVED))
		{
			closeEditor();
		}
		else if (propName.equals(WorkingCopy.PROP_DIRTY))
		{
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		else if (propName.equals(WorkingCopy.CHANGED))
		{
			if (formPage != null && evt instanceof NotificationPropertyChangeEvent)
			{
				Collection<Notification> notifications = ((NotificationPropertyChangeEvent) evt).getNotifications();
				if (isRefreshRequired(notifications))
				{
					formPage.doRefreshSections();
				}
			}
		}
	}

	/**
	 * @param notifications
	 * @return
	 */
	private boolean isRefreshRequired(Collection<Notification> notifications)
	{
		for (Notification notification : notifications)
		{
			Object notifier = notification.getNotifier();
			if (notifier instanceof Process || notifier instanceof Activity)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param arg0
	 */
	@Override
	public void notifyChanged(Notification arg0)
	{
		/*
		 * If we are called outside of the display thread then we must perform the refresh on the display thread later -
		 * else we will cause invalid thread access exception and popup an error box
		 */
		if (Thread.currentThread() != Display.getDefault().getThread())
		{
			Display.getDefault().asyncExec(() -> updateTitle()); // NOSONAR
		}
		else
		{
			/* We're on the display thread so we can do it now. */
			updateTitle();
		}
	}

	/**
	 * Close editor.
	 */
	private void closeEditor()
	{
		Display d = PlatformUI.getWorkbench().getDisplay();
		d.syncExec(() -> getSite().getPage().closeEditor(ProcessScriptLibraryPackageEditor.this, false));
	}

	/**
	 * @see org.eclipse.ui.ISaveablePart2#promptToSaveOnClose()
	 *
	 * @return
	 */
	@Override
	public int promptToSaveOnClose()
	{
		/*
		 * Don't prompt the user to save on editor close when running in RCP application
		 */
		if (XpdResourcesPlugin.isRCP())
		{
			return NO;
		}

		return DEFAULT;
	}

	/**
	 * @see org.eclipse.ui.ISaveablesSource#getSaveables()
	 *
	 * @return
	 */
	@Override
	public Saveable[] getSaveables()
	{
		return getActiveSaveables();
	}

	/**
	 * @see org.eclipse.ui.ISaveablesSource#getActiveSaveables()
	 *
	 * @return
	 */
	@Override
	public Saveable[] getActiveSaveables()
	{
		if (workingCopy != null && workingCopy.getSaveable() != null)
		{
			return new Saveable[]{workingCopy.getSaveable()};
		}
		return new Saveable[0];
	}

	/**
	 * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
	 *
	 *      Sid ACE-8170 handle open script function editor for script/parameter problem markers in PSL files By default
	 *      the system will try to open the editor for the whole file (in our case also open open the PSL function
	 *      editor when asked to 'goto marker' call on the pakcage editor.
	 *
	 * @param marker
	 */
	@Override
	public void gotoMarker(IMarker marker)
	{
		/* Get the file and then working copy from the marker */
		XpdProjectResourceFactory factory = XpdResourcesPlugin.getDefault()
				.getXpdProjectResourceFactory(marker.getResource().getProject());

		IResource res = marker.getResource();
		WorkingCopy workingCopy = factory.getWorkingCopy(res);

		if (workingCopy.isInvalidFile() || !(workingCopy.getRootElement() instanceof Package))
		{
			return;
		}

		Package xpdlPackage = (Package) workingCopy.getRootElement();
		try
		{
			/* Get the specific model object that the marker was raised against. */
			String location = (String) marker.getAttribute(IMarker.LOCATION);

			Resource resource = xpdlPackage.eResource();
			if (resource != null)
			{
				EObject target = resource.getEObject(location);
				if (target != null)
				{
					/*
					 * If the target model object of the marker is an Activity OR something under an activity then open
					 * the function editor for the PSL function represented by the Activity
					 */
					Activity pslFunctionActivity = (Activity) Xpdl2ModelUtil.getAncestor(target, Activity.class);

					if (pslFunctionActivity != null)
					{
						IConfigurationElement facConfig = XpdResourcesUIActivator
								.getEditorFactoryConfigFor(pslFunctionActivity);

						if (facConfig != null)
						{
							String editorId = facConfig.getAttribute("editorID"); //$NON-NLS-1$

							EditorInputFactory f = (EditorInputFactory) facConfig.createExecutableExtension("factory"); //$NON-NLS-1$

							IEditorInput input = f.getEditorInputFor(pslFunctionActivity);

							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							IEditorPart part = IDE.openEditor(page, input, editorId);

							if (part instanceof IGotoMarker)
							{
								((IGotoMarker) part).gotoMarker(marker);
							}
						}
					}
				}
			}
		}
		catch (CoreException e)
		{
			// Ignore.
		}

	}

}
