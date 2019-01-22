/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.wsdltransform.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.bom.wsdltransform.Activator;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.bom.wsdltransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.imports.progress.Xsd2BomMonitorMessage;
import com.tibco.xpd.bom.xsdtransform.imports.template.TransformHelper;
import com.tibco.xpd.bom.xsdtransform.internal.WsdlSchemaIndexManager;
import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.bom.xsdtransform.xsdindexing.XsdUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.UnprotectedWriteOperation;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.AbstractValidatingIncrementalProjectBuilder;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * Builder to build BOM artifacts from WSDLs.
 * 
 * @author rsomayaj
 * 
 */
public class WsdlToBomBuilder extends
        AbstractValidatingIncrementalProjectBuilder {

    /**
     * Storing the WSDL special folder relative path on a BOM file generated for
     * WSDL or any of its BOM's
     */
    private static final String WSDL_SOURCES_PROPERTY_DELIMITER = ","; //$NON-NLS-1$

    /**
     * 
     */
    private static final String XSD_FILE_EXTN = "xsd"; //$NON-NLS-1$

    private static final String MARKER_TYPE =
            "com.tibco.xpd.bom.wsdltransform.wsdlToBomMarker"; //$NON-NLS-1$

    private final static ResourceSet rSet = new ResourceSetImpl();

    /**
     * Qualified name to identify the WSDL file name from which the BOM was
     * derived.
     */
    final static QualifiedName WSDLSRC_QUALIFIEDNAME = new QualifiedName(
            "wsdltobom", "wsdlsrc"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * XPD-6119: This property is stored in the generated BOM which contains all
     * the WSDL sources that lead to generation of the BOM. It is updated
     * whenever any of BOM's wsdl sources are deleted or on BOM generation.
     * 
     * Before XPD-6029, we were using persistent property on the model as we
     * were not reusing generated BOMs and we had the persistent property
     * available in the builder to decide whether to re-generate/delete BOMs
     * when source WSDLs are changed. or delete that h
     */
    private static final String BOM_WSDL_SOURCES_ATTRIBUTE = "bomWsdlSources"; //$NON-NLS-1$

    /**
     * 
     */

    private static Map<IFile, Collection<String>> outputBOMFileNamesForWSDL =
            new HashMap<IFile, Collection<String>>();

    /**
     * 
     */
    private static final String BOM_SPECIAL_FOLDER_KIND =
            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;

    /**
     * 
     */
    private static final String WSDL_SPECIAL_FOLDER_KIND =
            WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND;

    private static final Logger LOG = Activator.getDefault().getLogger();

    private static final String DEFAULT_WSDL_FILE_EXTENSION =
            WsdlUIPlugin.WSDL_FILE_EXTENSION;

    public static final String GENERATED_BOM_FOLDER =
            BOMResourcesPlugin.GENERATED_BOM_SF_NAME;

    private List<IFolder> wsdlFolders;

    /**
     * 
     */
    public WsdlToBomBuilder() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.AbstractValidatingIncrementalProjectBuilder#
     * doBuild(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor,
     * java.util.Set)
     */
    @Override
    protected IProject[] doBuild(int kind, Map args, IProgressMonitor monitor,
            Set<IResource> filesToValidate) throws CoreException {
        LOG.trace(Logger.CATEGORY_BUILD, "BUILD STARTED: WsdlToBomBuilder:" //$NON-NLS-1$
                + getProject());
        long start = System.currentTimeMillis();

        /*
         * Keep track of the WSDLs for which new bom's were built. From this we
         * will build a list of dependent files that should be revalidated.
         */
        Set<IResource> affectingSources = new HashSet<IResource>();

        initSpecialFolders();
        IResourceDelta delta = getDelta(getProject());

        /*
         * XPD-6029: If the Generated BOM folder is marked as derived then set
         * it to not derived here. This will make sure that this folder will not
         * be ignored during export and check-in to repo.
         */
        IFolder genFolder = getGeneratedBOMFolder(getProject(), false);
        if (genFolder != null && genFolder.isDerived()) {
            try {
                genFolder.setDerived(false);
            } catch (CoreException e) {
                LOG.error(e, String
                        .format("Failed to mark '%s' folder as not-derived", //$NON-NLS-1$
                                genFolder.getName()));
            }
        }

        if (delta != null && !isConfigUpdated(delta)) {
            incrementalBuild(delta, monitor, affectingSources);
        } else {
            fullbuild(monitor, affectingSources);
        }

        /*
         * Get files that are dependent on the source WSDL(s) we have built.
         * 
         * Then return them as files to revalidate.
         */
        for (IResource wsdlFile : affectingSources) {
            Collection<IResource> dependeRes =
                    WorkingCopyUtil.getAffectedResources(wsdlFile);
            filesToValidate.add(wsdlFile);
            if (dependeRes != null && !dependeRes.isEmpty()) {
                filesToValidate.addAll(dependeRes);
            }
        }

        LOG.trace(Logger.CATEGORY_BUILD,
                String.format("BUILD FINISHED: WsdlToBomBuilder: %s [%d ms]", //$NON-NLS-1$
                        getProject(),
                        (System.currentTimeMillis() - start)));
        return null;
    }

    private void incrementalBuild(IResourceDelta delta,
            final IProgressMonitor monitor,
            final Set<IResource> affectingSources) {

        try {
            delta.accept(new IResourceDeltaVisitor() {

                public boolean visit(IResourceDelta delta) throws CoreException {
                    int kind = delta.getKind();
                    if (kind == IResourceDelta.REMOVED) {
                        handleResourceRemoved(monitor, delta.getResource());
                    } else if (kind == IResourceDelta.ADDED
                            || kind == IResourceDelta.CHANGED) {
                        handleResourceChanged(monitor,
                                delta.getResource(),
                                affectingSources);
                    }
                    checkCancel(monitor, true);
                    return true;
                }
            });
        } catch (CoreException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }
    }

    private void fullbuild(final IProgressMonitor monitor,
            final Set<IResource> affectingSources) {
        try {
            final Set<IResource> filesToBuild = new HashSet<IResource>();

            for (IFolder wsdFolder : wsdlFolders) {
                try {
                    wsdFolder.accept(new IResourceVisitor() {
                        public boolean visit(IResource resource)
                                throws CoreException {

                            if (resource instanceof IFile) {
                                filesToBuild.add(resource);
                            }

                            checkCancel(monitor, true);
                            return true;
                        }

                    });
                } catch (CoreException e) {
                    LOG.error(e);
                    throw new RuntimeException(e);
                }
            }

            monitor.beginTask("", filesToBuild.size()); //$NON-NLS-1$

            if (!filesToBuild.isEmpty()) {
                for (IResource resource : filesToBuild) {
                    handleResourceChanged(new SubProgressMonitor(monitor, 1),
                            resource,
                            affectingSources);
                    checkCancel(monitor, true);
                }
            }

        } finally {
            monitor.done();
        }
    }

    /**
     * Handles if either the WSDL or XSD resource is removed. If an XSD which is
     * referred to by any other WSDL is removed then iterate through the
     * workspace files to find out the WSDL that has changed, and then remove
     * BOMs that were created for that WSDL.
     * 
     * @param monitor
     * @param resource
     * @throws CoreException
     */
    private void handleResourceRemoved(IProgressMonitor monitor,
            IResource resource) throws CoreException {
        if (!(resource instanceof IFile)) {
            return;
        }
        IFile wkspaceFile = (IFile) resource;
        String fileExtension = resource.getFileExtension();
        if (fileExtension != null) {
            if (isWsdlAndInServicesSpecialFolder(wkspaceFile)) {
                // If the file deleted is a WSDL File - then delete the BOMs
                // associated with the WSDL file
                deleteBomsAssociatedWithWsdl(wkspaceFile,
                        getBomsGeneratedFromWsdl(wkspaceFile),
                        true);
            } else if (isXsdInServiceSpecialFolder(wkspaceFile)) {
                // If an XSD is deleted, find the WSDLs referring to it and
                // delete the BOM's and its referring BOMs that are generated
                // for the WSDLs
                List<IFile> wsdlsReferringToXsd =
                        getWsdlsReferringToXsd(wkspaceFile);
                for (IFile iFile : wsdlsReferringToXsd) {
                    deleteBomsAssociatedWithWsdl(iFile,
                            getBomsGeneratedFromWsdl(iFile),
                            true);
                }
            }
        }
    }

    /**
     * Find the list of BOMs created for the WSDL and deletes them all.
     * 
     * @param wsdlResource
     * @param bomFiles
     *            generated from the wsdl file
     * @param doCleanUp
     *            <code>true</code> if the generated bom file is a merged bom
     *            and needs to be cleaned up as a result of the deletion of a
     *            WSDL resource, <code>false</code> just to update the
     *            persistent source property and leave the model as is.
     * @throws CoreException
     */
    private void deleteBomsAssociatedWithWsdl(IFile wsdlResource,
            Collection<IFile> bomFiles, boolean doCleanUp) throws CoreException {
        // Iterate through each of the bomFilesToDelete - identify whether any
        // of the BOM files has more than one WSDL associated, if it does then
        // remove it from the set and modify the wsdlSources attribute in the
        // BOM model

        IPath wsdlFileNameSpFolderRelPath =
                SpecialFolderUtil.getSpecialFolderRelativePath(wsdlResource,
                        WSDL_SPECIAL_FOLDER_KIND);
        Set<IFile> bomFilesToDelete = new HashSet<IFile>();
        for (IFile bomRes : bomFiles) {
            if (doesBomBelongToWsdl(bomRes,
                    wsdlFileNameSpFolderRelPath.toString())) {
                // wsdlSource property in the BOM may contain multiple WSDL
                // names, if the BOM is generated from an XSD that is shared
                // amongst multiple WSDLs.
                WorkingCopy bomWorkingCopy =
                        WorkingCopyUtil.getWorkingCopy(bomRes);
                String bomWsdlSources =
                        getBomWsdlSourcesAttributeValue(bomWorkingCopy, bomRes);
                if (bomWsdlSources != null && bomWsdlSources.length() > 0) {
                    // This means that there are more than one WSDL which are
                    // referring to this BOM generated probably from an XSD.
                    // So, we should remove the WSDL resource name from the
                    // persistent property, and not delete this BOM.
                    String[] wsdlFileSpecialFolderRelPaths =
                            bomWsdlSources
                                    .split(WSDL_SOURCES_PROPERTY_DELIMITER);
                    String[] newBomWsdlSources =
                            new String[wsdlFileSpecialFolderRelPaths.length];
                    int count = 0;
                    for (String pathInWsdlSourcesProperty : wsdlFileSpecialFolderRelPaths) {
                        if (!pathInWsdlSourcesProperty
                                .equals(wsdlFileNameSpFolderRelPath.toString())) {
                            newBomWsdlSources[count++] =
                                    pathInWsdlSourcesProperty;
                        }
                    }
                    if (count < 1) {
                        /*
                         * If there are no new strings in the new array, then
                         * this BOM refers to an XSD which is not referred to by
                         * any WSDL, and so should be deleted.
                         */
                        bomFilesToDelete.add(bomRes);
                        continue;
                    } else {
                        /*
                         * If the new array does contains any strings, then they
                         * have to be strung together and set as the new
                         * property.
                         */
                        StringBuffer strBuffer = new StringBuffer();
                        for (String str : newBomWsdlSources) {
                            if (str != null && !str.equals("null")) { //$NON-NLS-1$
                                if (strBuffer.length() > 0) {
                                    strBuffer
                                            .append(WSDL_SOURCES_PROPERTY_DELIMITER);
                                    strBuffer.append(str);

                                } else {
                                    strBuffer.append(str);
                                }
                            }
                        }
                        updateChecksumAndBomWsdlSources(bomWorkingCopy,
                                bomRes,
                                strBuffer.toString(),
                                false);

                        // If required then clean up the bom model by removing
                        // the elements contributed from the wsdl resource being
                        // deleted (Note: we should update the model that was
                        // used in the update checksum, hence passing the
                        // workingcopy for the BOM)
                        removeRedundantElements(bomWorkingCopy,
                                bomRes,
                                wsdlResource,
                                doCleanUp);
                    }

                } else {
                    bomFilesToDelete.add(bomRes);
                }
            }
        }
        // And delete all the BOM files that are associated with the
        // WSDL file.
        removeFiles(bomFilesToDelete);
    }

    /**
     * Remove any redundant elements from the generated BOM. This will be
     * required when two inline schemas with the same target namespace generates
     * into a single BOM. When one of the WSDLs is deleted the generated BOM
     * needs to be tidied.
     * 
     * @param bomRes
     * @param wsdlSource
     * @param doCleanUp
     *            <code>true</code> if the generated bom file is a merged bom
     *            and needs to be cleaned up as a result of the deletion of a
     *            WSDL resource, <code>false</code> just to update the
     *            persistent source property and leave the model as is.
     * 
     */
    private void removeRedundantElements(WorkingCopy bomWorkingCopy,
            IFile bomRes, IFile wsdlSource, final boolean doCleanUp)
            throws CoreException {

        /*
         * XPD-6119: Changed this method to make changes on the model of the
         * given bomWorkingCopy instead of TransformationEditingDomain so that
         * we have the same model that we used for updating checksum and
         * wsdlSources (otherwise, it may leave some of the BOMs even though the
         * wsdl generating the BOMs have been deleted)
         */
        if (bomWorkingCopy != null) {

            EObject root = bomWorkingCopy.getRootElement();

            if (root instanceof Model) {
                final Model model = (Model) root;

                if (model != null) {
                    IPath path =
                            SpecialFolderUtil
                                    .getSpecialFolderRelativePath(wsdlSource,
                                            com.tibco.xpd.wsdl.Activator.WSDL_SPECIALFOLDER_KIND);
                    if (path != null) {
                        final String wsdlPath = path.toString();

                        try {

                            UnprotectedWriteOperation op =
                                    new UnprotectedWriteOperation(
                                            (TransactionalEditingDomain) bomWorkingCopy
                                                    .getEditingDomain()) {
                                        @Override
                                        protected Object doExecute() {

                                            boolean changeMade = false;
                                            if (doCleanUp) {
                                                changeMade =
                                                        WsdlSchemaIndexManager
                                                                .removeRedundantElements(model);
                                            }
                                            changeMade |=
                                                    removeSchemaLocationInStereotype(model,
                                                            wsdlPath);
                                            if (changeMade) {
                                                return Status.OK_STATUS;
                                            }
                                            return null;
                                        }
                                    };

                            Status status = (Status) op.execute();

                            if (Status.OK_STATUS.equals(status)) {

                                bomWorkingCopy.save();

                            }

                        } catch (Exception e) {
                            throw new CoreException(new Status(IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    e.getLocalizedMessage(), e));
                        }
                    }
                }
            }
        }
    }

    /**
     * Remove the given wsdl path from the Model's
     * XsdBasedModel.xsdSchemaLocation stereotype attribute.
     * 
     * @param model
     *            Model to update
     * @param wsdlPath
     *            the wsdl path to remove from the XsdSchemaLocation stereotype
     *            attribute.
     * 
     * @return <code>true</code> if the Model was updated.
     */
    private boolean removeSchemaLocationInStereotype(Model model,
            String wsdlPath) {

        Stereotype st = getXsdBasedModelStereotype(model);

        if (st != null) {
            Object value =
                    model.getValue(st, XsdStereotypeUtils.XSD_SCHEMA_LOCATION);
            if (value instanceof String) {
                String newValue =
                        removeSchemaLocation((String) value, wsdlPath);
                model.setValue(st,
                        XsdStereotypeUtils.XSD_SCHEMA_LOCATION,
                        newValue);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the given string from the schemaLocations string.
     * 
     * @param schemaLocations
     * @param toRemove
     * @return
     */
    private String removeSchemaLocation(String schemaLocations, String toRemove) {
        String[] values =
                schemaLocations
                        .split(XsdStereotypeUtils.SCHEMA_LOCATION_DELIMITER);
        StringBuffer newString = new StringBuffer();

        for (String value : values) {
            if (!toRemove.equals(value)) {
                if (newString.length() > 0) {
                    newString
                            .append(XsdStereotypeUtils.SCHEMA_LOCATION_DELIMITER);
                }
                newString.append(value);
            }
        }
        return newString.toString();
    }

    /**
     * Get the XsdBasedModel stereotype from the given Model.
     * 
     * @param model
     * @return Stereotype if applied, <code>null</code> otherwise.
     */
    private static Stereotype getXsdBasedModelStereotype(Model model) {
        for (Stereotype st : model.getAppliedStereotypes()) {
            if (XsdStereotypeUtils.XSD_BASED_MODEL.equals(st.getName())) {
                return st;
            }
        }
        return null;
    }

    /**
     * Handle WSDLs which are not derived, because for those derived, they would
     * already have corresponding BOMs if referred to.
     */
    private boolean handleResourceChanged(IProgressMonitor monitor,
            IResource resource, Set<IResource> affectingSources) {
        try {
            if (resource instanceof IFile) {
                IFile wkspaceRes = (IFile) resource;
                if (isWsdlAndInServicesSpecialFolder(wkspaceRes)) {
                    monitor.beginTask(Xsd2BomMonitorMessage
                            .getXsd2BomProgressText(wkspaceRes.getName()), 1);
                    monitor.subTask(Xsd2BomMonitorMessage
                            .getXsd2BomProgressText(wkspaceRes.getName()));

                    handleWsdlChanged(new SubProgressMonitor(monitor, 1),
                            affectingSources,
                            wkspaceRes);

                    monitor.worked(1);
                    monitor.done();

                } else if (isXsdInServiceSpecialFolder(wkspaceRes)
                        && getDelta(getProject()) != null) {
                    // Update the BOMs for changes in the XSD when it is an
                    // incremental build and not a full build
                    List<IFile> wsdlReferringToXsd =
                            getWsdlsReferringToXsd(wkspaceRes);

                    monitor.beginTask("", wsdlReferringToXsd.size()); //$NON-NLS-1$

                    // handleWsdlChanged on each of those.
                    for (IFile res : wsdlReferringToXsd) {
                        monitor.subTask(Xsd2BomMonitorMessage
                                .getXsd2BomProgressText(wkspaceRes.getName()));

                        handleWsdlChanged(new SubProgressMonitor(monitor, 1),
                                affectingSources,
                                res);

                        monitor.worked(1);
                    }

                    monitor.done();
                }
            }

        } catch (Exception ex) {
            LOG.error(ex, "Exception while processing the WSDL"); //$NON-NLS-1$
        }
        return true;
    }

    /**
     * The XSDs in the special folder may be referred to by the WSDLs in the
     * same special folder.
     * 
     * @param xsdFile
     * @return WSDLs that are referring to the XSD file passed in as parameter
     */
    private List<IFile> getWsdlsReferringToXsd(final IFile xsdFile)
            throws CoreException {
        /*
         * Earlier - referred xsds were retrieved by navigating thru all the
         * resources under special folder, then iterate thru to find the wsdl
         * for this xsd; get the xsds referred by this wsdl and then check if
         * this xsd is in the list of xsds returned for this wsdl. it was a
         * confusing round way trip.
         * 
         * replaced with the call to following util method
         */
        return Bom2XsdUtil.getWsdlsReferringToXsd(xsdFile);

    }

    /**
     * Returns true if the given IFile is a XSD file and contained in a
     * "Service Descriptors" special folder.
     * 
     * @param wsdlFile
     * @return
     */
    private boolean isXsdInServiceSpecialFolder(IFile file) {
        return isOfExtnAndInWsdlSpecialFolder(file, XSD_FILE_EXTN);
    }

    /**
     * Returns true if the given IFile is a WSDL file and contained in a
     * "Service Descriptors" special folder.
     * 
     * @param file
     * @return
     */
    private boolean isWsdlAndInServicesSpecialFolder(IFile file) {
        return isOfExtnAndInWsdlSpecialFolder(file, DEFAULT_WSDL_FILE_EXTENSION);
    }

    /**
     * Method returns true if the given file is of the given extension and
     * contained in the WSDL special folder.
     * 
     * @param file
     * @param fileExtn
     * @return
     */
    private boolean isOfExtnAndInWsdlSpecialFolder(final IFile file,
            final String fileExtn) {
        if (fileExtn.equalsIgnoreCase(file.getFileExtension())) {
            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(file.getProject());
            SpecialFolder folderContainer =
                    projectConfig.getSpecialFolders().getFolderContainer(file);
            if (folderContainer != null) {
                if (WSDL_SPECIAL_FOLDER_KIND.equals(folderContainer.getKind())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Handles changes to WSDLs with regards to BOM generation that are
     * 
     * 1. Those WSDLs not generated by Studio - which do not contain the tibex
     * XPDL attribute
     * 
     * 2. Those WSDLs which are supported by BDS
     * 
     * 3. Those WSDLs which are valid - validated by the WST Framework
     * validation.
     * 
     * @param monitor
     * @param resource
     * @param affectingSources
     * @param wsdlFile
     */
    private void handleWsdlChanged(IProgressMonitor monitor,
            final Set<IResource> affectingSources, final IFile wsdlFile)
            throws CoreException, IOException {

        if (!containsXpdlAttribute(wsdlFile)) {
            final Set<IFile> bomsGeneratedFromWsdl =
                    getBomsGeneratedFromWsdl(wsdlFile);

            if (WSDLTransformUtil.isBDSSupportEnabled(wsdlFile)
                    && !isWSDLInvalid(wsdlFile)) {

                boolean areBomFileNamesDifferentOrOutOfDate =
                        isAnyBomEarlierVersion(bomsGeneratedFromWsdl);

                if (!areBomFileNamesDifferentOrOutOfDate) {

                    /*
                     * In effect doesn't look like there is anything different
                     * for a full build or an incremental build since we don't
                     * use the IResourceDelta.
                     */
                    // Full build
                    areBomFileNamesDifferentOrOutOfDate =
                            areBomFileNamesDifferent(wsdlFile,
                                    bomsGeneratedFromWsdl);

                    if (!areBomFileNamesDifferentOrOutOfDate) {
                        /*
                         * Find the latest modification file stamp for the WSDL
                         * & any associated XSD
                         */
                        long latestWsdlTIme =
                                getLatestTimeStampFromWsdlFile(wsdlFile);

                        /*
                         * Compare the file stamp with each of the BOM's that
                         * would be created. Also, while processing the files
                         * check if any has an error-level validation problem
                         * and if it has then force re-generation of the BOMs
                         */
                        long earliestBomTime = Long.MAX_VALUE;
                        for (IFile iFile : bomsGeneratedFromWsdl) {

                            if (hasErrorValidationProblem(iFile)) {
                                areBomFileNamesDifferentOrOutOfDate = true;
                                break;
                            }

                            long fileModStamp = getFileModStamp(iFile);
                            if (fileModStamp < earliestBomTime) {
                                earliestBomTime = fileModStamp;
                            }
                        }

                        /*
                         * If any of the BOM's file stamps are less than any of
                         * the WSDLs or referring XSDs then delete the BOMs are
                         * regenerate them.
                         */
                        if (!areBomFileNamesDifferentOrOutOfDate
                                && earliestBomTime < latestWsdlTIme) {
                            areBomFileNamesDifferentOrOutOfDate = true;
                        }
                    }
                }

                if (areBomFileNamesDifferentOrOutOfDate) {

                    /*
                     * If the BOM file names are different or out of date - then
                     * there is some addition or remove to the list of BOMs
                     * generated previously for the same WSDL. Also applies if
                     * any BOM with earlier version than what our current studio
                     * is at
                     */

                    ResourcesPlugin.getWorkspace()
                            .run(new IWorkspaceRunnable() {
                                public void run(IProgressMonitor monitor)
                                        throws CoreException {
                                    /*
                                     * XPD-4460: get all implicit dependencies
                                     * from Error markers on the generated BOMs
                                     * before the BOMs are deleted, to validate
                                     * these resources later.
                                     */
                                    addtAffectingSources(bomsGeneratedFromWsdl,
                                            affectingSources);

                                    deleteBomsAssociatedWithWsdl(wsdlFile,
                                            bomsGeneratedFromWsdl,
                                            false);
                                    generateBom(wsdlFile,
                                            affectingSources,
                                            monitor);
                                    affectingSources.add(wsdlFile);
                                }
                            },
                                    null);
                }
            } else {
                /*
                 * If the wsdl has any errors, then remove all the BOMs
                 * associated with the WSDL.
                 */
                ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                    public void run(IProgressMonitor monitor)
                            throws CoreException {
                        deleteBomsAssociatedWithWsdl(wsdlFile,
                                bomsGeneratedFromWsdl,
                                false);
                    }
                },
                        null);
            }

        }
    }

    /**
     * This method adds the implicit dependency resources from the BOMs to the
     * affected resources list, to be validated these resources later.
     * 
     * @param bomsGeneratedFromWsdl
     * @param affectingSources
     */
    protected void addtAffectingSources(Set<IFile> bomsGeneratedFromWsdl,
            Set<IResource> affectingSources) {

        for (IFile bomResource : bomsGeneratedFromWsdl) {
            IMarker[] markers;
            try {
                markers =
                        bomResource.findMarkers(null,
                                true,
                                IResource.DEPTH_INFINITE);

                String duplicateResources = null;
                for (IMarker marker : markers) {
                    duplicateResources = null;
                    Object severity = marker.getAttribute(IMarker.SEVERITY);
                    if (severity != null && severity instanceof Number) {
                        int value = ((Number) severity).intValue();
                        if (value == IMarker.SEVERITY_ERROR) {
                            Properties info =
                                    ValidationUtil.getAdditionalInfo(marker);
                            if (info != null) {
                                Object linkedResValue =
                                        info.get(ValidationActivator.LINKED_RESOURCE);
                                if (linkedResValue instanceof String) {
                                    duplicateResources =
                                            ((String) linkedResValue);
                                }
                            }
                        }
                    }
                    if (duplicateResources != null) {
                        affectingSources
                                .addAll(getResourcesFromLinkedResource(duplicateResources));
                    }
                }
            } catch (CoreException e) {
                LOG.error(e,
                        String.format("Exception caught while extracting implicit dependencies for Generated BOMS.")); //$NON-NLS-1$
            }
        }

    }

    /**
     * Get the files that are set as the linked resources
     * 
     * @param value
     *            comma-separated URIs of the linked files.
     * @return
     */
    private static Set<IFile> getResourcesFromLinkedResource(String value) {
        Set<IFile> files = new HashSet<IFile>();

        if (value != null && value.length() > 0) {
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

            for (String path : value.split(",")) { //$NON-NLS-1$
                URI uri = URI.createURI(path);

                if (uri != null) {
                    path = uri.toPlatformString(true);
                    if (path != null) {
                        IFile file = root.getFile(new Path(path));

                        if (file != null && file.exists()) {
                            files.add(file);
                        }
                    }
                }
            }
        }

        return files;
    }

    /**
     * Check if the given file has an error-level validation problem.
     * 
     * @param resource
     * @return
     */
    private boolean hasErrorValidationProblem(IResource resource) {
        try {
            int severity =
                    resource.findMaxProblemSeverity(XpdConsts.VALIDATION_MARKER_TYPE,
                            true,
                            IResource.DEPTH_ZERO);

            return IMarker.SEVERITY_ERROR == severity;
        } catch (CoreException e) {
            // Do nothing - let it return true as we have failed to get problem
            // marker (to be safe, regenerate)
            return true;
        }
    }

    /**
     * 
     * @param wkspaceResource
     * @return local time stamp on the {@link IFile}
     */
    private long getFileModStamp(IFile wkspaceResource) {
        return wkspaceResource.getLocalTimeStamp();
    }

    /**
     * This returns the latest timestamp for a given WSDL. This iterates through
     * all the XSD Schemas that are referred to by the WSDL and then evaluate
     * the timestamp.
     * 
     * @param wsdlFile
     * @return maximum local timestamp of the WSDL and the XSDs it is referring
     *         to.
     */
    private long getLatestTimeStampFromWsdlFile(IFile wsdlFile)
            throws IOException {

        List<IFile> xsdsReferringToWsdlFile = getReferencedFiles(wsdlFile);
        // The longest modification stamp is initialised to the WSDL file's
        // local time stamp.
        long longestModStamp = wsdlFile.getLocalTimeStamp();
        // for every referred schema.
        for (IFile xsdFile : xsdsReferringToWsdlFile) {

            long localTimeStamp = xsdFile.getLocalTimeStamp();
            if (longestModStamp < localTimeStamp) {
                longestModStamp = localTimeStamp;
            }
        }

        return longestModStamp;
    }

    /**
     * Get the direct and indirect files the given WSDL file references.
     * 
     * @param wsdlFile
     * @return List of referenced files, empty list if none.
     */
    public static List<IFile> getReferencedFiles(IFile wsdlFile) {
        List<IFile> referenced = new ArrayList<IFile>();

        getReferencedFiles(wsdlFile, referenced);

        return referenced;
    }

    /**
     * Get the files referenced directly and indirectly by the given file.
     * 
     * @param file
     * @param referenced
     */
    private static void getReferencedFiles(IFile file, List<IFile> referenced) {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
        if (wc != null) {
            List<IResource> dependency = wc.getDependency();
            for (IResource res : dependency) {
                if (res instanceof IFile) {
                    if (!referenced.contains(res)) {
                        referenced.add((IFile) res);
                        // Get its referenced files
                        getReferencedFiles((IFile) res, referenced);
                    }
                }
            }
        }
    }

    /**
     * @param wsdlFile
     * @param bomsGeneratedFromWsdl
     * @return if the files previously generated for the WSDL are any different
     *         from those that it would be generating this time round.
     */
    private boolean areBomFileNamesDifferent(IFile wsdlFile,
            Set<IFile> bomsGeneratedFromWsdl) {
        // If there are no BOMs generated from the WSDL then needs to be
        // generated
        if (bomsGeneratedFromWsdl.isEmpty()) {
            return true;
        }
        //

        Set<IFile> bomFiles = getBOMFiles(wsdlFile, false, true);
        // If the sizes of the sets differ, then BOMs need to be generated
        if (bomFiles.size() != bomsGeneratedFromWsdl.size()) {
            return true;
        }
        // If an xsdSchema has been added to the WSDL, and the BOM doesn't
        // exist, then the BOM's need to be re-generated.
        if (!bomFiles.containsAll(bomsGeneratedFromWsdl)) {
            return true;
        }

        return false;
    }

    /**
     * Removes the given set of {@link IFile}s
     * 
     * @param bomsGeneratedFromWsdl
     */
    private void removeFiles(Set<IFile> bomsGeneratedFromWsdl) {
        for (IFile iFile : bomsGeneratedFromWsdl) {
            try {
                /*
                 * Don't pass the monitor to delete - it will wipe the current
                 * message and nothing resets after.
                 */
                iFile.delete(true, null);
            } catch (CoreException e) {
                LOG.error(e, "Error deleting Resource"); //$NON-NLS-1$
            }
        }

    }

    /**
     * This method introspects through each of the Generated BOMs and identifies
     * whether it has been generated from the WSDL.
     * 
     * @param wsdlFile
     * @return Set of BOM files if any. NOTE: A BOM file returned in tlist
     */
    // Set to public as it's used by the test unit
    public static Set<IFile> getBomsGeneratedFromWsdl(IFile wsdlFile)
            throws CoreException {
        Set<IFile> bomsGeneratedFromWsdl = new HashSet<IFile>();
        IProject prj = wsdlFile.getProject();
        IFolder generatedBOMFolder = getGeneratedBOMFolder(prj, false);
        if (generatedBOMFolder == null || !generatedBOMFolder.exists()) {
            return Collections.emptySet();
        }

        IPath wsdlFileNameSpFolderRel =
                SpecialFolderUtil.getSpecialFolderRelativePath(wsdlFile,
                        WSDL_SPECIAL_FOLDER_KIND);
        IResource[] members = generatedBOMFolder.members();
        for (IResource iResource : members) {
            if (iResource instanceof IFile) {
                IFile bomRes = (IFile) iResource;

                if (doesBomBelongToWsdl(bomRes,
                        wsdlFileNameSpFolderRel.toString())) {
                    bomsGeneratedFromWsdl.add(bomRes);
                }
            }
        }

        return bomsGeneratedFromWsdl;
    }

    /**
     * It looks in the bomWsdlSources property of the given BOM and retruns true
     * if the given wsdl is found in it.
     * 
     * @param bomRes
     * @param wsdlFileNameSpFolderRel
     * @return <code>true</code> if the wsdlSources attribute in the bomres
     *         contains the wsdlFileNameSpFolderRel value.
     */
    private static boolean doesBomBelongToWsdl(IFile bomRes,
            String wsdlFileNameSpFolderRel) throws CoreException {
        WorkingCopy bomWorkingCopy = WorkingCopyUtil.getWorkingCopy(bomRes);
        if (bomWorkingCopy != null) {
            String wsdlSources =
                    getBomWsdlSourcesAttributeValue(bomWorkingCopy, bomRes);
            if (wsdlSources != null && wsdlSources.length() > 0) {
                String[] wsdlPaths =
                        wsdlSources.split(WSDL_SOURCES_PROPERTY_DELIMITER);
                for (String wsdlPath : wsdlPaths) {
                    if (wsdlFileNameSpFolderRel.equals(wsdlPath)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param resource
     * @return <code>true</code> if the {@link IResource} has Error level
     *         markers.
     */
    private boolean isWSDLInvalid(IResource resource) {
        try {
            IMarker[] markers =
                    resource.findMarkers(null, true, IResource.DEPTH_INFINITE);
            for (IMarker marker : markers) {
                Object severity = marker.getAttribute(IMarker.SEVERITY);
                if (severity != null && severity instanceof Number) {
                    int value = ((Number) severity).intValue();
                    if (value == IMarker.SEVERITY_ERROR) {
                        return true;
                    }
                }
            }
        } catch (CoreException e) {
            LOG.error("Cant retrieve markers"); //$NON-NLS-1$
        }
        return false;
    }

    /**
     * @param resource
     * @return <code>true</code> if the WSDL is a Studio generated and has the
     *         the tibex:XPDL attribute in its Definition element.
     */
    private boolean containsXpdlAttribute(IResource resource) {
        WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
        if (null != workingCopy) {

            EObject rootElement = workingCopy.getRootElement();
            if (rootElement instanceof Definition) {
                Definition defn = (Definition) rootElement;
                if (null != defn.getElement()) {
                    String attribute =
                            defn.getElement()
                                    .getAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);
                    if (null != attribute && attribute.length() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Calls the transformer to generate a given WSDL file into relevant
     * Business Object Models.
     * 
     * @param wsdlResource
     * @param affectingSources
     * @param monitor
     */
    private void generateBom(IResource wsdlResource,
            Set<IResource> affectingSources, IProgressMonitor monitor) {

        outputBOMFileNamesForWSDL.clear();

        IProject proj = wsdlResource.getProject();
        boolean isWsdlToBomDestEnabled =
                BOMValidatorActivator
                        .getDefault()
                        .isValidationDestinationEnabled(wsdlResource.getProject(),
                                BOMValidatorActivator.VALIDATION_DEST_ID_WSDL_TO_BOM);
        boolean isBomAssetPresent = validateBomAssetIsPresent(proj);

        if (!isWsdlToBomDestEnabled || !isBomAssetPresent) {
            /*
             * Do not generate BOM from wsdl unless project has selected a
             * global destination env that includes the WSDL->BOM validation
             * component
             */
            return;
        }

        if (wsdlResource != null && wsdlResource.exists()
                && wsdlResource instanceof IFile) {
            Set<IFile> bomFiles =
                    getBOMFiles((IFile) wsdlResource, true, false);
            if (!bomFiles.isEmpty()) {
                List<IStatus> transformIssues =
                        WSDLTransformUtil.transformWSDLtoBOM(wsdlResource
                                .getLocation().toFile(), bomFiles.iterator()
                                .next().getFullPath(), monitor);
                int errors = 0;
                for (IStatus status : transformIssues) {
                    if (status.getSeverity() == IStatus.ERROR) {
                        errors++;
                    }
                }
                if (errors > 0) {
                    for (IStatus status : transformIssues) {
                        if (status.getSeverity() == IStatus.ERROR) {
                            LOG.info("WSDLToBOMBuilder::generateBOM:Problem with the WSDL:" //$NON-NLS-1$
                                    + wsdlResource.getFullPath()
                                            .toPortableString() + "->" //$NON-NLS-1$
                                    + status.getMessage());
                        }
                    }

                    removeFiles(bomFiles);
                } else {
                    String wsdlSpFolderRelPath = null;

                    IPath specialFolderRelativePath =
                            SpecialFolderUtil
                                    .getSpecialFolderRelativePath(wsdlResource,
                                            WSDL_SPECIAL_FOLDER_KIND);
                    if (specialFolderRelativePath != null) {
                        wsdlSpFolderRelPath =
                                specialFolderRelativePath.toPortableString();
                    }
                    for (IFile bomFile : bomFiles) {
                        if (bomFile.exists()) {
                            try {
                                /*
                                 * XPD-6029:Add checksum and wsdlSources
                                 * attributes to the generated BOM
                                 */

                                WorkingCopy bomWorkingCopy =
                                        WorkingCopyUtil.getWorkingCopy(bomFile);
                                if (bomWorkingCopy != null) {
                                    bomWorkingCopy.reLoad();

                                    /*
                                     * Get the updated WSDL sources of the
                                     * generated BOM
                                     */
                                    String bomWsdlSources =
                                            getUpdatedBomWsdlSources(bomWorkingCopy,
                                                    bomFile,
                                                    wsdlSpFolderRelPath);
                                    // XpdResourcesPlugin
                                    // .getDefault()
                                    // .getLogger()
                                    // .info("UPDATE CHECKSUM/WSDLSOUCES: "
                                    // + bomFile.getName());

                                    updateChecksumAndBomWsdlSources(bomWorkingCopy,
                                            bomFile,
                                            bomWsdlSources,
                                            true);
                                }

                                affectingSources.add(bomFile);

                                // Immediately index the bom file so the
                                // indexer is ready for down-stream builders.
                                index(bomFile);
                            } catch (CoreException e) {
                                LOG.error(e);
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * This does two things: <li>Updates the checksum value based on all the
     * WSLD/XSD sources used to build this BOM into this BOM's annotations (if
     * given updateChecksum is true).</li>
     * 
     * <li>Adds the given bomWsdlSources to the BOM's wsdlSources attribute. The
     * can be used to identify which WSDLs this BOM has been created from. If
     * there are more than one WSDL referring to the XSD(for which the BOM is
     * being created) then all the WSDLs name should be appended to a string
     * which is separated by a ","</li>
     * 
     * 
     * @param bomFile
     * @param bomWsdlSources
     * @param updateChecksum
     *            - set <code>true</code> if checksum should be updated or
     *            <code>false</code> if only wsdlSources should be added
     */
    private void updateChecksumAndBomWsdlSources(WorkingCopy bomWorkingCopy,
            final IFile bomFile, final String bomWsdlSources,
            final boolean updateChecksum) {

        IStatus status = null;

        String checksum = null;
        if (updateChecksum) {
            checksum =
                    Bom2XsdUtil
                            .calculateChecksumUsingGeneratedBomSources(bomFile);
        }

        final String checksumValue = checksum;
        if (bomWorkingCopy != null) {

            EObject root = bomWorkingCopy.getRootElement();

            if (root instanceof Model) {
                final Model model = (Model) root;
                final Stereotype st = getXsdBasedModelStereotype(model);

                /*
                 * Update checksum/wsdlSources changes the BOM model but not on
                 * command stack so we have to do in a separate write
                 * transaction.
                 */
                UnprotectedWriteOperation op =
                        new UnprotectedWriteOperation(
                                (TransactionalEditingDomain) bomWorkingCopy
                                        .getEditingDomain()) {

                            @Override
                            protected Object doExecute() {
                                // add checksum
                                if (updateChecksum && checksumValue != null) {
                                    setBOMCheckSum(model, checksumValue);
                                }
                                // add wsdlSources
                                if (st != null) {
                                    model.setValue(st,
                                            BOM_WSDL_SOURCES_ATTRIBUTE,
                                            bomWsdlSources);
                                }
                                return Status.OK_STATUS;
                            }
                        };

                status = (IStatus) op.execute();
            }

            /**
             * Then save (Don't save inside transaction as this causes an issue.
             */
            if (status != null && status.isOK()) {
                try {
                    bomWorkingCopy.save();

                } catch (IOException e) {
                    Activator
                            .getDefault()
                            .getLogger()
                            .error("Failed to save generated BOM after checksum/WSDL sources update: "
                                    + bomFile.getFullPath()
                                    + " :: " //$NON-NLS-1$
                                    + new Status(IStatus.WARNING,
                                            Activator.PLUGIN_ID, e.getMessage()));
                }
            } else {
                Activator
                        .getDefault()
                        .getLogger()
                        .error("Checksum/WSDL sources update failure on generated BOM: "
                                + bomFile.getFullPath() + " :: " //$NON-NLS-1$
                                + (status != null ? status.getMessage() : "")); //$NON-NLS-1$
            }
        }
    }

    /**
     * Adds the given wsdlSpFolderRelPath to the BOM's wsdlSources attribute and
     * returns the updated wsdlSources property value.
     * 
     * @param bomWorkingCopy
     * @param bomFile
     * @param wsdlSpFolderRelPath
     */
    private String getUpdatedBomWsdlSources(WorkingCopy bomWorkingCopy,
            IFile bomFile, String wsdlSpFolderRelPath) throws CoreException {

        // Get the BOM's 'wsdlSources' attribute
        String wsdlSources =
                getBomWsdlSourcesAttributeValue(bomWorkingCopy, bomFile);

        StringBuffer wsdlSourcesNew = null;
        if (wsdlSources != null && wsdlSources.length() > 0) {

            wsdlSourcesNew = new StringBuffer(wsdlSources);

            // only add if it doesn't exist already
            boolean exists = false;
            String[] existingWsdlFileSpecialFolderRelPaths =
                    wsdlSources.split(WSDL_SOURCES_PROPERTY_DELIMITER);
            for (String pathInWsdlSourcesProperty : existingWsdlFileSpecialFolderRelPaths) {
                if (pathInWsdlSourcesProperty.equals(wsdlSpFolderRelPath)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                wsdlSourcesNew.append(WSDL_SOURCES_PROPERTY_DELIMITER);
                wsdlSourcesNew.append(wsdlSpFolderRelPath);
            }
        } else if (wsdlSources == null || wsdlSources.length() == 0) {
            wsdlSourcesNew = new StringBuffer();
            wsdlSourcesNew.append(wsdlSpFolderRelPath);
        }
        return wsdlSourcesNew.toString();
    }

    /**
     * Returns the value of 'bomWsdlSources' attribute in the given BOM's model
     * or <code>null</code>
     * 
     * @param bomFile
     * @return bomWsdlSources or <code>null</code>
     */
    private static String getBomWsdlSourcesAttributeValue(
            WorkingCopy bomWorkingCopy, IFile bomFile) {

        if (bomWorkingCopy != null) {
            EObject root = bomWorkingCopy.getRootElement();

            if (root instanceof Model) {
                final Model model = (Model) root;
                Stereotype st = getXsdBasedModelStereotype(model);

                if (st != null) {
                    Object value =
                            model.getValue(st, BOM_WSDL_SOURCES_ATTRIBUTE);
                    if (value instanceof String) {
                        return (String) value;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Adds the given checksum value inside the model.metadata annotation in the
     * given model
     * 
     * @param model
     * @param checksum
     */
    private void setBOMCheckSum(Model model, String checksum) {
        if (model != null && model.getEAnnotations() != null) {
            EAnnotation eAnnotation =
                    model.getEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);
            if (eAnnotation != null) {
                eAnnotation
                        .getDetails()
                        .put(BOMResourcesPlugin.ModelEannotationMetaSource_checksum,
                                checksum);
            }
        }
    }

    /**
     * Provides an indication as to whether BOM asset is present. Also
     * explicitly controls the appearance of a problem marker.
     * 
     * @param proj
     *            Project
     * @return <code>true</code> if BOM asset is already present for the project
     */
    private boolean validateBomAssetIsPresent(IProject proj) {

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(getProject());
        String bomAssetId =
                com.tibco.xpd.bom.resources.ui.Activator.BOM_ASSET_ID;
        IProjectAsset bomAsset = config.getAssetById(bomAssetId);

        boolean present = false;
        for (AssetType assetType : config.getAssetTypes()) {
            if (assetType.getId().equals(bomAsset.getId())) {
                present = true;
                break;
            }
        }

        manageBomAssetMissingError(!present);

        return present;
    }

    /**
     * Controls appearance of problem marker to indicate required but missing
     * BOM asset
     * 
     * @param show
     *            whether to show problem marker
     */
    private void manageBomAssetMissingError(boolean show) {
        IProject proj = getProject();

        if (proj.isAccessible()) {
            try {
                IMarker[] markers =
                        proj.findMarkers(MARKER_TYPE,
                                false,
                                IResource.DEPTH_ZERO);
                boolean alreadyCreated = false;

                for (IMarker marker : markers) {
                    if (Activator.PLUGIN_ID.equals(marker
                            .getAttribute(IMarker.SOURCE_ID))) {
                        alreadyCreated = true;
                        break;
                    }
                }

                if (show) {

                    if (!alreadyCreated) {
                        IMarker marker = proj.createMarker(MARKER_TYPE);
                        marker.setAttribute(IMarker.MESSAGE,
                                Messages.WsdlToBomBuilder_missingBomAsset_error_message);
                        marker.setAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_ERROR);
                        marker.setAttribute(IMarker.SOURCE_ID,
                                Activator.PLUGIN_ID);
                    }
                } else {
                    proj.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_ZERO);
                }

            } catch (CoreException e) {
            }

        }
    }

    /**
     * Index the given file.
     * 
     * @param bomFile
     */
    private void index(IFile bomFile) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
        if (wc != null) {
            ((IndexerServiceImpl) XpdResourcesPlugin.getDefault()
                    .getIndexerService()).index(wc);
        }
    }

    /**
     * Check if the config file has been updated. This can happen when special
     * folders have changed/created/removed. It will also check if the config
     * file has been deleted.
     * 
     * @param delta
     *            changed resource delta.
     * @return <code>true</code> if the config file is an affected child of the
     *         resource delta, <code>false</code> otherwise.
     */
    private boolean isConfigUpdated(IResourceDelta delta) {
        boolean updated = false;

        if (delta != null) {
            IResourceDelta[] children = delta.getAffectedChildren();

            for (IResourceDelta child : children) {
                if (child.getResource().getProjectRelativePath().toString()
                        .equals(XpdResourcesPlugin.PROJECTCONFIGFILE)) {
                    // Only return true if contents of the file have changed or
                    // the file has been removed
                    updated =
                            child.getKind() == IResourceDelta.REMOVED
                                    || (child.getKind() == IResourceDelta.CHANGED && (child
                                            .getFlags() & IResourceDelta.CONTENT) != 0);
                    break;
                }
            }
        }

        return updated;
    }

    /**
     * 
     */
    private void initSpecialFolders() {
        wsdlFolders = getServiceDescriptorFolders(getProject());
    }

    /**
     * Returns list of eclipse folders which are marked as special packages
     * folder for provided project.
     * 
     * @param project
     *            the eclipse project.
     * @return list of special packages folders for the project.
     */
    public static List<IFolder> getServiceDescriptorFolders(IProject project) {
        SpecialFolders specialFolders =
                XpdResourcesPlugin.getDefault().getProjectConfig(project)
                        .getSpecialFolders();
        if (specialFolders != null) {
            return specialFolders
                    .getEclipseIFoldersOfKind(WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND);
        }
        return Collections.emptyList();
    }

    /**
     * Having passed and a WSDL file, this utility returns the BOM files that
     * have been or should be related to the given WSDL or XSD file.
     * 
     * @param wsdlOrXsdFile
     *            The WSDL or XSD File to return BOM files for.
     * @param shouldCreateGenBOMFolder
     *            <code>true</code> to return the BOMs that have OR should be
     *            related to the given WSDL/XSD. <code>false</code> will only
     *            return BOMs that HAVE been created
     * @param createBomsThatAreNotYetCreated
     *            <code>true</code> to return the BOMs that are to be created
     *            related to the given WSDL/XSD if they are not yet created.
     *            <code>false</code> will only return BOMs that HAVE been
     *            created
     * 
     * @return BOM Files related to the given WSDL/XSD (according to the
     *         returnBomsThatAreNotYetCreated parameter)
     */
    public static Set<IFile> getBOMFiles(IFile wsdlOrXsdFile,
            boolean shouldCreateGenBOMFolder,
            boolean createBomsThatAreNotYetCreated) {
        IProject xpdlProject = wsdlOrXsdFile.getProject();

        IFolder bomFolder =
                getGeneratedBOMFolder(xpdlProject, shouldCreateGenBOMFolder);
        HashSet<IFile> bomFiles = new HashSet<IFile>();
        if (bomFolder != null && bomFolder.exists()) {

            WorkingCopy wsdlWorkingCopy =
                    XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(wsdlOrXsdFile);
            if (wsdlWorkingCopy != null) {
                EObject wsdlRootElement = wsdlWorkingCopy.getRootElement();

                /*
                 * For generated WSDL iterate through each XSD Schema and find
                 * the BOM Origin file
                 */
                if (wsdlRootElement instanceof Definition
                        && WSDLTransformUtil
                                .isGeneratedWsdl((Definition) wsdlRootElement)) {
                    Definition definition = (Definition) wsdlRootElement;

                    Types types = definition.getETypes();
                    if (types != null) {
                        List<?> schemas = types.getSchemas();
                        for (Object object : schemas) {
                            if (object instanceof XSDSchema) {
                                XSDSchema schema = (XSDSchema) object;
                                String queryBomOriginNameProvidedTgtNamespace =
                                        XsdUIUtil
                                                .queryBomOriginNameProvidedTgtNamespace(schema
                                                        .getTargetNamespace());

                                if (null != queryBomOriginNameProvidedTgtNamespace) {
                                    IFile bomFile =
                                            ResourcesPlugin
                                                    .getWorkspace()
                                                    .getRoot()
                                                    .getFile(new Path(
                                                            queryBomOriginNameProvidedTgtNamespace));
                                    if (null != bomFile
                                            && bomFile.isAccessible()) {
                                        bomFiles.add(bomFile);
                                    }
                                }
                            }
                        }
                    }

                } else {
                    /*
                     * Not a generated WSDL
                     */
                    Collection<String> outputBOMNames = null;
                    if (outputBOMFileNamesForWSDL.get(wsdlOrXsdFile) == null) {
                        try {
                            outputBOMFileNamesForWSDL.put(wsdlOrXsdFile,
                                    WSDLTransformUtil
                                            .getOutputBOMNames(wsdlOrXsdFile));
                        } catch (Exception e) {
                            LOG.error(String
                                    .format("WSDLTransformUtil.getOutputBOMNamesForWSDL(%1$s) error: %2$s", //$NON-NLS-1$
                                            wsdlOrXsdFile.getFullPath(),
                                            e.getLocalizedMessage()));
                        }
                    }

                    outputBOMNames =
                            outputBOMFileNamesForWSDL.get(wsdlOrXsdFile);

                    for (String bomFileName : outputBOMNames) {
                        IFile bomFile = bomFolder.getFile(bomFileName);
                        if (shouldCreateGenBOMFolder
                                || createBomsThatAreNotYetCreated) {
                            bomFiles.add(bomFile);
                        } else {
                            if (bomFile != null && bomFile.isAccessible()) {
                                bomFiles.add(bomFile);
                            }
                        }
                    }
                }
            }
            return bomFiles;
        }
        return Collections.emptySet();
    }

    /**
     * XPD-4461: Providing the old method to support decisions
     * 
     * @deprecated
     * 
     * 
     *             Having passed and a WSDL file, this utility returns the BOM
     *             files that have been or should be related to the given WSDL
     *             or XSD file.
     * 
     * @param wsdlOrXsdFile
     *            The WSDL or XSD File to return BOM files for.
     * @param returnBomsThatAreNotYetCreated
     *            <code>true</code> to return the BOMs that have OR should be
     *            related to the given WSDL/XSD. <code>false</code> will only
     *            return BOMs that HAVE been created
     * 
     * @return BOM Files related to the given WSDL/XSD (according to the
     *         returnBomsThatAreNotYetCreated parameter)
     */
    @Deprecated
    public static Set<IFile> getBOMFiles(IFile wsdlOrXsdFile,
            boolean returnBomsThatAreNotYetCreated) {
        return getBOMFiles(wsdlOrXsdFile, returnBomsThatAreNotYetCreated, false);
    }

    /**
     * @param wsdlOrXsdFile
     * @return <code>true</code> if the WSDL is a Studio generated WSDL
     */
    public static Boolean isWSDLGenerated(IFile wsdlOrXsdFile) {
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(wsdlOrXsdFile);
        if (workingCopy != null
                && workingCopy.getRootElement() instanceof Definition) {

            return WSDLTransformUtil.isGeneratedWsdl((Definition) workingCopy
                    .getRootElement());
        }
        return false;
    }

    /**
     * @param xpdlProject
     * @return the folder if the Generated BOM exists and should create is
     *         false; otherwise returns NULL;
     * 
     *         if shouldCreate is TRUE , then it creates the folder if it doesn'
     *         exist
     */
    public static IFolder getGeneratedBOMFolder(final IProject xpdlProject,
            boolean shouldCreate) {

        /*
         * XPD-6029: We now mark the generated BOM folder as not derived so that
         * its not ignored during export and commits.
         */

        /*
         * Check if a "Generated Business Objects" special folder has been
         * created and if so return the folder.
         */
        final IFolder folder = xpdlProject.getFolder(GENERATED_BOM_FOLDER);
        if (folder.exists()) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(xpdlProject);
            if (config != null) {
                SpecialFolder sf = config.getSpecialFolders().getFolder(folder);
                if (sf != null) {
                    return sf.getFolder();
                }
            }
        }

        /*
         * Haven't found the special folder so create it if asked to do so
         */
        if (shouldCreate && !folder.exists()) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(xpdlProject);
            if (config != null) {
                try {
                    /*
                     * Create the folder
                     */
                    ResourcesPlugin.getWorkspace()
                            .run(new IWorkspaceRunnable() {
                                public void run(IProgressMonitor monitor)
                                        throws CoreException {
                                    folder.create(true, true, monitor);
                                }
                            },
                                    null);

                    /*
                     * Mark it as a generated special folder
                     */
                    config.getSpecialFolders().addFolder(folder,
                            BOM_SPECIAL_FOLDER_KIND,
                            BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE);

                } catch (CoreException e) {
                    LOG.error(e);
                }
            }
        }

        return folder.exists() ? folder : null;
    }

    /**
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {

        super.clean(monitor);
        /*
         * Delete all the derived resources in the "Generated BOM" folder if the
         * preference is set up
         */
        IProject proj = getProject();
        IFolder generatedBomSpFolder = getGeneratedBOMFolder(proj, false);
        if (isPreferenceToDeleteBOMsOnCleanSet()) {

            if (generatedBomSpFolder != null && generatedBomSpFolder.exists()) {

                IResource[] members = generatedBomSpFolder.members();
                for (int mem = 0; mem < members.length; mem++) {

                    if (isResourceGenerated(members[mem])) {

                        members[mem].delete(true, monitor);
                    }
                }
            }
        } else {

            /*
             * Get the generated bom special folder to check if there is any
             * bom(s) hanging around but there is no corresponding wsdl/xsd(s)
             * that it is generated from. If so then delete the bom on clean
             * build.
             * 
             * We should ideally be deleting the boms only if the preference to
             * delete boms is set. But this is an exception case required for
             * precompiled projects. If a wsdl/xsd is deleted from precompiled
             * project, a validation is shown that a source artifact for
             * precompiled resource has been deleted. The quick fix disables
             * precompile and invokes clean build. If the preference is not set,
             * then the clean build fails to delete the boms leaving the system
             * in inconsistent state having the generated boms with no sources
             * that has generated them.
             * 
             * If the project is not pre-compiled then the incremental build
             * deletes the bom(s) for the deleted wsdl(s) or xsd(s).
             */
            if (generatedBomSpFolder != null && generatedBomSpFolder.exists()) {

                IResource[] members = generatedBomSpFolder.members();
                for (int i = 0; i < members.length; i++) {

                    IResource bomResource = members[i];
                    /* Look for the wsdl or xsd the bom is generated from */
                    Set<IResource> wsdlOrXSDFiles =
                            Bom2XsdUtil.findRelevantWSDLOrXSDFiles(bomResource);
                    if (wsdlOrXSDFiles.isEmpty()) {

                        if (isResourceGenerated(bomResource)) {

                            bomResource.delete(true, monitor);
                        }
                    }
                }
            }
        }

    }

    /**
     * Preference on Business Object Modeler ->Business Object Model Generator
     * -> Re-generate BOM on project clean
     * 
     * @return
     */
    private boolean isPreferenceToDeleteBOMsOnCleanSet() {
        IPreferenceStore store =
                com.tibco.xpd.bom.gen.ui.Activator.getDefault()
                        .getPreferenceStore();
        return store
                .getBoolean(BomGenPreferenceConstants.P_ENABLE_DELETE_ON_CLEAN_FOR_WSDLBOM_GENERATION);

    }

    /**
     * This utility is used to inform the invoker whether the BOM resource is
     * generated from an XSD Schema or is created by the user using the New
     * Wizards.
     * 
     * @param bomResource
     * @return {@link Boolean}.<code>true</code> if the bomResource is generated
     *         from an XSD Schema. This is checked against the fact that it
     *         contains XSD Notation.
     */
    private Boolean isResourceGenerated(IResource bomResource) {
        WorkingCopy bomWC = WorkingCopyUtil.getWorkingCopy(bomResource);
        if (bomWC != null && bomWC.getRootElement() instanceof Model) {
            Model model = (Model) bomWC.getRootElement();
            // As simple as if the BOM has an applied profile then assume this
            // as generated from an XSD Schema.
            Profile appliedProfile =
                    model.getAppliedProfile(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME);
            if (appliedProfile != null) {
                return Boolean.TRUE;
            }

        }
        return Boolean.FALSE;
    }

    private boolean isAnyBomEarlierVersion(
            final Set<IFile> bomsGeneratedFromWsdl) {
        String currentStudioBundleVersion =
                TransformHelper
                        .getBundleVersion(com.tibco.xpd.bom.xsdtransform.Activator
                                .getDefault().getBundle());
        for (IFile file : bomsGeneratedFromWsdl) {
            WorkingCopy bomWC = WorkingCopyUtil.getWorkingCopy(file);
            if (bomWC != null && bomWC.getRootElement() instanceof Model) {
                Model model = (Model) bomWC.getRootElement();
                Stereotype appliedStereotype =
                        model.getAppliedStereotype("XsdNotationProfile::XsdBasedModel"); //$NON-NLS-1$
                if (appliedStereotype != null) {
                    Object version =
                            model.getValue(appliedStereotype,
                                    XsdStereotypeUtils.XSD_STUDIO_VERSION);
                    if (version == null
                            || !version.toString()
                                    .equals(currentStudioBundleVersion)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.AbstractValidatingIncrementalProjectBuilder#shouldRun()
     * 
     * @return
     */
    @Override
    protected boolean shouldRun() {

        boolean shouldRun = super.shouldRun();
        /*
         * TODO: return true for pre compile project if we want to ensure the
         * generated artefacts be up to date
         */
        IProject project = getProject();
        boolean isPreCompiled = ProjectUtil.isPrecompiledProject(project);
        if (isPreCompiled) {

            shouldRun = false;
        }
        return shouldRun;
    }
}
