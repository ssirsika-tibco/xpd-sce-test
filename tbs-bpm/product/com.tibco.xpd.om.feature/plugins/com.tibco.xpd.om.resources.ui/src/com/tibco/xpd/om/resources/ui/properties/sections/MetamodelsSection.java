/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypeProvider;
import com.tibco.xpd.resources.ui.types.TypedItem;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * @author wzurek
 * @author glewis
 * 
 */
public class MetamodelsSection extends AbstractTransactionalSection {

    private Button metamodelsTypeBtn;
    private TableViewer metamodelsViewer;

    public class MetamodelsTebleLabelProvider extends BaseLabelProvider
            implements ITableLabelProvider {

        public Image getColumnImage(Object element, int columnIndex) {
            if (columnIndex == 0 && element instanceof OrgMetaModel) {
                return WorkingCopyUtil.getImage((OrgMetaModel) element);
            }
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            OrgMetaModel mm = (OrgMetaModel) element;
            switch (columnIndex) {
            case 0:
                return mm.getDisplayName();
            case 1:
                if (mm.eResource() != null) {
                    IFile mmFile = WorkspaceSynchronizer
                            .getFile(mm.eResource());
                    return SpecialFolderUtil.getSpecialFolderRelativePath(
                            mmFile).toPortableString();
                }
                return null;
            }
            return null;
        }
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite cp = toolkit.createComposite(parent);
        GridLayout gdLayout = new GridLayout();
        gdLayout.numColumns = 2;
        cp.setLayout(gdLayout);

        Label lbl = toolkit.createLabel(cp,
                Messages.MetamodelsSection_addRemoveRefSchema_label);
        GridData gData = new GridData();
        lbl.setLayoutData(gData);

        metamodelsTypeBtn = toolkit.createButton(cp,
                "...", SWT.NONE, "metamodelsTypeBtn"); //$NON-NLS-1$ //$NON-NLS-2$
        manageControl(metamodelsTypeBtn);

        Table table = toolkit.createTable(cp, SWT.FULL_SELECTION,
                "metamodelsTable"); //$NON-NLS-1$

        GridData gDataTab = new GridData(SWT.FILL, SWT.FILL, true, true);
        gDataTab.horizontalSpan = 2;

        table.setLayoutData(gDataTab);
        table.setLinesVisible(true);

        configureTable(table);

        return cp;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand compCmd = null;

        if (obj == metamodelsTypeBtn) {
            EObject input = getInput();
            if (input != null) {
                final List<OrgMetaModel> oldReferencedMetamodels = new ArrayList<OrgMetaModel>(
                        ((OrgModel) getInput()).getMetamodels());

                TypeInfo typeInfo = OMTypesFactory.TYPE_ORG_META_MODEL;
                typeInfo.setData(getInput().eClass());

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getInput());

                IProject resourceToLookUp = wc.getEclipseResources().get(0)
                        .getProject();
                IResource[] queryResources = new IResource[] { resourceToLookUp };

                // Only want to show metamodels within the OMs project so create
                // a filter and pass to the picker
                IFilter filter = new ProjectPickerFilter(resourceToLookUp);

                Object[] result = TypeProvider.openMultiPickerDialog(getSite()
                        .getShell(), null, queryResources,
                        new TypeInfo[] { typeInfo }, null,
                        oldReferencedMetamodels.toArray(), Collections
                                .singletonList(filter));

                if (result != null) {
                    compCmd = new CompoundCommand();
                    List<Object> selected = new ArrayList<Object>(Arrays
                            .asList(result));

                    Iterator<?> it = selected.iterator();

                    // Try to remove from the old list all currently
                    // selected.
                    while (it.hasNext()) {
                        OrgMetaModel mm = (OrgMetaModel) it.next();
                        boolean isRemoved = oldReferencedMetamodels.remove(mm);
                        if (!isRemoved) {
                            // The object wasn't removed so it is a new one,
                            // hence we have to add it.
                            EditingDomain ed = getEditingDomain();
                            Command cmd = AddCommand.create(ed, getInput(),
                                    OMPackage.Literals.ORG_MODEL__METAMODELS,
                                    mm);

                            compCmd.append(cmd);
                        }
                    }

                    // Remove any all remaining objects (not selected anymore).
                    it = oldReferencedMetamodels.iterator();
                    if (!oldReferencedMetamodels.isEmpty()) {
                        Command cmd = RemoveCommand.create(getEditingDomain(),
                                getInput(),
                                OMPackage.Literals.ORG_MODEL__METAMODELS,
                                oldReferencedMetamodels);

                        compCmd.append(cmd);
                    }
                }
            }
        }

        return compCmd;
    }

    @Override
    protected void doRefresh() {
        OrgModel orgModel = (OrgModel) getInput();
        metamodelsViewer.setInput(orgModel.getMetamodels());
    }

    /**
     * @param twb
     */
    private void configureTable(final Table t) {
        metamodelsViewer = new TableViewer(t);
        t.setHeaderVisible(true);

        TableColumn t1 = new TableColumn(t, SWT.None);
        t1.setText(Messages.MetamodelsSection_nameColumn_title);
        t1.setWidth(150);
        TableColumn t2 = new TableColumn(t, SWT.None);
        t2.setText(Messages.MetamodelsSection_fileColumn_title);
        t2.setWidth(200);

        String[] props = new String[] { "name", "file" }; //$NON-NLS-1$ //$NON-NLS-2$
        metamodelsViewer.setColumnProperties(props);

        metamodelsViewer.setLabelProvider(new MetamodelsTebleLabelProvider());
        metamodelsViewer.setContentProvider(new ArrayContentProvider());
    }

    /**
     * Filter to select meta models from current project only.
     * 
     * @author njpatel
     * 
     */
    private class ProjectPickerFilter implements IFilter {

        private IProject resourceToLookUp;

        public ProjectPickerFilter(IProject resourceToLookUp) {
            this.resourceToLookUp = resourceToLookUp;

        }

        public boolean select(Object toTest) {
            if (toTest instanceof TypedItem) {
                TypedItem item = (TypedItem) toTest;
                String uriString = item.getUriString();

                URI uri = URI.createURI(uriString);

                String platformString = uri.toPlatformString(true);
                IResource resource = ResourcesPlugin.getWorkspace().getRoot()
                        .findMember(platformString);

                if (resource instanceof IFile) {
                    IProject project = resource.getProject();
                    return project.equals(resourceToLookUp);
                }
            }

            return false;
        }

    }
}
