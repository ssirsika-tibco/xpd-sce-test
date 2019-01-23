/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.pe.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.amx.model.service.ServiceFactory;
import com.tibco.bx.composite.core.it.BxImplementationTypeProvider;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.pe.util.PEN2Utils;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * @author kupadhya
 * 
 */
public class PEImplementationTypeProvider extends BxImplementationTypeProvider {

    private static final String PROCESSES_SPECIAL_FOLDER_KIND = "processes"; //$NON-NLS-1$

    /**
     * @see com.tibco.amf.sca.componenttype.implementation.ImplementationTypeProvider#isSupported(java.lang.String)
     * 
     * @param arg0
     * @return
     */
    @Override
    public boolean isSupported(String path) {
        String massagePath = massagePath(path);
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(massagePath));
        if (xpdlFile != null && xpdlFile.isAccessible()) {
            String fileExtension = xpdlFile.getFileExtension();
            if ("xpdl".equalsIgnoreCase(fileExtension) //$NON-NLS-1$
                    && isProcessSpecialFolder(xpdlFile.getParent())) {
                return true;
            }

        }

        return false;
    }

    public static boolean isProcessSpecialFolder(IResource folder) {
        return folder instanceof IFolder
                && PROCESSES_SPECIAL_FOLDER_KIND.equals(SpecialFolderUtil
                        .getSpecialFolderKind((IFolder) folder));
    }

    @Override
    protected IFile[] getBPELFiles(String path, URI compositeLocation,
            BxServiceImplementation bxImplementation) {
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(path));
        if (xpdlFile != null && xpdlFile.isAccessible()) {
            String fileExtension = xpdlFile.getFileExtension();
            if ("xpdl".equalsIgnoreCase(fileExtension) //$NON-NLS-1$
                    && isProcessSpecialFolder(xpdlFile.getParent())) {
                return getBpelFiles(xpdlFile);

            }
        }
        return new IFile[0];

    }

    protected IFile[] getBpelFiles(IFile xpdlFile) {
        IFile[] bpelFiles = BPELN2Utils.getBusinessProcessBpelFiles(xpdlFile);
        return bpelFiles;
    }

    protected String getFilePathAppendString() {
        return PEN2Utils.PROCESS_FLOW_APPEND;
    }

    @Override
    protected BxServiceImplementation newImplementation() {
        return ServiceFactory.eINSTANCE.createN2PEServiceImplementation();
    }

    /*
     * XPD-4867: we were having these methods to get the endpoint uri from bx
     * code. now we do this in SOAPBindingUtil as we handle the composite
     * generation info in XPD. we no longer need to have these methods. so
     * commenting the code for now, in the next iteration can remove this code
     */

    // @Override
    // public String getHttpclientJNDIName(IFile implementationFile, String
    // xpdlId) {
    // if (xpdlId == null) {
    // return null;
    // }
    // WorkingCopy workingCopy =
    // XpdResourcesPlugin.getDefault()
    // .getWorkingCopy(implementationFile);
    // if (workingCopy == null) {
    // return null;
    // }
    // EObject resolveEObject = workingCopy.resolveEObject(xpdlId);
    // if (!(resolveEObject instanceof Activity)) {
    // return null;
    // }
    // Activity activity = (Activity) resolveEObject;
    // return XPDLUtils.resolveWebServiceSimpleValue(activity);
    // }
    //
    // @Override
    // public String getEndpointUri(IFile implementationFile, String xpdlId) {
    // if (xpdlId == null) {
    // return null;
    // }
    // WorkingCopy workingCopy =
    // XpdResourcesPlugin.getDefault()
    // .getWorkingCopy(implementationFile);
    // if (workingCopy == null) {
    // return null;
    // }
    // EObject resolveEObject = workingCopy.resolveEObject(xpdlId);
    // if (!(resolveEObject instanceof Activity)) {
    // return null;
    // }
    // Activity activity = (Activity) resolveEObject;
    // return XPDLUtils.resolveWebServiceUri(activity);
    // }

    @Override
    public Implementation createImplementation(String path,
            URI compositeLocation) {
        String massagePath = massagePath(path);
        return super.createImplementation(massagePath, compositeLocation);
    }

    private String massagePath(String path) {
        IFile resolvePathToFile = resolvePathToFile(path);
        if (resolvePathToFile == null || !resolvePathToFile.isAccessible()) {
            return path;
        }
        return resolvePathToFile.getFullPath().toPortableString();
    }

    private IFile resolvePathToFile(String path) {
        final String filePathAppendString = getFilePathAppendString();
        if (path != null && path.endsWith(filePathAppendString)) {
            path = path.replaceFirst(filePathAppendString + "$", ""); //$NON-NLS-1$ //$NON-NLS-2$
            IPath xpdlFilePath = new Path(path);
            IFile xpdlFile =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getFile(xpdlFilePath);
            return xpdlFile;
        }
        return null;
    }

}
