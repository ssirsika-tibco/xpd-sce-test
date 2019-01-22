/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_group;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;

public class XSDBOM_Group04B_DuplicateRefs extends AbstractXSDBOMTest {

    public XSDBOM_Group04B_DuplicateRefs() {
        super("XSDBOM_Group04B_DuplicateRefs.xsd");
        setPlatformExampleFilesBase("platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd-group");
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#
     * testTransformation()
     */
    public void testTransformation() throws Exception {
        IPath outputBOMPath = getBomIPath();
        List<IStatus> result =
                importXSDtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(outputBOMPath.lastSegment()));

        List<IStatus> errors = getErrors(result);
        assertEquals(1, errors.size());
    }

    private IPath getBomIPath() {
        IPath outputBOMPath =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(testProjectName).getFullPath()
                        .append(TEST_FILE_NAME);
        return outputBOMPath;
    }
}
