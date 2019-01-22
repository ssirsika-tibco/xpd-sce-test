package com.tibco.xpd.capabilities.developer;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.internal.WWinPluginAction;

/**
 * 
 * <p>
 * <i>Created: 28 Mar 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeveloperStartup implements IStartup {

    @SuppressWarnings("restriction")
    public void earlyStartup() {
        WWinPluginAction.refreshActionList();
    }

}
