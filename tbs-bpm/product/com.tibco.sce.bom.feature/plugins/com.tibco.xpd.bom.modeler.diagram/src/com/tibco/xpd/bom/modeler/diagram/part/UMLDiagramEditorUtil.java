/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.modeler.diagram.part.custom.commands.CreateBOMDiagramCommand;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * @generated
 */
public class UMLDiagramEditorUtil {

    /**
     * @generated
     */
    public static Map getSaveOptions() {
        Map saveOptions = new HashMap();
        saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
        saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
                Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
        return saveOptions;
    }

    /**
     * @generated
     */
    public static boolean openDiagram(Resource diagram)
            throws PartInitException {
        String path = diagram.getURI().toPlatformString(true);
        IResource workspaceResource =
                ResourcesPlugin.getWorkspace().getRoot()
                        .findMember(new Path(path));
        if (workspaceResource instanceof IFile) {
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            return null != page.openEditor(new FileEditorInput(
                    (IFile) workspaceResource), UMLDiagramEditor.ID);
        }
        return false;
    }

    /**
     * Open the diagram containing the given <code>EObject</code>.
     * 
     * @param eo
     * @return <code>true</code> if the given object was opened/hilighted in a
     *         diagram, <code>false</code> otherwise.
     * @throws PartInitException
     */
    public static boolean openDiagram(EObject eo) throws PartInitException {
        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        /*
         * If the eo is already contained in the active editor then highlight it
         * there (this will be the case if in a package editor), otherwise open
         * the main model editor.
         */
        if (!(eo instanceof Model)) {
            IEditorPart editor = page.getActiveEditor();
            if (editor instanceof UMLDiagramEditor
                    && editor.getEditorInput() instanceof URIEditorInput) {
                // This is a package editor
                URIEditorInput input = (URIEditorInput) editor.getEditorInput();

                Package pkg = null;
                EObject objToHilight = eo;
                while (pkg == null) {
                    if (objToHilight instanceof Package) {
                        pkg = (Package) objToHilight;
                    } else if (objToHilight == null) {
                        break;
                    } else {
                        objToHilight = objToHilight.eContainer();
                    }
                }

                if (pkg != null) {
                    Diagram diagram = getDiagram(pkg);

                    if (diagram != null && diagram.eResource() != null) {
                        URI uri = EcoreUtil.getURI(diagram);
                        if (uri != null && uri.equals(input.getURI())) {
                            ((UMLDiagramEditor) editor).reveal(eo);
                            return true;
                        }
                    }
                }
            }
        }
        String path = eo.eResource().getURI().toPlatformString(true);

        if (path != null) {
            IResource workspaceResource =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .findMember(new Path(path));
            if (workspaceResource instanceof IFile) {
                IEditorPart editor =
                        page.openEditor(new FileEditorInput(
                                (IFile) workspaceResource), UMLDiagramEditor.ID);
                if (editor instanceof UMLDiagramEditor) {
                    UMLDiagramEditor ude = (UMLDiagramEditor) editor;
                    ude.reveal(eo);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Open a BOM editor for the given Diagram.
     * 
     * @param page
     * @param diagram
     * @return
     * @throws PartInitException
     * @since 3.3
     */
    public static IEditorPart openEditor(IWorkbenchPage page, Diagram diagram)
            throws PartInitException {
        return BomUIUtil.openEditor(page, diagram);
    }

    /**
     * Get a {@link Diagram} associated with the given package if any.
     * 
     * @param pkg
     *            {@link Package}
     * @return <code>Diagram</code> if one is associated with the given
     *         <code>Package</code>, <code>null</code> otherwise.
     */
    private static Diagram getDiagram(Package pkg) {
        if (pkg != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(pkg);
            if (wc instanceof AbstractGMFWorkingCopy) {
                List<Diagram> diagrams =
                        ((AbstractGMFWorkingCopy) wc).getDiagrams();

                if (diagrams != null) {
                    for (Diagram diag : diagrams) {
                        if (pkg.equals(diag.getElement())) {
                            return diag;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @generated
     */
    public static void setCharset(IFile file) {
        if (file == null) {
            return;
        }
        try {
            file.setCharset("UTF-8", new NullProgressMonitor()); //$NON-NLS-1$
        } catch (CoreException e) {
            BOMDiagramEditorPlugin
                    .getInstance()
                    .logError("Unable to set charset for file " + file.getFullPath(), e); //$NON-NLS-1$
        }
    }

    /**
     * @generated NOT
     */
    public static String getUniqueFileName(IPath containerFullPath,
            String fileName, String extension) {
        if (containerFullPath == null) {
            containerFullPath = new Path(""); //$NON-NLS-1$
        }

        /* Default the file name to the project name if project is selected. */
        if (!containerFullPath.isEmpty()) {
            String firstSegment = containerFullPath.segment(0);

            if (firstSegment != null && firstSegment.length() > 0) {
                IProject project =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(firstSegment);
                if (project != null && project.isAccessible()) {
                    fileName = project.getName();
                }
            }
        }

        if (fileName == null || fileName.trim().length() == 0) {
            fileName =
                    Messages.UMLDiagramEditorUtil_CreateDiagramDefaultFileNameLabel;
        }

        return SpecialFolderUtil.getUniqueFileName(containerFullPath,
                fileName,
                extension);
    }

    /**
     * Runs the wizard in a dialog.
     * 
     * @generated
     */
    public static void runWizard(Shell shell, Wizard wizard, String settingsKey) {
        IDialogSettings pluginDialogSettings =
                BOMDiagramEditorPlugin.getInstance().getDialogSettings();
        IDialogSettings wizardDialogSettings =
                pluginDialogSettings.getSection(settingsKey);
        if (wizardDialogSettings == null) {
            wizardDialogSettings =
                    pluginDialogSettings.addNewSection(settingsKey);
        }
        wizard.setDialogSettings(wizardDialogSettings);
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.create();
        dialog.getShell().setSize(Math.max(500, dialog.getShell().getSize().x),
                500);
        dialog.open();
    }

    /**
     * This method should be called within a workspace modify operation since it
     * creates resources.
     * 
     * @throws CoreException
     * 
     * @generated NOT
     */
    public static Resource createDiagram(URI diagramURI,
            IProgressMonitor progressMonitor) throws CoreException {

        return createDiagram(diagramURI, progressMonitor, null);
    }

    /**
     * This method should be called within a workspace modify operation since it
     * creates resources.
     * 
     * @throws CoreException
     * 
     * @generated NOT
     */
    public static Resource createDiagram(URI diagramURI,
            IProgressMonitor progressMonitor,
            IFirstClassProfileExtension extension) throws CoreException {
        return createDiagram(diagramURI, progressMonitor, extension, null);
    }

    /**
     * This method should be called within a workspace modify operation since it
     * creates resources.
     * <p>
     * This method applies the given template fragment to the model.
     * </p>
     * 
     * @see #createDiagram(URI, IProgressMonitor, Profile)
     * 
     * @param diagramURI
     *            diagram URI
     * @param progressMonitor
     *            monitor
     * @param extension
     *            profile to apply to the model
     * @param template
     *            fragment to apply to the model
     * @return <code>Resource</code>.
     * @throws CoreException
     */
    public static Resource createDiagram(URI diagramURI,
            IProgressMonitor progressMonitor,
            final IFirstClassProfileExtension extension,
            final IFragment template) throws CoreException {
        return createDiagram(diagramURI,
                null,
                progressMonitor,
                extension,
                template);
    }

    /**
     * @param diagramURI
     *            diagram URI
     * @param modelLabel
     *            label to give the Model, <code>null</code> to set default
     *            value
     * @param progressMonitor
     * @param extension
     *            profile to apply to the model
     * @param template
     *            fragment to apply to the model
     * @return <code>Resource</code>.
     * @throws CoreException
     * @since 3.5
     */
    public static Resource createDiagram(URI diagramURI,
            final String modelLabel, IProgressMonitor progressMonitor,
            final IFirstClassProfileExtension extension,
            final IFragment template) throws CoreException {
        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        progressMonitor
                .beginTask(Messages.UMLDiagramEditorUtil_CreateDiagramProgressTask,
                        3);
        Resource tempDiagramResource =
                editingDomain.getResourceSet().getResource(diagramURI, true);
        final Resource diagramResource;
        //XPD-4585: if resource exists reload it.
        if (tempDiagramResource != null) {
            diagramResource = tempDiagramResource;
        } else {
            diagramResource =
                editingDomain.getResourceSet().createResource(diagramURI);
        }
        final String diagramName = diagramURI.lastSegment();

        AbstractTransactionalCommand command =
                new CreateBOMDiagramCommand(
                        editingDomain,
                        Messages.UMLDiagramEditorUtil_CreateDiagramCommandLabel,
                        Collections.EMPTY_LIST, modelLabel, extension,
                        template, diagramResource, diagramName);

        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new SubProgressMonitor(progressMonitor, 1),
                    null);
        } catch (ExecutionException e) {
            throw new CoreException(new Status(IStatus.ERROR,
                    BOMDiagramEditorPlugin.ID, e.getLocalizedMessage(),
                    e.getCause()));
        }

        setCharset(WorkspaceSynchronizer.getFile(diagramResource));
        return diagramResource;
    }

    /**
     * Find a unique model name based on the name given. If the given name is
     * not unique then an numeric suffix will be added to make it unique.
     * 
     * @param modelName
     * @return
     * @since 3.3
     */
    private static String getUniqueModelName(String modelName) {
        if (modelName != null) {
            BOMIndexerService service =
                    BOMResourcesPlugin.getDefault().getIndexerService();
            if (service != null) {
                IndexerItem[] items = service.getAllModels();
                if (items.length > 0) {
                    Set<String> names = new HashSet<String>();
                    for (IndexerItem item : items) {
                        names.add(item.getName());
                    }
                    if (names.contains(modelName)) {
                        // Name is not unique so make it unique
                        int idx = 1;
                        String origName = modelName;
                        while (names.contains(modelName)) {
                            modelName = origName + String.valueOf(idx++);
                        }
                    }
                }
            }
        }

        return modelName;
    }

    /**
     * Apply the given fragment to the view.
     * 
     * @param view
     *            target view
     * @param fragment
     *            fragment template.
     */
    private static void applyTemplate(View view, IFragment fragment) {
        if (view != null && fragment != null
                && fragment.getLocalizedData() != null) {
            try {
                BOMCopyPasteCommandFactory.getInstance().pasteFromString(view,
                        fragment.getLocalizedData(),
                        null,
                        new Point(100, 100));
            } catch (Exception e) {
                BOMDiagramEditorPlugin.getInstance()
                        .logError("Unable to apply template to model.", e); //$NON-NLS-1$
            }
        }

    }

    /**
     * Create a new instance of domain element associated with canvas. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    private static Model createInitialModel() {
        return BOMUtils.createInitialModel();
    }

    /**
     * Store model element in the resource. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    private static void attachModelToResource(Package model, Resource resource) {
        resource.getContents().add(model);
    }

    /**
     * @generated
     */
    public static void selectElementsInDiagram(
            IDiagramWorkbenchPart diagramPart, List/* EditPart */editParts) {
        diagramPart.getDiagramGraphicalViewer().deselectAll();

        EditPart firstPrimary = null;
        for (Iterator it = editParts.iterator(); it.hasNext();) {
            EditPart nextPart = (EditPart) it.next();
            diagramPart.getDiagramGraphicalViewer().appendSelection(nextPart);
            if (firstPrimary == null && nextPart instanceof IPrimaryEditPart) {
                firstPrimary = nextPart;
            }
        }

        if (!editParts.isEmpty()) {
            diagramPart.getDiagramGraphicalViewer()
                    .reveal(firstPrimary != null ? firstPrimary
                            : (EditPart) editParts.get(0));
        }
    }

    /**
     * @generated
     */
    private static int findElementsInDiagramByID(DiagramEditPart diagramPart,
            EObject element, List editPartCollector) {
        IDiagramGraphicalViewer viewer =
                (IDiagramGraphicalViewer) diagramPart.getViewer();
        final int intialNumOfEditParts = editPartCollector.size();

        if (element instanceof View) { // support notation element lookup
            EditPart editPart =
                    (EditPart) viewer.getEditPartRegistry().get(element);
            if (editPart != null) {
                editPartCollector.add(editPart);
                return 1;
            }
        }

        String elementID = EMFCoreUtil.getProxyID(element);
        List associatedParts =
                viewer.findEditPartsForElement(elementID,
                        IGraphicalEditPart.class);
        // perform the possible hierarchy disjoint -> take the top-most parts
        // only
        for (Iterator editPartIt = associatedParts.iterator(); editPartIt
                .hasNext();) {
            EditPart nextPart = (EditPart) editPartIt.next();
            EditPart parentPart = nextPart.getParent();
            while (parentPart != null && !associatedParts.contains(parentPart)) {
                parentPart = parentPart.getParent();
            }
            if (parentPart == null) {
                editPartCollector.add(nextPart);
            }
        }

        if (intialNumOfEditParts == editPartCollector.size()) {
            if (!associatedParts.isEmpty()) {
                editPartCollector.add(associatedParts.iterator().next());
            } else {
                if (element.eContainer() != null) {
                    return findElementsInDiagramByID(diagramPart,
                            element.eContainer(),
                            editPartCollector);
                }
            }
        }
        return editPartCollector.size() - intialNumOfEditParts;
    }

    /**
     * @generated
     */
    public static View findView(DiagramEditPart diagramEditPart,
            EObject targetElement, LazyElement2ViewMap lazyElement2ViewMap) {
        boolean hasStructuralURI = false;
        if (targetElement.eResource() instanceof XMLResource) {
            hasStructuralURI =
                    ((XMLResource) targetElement.eResource())
                            .getID(targetElement) == null;
        }

        View view = null;
        if (hasStructuralURI
                && !lazyElement2ViewMap.getElement2ViewMap().isEmpty()) {
            view =
                    (View) lazyElement2ViewMap.getElement2ViewMap()
                            .get(targetElement);
        } else if (findElementsInDiagramByID(diagramEditPart,
                targetElement,
                lazyElement2ViewMap.editPartTmpHolder) > 0) {
            EditPart editPart =
                    (EditPart) lazyElement2ViewMap.editPartTmpHolder.get(0);
            lazyElement2ViewMap.editPartTmpHolder.clear();
            view =
                    editPart.getModel() instanceof View ? (View) editPart
                            .getModel() : null;
        }

        return (view == null) ? diagramEditPart.getDiagramView() : view;
    }

    /**
     * @generated
     */
    public static class LazyElement2ViewMap {
        /**
         * @generated
         */
        private Map element2ViewMap;

        /**
         * @generated
         */
        private final View scope;

        /**
         * @generated
         */
        private final Set elementSet;

        /**
         * @generated
         */
        public final List editPartTmpHolder = new ArrayList();

        /**
         * @generated
         */
        public LazyElement2ViewMap(View scope, Set elements) {
            this.scope = scope;
            this.elementSet = elements;
        }

        /**
         * @generated
         */
        public final Map getElement2ViewMap() {
            if (element2ViewMap == null) {
                element2ViewMap = new HashMap();
                // map possible notation elements to itself as these can't be
                // found by view.getElement()
                for (Iterator it = elementSet.iterator(); it.hasNext();) {
                    EObject element = (EObject) it.next();
                    if (element instanceof View) {
                        View view = (View) element;
                        if (view.getDiagram() == scope.getDiagram()) {
                            element2ViewMap.put(element, element); // take only
                            // those that
                            // part of
                            // our
                            // diagram
                        }
                    }
                }

                buildElement2ViewMap(scope, element2ViewMap, elementSet);
            }
            return element2ViewMap;
        }

        /**
         * @generated
         */
        static Map buildElement2ViewMap(View parentView, Map element2ViewMap,
                Set elements) {
            if (elements.size() == element2ViewMap.size())
                return element2ViewMap;

            if (parentView.isSetElement()
                    && !element2ViewMap.containsKey(parentView.getElement())
                    && elements.contains(parentView.getElement())) {
                element2ViewMap.put(parentView.getElement(), parentView);
                if (elements.size() == element2ViewMap.size())
                    return element2ViewMap;
            }

            for (Iterator it = parentView.getChildren().iterator(); it
                    .hasNext();) {
                buildElement2ViewMap((View) it.next(),
                        element2ViewMap,
                        elements);
                if (elements.size() == element2ViewMap.size())
                    return element2ViewMap;
            }
            for (Iterator it = parentView.getSourceEdges().iterator(); it
                    .hasNext();) {
                buildElement2ViewMap((View) it.next(),
                        element2ViewMap,
                        elements);
                if (elements.size() == element2ViewMap.size())
                    return element2ViewMap;
            }
            for (Iterator it = parentView.getSourceEdges().iterator(); it
                    .hasNext();) {
                buildElement2ViewMap((View) it.next(),
                        element2ViewMap,
                        elements);
                if (elements.size() == element2ViewMap.size())
                    return element2ViewMap;
            }
            return element2ViewMap;
        }
    } // LazyElement2ViewMap

}
