package com.tibco.xpd.resources.precompile.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Pre-Compile Action provider that provides the context menu options on the
 * project for enable/disable pre-compile
 * 
 * @author bharge
 * @since 29 May 2015
 */
public class PrecompileActionProvider extends CommonActionProvider {

    public PrecompileActionProvider() {
    }

    /**
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     * 
     * @param menu
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {

        MenuManager subMenu =
                new MenuManager(Messages.PreCompileProject_menu_label);

        if (null != getContext()
                && getContext().getSelection() instanceof IStructuredSelection) {

            IStructuredSelection structuredSelection =
                    (IStructuredSelection) getContext().getSelection();

            /*
             * Do not show the Pre-Compile Project menu if more than one project
             * is selected
             */
            if (structuredSelection.toList().size() > 1) {

                return;
            }

            if (structuredSelection.getFirstElement() instanceof IProject) {

                IProject project =
                        (IProject) structuredSelection.getFirstElement();

                EnablePreCompileProjectAction enablePrecompileAction =
                        new EnablePreCompileProjectAction(
                                Messages.PreCompileProject_menu_enable_menu_label);
                /* Set enable pre-compile image on the enable action */
                enablePrecompileAction
                        .setImageDescriptor(XpdResourcesUIActivator
                                .getDefault()
                                .getImageRegistry()
                                .getDescriptor(XpdResourcesUIConstants.ENABLE_PRECOMPILE_IMG_PATH));

                DisablePreCompileProjectAction disablePrecompileAction =
                        new DisablePreCompileProjectAction(
                                Messages.PreCompileProject_menu_disable_menu_label);
                /* Set disable pre-compile image on the disable action */
                disablePrecompileAction
                        .setImageDescriptor(XpdResourcesUIActivator
                                .getDefault()
                                .getImageRegistry()
                                .getDescriptor(XpdResourcesUIConstants.DISABLE_PRECOMPILE_IMG_PATH));

                subMenu.add(enablePrecompileAction);
                subMenu.add(disablePrecompileAction);

                enablePrecompileAction.updateSelection(structuredSelection);
                disablePrecompileAction.updateSelection(structuredSelection);

                boolean isPrecompiled =
                        ProjectUtil.isPrecompiledProject(project);
                if (isPrecompiled) {
                    /*
                     * if the project is pre-compiled then enable must be
                     * disabled and disable must be enabled
                     */
                    enablePrecompileAction.setEnabled(false);
                    disablePrecompileAction.setEnabled(true);
                } else {
                    /*
                     * if the project is not pre-compiled then enable must be
                     * enabled and disable must be disabled
                     */
                    disablePrecompileAction.setEnabled(false);
                    enablePrecompileAction.setEnabled(true);
                }

                menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, subMenu);
            }
        }
    }
}
