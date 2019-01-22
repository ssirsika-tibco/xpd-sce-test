/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;

/**
 * Conbtributes actions to the openspace gadget dev popup menu on main toolbar
 * (via plugin.xml contribution)
 * 
 * @author aallway
 * @since 15 Jan 2013
 */
public class OpenspaceToolbarExtensionFactory
        extends ExtensionContributionFactory {

    public OpenspaceToolbarExtensionFactory() {
    }

    /**
     * @see org.eclipse.ui.menus.AbstractContributionFactory#createContributionItems(org.eclipse.ui.services.IServiceLocator,
     *      org.eclipse.ui.menus.IContributionRoot)
     * 
     * @param serviceLocator
     * @param additions
     */
    @Override
    public void createContributionItems(final IServiceLocator serviceLocator,
            IContributionRoot additions) {

        Object initialtSelection = OpenspaceGadgetDevSelectionUtil
                .getSelectionFirstElement(serviceLocator);

        /*
         * Compound contributor that dynamically creates drop-down menu items.
         */
        final OpenspaceGadgetDevMenuItems gadgetMenuItems =
                new OpenspaceGadgetDevMenuItems(initialtSelection, false);
        /*
         * Visible when - selection is on or content of a GWT Gadget project.
         */
        Expression visibleWhen = new Expression() {

            @Override
            public EvaluationResult evaluate(IEvaluationContext context)
                    throws CoreException {
                /*
                 * Set selection before gadgetMenuItems.getContributionItems()
                 * is being run and creates sub-menu actions with correct
                 * enablement.
                 */
                Object currentSelection = OpenspaceGadgetDevSelectionUtil
                        .getSelectionFirstElement(serviceLocator);
                gadgetMenuItems.setSelection(currentSelection);

                IProject project = OpenspaceGadgetDevPropertyTester
                        .getProject(currentSelection);
                if (project != null
                        && OpenspaceGadgetNature.isGWTGadgetProject(project)) {
                    return EvaluationResult.TRUE;
                } else {
                    return EvaluationResult.TRUE;
                }
            }
        };

        /*
         * Then add the standard set of "Enable Openspace Gadget Dev" + create
         * samples entries
         */
        additions.addContributionItem(gadgetMenuItems, visibleWhen);

    }
}
