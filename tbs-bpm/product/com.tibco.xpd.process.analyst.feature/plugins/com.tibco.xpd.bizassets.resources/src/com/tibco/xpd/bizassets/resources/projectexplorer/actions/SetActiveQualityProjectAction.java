/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.projectexplorer.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.bizassets.resources.internal.Messages;
import com.tibco.xpd.bizassets.resources.wizard.QualityArchiveUtil;

/**
 * @author nwilson
 */
public class SetActiveQualityProjectAction extends BaseSelectionListenerAction {

    public static final String ID =
            PlatformUI.PLUGIN_ID + ".SetActiveQualityProjectAction"; //$NON-NLS-1$

    /**
     * @param text
     */
    public SetActiveQualityProjectAction() {
        super(Messages.SetActiveQualityProjectAction_activateActionName);

        setToolTipText(Messages.SetActiveQualityProjectAction_activateActionTooltip);
        setId(ID);
    }

    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        boolean show = super.updateSelection(selection);
        if (show) {
            show = selection.size() == 1;
        }
        return show;
    }

    @Override
    public void run() {
        IStructuredSelection selection = getStructuredSelection();
        if (selection.size() == 1) {
            Object item = selection.getFirstElement();
            if (item instanceof IProject) {
                IProject project = (IProject) item;
                QualityArchiveUtil.setActiveQualityArchiveProject(project);
            }
        }
    }

}
