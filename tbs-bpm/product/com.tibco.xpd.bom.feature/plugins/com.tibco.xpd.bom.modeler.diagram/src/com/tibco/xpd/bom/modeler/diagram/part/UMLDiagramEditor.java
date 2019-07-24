/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.Cursors;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.ResourceUndoContext;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.ui.palette.PaletteCustomizer;
import org.eclipse.gef.ui.parts.DomainEventDispatcher;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.parts.PaletteToolTransferDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.gef.ui.palette.customize.PaletteCustomizerEx;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.tibco.xpd.bom.fragments.dnd.BOMFragmentTransferDropTargetListener;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationSuperTypeNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimTypeSuperTypeNameLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.SuperClassNameLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMEditPartUtils;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMReferenceAware;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMStereoAwareCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMStereoAwareDiagramEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMStereoAwareLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.internal.dnd.LocalSelectionDropTargetListener;
import com.tibco.xpd.bom.modeler.diagram.part.custom.firstclassprofiles.FirstClassProfilePaletteFactory;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.ui.editor.IGotoObject;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.gmf.extensions.palette.SelectionToolExEx;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipEnabledDomainEventDispatcher;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.ui.IRefreshableTitle;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.resources.wc.WorkingCopySaveable;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;
import com.tibco.xpd.resources.wc.gmf.WorkingCopyDocumentProvider;

