/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.resources.wc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.xmi.ClassNotFoundException;
import org.eclipse.emf.ecore.xmi.PackageNotFoundException;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.AbstractTransactionalCommandStack;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.bom.resources.migration.BOMMigrationExtensionHelper;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.resources.wc.InvalidVersionException;
import com.tibco.xpd.resources.wc.WorkingCopySaveable;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * BOM working copy.
 * 
 * @author njpatel
 * 
 */
public class BOMWorkingCopy extends AbstractGMFWorkingCopy {

    public static final String JAVA_PACKAGE_SEPARATOR = "."; //$NON-NLS-1$

    public static final String UML_PACKAGE_SEPARATOR = "::"; //$NON-NLS-1$

    /**
     * Name of cached attribute that contains comma-separated list of fully
     * qualified package names.
     */
    public static final String PACKAGE_NAMES_CACHE_ATTRIB = "PackageNames"; //$NON-NLS-1$

    private Saveable saveable;

    /**
     * BOM Working copy.
     * 
     * @param resources
     *            list of <code>IResource</code>s to be maintained by this
     *            working copy.
     */
    public BOMWorkingCopy(List<IResource> resources) {
        super(resources);
    }

    /**
     * Force cleanup of the working copy (unloads the EMF resource etc.
     * 
     * @since 3.5
     */
    public void forceCleanup() {
        cleanup();
    }

    @Override
    protected void doMigrateToLatestVersion() throws CoreException {
        try {
            Resource resource = super.loadResource(getFirstResource());
            if (resource != null) {
                Model model = null;
                for (EObject eo : resource.getContents()) {
                    if (eo instanceof Model) {
                        model = (Model) eo;
                        break;
                    }
                }

                if (model != null) {
                    int modelVersion = getModelVersion(model);
                    int currVersion =
                            Integer.parseInt(BOMResourcesPlugin.BOM_VERSION);

                    migrate(getEditingDomain(),
                            model,
                            modelVersion,
                            currVersion);
                    resource.save(null);
                }
            }
        } catch (Exception e) {
            if (e.getCause() instanceof CoreException) {
                throw (CoreException) e.getCause();
            } else {
                throw new CoreException(new Status(IStatus.ERROR,
                        BOMResourcesPlugin.PLUGIN_ID, e.getLocalizedMessage(),
                        e.getCause()));
            }
        }
    }

