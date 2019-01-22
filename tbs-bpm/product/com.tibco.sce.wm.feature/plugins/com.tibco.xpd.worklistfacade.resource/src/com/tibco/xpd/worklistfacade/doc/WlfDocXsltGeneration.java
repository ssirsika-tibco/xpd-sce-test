/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.doc;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.documentation.DocGenModelInfo;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGenModelInfo;
import com.tibco.xpd.ui.documentation.engine.AbstractXSLTDocGen;
import com.tibco.xpd.ui.importexport.exportwizard.OutputFile;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;

/**
 * This class is responsible for the generation of documentation for the WLF
 * model(both index.html and xslt doc)
 * 
 * @author kthombar
 * @since May 20, 2015
 */
public class WlfDocXsltGeneration extends AbstractXSLTDocGen {

    private static final String XSLT = "/xslts/wlf2html.xsl"; //$NON-NLS-1$

    private URL[] xslts;

    private IDocGenInfo currentDocGenInfo = null;

    private IPath outputFilePath = null;

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2#getImportXsltURL(java.lang.String)
     * 
     * @param href
     * @return
     */
    @Override
    public URL getImportXsltURL(String href) {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2#getMessagePropertiesURL(java.net.URL)
     * 
     * @param xsltURL
     * @return
     */
    @Override
    public URL getMessagePropertiesURL(URL xsltURL) {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXslts()
     * 
     * @return
     */
    @Override
    public URL[] getXslts() {
        if (xslts == null) {
            xslts = new URL[] { getClass().getResource(XSLT) };
        }

        return xslts;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getSystemId()
     * 
     * @return
     */
    @Override
    public String getSystemId() {

        return null;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXsltParameters()
     * 
     * @return
     */
    @Override
    public Map<String, String> getXsltParameters() {
        HashMap<String, String> map = new HashMap<String, String>();

        if (currentDocGenInfo.getSource() instanceof IFile) {
            /*
             * passing the wlf file name as nowhere in wlf file do we store the
             * file name, hence there is no way that the xsl file can determine
             * the file name.
             */
            map.put("wlfFileName", currentDocGenInfo.getSource().getName()); //$NON-NLS-1$

            IPath fullPath =
                    ((IFile) currentDocGenInfo.getSource()).getFullPath();

            /*
             * pass the wlf file path as parameter.
             */
            if (fullPath != null && !fullPath.isEmpty()) {
                map.put("wlfFileFullPath", fullPath.toString()); //$NON-NLS-1$
            }

        }
        return map;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformProgressMonitorSupport#getMonitorSubTaskCount()
     * 
     * @return
     */
    @Override
    public int getMonitorSubTaskCount() {

        return 0;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformProgressMonitorSupport#getSubTaskLeader()
     * 
     * @return
     */
    @Override
    public String getSubTaskLeader() {

        return null;
    }

    /**
     * @see com.tibco.xpd.ui.documentation.engine.AbstractXSLTDocGen#getOutputFile(org.eclipse.core.resources.IFile,
     *      com.tibco.xpd.ui.documentation.IDocGenInfo)
     * 
     * @param inputFile
     * @param docGenInfo
     * @return
     * @throws CoreException
     */
    @Override
    protected OutputFile getOutputFile(IFile inputFile, IDocGenInfo docGenInfo)
            throws CoreException {
        OutputFile outputFile = null;
        String outputFileName = getOutputFileName(inputFile.getName());

        // If the output file name is null or blank then set it to the same name
        // as the input file
        if (outputFileName == null || outputFileName.length() == 0) {
            outputFileName = inputFile.getName();
        }

        IPath generatorOutputPath = docGenInfo.getGeneratorOutputPath();
        /*
         * If export destination is the project then create the export folder in
         * the workspace if required and return an IFile
         */
        if (generatorOutputPath != null) {
            IPath path = generatorOutputPath.append(outputFileName);
            outputFile = new OutputFile(path.toFile());
        }

        return outputFile;
    }

    /**
     * 
     * @param szInputFileName
     * @return the output html filename.
     */
    protected String getOutputFileName(String szInputFileName) {
        String outputFileExt = "html"; //$NON-NLS-1$;
        String outputFileName =
                szInputFileName.substring(0, szInputFileName.lastIndexOf('.'));
        outputFileName += "." + outputFileExt; //$NON-NLS-1$
        return outputFileName;
    }

    /**
     * @see com.tibco.xpd.ui.documentation.engine.AbstractBaseDocGen#runEngine(com.tibco.xpd.ui.documentation.IDocGenInfo)
     * 
     * @param docGenInfo
     */
    @Override
    protected void runEngine(IDocGenInfo docGenInfo) {
        currentDocGenInfo = docGenInfo;
        IResource source = docGenInfo.getSource();
        if (source instanceof IFile) {
            IFile sourceFile = (IFile) source;
            Thread current = Thread.currentThread();
            ClassLoader oldLoader = current.getContextClassLoader();
            try {
                current.setContextClassLoader(getClass().getClassLoader());
                OutputFile outputFile = null;

                try {
                    outputFile = getOutputFile(sourceFile, docGenInfo);
                } catch (CoreException e1) {

                    XpdResourcesPlugin.getDefault().getLogger().error(e1);
                }
                if (outputFile != null) {

                    ResourceCopier resourceCopier = new ResourceCopier();
                    try {
                        resourceCopier.perform(new SubProgressMonitor(
                                new NullProgressMonitor(), 30),
                                sourceFile,
                                outputFile.getFile());
                    } catch (CoreException e) {
                        XpdResourcesPlugin.getDefault().getLogger().error(e);
                    }

                    try {
                        performTransformation(new NullProgressMonitor(),
                                sourceFile,
                                docGenInfo);
                        if (outputFile != null && outputFile.getPath() != null) {
                            outputFilePath =
                                    new Path(outputFile.getFile()
                                            .getAbsolutePath());
                        }
                    } catch (CoreException e) {
                        XpdResourcesPlugin.getDefault().getLogger().error(e);
                    } catch (InterruptedException e) {
                        XpdResourcesPlugin.getDefault().getLogger().error(e);
                    }
                }
            } finally {
                current.setContextClassLoader(oldLoader);
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.documentation.engine.AbstractBaseDocGen#getGeneratorRootFolderName()
     * 
     * @return
     */
    @Override
    protected String getGeneratorRootFolderName() {

        return Messages.WlfDocXsltGeneration_WlfModelContainer_title;
    }

    /**
     * @see com.tibco.xpd.ui.documentation.engine.AbstractBaseDocGen#createDocGenModelInfo(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     * @return
     */
    @SuppressWarnings("deprecation")
    @Override
    protected List<IDocGenModelInfo> createDocGenModelInfo(IResource resource) {

        List<IDocGenModelInfo> docGenModelInfo =
                new ArrayList<IDocGenModelInfo>();

        if (resource != null) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(resource);

            if (workingCopy != null) {

                DocGenModelInfo docGenXPDLModelInfo = new DocGenModelInfo();

                String modelName = resource.getName();

                String changeDate = getChangeDate(resource);

                String wlfImagePath = getGeneratorRootFolderName() + "/" //$NON-NLS-1$
                        + ResourceCopier.IMAGES_FOLDER + "/" //$NON-NLS-1$
                        + ResourceCopier.WLF_FILE_ICON;

                populateDocGenModelInfo(docGenXPDLModelInfo,
                        null,
                        modelName,
                        modelName,
                        "-", // WLF has no description//$NON-NLS-1$
                        changeDate,
                        "-", // WLF has no Author//$NON-NLS-1$
                        null,
                        getLink(),
                        null,
                        null,
                        wlfImagePath,
                        Messages.WlfDocXsltGeneration_WlfModelContainer_title,
                        "500", // Setting priority =500 //$NON-NLS-1$
                        wlfImagePath);

                docGenModelInfo.add(docGenXPDLModelInfo);

            }
        }
        return docGenModelInfo;
    }

    /**
     * 
     * @return link to generated html file of the given gsd resource.
     */
    private String getLink() {
        if (outputFilePath != null) {
            IPath baseOutputPath = getBaseOutputPath(currentDocGenInfo);
            if (baseOutputPath != null) {
                String absoluteBaseOutputStrPath =
                        baseOutputPath.addTrailingSeparator()
                                .toPortableString();
                String modelLink =
                        makePathRelative(absoluteBaseOutputStrPath,
                                outputFilePath.toPortableString());
                if (modelLink != null) {
                    return modelLink;
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param resource
     * @return the last change date of the resource.
     */
    private String getChangeDate(IResource resource) {
        long stamp = resource.getLocalTimeStamp();
        Date dateObj = new Date(stamp);

        return LocaleUtils.getISO8601DateTime(dateObj);
    }
}
