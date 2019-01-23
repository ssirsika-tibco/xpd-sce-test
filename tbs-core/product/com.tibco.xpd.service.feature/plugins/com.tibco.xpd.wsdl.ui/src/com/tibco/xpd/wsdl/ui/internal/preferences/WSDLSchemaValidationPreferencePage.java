/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.internal.preferences;

import static com.tibco.xpd.wsdl.ui.WsdlUIPlugin.WSDL_VALIDATION_NAMESPACE_IGNORE_PREF;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Preference page for the WSDL validation. This page will allow the user to
 * enter namespace prefixes of schemas that should be ignored during validation.
 * 
 * @author njpatel
 */
public class WSDLSchemaValidationPreferencePage extends PreferencePage
        implements IWorkbenchPreferencePage {

    private static final String DELIM = " "; //$NON-NLS-1$

    private List<String> namespaces;

    private NamespaceTable table;

    public WSDLSchemaValidationPreferencePage() {
        setPreferenceStore(WsdlUIPlugin.getDefault().getPreferenceStore());
        namespaces = getNamespaces(getPreferenceStore());
    }

    @Override
    public boolean performOk() {
        try {
            getPreferenceStore()
                    .setValue(WSDL_VALIDATION_NAMESPACE_IGNORE_PREF,
                            serialize(namespaces));
            setErrorMessage(null);
        } catch (CoreException e) {
            setErrorMessage(e.getLocalizedMessage());
        }
        return true;
    }

    @Override
    protected void performDefaults() {
        namespaces =
                getNamespaces(getPreferenceStore()
                        .getDefaultString(WSDL_VALIDATION_NAMESPACE_IGNORE_PREF));
        if (table != null && !table.isDisposed()) {
            table.getTableViewer().setInput(namespaces);
        }
        super.performDefaults();
    }

    /**
     * Serialize the given list of namespaces. This will also encode the
     * namespaces.
     * 
     * @param namespaces
     * @return
     * @throws CoreException
     */
    private String serialize(List<String> namespaces) throws CoreException {
        StringBuffer buffer = new StringBuffer();

        int idx = 0;
        for (String namespace : namespaces) {
            if (idx++ > 0) {
                buffer.append(DELIM);
            }
            try {
                buffer.append(URLEncoder.encode(namespace, "UTF-8")); //$NON-NLS-1$
            } catch (UnsupportedEncodingException e) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                WsdlUIPlugin.PLUGIN_ID,
                                String
                                        .format(Messages.WSDLSchemaValidationPreferencePage_invalidNamespace_error_shortdesc,
                                                namespace)));
            } //$NON-NLS-1$
        }

        return buffer.toString();
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        Label lbl = new Label(root, SWT.WRAP);
        lbl.setText(Messages.WSDLSchemaValidationPreferencePage_longdesc);

        table = new NamespaceTable(root, new BaseXpdToolkit());
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        table.getTableViewer().setInput(namespaces);

        return root;
    }

    /**
     * Get namespaces stored in the preference store.
     * 
     * @param store
     * @return
     */
    private static List<String> getNamespaces(IPreferenceStore store) {
        return getNamespaces(store
                .getString(WSDL_VALIDATION_NAMESPACE_IGNORE_PREF));
    }

    /**
     * Get the namespaces from the given preference value.
     * 
     * @param preferenceValue
     * @return list of namespaces, empty list if none specified.
     */
    private static List<String> getNamespaces(String preferenceValue) {
        List<String> namespaces = new ArrayList<String>();
        if (preferenceValue != null && preferenceValue.length() > 0) {
            for (String value : preferenceValue.split(DELIM)) {
                if (value.length() > 0) {
                    try {
                        namespaces.add(URLDecoder.decode(value, "UTF-8")); //$NON-NLS-1$
                    } catch (UnsupportedEncodingException e) {
                        // Do nothing
                    }
                }
            }
        }
        return namespaces;
    }

    /**
     * Get the namespace prefixes of schemas to ignore during WSDL validation.
     * 
     * @return
     */
    public static List<String> getNamespaces() {
        return getNamespaces(WsdlUIPlugin.getDefault().getPreferenceStore());
    }

    @Override
    public void init(IWorkbench workbench) {

    }

    /**
     * Namespace prefix table.
     */
    private class NamespaceTable extends BaseTableControl {

        public NamespaceTable(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        @Override
        protected IContentProvider getViewerContentProvider() {
            return new ArrayContentProvider();
        }

        @Override
        protected ILabelProvider getViewerLabelProvider() {
            return new LabelProvider();
        }

        @Override
        protected void addColumns(ColumnViewer viewer) {
            TableViewerColumn column =
                    new TableViewerColumn((TableViewer) viewer, SWT.NONE);
            column
                    .getColumn()
                    .setText(Messages.WSDLSchemaValidationPreferencePage_NamespaceColumn_label);

            column.setEditingSupport(new NamespaceColEditingSupport(viewer));
            column.setLabelProvider(new ColumnLabelProvider());
            setColumnProportions(new float[] { 1 });
        }

        @Override
        protected ViewerDeleteAction createDeleteAction(
                final ColumnViewer viewer) {
            return new ViewerDeleteAction(viewer) {
                @Override
                public void run() {
                    viewer.cancelEditing();
                    setErrorMessage(null);
                    if (selection != null && !selection.isEmpty()) {
                        for (Iterator<?> iter = selection.iterator(); iter
                                .hasNext();) {
                            namespaces.remove(iter.next());
                        }
                        viewer.refresh();
                    }
                }
            };
        }

        @Override
        protected ViewerAddAction createAddAction(final ColumnViewer viewer) {
            return new ViewerAddAction(viewer) {
                @Override
                public void run() {
                    viewer.cancelEditing();
                    setErrorMessage(null);
                    if (namespaces.add("")) { //$NON-NLS-1$
                        viewer.refresh();
                        viewer.editElement("", 0); //$NON-NLS-1$
                    }
                }
            };
        }
    }

    /**
     * Editing support for the namespace prefix column.
     */
    private class NamespaceColEditingSupport extends EditingSupport {

        private final TextCellEditor editor;

        public NamespaceColEditingSupport(ColumnViewer viewer) {
            super(viewer);

            editor = new TextCellEditor((Composite) viewer.getControl());
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return editor;
        }

        @Override
        protected Object getValue(Object element) {
            return element;
        }

        @Override
        protected void setValue(Object element, Object value) {
            if (element != null && value != null && !element.equals(value)) {
                int idx = namespaces.indexOf(element);
                if (idx >= 0) {
                    if (!((String) value).trim().isEmpty()) {
                        namespaces.set(idx, (String) value);
                    } else {
                        namespaces.remove(element);
                    }
                    getViewer().refresh();
                }
            } else if (element.toString().isEmpty()
                    && value.toString().isEmpty()) {
                // User decided not to enter a new value
                namespaces.remove(element);
                getViewer().refresh();
            }
        }
    }
}
