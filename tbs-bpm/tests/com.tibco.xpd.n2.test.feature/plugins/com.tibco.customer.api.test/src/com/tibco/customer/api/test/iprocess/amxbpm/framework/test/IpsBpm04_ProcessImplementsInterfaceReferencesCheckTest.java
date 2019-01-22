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
 * @since 15-Jul-2014
 */
public class IpsBpm04_ProcessImplementsInterfaceReferencesCheckTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        assertEquals("Unexpected number of converted files", //$NON-NLS-1$
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
                if (iResource.getName().equals("MSTTEMPL.xpdl")) { //$NON-NLS-1$
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

        assertNotNull("Package name cannot be null at this point", packageName); //$NON-NLS-1$
        assertNotNull("Interface Id cannot be null at this point", interfaceID); //$NON-NLS-1$
        assertNotNull("Start method Id cannot be null at this point", //$NON-NLS-1$
                startMethodId);

        boolean testSuccessfull = false;

        for (IFile iFile : convertedXpdls) {
            if (iFile.getName().equals("MSTSUB.xpdl")) { //$NON-NLS-1$
                WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);
                Package pkg = (Package) workingCopy.getRootElement();

                EList<ExternalPackage> externalPackages =
                        pkg.getExternalPackages();

                assertNotNull("External Package cannot be null at this point", //$NON-NLS-1$
                        externalPackages);

                assertEquals("Expected External package not found", //$NON-NLS-1$
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

                    assertEquals("Implemented process id's do not match", //$NON-NLS-1$
                            interfaceID,
                            processInterfaceId);

                    assertEquals("The Package names of the implmented and the actual package do not match", //$NON-NLS-1$
                            packageName,
                            implementedIfc.getPackageRef());

                } else {
                    fail("The process is expected to implement an interface"); //$NON-NLS-1$
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
                        assertEquals("The implemented method Id's does not match", //$NON-NLS-1$
                                startMethodId,
                                implementStartMethodId);
                        testSuccessfull = true;
                    }
                }

                assertTrue("We expected the start event to implement the start method from interface, however no such event was found", //$NON-NLS-1$
                        foundImplementedStartEvent);

            }
        }

        assertTrue("Test did not run successfully", testSuccessfull); //$NON-NLS-1$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", "IpsBpm04_ProcessImplementsInterfaceReferencesCheckTest/Process Packages{processes}/test.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpsBpm04_ProcessImplementsInterfaceReferencesCheckTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

}
