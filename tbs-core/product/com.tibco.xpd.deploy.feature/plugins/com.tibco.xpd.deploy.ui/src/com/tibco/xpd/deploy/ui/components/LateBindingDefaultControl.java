/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.components;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.model.extension.ResourceBinding;
import com.tibco.xpd.deploy.model.extension.SharedResource;
import com.tibco.xpd.deploy.ui.ConfigToolkit;

/**
 * Control for managing server bindings.
 * <p>
 * <i>Created: 4 Dec 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz, Gary Lewis
 */
abstract public class LateBindingDefaultControl extends LateBindingControl {

    private final Collection<ResourceBinding> bindings;

    /** */
    private static final String MODULE_COL_PROPERTY = "module"; //$NON-NLS-1$

    private static final String LOCAL_RESOURCE_COL_PROPERTY = "localResource"; //$NON-NLS-1$

    /** */
    private static final String SHARED_RESOURCE_COL_PROPERTY = "sharedResource"; //$NON-NLS-1$

    /** */
    private static final String RESOURCE_TYPE_COL_PROPERTY = "resourceType"; //$NON-NLS-1$

    /**
     * 
     * <p>
     * <i>Created: 12 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    private final class ComboBoxViewerCellEditorExtension extends
            ComboBoxViewerCellEditor {
        /**
         * @param parent
         * @param style
         */
        protected ComboBoxViewerCellEditorExtension(Composite parent, int style) {
            super(parent, style);
        }

