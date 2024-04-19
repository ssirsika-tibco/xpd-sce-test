/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInput;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.IRefreshableTitle;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Process Script Library editor to edit the Javascript expressions stored in the activities in PSL file.
 *
 * @author ssirsika
 * @since 24-Jan-2024
 */
public class ProcessScriptLibraryEditor extends FormEditor implements ITabbedPropertySheetPageContributor,
		INotifyChangedListener, DisposeListener, PropertyChangeListener, IGotoMarker, ISaveablePart2, IRefreshableTitle
{

	/**
	 * This ID should be same as defined while contributing to the
	 * org.eclipse.ui.views.properties.tabbed.propertyContributor extension point in the plugin.xml.
	 */
	private static final String				PROPERTY_CONTRIBUTOR_ID	= "com.tibco.xpd.processscriptlibrary.resource.propertyContributor";	//$NON-NLS-1$

	private ProcessScriptLibraryFormPage formPage;

	private ISelectionProvider				selectionProvider	= new ProcessScriptLibrarySelectionProvider(this);

	private ActionRegistry					actions;

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 *
	 * @param monitor
	 */
	@Override
	public void doSave(IProgressMonitor monitor)
	{
		final String toggleMsg = Messages.ProcessScriptLibraryEditor_dontAskInFuture;
		ProcessScriptLibraryEditorInput pslEditorInput = (ProcessScriptLibraryEditorInput) getEditorInput();

		IPreferenceStore prefStore = Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
		boolean prefAskSave = prefStore.getBoolean(ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE);
		if (!prefAskSave)
		{
			MessageDialogWithToggle saveAllProcsDialog = MessageDialogWithToggle.openOkCancelConfirm(
					Display.getCurrent().getActiveShell(), Messages.ProcessScriptLibraryEditor_SaveDialogTitle,
					Messages.ProcessScriptLibraryEditor_SaveDialogMessage,
					toggleMsg,
					prefAskSave, prefStore,
					ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE);

			prefAskSave = saveAllProcsDialog.getToggleState();
			prefStore.setValue(ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE, prefAskSave);

			prefStore.setValue(ProcessEditorConstants.PREF_SAVE_EDITOR, saveAllProcsDialog.getReturnCode());

			if (IDialogConstants.OK_ID == saveAllProcsDialog.getReturnCode())
			{
				saveWorkingCopy(pslEditorInput);
			}
		}
		else
		{
			if (IDialogConstants.OK_ID == prefStore.getInt(ProcessEditorConstants.PREF_SAVE_EDITOR))
			{
				saveWorkingCopy(pslEditorInput);
			}
		}
	}

	private void saveWorkingCopy(ProcessScriptLibraryEditorInput input) throws WrappedException
	{
		try
		{
			input.getWorkingCopy().save();
		}
		catch (IOException e)
		{
			throw new WrappedException(e);
		}
		finally
		{
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}
	/**
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 *
	 */
	@Override
	public void doSaveAs()
	{
		// Do nothing as not supported.
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 *
	 * @param site
	 * @param input
	 * @throws PartInitException
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		super.init(site, input);
		site.setSelectionProvider(selectionProvider);
		if (!(input instanceof ProcessScriptLibraryEditorInput))
		{
			closeEditor();
			throw new PartInitException(Messages.ProcessScriptLibraryEditor_EditorInpurError + input);
		}
		ProcessScriptLibraryEditorInput pslEditorInput = (ProcessScriptLibraryEditorInput) input;

		WorkingCopy workingCopy = pslEditorInput.getWorkingCopy();

		// Add listeners
		ItemProviderAdapter ip = (ItemProviderAdapter) workingCopy.getAdapterFactory()
				.adapt(pslEditorInput.getActivity(), pslEditorInput.getActivity().eClass().getEPackage());
		ip.addListener(this);
		workingCopy.addListener(this);

		updateTitle();
		setTitleToolTip(pslEditorInput.getToolTipText());
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 *
	 * @return
	 */
	@Override
	public boolean isDirty()
	{
		ProcessScriptLibraryEditorInput pslEditorInput = (ProcessScriptLibraryEditorInput) getEditorInput();
		return pslEditorInput.getWorkingCopy().isWorkingCopyDirty();
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 *
	 * @return
	 */
	@Override
	public boolean isSaveAsAllowed()
	{
		// Not possible as we are only editing the Activity and not entire PSL file.
		return false;
	}

	@Override
	public Object getAdapter(Class adapter)
	{
		if (adapter == IPropertySheetPage.class)
		{
			return new TabbedPropertySheetPage(this);
		}
		return super.getAdapter(adapter);
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 *
	 */
	@Override
	public void setFocus()
	{
		formPage.setFocus();
	}

	/**
	 * Return action associated to the Editor with given ID, returns null when not found.
	 * 
	 * @param id
	 * @return {@link IAction} associated with this id.
	 */

	public IAction getAction(String id)
	{
		if (actions == null)
		{
			createActions();
		}
		return actions.getAction(id);
	}

	/**
	 * Create set of actions for PSL Editor
	 */
	protected void createActions()
	{
		IEditorInput editorInput = getEditorInput();
		if (editorInput instanceof ProcessScriptLibraryEditorInput)
		{
			ProcessScriptLibraryEditorInput pslEditorInput = (ProcessScriptLibraryEditorInput) editorInput;
			WorkingCopy workingCopy = pslEditorInput.getWorkingCopy();

			if (getSite() != null && workingCopy instanceof AbstractTransactionalWorkingCopy)
			{
				actions = new ActionRegistry();
				IUndoContext undoContext = ((AbstractTransactionalWorkingCopy) workingCopy).getUndoContext();
				UndoActionHandler undo = new UndoActionHandler(getSite(), undoContext);
				undo.setId(ActionFactory.UNDO.getId());
				actions.registerAction(undo);

				RedoActionHandler redo = new RedoActionHandler(getSite(), undoContext);
				redo.setId(ActionFactory.REDO.getId());
				actions.registerAction(redo);
			}
		}
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
	 * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
	 *
	 * @param marker
	 */
	@Override
	public void gotoMarker(IMarker marker)
	{
		// TODO Auto-generated method stub

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
	}

	/**
	 * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
	 *
	 * @param e
	 */
	@Override
	public void widgetDisposed(DisposeEvent e)
	{
		IEditorInput editorInput = getEditorInput();
		if (editorInput instanceof ProcessScriptLibraryEditorInput)
		{
			ProcessScriptLibraryEditorInput pslEditorInput = (ProcessScriptLibraryEditorInput) editorInput;
			WorkingCopy workingCopy = pslEditorInput.getWorkingCopy();
			if (workingCopy != null)
			{
				ItemProviderAdapter ip = (ItemProviderAdapter) workingCopy.getAdapterFactory()
						.adapt(pslEditorInput.getActivity(), pslEditorInput.getActivity().eClass().getEPackage());
				ip.removeListener(this);
				workingCopy.removeListener(this);
			}
		}
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
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
	 *
	 * @return
	 */
	@Override
	public String getContributorId()
	{
		return PROPERTY_CONTRIBUTOR_ID;
	}


	/**
	 * @see com.tibco.xpd.resources.ui.IRefreshableTitle#refreshTitle()
	 *
	 */
	@Override
	public void refreshTitle()
	{
		updateTitle();
		firePropertyChange(PROP_TITLE);
	}

	/**
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 *
	 */
	@Override
	protected void addPages()
	{
		formPage = new ProcessScriptLibraryFormPage(this, "processscriptlibrary.editor", //$NON-NLS-1$
				getPartName());

		try
		{
			addPage(formPage);
		}
		catch (PartInitException e)
		{
			e.printStackTrace();
		}

	}

	private void closeEditor()
	{
		Display d = PlatformUI.getWorkbench().getDisplay();
		d.syncExec(() -> getSite().getPage().closeEditor(ProcessScriptLibraryEditor.this, false));
	}

	/**
	 * Internal method to update the editor title.
	 */
	private void updateTitle()
	{
		ProcessScriptLibraryEditorInput pslEditorInput = (ProcessScriptLibraryEditorInput) getEditorInput();

		/*
		 * XPD-1140: Show correct title to same as editor title (display name (tokenname) ( + [Read-Only] as
		 * appropriate)
		 */
		WorkingCopy workingCopy = pslEditorInput.getWorkingCopy();

		String title = pslEditorInput.getName();
		if (workingCopy != null && workingCopy.isReadOnly())
		{
			title += " " + Messages.ProcessScriptLibraryEditor_ReadOnly; //$NON-NLS-1$
		}
		setPartName(title);
	}
	/**
	 * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
	 *
	 */
	@Override
	public void dispose()
	{
		widgetDisposed(null);
		super.dispose();
	}

	/**
	 * Filter to test for Activity present in PSL.
	 *
	 * @author ssirsika
	 * @since 24-Jan-2024
	 */
	public static class ProcessScriptEditorFilter implements IFilter
	{

		@Override
		public boolean select(Object toTest)
		{
			if (toTest instanceof Activity)
			{
				return PslEditorUtil.isScriptLibraryFunction(toTest);
			}
			return false;
		}
	}
}
