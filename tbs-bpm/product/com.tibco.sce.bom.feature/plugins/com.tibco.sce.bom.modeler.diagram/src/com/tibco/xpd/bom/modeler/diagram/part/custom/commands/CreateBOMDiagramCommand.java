package com.tibco.xpd.bom.modeler.diagram.part.custom.commands;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.resources.util.UserInfoUtil;

public class CreateBOMDiagramCommand extends AbstractTransactionalCommand {

    private Resource diagramResource;

    private final IFirstClassProfileExtension extension;

    private final IFragment template;

    private final String diagramName;

    private final String modelLabel;

    private TransactionalEditingDomain editingDomain;

    public CreateBOMDiagramCommand(TransactionalEditingDomain domain,
            String commandlabel, List affectedFiles, String modLbl,
            IFirstClassProfileExtension ext, IFragment tplate, Resource res,
            String diagName) {
        super(domain, commandlabel, affectedFiles);
        editingDomain = domain;
        diagramResource = res;
        extension = ext;
        template = tplate;
        modelLabel = modLbl;
        diagramName = diagName;

    }

    @Override
    protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
            IAdaptable info) throws ExecutionException {

        // Changed the initial model to be type Model rather
        // than Package. (Package is defined in the generator as
        // the Canvas element so that we can open multiple
        // editors).
        Model model = BOMUtils.createInitialModel();

        // Set the name of the Model as the filename without the
        // .bom extension
        IFile file = WorkspaceSynchronizer.getFile(diagramResource);

        String fileName = file.getName();
        int pos;
        if (fileName.endsWith(".bom")) { //$NON-NLS-1$
            pos = fileName.lastIndexOf(".bom"); //$NON-NLS-1$
            fileName = fileName.substring(0, pos).toLowerCase();
        } else {
            // For now be safe and set to something.
            // Shouldn't get here really
            fileName = "root"; //$NON-NLS-1$
        }

        // Add prefix to the model name - required for code
        // generators based on the BOM
        String domainName =
                UserInfoUtil.getProjectPreferences(file.getProject())
                        .getDomainName();
        if (domainName != null) {
            // Remove all trailing '.'
            domainName = domainName.replaceAll("\\.+$", ""); //$NON-NLS-1$ //$NON-NLS-2$
            model.setName(BOMUtils.createUniqueModelName(domainName + "." //$NON-NLS-1$
                    + fileName));
        } else {
            model.setName(fileName);
        }

        // Load the profile if first-class profile
        if (extension != null) {
            // Get the profile from the extension information
            Profile profile = extension.getProfile();
            if (profile != null) {
                model.applyProfile(profile);
                model.setViewpoint(FirstClassProfileManager.FIRST_CLASS_PREFIX
                        + extension.getId());
            }
        } else {
            IProject project = file.getProject();
            if (BOMUtils.isBusinessDataProject(project)) {
                Profile globalDataProfile =
                        BOMProfileUtils.getGlobalDataProfile();
                model.applyProfile(globalDataProfile);
            }
        }

        // Set Annotations
        EAnnotation annot =
                model.createEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);

        EMap<String, String> details = annot.getDetails();

        details.put(BOMResourcesPlugin.ModelEannotationMetaSource_author,
                UserInfoUtil.getProjectPreferences(file.getProject())
                        .getUserName());

        Calendar cal = Calendar.getInstance();
        long time = cal.getTimeInMillis();
        details.put(BOMResourcesPlugin.ModelEannotationMetaSource_datecreated,
                String.valueOf(time));

        // Set BOM version
        BOMUtils.setBOMVersion(model, BOMResourcesPlugin.BOM_VERSION);

        attachModelToResource(model, diagramResource);

        Diagram diagram =
                ViewService.createDiagram(model,
                        CanvasPackageEditPart.MODEL_ID,
                        BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        if (diagram != null) {
            diagramResource.getContents().add(diagram);
            diagram.setName(diagramName);
            diagram.setElement(model);
        }

        // If there is a model label provided then update the
        // model with it
        if (modelLabel != null && modelLabel.length() > 0) {
            PrimitivesUtil.setDisplayLabel(model, modelLabel);
        }

        // If a template is provided then apply template
        if (template != null) {
            applyTemplate(diagram, template);
        }

        try {

            diagramResource
                    .save(com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil
                            .getSaveOptions());
        } catch (IOException e) {
            throw new ExecutionException(
                    String.format(Messages.UMLDiagramEditorUtil_problemCreatingBOM_error_message,
                            file.getFullPath().toString()), e);
        }
        return CommandResult.newOKCommandResult();
    }

    /**
     * @generated
     */
    @Override
    public boolean canUndo() {
        // Don't want this command to be undo-able
        return false;
    }

    private void attachModelToResource(Package model, Resource resource) {
        resource.getContents().add(model);
    }

    /**
     * Apply the given fragment to the view.
     * 
     * @param view
     *            target view
     * @param fragment
     *            fragment template.
     */
    private void applyTemplate(View view, IFragment fragment) {
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

}
