/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.validationtestwizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import com.tibco.xpd.core.createtestwizards.CreateTestWizardsConstants;
import com.tibco.xpd.core.test.util.ValidationTestUtil;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.resolutions.GenericResolutionGenerator;

/**
 * CreateValidationTestPage
 * 
 * @author aallway
 * @since 3.3
 */
public class CreateValidationTestPage extends WizardPage {

    private final Collection<IResource> studioResources;

    /**
     * Prolem markers that have been selected per-resource.
     */
    private final Map<IResource, List<IMarker>> problemMarkerCache;

    /**
     * Map of the one quick fix that has been optionally selected per marker.
     */
    private final Map<IMarker, IMarkerResolution> selectedMarkerQuickFix;

    private XpdWizardToolkit toolkit;

    private ContainerCheckedTreeViewer problemTree;

    private ContainerCheckedTreeViewer quickFixList;

    public Boolean isCheckProblemsExists = true;

    public Text descText;

    protected CreateValidationTestPage(Collection<IResource> studioResources) {
        super(Messages.CreateValidationTestPage_SelectValdiationProblems_title);

        this.studioResources = studioResources;

        this.problemMarkerCache = cacheMarkers();

        selectedMarkerQuickFix = new HashMap<IMarker, IMarkerResolution>();

        setDescription(Messages.CreateValidationTestPage_SelectValidationProblems_longdesc);

        setPageComplete(false);
    }

    /**
     * @return the selected Problem Markers
     */
    public List<IMarker> getSelectedProblemMarkers() {
        List<IMarker> selectedMarkers = new ArrayList<IMarker>();

        Object[] checkedElements = problemTree.getCheckedElements();

        for (Object element : checkedElements) {
            if (element instanceof IMarker) {
                IMarker marker = (IMarker) element;

                selectedMarkers.add(marker);
            }
        }

        return selectedMarkers;
    }

    /**
     * @return the SelectedMarker to QuickFix map.
     */
    public Map<IMarker, IMarkerResolution> getSelectedMarkerQuickFix() {
        return selectedMarkerQuickFix;
    }

