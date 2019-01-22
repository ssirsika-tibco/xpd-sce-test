/**
 * 
 */
package com.tibco.xpd.deploy.ui.wizards;


/**
 * Represent WizardPage responsible for capturing repository's configuration
 * parameters - this is an extension of the {@link RepositoryConfigWizardPage}. 
 * Has been extended to enable implementing classes to be able to refresh the configuration 
 * and set or get the dirty status of the page.
 * 
 * @see RepositoryConfigWizardPage
 * <p>
 * <i>Created: 26 September 2008</i>
 * 
 * @author glewis
 * 
 */
public interface RepositoryConfigWizardPageExt extends
        RepositoryConfigWizardPage {
    /**
     * refreshes the details.
     */
    void refresh();
    
    /**
     * Sets the page as being dirty
     * @param dirty
     */
    void setDirty(boolean dirty);
    
    /**
     * Gets the dirty status of the page
     */
    boolean isDirty();
}
