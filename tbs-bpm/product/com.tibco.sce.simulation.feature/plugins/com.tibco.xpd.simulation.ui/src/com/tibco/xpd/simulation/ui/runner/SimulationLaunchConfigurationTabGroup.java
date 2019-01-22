/* 
** 
**  MODULE:             $RCSfile: SimulationLaunchConfigurationTabGroup.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2006-01-12 $ 
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

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class SimulationLaunchConfigurationTabGroup extends
        AbstractLaunchConfigurationTabGroup {

    public SimulationLaunchConfigurationTabGroup() {
    }

    public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
        ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
                new SimulationLaunchMainTab(), 
                new CommonTab()
            };
            setTabs(tabs);
    }

}
