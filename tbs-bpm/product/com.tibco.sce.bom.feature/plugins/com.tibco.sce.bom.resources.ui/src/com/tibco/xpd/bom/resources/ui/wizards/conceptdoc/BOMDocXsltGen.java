/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.wizards.conceptdoc;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.documentation.DocGenModelInfo;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGenModelInfo;
import com.tibco.xpd.ui.documentation.engine.AbstractXSLTDocGen;
import com.tibco.xpd.ui.importexport.exportwizard.OutputFile;

/**
 * This class is responsible for the generation of documentation for the BOM
 * model
 * 
 * @author mtorres
 */
public class BOMDocXsltGen extends AbstractXSLTDocGen {

    private static final String XSLT = "/xslts/bom2html.xsl"; //$NON-NLS-1$

    private static final String MESSAGES = "$nl$/xslts/messages.properties"; //$NON-NLS-1$

    private URL[] xslts;

    private IDocGenInfo currentDocGenInfo = null;

    private IPath outputFilePath = null;

    private Map<String, IPath> modelToImageMap;

    private static final String iso8601DateTimePattern =
            "yyyy-MM-dd'T'HH:mm:ssz"; //$NON-NLS-1$     

    public static final String BOM_CONTAINER_NAME =
            Messages.BOMDocXsltGen_GeneratorRootFolderName;

    public static final String PACKAGE_ELEMENT_HREF = "#packagedElement_"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider#getXslts()
     */
    @Override
    public URL[] getXslts() {
        if (xslts == null) {
            xslts = new URL[] { getClass().getResource(XSLT) };
        }
        return xslts;
    }

