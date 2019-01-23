/* 
 ** 
 **  MODULE:             $RCSfile: XpdlBaseTestCase.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-18 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.preprocess;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.util.SimulationResourceFactoryImpl;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ResourceFactoryImpl;

import junit.framework.TestCase;

public class XpdlBaseTestCase extends TestCase {

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        // register xpdl model
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
                "xpdl", new Xpdl2ResourceFactoryImpl()); //$NON-NLS-1$
        EPackage.Registry.INSTANCE.put(Xpdl2Package.eNS_URI,
                Xpdl2Package.eINSTANCE);

        // register simulation model
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
                "simulation", new SimulationResourceFactoryImpl()); //$NON-NLS-1$
        EPackage.Registry.INSTANCE.put(SimulationPackage.eNS_URI,
                SimulationPackage.eINSTANCE);

    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        // unregister xpdl model
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().remove(
                "xpdl"); //$NON-NLS-1$
        EPackage.Registry.INSTANCE.remove(Xpdl2Package.eNS_URI);

        // unregister simulation model
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().remove(
                "simulation"); //$NON-NLS-1$
        EPackage.Registry.INSTANCE.remove(SimulationPackage.eNS_URI);

        super.tearDown();

    }

}
