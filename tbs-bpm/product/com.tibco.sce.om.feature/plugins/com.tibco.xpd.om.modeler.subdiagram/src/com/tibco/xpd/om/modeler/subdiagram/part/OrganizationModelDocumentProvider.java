/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.part;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;

import com.tibco.xpd.om.resources.wc.InputStreamOMWorkingCopy;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;
import com.tibco.xpd.resources.wc.gmf.WorkingCopyDocumentProvider;

/**
 * @generated NOT
 */
public class OrganizationModelDocumentProvider extends
		WorkingCopyDocumentProvider {
	private final Map<IStorageEditorInput, AbstractGMFWorkingCopy> workingCopyMap = new HashMap<IStorageEditorInput, AbstractGMFWorkingCopy>();

	@Override
	protected boolean isInputValid(Object editorInput) {
		boolean isValid = super.isInputValid(editorInput);
		if (!isValid && editorInput instanceof IStorageEditorInput) {
			isValid = true;
		}
		return isValid;
	}

	@Override
	protected void disposeElementInfo(Object element, ElementInfo info) {
		super.disposeElementInfo(element, info);
		if (element instanceof IStorageEditorInput) {
			AbstractGMFWorkingCopy wc = workingCopyMap.get(element);
			if (wc != null) {
				wc.dispose();
				workingCopyMap.remove(element);
			}
		}
	}

	@Override
	protected AbstractGMFWorkingCopy getWorkingCopy(IEditorInput editorInput)
			throws CoreException {
		/*
		 * Add support for resources opened from storage (revision history)
		 */
		AbstractGMFWorkingCopy wc = super.getWorkingCopy(editorInput);
		if (wc == null) {
			if (editorInput instanceof IStorageEditorInput) {
				wc = workingCopyMap.get(editorInput);
				if (wc == null) {
					IStorage storage = ((IStorageEditorInput) editorInput)
							.getStorage();
					IFile file = ResourcesPlugin.getWorkspace().getRoot()
							.getFile(storage.getFullPath());

					wc = new InputStreamOMWorkingCopy(file, storage);
					((InputStreamOMWorkingCopy) wc).setEditorInput(editorInput);
					workingCopyMap.put((IStorageEditorInput) editorInput, wc);
				}
			} else if (editorInput instanceof URIEditorInput) {
				/*
				 * User has clicked to open the Organization from an
				 * Organization Model opened from revision history so find the
				 * correct working copy for this resource from the local cache.
				 */
				URI uri = ((URIEditorInput) editorInput).getURI();

			}
		}
		return wc;
	}
}