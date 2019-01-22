/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Expression;

/**
 * Base class for performing iprocess to bpm conversion and comparing the
 * resulting scripts with predefined gold files.
 * <p>
 * Unlike the base test class, this class deals with a single file at a time and
 * the gold file must be loaded into working copy, the gold resource into should
 * be located in the "Process Packages" special folder AND be differently named
 * from the conversion output XPDL (i.e. test file).
 * <p>
 * <b>ALSO</b> because conversion will not output any process that already
 * exists in workspace, you will need to change the name of the processes in the
 * gold file.
 * 
 * @author aallway
 * @since 11 Jun 2014
 */
public abstract class AbstractIProcessConversionScriptComparisonTest extends
        AbstractIProcessConversionTest {

    /**
     * Return the gold file containing scripts to compare against the scripts in
     * the iProcess to bpm conversion result file.
     * <p>
     * Because the gold file must be loaded into working copy, the gold resource
     * into should be located in the "Process Packages" special folder AND be
     * differently named from the conversion output XPDL (i.e. test file).
     * <p>
     * <b>ALSO</b> because conversion will not output any process that already
     * exists in workspace, you will need to change the name of the processes in
     * the gold file.
     * 
     * @return The gold file containing scripts to compare against the scripts
     *         in the iProcess to bpm conversion result file.
     */
    protected abstract TestResourceInfo getGoldXpdlResourceInfo();

    /**
     * @return the single iProcess XPDL file to convert.
     */
    protected abstract TestResourceInfo getImportIProcessXpdlResourceInfo();

    /**
     * @return The expected conversion result file.
     */
    protected abstract TestResourceInfo getConvertedTestXpdlResourceInfo();

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public final TestResourceInfo[] getImportResourcesInfo() {
        return new TestResourceInfo[] { getImportIProcessXpdlResourceInfo() };
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // SHouldn't need this method for script conversion tests.
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        try {
            ResourcesPlugin
                    .getWorkspace()
                    .getRoot()
                    .refreshLocal(IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());
            TestUtil.buildAndWait();
            TestUtil.waitForJobs();

            IProject mainImportProject = getMainImportProject();

            SpecialFolder specialFolder =
                    SpecialFolderUtil.getSpecialFolderOfKind(mainImportProject,
                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);

            TestResourceInfo goldXpdlInfo = getGoldXpdlResourceInfo();

            TestResourceInfo testXpdlInfo = getConvertedTestXpdlResourceInfo();

            // DO THE COMPARISON BETWEEN GOLD FILES AND PRODUCED XPDLs
            doCompare(goldXpdlInfo.getTestFile(), testXpdlInfo.getTestFile());

        } catch (Exception e) {
            fail("testConvertedXpdls() Exception: " + e.getMessage() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Perform the comparison against between the test and gold file.
     * <p>
     * Finds all scripts in the gold file and compares with corresponding script
     * in the test conversion file.
     * 
     * @param goldFile
     * @param testFile
     */
    protected void doCompare(IFile goldFile, IFile testFile) throws Exception {
        Assert.assertTrue(String.format("Gold File '%1s' not found", //$NON-NLS-1$
                goldFile.getName()), (goldFile != null && goldFile.exists()));
        Assert.assertTrue(String.format("Test File '%1s' not found", //$NON-NLS-1$
                testFile.getName()), (testFile != null && testFile.exists()));

        WorkingCopy goldWC = WorkingCopyUtil.getWorkingCopy(goldFile);

        if (goldWC == null) {
            fail(String
                    .format("Gold File '%1$s' Working load failed: Ensure gold file TstResourceInfo is in \"Project/Process Packages{processes}xx.xpdl\"", goldFile.getFullPath().toString())); //$NON-NLS-1$
        }

        EObject goldPackage = goldWC.getRootElement();

        WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(testFile);

        if (testWC == null) {
            fail(String
                    .format("Test File '%1$s' Working copy was found to be null (Make sure your test file is the expected conversion output file", testFile.getFullPath().toString())); //$NON-NLS-1$
        }

        EObject testPackage = testWC.getRootElement();

        /*
         * Look for expressions in gold file.
         * 
         * TODO FOR NOW WE WILL RELY ON TreeIterator to iterate in models in the
         * same order. I THINK THAT THIS WILL WORK, IF NOT we'll have to find a
         * way of drilling down thru UniqueId elements and features within the
         * elements until we find the same script
         * 
         * Alternatively we could make life easy another way and Just tag the
         * start of each script with a unique keyword comment that can be used
         * to match the scripts up.
         */

        List<Expression> goldExpressions = getExpressions(goldPackage);

        List<Expression> testExpressions = getExpressions(testPackage);

        if (goldExpressions.size() != testExpressions.size()) {
            fail(String
                    .format("There are a different number of scripts in converted file (%d) than gold file (%d)", //$NON-NLS-1$
                            testExpressions.size(),
                            goldExpressions.size()));
        }

        for (int i = 0; i < goldExpressions.size(); i++) {
            String goldExpression =
                    removeWhitespace(goldExpressions.get(i).getText());
            if (goldExpression != null) {
                String testExpression =
                        removeWhitespace(testExpressions.get(i).getText());

                if (testExpression == null
                        || !goldExpression.equals(testExpression)) {
                    fail(String
                            .format("Text test expression locate at '%1$s' does not match the equivalent gold expression at '%2$s", //$NON-NLS-1$
                                    EcoreUtil.getURI(testExpressions.get(i)),
                                    EcoreUtil.getURI(goldExpressions.get(i))));
                }
            }
        }

    }

    /**
     * @param expr
     * @return expr with all whitespace chars removed OR null if expr is null
     */
    private String removeWhitespace(String expr) {

        if (expr != null) {
            StringBuffer out = new StringBuffer();
            for (int i = 0; i < expr.length(); i++) {
                char c = expr.charAt(i);

                if (!Character.isWhitespace(c)) {
                    out.append(c);
                }

            }

            return out.toString();
        }

        return null;

    }

    /**
     * @param goldPackage
     * @return list of all expressions in pkg.
     */
    private List<Expression> getExpressions(EObject pkg) {
        TreeIterator<Object> allGoldContents =
                EcoreUtil.getAllContents(pkg, true);

        List<Expression> expressions = new ArrayList<Expression>();

        for (Iterator iterator = allGoldContents; iterator.hasNext();) {
            Object o = iterator.next();

            if (o instanceof Expression) {
                expressions.add((Expression) o);
            }
        }
        return expressions;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {

        List<TestResourceInfo> resources = new ArrayList<TestResourceInfo>();

        TestResourceInfo[] testResourcesInfo = super.getTestResources();

        if (testResourcesInfo != null) {
            for (TestResourceInfo tri : testResourcesInfo) {
                resources.add(tri);
            }
        }

        TestResourceInfo goldResourceInfo = getGoldXpdlResourceInfo();
        if (goldResourceInfo != null) {
            resources.add(goldResourceInfo);
        }

        return resources.toArray(new TestResourceInfo[0]);

    }

}
