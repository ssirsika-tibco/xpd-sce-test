/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.registry.Search;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * @author nwilson
 */
public class NewRegistrySearchWizard extends Wizard implements INewWizard {

    /** New registry search title text id. */
    private static final String NEW_REGISTRY_SEARCH_WIZARD_TITLE = Messages.NewRegistrySearchWizard_Wizard_title;

    /** Business search page title id. */
    private static final String BUSINESS_SEARCH_PAGE_TITLE = Messages.NewRegistrySearchWizard_BusinessSearchPage_title;

    /** Service search page title id. */
    private static final String SERVICE_SEARCH_PAGE_TITLE = Messages.NewRegistrySearchWizard_ServiceSearchPage_title;

    /** Search name label. */
    private static final String SEARCH_NAME = Messages.NewRegistrySearchWizard_SearchName_label;

    /** Search criteria label. */
    private static final String SEARCH_CRITERIA = Messages.NewRegistrySearchWizard_SearchCriteria_label;

    /** The search type page. */
    private SearchTypePage searchTypePage;

    /** The search page. */
    private SearchPage searchPage;

    /** The search page. */
    private SearchPage businessSearchPage;

    /** The search page. */
    private SearchPage serviceSearchPage;

    /** The search to add. */
    private Search search;

    /**
     * @param workbench
     *            The workbench.
     * @param selection
     *            The current selection.
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        String searchTypePageName = NEW_REGISTRY_SEARCH_WIZARD_TITLE;
        searchTypePage = new SearchTypePage(searchTypePageName);
        businessSearchPage = new BusinessSearchPage(BUSINESS_SEARCH_PAGE_TITLE);
        serviceSearchPage = new ServiceSearchPage(SERVICE_SEARCH_PAGE_TITLE);
        addPage(searchTypePage);
        addPage(businessSearchPage);
        addPage(serviceSearchPage);
    }

    /**
     * @param page
     *            The current page.
     * @return The next page.
     * @see org.eclipse.jface.wizard.Wizard#getNextPage(
     *      org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        IWizardPage next = null;
        if (page instanceof SearchTypePage) {
            int searchType = searchTypePage.getSearchType();
            if (searchType == Search.FIND_BUSINESS) {
                searchPage = businessSearchPage;
            } else if (searchType == Search.FIND_SERVICE) {
                searchPage = serviceSearchPage;
            }
            next = searchPage;
        }
        return next;
    }

    @Override
    public boolean canFinish() {
        boolean isSearchTypePageComplete = searchTypePage.isPageComplete();
        boolean isSearchPageComplete = false;
        int searchType = searchTypePage.getSearchType();
        if (searchType == Search.FIND_BUSINESS) {
            isSearchPageComplete = businessSearchPage.isPageComplete();
        } else if (searchType == Search.FIND_SERVICE) {
            isSearchPageComplete = searchPage.isPageComplete();
        }
        return isSearchTypePageComplete && isSearchPageComplete;
    }

    /**
     * @return The page count.
     * @see org.eclipse.jface.wizard.Wizard#getPageCount()
     */
    @Override
    public int getPageCount() {
        return 2;
    }

    /**
     * @param page
     *            The current page.
     * @return The previous page.
     * @see org.eclipse.jface.wizard.Wizard#getPreviousPage(
     *      org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public IWizardPage getPreviousPage(IWizardPage page) {
        IWizardPage previous = null;
        if (page instanceof SearchPage) {
            previous = searchTypePage;
        }
        return previous;
    }

    /**
     * @return true if finished ok otherwise false.
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        if (searchPage != null) {
            String name = searchPage.getSearchName();
            int type = searchPage.getSearchType();
            String criteria = searchPage.getSearchCriteria();
            search = new Search(name, type);
            search.addNameCriteria(criteria);
        }
        return true;
    }

    /**
     * @author nwilson
     */
    static class SearchTypePage extends AbstractXpdWizardPage {

        /** Search type page description. */
        private static final String SEARCH_TYPE_PAGE_DESCRIPTION = Messages.NewRegistrySearchWizard_SearchTypePage_longdesc;

        /** Business search button label. */
        private static final String BUSINESS_SEARCH_LABEL = Messages.NewRegistrySearchWizard_SearchForBussines_button;

        /** Service search button label. */
        private static final String SERVICE_SEARCH_LABEL = Messages.NewRegistrySearchWizard_SearchForService_button;

        /** The manager to control radio buttons and Control enablement. */
        private final SelectionGroupManager sgm;

        /** Radio button for the file section. */
        private Button business;

        /** Radio button for the URL section. */
        private Button service;

        /**
         * @param pageName
         *            The page title.
         */
        protected SearchTypePage(String pageName) {
            super(pageName);
            setTitle(pageName);
            setDescription(SEARCH_TYPE_PAGE_DESCRIPTION);
            sgm = new SelectionGroupManager();
            setPageComplete(true);
        }

