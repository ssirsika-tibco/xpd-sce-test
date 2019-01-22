/**
 * QuickSearchContributionHelper.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.quickSearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.dialogs.SearchPattern;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;
import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopupAction;

/**
 * QuickSearchContributionHelper
 * <p>
 * This class loads the contributions to the
 * <code>com.tibco.xpd.resources.ui.quickSearchViewCntribition</code> extension
 * point.
 * <p>
 * You can use this directly or, preferably, via the
 * {@link QuickSearchPopupAction} action which provides the standard UI
 * components and handling for quick search in local view.
 */
public class QuickSearchContributionHelper {

    private static boolean loggingOn = false;

    /**
     * Cache all the contributions to quick search statically.
     */
    static {
        loadAllQuickSearchContributions();
    }

    private static List<ViewIdAndContribution> allContributions;

    /**
     * ViewIdAndContribution
     * <p>
     * Class representing one contribution to QuickSearchViewContribution
     * extension point and the view id it is related to.
     * 
     */
    private static class ViewIdAndContribution {
        private String viewId;

        private AbstractQuickSearchPopupContribution contribution;

        /**
         * @param viewId
         * @param contribution
         */
        public ViewIdAndContribution(String viewId,
                AbstractQuickSearchPopupContribution contribution) {
            super();
            this.viewId = viewId;
            this.contribution = contribution;
        }

        /**
         * @return the contribution
         */
        public AbstractQuickSearchPopupContribution getContribution() {
            return contribution;
        }

        /**
         * @return the viewId
         */
        public String getViewId() {
            return viewId;
        }
    }

    private static final String EXT_POINT_ID = "quickSearchContribution"; //$NON-NLS-1$

    private static final String VIEW_CONTRIBUTION_EL = "viewContribution"; //$NON-NLS-1$

    private static final String VIEW_ID_ATTR = "viewId"; //$NON-NLS-1$

    private static final String CLASS_ATTR = "class"; //$NON-NLS-1$

    private IWorkbenchPartReference workbenchPart = null;

    private List<QuickSearchPartContributionAndProviders> contributionsForCurrentPart =
            Collections.emptyList();

    private List<QuickSearchCategoryAndCheckstate> searchCategoriesForCurrentPart =
            Collections.emptyList();

    private String currentSearchString = ""; //$NON-NLS-1$

    private List<QuickSearchElementAndContributor> allElementsForCurrentPartAndCategory =
            Collections.emptyList();

    private List<QuickSearchElementAndContributor> matchingElementsForCurrentPart =
            Collections.emptyList();

    private SearchPattern searchPatternCaseSensitive = new SearchPattern(
            SearchPattern.RULE_EXACT_MATCH | SearchPattern.RULE_PREFIX_MATCH
                    | SearchPattern.RULE_PATTERN_MATCH
                    | SearchPattern.RULE_CAMELCASE_MATCH
                    | SearchPattern.RULE_BLANK_MATCH
                    | SearchPattern.RULE_CASE_SENSITIVE);

    public QuickSearchContributionHelper(IWorkbenchPartReference partRef) {
        setWorkBenchPart(partRef);
    }

