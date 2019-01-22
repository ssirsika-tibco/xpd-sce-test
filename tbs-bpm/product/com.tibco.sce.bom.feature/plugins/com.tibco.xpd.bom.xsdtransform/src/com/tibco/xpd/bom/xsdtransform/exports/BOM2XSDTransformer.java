/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.exports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDSchemaLocationResolver;
import org.openarchitectureware.xsd.OawXMLResource;
import org.openarchitectureware.xsd.XSDMetaModel;
import org.openarchitectureware.xsd.builder.OawXSDResourceSet;
import org.openarchitectureware.xtend.XtendFacade;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.resources.utils.DependencyAnalyzer;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.api.Bom2XsdSourceValidationType;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.export.progress.Bom2XsdMonitorMessages;
import com.tibco.xpd.bom.xsdtransform.exports.template.ExportTransformationData;
import com.tibco.xpd.bom.xsdtransform.internal.AbstractXtendExportTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.BaseBOMXtendTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.ondemand.BuildSourceSet;
import com.tibco.xpd.resources.builder.ondemand.BuildTargetSet;
import com.tibco.xpd.resources.ui.util.ResourceLocator;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.ValidationException;
import com.tibco.xpd.xsd.XSDWorkingCopy;

/**
 * Transforms XSD Model to BOM.
 * <p>
 * Note that the XSD schemas transformed from boms during this session are
 * cached. Therefore if an XSD is a dependent of multiple files input to the
 * transform method then the depenmdecy XSD will NOT be transformed twice.
 * However it WILL be serialised to the given target folder on each call to
 * transform.
 * <p>
 * <li>For instance if A.bom refs SUB.bom _and_ B.BOM refs SUB.bom</li>
 * <li>If you pass A.bom into transform() then A.xsd and SUB.xsd will be
 * generated and output to the target folder</li>
 * <li>If you then pass B.bom into transform() then B.xsd is generated, SUB.xsd
 * is grabbed from cache from previous transform() and both are output to the
 * target folder.
 * <p>
 * <i>Created: 24 July 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class BOM2XSDTransformer extends AbstractXtendExportTransformer {
    public static final String EXTENSION_FILE =
            "com::tibco::xpd::bom::xsdtransform::exports::template::Bom2Xsd"; //$NON-NLS-1$

    private static final String TRANSFORM_EXPRESSION = "transform"; //$NON-NLS-1$

    /**
     * SID XPD-1605: incrementalTransformBOM2XSD() code moved to
     * BOM2XMLTransformer.
     */

    /**
     * The OAW transformation facade.
     */
    private XtendFacade facade;

    /**
     * The export transformatioin data.
     */
    private ExportTransformationData exportTransformationData;

    /**
     * <code>true</code> If each call to transform() should perform a
     * validation. Some callers may already have performed a validation and
     * therefore wdo not need to duplicate the effort.
     */
    private Bom2XsdSourceValidationType sourceBomValidationRequirements;

    /**
     * if {@link #sourceBomValidationRequirements} is <code>true</code> then
     * this is a cache of previous validation results (some files may be
     * validated multiple times when they are dependencies of multiple top level
     * boms.
     */
    Map<IResource, Boolean> cachedBomProblemMarkerValidationResults =
            new HashMap<IResource, Boolean>();

    /**
     * If true then only validates resultant XML Schema(s) if ticked in
     * preferences else it always defaults to validating it each method call
     */
    private boolean usePrefValidation;

    /**
     * COnstruct the BOM2XSDTransformer.
     * <p>
     * Note that the XSD schemas transformed from boms during this session are
     * cached. Therefore if an XSD is a dependent of multiple files input to the
     * transform method then the depenmdecy XSD will NOT be transformed twice.
     * However it WILL be serialised to the given target folder on each call to
     * transform.
     * <p>
     * <li>For instance if A.bom refs SUB.bom _and_ B.BOM refs SUB.bom</li>
     * <li>If you pass A.bom into transform() then A.xsd and SUB.xsd will be
     * generated and output to the target folder</li>
     * <li>If you then pass B.bom into transform() then B.xsd is generated,
     * SUB.xsd is grabbed from cache from previous transform() and both are
     * output to the target folder.
     * 
     * @param useEcoreNames
     *            - determines whether the export will generate ecore names for
     *            each construct
     * 
     * @param usePrefValidation
     *            - If true then only validates resultant XML Schema(s) if
     *            ticked in preferences else it always defaults to validating it
     *            each method call
     * 
     * @param sourceBomValidationRequirements
     *            <code>true</code> to ensure that the source bom has passed
     *            previous validation run successfully.
     * 
     */
    public BOM2XSDTransformer(boolean usePrefValidation,
            Bom2XsdSourceValidationType sourceBomValidationRequirements) {

        this.sourceBomValidationRequirements = sourceBomValidationRequirements;
        this.usePrefValidation = usePrefValidation;

        facade = XtendFacade.create(EXTENSION_FILE);
        configureWorkflow(facade);

        exportTransformationData = new ExportTransformationData();
    }

    /**
     * <code>true</code> If want all the schemas returned by the transform
     * regardless of whether they were returned by a previous transform
     * performed using this Export data.
     * 
     * This is useful if you are performing multiple transforms with this data
     * BUT outputting to separate folders (and hence need all referenced schemas
     * output also).
     * 
     * @param wantAllReferencedSchemas
     *            the wantAllReferencedSchemas to set
     * 
     * @deprecated This method was only ever here so that when BDS called
     *             transform it could take advantage of using the same
     *             transformer instance for multiple transforms (hence re-using
     *             already parsed schemas) BUT needed them to ALWAYS to be
     *             serialized to taget folder - HOWEVER now that we serialise to
     *             bom2xsd if out of date then copy from there to BDS, then
     *             there is no need to ask Bom2Xsd.ext to always return all
     *             schemas because any already previously-parsed schema will
     *             already be in bom2xsd ready to copy to BDS. Can't see any
     *             need to keep this functionality anymore but will leave here
     *             for a while just in case.
     */
    @Deprecated
    public void setWantAllReferencedSchemasEveryTime(
            boolean wantAllReferencedSchemas) {
        exportTransformationData
                .setWantAllReferencedSchemasEveryTime(wantAllReferencedSchemas);
    }

    /**
     * Perform a transformation of the given TOP-LEVEL BOM files (BOM files in a
     * set of BOM files that are not referenced by other BOM files).
     * <p>
     * The OAW transformation to XSD is performed on each and will traverse and
     * output the underlying referenced BOM models.
     * <p>
     * IT IS ASSUMED that the caller has performed all relevant validation of
     * the BOM files (and the files referenced from them). This can be performed
     * with {@link #isDestinationAndNoErrors(IResource, String, String, List)}
     * <p>
     * <b>The transformation is always performed into the .bom2xsd folder
     * appropriate for each BOM file in each set.</b> The generated XSD is then
     * optionally copied on to a final target folder.
     * <p>
     * It is assumed that caller has done any 'out-of-dateness' checking so the
     * transform is always performed (but already parsed schemas from previous
     * calls to same class instance will be re-used).
     * 
     * @param source
     * @param monitor
     * 
     * @return {@link IStatus}
     */
    public List<IStatus> transformToBom2Xsd(final IFile source,
            IProgressMonitor monitor) {
        return this.transform(source, null, true, monitor);
    }

    /**
     * Perform a transformation of the given TOP-LEVEL BOM files (BOM files in a
     * set of BOM files that are not referenced by other BOM files).
     * <p>
     * The OAW transformation to XSD is performed on each and will traverse and
     * output the underlying referenced BOM models.
     * <p>
     * IT IS ASSUMED that the caller has performed all relevant validation of
     * the BOM files (and the files referenced from them). This can be performed
     * with {@link #isDestinationAndNoErrors(IResource, String, String, List)}
     * <p>
     * <b>The transformation is always performed into the .bom2xsd folder
     * appropriate for each BOM file in each set.</b> The generated XSD is then
     * optionally copied on to a final target folder.
     * <p>
     * If a finalTargetFolder is provided <b>and the XSDs expected to be
     * generated for the given BOM and all the BOMs it is dependent upon are up
     * to date</b> then existing XSDs are copied from .bom2xsd to
     * finalTargetFolder.
     * <p>
     * If finalTargetFolder is NOT provided then the transformation to .bom2xsd
     * will always be performed regardless existing xsds in .bom2xsd folders. If
     * the same trnasformer class instance is used to perform multiple
     * transforms then previously transformed schemas (in memory) can and will
     * be re-used for further transforms.
     * 
     * @param sources
     * @param finalTargetFolder
     *            Final target folder to copy generated XSD files (which are
     *            ALWAYS generated into appropriate .bom2xsd folder) to <b>or
     *            <code>null</code> if no onward copy to other folder
     *            required.</b>
     * @param isLocalWorkspaceTarget
     *            whether finalTargetFolder is in workspace (else in file system
     *            outside of workspace).
     * @param monitor
     * 
     * @return {@link IStatus}
     */
    public List<IStatus> transform(final IFile source,
            final IPath finalTargetFolder, boolean isLocalWorkspaceTarget,
            IProgressMonitor monitor) {

        List<IStatus> result = new ArrayList<IStatus>();

        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }

        long start = System.currentTimeMillis();
        long start2 = start;
        long next;

        try {
            monitor.beginTask("", 3); //$NON-NLS-1$

            boolean isValid = validate(source, monitor, result);

            if (result.size() > 0) {
                for (IStatus res : result) {
                    /*
                     * XPD-3984: if there are any operations defined in a bom,
                     * validation comes with a warning message saying
                     * 'operations are ignored' and export wizard was not doing
                     * anything. added a check if the severity of the validation
                     * is error then don't do anything. if it is not an error
                     * then export wizard must behave in the same way as the
                     * builder does.
                     */
                    if (res.getSeverity() == IStatus.ERROR) {
                        return result;
                    }
                }
            }

            monitor.worked(1);

            if (isValid) {

                /**
                 * We now operate by ALWAYS doing a full (i.e. all extension
                 * meta data) transform to bom2xsd - then if the target folder
                 * is NOT .bom2xsd then we copy the derived xsd's to the target
                 * folder.
                 * 
                 * If necessary, the extension meta data is removed from xsd
                 * during the copy phase at end.
                 * 
                 * This way we should always have the latest up-to-date xsd's in
                 * bom2xsd to re-use for BDS build, manual export, next bom2xsd
                 * build and so on.
                 * 
                 * STEP 1: Check if we need to perform a transform to bom2xsd.
                 */

                /*
                 * Get the complete source set for this BOM (i.e.this BOM and
                 * all BOMs it depends upon (the keySet()) And the .bom2xsd
                 * files that 'should' be generated for these (the valueSet()).
                 */
                BuildSourceSet sourceBomSet = getBOMSourceSet(source);

                BuildTargetSet targetXsdSet = getXsdTargetSet(sourceBomSet);

                /*
                 * XPD-4533: Get the complete target xsd set for this BOM (i.e
                 * all the xsd(s) that are directly/indirectly referenced in
                 * this BOM. Also includes those xsd(s) that are not referenced
                 * but are part of the imports)
                 */
                BuildTargetSet completeXsdSet = getCompleteXSDSet(source);

                for (IResource targetRes : completeXsdSet.getTargetResources()) {
                    if (!targetXsdSet.getTargetResources().contains(targetRes)) {
                        targetXsdSet.addTargetResource(targetRes);
                    }
                }

                boolean needTransform = false;

                if (finalTargetFolder == null) {
                    /*
                     * If we are asked to explicitly transform into .bom2xsd
                     * then we will ALWAYS do the transform for the given model
                     * (because bom2xsd builder is telling us that the file has
                     * to be rebuilt).
                     */
                    needTransform = true;

                } else {
                    /*
                     * If the target folder is not bom2xsd, then we can afford
                     * to simply copy the last set of files we transformed and
                     * serialized to bom2xsd IF THEY ALL EXIST AND ARE UP TO
                     * DATE.
                     * 
                     * Check that the earliest output xsd timestamp is later
                     * than the latest BOM timestamp.
                     */
                    long earliestXsdTime = targetXsdSet.getEarliestTimeStamp();
                    long latestBomTime = sourceBomSet.getLatestTimeStamp();

                    if (!targetXsdSet.allTargetsExist()
                            || latestBomTime > earliestXsdTime) {

                        /*
                         * XPD-7608: Fix one of the failing junit daa test
                         * comparison projects for pre-compile. The cause of the
                         * issue: generated xsd being available under .bom2xsd
                         * and .precompiled/bom2xsd folder. This was happening
                         * because the timestamp on bom is later than the
                         * timestamp on the xsd in precompiled folder. The
                         * timestamp on the bom is import timestamp (because
                         * project is copied into workspace) and the timestamp
                         * on xsd is the timestamp from the file system (i.e.
                         * from .precompiled\bom2xsd folder).
                         * 
                         * Do not re-generate the xsd if the project is
                         * pre-compiled.
                         */
                        IProject project = source.getProject();
                        boolean isPrecompiled =
                                ProjectUtil.isPrecompiledProject(project);
                        if (!isPrecompiled) {
                            /*
                             * At least one XSD hasn't been re-generated since
                             * the last BOM in set to have been modified.
                             */
                            needTransform = true;
                        }
                    }
                }

                /**
                 * STEP 2: Do the transformation and serialize to bom2xsd if
                 * necessary.
                 */
                if (needTransform) {
                    Object generatedSchemaObjects =
                            callOAWTransform(source, monitor, result, start);

                    if (generatedSchemaObjects instanceof List<?>) {
                        /* Create resource set to assist in serializing files. */
                        OawXSDResourceSet rSet = new OawXSDResourceSet();

                        serializeTransformedSchemasToBom2Xsd(source,
                                result,
                                rSet,
                                (List<?>) generatedSchemaObjects,
                                monitor);

                        /*
                         * Unload all resources
                         */
                        rSet.clear();
                    }
                }

                monitor.worked(1);

                /**
                 * STEP 3: If a final onward target folder is provided then copy
                 * the xsd set (the xsd for the given source AND all its
                 * dependent xsds) from bom2xsd to target folder.
                 */
                if (finalTargetFolder != null
                        && !Bom2XsdUtil.isBOMToXSDFolderPath(finalTargetFolder)) {

                    for (IResource bom2xsdFile : targetXsdSet
                            .getTargetResources()) {
                        copyFromBom2XsdToTargetFolder((IFile) bom2xsdFile,
                                finalTargetFolder,
                                isLocalWorkspaceTarget);
                    }
                }

                monitor.worked(1);
            }

            next = System.currentTimeMillis();
            sysout("  BOM2XSDTransformer.transform() End Total Time: " //$NON-NLS-1$
                    + ((next - start2) / 1000.0f));

        } finally {
            monitor.done();
        }

        return result;
    }

    /**
     * Create the set of target xsds expected to be genrated in the bom2xsd
     * output folder for each of the given BOM files in source bom set
     * 
     * @param sourceBomSet
     * @return target xsd set.
     */
    private BuildTargetSet getXsdTargetSet(BuildSourceSet sourceBomSet) {

        BuildTargetSet xsdTargetSet = new BuildTargetSet("Bom2Xsd Target XSDs"); //$NON-NLS-1$
        IFolder xsdOutputFolder = null;

        for (IResource bomFile : sourceBomSet.getSourceResources()) {

            /*
             * If bom project is pre compiled, then get the xsd output folder
             * from project/.precompiled/bom2xsd folder instead of
             * project/.bom2xsd folder
             */

            IProject project = bomFile.getProject();
            xsdOutputFolder = Bom2XsdUtil.getXsdOutputFolder(project, true);

            boolean isPrecompiled = ProjectUtil.isPrecompiledProject(project);
            if (isPrecompiled) {

                try {

                    xsdOutputFolder =
                            (IFolder) ResourceLocator
                                    .getTargetResource(project, xsdOutputFolder);
                } catch (CoreException e) {

                    e.printStackTrace();
                }

            }
            if (null != xsdOutputFolder) {

                Collection<String> outputXsdFileNames =
                        XSDUtil.getOutputXsdFileNames(bomFile);

                for (String xsdFileName : outputXsdFileNames) {

                    IFile xsdFile = xsdOutputFolder.getFile(xsdFileName);

                    xsdTargetSet.addTargetResource(xsdFile);
                }
            }

        }
        return xsdTargetSet;
    }

    /**
     * @param sourceBomFile
     * @return source file set for given bom and it's dependents
     */
    private BuildSourceSet getBOMSourceSet(IFile sourceBomFile) {
        DependencyAnalyzer dependencyAnalyzer =
                new DependencyAnalyzer(Collections.singletonList(sourceBomFile));

        List<IFile> allDependencies =
                dependencyAnalyzer.getAllDependencies(sourceBomFile);

        BuildSourceSet bomSourceSet = new BuildSourceSet("Bom2Xsd Source BOMs"); //$NON-NLS-1$

        bomSourceSet.addSourceResource(sourceBomFile);

        for (IFile bomFIle : allDependencies) {
            bomSourceSet.addSourceResource(bomFIle);
        }

        return bomSourceSet;
    }

    /**
     * Create the set of target xsd(s) expected to be generated in the bom2xsd
     * output folder for a given BOM file
     * 
     * @param sourceBomFile
     * @return target xsd set
     */
    private BuildTargetSet getCompleteXSDSet(IFile sourceBomFile) {

        IFile xsdFile = getCorrespondingXSDFileForBOMFile(sourceBomFile);

        BuildTargetSet xsdTargetSet = new BuildTargetSet("Bom2Xsd Target XSDs"); //$NON-NLS-1$

        if (null != xsdFile) {
            xsdTargetSet.addTargetResource(xsdFile);
            getXsdDependencies(xsdFile, xsdTargetSet);
        }

        return xsdTargetSet;

    }

    /**
     * recursively gets all the dependent/related xsd(s) and adds it to the
     * target xsd set
     * 
     * @param xsdFile
     * @param xsdTargetSet
     */
    private void getXsdDependencies(IFile xsdFile, BuildTargetSet xsdTargetSet) {

        WorkingCopy xsdWC = WorkingCopyUtil.getWorkingCopy(xsdFile);

        if (xsdWC instanceof XSDWorkingCopy) {
            XSDWorkingCopy xsdWorkingCopy = (XSDWorkingCopy) xsdWC;
            List<IResource> dependency = xsdWorkingCopy.getDependency();

            for (IResource iResource : dependency) {
                if (!xsdTargetSet.getTargetResources().contains(iResource)) {
                    xsdTargetSet.addTargetResource(iResource);
                    getXsdDependencies((IFile) iResource, xsdTargetSet);
                }
            }
        }
    }

    /**
     * @param sourceBomFile
     * @return corresponding xsd file for the given bom file
     */
    private IFile getCorrespondingXSDFileForBOMFile(IFile sourceBomFile) {

        IFile xsdFile = null;
        IProject project = sourceBomFile.getProject();
        IFolder xsdOutputFolder = Bom2XsdUtil.getXsdOutputFolder(project, true);

        /*
         * If bom project is pre compiled, then get the xsd output folder from
         * project/.precompiled/bom2xsd folder instead of project/.bom2xsd
         * folder
         */
        boolean isPreCompiled = ProjectUtil.isPrecompiledProject(project);

        if (isPreCompiled) {

            try {

                xsdOutputFolder =
                        (IFolder) ResourceLocator.getTargetResource(project,
                                xsdOutputFolder);
            } catch (CoreException e) {

                e.printStackTrace();
            }

        }
        if (null != xsdOutputFolder) {

            WorkingCopy bomWC = WorkingCopyUtil.getWorkingCopy(sourceBomFile);

            if (null != bomWC && bomWC.getRootElement() instanceof Model) {

                Package umlPkg = (Package) bomWC.getRootElement();
                String xsdFileName =
                        BOMWorkingCopy.getQualifiedPackageName(umlPkg) + ".xsd"; //$NON-NLS-1$
                xsdFile = xsdOutputFolder.getFile(xsdFileName);
            }
        }
        return xsdFile;
    }

    /**
     * Copy the given XSD file (in bom2xsd folder) from there to the target
     * folder.
     * 
     * 
     * @param bom2xsdFile
     * @param xsdTargetFolderPath
     */
    private void copyFromBom2XsdToTargetFolder(IFile bom2xsdFile,
            IPath xsdTargetFolderPath, boolean isLocalWorkspaceTarget) {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bom2xsdFile);
        if (wc == null || !(wc.getRootElement() instanceof XSDSchema)) {
            throw new RuntimeException(
                    "Could not access working copy for derived XSD file: " + bom2xsdFile); //$NON-NLS-1$
        }

        try {

            if (isLocalWorkspaceTarget) {
                /* Save to folder in workspace. */
                ProjectUtil.createFolder(ResourcesPlugin.getWorkspace()
                        .getRoot().getFolder(xsdTargetFolderPath), false, null);

                IPath copyToPath =
                        xsdTargetFolderPath.append(bom2xsdFile.getName());

                IFile targetFile =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(copyToPath);
                if (targetFile.exists()) {
                    targetFile.delete(true, null);
                }
                bom2xsdFile.copy(copyToPath, true, null);

            } else {
                /* Save to folder outside of workspace. */
                File fileSystemTargetFolder = xsdTargetFolderPath.toFile();

                if (!fileSystemTargetFolder.exists()) {
                    fileSystemTargetFolder.mkdirs();
                }

                IPath copyToPath =
                        xsdTargetFolderPath.append(bom2xsdFile.getName());

                File targetFile = copyToPath.toFile();
                copyWorkspaceFileToFileSystemFile(bom2xsdFile, targetFile);
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not copy : " //$NON-NLS-1$
                    + bom2xsdFile.getName()
                    + " to the path " + xsdTargetFolderPath.toString() + //$NON-NLS-1$
                    "(Cause: " + e.getMessage() + ")", e); //$NON-NLS-1$ //$NON-NLS-2$
        } finally {
        }
    }

    /**
     * Copy file from inside workspace to file system outside of workspace
     * 
     * @param bom2xsdFile
     * @param targetFile
     * @throws CoreException
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void copyWorkspaceFileToFileSystemFile(IFile bom2xsdFile,
            File targetFile) throws CoreException, IOException,
            FileNotFoundException {
        if (targetFile.exists()) {
            targetFile.delete();
        }

        InputStream inputStream = null;
        OutputStream outStream = null;
        try {
            inputStream = bom2xsdFile.getContents();
            targetFile.createNewFile();

            outStream = new FileOutputStream(targetFile);
            byte[] buf = new byte[8192];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

            if (outStream != null) {
                outStream.close();
            }

        }
    }

    /**
     * @param source
     * @param monitor
     * @param result
     * @param start
     * @return
     */
    private Object callOAWTransform(final IFile source,
            IProgressMonitor monitor, List<IStatus> result, long start) {

        Object objects = null;

        long next;
        /*
         * Setup everything ready for the transformation.
         */
        exportTransformationData.getMonitor().setParentMonitor(monitor);

        exportTransformationData.clearBetweenTransforms();

        exportTransformationData.setTopLevelSourceFile(source);

        exportTransformationData.setProject(source.getProject());

        String uri = source.getFullPath().toPortableString();

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(source);
        EObject model = wc.getRootElement();

        /*
         * Perform the transformation only if the model is not null. (XPD-4084:
         * This is to avoid NPE that gets thrown when a BOM is copied. When a
         * BOM is copied it gets the name with spaces and validation for invalid
         * bom name does not allow to load the resource. Because resource is not
         * loaded, root element would be null)
         */
        if (null != model) {
            try {
                /*
                 * First instantiation can take a long time so let the user know
                 * (2nd tiem it should flash past and the user wont' see it as
                 * it will be immedaitely overwritten by first monitor message
                 * in Bom2Xsd.ext
                 */
                monitor.subTask(Bom2XsdMonitorMessages
                        .getBom2XsdProgressText(source.getName()
                                + " :: " + //$NON-NLS-1$
                                com.tibco.xpd.bom.xsdtransform.export.progress.Messages.BOM2XSDTransformer_BuildXsdsFromBomsLeader_message));

                objects =
                        facade.call(TRANSFORM_EXPRESSION,
                                exportTransformationData,
                                model,
                                uri);

                next = System.currentTimeMillis();
                sysout("  BOM2XSDTransformer.transform() facade.call(" + uri //$NON-NLS-1$
                        + "): " + ((next - start) / 1000.0f)); //$NON-NLS-1$
                start = next;

            } catch (Exception e) {
                // Return error
                sysout("Bom2Xsd OAW Exception transforming: " + source.getFullPath().toString()); //$NON-NLS-1$
                sysout(e.getMessage());

                Activator.getDefault().getLogger()
                        .error(e, "Bom2Xsd OAW Exception transforming: " //$NON-NLS-1$
                                + source.getFullPath().toString());

                result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e
                        .getLocalizedMessage(), e));
            }
        }
        return objects;
    }

    private boolean validate(IFile source, IProgressMonitor monitor,
            List<IStatus> result) {

        boolean isValid = true;

        if (Bom2XsdSourceValidationType.USE_MARKERS_FROM_PREVIOUS_BUILD
                .equals(sourceBomValidationRequirements)) {
            /*
             * Check problem markers for validation errors.
             */
            monitor.subTask(Bom2XsdMonitorMessages
                    .getBom2XsdProgressText(source.getName()
                            + " :: " + //$NON-NLS-1$
                            com.tibco.xpd.bom.xsdtransform.export.progress.Messages.BOM2XSDTransformer_CheckingProblemMarkers_message));

            isValid =
                    hasPassedBom2XsdValidation(source,
                            false,
                            cachedBomProblemMarkerValidationResults);
        } else if (Bom2XsdSourceValidationType.LIVE_VALIDATION
                .equals(sourceBomValidationRequirements)) {
            /*
             * Perform live validation.
             */
            monitor.subTask(Bom2XsdMonitorMessages
                    .getBom2XsdProgressText(source.getName()
                            + " :: " + //$NON-NLS-1$
                            com.tibco.xpd.bom.xsdtransform.export.progress.Messages.BOM2XSDTransformer_PerformingValidation_message));
            try {
                isValid =
                        isDestinationAndNoErrors(source,
                                BomGenPreferenceConstants.XSD_MARKER_TYPE,
                                BaseBOMXtendTransformer.XSD_DESTINATION,
                                result);
            } catch (ValidationException e) {
                // add error
                result.add(new Status(
                        IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        String.format(Messages.BOM2XSDTransformer_validationFailed_error_message,
                                source.getFullPath().toString()), e));
            }
        }
        return isValid;
    }

    /**
     * Serialise each of the schema objects returned by the transform to the
     * bom2xsd folder in the project of the BOM that the schema was derived
     * from.
     * <p>
     * Transform must have been previously done (to setup
     * exportTransformationData
     * 
     * 
     * @param source
     * @param xsdFilePath
     * @param result
     * @param rSet
     * @param generatedSchemaObjects
     * @param monitor
     */
    private void serializeTransformedSchemasToBom2Xsd(IFile source,
            List<IStatus> result, OawXSDResourceSet rSet,
            List<?> generatedSchemaObjects, IProgressMonitor monitor) {

        monitor.subTask(Bom2XsdMonitorMessages.getBom2XsdProgressText(source
                .getName()
                + " :: " + //$NON-NLS-1$
                com.tibco.xpd.bom.xsdtransform.export.progress.Messages.BOM2XSDTransformer_SavingSchemas_message));

        /*
         * XPD-5253 When BOM references are made across projects then each
         * projkect contains the generated schema for it's BOMs but NOT
         * referenced ones. Therefore when resolving imports/includes we need to
         * treat other project's .bom2xsd as 'local' to the referencing xsd.
         * 
         * To do this we setup an adapter factory XSD import/include schema
         * locator. This is used to resolve relative locations potentially
         * across .bom2xsd folders in multiple projects.
         */
        Bom2XsdXsdSchemaLocatorAdapterFactory schemaLocatoryAdapterFactory =
                new Bom2XsdXsdSchemaLocatorAdapterFactory(
                        new Bom2XsdSchemaLocationResolver(source.getProject()));

        /*
         * Insert at heald of adapter factory list so our schema locator is used
         * in preference to default one.
         */
        rSet.getAdapterFactories().add(0, schemaLocatoryAdapterFactory);

        try {

            List<URI> urisToLoad = new ArrayList<URI>();
            for (Object schemaObject : (List<?>) generatedSchemaObjects) {

                if (schemaObject instanceof EObject) {
                    serializeSchemaToBom2Xsd(schemaObject, result, urisToLoad);
                }
            }

            /*
             * Load all the XSDs to validate now that all the resources have
             * been saved
             */
            for (URI uriToLoad : urisToLoad) {
                rSet.getResource(uriToLoad, true);
            }
            /*
             * Validate the schemas
             */
            for (XSDSchema schema : rSet.getSchemas()) {
                result.addAll(validateSchema(schema, usePrefValidation));
                /*
                 * XPD-4679: After adopting new eclipse 3.7, it seems that
                 * notifications have changed and schemas generated from a bom
                 * (.bom2xsd) are not getting indexed (probably might be a
                 * timing issue). This results in error messages being logged
                 * saying "failed to resolve external reference" during
                 * xpdl2wsdl transformation.
                 * 
                 * Explicitly making a call to the indexer by getting the
                 * working copy for each schema
                 * 
                 * Please note that this problem does not occur in
                 * development/debug mode
                 */
                IFile xsdFile = WorkingCopyUtil.getFile(schema);
                WorkingCopyUtil.getWorkingCopy(xsdFile);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            rSet.getAdapterFactories().remove(schemaLocatoryAdapterFactory);
        }
    }

    /**
     * Serialise the given schema objects returned by the transform to the
     * bom2xsd folder in the project of the BOM that the schema was derived
     * from.
     * 
     * @param schemaObject
     * @param result
     * @param urisToLoad
     */
    private void serializeSchemaToBom2Xsd(Object schemaObject,
            List<IStatus> result, List<URI> urisToLoad) {

        EObject eo = (EObject) schemaObject;

        if (!eo.eContents().isEmpty()) {
            /*
             * Figure out the source BOM and it's project from teh return
             * schema.
             */
            IProject projectOfSourceBOM = exportTransformationData.getProject();

            Package packageForDocRoot =
                    exportTransformationData.getPackageForDocRoot(eo);

            if (packageForDocRoot != null) {
                IProject tmpProject =
                        WorkingCopyUtil.getProjectFor(packageForDocRoot);
                if (tmpProject != null) {
                    projectOfSourceBOM = tmpProject;
                }
            }

            String namespace = getTargetNamespace(eo.eContents().get(0));
            if (namespace != null) {
                try {
                    String filename =
                            exportTransformationData.getFileName(namespace);

                    /*
                     * Figure out the special folder for bom2xsd here.
                     */

                    IFolder bom2XsdFOlder =
                            Bom2XsdUtil.getXsdOutputFolder(projectOfSourceBOM,
                                    true);

                    if (bom2XsdFOlder != null && bom2XsdFOlder.exists()) {
                        /*
                         * save the file to bom2xsd.
                         */
                        URI bom2xsdFileURI =
                                URI.createPlatformResourceURI(bom2XsdFOlder
                                        .getFullPath().append(filename)
                                        .toString(), true);

                        Resource res =
                                new OawXMLResource(bom2xsdFileURI,
                                        new XSDMetaModel());

                        if (res.getContents() != null) {
                            res.getContents().add(eo);
                            res.save(Collections.EMPTY_MAP);
                            urisToLoad.add(bom2xsdFileURI);
                        }
                    }

                } catch (Exception e) {
                    result.add(new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            String.format(Messages.BOM2XSDTransformer_problemGeneratingXSDFor_error_message,
                                    namespace), e));
                }
            }
        }
    }

    /**
     * Get the target namespace of the given XSD element.
     * 
     * @param eo
     * @return
     */
    private String getTargetNamespace(EObject eo) {
        EClass eClass = eo.eClass();
        if (eClass != null) {
            EList<EAttribute> attributes = eClass.getEAttributes();
            for (EAttribute attr : attributes) {
                if ("targetNamespace".equals(attr.getName())) { //$NON-NLS-1$
                    Object get = eo.eGet(attr);
                    if (get instanceof String) {
                        return (String) get;
                    }
                }
            }
        }
        return null;
    }

    /**
     * SID XPD-1605: incrementalTransformBOM2XSD() code moved to
     * BOM2XMLTransformer.
     */

    /**
     * Output to system console (can be turned off/on)
     * 
     * @param msg
     */
    private void sysout(String msg) {
        // System.out.println(msg);
        return;
    }

    /**
     * XPD-5253 XSD Schema location resolver for xsd's spread across multiple
     * .bom2xsd folders (as is the case when bom references bom in another
     * project). In that case each project bom2xsd only contains xsd's from its
     * own boms and therefore the referenced xsd from other projects will not be
     * in local bom2xsd so we need to resolve them to bom2xsd folders from
     * referenced projects.
     * 
     * @author aallway
     * @since 9 Aug 2013
     */
    private static class Bom2XsdSchemaLocationResolver extends AdapterImpl
            implements XSDSchemaLocationResolver {

        LinkedHashSet<IFolder> bom2XsdFolders = new LinkedHashSet<IFolder>();

        private IFolder defaultBom2XsdFolder;

        public Bom2XsdSchemaLocationResolver(IProject sourceProject) {

            try {
                /*
                 * Cache a list of all bom2xsd folders for this project and all
                 * of it's referenced projects.
                 */
                defaultBom2XsdFolder =
                        Bom2XsdUtil.getXsdOutputFolder(sourceProject, false);

                if (defaultBom2XsdFolder != null) {
                    bom2XsdFolders.add(defaultBom2XsdFolder);
                }

                Set<IProject> refdProjects =
                        ProjectUtil2
                                .getReferencedProjectsHierarchy(sourceProject,
                                        true);

                for (IProject refdProject : refdProjects) {
                    IFolder bom2XsdFolder =
                            Bom2XsdUtil.getXsdOutputFolder(refdProject, false);

                    if (bom2XsdFolder != null) {
                        bom2XsdFolders.add(bom2XsdFolder);
                    }
                }

            } catch (CyclicDependencyException e) {
                Activator
                        .getDefault()
                        .getLogger()
                        .warn(e,
                                "Will not correctly resolve schemaLocations for generated .bom2xsd schemas due to cyclic project dependency."); //$NON-NLS-1$
            }
        }

        /**
         * @see org.eclipse.xsd.util.XSDSchemaLocationResolver#resolveSchemaLocation(org.eclipse.xsd.XSDSchema,
         *      java.lang.String, java.lang.String)
         * 
         * @param xsdSchema
         * @param namespaceURI
         * @param schemaLocationURI
         * @return
         */
        @Override
        public String resolveSchemaLocation(XSDSchema xsdSchema,
                String namespaceURI, String schemaLocationURI) {
            String resolvedSchemaLocation = null;

            /*
             * in Bom2Xsd the schemaLocations are allrelative and flattened into
             * non-hierarchical list, so we can just attempt to get the file in
             * each folder in turn til we find one.
             */
            for (IFolder bom2Xsd : bom2XsdFolders) {
                IFile schemaFile = bom2Xsd.getFile(schemaLocationURI);

                if (schemaFile.exists()) {
                    resolvedSchemaLocation =
                            URI.createPlatformResourceURI(schemaFile
                                    .getFullPath().toString(),
                                    true).toString();

                    break;
                }
            }

            if (resolvedSchemaLocation == null && defaultBom2XsdFolder != null) {
                IFile schemaFile =
                        defaultBom2XsdFolder.getFile(schemaLocationURI);

                resolvedSchemaLocation =
                        URI.createPlatformResourceURI(schemaFile.getFullPath()
                                .toString(),
                                true).toString();
            }

            return resolvedSchemaLocation;
        }

        /**
         * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
         * 
         * @param type
         * @return
         */
        @Override
        public boolean isAdapterForType(Object type) {
            return type == XSDSchemaLocationResolver.class;
        }
    }

    /**
     * XPD-5253 Adapter factory to add to resource set for xsd import schema
     * location (when custom one is set via
     * {@link BOM2XSDTransformer#setXSDImportSchemaLocatorForValidation(XSDSchemaLocationResolver)}
     * 
     * @author aallway
     * @since 8 Aug 2013
     */
    private static class Bom2XsdXsdSchemaLocatorAdapterFactory extends
            AdapterFactoryImpl {

        private XSDSchemaLocationResolver xsdSchemaLocationResolver;

        /**
         * @param xsdSchemaLocationResolver
         */
        public Bom2XsdXsdSchemaLocatorAdapterFactory(
                XSDSchemaLocationResolver xsdSchemaLocationResolver) {
            super();
            if (!(xsdSchemaLocationResolver instanceof Adapter)) {
                throw new RuntimeException(
                        "xsdSchemaLocationResolver must also implement Adapter interface"); //$NON-NLS-1$
            }

            this.xsdSchemaLocationResolver = xsdSchemaLocationResolver;
        }

        @Override
        public boolean isFactoryForType(Object type) {
            return ((Adapter) xsdSchemaLocationResolver).isAdapterForType(type);
        }

        @Override
        public Adapter adaptNew(Notifier target, Object type) {
            return (Adapter) xsdSchemaLocationResolver;
        }

    }

}
