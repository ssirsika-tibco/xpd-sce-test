/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.xsdindexing.XsdUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * Public utilities for bom2xsd conversion stuff.
 * 
 * @author aallway
 * @since 11 May 2011
 */
public class Bom2XsdUtil {
    /** Default project relative Bom 2 Xsd folder. */
    public static final String DEFAULT_BOM_2_XSD_FOLDER = ".bom2Xsd"; //$NON-NLS-1$

    /** Precompile project relative Bom 2 Xsd folder. */
    public static final String PRECOMPILE_BOM_2_XSD_FOLDER = "bom2Xsd"; //$NON-NLS-1$

    /**
     * Kind of special folder used for delivering Xsds for Boms
     */
    public static final String BOM_2_XSD_SPECIAL_FOLDER_KIND = "bom2xsd"; //$NON-NLS-1$

    /**
     * Regular expression string to detect line feed without carriage return.In
     * other words, it will not match '\r\n' but will match '\n',' \n','abc\n'
     * etc.
     */
    private static Pattern LF_WITHOUT_CR = Pattern.compile("(?<!\\r)\\n"); //$NON-NLS-1$

    /**
     * Uses the indexer to find the XSD files that are generated for a given BOM
     * File AND that exist.
     * 
     * @param bomFile
     * @return a set of XSD {@link IFile}s for a given BOM file.
     */
    public static Set<IFile> getCurrentXsdsGeneratedFromBom(IProject project,
            IFile bomFile) {
        String bomFileName = bomFile.getFullPath().toPortableString();
        Set<String> queryGeneratedXsdsFromBom =
                XsdUIUtil.queryGeneratedXsdsFromBom(project, bomFileName);

        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

        Set<IFile> xsdFiles = new HashSet<IFile>();
        for (String string : queryGeneratedXsdsFromBom) {
            IFile file = workspaceRoot.getFile(new Path(string));
            if (file.isAccessible()) {
                xsdFiles.add(file);
            }
        }
        return xsdFiles;
    }

    /**
     * Checks if the path is actually one of bom2xsd special folder type
     * 
     * @param path
     * @return
     */
    public static boolean isBOMToXSDFolderPath(IPath path) {
        boolean isBom2XsdFolder = false;
        for (String segment : path.segments()) {
            if (segment.equals(Bom2XsdUtil.DEFAULT_BOM_2_XSD_FOLDER)) {
                isBom2XsdFolder = true;
            }
        }
        return isBom2XsdFolder;
    }

    /**
     * Gets eclipse IFolder which is marked as the BOM to XSD special folder for
     * the project. If the special folder is not set (and project has required
     * nature) the default one will be created and returned.
     * 
     * @param create
     *            true if the folder should be created if don't exist.
     * @return eclipse IFolder which is marked as the BOM to XSD special folder.
     *         If special folder doesn't exist (and project has required nature)
     *         and the create parameter is true, the default folder will be
     *         created otherwise null will be returned.
     */
    public static IFolder getXsdOutputFolder(IProject project, boolean create) {
        return getXsdOutputFolder(project, create, false);
    }

    /**
     * Gets eclipse IFolder which is marked as the BOM to XSD special folder for
     * the project. If the special folder is not set (and project has required
     * nature) the default one will be created and returned.
     * 
     * @param create
     *            true if the folder should be created if don't exist.
     * @param returnIfNotExists
     *            Return IFolder even if it does not exist yet.
     * 
     * @return eclipse IFolder which is marked as the BOM to XSD special folder.
     *         If special folder doesn't exist (and project has required nature)
     *         and the create parameter is true, the default folder will be
     *         created otherwise null will be returned.
     */
    public static IFolder getXsdOutputFolder(IProject project, boolean create,
            boolean returnIfNotExists) {
        SpecialFolder sf =
                SpecialFolderUtil.getSpecialFolderOfKind(project,
                        BOM_2_XSD_SPECIAL_FOLDER_KIND);
        if (sf == null || sf.getFolder() == null || !sf.getFolder().exists()) {
            if (create) {
                sf =
                        SpecialFolderUtil.getCreateSpecialFolderOfKind(project,
                                BOM_2_XSD_SPECIAL_FOLDER_KIND,
                                Bom2XsdUtil.DEFAULT_BOM_2_XSD_FOLDER);
            }
        }

        IFolder folder = (sf == null ? null : sf.getFolder());

        if (folder == null && returnIfNotExists) {
            folder = project.getFolder(Bom2XsdUtil.DEFAULT_BOM_2_XSD_FOLDER);
        }

        return folder;
    }

    /**
     * The XSDs in the special folder may be referred to by the WSDLs in the
     * same special folder.
     * 
     * @param xsdFile
     * @return WSDLs that are referring to the XSD file passed in as parameter
     */
    public static List<IFile> getWsdlsReferringToXsd(final IFile xsdFile)
            throws CoreException {

        return new ArrayList<IFile>(getWsdlReferringToXsd(xsdFile,
                new HashSet<IFile>()));
    }

    private static Set<IFile> getWsdlReferringToXsd(final IFile xsdFile,
            Set<IFile> alreadyProcessed) {
        final Set<IFile> wsdlsReferringXsd = new HashSet<IFile>();

        if (!alreadyProcessed.contains(xsdFile)) {
            alreadyProcessed.add(xsdFile);
            Collection<IResource> affectedWsdlResources =
                    WorkingCopyUtil.getAffectedResources(xsdFile);
            for (IResource iResource : affectedWsdlResources) {
                if (iResource instanceof IFile) {
                    if (Activator.WSDL_FILE_EXTENSION.equals(iResource
                            .getFileExtension())) {
                        wsdlsReferringXsd.add((IFile) iResource);
                    } else {
                        wsdlsReferringXsd
                                .addAll(getWsdlReferringToXsd((IFile) iResource,
                                        alreadyProcessed));
                    }
                }
            }
        }

        return wsdlsReferringXsd;
    }

