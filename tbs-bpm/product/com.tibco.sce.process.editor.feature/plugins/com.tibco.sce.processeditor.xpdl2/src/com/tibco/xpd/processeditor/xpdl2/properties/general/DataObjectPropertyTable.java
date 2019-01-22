/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2DataObjectAdapter;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.ui.properties.table.MultilineTextCellEditor;
import com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes;
import com.tibco.xpd.xpdExtension.XpdExtProperty;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.DataObject;

/**
 * DataObjectPropertyTable
 * 
 * 
 * @author bharge
 * @since 3.3 (14 Apr 2010)
 */
public class DataObjectPropertyTable extends BaseTableControl {

    /**
     * @param parent
     * @param toolkit
     * @param viewerInput
     * @param createContent
     */
    protected DataObjectPropertyTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
    }

    private EditingDomain editingDomain;

    private IContentProvider contentProvider;

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getMovableFeatures()
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        Set<EStructuralFeature> features = super.getMovableFeatures();
        features.add(XpdExtensionPackage.eINSTANCE
                .getXpdExtDataObjectAttributes_Properties());
        return features;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.DataObjectPropertiesHandler_AddNewProperty_label,
                Messages.DataObjectPropertiesHandler_AddNewProperty_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                String firstCellVal = getNewRowFirstCellVal();
                Command cmd =
                        getAddPropertyCommand(editingDomain, firstCellVal, ""); //$NON-NLS-1$
                if (cmd.canExecute()) {
                    editingDomain.getCommandStack().execute(cmd);

                    // Find the property we added,
                    Object newProperty = null;
                    Collection affObj = cmd.getAffectedObjects();
                    for (Iterator iterator = affObj.iterator(); iterator
                            .hasNext();) {
                        Object o = iterator.next();

                        if (o instanceof XpdExtProperty) {
                            newProperty = o;
                        }
                    }

                    return newProperty;

                }
                return null;
            }

            protected String getNewRowFirstCellVal() {
                return Messages.DataObjectPropertiesHandler_DefaultNewPropertyName_label;
            }

            /**
             * Get command that adds the given property.
             * 
             * @param editingDomain
             * @param name
             * @param value
             * 
             * @return
             */
            private Command getAddPropertyCommand(EditingDomain editingDomain,
                    String name, String value) {

                CompoundCommand cmd = new CompoundCommand();
                cmd
                        .setLabel(Messages.DataObjectPropertiesHandler_AddNewProperty_tooltip);

                XpdExtDataObjectAttributes extAttr =
                        Xpdl2DataObjectAdapter
                                .getOrCreateXpdExtDataObjectAttributes(editingDomain,
                                        cmd,
                                        getArtifact());

                XpdExtProperty prop =
                        XpdExtensionFactory.eINSTANCE.createXpdExtProperty();
                prop.setName(name);
                prop.setValue(value);

                cmd.append(AddCommand.create(editingDomain,
                        extAttr,
                        XpdExtensionPackage.eINSTANCE
                                .getXpdExtDataObjectAttributes_Properties(),
                        prop));

                return cmd;
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.DataObjectPropertiesHandler_DeleteProperty_label,
                Messages.DataObjectPropertiesHandler_DeleteProperty_tooltip) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                if (selection.size() > 0) {
                    CompoundCommand cmd = new CompoundCommand();
                    cmd
                            .setLabel(Messages.DataObjectPropertiesHandler_DeleteProperty_tooltip);
                    for (Iterator iter = selection.iterator(); iter.hasNext();) {
                        Object obj = iter.next();

                        cmd
                                .append(getDeletePropertyCommand(editingDomain,
                                        obj));

                    }

                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);

                    }
                }
            }

            /**
             * Return command to delete given data object property
             * 
             * @param editingDomain
             * @param property
             * @return
             */
            private Command getDeletePropertyCommand(
                    EditingDomain editingDomain, Object property) {

                XpdExtDataObjectAttributes extData =
                        Xpdl2DataObjectAdapter
                                .getXpdExtDataObjectAttributes(getDataObject());
                if (extData != null) {
                    if (extData.getProperties().contains(property)) {
                        CompoundCommand cmd = new CompoundCommand();
                        cmd
                                .setLabel(Messages.DataObjectPropertiesHandler_DeleteProperty_tooltip);

                        cmd.append(RemoveCommand.create(editingDomain,
                                ((XpdExtProperty) property).eContainer(),
                                ((XpdExtProperty) property)
                                        .eContainmentFeature(),
                                property));
                        return cmd;
                    }
                }

                return UnexecutableCommand.INSTANCE;
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (null == contentProvider) {
            contentProvider = new IStructuredContentProvider() {

                public Object[] getElements(Object inputElement) {
                    Object[] ret = new Object[0];
                    if (inputElement instanceof Artifact) {
                        Collection<Object> props = getDataObjectProperties();
                        ret = props.toArray();
                    }

                    return ret;
                }

                public void dispose() {

                }

                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {

                }

            };
        }
        return contentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new PropertyColumn(editingDomain, viewer);
        new ValueColumn(editingDomain, viewer);
        new EditColumn(editingDomain, viewer);

        setColumnProportions(new float[] { 0.3f, 0.55f, 0.15f });
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    /**
     * Get the xpdl2 Artifact element for the current input
     * 
     * @return Artifact input or null on error.
     */
    public Artifact getArtifact() {
        Object o = getInput();
        if (o instanceof Artifact) {
            return (Artifact) o;
        }
        return null;
    }

    /**
     * Get the DataObject element from the Artifact input object.
     * 
     * @return DataObject element from Artifact input or null on error.
     */
    public DataObject getDataObject() {
        Artifact art = getArtifact();
        if (art != null) {
            return art.getDataObject();
        }
        return null;
    }

    private Collection<Object> getDataObjectProperties() {
        XpdExtDataObjectAttributes extAttr =
                Xpdl2DataObjectAdapter
                        .getXpdExtDataObjectAttributes(getDataObject());

        if (extAttr != null) {
            Collection<?> props = extAttr.getProperties();

            if (props != null && props.size() > 0) {
                // Take a copy of the list because we doin't want caller messing
                // with the original.
                List<Object> retProps = new ArrayList<Object>(props.size());

                retProps.addAll(props);

                return retProps;
            }
        }

        return Collections.emptyList();
    }

    /**
     * PropertyColumn
     * 
     * 
     * @author bharge
     * @since 3.3 (14 Apr 2010)
     */
    private class PropertyColumn extends AbstractColumn {
        private final TextCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public PropertyColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer,
                    Messages.DataObjectPropertySection_Property_label, 170);
            editor = new TextCellEditor((Composite) viewer.getControl());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof XpdExtProperty) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof XpdExtProperty) {
                XpdExtProperty xpdExtProperty = (XpdExtProperty) element;
                CompoundCommand cmd = new CompoundCommand();

                cmd
                        .setLabel(Messages.DataObjectPropertiesHandler_CHangeProeprty_menu);
                cmd.append(SetCommand.create(editingDomain,
                        xpdExtProperty,
                        XpdExtensionPackage.eINSTANCE.getXpdExtProperty_Name(),
                        value));

                return cmd;

            }
            return UnexecutableCommand.INSTANCE;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof XpdExtProperty) {
                XpdExtProperty property = (XpdExtProperty) element;
                text = property.getName();
                if (null == text) {
                    text = ""; //$NON-NLS-1$
                }
            }
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    /**
     * ValueColumn
     * 
     * 
     * @author bharge
     * @since 3.3 (14 Apr 2010)
     */
    private class ValueColumn extends AbstractColumn {
        private final MultilineTextCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public ValueColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer,
                    Messages.DataObjectPropertySection_Value_label, 320);
            editor =
                    new MultilineTextCellEditor(
                            (Composite) viewer.getControl(), SWT.MULTI
                                    | SWT.WRAP | SWT.V_SCROLL);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof XpdExtProperty) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof XpdExtProperty) {
                XpdExtProperty xpdExtProperty = (XpdExtProperty) element;
                CompoundCommand cmd = new CompoundCommand();

                cmd
                        .setLabel(Messages.DataObjectPropertiesHandler_CHangeProeprty_menu);
                cmd.append(SetCommand
                        .create(editingDomain,
                                xpdExtProperty,
                                XpdExtensionPackage.eINSTANCE
                                        .getXpdExtProperty_Value(),
                                value));

                return cmd;
            }
            return UnexecutableCommand.INSTANCE;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof XpdExtProperty) {
                XpdExtProperty property = (XpdExtProperty) element;
                String val = property.getValue();

                int idx = val.indexOf('\n');
                if (idx != -1) {
                    // When showing label for multi-line value then append
                    // chevron to indicate that there is more.
                    text = val.substring(0, idx - 1) + " >>"; //$NON-NLS-1$
                } else {
                    text = val;
                }

                if (null == text) {
                    text = ""; //$NON-NLS-1$
                }
            }
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    /**
     * EditColumn
     * 
     * 
     * @author bharge
     * @since 3.3 (14 Apr 2010)
     */
    private class EditColumn extends AbstractColumn {
        private EditPropertyValuePickerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public EditColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer,
                    Messages.DataObjectPropertySection_Edit_label, 60);
            editor =
                    new EditPropertyValuePickerCellEditor((Composite) viewer
                            .getControl());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof XpdExtProperty) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof XpdExtProperty) {
                XpdExtProperty xpdExtProperty = (XpdExtProperty) element;
                CompoundCommand cmd = new CompoundCommand();

                cmd
                        .setLabel(Messages.DataObjectPropertiesHandler_CHangeProeprty_menu);
                cmd.append(SetCommand.create(editingDomain,
                        xpdExtProperty,
                        XpdExtensionPackage.eINSTANCE.getXpdExtProperty_Name(),
                        xpdExtProperty.getName()));
                cmd.append(SetCommand
                        .create(editingDomain,
                                xpdExtProperty,
                                XpdExtensionPackage.eINSTANCE
                                        .getXpdExtProperty_Value(),
                                value));

                return cmd;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return null;
        }

        private class EditPropertyValuePickerCellEditor extends
                DialogCellEditor {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.
             * eclipse.swt.widgets.Control)
             */
            /**
             * @param parent
             */
            public EditPropertyValuePickerCellEditor(Composite parent) {
                super(parent);
            }

            @Override
            protected Object openDialogBox(Control cellEditorWindow) {
                Artifact art = getArtifact();
                if (art != null) {
                    StructuredSelection selection =
                            (StructuredSelection) getTableViewer()
                                    .getSelection();
                    Object firstElement = selection.getFirstElement();
                    if (firstElement instanceof XpdExtProperty) {
                        XpdExtProperty property = (XpdExtProperty) firstElement;
                        String name =
                                property.getName() != null ? property.getName()
                                        : ""; //$NON-NLS-1$

                        String value =
                                property.getValue() != null ? property
                                        .getValue() : ""; //$NON-NLS-1$

                        String title =
                                Messages.DataObjectPropertiesHandler_PropertyValueDialog_title;

                        DataObjectPropertyValueDialog dlg =
                                new DataObjectPropertyValueDialog(
                                        cellEditorWindow.getShell(), title
                                                + name, value);
                        if (dlg.open() == DataObjectPropertyValueDialog.OK) {
                            return dlg.getPropertyValue();
                        }
                    }
                }
                return null;
            }
        }

    }

}
