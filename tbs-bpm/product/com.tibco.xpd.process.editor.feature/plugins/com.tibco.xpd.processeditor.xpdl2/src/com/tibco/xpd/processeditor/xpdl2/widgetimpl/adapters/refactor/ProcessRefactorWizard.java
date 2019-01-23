/**
 * ProcessRefactorWizard.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

/**
 * ProcessRefactorWizard
 * 
 */
public abstract class ProcessRefactorWizard extends Wizard {

    private ProcessRefactorConfigurationPage configPage;

    /**
     * Any objec that the sub-class fancies.
     */
    Object inputObject = null;

    /**
     * Create process refactor wizard.
     * 
     * @param inputObject
     *            Any object that sub-class wants to use - is passed to all
     *            abstract methods so that caller can reference object even
     *            during construction and initialisation.
     */
    public ProcessRefactorWizard(Object inputObject) {
        super();

        this.inputObject = inputObject;

        init(inputObject);

        createPages();
    }

    /**
     * Because some abstract functions are called from constructor, you can
     * override this method to provide initialisation normally performed in
     * subclass constructor (or class variable initialisation)
     * 
     * @param inputObject
     */
    public void init(Object inputObject) {
        return;
    }

    /**
     * Sub-class returns a list of process refactor configuration items. If null
     * or empty list is returned then no configuration page is displayed.
     * 
     * Items in the configuration tree are updated with user's checkbox
     * selections during the processing of the dialog (regardless of wether user
     * cancels or finishes the wizard)
     * 
     * @return List of configuration items to present the user or null on error.
     */
    protected abstract List<ProcessRefactorConfigurationItem> getConfigurationItems(
            Object inputObject);

    /**
     * As the user checks / unchecks configuration items the original set of
     * configurations returned by getConfigurationItems() is updated with the
     * latest isChecked status.
     * <p>
     * After this is done the sub-class is asked whether the configuration is
     * now complete. The sub-class should check the configuration and return
     * <b>false</b> if any required configuration (or combination of
     * configurations) are not checked.
     * </p>
     * <p>
     * If false is returned then the next and finish buttons are disabled on the
     * wizard. Hence allowing the sub-class to control acceptance of only valid
     * configurations
     * </p>
     * 
     * @param configItems
     * @return
     */
    protected abstract boolean isConfigurationComplete(Object inputObject,
            List<ProcessRefactorConfigurationItem> configItems);

    /**
     * Sub-class provides the post configuration pages.
     */
    protected abstract List<WizardPage> getPostConfigPages(Object inputObject);

    /**
     * Create the wizard pages.
     */
    private void createPages() {
        // Get the refactor configuration items.
        List<ProcessRefactorConfigurationItem> configItems =
                getConfigurationItems(inputObject);
        if (configItems != null && configItems.size() > 0) {
            // Create the configuration page.
            configPage =
                    new ProcessRefactorConfigurationPage(this, configItems);
            addPage(configPage);
        }

        List<WizardPage> pages = getPostConfigPages(inputObject);
        if (pages != null && pages.size() > 0) {
            for (WizardPage page : pages) {
                addPage(page);
            }
        }

        postPageCreation();
    }

    /**
     * Override this method to perform any processing required after wizard
     * pages have been created.
     */
    protected void postPageCreation() {
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        return true;
    }

    /**
     * @return the configPage
     */
    public ProcessRefactorConfigurationPage getConfigPage() {
        return configPage;
    }

    /**
     * @param configPage
     *            the configPage to set
     */
    public void setConfigPage(ProcessRefactorConfigurationPage configPage) {
        this.configPage = configPage;
    }

    /**
     * @return the inputObject
     */
    public Object getInputObject() {
        return inputObject;
    }

    /**
     * @param inputObject
     *            the inputObject to set
     */
    public void setInputObject(Object inputObject) {
        this.inputObject = inputObject;
    }

}
