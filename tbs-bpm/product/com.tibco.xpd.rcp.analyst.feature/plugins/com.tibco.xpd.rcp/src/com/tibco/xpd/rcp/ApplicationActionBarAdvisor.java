/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.tibco.xpd.rcp.ribbon.RibbonConsts;

/**
 * Action bar configuration.
 * 
 * @author njpatel
 * 
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui
     * .IWorkbenchWindow)
     */
    @Override
    protected void makeActions(IWorkbenchWindow window) {
        /*
         * Register the global actions to add to the ribbon here - at this point
         * the ribbon will not have been created and we will just pick up the
         * registered action when it gets created.
         */
        registerAction(RibbonConsts.ACTION_SAVE.getId(),
                ActionFactory.SAVE,
                window);
        registerAction(RibbonConsts.ACTION_FIND.getId(),
                ActionFactory.FIND,
                window);
        registerAction(RibbonConsts.ACTION_CUT.getId(),
                ActionFactory.CUT,
                window);
        registerAction(RibbonConsts.ACTION_COPY.getId(),
                ActionFactory.COPY,
                window);
        registerAction(RibbonConsts.ACTION_PASTE.getId(),
                ActionFactory.PASTE,
                window);
        registerAction(RibbonConsts.ACTION_DELETE.getId(),
                ActionFactory.DELETE,
                window);
        registerAction(RibbonConsts.ACTION_UNDO.getId(),
                ActionFactory.UNDO,
                window);
        registerAction(RibbonConsts.ACTION_REDO.getId(),
                ActionFactory.REDO,
                window);
        // registerAction(RibbonConsts.ACTION_REVERT.getId(),
        // ActionFactory.REVERT,
        // window);
        registerAction(RibbonConsts.ACTION_PRINT.getId(),
                ActionFactory.PRINT,
                window);
        registerAction(RibbonConsts.ACTION_RENAME.getId(),
                ActionFactory.RENAME,
                window);
    }

    /**
     * Register a global action. This create the action from the given factory
     * and register it with the key binding service and also the activator for
     * the ribbon to use.
     * 
     * @param id
     * @param factory
     * @param window
     */
    private void registerAction(String id, ActionFactory factory,
            IWorkbenchWindow window) {
        IWorkbenchAction action = factory.create(window);
        register(action);
        RCPActivator.registerGlobalAction(id, action);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.jface
     * .action.IMenuManager)
     */
    @Override
    protected void fillMenuBar(IMenuManager menuBar) {
    }

}
