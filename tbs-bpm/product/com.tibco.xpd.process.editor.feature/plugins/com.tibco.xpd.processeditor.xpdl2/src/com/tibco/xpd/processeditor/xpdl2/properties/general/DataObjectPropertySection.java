/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.SashDividedNamedElementSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2DataObjectAdapter;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.DataObject;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author aallway
 * 
 */
public class DataObjectPropertySection extends SashDividedNamedElementSection {

    /**
     * Resource picker for the data object external reference
     * 
     * @author aallway
     */
    private class DataObjectExtRefPicker extends BaseObjectPicker {

        private final IResource resBeingEdited;

        /**
         * Constructor
         * 
         * @param parent
         *            Shell
         * @param resBeingEdited
         *            The resource being edited. This resource will be excluded
         *            from the picker.
         */
        public DataObjectExtRefPicker(Shell parent, IResource resBeingEdited) {
            super(parent);
            this.resBeingEdited = resBeingEdited;
            setAllowMultiple(false);
            setTitle(Messages.DataObjectPropertySection_ExtRefDialog_title);
            setMessage(Messages.DataObjectPropertySection_ExtRefDialog_message);
            setEmptyListMessage(Messages.DataObjectPropertySection_NoResourcesFound_message);

            /*
             * Add filter to exclude system folders/files, the file being edited
             * and EObjects
             */
            addFilter(new ViewerFilter() {

                @Override
                public boolean select(Viewer viewer, Object parentElement,
                        Object element) {
                    boolean select = true;

                    // Allow special folders
                    if (element instanceof EObject
                            && !(element instanceof SpecialFolder)) {
                        select = false;
                    } else {
                        IResource res = null;

                        if (element instanceof IResource) {
                            res = (IResource) element;
                        } else if (element instanceof IAdaptable) {
                            res =
                                    (IResource) ((IAdaptable) element)
                                            .getAdapter(IResource.class);
                        }

                        if (res != null) {

                            if (res
                                    .equals(DataObjectExtRefPicker.this.resBeingEdited)) {
                                // Don't show the file being edited
                                select = false;
                            } else {
                                // Any resource that starts with a '.' is a
                                // system resource so don't select
                                select = !res.getName().startsWith("."); //$NON-NLS-1$
                            }
                        }
                    }

                    return select;
                }

            });

            // Set the sorter - special folders should be shown before standard
            // folders
            setSorter(new ViewerSorter() {

                private int SPECIAL_FOLDER = 0;

                private int FOLDER = 1;

                private int OTHER = 2;

                @Override
                public int category(Object element) {
                    int cat = OTHER;

                    if (element instanceof SpecialFolder) {
                        cat = SPECIAL_FOLDER;
                    } else if (element instanceof IFolder) {
                        cat = FOLDER;
                    }

                    return cat;
                }
            });

            // Set the validator - only allow files to be selected
            setValidator(new ISelectionStatusValidator() {

                public IStatus validate(Object[] selection) {

                    if (selection != null && selection.length == 1) {
                        boolean valid = false;

                        if (selection[0] instanceof IFile) {
                            valid = true;
                        } else if (selection[0] instanceof IAdaptable) {
                            valid =
                                    (((IAdaptable) selection[0])
                                            .getAdapter(IFile.class) != null);
                        }

                        if (valid) {
                            return new Status(IStatus.OK,
                                    Xpdl2ProcessEditorPlugin.ID, IStatus.OK,
                                    "", //$NON-NLS-1$
                                    null);
                        }
                    }

                    return new Status(
                            IStatus.ERROR,
                            Xpdl2ProcessEditorPlugin.ID,
                            IStatus.OK,
                            Messages.DataObjectPropertySection_ExtRefDialog_message,
                            null);
                }

            });
        }
    }

    public static final int DATAOBJECT_WIDTH_SIZE = 32;

    public static final int DATAOBJECT_HEIGHT_SIZE = 38;

    public static final int DATAOBJECT_CORNERBEND_SIZE = 8;

    private static final String EXTREF_HYPERLINK_HREF =
            "ExternalReference.HyperLink"; //$NON-NLS-1$