    /**
     * @param bomResource
     * @return <code>checksum</code> value calculated based on the sources
     *         (WSDL/XSD) of the given generated BOM resource or
     *         <code>null</code> if the source(s) not found.
     */
    public static String calculateChecksumUsingGeneratedBomSources(
            IResource bomResource) {

        if (bomResource != null) {
            final CRC32 crc = new CRC32();

            Set<IResource> srcFiles = findRelevantWSDLOrXSDFiles(bomResource);
            // If sources not found, then return null
            if (srcFiles == null || srcFiles.isEmpty()) {
                return null;
            }

            for (IResource res : srcFiles) {

                if (res != null && res.isAccessible()) {

                    ResourceSet rSet = new ResourceSetImpl();
                    Resource resource =
                            rSet.getResource(URI.createPlatformResourceURI(res
                                    .getFullPath().toString(), true), true);
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    try {
                        resource.save(os, null);
                        /*
                         * XPD-8034: applied XPD-7992 fix here as well which converts bytes to string and replace all \n
                         * by \r\n
                         */
                        String decodedString =
                                new String(os.toByteArray(),
                                        StandardCharsets.UTF_8);
                        decodedString =
                                LF_WITHOUT_CR.matcher(decodedString)
                                        .replaceAll("\r\n"); //$NON-NLS-1$
                        crc.update(decodedString
                                .getBytes(StandardCharsets.UTF_8));

                    } catch (IOException e) {
                        BOMResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Unable to generate a stream from '%s' to calculate checksum.",
                                                resource.getURI().path()));
                    } finally {
                        try {
                            os.close();
                        } catch (IOException e) {
                            // Do nothing
                        }
                    }
                }
            }
            return String.valueOf(crc.getValue());
        }
        return null;
    }

    /**
     * Finds the relevant WSDL or XSD File given a generated BOM File. The
     * absolute location of the WSDL/XSD file name is stored as a stereotype
     * information in the generated BOM File.
     * 
     * The file name is stripped and the equivalent IFile is found and returned.
     * 
     * @param bomFile
     * @param bomsXsdTargetNamespace
     * 
     * @return The WSDL files of XSD files that the given BOM file was generated
     *         from.
     */
    public static Set<IResource> findRelevantWSDLOrXSDFiles(IResource bomFile) {
        WorkingCopy bomWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        Set<IResource> srcFiles = new HashSet<IResource>();
        if (bomWC != null) {
            EObject rootElement = bomWC.getRootElement();
            if (rootElement instanceof Model) {
                Model model = (Model) rootElement;

                List<Stereotype> appliedStereotypes =
                        model.getAppliedStereotypes();

                Stereotype stereoType = null;
                for (Stereotype st : appliedStereotypes) {
                    if (st.getName().equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                        stereoType = st;
                        break;
                    }
                }

                if (stereoType != null) {
                    Object value =
                            model.getValue(stereoType,
                                    XsdStereotypeUtils.XSD_SCHEMA_LOCATION);
                    if (value instanceof String) {
                        for (String path : ((String) value)
                                .split(XsdStereotypeUtils.SCHEMA_LOCATION_DELIMITER)) {

                            IResource file = getWSDLOrXSDFile(bomFile, path);
                            if (file != null && file.exists()) {
                                srcFiles.add(file);
                            }
                        }
                    }
                }
            }
        }
        return srcFiles;
    }

    /**
     * @param bomFile
     * 
     * @return The target namespace of the wsdl/xsd that the given BOM was
     *         generated from.
     */
    public static String findBomsXsdTargetNamespace(IResource bomFile) {
        WorkingCopy bomWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        if (bomWC != null) {
            EObject rootElement = bomWC.getRootElement();
            if (rootElement instanceof Model) {
                Model model = (Model) rootElement;
                List<Stereotype> appliedStereotypes =
                        model.getAppliedStereotypes();
                Stereotype stereoType = null;
                for (Stereotype st : appliedStereotypes) {
                    if (st.getName().equals(XsdStereotypeUtils.XSD_BASED_MODEL)) {
                        stereoType = st;
                        break;
                    }
                }

                if (stereoType != null) {
                    Object xsdTargetNamespace =
                            model.getValue(stereoType,
                                    XsdStereotypeUtils.XSD_TARGET_NAMESPACE);
                    if (xsdTargetNamespace != null
                            && xsdTargetNamespace instanceof String) {
                        return (String) xsdTargetNamespace;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Find the given wsdl / xsd in a wsdl special folder.
     * 
     * @param bomFile
     * @param wsdlOrXsdFilePath
     *            -- sp folder relative path
     */
    private static IResource getWSDLOrXSDFile(IResource bomFile,
            String wsdlOrXsdFilePath) {

        IProject bomProject = bomFile.getProject();

        if (null != bomProject) {
            /*
             * XPD-6062: Because wsdlOrXsdFilePath used to be just simple name,
             * old code used to look in main Service Descriptors folder for any
             * file with same name and then only look in sub-folders for file
             * with same name if main folder empty. All of which was pretty
             * damned flakey. So now we store SF relative path in BOM source
             * schemaLocation annotation, we can just get the SF relative file
             * directly.
             */
            IFile file =
                    SpecialFolderUtil.resolveSpecialFolderRelativePath(bomFile
                            .getProject(),
                            WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND,
                            wsdlOrXsdFilePath);

            return file;
        }
        return null;
    }
}
