/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import org.eclipse.core.resources.IProject;

/**
 * Interface for use of the
 * <code>com.tibco.xpd.core.testutil.testResourceManager</code> extension
 * point's <code>TestProjectManager</code> element.
 * <p>
 * Implementors of this class are given the opportunity to modify the project
 * after it has been created in the test workspace and before the tests are
 * executed.
 * 
 * @author aallway
 * @since 3.2
 */
public interface ITestProjectManager {

    void testProjectCreated(IProject project) throws Exception;
}