    private Text stateText;

    private Text descText;

    private FormText extRefHyper;

    private Text extRefText;

    private Button extRefBrowse;

    private Artifact inputOnLastRefresh = null;

    DataObjectPropertyTable dataObjectPropertyTable;

    private Composite detailsRoot;

    public DataObjectPropertySection() {
        // Main selection element for DataObject is xpdl2 Artifact
        super(Xpdl2Package.eINSTANCE.getArtifact(), Xpdl2ProcessEditorPlugin.ID
                + ".dataobjectproperties"); //$NON-NLS-1$

    }

    /**
     * @see com.tibco.xpd.ui.properties.SashDividedEObjectSection#createDetailsSection(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Composite doCreateDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        ScrolledComposite scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        Composite root = internalCreateDetails(toolkit, scrolledContainer);
        scrolledContainer.setContent(root);

        scrolledContainer.setMinSize(root.computeSize(200, SWT.DEFAULT));

        return scrolledContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.SashDividedEObjectSection#createGeneralSection(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Composite doCreateGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        ScrolledComposite scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);
        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        Composite root =
                internalCreateGeneralSection(toolkit, scrolledContainer);

        scrolledContainer.setContent(root);

        scrolledContainer
                .setMinSize(root.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return scrolledContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetDetailsCommand(Object obj) {
        CompoundCommand cmd = null;

        Artifact artifact = getArtifact();

        EditingDomain editingDomain = getEditingDomain();

        if (artifact != null && editingDomain != null) {
            cmd = new CompoundCommand();

            // Update the name in the model
            if (obj == stateText) {
                String curr =
                        getDataObject() != null ? getDataObject().getState()
                                : null;

                if (curr == null) {
                    curr = ""; //$NON-NLS-1$
                }

                String newText = stateText.getText();
                if (newText == null) {
                    newText = ""; //$NON-NLS-1$
                }

                if (true || !curr.equals(newText)) {

                    cmd
                            .setLabel(Messages.DataObjectPropertySection_SetDataObjectState_menu);

                    DataObject dataObj =
                            Xpdl2DataObjectAdapter
                                    .getOrCreateDataObject(editingDomain,
                                            cmd,
                                            getArtifact());

                    cmd.append(SetCommand.create(editingDomain,
                            dataObj,
                            Xpdl2Package.eINSTANCE.getDataObject_State(),
                            newText));
                }
            } else if (obj == descText) {
                String curr =
                        Xpdl2DataObjectAdapter
                                .getXpdExtDataObjectAttributes(getDataObject()) != null ? Xpdl2DataObjectAdapter
                                .getXpdExtDataObjectAttributes(getDataObject())
                                .getDescription()
                                : null;

                if (curr == null) {
                    curr = ""; //$NON-NLS-1$
                }

                String newText = descText.getText();
                if (newText == null) {
                    newText = ""; //$NON-NLS-1$
                }

                if (true || !curr.equals(newText)) {

                    cmd
                            .setLabel(Messages.DataObjectPropertySection_SetDataObjectDesc_menu);

                    XpdExtDataObjectAttributes extData =
                            Xpdl2DataObjectAdapter
                                    .getOrCreateXpdExtDataObjectAttributes(editingDomain,
                                            cmd,
                                            getArtifact());

                    cmd
                            .append(SetCommand
                                    .create(editingDomain,
                                            extData,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getXpdExtDataObjectAttributes_Description(),
                                            newText));
                }
            } else if (obj.equals(extRefText)) {
                cmd.setLabel(Messages.DataObjectPropertySection_SetExtRef_menu);
                cmd.append(Xpdl2DataObjectAdapter
                        .getSetExternalReferenceCommand(editingDomain,
                                extRefText.getText(),
                                getArtifact()));

            } else if (obj.equals(EXTREF_HYPERLINK_HREF)
                    || obj.equals(extRefBrowse)) {
                cmd.setLabel(Messages.DataObjectPropertySection_SetExtRef_menu);
                cmd.append(browseExternalReference(editingDomain, obj
                        .equals(EXTREF_HYPERLINK_HREF)));
            }
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefreshDetailsSection() {
        // If controls disposed then unregister adapter
        if (!stateText.isDisposed()) {
            Artifact artifact = getArtifact();
            DataObject dataObject = getDataObject();
            if (artifact != null) {

                // Update name
                updateText(stateText, (dataObject != null ? dataObject
                        .getState() : null));

                XpdExtDataObjectAttributes extData =
                        Xpdl2DataObjectAdapter
                                .getXpdExtDataObjectAttributes(getDataObject());

                updateText(descText, (extData != null ? extData
                        .getDescription() : null));

                updateText(extRefText, getExternalReference());

                // Add table items for user attributes.
                if (inputOnLastRefresh != getArtifact()) {
                    dataObjectPropertyTable.getViewer().setInput(getArtifact());
                }

                dataObjectPropertyTable.getViewer().refresh();
            }
        }

        inputOnLastRefresh = getArtifact();
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

    @Override
    public void layoutUpdated() {
        super.layoutUpdated();
    }

    /**
     * Override to further filter base selection of Artifact looking for
     * DataObject Artifacts
     * 
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            // It's an artifact, is it a data object type
            Artifact art = (Artifact) getBaseSelectObject(toTest);
            if (ArtifactType.DATA_OBJECT_LITERAL.equals(art.getArtifactType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
    }

    private Command browseExternalReference(EditingDomain editingDomain,
            boolean isHyperlink) {

        boolean openedRef = false;
        String extRef = getExternalReference();
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getArtifact());

        if (wc != null && !wc.getEclipseResources().isEmpty()) {
            IProject project = wc.getEclipseResources().get(0).getProject();
            IFile extRefFile = null;

            if (extRef != null && extRef.trim().length() > 0) {
                IResource resource = project.findMember(extRef.trim());

                if (resource instanceof IFile) {
                    // Get the file set as the external reference
                    extRefFile = (IFile) resource;
                }
            }

            if (isHyperlink && extRefFile != null) {
                IWorkbenchPage activePage =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();

                try {
                    // Open the file in it's editor
                    IDE.openEditor(activePage, extRefFile, true);

                } catch (PartInitException e) {
                    IStatus status =
                            new Status(IStatus.ERROR,
                                    Xpdl2ProcessEditorPlugin.ID, IStatus.OK, e
                                            .getLocalizedMessage(), e);

                    ErrorDialog
                            .openError(getPart().getSite().getShell(),
                                    Messages.DataObjectPropertySection_FailedToOpen_title,
                                    String
                                            .format(Messages.DataObjectPropertySection_FailedToOpen_message,
                                                    extRefFile
                                                            .getProjectRelativePath()
                                                            .toString()),
                                    status);

                } finally {
                    openedRef = true;
                }
            }

            if (!openedRef) {
                // Show the resource picker
                DataObjectExtRefPicker picker =
                        new DataObjectExtRefPicker(getSite().getShell(), wc
                                .getEclipseResources().get(0));

                picker.setInput(project);

                // If a valid file has already been set as the external
                // ref then set it as the initial selection
                if (extRefFile != null) {
                    picker.setInitialSelection(extRefFile);
                }

                if (picker.open() == DataObjectExtRefPicker.OK) {
                    /*
                     * If a file is selected then update the external ref. with
                     * the project relative path to the file and update the data
                     * object
                     */
                    Object firstResult = picker.getFirstResult();
                    IFile selectedFile = null;

