package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Property Tester to test for XPDL2 package file
 * 
 * @author njpatel
 * 
 */
public class packageTester extends PropertyTester {

    public static final String PROP_ISXPDL2PACKAGE = "isXPDL2Package"; //$NON-NLS-1$

    private XpdResourcesPlugin resourcesPlugin = null;

    public packageTester() {
        resourcesPlugin = XpdResourcesPlugin.getDefault();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (receiver instanceof IFile) {
            IFile file = (IFile) receiver;

            // Test if the given file is a XPDL2 package file
            if (property.equals(PROP_ISXPDL2PACKAGE)) {
                IProject project = file.getProject();

                if (project != null) {
                    // Attempt to load the working copy of the file
                    XpdProjectResourceFactory fact = resourcesPlugin
                            .getXpdProjectResourceFactory(project);

                    if (fact != null) {
                        // Get the working copy of the file
                        WorkingCopy wc = fact.getWorkingCopy(file);

                        return (wc != null && !wc.isInvalidFile() && wc
                                .getWorkingCopyEPackage() instanceof Xpdl2Package);
                    }
                }
            }
        }

        return false;
    }

}
