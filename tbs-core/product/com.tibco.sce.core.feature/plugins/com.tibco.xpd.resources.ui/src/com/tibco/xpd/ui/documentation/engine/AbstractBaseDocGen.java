/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.engine;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import com.tibco.xpd.ui.documentation.DocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGen;
import com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo;
import com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo2;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGenModelInfo;

/**
 * This class is used for the generation of documentation for the passed input
 * {@link IResource}
 * 
 * The documentation is generated in the outputPath
 * 
 * @author mtorres
 */
public abstract class AbstractBaseDocGen implements IDocGen {

    public static final String MODEL_TYPE_CONTAINER = "modelcontainer"; //$NON-NLS-1$

    public static final String MODEL_CONTAINER = "model"; //$NON-NLS-1$

    /**
     * Runs the engine
     * 
     * @param docPostGenInfo
     */
    protected abstract void runEngine(IDocGenInfo docGenInfo);

    /**
     * Disposes of the report engine and any associated resources.
     * 
     * @param docGenInfo
     */
    protected abstract void disposeEngine(IDocGenInfo docGenInfo);

    /**
     * Returns the base Generator Roots Folder Name
     * 
     * @return the folder name
     * 
     **/
    protected abstract String getGeneratorRootFolderName();

    /**
     * Returns the base output path selected by the user
     * 
     * @param docGenInfo
     * 
     **/
    protected IPath getBaseOutputPath(IDocGenInfo docGenInfo) {
        if (docGenInfo != null) {
            return docGenInfo.getBaseOutputPath();
        }
        return null;
    }

    /**
     * Generates the documentation.
     * 
     * @param inputs
     * @param outputPath
     * @param templatePath
     */
    @Override
    public List<IDocGenInfo> generate(List<IResource> inputs, IPath outputPath) {
        List<IDocGenInfo> docGenInfos = new ArrayList<IDocGenInfo>();
        if (inputs != null && outputPath != null) {
            for (IResource resource : inputs) {
                DocGenInfo docGenInfo = new DocGenInfo();
                docGenInfo.setSource(resource);
                docGenInfo.setBaseOutputPath(outputPath);
                docGenInfo.setGeneratorOutputPath(outputPath
                        .append(getGeneratorRootFolderName()));
                runEngine(docGenInfo);
                List<IDocGenModelInfo> docGenModelInfo =
                        createDocGenModelInfo(resource);
                docGenInfo.setDocGenModelInfo(docGenModelInfo);
                disposeEngine(docGenInfo);
                docGenInfos.add(docGenInfo);
            }
        }
        return docGenInfos;
    }

    /**
     * Returns the Generator output path
     * 
     * @return the folder name
     * 
     **/
    protected IPath getGeneratorOutputPath(IDocGenInfo docGenInfo) {
        if (docGenInfo != null && docGenInfo.getGeneratorOutputPath() != null) {
            return docGenInfo.getGeneratorOutputPath();
        }
        return null;
    }

    /**
     * Creates the DocGenModelInfo object populating all the needed information
     * from the model
     * 
     * @return List<IDocGenModelInfo>
     */
    protected abstract List<IDocGenModelInfo> createDocGenModelInfo(
            IResource resource);

    /**
     * 
     * @param docGenModelInfo
     *            the model info that will be populated with necessary
     *            information required for doc generation.
     * @param thumbnailPath
     *            the path to the Diagram Image of the of the exported element.
     * @param typeContainer
     *            the container of the model
     * @param type
     *            the type of model being exported
     * @param name
     *            the name of the element that is being exported
     * @param modelName
     *            the model name of the element that is being exported
     * @param description
     *            the description of the element that is being exported
     * @param changeDate
     *            the change date info
     * @param author
     *            the author of model that is being exported
     * @param notes
     *            additional notes
     * @param link
     *            link to the Xslt documentation that will have detailed info of
     *            the element.
     * @param subDocGenModels
     *            the additional sub documentation if any.
     * @param extraStuff
     *            any extra details
     * 
     * @deprecated This method should strictly not be used. Use
     *             {@link AbstractBaseDocGen#populateDocGenModelInfo(IDocGenModelInfo, String, String, String, String, String, String, String, String, List, String, String, String, String, String)}
     *             instead because we no longer pass the container to generate
     *             the index.html documentation as it was very specific to doc
     *             export of that element type. Now we have changed the
     *             index.xml(in the .documentationOutput folder) file that gets
     *             generated to have more generalised elements like
     *             'modelconatiner' which specifies the title of the table and
     *             the priority in which the table should be displayed; which
     *             has the child element 'model' that decides the contents of
     *             the table.
     */
    @Deprecated
    protected void populateDocGenModelInfo(IDocGenModelInfo docGenModelInfo,
            String thumbnailPath, String typeContainer, String type,
            String name, String modelName, String description,
            String changeDate, String author, String notes, String link,
            List<IDocGenModelInfo> subDocGenModels, String extraStuff) {
        docGenModelInfo.setTypeContainer(typeContainer);
        docGenModelInfo.setType(type);
        // Set the thumbnail Path
        if (thumbnailPath != null) {
            docGenModelInfo.setThumbnailPath(thumbnailPath);
        }
        // Set the name
        if (name != null) {
            docGenModelInfo.setName(name);
        }
        // Set the model Name
        if (modelName != null) {
            docGenModelInfo.setModelName(modelName);
        }
        // Set the description
        if (description != null) {
            docGenModelInfo.setDescription(description);
        }
        // Set the changed date
        if (changeDate != null) {
            docGenModelInfo.setChangeDate(changeDate);
        }
        // Set the author
        if (author != null) {
            docGenModelInfo.setAuthor(author);
        }
        // Set the link
        if (link != null) {
            docGenModelInfo.setLink(link);
        }
        // Set the submodels
        if (subDocGenModels != null) {
            docGenModelInfo.setDocGenModelInfo(subDocGenModels);
        }
        // Set the extra stuff
        if (extraStuff != null) {
            docGenModelInfo.setExtraStuff(extraStuff);
        }
    }