        /** {@inheritDoc} */
        @Override
        public void focusLost() {
            super.focusLost();
        }
    }

    private class EmptySharedResourceElement extends LabelProvider implements
            IItemLabelProvider {

        /** {@inheritDoc} */
        @Override
        public String getText(Object object) {
            return "Unspecified";
        }
    }

    private final EmptySharedResourceElement emptySharedResourceElement =
            new EmptySharedResourceElement();

    /**
     * 
     * <p>
     * <i>Created: 3 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    private final class SharedResourceContentProvider extends
            ArrayContentProvider {
        /** {@inheritDoc} */
        @Override
        public Object[] getElements(Object inputElement) {
            Object[] elements = super.getElements(inputElement);
            Object[] newElements = new Object[elements.length + 1];
            newElements[0] = emptySharedResourceElement;
            System.arraycopy(elements, 0, newElements, 1, elements.length);
            return newElements;
        }
    }

    private final class SharedResourceLabelProvider extends LabelProvider {
        /** {@inheritDoc} */
        @Override
        public String getText(Object element) {
            if (element instanceof SharedResource) {
                SharedResource sharedResource = (SharedResource) element;
                return sharedResource.getId();
            }
            return super.getText(element);
        }
    }

    /**
     * 
     * <p>
     * <i>Created: 1 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    public class BindingsContentProvider extends ArrayContentProvider {
    };

    /**
     * 
     * <p>
     * <i>Created: 1 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    private final class BindingsLabelProvider extends LabelProvider implements
            ITableLabelProvider {
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof ResourceBinding) {
                ResourceBinding resourceBinding = (ResourceBinding) element;
                switch (columnIndex) {
                case 0:
                    return new Path(resourceBinding.getLocalModuleURL()
                            .toString()).lastSegment();
                case 1:
                    return resourceBinding.getLocalName();
                case 2:
                    String sharedResourceId =
                            resourceBinding.getSharedResourceId();
                    return sharedResourceId != null ? sharedResourceId
                            : "Unspecified";
                case 3:
                    return resourceBinding.getLocalType();
                default:
                    Assert.isLegal(false, "Column index out of range.");
                }
            }
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void dispose() {
            // TODO Auto-generated method stub
            super.dispose();
        }
    }

    /**
     * Handles the cell editing / selection of the servers table.
     * 
     */
    private class ServersCellModifier implements ICellModifier {

        /** {@inheritDoc} */
        public boolean canModify(Object element, String property) {
            if (SHARED_RESOURCE_COL_PROPERTY.equals(property)
                    && element instanceof ResourceBinding) {
                return true;
            }
            return false;
        }

        /** {@inheritDoc} */
        public Object getValue(Object element, String property) {
            if (SHARED_RESOURCE_COL_PROPERTY.equals(property)) {
                if (element instanceof ResourceBinding) {
                    return ((ResourceBinding) element).getSharedResourceId();
                }
            }
            return null;
        }

        /** {@inheritDoc} */
        public void modify(Object element, String property, Object value) {
            ResourceBinding binding = null;
            if (element instanceof TableItem
                    && ((TableItem) element).getData() instanceof ResourceBinding) {
                binding = (ResourceBinding) ((TableItem) element).getData();
            }
            if (binding != null && value instanceof SharedResource) {
                binding.setSharedResourceId(((SharedResource) value).getId());
                bindingsViewer.refresh(binding);
            } else if (binding != null
                    && value instanceof EmptySharedResourceElement) {
                binding.setSharedResourceId(null);
                bindingsViewer.refresh(binding);
            }
            setControlComplete(validatePage(true));
        }
    }

    protected boolean initialised = false;

    protected final TableViewer bindingsViewer;

    protected boolean isControlComplete = false;

    protected ServerGroup contextServerGroup;

    protected final ComboBoxViewerCellEditorExtension bindingCellEditor;

    /**
     * @param parent
     * @param style
     */
    public LateBindingDefaultControl(Composite parent,
            Collection<ResourceBinding> bindings,
            Collection<SharedResource> sharedResources) {
        super(parent, SWT.NONE);
        this.bindings = new LinkedHashSet<ResourceBinding>(bindings);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        this.setLayout(gridLayout);

        Group serverGroupGroup = new Group(this, SWT.NULL);
        serverGroupGroup.setText("Bindings");
        GridData gridData2 = new GridData(GridData.FILL_BOTH);
        serverGroupGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        serverGroupGroup.setLayout(paramGroupLayout);

        final ConfigToolkit toolkit = ConfigToolkit.INSTANCE;

        Table bindingsTable = toolkit.createTable(serverGroupGroup);

        TableColumn moduleColumn = new TableColumn(bindingsTable, SWT.NONE);
        moduleColumn.setText("Module");
        moduleColumn.setWidth(150);

        TableColumn localResourceColumn =
                new TableColumn(bindingsTable, SWT.NONE);
        localResourceColumn.setText("Local Resource");
        localResourceColumn.setWidth(200);

        TableColumn sharedResourceColumn =
                new TableColumn(bindingsTable, SWT.NONE);
        sharedResourceColumn.setText("Server Resource");
        sharedResourceColumn.setWidth(200);

        TableColumn resourceTypeColumn =
                new TableColumn(bindingsTable, SWT.NONE);
        resourceTypeColumn.setText("Type");
        resourceTypeColumn.setWidth(50);

        GridDataFactory gdFactory =
                GridDataFactory.fillDefaults().grab(true, true).span(3, 1);
        gdFactory.applyTo(bindingsTable);
        bindingsViewer = new TableViewer(bindingsTable);
        bindingsViewer.getTable().setLinesVisible(true);
        bindingsViewer.getTable().setHeaderVisible(true);
        bindingsViewer.setContentProvider(new BindingsContentProvider());
        bindingsViewer.setLabelProvider(new BindingsLabelProvider());
        bindingsViewer.setInput(this.bindings);
        bindingsViewer.setColumnProperties(new String[] { MODULE_COL_PROPERTY,
                LOCAL_RESOURCE_COL_PROPERTY, SHARED_RESOURCE_COL_PROPERTY,
                RESOURCE_TYPE_COL_PROPERTY });

        bindingCellEditor =
                new ComboBoxViewerCellEditorExtension(bindingsTable, SWT.NONE);
        // SWT.READ_ONLY);
        bindingCellEditor.getViewer()
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        bindingCellEditor.focusLost();
                    }
                });

        bindingCellEditor
                .setContenProvider(new SharedResourceContentProvider());
        bindingCellEditor.setLabelProvider(new SharedResourceLabelProvider());
        bindingCellEditor.setInput(sharedResources);
        bindingsViewer.setCellModifier(new ServersCellModifier());
        bindingsViewer.setCellEditors(new CellEditor[] { null, null,
                bindingCellEditor, null });

        bindingsViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        bindingCellEditor
                                .getViewer()
                                .setFilters(new ViewerFilter[] { new ViewerFilter() {
                                    @Override
                                    public boolean select(Viewer viewer,
                                            Object parentElement, Object element) {
                                        IStructuredSelection sel =
                                                (IStructuredSelection) bindingsViewer
                                                        .getSelection();
                                        Object firstElement =
                                                sel.getFirstElement();
                                        if (firstElement instanceof ResourceBinding) {
                                            ResourceBinding binding =
                                                    (ResourceBinding) firstElement;
                                            if (binding.getLocalType() != null) {
                                                if (element instanceof SharedResource) {
                                                    SharedResource sharedResource =
                                                            (SharedResource) element;
                                                    return binding
                                                            .getLocalType()
                                                            .equals(sharedResource
                                                                    .getType());
                                                }
                                            }
                                        } else if (element instanceof EmptySharedResourceElement) {
                                            return true;
                                        }
                                        return false;
                                    };
                                } });
                    }
                });
        setControlComplete(false);
    }

    /**
     * @param validatePage
     */
    protected void setControlComplete(boolean isComplete) {
        isControlComplete = isComplete;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isControlComplete() {
        return isControlComplete;
    }

    /**
     * @return the contextServerGroup
     */
    public ServerGroup getContext() {
        return contextServerGroup;
    }

    /** {@inheritDoc} */
    @Override
    public void setMessage(String newMessage, int newType) {
        // Implement if you need validation messages.
    }

    /** {@inheritDoc} */
    @Override
    public Collection<ResourceBinding> getBindings() {
        return Collections.unmodifiableCollection(bindings);
    }

    /** {@inheritDoc} */
    @Override
    public void initializeControlDefaults() {

        initialised = true;
        setControlComplete(validatePage(false));
    }

    /** {@inheritDoc} */
    @Override
    public void commitBindings() throws Exception {
    }

    /** {@inheritDoc} */
    @Override
    public void updateBindings(Collection<ResourceBinding> resourceBindings) {
        if (bindingsViewer.getControl() != null
                && !bindingsViewer.getControl().isDisposed()) {
            // add new bindings

            for (ResourceBinding binding : resourceBindings) {
                if (!bindings.contains(binding)) {
                    bindings.add(binding);
                }
            }
            // remove bindings that don't exist in current resource bindings.
            for (Iterator<ResourceBinding> iterator = bindings.iterator(); iterator
                    .hasNext();) {
                ResourceBinding binding = iterator.next();
                if (!resourceBindings.contains(binding)) {
                    iterator.remove();
                }
            }
            bindingsViewer.refresh();
            setControlComplete(validatePage(true));
        }
    }

    protected boolean validatePage(boolean showMessages) {
        setMessage(null);
        setErrorMessage(null);
        if (!initialised)
            return false;

        // errors
        for (ResourceBinding binding : bindings) {
            if (binding.getSharedResourceId() == null) {
                if (showMessages) {
                    setErrorMessage("One of the binding does not have assigned value.");
                }
                return false;
            }
        }

        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        setWarningMessage(null);

        return true;
    }

    /**
     * @param object
     */
    protected void setMessage(String object) {
        setMessage(object, IMessageProvider.NONE);
    }

    /**
     * @param object
     */
    protected void setErrorMessage(String object) {
        setMessage(object, IMessageProvider.ERROR);
    }

    /**
     * @param object
     */
    protected void setWarningMessage(String object) {
        setMessage(object, IMessageProvider.WARNING);
    }
}
