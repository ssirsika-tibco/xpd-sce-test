/**
 * <copyright>
 * </copyright>
 *
 * $Id: XPDLActionBarContributor.java 1.3 2005/04/01 15:41:25Z wzurek Development $
 */
package com.tibco.xpd.processwidget.viewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;

/**
 * This is the action bar contributor for the XPDL model editor.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class XPDLActionBarContributor
    extends EditingDomainActionBarContributor
    implements ISelectionChangedListener {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright 2004 TIBCO Software"; //$NON-NLS-1$

    /**
     * This keeps track of the active editor.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IEditorPart activeEditorPart;

    /**
     * This keeps track of the current selection provider.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ISelectionProvider selectionProvider;

    /**
     * This action opens the Properties view.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IAction showPropertiesViewAction =
        new Action("_UI_ShowPropertiesView_menu_item") { //$NON-NLS-1$
            public void run() {
                try {
                    getPage().showView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$
                }
                catch (PartInitException exception) {
                    //XPDLEditorPlugin.INSTANCE.log(exception);
                }
            }
        };

    /**
     * This action refreshes the viewer of the current editor if the editor
     * implements {@link org.eclipse.emf.common.ui.viewer.IViewerProvider}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IAction refreshViewerAction =
        new Action("_UI_RefreshViewer_menu_item") {  //$NON-NLS-1$
            public boolean isEnabled() {
                return activeEditorPart instanceof IViewerProvider;
            }

            public void run() {
                if (activeEditorPart instanceof IViewerProvider) {
                    Viewer viewer = ((IViewerProvider)activeEditorPart).getViewer();
                    if (viewer != null) {
                        viewer.refresh();
                    }
                }
            }
        };

    /**
     * This will contain one {@link org.eclipse.emf.edit.ui.action.CreateChildAction} corresponding to each descriptor
     * generated for the current selection by the item provider.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection createChildActions;

    /**
     * This is the menu manager into which menu contribution items should be added for CreateChild actions.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IMenuManager createChildMenuManager;

    /**
     * This will contain one {@link org.eclipse.emf.edit.ui.action.CreateSiblingAction} corresponding to each descriptor
     * generated for the current selection by the item provider.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection createSiblingActions;

    /**
     * This is the menu manager into which menu contribution items should be added for CreateSibling actions.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IMenuManager createSiblingMenuManager;

    /**
     * This creates an instance of the contributor.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XPDLActionBarContributor() {
        loadResourceAction = new LoadResourceAction();
        validateAction = new ValidateAction();
    }

    /**
     * This adds Separators for editor additions to the tool bar.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param toolBarManager
     * @generated
     */
    public void contributeToToolBar(IToolBarManager toolBarManager) {
        toolBarManager.add(new Separator("xpdl-settings")); //$NON-NLS-1$
        toolBarManager.add(new Separator("xpdl-additions")); //$NON-NLS-1$
    }

    /**
     * This adds to the menu bar a menu and some separators for editor additions,
     * as well as the sub-menus for object creation items.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param menuManager
     * @generated
     */
    public void contributeToMenu(IMenuManager menuManager) {
        super.contributeToMenu(menuManager);

        IMenuManager submenuManager = new MenuManager(
                "_UI_XPDLEditor_menu", "com.tibco.xpd.xpdl.modelMenuID"); //$NON-NLS-1$ //$NON-NLS-2$
        menuManager.insertAfter("additions", submenuManager); //$NON-NLS-1$
        submenuManager.add(new Separator("settings")); //$NON-NLS-1$
        submenuManager.add(new Separator("actions")); //$NON-NLS-1$
        submenuManager.add(new Separator("additions")); //$NON-NLS-1$
        submenuManager.add(new Separator("additions-end")); //$NON-NLS-1$

        // Prepare for CreateChild item addition or removal.
        //
        createChildMenuManager = new MenuManager(("_UI_CreateChild_menu_item"));  //$NON-NLS-1$
        submenuManager.insertBefore("additions", createChildMenuManager); //$NON-NLS-1$

        // Prepare for CreateSibling item addition or removal.
        //
        createSiblingMenuManager = new MenuManager(("_UI_CreateSibling_menu_item"));  //$NON-NLS-1$
        submenuManager.insertBefore("additions", createSiblingMenuManager); //$NON-NLS-1$

        // Force an update because Eclipse hides empty menus now.
        //
        submenuManager.addMenuListener
            (new IMenuListener() {
                 public void menuAboutToShow(IMenuManager mm) {
                     mm.updateAll(true);
                 }
             });

        addGlobalActions(submenuManager);
    }

    /**
     * When the active editor changes, this remembers the change and registers with it as a selection provider.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param part
     * @generated
     */
    public void setActiveEditor(IEditorPart part) {
        super.setActiveEditor(part);
        activeEditorPart = part;

        // Switch to the new selection provider.
        //
        if (selectionProvider != null) {
            selectionProvider.removeSelectionChangedListener(this);
        }
        if (part == null) {
            selectionProvider = null;
        }
        else {
            selectionProvider = part.getSite().getSelectionProvider();
            selectionProvider.addSelectionChangedListener(this);

            // Fake a selection changed event to update the menus.
            //
            if (selectionProvider.getSelection() != null) {
                selectionChanged(new SelectionChangedEvent(selectionProvider, selectionProvider.getSelection()));
            }
        }
    }

    /**
     * This implements {@link org.eclipse.jface.viewers.ISelectionChangedListener},
     * handling {@link SelectionChangedEvent} by querying for the children and siblings
     * that can be added to the selected object and updating the menus accordingly.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param event
     * @generated
     */
    public void selectionChanged(SelectionChangedEvent event) {
        // Remove any menu items for old selection.
        //
        if (createChildMenuManager != null) {
            depopulateManager(createChildMenuManager, createChildActions);
        }
        if (createSiblingMenuManager != null) {
            depopulateManager(createSiblingMenuManager, createSiblingActions);
        }

        // Query the new selection for appropriate new child/sibling descriptors
        //
        Collection newChildDescriptors = null;
        Collection newSiblingDescriptors = null;

        ISelection selection = event.getSelection();
        if (selection instanceof IStructuredSelection && ((IStructuredSelection)selection).size() == 1) {
            Object object = ((IStructuredSelection)selection).getFirstElement();

            EditingDomain domain = ((IEditingDomainProvider)activeEditorPart).getEditingDomain();

            newChildDescriptors = domain.getNewChildDescriptors(object, null);
            newSiblingDescriptors = domain.getNewChildDescriptors(null, object);
        }

        // Generate actions for selection; populate and redraw the menus.
        //
        createChildActions = generateCreateChildActions(newChildDescriptors, selection);
        createSiblingActions = generateCreateSiblingActions(newSiblingDescriptors, selection);

        if (createChildMenuManager != null) {
            populateManager(createChildMenuManager, createChildActions, null);
            createChildMenuManager.update(true);
        }
        if (createSiblingMenuManager != null) {
            populateManager(createSiblingMenuManager, createSiblingActions, null);
            createSiblingMenuManager.update(true);
        }
    }

    /**
     * This generates a {@link org.eclipse.emf.edit.ui.action.CreateChildAction} for each object in <code>descriptors</code>,
     * and returns the collection of these actions.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param descriptors
     * @param selection
     * @return
     * @generated
     */
    protected Collection generateCreateChildActions(Collection descriptors, ISelection selection) {
        Collection actions = new ArrayList();
        if (descriptors != null) {
            for (Iterator i = descriptors.iterator(); i.hasNext(); ) {
                actions.add(new CreateChildAction(activeEditorPart, selection, i.next()));
            }
        }
        return actions;
    }

    /**
     * This generates a {@link org.eclipse.emf.edit.ui.action.CreateSiblingAction} for each object in <code>descriptors</code>,
     * and returns the collection of these actions.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param descriptors
     * @param selection
     * @return
     * @generated
     */
    protected Collection generateCreateSiblingActions(Collection descriptors, ISelection selection) {
        Collection actions = new ArrayList();
        if (descriptors != null) {
            for (Iterator i = descriptors.iterator(); i.hasNext(); ) {
                actions.add(new CreateSiblingAction(activeEditorPart, selection, i.next()));
            }
        }
        return actions;
    }

    /**
     * This populates the specified <code>manager</code> with {@link org.eclipse.jface.action.ActionContributionItem}s
     * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection,
     * by inserting them before the specified contribution item <code>contributionID</code>.
     * If <code>ID</code> is <code>null</code>, they are simply added.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param manager
     * @param actions
     * @param contributionID
     * @generated
     */
    protected void populateManager(IContributionManager manager, Collection actions, String contributionID) {
        if (actions != null) {
            for (Iterator i = actions.iterator(); i.hasNext(); ) {
                IAction action = (IAction)i.next();
                if (contributionID != null) {
                    manager.insertBefore(contributionID, action);
                }
                else {
                    manager.add(action);
                }
            }
        }
    }
        
    /**
     * This removes from the specified <code>manager</code> all {@link org.eclipse.jface.action.ActionContributionItem}s
     * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param manager
     * @param actions
     * @generated
     */
    protected void depopulateManager(IContributionManager manager, Collection actions) {
        if (actions != null) {
            IContributionItem[] items = manager.getItems();
            for (int i = 0; i < items.length; i++) {
                // Look into SubContributionItems
                //
                IContributionItem contributionItem = items[i];
                while (contributionItem instanceof SubContributionItem) {
                    contributionItem = ((SubContributionItem)contributionItem).getInnerItem();
                }

                // Delete the ActionContributionItems with matching action.
                //
                if (contributionItem instanceof ActionContributionItem) {
                    IAction action = ((ActionContributionItem)contributionItem).getAction();
                    if (actions.contains(action)) {
                        manager.remove(contributionItem);
                    }
                }
            }
        }
    }

    /**
     * This populates the pop-up menu before it appears.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param menuManager
     * @generated
     */
    public void menuAboutToShow(IMenuManager menuManager) {
        super.menuAboutToShow(menuManager);
        MenuManager submenuManager = null;

        submenuManager = new MenuManager(("_UI_CreateChild_menu_item"));  //$NON-NLS-1$
        populateManager(submenuManager, createChildActions, null);
        menuManager.insertBefore("additions", submenuManager); //$NON-NLS-1$

        submenuManager = new MenuManager(("_UI_CreateSibling_menu_item")); //$NON-NLS-1$
        populateManager(submenuManager, createSiblingActions, null);
        menuManager.insertBefore("additions", submenuManager); //$NON-NLS-1$
    }

    /**
     * This inserts global actions before the "additions-end" separator.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param menuManager
     * @generated
     */
    protected void addGlobalActions(IMenuManager menuManager) {
        menuManager.insertAfter("additions-end", new Separator("ui-actions")); //$NON-NLS-1$ //$NON-NLS-2$
        menuManager.insertAfter("ui-actions", showPropertiesViewAction); //$NON-NLS-1$

        refreshViewerAction.setEnabled(refreshViewerAction.isEnabled());		
        menuManager.insertAfter("ui-actions", refreshViewerAction); //$NON-NLS-1$

        super.addGlobalActions(menuManager);
    }

}
