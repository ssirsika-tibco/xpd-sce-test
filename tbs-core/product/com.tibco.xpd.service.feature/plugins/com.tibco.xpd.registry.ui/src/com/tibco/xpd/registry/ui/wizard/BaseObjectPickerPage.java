/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorContentService;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Common root for ObjectPickers inside wizard pages.
 * 
 * @author glewis
 */
public class BaseObjectPickerPage extends AbstractXpdWizardPage {

    private String emptyListMessage =
            Messages.BaseObjectPicker_noEntries_message;

    /*
     * XPD-4720: to avoid getting ClassCastException when
     * org.eclipse.jface.viewers.TreeViewer is cast to
     * org.eclipse.ui.navigator.CommonViewer (This started happening after we
     * have moved to new eclipse 3.7)
     */
    private static final String PICKER_PAGE_VIEWER_ID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    private CommonViewer treeViewer = null;

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

    private Object[] result;

    public BaseObjectPickerPage() {
        super(""); //$NON-NLS-1$
        setTitle(""); //$NON-NLS-1$
        setPageComplete(false);
    }

    public class EClassStatusValidator implements ISelectionStatusValidator {

        private final List eClasses;

        private boolean allowMultiple;

        public EClassStatusValidator(EClass eClass) {
            this(Collections.singletonList(eClass));
        }

        public EClassStatusValidator(List eClasses) {
            this(eClasses, false);
        }

        public EClassStatusValidator(List eClasses, boolean allowMultiple) {
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
                        for (Iterator iter = eClasses.iterator(); iter
                                .hasNext();) {
                            EClass eClass = (EClass) iter.next();
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
     * Set the input for the viewer
     * 
     * @param input
     */
    public void setInput(Object input) {
        this.input = input;
        if (treeViewer != null) {
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    treeViewer.setInput(BaseObjectPickerPage.this.input);
                    // Check if tree is empty
                    treeIsEmpty = (treeViewer.getTree().getItemCount() == 0);
                    Tree treeWidget = treeViewer.getTree();
                    treeWidget.setEnabled(!treeIsEmpty);
                }

            });
        }
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
     * and TreeViewer.ALL_LEVELS for all.
     * 
     * @param expandLevel
     */
    public void setExpandLevel(int expandLevel) {
        setExpandLevel(null, expandLevel);
    }

    /**
     * Set the initial expand level for the tree control. 0 for none (default)
     * and TreeViewer.ALL_LEVELS for all.
     * <p>
     * If expandFromObject != null then the parent objects of it are is expanded
     * to make it visible and then the number of levels given by expand level
     * are expanded underneath it.
     * 
     * @param expandFromObject
     *            Object to perform expansion from.
     * @param expandLevel
     *            Number of levels to expand (or TreeViewer.ALL_LEVELS)
     * 
     */
    public void setExpandLevel(Object expandFromObject, int expandLevel) {
        this.expandLevel = expandLevel;
        this.expandFromObject = expandFromObject;
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
    public void dispose() {
        // Dispose off the navigator service and remove the tree viewer
        if (navigatorService != null) {
            navigatorService.dispose();
            navigatorService = null;
        }

        if (treeViewer != null) {
            treeViewer = null;
        }
        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#create()
     */
    public void create() {
        BusyIndicator.showWhile(null, new Runnable() {
            @Override
            public void run() {

                if (expandFromObject != null) {
                    treeViewer.expandToLevel(expandFromObject, expandLevel);
                } else {
                    treeViewer.expandToLevel(expandLevel);
                }

                // If no selection set then set the first thing in list.
                TreeItem item = null;
                if (treeViewer.getTree().getItemCount() > 0) {
                    item = treeViewer.getTree().getItem(0);
                }
                if (item != null && item.getData() != null) {
                    treeViewer.setSelection(new StructuredSelection(item
                            .getData()),
                            true);
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
        return treeViewer;
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

        setPageComplete(false);
        if (status != null && status.getSeverity() == IStatus.OK) {
            setPageComplete(true);
        }
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

        if (treeViewer == null) {
            /*
             * XPD-4720: to avoid getting ClassCastException when
             * org.eclipse.jface.viewers.TreeViewer is cast to
             * org.eclipse.ui.navigator.CommonViewer (This started happening
             * after we have moved to new eclipse 3.7)
             */
            treeViewer =
                    new CommonViewer(PICKER_PAGE_VIEWER_ID, composite,
                            SWT.BORDER
                                    | (allowMultiple ? SWT.MULTI : SWT.SINGLE));

            // Apply input
            treeViewer.setInput(input);

            // Listen to tree selection
            treeViewer
                    .addSelectionChangedListener(new ISelectionChangedListener() {

                        @Override
                        public void selectionChanged(SelectionChangedEvent event) {
                            setResult(((IStructuredSelection) event
                                    .getSelection()).toList());
                            updateOKStatus();
                        }

                    });

            treeViewer.addDoubleClickListener(new IDoubleClickListener() {
                @Override
                public void doubleClick(DoubleClickEvent event) {
                    updateOKStatus();
                }
            });

            // Apply filters
            if (viewerFilters != null) {
                for (ViewerFilter filter : viewerFilters) {
                    treeViewer.addFilter(filter);
                }
            }
            // Apply sorter
            if (viewerSorter != null) {
                treeViewer.setSorter(viewerSorter);
            }
        }

        return treeViewer;
    }

    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

        /*
         * XPD-4720: to avoid getting ClassCastException when
         * org.eclipse.jface.viewers.TreeViewer is cast to
         * org.eclipse.ui.navigator.CommonViewer (This started happening after
         * we have moved to new eclipse 3.7)
         */
        CommonViewer treeViewer = createTreeViewer(composite);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = convertWidthInCharsToPixels(fWidth);
        data.heightHint = convertHeightInCharsToPixels(fHeight);

        Tree treeWidget = treeViewer.getTree();
        treeWidget.setLayoutData(data);
        treeWidget.setFont(parent.getFont());

        // If the tree is empty then disable the controls
        // if (treeIsEmpty) {
        // treeWidget.setEnabled(false);
        // }
        setControl(composite);
    }

    /**
     * Returns the list of selections made by the user
     * 
     * @return the array of selected elements
     */
    public Object[] getResult() {
        return result;
    }

    /**
     * Set the selections made by the user
     * 
     * @param newResult
     *            list of selected elements
     */
    protected void setResult(List newResult) {
        if (newResult == null) {
            result = null;
        } else {
            result = new Object[newResult.size()];
            newResult.toArray(result);
        }
    }

    // MR 35822 - begin
    public void setSelection(Object[] element) {
        if (element != null && getTreeViewer() != null) {
            TreePath tp = new TreePath(element);
            getTreeViewer().setSelection(new TreeSelection(tp));
        }
    }
    // MR 35822 - end

}