/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDAttributeGroupDefinition;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDDerivationMethod;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDIdentityConstraintDefinition;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDInclude;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDModelGroupDefinition;
import org.eclipse.xsd.XSDNotationDeclaration;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDRedefine;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.XSDVariety;
import org.eclipse.xsd.XSDWildcard;
import org.openarchitectureware.xtend.XtendFacade;
import org.w3c.dom.Node;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.imports.progress.Xsd2BomMonitorMessage;
import com.tibco.xpd.bom.xsdtransform.imports.template.ImportTransformationData;
import com.tibco.xpd.bom.xsdtransform.imports.template.TransformHelper;
import com.tibco.xpd.bom.xsdtransform.utils.FileUtil;
import com.tibco.xpd.bom.xsdtransform.utils.NameAssignmentException;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelAttributeWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.TopLevelElementWrapper;
import com.tibco.xpd.bom.xsdtransform.utils.XSDDerivedBOMNameAssigner;
import com.tibco.xpd.bom.xsdtransform.utils.XsdTypes;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Abstract class for the import OAW transformer to generate a BOM from a given
 * model (ie XSD or WSDL).
 * 
 * @author njpatel
 */
public abstract class AbstractXtendImportTransformer extends
        BaseBOMXtendTransformer {

    private IProject project;

    protected boolean doMerge = true;

    protected boolean doOverwrite = false;

    protected boolean doRemoveXSDNotation = false;

    /**
     * Get the project into which the file is being imported.
     * 
     * @return
     */
    protected IProject getProject() {
        return project;
    }

    /**
     * Transforms any given XML Schema to a BOM Model If source or bomFilePath
     * are null then method returns an empty Issues variable.
     * 
     * @param source
     *            - The actual XSD Schema file to be transformed
     * @param bomFilePath
     *            - The path that the newly created BOM file will take
     * @param monitor
     *            progress monitor
     * @return - Returns any errors or warnings that occurred for inspection
     */
    public List<IStatus> transform(final File source, final IPath bomFilePath,
            IProgressMonitor monitor) {
        List<IStatus> result = new ArrayList<IStatus>();

        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }

        if (source != null || bomFilePath != null) {
            project = getProject(bomFilePath);
            ImportTransformationData data =
                    new ImportTransformationData(monitor, this);
            initialiseData(data);

            try {
                /*
                 * set up the source location to be the folder in which the
                 * selected xsd file resides. This needs to be done in case
                 * there are relative path references inside it while we are
                 * parsing the xml.
                 */
                String sourceLocation =
                        source.getAbsolutePath().replace(source.getName(), ""); //$NON-NLS-1$
                data.setSourceLocation(sourceLocation);
                data.setSourceFile(source);
                data.setDestinationFolder(getParentFolder(bomFilePath));
                data.getMonitor()
                        .setTopLevelSourceInTransform(source.getName());

                monitor.beginTask("", 6); //$NON-NLS-1$

                /*
                 * Create the resource with the output path specified so we can
                 * later add the model, diagram and restriction types to it.
                 */
                URI uri =
                        URI.createPlatformResourceURI(bomFilePath
                                .toPortableString(), true);
                data.setDiagramResource(data.getEditingDomain()
                        .getResourceSet().getResource(uri, true));

                monitor.worked(1);

                final XtendFacade f = getXtendFacade();
                monitor.worked(1);

                LOG("AbstractXtendImportTransformer: Reading schema..."); //$NON-NLS-1$
                long start = System.currentTimeMillis();
                URI srcFileUri = URI.createFileURI(source.getAbsolutePath());
                data.getCtx()
                        .setSourceFileAbsolutePath(source.getAbsolutePath());

                EObject schema = getModel(data, srcFileUri);

                LOG("AbstractXtendImportTransformer: Reading schema complete: " //$NON-NLS-1$
                        + (System.currentTimeMillis() - start));
                monitor.worked(1);

                // Load the resource
                Resource resource =
                        data.getEditingDomain()
                                .loadResource(srcFileUri.toString());
                if (resource == null) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String.format(Messages.AbstractXtendImportTransformer_loadResourceFailed_error_message,
                                            source.getAbsoluteFile())));
                }
                List<IStatus> validationResult = validateSource(data, resource);
                result.addAll(validationResult);

                monitor.worked(1);

                boolean hasErrors = false;
                for (IStatus status : validationResult) {
                    if (status.getSeverity() == IStatus.ERROR) {
                        hasErrors = true;
                        break;
                    }
                }

                /*
                 * XPD-6146: transformation should not be called if there are
                 * any validation errors on the wsdl/xsd
                 */
                IFile tempFile = WorkspaceSynchronizer.getFile(resource);
                if (null != tempFile) {

                    IMarker[] markers =
                            tempFile.findMarkers(IMarker.PROBLEM,
                                    true,
                                    IResource.DEPTH_INFINITE);

                    for (IMarker iMarker : markers) {

                        if (iMarker.getAttribute(IMarker.SEVERITY, -1) == IMarker.SEVERITY_ERROR) {

                            hasErrors = true;
                        }
                    }
                }

                if (!hasErrors) {
                    monitor.subTask(Xsd2BomMonitorMessage
                            .getXsd2BomProgressText(source.getName()
                                    + " :: " + //$NON-NLS-1$                                    
                                    com.tibco.xpd.bom.xsdtransform.imports.progress.Messages.AbstractXtendImportTransformer_InstatiatingTransform_message));
                    runWorkflow(schema, f, data);

                    /*
                     * no need to do monitor.worked() because the transform
                     * should consume a 'tick' on the monitor via the
                     * sub-progress monitor in ImportTransformationData
                     */
                }

            } catch (CoreException e) {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format(Messages.AbstractXtendImportTransformer_errorInTransformation_error_message,
                                        source.getAbsolutePath()));
                result.add(e.getStatus());
            } finally {
                // Unload resources
                cleanEditingDomain(data.getEditingDomain());
                // Unload all resources from the OAW resourceset
                for (Resource res : data.getOAWResourceSet().getResources()) {
                    if (res.isLoaded()) {
                        res.unload();
                    }
                }
                data.getOAWResourceSet().getResources().clear();

                monitor.worked(1);
                monitor.done();
            }

        }

        return result;
    }

    /**
     * Get the project of the target bom file.
     * 
     * @param bomFilePath
     * @return
     */
    private IProject getProject(IPath bomFilePath) {
        IFile file =
                ResourcesPlugin.getWorkspace().getRoot().getFile(bomFilePath);
        return file.getProject();
    }

    /**
     * Get the parent folder of the target bom file.
     * 
     * @param bomFilePath
     * @return folder if the bom file is contained in a folder,
     *         <code>null</code> otherwise.
     */
    private IFolder getParentFolder(IPath bomFilePath) {
        IFile file =
                ResourcesPlugin.getWorkspace().getRoot().getFile(bomFilePath);
        IContainer parent = file.getParent();

        return (IFolder) (parent instanceof IFolder ? parent : null);
    }

    /**
     * Create a linked file to the file-system resource path given.
     * 
     * @param project
     * @param srcPath
     * @return
     * @throws CoreException
     */
    protected IFile createTempLinkedFile(IProject project, IPath srcPath)
            throws CoreException {
        IFile linkFile = null;
        IFolder tmpFolder = project.getFolder(".tmp"); //$NON-NLS-1$
        // Create a temp folder to contain the linked resources
        if (!tmpFolder.exists()) {
            tmpFolder.create(true, true, null);
            tmpFolder.setDerived(true);// XPD-3743
        }

        String fileName = srcPath.lastSegment();
        String fileExt = srcPath.getFileExtension();

        if (fileExt != null && fileExt.length() > 0) {
            fileName =
                    fileName.substring(0,
                            (fileName.length() - fileExt.length() - 1));
        }

        linkFile = tmpFolder.getFile(srcPath.lastSegment());
        int idx = 1;
        while (linkFile.exists()) {
            if (fileExt != null) {
                linkFile = tmpFolder.getFile(String.format("%s%d.%s", //$NON-NLS-1$
                        fileName,
                        idx++,
                        fileExt));
            } else {
                linkFile =
                        tmpFolder.getFile(String
                                .format("%s%d", fileName, idx++)); //$NON-NLS-1$
            }
        }

        linkFile.createLink(srcPath, 0, null);
        return linkFile;
    }

    /**
     * Validate the given source file.
     * 
     * @param data
     *            transformation data
     * @param resource
     *            the resource to validate
     * @return validation result.
     */
    protected abstract List<IStatus> validateSource(
            ImportTransformationData data, Resource resource);

    /**
     * Run the transformation workflow.
     * 
     * @param schema
     *            the schema to import
     * @param f
     * @param data
     * @throws CoreException
     */
    private void runWorkflow(EObject schema, XtendFacade f,
            ImportTransformationData data) throws CoreException {
        LOG("AbstractXtendImportTransformer: runWorkflow..."); //$NON-NLS-1$
        long start = System.currentTimeMillis();
        Transaction transaction = null;
        /*
         * Create the resource with the output path specified so we can later
         * add the model, diagram and restriction types to it.
         */
        Resource res = data.getDiagramResource();
        try {
            transaction =
                    startTransaction((InternalTransactionalEditingDomain) data
                            .getEditingDomain());
            if (res != null) {
                res.getContents().clear();
            }

            f.call(getExtension(), schema, data);
            try {
                /*
                 * SID XPD-6059. Revert XPD-2542 which stops BOM generation for
                 * empty schema (causes "Business objects Not generated" on
                 * service tasks because validation expects ALL schemas to have
                 * BOM even if empty AND it caused no specific issues. AND
                 * because ALL process-as-service generated WSDL's for processes
                 * with no simple type params WILL have an empty schema and
                 * therefore when copied and used into consumer projects cause
                 * issues.
                 * 
                 * removed removeEmptyModels(Set<Model> models) method
                 */
                Set<Model> models = data.getModelResourceMap().keySet();

                XSDDerivedBOMNameAssigner importNameFixer =
                        new XSDDerivedBOMNameAssigner();

                /*
                 * XPD-2946: Keep track of the BOM working copies that already
                 * exist prior to saving here.
                 * 
                 * IF the generated BOM already exists in the import ZIP BECAUSE
                 * even though it is deleted during import, the BOMWorkingCopy
                 * may already have been loaded by then. If we don't force a
                 * reload, then other things like the indexers can end up
                 * running on the old info (because working copy may not get the
                 * event to perform reload for a while after saving in this,
                 * separate, edit domain.
                 * 
                 * ALSO, if we are saving several gen'd boms AND one of the boms
                 * that depends on others is loaded into its working copy (i.e.
                 * emf resourceSet) then THAT can cause BOM class references to
                 * be PROXIES. So we ned to ensure that we force this reload of
                 * BOM working copies after ALL boms have been saved.
                 */
                List<IFile> bomsToReload = new ArrayList<IFile>();

                for (Model model : models) {
                    try {
                        importNameFixer.setNames(model);
                    } catch (NameAssignmentException e) {
                        throw new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        Messages.AbstractXtendImportTransformer_nameConversionProblem_error_message,
                                        e));
                    }

                    IFile bomFile = getExistingBomFile(model);

                    if ((bomFile == null || !bomFile.exists())
                            || (!doMerge && doOverwrite)) {

                        if (bomFile == null || !bomFile.exists()) {
                            Resource resource = model.eResource();
                            if (resource != null) {
                                IContainer directory =
                                        WorkspaceSynchronizer
                                                .getFile(new ResourceImpl(
                                                        resource.getURI()))
                                                .getParent();
                                if (directory.members() != null) {
                                    for (IResource tmpResource : directory
                                            .members()) {
                                        if (tmpResource
                                                .getName()
                                                .equalsIgnoreCase(resource
                                                        .getURI().lastSegment())) {
                                            bomFile =
                                                    WorkspaceSynchronizer
                                                            .getFile(new ResourceImpl(
                                                                    resource.getURI()
                                                                            .trimSegments(1)
                                                                            .appendSegment(tmpResource
                                                                                    .getName())));
                                            bomFile.delete(true,
                                                    new NullProgressMonitor());
                                        }
                                    }
                                }
                            }
                        }

                        EAnnotation annot =
                                model.createEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);
                        EMap<String, String> details = annot.getDetails();
                        String user =
                                UserInfoUtil.getProjectPreferences(project)
                                        .getUserName();
                        details.put(BOMResourcesPlugin.ModelEannotationMetaSource_author,
                                user);
                        Calendar cal = Calendar.getInstance();
                        long time = cal.getTimeInMillis();
                        details.put(BOMResourcesPlugin.ModelEannotationMetaSource_datecreated,
                                String.valueOf(time));

                        details.put(BOMResourcesPlugin.ModelEannotationMetaSource_version,
                                BOMResourcesPlugin.BOM_VERSION);

                        Resource resource =
                                data.getModelResourceMap().get(model);

                        /*
                         * XPD-3167: JA: On normal XSD to BOM import XSD
                         * annotations should be removed.
                         */
                        if (doRemoveXSDNotation) {
                            Profile xsdProfile =
                                    BOMProfileUtils.getXSDProfile(model);
                            if (xsdProfile != null) {
                                model.unapplyProfile(xsdProfile);
                            }
                        }
                        if (!XpdResourcesPlugin.isInHeadlessMode()) {
                            Diagram diagram =
                                    ViewService
                                            .createDiagram(model,
                                                    CanvasPackageEditPart.MODEL_ID,
                                                    BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                            if (diagram != null) {
                                resource.getContents().add(diagram);
                                diagram.setName(model.getName());
                                diagram.setElement(model);
                            }
                        }

                        /*
                         * save the new changes we have made to resource
                         */
                        if (resource != null) {
                            /* Remember bom files for later working copy reload. */
                            bomsToReload.add(bomFile);

                            resource.save(com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil
                                    .getSaveOptions());

                            UMLDiagramEditorUtil
                                    .setCharset(WorkspaceSynchronizer
                                            .getFile(resource));

                        }
                    } else {
                        if (doMerge && merge(data, bomFile, model)) {
                            /* Remember bom files for later working copy reload. */
                            bomsToReload.add(bomFile);
                        }
                    }

                    // fix the comment spaces to right format chars
                    // fixCommentSpaces(resource);
                }

                /*
                 * Sid XPD-2946:
                 * 
                 * IF the generated BOM already exists in the import ZIP BECAUSE
                 * even though it is deleted during import, the BOMWorkingCopy
                 * may already have been loaded by then. If we don't force a
                 * reload, then other things like the indexers can end up
                 * running on the old info (because working copy may not get the
                 * event to perform reload for a while after saving in this,
                 * separate, edit domain.
                 * 
                 * ALSO, if we are saving several gen'd boms AND one of the boms
                 * that depends on others is loaded into its working copy (i.e.
                 * emf resourceSet) then THAT can cause BOM class references to
                 * be PROXIES. So we ned to ensure that we force this reload of
                 * BOM working copies after ALL boms have been saved.
                 */
                for (IFile bomFile : bomsToReload) {
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
                    if (wc != null) {
                        wc.reLoad();
                    }
                }

            } catch (IOException e) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                Activator.PLUGIN_ID,
                                String.format(Messages.XtendTransformer_unableToStore_message,
                                        (res != null && res.getURI() != null) ? res
                                                .getURI().toString() : ""), e)); //$NON-NLS-1$
            }
        } catch (Exception e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            String.format(Messages.XtendTransformer_transformError_message,
                                    (res != null && res.getURI() != null) ? res
                                            .getURI().toString() : ""), e)); //$NON-NLS-1$
        } finally {
            if (transaction != null) {
                try {
                    transaction.commit();
                } catch (RollbackException e) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String.format(Messages.XtendTransformer_transformError_message,
                                            (res != null && res.getURI() != null) ? res
                                                    .getURI().toString() : ""), //$NON-NLS-1$
                                    e));
                }
            }
            LOG("AbstractXtendImportTransformer: runWorkflow complete: " //$NON-NLS-1$
                    + (System.currentTimeMillis() - start));
        }
    }

    /*
     * SID XPD-6059. Revert XPD-2542 which stops BOM generation for empty schema
     * (causes "Business objects Not generated" on service tasks because
     * validation expects ALL schemas to have BOM even if empty AND it caused no
     * specific issues. AND because ALL process-as-service generated WSDL's for
     * processes with no simple type params WILL have an empty schema and
     * therefore when copied and used into consumer projects cause issues.
     * 
     * removed removeEmptyModels(Set<Model> models) method
     */

    /**
     * @param model
     * @return
     */
    private IFile getExistingBomFile(Model model) {
        Resource resource = model.eResource();
        if (resource != null) {
            return WorkspaceSynchronizer.getFile(resource);
        }

        return null;
    }

    /**
     * Merge the given model into the existing BOM in the workspace.
     * 
     * @param ed
     * @param existingBom
     * @param newModel
     * @return <code>true</code> if the existing BOM is updated,
     *         <code>false</code> if no change has been made.
     * @throws CoreException
     */
    private boolean merge(ImportTransformationData data, IFile existingBom,
            Model newModel) throws CoreException {
        /*
         * Reset the URI of the loaded model as we need to re-load the model
         * from the workspace to compare. If the URI isn't changed then the same
         * (in-memory) resource will be returned for the existing model.
         */
        TransactionalEditingDomain ed = data.getEditingDomain();
        URI origUri = newModel.eResource().getURI();
        newModel.eResource().setURI(URI.createURI(newModel.getName()));
        Model existingModel = null;
        try {
            existingModel = getModel(ed, existingBom);

            if (existingModel != null) {
                boolean changed =
                        WsdlSchemaIndexManager
                                .removeRedundantElements(existingModel);

                Collection<PackageableElement> newElements =
                        getNewElements(existingModel, ed, newModel);

                if (!newElements.isEmpty()) {
                    addExistingModelAssociationsToData(data, existingModel);

                    // get top level element and attributes from new model
                    List<TopLevelElementWrapper> topLevelElementWrappers =
                            com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                                    .getTopLevelElementWrappers(newModel);

                    List<TopLevelAttributeWrapper> topLevelAttributesWrappers =
                            com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                                    .getTopLevelAttributeWrappers(newModel);

                    // copy all new elements over to the existing model along
                    // with any stereotype applications associated with it
                    for (PackageableElement elem : newElements) {
                        deepCopy(elem, existingModel);
                    }

                    // go through each new element that has been copied to
                    // existing model
                    for (PackageableElement elem : newElements) {
                        addAdditionalTopLevelConstructsToExistingModel(data,
                                existingModel,
                                topLevelElementWrappers,
                                topLevelAttributesWrappers,
                                elem);

                        // copy any class generalisations and reset them to the
                        // similar Classifier inside existing model so we don't
                        // get an unresolved reference later on
                        if (elem instanceof Class
                                && ((Class) elem).getGenerals().size() > 0) {
                            fixGeneralisations(newModel, existingModel, elem);
                        }

                        if (elem instanceof Class) {
                            PackageableElement existingCls =
                                    existingModel.getPackagedElement(elem
                                            .getName());
                            if (existingCls instanceof Class) {
                                // fix operation types to stop unresolved
                                // references
                                fixOperationTypes(newModel,
                                        existingModel,
                                        existingCls);
                                // fix property types to stop unresolved
                                // references
                                fixPropertyTypes(data,
                                        newModel,
                                        existingModel,
                                        existingCls);
                            }
                        }
                    }
                    changed = true;
                }

                changed |= updateSchemaLocationAttr(existingModel, newModel);

                if (changed) {
                    try {
                        existingModel.eResource()
                                .save(UMLDiagramEditorUtil.getSaveOptions());

                        return true;
                    } catch (IOException e) {
                        throw new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        String.format(Messages.AbstractXtendImportTransformer_problemSavingFile_error_message,
                                                existingBom.getFullPath()
                                                        .toString()), e));
                    }
                } else {
                    // XPD-3938: Touch the original file so that it isn't older
                    // than its referenced WSDL and hence avoid a transformation
                    // loop.
                    existingBom.setLocalTimeStamp(System.currentTimeMillis());
                }
            }
        } finally {
            /*
             * Reset the URI of the new model as otherwise we will get the wrong
             * URI in references to this model. Also unload the existing model
             * as we will have 2 resources with the same URI.
             */
            if (existingModel != null) {
                existingModel.eResource().unload();
                ed.getResourceSet().getResources()
                        .remove(existingModel.eResource());
            }
            newModel.eResource().setURI(origUri);

        }
        return false;
    }

    /**
     * Resolves any generalisations
     * 
     * @param newModel
     * @param existingModel
     * @param elem
     */
    private void fixGeneralisations(Model newModel, Model existingModel,
            PackageableElement elem) {
        Classifier generalClass = ((Class) elem).getGenerals().get(0);
        PackageableElement existingGeneralCls =
                existingModel.getPackagedElement(generalClass.getName());
        if (generalClass != null
                && generalClass.getModel().getName().equals(newModel.getName())
                && generalClass.getModel() != existingModel) {
            ((Class) elem).getGeneralizations().get(0)
                    .setGeneral((Classifier) existingGeneralCls);
        }
    }

    /**
     * Resolves any property type references
     * 
     * @param data
     * @param newModel
     * @param existingModel
     * @param existingCls
     */
    private void fixPropertyTypes(ImportTransformationData data,
            Model newModel, Model existingModel, PackageableElement existingCls) {
        // go through all properties in the class
        EList<Property> ownedAttributes =
                ((Class) existingCls).getOwnedAttributes();
        HashMap<Integer, Property> propertiesToInsert =
                new HashMap<Integer, Property>();
        int index = 0;
        for (Property prop : ownedAttributes) {
            // only look at the property if the type is still residing in the
            // new Model so we need to correct this to stop unresolved
            // references
            if (prop.getType() != null
                    && prop.getType().getModel().getName()
                            .equals(newModel.getName())
                    && prop.getType().getModel() != existingModel) {
                // find the equivalent type inside our existing model (should be
                // there or been copied in from new model in previous steps)
                PackageableElement packagedElement =
                        existingModel.getPackagedElement(prop.getType()
                                .getName());
                if (packagedElement instanceof Type) {
                    // if enumeration or primitive type we can simple set the
                    // new type
                    if (!(packagedElement instanceof Class)) {
                        prop.setType((Type) packagedElement);
                    }

                    // if we have an association then we need to remove the
                    // current one from existing model if it exists and copy
                    // over from new model
                    if (prop.getAssociation() != null) {
                        PackageableElement association = null;
                        String targetMemberEnds = ""; //$NON-NLS-1$
                        for (Property tmpProp : prop.getAssociation()
                                .getMemberEnds()) {
                            targetMemberEnds += tmpProp.getName();
                            targetMemberEnds += "_"; //$NON-NLS-1$
                        }
                        for (PackageableElement tmpElem : existingModel
                                .getPackagedElements()) {
                            if (tmpElem instanceof Association) {
                                String tmpMemberEnds = ""; //$NON-NLS-1$
                                for (Property tmpProp : ((Association) tmpElem)
                                        .getMemberEnds()) {
                                    tmpMemberEnds += tmpProp.getName();
                                    tmpMemberEnds += "_"; //$NON-NLS-1$
                                }
                                if (targetMemberEnds.equals(tmpMemberEnds)) {
                                    association = tmpElem;
                                    break;
                                }
                            }
                        }
                        // actually remove it and set new one if previously
                        // existed
                        if (association != null) {
                            existingModel.getPackagedElements()
                                    .remove(association);
                            prop.getAssociation().setPackage(existingModel);
                        } else {
                            // if association and not existing one then we need
                            // to add a new composition rather than just setting
                            // the type as it is a Class
                            if (packagedElement instanceof Class) {
                                propertiesToInsert
                                        .put(new Integer(index), prop);
                            }
                        }
                    }
                }
            }
            index++;
        }

        Set<Integer> keySet = propertiesToInsert.keySet();
        for (Integer tmpInteger : keySet) {
            Property prop = propertiesToInsert.get(tmpInteger);
            PackageableElement packagedElement =
                    existingModel.getPackagedElement(prop.getType().getName());

            // add composition - we put dummy stereotype
            // values as it doesnt matter as we will recopy
            // the right values from existing property after
            // this method call
            ArrayList<String> blockList = new ArrayList<String>();
            Property newProperty =
                    TransformHelper.createCompositionProperty(data,
                            existingModel,
                            (Class) existingCls,
                            (Class) packagedElement,
                            ((Class) existingCls).getName(),
                            prop.getName(),
                            "1", //$NON-NLS-1$
                            "1", //$NON-NLS-1$
                            prop.getName(),
                            "", //$NON-NLS-1$
                            "", //$NON-NLS-1$
                            "", //$NON-NLS-1$
                            "", //$NON-NLS-1$
                            "", //$NON-NLS-1$
                            blockList);

            // go through each existing property stereotype
            for (Stereotype stereotype : prop.getAppliedStereotypes()) {
                Stereotype currentAppliedStereotype =
                        newProperty.getAppliedStereotype(stereotype
                                .getQualifiedName());
                // if not exist then we apply it
                if (currentAppliedStereotype == null) {
                    newProperty.applyStereotype(stereotype);
                    currentAppliedStereotype =
                            newProperty.getAppliedStereotype(stereotype
                                    .getQualifiedName());
                }
                // copy over all values from existing
                // property to the composition property so
                // we don't lose any info for export
                // transformations later on
                for (Property propertyAttr : stereotype.getOwnedAttributes()) {
                    if (!propertyAttr.getName().startsWith("base_")) { //$NON-NLS-1$
                        Object value =
                                prop.getValue(stereotype,
                                        propertyAttr.getName());
                        TransformHelper.setStereotypeValue(newProperty,
                                currentAppliedStereotype,
                                propertyAttr.getName(),
                                value);
                    }
                }
            }

            ownedAttributes.remove(newProperty);
            ownedAttributes.set(tmpInteger.intValue(), newProperty);
        }

    }

    /**
     * Resolves any operation type references
     * 
     * @param newModel
     * @param existingModel
     * @param existingCls
     */
    private void fixOperationTypes(Model newModel, Model existingModel,
            PackageableElement existingCls) {
        for (Operation oper : ((Class) existingCls).getOwnedOperations()) {
            for (Parameter param : oper.getOwnedParameters()) {
                if (param.getType() != null
                        && param.getType().getModel().getName()
                                .equals(newModel.getName())
                        && param.getType().getModel() != existingModel) {
                    PackageableElement packagedElement =
                            existingModel.getPackagedElement(param.getType()
                                    .getName());
                    if (packagedElement instanceof Type) {
                        param.setType((Type) packagedElement);
                    }
                }
            }
        }
    }

    /**
     * Adds associations to data so we are force unique names if we need to add
     * any compostions during the merge
     * 
     * @param data
     * @param existingModel
     */
    private void addExistingModelAssociationsToData(
            ImportTransformationData data, Model existingModel) {
        TreeIterator<EObject> allContents = existingModel.eAllContents();
        while (allContents.hasNext()) {
            EObject next = allContents.next();
            if (next instanceof Association) {
                data.getCreatedCompositions().add((Association) next);
            }
        }
    }

    /**
     * Adds additional top level elements or attributes from new model to
     * existing model
     * 
     * @param data
     * @param existingModel
     * @param topLevelElementWrappers
     * @param topLevelAttributesWrappers
     * @param elem
     */
    private void addAdditionalTopLevelConstructsToExistingModel(
            ImportTransformationData data, Model existingModel,
            List<TopLevelElementWrapper> topLevelElementWrappers,
            List<TopLevelAttributeWrapper> topLevelAttributesWrappers,
            PackageableElement elem) {
        if (elem instanceof Classifier) {
            for (TopLevelElementWrapper wrapper : topLevelElementWrappers) {
                if (wrapper.getType() != null && wrapper.getType().equals(elem)) {
                    ArrayList<String> finalList = new ArrayList<String>();
                    finalList.add(wrapper.getFinal());
                    ArrayList<String> blockList = new ArrayList<String>();
                    blockList.add(wrapper.getBlock());
                    TransformHelper.addTopLevelElement(data,
                            (Classifier) elem,
                            wrapper.getName(),
                            wrapper.getIsAnonymous(),
                            wrapper.getIsBaseXSDType(),
                            wrapper.getID(),
                            wrapper.getFixed(),
                            finalList,
                            blockList,
                            wrapper.getNillable(),
                            wrapper.getAbstract(),
                            wrapper.getSubstitutionGroup(),
                            wrapper.getDefault(),
                            wrapper.getTargetNamespace(),
                            existingModel);
                }
            }
            for (TopLevelAttributeWrapper wrapper : topLevelAttributesWrappers) {
                if (wrapper.getType() != null && wrapper.getType().equals(elem)) {
                    TransformHelper.addTopLevelAttribute(data,
                            (Classifier) elem,
                            wrapper.getName(),
                            wrapper.getIsAnonymous(),
                            wrapper.getIsBaseXSDType(),
                            wrapper.getID(),
                            wrapper.getFixed(),
                            wrapper.getDefault(),
                            wrapper.getTargetNamespace(),
                            existingModel);
                }
            }
        }
    }

    /**
     * Update the XsdBasedModel.xsdSchemaLocation with the path of the newly
     * merged schema.
     * 
     * @param existingModel
     * @param newModel
     * @return <code>true</code> if the model was updated.
     */
    private boolean updateSchemaLocationAttr(Model existingModel, Model newModel) {
        Stereotype stereotype = null;

        for (Stereotype st : existingModel.getAppliedStereotypes()) {
            if (XsdStereotypeUtils.XSD_BASED_MODEL.equals(st.getName())) {
                stereotype = st;
                break;
            }
        }

        if (stereotype != null) {
            Object existingValue =
                    existingModel.getValue(stereotype,
                            XsdStereotypeUtils.XSD_SCHEMA_LOCATION);

            if (existingValue instanceof String) {
                String schemaLocation = (String) existingValue;

                Object newValue =
                        newModel.getValue(stereotype,
                                XsdStereotypeUtils.XSD_SCHEMA_LOCATION);

                if (newValue instanceof String) {
                    String newSchemaLocation = (String) newValue;

                    String[] paths =
                            schemaLocation
                                    .split(XsdStereotypeUtils.SCHEMA_LOCATION_DELIMITER);

                    if (paths != null && !contains(paths, newSchemaLocation)) {
                        schemaLocation =
                                schemaLocation
                                        .concat(XsdStereotypeUtils.SCHEMA_LOCATION_DELIMITER
                                                + newSchemaLocation);

                        existingModel.setValue(stereotype,
                                XsdStereotypeUtils.XSD_SCHEMA_LOCATION,
                                schemaLocation);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check if the given array of strings contains the given string value.
     * 
     * @param values
     * @param valueToSearch
     * @return <code>true</code> if the array already contains the given string.
     */
    private boolean contains(String[] values, String valueToSearch) {
        if (values != null && valueToSearch != null)
            for (String value : values) {
                if (valueToSearch.equals(value)) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Copy the given element into the target model, including all stereotype
     * applications of the element and its children.
     * 
     * @param element
     * @param target
     */
    private void deepCopy(PackageableElement element, Model target) {
        EList<EObject> applications = element.getStereotypeApplications();
        target.getPackagedElements().add(element);
        if (!applications.isEmpty()) {
            target.eResource().getContents().addAll(applications);
        }

        for (EObject eo : element.eContents()) {
            if (eo instanceof Element) {
                applications = ((Element) eo).getStereotypeApplications();
                if (!applications.isEmpty()) {
                    target.eResource().getContents().addAll(applications);
                }
            }
        }

    }

    /**
     * Get all elements in the newly generated model that are an addition to the
     * existing model in the workspace (schema merge).
     * 
     * @param existingModel
     * @param newModel
     * @return
     */
    private Collection<PackageableElement> getNewElements(Model existingModel,
            TransactionalEditingDomain ed, Model newModel) {
        EList<PackageableElement> elementsToAdd =
                new BasicEList<PackageableElement>();
        String tns = XSDUtil.getXSDNamespaceFromPackage(existingModel);
        for (PackageableElement elem : newModel.getPackagedElements()) {
            if (existingModel.getPackagedElement(elem.getName(),
                    false,
                    elem.eClass(),
                    false) == null) {

                if (elem instanceof Class) {
                    if (!isAdditiveContainmentClass(tns,
                            existingModel,
                            (Class) elem)) {
                        elementsToAdd.add(elem);
                    }
                } else if (!(elem instanceof Association)) {
                    elementsToAdd.add(elem);
                } else if (elem instanceof Property) {
                    int h = 0;
                }
            }
        }

        /*
         * Process all new associations - only add association to new model if
         * the two ends are new additions to the model
         */
        for (PackageableElement elem : newModel.getPackagedElements()) {
            if (elem instanceof Association) {
                Association assoc = (Association) elem;
                /*
                 * XPD-4323: for uni-directional association get the owned ends,
                 * get the type for each owned end and check if it exists in the
                 * new model elements to add
                 */
                if (!assoc.getOwnedEnds().isEmpty()) {
                    boolean doAdd = true;
                    for (Property end : assoc.getOwnedEnds()) {
                        if (null != end.getType()) {
                            if (!elementsToAdd.contains(end.getType())) {
                                doAdd = false;
                                break;
                            }
                        }
                    }
                    if (doAdd) {
                        elementsToAdd.add(elem);
                        /*
                         * XPD-5067: for some reason in the schemas attached to
                         * this project, it was setting the incorrect resource
                         * uri to the association between new class and existing
                         * class. this is the only safe workaround we (Nilay)
                         * could think of to fix this
                         */
                        for (Property prop : assoc.getMemberEnds()) {

                            if (!elementsToAdd.contains(prop.getType())) {

                                PackageableElement existingClass =
                                        existingModel.getPackagedElement(prop
                                                .getType().getName(),
                                                false,
                                                prop.getType().eClass(),
                                                false);

                                if (existingClass instanceof Type) {

                                    prop.setType((Type) existingClass);
                                }
                            }
                        }

                    }

                } else {
                    boolean doAdd = true;
                    /*
                     * for bi-directional association since there is no owner
                     * (as the types belongs to both the classes participating
                     * in bi-directional relationship) we check for end types
                     * exists in the new model elements to add
                     */
                    for (Type type : assoc.getEndTypes()) {
                        if (!elementsToAdd.contains(type)) {
                            doAdd = false;
                            break;
                        }
                    }
                    if (doAdd) {
                        elementsToAdd.add(elem);
                    }
                }
            }
        }

        return elementsToAdd;
    }

    /**
     * Check if the given class is a containment class and is an additive change
     * (as this is not allowed).
     * 
     * @param tns
     *            target namespace
     * @param existingModel
     * @param clz
     * @return <code>true</code> if this will end up being an additive change to
     *         the existing model.
     */
    private boolean isAdditiveContainmentClass(String tns, Model existingModel,
            Class clz) {
        if (!WsdlSchemaIndexManager.isSchemaElementForClassPresent(tns, clz)) {
            for (Association assoc : clz.getAssociations()) {
                for (Type type : assoc.getEndTypes()) {
                    // If one end class is already present in the existing model
                    // then this is an additive change
                    if (clz != type
                            && existingModel.getPackagedElement(type.getName(),
                                    false,
                                    type.eClass(),
                                    false) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the model from the given BOM file.
     * 
     * @param ed
     * @param bomFile
     * @return
     */
    private Model getModel(TransactionalEditingDomain ed, IFile bomFile) {
        Resource resource =
                ed.loadResource(URI.createPlatformResourceURI(bomFile
                        .getFullPath().toString(),
                        true).toString());
        if (resource != null) {
            for (EObject eo : resource.getContents()) {
                if (eo instanceof Model) {
                    return (Model) eo;
                }
            }
        }
        return null;
    }

    /**
     * Initialise the transformation data.
     * 
     * @param data
     */
    private void initialiseData(ImportTransformationData data) {
        data.setXsdProfileResource(data
                .getEditingDomain()
                .getResourceSet()
                .getResource(URI.createURI(TransformHelper.XSD_NOTATION_URI),
                        true));
        for (int i = 0; i < xsdTypes.length; i++) {
            data.getXsdTypesArrayList().add(xsdTypes[i]);
        }
    }

    /**
     * Get the XtendFacade for the transformation.
     * <p>
     * Use {@link #configureFacade(XtendFacade)} to configure this facade.
     * </p>
     * 
     * @return
     */
    protected abstract XtendFacade getXtendFacade();

    /**
     * Get the name of the extension to be called from the Xtend transformation.
     * 
     * @return
     */
    protected abstract String getExtension();

    /**
     * Configure the given facade.
     * 
     * @param xtendFacade
     */
    protected static void configureFacade(XtendFacade xtendFacade) {
        xtendFacade.registerMetaModel(getUML2MetaModel());
        xtendFacade.registerMetaModel(getXsdMetaModel());
        xtendFacade.registerMetaModel(getProfileMetaModel());
        // Setup java meta model to allow the usage of java beans
        xtendFacade.registerMetaModel(getJavaMetaModel());
    }

    /**
     * Parse the given XSD schema and report any unsupported elements/features.
     * 
     * @param data
     *            {@link ImportTransformationData} that will be updated with
     *            prefix namespaces and also elements and attributes with
     *            undeclared forms (this will be used in any subsequent
     *            transformation)
     * @param schema
     *            schema to process
     * @param parsedSchemas
     *            all schemas that have already been processed (used to avoid
     *            cyclic processing).
     * @return constraint check status. Empty list if no problems found.
     */
    protected List<IStatus> validateSchemaContents(
            ImportTransformationData data, XSDSchema schema,
            HashMap<String, Boolean> parsedSchemas, boolean isInWSDLContainer,
            Map<String, IProject> namespacesSet) {
        List<IStatus> statusArr = new ArrayList<IStatus>();

        IProject project = WorkingCopyUtil.getProjectFor(schema, true);
        namespacesSet.put(schema.getTargetNamespace(), project);

        ArrayList<XSDSchema> schemas = new ArrayList<XSDSchema>();

        Map<String, String> namePrefixToNamespaceMap =
                schema.getQNamePrefixToNamespaceMap();
        data.getInnerWSDLSchemaPrefixMap().putAll(namePrefixToNamespaceMap);

        List<String> attrUndeclaredForms = data.getAttrUndeclaredForms();
        List<String> elemUndeclaredForms = data.getElemUndeclaredForms();

        List<String> topLevelElementNames = new ArrayList<String>();
        List<String> topLevelElementTypes = new ArrayList<String>();
        List<String> topLevelAttributeNames = new ArrayList<String>();
        List<String> topLevelAttributeTypes = new ArrayList<String>();
        List<String> topLevelComplexTypes = new ArrayList<String>();
        List<String> topLevelSimpleTypes = new ArrayList<String>();

        EList<XSDModelGroupDefinition> modelGroupDefinitions =
                schema.getModelGroupDefinitions();
        List<XSDParticle> groupRefs = new ArrayList<XSDParticle>();
        HashMap<EObject, List<String>> groupRefContainers =
                new HashMap<EObject, List<String>>();

        Set<XSDSchema> alreadyProcessedSchemas = new HashSet<XSDSchema>();

        List<EObject> allContents = new ArrayList<EObject>();

        getAllSchemaContents(schema, allContents, alreadyProcessedSchemas);

        for (EObject node : allContents) {
            if (node instanceof XSDParticle
                    && ((XSDParticle) node).getElement() != null
                    && ((XSDParticle) node).getElement().getLocalName()
                            .equals("group")) { //$NON-NLS-1$
                groupRefs.add((XSDParticle) node);
                String ref =
                        ((XSDParticle) node).getElement().getAttribute("ref"); //$NON-NLS-1$
                int indexPos = ref.indexOf(":"); //$NON-NLS-1$
                if (indexPos >= 0) {
                    ref = ref.substring(indexPos + 1);
                }
                EObject complexTypeOrGroupContainer =
                        getComplexTypeOrGroupContainer(node.eContainer());
                List<String> grpRefLocalParts =
                        groupRefContainers.get(complexTypeOrGroupContainer);
                if (grpRefLocalParts != null && grpRefLocalParts.size() > 0) {
                    boolean found = false;
                    for (String tmpRef : grpRefLocalParts) {
                        if (tmpRef.equals(ref)) {
                            found = true;
                            statusArr
                                    .add(new Status(
                                            IStatus.ERROR,
                                            Activator.PLUGIN_ID,
                                            String.format(Messages.XtendTransformer_does_not_support_duplicate_grouprefs_message,
                                                    (schema != null ? schema
                                                            .getTargetNamespace()
                                                            : "")))); //$NON-NLS-1$
                        }
                    }
                    if (!found) {
                        grpRefLocalParts.add(ref);
                        groupRefContainers.put(complexTypeOrGroupContainer,
                                grpRefLocalParts);
                    }

                } else {
                    grpRefLocalParts = new ArrayList<String>();
                    grpRefLocalParts.add(ref);
                    groupRefContainers.put(complexTypeOrGroupContainer,
                            grpRefLocalParts);
                }
            }
            /*
             * XPD-5248: Saket- Removing if-check for "included" schemas because
             * now we include the contents of included schemas along with the
             * contents of the main schema.
             */
            if (node instanceof XSDImport) {
                XSDSchema resolvedSchema =
                        ((XSDImport) node).getResolvedSchema();
                if (resolvedSchema != null
                        && parsedSchemas
                                .get(resolvedSchema.getSchemaLocation()) == null) {
                    schemas.add(resolvedSchema);
                    parsedSchemas.put(resolvedSchema.getSchemaLocation(),
                            Boolean.TRUE);
                }
            }
            if (node instanceof XSDSimpleTypeDefinition) {
                XSDSimpleTypeDefinition tmpSimple =
                        (XSDSimpleTypeDefinition) node;
                if (tmpSimple.eContainer() instanceof XSDSchema) {
                    String name = tmpSimple.getName();
                    topLevelSimpleTypes.add(name);
                    boolean clash =
                            isNameClashWithSimpleTypeTemplateNames(name);
                    if (clash) {
                        statusArr
                                .add(new Status(
                                        IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        String.format(Messages.XtendTransformer_simple_type_name_class_with_template,
                                                ((tmpSimple != null && tmpSimple instanceof XSDSchema) ? ((XSDSchema) tmpSimple
                                                        .eContainer())
                                                        .getSchemaLocation()
                                                        : ""), //$NON-NLS-1$
                                                name)));
                    }
                }
            }
            if (node instanceof XSDRedefine) {
                statusArr
                        .add(new Status(
                                IStatus.ERROR,
                                Activator.PLUGIN_ID,
                                String.format(Messages.XtendTransformer_does_not_support_redefine_message,
                                        (schema != null ? schema
                                                .getTargetNamespace() : "")))); //$NON-NLS-1$
            } else if (node instanceof XSDNotationDeclaration) {
                statusArr
                        .add(new Status(
                                IStatus.ERROR,
                                Activator.PLUGIN_ID,
                                String.format(Messages.XtendTransformer_does_not_support_notation_message,
                                        (schema != null ? schema
                                                .getTargetNamespace() : "")))); //$NON-NLS-1$
            } else if (node instanceof XSDSimpleTypeDefinition
                    && ((XSDSimpleTypeDefinition) node).getVariety()
                            .equals(XSDVariety.LIST_LITERAL)) {
                // this is the only way to differentiate between list and
                // nmtokens , idrefs etc as they dont have any contents
                XSDSimpleTypeDefinition baseTypeDefinition =
                        ((XSDSimpleTypeDefinition) node)
                                .getBaseTypeDefinition();
                if (!baseTypeDefinition.getName().equalsIgnoreCase("idrefs") //$NON-NLS-1$
                        && !baseTypeDefinition.getName()
                                .equalsIgnoreCase("nmtokens") //$NON-NLS-1$
                        && !baseTypeDefinition.getName()
                                .equalsIgnoreCase("entities")) { //$NON-NLS-1$ 
                    statusArr
                            .add(new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String.format(Messages.XtendTransformer_does_not_support_list_message,
                                            (schema != null ? schema
                                                    .getTargetNamespace() : "")))); //$NON-NLS-1$
                }
            } else if (node instanceof XSDModelGroupDefinition) {
                TreeIterator<EObject> complexContents =
                        ((XSDModelGroupDefinition) node).eAllContents();
                while (complexContents.hasNext()) {
                    EObject tmpNode = complexContents.next();
                    if (tmpNode instanceof XSDModelGroup
                            && ((XSDModelGroup) tmpNode).getElement() != null
                            && ((XSDModelGroup) tmpNode).getElement()
                                    .getLocalName().equals("choice")) { //$NON-NLS-1$
                        /*
                         * statusArr .add(new Status( IStatus.ERROR,
                         * Activator.PLUGIN_ID,String.format(Messages.
                         * XtendTransformer_does_not_support_choices_message
                         * ,(schema != null ? schema .getTargetNamespace() :
                         * ""))));
                         */
                    }
                    if (tmpNode instanceof XSDModelGroup
                            && ((XSDModelGroup) tmpNode).getElement() != null
                            && ((XSDModelGroup) tmpNode).getElement()
                                    .getLocalName().equals("all")) { //$NON-NLS-1$
                        /*
                         * statusArr .add(new Status( IStatus.ERROR,
                         * Activator.PLUGIN_ID, String.format(Messages.
                         * XtendTransformer_does_not_support_all_message,(schema
                         * != null ? schema .getTargetNamespace() : "") )));
                         */
                    }
                    if (tmpNode instanceof XSDModelGroup
                            && ((XSDModelGroup) tmpNode).getElement() != null
                            && (((XSDModelGroup) tmpNode).getElement()
                                    .getLocalName().equals("sequence") || ((XSDModelGroup) tmpNode) //$NON-NLS-1$
                                    .getElement().getLocalName()
                                    .equals("choice"))) { //$NON-NLS-1$                        
                        isInvalidAnyOrdering(statusArr, tmpNode);

                    }
                }
                /*
                 * statusArr .add(new Status( IStatus.ERROR,
                 * Activator.PLUGIN_ID, String.format(Messages.
                 * XtendTransformer_does_not_support_groups_message ,(schema!=
                 * null ? schema.getTargetNamespace() : ""))));
                 */
            } else if (node instanceof XSDAttributeGroupDefinition) {
                /*
                 * statusArr .add(new Status( IStatus.ERROR,
                 * Activator.PLUGIN_ID,String.format(Messages.
                 * XtendTransformer_does_not_support_attribute_groups_message
                 * ,(schema!= null ? schema.getTargetNamespace() : ""))));
                 */
            } else if (node instanceof XSDAttributeDeclaration) {
                XSDAttributeDeclaration tmpAttr =
                        (XSDAttributeDeclaration) node;
                if (tmpAttr.eContainer() instanceof XSDSchema) {
                    topLevelAttributeNames.add(tmpAttr.getName());
                    String type = ""; //$NON-NLS-1$
                    if (tmpAttr.getType() != null
                            && tmpAttr.getType().getName() != null) {
                        type = tmpAttr.getType().getName();
                    }
                    topLevelAttributeTypes.add(type);
                    /*
                     * statusArr .add(new Status( IStatus.ERROR,
                     * Activator.PLUGIN_ID,Messages.
                     * XtendTransformer_does_not_support_top_level_attributes_message
                     * ));
                     */
                } else {
                    boolean isSetForm = tmpAttr.isSetForm();
                    if (!isSetForm) {
                        String key =
                                getElementOrAttributeKey(tmpAttr.getName(),
                                        tmpAttr.eContainer());
                        if (!attrUndeclaredForms.contains(key)) {
                            attrUndeclaredForms.add(key);
                        }
                    }
                }
            } else if (node instanceof XSDElementDeclaration) {
                XSDElementDeclaration tmpElem = (XSDElementDeclaration) node;
                if (tmpElem.eContainer() instanceof XSDSchema) {
                    topLevelElementNames.add(tmpElem.getName());
                    String type = ""; //$NON-NLS-1$
                    if (tmpElem.getType() != null
                            && tmpElem.getType().getName() != null) {
                        type = tmpElem.getType().getName();
                    }
                    topLevelElementTypes.add(type);
                    /*
                     * statusArr .add(new Status( IStatus.ERROR,
                     * Activator.PLUGIN_ID,Messages.
                     * XtendTransformer_does_not_support_top_level_elements_message
                     * ));
                     */
                } else {
                    boolean isSetForm = tmpElem.isSetForm();
                    if (!isSetForm) {
                        String key =
                                getElementOrAttributeKey(tmpElem.getName(),
                                        tmpElem.eContainer());
                        if (!elemUndeclaredForms.contains(key)) {
                            elemUndeclaredForms.add(key);
                        }
                    }
                }
                String maxOccurs =
                        tmpElem.getElement().getAttribute("maxOccurs"); //$NON-NLS-1$
                String nillable = tmpElem.getElement().getAttribute("nillable"); //$NON-NLS-1$
                if (!(maxOccurs == null || maxOccurs.trim().length() == 0
                        || maxOccurs.equals("0") || maxOccurs.equals("1")) //$NON-NLS-1$ //$NON-NLS-2$
                        && (nillable != null && nillable.equals("true"))) { //$NON-NLS-1$
                    statusArr
                            .add(new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String.format(Messages.XtendTransformer_does_not_support_nillable_with_multiplicity_message,
                                            (schema != null ? schema
                                                    .getTargetNamespace() : ""), //$NON-NLS-1$
                                            tmpElem.getName())));
                }

            } else if (node instanceof XSDComplexTypeDefinition) {
                XSDComplexTypeDefinition tmpComplex =
                        (XSDComplexTypeDefinition) node;
                /*
                 * XPD-4386: disable this validation to support mixed construct
                 * on xsd
                 */
                /*
                 * if (((XSDComplexTypeDefinition) node).isSetMixed() &&
                 * ((XSDComplexTypeDefinition) node).isMixed() == true) {
                 * statusArr .add(new Status( IStatus.ERROR,
                 * Activator.PLUGIN_ID, String.format(Messages.
                 * XtendTransformer_does_not_support_complextype_mixed_message
                 * ,(schema!= null ? schema.getTargetNamespace() : "")))); }
                 */
                // Disable complex type restriction validation rule
                /*
                 * if (tmpComplex.isSetDerivationMethod() && tmpComplex
                 * .getDerivationMethod()
                 * .equals(XSDDerivationMethod.RESTRICTION_LITERAL)) { statusArr
                 * .add(new Status( IStatus.ERROR, Activator.PLUGIN_ID,
                 * String.format(Messages.
                 * XtendTransformer_does_not_support_complextype_restriction_message
                 * ,(schema!= null ? schema.getTargetNamespace() :
                 * ""),tmpComplex.getName()))); }
                 */
                if (tmpComplex.eContainer() instanceof XSDSchema) {
                    topLevelComplexTypes.add(tmpComplex.getName());
                }
                HashMap<String, Boolean> tmpNamesMap =
                        new HashMap<String, Boolean>();
                HashMap<String, Object> elementNamesMap =
                        new HashMap<String, Object>();
                TreeIterator<EObject> complexContents =
                        tmpComplex.eAllContents();

                while (complexContents.hasNext()) {

                    EObject tmpNode = complexContents.next();
                    String name = null;

                    if (tmpNode instanceof XSDModelGroup
                            && ((XSDModelGroup) tmpNode).getElement() != null
                            && ((XSDModelGroup) tmpNode).getElement()
                                    .getLocalName().equals("choice")) { //$NON-NLS-1$
                        /*
                         * statusArr .add(new Status( IStatus.ERROR,
                         * Activator.PLUGIN_ID,String.format(Messages.
                         * XtendTransformer_does_not_support_choices_message
                         * ,(schema!= null ? schema.getTargetNamespace() :
                         * "")));
                         */
                    }
                    if (tmpNode instanceof XSDModelGroup
                            && ((XSDModelGroup) tmpNode).getElement() != null
                            && ((XSDModelGroup) tmpNode).getElement()
                                    .getLocalName().equals("all")) { //$NON-NLS-1$
                        /*
                         * statusArr .add(new Status( IStatus.ERROR,
                         * Activator.PLUGIN_ID, String.format(Messages.
                         * XtendTransformer_does_not_support_all_message
                         * ,(schema!= null ? schema.getTargetNamespace() :
                         * ""))));
                         */
                    }
                    if (tmpNode instanceof XSDModelGroup
                            && ((XSDModelGroup) tmpNode).getElement() != null
                            && (((XSDModelGroup) tmpNode).getElement()
                                    .getLocalName().equals("sequence") || ((XSDModelGroup) tmpNode) //$NON-NLS-1$
                                    .getElement().getLocalName()
                                    .equals("choice"))) { //$NON-NLS-1$                  
                        isInvalidAnyOrdering(statusArr, tmpNode);
                    }

                    if (tmpNode instanceof XSDAttributeDeclaration) {
                        name = ((XSDAttributeDeclaration) tmpNode).getName();
                        boolean isSetForm =
                                ((XSDAttributeDeclaration) tmpNode).isSetForm();
                        if (!isSetForm) {
                            String key =
                                    getElementOrAttributeKey(name,
                                            ((XSDAttributeDeclaration) tmpNode)
                                                    .eContainer());
                            if (!attrUndeclaredForms.contains(key)) {
                                attrUndeclaredForms.add(key);
                            }
                        }
                    }
                    if (tmpNode instanceof XSDElementDeclaration) {
                        name = ((XSDElementDeclaration) tmpNode).getName();

                        if (getElementComplexContainer((tmpNode))
                                .equals(tmpComplex) && name != null) {

                            /*
                             * XPD-1935: find for duplicate element names
                             * between base complex and complex extension
                             */
                            boolean isComplexTypeExtension =
                                    isComplexTypeExtension(tmpComplex,
                                            topLevelComplexTypes);
                            if (isComplexTypeExtension) {

                                List<String> elementNamesFromBaseComplexType =
                                        getElementNamesFromBaseComplexType(tmpComplex
                                                .getBaseTypeDefinition());
                                /*
                                 * if it is complex type extension, element
                                 * names cannot be duplicate in base complex
                                 * type and sub complex type
                                 */
                                boolean duplicateFound =
                                        isDuplicateElementNameInBaseAndSub(name,
                                                elementNamesFromBaseComplexType);
                                if (duplicateFound) {

                                    statusArr
                                            .add(new Status(
                                                    IStatus.ERROR,
                                                    Activator.PLUGIN_ID,
                                                    String.format(Messages.XtendTransformer_does_not_support_duplicate_element_names_inside_complextype_and_complex_extension_message,
                                                            (schema != null ? schema
                                                                    .getTargetNamespace()
                                                                    : ""), //$NON-NLS-1$
                                                            ((XSDElementDeclaration) tmpNode)
                                                                    .getName(),
                                                            tmpComplex
                                                                    .getBaseTypeDefinition()
                                                                    .getName(),
                                                            (tmpComplex
                                                                    .getName() != null ? tmpComplex
                                                                    .getName()
                                                                    : "")))); //$NON-NLS-1$
                                }
                            }
                            /* end: XPD-1935 */
                            Object tmpObject = elementNamesMap.get(name);
                            if (tmpObject != null) {
                                statusArr
                                        .add(new Status(
                                                IStatus.ERROR,
                                                Activator.PLUGIN_ID,
                                                String.format(Messages.XtendTransformer_does_not_support_duplicate_element_names_inside_complextype_message,
                                                        (schema != null ? schema
                                                                .getTargetNamespace()
                                                                : ""), //$NON-NLS-1$
                                                        ((XSDElementDeclaration) tmpNode)
                                                                .getName(),
                                                        tmpComplex.getName())));
                            }
                            elementNamesMap.put(name, true);
                        }

                        boolean isSetForm =
                                ((XSDElementDeclaration) tmpNode).isSetForm();
                        if (!isSetForm) {
                            String key =
                                    getElementOrAttributeKey(name,
                                            ((XSDElementDeclaration) tmpNode)
                                                    .eContainer());
                            if (!elemUndeclaredForms.contains(key)) {
                                elemUndeclaredForms.add(key);
                            }
                        }

                    }
                    if (name != null
                            && !elementParent(tmpComplex, tmpNode.eContainer())) {
                        if (tmpNamesMap.containsKey(name)) {
                            /*
                             * statusArr .add(new Status( IStatus.ERROR,
                             * Activator.PLUGIN_ID,Messages.
                             * XtendTransformer_does_not_support_duplicate_element_and_attribute_names_message
                             * )); break;
                             */
                        } else {
                            tmpNamesMap.put(name, Boolean.TRUE);
                        }
                    }
                }
            } else if (node instanceof XSDWildcard
                    || node instanceof XSDIdentityConstraintDefinition) {
                org.w3c.dom.Element element = null;
                if (node instanceof XSDWildcard) {
                    element = ((XSDWildcard) node).getElement();
                } else if (node instanceof XSDIdentityConstraintDefinition) {
                    element =
                            ((XSDIdentityConstraintDefinition) node)
                                    .getElement();
                }
                if (element != null
                        && element.getLocalName().equalsIgnoreCase("any")) { //$NON-NLS-1$
                    /*
                     * statusArr .add(new Status( IStatus.ERROR,
                     * Activator.PLUGIN_ID, String.format(Messages.
                     * XtendTransformer_does_not_support_any_message ,(schema!=
                     * null ? schema.getTargetNamespace() : ""))));
                     */
                } else if (element != null
                        && element.getLocalName().equalsIgnoreCase("key")) { //$NON-NLS-1$
                    statusArr
                            .add(new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String.format(Messages.XtendTransformer_does_not_support_key_message,
                                            (schema != null ? schema
                                                    .getTargetNamespace() : "")))); //$NON-NLS-1$
                } else if (element != null
                        && element.getLocalName().equalsIgnoreCase("keyref")) { //$NON-NLS-1$
                    statusArr
                            .add(new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String.format(Messages.XtendTransformer_does_not_support_keyref_message,
                                            (schema != null ? schema
                                                    .getTargetNamespace() : "")))); //$NON-NLS-1$
                } else if (element != null
                        && element.getLocalName().equalsIgnoreCase("unique")) { //$NON-NLS-1$
                    statusArr
                            .add(new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String.format(Messages.XtendTransformer_does_not_support_unique_message,
                                            (schema != null ? schema
                                                    .getTargetNamespace() : "")))); //$NON-NLS-1$
                }
            }
        }

        /*
         * XPD-5248: Saket- Re-instating the validation rule which was removed
         * in XPD-4810.
         */

        // check elements
        Iterator<String> elemNameIter = topLevelElementNames.iterator();
        Iterator<String> elemTypeIter = topLevelElementTypes.iterator();
        while (elemNameIter.hasNext()) {
            String elemName = elemNameIter.next();
            String elemType = elemTypeIter.next();

            if (topLevelComplexTypes.contains(elemName)
                    && !elemType.equals(elemName)) {
                statusArr
                        .add(new Status(
                                IStatus.ERROR,
                                Activator.PLUGIN_ID,
                                String.format(Messages.XtendTransformer_duplicate_top_level_element_and_complex_type1,
                                        (schema != null ? schema
                                                .getTargetNamespace() : ""), //$NON-NLS-1$
                                        elemName)));
            }

        }

        // check to ensure that each group doesn't in turn have a group
        // reference that also indirectly references itself somewhere else in
        // the schema under the same container
        for (XSDModelGroupDefinition group : modelGroupDefinitions) {
            TreeIterator<EObject> groupContents = group.eAllContents();

            while (groupContents.hasNext()) {
                EObject node = groupContents.next();
                if (node instanceof XSDParticle
                        && ((XSDParticle) node).getElement() != null
                        && ((XSDParticle) node).getElement().getLocalName()
                                .equals("group")) { //$NON-NLS-1$
                    String ref =
                            ((XSDParticle) node).getElement()
                                    .getAttribute("ref"); //$NON-NLS-1$
                    int indexPos = ref.indexOf(":"); //$NON-NLS-1$
                    if (indexPos >= 0) {
                        ref = ref.substring(indexPos + 1);
                    }

                    ArrayList<EObject> containersWithRefToGroup =
                            new ArrayList<EObject>();
                    Iterator<EObject> iterator =
                            groupRefContainers.keySet().iterator();
                    while (iterator.hasNext()) {
                        EObject container = iterator.next();
                        List<String> listRefs =
                                groupRefContainers.get(container);
                        for (String tmpRef : listRefs) {
                            if (tmpRef.equals(group.getName())) {
                                containersWithRefToGroup.add(container);
                                break;
                            }
                        }
                    }

                    for (EObject container : containersWithRefToGroup) {
                        List<String> listRefs =
                                groupRefContainers.get(container);
                        for (String tmpRef : listRefs) {
                            if (ref.equals(tmpRef)) {
                                statusArr
                                        .add(new Status(
                                                IStatus.ERROR,
                                                Activator.PLUGIN_ID,
                                                String.format(Messages.XtendTransformer_does_not_support_cross_referenced_duplicate_grouprefs_message,
                                                        (schema != null ? schema
                                                                .getTargetNamespace()
                                                                : "")))); //$NON-NLS-1$
                            }
                        }
                    }
                }
            }
        }

        if (statusArr.size() == 0 && schemas.size() > 0) {
            for (XSDSchema tmpSchema : schemas) {
                statusArr.addAll(validateSchemaContents(data,
                        tmpSchema,
                        parsedSchemas,
                        isInWSDLContainer,
                        namespacesSet));
            }
        }
        return statusArr;
    }

    /**
     * Fetch all the contents from the schema and schemas included in it.
     * 
     * @param schema
     * @param allContents
     *            Return list containing all content without XSDIncludes but
     *            with all content from included schemas.
     * @param alreadyProcessedSchemas
     */
    private void getAllSchemaContents(XSDSchema schema,
            List<EObject> allContents, Set<XSDSchema> alreadyProcessedSchemas) {

        /*
         * Check if resolvedSchema has already been processed.
         */
        if (!alreadyProcessedSchemas.contains(schema)) {
            alreadyProcessedSchemas.add(schema);

            for (Iterator iterator = schema.eAllContents(); iterator.hasNext();) {
                EObject node = (EObject) iterator.next();

                if (node instanceof XSDInclude) {

                    XSDSchema resolvedSchema =
                            ((XSDInclude) node).getResolvedSchema();

                    if (resolvedSchema != null) {
                        getAllSchemaContents(resolvedSchema,
                                allContents,
                                alreadyProcessedSchemas);
                    }
                } else {
                    allContents.add(node);
                }
            }
        }

        return;
    }

    /**
     * checks if the given complex type is a sub class (complex type extension)
     * of another complex type
     * 
     * @param tmpComplex
     * @param topLevelComplexTypes
     * @return <code>true</code> if the given complex type is a complex type
     *         extension of another complex type; <code>false</code> otherwise
     */
    private boolean isComplexTypeExtension(XSDComplexTypeDefinition tmpComplex,
            List<String> topLevelComplexTypes) {

        if (!XsdStereotypeUtils.W3_ORG_SCHEMA_NAMESPACE.equals(tmpComplex
                .getBaseTypeDefinition().getTargetNamespace())
                && !XsdTypes.ANYTYPE.toString().equals(tmpComplex
                        .getBaseTypeDefinition().getName())) {

            /* to check if the base complex type is a top level complex type */
            if (tmpComplex.getBaseTypeDefinition().eContainer() instanceof XSDSchema) {
                /*
                 * get the parent node to check if it is an extension or
                 * restriction
                 */
                Node parentNode =
                        tmpComplex.getContent().getElement().getParentNode();
                if (XSDDerivationMethod.EXTENSION_LITERAL.toString()
                        .equals(parentNode.getLocalName())) {

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * gets the list of base complex types elements
     * 
     * @param xsdTypeDefinition
     * @return {@link List} of base complex type element names
     */
    private List<String> getElementNamesFromBaseComplexType(
            XSDTypeDefinition xsdTypeDefinition) {

        List<String> baseElementNames = new ArrayList<String>();

        if (xsdTypeDefinition instanceof XSDComplexTypeDefinition) {

            XSDComplexTypeDefinition complexTypeDefinition =
                    (XSDComplexTypeDefinition) xsdTypeDefinition;
            TreeIterator<EObject> baseComplexTypeContents =
                    complexTypeDefinition.eAllContents();

            while (baseComplexTypeContents.hasNext()) {

                EObject next = baseComplexTypeContents.next();

                if (next instanceof XSDElementDeclaration) {
                    baseElementNames.add(((XSDElementDeclaration) next)
                            .getName());
                }
            }
        }

        return baseElementNames;

    }

    /**
     * for a given element name in a sub complex type, finds if that element
     * name matches with base element names
     * 
     * @param subElemName
     * @param baseElementNames
     * @return <code>true</code> if sub element name matches with one of base
     *         element names; <code>false</code> otherwise
     */
    private boolean isDuplicateElementNameInBaseAndSub(String subElemName,
            List<String> baseElementNames) {

        if (baseElementNames.contains(subElemName)) {
            return true;
        }

        return false;
    }

    private void isInvalidAnyOrdering(List<IStatus> statusArr, EObject tmpNode) {
        /*
         * look inside the sequence or choice to find an invalid ##any with
         * element construct
         */
        EObject anyObject = null;

        /* this is to cache immediate preceding element to ##any object */
        XSDElementDeclaration precedingElementDeclaration = null;

        /*
         * boolean to check if we have found ##any object. this is just for
         * readability sake. otherwise can manage with anyObject
         */
        boolean anyObjectFound = false;

        TreeIterator<EObject> explicitGroupContents = tmpNode.eAllContents();
        while (explicitGroupContents.hasNext()) {
            EObject tmpObj = explicitGroupContents.next();

            if (tmpObj instanceof XSDWildcard
                    || tmpObj instanceof XSDIdentityConstraintDefinition) {

                org.w3c.dom.Element element = null;

                if (tmpObj instanceof XSDWildcard) {
                    element = ((XSDWildcard) tmpObj).getElement();
                } else if (tmpObj instanceof XSDIdentityConstraintDefinition) {
                    element =
                            ((XSDIdentityConstraintDefinition) tmpObj)
                                    .getElement();
                }

                if (element != null
                        && element.getLocalName().equalsIgnoreCase("any")) { //$NON-NLS-1$
                    anyObject = tmpObj;
                    anyObjectFound = true;
                }
            }

            if (anyObjectFound) {
                /*
                 * XPD-4551: when we encounter an any object and we have cached
                 * preceding element, then we must check the min max of the
                 * preceding element (to ensure it's not a variable length
                 * element followed by ##any) - provided they both belong to the
                 * same ancestor
                 */
                if (null != precedingElementDeclaration) {
                    /*
                     * Ensure that the preceding ElementDeclaration is in same
                     * complex type not from a completely different previous
                     * complex type.
                     */
                    if (getElementComplexContainer(precedingElementDeclaration) == getElementComplexContainer(anyObject)) {

                        /* compare min max for preceeding element */
                        comparePreceedingElementMinMax(statusArr,
                                precedingElementDeclaration,
                                anyObject);

                    }
                    precedingElementDeclaration = null;

                } else {
                    /*
                     * XPD-4551: PREVIOUS time around we found a ##any.
                     * 
                     * Now look for next Element declaration. If we are on the
                     * same complex type definition then this is a problem if
                     * the ##any is variable length followed by another element
                     */

                    if (getElementComplexContainer(tmpObj) != getElementComplexContainer(anyObject)) {
                        /*
                         * No longer inside the complex type in which the ##any
                         * was found so that means that this ##any is at the end
                         * of it's complex type and therefore the last thing in
                         * a sequence (or other XSDParticle) within that complex
                         * type (because we would have reset anyObject to null
                         * if we found an element following the ##any)
                         * 
                         * Therefore if it is variable length AND any of it's
                         * XSDParticle ancestors are multiple item sequences
                         * then that's a problem as XML processor can't tell the
                         * difference between the variable multiple ##any's at
                         * the end of one sequence iteration and the start of
                         * the next sequence iteration.
                         */
                        checkForMultiAnyInMutliSequence(statusArr, anyObject);

                        anyObject = null;
                        anyObjectFound = false;

                    } else if (tmpObj instanceof XSDElementDeclaration) {
                        /*
                         * Found an element after the ##any IN THE SAME complex
                         * type definition. Check if it's an element following a
                         * variable length ##any.
                         */
                        /* compare min max for ##any object */
                        compareFollowingElementMinMax(statusArr,
                                tmpObj,
                                anyObject);

                        anyObject = null;
                        anyObjectFound = false;
                    }
                }
            }

            /* cache previous element until we come across an ##any object */
            if (tmpObj instanceof XSDElementDeclaration) {
                precedingElementDeclaration = (XSDElementDeclaration) tmpObj;
            }

        }

        /*
         * Finished up to end of complex type if we found a ##any BUT didn't
         * find a following element (we would have reset anyObject = null if we
         * had) that means that this ##any is at the end of it's complex type
         * and therefore the last thing in a sequence (or other XSDParticle)
         * within that complex type (because we would have reset anyObject to
         * null if we found an element following the ##any)
         * 
         * Therefore if it is variable length AND any of it's XSDParticle
         * ancestors are multiple item sequences then that's a problem as XML
         * processor can't tell the difference between the variable multiple
         * ##any's at the end of one sequence iteration and the start of the
         * next sequence iteration.
         */
        if (anyObjectFound && null != anyObject) {
            checkForMultiAnyInMutliSequence(statusArr, anyObject);
        }
    }

    /**
     * Check whether the given ##any object (which has already been proved to be
     * the last thing in a complex type definition by calling method) is a
     * variable length sequence within another multiple sequence.
     * <p>
     * If that's the case it's a problem because the XML processor can't tell
     * between ##any's at the end of one sequence iteration and the elements at
     * the start of the next sequence iteration.
     * 
     * @param anyObject
     */
    private void checkForMultiAnyInMutliSequence(List<IStatus> statusArr,
            EObject anyObject) {
        if (isVariableLength(anyObject)) {
            /**
             * already been proved to be the last thing in a complex type
             * definition by calling method.
             * 
             * And now we see this is variable length (i.e. is itself set as
             * variable length or is the only single object in a nest of
             * sequences the top of which is variable length (that's what
             * isVariableLength() does
             * 
             * So now we need to ensure that this is not is a group of elements
             * that is itself multiple.
             * 
             * i.e. if you have
             * 
             * <pre>
             * <xsd:sequence maxOccurs="12" minOccurs="12">
             *      <xsd:sequence maxOccurs="1" minOccurs="1">
             *      
             *          <xsd:element name="er" maxOccurs="1" minOccurs="1"></xsd:element>
             *           
             *          <xsd:sequence maxOccurs="unbounded" minOccurs="1">
             *              <xsd:any maxOccurs="1" minOccurs="1" namespace="##any" processContents="lax"/>
             *          </xsd:sequence>
             * </xsd:sequence>
             * </pre>
             * 
             * Then XML processor can't cope as can't tell between ##any's at
             * end of one iteration of the maxOccurs=12 block and the start of
             * the next iteration.
             * 
             * So go back till we get to a multi item group and then start
             * checking if it or any of it's ancestors are within a multiple
             * item sequence.
             */
            EObject node = anyObject.eContainer();
            boolean foundMultiElementSequence = false;

            while (node != null && !(node instanceof XSDComplexTypeDefinition)) {

                if (node instanceof XSDModelGroup) {
                    if (((XSDModelGroup) node).getContents().size() > 1) {
                        foundMultiElementSequence = true;
                    }
                }

                if (foundMultiElementSequence) {
                    if (node instanceof XSDParticle) {

                        int maxOccurs = ((XSDParticle) node).getMaxOccurs();

                        if (maxOccurs > 1 || maxOccurs == -1) {
                            /*
                             * Have variable length ##any contained at end of a
                             * group of other elements that is itself multiple -
                             * that's a problem (see above)
                             */
                            statusArr
                                    .add(new Status(
                                            IStatus.ERROR,
                                            Activator.PLUGIN_ID,
                                            WorkingCopyUtil.getFile(anyObject)
                                                    + " :: " //$NON-NLS-1$
                                                    + Messages.XtendTransformer_any_cannot_be_endof_multi_item_group));
                            return;
                        }
                    }
                }

                node = node.eContainer();
            }
        }
    }

    /**
     * Ensure that for an element following a ##any, that the ##any has valid
     * min-max values (i.e. must not be variable length sequence
     * 
     * @param statusArr
     * @param elementObject
     * @param anyObject
     */
    private void compareFollowingElementMinMax(List<IStatus> statusArr,
            EObject elementObject, EObject anyObject) {

        boolean isVariableLength = isVariableLength(anyObject);

        if (isVariableLength) {
            statusArr
                    .add(new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            WorkingCopyUtil.getFile(anyObject) + " :: " //$NON-NLS-1$
                                    + String.format(Messages.XtendTransformer_cannot_have_element_after_any_with_multiplicity_defined_message,
                                            (elementObject != null ? ((XSDElementDeclaration) elementObject)
                                                    .getName() : "")))); //$NON-NLS-1$
        }

    }

    /**
     * Ensure that the element preceeding a ##any has valid min-max values (i.e.
     * must not be variable length.
     * 
     * @param statusArr
     * @param elementDeclaration
     * @param anyObject
     */
    private void comparePreceedingElementMinMax(List<IStatus> statusArr,
            XSDElementDeclaration elementDeclaration, EObject anyObject) {

        boolean isVariableLengthFound = isVariableLength(elementDeclaration);

        if (isVariableLengthFound) {
            statusArr
                    .add(new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            WorkingCopyUtil.getFile(anyObject)
                                    + " :: " //$NON-NLS-1$
                                    + Messages.XtendTransformer_cannot_have_element_with_mulitiplicity_before_any_message));
        }
    }

    /**
     * @param element
     * @param stopAtAncestor
     * 
     * @return <code>true</code> if the given element is a variable length
     *         sequence, or is within a variable length sequence up to but NOT
     *         including stopAtAncestor
     */
    private boolean isVariableLength(EObject element) {

        EObject node = element;

        while (node != null) {

            /**
             * An element is ITSELF multiple instance if IT's containing
             * particle says so OR a parent sequence particle says so BUT only
             * if the element is the only one in this set of particles.
             * 
             * i.e. <xsd:any maxOccurs="10"/> ; the containing XSDPaticle max
             * Occurs is 10 and its the only thing there.
             * 
             * <pre>
             * Same for: 
             *   <sequence maxOccurs="10">
             *     <sequence maxOccurs="1">
             *       <xsd:any maxOccurs="1"/> 
             *     </sequence>
             * </sequence>
             * 
             * But the ##any below is NOT multiple...
             *   <sequence maxOccurs="10">
             *       <xsd:element name="fred" type="string"/>
             *       <xsd:any maxOccurs="1"/> 
             *   </sequence>
             * </pre>
             * 
             * Therefore as soon as we step up to an ancestor with multiple
             * children we are no longer in defining the multiplicity for the
             * given element alone, we are defining the multiplicity for a group
             * of elements/##anys
             */
            if (node instanceof XSDModelGroup) {
                if (((XSDModelGroup) node).getContents().size() > 1) {
                    break;
                }
            }

            if (node instanceof XSDParticle) {

                int maxOccurs = ((XSDParticle) node).getMaxOccurs();
                int minOccurs = ((XSDParticle) node).getMinOccurs();
                if (minOccurs != maxOccurs) {
                    return true;
                }
            }

            node = node.eContainer();
        }

        return false;
    }

    /***
     * 
     * @param node
     * @return
     */
    EObject getComplexTypeOrGroupContainer(EObject node) {
        if (node instanceof XSDComplexTypeDefinition) {
            return node;
        }
        if (node instanceof XSDModelGroupDefinition) {
            return node;
        }
        return getComplexTypeOrGroupContainer(node.eContainer());
    }

    /**
     * @param key
     * @param container
     * @return
     */
    private String getElementOrAttributeKey(String key, EObject container) {
        if (container instanceof XSDComplexTypeDefinition) {
            XSDComplexTypeDefinition xsdComplexTypeDefinition =
                    (XSDComplexTypeDefinition) container;
            String name = xsdComplexTypeDefinition.getName();
            if ((name == null || name.trim().length() == 0)
                    && container.eContainer() instanceof XSDElementDeclaration) {
                XSDElementDeclaration xsdElementDeclaration =
                        (XSDElementDeclaration) container.eContainer();
                name = xsdElementDeclaration.getName();
            }
            String newKey = name + "*" + key; //$NON-NLS-1$
            return getElementOrAttributeKey(newKey, container.eContainer());
        } else if (container instanceof XSDAttributeGroupDefinition) {
            XSDAttributeGroupDefinition xsdAttributeGroupDefinition =
                    (XSDAttributeGroupDefinition) container;
            String newKey = xsdAttributeGroupDefinition.getName() + "*" + key; //$NON-NLS-1$
            return getElementOrAttributeKey(newKey, container.eContainer());
        } else if (container instanceof XSDModelGroupDefinition) {
            XSDModelGroupDefinition xsdModelGroupDefinition =
                    (XSDModelGroupDefinition) container;
            String newKey = xsdModelGroupDefinition.getName() + "*" + key; //$NON-NLS-1$
            return getElementOrAttributeKey(newKey, container.eContainer());
        } else if (container instanceof XSDSchema) {
            XSDSchema xsdSchema = (XSDSchema) container;
            String newKey = xsdSchema.getTargetNamespace() + "*" + key; //$NON-NLS-1$
            return newKey;
        } else {
            return getElementOrAttributeKey(key, container.eContainer());
        }
    }

    /**
     * Checks if parent is element or the complex type
     * 
     * @param complexType
     * @param container
     * @return
     */
    private boolean elementParent(XSDComplexTypeDefinition complexType,
            EObject container) {
        if (container.equals(complexType)) {
            return false;
        } else if (container instanceof XSDElementDeclaration) {
            return true;
        } else {
            return elementParent(complexType, container.eContainer());
        }
    }

    /**
     * Get containing complex type for an element
     * 
     * @param element
     * @return
     */
    private XSDComplexTypeDefinition getElementComplexContainer(EObject element) {
        if (element.eContainer() instanceof XSDComplexTypeDefinition) {
            return (XSDComplexTypeDefinition) element.eContainer();
        } else {
            return getElementComplexContainer(element.eContainer());
        }
    }

    /**
     * @param resource
     * @throws IOException
     */
    private void fixCommentSpaces(Resource resource) throws IOException {
        IFile bomFile = WorkspaceSynchronizer.getFile(resource);
        String bomFileContents = FileUtil.getFileContents(bomFile);

        CharSequence inputStr = bomFileContents;
        String patternStr = "&amp;#xD;&amp;#xA;"; //$NON-NLS-1$       

        // Compile regular expression
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(inputStr);

        // Replace all occurrences of pattern in input
        StringBuffer buf = new StringBuffer();
        while (matcher.find()) {
            // Get the match result
            String replaceStr = matcher.group();

            replaceStr = replaceStr.replace(patternStr, "&#xD;&#xA;"); //$NON-NLS-1$                        

            // Insert replacement
            matcher.appendReplacement(buf, replaceStr);
        }
        matcher.appendTail(buf);

        // Get result
        bomFileContents = buf.toString();

        FileUtil.setFileContents(bomFile, bomFileContents);
    }
}
