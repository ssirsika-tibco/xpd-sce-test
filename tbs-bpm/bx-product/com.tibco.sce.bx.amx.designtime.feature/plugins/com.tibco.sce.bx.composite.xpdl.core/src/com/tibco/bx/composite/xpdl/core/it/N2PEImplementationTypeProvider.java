package com.tibco.bx.composite.xpdl.core.it;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.amx.model.service.N2PEServiceImplementation;
import com.tibco.bx.amx.model.service.ServiceFactory;
import com.tibco.bx.composite.core.BxCompositeCoreActivator;
import com.tibco.bx.composite.core.it.BxImplementationTypeProvider;
import com.tibco.bx.composite.core.util.BxCompositeCoreUtil;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.util.ConverterUtil;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;

public class N2PEImplementationTypeProvider extends BxImplementationTypeProvider {

	private static final IFile[] NO_BPEL_FILES = new IFile[0];
	
	/**
	 * Returns true when given path is supported by this implementation type.
	 * 
	 * @param path absolute path in the workspace
	 * @return
	 */
	public boolean isSupported(String path) {
		return XPDLUtils.isSupportedXpdlFile(new Path(path));
	}

	@Override
	protected IFile[] getBPELFiles(String path, URI compositeLocation, BxServiceImplementation bxImplementation) {
		IFile xpdlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(path));
		IFolder bpelRootFolder = BxCompositeCoreUtil
				.getBpelOutputFolder(compositeLocation);
		IFolder bpelFolder = bpelRootFolder.getFolder(ConverterUtil
				.getFileName(xpdlFile));
		try {
			ConverterUtil.buildXPDLFile(xpdlFile, bpelFolder, null);
			if (!bpelFolder.exists()) {
				return NO_BPEL_FILES;
			}
			
			IResource[] members = (IResource[]) bpelFolder.members();
			List<IFile> bpelFiles = new ArrayList<IFile>();
			for (IResource r : members) {
				if (!(r.getType() == IResource.FILE)
						|| !N2PEConstants.BPEL_EXTENSION.equals("."
								+ r.getFileExtension())) {
					continue;
				}
				bpelFiles.add((IFile) r);
			}
			return bpelFiles.toArray(new IFile[bpelFiles.size()]);
		} catch (CoreException e) {
			BxCompositeCoreActivator.log(e.getStatus());
		}
		return NO_BPEL_FILES;
	}

	protected N2PEServiceImplementation newImplementation() {
		return ServiceFactory.eINSTANCE.createN2PEServiceImplementation();
	}

	public String getHttpclientJNDIName(IFile implementationFile, String xpdlId) {
		if (xpdlId == null) {
			return null;
		}
		WorkingCopy workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy(implementationFile);
		if (workingCopy == null) {
			return null;
		}
		EObject resolveEObject = workingCopy.resolveEObject(xpdlId);
		if (!(resolveEObject instanceof Activity)) {
			return null;
		}
		Activity activity = (Activity) resolveEObject;
		return XPDLUtils.resolveWebServiceSimpleValue(activity);
	}

	@Override
	public String getEndpointUri(IFile implementationFile, String xpdlId) {
		if (xpdlId == null) {
			return null;
		}
		WorkingCopy workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy(implementationFile);
		if (workingCopy == null) {
			return null;
		}
		EObject resolveEObject = workingCopy.resolveEObject(xpdlId);
		if (!(resolveEObject instanceof Activity)) {
			return null;
		}
		Activity activity = (Activity) resolveEObject;
		return XPDLUtils.resolveWebServiceUri(activity);
	}
	
}
