/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.navigator.pickers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;
import org.eclipse.ui.navigator.resources.ProjectExplorer;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectExclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Base for workspace resource pickers. This picker will show a 'project
 * explorer' view that will allow selection of any object that appears in the
 * project explorer. BaseObjectPicker can be used as is or subclassed if a
 * picker for a specific object is required.
 * <p>
 * The following shows a simple subclass of this class to create an XPD nature
 * project picker:
 * 
 * <code><pre>
 * private class ProjectPicker extends BaseObjectPicker {
 * 
 *     public ProjectPicker(Shell parent) {
 *         super(parent);
 *         setTitle("Select Project");
 *         setMessage("Select a project");
 * 
 *         addFilter(new XpdNatureProjectsOnly());
 *         addFilter(new TypedViewerFilter(new Class&lt;?&gt;[] { IProject.class }));
 *     }
 * }
 * </pre></code>
 * </p>
 * <p>
 * The following example shows a picker for a process (xpdl) file:
 * 
 * <code><pre>
 * private class PackagePicker extends BaseObjectPicker {
 * 
 *        public PackagePicker(Shell parent) {
 *          super(parent);
 *          setTitle(Messages.SimulationLaunchMainTab_packagePicker_title);
 *          setMessage(Messages.SimulationLaunchMainTab_packagePicker_shortdesc);
 * 
 *          setValidator(new TypedElementSelectionValidator(
 *                new Class[] { IFile.class }, false));
 * 
 *          addFilter(new SpecialFoldersOnlyFilter(
 *                Collections
 *                      .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND)));
 * 
 *           addFilter(new FileExtensionInclusionFilter(Collections
 *                 .singleton("xpdl")));
 *           addFilter(new NoFileContentFilter());
 *      }
 *  }
 * </pre></code>
 * </p>
 * <p>
 * The following filters are available for convenience - these filters can be
 * combined to achieve the picker required:
 * 
 * <ul>
 * <li>{@link EObjectExclusionFilter} - Excludes <code>EObject</code>s that are
 * instances of the given <code>EClass</code>es.</li>
 * <li>{@link EObjectFilter} - include only the given <code>EObject</code>.</li>
 * <li>{@link EObjectInclusionFilter} - Include all <code>EObject</code>s that
 * are instances of the given <code>EClass</code>es</li>
 * <li>{@link FileExtensionInclusionFilter} - Include all <code>IFile</code>s
 * with the given file extension.</li>
 * <li>{@link NoFileContentFilter} - Exclude all (model) content of a file.</li>
 * <li>{@link SpecialFoldersOnlyFilter} - Include <code>SpecialFolder</code>s of
 * the given kind(s).</li>
 * <li>{@link XpdNatureProjectsOnly} - Include XPD nature projects only.</li>
 * <li>
 * {@link com.tibco.xpd.ui.projectexplorer.viewerfilters.TypedViewerFilter
 * TypedViewerFilter} - Include objects that are instances of the given Classes
 * (optionally explicit objects to exclude can also be provided).</li>
 * </ul>
 * </p>
 * <p>
 * HINT: If the parent of an <code>IResource</code> may potentially be a
 * <code>SpecialFolder</code> then use {@link #getParent(IResource)} to get the
 * parent of this resource - this will ensure a SpecialFolder will be returned
 * if it is the parent, otherwise the resource's <code>IContainer</code> parent
 * will be returned.
 * </p>
 * 
 * @author njpatel
 */
public class BaseObjectPicker extends SelectionStatusDialog {

    private String emptyListMessage =
            Messages.BaseObjectPicker_noEntries_message;

    /*
     * XPD-5482: to avoid getting ClassCastException when
     * org.eclipse.jface.viewers.TreeViewer is cast to
     * org.eclipse.ui.navigator.CommonViewer (This started happening after we
     * have moved to new eclipse 3.7)
     */
    private CommonViewer commonViewer = null;

    private int fWidth = 60;

    private int fHeight = 18;

    private Object input = null;

    private boolean allowMultiple = false;

    private int expandLevel = 0;

    private Object expandFromObject = null;

    private boolean noSelectionIsOK = false;

    private String invalidSelectionMessage = ""; //$NON-NLS-1$

    private ISelectionStatusValidator validator = null;

    private List<ViewerFilter> viewerFilters = null;

    private ViewerSorter viewerSorter = null;

    private INavigatorContentService navigatorService = null;

    private boolean treeIsEmpty = false;

    /** Return value for CLEAR button pressed */
    public static final int CLEAR = IDialogConstants.CLIENT_ID + 1;

    private Button clearBtn;

    private boolean clearEnabled = false;

    public class EClassStatusValidator implements ISelectionStatusValidator {

        private final List<EClass> eClasses;

        private boolean allowMultiple;

        public EClassStatusValidator(EClass eClass) {
            this(Collections.singletonList(eClass));
        }

        public EClassStatusValidator(List<EClass> eClasses) {
            this(eClasses, false);
        }

        public EClassStatusValidator(List<EClass> eClasses,
                boolean allowMultiple) {
            this.eClasses = eClasses;
            this.allowMultiple = allowMultiple;
        }

        public void setAllowMultiple(boolean allowMultiple) {
            this.allowMultiple = allowMultiple;
        }

        protected boolean extraValidation(EObject eObj) {
            return true;
        }

        @Override
        public IStatus validate(Object[] selection) {
            if (selection != null && (selection.length == 1 || allowMultiple)) {
                boolean valid = selection.length > 0;
                EObject eo = null;
                for (int i = 0; i < selection.length; i++) {
                    if (selection[i] instanceof EObject) {
                        eo = (EObject) selection[i];
                    } else if (selection[i] instanceof IAdaptable) {
                        eo =
                                (EObject) ((IAdaptable) selection[i])
                                        .getAdapter(EObject.class);
                    }
                    if (eo != null) {
                        boolean cl = false;
                        for (EClass eClass : eClasses) {
                            if (eClass.isSuperTypeOf(eo.eClass())) {
                                cl = true;
                                break;
                            }
                        }

                        if (cl) {
                            cl = extraValidation(eo);
                        }

                        if (!cl) {
                            valid = false;
                            break;
                        }
                    } else {
                        valid = false;
                    }
                }
                if (valid) {
                    String msg;
                    if (selection.length == 1) {
                        if (eo != null) {
                            msg =
                                    String.format(Messages.BaseObjectPicker_selectedObj_shortdesc,
                                            WorkingCopyUtil.getText(eo));
                        } else {
                            msg = String.valueOf(selection[0]);
                        }
                    } else {
                        msg =
                                Messages.BaseObjectPicker_multipleSelection_shortdesc;
                    }
                    return new Status(Status.OK, XpdResourcesUIActivator.ID,
                            Status.OK, msg, null);
                }
            }

            return new Status(Status.ERROR, XpdResourcesUIActivator.ID,
                    Status.ERROR, invalidSelectionMessage, null);
        }
    }

    /**
     * Common object picker
     * 
     * @param parent
     */
    public BaseObjectPicker(Shell parent) {
        super(parent);
        setHelpAvailable(false);
        setResult(new ArrayList<Object>(0));
        setStatusLineAboveButtons(true);
    }

    /**
     * Set the input for the viewer
     * 
     * @param input
     */
    public void setInput(Object input) {
        this.input = input;
    }

    /**
     * Allow multiple selection in the tree
     * 
     * @param allowMultiple
     *            Set to <code>true</code> to allow multiple selections
     */
    public void setAllowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;

        if (this.validator instanceof EClassStatusValidator) {
            ((EClassStatusValidator) this.validator)
                    .setAllowMultiple(allowMultiple);
        }

    }

    /**
     * Get the parent of the given resource. If this resource is contained in a
     * special folder then the <code>SpecialFolder</code> will be returned,
     * otherwise its {@link IContainer} will be returned.
     * 
     * @param resource
     *            get parent of this resource
     * @return <code>SpecialFolder</code> or <code>IContainer</code> parent,
     *         <code>null</code> if no parent can be computed.
     * 
     * @see SpecialFolderUtil#getParent(IResource)
     * 
     * @since 3.1
     */
    public static Object getParent(IResource resource) {
        return SpecialFolderUtil.getParent(resource);
    }

    /**
     * Set whether having nothing selected is ok. (This way we can distinguish
     * between selection(s) removed and cancel).
     * 
     * @param noSelectionIsOK
     */
    public void setNoSelectionIsOK(boolean noSelectionIsOK) {
        this.noSelectionIsOK = noSelectionIsOK;
    }

    /**
     * Set the initial expand level for the tree control. 0 for none (default)
     * and CommonViewer.ALL_LEVELS for all.
     * 
     * @param expandLevel
     */
    public void setExpandLevel(int expandLevel) {
        setExpandLevel(null, expandLevel);
    }

    /**
     * Set the initial expand level for the tree control. 0 for none (default)
     * and CommonViewer.ALL_LEVELS for all.
     * <p>
     * If expandFromObject != null then the parent objects of it are is expanded
     * to make it visible and then the number of levels given by expand level
     * are expanded underneath it.
     * 
     * @param expandFromObject
     *            Object to perform expansion from.
     * @param expandLevel
     *            Number of levels to expand (or CommonViewer.ALL_LEVELS)
     * 
     */
    public void setExpandLevel(Object expandFromObject, int expandLevel) {
        this.expandLevel = expandLevel;
        this.expandFromObject = expandFromObject;
    }

    /**
     * Set the initial selection in the tree
     * 
     * @param selection
     */
    public void setInitialSelection(Object selection) {
        setInitialSelections(new Object[] { selection });
    }

    /**
     * Set the invalid selection messsage
     * 
     * @param invalidSelectionMessage
     */
    public void setInvalidSelectionMessage(String invalidSelectionMessage) {
        this.invalidSelectionMessage = invalidSelectionMessage;
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
     * Add the tree sorter
     * 
     * @param sorter
     */
    public void setSorter(ViewerSorter sorter) {
        this.viewerSorter = sorter;
    }

    /**
     * Set the validator for the selection in the tree
     * 
     * @param validator
     */
    public void setValidator(ISelectionStatusValidator validator) {
        this.validator = validator;
    }

    public ISelectionStatusValidator getValidator() {
        return validator;
    }

    /**
     * Set the empty tree message
     * 
     * @param msg
     */
    public void setEmptyListMessage(String msg) {
        emptyListMessage = msg;
    }

    /**
     * Sets the size of the tree in unit of characters.
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

    @Override
    public boolean close() {
        // Dispose off the navigator service and remove the tree viewer
        if (navigatorService != null) {
            navigatorService.dispose();
            navigatorService = null;
        }

        if (commonViewer != null) {
            commonViewer = null;
        }
        return super.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#create()
     */
    @Override
    public void create() {
        BusyIndicator.showWhile(null, new Runnable() {
            @Override
            public void run() {
                BaseObjectPicker.super.create();

                if (expandFromObject != null) {
                    commonViewer.expandToLevel(expandFromObject, expandLevel);
                } else {
                    commonViewer.expandToLevel(expandLevel);
                }

                // Make initial selection of elements provided
                List<?> initialElementSelections =
                        getInitialElementSelections();
                if (initialElementSelections != null
                        && initialElementSelections.size() > 0) {
                    commonViewer.setSelection(new StructuredSelection(
                            initialElementSelections), true);
                } else {
                    // If no selection set then set the first thing in list.
                    TreeItem item = null;
                    if (commonViewer.getTree().getItemCount() > 0) {
                        item = commonViewer.getTree().getItem(0);
                    }
                    if (item != null && item.getData() != null) {
                        commonViewer.setSelection(new StructuredSelection(item
                                .getData()),
                                true);
                    }
                }
                updateOKStatus();

            }
        });
    }

    /**
     * Returns the tree viewer.
     * 
     * @return the tree viewer
     */
    protected TreeViewer getTreeViewer() {
        return commonViewer;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        // Create a message and tree view area
        Label messageLabel = createMessageArea(composite);
        CommonViewer commonViewer = createTreeViewer(composite);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = convertWidthInCharsToPixels(fWidth);
        data.heightHint = convertHeightInCharsToPixels(fHeight);

        Tree treeWidget = commonViewer.getTree();
        treeWidget.setLayoutData(data);
        treeWidget.setFont(parent.getFont());

        // If the tree is empty then disable the controls
        if (treeIsEmpty) {
            messageLabel.setEnabled(false);
            treeWidget.setEnabled(false);

            updateStatus(new Status(Status.ERROR, XpdResourcesUIActivator.ID,
                    Status.ERROR, emptyListMessage, null));

        }

        return composite;
    }

    @Override
    protected void computeResult() {
        setResult(((IStructuredSelection) commonViewer.getSelection()).toList());
    }

    /**
     * Validate the receiver and update the ok status.
     * 
     */
    protected void updateOKStatus() {
        IStatus status = null;

        if (!treeIsEmpty) {
            if (validator != null) {

                Object[] res2 = getResult();
                if (noSelectionIsOK && (res2 == null || res2.length == 0)) {
                    status =
                            new Status(IStatus.OK, XpdResourcesUIActivator.ID,
                                    IStatus.OK, "", //$NON-NLS-1$
                                    null);
                } else {
                    status = validator.validate(res2);
                }

            } else {
                status =
                        new Status(IStatus.OK, XpdResourcesUIActivator.ID,
                                IStatus.OK, "", //$NON-NLS-1$
                                null);
            }
        } else {
            status =
                    new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                            IStatus.ERROR, emptyListMessage, null);
        }
        if (status != null)
            updateStatus(status);
    }

    /**
     * Create the tree viewer. This will use the Common Navigator service for
     * the content and label provider. All registered filters and sorter will be
     * applied.
     * 
     * @param composite
     * @return
     */
    private CommonViewer createTreeViewer(Composite composite) {

        if (commonViewer == null) {
            commonViewer =
                    new CommonViewer(ProjectExplorer.VIEW_ID, composite,
                            SWT.BORDER
                                    | (allowMultiple ? SWT.MULTI : SWT.SINGLE));
            // Get the navigator content service
            NavigatorContentServiceFactory fact =
                    NavigatorContentServiceFactory.INSTANCE;
            navigatorService =
                    fact.createContentService(ProjectExplorer.VIEW_ID,
                            commonViewer);

            if (navigatorService != null) {

                /*
                 * Deactivate the Java Element content service in the picker as
                 * it can cause undesirable effects. One commom problem is when
                 * a initial selection is made - this causes a
                 * CyclicPathException when it comes to finding the parents of
                 * the selected object
                 */
                INavigatorActivationService service =
                        navigatorService.getActivationService();

                if (service != null) {
                    service.deactivateExtensions(new String[] { "org.eclipse.jdt.java.ui.javaContent" }, false); //$NON-NLS-1$
                }

                // Set the content and label providers
                commonViewer.setContentProvider(navigatorService
                        .createCommonContentProvider());
                commonViewer.setLabelProvider(navigatorService
                        .createCommonLabelProvider());

                // Apply the navigator active filters
                ViewerFilter[] navigatorFilters =
                        navigatorService.getFilterService()
                                .getVisibleFilters(true);

                if (navigatorFilters != null) {
                    for (ViewerFilter filter : navigatorFilters) {
                        commonViewer.addFilter(filter);
                    }
                }

                // Apply filters
                if (viewerFilters != null) {
                    for (ViewerFilter filter : viewerFilters) {
                        commonViewer.addFilter(filter);
                    }
                }
                // Apply sorter
                if (viewerSorter != null) {
                    commonViewer.setSorter(viewerSorter);
                }
                // Apply input
                commonViewer.setInput(input);
                // Check if tree is empty
                treeIsEmpty = (commonViewer.getTree().getItemCount() == 0);

                // Listen to tree selection
                commonViewer
                        .addSelectionChangedListener(new ISelectionChangedListener() {

                            @Override
                            public void selectionChanged(
                                    SelectionChangedEvent event) {
                                // Update result and check status of input
                                setResult(((IStructuredSelection) event
                                        .getSelection()).toList());
                                updateOKStatus();
                            }

                        });

                commonViewer.addDoubleClickListener(new IDoubleClickListener() {
                    @Override
                    public void doubleClick(DoubleClickEvent event) {
                        updateOKStatus();
                        boolean okStatus = getOKButtonStatus();
                        if (okStatus) {
                            okPressed();
                        }
                    }
                });
            }
        }

        return commonViewer;
    }

    /**
     * Use enablement of OK button to determine whether selection is valid.
     * 
     * @return
     */
    protected boolean getOKButtonStatus() {
        Button okButton = getOkButton();
        if (okButton != null && !okButton.isDisposed()) {
            return okButton.isEnabled();
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.dialogs.SelectionDialog#createButtonsForButtonBar(org.
     * eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);

        if (isClearEnabled()) {
            clearBtn =
                    createButton(parent,
                            CLEAR,
                            Messages.BaseObjectPicker_clear_button,
                            false);
            clearBtn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    setReturnCode(CLEAR);
                    // Clear the result
                    setResult(Collections.EMPTY_LIST);
                    close();
                }
            });

        }
    }

    /**
     * @return the clearEnabled
     */
    public boolean isClearEnabled() {
        return clearEnabled;
    }

    /**
     * @param clearEnabled
     *            the clearEnabled to set
     */
    public void setClearEnabled(boolean clearEnabled) {
        this.clearEnabled = clearEnabled;
    }

}