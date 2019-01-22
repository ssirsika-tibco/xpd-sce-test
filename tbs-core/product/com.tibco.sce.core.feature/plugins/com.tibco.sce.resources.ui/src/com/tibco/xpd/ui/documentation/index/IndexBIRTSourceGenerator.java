/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.dom.DocumentImpl;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo;
import com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo2;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGenModelInfo;

/**
 * This class is responsible for the creation of the source file containing all
 * the data needed for the BIRT mappings in the index document generation
 * 
 * @author mtorres
 */
public class IndexBIRTSourceGenerator {

    private String INDEX_FILE_NAME = "index.xml";//$NON-NLS-1$

    private static IndexBIRTSourceGenerator INSTANCE = null;

    private final String ROOT_ELEMENT_NAME = "index";//$NON-NLS-1$

    private final String PROJECT_NAME_PROPERTY_NAME = "projectname";//$NON-NLS-1$

    private final String THUMBNAIL_PATH_ELEMENT_NAME = "thumbnailpath";//$NON-NLS-1$

    private final String NAME_ELEMENT_NAME = "name";//$NON-NLS-1$

    private final String MODELNAME_ELEMENT_NAME = "modelname";//$NON-NLS-1$

    private final String DESCRIPTION_ELEMENT_NAME = "description";//$NON-NLS-1$

    private final String CHANGEDATE_ELEMENT_NAME = "changeDate";//$NON-NLS-1$

    private final String AUTHOR_ELEMENT_NAME = "author";//$NON-NLS-1$

    private final String NOTES_ELEMENT_NAME = "notes";//$NON-NLS-1$

    private final String LINK_ELEMENT_NAME = "link";//$NON-NLS-1$

    private final String EXTRASTUFF_ELEMENT_NAME = "extrastuff";//$NON-NLS-1$

    private final String ICON_PATH = "iconpath";//$NON-NLS-1$

    private final String ATTR_MODEL_CONTAINER_TITLE = "title";//$NON-NLS-1$

    private final String ATTR_MODEL_CONTAINER_PRIORITY = "priority";//$NON-NLS-1$

    private IndexBIRTSourceGenerator() {
    }

