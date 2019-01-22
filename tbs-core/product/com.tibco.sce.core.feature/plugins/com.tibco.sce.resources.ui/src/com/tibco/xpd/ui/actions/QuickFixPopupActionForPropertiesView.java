/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.ui.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;


/**
 * 
 * 
 * @author aallway
 * @since 3.3 (18 Jan 2010)
 */
public class QuickFixPopupActionForPropertiesView extends QuickFixPopupAction
        implements ISelectionListener {

    private final static String VIEW_ID = "org.eclipse.ui.views.PropertySheet"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.projectexplorer.actions.providers.QuickFixPopupAction
     * #init(org.eclipse.ui.IViewPart)
     */
    @Override
    public void init(IViewPart view) {
        super.init(view);

        /*
         * Listen to the page that owns the property sheet for seleciton changes
         * - the properties view selection is the selected tab NOT the object
         * that's set as input!
         */
        view.getSite().getPage().addSelectionListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.
     * IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        /*
         * When you select object in the input provider for property sheet (like
         * the project explorer) you get proper selection here. Unfortunetely
         * for reasons best known to Eclipse bods, when you then activate the
         * proeprties view it call selected changed again with an empty
         * selection!!!
         * 
         * So ignore selection changes from the property sheet itself - which
         * admittedly looks bizarre for this action which is specially designed
         * for properties view!!!!!
         */
        if (!VIEW_ID.equals(part.getSite().getId())) {
            internalSelectionChanged(selection);
        }

        return;
    }
}
