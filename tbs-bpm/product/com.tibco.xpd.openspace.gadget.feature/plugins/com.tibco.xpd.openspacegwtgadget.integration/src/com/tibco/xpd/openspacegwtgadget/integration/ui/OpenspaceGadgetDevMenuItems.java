/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.CompoundContributionItem;

import com.tibco.xpd.openspacegwtgadget.integration.samples.OpenspaceSampleCreatorContribution;
import com.tibco.xpd.openspacegwtgadget.integration.ui.actions.CreateOpenspaceSampleGadgetAction;
import com.tibco.xpd.openspacegwtgadget.integration.ui.actions.EnableOpenspaceGadgetDevAction;
import com.tibco.xpd.openspacegwtgadget.integration.ui.actions.ExportOpenspaceGadgetProjectDaaAction;

/**
 * Contribution item that provides the set of actions available for openspace
 * development options (enable openspace development, create samples etc.
 * 
 * @author aallway
 * @since 15 Jan 2013
 */
public class OpenspaceGadgetDevMenuItems extends CompoundContributionItem {

    private Object currentSelection;

    private boolean contextDriven = false;

    /**
     * 
     * @param currentSelection
     * @param contextDriven
     *            Whether actions should run in contextDriven only mode (i.e. if
     *            appropriate object not selected then disabled etc) as opposed
     *            to only being initially context driven but, where necessary,
     *            providing the user the option to select context via dialog.
     */
    public OpenspaceGadgetDevMenuItems(Object currentSelection,
            boolean contextDriven) {
        super();
        this.currentSelection = currentSelection;
        this.contextDriven = contextDriven;
    }

    public OpenspaceGadgetDevMenuItems() {
        super();
    }

    /**
     * Sets current selection.
     * 
     * @param selection
     *            the selection to be set.
     */
    public void setSelection(Object selection) {
        this.currentSelection = selection;
    }

    /**
     * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
     * 
     * @return
     */
    @Override
    protected IContributionItem[] getContributionItems() {
        List<IContributionItem> contributionItems =
                new ArrayList<IContributionItem>();

        /*
         * Add the enable openspace gadget development action.
         */
        contributionItems.add(new ActionContributionItem(
                new EnableOpenspaceGadgetDevAction(currentSelection)));

        /*
         * And then an creation wizard action for each openspace sample
         * contribution.
         */
        Collection<OpenspaceSampleCreatorContribution> sampleContributions =
                OpenspaceSampleCreatorContribution
                        .getOpenspaceSampleContributions();

        if (sampleContributions.size() > 0) {
            contributionItems.add(new Separator());
        }

        for (OpenspaceSampleCreatorContribution sampleContribution : sampleContributions) {

            CreateOpenspaceSampleGadgetAction action =
                    new CreateOpenspaceSampleGadgetAction(sampleContribution,
                            currentSelection, contextDriven);

            ActionContributionItem contributionItem =
                    new ActionContributionItem(action);

            contributionItems.add(contributionItem);

        }

        /* Add The export as DAA option. */
        contributionItems.add(new Separator());
        contributionItems.add(new ActionContributionItem(
                new ExportOpenspaceGadgetProjectDaaAction(currentSelection)));

        return contributionItems.toArray(new IContributionItem[0]);
    }

}
