/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.views.properties.tabbed.ITabItem;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.tibco.xpd.ui.properties.internal.util.XpdPropertiesUtil;
import com.tibco.xpd.ui.properties.wizards.BasicNewBpmResourceWizard;

/**
 * Wizard that use propery page sections to provide create the object. Use the
 * wizard alone and configure it using TemplateFactory or extend it by
 * subclassing
 * 
 * 
 * @author wzurek
 */

public abstract class CreationWizard implements IWizard,
        ITabbedPropertySheetPageContributor, XpdSectionInputContainerProvider {

    public interface TemplateFactory {
        EObject createTemplate();

        Command createCommand(EObject input, IWizardPage locationPage);
    }

    private IWizardPage locationPage;

    protected EObject input;

    private TemplateFactory factory;

    // List of wizard pages
    private List<IWizardPage> wizardPageList = new ArrayList<IWizardPage>();

    // Wizard container the wizard belongs to, null if none
    private IWizardContainer container = null;

    // Dialog settings for this dialog, null if none
    private IDialogSettings dialogSettings = null;

    // The colour of the title bar; null if none
    private RGB titleBarColor = null;

    // The wizard title; null if none
    private String windowTitle = null;

    // Indicate whether this wizard provides help
    private boolean helpAvailable = false;

    // Indicate whether this wizard needs the previous/next buttons. Can also be
    // available with only one page
    private boolean forcePreviousAndNextButtons = false;

    // Indicate whether the wizard needs a progress monitor
    private boolean needsProgressMonitor = false;

    // Default image for a wizard that does not specify one. Set to null if none
    private Image defaultImage = null;

    public boolean bCanFinish = true;

    // On startup, when setCanFinish() is called, the error message can be lost
    // because the current page has not yet been set.
    // In this circumstance, the message will be saved here and will be set of
    // current page when canFinish() is called instead.
    private String savedCantFinishMessage;

    /*
     * Register the default page image
     */
    public static final String DEFAULTIMAGE =
            "com.tibco.xpd.ui.properties.creationwizard.defaultimage"; //$NON-NLS-1$
    static {
        JFaceResources.getImageRegistry()
                .put(DEFAULTIMAGE,
                        ImageDescriptor.createFromFile(Wizard.class,
                                "images/page.gif")); //$NON-NLS-1$
    }

    /**
     * The default page image descriptor, used for creating a default page image
     * if required; <code>null</code> if none.
     */
    private ImageDescriptor defaultImageDescriptor = JFaceResources
            .getImageRegistry().getDescriptor(DEFAULTIMAGE);

    private boolean isRCP = false;

    /**
     * Creation Wizard class.
     */
    protected CreationWizard() {
        super();
    }

    /**
     * Indicate that this wizard is being called from the RCP application so
     * things like the project/file selection page can be hidden.
     * <p>
     * <strong>NOTE: FOR INTERNAL USE ONLY</strong>
     * </p>
     * 
     * @param isRCP
     *            <code>true</code> if this wizard is being called from an RCP
     *            Application.
     * 
     * @since 3.5
     */
    public final void setIsRCP(boolean isRCP) {
        this.isRCP = isRCP;
    }

    /**
     * Initialise the new wizard
     * 
     * @param factory
     * @param locationPage
     */
    public void init(TemplateFactory factory, IWizardPage locationPage) {
        this.factory = factory;
        this.locationPage = locationPage;
        input = factory.createTemplate();
        if (input == null) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns page title for the specified index of page
     * 
     * @param index
     * @return
     */
    public abstract String getPageTitle(int index);

    /**
     * Returns page description for the specified index of page
     * 
     * @param index
     * @return
     */
    public abstract String getPageDescription(int index);

    /**
     * Get the current workbench
     * 
     * @return The current workbench
     */
    public abstract IWorkbench getWorkbench();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        // This will add pages to the wizard
        updatePages();
    }

    /**
     * Add page to this wizard
     * 
     * @param page
     *            to add to this wizard
     */
    public void addPage(IWizardPage page) {
        page.setWizard(this);
        wizardPageList.add(page);
    }

    protected void setPages(List<?> pages) {
        wizardPageList.clear();
        for (Iterator<?> iter = pages.iterator(); iter.hasNext();) {
            addPage((IWizardPage) iter.next());
        }
    }

    public void setCanFinish(boolean canFinish, String cantFinishMessage) {
        bCanFinish = canFinish;

        savedCantFinishMessage = null;

        if (getContainer().getCurrentPage() instanceof SectionWizardPage) {
            getContainer().updateButtons();

            if (!bCanFinish) {
                ((SectionWizardPage) getContainer().getCurrentPage())
                        .setErrorMessage(cantFinishMessage);
            } else {
                ((SectionWizardPage) getContainer().getCurrentPage())
                        .setErrorMessage(null);
            }
        } else {
            // On startup, when setCanFinish() is called, the error message can
            // be lost because the current page has not yet been set.
            // In this circumstance, the message will be saved here and will be
            // set of current page when canFinish() is called instead.
            if (!canFinish) {
                savedCantFinishMessage = cantFinishMessage;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    public boolean canFinish() {
        boolean localCanFinish = bCanFinish;

        if (localCanFinish) {
            // Can finish if all pages are complete
            for (int i = 0; i < getPageCount() && localCanFinish; i++) {
                if (!(getPage(i)).isPageComplete())
                    localCanFinish = false;
            }
        }

        // On startup, when setCanFinish() is called, the error message can
        // be lost because the current page has not yet been set.
        // In this circumstance, the message will be saved here and will be
        // set of current page when canFinish() is called instead.
        if (!bCanFinish && savedCantFinishMessage != null) {
            if (getContainer().getCurrentPage() instanceof SectionWizardPage) {
                ((SectionWizardPage) getContainer().getCurrentPage())
                        .setErrorMessage(savedCantFinishMessage);
                savedCantFinishMessage = null;
            }
        }

        return localCanFinish;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.wizard.IWizard#createPageControls(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createPageControls(Composite pageContainer) {
        IWizardPage page = null;
        // Create the page controls
        for (int i = 0; i < getPageCount(); i++) {
            page = getPage(i);
            page.createControl(pageContainer);
            // Page is responsible for ensuring the created control is
            // accessable via getControl.
            Assert.isNotNull(page.getControl());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#dispose()
     */
    public void dispose() {
        // notify pages to dispose
        for (int i = 0; i < getPageCount(); i++) {
            (getPage(i)).dispose();
        }

        // dispose of image
        if (defaultImage != null) {
            JFaceResources.getResources().destroyImage(defaultImageDescriptor);
            defaultImage = null;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getContainer()
     */
    public IWizardContainer getContainer() {
        return container;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getDefaultPageImage()
     */
    public Image getDefaultPageImage() {
        // If default image not created then do so
        if (defaultImage == null) {
            defaultImage =
                    JFaceResources.getResources()
                            .createImageWithDefault(defaultImageDescriptor);
        }
        return defaultImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getDialogSettings()
     */
    public IDialogSettings getDialogSettings() {
        return dialogSettings;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.wizard.IWizard#getNextPage(org.eclipse.jface.wizard
     * .IWizardPage)
     */
    public IWizardPage getNextPage(IWizardPage page) {

        IWizardPage[] pages = getPages();
        // Get the index of the current page
        int idx = getPageIndex(pages, page);

        // Check if there is a page available after the current page
        if (idx < 0 || idx + 1 >= pages.length) {
            // last page or page not found
            return null;
        } else {
            return pages[idx + 1];
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getPage(java.lang.String)
     */
    public IWizardPage getPage(String pageName) {
        IWizardPage page = null;

        // Find the page with the given name
        for (int x = 0; x < getPageCount(); x++) {
            page = getPage(x);

            if (page.getName().equals(pageName))
                return page;
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getPageCount()
     */
    public int getPageCount() {
        return wizardPageList.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getPages()
     */
    public IWizardPage[] getPages() {
        return wizardPageList.toArray(new IWizardPage[wizardPageList.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.wizard.IWizard#getPreviousPage(org.eclipse.jface.wizard
     * .IWizardPage)
     */
    public IWizardPage getPreviousPage(IWizardPage page) {
        IWizardPage[] pages = getPages();

        // Get the index of the current page
        int idx = getPageIndex(pages, page);

        // Check if there is a page available before the current page
        if (idx <= 0) {
            // first page or page not found
            return null;
        } else {
            return pages[idx - 1];
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getStartingPage()
     */
    public IWizardPage getStartingPage() {
        // Get the first page
        IWizardPage startPage = getPage(0);

        // If this is the package location page then check if it's complete
        // If it is then skip to the next page
        if (startPage != null && startPage.equals(locationPage)) {
            if (startPage.isPageComplete())
                startPage = getNextPage(startPage);
        }

        return startPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getTitleBarColor()
     */
    public RGB getTitleBarColor() {
        return titleBarColor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#getWindowTitle()
     */
    public String getWindowTitle() {
        return windowTitle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#isHelpAvailable()
     */
    public boolean isHelpAvailable() {
        return helpAvailable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#needsPreviousAndNextButtons()
     */
    public boolean needsPreviousAndNextButtons() {
        return forcePreviousAndNextButtons || getPageCount() > 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#needsProgressMonitor()
     */
    public boolean needsProgressMonitor() {
        return needsProgressMonitor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#performCancel()
     */
    public boolean performCancel() {
        // No cancel operation required
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish() {
        // No operation required on finish
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.wizard.IWizard#setContainer(org.eclipse.jface.wizard
     * .IWizardContainer)
     */
    public void setContainer(IWizardContainer wizardContainer) {
        container = wizardContainer;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * ITabbedPropertySheetPageContributor#getContributorId()
     */
    public String getContributorId() {
        return PropertiesPluginConstants.XPD_PROPERTY_CONTRIBUTOR_ID;
    }

    /**
     * Sets the window title for the container that hosts this page
     * 
     * @param newTitle
     *            the window title for the container
     */
    public void setWindowTitle(String newTitle) {
        windowTitle = newTitle;
        if (container != null)
            container.updateWindowTitle();
    }

    /**
     * Sets the default page image descriptor for this wizard.
     * <p>
     * This image descriptor will be used to generate an image for a page with
     * no image of its own; the image will be computed once and cached.
     * </p>
     * 
     * @param imageDescriptor
     *            the default page image descriptor
     */
    public void setDefaultPageImageDescriptor(ImageDescriptor imageDescriptor) {
        defaultImageDescriptor = imageDescriptor;
    }

    /**
     * Returns the wizard's shell if the wizard is visible. Otherwise
     * <code>null</code> is returned.
     * 
     * @return Shell
     */
    public Shell getShell() {
        if (container == null)
            return null;
        return container.getShell();
    }

    /**
     * Sets the dialog settings for this wizard.
     * <p>
     * The dialog settings is used to record state between wizard invocations
     * (for example, radio button selection, last import directory, etc.)
     * </p>
     * 
     * @param settings
     *            the dialog settings, or <code>null</code> if none
     * @see #getDialogSettings
     * 
     */
    public void setDialogSettings(IDialogSettings settings) {
        dialogSettings = settings;
    }

    /**
     * Controls whether the wizard needs Previous and Next buttons even if it
     * currently contains only one page.
     * <p>
     * This flag should be set on wizards where the first wizard page adds
     * follow-on wizard pages based on user input.
     * </p>
     * 
     * @param b
     *            <code>true</code> to always show Next and Previous buttons,
     *            and <code>false</code> to suppress Next and Previous buttons
     *            for single page wizards
     */
    public void setForcePreviousAndNextButtons(boolean b) {
        forcePreviousAndNextButtons = b;
    }

    /**
     * Sets whether help is available for this wizard.
     * <p>
     * The result of this method is typically used by the container to show or
     * hide the Help button.
     * </p>
     * 
     * @param b
     *            <code>true</code> if help is available, and <code>false</code>
     *            if this wizard is helpless
     * @see #isHelpAvailable
     */
    public void setHelpAvailable(boolean b) {
        helpAvailable = b;
    }

    /**
     * Sets whether this wizard needs a progress monitor.
     * 
     * @param b
     *            <code>true</code> if a progress monitor is required, and
     *            <code>false</code> if none is needed
     * @see #needsProgressMonitor()
     */
    public void setNeedsProgressMonitor(boolean b) {
        needsProgressMonitor = b;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.XpdSection.XpdSectionContainer#refreshTabs()
     */
    public void refreshTabs() {
        updatePages();
    }

    /**
     * Get the command to execute
     * 
     * @return
     */
    public Command getCommand() {
        return factory.createCommand(input, locationPage);
    }

    protected void selectAndReveal(Object objectToSelect) {
        IWorkbench workbench = getWorkbench();

        if (workbench != null) {
            BasicNewBpmResourceWizard.selectAndReveal(objectToSelect,
                    workbench.getActiveWorkbenchWindow());
        }
    }

    /**
     * Get the page at the given index
     * 
     * @param index
     *            of page to get
     * @return <code>IWizardPage</code> at the given index
     */
    protected IWizardPage getPage(int index) {
        return wizardPageList.get(index);
    }

    /**
     * Get the index of the page given
     * 
     * @param pages
     *            - List of wizard pages
     * @param page
     *            - page to get index of
     * @return int - the index of the page
     */
    protected int getPageIndex(IWizardPage[] pages, IWizardPage page) {

        if (pages != null && page != null) {
            for (int i = 0; i < pages.length; i++) {
                if (page.equals(pages[i]))
                    return i;
            }
        }

        return -1;
    }

    /**
     * Update the list of pages for this wizard. This will be based on the
     * change to the model (change to the property tabs)
     * 
     * @return Update pages
     */
    private IWizardPage[] updatePages() {
        int pageIndex = 0;
        int tabIdx = 0;

        // If this is the first call to add pages then just add them
        if (wizardPageList == null) {
            wizardPageList = new ArrayList<IWizardPage>();
        }

        // Add the location (first) page, if not already done so - ignore if
        // called from RCP app
        if (wizardPageList.size() == 0 && locationPage != null && !isRCP) {
            locationPage.setWizard(this);
            wizardPageList.add(locationPage);
        }

        // If the first page is the location page then set the page marker
        // accordingly
        if (wizardPageList.size() > 0
                && wizardPageList.get(0).equals(locationPage))
            ++pageIndex;

        ITabItem[] tabDescriptors =
                XpdPropertiesUtil.getTabDescriptors(this,
                        new StructuredSelection(input));

        // Create a wizard page for each tab
        for (tabIdx = 0; tabIdx < tabDescriptors.length; tabIdx++) {
            boolean bUpdatePage = true;
            ITabItem thisTabDescriptor = tabDescriptors[tabIdx];
            SectionWizardPage thisPage = null;

            // Don't include the advanced tab
            if (thisTabDescriptor != null) {
                /*
                 * If the page content has changed then we need to reload the
                 * page. Make sure the page index is less than the size of the
                 * wizard pages, otherwise this indicates that there are more
                 * pages to add to the wizard now
                 */
                if (pageIndex < wizardPageList.size()) {
                    thisPage =
                            (SectionWizardPage) wizardPageList.get(pageIndex);

                    // If a page is already loaded then check that it's the
                    // right one - if not then reload
                    // the right page
                    ITabItem thisPageTabDescriptor =
                            thisPage.getTabDescriptor();

                    if (thisPageTabDescriptor == null
                            || thisTabDescriptor.equals(thisPageTabDescriptor)) {
                        bUpdatePage = false;
                        ++pageIndex;
                    }
                }

                if (bUpdatePage) {
                    // If replacing the page then dispose the current page
                    if (thisPage != null) {
                        // Remove this page from the pages list
                        wizardPageList.remove(thisPage);
                        thisPage.dispose();
                    }

                    try {

                        thisPage = new SectionWizardPage("sec" + tabIdx, //$NON-NLS-1$
                                thisTabDescriptor, this);

                        String pageTitle = getPageTitle(tabIdx);
                        String pageDescrition = getPageDescription(tabIdx);
                        if (pageTitle == null) {
                            pageTitle = tabDescriptors[tabIdx].getText();
                        }
                        if (pageDescrition == null) {
                            pageDescrition = ""; //$NON-NLS-1$
                        }
                        thisPage.setTitle(pageTitle);
                        thisPage.setDescription(pageDescrition);
                        thisPage.setInput(input);
                        thisPage.setWizard(this);

                        wizardPageList.add(pageIndex++, thisPage);
                    } catch (IllegalArgumentException e) {
                        /*
                         * Thrown by SectionWizardPage constructor when the tab
                         * being examined doesn't contain any XPDSections.
                         */
                    }
                }
            }
        }

        // If tabs have been removed then dispose the pages that contained them
        for (int idx = tabIdx + 1; idx < wizardPageList.size(); idx++) {
            IWizardPage page = wizardPageList.get(idx);

            wizardPageList.remove(idx);
            page.dispose();
        }

        List<IWizardPage> finaliseWizardPageList =
                finaliseWizardPageList(wizardPageList);

        if (finaliseWizardPageList != null && !finaliseWizardPageList.isEmpty()) {
            wizardPageList = finaliseWizardPageList;
        }

        IWizardPage[] toArray =
                wizardPageList.toArray(new IWizardPage[wizardPageList.size()]);

        return toArray;
    }

    /**
     * Allows sub-class to modify the wizard page list after the contributed
     * seciton pages list has been built.
     * 
     * @param contributedPages
     * @return null if no mods or modified list.
     */
    protected List<IWizardPage> finaliseWizardPageList(
            List<IWizardPage> contributedPages) {
        return null;
    }
}
