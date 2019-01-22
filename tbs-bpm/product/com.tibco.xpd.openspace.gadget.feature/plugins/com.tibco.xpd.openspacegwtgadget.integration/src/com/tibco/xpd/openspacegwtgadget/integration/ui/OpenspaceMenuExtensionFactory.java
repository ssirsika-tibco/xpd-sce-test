/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;

/**
 * Conbtributes actions to the project explorer popup menu (via plugin.xml
 * contribution)
 * 
 * @author aallway
 * @since 15 Jan 2013
 */
public class OpenspaceMenuExtensionFactory
        extends ExtensionContributionFactory {

    public OpenspaceMenuExtensionFactory() {
    }

    /**
     * @see org.eclipse.ui.menus.AbstractContributionFactory#createContributionItems(org.eclipse.ui.services.IServiceLocator,
     *      org.eclipse.ui.menus.IContributionRoot)
     * 
     * @param serviceLocator
     * @param additions
     */
    @Override
    public void createContributionItems(IServiceLocator serviceLocator,
            IContributionRoot additions) {

        /*
         * As we are only used for popup menu/toolbar dropdown-menu purposes, we
         * are recreated each time that user activates menu/button. Therefor it
         * is safe to use the selection provided via service locator.
         */
        Object currentSelection = OpenspaceGadgetDevSelectionUtil
                .getSelectionFirstElement(serviceLocator);

        /*
         * Visible when - selection is on or content of a GWT Gadget project.
         */
        final Object finalSelection = currentSelection;
        Expression visibleWhen = new Expression() {

            @Override
            public EvaluationResult evaluate(IEvaluationContext context)
                    throws CoreException {
                IProject project = OpenspaceGadgetDevPropertyTester
                        .getProject(finalSelection);
                if (project != null
                        && OpenspaceGadgetNature.isGWTGadgetProject(project)) {
                    return EvaluationResult.TRUE;
                } else {
                    return EvaluationResult.FALSE;
                }
            }
        };

        /*
         * XPD-8937: I think visibility for pop-up menu contributions in eclipse
         * oxygen has changed (even if the evaluated expression returns false,
         * because sub-menu is created and available main pop-up menu is shown),
         * so creating the sub-menu only if the selection is eligible for menu
         * option
         */
        IProject project =
                OpenspaceGadgetDevPropertyTester.getProject(currentSelection);
        if (project != null
                && OpenspaceGadgetNature.isGWTGadgetProject(project)) {
            /*
             * For contribution to context menu, we need to contribute a
             * sub-menu...
             */
            MenuManager openspaceGadgetDevSubMenu = new MenuManager(
                    Messages.OpenspaceMenuExtensionFactory_OpenspacegadgetDev_menu,
                    OpenspaceGadgetPlugin.getImageDescriptor(
                            OpenspaceGadgetPlugin.IMG_OPENSPACEGADGET_POPUP),
                    OpenspaceGadgetPlugin.PLUGIN_ID + "." //$NON-NLS-1$
                            + "openspaceGadgetDevMenu"); //$NON-NLS-1$

            /*
             * Then add the standard set of "Enable Openspace Gadget Dev" +
             * create samples entries
             */
            openspaceGadgetDevSubMenu.add(
                    new OpenspaceGadgetDevMenuItems(currentSelection, true));

            additions.addContributionItem(openspaceGadgetDevSubMenu,
                    visibleWhen);
        }
    }
}