//Don't put any generated NOT here! The Class definition line is custom and removing
//the "generated" and "generated NOT" line ensures that this line is not regenerated
//but the contents of the Class IS. Putting a "generated NOT" at the top
//will have the affect of blocking the whole class from being regenerated.
//We don't want the generator to overwrite the following line because we are
//implementing our own custom interface IGoToObject.
public class UMLDiagramEditor extends DiagramDocumentEditor implements
        ISaveablesSource, IGotoMarker, IGotoObject, ISaveablePart2, IRefreshableTitle {

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor#isStatusLineOn()
     * 
     * @return
     */
    @Override
    protected boolean isStatusLineOn() {
        if (getGraphicalViewer() != null) {
            RootEditPart rep = getGraphicalViewer().getRootEditPart();
            if (rep instanceof DiagramRootEditPart) {
                DiagramRootEditPart root = (DiagramRootEditPart) rep;
                boolean statusLineIsOn =
                        ((IPreferenceStore) root.getPreferencesHint()
                                .getPreferenceStore())
                                .getBoolean(IPreferenceConstants.PREF_SHOW_STATUS_LINE);
                return statusLineIsOn;
            }
        }
        return false;
    }

    /**
     * @generated NOT
     */
    private final DiagramResourceListener rsl;

    /**
     * @generated
     */
    public static final String ID =
            "com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorID"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final String CONTEXT_ID =
            "com.tibco.xpd.bom.modeler.diagram.ui.diagramContext"; //$NON-NLS-1$

    private final PropertyChangeListener wcListener;

    private TransactionalEditingDomain editorEditingDomain;

    /**
     * @generated NOT
     */
    public UMLDiagramEditor() {
        super(true);
        rsl = new DiagramResourceListener();
        XpdResourcesPlugin.getDefault().getEditingDomain()
                .addResourceSetListener(rsl);

        wcListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName()
                        .equals(WorkingCopy.PROP_DEPENDENCY_CHANGED)) {
                    // Refresh diagram as dependency has changed
                    final GraphicalViewer viewer = getGraphicalViewer();
                    if (viewer != null
                            && !viewer.getEditPartRegistry().isEmpty()) {
                        getSite().getShell().getDisplay()
                                .asyncExec(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (viewer != null) {
                                            // Refresh all reference aware edit
                                            // parts
                                            Collection<?> parts =
                                                    viewer.getEditPartRegistry()
                                                            .values();

                                            if (parts != null) {
                                                for (Object part : parts) {
                                                    if (part instanceof EditPart
                                                            && part instanceof BOMReferenceAware) {
                                                        ((EditPart) part)
                                                                .refresh();
                                                    }
                                                }
                                            }

                                        }
                                    }
                                });
                    }
                }
            }
        };

    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (rsl != null) {
            XpdResourcesPlugin.getDefault().getEditingDomain()
                    .removeResourceSetListener(rsl);
        }
        super.dispose();
    }

    @Override
    public void createPartControl(Composite parent) {

        IDocumentProvider provider = getDocumentProvider();
        IStatus status = provider.getStatus(getEditorInput());
        if (status != null && !status.isOK()) {

            if (status.getException() != null) {
                BOMDiagramEditorPlugin.getInstance().getLogger()
                        .error(status.getException());
            } else {
                BOMDiagramEditorPlugin.getInstance().getLogger()
                        .error(status.getMessage());
            }
            closeEditor();
            disposeDocumentProvider();
        } else {
            super.createPartControl(parent);

            WorkingCopy wc = getWorkingCopy(getEditorInput());

            if (wc != null) {
                wc.addListener(wcListener);
            }
        }
    }

    @Override
    protected void disposeDocumentProvider() {
        // Stop listening to working copy
        WorkingCopy wc = getWorkingCopy(getEditorInput());
        if (wc != null) {
            wc.removeListener(wcListener);
        }
        super.disposeDocumentProvider();
    }

    @Override
    public String getTitleToolTip() {

        String toolTip = null;

        IEditorInput input = getEditorInput();
        if (input instanceof URIEditorInput) {
            URI uri = ((URIEditorInput) input).getURI();
            if (uri.isPlatformResource()) {
                toolTip = uri.toPlatformString(true);
                if (toolTip.startsWith("" + IPath.SEPARATOR)) { //$NON-NLS-1$
                    toolTip = toolTip.substring(1);
                }
            }
        }

        return toolTip != null ? toolTip : super.getTitleToolTip();
    }

    @Override
    public void doSetInput(IEditorInput input, boolean releaseEditorContents)
            throws CoreException {

        /*
         * If this model needs migrating then do it here before it's loaded
         */
        if (input != null) {
            final AbstractGMFWorkingCopy wc = getWorkingCopy(input);
            if (wc != null && wc.isInvalidVersion()) {
                final ProjectAssetMigrationManager migrationManager =
                        ProjectAssetMigrationManager.getInstance();
                final IProject project = getProject(wc);
                WorkspaceModifyOperation op = null;
                if (project != null
                        && migrationManager.doesProjectNeedMigrating(project)) {
                    /*
                     * Project needs migrating so provide user with choice to
                     * migrate entire project.
                     */
                    if (MessageDialog
                            .openQuestion(getSite().getShell(),
                                    Messages.UMLDiagramEditor_projectNeedsMigration_errorDialog_title,
                                    Messages.UMLDiagramEditor_projectNeedsMigration_errorDialog_message)) {
                        op = new WorkspaceModifyOperation() {

                            @Override
                            protected void execute(IProgressMonitor monitor)
                                    throws CoreException,
                                    InvocationTargetException,
                                    InterruptedException {

                                migrationManager.migrate(project, monitor);

                                /*
                                 * Nominally the project migrate should have
                                 * addressed the migration of the bom we are
                                 * trying to open.
                                 * 
                                 * HOWEVER there is a slight chance another
                                 * project asset said that it required migration
                                 * when the bom asset is up-to-date (and the
                                 * user has manually copied in old bom whilst
                                 * project is in this state).
                                 * 
                                 * In that case the bom will NOT be migrated
                                 * because the project migration only migrates
                                 * individual files of assets that say they need
                                 * migration.
                                 * 
                                 * So just in case, re-check the invalid version
                                 * and execute the migration for our one bom if
                                 * necessary.
                                 */

                                if (wc.getRootElement() == null
                                        && wc.isInvalidVersion()) {
                                    wc.migrate();
                                }
                            }
                        };
                    }
                } else {
                    /*
                     * Project is up-to-date so provide user with choice to
                     * migrate this file.
                     */
                    if (MessageDialog
                            .openQuestion(getSite().getShell(),
                                    Messages.UMLDiagramEditor_invalidVersion_errorDialog_title,
                                    Messages.UMLDiagramEditor_invalidVersion_errorDialog_message)) {
                        op = new WorkspaceModifyOperation() {

                            @Override
                            protected void execute(IProgressMonitor monitor)
                                    throws CoreException,
                                    InvocationTargetException,
                                    InterruptedException {
                                monitor.beginTask(Messages.UMLDiagramEditor_migrating_progress_shortdesc,
                                        IProgressMonitor.UNKNOWN);
                                wc.migrate();
                            }

                        };
                    }
                }

                if (op != null) {
                    try {
                        new ProgressMonitorDialog(getSite().getShell())
                                .run(true, true, op);
                    } catch (InvocationTargetException e) {
                        if (e.getTargetException() instanceof CoreException) {
                            throw (CoreException) e.getTargetException();
                        } else {
                            throw new CoreException(new Status(IStatus.ERROR,
                                    BOMDiagramEditorPlugin.ID,
                                    e.getLocalizedMessage(),
                                    e.getTargetException()));
                        }
                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                }
            }
        }

        super.doSetInput(input, releaseEditorContents);
    }

    /**
     * Get the project that contains the resource managed by the given working
     * copy.
     * 
     * @param wc
     * @return project if found, <code>null</code> otherwise.
     */
    private IProject getProject(WorkingCopy wc) {
        IProject project = null;

        List<IResource> resources = wc.getEclipseResources();
        if (resources != null && !resources.isEmpty()) {
            project = resources.get(0).getProject();
        }
        return project;
    }

    @Override
    protected ScrollingGraphicalViewer createScrollingGraphicalViewer() {
        /*
         * Create our own graphical viewer that supports the quickfix tooltip
         * for problem decorators.
         */
        return new BOMGraphicalViewer();
    }

    private Composite createErrorComposite(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();

        layout.numColumns = 3;
        layout.makeColumnsEqualWidth = false;
        int spacing = 8;
        int margins = 8;
        layout.marginBottom = margins;
        layout.marginTop = margins;
        layout.marginLeft = margins;
        layout.marginRight = margins;
        layout.horizontalSpacing = spacing;
        layout.verticalSpacing = spacing;

        root.setLayout(layout);
        Label imageLabel = new Label(root, SWT.NONE);
        Display display = Display.getCurrent();
        Image image = display.getSystemImage(SWT.ICON_ERROR);

        if (image != null) {
            image.setBackground(imageLabel.getBackground());
            imageLabel.setImage(image);
            imageLabel.setLayoutData(new GridData(
                    GridData.HORIZONTAL_ALIGN_CENTER
                            | GridData.VERTICAL_ALIGN_BEGINNING));
        }

        Label lbl = new Label(root, SWT.NONE);
        lbl.setText(Messages.UMLDiagramEditor_InvalidFile_message);
        Button btn = new Button(root, SWT.NONE);
        btn.setText(Messages.UMLDiagramEditor_CloseEditor_button);
        btn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                closeEditor();
            }
        });

        return root;
    }

    private void closeEditor() {
        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                getSite().getPage().closeEditor(UMLDiagramEditor.this, false);
            }
        });
    }

    @Override
    public DiagramEditPart getDiagramEditPart() {

        if (getDiagramGraphicalViewer() == null) {
            return null;
        }

        return super.getDiagramEditPart();
    }

    @Override
    public Object getAdapter(Class type) {
        // if (type == IContentOutlinePage.class) {
        // if (overview == null || overview.getControl().isDisposed()) {
        // overview = new OverviewViewPage(getGraphicalViewer());
        // }
        // return overview;
        // }

        AbstractGMFWorkingCopy wc = getWorkingCopy(getEditorInput());

        if (type == WorkingCopy.class) {
            return wc;
        }

        if (wc == null || wc.isInvalidFile()) {

            if ((type == IContentOutlinePage.class)
                    || (type == ZoomManager.class)) {
                // file is invalid
                return null;
            }
        }

        return super.getAdapter(type);
    }

    /**
     * Get the working copy of the model associated with the editor input.
     * 
     * @param editorInput
     * @return Working copy of the model being edited or <code>null</code> if
     *         working copy not found.
     */
    private AbstractGMFWorkingCopy getWorkingCopy(IEditorInput editorInput) {
        AbstractGMFWorkingCopy gmfWorkingCopy = null;

        if (editorInput != null) {
            WorkingCopy wc = null;
            IDocumentProvider provider = getDocumentProvider(editorInput);
            if (provider instanceof WorkingCopyDocumentProvider) {
                wc =
                        ((WorkingCopyDocumentProvider) provider)
                                .getWorkingCopy(editorInput);
            }

            if (wc == null) {
                wc =
                        Platform.getAdapterManager()
                                .getAdapter(editorInput, WorkingCopy.class);
            }

            if (wc instanceof AbstractGMFWorkingCopy) {
                gmfWorkingCopy = (AbstractGMFWorkingCopy) wc;
            }
        }

        return gmfWorkingCopy;
    }

    @Override
    protected void initializeGraphicalViewer() {
        /*
         * 21.05.09: When GMF2.1.3 was introduced in the target platform DND got
         * broken. Whenever a BOM element was dragged from the project explorer
         * to the diagram an SWT exception occurred. This was because of the
         * change to the GMF class ImageFileDropTargetListener which is
         * registered in the super class (the previous version used to check for
         * a FileTransfer and if there wasn't on would check for a
         * LocalSelectionTransfer, the new version only checks for a
         * FileTransfer). The workaround is to not call the super class and
         * instead call the initializeGraphicalViewerContent which the
         * super.super method does. The bugzilla report on this matter can be
         * found at: https://bugs.eclipse.org/bugs/show_bug.cgi?id=249718
         */

        try {
            // super.initializeGraphicalViewer();
            initializeGraphicalViewerContents();

        } catch (ClassCastException e) {
            /*
             * ClassCaseException is thrown for some older models that somehow
             * ended up with diagram visual nodes for UML elements (CLass/Enum
             * etc) INSIDE the Badge visual node). So we will intercept this and
             * hint to the user to go check problems/quick fixes.
             */
            throw new IllegalStateException(
                    Messages.UMLDiagramEditor_ErrorInitialisingDiagram_message,
                    e);
        }

        // Add listener for local selection transfers
        getDiagramGraphicalViewer()
                .addDropTargetListener(new LocalSelectionDropTargetListener(
                        getDiagramGraphicalViewer()));

        // Add listener for fragment transfers
        getDiagramGraphicalViewer()
                .addDropTargetListener(new BOMFragmentTransferDropTargetListener(
                        this, getEditingDomain()));

        // Add a transfer drag target listener that is supported on
        // palette template entries whose template is a creation tool.
        // This will enable drag and drop of the palette shape creation
        // tools.
        getDiagramGraphicalViewer()
                .addDropTargetListener(new PaletteToolTransferDropTargetListener(
                        getGraphicalViewer()) {

                    @Override
                    public boolean isEnabled(DropTargetEvent event) {
                        // Set this so that we get the unexecutable icon when
                        // mouse is over the canvas
                        setEnablementDeterminedByCommand(true);
                        return super.isEnabled(event);
                    }

                });

    }

    /**
     * @generated
     */
    @Override
    protected String getContextID() {
        return CONTEXT_ID;
    }

    // /**
    // * @generated NOT
    // */
    // @Override
    // protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot)
    // {
    // PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
    //
    // EObject element = getDiagram().getElement();
    //
    // if (element instanceof Package) {
    // Model mod = ((Package) element).getModel();
    //
    // // Check for First Class Profile
    // if (mod != null
    // && FirstClassProfileManager.isFirstClassProfileApplied(mod)) {
    // // Create a custom palette using the profile
    // FirstClassProfileExtension profileFirstClassExtConfig =
    // FirstClassProfileManager
    // .getProfileFirstClassExtConfig(mod);
    // new FirstClassProfilePaletteFactory().fillPalette(root,
    // profileFirstClassExtConfig);
    // return (root);
    // } else {
    // new UMLPaletteFactory().fillPalette(root);
    // return (root);
    // }
    // } else {
    // new UMLPaletteFactory().fillPalette(root);
    // }
    //
    // return root;
    // }

    @Override
    protected PaletteCustomizer createPaletteCustomizer() {
        return new PaletteCustomizerEx(getPreferenceStore()) {
            @Override
            public void applyCustomizationsToPalette(PaletteRoot paletteRoot) {
                super.applyCustomizationsToPalette(paletteRoot);
                final FirstClassProfileManager firstClassManager =
                        FirstClassProfileManager.getInstance();
                IFirstClassProfileExtension firstClassExt = null;

                // Check for first-class support profile
                EObject element = getDiagram().getElement();
                if (element instanceof Package) {
                    Model mod = ((Package) element).getModel();

                    if (mod != null) {
                        firstClassExt =
                                firstClassManager
                                        .getAppliedFirstClassProfile(mod);
                    }
                }

                Diagram diagram = getDiagram();
                boolean userDiagram = BomUIUtil.isUserDiagram(diagram);

                // Show BOM elements
                if (firstClassExt == null
                        || firstClassExt.showBomPaletteElements()) {
                    new UMLPaletteFactory().fillPalette(paletteRoot,
                            userDiagram);
                }

                // Show first-class support elements
                if (firstClassExt != null) {
                    new FirstClassProfilePaletteFactory()
                            .fillPalette(paletteRoot,
                                    firstClassExt,
                                    userDiagram);
                }

                /*
                 * XPD-3814 Override the default SelectionTool class with our
                 * class that shows properties view on object double-click.
                 */
                SelectionToolEntry selToolEntry =
                        findSelectionToolEntry(paletteRoot.getChildren());
                if (selToolEntry != null) {
                    selToolEntry.setToolClass(SelectionToolExEx.class);
                }

            }

            private SelectionToolEntry findSelectionToolEntry(
                    List<Object> paletteChildren) {
                for (Object child : paletteChildren) {
                    if (child instanceof SelectionToolEntry) {
                        return (SelectionToolEntry) child;

                    } else if (child instanceof PaletteContainer) {
                        /* Recurs into drawers */
                        SelectionToolEntry selToolEntry =
                                findSelectionToolEntry(((PaletteContainer) child)
                                        .getChildren());

                        if (selToolEntry != null) {
                            return selToolEntry;
                        }
                    }
                }

                return null;
            }

        };
    }

    /**
     * @generated
     */
    protected PaletteRoot createPaletteRootGen(PaletteRoot existingPaletteRoot) {
        PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
        new UMLPaletteFactory().fillPalette(root);
        return root;
    }

    /**
     * @generated
     */
    @Override
    protected String getEditingDomainID() {
        return XpdConsts.EDITING_DOMAIN_ID;
    }

    /**
     * @generated
     */
    @Override
    protected boolean shouldAddUndoContext(IUndoableOperation operation) {
        // Don't add undo context- ResourceUndoContext should have been added
        // already and that is what's needed
        return false;
    }

    /**
     * @generated
     */
    @Override
    protected IUndoContext getUndoContext() {
        IUndoContext context = null;
        Diagram diagram = getDiagram();

        if (diagram != null && diagram.eResource() != null) {
            context =
                    new ResourceUndoContext(getEditingDomain(),
                            diagram.eResource());
        }
        return context != null ? context : super.getUndoContext();
    }

    /**
     * @generated
     */
    @Override
    protected PreferencesHint getPreferencesHint() {
        return BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
    }

    /**
     * @generated
     */
    @Override
    public String getContributorId() {
        return BOMDiagramEditorPlugin.ID;
    }

    /**
     * @generated
     */
    @Override
    protected IDocumentProvider getDocumentProvider(IEditorInput input) {
        if (input instanceof IStorageEditorInput
                || input instanceof URIEditorInput) {
            return BOMDiagramEditorPlugin.getInstance().getDocumentProvider();
        }
        return super.getDocumentProvider(input);
    }

    /**
     * @generated NOT
     */
    @Override
    public TransactionalEditingDomain getEditingDomain() {
        if (editorEditingDomain == null) {
            IDocument document =
                    (getEditorInput() != null && getDocumentProvider() != null) ? getDocumentProvider()
                            .getDocument(getEditorInput()) : null;
            if (document instanceof IDiagramDocument) {
                editorEditingDomain =
                        ((IDiagramDocument) document).getEditingDomain();
            } else {
                editorEditingDomain = super.getEditingDomain();
            }

        }
        return editorEditingDomain;
    }

    @Override
    protected void setPartName(String partName) {
        AbstractGMFWorkingCopy wc = getWorkingCopy(getEditorInput());
        if (wc != null && wc.isReadOnly()) {
            partName =
                    String.format(Messages.UMLDiagramEditor_editorTitle_withReadOnlyTag_title,
                            partName);
        }
        super.setPartName(partName);
    }

    /**
     * @generated
     */
    @Override
    protected void setDocumentProvider(IEditorInput input) {
        if (input instanceof IStorageEditorInput
                || input instanceof URIEditorInput) {
            setDocumentProvider(BOMDiagramEditorPlugin.getInstance()
                    .getDocumentProvider());
        } else {
            super.setDocumentProvider(input);

        }
    }

    /**
     * @generated
     */
    @Override
    public void gotoMarker(IMarker marker) {
        MarkerNavigationService.getInstance().gotoMarker(this, marker);
    }

    /**
     * @generated
     */
    @Override
    public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     * @generated
     */
    @Override
    public void doSaveAs() {
        performSaveAs(new NullProgressMonitor());
    }

    /**
     * @generated
     */
    @Override
    protected void handleEditorInputChanged() {
        final IDocumentProvider provider = getDocumentProvider();
        IEditorInput input = getEditorInput();
        Shell shell = getSite().getShell();

        if (provider != null) {
            if (provider.isDeleted(input)) {
                // File deleted on the file system
                MessageDialog.openInformation(shell,
                        Messages.UMLDiagramEditor_deletedFileTitle,
                        Messages.UMLDiagramEditor_deletedFileMessage);
                close(false);
            }
            try {
                provider.synchronize(getEditorInput());
            } catch (CoreException e) {
                IStatus status = e.getStatus();
                if (status == null || status.getSeverity() != IStatus.CANCEL) {
                    ErrorDialog
                            .openError(shell,
                                    Messages.UMLDiagramEditor_problemLoadingFileTitle,
                                    Messages.UMLDiagramEditor_problemLoadingFileMessage,
                                    status);
                }
            }
        } else {
            // fix for http://dev.eclipse.org/bugs/show_bug.cgi?id=15066
            close(false);
            return;
        }
    }

    /**
     * @generated
     */
    @Override
    protected void performSaveAs(IProgressMonitor progressMonitor) {
        Shell shell = getSite().getShell();
        IEditorInput input = getEditorInput();
        SaveAsDialog dialog = new SaveAsDialog(shell);
        IFile original =
                input instanceof IFileEditorInput ? ((IFileEditorInput) input)
                        .getFile() : null;
        if (original != null) {
            dialog.setOriginalFile(original);
        }
        dialog.create();
        IDocumentProvider provider = getDocumentProvider();
        if (provider == null) {
            // editor has been programmatically closed while the dialog was open
            return;
        }
        if (provider.isDeleted(input) && original != null) {
            String message =
                    NLS.bind(Messages.UMLDiagramEditor_SavingDeletedFile,
                            original.getName());
            dialog.setErrorMessage(null);
            dialog.setMessage(message, IMessageProvider.WARNING);
        }
        if (dialog.open() == Window.CANCEL) {
            if (progressMonitor != null) {
                progressMonitor.setCanceled(true);
            }
            return;
        }
        IPath filePath = dialog.getResult();
        if (filePath == null) {
            if (progressMonitor != null) {
                progressMonitor.setCanceled(true);
            }
            return;
        }
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IFile file = workspaceRoot.getFile(filePath);
        final IEditorInput newInput = new FileEditorInput(file);
        // Check if the editor is already open
        IEditorMatchingStrategy matchingStrategy =
                getEditorDescriptor().getEditorMatchingStrategy();
        IEditorReference[] editorRefs =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getEditorReferences();
        for (int i = 0; i < editorRefs.length; i++) {
            if (matchingStrategy.matches(editorRefs[i], newInput)) {
                MessageDialog.openWarning(shell,
                        Messages.UMLDiagramEditor_SaveAsErrorTitle,
                        Messages.UMLDiagramEditor_SaveAsErrorMessage);
                return;
            }
        }
        boolean success = false;
        try {
            provider.aboutToChange(newInput);
            getDocumentProvider(newInput).saveDocument(progressMonitor,
                    newInput,
                    getDocumentProvider().getDocument(getEditorInput()),
                    true);
            success = true;
        } catch (CoreException x) {
            IStatus status = x.getStatus();
            if (status == null || status.getSeverity() != IStatus.CANCEL) {
                ErrorDialog.openError(shell,
                        Messages.UMLDiagramEditor_SaveErrorTitle,
                        Messages.UMLDiagramEditor_SaveErrorMessage,
                        x.getStatus());
            }
        } finally {
            provider.changed(newInput);
            if (success) {
                setInput(newInput);
            }
        }
        if (progressMonitor != null) {
            progressMonitor.setCanceled(!success);
        }
    }

    /**
     * @generated NOT
     */
    @Override
    public ShowInContext getShowInContext() {
        if (getGraphicalViewer() != null) {
            return new ShowInContext(getEditorInput(), getGraphicalViewer()
                    .getSelection());
        }
        return null;
    }

    /**
     * @generated
     */
    @Override
    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        DiagramEditorContextMenuProvider provider =
                new DiagramEditorContextMenuProvider(this,
                        getDiagramGraphicalViewer());
        getDiagramGraphicalViewer().setContextMenu(provider);
        getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU,
                provider,
                getDiagramGraphicalViewer());
    }

    /**
     * Get the {@link WorkingCopySaveable WorkingCopySaveable} associated with
     * the editor input.
     * 
     * @generated
     */
    @Override
    public Saveable[] getActiveSaveables() {
        IDocumentProvider documentProvider =
                getDocumentProvider(getEditorInput());

        if (documentProvider instanceof UMLDocumentProvider) {
            return ((UMLDocumentProvider) documentProvider)
                    .getSaveables(getEditorInput());
        }

        return new Saveable[0];
    }

    /**
     * @generated
     */
    @Override
    public Saveable[] getSaveables() {
        return getActiveSaveables();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.resources.ui.editor.IGotoObject#reveal(org.eclipse.
     * emf.ecore.EObject)
     */
    @Override
    public void reveal(EObject eo) {
        TreeIterator<EObject> content = getDiagram().eAllContents();
        @SuppressWarnings("unchecked")
        Class epClass = null;

        // Specific to association labels
        if (eo instanceof Property) {
            if (((Property) eo).getAssociation() != null) {
                Association assoc = ((Property) eo).getAssociation();
                if (assoc.getMemberEnds().get(0) == eo) {
                    epClass = AssociationSourceLabelEditPart.class;
                } else {
                    epClass = AssociationTargetLabelEditPart.class;
                }
                eo = assoc;
            }
        }
        // Special to parameters - select an operation
        if (eo instanceof Parameter) {
            eo = ((Parameter) eo).getOperation();
        }

        while (content.hasNext()) {
            EObject ref = content.next();

            if (ref instanceof View) {
                if (((View) ref).getElement() != eo) {
                    continue;
                }
                EditPart ep =
                        (EditPart) getGraphicalViewer().getEditPartRegistry()
                                .get(ref);
                if (epClass != null && epClass != ep.getClass()) {
                    continue;
                }
                getGraphicalViewer().select(ep);
                getGraphicalViewer().reveal(ep);
                return;
            } else {
                // content.prune();
            }
        }
    }

    /**
     * 
     * A Resource listener for the diagram, listening for stereotype additions
     * and removals.
     * 
     * @author rgreen
     * 
     */
    class DiagramResourceListener implements ResourceSetListener {

        public DiagramResourceListener() {
        }

        @Override
        public NotificationFilter getFilter() {
            return null;
        }

        @Override
        public boolean isAggregatePrecommitListener() {
            return false;
        }

        @Override
        public boolean isPostcommitOnly() {
            return true;
        }

        @Override
        public boolean isPrecommitOnly() {
            return false;
        }

        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {

            // Loop through all the notification this event contains and
            // locate the one we are interested in.
            List<Notification> notifications = event.getNotifications();

            if (getDiagram() != null) {

                // Check for set/unset of stereotype
                refreshStereotypes(notifications);

                // Check if we need to refresh supertype labels
                refreshSupertypes(notifications);

                if (BomUIUtil.isUserDiagram(getDiagram())) {
                    /*
                     * Update the user diagrams. If the parent of a class has
                     * changed then the qualification of the class in the user
                     * diagram will need updating
                     */
                    refreshUserDiagram(notifications);
                }
            }

        }

        private void refreshUserDiagram(List<Notification> notifications) {
            for (Notification notification : notifications) {
                if (notification.getNotifier() instanceof Diagram
                        && getDiagram() == notification.getNotifier()
                        && notification.getEventType() == Notification.SET
                        && notification.getFeature() == NotationPackage.eINSTANCE
                                .getDiagram_Name()) {
                    // Name of this diagram has changes so update the title
                    setPartName(getDiagram().getName());

                } else if (notification.getNotifier() instanceof Package
                        && (notification.getEventType() == Notification.ADD || notification
                                .getEventType() == Notification.ADD_MANY)
                        && notification.getNewValue() instanceof NamedElement) {
                    NamedElement elem =
                            (NamedElement) notification.getNewValue();

                    Map<?, ?> registry =
                            getGraphicalViewer().getEditPartRegistry();

                    for (Entry<?, ?> entry : registry.entrySet()) {
                        if (entry.getKey() instanceof View
                                && ((View) entry.getKey()).getElement() == elem) {
                            Object value = entry.getValue();
                            if (value instanceof BOMStereoAwareCompartmentEditPart) {
                                ((BOMStereoAwareCompartmentEditPart) value)
                                        .refresh();
                            }
                        }
                    }
                }
            }
        }

        /**
         * 
         * We may need to refesh a supertype label so that it changes from a
         * standard name to a qualified name or vice versa.
         * 
         * If a classifier is added, removed or has its name changed we need to
         * see if the names of other classifiers have supertypes with the same
         * name. If they do, the supertype exists in a different file and the
         * supertype label will need to change to a fully qualified name.
         * 
         * Refresh the labels from here because it is much more efficient than
         * having the label editparts listening to and interpreting events.
         * 
         * @param notifications
         */
        private void refreshSupertypes(List<Notification> notifications) {
            for (Notification n : notifications) {

                Resource resource = getDiagram().eResource();
                Object notifier = n.getNotifier();

                if (notifier == resource
                        || (notifier instanceof EObject && ((EObject) notifier)
                                .eResource() == resource)) {

                    Classifier classifier = null;
                    Object affectedValue = null;

                    if (n.getEventType() == Notification.ADD
                            || n.getEventType() == Notification.REMOVE) {

                        if (n.getEventType() == Notification.ADD) {
                            affectedValue = n.getNewValue();
                        } else {
                            affectedValue = n.getOldValue();
                        }

                        if (affectedValue instanceof EObject) {
                            if (isSuperTypeClassifier((EObject) affectedValue)) {
                                classifier = (Classifier) affectedValue;
                            } else if (affectedValue instanceof Package) {
                                TreeIterator<EObject> iter =
                                        ((Package) affectedValue)
                                                .eAllContents();

                                while (iter.hasNext()) {
                                    EObject next = iter.next();
                                    if (isSuperTypeClassifier(next)) {
                                        classifier = (Classifier) next;
                                        break;
                                    }
                                }
                            }
                        }

                    } else if (n.getEventType() == Notification.SET) {
                        if (n.getFeature() == UMLPackage.eINSTANCE
                                .getNamedElement_Name()
                                && isSuperTypeClassifier((EObject) n
                                        .getNotifier())) {
                            classifier = (Classifier) n.getNotifier();
                        }
                    }

                    if (classifier != null) {

                        // Only refresh the supertype labels affected by this
                        // change
                        Model mdl = null;

                        if (classifier.getModel() == null) {
                            // This must have been a REMOVE, so get
                            // the model from the notifier
                            if (notifier instanceof Package) {
                                mdl = ((Package) notifier).getModel();
                            }
                        } else {
                            mdl = classifier.getModel();
                        }

                        List<Classifier> lstElems = new ArrayList<Classifier>();

                        // Get a list of all classifiers in the model that have
                        // a
                        // supertype with the same name as our affectedValue
                        getModelSubTypes(mdl, lstElems, classifier.getName());

                        if (!lstElems.isEmpty()) {

                            Map editPartRegistry =
                                    getGraphicalViewer().getEditPartRegistry();

                            // Refresh all supertype labels
                            for (Object key : editPartRegistry.keySet()) {
                                if (key instanceof View) {
                                    Object value = editPartRegistry.get(key);

                                    if (value instanceof SuperClassNameLabelEditPart) {
                                        SuperClassNameLabelEditPart ep =
                                                (SuperClassNameLabelEditPart) value;
                                        ep.refresh();
                                    }

                                    if (value instanceof PrimTypeSuperTypeNameLabelEditPart) {
                                        PrimTypeSuperTypeNameLabelEditPart ep =
                                                (PrimTypeSuperTypeNameLabelEditPart) value;
                                        ep.refresh();
                                    }

                                    if (value instanceof EnumerationSuperTypeNameEditPart) {
                                        EnumerationSuperTypeNameEditPart ep =
                                                (EnumerationSuperTypeNameEditPart) value;
                                        ep.refresh();
                                    }
                                }
                            }

                        }

                        break;
                    }
                }

            }

        }

        /**
         * @param pkg
         * @param lst
         * @param name
         */
        private void getModelSubTypes(Package pkg, List<Classifier> lst,
                String name) {

            if( (pkg != null) && (name != null)) {
                List<PackageableElement> pkgElements =
                        pkg.getPackagedElements();

                for (PackageableElement element : pkgElements) {
                    if (element instanceof Package) {
                        getModelSubTypes((Package) element, lst, name);
                    }

                    if (element instanceof Classifier) {
                        Classifier clr = (Classifier) element;
                        EList<Classifier> generals = clr.getGenerals();

                        if (!generals.isEmpty()) {
                            Classifier superType = generals.get(0);

                            if (name.equals(superType.getName())) {
                                lst.add(clr);
                            }
                        }
                    }
                }
            }
        }

        /**
         * @param eo
         * @return
         */
        private boolean isSuperTypeClassifier(EObject eo) {
            return (eo instanceof org.eclipse.uml2.uml.Class
                    || eo instanceof PrimitiveType || eo instanceof Enumeration);
        }

        /**
         * @param notifications
         */
        private void refreshStereotypes(List<Notification> notifications) {

            Cursor oldCursor = null;
            boolean waitCursorSetOn = false;

            try {
                Set<GraphicalEditPart> refreshSet =
                        new HashSet<GraphicalEditPart>();

                Set<BOMStereoAwareCompartmentEditPart> refreshStereoSet =
                        new HashSet<BOMStereoAwareCompartmentEditPart>();

                for (Notification n : notifications) {
                    if (BOMEditPartUtils.isStereotypeBeingSet(n)) {
                        /*
                         * The SET event can be either an addition or a removal
                         * of a stereotype
                         */
                        Resource resource = getDiagram().eResource();
                        Object notifier = n.getNotifier();
                        boolean doUpdate = false;

                        if (notifier == resource
                                || (notifier instanceof EObject && ((EObject) notifier)
                                        .eResource() == resource)) {
                            doUpdate = true;
                        }
                        /*
                         * If this is an unset of a stereotype then the above
                         * test will fail since the notifier's resource will be
                         * null So we can get the Resource of the old or new
                         * values and retrive its Resource
                         */
                        if (!doUpdate && n.getOldValue() != null) {
                            Object oldValue = n.getOldValue();
                            if (oldValue == resource
                                    || (oldValue instanceof EObject && ((EObject) oldValue)
                                            .eResource() == resource)) {
                                doUpdate = true;
                            }
                        }
                        if (!doUpdate && n.getNewValue() != null) {
                            Object newValue = n.getNewValue();
                            if (newValue == resource
                                    || (newValue instanceof EObject && ((EObject) newValue)
                                            .eResource() == resource)) {
                                doUpdate = true;
                            }
                        }
                        if (doUpdate) {
                            /*
                             * XPD-4544: Can take a long time to refresh from
                             * stereo types in large BOM so show busy cursor
                             * when we do.
                             */
                            if (!waitCursorSetOn
                                    && Thread.currentThread() == Display
                                            .getDefault().getThread()) {
                                oldCursor =
                                        Display.getDefault().getActiveShell()
                                                .getCursor();
                                Display.getDefault().getActiveShell()
                                        .setCursor(Cursors.WAIT);

                                waitCursorSetOn = true;
                            }

                            /*
                             * If so find the appropriate edit part and refresh
                             * it
                             */
                            Object obj =
                                    BOMEditPartUtils
                                            .getFeatureObjectAffected(n);

                            if (obj == null) {
                                Map<EObject, Collection<Setting>> find =
                                        EcoreUtil.CrossReferencer
                                                .find(Collections
                                                        .singleton(notifier));

                                for (EObject eo : find.keySet()) {
                                    if (eo instanceof NamedElement) {
                                        obj = eo;
                                        break;
                                    }
                                }
                            }
                            Map<?, ?> editPartRegistry =
                                    getGraphicalViewer().getEditPartRegistry();
                            for (Object key : editPartRegistry.keySet()) {
                                /*
                                 * XPD:4025 Association source/target label
                                 * wasn't being refreshed
                                 */
                                if ((obj instanceof Property
                                        && key instanceof EditPart && obj
                                            .equals(((EditPart) key).getModel()))
                                        || (key instanceof View && ((View) key)
                                                .getElement() == obj)) {
                                    Object value = editPartRegistry.get(key);
                                    /*
                                     * Check that this isn't a restricted type
                                     * stereotype which is always hidden and
                                     * shouldn't be displayed
                                     */
                                    Stereotype stereotype =
                                            UMLUtil.getStereotype((EObject) n
                                                    .getNotifier());
                                    if ("RestrictedType".equals(stereotype //$NON-NLS-1$
                                            .getName())) {
                                        continue;
                                    }
                                    if (value instanceof BOMStereoAwareCompartmentEditPart) {
                                        refreshStereoSet
                                                .add((BOMStereoAwareCompartmentEditPart) value);
                                        /*
                                         * Continue rather than return as there
                                         * may be more stereotype events to
                                         * follow (e.g. if a profile has been
                                         * removed then all stereotypes will
                                         * have to be removed too.
                                         */
                                        continue;
                                    }
                                    if (value instanceof BOMStereoAwareLabelEditPart) {
                                        refreshSet
                                                .add((GraphicalEditPart) value);
                                        continue;
                                    }
                                    if (value instanceof BOMStereoAwareDiagramEditPart) {
                                        refreshSet
                                                .add((GraphicalEditPart) value);
                                        continue;
                                    }
                                }
                            }
                        }
                    }

                }
                /*
                 * XPD-4544: call refresh stereotypes outside the notifications
                 * loop as large BOM might hang up refreshing stereotypes for
                 * each notification
                 */
                for (BOMStereoAwareCompartmentEditPart bomStereoAwareCompartmentEditPart : refreshStereoSet) {
                    bomStereoAwareCompartmentEditPart.refreshStereotype();
                }

                for (GraphicalEditPart graphicalEditPart : refreshSet) {
                    graphicalEditPart.refresh();
                }

            } finally {
                /* If we showed wait cursor then turn it back off. */
                if (waitCursorSetOn) {
                    Display.getDefault().getActiveShell().setCursor(oldCursor);
                }
            }

        }

        @Override
        public org.eclipse.emf.common.command.Command transactionAboutToCommit(
                ResourceSetChangeEvent event) throws RollbackException {
            return null;
        }
    }

    /**
     * Extension of the diagram graphical viewer that includes the quick fix
     * tooltip.
     * 
     * @author njpatel
     * 
     */
    private class BOMGraphicalViewer extends DiagramGraphicalViewer {
        private QuickFixToolTipEnabledDomainEventDispatcher stickyToolTipEnabledDomainEventDispatcher;

        @Override
        public void setEditDomain(EditDomain domain) {
            super.setEditDomain(domain);
            stickyToolTipEnabledDomainEventDispatcher =
                    new QuickFixToolTipEnabledDomainEventDispatcher(domain,
                            this);
            getLightweightSystem()
                    .setEventDispatcher(stickyToolTipEnabledDomainEventDispatcher);
        }

        @Override
        protected DomainEventDispatcher getEventDispatcher() {
            /*
             * Override to set our own domain event dispatcher, doing so seems
             * to be the ONLY way of providing a different ToolTipHelper which
             * seems to be the only way of providing a way of not destroying the
             * tooltip if the mouse is moved awy from original host figure and
             * over the tooltip.
             * 
             * We overrode the domain event dispatcher so we should get
             * underlying class to use ours.
             */
            return stickyToolTipEnabledDomainEventDispatcher;
        }
    }

    @Override
    public int promptToSaveOnClose() {
        /*
         * Don't prompt the user to save on editor close when running in RCP
         * application
         */
        if (XpdResourcesPlugin.isRCP()) {
            return NO;
        }

        return DEFAULT;
    }

    /**
     * @see com.tibco.xpd.resources.ui.IRefreshableTitle#refreshTitle()
     *
     */
    @Override
    public void refreshTitle() {
        setPartName(getDiagram().getName());
    }
}
