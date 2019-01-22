/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.resources.wizards.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;
import org.eclipse.ui.navigator.resources.ProjectExplorer;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.iprocess.amxbpm.resources.ui.internal.Messages;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.edit.util.XpdlUtils;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Page for Selection of target Special Process Packages Folder for Conversion
 * of IPS to BPM.
 * 
 * @author aprasad
 * @since 04-Jun-2014
 */
public class ConvertWizardResourceSelectionPage extends WizardPage implements
        Listener {

    private Collection<IResource> selectedXpdls;

    /**
     * all the xpdl's to convert,user selected as well as implicitly selected
     */
    private Set<IResource> allXpdlsToConvert = null;

    private static final String INTO_FOLDER_PREF_KEY = "INTO_FOLDER_PREF_KEY"; //$NON-NLS-1$

    private Text containerNameField;

    private Button containerBrowseButton;

    private final IImportWizardPageProvider provider;

    /**
     * 
     * @param pageName
     * @param provider
     * @param selectedXpdls
     */
    protected ConvertWizardResourceSelectionPage(String pageName,
            IImportWizardPageProvider provider,
            Collection<IResource> selectedXpdls) {
        super(pageName);
        this.provider = provider;
        this.selectedXpdls = selectedXpdls;
        allXpdlsToConvert = new HashSet<IResource>(selectedXpdls);
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(2, true));
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        composite.setFont(parent.getFont());

        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

        /* Control to Show , Explicitly Selected XPDLs */
        SelectedResourcesTreeControl explicitSelControl =
                new SelectedResourcesTreeControl(
                        composite,
                        Messages.ConvertWizardResourceSelectionPage_SelectedXpdlsControl_title,
                        selectedXpdls);

        explicitSelControl.setInput(workspaceRoot);

        SelectedResourcesTreeControl implicitSelControl =
                new SelectedResourcesTreeControl(
                        composite,
                        Messages.ConvertWizardResourceSelectionPage_ImplicitSelectedXpdlsControl_title,
                        getReferencedXpdlFilesForSelectedXpdls());

        implicitSelControl.setInput(workspaceRoot);

        /*
         * Note by Sid during the 3.8.0 meeting:The target selection control
         * should be after the Source selection.
         */
        createDestinationGroup(composite);

        /* Control to Show , Implicitly Selected referenced XPDLs */
        setControl(composite);
    }

    /**
     * Returns Set of referenced xpdl files for the selected xpdl files.
     * 
     * @return Set of xpdl files directly or indirectly referenced by the
     *         selected xpdl files, and which are not already included in
     *         selected files .
     */
    private Set<IResource> getReferencedXpdlFilesForSelectedXpdls() {

        Set<IResource> refXpdls = new HashSet<IResource>();

        for (IResource inputResource : selectedXpdls) {

            Set<IResource> deepDependencies =
                    WorkingCopyUtil.getDeepDependencies(inputResource);

            for (IResource iResource : deepDependencies) {

                /* If referenced and not already selected */
                if (!selectedXpdls.contains(iResource)
                        && XpdlUtils.isSupportedXPDLFile(iResource)
                        && !isBpmDestEnabledForXPDL(iResource)) {

                    refXpdls.add(iResource);
                }
            }

        }
        /* add all the referenced xpdl's */
        allXpdlsToConvert.addAll(refXpdls);
        return refXpdls;

    }

    /**
     * Checks if the BPM Destination is enabled on the passed XPDL
     * 
     * @param xpdlFile
     *            to check the destination for.
     * @return true if the given XPDL file has iProcess Destination Enabled,
     *         uses DestinationUtil.isGlobalDestinationEnabled().
     */
    private boolean isBpmDestEnabledForXPDL(IResource xpdlFile) {
        WorkingCopy xpdlWorkingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);

        if (xpdlWorkingCopy != null) {

            Package xpdlPackage =
                    Xpdl2ModelUtil.getPackage(xpdlWorkingCopy.getRootElement());

            if (xpdlPackage != null) {

                return GlobalDestinationHelper
                        .isGlobalDestinationEnabled(xpdlPackage,
                                N2Utils.N2_GLOBAL_DESTINATION_ID);
            }
        }

        return false;
    }

    /**
     * 
     * @return all the Xpdl's (explicilty selected by the user and implicitly
     *         dependent resources)
     */
    public Set<IResource> getAllXpdlsToConvert() {
        return allXpdlsToConvert;
    }

    /**
     * @return
     */
    private boolean determinePageCompletion() {

        IStatus status =
                provider.validateDestinationContainer(getSpecifiedContainer());

        if (status != Status.OK_STATUS) {

            setMessage(status.getMessage(), ERROR);

        } else {
            setMessage(null);
        }

        return (status == Status.OK_STATUS);

    }

    /**
     * Returns the container resource specified in the container name entry
     * field, or <code>null</code> if such a container does not exist in the
     * workbench.
     * 
     * @return the container resource specified in the container name entry
     *         field, or <code>null</code>
     */
    protected IContainer getSpecifiedContainer() {

        IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
        IPath path = getContainerFullPath();
        if (workspace.getRoot().exists(path)) {

            IResource resource = workspace.getRoot().findMember(path);

            if (resource.getType() == IResource.FILE) {
                return null;
            }
            return (IContainer) resource;

        }

        return null;
    }

    /**
     * Creates the import destination specification controls.
     * 
     * @param parent
     *            the parent control
     */
    protected final void createDestinationGroup(Composite parent) {
        // container specification group
        Composite containerGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        containerGroup.setLayout(layout);
        GridData gridData =
                new GridData(GridData.HORIZONTAL_ALIGN_FILL
                        | GridData.GRAB_HORIZONTAL);
        gridData.horizontalSpan = 2;
        containerGroup.setLayoutData(gridData);
        containerGroup.setFont(parent.getFont());

        // container label
        Label resourcesLabel = new Label(containerGroup, SWT.NONE);
        resourcesLabel.setText(IDEWorkbenchMessages.WizardImportPage_folder);
        resourcesLabel.setFont(parent.getFont());

        // container name entry field
        containerNameField = new Text(containerGroup, SWT.SINGLE | SWT.BORDER);
        containerNameField.addListener(SWT.Modify, this);
        GridData data =
                new GridData(GridData.HORIZONTAL_ALIGN_FILL
                        | GridData.GRAB_HORIZONTAL);
        data.widthHint = 250;
        containerNameField.setLayoutData(data);
        containerNameField.setFont(parent.getFont());

        // container browse button
        containerBrowseButton = new Button(containerGroup, SWT.PUSH);
        containerBrowseButton
                .setText(IDEWorkbenchMessages.WizardImportPage_browse2);
        containerBrowseButton.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL));
        containerBrowseButton.addListener(SWT.Selection, this);
        containerBrowseButton.setFont(parent.getFont());
        setButtonLayoutData(containerBrowseButton);

        initialPopulateContainerField();
    }

    /**
     * Sets the initial contents of the container name field.
     */
    protected final void initialPopulateContainerField() {
        String preference = getPreference(INTO_FOLDER_PREF_KEY);
        if (preference != null) {
            containerNameField.setText(preference);
        }
    }

    /**
     * Get the value of the given preference key
     * 
     * @param storeKey
     * @return String, value of given preference Key.
     */
    private String getPreference(String storeKey) {
        String szRet = null;

        if (storeKey != null && storeKey != "") { //$NON-NLS-1$
            IPreferenceStore pStore =
                    XpdResourcesUIActivator.getDefault().getPreferenceStore();
            szRet = pStore.getString(storeKey);
        }

        return szRet;
    }

    /**
     * Store a preference key, value pair in the plugin
     * 
     * @param storeKey
     * @param value
     *            , to be stored for given preference key.
     */
    private void setPreference(String storeKey, String value) {

        // Make sure all data is present
        if (storeKey != null && storeKey != "" && value != null) { //$NON-NLS-1$
            IPreferenceStore pStore =
                    XpdResourcesUIActivator.getDefault().getPreferenceStore();
            pStore.setValue(storeKey, value);
        }
    }

    /**
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     * 
     * @param event
     */
    @Override
    public void handleEvent(Event event) {
        Widget source = event.widget;

        if (source == containerBrowseButton) {
            handleContainerBrowseButtonPressed();
        }

        updateControls();
    }

    /**
     * Updates controls for the current Page status
     */
    private void updateControls() {
        setPageComplete(determinePageCompletion());
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ui.dialogs.WizardResourceImportPage#
     * handleContainerBrowseButtonPressed()
     */

    protected void handleContainerBrowseButtonPressed() {
        // Display the picker dialog
        SelectionDialog dialog =
                provider.getDestinationContainerSelectionDialog();

        if (dialog != null && dialog.open() == Dialog.OK) {
            // OK clicked so store the selected packages folder
            if (dialog.getResult()[0] instanceof IAdaptable) {
                IFolder folder =
                        (IFolder) ((IAdaptable) dialog.getResult()[0])
                                .getAdapter(IFolder.class);

                if (folder != null) {
                    String folderName =
                            folder.getProject().getName()
                                    + IPath.SEPARATOR
                                    + folder.getProjectRelativePath()
                                            .toString();

                    setErrorMessage(null);
                    setContainerFieldValue(folderName);
                }
            }
        }
    }

    /**
     * Returns the path of the container resource specified in the container
     * name entry field, or <code>null</code> if no name has been typed in.
     * <p>
     * The container specified by the full path might not exist and would need
     * to be created.
     * </p>
     * 
     * @return the full path of the container resource specified in the
     *         container name entry field, or <code>null</code>
     */
    protected IPath getContainerFullPath() {
        IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

        // make the path absolute to allow for optional leading slash
        IPath testPath = getPathFromText(this.containerNameField);
        ;

        if (testPath.equals(workspace.getRoot().getFullPath())) {
            return testPath;
        }

        IStatus result =
                workspace.validatePath(testPath.toString(), IResource.PROJECT
                        | IResource.FOLDER | IResource.ROOT);
        if (result.isOK()) {
            return testPath;
        }

        return null;
    }

    /**
     * Get a path from the supplied text widget.
     * 
     * @return org.eclipse.core.runtime.IPath
     */
    protected IPath getPathFromText(Text textField) {
        String text = textField.getText();
        // Do not make an empty path absolute so as not to confuse with the root
        if (text.length() == 0) {
            return new Path(text);
        }

        return (new Path(text)).makeAbsolute();
    }

    /**
     * Queries the user to supply a container resource.
     * 
     * @return the path to an existing or new container, or <code>null</code> if
     *         the user cancelled the dialog
     */
    protected IPath queryForContainer(IContainer initialSelection, String msg,
            String title) {
        ContainerSelectionDialog dialog =
                new ContainerSelectionDialog(getControl().getShell(),
                        initialSelection, false, msg);
        if (title != null) {
            dialog.setTitle(title);
        }
        dialog.showClosedProjects(false);
        dialog.open();
        Object[] result = dialog.getResult();
        if (result != null && result.length == 1) {
            return (IPath) result[0];
        }
        return null;
    }

    /**
     * Sets the Into Folder target path,
     * 
     * @param value
     *            String
     */
    public void setContainerFieldValue(String value) {

        if (containerNameField != null) {
            containerNameField.setText(value);
            setPreference(INTO_FOLDER_PREF_KEY, value);
        }
    }

    class SelectedResourcesTreeControl {

        private Composite control;

        private Label headerLabel;

        private CommonViewer contentViewer = null;

        private Object input = null;

        private List<ViewerFilter> viewerFilters = null;

        private ViewerSorter viewerSorter = null;

        private int fWidth = 60;

        private int fHeight = 18;

        private INavigatorContentService navigatorService = null;

        public SelectedResourcesTreeControl(Composite composite, String header,
                Collection<IResource> selectedXpdls) {

            addFilter(new SpecialFoldersOnlyFilter(
                    Collections
                            .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND)));

            addFilter(new FileExtensionInclusionFilter(
                    Collections.singleton(XpdlUtils.XPDL_FILE_EXTENSION)));

            addFilter(new SelectedXpdlFilter(selectedXpdls));

            createControls(composite, header);

        }

        /**
         * Set the input for the viewer
         * 
         * @param input
         */
        public void setInput(Object input) {
            this.input = input;
            // Apply input
            contentViewer.setInput(input);
            contentViewer.expandAll();
        }

        public Composite getControl() {
            return control;
        }

        private void createControls(Composite composite, String header) {

            control = new Composite(composite, SWT.NONE);
            GridLayout layout = new GridLayout();
            layout.marginHeight =
                    convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
            layout.marginWidth =
                    convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
            layout.verticalSpacing =
                    convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
            layout.horizontalSpacing =
                    convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
            control.setLayout(layout);
            control.setLayoutData(new GridData(GridData.FILL_BOTH));

            headerLabel = new Label(control, SWT.NONE);
            if (header != null) {
                headerLabel.setText(header);
            }
            headerLabel.setFont(control.getFont());

            contentViewer = createTreeViewer(control);
            GridData data = new GridData(GridData.FILL_BOTH);
            data.widthHint = convertWidthInCharsToPixels(fWidth);
            data.heightHint = convertHeightInCharsToPixels(fHeight);

            Tree treeWidget = contentViewer.getTree();
            treeWidget.setLayoutData(data);
            treeWidget.setFont(control.getFont());

        }

        /**
         * Sets the size of the tree in unit of characters.
         * 
         * 
         * @param width
         *            the width of the tree.
         * @param height
         *            the height of the tree.
         */
        public void setSize(int width, int height) {
            fWidth = width;
            fHeight = height;
        }

        /**
         * Add the tree sorter
         * 
         * @param sorter
         */
        public void setSorter(ViewerSorter sorter) {
            this.viewerSorter = sorter;
        }

        /**
         * Add tree filter. This may be called multiple times to add multiple
         * filters
         * 
         * @param filter
         */
        public void addFilter(ViewerFilter filter) {

            if (viewerFilters == null) {
                viewerFilters = new ArrayList<ViewerFilter>();
            }

            viewerFilters.add(filter);

        }

        /**
         * Create the tree viewer. This will use the Common Navigator service
         * for the content and label provider. All registered filters and sorter
         * will be applied.
         * 
         * @param composite
         * @return
         */
        private CommonViewer createTreeViewer(Composite composite) {

            if (contentViewer == null) {
                contentViewer =
                        new CommonViewer(ProjectExplorer.VIEW_ID, composite,
                                SWT.BORDER | SWT.SINGLE);
                // Get the navigator content service
                NavigatorContentServiceFactory fact =
                        NavigatorContentServiceFactory.INSTANCE;
                navigatorService =
                        fact.createContentService(ProjectExplorer.VIEW_ID,
                                contentViewer);

                if (navigatorService != null) {

                    /*
                     * Deactivate the Java Element content service in the picker
                     * as it can cause undesirable effects. One common problem
                     * is when a initial selection is made - this causes a
                     * CyclicPathException when it comes to finding the parents
                     * of the selected object
                     */
                    INavigatorActivationService service =
                            navigatorService.getActivationService();

                    if (service != null) {
                        service.deactivateExtensions(new String[] { "org.eclipse.jdt.java.ui.javaContent" }, false); //$NON-NLS-1$
                    }

                    // Set the content and label providers
                    contentViewer.setContentProvider(navigatorService
                            .createCommonContentProvider());
                    contentViewer.setLabelProvider(navigatorService
                            .createCommonLabelProvider());

                    // Apply the navigator active filters
                    ViewerFilter[] navigatorFilters =
                            navigatorService.getFilterService()
                                    .getVisibleFilters(true);

                    if (navigatorFilters != null) {
                        for (ViewerFilter filter : navigatorFilters) {
                            contentViewer.addFilter(filter);
                        }
                    }

                    // Apply filters
                    if (viewerFilters != null) {
                        for (ViewerFilter filter : viewerFilters) {
                            contentViewer.addFilter(filter);
                        }
                    }
                    // Apply sorter
                    if (viewerSorter != null) {
                        contentViewer.setSorter(viewerSorter);
                    }
                    // Apply input
                    contentViewer.setInput(input);

                }
            }

            return contentViewer;
        }
    }

    /**
     * ViewerFilter based on selected list of XPDL resources. The filter is only
     * supposed to show the Selected Projects , Process Packages and the Xpdl
     * resources and should exclude all other stuff in tree.
     * 
     * @author aprasad
     * @since 05-Jun-2014
     */
    class SelectedXpdlFilter extends ViewerFilter {

        Collection<IResource> inclusions;

        /**
         * Constructor.
         * 
         * @param inclusions
         *            Collection of resources to be included.
         */
        public SelectedXpdlFilter(Collection<IResource> inclusions) {

            this.inclusions = inclusions;
        }

        /**
         * Only show resource upto the Xpdl files.
         * 
         * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param parentElement
         * @param element
         * @return
         */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {

            if (element instanceof IAdaptable) {

                IResource res =
                        (IResource) ((IAdaptable) element)
                                .getAdapter(IResource.class);

                if (res instanceof IFile) {
                    return inclusions.contains(element);

                } else {
                    return isParent(res);
                }
            }
            return false;

        }

        /**
         * @param element
         * @return
         */
        private boolean isParent(Object element) {

            for (IResource resource : inclusions) {

                IContainer parent = resource.getParent();

                do {
                    if (element.equals(parent)) {
                        return true;
                    }
                    parent = parent.getParent();

                } while (parent != null);

            }
            return false;
        }
    }

    /**
     * Returns specific help page context ID. Extending class can override this
     * method to provide specific context id.
     * 
     * @Note If not overridden, default implementation returns <code>null</code>
     * 
     * @return Help context id if available, otherwise <code>null</code>
     */
    protected String getHelpContextId() {
        return null;
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#performHelp() Extednig classes
     */
    @SuppressWarnings("restriction")
    @Override
    public void performHelp() {

        /*
         * XPD-6493: Saket: A wizard page needs to override
         * DialogPage.performHelp() method in order to make the 'Help' button
         * function correctly.
         */

        String contextId = getHelpContextId();

        if (contextId == null) {

            contextId = IWorkbenchHelpContextIds.MISSING;
        }

        PlatformUI.getWorkbench().getHelpSystem().displayHelp(contextId);

    }
}
