package com.tibco.bx.emulation.bpm.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.emulation.bpm.core.common.BomProjectManager;


public class BusinessModelChangeListener implements IResourceChangeListener, IResourceDeltaVisitor{
	private final static String CLASS_FILE_EXTENSION = "class";//$NON-NLS-1$
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
			return;
		}
		
		try {
			if(event.getDelta()!=null){
				event.getDelta().accept(this);
			}
		} catch (CoreException e) {
			//can't happen
		}
	}

	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		if(resource.getType() == IResource.PROJECT ){
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
			case IResourceDelta.OPEN:
			case IResourceDelta.CHANGED:
			case IResourceDelta.CONTENT:
				BomProjectManager.INSTANCE.addProject((IProject)resource);
				break;
			case IResourceDelta.REMOVED:
				BomProjectManager.INSTANCE.removeProject((IProject)resource);
				break;
			default:
				break;
			}
			return false;
		}
		return true;
	}
	
	private boolean isEMFModelPackageClass(IFile file){
		if(file.getName().endsWith("PackageImpl." + CLASS_FILE_EXTENSION)//$NON-NLS-1$
				&& "impl".equals(file.getParent().getName())){//$NON-NLS-1$
			return true;
		}
		return false;
	}
}
