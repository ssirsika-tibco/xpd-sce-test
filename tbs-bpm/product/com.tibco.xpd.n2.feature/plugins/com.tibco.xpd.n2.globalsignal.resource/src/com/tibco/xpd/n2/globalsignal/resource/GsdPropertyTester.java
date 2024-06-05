/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Property tester for GSD files
 * 
 * Sid ACE-8054
 * 
 * @author aallway
 * @since May 2024
 */
public class GsdPropertyTester extends PropertyTester {

    /**
	 * Is the given object an .gsd process package file content.
	 */
	private static final String IS_GSD_FILECONTENT = "isGsdFileContent"; //$NON-NLS-1$


    /**
	 * Constructor
	 */
    public GsdPropertyTester() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    @Override
	public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (IS_GSD_FILECONTENT.equals(property)) {
            if (receiver instanceof EObject) {
				return isGsdFileContent((EObject) receiver);

            } else if (receiver instanceof INavigatorGroup) {
                Object o = ((INavigatorGroup) receiver).getParent();
                if (o instanceof EObject) {
					return isGsdFileContent((EObject) o);
                }
            }
		}
        return false;
    }

    /**
     * @param eObject
     * @return true if the given object is the child content of a bpmn process
     */
	public static boolean isGsdFileContent(EObject eObject)
	{
        IFile file = WorkingCopyUtil.getFile(eObject);

        if (file != null) {
			if ("gsd".equals(file.getFileExtension()))
			{
                return true;
            }
        }
        return false;
    }

}