                    if (firstResult instanceof IFile) {
                        selectedFile = (IFile) firstResult;
                    } else if (firstResult instanceof IAdaptable) {
                        selectedFile =
                                (IFile) ((IAdaptable) firstResult)
                                        .getAdapter(IFile.class);
                    }

                    if (selectedFile != null) {
                        extRef =
                                selectedFile.getProjectRelativePath()
                                        .toString();

                        return (Xpdl2DataObjectAdapter
                                .getSetExternalReferenceCommand(editingDomain,
                                        extRef,
                                        getArtifact()));
                    }
                }
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    private String getExternalReference() {
        String extRef = null;

        XpdExtDataObjectAttributes extData =
                Xpdl2DataObjectAdapter
                        .getXpdExtDataObjectAttributes(getDataObject());

        if (extData != null) {
            extRef = extData.getExternalReference();
        }

        if (extRef == null) {
            extRef = ""; //$NON-NLS-1$
        }
        return extRef;
    }

    /**
     * @param toolkit
     * @param parent
     * @return
     */
    private Composite internalCreateDetails(XpdFormToolkit toolkit,
            Composite parent) {
        detailsRoot = toolkit.createComposite(parent);

        GridLayout rootLayout = new GridLayout(1, false);
        // rootLayout.verticalSpacing = 10;
        detailsRoot.setLayout(rootLayout);

        // External object reference...
        Composite extRefCmp = toolkit.createComposite(detailsRoot);
        extRefCmp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridLayout extRefLayout = new GridLayout(3, false);
        extRefLayout.marginHeight = 0;
        extRefLayout.marginWidth = 0;
        extRefLayout.marginBottom = 5;
        extRefCmp.setLayout(extRefLayout);

        extRefHyper =
                toolkit.createFormText(extRefCmp, true, "DataObjectExtRef"); //$NON-NLS-1$
        String hLinktext = "<text><p><a href='%s'>%s</a>:</p></text>"; //$NON-NLS-1$
        extRefHyper.setText(String.format(hLinktext,
                EXTREF_HYPERLINK_HREF,
                Messages.DataObjectPropertySection_ExtRef_label), true, false);
        extRefHyper.setLayoutData(new GridData());
        manageControl(extRefHyper);

        extRefText =
                toolkit.createText(extRefCmp,
                        "", SWT.BORDER, "DataObjectExtRef"); //$NON-NLS-1$ //$NON-NLS-2$
        extRefText.setData("name", "textDataObjectExtRef"); //$NON-NLS-1$ //$NON-NLS-2$
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.widthHint = 60;
        extRefText.setLayoutData(gridData);
        manageControl(extRefText);

        extRefBrowse =
                toolkit.createButton(extRefCmp,
                        "...", SWT.PUSH, "DataObjectBrowseExtRef"); //$NON-NLS-1$ //$NON-NLS-2$
        extRefBrowse.setData("name", "buttonDataObjectBrowseExtRef"); //$NON-NLS-1$ //$NON-NLS-2$
        extRefBrowse.setLayoutData(new GridData());
        manageControl(extRefBrowse);

        Composite root = toolkit.createComposite(detailsRoot);
        root.setLayout(new GridLayout());
        root.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true,
                true));

        dataObjectPropertyTable =
                new DataObjectPropertyTable(root, toolkit, getEditingDomain());
        dataObjectPropertyTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        return detailsRoot;
    }

    /**
     * @param toolkit
     * @param parent
     * @return
     */
    private Composite internalCreateGeneralSection(XpdFormToolkit toolkit,
            Composite parent) {
        Composite root = toolkit.createComposite(parent);

        GridLayout rootLayout = new GridLayout(2, false);
        rootLayout.verticalSpacing = 10;
        root.setLayout(rootLayout);

        createLabel(root,
                toolkit,
                Messages.DataObjectPropertySection_State_label);
        stateText = toolkit.createText(root, "", "DataObjectState"); //$NON-NLS-1$ //$NON-NLS-2$
        stateText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(stateText);

        Label desc =
                toolkit.createLabel(root,
                        Messages.DataObjectPropertySection_Description_label);
        desc.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        descText =
                toolkit.createText(root,
                        "", SWT.MULTI | SWT.WRAP, "DataObjectDescription"); //$NON-NLS-1$ //$NON-NLS-2$
        descText.setData("name", "textDataObjectDescription"); //$NON-NLS-1$ //$NON-NLS-2$
        descText.setLayoutData(new GridData(GridData.FILL_BOTH));
        manageControl(descText);

        return root;
    }

    @Override
    protected void doRefreshImplementationSection() {
    }
}
