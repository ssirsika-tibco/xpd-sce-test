package com.tibco.bx.xpdl2bpel.util;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.converter.XPDL2BPEL;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.ValidationException;


public class ConverterUtil {
    
	public static void buildProject(IProject project, IFolder destFolder, IProgressMonitor monitor) throws CoreException {
		List<IResource> processFiles = SpecialFolderUtil.getAllDeepResourcesInSpecialFolderOfKind(
				project, XPDLUtils.PROCESSES_SPECIAL_FOLDER_KIND, "xpdl", false); //$NON-NLS-1$
		for (IResource xpdlFile: processFiles) {
			IFolder processFolder = destFolder.getFolder(getFileName(xpdlFile));
			buildXPDLFile((IFile)xpdlFile, processFolder, monitor);
		}
	}

	/**
	 * convert xpdl file
	 * 
	 * @param xpdlFile:
	 *            the xpdl file
	 * @param destFolder:
	 *            the dest folder of conversion.if the folder not exist then it
	 *            will be created
	 * @param monitor
	 * @throws CoreException 
	 */
	public static void buildXPDLFile(IResource xpdlFile, IFolder destFolder, IProgressMonitor monitor) throws CoreException {
		//only xpdl files that pass the N2PE validations will be converted
		if (!XPDLUtils.isSupportedXpdlFile(xpdlFile)) {
			return;
		}
		
		IFile file = (IFile) xpdlFile;
		if (!IsValidForN2PE(file)) {
			return;
		}
        
        if (destFolder.exists()) {
        	destFolder.delete(true, monitor);
        }
        
        createFolder(destFolder, monitor);

        //XPDL2WSDL.convertXPDLFile2WSDLs(file, destFolder, monitor);
		
		XPDL2BPEL converter = new XPDL2BPEL(file);
        IStatus status = converter.convertToBPEL(destFolder.getFullPath().toOSString());
        if (!status.isOK()) {
            throw new CoreException(status);
        }
	}

	public static boolean IsValidForN2PE(IFile xpdlFile) throws CoreException {
		try {
			Collection<IIssue> issues = ConverterActivator.getValidator().validate(xpdlFile);
			if (issues != null) {
				for (IIssue issue : issues) {
					if (issue.getSeverity() == IMarker.SEVERITY_ERROR) {
						return false;
					}
				}
			}
		} catch (ValidationException e) {
			IStatus status = ConverterActivator.createErrorStatus(
					Messages.getString("XpdProjectBuilder.validationFailed") + xpdlFile.getFullPath(), e); //$NON-NLS-1$
	        throw new CoreException(status);
		}
		
		return true;
	}


    /**
	 * A simple way to convert an object that might be null to a String.
	 *
	 * @param object
	 *            An object to run toString on.
	 * @return A String representation of the object parameter.
	 */
    public static String toString(java.lang.Object object) {
		return ((object == null) ? "" : object.toString()); //$NON-NLS-1$
	}

	public static EObject clone(EObject eObject) {
		Copier copier = new Copier();
		EObject result = copier.copy(eObject);
		copier.copyReferences();
		return result;
	}

	/**
	 * create folder
	 * 
	 * @param folder:
	 *            the folder will be created
	 * @param monitor
	 * @throws CoreException
	 */
	public static void createFolder(IFolder folder, IProgressMonitor monitor)
			throws CoreException {
		while (!folder.getParent().exists() && folder.getParent().getType() == IResource.FOLDER) {
			createFolder((IFolder) folder.getParent(), monitor);
		}
		if (!folder.exists()) {
			folder.create(true, true, monitor);
		}
		return;
	}

	/**
     * creates a folder for a given path
     * 
     * @param absolutePath:
     *            the path
     */
	public static void createFolder(String absolutePath) {
		File file = new File(absolutePath);
		while (!file.getParentFile().exists()) {
			createFolder(file.getParent());
		}
		if (!file.exists() || file.isFile()) {
			file.mkdir();
		}
	}
	
    /**
     * returns the name of a file without extension
     * 
     * @param resource:
     *            the file
     * @return the file name without the extension
     */
	public static String getFileName(IResource resource) {
        if (resource != null) {
            String name = resource.getName();
            String fileExtension = resource.getFileExtension();
            if (fileExtension != null && fileExtension.length() > 0
                    && name.length() > fileExtension.length() - 1) {
                name = name.substring(
                		0, 
                		name.length() - fileExtension.length() - 1);
            }
            return name;
        }
        
        return null;
    }

}