        /**
         * @param parent
         *            The parent control.
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(
         *      org.eclipse.swt.widgets.Composite)
         */
        public void createControl(Composite parent) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayout(new GridLayout());
            business = new Button(composite, SWT.RADIO);
            business.setText(BUSINESS_SEARCH_LABEL);
            service = new Button(composite, SWT.RADIO);
            service.setText(SERVICE_SEARCH_LABEL);

            sgm.addButton(business);
            sgm.addButton(service);

            setControl(composite);
        }

        /**
         * @return The search type.
         */
        public int getSearchType() {
            int type = Search.FIND_BUSINESS;
            if (business.getSelection()) {
                type = Search.FIND_BUSINESS;
            } else if (service.getSelection()) {
                type = Search.FIND_SERVICE;
            }
            return type;
        }
    }

    /**
     * @author nwilson
     */
    private static class BusinessSearchPage extends SearchPage {

        /** Business search page description. */
        private static final String DESCRIPTION = Messages.NewRegistrySearchWizard_BusinessSearchPage_longdesc;

        /**
         * @param pageName
         *            The page name.
         */
        protected BusinessSearchPage(String pageName) {
            super(pageName);
            setDescription(DESCRIPTION);
        }

        /**
         * @return The search type.
         * @see com.tibco.xpd.registry.ui.wizard.NewRegistrySearchWizard.SearchPage
         *      #getSearchType()
         */
        @Override
        public int getSearchType() {
            return Search.FIND_BUSINESS;
        }
    }

    /**
     * @author nwilson
     */
    private static class ServiceSearchPage extends SearchPage {

        /** Service search page description. */
        private static final String DESCRIPTION = Messages.NewRegistrySearchWizard_ServiceSearchPage_longdesc;

        /**
         * @param pageName
         *            The page name.
         */
        protected ServiceSearchPage(String pageName) {
            super(pageName);
            setDescription(DESCRIPTION);
        }

        /**
         * @return The search type.
         * @see com.tibco.xpd.registry.ui.wizard.NewRegistrySearchWizard.SearchPage
         *      #getSearchType()
         */
        @Override
        public int getSearchType() {
            return Search.FIND_SERVICE;
        }
    }

    /**
     * Basic initial search page allows for the entry of one string.
     * 
     * @author nwilson
     */
    private abstract static class SearchPage extends WizardPage {

        /**  */
        private Text name;

        /**  */
        private Text criteria;

        /** Listener for text modification events. */
        private final ModifyListener modify;

        private boolean initialised;

        /**
         * @param pageName
         *            The page name.
         */
        protected SearchPage(String pageName) {
            super(pageName);
            setTitle(pageName);
            modify = new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    setPageComplete(validatePage(false, true));
                }
            };
            setPageComplete(true);
        }

        private boolean validatePage(boolean setFocus, boolean showMessage) {
            if (!initialised) {
                return false;
            }
            setMessage(null);
            setErrorMessage(null);

            // Name
            String serverName = name.getText().trim();
            if (serverName.length() == 0) {
                if (showMessage) {
                    setErrorMessage(Messages.SearchPropertyPage_NameEmpty_message);
                }
                if (setFocus) {
                    name.setFocus();
                }
                return false;
            }

            // Criteria
            String searchCriteria = criteria.getText().trim();
            if (searchCriteria.length() == 0) {
                if (showMessage) {
                    setErrorMessage(Messages.SearchPropertyPage_SearchCriteriaEmpty_message);
                }
                if (setFocus) {
                    criteria.setFocus();
                }
                return false;
            }
            return true;
        }

        /**
         * @return The search criteria text.
         */
        public String getSearchCriteria() {
            return criteria.getText();
        }

        /**
         * @return The search type.
         */
        public abstract int getSearchType();

        /**
         * @return The search name.
         */
        public String getSearchName() {
            return name.getText();
        }

        /**
         * @param parent
         *            The parent control.
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(
         *      org.eclipse.swt.widgets.Composite)
         */
        public void createControl(Composite parent) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayout(new GridLayout(2, false));

            Label nameLabel = new Label(composite, SWT.NONE);
            nameLabel.setText(SEARCH_NAME);
            nameLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
                    false));
            name = new Text(composite, SWT.BORDER);
            name.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

            Label criteriaLabel = new Label(composite, SWT.NONE);
            criteriaLabel.setText(SEARCH_CRITERIA);
            criteriaLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
                    false));
            criteria = new Text(composite, SWT.BORDER);
            criteria
                    .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
            name.addModifyListener(modify);
            criteria.addModifyListener(modify);

            new Label(composite, SWT.NONE);
            Label searchInstructionsLabel = new Label(composite, SWT.NONE);
            GridDataFactory.fillDefaults().applyTo(searchInstructionsLabel);
            searchInstructionsLabel
                    .setText(Messages.NewRegistrySearchWizard_SearchDesc_label);
            initialised = true;
            setPageComplete(validatePage(true, false));
            setControl(composite);
        }

        @Override
        public void setVisible(boolean visible) {
            super.setVisible(visible);
            if (visible) {
                if (validatePage(true, false)) {
                    name.setFocus();
                }
            }
        }
    }

    /**
     * @return The new search.
     */
    public Search getSearch() {
        return search;
    }

}
