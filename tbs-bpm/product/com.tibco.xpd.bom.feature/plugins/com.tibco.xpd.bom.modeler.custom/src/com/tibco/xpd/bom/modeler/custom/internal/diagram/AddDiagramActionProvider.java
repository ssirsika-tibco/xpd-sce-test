/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.diagram;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.ui.providers.DiagramGroupTransientItemProvider;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Action provider to add a new user {@link Diagram} to a BOM. This just uses
 * the <code>NewDiagramWizard</code>.
 * 
 * @see NewDiagramWizard
 * 
 * @author njpatel
 * 
 */
public class AddDiagramActionProvider extends CommonActionProvider {

    private static final String ADD_CHILD_SUBMENU_ID = "bom.diagram.addChild"; //$NON-NLS-1$

    private final AddDiagramAction addDiagramAction;

    private IWorkbenchPage page;

    public AddDiagramActionProvider() {
        addDiagramAction = new AddDiagramAction();
        addDiagramAction
                .setDescription(Messages.AddDiagramActionProvider_addDiagram_action_shortdesc);
        addDiagramAction.setId("add_diagram"); //$NON-NLS-1$
        addDiagramAction
                .setText(Messages.AddDiagramActionProvider_addDiagram_action);
        addDiagramAction.setImageDescriptor(Activator
                .imageDescriptorFromPlugin(Activator.PLUGIN_ID,
                        "/icons/obj16/View Diagram 16 n p.png")); //$NON-NLS-1$
        page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();

            if (selection.size() == 1) {
                Object sel = selection.getFirstElement();
                BOMWorkingCopy bomWc = null;

                if (sel instanceof IFile) {
                    WorkingCopy wc =
                            WorkingCopyUtil.getWorkingCopy((IResource) sel);
                    if (wc instanceof BOMWorkingCopy) {
                        bomWc = (BOMWorkingCopy) wc;
                    }
                } else if (sel instanceof DiagramGroupTransientItemProvider) {
                    bomWc =
                            ((DiagramGroupTransientItemProvider) sel)
                                    .getWorkingCopy();
                }

                if (bomWc != null) {
                    if (!bomWc.getEclipseResources().isEmpty()) {
                        addDiagramAction.configureAction(bomWc
                                .getEclipseResources().get(0));

                        /*
                         * Create an "Add Child..." submenu to match context
                         * menu of other elements in the BOM
                         */
                        IMenuManager addChildSubMenu =
                                new MenuManager(
                                        Messages.AddDiagramActionProvider_addChild_submenu_label,
                                        ADD_CHILD_SUBMENU_ID);

                        /*
                         * Working copy is read only if the project is
                         * pre-compiled. Then we want to disable this action
                         */
                        boolean isWCReadonly = false;
                        if (bomWc.isReadOnly()) {

                            isWCReadonly = true;
                        }

                        /*
                         * ACE-2473: Saket: Action should be disabled for locked
                         * application.
                         */
                        if (selection.getFirstElement() instanceof EObject) {
                            isWCReadonly = isWCReadonly || (new GovernanceStateService())
                                    .isLockedForProduction((EObject) (selection.getFirstElement()));
                        }

                        if (isWCReadonly) {

                            addDiagramAction.setEnabled(false);
                        } else {

                            addDiagramAction.setEnabled(true);
                        }
                        addChildSubMenu.add(addDiagramAction);
                        menu.appendToGroup(ICommonMenuConstants.GROUP_NEW,
                                addChildSubMenu);
                    }
                }
            }
        }
    }

    /**
     * Action to add a new {@link Diagram}.
     * 
     * @author njpatel
     * 
     */
    private class AddDiagramAction extends Action {

        private IResource file;

        public AddDiagramAction() {
        }

        public void configureAction(IResource file) {
            this.file = file;
        }

        @Override
        public void run() {
            NewDiagramWizard wizard = new NewDiagramWizard();
            wizard.init(page.getWorkbenchWindow().getWorkbench(),
                    new StructuredSelection(file));
            WizardDialog dlg =
                    new WizardDialog(getActionSite().getViewSite().getShell(),
                            wizard);
            dlg.open();
        }

    }
}
