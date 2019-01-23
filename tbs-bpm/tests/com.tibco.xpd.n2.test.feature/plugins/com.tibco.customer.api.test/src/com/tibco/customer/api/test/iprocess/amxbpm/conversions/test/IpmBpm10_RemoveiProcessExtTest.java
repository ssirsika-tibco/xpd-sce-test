/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;

/**
 * Tests that checks if all the iProcessExt: tags are removed after the
 * conversion from IPM XPDL to BPM XPDL.
 * 
 * @author kthombar
 * @since 14-May-2014
 */
public class IpmBpm10_RemoveiProcessExtTest extends AbstractIProcessConversionTest {

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
        checkForiProcessExtTags(convertedXpdls);
    }

    /**
     * @param convertedXpdls
     */
    private void checkForiProcessExtTags(Collection<IFile> convertedXpdls) {
        for (IFile iFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);

            if (workingCopy == null) {
                fail("Working copy was found to be null"); //$NON-NLS-1$
            }

            EObject rootElement = workingCopy.getRootElement();

            if (rootElement instanceof Package) {
                Package pkg = (Package) rootElement;
                for (Iterator iterator = pkg.eAllContents(); iterator.hasNext();) {

                    EObject obj = (EObject) iterator.next();

                    if (obj instanceof OtherElementsContainer) {
                        /* get other elements */
                        OtherElementsContainer eachAttribute =
                                (OtherElementsContainer) obj;

                        FeatureMap featureMap =
                                eachAttribute.getOtherElements();

                        checkForiProcessExtTags(obj, featureMap);
                    }

                    if (obj instanceof OtherAttributesContainer) {
                        /* get other attributes */
                        OtherAttributesContainer eachAttribute =
                                (OtherAttributesContainer) obj;

                        FeatureMap featureMap =
                                eachAttribute.getOtherAttributes();

                        checkForiProcessExtTags(obj, featureMap);
                    }
                }
            }
        }
    }

    /**
     * Remove the IProcess extension from the feature map passed.
     * 
     * @param obj
     * @param featureMap
     */
    private void checkForiProcessExtTags(EObject obj, FeatureMap featureMap) {

        Iterator<Entry> iterator = featureMap.iterator();

        for (; iterator.hasNext();) {

            Entry next = iterator.next();

            if (null != next.getEStructuralFeature()
                    && null != next.getEStructuralFeature()
                            .getEContainingClass()
                    && next.getEStructuralFeature().getEContainingClass()
                            .getEPackage() instanceof IProcessExtPackage) {

                fail("'iProcessExt' element was found in the converted XPDL, however the converted XPDL should not have any iProcessExt in it."); //$NON-NLS-1$

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
                                "resources/ConversionTests", "IpmBpm10_RemoveiProcessExtTest/ImportIpmXpdls/9EDTB.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpmBpm10_RemoveiProcessExtTest/ImportIpmXpdls/RemoveIProcExt.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpmBpm10_RemoveiProcessExtTest/ImportIpmXpdls/ProcIfc.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpmBpm10_RemoveiProcessExtTest/ImportIpmXpdls/ProcessCallingProcessInterface_ProcIfc.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$

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

        return "IpmBpm10_RemoveiProcessExtTest"; //$NON-NLS-1$
    }

}