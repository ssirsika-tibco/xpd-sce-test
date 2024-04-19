/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.processscriptlibrary.resource.editor.input;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.internal.part.NullEditorInput;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process Script Library (PSL) editor persistence element factory. This is the class defined for the
 * <code>elementFactorys</code> extension point.
 * 
 * @author ssirsika
 * @since Jan 31, 2024
 * 
 */
public class ProcessScriptLibraryEditorPersist implements IElementFactory
{

	private static final String	ELEMENT_FACTORY_ID		= "com.tibco.xpd.processscriptlibraryeditor.psleditorelementfactory";	//$NON-NLS-1$

	private static final String	TAG_PSL					= "psl";																//$NON-NLS-1$

	private static final String	TAG_PATH				= "path";																//$NON-NLS-1$

	private static final String	TAG_SCRIPTFUNCTIONID	= "scriptfunctionid";													//$NON-NLS-1$

	/**
	 * Persistable Element object to persist the open PSL editors
	 * 
	 * @author ssirsika
	 * 
	 */
	public class PersistableElement implements IPersistableElement
	{

		private IFile		pslFile		= null;

		private Activity	activity	= null;

		/**
		 * @param aPslFile
		 * @param anActivity
		 */
		public PersistableElement(IFile aPslFile, Activity anActivity)
		{
			this.pslFile = aPslFile;
			this.activity = anActivity;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.IPersistableElement#getFactoryId()
		 */
		public String getFactoryId()
		{
			return ELEMENT_FACTORY_ID;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.IPersistable#saveState(org.eclipse.ui.IMemento)
		 */
		public void saveState(IMemento memento)
		{
			if (memento != null && pslFile != null && activity != null)
			{
				// Save the XPDL file name and process ID
				IMemento xpdlMemento = memento.createChild(TAG_PSL);
				xpdlMemento.putString(TAG_PATH, pslFile.getFullPath().toString());
				xpdlMemento.putString(TAG_SCRIPTFUNCTIONID, activity.getId());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IElementFactory#createElement(org.eclipse.ui.IMemento)
	 */
	@SuppressWarnings("restriction")
	public IAdaptable createElement(IMemento memento)
	{
		if (memento != null)
		{
			// Get the PSL memento
			IMemento pslMemento = memento.getChild(TAG_PSL);

			if (pslMemento != null)
			{
				// Get the path to the xpdl file
				String path = pslMemento.getString(TAG_PATH);
				// Get the activity id
				String activityId = pslMemento.getString(TAG_SCRIPTFUNCTIONID);

				if (path != null && activityId != null)
				{
					// Get the psl IFile
					IPath pslPath = new Path(path);

					IFile pslFile = (IFile) ResourcesPlugin.getWorkspace().getRoot().findMember(pslPath);

					if (pslFile != null)
					{
						// Get the working copy of the psl file
						WorkingCopy wc = getWorkingCopy(pslFile);

						if (wc != null)
						{
							// Get the process
							Package pkg = (Package) wc.getRootElement();

							if (pkg != null)
							{
								// Get the activity
								Activity activity = getActivity(pkg, activityId);

								if (activity != null)
								{
									// Return the PSL editor input
									return new ProcessScriptLibraryEditorInput(wc, activity);
								}
							}
						}
					}
				}
			}
		}

		return new NullEditorInput();
	}

	/**
	 * Get the {@link Activity} for passed activity ID.
	 * 
	 * @param pkg
	 * @param anActivityId
	 * @return
	 */
	private Activity getActivity(Package pkg, String anActivityId)
	{
		if (pkg != null)
		{
			Process process = pkg.getProcesses().get(0);
			return Xpdl2ModelUtil.getActivityById(process, anActivityId);
		}
		return null;
	}

	/**
	 * Get the {@link IPersistableElement} object for the PSL Editor
	 * 
	 * @param aPslFile
	 * @param anActivity
	 * @return <code>PersistableElement</code>
	 */
	public static IPersistableElement getPersistableElement(IFile aPslFile, Activity anActivity)
	{
		ProcessScriptLibraryEditorPersist instance = new ProcessScriptLibraryEditorPersist();

		return instance.new PersistableElement(aPslFile, anActivity);
	}

	/**
	 * Get the working copy of the given IFile.
	 * 
	 * @param pslFile
	 * 
	 * @return <code>WorkingCopy</code> of the given IFile, <code>null</code> if not a PSL or cannot get the working
	 *         copy
	 */
	private WorkingCopy getWorkingCopy(IFile pslFile)
	{
		if (pslFile != null && pslFile.getProject() != null)
		{
			WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(pslFile);

			if (workingCopy instanceof Xpdl2WorkingCopyImpl && ((Xpdl2WorkingCopyImpl) workingCopy)
					.isOneOfXpdl2FileType(new Xpdl2FileType[]{Xpdl2FileType.SCRIPT_LIBRARY}))
			{
				return workingCopy;
			}
		}

		return null;
	}

}
