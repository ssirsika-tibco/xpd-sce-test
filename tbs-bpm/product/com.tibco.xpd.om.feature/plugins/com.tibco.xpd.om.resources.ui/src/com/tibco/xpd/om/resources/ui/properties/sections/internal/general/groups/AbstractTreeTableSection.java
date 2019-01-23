/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups;

import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.om.core.om.provider.TransientItemProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTreeControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Abstract properties view section that contains a tree table viewer.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractTreeTableSection extends
        AbstractTransactionalSection {

    private String heading;

    private Font tableHeaderFont;

    private BaseTreeControl table;

    private EObject currentInput;

    public AbstractTreeTableSection() {
        this(null);
    }

    /**
     * Abstract section for all group general tabs.
     * 
     * @param heading
     * @param eAttr
     *            model attribute to add new element to
     */
    public AbstractTreeTableSection(String heading) {
        setHeading(heading);
    }

    /**
     * Set the heading of this section.
     * 
     * @param heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * Get the tree control.
     * 
     * @return
     */
    protected final BaseTreeControl getTable() {
        return table;
    }

    @Override
    protected EObject resollveInput(Object object) {

        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        } else if (object instanceof TransientItemProvider) {
            Notifier target = ((TransientItemProvider) object).getTarget();
            if (target instanceof EObject) {
                return (EObject) target;
            }
        }

        return super.resollveInput(object);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);

        if (heading != null) {
            root.setLayout(new GridLayout());
            Label label = toolkit.createLabel(root, heading);
            GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
            data.horizontalIndent = 5;
            label.setLayoutData(data);
            label.setForeground(ColorConstants.darkGray);
            tableHeaderFont = new Font(root.getDisplay(), "Arial", 10, //$NON-NLS-1$
                    SWT.BOLD);
            label.setFont(tableHeaderFont);

            Control area = createClientArea(root, toolkit);
            if (area != null) {
                area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            }
        } else {
            FillLayout layout = new FillLayout();
            layout.marginHeight = 5;
            layout.marginWidth = 5;
            root.setLayout(layout);
            createClientArea(root, toolkit);
        }

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {

        if (tableHeaderFont != null) {
            tableHeaderFont.dispose();
            tableHeaderFont = null;
        }

        super.dispose();
    }

    @Override
    protected Command doGetCommand(Object obj) {
        /* Not used as changes handled by the table */
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (table != null && !table.isDisposed()) {
            table.getViewer().cancelEditing();
            if (currentInput != input) {
                currentInput = input;
                table.getViewer().setInput(input);
            } else {
                table.getViewer().refresh();
            }
        }
    }

    /**
     * Add the columns to the tree table
     * 
     * @see AbstractColumn
     * @param viewer
     *            tree table viewer
     * @return proportions of the columns (width) in the table,
     *         <code>null</code> to set the widths as specified in the
     *         individual columns.
     */
    protected abstract float[] addColumns(ColumnViewer viewer);

    /**
     * Get the features in the table that can be deleted (rows).
     * 
     * @return
     */
    protected abstract Set<EStructuralFeature> getDeletableFeatures();

    /**
     * Add "Add" actions to the table action bar.
     * 
     * @param viewer
     * @return
     */
    protected abstract ViewerAddAction[] createAddActions(ColumnViewer viewer);

    /**
     * Get the content provider for the tree table viewer. Default
     * implementation will return <code>null</code> and so the Transactional
     * Adapter Factory content provider will be used. Subclasses may override to
     * provide custom content providers.
     * 
     * @return
     */
    protected ITreeContentProvider getContentProvider() {
        return null;
    }

    /**
     * Create the main area of this section. This will include the group table
     * for this section.
     * 
     * @param parent
     *            parent container
     * @param toolkit
     *            form toolkit
     * @return created control
     */
    protected Control createClientArea(Composite parent, XpdToolkit toolkit) {
        // Create the tree table viewer
        table = new TreeViewerControl(parent, toolkit);
        TreeViewer viewer = table.getTreeViewer();
        viewer.getTree().setHeaderVisible(true);
        viewer.getTree().setLinesVisible(true);
        return table;
    }

    /**
     * Get the item provider of the given EObject.
     * 
     * @param eo
     * @return
     */
    protected IEditingDomainItemProvider getItemProvider(EObject eo) {
        return (IEditingDomainItemProvider) XpdResourcesPlugin.getDefault()
                .getAdapterFactory()
                .adapt(eo, IEditingDomainItemProvider.class);
    }

    /**
     * @author njpatel
     * 
     */
    private class TreeViewerControl extends BaseTreeControl {
        private ViewerAddAction[] addActions;

        public TreeViewerControl(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        @Override
        protected void addColumns(ColumnViewer viewer) {
            float[] propotions =
                    AbstractTreeTableSection.this.addColumns(viewer);
            if (propotions != null) {
                setColumnProportions(propotions);
            }
        }

        @Override
        protected void createActions(ColumnViewer viewer) {
            addActions = createAddActions(viewer);
            if (addActions != null) {
                for (ViewerAddAction addAction : addActions) {
                    viewer.addSelectionChangedListener(addAction);
                    registerActionAccelerator(addAction);
                }
            }
            super.createActions(viewer);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
         * fillViewerButtonsBar(org.eclipse.jface.action.IContributionManager,
         * org.eclipse.jface.viewers.ColumnViewer)
         */
        @Override
        protected void fillViewerButtonsBar(IContributionManager manager,
                ColumnViewer viewer) {
            if (addActions != null) {
                for (ViewerAddAction addAction : addActions) {
                    manager.add(addAction);
                }
            }
            super.fillViewerButtonsBar(manager, viewer);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
         * getViewerContentProvider()
         */
        @Override
        protected IContentProvider getViewerContentProvider() {
            ITreeContentProvider provider = getContentProvider();
            return provider != null ? provider : super
                    .getViewerContentProvider();
        }

        /**
         * @param element
         * @return
         */
        public Object[] getChildren(Object element) {
            return ((IStructuredContentProvider) getViewerContentProvider())
                    .getElements(element);
        }

        @Override
        protected Set<EStructuralFeature> getDeletableFeatures() {
            Set<EStructuralFeature> features =
                    AbstractTreeTableSection.this.getDeletableFeatures();

            return features != null ? features : super.getDeletableFeatures();
        }
    }

}
