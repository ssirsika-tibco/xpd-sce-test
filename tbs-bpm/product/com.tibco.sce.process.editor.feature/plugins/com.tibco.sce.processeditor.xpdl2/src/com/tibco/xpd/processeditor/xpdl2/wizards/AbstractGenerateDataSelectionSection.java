/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Section explicitly designed to be used as initial page in
 * "Generate <some kind of process from task interface data>" wizard - allows
 * user to configure the data to be passed pass to/from a given process.
 * <p>
 * The selection
 * 
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractGenerateDataSelectionSection extends
        AbstractTransactionalSection {
    private List<ProcessRelevantData> otherAvailableData;

    private List<ProcessRelevantData> associatedData;

    private static final String ASSOCIATED_DATA_FOLDER =
            "AssocDataFolderObject"; //$NON-NLS-1$

    private static final String OTHER_DATA_FOLDER = "OtherDataFolderObject"; //$NON-NLS-1$

    private CheckboxTreeViewer treeViewer;

    protected Object[] treeSelectedData;

    public AbstractGenerateDataSelectionSection(
            List<ProcessRelevantData> availableData,
            List<ProcessRelevantData> associatedData) {
        super();
        this.associatedData = associatedData;
        this.otherAvailableData =
                new ArrayList<ProcessRelevantData>(availableData);
        this.otherAvailableData.removeAll(associatedData);
    }

    public List<ProcessRelevantData> getUserSelectedData() {
        List<ProcessRelevantData> selectedData =
                new ArrayList<ProcessRelevantData>();

        if (treeViewer != null) {
            Object[] checked = treeSelectedData;

            for (Object o : checked) {
                if (o instanceof ProcessRelevantData) {
                    selectedData.add((ProcessRelevantData) o);
                }
            }
        }

        return selectedData;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new FillLayout());

        treeViewer = new ContainerCheckedTreeViewer(root);
        treeViewer.setLabelProvider(new DataLabelProvider());
        treeViewer.setContentProvider(new DataContentProvider());

        treeViewer.setInput(this);
        treeViewer.expandToLevel(ASSOCIATED_DATA_FOLDER, treeViewer.ALL_LEVELS);
        treeViewer.setSubtreeChecked(ASSOCIATED_DATA_FOLDER, true);

        /*
         * Keep local field with selected data in so that it can be accessed
         * after wizard has finished (potentially outside of UI thread.
         */
        treeViewer.addCheckStateListener(new ICheckStateListener() {
            @Override
            public void checkStateChanged(CheckStateChangedEvent event) {
                treeSelectedData = treeViewer.getCheckedElements();
            }
        });

        treeSelectedData = treeViewer.getCheckedElements();

        // treeViewer.addCheckStateListener(new AutoTreeCheckStateListener(
        // treeViewer));

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    @Override
    protected void doRefresh() {
        return;
    }

    private class DataContentProvider implements ITreeContentProvider {

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement == ASSOCIATED_DATA_FOLDER) {
                return associatedData.toArray();

            } else if (parentElement == OTHER_DATA_FOLDER) {
                List<ProcessRelevantData> otherData =
                        new ArrayList<ProcessRelevantData>(otherAvailableData);
                otherData.removeAll(associatedData);

                return otherData.toArray();
            }
            return new Object[0];
        }

        @Override
        public Object getParent(Object element) {
            if (associatedData.contains(element)) {
                return ASSOCIATED_DATA_FOLDER;
            } else if (otherAvailableData.contains(element)) {
                return OTHER_DATA_FOLDER;
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element == ASSOCIATED_DATA_FOLDER) {
                return true;
            } else if (element == OTHER_DATA_FOLDER) {
                return true;
            }
            return false;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            return new Object[] { ASSOCIATED_DATA_FOLDER, OTHER_DATA_FOLDER };
        }

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    /**
     * @return Get label name to use for "other available data" folder in field
     *         selection tree.
     */
    protected abstract String getOtherAvailableDataFolderName();

    /**
     * @return Get label name to use for task associated data folder in data
     *         selection tree.
     */
    protected abstract String getAssociatedDataTaskFolderName();

    private class DataLabelProvider extends LabelProvider {

        @Override
        public String getText(Object element) {
            if (element instanceof ProcessRelevantData) {
                return Xpdl2ModelUtil
                        .getDisplayNameOrName((ProcessRelevantData) element);

            } else if (ASSOCIATED_DATA_FOLDER == element) {
                return getAssociatedDataTaskFolderName();
            } else if (OTHER_DATA_FOLDER == element) {
                return getOtherAvailableDataFolderName();
            }
            return ""; //$NON-NLS-1$
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof ProcessRelevantData) {
                return WorkingCopyUtil.getImage((ProcessRelevantData) element);
            } else if (ASSOCIATED_DATA_FOLDER == element) {
                return Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_ACTIVITY_INTERFACE);
            } else if (OTHER_DATA_FOLDER == element) {
                return Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                        .get(ProcessEditorConstants.IMG_DATAFIELD);
            }
            return null;
        }
    }

}