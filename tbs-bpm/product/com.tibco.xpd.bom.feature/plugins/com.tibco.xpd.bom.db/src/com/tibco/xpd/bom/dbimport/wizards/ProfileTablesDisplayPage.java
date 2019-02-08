/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.modelbase.sql.tables.PersistentTable;
import org.eclipse.datatools.modelbase.sql.tables.Table;
import org.eclipse.datatools.sqltools.core.DatabaseIdentifier;
import org.eclipse.datatools.sqltools.core.profile.ProfileUtil;
import org.eclipse.datatools.sqltools.sql.util.ModelUtil;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.xpd.bom.dbimport.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Wizard page displaying all the tables of a particular profile.
 * 
 * @author rsomayaj
 * 
 */
public class ProfileTablesDisplayPage extends AbstractXpdWizardPage {

    private boolean debug = false;

    private IConnectionProfile connectionProfile;

    /**
     * @param pageName
     */
    protected ProfileTablesDisplayPage() {
        super("TablesInProfileWizardPage"); //$NON-NLS-1$
        setTitle(Messages.ProfileTablesDisplayPage_WindowTitle_title);
        setDescription(Messages.ProfileTablesDisplayPage_WindowDesc_shortdesc);

    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.None);
        container.setLayout(new GridLayout());

        final Group group = new Group(container, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setText(Messages.ProfileTablesDisplayPage_TableInDB_label);
        GridData layoutData =
                new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        layoutData.heightHint = 320;
        group.setLayoutData(layoutData);

        chkTableViewer =
                CheckboxTableViewer.newCheckList(group, SWT.BORDER
                        | SWT.FULL_SELECTION);
        org.eclipse.swt.widgets.Table table = chkTableViewer.getTable();
        GridData gridData = new GridData(GridData.FILL_BOTH);

        table.setLayoutData(gridData);

        TableLayout tl = new TableLayout();
        {
            tl.addColumnData(new ColumnWeightData(100));
            {
                final TableColumn tableColumn =
                        new TableColumn(table, SWT.NONE);
                tableColumn.setWidth(400);
            }
        }

        chkTableViewer.setContentProvider(contentProvider);
        chkTableViewer.setLabelProvider(labelProvider);
        if (connectionProfile != null) {
            DatabaseIdentifier dbId =
                    new DatabaseIdentifier(connectionProfile.getName());
            Database database = ProfileUtil.getDatabase(dbId);
            chkTableViewer.setInput(database);
        }
        chkTableViewer.setAllChecked(true);

        Composite buttonsComposite = new Composite(container, SWT.None);
        GridData buttonsCompositeLayoutData =
                new GridData(SWT.FILL, SWT.END, true, true, 1, 1);
        buttonsComposite.setLayoutData(buttonsCompositeLayoutData);
        buttonsComposite.setLayout(new GridLayout(2, false));
        Button btnSelectAll = new Button(buttonsComposite, SWT.PUSH);
        btnSelectAll
                .setText(Messages.ProfileTablesDisplayPage_SelectAllBtn_label);
        btnSelectAll.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        btnSelectAll.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                chkTableViewer.setAllChecked(true);
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
        Button btnDeselectAll = new Button(buttonsComposite, SWT.PUSH);
        btnDeselectAll
                .setText(Messages.ProfileTablesDisplayPage_DeselectAllBtn_label2);
        btnDeselectAll
                .setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        btnDeselectAll.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {

            }

            public void widgetSelected(SelectionEvent e) {
                chkTableViewer.setAllChecked(false);

            }

        });

        setControl(container);
    }

    public void setConnectionProfile(IConnectionProfile connectionProfile) {
        this.connectionProfile = connectionProfile;
        if (connectionProfile != null) {
            DatabaseIdentifier dbId =
                    new DatabaseIdentifier(connectionProfile.getName());
            Database database = ProfileUtil.getDatabase(dbId);
            if (chkTableViewer != null) {
                chkTableViewer.setInput(database);
                chkTableViewer.setAllChecked(true);
            }
        }

    }

    ILabelProvider labelProvider = new ILabelProvider() {

        public Image getImage(Object element) {
            return null;
        }

        public String getText(Object element) {
            if (element instanceof Table) {
                return ((Table) element).getName();
            }
            return null;
        }

        public void addListener(ILabelProviderListener listener) {
        }

        public void dispose() {

        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {

        }

    };

    IStructuredContentProvider contentProvider =
            new IStructuredContentProvider() {

                public Object[] getElements(Object inputElement) {
                    List<Table> tables = new ArrayList<Table>();
                    if (inputElement instanceof Database) {
                        Database db = (Database) inputElement;

                        List<Catalog> catalogs = db.getCatalogs();
                        List<Schema> schemas = Collections.EMPTY_LIST;
                        if (!catalogs.isEmpty()) {
                            for (Catalog catalog : catalogs) {
                                findTables(tables, db, catalog.getSchemas());
                            }
                        } else {
                            schemas = db.getSchemas();
                            findTables(tables, db, schemas);
                        }
                    }

                    return tables.toArray();
                }

                /**
                 * @param tables
                 * @param db
                 * @param schemas
                 */
                private void findTables(List<Table> tables, Database db,
                        List<Schema> schemas) {
                    for (Object obj : schemas) {
                        if (obj instanceof Schema) {
                            try {
                                Schema schema = ((Schema) obj);
                                Database database =
                                        ModelUtil.getDatabase(schema);
                                if (db.equals(database)) {
                                    List<Table> tables2 =
                                            ((Schema) obj).getTables();
                                    for (Object tableObj : tables2) {
                                        if (tableObj instanceof PersistentTable) {
                                            tables.add((PersistentTable) tableObj);
                                            if (debug) {
                                                System.out.println("------");
                                                System.out
                                                        .println("PersistentTabel##"
                                                                + tableObj);
                                                System.out.println("___");
                                            }

                                        }
                                    }
                                }
                            } catch (Exception exception) {
                                System.err.println(exception.getMessage());
                            }
                        }
                    }
                }

                public void dispose() {

                }

                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {

                }

            };

    private CheckboxTableViewer chkTableViewer;

    /**
     * @return the chkTableViewer
     */
    public List<?> getListOfTables() {
        return Arrays.asList(chkTableViewer.getCheckedElements());
    }

    /**
     * @return the connectionProfile
     */
    public IConnectionProfile getConnectionProfile() {
        return connectionProfile;
    }

}
