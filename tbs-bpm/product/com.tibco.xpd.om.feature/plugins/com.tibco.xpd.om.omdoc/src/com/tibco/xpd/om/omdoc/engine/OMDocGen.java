/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.model.api.DefaultResourceLocator;
import org.eclipse.birt.report.model.api.ModuleHandle;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.omdoc.Activator;
import com.tibco.xpd.om.omdoc.internal.Messages;
import com.tibco.xpd.om.omdoc.wizards.ImageCreator;
import com.tibco.xpd.om.omdoc.wizards.OMResourceCopier;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.documentation.DocGenModelInfo;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGenModelInfo;
import com.tibco.xpd.ui.documentation.engine.AbstractBIRTDocGen;

/**
 * This class is responsible for the generation of documentation for the
 * Organization model
 * 
 * @author mtorres
 */
public class OMDocGen extends AbstractBIRTDocGen {

    private static final String ORGANIZATION_TYPE = "organization";//$NON-NLS-1$

    private static final String ORGUNIT_TYPE = "orgunit";//$NON-NLS-1$

    private static final String POSITION_TYPE = "position";//$NON-NLS-1$

    private static final String iso8601DateTimePattern =
            "yyyy-MM-dd'T'HH:mm:ssz"; //$NON-NLS-1$ 

    private Map<String, IPath> modelToImageMap;

    @Override
    protected void generateExtraResources(IFile inputFile,
            IPath outputFilePath, IDocGenInfo docGenInfo) {
        if (inputFile != null && outputFilePath != null) {
            try {
                ImageCreator ic = new ImageCreator();
                ic.create(inputFile,
                        new File(outputFilePath.toPortableString()));
                modelToImageMap = ic.getModelImagesMap();
            } catch (CoreException e) {
                Activator.getDefault().getLogger().error(e);
            }
            OMResourceCopier resourceCopier = new OMResourceCopier();
            try {
                resourceCopier.perform(new SubProgressMonitor(
                        new NullProgressMonitor(), 30),
                        inputFile,
                        outputFilePath.toFile());
            } catch (CoreException e) {
                Activator.getDefault().getLogger().error(e);
            }
        }
    }

    @Override
    protected InputStream getDefaultTemplateStream(EngineConfig config,
            IDocGenInfo docGenInfo) {
        InputStream defaultTemplateStream = null;
        // set the resource location for it to find the localised properties
        // for birt reports
        config.setResourceLocator(new DefaultResourceLocator() {
            @Override
            public URL findResource(ModuleHandle moduleHandle, String fileName,
                    int type) {
                URL url =
                        Activator.getDefault().getBundle()
                                .getResource(fileName);
                return url;
            }

            /**
             * @see org.eclipse.birt.report.model.api.DefaultResourceLocator#findResource(org.eclipse.birt.report.model.api.ModuleHandle,
             *      java.lang.String, int, java.util.Map)
             * 
             * @param moduleHandle
             * @param fileName
             * @param type
             * @param appContext
             * @return
             */
            @SuppressWarnings("rawtypes")
            @Override
            public URL findResource(ModuleHandle moduleHandle, String fileName,
                    int type, Map appContext) {
                URL url =
                        Activator.getDefault().getBundle()
                                .getResource(fileName);
                return url;
            }
        });

        try {
            IPath reportFile = new Path("reports").append( //$NON-NLS-1$
                    "orgModelReportFlattened.rptdesign");//$NON-NLS-1$
            defaultTemplateStream =
                    FileLocator.openStream(Activator.getDefault().getBundle(),
                            reportFile,
                            false);
        } catch (IOException e) {
            XpdResourcesUIActivator.getDefault().getLogger().equals(e);
            if (docGenInfo != null && docGenInfo.getGenerationStatus() != null) {
                docGenInfo.setGenerationStatus(Status.CANCEL_STATUS);
            }
        }
        return defaultTemplateStream;
    }

    @Override
    protected String getGeneratorRootFolderName() {
        return Messages.OMDocGen_GeneratorRootFolderName;
    }

