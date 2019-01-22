/* 
** 
**  MODULE:             $RCSfile: TestUtil.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2005-08-17 $ 
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import com.tibco.xpd.simulation.provider.SimulationItemProviderAdapterFactory;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.provider.Xpdl2ItemProviderAdapterFactory;


/**
 * Utility class for tests.
 *
 * @author jarciuch
 */
public class TestUtil {
    
    private TestUtil() {}

    public static Resource getResource(String filePath) {
        Resource result = null;
        URI uri = URI.createFileURI(filePath);
        Factory fact = Resource.Factory.Registry.INSTANCE.getFactory(uri);
        result = fact.createResource(uri);
        try {
            result.load(null);
        } catch (IOException e) {
            result = null;
        }
        return result;
    }
    
    public static Package getXPDLPackage(String xpdlPath) {
        Resource resource = getResource(xpdlPath);
        
        if (resource != null && !resource.getContents().isEmpty()
                && resource.getContents().get(0) instanceof DocumentRoot) {
            Package xpdlPackage = ((DocumentRoot) resource.getContents().get(0))
                    .getPackage();
            return xpdlPackage;
        } else {
            return null;
        }
    }
    
    public static void listCollection(Collection c, String header) {
        System.out.println(header);
        for (Iterator iter = c.iterator(); iter.hasNext();) {
            System.out.println(iter.next());
        }
    }
   

    public static EditingDomain createEditingDomain() {
        // Create an adapter factory that yields item providers.
        //
        List factories = new ArrayList();
        factories.add(new ResourceItemProviderAdapterFactory());

        Xpdl2ItemProviderAdapterFactory xpdlItemProviderAdapterFactory = new Xpdl2ItemProviderAdapterFactory();
        factories.add(xpdlItemProviderAdapterFactory);
        factories.add(new SimulationItemProviderAdapterFactory());
        factories.add(new ReflectiveItemProviderAdapterFactory());

        ComposedAdapterFactory factory = new ComposedAdapterFactory(factories);

        BasicCommandStack commandStack = new BasicCommandStack();
        return new AdapterFactoryEditingDomain(factory, commandStack,
                new HashMap());
    }
    
}
