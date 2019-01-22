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
public class N2_08_ExternalRefOneWayWsdlTest extends AbstractWSDLGenForNonAPICompareTest {

    /**
     * @throws Exception
     */
    public void testExtRefOneWayWsdl() throws Exception {
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
                "/XPD365/Process Packages/ExternalReferenceProcess.xpdl")); //$NON-NLS-1$
    }
}
