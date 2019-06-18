package com.tibco.xpd.om.modeler.diagram.part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.provider.OrgModelItemProvider;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrganizationSubBadgeEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.editor.IGotoObject;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @generated
 */
/**
 * @author rgreen, Jan Arciuchiewicz
 * 
 */
public class OrganizationModelDiagramEditorUtil {

    /**
     * Parameter indicating if the default meta-model for initial model should
     * be created. The value should be of type java.lang.Boolean.
     * 
     * @see #createDiagram(URI, IProgressMonitor, Map)
     */
    public static String CREATE_DEFAULT_METAMODEL_PARAM =
            "CreateDefaultMetamodel"; //$NON-NLS-1$

    public static String APPLY_STANDARD_TYPE_PARAM = "ApplyStandardType"; //$NON-NLS-1$

    private static String SUB_DIAGRAM_ID = "Organization Model Sub"; //$NON-NLS-1$

    private static String SUB_DIAGRAM_EDITOR_ID =
            OMResourcesUIActivator.SUB_DIAGRAM_EDITOR_ID;

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
     * 
     * Overrides the generated openDiagram method so that we open an editor for
     * the default Organization's sub-diagram.
     * 
     * @param Resource
     *            diagram
     * @return boolean
     * @throws PartInitException
     */
    public static boolean openDiagram(Resource diagram)
            throws PartInitException {

        // openDiagramGen(diagram);

        EList<EObject> contents = diagram.getContents();

        for (EObject object : contents) {
            if (object instanceof Diagram) {
                Diagram diag = (Diagram) object;

                if (SUB_DIAGRAM_ID.equals(diag.getType())) {
                    // open up the editor
                    URI uri = EcoreUtil.getURI(diag);

                    EObject element = diag.getElement();

                    String editorName = ""; //$NON-NLS-1$
                    if (element instanceof Organization) {
                        Organization org = (Organization) element;
                        editorName = org.getDisplayName();
                    } else {
                        editorName =
                                uri.lastSegment()
                                        + "#" + diag.eResource().getContents().indexOf(diag); //$NON-NLS-1$
                    }

                    IEditorInput editorInput =
                            new URIEditorInput(uri, editorName);
                    IWorkbenchPage page =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage();
                    return null != page.openEditor(editorInput,
                            SUB_DIAGRAM_EDITOR_ID);
                }

            }
        }

        return false;

    }

