package com.tibco.inteng;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.impl.Application;
import com.tibco.inteng.resources.ResourceLocator;
import com.tibco.inteng.security.SecurityPlugIn;

public interface InteractionRepository {
	
	/**
	 * Get InteractionImpl (xpdl package) from repository.
	 * 
	 * If repositore does not contains this interaction it try to
	 * load it from resource locator
	 * 
	 * @param packageID - id of package (it is passed to resource locator
	 * to locate it)
	 * @return interaction - xpdl package from repository
	 * @throws IOException - on IO errors
	 */
	public abstract Package getPackage(String packageID)
			throws IOException;
	/**
	 * 
	 * @param messageBundle
	 * @param loc
	 * @return
	 */
	public abstract ResourceBundle getMessagesBundle(String messageBundle,
			Locale loc);
	/**
	 * Sets (and overide) current resource locator, repository cache will
	 * be cleared
	 * 
	 * @param locator - new ResourceLocator
	 */
	public abstract void setResourceLocator(ResourceLocator locator);
	
	/**
	 * Resource locator for this repository.
	 * 
	 * Resource locator is shared accross interactions in this repository, in addition
	 * every interaction state can have special resource locator (which has fallback to 
	 * this one)
	 * @return resoourceLocator associated with InteractionRepositoryImpl
	 */
	public abstract ResourceLocator getResourceLocator();

	/**
	 * Delegate of AutomaticApplicationLocator, allows to install new application
	 * inplementation to Repository
	 * 
	 * @param key - name of the implementation ('implementation' extended atribute)
	 * @param application - automatic application to install
	 */
	public abstract void installAutomaticApplication(String key,
			AutomaticApplication application);

	/**
	 * check if application is installed
	 * 
	 * @param key - name of application ('implementation' extended attribute in XPDL)
	 * @return true, is application is installed
	 */
	public abstract boolean isApplicationInstalled(String key);

	/**
	 * Install set of default 'build-in' applications:
	 *  - 'staticApp'   - static application use configuration file to simulate service
	 *  - 'xsltApp'     - xlst transformation
	 *  - 'populateApp' - init/copy between formal parameters
	 *  - 'dummyApp'    - placeholder, do noting
	 *  - 'scriptApp'   - execute script on formal parameters 
	 */
	public abstract void installDefaultApplications();
	/**
	 * @param app
	 * @return
	 */
	public abstract AutomaticApplication getAutomaticApplication(
			Application app);	
	/**
	 * This method will set the SecurityPlugIn for the InteractionRepository
	 * @param securityPlugIn
	 */
	public abstract void setSecurityPlugIn(SecurityPlugIn securityPlugIn);

	/**
	 * This method will return the SecurityPlugIn for the InteractionRepository
	 * @return
	 */
	public abstract SecurityPlugIn getSecurityPlugIn();
	
	/**
	 * This method expects a String representation of the xml document which 
	 * contains the process state. It returns an instance of ProcessState which 
	 * can be further executed by the InteractionEngine.
	 * 
	 * @param xml, String representing the state of the process
	 * @return ProcessState object
	 */
	public abstract ProcessState restoreState(String xml) throws IOException;
    
    /**
     * This will help the user to set the parameters which will override the
     * default behaviour while loading a package.
     * 
     * @param configPrameters
     */
    public abstract void setConfigParameters(Map configPrameters);

    /**
     * Returns the overriden behaviour while loading a package.
     * 
     * @return
     */
    public abstract Map getConfigParameters();

	

}