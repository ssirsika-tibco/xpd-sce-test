/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;

/**
 * Tests if the xpdl's after conversion does not contain any iProcessScripts.
 * 
 * @author kthombar
 * @since 14-May-2014
 */
public class IpmBpm09_iProcessScriptToJavaScriptConversionTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        checkForiProcessScripts(convertedXpdls);
    }

    /**
     * @param convertedXpdls
     */
    private void checkForiProcessScripts(Collection<IFile> convertedXpdls) {
        for (IFile iFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);

            if (workingCopy == null) {
                fail("Working copy was found to be null"); //$NON-NLS-1$
            }

            EObject rootElement = workingCopy.getRootElement();

            if (rootElement instanceof Package) {
                Package pkg = (Package) rootElement;
                for (Iterator iterator = pkg.eAllContents(); iterator.hasNext();) {
                    EObject eo = (EObject) iterator.next();
                    if (eo instanceof Expression) {
                        Expression expression = (Expression) eo;

                        if (AbstractIProcessToJavaScriptConverter.IPROCESSSCRIPT_GRAMMAR
                                .equals(expression.getScriptGrammar())) {

                            fail("iProcess Scripts were found, however the xpdl after conversion should not contain any iProcess Scripts."); //$NON-NLS-1$
                        }
                    }
                }
            }
        }
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpmBpm09_iProcessScriptToJavaScriptConversionTest/ImportIpmXpdls/iProcessScriptToJavaScript.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpmBpm09_iProcessScriptToJavaScriptConversionTest/ImportIpmXpdls/dh8clmgt.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$

                };

        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        // TODO Auto-generated method stub
        return "IpmBpm09_iProcessScriptToJavaScriptConversionTest"; //$NON-NLS-1$
    }

}