    public static IndexBIRTSourceGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IndexBIRTSourceGenerator();
        }
        return INSTANCE;
    }

    /**
     * Generation of the source file
     * 
     * @param docGenInfo
     * @return IFile
     */
    public IFile generateSource(String projectName,
            List<IDocGenInfo> docGenInfo, IPath outputSourcePath) {
        Document sourceDom = createSourceDom(projectName, docGenInfo);
        try {
            IPath tempFolderPath =
                    createTempFolder(ResourcesPlugin.getWorkspace().getRoot()
                            .getFolder(outputSourcePath));
            if (tempFolderPath != null) {
                return writeXmlFile(sourceDom,
                        tempFolderPath.append(INDEX_FILE_NAME));
            }
        } catch (CoreException e) {
            XpdResourcesUIActivator.getDefault().getLogger().error(e);
        }
        return null;
    }

    /**
     * Create the source dom
     * 
     * @param docGenInfo
     * @return the source Document
     */
    @SuppressWarnings("deprecation")
    private Document createSourceDom(String projectName,
            List<IDocGenInfo> docGenInfo) {
        // Create Document
        Document sourceDocument = new DocumentImpl();
        Element sourceRootElement =
                createSourceRootElement(projectName, sourceDocument);
        if (sourceRootElement != null) {
            sourceDocument.appendChild(sourceRootElement);
            if (docGenInfo != null && !docGenInfo.isEmpty()) {

                /*
                 * XPD-4744 Sort entries by name. Easiest way to ammend this
                 * code to do so is to sort by type then name
                 */
                List<IDocGenModelInfo> sortedInfos =
                        new ArrayList<IDocGenModelInfo>();
                for (IDocGenInfo info : docGenInfo) {
                    List<IDocGenModelInfo> docGenModelInfo =
                            info.getDocGenModelInfo();
                    if (docGenModelInfo != null) {
                        sortedInfos.addAll(docGenModelInfo);
                    }
                }

                Collections.sort(sortedInfos,
                        new Comparator<IDocGenModelInfo>() {

                            @Override
                            public int compare(IDocGenModelInfo o1,
                                    IDocGenModelInfo o2) {
                                /*
                                 * SCF-420 : No need to sort the container,
                                 * because now we use the 'modelcontainer'
                                 * element which has priority and BIRT report
                                 * sorts the container according to these
                                 * priorities.
                                 */

                                /*
                                 * Sort the elements by name so that they are
                                 * displayed in alphabetic order in the table.
                                 */
                                String name1 = o1.getName();
                                if (name1 == null) {
                                    name1 = ""; //$NON-NLS-1$
                                }

                                String name2 = o2.getName();
                                if (name2 == null) {
                                    name2 = ""; //$NON-NLS-1$
                                }

                                return name1.compareToIgnoreCase(name2);
                            }
                        });

                for (IDocGenModelInfo docGenModel : sortedInfos) {
                    String typeContainer = docGenModel.getTypeContainer();
                    if (typeContainer != null) {
                        /*
                         * Create or get the modecontainer element(either get
                         * the existing one or create a new one and get it.)
                         */
                        /*
                         * SCF-420: We have made the BIRT documentation more
                         * generalized. Initially we specially passed the
                         * container type; now instead we used the model
                         * container which takes the title of the element we
                         * wish to display in BIRT documentation table.
                         */
                        Element typeContainerElement =
                                getTypeContainerElement(sourceDocument,
                                        sourceRootElement,
                                        docGenModel);
                        if (typeContainerElement != null) {

                            createTypeElement(sourceDocument,
                                    typeContainerElement,
                                    docGenModel);
                        }
                    }
                }
            }
        }
        return sourceDocument;
    }

    @SuppressWarnings("deprecation")
    private Element createTypeElement(Document sourceDocument,
            Element typeContainerElement, IDocGenModelInfo docGenModel) {
        String type = docGenModel.getType();
        if (type != null) {
            Element typeElement = sourceDocument.createElement(type);
            if (typeElement != null) {
                typeContainerElement.appendChild(typeElement);
                // Create fields
                // Create ThumbnailPath Field
                Element thumbnailPathElement =
                        sourceDocument
                                .createElement(THUMBNAIL_PATH_ELEMENT_NAME);
                if (docGenModel.getThumbnailPath() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getThumbnailPath());
                    thumbnailPathElement.appendChild(cdData);
                }
                typeElement.appendChild(thumbnailPathElement);
                // Create Name Field
                Element nameElement =
                        sourceDocument.createElement(NAME_ELEMENT_NAME);
                if (docGenModel.getName() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getName());
                    nameElement.appendChild(cdData);
                }
                typeElement.appendChild(nameElement);
                // Create Model Name Field
                Element modelNameElement =
                        sourceDocument.createElement(MODELNAME_ELEMENT_NAME);
                if (docGenModel.getModelName() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getModelName());
                    modelNameElement.appendChild(cdData);
                }
                typeElement.appendChild(modelNameElement);
                // Create Description Field
                Element descriptionElement =
                        sourceDocument.createElement(DESCRIPTION_ELEMENT_NAME);
                if (docGenModel.getDescription() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getDescription());
                    descriptionElement.appendChild(cdData);
                }
                typeElement.appendChild(descriptionElement);
                // Create Change Date Field
                Element changeDateElement =
                        sourceDocument.createElement(CHANGEDATE_ELEMENT_NAME);
                if (docGenModel.getChangeDate() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getChangeDate());
                    changeDateElement.appendChild(cdData);
                }
                typeElement.appendChild(changeDateElement);
                // Create Author Field
                Element authorElement =
                        sourceDocument.createElement(AUTHOR_ELEMENT_NAME);
                if (docGenModel.getAuthor() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getAuthor());
                    authorElement.appendChild(cdData);
                }
                typeElement.appendChild(authorElement);
                // Create Author Field
                Element notesElement =
                        sourceDocument.createElement(NOTES_ELEMENT_NAME);
                if (docGenModel.getNotes() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getNotes());
                    notesElement.appendChild(cdData);
                }
                typeElement.appendChild(notesElement);
                // Create Link Field
                Element linkElement =
                        sourceDocument.createElement(LINK_ELEMENT_NAME);
                if (docGenModel.getLink() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getLink());
                    linkElement.appendChild(cdData);
                }
                typeElement.appendChild(linkElement);
                // Create Sub Elements
                List<IDocGenModelInfo> subDocGenModelInfo =
                        docGenModel.getDocGenModelInfo();
                if (subDocGenModelInfo != null && !subDocGenModelInfo.isEmpty()) {
                    for (IDocGenModelInfo subModel : subDocGenModelInfo) {
                        if (subModel != null) {
                            createTypeElement(sourceDocument,
                                    typeElement,
                                    subModel);
                        }
                    }
                }
                // Create extra stuff Field
                Element extraStuffElement =
                        sourceDocument.createElement(EXTRASTUFF_ELEMENT_NAME);
                if (docGenModel.getExtraStuff() != null) {
                    CDATASection cdData =
                            sourceDocument.createCDATASection(docGenModel
                                    .getExtraStuff());
                    extraStuffElement.appendChild(cdData);
                }
                typeElement.appendChild(extraStuffElement);

                // Create Icon Path field
                if (docGenModel instanceof IDocGenAdditionalModelInfo) {
                    Element iconPathElement =
                            sourceDocument.createElement(ICON_PATH);
                    if (((IDocGenAdditionalModelInfo) docGenModel)
                            .getIconPath() != null) {
                        CDATASection cdData =
                                sourceDocument
                                        .createCDATASection(((IDocGenAdditionalModelInfo) docGenModel)
                                                .getIconPath());
                        iconPathElement.appendChild(cdData);
                    }
                    typeElement.appendChild(iconPathElement);
                }
            }
        }
        return null;
    }

    /**
     * Create and returns the modelcontainer element with the title and priority
     * in the passed docGenModel. If an element already exists with the given
     * name then returns it.
     * 
     * @param sourceDocument
     * @param sourceRootElement
     * @param docGenModel
     * @return
     */
    @SuppressWarnings("deprecation")
    private Element getTypeContainerElement(Document sourceDocument,
            Element sourceRootElement, IDocGenModelInfo docGenModel) {

        String modelTitle = null;

        if (docGenModel instanceof IDocGenAdditionalModelInfo2) {

            IDocGenAdditionalModelInfo2 additionalDocGenInfo =
                    (IDocGenAdditionalModelInfo2) docGenModel;

            modelTitle = additionalDocGenInfo.getModelTitle();
        }

        String typeContainer = docGenModel.getTypeContainer();

        //
        if (sourceDocument != null && typeContainer != null
                && modelTitle != null) {

            /*
             * get the modelcontainer element if it already exists.
             */
            Element alreadyExistingElement =
                    getAlreadyExistingElementInSourceDoc(sourceRootElement,
                            typeContainer,
                            modelTitle);

            if (alreadyExistingElement == null) {
                // Create the element if none found

                Element modelContainer =
                        sourceDocument.createElement(typeContainer);

                /*
                 * Create the title and priority attributes and Icon path child
                 * element for the model container.
                 */
                createModelContainerArrtibutesAndChildElements(sourceDocument,
                        docGenModel,
                        modelContainer);

                // Add the element to the root
                sourceRootElement.appendChild(modelContainer);
                return modelContainer;
            } else {

                return alreadyExistingElement;

            }
        }
        return null;
    }

    /**
     * Create the title and priority attributes and Icon path child element for
     * the model container.
     * 
     * @param sourceDocument
     * @param docGenModel
     * @param modelContainer
     */
    @SuppressWarnings("deprecation")
    private void createModelContainerArrtibutesAndChildElements(
            Document sourceDocument, IDocGenModelInfo docGenModel,
            Element modelContainer) {

        if (docGenModel instanceof IDocGenAdditionalModelInfo2) {

            IDocGenAdditionalModelInfo2 additionalDocGenInfo =
                    (IDocGenAdditionalModelInfo2) docGenModel;

            String modelTitle = additionalDocGenInfo.getModelTitle();

            String modelPriority = additionalDocGenInfo.getModelPriority();

            if (modelTitle != null) {
                Attr titleAttribute =
                        sourceDocument
                                .createAttribute(ATTR_MODEL_CONTAINER_TITLE);

                if (titleAttribute != null) {
                    titleAttribute.setValue(modelTitle);
                    modelContainer.setAttributeNode(titleAttribute);
                }
            }

            if (modelPriority != null) {
                Attr priorityAttribute =
                        sourceDocument
                                .createAttribute(ATTR_MODEL_CONTAINER_PRIORITY);

                if (priorityAttribute != null) {
                    priorityAttribute.setValue(modelPriority);
                    modelContainer.setAttributeNode(priorityAttribute);
                }
            }
            /*
             * create the icon path child element.
             */
            createModelContainerIconPathChild(sourceDocument,
                    modelContainer,
                    additionalDocGenInfo);
        }
    }

    /**
     * Create the Icon Path child element for the passed 'modelContainer'
     * 
     * @param sourceDocument
     * @param modelContainer
     * @param docGenModel
     */
    private void createModelContainerIconPathChild(Document sourceDocument,
            Element modelContainer, IDocGenAdditionalModelInfo2 docGenModel) {

        String modelIconPath = docGenModel.getModelIconPath();

        if (modelIconPath != null) {

            Element iconPathElement = sourceDocument.createElement(ICON_PATH);

            CDATASection cdData =
                    sourceDocument.createCDATASection(modelIconPath);

            iconPathElement.appendChild(cdData);

            modelContainer.appendChild(iconPathElement);
        }
    }

    /**
     * 
     * @param sourceRootElement
     * @param typeContainer
     * @param modelTitle
     * @return the already existing modelcontainer element with the passed title
     *         else return <code>null</code> if none exists.
     */
    private Element getAlreadyExistingElementInSourceDoc(
            Element sourceRootElement, String typeContainer, String modelTitle) {

        Element alreadyExistingElement = null;

        NodeList elementsByTagName =
                sourceRootElement.getElementsByTagName(typeContainer);

        if (elementsByTagName != null && elementsByTagName.getLength() != 0) {

            for (int i = 0; i < elementsByTagName.getLength(); i++) {

                Node item = elementsByTagName.item(i);

                if (item instanceof Element) {

                    String modelTitleFromElement =
                            ((Element) item)
                                    .getAttribute(ATTR_MODEL_CONTAINER_TITLE);

                    if (modelTitleFromElement != null
                            && modelTitleFromElement.equals(modelTitle)) {

                        alreadyExistingElement = (Element) item;
                        break;
                    }
                }
            }
        }

        return alreadyExistingElement;
    }

    private Element createSourceRootElement(String projectName,
            Document sourceDocument) {
        Element rootElement = sourceDocument.createElement(ROOT_ELEMENT_NAME);
        rootElement.setAttribute(PROJECT_NAME_PROPERTY_NAME, projectName);
        return rootElement;
    }

    @SuppressWarnings("deprecation")
    private IFile writeXmlFile(Document doc, IPath outputFile) {
        try {
            // Prepare the DOM document for writing
            Source source = new DOMSource(doc);
            // Prepare the output file
            IFile worspaceFile =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getFileForLocation(outputFile);
            File file = outputFile.toFile();
            if (file != null && !file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    XpdResourcesUIActivator.getDefault().getLogger().error(e);
                }
            }
            Result result = new StreamResult(file);

            // Write the DOM document to the file
            Transformer xformer =
                    TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
            try {
                worspaceFile.refreshLocal(IResource.DEPTH_ZERO, null);
                worspaceFile.setDerived(true);
            } catch (CoreException e) {
                XpdResourcesUIActivator.getDefault().getLogger().error(e);
            }
            return worspaceFile;
        } catch (TransformerConfigurationException e) {
            XpdResourcesUIActivator.getDefault().getLogger().error(e);
        } catch (TransformerException e) {
            XpdResourcesUIActivator.getDefault().getLogger().error(e);
        }
        return null;
    }

    /**
     * Create project export folders in the workspace
     * 
     * @param folder
     * @throws CoreException
     */
    @SuppressWarnings("deprecation")
    protected IPath createTempFolder(IFolder folder) throws CoreException {
        // Refresh the folder to align it with the file system
        if (!folder.isSynchronized(IResource.DEPTH_ONE)) {
            folder.refreshLocal(IResource.DEPTH_ONE, null);
        }

        if (!folder.exists()) {
            IContainer parent = folder.getParent();

            if (parent instanceof IFolder) {
                IFolder parentFolder = (IFolder) parent;
                // Create parent folder
                createTempFolder(parentFolder);
            }
            // Create current folder
            folder.create(true, true, null);
            folder.setDerived(true);
            return folder.getLocation();
        }
        return folder.getLocation();
    }

}
