/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetProjectPropertyChangeListener;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetCategory;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.OtherProjectAssetCategory;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetBindingUtil;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetCategoryElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement.CustomParam;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetManager;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdSelectionWizardPage;
import com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite;
import com.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.ViewerServicesProvider;

/**
 * Wizard selection page that allows the selection of the asset type for a new
 * XPD project
 * 
 * @author njpatel
 */
public class ProjectAssetSelectionPage extends AbstractXpdSelectionWizardPage
        implements ViewerServicesProvider, ICheckStateListener,
        PropertyChangeListener {

    private CheckTreeWithDescriptionComposite mainControl;

    private final IProjectAssetManager assetManager = ProjectAssetManager
            .getProjectAssetMenager();

    private final ProjectAssetElement[] assets = assetManager.getAssets();

    private final String[] assetTypesToEnable;

    // Set to true when hidden asset types are present in the assets list
    private boolean hasHiddenAssetTypes = false;

    /**
     * List of assets set as mandatory - user will not be able to unselect these
     */
    private final List<String> mandatoryAssets;

    /**
     * Elements to be selected by default in this wizard based on the asset
     * bindings.
     */
    private List<ProjectAssetElement> elementsToSelect;

    private AssetTypeSorter assetTypeSorter;

    private String currentProjectName = ""; //$NON-NLS-1$

    private String wizardId;

    /**
     * Flag to force complete state for the page.
     */
    private boolean forcePageComplete = false;

    /**
     * Sets force page complete flag to true.This should be done when the Asset
     * selection page is set to be hidden.
     */
    void setForcePageComplete() {
        this.forcePageComplete = true;
    }

    /**
     * Asset selection page for the new project wizard.
     * 
     * @param assetTypesToEnable
     *            ids of asset types to select by default
     */
    public ProjectAssetSelectionPage(String[] assetTypesToEnable) {
        this(assetTypesToEnable, null);
    }

    /**
     * Asset selection page for the new project wizard.
     * 
     * @param assetTypesToEnable
     *            ids of asset types to select by default
     * @param mandatoryAssets
     *            list of asset ids to be set as mandatory(user will not be able
     *            to unselect these assets)
     * @since 3.2
     */
    public ProjectAssetSelectionPage(String[] assetTypesToEnable,
            List<String> mandatoryAssets) {
        super("assetSelection"); //$NON-NLS-1$
        this.assetTypesToEnable = assetTypesToEnable;
        this.mandatoryAssets = mandatoryAssets;

        // Check for hidden asset types
        for (ProjectAssetElement asset : assets) {
            if (!asset.isVisible()) {
                hasHiddenAssetTypes = true;
                break;
            }
        }
    }

    /**
     * Set a sorter that will control the order of the asset type contributions
     * in this wizard. Note that this does not affect this selection page's
     * asset viewer.
     * 
     * @param sorter
     * @since 3.2
     */
    public void setAssetSorter(AssetTypeSorter sorter) {
        this.assetTypeSorter = sorter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        container.setFont(parent.getFont());
        initializeDialogUnits(parent);

        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        mainControl =
                new CheckTreeWithDescriptionComposite(container, this,
                        SWT.CHECK);
        mainControl.setLayoutData(new GridData(GridData.FILL_BOTH));

        mainControl.addCheckStateListener(this);
        mainControl.getViewer().setSorter(new AssetViewerSorter());
        mainControl.getViewer().addFilter(new UnvisibleAssetFilter());
        mainControl.getViewer().addFilter(new EmptyCategoriesFilter());
        mainControl.getViewer().addFilter(new ExtensionAssetFilter());

        Composite btnContainer = createButtonsContainer(container);
        btnContainer.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_BEGINNING));

        /*
         * Select the default asset types and their dependencies. A map will be
         * created to resolve dependencies
         */
        Map<String, ProjectAssetElement> assetMap =
                new HashMap<String, ProjectAssetElement>();
        for (ProjectAssetElement asset : assets) {
            assetMap.put(asset.getId(), asset);
        }

        if (assetTypesToEnable != null) {
            elementsToSelect = new ArrayList<ProjectAssetElement>();

            for (String id : assetTypesToEnable) {
                ProjectAssetElement asset = assetMap.get(id);

                if (asset != null && !elementsToSelect.contains(asset)) {
                    elementsToSelect.add(asset);

                    // Add any dependencies
                    addDependencies(asset, elementsToSelect, assetMap);
                }
            }

            if (!elementsToSelect.isEmpty()) {
                Object[] elements = elementsToSelect.toArray();
                mainControl.setCheckedElements(elements);
                mainControl.setExpandedElements(elements);

            }
        }
        updateWizardPages();

        setControl(container);
    }

    /**
     * Checks if at least one Asset is selected in the Asset selection Wizard
     * 
     * @return return true if at least one Asset is selected , else return
     *         false.
     */
    private boolean validatePage() {
        Object[] checkedElements = mainControl.getCheckedElements();
        if (checkedElements.length == 0) {
            setErrorMessage(Messages.ProjectAssetSelectionPage_noAssetSelected_message);
            return false;
        }
        setErrorMessage(null);
        return true;
    }

    /**
     * Add the dependencies of the given asset to the assetToSelect list. This
     * is a recursive method and will also include all implicit dependencies.
     * 
     * @param asset
     *            get dependencies of
     * @param assetsToSelect
     *            list of assets that will be selected
     * @param assetMap
     *            assets
     */
    private void addDependencies(ProjectAssetElement asset,
            List<ProjectAssetElement> assetsToSelect,
            Map<String, ProjectAssetElement> assetMap) {

        for (String assetId : asset.getDependencies()) {
            ProjectAssetElement depAsset = assetMap.get(assetId);

            if (depAsset != null && !elementsToSelect.contains(depAsset)) {
                elementsToSelect.add(depAsset);

                addDependencies(depAsset, assetsToSelect, assetMap);
            }
        }
    }

    /**
     * Create a container to contain the Select All / Unselect All buttons
     * 
     * @param parent
     * @return
     */
    protected Composite createButtonsContainer(Composite parent) {
        Composite container = null;

        if (parent != null) {
            container = new Composite(parent, SWT.NONE);
            container.setLayout(new FillLayout());

            Button selectAll = new Button(container, SWT.NONE);
            selectAll
                    .setText(Messages.ProjectAssetSelectionPage_selectAll_button);
            selectAll.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    mainControl.setAllChecked(true);
                    updateWizardPages();
                }
            });

            Button unselectAll = new Button(container, SWT.NONE);
            unselectAll
                    .setText(Messages.ProjectAssetSelectionPage_deselectAll_button);
            unselectAll.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    mainControl.setAllChecked(false);

                    if (elementsToSelect != null && !elementsToSelect.isEmpty()
                            && mandatoryAssets != null
                            && !mandatoryAssets.isEmpty()) {
                        // Reset all mandatory assets
                        List<ProjectAssetElement> itemsToSelect =
                                new ArrayList<ProjectAssetElement>();
                        for (ProjectAssetElement element : elementsToSelect) {
                            if (mandatoryAssets.contains(element.getId())) {
                                itemsToSelect.add(element);
                            }
                        }
                        if (!itemsToSelect.isEmpty()) {
                            mainControl.setCheckedElements(itemsToSelect
                                    .toArray());
                        }
                    }

                    updateWizardPages();
                }
            });

        }

        return container;
    }

    @Override
    public boolean isPageComplete() {
        boolean pageComplete = true;
        // SCF-275: When page is set to forced completion, no need to do
        // further checks.
        if (forcePageComplete) {
            return pageComplete;
        }

        IWizardNode wizardNode = getSelectedNode();
        /*
         * If a wizard node has been set then check if the wizard can finish,
         * otherwise page is complete
         */
        if (wizardNode != null && wizardNode.getWizard() != null) {
            pageComplete = wizardNode.getWizard().canFinish();
        }
        /*
         * XPD-4571: Kapil- Check if at least one Asset is selected in the Asset
         * Selection Wizard. If not then set pageComplete to false.
         */
        if (!validatePage()) {
            pageComplete = false;
        }

        return pageComplete;
    }

    @Override
    public void dispose() {
        mainControl.removeCheckStateListener(this);
        mainControl.dispose();
        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.CheckTableWithDescriptionControl.
     * ICheckTableWithDescriptionInputProvider#getContentProvider()
     */
    @Override
    public IStructuredContentProvider getContentProvider() {
        return new CategoriesTreeContentProvider();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.CheckTableWithDescriptionControl.
     * ICheckTableWithDescriptionInputProvider#getDescription(java.lang.Object)
     */
    @Override
    public String getDescription(Object element) {
        String description = null;
        if (element instanceof ProjectAssetElement) {
            description = ((ProjectAssetElement) element).getDescription();

            if (mandatoryAssets != null
                    && mandatoryAssets.contains(((ProjectAssetElement) element)
                            .getId())) {
                String message =
                        String.format("<p><span font=\"header\" color=\"gray\">%s</span><br/><br/></p>", //$NON-NLS-1$
                                Messages.ProjectAssetSelectionPage_requiredAsset_shortdesc);

                if (description != null) {
                    description += "<p><br/></p>" + message; //$NON-NLS-1$
                } else {
                    description = message;
                }
            }
        } else if (element instanceof IProjectAssetCategory) {
            description = ((IProjectAssetCategory) element).getDescription();
        }
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.TableWithDescriptionComposite.
     * ICheckTableWithDescriptionInputProvider#getInput()
     */
    @Override
    public Object getInput() {
        return assetManager.getTopLevelCategories();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.CheckTableWithDescriptionControl.
     * ICheckTableWithDescriptionInputProvider#getLabelProvider()
     */
    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.
     * ViewerServicesProvider#getLabelProvider()
     */
    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.
     * ViewerServicesProvider#getLabelProvider()
     */
    @Override
    public ILabelProvider getLabelProvider() {
        return new AssetLabelProvider();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse
     * .jface.viewers.CheckStateChangedEvent)
     */
    @Override
    public void checkStateChanged(CheckStateChangedEvent event) {
        Object src = event.getSource();

        if (src instanceof ContainerCheckedTreeViewer) {
            ContainerCheckedTreeViewer tableViewer =
                    (ContainerCheckedTreeViewer) src;
            updateStateChange(event, tableViewer, event.getElement());
            updateWizardPages();
        }
    }

    /**
     * Update any dependency assets when the state of an asset/category changes.
     * 
     * @param event
     * @param tableViewer
     * @param element
     */
    private void updateStateChange(CheckStateChangedEvent event,
            ContainerCheckedTreeViewer tableViewer, Object element) {
        if (event != null && tableViewer != null && element != null) {
            if (element instanceof ProjectAssetCategoryElement) {
                Object[] children =
                        ((ProjectAssetCategoryElement) element).getChildren();

                if (children != null) {
                    for (Object child : children) {
                        updateStateChange(event, tableViewer, child);
                    }
                }
            } else if (element instanceof ProjectAssetElement) {
                ProjectAssetElement selectedAsset =
                        (ProjectAssetElement) element;

                if (event.getChecked()) {
                    /*
                     * If the selected asset depends on other assets then all
                     * these assets need to be selected
                     */
                    String[] dependencies = selectedAsset.getDependencies();

                    if (dependencies != null) {
                        for (String id : dependencies) {
                            ProjectAssetElement dependentOnAsset =
                                    assetManager.getAssetById(id);

                            if (dependentOnAsset != null) {
                                if (!tableViewer.getChecked(dependentOnAsset)) {
                                    tableViewer.setChecked(dependentOnAsset,
                                            true);
                                }
                            }
                        }
                    }
                } else /* unchecked */{

                    // If this is a mandatory asset then do not allow to be
                    // unselected
                    if (mandatoryAssets != null
                            && mandatoryAssets.contains(selectedAsset.getId())) {
                        event.getCheckable().setChecked(selectedAsset, true);
                    } else {
                        /*
                         * If the unselected asset has dependencies then all
                         * assets that depend on this asset should also be
                         * unselected
                         */
                        Object[] checkedElements =
                                tableViewer.getCheckedElements();

                        /*
                         * Check each checked asset - if one is dependent on the
                         * selected asset then uncheck it
                         */
                        for (Object obj : checkedElements) {
                            if (obj instanceof ProjectAssetElement) {
                                ProjectAssetElement elem =
                                        (ProjectAssetElement) obj;

                                String[] dependencies = elem.getDependencies();

                                if (dependencies != null) {
                                    for (String id : dependencies) {
                                        if (id.equals(selectedAsset.getId())) {
                                            tableViewer.setChecked(obj, false);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canFlipToNextPage() {
        boolean ret = super.canFlipToNextPage();

        if (ret) {
            /*
             * If there are not pages in the select node's wizard - this will be
             * the case when all visible asset types have been unchecked and
             * there are hidden asset types - then disable the next button.
             */
            ret = getSelectedNode().getWizard().getPageCount() != 0;
        }

        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.TableWithDescriptionComposite.
     * ICheckTableWithDescriptionInputProvider#getInitialSelection()
     */
    @Override
    public Object getInitialSelection() {
        return null;
    }

    /**
     * Update the wizard pages for this wizard based on the selection of assets.
     * 
     */
    private void updateWizardPages() {
        IWizardNode wizardNode = null;

        Object[] checkedElements = mainControl.getCheckedElements();
        if (checkedElements.length > 0 || hasHiddenAssetTypes) {
            List<ProjectAssetElement> assetsToExecute =
                    new ArrayList<ProjectAssetElement>();

            HashSet<Object> checkedSet =
                    new HashSet<Object>(Arrays.asList(checkedElements));
            /*
             * Create a list of assets containing the checked assets and hidden
             * assets to build the wizard (pages). This method of building the
             * list is used to preserve the order of execution
             */
            for (ProjectAssetElement elem : assets) {
                if (checkedSet.contains(elem)
                        || isAssetHiddenAndApplicable(elem)) {
                    assetsToExecute.add(elem);
                }
            }

            /*
             * If asset type sorter is defined then sort the list of assets that
             * will be executed.
             */
            if (assetTypeSorter != null) {
                List<IProjectAsset> assetsToSort =
                        new ArrayList<IProjectAsset>();
                for (ProjectAssetElement elem : assetsToExecute) {
                    assetsToSort.add(elem);
                }
                assetsToSort = assetTypeSorter.sortAssetTypes(assetsToSort);

                // Create the new ordered list
                if (assetsToSort != null) {
                    assetsToExecute.clear();
                    for (IProjectAsset asset : assetsToSort) {
                        if (asset instanceof ProjectAssetElement) {
                            assetsToExecute.add((ProjectAssetElement) asset);
                        }
                    }
                }
            }

            // Create new wizard node with the checked assets
            wizardNode =
                    new ProjectAssetWizardNode(getWizard().getWindowTitle(),
                            getContainer(), assetsToExecute,
                            getProjectDetailsProvider());
        }

        // Set the selected node
        setSelectedNode(wizardNode);

        /* SID XPD-2139: Track project name changes in project seleciton page */
        IWizard wizard = getWizard();
        if (wizard != null) {
            IWizardPage page =
                    wizard.getPage(XpdProjectWizard.PROJECT_SELECTION_PAGE_ID);
            if (page instanceof ProjectSelectionPage) {
                ((ProjectSelectionPage) page).addPropertyChangeListener(this);
            }
        }

        /*
         * We may have just turned extra asset pages on lets notify them all of
         * the current project nameproject name
         */
        notifyAssetPagesOfProjectNameChange(new PropertyChangeEvent(this,
                IAssetProjectPropertyChangeListener.PROJECTNAME_PROPERTY,
                currentProjectName, currentProjectName));

    }

    /**
     * Check if the given asset is hidden and can apply to the project being
     * created.
     * 
     * @param elem
     * @return
     */
    private boolean isAssetHiddenAndApplicable(ProjectAssetElement elem) {
        if (!elem.isVisible()) {
            // If additional parameters have been applied then check it
            String value =
                    elem.getCustomParamValue(CustomParam.NOT_APPLIED_BY_DEFAULT);
            boolean notAppliedByDefault =
                    (value != null) ? Boolean.parseBoolean(value) : false;
            if (notAppliedByDefault) {
                if (wizardId != null) {
                    String[] boundAssets =
                            ProjectAssetBindingUtil
                                    .getAssetIdsForNewProjectWizard(wizardId);
                    for (String assetId : boundAssets) {
                        if (assetId.equals(elem.getId())) {
                            return true;
                        }
                    }
                }
            } else {
                return true; // We want to preserve behaviour that hidden assets
                             // are added by default.
            }
        }
        return false;

    }

    /**
     * Get the project details provider.
     * 
     * @return
     * @since 3.5
     */
    protected IProjectDetailsProvider getProjectDetailsProvider() {
        return new ProjectDetailsProvider(getWizard());
    }

    /**
     * 
     * <p>
     * <i>Created: 19 Jun 2007</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     * 
     */
    private final static class CategoriesTreeContentProvider implements
            ITreeContentProvider {
        private final Object[] EMPTY_LIST = new Object[0];

        @Override
        public Object[] getElements(Object inputElement) {
            Object[] elements = null;
            if (inputElement instanceof Object[]) {
                return (Object[]) inputElement;
            }
            if (inputElement instanceof Collection) {
                elements = ((Collection<?>) inputElement).toArray();
            }
            return elements != null ? elements : EMPTY_LIST;
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof IProjectAssetCategory) {
                IProjectAssetCategory category =
                        (IProjectAssetCategory) parentElement;
                return category.getChildren();
            }
            return EMPTY_LIST;
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof IProjectAssetCategory) {
                IProjectAssetCategory category =
                        (IProjectAssetCategory) element;
                return category.getParentCategory();
            } else if (element instanceof IProjectAsset) {
                IProjectAsset asset = (IProjectAsset) element;
                return asset.getCategory();
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof IProjectAsset) {
                return false;
            } else if (element instanceof IProjectAssetCategory) {
                IProjectAssetCategory category =
                        (IProjectAssetCategory) element;
                return category.getChildren().length > 0;
            }
            return false;
        }

        @Override
        public void dispose() {
            // Nothing to do
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // Nothing to do
        }
    }

    private static class AssetViewerSorter extends ViewerSorter {
        /*
         * @see
         * org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
         */
        @Override
        public int category(Object element) {
            if (element instanceof IProjectAssetCategory) {
                if (element instanceof OtherProjectAssetCategory) {
                    return 1;
                }
                return 0;
            } else if (element instanceof IProjectAsset) {
                return 2;
            }
            return 3;
        }
    }

    private static class UnvisibleAssetFilter extends ViewerFilter {
        /*
         * @see
         * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
         * .Viewer, java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof ProjectAssetElement) {
                ProjectAssetElement asset = (ProjectAssetElement) element;
                return asset.isVisible();
            }
            return true;
        }
    }

    private static class ExtensionAssetFilter extends ViewerFilter {
        /*
         * @see
         * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
         * .Viewer, java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof ProjectAssetElement) {
                ProjectAssetElement asset = (ProjectAssetElement) element;
                return asset.getExtends() == null;
            }
            return true;
        }
    }

    private static class EmptyCategoriesFilter extends ViewerFilter {
        /*
         * @see
         * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
         * .Viewer, java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof IProjectAssetCategory) {
                IProjectAssetCategory category =
                        (IProjectAssetCategory) element;
                return category.containsVisibleAssets();
            }
            return true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.
     * ViewerServicesProvider#getToolBarActions()
     */
    @Override
    public List<IAction> getToolBarActions() {
        IAction showHideCategoriesAction =
                new Action("", IAction.AS_CHECK_BOX) {//$NON-NLS-1$
                    /*
                     * (non-Javadoc)
                     * 
                     * @see org.eclipse.jface.action.Action#run()
                     */
                    @Override
                    public void run() {
                        Object[] elements = mainControl.getCheckedElements();
                        if (isChecked()) {
                            mainControl
                                    .getViewer()
                                    .setInput(assetManager.getTopLevelCategories());
                        } else {
                            mainControl.getViewer()
                                    .setInput(assetManager.getAssets());
                        }

                        if (elements.length > 0) {
                            mainControl.setCheckedElements(elements);
                            mainControl.setExpandedElements(elements);

                        }
                        // mainControl.getViewer().refresh();
                    }
                };
        showHideCategoriesAction
                .setToolTipText(Messages.ProjectAssetSelectionPage_showCategories_tooltip);
        showHideCategoriesAction.setImageDescriptor(XpdResourcesUIActivator
                .getDefault().getImageRegistry()
                .getDescriptor(XpdResourcesUIConstants.HIERARCHY));
        showHideCategoriesAction.setChecked(true);

        ArrayList<IAction> actions = new ArrayList<IAction>();
        actions.add(showHideCategoriesAction);
        return actions;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.misc.CheckTreeWithDescriptionComposite.
     * ViewerServicesProvider#getMainControlLabelText()
     */
    @Override
    public String getMainControlLabelText() {
        return Messages.ProjectAssetSelectionPage_assets_label;
    }

    /**
     * Label provider for the asset selection tree view. This will set the
     * colour of the assets that are mandatory to gray.
     * 
     * @author njpatel
     * 
     */
    private class AssetLabelProvider extends LabelProvider implements
            IColorProvider {

        private final Color GRAY = getShell().getDisplay()
                .getSystemColor(SWT.COLOR_GRAY);

        /*
         * @see
         * org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         */
        @Override
        public String getText(Object element) {
            if (element instanceof IProjectAsset) {
                return ((IProjectAsset) element).getName();
            } else if (element instanceof IProjectAssetCategory) {
                return ((IProjectAssetCategory) element).getName();
            }

            return super.getText(element);
        }

        /*
         * @see
         * org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
         */
        @Override
        public Image getImage(Object element) {
            ImageDescriptor imageDesc = null;
            if (element instanceof IProjectAsset) {
                imageDesc = ((IProjectAsset) element).getImageDescriptor();
                if (imageDesc != null) {
                    return getImage(imageDesc);
                } else {
                    return XpdResourcesUIActivator.getDefault()
                            .getImageRegistry()
                            .get(XpdResourcesUIConstants.PROJECT_ASSET);
                }
            } else if (element instanceof IProjectAssetCategory) {
                imageDesc =
                        ((IProjectAssetCategory) element).getImageDesctiptor();
                if (imageDesc != null) {
                    return getImage(imageDesc);
                } else {
                    return XpdResourcesUIActivator
                            .getDefault()
                            .getImageRegistry()
                            .get(XpdResourcesUIConstants.PROJECT_ASSET_CATEGORY);
                }
            }
            return super.getImage(element);
        }

        private final Map<ImageDescriptor, Image> imageMap =
                new HashMap<ImageDescriptor, Image>();

        private Image getImage(ImageDescriptor imageDescriptor) {
            if (imageDescriptor == null)
                return null;
            Image image = imageMap.get(imageDescriptor);
            if (image == null) {
                image = imageDescriptor.createImage();
                imageMap.put(imageDescriptor, image);
            }
            return image;
        }

        @Override
        public void dispose() {
            for (Image image : imageMap.values()) {
                image.dispose();
            }
            imageMap.clear();
        }

        @Override
        public Color getBackground(Object element) {
            return null;
        }

        @Override
        public Color getForeground(Object element) {
            // If this is a mandatory asset then change colour to indicate this
            if (element instanceof ProjectAssetElement
                    && mandatoryAssets != null
                    && mandatoryAssets.contains(((ProjectAssetElement) element)
                            .getId())) {
                return GRAY;
            }
            return null;
        }
    }

    /**
     * Project details provider that will get the project details from the first
     * (project selection) page of the new XPD project creation wizard.
     * 
     * @author njpatel
     * @since 3.2
     */
    private class ProjectDetailsProvider implements IProjectDetailsProvider {

        private ProjectSelectionPage selectionPage;

        /**
         * @param wizard
         */
        ProjectDetailsProvider(IWizard wizard) {
            if (wizard != null) {
                IWizardPage page =
                        wizard.getPage(XpdProjectWizard.PROJECT_SELECTION_PAGE_ID);
                if (page instanceof ProjectSelectionPage) {
                    this.selectionPage = (ProjectSelectionPage) page;
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider#
         * getProjectDetails()
         */
        @Override
        public ProjectDetails getProjectDetails() {
            if (selectionPage != null) {
                ProjectDetails details = selectionPage.getProjectDetails();

                if (details != null) {
                    // Return copy of the details as otherwise the receiver will
                    // be able to change the details
                    return EcoreUtil.copy(details);
                }
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider#
         * getProjectName()
         */
        @Override
        public String getProjectName() {
            return selectionPage != null ? selectionPage.getProjectName()
                    : null;
        }

    }

    /**
     * SID XPD-2139: Track project name changes in project seleciton page
     * 
     * @param evt
     */
    private void notifyAssetPagesOfProjectNameChange(PropertyChangeEvent evt) {
        IWizardNode selectedNode = getSelectedNode();

        if (selectedNode != null) {
            IWizard selectedWizard = selectedNode.getWizard();
            if (selectedWizard != null) {
                IWizardPage[] pages = selectedWizard.getPages();

                for (IWizardPage page : pages) {
                    if (page instanceof IAssetProjectPropertyChangeListener) {
                        ((IAssetProjectPropertyChangeListener) page)
                                .projectPropertyChanged(evt);
                    }
                }
            }
        }
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * 
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        /*
         * SID XPD-2139: When project name changes inform all the asset config
         * pages.
         */
        if (IAssetProjectPropertyChangeListener.PROJECTNAME_PROPERTY.equals(evt
                .getPropertyName())) {
            currentProjectName = (String) evt.getNewValue();
        }

        notifyAssetPagesOfProjectNameChange(evt);
        return;
    }

    /**
     * Set the wizard id (the id of the new project wizard being executed).
     * 
     * @param wizardId
     */
    public void setWizardId(String wizardId) {
        this.wizardId = wizardId;
    }
}
