package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.deploy.WorkspaceModule;

/**
 * This interface must be implemented by all wizards contributing
 * "deployWizards" extension.
 * <p>
 * <i>Created: 5 Jan 2007</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public interface IDeployWizard extends ISimpleDeployWizard {
    /**
     * Returns the list of URL's to be deployed. (local URLs - usually relative
     * to full system path to the file)
     * 
     * @return the list of module's URLs.
     */
    List<URL> getModulesUrls();

    /**
     * Returns the list of auto-deployable modules (a.k.a. WorkspaceModules) to
     * be set for the server.
     * 
     * @return the list of auto-deployable modules.
     */
    Collection<WorkspaceModule> getWorkspaceModules();
}
