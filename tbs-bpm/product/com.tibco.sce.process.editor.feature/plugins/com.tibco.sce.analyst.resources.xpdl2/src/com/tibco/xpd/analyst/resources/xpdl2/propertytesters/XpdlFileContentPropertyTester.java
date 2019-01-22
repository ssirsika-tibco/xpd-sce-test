/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * XpdlFileContentPropertyTester
 * 
 * @author aallway
 * @since 3.2
 */
public class XpdlFileContentPropertyTester extends PropertyTester {

    /**
     * Is the given object an .xpdl2 process package file content.
     */
    private static final String IS_XPDL_FILECONTENT = "isXpdlFileContent"; //$NON-NLS-1$

    /**
     * Is the given object an .tasks (xpdl format file for task libraries)
     */
    private static final String IS_TASKS_FILECONTENT = "isTasksFileContent"; //$NON-NLS-1$

    /**
     * 
     */
    public XpdlFileContentPropertyTester() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (IS_XPDL_FILECONTENT.equals(property)) {
            if (receiver instanceof EObject) {
                return isXpdlFileContent((EObject) receiver);

            } else if (receiver instanceof INavigatorGroup) {
                Object o = ((INavigatorGroup) receiver).getParent();
                if (o instanceof EObject) {
                    return isXpdlFileContent((EObject) o);
                }
            }
        } else if (IS_TASKS_FILECONTENT.equals(property)) {
            if (receiver instanceof EObject) {
                return isTasksFileContent((EObject) receiver);

            } else if (receiver instanceof INavigatorGroup) {
                Object o = ((INavigatorGroup) receiver).getParent();
                if (o instanceof EObject) {
                    return isTasksFileContent((EObject) o);
                }
            }
        }
        return false;
    }

    /**
     * @param eObject
     * @return true if the given object is the child content of a bpmn process
     */
    public static boolean isXpdlFileContent(EObject eObject) {
        IFile file = WorkingCopyUtil.getFile(eObject);

        if (file != null) {
            if (Xpdl2ResourcesConsts.XPDL_EXTENSION.equals(file
                    .getFileExtension())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param eObject
     * @return true if the given object is the child content of a bpmn process
     */
    public static boolean isTasksFileContent(EObject eObject) {
        if (eObject != null) {
            IFile file = WorkingCopyUtil.getFile(eObject);

            if (file != null) {
                if (Xpdl2ResourcesConsts.TASKS_EXTENSION.equals(file
                        .getFileExtension())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param eObject
     * @return true if the given object is the child content of a bpmn process
     */
    public static boolean isDecisionFlowFileContent(EObject eObject) {
        if (eObject != null) {
            IFile file = WorkingCopyUtil.getFile(eObject);

            if (file != null) {
                if (Xpdl2ResourcesConsts.DECISION_FLOW_EXTENSION.equals(file
                        .getFileExtension())) {
                    return true;
                }
            }
        }
        return false;
    }

}
