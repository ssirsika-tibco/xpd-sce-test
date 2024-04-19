/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInput;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * PslFileContentPropertyTester - Property Tester to test that a file is content of psl file model.
 *
 * @author nkelkar
 * @since Jan 17, 2024
 */
public class PslFileContentPropertyTester extends PropertyTester {

    /**
	 * Is the given object an .psl process package file content.
	 */
	private static final String	IS_PSL_FILECONTENT		= "isPslFileContent";	//$NON-NLS-1$

	/**
	 * Property name to check for ProcessScriptLibraryEditorInput type
	 */
	private static final String	IS_PSL_EDITOR_INPUT	= "isPslEditorInput";	//$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    @Override
	public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
		if (IS_PSL_FILECONTENT.equals(property))
		{
			if (receiver instanceof EObject)
			{
				return isPslFileContent((EObject) receiver);
			}
			else if (receiver instanceof INavigatorGroup)
			{
				Object o = ((INavigatorGroup) receiver).getParent();
				if (o instanceof EObject)
				{
					return isPslFileContent((EObject) o);

				}
			}
		}
		else if (IS_PSL_EDITOR_INPUT.equals(property))
		{
			return (receiver instanceof ProcessScriptLibraryEditorInput);
		}
        return false;
    }

    /**
	 * @param eObject
	 * @return true if the given object is the child content of a psl file
	 */
	public static boolean isPslFileContent(EObject eObject)
	{
		IFile file = WorkingCopyUtil.getFile(eObject);

		if (file != null)
		{
			if (ProcessScriptLibraryConstants.PSL_FILE_EXTENSION.equals(file.getFileExtension()))
			{
				return true;
			}
		}
		return false;
	}

}