    /**
     * Migrate the given Model to the given version.
     * 
     * @param ed
     * @param model
     * @param modelVersion
     * @param currVersion
     * @throws CoreException
     */
    private void migrate(EditingDomain ed, Model model, int modelVersion,
            int currVersion) throws CoreException {
        CommandStack commandStack = ed.getCommandStack();
        List<Command> commands = new ArrayList<Command>();
        for (int ver = modelVersion + 1; ver <= currVersion; ver++) {
            List<Command> migrationCommands =
                    getMigrationCommands((TransactionalEditingDomain) ed,
                            model,
                            ver);
            if (migrationCommands != null) {
                commands.addAll(migrationCommands);
            }
        }
        // Finally apply the latest version to the model
        commands.add(new AddBOMVersionCommand(ed, model));

        CompoundCommand ccmd =
                new CompoundCommand(
                        String.format(Messages.BOMWorkingCopy_migratingModel_command_shortdesc,
                                modelVersion,
                                currVersion), commands);
        if (ccmd.canExecute()) {
            if (commandStack instanceof AbstractTransactionalCommandStack) {
                try {
                    /*
                     * Don't fire pre-commit listeners as we don't want the XSD
                     * schema check listener to fire and pop up a dialog to the
                     * user saying that this change should not be made.
                     */
                    ((AbstractTransactionalCommandStack) commandStack)
                            .execute(ccmd,
                                    Collections
                                            .singletonMap(Transaction.OPTION_NO_TRIGGERS,
                                                    Boolean.TRUE));
                } catch (Exception e) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    BOMResourcesPlugin.PLUGIN_ID,
                                    String.format(Messages.BOMWorkingCopy_problemMigrating_error_shortdesc,
                                            model.eResource().getURI()
                                                    .toPlatformString(true)), e));

                }
            } else {
                ed.getCommandStack().execute(ccmd);
            }
        } else {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            BOMResourcesPlugin.PLUGIN_ID,
                            String.format(Messages.BOMWorkingCopy_UnableToMigrate_error_shortdesc,
                                    model.eResource().getURI()
                                            .toPlatformString(true),
                                    modelVersion,
                                    currVersion)));
        }
    }

    /**
     * 
     * Returns a list of the required migration commands.
     * 
     * @param editingdomain
     * @param model
     * @param version
     * @return List of {@link Command}s to migrate the BOM to the given version,
     *         empty list if there are no registered command for the given
     *         version.
     */
    private List<Command> getMigrationCommands(TransactionalEditingDomain ed,
            Model model, int version) {
        // Get commands from any BOM Migration extension point
        return BOMMigrationExtensionHelper.getInstance()
                .getMigrationCommands(ed, model, version);
    }

    /**
     * 
     * @param r
     * @param kind
     * @return <code>true</code> if the BOM is in generated BOMs folder.
     */
    protected static boolean inGeneratedBOMFolder(IResource r, String kind) {
        List<SpecialFolder> genSFs =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(r.getProject(),
                        kind);

        if (genSFs != null) {
            for (SpecialFolder sf : genSFs) {
                IFolder folder = sf.getFolder();
                if (folder != null) {
                    /*
                     * Sid ACE-1327 the check for is generated should be based
                     * on the generated type configuration NOT the generated
                     * business objects folder name because that is
                     * localisable!!
                     */
                    if (folder.getFullPath().isPrefixOf(r.getFullPath())
                            && BOMResourcesPlugin.GENERATED_BOM_FOLDER_TYPE
                                    .equals(sf.getGenerated())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Saveable getSaveable() {
        if (saveable == null) {
            saveable = new WorkingCopySaveable(this) {

                /*
                 * Warns user if the changes are saved to a generated file.
                 */
                @Override
                public void doSave(IProgressMonitor monitor)
                        throws CoreException {

                    IResource res = getEclipseResources().get(0);
                    if (inGeneratedBOMFolder(res,
                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
                        String title = Messages.BOMWorkingCopy_BOMMessage_title;
                        String message =
                                String.format(Messages.BOMWorkingCopy_ModelGeneratedModelChange_message,
                                        res.getName());

                        MessageDialog dialog =
                                new MessageDialog(
                                        PlatformUI.getWorkbench()
                                                .getActiveWorkbenchWindow()
                                                .getShell(),
                                        title,
                                        null, // accept
                                        // the
                                        // default
                                        // window
                                        // icon
                                        message,
                                        MessageDialog.QUESTION,
                                        new String[] {
                                                Messages.BOMWorkingCopy_Revert_button,
                                                Messages.BOMWorkingCopy_SaveAnyway_button,
                                                IDialogConstants.CANCEL_LABEL },
                                        0); // Revert is the default
                        int answer = dialog.open();
                        switch (answer) {
                        case 0: // Revert
                            BOMWorkingCopy.this.reLoad();
                            break;
                        case 1: // Save Anyway
                            super.doSave(monitor);
                            break;
                        case 3: // Cancel
                            // do noting
                            break;
                        default:
                            // do nothing
                            break;
                        }

                    } else {
                        super.doSave(monitor);
                    }
                }
            };
        }
        return saveable;
    }

    @Override
    protected Resource loadResource(IResource resource)
            throws InvalidFileException {
        Resource res = super.loadResource(resource);
        // Check that the version of this BOM is valid otherwise throw an
        // exception
        EObject model = getModelFromResource(res);
        if (model instanceof Model) {
            int version = getModelVersion((Model) model);
            if (version < Integer.parseInt(BOMResourcesPlugin.BOM_VERSION)) {
                // Unload the resource and throw exception
                res.unload();
                throw new InvalidVersionException(
                        Messages.BOMWorkingCopy_invalidVersion_exception_shortdesc);
            }
        }
        return res;
    }

    @Override
    protected EObject getModelFromResource(Resource res) {
        // Get the Model object from the resource
        if (res != null && res.getContents() != null) {
            for (EObject content : res.getContents()) {
                if (content instanceof Model) {
                    return content;
                }
            }
        }
        return null;
    }

    /**
     * Get the model version.
     * 
     * @param model
     * @return
     * @since 3.5
     */
    private int getModelVersion(Model model) {
        int ver = 0;
        EAnnotation annotation =
                model.getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);

        if (annotation != null) {
            String value =
                    annotation
                            .getDetails()
                            .get(BOMResourcesPlugin.ModelEannotationMetaSource_version);

            if (value != null) {
                try {
                    ver = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    ver = 0;
                    BOMResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    String.format(Messages.BOMWorkingCopy_invalidVersion_error_shortdesc,
                                            getFirstResource().getFullPath()
                                                    .toString()));
                }
            }
        }
        return ver;
    }

    @Override
    protected IStatus getErrorStatus(EList<Diagnostic> errors,
            IResource resource) {
        /*
         * If all the errors reported are PackageNotFound or ClassNotFound
         * exceptions then they should be downgraded to Warnings. This will
         * allow the model to be loaded and the user will then be able to
         * correct the problem (usually happens if a profile being referenced is
         * not available).
         */
        if (errors != null && resource != null) {
            List<Diagnostic> notFoundExceptions = new ArrayList<Diagnostic>();
            for (Diagnostic error : errors) {
                if (error instanceof PackageNotFoundException
                        || error instanceof ClassNotFoundException) {
                    notFoundExceptions.add(error);
                }
            }

            if (errors.size() > 0 && errors.size() == notFoundExceptions.size()) {
                List<IStatus> status = new ArrayList<IStatus>();
                /*
                 * All errors are for Package or Class not found which is
                 * probably due to a missing profile so report as warnings and
                 * continue loading the model.
                 */
                for (Diagnostic error : notFoundExceptions) {
                    status.add(new Status(IStatus.WARNING,
                            BOMResourcesPlugin.PLUGIN_ID, error.getMessage()));
                }

                return new MultiStatus(
                        BOMResourcesPlugin.PLUGIN_ID,
                        0,
                        status.toArray(new IStatus[status.size()]),
                        String.format(Messages.BOMWorkingCopy_warningsDuringLoad_message,
                                resource.getFullPath().toString()), null);
            }
        }

        return super.getErrorStatus(errors, resource);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
     */
    @Override
    public EPackage getWorkingCopyEPackage() {
        return UMLPackage.eINSTANCE;
    }

    /**
     * Produce fully qualified name of the concept. It differ from uml qualified
     * name as it doesn't use model name as prefix, and use '.' as section
     * separator.
     * 
     * @param cl
     * @return
     */
    public static String getQualifiedClassName(NamedElement cl) {
        return getQualifiedName(cl.getQualifiedName());
    }

    /**
     * @see ConceptWorkingCopy#getQualifiedClassName(Class)
     */
    public static String getQualifiedPackageName(Package aPackage) {
        return getQualifiedName(aPackage.getQualifiedName());
    }

    public static String getQualifiedName(String rawName) {
        if (rawName == null)
            return ""; //$NON-NLS-1$
        // removed, because it looks like we don't need this any more
        // rawName = rawName.substring(rawName.indexOf(UML_PACKAGE_SEPARATOR) +
        // 2);

        rawName =
                rawName.replace(UML_PACKAGE_SEPARATOR, JAVA_PACKAGE_SEPARATOR);
        return rawName;
    }

    @Override
    public String getMetaText(EObject eo) {

        String text = _getMetaText(eo);

        return text != null ? text : super.getMetaText(eo);
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#doCheckFileName(java.lang.String)
     * 
     * @param fileName
     * @return
     */
    @Override
    protected String doCheckFileName(IResource resource) {
        /*
         * There is a problem (currently if there are spaces or other characters
         * that get encoded when converted to URI).
         * 
         * This in the fact that some things (especially xpdl process references
         * to BOM files) store the path as a URI BUT not all readers read it as
         * a URI and hence try to use the encoded URI (with %20 etc) as a file
         * name which then fails.
         * 
         * So this short term fix is to ensure that the encoded name is the same
         * as the original - then we should be ok.
         */

        if (SpecialFolderUtil.getSpecialFolderRelativePath(resource,
                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                false) != null) {

            String resourceName = resource.getName();
            if (resourceName != null) {

                String encodedPathString =
                        URI.encodeFragment(resourceName, false);

                if (!resourceName.equals(encodedPathString)) {
                    return Messages.BOMWorkingCopy_InvalidBOMFileName;
                }
            }
        }

        return super.doCheckFileName(resource);
    }

    /**
     * Package level method shared with {@link InputStreamBOMWorkingCopy}.
     * 
     * @param eo
     * @return
     */
    /* Package */static String _getMetaText(EObject eo) {
        if (eo instanceof Element) {
            Element elem = (Element) eo;

            // Show the first class support Stereotype name rather then the
            // element name if the Stereotype is public
            if (FirstClassProfileManager.getInstance()
                    .isFirstClassProfileApplied(elem.getModel())) {
                EList<Stereotype> appliedStereotypes =
                        elem.getAppliedStereotypes();

                if (!appliedStereotypes.isEmpty()) {
                    Stereotype stereotype = appliedStereotypes.get(0);
                    if (stereotype != null
                            && (!stereotype.isSetVisibility() || stereotype
                                    .getVisibility() != VisibilityKind.PRIVATE_LITERAL)
                            && stereotype.getLabel() != null) {
                        return stereotype.getLabel();
                    }
                }
            }
        }

        if (eo instanceof AssociationClass) {
            return Messages.BOMWorkingCopy_associationClass_label;
        } else if (eo instanceof Association) {
            AggregationKind assocType =
                    UML2ModelUtil.getAggregationType((Association) eo);
            switch (assocType) {
            case COMPOSITE_LITERAL:
                return Messages.BOMWorkingCopy_composition_label;
            case SHARED_LITERAL:
                return Messages.BOMWorkingCopy_agregation_label;
            case NONE_LITERAL:
            default:
                return Messages.BOMWorkingCopy_association_label;
            }

        } else if (eo instanceof Model) {
            return Messages.BOMWorkingCopy_model_label;
        } else if (eo instanceof Package) {
            return Messages.BOMWorkingCopy_package_label;
        } else if (eo instanceof PrimitiveType) {
            return Messages.BOMWorkingCopy_primitive_type_label;
        } else if (eo instanceof Class) {
            return Messages.BOMWorkingCopy_class_label;
        } else if (eo instanceof Property) {
            return Messages.BOMWorkingCopy_attribute_label;
        } else if (eo instanceof Operation) {
            return Messages.BOMWorkingCopy_operation_label;
        } else if (eo instanceof Generalization) {
            return Messages.BOMWorkingCopy_generalization_label;
        } else if (eo instanceof Enumeration) {
            return Messages.BOMWorkingCopy_enum_label;
        } else if (eo instanceof EnumerationLiteral) {
            return Messages.BOMWorkingCopy_enumLiteral_label;
        } else if (eo instanceof Diagram) {
            return Messages.BOMWorkingCopy_Diagram_label;
        }
        return null;
    }

    /**
     * 
     * Adds a the version eAnnotation to the BOM model.
     * 
     * @author rgreen
     * 
     */
    private static class AddBOMVersionCommand extends RecordingCommand {

        private final Model model;

        public AddBOMVersionCommand(EditingDomain ed, Model model) {
            super((TransactionalEditingDomain) ed);
            this.model = model;
        }

        @Override
        protected void doExecute() {
            EAnnotation annotation = getAnnotation(model);
            if (annotation != null) {
                setModelVersion(annotation, BOMResourcesPlugin.BOM_VERSION);
            } else {
                createAnnotation(WorkingCopyUtil.getProjectFor(model),
                        model,
                        BOMResourcesPlugin.BOM_VERSION);
            }
        }

        /**
         * Set the version of the Model to the given version.
         * 
         * @param model
         * @param version
         */
        private void setModelVersion(EAnnotation annotation, String version) {
            annotation.getDetails()
                    .put(BOMResourcesPlugin.ModelEannotationMetaSource_version,
                            version);

        }

        /**
         * Create the annotation to contain the additional info for this Model.
         * 
         * @param project
         * @param model
         * @param bomVersion
         */
        private void createAnnotation(IProject project, Model model,
                String bomVersion) {
            EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
            annotation.setSource(BOMResourcesPlugin.ModelEannotationMetaSource);
            model.getEAnnotations().add(annotation);

            EMap<String, String> details = annotation.getDetails();

            String user =
                    project != null ? UserInfoUtil
                            .getProjectPreferences(project).getUserName()
                            : UserInfoUtil.getWorkspacePreferences()
                                    .getUserName();

            details.put(BOMResourcesPlugin.ModelEannotationMetaSource_author,
                    user != null ? user : ""); //$NON-NLS-1$
            details.put(BOMResourcesPlugin.ModelEannotationMetaSource_version,
                    bomVersion);
        }

        /**
         * Get the annotation containing the model information.
         * 
         * @param model
         * @return
         */
        private EAnnotation getAnnotation(Model model) {
            EAnnotation annotation =
                    model.getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);
            return annotation;
        }
    }
}
