/* 
** 
**  MODULE:             $RCSfile: ILaunchableAdapterFactory.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2006-02-08 $ 
** 
**  DESCRIPTION:           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: $ 
** 
*/
package com.tibco.xpd.simulation.ui.runner;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.ILaunchable;

public class LaunchableAdapterFactory implements IAdapterFactory {

    private Class[] supportedAdapterTypes = new Class[] {ILaunchable.class};
    
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        // Implemantation is not needed as it is only adapter factory for 
        // marking interface
        return null;
    }

    public Class[] getAdapterList() {
        return supportedAdapterTypes;
    }

}