    /**
     * @generated
     */
    public static boolean openDiagramGen(Resource diagram)
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
                    (IFile) workspaceResource),
                    OrganizationModelDiagramEditor.ID);
        }
        return false;
    }

    /**
     * Open diagram and select the passed in EObject. Usually called from
     * double-clicking a marker entry on the problems page
     * 
     * @param eo
     * @return boolean
     * @throws PartInitException
     */
    public static boolean openDiagram(EObject eo) throws PartInitException {
        if (eo != null && eo.eClass() != null
                && eo.eClass().getEPackage() == OMPackage.eINSTANCE) {
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            if (eo instanceof OrgModel
                    || (eo instanceof OrgUnitRelationship && isCrossOrganizationRelation((OrgUnitRelationship) eo))) {
                // Open the org model editor
                return openOrganizationModel(page, eo);
            } else {
                // Find the parent Organization
                EObject objToOpen = eo;
                while (!(objToOpen instanceof Organization)) {
                    objToOpen = objToOpen.eContainer();
                    if (objToOpen == null) {
                        break;
                    }
                }

                if (objToOpen instanceof Organization) {
                    Diagram diagram = getDiagram(objToOpen);

                    if (diagram != null && diagram.eResource() != null) {
                        URI uri = EcoreUtil.getURI(diagram);

                        if (uri != null) {
                            String editorTitle =
                                    ((Organization) objToOpen).getDisplayName();
                            IEditorPart editor =
                                    IDE.openEditor(page,
                                            new URIEditorInput(uri, editorTitle),
                                            SUB_DIAGRAM_EDITOR_ID);

                            if (editor instanceof IGotoObject) {
                                ((IGotoObject) editor).reveal(eo);
                            }
                            return editor != null;
                        }
                    } else {
                        // Open the main diagram
                        return openOrganizationModel(page, eo);
                    }
                }
            }
        }

        return false;
    }

    /**
     * Open the main Organization Model diagram for the given object and
     * highlight it.
     * 
     * @param page
     * @param eo
     * @return
     * @throws PartInitException
     */
    private static boolean openOrganizationModel(IWorkbenchPage page, EObject eo)
            throws PartInitException {
        // Open the org model editor
        IFile file = WorkingCopyUtil.getFile(eo);
        if (file != null) {
            IEditorPart editor = IDE.openEditor(page, file);
            if (editor instanceof IGotoObject && !(eo instanceof OrgModel)) {
                ((IGotoObject) editor).reveal(eo);
            }
            return editor != null;
        }

        return false;
    }

    /**
     * Check if the given {@link OrgUnitRelationship} is a cross-Organization
     * relationship.
     * 
     * @param relationship
     * @return
     */
    private static boolean isCrossOrganizationRelation(
            OrgUnitRelationship relationship) {
        if (relationship != null) {
            OrgUnit from = relationship.getFrom();
            OrgUnit to = relationship.getTo();

            return to != null && from != null
                    && to.eContainer() != from.eContainer();
        }
        return false;
    }

    /**
     * Get the {@link Diagram} of the given semantic element.
     * 
     * @param semanticElement
     * @return
     */
    private static Diagram getDiagram(EObject semanticElement) {
        if (semanticElement != null) {
            ECrossReferenceAdapter adapter =
                    ECrossReferenceAdapter
                            .getCrossReferenceAdapter(semanticElement);
            if (adapter != null) {
                Collection<Setting> references =
                        adapter.getInverseReferences(semanticElement);

                if (references != null) {
                    for (Setting ref : references) {
                        if (ref.getEObject() instanceof Diagram) {
                            return (Diagram) ref.getEObject();
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
            OrganizationModelDiagramEditorPlugin
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
                    Messages.OrganizationModelDiagramEditorUtil_CreateDiagramDefaultFileNameLabel;
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
                OrganizationModelDiagramEditorPlugin.getInstance()
                        .getDialogSettings();
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
     * @param params
     *            additional parameters and settings for initial diagram.
     * @throws CoreException
     */
    public static Resource createDiagram(URI diagramURI,
            IProgressMonitor progressMonitor, final Map<String, Object> params)
            throws CoreException {

        String modelDisplayName = null;

        // Get the Org Model name from the user profile settings
        if (diagramURI.isPlatformResource()) {
            String path = diagramURI.toPlatformString(true);
            if (path != null) {
                IFile file =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(new Path(path));
                if (file != null && file.getProject() != null) {
                    modelDisplayName =
                            UserInfoUtil
                                    .getProjectPreferences(file.getProject())
                                    .getOrganisationName();
                }
            }
        }

        return createDiagram(modelDisplayName != null ? modelDisplayName
                : trimFileExtension(diagramURI.lastSegment()),
                null,
                diagramURI,
                progressMonitor,
                params);
    }

    /**
     * This method should be called within a workspace modify operation since it
     * creates resources.
     * 
     * @param modelDisplayName
     *            the display name to give the Organization Model
     * @param orgDisplayLabel
     *            the display name to give the Organization
     * @param diagramURI
     *            URI of the model file
     * @param progressMonitor
     *            monitor to report progress, can be <code>null</code>.
     * @param params
     *            additional parameters and settings for initial diagram.
     * @throws CoreException
     * @since 3.5
     */
    public static Resource createDiagram(final String modelDisplayName,
            final String orgDisplayLabel, URI diagramURI,
            IProgressMonitor progressMonitor, final Map<String, Object> params)
            throws CoreException {

        /*
         * Created so that the RCP application can create an OrganizationModel
         * of a given name (and Organization name).
         */
        if (progressMonitor == null) {
            progressMonitor = new NullProgressMonitor();
        }

        Map<String, Object> emptyMap = Collections.emptyMap();
        final Map<String, Object> initialParams =
                (params != null) ? params : emptyMap;

        // Make sure its our editing domain
        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        progressMonitor
                .beginTask(Messages.OrganizationModelDiagramEditorUtil_CreateDiagramProgressTask,
                        3);
        final Resource diagramResource =
                editingDomain.getResourceSet().createResource(diagramURI);
        final String diagramName = diagramURI.lastSegment();
        AbstractTransactionalCommand command =
                new AbstractTransactionalCommand(
                        editingDomain,
                        Messages.OrganizationModelDiagramEditorUtil_CreateDiagramCommandLabel,
                        Collections.EMPTY_LIST) {
                    @Override
                    protected CommandResult doExecuteWithResult(
                            IProgressMonitor monitor, IAdaptable info)
                            throws ExecutionException {

                        OrgModel model =
                                createInitialModel(initialParams,
                                        diagramResource);
                        model.setDisplayName(modelDisplayName);
                        // model.setDisplayName("MyOrg");
                        attachModelToResource(model, diagramResource);

                        Diagram diagram =
                                ViewService
                                        .createDiagram(model,
                                                OrgModelEditPart.MODEL_ID,
                                                OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

                        if (diagram != null) {
                            diagramResource.getContents().add(diagram);
                            diagram.setName(diagramName);
                            diagram.setElement(model);

                            // Start of our custom code open up subdiagram

                            /*
                             * We need to create a Node for the Organization
                             * element we created in the initial model. This
                             * ensures that View notation for the Organization
                             * is written to file. We need this so that we can
                             * get hold of the HintedDiagramLinkStyle of the
                             * Organization View which links the organization
                             * element on the main diagram to its sub-diagram.
                             */

                            Node badgeNode =
                                    ViewService
                                            .createNode(diagram,
                                                    model,
                                                    OrgModelBadgeEditPart.VISUAL_ID,
                                                    OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

                            LayoutConstraint constraintBadge =
                                    badgeNode.getLayoutConstraint();

                            if (constraintBadge instanceof Bounds) {
                                ((Bounds) constraintBadge).setX(5);
                                ((Bounds) constraintBadge).setY(5);
                            }

                            Organization org = model.getOrganizations().get(0);

                            if (org != null) {
                                // If the Organization name is supplied then set
                                // it
                                if (orgDisplayLabel != null
                                        && orgDisplayLabel.length() > 0) {
                                    org.setDisplayName(orgDisplayLabel);
                                }

                                OrganizationModelNodeDescriptor desc =
                                        new OrganizationModelNodeDescriptor(
                                                org,
                                                OrganizationEditPart.VISUAL_ID);

                                Node nodeInitialOrg =
                                        ViewService
                                                .createNode(diagram,
                                                        org,
                                                        desc.getType(),
                                                        OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

                                LayoutConstraint constraintInitialOrg =
                                        nodeInitialOrg.getLayoutConstraint();

                                if (constraintInitialOrg instanceof Bounds) {
                                    ((Bounds) constraintInitialOrg).setX(270);
                                    ((Bounds) constraintInitialOrg).setY(50);
                                }

                                Style diagLink =
                                        nodeInitialOrg
                                                .getStyle(NotationPackage.eINSTANCE
                                                        .getHintedDiagramLinkStyle());

                                if (diagLink instanceof HintedDiagramLinkStyle) {
                                    HintedDiagramLinkStyle diagramFacet =
                                            (HintedDiagramLinkStyle) diagLink;

                                    Diagram subDiagram =
                                            diagramFacet.getDiagramLink();

                                    if (subDiagram == null) {
                                        // Initialize the sub diagram
                                        Diagram initialSubDiagram =
                                                ViewService
                                                        .createDiagram(org,
                                                                SUB_DIAGRAM_ID,
                                                                OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

                                        if (initialSubDiagram == null) {
                                            throw new ExecutionException(
                                                    String.format(Messages.OrganizationModelDiagramEditorUtil_cannotCreateDiagramKind_error_shortdesc,
                                                            SUB_DIAGRAM_ID));
                                        }

                                        diagramFacet
                                                .setDiagramLink(initialSubDiagram);
                                        diagramResource.getContents()
                                                .add(initialSubDiagram);

                                        EObject container =
                                                diagramFacet.eContainer();
                                        while (container instanceof View) {
                                            ((View) container).persist();
                                            container = container.eContainer();

                                        }

                                        // Create badge for the sub-diagram
                                        // (Organization)
                                        ViewService
                                                .createNode(initialSubDiagram,
                                                        org,
                                                        OrganizationSubBadgeEditPart.VISUAL_ID,
                                                        OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

                                        LayoutConstraint constraintSubDiagBadge =
                                                badgeNode.getLayoutConstraint();

                                        if (constraintSubDiagBadge instanceof Bounds) {
                                            ((Bounds) constraintBadge).setX(0);
                                            ((Bounds) constraintBadge).setY(0);

                                        }
                                    }
                                }
                            }
                        }
                        try {

                            diagramResource
                                    .save(com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil
                                            .getSaveOptions());
                        } catch (IOException e) {
                            IFile file =
                                    WorkspaceSynchronizer
                                            .getFile(diagramResource);
                            throw new ExecutionException(
                                    file != null ? String
                                            .format(Messages.OrganizationModelDiagramEditorUtil_problemCreatingOrgModel_error_message,
                                                    file.getFullPath()
                                                            .toString())
                                            : Messages.OrganizationModelDiagramEditorUtil_problemCreatingOrgModel_genericError_message,
                                    e);
                        }
                        return CommandResult.newOKCommandResult();
                    }

                    @Override
                    public boolean canUndo() {
                        // Should not be able to undo the creation of a new
                        // model.
                        return false;
                    }
                };
        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new SubProgressMonitor(progressMonitor, 1),
                    null);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof CoreException) {
                throw (CoreException) e.getCause();
            } else {
                throw new CoreException(new Status(IStatus.ERROR,
                        OrganizationModelDiagramEditorPlugin.ID,
                        e.getLocalizedMessage(), e.getCause()));
            }
        }

        setCharset(WorkspaceSynchronizer.getFile(diagramResource));
        return diagramResource;

    }

    /**
     * Create a new instance of domain element associated with canvas.
     * 
     * @return OrgModel
     */
    private static OrgModel createInitialModel(Map<String, Object> params,
            Resource diagramResource) {
        String projPrefUserName = null;

        OrgModel om = createInitialModelGen();

        IFile file = WorkspaceSynchronizer.getFile(diagramResource);
        if (file != null) {
            projPrefUserName =
                    UserInfoUtil.getProjectPreferences(file.getProject())
                            .getUserName();
        }
        // Human Resource Type
        ResourceType humanResourceType =
                OMFactory.eINSTANCE.createResourceType();
        humanResourceType
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_humanResourceType_label);
        om.getEmbeddedMetamodel().getResourceTypes().add(humanResourceType);
        om.setHumanResourceType(humanResourceType);

        if (null != projPrefUserName) {
            if (projPrefUserName.length() > 0) {
                om.setAuthor(projPrefUserName);
            } else {
                om.setAuthor(Messages.OrganizationModelDiagramEditorUtil_authorNotSet_label);
            }
        } else {
            om.setAuthor(Messages.OrganizationModelDiagramEditorUtil_authorNotSet_label);
        }

        Calendar cal = Calendar.getInstance();
        long time = cal.getTimeInMillis();
        om.setDateCreated(time);

        /*
         * Sid ACE-1354 - GIVEN that there is a validation rule that has always
         * ensured that the organisation version exactly matches the project
         * version THEN we can get rid of the organisation version altogether
         * and use the parent Project version instead.
         */

        Organization org = OMFactory.eINSTANCE.createOrganization();

        IEditingDomainItemProvider provider =
                (IEditingDomainItemProvider) XpdResourcesPlugin.getDefault()
                        .getAdapterFactory()
                        .adapt(om, IEditingDomainItemProvider.class);

        String defaultName = null;

        if (provider instanceof OrgModelItemProvider) {
            OrgModelItemProvider orgProvider = (OrgModelItemProvider) provider;

            String prefix =
                    orgProvider.getString("_UI_DefaultName_Organisation_label"); //$NON-NLS-1$

            Collection<String> displayNamesArray =
                    OMUtil.getDisplayNamesArray(om.getOrganizations());

            defaultName = OMUtil.getDefaultName(prefix, displayNamesArray);
        }

        org.setDisplayName(defaultName != null ? defaultName : ""); //$NON-NLS-1$
        om.getOrganizations().add(org);

        // If create default embedded meta-model (schema).
        Object createMMValue = params.get(CREATE_DEFAULT_METAMODEL_PARAM);
        if (createMMValue instanceof Boolean
                && createMMValue.equals(Boolean.TRUE)) {
            OrgMetaModel metaModel = om.getEmbeddedMetamodel();
            PositionType standardPositionType =
                    createStandardPositionType(metaModel);
            // createStandardTypes(metaModel, standardPositionType);
            createPublicCompanyTypes(metaModel, standardPositionType);
            createAdditionalTypes(metaModel);

            Object applyStandardOrgValue =
                    params.get(APPLY_STANDARD_TYPE_PARAM);

            if (applyStandardOrgValue instanceof Boolean
                    && applyStandardOrgValue.equals(Boolean.TRUE)) {
                // Apply the Standard Organization defined in the default schema
                org.setOrganizationType(metaModel.getOrganizationTypes().get(0));

            }

        }
        return om;
    }

    /**
     * @param metaModel
     */
    private static void createAdditionalTypes(OrgMetaModel metaModel) {
        OMFactory f = OMFactory.eINSTANCE;
        // Location Type
        LocationType locationType = f.createLocationType();
        locationType
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_stdLocationType_label);
        String[] stringLocationParameters =
                new String[] {
                        Messages.OrganizationModelDiagramEditorUtil_stdLocationType_country_label,
                        Messages.OrganizationModelDiagramEditorUtil_stdLocationType_state_label,
                        Messages.OrganizationModelDiagramEditorUtil_stdLocationType_city_label,
                        Messages.OrganizationModelDiagramEditorUtil_stdLocationType_street_label,
                        Messages.OrganizationModelDiagramEditorUtil_stdLocationType_postcode_label };
        for (String paramName : stringLocationParameters) {
            Attribute paramFeature = f.createAttribute();
            paramFeature.setDisplayName(paramName);
            paramFeature.setType(AttributeType.TEXT);
            locationType.getAttributes().add(paramFeature);
        }
        metaModel.getLocationTypes().add(locationType);

        // Resource Types
        // Consumable Resource Type
        OrgModel orgModel = (OrgModel) metaModel.eContainer();
        ResourceType consumableResourceType =
                OMFactory.eINSTANCE.createResourceType();
        consumableResourceType
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_consumableResourceType_label);
        metaModel.getResourceTypes().add(consumableResourceType);
        orgModel.setConsumableResourceType(consumableResourceType);

        // Durable Resource Type
        ResourceType durableResourceType =
                OMFactory.eINSTANCE.createResourceType();
        durableResourceType
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_durableResourceType_label);
        metaModel.getResourceTypes().add(durableResourceType);
        orgModel.setDurableResourceType(durableResourceType);

    }

    /**
     * Creates standard types for OM model.
     * 
     * @param metaModel
     *            Organization meta model.
     * @param standardPositionType2
     */
    private static void createStandardTypes(OrgMetaModel metaModel,
            PositionType standardPositionType) {

        OMFactory f = OMFactory.eINSTANCE;

        // OrgUnit Type
        OrgUnitType standardOrgUnitType = f.createOrgUnitType();
        standardOrgUnitType
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_stdOrgUnitType_label);

        OrgUnitFeature unitOrgUnitFeature = f.createOrgUnitFeature();
        unitOrgUnitFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_subUnitFeature_label);
        unitOrgUnitFeature.setLowerBound(0);
        unitOrgUnitFeature.setUpperBound(-1);
        unitOrgUnitFeature.setFeatureType(standardOrgUnitType);

        PositionFeature managerPositionFeature = f.createPositionFeature();
        managerPositionFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_managerPosition_label);
        managerPositionFeature.setLowerBound(0);
        managerPositionFeature.setUpperBound(1);
        managerPositionFeature.setFeatureType(standardPositionType);

        PositionFeature memberPositionFeature = f.createPositionFeature();
        memberPositionFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_memberPosition_label);
        memberPositionFeature.setLowerBound(0);
        memberPositionFeature.setUpperBound(-1);
        memberPositionFeature.setFeatureType(standardPositionType);

        standardOrgUnitType.getOrgUnitFeatures().add(unitOrgUnitFeature);
        standardOrgUnitType.getPositionFeatures().add(managerPositionFeature);
        standardOrgUnitType.getPositionFeatures().add(memberPositionFeature);

        metaModel.getOrgUnitTypes().add(standardOrgUnitType);

        OrganizationType standardOrganizationType = f.createOrganizationType();
        standardOrganizationType
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_stdOrgType_label);

        OrgUnitFeature headOrgUnitFeature = f.createOrgUnitFeature();
        headOrgUnitFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_headUnit_label);
        headOrgUnitFeature.setLowerBound(0);
        headOrgUnitFeature.setUpperBound(1);
        headOrgUnitFeature.setFeatureType(standardOrgUnitType);

        standardOrganizationType.getOrgUnitFeatures().add(headOrgUnitFeature);

        metaModel.getOrganizationTypes().add(standardOrganizationType);
    }

    /**
     * Creates standard types for OM model.
     * 
     * @param metaModel
     *            Organization meta model.
     */
    private static void createPublicCompanyTypes(OrgMetaModel metaModel,
            PositionType standardPositionType) {

        OMFactory f = OMFactory.eINSTANCE;

        // OrgUnit Type
        OrgUnitType departmentOrgUnitType = f.createOrgUnitType();
        departmentOrgUnitType
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_departmentType_label);

        OrgUnitFeature departmentUnitOrgUnitFeature = f.createOrgUnitFeature();
        departmentUnitOrgUnitFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_departmentMember_label);
        departmentUnitOrgUnitFeature.setLowerBound(0);
        departmentUnitOrgUnitFeature.setUpperBound(-1);
        departmentUnitOrgUnitFeature.setFeatureType(departmentOrgUnitType);

        OrgUnitFeature businessUnitOrgUnitFeature = f.createOrgUnitFeature();
        businessUnitOrgUnitFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_businessUnitMember_label);
        businessUnitOrgUnitFeature.setLowerBound(0);
        businessUnitOrgUnitFeature.setUpperBound(-1);
        businessUnitOrgUnitFeature.setFeatureType(departmentOrgUnitType);

        OrgUnitFeature teamUnitOrgUnitFeature = f.createOrgUnitFeature();
        teamUnitOrgUnitFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_teamMember_label);
        teamUnitOrgUnitFeature.setLowerBound(0);
        teamUnitOrgUnitFeature.setUpperBound(-1);
        teamUnitOrgUnitFeature.setFeatureType(departmentOrgUnitType);

        PositionFeature managerPositionFeature = f.createPositionFeature();
        managerPositionFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_managerPosition_label);
        managerPositionFeature.setLowerBound(0);
        managerPositionFeature.setUpperBound(1);
        managerPositionFeature.setFeatureType(standardPositionType);

        PositionFeature memberPositionFeature = f.createPositionFeature();
        memberPositionFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_memberPosition_label);
        memberPositionFeature.setLowerBound(0);
        memberPositionFeature.setUpperBound(-1);
        memberPositionFeature.setFeatureType(standardPositionType);

        departmentOrgUnitType.getOrgUnitFeatures()
                .add(departmentUnitOrgUnitFeature);
        departmentOrgUnitType.getOrgUnitFeatures()
                .add(businessUnitOrgUnitFeature);
        departmentOrgUnitType.getOrgUnitFeatures().add(teamUnitOrgUnitFeature);
        departmentOrgUnitType.getPositionFeatures().add(managerPositionFeature);
        departmentOrgUnitType.getPositionFeatures().add(memberPositionFeature);

        metaModel.getOrgUnitTypes().add(departmentOrgUnitType);

        // Organization Type
        OrganizationType publicCompany = f.createOrganizationType();
        publicCompany
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_publicCompany_label);

        OrgUnitFeature headOrgUnitFeature = f.createOrgUnitFeature();
        headOrgUnitFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_headDepartment_label);
        headOrgUnitFeature.setLowerBound(0);
        headOrgUnitFeature.setUpperBound(1);
        headOrgUnitFeature.setFeatureType(departmentOrgUnitType);

        publicCompany.getOrgUnitFeatures().add(headOrgUnitFeature);

        metaModel.getOrganizationTypes().add(publicCompany);
    }

    private static PositionType createStandardPositionType(
            OrgMetaModel metaModel) {

        OMFactory f = OMFactory.eINSTANCE;

        // Position Type
        PositionType standardPositionType = f.createPositionType();
        standardPositionType
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_stdPositionType_label);

        Attribute kindAttributeFeature = f.createAttribute();
        kindAttributeFeature
                .setDisplayName(Messages.OrganizationModelDiagramEditorUtil_contractType_label);
        kindAttributeFeature.setType(AttributeType.ENUM);
        String[] kindValues =
                new String[] {
                        Messages.OrganizationModelDiagramEditorUtil_contractType_temporary_label,
                        Messages.OrganizationModelDiagramEditorUtil_contractType_permanent_label };
        for (String value : kindValues) {
            EnumValue kindValue = f.createEnumValue();
            kindValue.setValue(value);
            kindAttributeFeature.getValues().add(kindValue);
        }
        standardPositionType.getAttributes().add(kindAttributeFeature);

        metaModel.getPositionTypes().add(standardPositionType);
        return standardPositionType;
    }

    /**
     * Create a new instance of domain element associated with canvas. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static OrgModel createInitialModelGen() {
        return OMFactory.eINSTANCE.createOrgModel();
    }

    /**
     * Store model element in the resource. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    private static void attachModelToResource(OrgModel model, Resource resource) {
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
         * @generated NOT
         */
        private final View scope;

        /**
         * @generated NOT
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

    private static String trimFileExtension(String fileName) {
        String trimmedName = fileName;

        int lastIndex = trimmedName.lastIndexOf("."); //$NON-NLS-1$

        if (lastIndex > -1) {
            trimmedName = trimmedName.substring(0, lastIndex);
        }

        return trimmedName;
    }
}