    public void createControl(Composite parent) {
        toolkit = new XpdWizardToolkit(parent);

        Composite root = toolkit.createComposite(parent, SWT.NONE);
        root.setLayout(new GridLayout(1, false));

        Group validationTestTypeGroup =
                toolkit.createGroup(root,
                        Messages.CreateValidationTestPage_group_label);
        validationTestTypeGroup.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        validationTestTypeGroup.setLayout(new RowLayout());
        Button existsButton =
                toolkit.createButton(validationTestTypeGroup,
                        Messages.CreateValidationTestPage_ExistsValidationBtn_label,
                        SWT.RADIO);

        existsButton.setSelection(true);

        existsButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                isCheckProblemsExists = true;
                quickFixList.getTree().setEnabled(true);
            }

            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });
        Button doesntExistButton =
                toolkit.createButton(validationTestTypeGroup,
                        Messages.CreateValidationTestPage_DontExistsMarker_label,
                        SWT.RADIO);
        doesntExistButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                isCheckProblemsExists = false;
                TreeItem[] items = quickFixList.getTree().getItems();
                for (TreeItem treeItem : items) {
                    quickFixList.setSubtreeChecked(treeItem, false);
                }
                quickFixList.getTree().setEnabled(false);
            }

            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Composite descComposite = toolkit.createComposite(root);
        descComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        descComposite.setLayout(new GridLayout(2, false));
        Label lab;

        lab = toolkit.createLabel(descComposite, "Description");
        lab.setLayoutData(new GridData());
        lab.setToolTipText("Description will be included in the test class.(Additional information added will be good note for further maintenance)");

        descText = toolkit.createText(descComposite, "");
        descText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //
        // Overall test name
        lab = toolkit.createLabel(root, "", SWT.NONE); //$NON-NLS-1$
        lab.setText(Messages.CreateValidationTestPage_ProblemsToInc_label);
        lab.setToolTipText(Messages.CreateValidationTestPage_ProblemsToInc_tooltip);
        lab.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        problemTree = new ContainerCheckedTreeViewer(root);
        problemTree.setLabelProvider(new ProblemViewerLabelProvider());
        problemTree.setContentProvider(new ProblemViewerContentProvider());

        problemTree.setInput(this);
        problemTree.expandToLevel(ContainerCheckedTreeViewer.ALL_LEVELS);

        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.widthHint = 900;
        gd.heightHint = 300;
        problemTree.getTree().setLayoutData(gd);

        lab = toolkit.createLabel(root, "", SWT.NONE); //$NON-NLS-1$
        lab.setText(Messages.CreateValidationTestPage_QuickFixToExec_label);
        lab.setToolTipText(Messages.CreateValidationTestPage_QuickFixToExec_tooltip);
        lab.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        quickFixList = new ContainerCheckedTreeViewer(root);
        quickFixList.setLabelProvider(new QuickFixViewerLabelProvider());
        quickFixList.setContentProvider(new QuickFixViewerContentProvider());

        quickFixList.setInput(this);
        quickFixList.expandToLevel(ContainerCheckedTreeViewer.ALL_LEVELS);

        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 50;
        quickFixList.getTree().setLayoutData(gd);

        CLabel clab = toolkit.createCLabel(root, "", SWT.NONE); //$NON-NLS-1$
        clab.setText(Messages.CreateValidationTestPage_QuickShouldntDisplayDialog_label);
        clab.setImage(CreateTestWizardsConstants
                .getImage(CreateTestWizardsConstants.IMG_WARNING_LARGE));
        lab.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //
        // Setup the control listeners
        setupControlListeners();
        setControl(root);

        validatePage();

        return;
    }

    /**
     * Setup the control listeners
     */
    private void setupControlListeners() {
        problemTree
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    public void selectionChanged(SelectionChangedEvent event) {
                        IStructuredSelection selection =
                                (IStructuredSelection) problemTree
                                        .getSelection();

                        if (selection.getFirstElement() instanceof IMarker) {
                            IMarker marker =
                                    (IMarker) selection.getFirstElement();
                            quickFixList.setInput(marker);

                            IMarkerResolution selectedQuickFix =
                                    selectedMarkerQuickFix.get(marker);
                            if (selectedQuickFix != null) {
                                quickFixList
                                        .setCheckedElements(new Object[] { selectedQuickFix });
                            } else {
                                quickFixList.setCheckedElements(new Object[0]);
                            }

                        } else {
                            quickFixList.setInput(null);
                        }

                        validatePage();
                        return;
                    }
                });

        problemTree.addCheckStateListener(new ICheckStateListener() {

            public void checkStateChanged(CheckStateChangedEvent event) {
                validatePage();
                problemTree.setSelection(new StructuredSelection(event
                        .getElement()));
                return;
            }
        });

        quickFixList.addCheckStateListener(new ICheckStateListener() {

            public void checkStateChanged(CheckStateChangedEvent event) {
                IStructuredSelection selection =
                        (IStructuredSelection) problemTree.getSelection();

                if (selection.getFirstElement() instanceof IMarker
                        && event.getElement() instanceof IMarkerResolution) {
                    IMarker marker = (IMarker) selection.getFirstElement();

                    IMarkerResolution resolution =
                            (IMarkerResolution) event.getElement();

                    if (event.getChecked()) {
                        // Replace existing marker + checked resolution from
                        // cache.
                        selectedMarkerQuickFix.put(marker, resolution);

                        quickFixList
                                .setCheckedElements(new Object[] { resolution });

                    } else {
                        // Remove the marker + checked resolution pair from
                        // cache.
                        selectedMarkerQuickFix.remove(marker);
                    }

                }

                validatePage();
                return;
            }
        });

        return;
    }

    /**
     * validate and set page complete if it is.
     */
    private void validatePage() {
        String errorDescAndCompleteFlag = null;

        Object[] selectedProblems = problemTree.getCheckedElements();

        if (selectedProblems == null || selectedProblems.length == 0) {
            errorDescAndCompleteFlag =
                    Messages.CreateValidationTestPage_MustSelectOneProbMarker_warning1;
        }

        setErrorMessage(errorDescAndCompleteFlag);

        if (errorDescAndCompleteFlag == null) {
            setPageComplete(true);
        } else {
            setPageComplete(false);
        }

        return;
    }

    /**
     * Create a cache map of resource file to problem markers on it.
     */
    private Map<IResource, List<IMarker>> cacheMarkers() {
        Map<IResource, List<IMarker>> markers =
                new HashMap<IResource, List<IMarker>>();

        for (IResource resource : studioResources) {
            List<IMarker> problemMarkers =
                    ValidationTestUtil.getProblemMarkers(resource);

            Collections.sort(problemMarkers, new Comparator<IMarker>() {

                public int compare(IMarker o1, IMarker o2) {
                    //
                    // Group by severity.
                    int dest1Sev = getSeveritySortValue(o1);
                    int dest2Sev = getSeveritySortValue(o2);

                    if (dest1Sev != dest2Sev) {
                        return dest2Sev - dest1Sev;
                    }

                    //
                    // Then By destination.
                    String destId1 = o1.getAttribute(IIssue.DESTINATION_ID, ""); //$NON-NLS-1$

                    String destId2 = o2.getAttribute(IIssue.DESTINATION_ID, ""); //$NON-NLS-1$

                    if (!destId1.equals(destId2)) {
                        return destId1.compareTo(destId2);
                    }

                    return 0;
                }

                private int getSeveritySortValue(IMarker marker) {
                    int severity = marker.getAttribute(IMarker.SEVERITY, -1);
                    if (severity == IMarker.SEVERITY_ERROR) {
                        return 3;
                    }
                    if (severity == IMarker.SEVERITY_WARNING) {
                        return 2;
                    }
                    if (severity == IMarker.SEVERITY_INFO) {
                        return 1;
                    }
                    return 0;
                }

            });

            markers.put(resource, problemMarkers);
        }

        return markers;
    }

    /**
     * ProblemViewerLabelProvider
     * 
     * @author aallway
     * @since 3.3 (24 Jul 2009)
     */
    private class ProblemViewerLabelProvider extends
            ProjectExplorerLabelProvider {
        @Override
        public String getText(Object element) {
            if (element instanceof IMarker) {
                IMarker marker = (IMarker) element;

                return marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
            }

            return super.getText(element);
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof IMarker) {
                IMarker marker = (IMarker) element;

                int severity = marker.getAttribute(IMarker.SEVERITY, -1);

                switch (severity) {
                case IMarker.SEVERITY_ERROR:
                    return PlatformUI.getWorkbench().getSharedImages()
                            .getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
                case IMarker.SEVERITY_WARNING:
                    return PlatformUI.getWorkbench().getSharedImages()
                            .getImage(ISharedImages.IMG_OBJS_WARN_TSK);
                case IMarker.SEVERITY_INFO:
                    return PlatformUI.getWorkbench().getSharedImages()
                            .getImage(ISharedImages.IMG_OBJS_INFO_TSK);
                }

            }
            return super.getImage(element);
        }
    }

    /**
     * ProblemViewerContentProvider
     * 
     * Currently content is just the files the user selected directly and the
     * problems associated with that file as children.
     * <p>
     * Later, we can add full project tree in if we wish.
     * 
     * @author aallway
     * @since 3.3 (24 Jul 2009)
     */
    private class ProblemViewerContentProvider implements ITreeContentProvider {

        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof IFile) {
                IFile file = (IFile) parentElement;

                List<IMarker> markers = problemMarkerCache.get(file);
                if (markers != null && !markers.isEmpty()) {
                    return markers.toArray();
                }
            }

            return new Object[0];
        }

        public Object getParent(Object element) {
            if (element instanceof IMarker) {
                IMarker marker = (IMarker) element;
                return marker.getResource();
            }
            return null;
        }

        public boolean hasChildren(Object element) {
            if (element instanceof IFile) {
                IFile file = (IFile) element;

                List<IMarker> markers = problemMarkerCache.get(file);
                if (markers != null && !markers.isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        public Object[] getElements(Object inputElement) {
            //
            // For now we will only deal with files that the user has directly
            // selected (later we can get fancy and provide a proper tree view).
            List<IResource> files = new ArrayList<IResource>();
            for (IResource resource : studioResources) {
                if (resource instanceof IFile) {
                    files.add(resource);
                }
            }
            return files.toArray();
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        }
    }

    /**
     * QuickFixViewerLabelProvider
     * 
     * @author aallway
     * @since 3.3 (24 Jul 2009)
     */
    private class QuickFixViewerLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            if (element instanceof IMarkerResolution) {
                IMarkerResolution fix = (IMarkerResolution) element;

                return fix.getLabel();
            }
            return super.getText(element);
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof IMarkerResolution) {
                IMarkerResolution fix = (IMarkerResolution) element;

                if (fix instanceof IMarkerResolution2) {
                    IMarkerResolution2 res2 = (IMarkerResolution2) fix;

                    Image img = res2.getImage();
                    if (img != null) {
                        return img;
                    }
                }

                return CreateTestWizardsConstants
                        .getImage(CreateTestWizardsConstants.IMG_DEFAULT_QUICKFIX);
            }

            return super.getImage(element);
        }
    }

    /**
     * QuickFixViewerContentProvider
     * 
     * @author aallway
     * @since 3.3 (24 Jul 2009)
     */
    private class QuickFixViewerContentProvider implements ITreeContentProvider {

        public Object[] getChildren(Object parentElement) {
            return new Object[0];
        }

        public Object getParent(Object element) {
            return null;
        }

        public boolean hasChildren(Object element) {
            return false;
        }

        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof IMarker) {
                IMarker marker = (IMarker) inputElement;

                List<IMarkerResolution> quickFixes =
                        new ArrayList<IMarkerResolution>();
                IMarkerResolution[] found =
                        new GenericResolutionGenerator().getResolutions(marker);
                if (found != null) {
                    quickFixes = new ArrayList<IMarkerResolution>();
                    for (int i = 0; i < found.length; i++) {
                        IMarkerResolution markerResolution = found[i];
                        quickFixes.add(markerResolution);
                    }
                }

                return quickFixes.toArray();
            }

            return new Object[0];
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

}
