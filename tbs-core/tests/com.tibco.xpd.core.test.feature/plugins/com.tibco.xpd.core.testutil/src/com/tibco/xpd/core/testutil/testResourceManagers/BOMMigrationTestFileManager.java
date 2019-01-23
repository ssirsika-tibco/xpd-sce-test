/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.testutil.testResourceManagers;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.core.test.util.ITestFileManager;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Test Resource Manager that contributes BOM file migration for any bom file
 * loaded into test workspace via {@link TestResourceInfo}.
 * <p>
 * Contributed via the <code>com.tibco.core.testutil.testResourceManager</code>
 * extension point.
 * 
 * @author mtorres
 * @since 3.5
 */
public class BOMMigrationTestFileManager implements ITestFileManager {

    public void testFileCreated(IFile testFile) throws Exception {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);
        if (wc instanceof AbstractWorkingCopy) {
            ((AbstractWorkingCopy) wc).migrate();
        }
    }

}