    @Override
    protected List<IDocGenModelInfo> createDocGenModelInfo(IResource resource) {
        if (resource != null) {
            WorkingCopy tempWC =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
            if (tempWC instanceof OMWorkingCopy) {
                OMWorkingCopy omWorkingCopy = (OMWorkingCopy) tempWC;
                EObject rootElement = omWorkingCopy.getRootElement();
                if (rootElement instanceof OrgModel) {
                    OrgModel model = (OrgModel) rootElement;
                    List<IDocGenModelInfo> docGenModelInfo =
                            new ArrayList<IDocGenModelInfo>();
                    String changeDate = getChangeDate(resource);
                    String packageLabel = model.getDisplayName();
                    String author = model.getAuthor();
                    EList<Organization> organizations =
                            model.getOrganizations();
                    if (organizations != null && !organizations.isEmpty()) {
                        for (Organization organization : organizations) {
                            DocGenModelInfo docGenOMModelInfo =
                                    new DocGenModelInfo();
                            String organizationLabel =
                                    organization.getDisplayName();
                            String description = organization.getDescription();
                            String imagePath =
                                    getImagePathForModelId(organization.getId());
                            String link =
                                    getOrganizationElementLink(organization
                                            .getName());
                            List<IDocGenModelInfo> orgUnitSubModels =
                                    createOrgUnitSubModels(organization);

                            /*
                             * store the path of the image folder.
                             */
                            String imageFolderPath;

                            if (imagePath == null) {
                                /*
                                 * Kapil: The Image Path should really never be
                                 * null, however the bug raised in XPD-7387
                                 * needs to be fixed first as it does not
                                 * generate the diagram info for the
                                 * organizations and hence does not create
                                 * images for them. So for now this is a
                                 * temporary fix, when XPD-7387 is fixed the
                                 * code won't enter this if statement ever!
                                 */
                                imageFolderPath = "Organization Model/images"; //$NON-NLS-1$
                            } else {
                                /*
                                 * gets the path of the Images Folder.
                                 */
                                imageFolderPath =
                                        new Path(imagePath)
                                                .removeLastSegments(1)
                                                .toString();
                            }
                            /*
                             * SCF-420: We no longer pass the omcontainer and om
                             * title to generate the OM index.html documentation
                             * as it was very specific to OM doc export. Now we
                             * have changed the index.xml(in the
                             * .documentationOutput folder) file that gets
                             * generated to have more generalised elements like
                             * 'modelconatiner' which specifies the title of the
                             * table and the priority in which the table should
                             * be displayed; which has the child element 'model'
                             * that decides the contents of the table.
                             */
                            populateDocGenModelInfo(docGenOMModelInfo,
                                    imagePath,
                                    organizationLabel,
                                    packageLabel,
                                    description,
                                    changeDate,
                                    author,
                                    null,
                                    link,
                                    orgUnitSubModels,
                                    null,
                                    imageFolderPath
                                            + "/" + OMResourceCopier.ORGANIZATION_IMAGE_NAME, //$NON-NLS-1$
                                    Messages.OMDocGen_OmModelContainer_title,
                                    "300", /* Priority set to 300 *///$NON-NLS-1$
                                    imageFolderPath
                                            + "/"//$NON-NLS-1$
                                            + OMResourceCopier.ORGANIZATION_IMAGE_NAME);
                            docGenModelInfo.add(docGenOMModelInfo);
                        }
                    }
                    return docGenModelInfo;
                }
            }
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("deprecation")
    private List<IDocGenModelInfo> createOrgUnitSubModels(
            Organization organization) {
        if (organization != null) {
            EList<OrgUnit> units = organization.getUnits();
            if (units != null && !units.isEmpty()) {
                List<IDocGenModelInfo> docGenModelInfo =
                        new ArrayList<IDocGenModelInfo>();
                String modelName = organization.getName();
                for (OrgUnit orgUnit : units) {
                    DocGenModelInfo docGenOMModelInfo = new DocGenModelInfo();
                    String name = orgUnit.getName();
                    String description = orgUnit.getDescription();
                    String imagePath = getImagePathForModelType(orgUnit);
                    String link = getOrganizationElementLink(orgUnit.getId());
                    populateDocGenModelInfo(docGenOMModelInfo,
                            imagePath,
                            ORGANIZATION_TYPE,
                            ORGUNIT_TYPE,
                            name,
                            modelName,
                            description,
                            null,
                            null,
                            null,
                            link,
                            createPositionSubModels(orgUnit),
                            null);
                    docGenModelInfo.add(docGenOMModelInfo);
                }
                return docGenModelInfo;
            }
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("deprecation")
    private List<IDocGenModelInfo> createPositionSubModels(OrgUnit orgUnit) {
        if (orgUnit != null) {
            EList<Position> positions = orgUnit.getPositions();
            if (positions != null && !positions.isEmpty()) {
                List<IDocGenModelInfo> docGenModelInfo =
                        new ArrayList<IDocGenModelInfo>();
                String modelName = orgUnit.getName();
                String link =
                        getOrganizationElementLink(orgUnit.getId()
                                + " children");//$NON-NLS-1$
                for (Position position : positions) {
                    DocGenModelInfo docGenOMModelInfo = new DocGenModelInfo();
                    String name = position.getName();
                    String description = position.getDescription();
                    String imagePath = getImagePathForModelType(position);
                    populateDocGenModelInfo(docGenOMModelInfo,
                            imagePath,
                            ORGUNIT_TYPE,
                            POSITION_TYPE,
                            name,
                            modelName,
                            description,
                            null,
                            null,
                            null,
                            link,
                            null,
                            null);
                    docGenModelInfo.add(docGenOMModelInfo);
                }
                return docGenModelInfo;
            }
        }
        return Collections.emptyList();
    }

    private String getImagePathForModelId(String modelId) {
        if (modelToImageMap != null && modelId != null) {
            IPath imagePath = modelToImageMap.get(modelId);
            if (imagePath != null && getCurrentDocGenInfo() != null) {
                IPath baseOutputPath =
                        getBaseOutputPath(getCurrentDocGenInfo());
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

    private String getImagePathForModelType(OrgTypedElement orgTypedElement) {
        if (orgTypedElement instanceof OrgUnit) {
            return getGeneratorRootFolderName() + "/" //$NON-NLS-1$
                    + OMResourceCopier.ORGUNIT_IMAGE_TYPE_PATH;
        } else if (orgTypedElement instanceof Position) {
            return getGeneratorRootFolderName() + "/" //$NON-NLS-1$
                    + OMResourceCopier.POSITION_IMAGE_TYPE_PATH;
        }
        return null;
    }

    @Override
    protected void clearOldResources(IFile inputFile, IPath outputFilePath,
            IDocGenInfo docGenInfo) {
        // deleteImages();
    }

    private String getOrganizationElementLink(String orgName) {
        if (orgName != null && getHtmlOutput() != null) {
            IPath baseOutputPath = getBaseOutputPath(getCurrentDocGenInfo());
            if (baseOutputPath != null) {
                String absoluteBaseOutputStrPath =
                        baseOutputPath.addTrailingSeparator()
                                .toPortableString();
                String orgLink =
                        makePathRelative(absoluteBaseOutputStrPath,
                                getHtmlOutput().toPortableString());
                if (orgLink != null) {
                    return orgLink + "#" + orgName;//$NON-NLS-1$
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