    /**
     * Populates the Documentation model with the path of icons.
     * 
     * @param docGenModelInfo
     *            the model info that will be populated with necessary
     *            information required for doc generation.
     * @param thumbnailPath
     *            the path to the Diagram Image of the of the exported element.
     * @param typeContainer
     *            the container of the model
     * @param type
     *            the type of model being exported
     * @param name
     *            the name of the element that is being exported
     * @param modelName
     *            the model name of the element that is being exported
     * @param description
     *            the description of the element that is being exported
     * @param changeDate
     *            the change date info
     * @param author
     *            the author of model that is being exported
     * @param notes
     *            additional notes
     * @param link
     *            link to the Xslt documentation that will have detailed info of
     *            the element.
     * @param subDocGenModels
     *            the additional sub documentation if any.
     * @param extraStuff
     *            any extra details
     * @param iconPath
     *            the path to the icon to be displayed for the element.
     * 
     * @deprecated This method should strictly not be used. Use
     *             {@link AbstractBaseDocGen#populateDocGenModelInfo(IDocGenModelInfo, String, String, String, String, String, String, String, String, List, String, String, String, String, String)}
     *             instead because we no longer pass the container to generate
     *             the index.html documentation as it was very specific to doc
     *             export of that element type. Now we have changed the
     *             index.xml(in the .documentationOutput folder) file that gets
     *             generated to have more generalised elements like
     *             'modelconatiner' which specifies the title of the table and
     *             the priority in which the table should be displayed; which
     *             has the child element 'model' that decides the contents of
     *             the table.
     */
    @Deprecated
    protected void populateDocGenModelInfo(IDocGenModelInfo docGenModelInfo,
            String thumbnailPath, String typeContainer, String type,
            String name, String modelName, String description,
            String changeDate, String author, String notes, String link,
            List<IDocGenModelInfo> subDocGenModels, String extraStuff,
            String iconPath) {
        if (docGenModelInfo instanceof IDocGenAdditionalModelInfo) {
            if (iconPath != null) {
                ((IDocGenAdditionalModelInfo) docGenModelInfo)
                        .setIconPath(iconPath);
            }
        }
        /*
         * populate the Doc Model.
         */
        populateDocGenModelInfo(docGenModelInfo,
                thumbnailPath,
                typeContainer,
                type,
                name,
                modelName,
                description,
                changeDate,
                author,
                notes,
                link,
                subDocGenModels,
                extraStuff);
    }

    /**
     * Populates the passed Documentation model 'docGenModelInfo' with all the
     * necessary info required for Documentation export.
     * 
     * @param docGenModelInfo
     *            the model info that will be populated with necessary
     *            information required for doc generation.
     * @param exportedElementThumbnailPath
     *            the path to the Diagram Image of the of the exported element.
     * @param exportedElementName
     *            the name of the element that is being exported
     * @param exportedElementModelName
     *            the model name of the element that is being exported
     * @param exportedElementDescription
     *            the description of the element that is being exported
     * @param changeDate
     *            the change date info
     * @param author
     *            the author of model that is being exported
     * @param notes
     *            additional notes
     * @param linkToTheXsltDocumentation
     *            link to the Xslt documentation that will have detailed info of
     *            the element.
     * @param subDocGenModels
     *            the additional sub documentation if any.
     * @param extraStuff
     *            any extra deatils
     * @param exportedElementIconPath
     *            the path to the icon to be displayed for the element.
     * @param modelContainerTitle
     *            the title of the model that will be displayed as the table
     *            title.
     * @param modelPriority
     *            the priority/order in which the model should be displayed in
     *            the table
     * @param modelIconPath
     *            the path to the model icon.
     */
    protected void populateDocGenModelInfo(
            IDocGenAdditionalModelInfo2 docGenModelInfo,
            String exportedElementThumbnailPath, String exportedElementName,
            String exportedElementModelName, String exportedElementDescription,
            String changeDate, String author, String notes,
            String linkToTheXsltDocumentation,
            List<IDocGenModelInfo> subDocGenModels, String extraStuff,
            String exportedElementIconPath, String modelContainerTitle,
            String modelPriority, String modelIconPath) {

        if (modelContainerTitle != null) {
            /*
             * add the model table title
             */
            docGenModelInfo.setModelTitle(modelContainerTitle);
        }

        if (modelPriority != null) {
            /*
             * add the priority in which the doc should be displayed
             */
            docGenModelInfo.setModelPriority(modelPriority);
        }

        if (modelIconPath != null) {
            /*
             * add the model Icon path info
             */
            docGenModelInfo.setModelIconPath(modelIconPath);
        }

        /*
         * populate the remaining doc model using existing methods.
         */
        populateDocGenModelInfo(docGenModelInfo,
                exportedElementThumbnailPath,
                MODEL_TYPE_CONTAINER,
                MODEL_CONTAINER,
                exportedElementName,
                exportedElementModelName,
                exportedElementDescription,
                changeDate,
                author,
                notes,
                linkToTheXsltDocumentation,
                subDocGenModels,
                extraStuff,
                exportedElementIconPath);
    }

    protected String makePathRelative(String basePath, String path) {
        if (basePath != null && path != null && path.startsWith(basePath)) {
            // Make the path relative to the base output path
            return path.replaceFirst(basePath, "");//$NON-NLS-1$
        }
        return null;
    }

}
