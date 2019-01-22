/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Test that verifies that the fix in XPD-6375 works fine. Checks if post
 * conversion the process implmenting a process interface has proper refernce to
 * the interface , its start methods and xorrect external package reference.
 * 
 * @author kthombar
 * @since 19-Jun-2014
 */
public class IpmBpm11_ProcessImplementsInterfaceReferencesCheckTest extends
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
    @SuppressWarnings("nls")
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        assertEquals("Unexpected number of converted files",
                3,
                convertedXpdls.size());

        IContainer parent = null;

        for (IFile iFile : convertedXpdls) {
            parent = iFile.getParent();
            break;
        }

        String interfaceID = null;
        String startMethodId = null;
        String packageName = null;

        try {
            IResource[] members = parent.members();

            for (IResource iResource : members) {
                if (iResource.getName().equals("MSTTEMPL.xpdl")) {
                    /*
                     * we search the process interface that other processes will
                     * implement and store the process interface id , start
                     * method id and package name so that we can verify later
                     * that the process which implements this interface has
                     * correct refernces.
                     */
                    WorkingCopy workingCopy =
                            WorkingCopyUtil.getWorkingCopy(iResource);

                    Package rootElement =
                            (Package) workingCopy.getRootElement();

                    packageName = rootElement.getName();

                    ProcessInterfaces processInterfaces =
                            ProcessInterfaceUtil
                                    .getProcessInterfaces(rootElement);

                    EList<ProcessInterface> processInterface =
                            processInterfaces.getProcessInterface();

                    ProcessInterface ifc = processInterface.get(0);

                    interfaceID = ifc.getId();

                    EList<StartMethod> startMethods = ifc.getStartMethods();

                    startMethodId = startMethods.get(0).getId();

                }
            }
        } catch (CoreException e) {

            e.printStackTrace();
        }

        assertNotNull("Package name cannot be null at this point", packageName);
        assertNotNull("Interface Id cannot be null at this point", interfaceID);
        assertNotNull("Start method Id cannot be null at this point",
                startMethodId);

        boolean testSuccessfull = false;

        for (IFile iFile : convertedXpdls) {
            if (iFile.getName().equals("MSTSUB.xpdl")) {
                WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
                Package pkg = (Package) workingCopy.getRootElement();

                EList<ExternalPackage> externalPackages =
                        pkg.getExternalPackages();

                assertNotNull("External Package cannot be null at this point",
                        externalPackages);

                assertEquals("Expected External package not found",
                        packageName,
                        externalPackages.get(0).getHref());

                Process process = pkg.getProcesses().get(0);
                Object otherElement =
                        Xpdl2ModelUtil
                                .getOtherElement(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ImplementedInterface());

                if (otherElement instanceof ImplementedInterface) {
                    /* go ahead only if the process implements an interface */
                    ImplementedInterface implementedIfc =
                            (ImplementedInterface) otherElement;

                    String processInterfaceId =
                            implementedIfc.getProcessInterfaceId();

                    assertEquals("Implemented process id's do not match",
                            interfaceID,
                            processInterfaceId);

                    assertEquals("The Package names of the implmented and the actual package do not match",
                            packageName,
                            implementedIfc.getPackageRef());

                } else {
                    fail("The process is expected to implement an interface");
                }

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                boolean foundImplementedStartEvent = false;

                for (Activity activity : allActivitiesInProc) {
                    Object obj =
                            Xpdl2ModelUtil.getOtherAttribute(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Implements());
                    if (obj != null) {
                        foundImplementedStartEvent = true;
                        String implementStartMethodId = (String) obj;
                        assertEquals("The implemented method Id's does not match",
                                startMethodId,
                                implementStartMethodId);
                        testSuccessfull = true;
                    }
                }

                assertTrue("We expected the start event to implement the start method from interface, however no such event was found",
                        foundImplementedStartEvent);

            }
        }

        assertTrue("Test did not run successfully", testSuccessfull);
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", "IpmBpm11_ProcessImplementsInterfaceReferencesCheckTest/ImportIpmXpdls/MSTMAIN.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm11_ProcessImplementsInterfaceReferencesCheckTest"; //$NON-NLS-1$
    }

}
