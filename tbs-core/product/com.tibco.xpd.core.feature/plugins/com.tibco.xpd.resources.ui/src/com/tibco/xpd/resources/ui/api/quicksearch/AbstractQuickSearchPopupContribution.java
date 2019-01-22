/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopup;
import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopupAction;

/**
 * AbstractQuickSearchPopupContribution
 * <p>
 * Class implementation required for contributions to the
 * <code>com.tibco.xpd.resources.ui.quickSearchContribution</code> extension
 * point.
 * <p>
 * Once the {@link QuickSearchPopup} is used/created for a particular view
 * (maybe using {@link QuickSearchPopupAction} an implementation of this
 * <code>AbstractQuickSearchPopupContribution</code> class is contributed to the
 * <code>quickSearchContribution</code> extension point to contribute searchable
 * content (and content categories).
 * </p>
 * <p>
 * The QuickSearchPopup UI uses these contributions to gather together all
 * searchable content for a view and then allows the user to filter thru this
 * content in a standard way.
 * </p>
 * 
 * @author aallway
 * @since 3.1
 */
public abstract class AbstractQuickSearchPopupContribution {

    public AbstractQuickSearchPopupContribution() {
    }

    /**
     * Create the searchable content provider for the given view part.
     * <p>
     * This will only be called for the view part(s) that this class is
     * contributed for.
     * 
     * @param partRef
     *            View part reference.
     * 
     * @return The content provider.
     */
    public abstract AbstractQuickSearchContentProvider createContentProvider(
            IWorkbenchPartReference partRef);

    /**
     * Create the searchable content provider for the given view part.
     * <p>
     * This will only be called for the view part(s) that this class is
     * contributed for.
     * 
     * @param partRef
     *            View part reference.
     * 
     * @return The label provider
     */
    public abstract AbstractQuickSearchLabelProvider createLabelProvider(
            IWorkbenchPartReference partRef);

    /**
     * Select and reveal the given object (which is one of the objects returned
     * by your content provider).
     * <p>
     * This is called when the user performs a 'goto' using the quick search
     * popup.
     * <p>
     * Use
     * {@link AbstractQuickSearchPopupContribution#selectAndReveal(IWorkbenchPartReference, Object, String, boolean)}
     * if the user in interested in knowing the state of the quick search popup
     * box (open/closed) and the search string.
     * 
     * @param partRef
     *            View part reference
     * @param element
     *            The object to select and reveal
     * 
     * @return Screen Bounds of the selected object (i.e. in display coords) or
     *         <code>null</code>. This allows the quick search popup to move out
     *         of the way of selected object if necessary.
     */
    public abstract Rectangle selectAndReveal(IWorkbenchPartReference partRef,
            Object element);

    /*
     * SCF-292: Saket: Adding an overrideable non-abstract overload to add an
     * indication of which action is being executed.
     */
    /**
     * Select and reveal the given object (which is one of the objects returned
     * by your content provider).
     * <p>
     * This is called when the user performs a 'goto' using the quick search
     * popup.
     * 
     * @param partRef
     *            View part reference
     * @param element
     *            The object to select and reveal
     * @param searchString
     *            The searchString parameter provides the string for which the
     *            user is searching, to enable the contributor to (for example)
     *            select the corresponding text range in a text widget.
     * @param closing
     *            The closing parameter would be false in the case of Left/Right
     *            arrow navigation and true in the case of Enter/Double-Click
     *            navigation.
     * 
     * @return Screen Bounds of the selected object (i.e. in display coords) or
     *         <code>null</code>. This allows the quick search popup to move out
     *         of the way of selected object if necessary.
     */
    public Rectangle selectAndReveal(IWorkbenchPartReference partRef,
            Object element, String searchString, boolean closing) {
        return selectAndReveal(partRef, element);
    }

}
