/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.ColorUtil;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.SystemAction;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.resources.systemActions.ISystemAction;
import com.tibco.xpd.om.resources.systemActions.ISystemActionComponent;
import com.tibco.xpd.om.resources.systemActions.SystemActionManager;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes.QualifiedValueColumn;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTreeControl;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Properties section for the System Actions tab.
 * 
 * @author njpatel
 * 
 */
public class SystemActionTabSection extends AbstractTransactionalSection
        implements IFilter {

    private TreeViewer viewer;

    private final SystemActionManager actionManager;

    private EObject currentInput;

    private Font tableHeaderFont;

    private boolean useComponentFolders = true;

    /**
     * Properties section for the System Actions tab.
     */
    public SystemActionTabSection() {
        actionManager = SystemActionManager.getInstance();
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {

        EObject eo = resollveInput(toTest);

        /*
         * XPD-5300: Don't show for a Dynamic OrgUnit
         */
        if (eo instanceof DynamicOrgUnit) {
            return false;
        }

        if (eo instanceof Authorizable) {

            if (!useComponentFolders) {

                Collection<ISystemAction> actionsForAuthorizable =
                        actionManager.getActionsFor(eo);
                Collection<ISystemAction> actions =
                        filterGlobalDataActions(actionsForAuthorizable);
                return actions != null && !actions.isEmpty();
            } else {

                Collection<ISystemActionComponent> sysActionCompsForAuthorizable =
                        actionManager.getSystemActionComponentsFor(eo);
                Collection<ISystemActionComponent> componentsWithActions =
                        filterGlobalDataActionComponents(sysActionCompsForAuthorizable);

                return componentsWithActions != null
                        && !componentsWithActions.isEmpty();
            }

        }
        return false;
    }

    /**
     * Checks whether global data is enabled using command line arg
     * "-Dglobaldata"
     * 
     * This check is expected to only be temporary; whilst Global Data is in
     * development.
     */
    public static boolean isGlobalDataEnabled() {
        return Boolean.parseBoolean(System.getProperty("globaldata", "true"));//$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @param eo
     * @return
     */
    private Collection<ISystemAction> filterGlobalDataActions(
            Collection<ISystemAction> actionsFor) {

        /* If not for GlobalData, exclude the BDS GlobalData System Actions */
        if (!isGlobalDataEnabled()) {

            List<ISystemAction> filteredActionsComponents =
                    new ArrayList<ISystemAction>();
            /* filter out the BDS System Actions for Global Data */
            for (ISystemAction systemAction : actionsFor) {

                if (!systemAction.getComponent().getId()
                        .equalsIgnoreCase("BDS")) { //$NON-NLS-1$
                    /* Include if not BDS GlobalData System Actions */
                    filteredActionsComponents.add(systemAction);
                }
            }
            return filteredActionsComponents;
        }
        return actionsFor;
    }

    /**
     * @param eo
     * @return
     */
    private Collection<ISystemActionComponent> filterGlobalDataActionComponents(
            Collection<ISystemActionComponent> systemActionComponentsFor) {

        /* If not for GlobalData, exclude the BDS GlobalData System Actions */
        if (!isGlobalDataEnabled()) {

            List<ISystemActionComponent> filteredActionsComponents =
                    new ArrayList<ISystemActionComponent>();
            /* filter out the BDS System Actions for Global Data */
            for (ISystemActionComponent iSystemActionComponent : systemActionComponentsFor) {

                if (!iSystemActionComponent.getId().equalsIgnoreCase("BDS")) { //$NON-NLS-1$
                    /* Include if not BDS GlobalData System Actions */
                    filteredActionsComponents.add(iSystemActionComponent);
                }
            }
            return filteredActionsComponents;
        }

        return systemActionComponentsFor;
    }

    @Override
    public void dispose() {
        if (tableHeaderFont != null) {
            tableHeaderFont.dispose();
            tableHeaderFont = null;
        }
        super.dispose();
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());
        Label label =
                toolkit.createLabel(root,
                        Messages.SystemActionTabSection_systemActions_label);
        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;
        label.setLayoutData(data);
        label.setForeground(ColorConstants.darkGray);
        tableHeaderFont = new Font(root.getDisplay(), "Arial", 10, //$NON-NLS-1$
                SWT.BOLD);
        label.setFont(tableHeaderFont);

        SystemActionTable actionTable = new SystemActionTable(root, toolkit);
        actionTable.setBackground(root.getBackground());
        actionTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        viewer = actionTable.getTreeViewer();
        viewer.getTree().setHeaderVisible(true);
        viewer.getTree().setLinesVisible(true);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Table will handle the update of the model.
        return null;
    }

    @Override
    protected void doRefresh() {
        if (viewer != null && !viewer.getControl().isDisposed()) {
            EObject input = getInput();
            if (input instanceof Authorizable) {
                // Update input only if it has changed
                if (input != currentInput) {
                    currentInput = input;

                    if (!useComponentFolders) {
                        Collection<ISystemAction> actionsForAuthorizable =
                                actionManager.getActionsFor(input);
                        viewer.setInput(filterGlobalDataActions(actionsForAuthorizable));
                    } else {
                        Collection<ISystemActionComponent> sysActionCompsForAuthorizable =
                                actionManager
                                        .getSystemActionComponentsFor(input);
                        viewer.setInput(filterGlobalDataActionComponents(sysActionCompsForAuthorizable));
                    }

                } else {
                    viewer.refresh();
                }
            }
        }
    }

    /**
     * Get the system action definition related to the stored system action in
     * the model.
     * 
     * @param systemAction
     * @return
     */
    private ISystemAction getSystemActionDefinition(SystemAction systemAction) {
        if (systemAction != null) {
            ISystemActionComponent component =
                    actionManager.getComponent(systemAction.getComponent());
            if (component != null) {
                Collection<ISystemAction> actions = component.getActions();
                if (actions != null) {
                    for (ISystemAction action : actions) {
                        if (action.getId().equals(systemAction.getActionId())) {
                            return action;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the {@link SystemAction} set in the given {@link Authorizable} object
     * that matches the registered system action value.
     * 
     * @param authElem
     * @param actionValue
     * @return
     */
    private SystemAction getSystemAction(Authorizable authElem,
            ISystemAction actionValue) {
        if (authElem != null && authElem.getSystemActions() != null
                && actionValue != null) {
            for (SystemAction sysAction : authElem.getSystemActions()) {
                if (matches(sysAction, actionValue)) {
                    return sysAction;
                }
            }
        }
        return null;
    }

    /**
     * Check if the given action in the OM matches a registered system action
     * value.
     * 
     * @param action
     *            OM action
     * @param sysActionValue
     *            registered system action
     * @return <code>true</code> if they match
     */
    private boolean matches(SystemAction action, ISystemAction sysActionValue) {
        if (action != null && sysActionValue != null) {
            if (action.getActionId() != null
                    && action.getActionId().equals(sysActionValue.getId())) {
                if (action.getComponent() != null
                        && sysActionValue.getComponent() != null) {
                    return action.getComponent().equals(sysActionValue
                            .getComponent().getId());
                }
            }
        }
        return false;
    }

    /**
     * The system action tree view table.
     * 
     * @author njpatel
     */
    private class SystemActionTable extends BaseTreeControl {

        private AddPrivilegeAction addPrivilegeAction;

        private ResetToDefaultAction resetAction;

        public SystemActionTable(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        @Override
        protected void addColumns(ColumnViewer viewer) {
            new NameColumn(getEditingDomain(), viewer);
            new ValueColumn(getEditingDomain(), viewer);

            setColumnProportions(new float[] { 0.5f, 0.5f });
        }

        @Override
        protected IContentProvider getViewerContentProvider() {
            return new ActionContentProvider();
        }

        @Override
        protected void createActions(ColumnViewer viewer) {
            addPrivilegeAction = createAddPrivilegeAction(viewer);
            if (addPrivilegeAction != null) {
                viewer.addSelectionChangedListener(addPrivilegeAction);
                registerActionAccelerator(addPrivilegeAction);
            }

            super.createActions(viewer);

            resetAction = new ResetToDefaultAction(viewer);
            resetAction.setEnabled(false);
            viewer.addSelectionChangedListener(resetAction);
            registerActionAccelerator(resetAction);
        }

        @Override
        protected void fillViewerButtonsBar(IContributionManager manager,
                ColumnViewer viever) {
            if (addPrivilegeAction != null) {
                manager.add(addPrivilegeAction);
            }
            super.fillViewerButtonsBar(manager, viever);

            if (resetAction != null) {
                manager.add(resetAction);
            }
        }

        @Override
        protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
            return new DeleteAction(viewer);
        }

        private AddPrivilegeAction createAddPrivilegeAction(ColumnViewer viewer) {
            ImageDescriptor imgDesc = null;
            Object imgObj =
                    OMModelImages.getImageObject(OMModelImages.IMAGE_PRIVILEGE);
            if (imgObj != null) {
                imgDesc =
                        ExtendedImageRegistry.getInstance()
                                .getImageDescriptor(imgObj);
            }

            // In case we failed to get the privilege icon then get default add
            // icon
            if (imgDesc == null) {
                imgDesc =
                        XpdResourcesUIActivator
                                .getDefault()
                                .getImageRegistry()
                                .getDescriptor(XpdResourcesUIConstants.IMAGE_ADD);
            }

            AddPrivilegeAction action =
                    new AddPrivilegeAction(
                            viewer,
                            Messages.SystemActionTabSection_addPrivileges_action,
                            imgDesc);
            action.setEnabled(false);

            return action;
        }

        @Override
        protected Set<EStructuralFeature> getMovableFeatures() {
            Set<EStructuralFeature> features = super.getMovableFeatures();
            features.add(OMPackage.Literals.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS);
            return features;
        }
    }

    /**
     * Name column of the system action tree view table.
     * 
     * @author njpatel
     */
    private class NameColumn extends AbstractColumn {

        public NameColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.LEFT,
                    Messages.SystemActionTabSection_nameColumn_title, 240);
            setShowImage(true);
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            // Not editable
            return null;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            return null;
        }

        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof ISystemAction) {
                text = ((ISystemAction) element).getName();
            } else if (element instanceof ISystemActionComponent) {
                text = ((ISystemActionComponent) element).getName();
            } else if (element instanceof PrivilegeAssociation) {
                text =
                        getModelLabelProvider()
                                .getText(((PrivilegeAssociation) element).getPrivilege());
            }

            return text != null ? text : ""; //$NON-NLS-1$
        }

        @Override
        protected Image getImage(Object element) {
            if (element instanceof PrivilegeAssociation) {
                element = ((PrivilegeAssociation) element).getPrivilege();

            }

            if (!useComponentFolders) {
                return super.getImage(element);
            }

            else if (element instanceof ISystemAction) {
                EObject input = getInput();
                if (input instanceof Authorizable) {
                    /*
                     * If modified action (i.e. a system action with privileges
                     * defined) then show the 'modified' icon.
                     */
                    SystemAction systemAction =
                            getSystemAction((Authorizable) input,
                                    (ISystemAction) element);
                    if (systemAction != null
                            && !systemAction.getPrivilegeAssociations()
                                    .isEmpty()) {

                        return OMResourcesUIActivator
                                .getImage(OMResourcesUIActivator.ICON_SYSTEM_ACTION_CHANGED);
                    } else {
                        return OMResourcesUIActivator
                                .getImage(OMResourcesUIActivator.ICON_SYSTEM_ACTION);
                    }
                }

            } else if (element instanceof ISystemActionComponent) {
                /*
                 * If modified action (i.e. a system action with privileges
                 * defined) then show the 'modified' icon.
                 */
                EObject input = getInput();
                if (input instanceof Authorizable) {
                    for (ISystemAction action : ((ISystemActionComponent) element)
                            .getActions()) {

                        SystemAction systemAction =
                                getSystemAction((Authorizable) input, action);
                        if (systemAction != null
                                && !systemAction.getPrivilegeAssociations()
                                        .isEmpty()) {
                            return OMResourcesUIActivator
                                    .getImage(OMResourcesUIActivator.ICON_SYSTEM_ACTION_COMPONENT_CHANGED);
                        }
                    }
                }

                return OMResourcesUIActivator
                        .getImage(OMResourcesUIActivator.ICON_SYSTEM_ACTION_COMPONENT);

            }

            return super.getImage(element);
        }

        @Override
        protected Object getValueForEditor(Object element) {
            // Not editable
            return null;
        }
    }

    /**
     * Content provider for the system action tree view table.
     * 
     * @author njpatel
     */
    private class ActionContentProvider implements ITreeContentProvider {

        @Override
        public Object[] getChildren(Object parentElement) {
            EObject input = getInput();

            if (input instanceof Authorizable) {

                if (parentElement instanceof ISystemAction) {

                    SystemAction action =
                            getSystemAction((Authorizable) input,
                                    (ISystemAction) parentElement);
                    if (action != null) {

                        EList<PrivilegeAssociation> assocs =
                                action.getPrivilegeAssociations();
                        if (assocs != null) {
                            return assocs.toArray();
                        }
                    }

                } else if (parentElement instanceof ISystemActionComponent) {

                    Collection<ISystemAction> actionsForSysActionComp =
                            actionManager
                                    .getActionsForComponent((ISystemActionComponent) parentElement,
                                            input);
                    Collection<ISystemAction> actionsForComponent =
                            filterGlobalDataActions(actionsForSysActionComp);
                    return actionsForComponent.toArray();
                }
            }

            return new Object[0];
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof PrivilegeAssociation) {
                EObject container =
                        ((PrivilegeAssociation) element).eContainer();
                if (container instanceof SystemAction) {
                    return getSystemActionDefinition((SystemAction) container);
                }

            } else if (element instanceof SystemAction) {
                ISystemAction systemActionDefinition =
                        getSystemActionDefinition((SystemAction) element);
                if (systemActionDefinition != null) {
                    return systemActionDefinition.getComponent();
                }
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof ISystemAction
                    || element instanceof ISystemActionComponent) {
                return getChildren(element).length > 0;
            }
            return false;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Collection<?>) {
                return ((Collection<?>) inputElement).toArray();
            }
            return new Object[0];
        }

        @Override
        public void dispose() {
            // Nothing to dispose
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // Nothing to do
        }
    }

    private class AddPrivilegeAction extends ViewerAction {

        private ISystemAction selectedAction;

        public AddPrivilegeAction(StructuredViewer viewer, String text,
                ImageDescriptor imgDesc) {
            super(viewer, text, imgDesc);
            setToolTipText(Messages.SystemActionTabSection_addPrivilege_action_tooltip);
            setAccelerator(SWT.CTRL | 'p');
        }

        @Override
        public void selectionChanged(IStructuredSelection selection) {
            selectedAction = null;
            if (selection != null && selection.size() == 1
                    && selection.getFirstElement() instanceof ISystemAction) {
                selectedAction = (ISystemAction) selection.getFirstElement();
            }
            setEnabled(selectedAction != null);
        }

        @Override
        public void run() {

            EObject input = getInput();
            if (input instanceof Authorizable && selectedAction != null) {
                Authorizable authElem = (Authorizable) input;
                SystemAction systemAction =
                        getSystemAction(authElem, selectedAction);
                List<PrivilegeAssociation> existingAssociations =
                        new ArrayList<PrivilegeAssociation>();
                List<Privilege> linkedPrivileges = new ArrayList<Privilege>();

                /*
                 * If there already is a system action then get all privilege
                 * associations
                 */
                if (systemAction != null) {

                    existingAssociations.addAll(systemAction
                            .getPrivilegeAssociations());

                    /* Build a list of privileges to pre-select in the picker */
                    for (PrivilegeAssociation assoc : existingAssociations) {

                        linkedPrivileges.add(assoc.getPrivilege());
                    }
                }

                Object[] items =
                        PickerService
                                .getInstance()
                                .openMultiPickerDialog(getSite().getShell(),
                                        new PickerTypeQuery[] { new OMTypeQuery(
                                                OMTypeQuery.TYPE_ID_PRIVILEGE) },
                                        null,
                                        null,
                                        null,
                                        new IFilter[] { new SameResourceFilter(
                                                authElem) },
                                        linkedPrivileges.toArray());
                Command cmd = null;
                EditingDomain ed = getEditingDomain();

                if (null != items) {

                    /*
                     * system action does not already exist so add it
                     */
                    if (null == systemAction) {

                        if (items.length > 0) {

                            /* Add command to create a new system action */
                            systemAction =
                                    OMFactory.eINSTANCE.createSystemAction();
                            systemAction.setActionId(selectedAction.getId());
                            systemAction.setComponent(selectedAction
                                    .getComponent().getId());
                            EList<PrivilegeAssociation> associations =
                                    systemAction.getPrivilegeAssociations();
                            for (Object item : items) {

                                if (item instanceof Privilege) {

                                    PrivilegeAssociation assoc =
                                            OMFactory.eINSTANCE
                                                    .createPrivilegeAssociation();
                                    assoc.setPrivilege((Privilege) item);
                                    associations.add(assoc);
                                }
                            }
                            cmd =
                                    AddCommand
                                            .create(ed,
                                                    authElem,
                                                    OMPackage.eINSTANCE
                                                            .getAuthorizable_SystemActions(),
                                                    systemAction);
                        }

                    } else { /* system action already added so modifying it */

                        if (items.length == 0) {

                            /*
                             * All selected privileges in the picker have been
                             * removed so remove the system action
                             */
                            cmd =
                                    RemoveCommand
                                            .create(ed,
                                                    authElem,
                                                    OMPackage.eINSTANCE
                                                            .getAuthorizable_SystemActions(),
                                                    systemAction);
                        } else {

                            List<Privilege> toAdd = new ArrayList<Privilege>();
                            for (Object item : items) {

                                if (item instanceof Privilege) {

                                    if (linkedPrivileges.contains(item)) {

                                        linkedPrivileges.remove(item);
                                    } else {

                                        toAdd.add((Privilege) item);
                                    }
                                }
                            }

                            CompoundCommand ccmd = new CompoundCommand();

                            if (!linkedPrivileges.isEmpty()) {

                                /* Remove this associations */
                                List<PrivilegeAssociation> assocsToRemove =
                                        new ArrayList<PrivilegeAssociation>();
                                for (Privilege privilege : linkedPrivileges) {

                                    PrivilegeAssociation assoc =
                                            getAssociation(existingAssociations,
                                                    privilege);
                                    if (assoc != null) {
                                        assocsToRemove.add(assoc);
                                    }
                                }

                                if (!assocsToRemove.isEmpty()) {

                                    ccmd.append(RemoveCommand
                                            .create(ed,
                                                    systemAction,
                                                    OMPackage.eINSTANCE
                                                            .getAssociableWithPrivileges_PrivilegeAssociations(),
                                                    assocsToRemove));
                                }
                            }

                            if (!toAdd.isEmpty()) {

                                List<PrivilegeAssociation> assocsToAdd =
                                        new ArrayList<PrivilegeAssociation>();
                                for (Privilege privilege : toAdd) {

                                    PrivilegeAssociation assoc =
                                            OMFactory.eINSTANCE
                                                    .createPrivilegeAssociation();
                                    assoc.setPrivilege(privilege);
                                    assocsToAdd.add(assoc);
                                }
                                ccmd.append(AddCommand
                                        .create(ed,
                                                systemAction,
                                                OMPackage.eINSTANCE
                                                        .getAssociableWithPrivileges_PrivilegeAssociations(),
                                                assocsToAdd));
                            }

                            cmd = ccmd;
                        }
                    }
                }

                if (cmd != null) {

                    ed.getCommandStack().execute(cmd);
                    viewer.expandToLevel(selectedAction, TreeViewer.ALL_LEVELS);
                }
            }
        }

        /**
         * Get the {@link PrivilegeAssociation} from the list that is associated
         * with the given privilege.
         * 
         * @param assocs
         * @param privilege
         * @return
         */
        private PrivilegeAssociation getAssociation(
                List<PrivilegeAssociation> assocs, Privilege privilege) {
            for (PrivilegeAssociation assoc : assocs) {
                if (assoc.getPrivilege() == privilege) {
                    return assoc;
                }
            }
            return null;
        }
    }

    /**
     * Delete privilege assocation from the system actions.
     * 
     * @author njpatel
     */
    private class DeleteAction extends ViewerDeleteAction {

        public DeleteAction(StructuredViewer viewer) {
            super(viewer);
        }

        @Override
        protected boolean canDelete(IStructuredSelection selection) {
            if (selection != null) {
                if (selection.size() > 1) {
                    // All items in the list should be privilege associations
                    // with the same parent
                    EObject parent = null;
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof PrivilegeAssociation) {
                            if (parent == null) {
                                parent =
                                        ((PrivilegeAssociation) next)
                                                .eContainer();
                            } else if (parent != ((PrivilegeAssociation) next)
                                    .eContainer()) {
                                return false;
                            }
                        } else {
                            // Wrong object in selection
                            return false;
                        }
                    }

                } else {
                    // Only 1 item selected and it should be Privilege assoc
                    if (!(selection.getFirstElement() instanceof PrivilegeAssociation)) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public void run() {
            if (!selection.isEmpty()) {
                int idx = -1;
                SystemAction action = null;
                EditingDomain ed = getEditingDomain();
                EObject container =
                        ((EObject) selection.getFirstElement()).eContainer();
                if (container instanceof SystemAction) {
                    action = (SystemAction) container;
                    idx =
                            action.getPrivilegeAssociations()
                                    .indexOf(selection.getFirstElement());

                    // Remove the associations
                    ed.getCommandStack()
                            .execute(RemoveCommand.create(ed,
                                    container,
                                    OMPackage.eINSTANCE
                                            .getAssociableWithPrivileges_PrivilegeAssociations(),
                                    selection.toList()));

                    if (idx >= 0 && action != null) {
                        EList<PrivilegeAssociation> associations =
                                action.getPrivilegeAssociations();
                        if (!associations.isEmpty()) {
                            // If last item in list was deleted then select item
                            // before that
                            if (idx >= associations.size()) {
                                --idx;
                            }
                            PrivilegeAssociation assoc = associations.get(idx);
                            viewer.setSelection(new StructuredSelection(assoc),
                                    true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Action to reset the values of the privilege qualifiers to their default
     * values.
     * 
     * @author njpatel
     * 
     */
    private class ResetToDefaultAction extends ViewerAction {

        private List<PrivilegeAssociation> selection;

        public ResetToDefaultAction(StructuredViewer viewer) {
            super(viewer,
                    Messages.SystemActionTabSection_setDefaultValue_action,
                    PlatformUI.getWorkbench().getSharedImages()
                            .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
            setToolTipText(Messages.SystemActionTabSection_setDefaultValue_action_tooltip);
            setAccelerator(SWT.CTRL | 'r');
        }

        @Override
        public void selectionChanged(IStructuredSelection selection) {
            this.selection = null;
            setEnabled(false);

            if (selection != null && !selection.isEmpty()) {
                List<PrivilegeAssociation> assocs =
                        new ArrayList<PrivilegeAssociation>();
                for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                    Object next = iter.next();
                    if (next instanceof PrivilegeAssociation) {
                        assocs.add((PrivilegeAssociation) next);
                    } else {
                        // Invalid selection
                        return;
                    }
                }
                this.selection = assocs;

                setEnabled(true);
            }
        }

        @Override
        public void run() {
            if (selection != null && !selection.isEmpty()) {
                CompoundCommand cmd = new CompoundCommand();
                EditingDomain ed = getEditingDomain();
                for (PrivilegeAssociation assoc : selection) {
                    cmd.append(RemoveCommand.create(ed,
                            assoc.getQualifierValue()));
                }

                ed.getCommandStack().execute(cmd);
            }
        }
    }

    private class ValueColumn extends
            QualifiedValueColumn<PrivilegeAssociation> {

        private Color disableColor;

        public ValueColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer,
                    Messages.SystemActionTabSection_valueColumn_title, 200);
        }

        @Override
        protected Color getForeground(Object element) {
            if (element instanceof ISystemAction) {
                if (disableColor == null) {
                    Color foreground =
                            getColumn().getViewer().getControl()
                                    .getForeground();
                    disableColor =
                            new Color(foreground.getDevice(),
                                    ColorUtil.blend(foreground.getRGB(),
                                            ColorConstants.white.getRGB(),
                                            40));
                }
                return disableColor;
            }
            return super.getForeground(element);
        }

        @Override
        protected void dispose() {
            if (disableColor != null) {
                disableColor.dispose();
                disableColor = null;
            }

            super.dispose();
        }

        @Override
        protected Attribute getQualifier(PrivilegeAssociation element) {
            if (element != null && element.getPrivilege() != null) {
                return element.getPrivilege().getQualifierAttribute();
            }
            return null;
        }

        @Override
        protected String getText(Object element) {
            if (element instanceof ISystemAction) {
                EObject input = getInput();
                if (input instanceof Authorizable) {
                    SystemAction action =
                            getSystemAction((Authorizable) input,
                                    (ISystemAction) element);
                    if (action == null
                            || action.getPrivilegeAssociations().isEmpty()) {
                        String value =
                                ((ISystemAction) element).getDefaultValue();
                        if (value != null
                                && (value.equalsIgnoreCase("yes") || Boolean //$NON-NLS-1$
                                        .parseBoolean(value))) {
                            return Messages.SystemActionTabSection_availableToAllUsers_shortdesc;
                        }
                    }
                }
            } else if (element instanceof PrivilegeAssociation) {
                return super.getText(element);
            }
            return ""; //$NON-NLS-1$
        }

    }
}
