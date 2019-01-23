/**
 * 
 */
package com.tibco.xpd.n2.wsdlgen.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

/**
 * @author rsomayaj
 * 
 */
public class N2_09_SimpleTypeOneWayWsdlTest extends AbstractWSDLGenForNonAPICompareTest {

    public void testSimpleTypeOneWayWsdl() throws Exception {
        doTest();
    }

    /**
     * @see com.tibco.xpd.n2.wsdlgen.test.AbstractWSDLGenForNonAPICompareTest#getXpdlFileTested()
     * 
     * @return
     */
    @Override
    protected IFile getXpdlFileTested() {
        return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
                "/XPD365/Process Packages/SimpleTypeReferencePackage.xpdl")); //$NON-NLS-1$
    }
}