    @Override
    public Map<String, String> getXsltParameters() {
        return new HashMap<String, String>();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider2#getImportXsltURL(java.lang.String)
     */
    @Override
    public URL getImportXsltURL(String href) {
        return null;
    }

    @Override
    public URL getMessagePropertiesURL(URL xsltURL) {
        return FileLocator.find(Activator.getDefault().getBundle(), new Path(
                MESSAGES), null);

    }

    @Override
    protected String getGeneratorRootFolderName() {
        return BOM_CONTAINER_NAME;
    }

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
                    Activator.getDefault().getLogger().error(e1);
                }
                if (outputFile != null) {
                    // Don't bother creating image if that option has been
                    // turned off.
                    ImageCreator imageCreator = new ImageCreator(null);
                    try {
                        imageCreator.perform(new SubProgressMonitor(
                                new NullProgressMonitor(), 30),
                                sourceFile,
                                outputFile.getFile());
                        modelToImageMap = imageCreator.getModelImagesMap();
                    } catch (CoreException e) {
                        Activator.getDefault().getLogger().error(e);
                    }

                    ResourceCopier resourceCopier = new ResourceCopier();
                    try {
                        resourceCopier.perform(new SubProgressMonitor(
                                new NullProgressMonitor(), 30),
                                sourceFile,
                                outputFile.getFile());
                    } catch (CoreException e) {
                        Activator.getDefault().getLogger().error(e);
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
                        Activator.getDefault().getLogger().error(e);
                    } catch (InterruptedException e) {
                        Activator.getDefault().getLogger().error(e);
                    }
                }
            } finally {
                current.setContextClassLoader(oldLoader);
            }
        }
    }

    /**
     * Returns the output file object based on the selection made in the wizard
     * with regards to the destination of the export.
     * 
     * @param inputFile
     * @return The <code>{@link OutputFile}</code> object which wraps the
     *         resource being created.
     * @throws CoreException
     */
    @Override
    public OutputFile getOutputFile(IFile inputFile, IDocGenInfo docGenInfo)
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
         * XPD-5325: output path is always folder location on the file system,
         * even if the output location is in the workspace.
         */
        IPath path = generatorOutputPath.append(outputFileName);
        outputFile = new OutputFile(path.toFile());

        return outputFile;
    }

    protected String getOutputFileName(String szInputFileName) {
        String outputFileExt = "html"; //$NON-NLS-1$;
        String outputFileName =
                szInputFileName.substring(0, szInputFileName.lastIndexOf('.'));
        outputFileName += "." + outputFileExt; //$NON-NLS-1$
        return outputFileName;
    }

    @Override
    public String getSystemId() {
        return null;
    }

    @Override
    public int getMonitorSubTaskCount() {
        return 0;
    }

    @Override
    public String getSubTaskLeader() {
        return null;
    }

    @Override
    protected List<IDocGenModelInfo> createDocGenModelInfo(IResource resource) {
        if (resource != null) {
            WorkingCopy tempWC =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
            if (tempWC instanceof BOMWorkingCopy) {
                BOMWorkingCopy bomWorkingCopy = (BOMWorkingCopy) tempWC;
                EObject rootElement = bomWorkingCopy.getRootElement();
                if (rootElement instanceof Model) {
                    Model model = (Model) rootElement;
                    List<IDocGenModelInfo> docGenModelInfo =
                            new ArrayList<IDocGenModelInfo>();

                    String packageName = model.getName();
                    String modelId = getModelId(bomWorkingCopy);
                    String modelImage = getImagePathForModelId(modelId);
                    String description = null;
                    EList<Comment> ownedComments = model.getOwnedComments();
                    if (ownedComments != null && ownedComments.size() > 0) {
                        Comment comment = ownedComments.iterator().next();
                        if (comment != null) {
                            description = comment.getBody();
                        }
                    }

                    /*
                     * store the path of the 'Images' folder.
                     */
                    String imageFolderPath =
                            new Path(modelImage).removeLastSegments(1)
                                    .toString();

                    String changeDate = getChangeDate(resource);
                    String author = getAuthor(model);
                    String link = getModelLink(modelId);
                    DocGenModelInfo docGenBOMModelInfo = new DocGenModelInfo();
                    /*
                     * SCF-420: We no longer pass the bomcontainer and bom title
                     * to generate the BOM index.html documentation as it was
                     * very specific to BOM doc export. Now we have changed the
                     * index.xml(in the .documentationOutput folder) file that
                     * gets generated to have more generalised elements like
                     * 'modelconatiner' which specifies the title of the table
                     * and the priority in which the table should be displayed;
                     * which has the child element 'model' that decides the
                     * contents of the table.
                     */
                    populateDocGenModelInfo(docGenBOMModelInfo,
                            modelImage,
                            packageName,
                            packageName,
                            description,
                            changeDate,
                            author,
                            null,
                            link,
                            null,
                            null,
                            imageFolderPath
                                    + "/" + ResourceCopier.BOM_IMAGE_NAME, //$NON-NLS-1$
                            Messages.BOMDocXsltGen_BomModelConatiner_title,
                            "200", /* priority 200 *///$NON-NLS-1$
                            imageFolderPath
                                    + "/" + ResourceCopier.BOM_IMAGE_NAME); //$NON-NLS-1$
                    docGenModelInfo.add(docGenBOMModelInfo);
                    return docGenModelInfo;
                }
            }
        }
        return Collections.emptyList();
    }

    private String getModelId(BOMWorkingCopy bwc) {
        if (bwc != null) {
            Diagram diagram = null;
            List<Diagram> diagrams = bwc.getDiagrams();
            if (diagrams.size() > 0) {
                diagram = diagrams.get(0);

            }
            if (diagram != null && diagram.eResource() instanceof XMIResource) {
                XMIResource res = (XMIResource) diagram.eResource();
                return res.getID(diagram);
            }
        }
        return null;
    }

    private String getImagePathForModelId(String modelId) {
        if (modelToImageMap != null && modelId != null) {
            IPath imagePath = modelToImageMap.get(modelId);
            if (imagePath != null && currentDocGenInfo != null) {
                IPath baseOutputPath = getBaseOutputPath(currentDocGenInfo);
                String absoluteImageStrPath = imagePath.toPortableString();
                if (baseOutputPath != null) {
                    String absoluteBaseOutputStrPath =
                            baseOutputPath.addTrailingSeparator()
                                    .toPortableString();
                    return makePathRelative(absoluteBaseOutputStrPath,
                            absoluteImageStrPath);
                }
            }
        }
        return null;
    }

    private String getAuthor(Model model) {
        if (model != null) {
            return getDetail(model, "author");//$NON-NLS-1$
        }
        return null;
    }

    private String getDetail(Model model, String key) {
        EList<EAnnotation> annotations = model.getEAnnotations();
        if (annotations != null) {
            for (EAnnotation annotation : annotations) {
                if (annotation != null) {
                    EMap<String, String> details = annotation.getDetails();
                    if (details != null) {
                        String created = details.get(key);
                        if (created != null) {
                            return created;
                        }
                    }
                }
            }
        }
        return null;
    }

    private String getModelLink(String modelId) {
        if (modelId != null && outputFilePath != null) {
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
     * Method to get an ISO8601 date time pattern string from a date
     * 
     * @param date
     *            Date to turn into iso8601 pattern string
     * @return
     */
    private String getISO8601DateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(iso8601DateTimePattern);
        return dateFormat.format(date);

    }

    private String getChangeDate(IResource resource) {
        long stamp = resource.getLocalTimeStamp();
        Date dateObj = new Date(stamp);
        return getISO8601DateTime(dateObj);
    }

}
