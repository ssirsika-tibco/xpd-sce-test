/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2;


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
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Process editor persistance element factory.  This is the class
 * defined for the <code>elementFactory</code> extension point
 * 
 * @author njpatel
 *
 */
public class ProcessEditorPersist implements IElementFactory {

	private static final String ELEMENT_FACTORY_ID = "com.tibco.xpd.processeditor.xpdl2.processelementfactory"; //$NON-NLS-1$

	private static final String TAG_XPDL = "xpdl"; //$NON-NLS-1$

	private static final String TAG_PATH = "path"; //$NON-NLS-1$

	private static final String TAG_PROCESSID = "processid"; //$NON-NLS-1$

	/**
	 * Persistable Element object to persist the open 
	 * process editors
	 * 
	 * @author njpatel
	 *
	 */
	public class PersistableElement implements IPersistableElement {

		private IFile xpdlFile = null;

		private Process process = null;

		/**
		 * @param xpdlFile
		 * @param process
		 */
		public PersistableElement(IFile xpdlFile, Process process) {
			this.xpdlFile = xpdlFile;
			// TODO Auto-generated constructor stub
			this.process = process;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.IPersistableElement#getFactoryId()
		 */
		public String getFactoryId() {
			return ELEMENT_FACTORY_ID;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.IPersistable#saveState(org.eclipse.ui.IMemento)
		 */
		public void saveState(IMemento memento) {
			if (memento != null && xpdlFile != null && process != null) {
				//Save the XPDL file name and process ID
				IMemento xpdlMemento = memento.createChild(TAG_XPDL);
				xpdlMemento.putString(TAG_PATH, xpdlFile.getFullPath().toString());
				xpdlMemento.putString(TAG_PROCESSID, process.getId());
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IElementFactory#createElement(org.eclipse.ui.IMemento)
	 */
	public IAdaptable createElement(IMemento memento) {
		if (memento != null) {
			// Get the XPDL memento
			IMemento xpdlMemento = memento.getChild(TAG_XPDL);

			if (xpdlMemento != null) {
				// Get the path to the xpdl file
				String path = xpdlMemento.getString(TAG_PATH);
				// Get the process id
				String processId = xpdlMemento.getString(TAG_PROCESSID);

				if (path != null && processId != null) {
					// Get the xpdl IFile
					IPath xpdlPath = new Path(path);

					if (xpdlPath != null) {
						IFile xpdlFile = (IFile) ResourcesPlugin.getWorkspace()
								.getRoot().findMember(xpdlPath);

						if (xpdlFile != null) {
							// Get the working copy of the xpdl file
							WorkingCopy wc = getWorkingCopy(xpdlFile);

							if (wc != null) {
								// Get the process
								Package pkg = (Package) wc.getRootElement();

								if (pkg != null) {
									// Get the process
									Process process = pkg.getProcess(processId);

									if (process != null) {
										// Return the process editor
										return new ProcessEditorInput(wc,
												process);
									}
								}
							}
						}
					}
				}
			}
		}
        
        NullEditorInput nEI = new NullEditorInput();
        
		return nEI;
	}
	
	/**
	 * Get the {@link IPersistableElement} object for the Process Editor
	 * 
	 * @param xpdlFile
	 * @param process
	 * @return <code>PersistableElement</code>
	 */
	public static IPersistableElement getPersistableElement (IFile xpdlFile, Process process) {
		ProcessEditorPersist instance = new ProcessEditorPersist();
		
		return instance.new PersistableElement(xpdlFile, process);
	}

	/**
	 * Get the working copy of the given IFile.
	 * 
	 * @param packageFile
	 * 
	 * @return <code>WorkingCopy</code> of the given IFile, <code>null</code>
	 *         if package is not XPDL2, or cannot get the working copy
	 */
	private WorkingCopy getWorkingCopy(IFile packageFile) {
		if (packageFile != null && packageFile.getProject() != null) {
			// Get the project resource factory
			XpdProjectResourceFactory fact = XpdResourcesPlugin.getDefault()
					.getXpdProjectResourceFactory(packageFile.getProject());

			if (fact != null) {
				WorkingCopy workingCopy = fact.getWorkingCopy(packageFile);

				// Check if it's a XPDL2 package
				if (workingCopy != null
						&& workingCopy.getWorkingCopyEPackage() == Xpdl2Package.eINSTANCE) {
					return workingCopy;
				}
			}
		}

		return null;
	}

}
