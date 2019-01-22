/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.dbimport.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.sqltools.core.profile.ProfileUtil;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.xpd.bom.dbimport.DBImageConstants;
import com.tibco.xpd.bom.dbimport.DBImportPlugin;
import com.tibco.xpd.bom.dbimport.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdSelectionWizardPage;

/**
 * Wizard page to select a Profile from the list of already setup profiles.
 * 
 * @author rsomayaj
 * 
 */
public class ProfileSelectionPage extends AbstractXpdSelectionWizardPage {

    /**
     * 
     */
    private static final String CREATE_NEW_WIZARDITEM =
            Messages.ProfileSelectionPage_CreateProfileItem_label;

    private NewDBCategoryWizardNode newCategoryWizardNode;

    private ProfileSelectImportWizardNode selectTablesWizardNode;

    private Object initialSelectedObject;

    protected ProfileSelectionPage(String pageName, Object initialSelectedObject) {
        super(pageName);
        this.initialSelectedObject = initialSelectedObject;
        newCategoryWizardNode = new NewDBCategoryWizardNode();
        selectTablesWizardNode =
                new ProfileSelectImportWizardNode(initialSelectedObject);
        setTitle(Messages.ProfileSelectionPage_WindowTitle_title);
        setDescription(Messages.ProfileSelectionPage_WindowDesc_shortdesc);
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new GridLayout());
        final Group group = new Group(container, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setText(Messages.ProfileSelectionPage_Group_label);
        group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 8));
        {
            final TableViewer tableViewer =
                    new TableViewer(group, SWT.BORDER | SWT.FULL_SELECTION);
            tableViewer
                    .addSelectionChangedListener(new ISelectionChangedListener() {

                        public void selectionChanged(SelectionChangedEvent event) {
                            IStructuredSelection iss =
                                    (IStructuredSelection) tableViewer
                                            .getSelection();
                            Object element = iss.getFirstElement();
                            if (element instanceof IConnectionProfile) {
                                IConnectionProfile connectionProfile =
                                        (IConnectionProfile) element;
                                if (IConnectionProfile.DISCONNECTED_STATE != connectionProfile
                                        .getConnectionState()) {
                                    selectTablesWizardNode
                                            .setConnectionProfileObj(connectionProfile);
                                    setErrorMessage(null);
                                    setSelectedNode(selectTablesWizardNode);
                                } else {
                                    setErrorMessage(Messages.ProfileSelectionPage_UnableToConnectErr_shortdesc);
                                    setSelectedNode(null);
                                    return;
                                }

                            } else if (element instanceof String
                                    && element.toString()
                                            .equals(CREATE_NEW_WIZARDITEM)) {
                                setSelectedNode(newCategoryWizardNode);
                            }
                            getContainer().updateButtons();
                        }
                    });
            tableViewer.addDoubleClickListener(new IDoubleClickListener() {

                public void doubleClick(DoubleClickEvent e) {
                    IStructuredSelection iss =
                            (IStructuredSelection) tableViewer.getSelection();
                    flipToNextPage(iss.getFirstElement());
                }

            });
            tableViewer.setContentProvider(new TableContentProvider());
            tableViewer.setLabelProvider(new TableLabelProvider());
            final Table table = tableViewer.getTable();
            table.setLayoutData(new GridData(GridData.FILL_BOTH));
            TableLayout tl = new TableLayout();
            {
                tl.addColumnData(new ColumnWeightData(100));
                {
                    final TableColumn tableColumn =
                            new TableColumn(table, SWT.NONE);
                    tableColumn.setWidth(400);
                }
            }
            tableViewer.setInput(ProfileUtil.getProfiles());
            if (ProfileUtil.getProfiles().length < 1) {
                setErrorMessage("Create a database profile before proceeding to import the tables.");
            } else {
                setErrorMessage(null);
            }
        }

        setControl(container);

    }

    private void flipToNextPage(Object element) {
        if (element instanceof IConnectionProfile) {
            IConnectionProfile connectionProfile = (IConnectionProfile) element;
            if (IConnectionProfile.DISCONNECTED_STATE != connectionProfile
                    .getConnectionState()) {
                selectTablesWizardNode
                        .setConnectionProfileObj(connectionProfile);
                setSelectedNode(selectTablesWizardNode);
            } else {
                setErrorMessage(Messages.ProfileSelectionPage_UnableToConnectErr_shortdesc);
                return;
            }
        } else if (element instanceof String
                && element.toString().equals(CREATE_NEW_WIZARDITEM)) {
            setSelectedNode(newCategoryWizardNode);
        }
        getContainer().showPage(getNextPage());
    }

    class TableLabelProvider implements ILabelProvider {

        /**
         * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
         * 
         * @param element
         * @return
         */
        public Image getImage(Object element) {
            if (element instanceof IConnectionProfile) {
                return DBImportPlugin.getDefault().getImageRegistry()
                        .get(DBImageConstants.IMG_DB);
            }
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        public String getText(Object element) {
            if (element instanceof IConnectionProfile) {
                return ((IConnectionProfile) element).getName();
            }
            return element.toString();
        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
         * 
         * @param listener
         */
        public void addListener(ILabelProviderListener listener) {

        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
         * 
         */
        public void dispose() {
        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
         *      java.lang.String)
         * 
         * @param element
         * @param property
         * @return
         */
        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        /**
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
         * 
         * @param listener
         */
        public void removeListener(ILabelProviderListener listener) {
        }
    }

    class TableContentProvider implements IStructuredContentProvider {

        public void dispose() {
            // do nothing
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param oldInput
         * @param newInput
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        public Object[] getElements(Object inputElement) {
            List profiles =
                    new ArrayList(Arrays.asList(ProfileUtil.getProfiles()));
            return profiles.toArray();
        }
    }

}