    /**
     * return whether the current view part (last set with viewpart changed) has
     * quick search contributors.
     * 
     * @return
     */
    public static boolean viewHasQuickSearchContributors(
            IWorkbenchPartReference partRef) {
        for (ViewIdAndContribution contr : allContributions) {
            if (partRef.getId().equals(contr.getViewId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the list last setup from getMatchingElements()
     * 
     * @return
     */
    public List<QuickSearchElementAndContributor> getCurrentMatchingElements() {
        return matchingElementsForCurrentPart;
    }

    /**
     * @return the currentWorkbenchPart
     */
    public IWorkbenchPartReference getCurrentWorkbenchPart() {
        return workbenchPart;
    }

    public List<QuickSearchCategoryAndCheckstate> getSearchCategories() {
        return searchCategoriesForCurrentPart;
    }

    /**
     * @return the currentSearchString
     */
    public String getSearchString() {
        return currentSearchString;
    }

    /**
     * Cache all contributed elements for current workbench part and selected
     * category.
     */
    public void cacheContributedElements() {
        logToConsole("== > QuickSearchContributionHelper.cacheContributedElements()");
        allElementsForCurrentPartAndCategory =
                new ArrayList<QuickSearchElementAndContributor>();

        if (workbenchPart != null) {
            for (QuickSearchPartContributionAndProviders contr : contributionsForCurrentPart) {

                List<QuickSearchPopupCategory> enabledCategories =
                        getEnabledCategories();

                long start = System.currentTimeMillis();

                Collection<?> objects = null;
                if (enabledCategories.isEmpty()) {
                    objects = contr.getContentProvider().getElements();

                } else {
                    objects =
                            contr.getContentProvider()
                                    .getElements(enabledCategories);

                }

                long took = System.currentTimeMillis() - start;

                if (objects != null) {
                    logToConsole("    Took " + took + " ms to get " + objects.size() + " elements from Quick Search Content From: " + contr.getClass());//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-2$

                    LabelProvider labelProvider = contr.getLabelProvider();

                    for (Object o : objects) {
                        allElementsForCurrentPartAndCategory
                                .add(new QuickSearchElementAndContributor(o,
                                        contr));
                    }
                }
            }
        }

        //
        // Sort it.
        long startSort = System.currentTimeMillis();

        Collections.sort(allElementsForCurrentPartAndCategory,
                new Comparator<QuickSearchElementAndContributor>() {
                    @Override
                    public int compare(QuickSearchElementAndContributor o1,
                            QuickSearchElementAndContributor o2) {
                        return o1.getLabel().compareToIgnoreCase(o2.getLabel());
                    }
                });
        long sortTook = System.currentTimeMillis() - startSort;

        logToConsole("--------------------------------------------------------------------------------------");
        logToConsole("    Took " + sortTook + " ms to sort " + allElementsForCurrentPartAndCategory.size() + " quick search elements"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        logToConsole("<== QuickSearchContributionHelper.cacheContributedElements()");
        return;
    }

    /**
     * Get a flat list of the lowest level enabled categories (i.e. those
     * without children that represent real search able element types).
     * 
     * @return
     */
    private List<QuickSearchPopupCategory> getEnabledCategories() {
        List<QuickSearchPopupCategory> enabledCats =
                new ArrayList<QuickSearchPopupCategory>();

        recursiveGetEnabledCategories(searchCategoriesForCurrentPart,
                enabledCats,
                false);

        return enabledCats;
    }

    private void recursiveGetEnabledCategories(
            Collection<QuickSearchCategoryAndCheckstate> cats,
            List<QuickSearchPopupCategory> enabledCats, boolean forceEnabled) {

        for (QuickSearchCategoryAndCheckstate cat : cats) {
            if (!cat.getChildren().isEmpty()) {
                // If this category has children then recurs.
                // If this parent item is checked then force it's children to be
                // enabled too.
                recursiveGetEnabledCategories(cat.getChildren(),
                        enabledCats,
                        forceEnabled || cat.isChecked());

            } else {
                // lowest level category of objects add if enabled or parent
                // hierarchy is.
                if (forceEnabled || cat.isChecked()) {
                    enabledCats.add(cat.getCategory());
                }
            }
        }

        return;
    }

    /**
     * Reget the list of matching elements.
     */
    private void recacheMatchingElements() {
        //
        // Re-filter the list of elements
        //
        getMatchingElements();

        return;
    }

    /**
     * Sets the search element and attempts to reveal it in current part (via
     * the elements' quick search contributor)
     * 
     * @param currentSearchedElement
     *            the currentSearchedElement to set
     * @param popupClosed
     *            state of the quick search popup box (true for "closed", false
     *            for "open")
     * 
     * @return Screen Bounds of the selected object (i.e. in display coords) or
     *         <code>null</code>. This allows the quick search popup to move out
     *         of the way of selected object if necessary.
     */
    public Rectangle selectSearchElement(
            QuickSearchElementAndContributor currentSearchedElement,
            boolean popupClosed) {

        if (currentSearchedElement != null) {
            return currentSearchedElement.getContributor()
                    .selectAndReveal(workbenchPart,
                            currentSearchedElement.getContributedElement(),
                            currentSearchString,
                            popupClosed);
        }

        return null;
    }

    /**
     * @return the currentSearchString
     */
    public void setSearchString(String searchString) {
        currentSearchString = searchString;

        recacheMatchingElements();
    }

    /**
     * Do everything that needs doing when workbench part changes.
     * 
     * @param partRef
     * @return
     */
    public void setWorkBenchPart(IWorkbenchPartReference partRef) {
        logToConsole("==> QuickSearchContributionHelper.setWorkbenchPart() - load quick search conbtributions for workbench part"); //$NON-NLS-1$
        long start = System.currentTimeMillis();

        workbenchPart = partRef;

        //
        // Dispose anything for the current workbench part.
        if (contributionsForCurrentPart != null) {
            for (QuickSearchPartContributionAndProviders contr : contributionsForCurrentPart) {
                contr.dispose();
            }
        }

        //
        // Cache the list of contributions for current workbench part.
        //
        long s1 = System.currentTimeMillis();

        contributionsForCurrentPart = Collections.emptyList();

        if (workbenchPart != null) {
            String partId = partRef.getId();

            for (ViewIdAndContribution contr : allContributions) {
                if (partId.equals(contr.getViewId())) {
                    if (contributionsForCurrentPart == Collections.EMPTY_LIST) {
                        contributionsForCurrentPart =
                                new ArrayList<QuickSearchPartContributionAndProviders>();
                    }

                    long s3 = System.currentTimeMillis();

                    contributionsForCurrentPart
                            .add(new QuickSearchPartContributionAndProviders(
                                    workbenchPart, contr.contribution));

                    logToConsole("       Took "
                            + (System.currentTimeMillis() - s3)
                            + " ms to get create contribution and providers for contributor "
                            + contr.getContribution().getClass());
                }
            }
        }

        logToConsole("   Took " + (System.currentTimeMillis() - s1)
                + " ms to get " + contributionsForCurrentPart.size()
                + " contributors to workbench part");

        //
        // Get list of search categories from contributions.
        long s2 = System.currentTimeMillis();

        searchCategoriesForCurrentPart =
                new ArrayList<QuickSearchCategoryAndCheckstate>();
        if (workbenchPart != null) {
            for (QuickSearchPartContributionAndProviders contr : contributionsForCurrentPart) {
                Collection<QuickSearchPopupCategory> cats =
                        contr.getContentProvider().getCategories();

                if (cats != null && !cats.isEmpty()) {
                    for (QuickSearchPopupCategory cat : cats) {
                        searchCategoriesForCurrentPart
                                .add(new QuickSearchCategoryAndCheckstate(cat));
                    }
                }
            }

            // And sort the top level list by label (children in hierarchy are
            // sorted in QuickSearchPopupCategory
            Collections.sort(searchCategoriesForCurrentPart,
                    new Comparator<QuickSearchCategoryAndCheckstate>() {
                        @Override
                        public int compare(QuickSearchCategoryAndCheckstate o1,
                                QuickSearchCategoryAndCheckstate o2) {
                            return o1.getCategory().getLabel()
                                    .compareTo(o2.getCategory().getLabel());
                        }
                    });
        }

        logToConsole("   Took " + (System.currentTimeMillis() - s2)
                + " to load categories from contributors");

        logToConsole("<== QuickSearchContributionHelper.setWorkbenchPart() - took " + (System.currentTimeMillis() - start + " ms")); //$NON-NLS-1$ //$NON-NLS-2$

        return;
    }

    /**
     * Get filtered list of elements that match given regular expression
     * (terminating "*" always applied) from the last list of elements
     * contributed when recacheContributedElements() was called.
     * 
     * @param searchString
     * @return
     */
    private List<QuickSearchElementAndContributor> getMatchingElements() {

        if (currentSearchString == null) {
            currentSearchString = ""; //$NON-NLS-1$
        }

        if (currentSearchString.length() == 0) {
            matchingElementsForCurrentPart = Collections.emptyList();

        } else {
            matchingElementsForCurrentPart =
                    new ArrayList<QuickSearchElementAndContributor>();

            searchPatternCaseSensitive.setPattern(currentSearchString);

            for (QuickSearchElementAndContributor element : allElementsForCurrentPartAndCategory) {
                if (searchPatternCaseSensitive.matches(element.getLabel())) {
                    matchingElementsForCurrentPart.add(element);
                }
            }

        }

        return matchingElementsForCurrentPart;
    }

    /**
     * Load the Quick Search contributions contributed to all Views
     */
    private static void loadAllQuickSearchContributions() {
        long start = System.currentTimeMillis();
        logToConsole("==> QuickSearchContributionHelper.loadAllQuickSearchContribitions() - loading quick search ext point contributors"); //$NON-NLS-1$

        allContributions = new ArrayList<ViewIdAndContribution>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(XpdResourcesUIActivator.ID,
                                EXT_POINT_ID);

        if (point != null) {
            //
            // Go thru each contribution to this extension point.
            //
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] configElements =
                            ext.getConfigurationElements();

                    for (int i = 0; i < configElements.length; i++) {
                        IConfigurationElement configEl = configElements[i];

                        if (VIEW_CONTRIBUTION_EL.equals(configEl.getName())) {

                            try {
                                Object o =
                                        configEl.createExecutableExtension(CLASS_ATTR);
                                if (o instanceof AbstractQuickSearchPopupContribution) {
                                    allContributions
                                            .add(new ViewIdAndContribution(
                                                    configEl.getAttribute(VIEW_ID_ATTR),
                                                    (AbstractQuickSearchPopupContribution) o));
                                }

                            } catch (Exception e) {
                                throw new RuntimeException(
                                        "Failed loading quick search contribution from: " //$NON-NLS-1$
                                                + ext.getContributor()
                                                        .getName(), e);
                            }
                        }
                    }
                }
            }
        }

        logToConsole("<== QuickSearchContributionHelper.loadAllQuickSearchContribitions() - took " + (System.currentTimeMillis() - start) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

        return;
    }

    private static void logToConsole(String msg) {
        if (loggingOn) {
            System.out.println(msg);
        }
        return;
    }

}
